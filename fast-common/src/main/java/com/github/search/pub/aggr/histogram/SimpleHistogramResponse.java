package com.github.search.pub.aggr.histogram;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月05日
 * @modifytime:
 */
public class SimpleHistogramResponse {

    /**
     * 横坐标
     */
    private String field;


    /**
     * 文档数
     */
    private long docCount;


    /**
     * 指标
     */
    private double metrics;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public long getDocCount() {
        return docCount;
    }

    public void setDocCount(long docCount) {
        this.docCount = docCount;
    }

    public double getMetrics() {
        return metrics;
    }

    public void setMetrics(double metrics) {
        this.metrics = metrics;
    }
}
