package com.github.search.pub.settings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月21日
 * @modifytime:
 */
public class ClusterEntityInfo {

    private String clusterName;

    private int status;

    private List<PluginsInfo> plugins;

    private List<ClusterIndexInfo> indexList;


    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PluginsInfo> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<PluginsInfo> plugins) {
        this.plugins = plugins;
    }

    public List<ClusterIndexInfo> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<ClusterIndexInfo> indexList) {
        this.indexList = indexList;
    }
}
