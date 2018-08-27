package com.github.search.pub.settings;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月21日
 * @modifytime:
 */
public class IndexStatusEntity implements Serializable {

    private String index;

    private int sharedNum;

    private int replicasNum;

    private int status;

    // 活跃分片数(分片+ 副本总数)
    private int activeShards;
    // 活跃主分片数
    private int activePrimaryShards;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getSharedNum() {
        return sharedNum;
    }

    public void setSharedNum(int sharedNum) {
        this.sharedNum = sharedNum;
    }

    public int getReplicasNum() {
        return replicasNum;
    }

    public void setReplicasNum(int replicasNum) {
        this.replicasNum = replicasNum;
    }

    public int getActiveShards() {
        return activeShards;
    }

    public void setActiveShards(int activeShards) {
        this.activeShards = activeShards;
    }

    public int getActivePrimaryShards() {
        return activePrimaryShards;
    }

    public void setActivePrimaryShards(int activePrimaryShards) {
        this.activePrimaryShards = activePrimaryShards;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IndexStatusEntity{" +
                "index='" + index + '\'' +
                ", sharedNum=" + sharedNum +
                ", replicasNum=" + replicasNum +
                ", status=" + status +
                ", activeShards=" + activeShards +
                ", activePrimaryShards=" + activePrimaryShards +
                '}';
    }
}
