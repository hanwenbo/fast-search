package com.github.search.pub.aggr;

import com.github.search.enums.MetricsEnums;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 桶,指标
 * @time: 2018年07月04日
 * @modifytime:
 *
 */
public class AggrSearchEntity {

    private AggrSearchEntity() {
    }

    private String field;
    /**
     *
     * {@link com.github.search.enums.MetricsEnums}
     */
    private List<AggrMetricsEntity> metrics;

    private AggrSearchEntity aggrSearchEntity;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<AggrMetricsEntity> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<AggrMetricsEntity> metrics) {
        this.metrics = metrics;
    }

    public AggrSearchEntity getAggrSearchEntity() {
        return aggrSearchEntity;
    }

    public void setAggrSearchEntity(AggrSearchEntity aggrSearchEntity) {
        this.aggrSearchEntity = aggrSearchEntity;
    }

    public static class Builder {

        private AggrSearchEntity target ;

        public Builder(String field) {
            target = new AggrSearchEntity();
            target.setField(field);
        }

        public Builder addMetrics(AggrMetricsEntity metrics){
            if(target.getMetrics() == null) {
                target.setMetrics(new ArrayList<AggrMetricsEntity>());
            }
            target.getMetrics().add(metrics);
            return this;
        }

        public Builder setChildBucket(AggrSearchEntity bucket){
            target.setAggrSearchEntity(bucket);
            return this;
        }

        public AggrSearchEntity build() {
            if(StringUtils.isBlank(target.getField())){
                throw new IllegalStateException("分组字段( field )不能设置为空");
            }
            return target;
        }
    }
}
