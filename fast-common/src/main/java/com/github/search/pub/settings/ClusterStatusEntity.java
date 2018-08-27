package com.github.search.pub.settings;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月21日
 * @modifytime:
 */
public class ClusterStatusEntity implements Serializable {

    private String clusterName ;

    private int nodeNum;

    private int dataNodeNum;

    private int status;

    List<IndexStatusEntity> indexs;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public int getDataNodeNum() {
        return dataNodeNum;
    }

    public void setDataNodeNum(int dataNodeNum) {
        this.dataNodeNum = dataNodeNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<IndexStatusEntity> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<IndexStatusEntity> indexs) {
        this.indexs = indexs;
    }

    @Override
    public String toString() {
        return "ClusterStatusEntity{" +
                "clusterName='" + clusterName + '\'' +
                ", nodeNum=" + nodeNum +
                ", dataNodeNum=" + dataNodeNum +
                ", status=" + status +
                ", indexs=" + indexs +
                '}';
    }
}
