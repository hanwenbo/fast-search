package com.github.search.pub.aggr.histogram;


import com.github.search.enums.MetricsEnums;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月05日
 * @modifytime:
 */
public class SimpleHistogram {

    private SimpleHistogram() {
    }

    /**
     * index
     */
    private String index ;

    /**
     * type
     */
    private String type;

    /**
     * 桶
     */
    private String field;

    /**
     * 间隔
     */
    private double interval;

    /**
     * 第二
     */
    private String twoField;

    /**
     * 指标
     */
    private MetricsEnums metrics;


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public String getTwoField() {
        return twoField;
    }

    public void setTwoField(String twoField) {
        this.twoField = twoField;
    }

    public MetricsEnums getMetrics() {
        return metrics;
    }

    public void setMetrics(MetricsEnums metrics) {
        this.metrics = metrics;
    }

    public static class Builder {
        SimpleHistogram target;
        public Builder(String index, String type) {
            target = new SimpleHistogram();
            target.setIndex(index);
            target.setType(type);
        }

        public Builder fieldOne(String field,double interval){
            target.setField(field);
            target.setInterval(interval);
            return this;
        }

        public Builder fieldTwo(String field , MetricsEnums metrics){
            target.setTwoField(field);
            target.setMetrics(metrics);
            return this;
        }

        public SimpleHistogram build() {
            return target;
        }
    }
}
