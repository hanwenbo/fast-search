package com.github.search.pub.aggr;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 指标
 * @time: 2018年07月04日
 * @modifytime:
 */
public class AggrMetricsResponse {

    private String field;

    private double value;



    public AggrMetricsResponse(String field, double value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
