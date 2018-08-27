package com.github.search.index;


import com.github.search.commons.ESConnect;
import com.github.search.index.manage.RefreshOperation;
import com.github.search.utils.JsonUtils;
import com.github.search.utils.UniqueIDUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 索引维护类
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:06
 */
public class BaseInsert {

    /**
     * 添加单个索引
     * @param client ES客户端链接
     * @param index 索引名称
     * @param type  类型名称
     * @param id    id
     * @param obj   实现了Serializable接口的实体类
     * @return  indexId 返回索引ID
     * @throws Exception
     */
    public static String basicInsert(TransportClient client, String index, String type , Long id , Object obj) throws Exception{
        try{
            String jsonStr = JsonUtils.obj2Str(obj);
            if(id == null && (id = ((Long)JsonUtils.getValue(jsonStr,"id")).longValue()) == null){
                id = UniqueIDUtils.getUniqueID();
            }
            //保证json中id 与 传入ID保持一致
            Map<String,String> map = new HashMap<String,String>();
            map.put("id",id.toString());
            JsonElement parse = new JsonParser().parse(jsonStr);
            jsonStr = JsonUtils.replaceOrAddValue(parse, map).toString();
            IndexResponse response = client.prepareIndex(index,type,id.toString())
                    .setSource(jsonStr, XContentType.JSON).get();
            return response.getId();
        }finally {
            ESConnect.closeClient(client);
        }
    }

    public static String basicInsert(TransportClient client, String index, String type , Long id , String jsonStr) throws Exception{
        try{
            if(id == null && (id = ((Long)JsonUtils.getValue(jsonStr,"id")).longValue()) == null){
                id = UniqueIDUtils.getUniqueID();
            }
            //保证json中id 与 传入ID保持一致
            Map<String,String> map = new HashMap<String,String>();
            map.put("id",id.toString());
            JsonElement parse = new JsonParser().parse(jsonStr);
            jsonStr = JsonUtils.replaceOrAddValue(parse, map).toString();
            IndexResponse response = client.prepareIndex(index,type,id.toString())
                    .setSource(jsonStr, XContentType.JSON).get();
            return response.getId();
        }finally {
            ESConnect.closeClient(client);
        }
    }



    /**
     * 批量添加多个索引信息
     * @param client
     * @param index
     * @param type
     * @param objs
     * @throws Exception
     */
    public static void batchAdd(TransportClient client,String index,String type, List<Object> objs) throws Exception{
        try{
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            for (Object obj : objs) {
                String id = null;
                String json = JsonUtils.obj2Str(obj);
                id = (String) JsonUtils.getValue(json,"id");
                if(StringUtils.isBlank(id)){
                    id  = new Long(UniqueIDUtils.getUniqueID()).toString();
                }
                IndexRequest request = client.prepareIndex(index,type,id).setSource(json,XContentType.JSON).request();
                bulkRequest.add(request);
            }
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if(bulkResponse.hasFailures()){
                //TODO
            }
            RefreshOperation.refresh(client,index);
        }finally {
            ESConnect.closeClient(client);
        }
    }



}
