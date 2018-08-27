package com.github.search;

import com.github.search.commons.ESConnect;
import com.github.search.index.aggr.AggregationSearcher;
import com.github.search.index.aggr.histogram.SimpleHistogramSearcher;
import com.github.search.pub.aggr.AggrIndexEntity;
import com.github.search.pub.aggr.AggrResponse;
import com.github.search.pub.aggr.AggrSearchEntity;
import com.github.search.pub.aggr.histogram.SimpleHistogram;
import com.github.search.pub.aggr.histogram.SimpleHistogramResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月03日
 * @modifytime:
 */
public class AggrsTest {

    @Test
    public void test1()  throws Exception{
        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.11.117:9300");
        address.add("172.16.11.117:9301");
        address.add("172.16.11.117:9302");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();

        SearchRequestBuilder builder = client.prepareSearch("goods_index").setTypes("goods_type");


        TermsAggregationBuilder supplierName = AggregationBuilders.terms("supplierName").field("supplierName");
        TermsAggregationBuilder brandName = AggregationBuilders.terms("brandName").field("brandName");

        supplierName.subAggregation(brandName);
        builder.addAggregation(supplierName);
        SearchResponse response = builder.execute().actionGet();
        Aggregations aggregations = response.getAggregations();
        Map<String, Aggregation> aggMap  = aggregations.asMap();

        StringTerms teamAgg= (StringTerms) aggMap.get("supplierName");
        Iterator<StringTerms.Bucket> iterator = teamAgg.getBuckets().iterator();
        while(iterator.hasNext()) {
            StringTerms.Bucket next = iterator.next();
            Object key = next.getKey();
            long docCount = next.getDocCount();
            System.out.println(key + " : " + docCount);
        }
    }


    @Test
    public void test2() throws Exception {
        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.11.119:9300");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();
        AggrIndexEntity entity =  new AggrIndexEntity();
        entity.setIndex("car");
        entity.setType("sales");
        /*AggrSearchEntity search = new AggrSearchEntity();
        search.setField("supplierName");
        search.setMetrics(Arrays.asList(new AggrMetricsEntity[]{new AggrMetricsEntity("goodsStorage", MetricsEnums.MAX),new AggrMetricsEntity("goodsStorage", MetricsEnums.MIN)}));
*/
        /*AggrSearchEntity search1 = new AggrSearchEntity();
        search1.setField("brandName");
        search1.setMetrics(Arrays.asList(new AggrMetricsEntity[]{new AggrMetricsEntity("marketPrice", MetricsEnums.MAX)));
        search.setAggrSearchEntity(search1);*/

        /*AggrSearchEntity search1 = new AggrSearchEntity.Builder("brandName")
                .addMetrics(new AggrMetricsEntity("marketPrice", MetricsEnums.MAX))
                .addMetrics(new AggrMetricsEntity("marketPrice", MetricsEnums.MIN))
                .build();

        AggrSearchEntity search = new AggrSearchEntity.Builder("supplierName")
                .addMetrics(new AggrMetricsEntity("marketPrice", MetricsEnums.MAX))
                .addMetrics(new AggrMetricsEntity("marketPrice", MetricsEnums.MIN))
                .setChildBucket(search1)
                .build();*/

        AggrSearchEntity search1 = new AggrSearchEntity.Builder("age")
                .build();

        AggrSearchEntity search = new AggrSearchEntity.Builder("brand")
                .setChildBucket(search1)
                .build();
        search.setAggrSearchEntity(search1);
        entity.setAggrSearchEntity(search);

        AggrResponse response = AggregationSearcher.aggrSearch(client, entity);
        System.out.println(response);
    }





    @Test
    public void test3() throws Exception {
        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.11.119:9300");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();

        HistogramAggregationBuilder builder = AggregationBuilders.histogram("age").field("age").interval(5).subAggregation(AggregationBuilders.avg("revenue").field("salary"));
        SearchResponse response = client.prepareSearch("car")
                .setTypes("sales")
                .addAggregation(builder)
                .setSize(0)
                .get();

        System.out.println(response);
    }

    @Test
    public void test4() throws Exception {
        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.11.119:9300");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();

        /*HistogramAggregationBuilder builder = AggregationBuilders.histogram("age").field("age").interval(5).subAggregation(AggregationBuilders.avg("revenue").field("salary"));
        SearchResponse response = client.prepareSearch("car")
                .setTypes("sales")
                .addAggregation(builder)
                .setSize(0)
                .get();

        System.out.println(response);*/

        SimpleHistogram histogram = new SimpleHistogram.Builder("car","sales")
                .fieldOne("price",20000)

                .build();
        List<SimpleHistogramResponse> simpleHistogramResponses =    SimpleHistogramSearcher.simpleSearch(client, histogram);
        System.out.println(simpleHistogramResponses);

    }

}
