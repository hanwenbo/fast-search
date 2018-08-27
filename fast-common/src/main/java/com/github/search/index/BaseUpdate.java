package com.github.search.index;

import com.github.search.commons.ESConnect;
import com.github.search.commons.ESUtils;
import com.github.search.commons.SpringUtils;
import com.github.search.page.BoolPager;
import com.github.search.utils.JsonUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.script.Script;

/**
 * Created with IntelliJ IDEA.
 * Description: 更新索引信息
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:07
 */
public class BaseUpdate {

    /**
     *
     * @param client
     * @param _index
     * @param _type
     * @param _id
     * @param jsonStr json doc 字符串  {"labelIds":[1,2,3,4,5,6],"readNum":33}
     * @throws Exception
     */
    public static void updateById(TransportClient client, String _index , String _type, String _id , String jsonStr) throws Exception{

        UpdateRequest request = new UpdateRequest();
        request.index(_index);
        request.type(_type);
        request.id(_id);
        jsonStr = JsonUtils.jsonSkipField(jsonStr,new String[]{"id"});  //过滤update json字符串 中的id
        request.doc(jsonStr, XContentType.JSON);
        UpdateResponse updateResponse = client.update(request).get();
    }

    /**
     *
     * @param client
     * @param _index 索引名称
     * @param script 执行脚本字符串
     * @param pager
     */
    public static void updateByQuery(TransportClient client, String _index ,  String script , BoolPager pager){
        BoolQueryBuilder boolQueryBuilder = ESUtils.getBoolQueryBuilder(pager);
        UpdateByQueryAction.INSTANCE.newRequestBuilder(client).source(_index).script(new Script(script)).filter(boolQueryBuilder)
                .execute().actionGet();
    }

    public static int updateByQuery(TransportClient client, String _index ,  Script script , BoolPager pager){
        BoolQueryBuilder boolQueryBuilder = ESUtils.getBoolQueryBuilder(pager);
        BulkByScrollResponse response = UpdateByQueryAction.INSTANCE.newRequestBuilder(client).source(_index).script(script).filter(boolQueryBuilder)
                .execute().actionGet();
        return response.getBatches();
    }

    /**
     * @param client
     * @param _index 索引名称
     * @param script 执行脚本字符串
     * @param _type
     */
    public static void updateByType(TransportClient client, String _index ,  String script , String _type){
        QueryBuilder builder = QueryBuilders.typeQuery(_type);
        UpdateByQueryAction.INSTANCE.newRequestBuilder(client).source(_index).script(new Script(script)).filter(builder)
                .execute().actionGet();
    }

    public static void main(String[] args) throws Exception {
        /*TransportClient client = esConnect.getTransportClient();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id","11111");
        map.put("labelIds",new Integer[]{1,2,3,4,5,6});
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        System.out.println(jsonStr);
        updateById(client, IndexConfig.articleIndex,IndexConfig.articleType,"AV4w340l1ZcaBBc7InDz",jsonStr);*/
        TransportClient client = ((ESConnect) SpringUtils.getBean("esConnect")).getTransportClient();
        //UpdateByQueryAction.INSTANCE.newRequestBuilder(client).source("").
        //updateByQuery(client,IndexConfig.articleIndex,,new BoolPager());

    }
}
