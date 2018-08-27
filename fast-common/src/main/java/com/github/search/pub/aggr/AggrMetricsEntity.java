package com.github.search.pub.aggr;


import com.github.search.enums.MetricsEnums;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 聚合指标实体
 * @time: 2018年07月04日
 * @modifytime:
 */
public class AggrMetricsEntity {

    private String field;

    private MetricsEnums metrics;

    public AggrMetricsEntity(String field, MetricsEnums metrics) {
        this.field = field;
        this.metrics = metrics;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public MetricsEnums getMetrics() {
        return metrics;
    }

    public void setMetrics(MetricsEnums metrics) {
        this.metrics = metrics;
    }
}
