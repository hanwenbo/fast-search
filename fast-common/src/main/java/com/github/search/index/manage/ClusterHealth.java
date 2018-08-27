package com.github.search.index.manage;

import com.github.search.pub.settings.*;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.node.info.NodeInfo;
import org.elasticsearch.action.admin.cluster.node.stats.NodeStats;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsNodeResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.stats.CommonStats;
import org.elasticsearch.action.admin.indices.stats.IndexStats;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.action.admin.indices.stats.ShardStats;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.cluster.routing.ShardRouting;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.monitor.jvm.JvmStats;
import org.elasticsearch.plugins.PluginInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 集群健康监测API
 * @time: 2018年06月21日
 * @modifytime:
 */
public class ClusterHealth {





    /**
     * 查询集群的健康状况
     *
     * @param client
     */
    public static ClusterStatusEntity clusterHealth(TransportClient client){
        ClusterStatusEntity entity = new ClusterStatusEntity();
        ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get(); // 所有指标的获得信息
        String clusterName = healths.getClusterName();  // 访问集群名称
        int numberOfDataNodes = healths.getNumberOfDataNodes();  // 数据节点的总数
        int numberOfNodes = healths.getNumberOfNodes();  // 得到节点的总数
        ClusterHealthStatus clusterStatus = healths.getStatus();
        entity.setClusterName(clusterName);
        entity.setNodeNum(numberOfNodes);
        entity.setDataNodeNum(numberOfDataNodes);
        entity.setStatus(clusterStatus.value());

        Map<String, ClusterIndexHealth> indices = healths.getIndices();
        Set<String> strings = indices.keySet();
        List<IndexStatusEntity> indexList = new ArrayList<>(strings.size());
        for (String key: strings) {  // 遍历所有指标
            IndexStatusEntity e = new IndexStatusEntity();
            ClusterIndexHealth health = indices.get(key);
            String index = health.getIndex();  //  索引名称
            int numberOfShards = health.getNumberOfShards();   //  分片
            int numberOfReplicas = health.getNumberOfReplicas();  //  副本数
            ClusterHealthStatus status = health.getStatus();  //  健康状况
            int activeShards = health.getActiveShards();    // 活跃分片数(分片+ 副本总数)
            int activePrimaryShards = health.getActivePrimaryShards();  // 活跃主分片数

            e.setActiveShards(activeShards);
            e.setActivePrimaryShards(activePrimaryShards);
            e.setIndex(index);
            e.setSharedNum(numberOfShards);
            e.setReplicasNum(numberOfReplicas);
            e.setStatus(status.value());
            indexList.add(e);
        }
        entity.setIndexs(indexList);
        return entity;
    }


