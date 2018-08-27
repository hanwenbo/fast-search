package com.github.search;

import com.github.search.commons.ESConnect;
import com.github.search.index.manage.AnalyzeSearch;
import org.elasticsearch.client.transport.TransportClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月28日
 * @modifytime:
 */
public class AnalyzeSearchTest {

    public static void main(String[] args) throws Exception {
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
        AnalyzeSearch.analyze(client,"goods_index","chinese_analyzer","我是中国人");
    }
}
