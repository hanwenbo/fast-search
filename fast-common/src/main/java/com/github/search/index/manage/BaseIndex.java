package com.github.search.index.manage;

import com.github.search.commons.ESConnect;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 索引维护工具类
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:01
 */
public class BaseIndex {


    /**
     *
     * 只创建索引 ,使用的默认设置 ,并没有映射
     *
     * @param client
     * @param index
     * @return
     */
    public static int createSimpleIndex(TransportClient client , String index) {
        CreateIndexResponse response = client.admin().indices().prepareCreate(index).get();
        if (response.isAcknowledged()) {
            return 1;
        }
        return 0;
    }

    /**
     * 定义基本索引
     * @param client
     * @param index
     * @param shares 一个索引中含有的主分片(Primary Shard)的数量,索引创建后这个值是不能被更改
     * @param replicas 每一个主分片关联的副本分片(Replica Shard)的数量，默认值是1。这个设置在任何时候都可以被修改。
     */
    public static int createIndexBasic(TransportClient client, String index, Integer shares , Integer replicas){
        if(shares == null || shares == 0){
            shares = 1;
        }
        if(replicas == null || replicas == 0){
            replicas = 1;
        }
        try{
            AdminClient adminClient = client.admin();
            IndicesAdminClient indicesAdminClient = adminClient.indices();
            indicesAdminClient.prepareCreate(index)
                    .setSettings(Settings.builder()
                            .put("index.number_of_shards", shares)
                            .put("index.number_of_replicas",replicas)
                    ).get();
        }finally {
            ESConnect.closeClient(client);
        }
        return 1;
    }

    /**
     * 修改创建索引的副本数
     * @param client  客户端链接
     * @param replicas 副本数
     */
    public static int updateReplicas(TransportClient client,String index , Integer replicas){
        try{
            AdminClient adminClient = client.admin();
            IndicesAdminClient indicesAdminClient = adminClient.indices();
            indicesAdminClient.prepareUpdateSettings(index).setSettings(Settings.builder().put("index.number_of_replicas",replicas)).get();
        }finally {
            ESConnect.closeClient(client);
        }
        return 1;
    }


    /**
     * 制定Json文件创建索引
     * @param client
     * @param index
     * @param confPath  相对于项目根路径的目录结构
     * @throws IOException
     */
    public static int createWithSetting(TransportClient client ,String index,String confPath)throws IOException{
        ClassLoader classLoader = BaseIndex.class.getClassLoader();
        String jsonStr = IOUtils.toString(classLoader.getResourceAsStream(confPath),"UTF-8");
        try{
            AdminClient adminClient = client.admin();
            IndicesAdminClient indicesAdminClient = adminClient.indices();
            indicesAdminClient.prepareCreate(index).setSettings(jsonStr, XContentType.JSON).get();
        }finally {
            ESConnect.closeClient(client);
        }
        return 1;
    }

    /**
     * 创建索引类型Mapping映射
     * @param client
     * @param index
     * @param type
     * @param confPath
     * @throws IOException
     */
    public static int createMapping(TransportClient client,String index , String type ,String confPath)throws IOException{
        ClassLoader classLoader = BaseIndex.class.getClassLoader();
        String jsonStr = IOUtils.toString(classLoader.getResourceAsStream(confPath),"UTF-8");
        return createMappingStr( client, index ,  type , jsonStr);
    }

    public static int createMappingStr(TransportClient client,String index , String type ,String mappingStr){
        try{
            AdminClient adminClient = client.admin();
            IndicesAdminClient indicesAdminClient = adminClient.indices();
            indicesAdminClient.preparePutMapping(index).setType(type).setSource(mappingStr,XContentType.JSON).execute().actionGet();
        }finally {
            ESConnect.closeClient(client);
        }
        return 1;
    }

    /**
     * 删除索引数据 //TODO
     * @param client
     * @param index
     * @throws Exception
     */
    public static void emptyIndex(TransportClient client,String index)throws Exception{

    }

    /**
     * 删除整个索引
     * @param client
     * @param index
     * @throws Exception
     */
    public static boolean deleteIndex(TransportClient client,String index)throws Exception{
        DeleteIndexResponse response = client.admin().indices().prepareDelete(index).execute().actionGet();
        return response.isAcknowledged();
    }


}
