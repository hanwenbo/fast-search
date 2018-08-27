package com.github.search;


import com.github.search.commons.ESConnect;
import com.github.search.index.aggr.AggregationSearcher;
import com.github.search.pub.aggr.AggrIndexEntity;
import com.github.search.pub.aggr.AggrResponse;
import com.github.search.pub.aggr.AggrSearchEntity;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月12日
 * @modifytime:
 */
public class AggregationSearchTest {

    @Test
    public void test1() throws Exception{

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

        AggrIndexEntity entity = new AggrIndexEntity();
        entity.setIndex("goods_index");
        entity.setType("goods_type");
        AggrSearchEntity e = new AggrSearchEntity.Builder("brandName").build();
        entity.setAggrSearchEntity(e);
        AggrResponse response = AggregationSearcher.aggrSearch(client, entity);
        System.out.println(response);


    }

}
