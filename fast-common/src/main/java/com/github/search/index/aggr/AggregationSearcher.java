package com.github.search.index.aggr;

import com.github.search.commons.ESUtils;
import com.github.search.enums.MetricsEnums;
import com.github.search.page.BoolPager;
import com.github.search.pub.aggr.AggrIndexEntity;
import com.github.search.pub.aggr.AggrMetricsEntity;
import com.github.search.pub.aggr.AggrResponse;
import com.github.search.pub.aggr.AggrSearchEntity;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月04日
 * @modifytime:
 */
public class AggregationSearcher {

    public static AggrResponse aggrSearch(TransportClient client , AggrIndexEntity entity) {
        SearchRequestBuilder builder = client.prepareSearch(entity.getIndex()).setTypes(entity.getType());
        AggrSearchEntity aggrSearchEntity = entity.getAggrSearchEntity();
        AggregationBuilder aggregationBuilder = basicAggreSearch(aggrSearchEntity);
        SearchResponse response = builder.addAggregation(aggregationBuilder).execute().actionGet();
        Aggregations aggregations = response.getAggregations();
        AggrResponse build = AggregationResponseBuilders.prepareResponse(aggrSearchEntity).setAggs(aggregations).build();
        return build;
    }


    public static AggrResponse aggrSearchPager(TransportClient client , AggrIndexEntity entity, BoolPager pager) {
        SearchRequestBuilder builder = client.prepareSearch(entity.getIndex()).setTypes(entity.getType());
        //根据pager初始化boolQuery
        BoolQueryBuilder boolQueryBuilder = ESUtils.getBoolQueryBuilder(pager);
        if(boolQueryBuilder != null){
            builder.setQuery(boolQueryBuilder);
        }
        AggrSearchEntity aggrSearchEntity = entity.getAggrSearchEntity();
        AggregationBuilder aggregationBuilder = basicAggreSearch(aggrSearchEntity);
        SearchResponse response = builder.addAggregation(aggregationBuilder).execute().actionGet();
        Aggregations aggregations = response.getAggregations();
        AggrResponse build = AggregationResponseBuilders.prepareResponse(aggrSearchEntity).setAggs(aggregations).build();
        return build;
    }



    /**
     *
     * @param entity
     * @return
     */
    public static AggregationBuilder basicAggreSearch(AggrSearchEntity entity) {
        if(entity != null) {
            List<AggrMetricsEntity> metrics = entity.getMetrics();
            if( metrics == null || metrics.size() == 0){
                TermsAggregationBuilder terms = AggregationBuilders.terms(entity.getField()).field(entity.getField()).minDocCount(1).size(40);
                if(entity != null){
                    AggregationBuilder aggregationBuilder = basicAggreSearch(entity.getAggrSearchEntity());
                    if(aggregationBuilder != null){
                        terms.subAggregation(aggregationBuilder);
                    }
                }
                return terms;
            }else {
                TermsAggregationBuilder terms = AggregationBuilders.terms(entity.getField()).field(entity.getField());
                for (AggrMetricsEntity i :  metrics) {
                    switch(i.getMetrics()) {
                        case MAX :
                            MaxAggregationBuilder maxField = AggregationBuilders.max(i.getField()+ MetricsEnums.MAX.getMsg()).field(i.getField());
                            terms.subAggregation(maxField);
                            break;
                        case MIN :
                            MinAggregationBuilder minField = AggregationBuilders.min(i.getField()+MetricsEnums.MIN.getMsg()).field(i.getField());
                            terms.subAggregation(minField);
                            break;
                        case AVG :
                            AvgAggregationBuilder avgField = AggregationBuilders.avg(i.getField()+MetricsEnums.AVG.getMsg()).field(i.getField());
                            terms.subAggregation(avgField);
                            break;
                        case SUM:
                            SumAggregationBuilder sumField = AggregationBuilders.sum(i.getField()+MetricsEnums.SUM.getMsg()).field(i.getField());
                            terms.subAggregation(sumField);
                            break;
                    }
                }
                if(entity != null){
                    AggregationBuilder aggregationBuilder = basicAggreSearch(entity.getAggrSearchEntity());
                    if (aggregationBuilder != null) {
                        terms.subAggregation(aggregationBuilder);
                    }
                }
                return terms;
            }
        }else {
            return null;
        }

    }




}
