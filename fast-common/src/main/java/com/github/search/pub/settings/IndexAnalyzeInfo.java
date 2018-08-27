package com.github.search.pub.settings;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 索引分词展示实体
 * @time: 2018年06月29日
 * @modifytime:
 */
public class IndexAnalyzeInfo {

    private String indexName;

    private Set<String> analyzers;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Set<String> getAnalyzers() {
        return analyzers;
    }

    public void setAnalyzers(Set<String> analyzers) {
        this.analyzers = analyzers;
    }

    @Override
    public String toString() {
        return "IndexAnalyzeInfo{" +
                "indexName='" + indexName + '\'' +
                ", analyzers=" + analyzers +
                '}';
    }
}
