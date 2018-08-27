package com.github.search.pub.aggr;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:桶
 * @time: 2018年07月04日
 * @modifytime:
 */
public class AggrBucketResponse {

    private String field;

    private long count;

    private AggrMetricsResponse max;

    private AggrMetricsResponse min ;

    private AggrMetricsResponse avg;

    private AggrMetricsResponse sum;

    private AggrResponse resp;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public AggrMetricsResponse getMax() {
        return max;
    }

    public void setMax(AggrMetricsResponse max) {
        this.max = max;
    }

    public AggrMetricsResponse getMin() {
        return min;
    }

    public void setMin(AggrMetricsResponse min) {
        this.min = min;
    }

    public AggrMetricsResponse getAvg() {
        return avg;
    }

    public void setAvg(AggrMetricsResponse avg) {
        this.avg = avg;
    }

    public AggrMetricsResponse getSum() {
        return sum;
    }

    public void setSum(AggrMetricsResponse sum) {
        this.sum = sum;
    }

    public AggrResponse getResp() {
        return resp;
    }

    public void setResp(AggrResponse resp) {
        this.resp = resp;
    }
}
