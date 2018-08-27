package com.github.search;

import com.github.search.commons.ESConnect;
import com.github.search.index.manage.ClusterHealth;
import com.github.search.pub.settings.ClusterEntityInfo;
import com.github.search.pub.settings.ClusterStatusEntity;
import com.github.search.pub.settings.IndexDocInfo;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月21日
 * @modifytime:
 */
public class ClusterHealthTest {

    @Test
    public void clusterHealth() throws Exception{
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
        ClusterStatusEntity entity = ClusterHealth.clusterHealth(client);
        System.out.println(entity);

    }

    @Test
    public void clusterStatus() throws Exception{
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
        ClusterEntityInfo clusterEntityInfo = ClusterHealth.clusterStatus(client);
        System.out.println(clusterEntityInfo);

    }

    @Test
    public void searchIndices() throws Exception{
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
        List<IndexDocInfo> indexDocInfos = ClusterHealth.searchIndices(client);
        System.out.println(indexDocInfos);

    }



}
