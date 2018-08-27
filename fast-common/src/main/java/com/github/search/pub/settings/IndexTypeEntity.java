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
public class IndexTypeEntity {

    private String indexName;

    private List<String> typeNames;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public List<String> getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(List<String> typeNames) {
        this.typeNames = typeNames;
    }

    @Override
    public String toString() {
        return "IndexTypeEntity{" +
                "indexName='" + indexName + '\'' +
                ", typeNames=" + typeNames +
                '}';
    }
}
