package com.github.search;

import com.github.search.commons.ESConnect;
import com.github.search.index.manage.SettingSearch;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月21日
 * @modifytime:
 */
public class SettingSearchTest {

    @Test
    public void test1() throws Exception {
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
        TransportClient client = connect.getTransportClient();
        Map<String, Object> goods_index = SettingSearch.indexSetting(client, "goods_index");
        System.out.println(goods_index);

        // {index={refresh_interval=1s, number_of_shards=2, provided_name=goods_index, creation_date=1524736114379,
        // analysis={filter={pinyin_first_letter_and_full_pinyin_filter={keep_joined_full_pinyin=true, keep_none_chinese_in_first_letter=true, lowercase=true, keep_original=false, keep_first_letter=true, trim_whitespace=true, type=pinyin, keep_none_chinese=false, limit_first_letter_length=16, keep_full_pinyin=false},
        // autocomplete_filter={type=edge_ngram, min_gram=1, max_gram=15},
        // full_pinyin_filter={keep_joined_full_pinyin=true, keep_none_chinese_in_first_letter=true, lowercase=true, keep_original=true, keep_first_letter=true, trim_whitespace=true, type=pinyin, keep_none_chinese=false, limit_first_letter_length=16, keep_full_pinyin=false}},
        // analyzer={chinese_analyzer={filter=[lowercase, autocomplete_filter], char_filter=[html_strip], type=custom, tokenizer=keyword}, full_prefix_analyzer={filter=[lowercase, full_pinyin_filter, autocomplete_filter],
        // char_filter=[html_strip], type=custom, tokenizer=keyword},
        // pinyin_analyzer={filter=[pinyin_first_letter_and_full_pinyin_filter, autocomplete_filter], char_filter=[html_strip], type=custom, tokenizer=keyword}}},
        // number_of_replicas=1, uuid=EsmSA6TkT_i39tlpm6izxA, version={created=5050099}}}

    }


    @Test
    public void test2() throws  Exception {
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
        TransportClient client = connect.getTransportClient();
        //SettingSearch.clusterSetting(client);
    }
}
