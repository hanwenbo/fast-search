package com.github.search.index.aggr.histogram;

import com.github.search.commons.ESUtils;
import com.github.search.enums.MetricsEnums;
import com.github.search.page.BoolPager;
import com.github.search.pub.aggr.histogram.SimpleHistogram;
import com.github.search.pub.aggr.histogram.SimpleHistogramResponse;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalHistogram;
import org.elasticsearch.search.aggregations.metrics.InternalNumericMetricsAggregation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月16日
 * @modifytime:
 */
public class DateHistogramSearcher {


    /**
     * @param client
     * @param histogram
     * @return
     */
    public static List<SimpleHistogramResponse> dateSearch(TransportClient client , SimpleHistogram histogram) {
        if(StringUtils.isBlank(histogram.getIndex()) || StringUtils.isBlank(histogram.getType()) || histogram.getInterval() == 0 || StringUtils.isBlank(histogram.getField())){
            throw new IllegalStateException("查询必要参数不能为空.");
        }
        if(histogram.getField().equals(histogram.getTwoField())){
            throw new IllegalStateException("两字段不能相同");
        }
        if(histogram.getTwoField() != null  && histogram.getMetrics() == null) {
            throw new IllegalStateException("指标不能为空");
        }
        List<SimpleHistogramResponse> simpleHistogramResponses = dateSearchPager(client,histogram,null);
        return simpleHistogramResponses;
    }



    public static List<SimpleHistogramResponse> dateSearchPager(TransportClient client , SimpleHistogram histogram , BoolPager pager) {
        DateHistogramAggregationBuilder builder = null;
        if(histogram.getTwoField() != null){
            AggregationBuilder metrics = buildMetrics(histogram);
            builder = AggregationBuilders.dateHistogram(histogram.getField())
                    .field(histogram.getField())
                    .dateHistogramInterval(DateHistogramInterval.MONTH)
                    .interval(2L)
                    .format("yyyy-MM-dd")
                    .subAggregation(metrics);
        }else {
            builder = AggregationBuilders.dateHistogram(histogram.getField())
                    .field(histogram.getField())
                    .dateHistogramInterval(DateHistogramInterval.MONTH)
                    .format("yyyy-MM-dd");
        }


        SearchRequestBuilder searchBuilder = client.prepareSearch(histogram.getIndex()).setTypes(histogram.getType());
        if(pager != null) {
            BoolQueryBuilder boolQueryBuilder = ESUtils.getBoolQueryBuilder(pager);
            if(boolQueryBuilder != null){
                searchBuilder.setQuery(boolQueryBuilder);
            }
        }
        SearchResponse response = searchBuilder
                .addAggregation(builder)
                .setSize(0)
                .get();
        List<SimpleHistogramResponse> simpleHistogramResponses = responseBuild(response, histogram);
        return simpleHistogramResponses;
    }


    private static List<SimpleHistogramResponse> responseBuild(SearchResponse response , SimpleHistogram histogram){
        Aggregations aggregations = response.getAggregations();
        InternalHistogram terms = (InternalHistogram)aggregations.asMap().get(histogram.getField());
        List<InternalHistogram.Bucket> buckets = terms.getBuckets();
        List<SimpleHistogramResponse> returnList = new ArrayList<>();
        for (InternalHistogram.Bucket b :  buckets) {
            SimpleHistogramResponse resp = new SimpleHistogramResponse();
            double begin = (double)b.getKey();
            double end = begin + histogram.getInterval();
            String fieldStr = begin + "~" + end;
            long docCount = b.getDocCount();
            resp.setField(fieldStr);
            resp.setDocCount(docCount);

            if(histogram.getTwoField() != null) { // 两个维度带指标搜索
                Aggregations aggr = b.getAggregations();
                InternalNumericMetricsAggregation.SingleValue singleValue = (InternalNumericMetricsAggregation.SingleValue) aggr.get(histogram.getTwoField() + histogram.getMetrics().getMsg());
                double value = singleValue.value();
                if(Double.isNaN(value)){
                    resp.setMetrics(0);
                }else {
                    resp.setMetrics(value);
                }
            }
            returnList.add(resp);
        }
        return returnList;
    }

    private static AggregationBuilder buildMetrics(SimpleHistogram histogram){
        String twoField = histogram.getTwoField();
        MetricsEnums metrics = histogram.getMetrics();
        if(metrics == null) {
            return AggregationBuilders.terms(twoField).field(twoField);
        }
        AggregationBuilder builder = null;
        switch(metrics) {
            case MAX:
                builder = AggregationBuilders.max(twoField+MetricsEnums.MAX.getMsg()).field(twoField);
                break;
            case MIN :
                builder = AggregationBuilders.min(twoField+MetricsEnums.MIN.getMsg()).field(twoField);
                break;
            case AVG :
                builder = AggregationBuilders.avg(twoField+MetricsEnums.AVG.getMsg()).field(twoField);
                break;
            case SUM :
                builder = AggregationBuilders.sum(twoField+MetricsEnums.SUM.getMsg()).field(twoField);
                break;
        }
        return builder;
    }







}
