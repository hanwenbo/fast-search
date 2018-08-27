package com.github.search;

import com.github.search.commons.ESConnect;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

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
public class DateAggregationSearchTest {

    @Test
    public void test2() throws Exception {

        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.6.87:9300");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();

        /*SimpleHistogram simpleHistogram = new SimpleHistogram.Builder("","").fieldOne("",).build();
        entity.setAggrSearchEntity(e);
        AggrResponse response = DateHistogramSearcher.dateSearch(client,)
        System.out.println(response);*/


    }
}
