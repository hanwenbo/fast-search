package com.github.search.pub.settings;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月21日
 * @modifytime:
 */
public class ClusterIndexInfo {

    private String nodeId;

    private String nodeName;

    private String address;

    private Map<String, Object> info;

    private String[] gcs;


    private JvmInfo jvm ;


    private List<ClusterPluginInfo> pluginsInfo ;


    private List<ClusterShardInfo> shardsInfo;


    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public String[] getGcs() {
        return gcs;
    }

    public void setGcs(String[] gcs) {
        this.gcs = gcs;
    }

    public JvmInfo getJvm() {
        return jvm;
    }

    public void setJvm(JvmInfo jvm) {
        this.jvm = jvm;
    }

    public List<ClusterPluginInfo> getPluginsInfo() {
        return pluginsInfo;
    }

    public void setPluginsInfo(List<ClusterPluginInfo> pluginsInfo) {
        this.pluginsInfo = pluginsInfo;
    }

    public List<ClusterShardInfo> getShardsInfo() {
        return shardsInfo;
    }

    public void setShardsInfo(List<ClusterShardInfo> shardsInfo) {
        this.shardsInfo = shardsInfo;
    }
}
