package com.github.search.index.aggr;

import com.github.search.pub.aggr.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月04日
 * @modifytime:
 */
public class AggregationResponseBuilder {

    private AggrSearchEntity entity;

    private Aggregations aggs;


    public AggrSearchEntity getEntity() {
        return entity;
    }

    public AggregationResponseBuilder setEntity(AggrSearchEntity entity) {
        this.entity = entity;
        return this;
    }

    public Aggregations getAggs() {
        return aggs;
    }

    public AggregationResponseBuilder setAggs(Aggregations aggs) {
        this.aggs = aggs;
        return this;
    }


    public AggrResponse build(){
        if(entity == null || aggs == null) {
            throw new IllegalStateException("entity or aggs 为空");
        }
        return convert(entity,aggs);
    }

    private AggrResponse convert(AggrSearchEntity entity , Aggregations aggs) {
        if(entity != null) {
            Map<String, Aggregation> aggregationMap = aggs.asMap();
            Terms terms = (Terms)aggregationMap.get(entity.getField());
            AggrResponse response = itemConvert(entity, terms);
            return response;
        }
        return null;


    }


    private static AggrResponse itemConvert(AggrSearchEntity entity ,Terms stringTerms) {
        AggrResponse response = new AggrResponse();
        List<AggrBucketResponse> list = new ArrayList<>();
        response.setList(list);

        response.setField(entity.getField());
        List<AggrMetricsEntity> metrics = entity.getMetrics();
        Iterator<? extends Terms.Bucket> iterator = stringTerms.getBuckets().iterator();
        while(iterator.hasNext()) {
            Terms.Bucket bucket = iterator.next();
            AggrBucketResponse resp = new AggrBucketResponse();
            String key = bucket.getKey().toString();
            long docCount = bucket.getDocCount();
            resp.setField(key);
            resp.setCount(docCount);
            Aggregations aggregations = bucket.getAggregations();
            Map<String, Aggregation> aggregationMap = aggregations.asMap();
            if(entity.getMetrics() != null && entity.getMetrics().size() > 0) {
                for (AggrMetricsEntity e: metrics) {
                    getBucketValue(e, aggregationMap ,resp);
                }
            }
            AggrSearchEntity childSearchEntity = entity.getAggrSearchEntity();
            if(childSearchEntity != null) {
                String field = childSearchEntity.getField();
                Terms aggregation = (Terms)aggregationMap.get(field);
                AggrResponse r = itemConvert(childSearchEntity, aggregation);
                resp.setResp(r);
            }
            list.add(resp);
        }
        return response;
    }


    private static AggrMetricsResponse getBucketValue(AggrMetricsEntity entity , Map<String, Aggregation> aggregationMap, AggrBucketResponse resp) {
        Aggregation aggregation = aggregationMap.get(entity.getField() + entity.getMetrics().getMsg());
        AggrMetricsResponse r = null;
        switch (entity.getMetrics()) {
            case MAX :
                double maxValue = ((InternalMax) aggregation).getValue();
                r = new AggrMetricsResponse(entity.getField(),maxValue);
                resp.setMax(r);
                break;
            case MIN :
                double minValue = ((InternalMin) aggregation).getValue();
                r = new AggrMetricsResponse(entity.getField(),minValue);
                resp.setMin(r);
                break;
            case AVG :
                double avgValue = ((InternalAvg) aggregation).getValue();
                r = new AggrMetricsResponse(entity.getField(),avgValue);
                resp.setAvg(r);
                break;
            case SUM :
                double sumValue = ((InternalSum) aggregation).getValue();
                r = new AggrMetricsResponse(entity.getField(),sumValue);
                resp.setSum(r);
                break;
        }
        return r;
    }


}
