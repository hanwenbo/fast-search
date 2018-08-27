package com.github.search;

import com.github.search.commons.ESConnect;
import com.github.search.index.manage.ClusterIndexStatus;
import com.github.search.pub.settings.IndexAnalyzeInfo;
import com.github.search.pub.settings.IndexTypeEntity;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月29日
 * @modifytime:
 */
public class ClusterIndexStatusTest {

    @Test
    public void indexAnalyzeGet() throws Exception{
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
        List<IndexAnalyzeInfo> indexAnalyzeInfos = ClusterIndexStatus.indexAnalyzeGet(client);
        System.out.println(indexAnalyzeInfos);

    }

    @Test
    public void indexTypeGet() throws Exception{
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
        List<IndexTypeEntity> indexTypeEntities = ClusterIndexStatus.indexTypeGet(client);
        System.out.println(indexTypeEntities);

    }
}