    /**
     * 获取节点信息
     * @param client
     * @return
     */
    public static ClusterEntityInfo clusterStatus(TransportClient client) {

        ClusterEntityInfo cInfo = new ClusterEntityInfo();
        ClusterStatsResponse response = client.admin().cluster().prepareClusterStats().get();

        ClusterName clusterName = response.getClusterName();
        cInfo.setClusterName(clusterName.value());
        ClusterHealthStatus status = response.getStatus();
        cInfo.setStatus(status.value());
        Set<PluginInfo> plugins = response.getNodesStats().getPlugins();
        List<PluginsInfo> pluginList = new ArrayList<>();
        if(plugins != null && plugins.size() > 0) {
            for (PluginInfo i : plugins) {
                String name = i.getName();
                String description = i.getDescription();
                String classname = i.getClassname();
                String version = i.getVersion();
                pluginList.add(new PluginsInfo(name,description,classname,version));
            }
        }
        cInfo.setPlugins(pluginList);

        List<ClusterIndexInfo> infoList = new ArrayList<>();
        List<ClusterStatsNodeResponse> nodes = response.getNodes();
        for (ClusterStatsNodeResponse node: nodes) {
            ClusterIndexInfo cii = new ClusterIndexInfo();
            DiscoveryNode n = node.getNode();
            String nodeId = n.getId();
            String nodeName = n.getName();
            String address = n.getAddress().toString();
            cii.setNodeId(nodeId);
            cii.setNodeName(nodeName);
            cii.setAddress(address);

            NodeInfo nodeInfo = node.nodeInfo();
            Map<String, Object> asStructuredMap = nodeInfo.getSettings().getAsStructuredMap();
            String[] gcCollectors = nodeInfo.getJvm().getGcCollectors();// 垃圾收集器 ParNew ConcurrentMarkSweep
            // 设置info参数
            cii.setInfo(asStructuredMap);
            // 设置GC参数
            cii.setGcs(gcCollectors);

            List<ClusterPluginInfo> infos = new ArrayList<>();
            List<PluginInfo> pluginInfos = nodeInfo.getPlugins().getPluginInfos();
            if (pluginInfos!= null && pluginInfos.size()> 0) {
                for (PluginInfo info : pluginInfos) {
                    ClusterPluginInfo pi = new ClusterPluginInfo();
                    String name = info.getName();
                    String description = info.getDescription();
                    String classname = info.getClassname();
                    pi.setName(name);
                    pi.setDescription(description);
                    pi.setClassName(classname);
                    infos.add(pi);
                }
            }
            // 设置插件参数
            cii.setPluginsInfo(infos);

            NodeStats nodeStats = node.nodeStats();
            JvmStats jvm = nodeStats.getJvm();
            JvmStats.Mem mem = jvm.getMem();
            long heapCommitted = mem.getHeapCommitted().getMb();
            long heapUsed = mem.getHeapUsed().getMb();
            long heapMax = mem.getHeapMax().getMb();
            JvmInfo ji = new JvmInfo();
            ji.setHeapCommitted(heapCommitted);
            ji.setHeapUsed(heapUsed);
            ji.setHeapMax(heapMax);
            // 设置JVM参数
            cii.setJvm(ji);


            ShardStats[] shardStatses = node.shardsStats();
            List<ClusterShardInfo> csiList = new ArrayList<>();
            for (ShardStats ss: shardStatses) {
                ClusterShardInfo csi = new ClusterShardInfo();
                String dataPath = ss.getDataPath();
                String statePath = ss.getStatePath();
                ShardRouting shardRouting = ss.getShardRouting();
                ShardId shardId = shardRouting.shardId();
                Index index = shardId.getIndex();
                String name = index.getName();
                String uuid = index.getUUID();
                int id = shardId.getId();
                csi.setId(id);
                csi.setUuid(uuid);
                csi.setName(name);
                csi.setDataPath(dataPath);
                csi.setStatePath(statePath);
                csiList.add(csi);
            }
            cii.setShardsInfo(csiList);

            infoList.add(cii);
        }
        cInfo.setIndexList(infoList);

        return cInfo;
    }




    public static List<IndexDocInfo> searchIndices(TransportClient client,String... indexs) throws Exception{

        IndicesStatsResponse response = null;
        if(indexs.length > 0) {
            response = client.admin().indices().prepareStats().setIndices(indexs).execute().get();
        }else {
            response = client.admin().indices().prepareStats().execute().get();
        }

        Map<String, IndexStats> indices = response.getIndices();
        Set<String> strings = indices.keySet();
        List<IndexDocInfo> idiList = new ArrayList<>();
        for (String key : strings) {

            IndexDocInfo docInfo = new IndexDocInfo();
            String indexName = key;
            IndexStats indexShardStatses = indices.get(indexName);
            CommonStats primaries = indexShardStatses.getPrimaries();
            long count = primaries.getDocs().getCount();
            long deleted = primaries.getDocs().getDeleted();
            long mb = primaries.getStore().getSize().getKb();
            docInfo.setIndexName(indexName);
            docInfo.setCount(count); // 文档总数
            docInfo.setDelete(deleted); // 删除文档数
            docInfo.setKb(mb);  // 文档占用空间大小

            ShardStats[] shards = indexShardStatses.getShards();
            List<IndexShardInfo> isiList = new ArrayList<>();
            for (ShardStats s : shards) {
                IndexShardInfo isi = new IndexShardInfo();
                String uuid = s.getShardRouting().shardId().getIndex().getUUID();
                String name = s.getShardRouting().shardId().getIndex().getName();
                boolean primary = s.getShardRouting().primary();
                int id = s.getShardRouting().shardId().getId();// 分片ID
                String nodeId = s.getShardRouting().currentNodeId();
                long shardCount = s.getStats().getDocs().getCount();
                long shardDelete = s.getStats().getDocs().getDeleted();
                String dataPath = s.getDataPath();
                String statePath = s.getDataPath();
                isi.setUuid(uuid);
                isi.setName(name);
                isi.setId(id);
                isi.setNodeId(nodeId);
                isi.setPrimary(primary);
                isi.setCount(shardCount);
                isi.setDelete(shardDelete);
                isi.setDataPath(dataPath);
                isi.setStatePath(statePath);
                isiList.add(isi);
            }
            docInfo.setShards(isiList);
            idiList.add(docInfo);
        }
        return idiList;
    }




}
