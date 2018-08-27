package com.github.search.pub.settings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月22日
 * @modifytime:
 */
public class IndexDocInfo {

    private String indexName;

    private long count;

    private long delete;
    // 磁盘空间占用量
    private long kb;

    List<IndexShardInfo> shards;


    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
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

    public long getKb() {
        return kb;
    }

    public void setKb(long kb) {
        this.kb = kb;
    }

    public List<IndexShardInfo> getShards() {
        return shards;
    }

    public void setShards(List<IndexShardInfo> shards) {
        this.shards = shards;
    }
}
