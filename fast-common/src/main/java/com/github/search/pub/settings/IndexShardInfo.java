package com.github.search.pub.settings;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月22日
 * @modifytime:
 */
public class IndexShardInfo {

    private String uuid;

    private int id ;

    private String name ;

    private String nodeId;

    private boolean primary;

    private long count ;

    private long delete;

    private String dataPath;

    private String statePath;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getDelete() {
        return delete;
    }

    public void setDelete(long delete) {
        this.delete = delete;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getStatePath() {
        return statePath;
    }

    public void setStatePath(String statePath) {
        this.statePath = statePath;
    }

    @Override
    public String toString() {
        return "IndexShardInfo{" +
                "uuid='" + uuid + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", primary=" + primary +
                ", count=" + count +
                ", delete=" + delete +
                ", dataPath='" + dataPath + '\'' +
                ", statePath='" + statePath + '\'' +
                '}';
    }
}
