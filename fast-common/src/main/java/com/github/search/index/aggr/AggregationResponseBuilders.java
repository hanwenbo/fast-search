package com.github.search.index.aggr;


import com.github.search.pub.aggr.AggrSearchEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月04日
 * @modifytime:
 */
public class AggregationResponseBuilders {

    public static AggregationResponseBuilder prepareResponse(AggrSearchEntity entity){
        AggregationResponseBuilder aggregationResponse = new AggregationResponseBuilder().setEntity(entity);
        return aggregationResponse;
    }



}
