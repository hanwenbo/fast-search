package com.github.search.pub.aggr;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 查询
 * @time: 2018年07月04日
 * @modifytime:
 */
public class AggrResponse {

    //字段声明
    private String field;

    // 桶
    private List<AggrBucketResponse> list;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<AggrBucketResponse> getList() {
        return list;
    }

    public void setList(List<AggrBucketResponse> list) {
        this.list = list;
    }
}
