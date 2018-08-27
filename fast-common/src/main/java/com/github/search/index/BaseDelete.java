package com.github.search.index;


import com.github.search.commons.ESUtils;
import com.github.search.page.BoolPager;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

/**
 * Created with IntelliJ IDEA.
 * Description: 索引删除工具类
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:41
 */
public class BaseDelete {
    /**
     * 根据索引ID删除
     * @param client
     * @param _id
     */
    public static String deleteById(TransportClient client, String _index, String _type, String _id){
        DeleteResponse response = client.prepareDelete(_index,_type,_id).execute().actionGet();
        return response.getId();
    }

    /**
     * 根据查询删除索引信息
     * @param client
     * @param _index
     * @param pager
     */
    public static long deleteByQuery(TransportClient client, String _index, BoolPager pager){
        BoolQueryBuilder boolQueryBuilder = ESUtils.getBoolQueryBuilder(pager);
        BulkByScrollResponse bulkByScrollResponse = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).source(_index).filter(boolQueryBuilder).execute().actionGet();
        return bulkByScrollResponse.getDeleted();
    }

    /**
     * 根据类型删除索引doc信息
     * @param client
     * @param _index
     * @param _type
     */
    public static long deleteByType(TransportClient client, String _index, String _type){
        QueryBuilder builder = QueryBuilders.typeQuery(_type);
        BulkByScrollResponse bulkByScrollResponse = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).source(_index).filter(builder).execute().actionGet();
        return bulkByScrollResponse.getDeleted();
    }

    public void test(){
        String abc = "{\"query\":{\"term\":{\"make\":{\"value\":\"{{brand}}\"}}},\"size\":0,\"aggs\":{\"makes\":{\"terms\":{\"field\":\"make\"},\"aggs\":{\"avg_val\":{\"avg\":{\"field\":\"price\"}},\"max_val\":{\"max\":{\"field\":\"price\"}},\"min_val\":{\"min\":{\"field\":\"price\"}}}}}}";

        String bcd = "{\"size\":0,\"aggs\":{\"makes\":{\"terms\":{\"field\":\"make\"},\"aggs\":{\"avg_val\":{\"avg\":{\"field\":\"price\"}},\"max_val\":{\"max\":{\"field\":\"price\"}},\"min_val\":{\"min\":{\"field\":\"price\"}}}}}}";


        String dd = "\"query\":{\"term\":{\"make\":{\"value\":\"{{brand}}\"}}},";


        String str = "{{{#brand}}\"query\":{\"term\":{\"make\":{\"value\":\"{{brand}}\"}}}, {{/brand}}\"size\":0,\"aggs\":{\"makes\":{\"terms\":{\"field\":\"make\"},\"aggs\":{\"avg_val\":{\"avg\":{\"field\":\"price\"}}{{#max_val}},\"max_val\":{\"max\":{\"field\":\"price\"}}{{/max_val}}{{#min_val}},\"min_val\":{\"min\":{\"field\":\"price\"}}{{/min_val}}}}}}";


        String abs = "{\"size\":0,{{#brand}}\"query\":{\"term\":{\"brand\":{\"value\":\"{{brand}}\"}}}{{/brand}},\"aggs\":{\"makes\":{\"terms\":{\"size\":5,\"field\":\"brand\"},\"aggs\":{\"states\":{\"terms\":{\"field\":\"state\"},\"aggs\":{\"avg_price\":{\"avg\":{\"field\":\"price\"}},\"sum_price\":{\"sum\":{\"field\":\"price\"}}}},\"sexs\":{\"terms\":{\"field\":\"sex\"}}}},\"price\":{\"histogram\":{\"field\":\"price\",\"interval\":200000}}}}";


        String ddd = "{\"size\":0,{{#brand}}\"query\":{\"terms\":{\"brand\":[\"{{#brand}}\",\"{{.}}\",\"{{/brand}}\"]}},{{/brand}}\"aggs\":{\"makes\":{\"terms\":{\"size\":5,\"field\":\"brand\"},\"aggs\":{\"states\":{\"terms\":{\"field\":\"state\"},\"aggs\":{\"avg_price\":{\"avg\":{\"field\":\"price\"}},\"sum_price\":{\"sum\":{\"field\":\"price\"}}}},\"sexs\":{\"terms\":{\"field\":\"sex\"}}}},\"price\":{\"histogram\":{\"field\":\"price\",\"interval\":200000}}}}";

        String size = "{\"size\":0,{{#brand}}\"query\":{\"terms\":{\"brand\":[\"{{#brand}}\",\"{{.}}\",\"{{/brand}}\"]}},{{/brand}}\"aggs\":{\"makes\":{\"terms\":{\"size\":{{size}}{{^size}}5{{/size}},\"field\":\"brand\"},\"aggs\":{\"states\":{\"terms\":{\"field\":\"state\"},\"aggs\":{\"avg_price\":{\"avg\":{\"field\":\"price\"}},\"sum_price\":{\"sum\":{\"field\":\"price\"}}}},\"sexs\":{\"terms\":{\"field\":\"sex\"}}}},\"price\":{\"histogram\":{\"field\":\"price\",\"interval\":200000}}}}";

        String from = "{\"size\":0,{{#brand}}\"query\":{\"terms\":{\"brand\":[\"{{#brand}}\",\"{{.}}\",\"{{/brand}}\"]}},{{/brand}}\"aggs\":{\"makes\":{\"terms\":{\"from\":{{from}}{{^from}}0{{/from}},\"size\":{{size}}{{^size}}5{{/size}},\"field\":\"brand\"},\"aggs\":{\"states\":{\"terms\":{\"field\":\"state\"},\"aggs\":{\"avg_price\":{\"avg\":{\"field\":\"price\"}},\"sum_price\":{\"sum\":{\"field\":\"price\"}}}},\"sexs\":{\"terms\":{\"field\":\"sex\"}}}},\"price\":{\"histogram\":{\"field\":\"price\",\"interval\":200000}}}}";
    }

}
