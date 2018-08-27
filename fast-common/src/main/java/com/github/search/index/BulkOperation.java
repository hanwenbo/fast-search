package com.github.search.index;

import com.github.search.commons.IndexConfig;
import com.github.search.enums.OpEnum;
import com.github.search.pub.BulkEntity;
import com.github.search.pub.BulkUpdate;
import com.github.search.utils.JsonUtils;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description: 批量处理索引数据封装的对象
 * User: zhubo
 * Date: 2017-09-22
 * Time: 11:31
 */
public class BulkOperation {

    /**
     * 批量添加索引数据
     * @param client
     * @param index
     * @param type
     * @param objs
     * @return
     * 返回没有操作成功的数据ID
     */
    public static List<String> batchObjInsert(TransportClient client, String index, String type , List<Object> objs) {

        List<String> list = new ArrayList<String>();
        try{
            BulkProcessor bulkProcessor = getProcessor(client,list);
            objs.forEach(item ->{
                try{
                    String jsonStr = JsonUtils.obj2Str(item);
                    String id = ((Long)JsonUtils.getValue(jsonStr,"id")).toString();
                    bulkProcessor.add(new IndexRequest(index,type,id).source(jsonStr, XContentType.JSON));
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            bulkProcessor.flush();
            bulkProcessor.awaitClose(36, TimeUnit.SECONDS);//接口调用超时时间
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 批量添加索引数据
     * @param client
     * @param index
     * @param type
     * @param jsonStrs
     * @return
     * 返回没有操作成功的数据ID
     */
    public static List<String>  batchStringInsert(TransportClient client, String index, String type , List<String> jsonStrs ) {
        List<String> list = new ArrayList<String>();
        try{
            BulkProcessor bulkProcessor = getProcessor(client,list);
            jsonStrs.forEach(item ->{
                try{
                    String jsonStr = item;
                    String id = ((Long)JsonUtils.getValue(jsonStr,"id")).toString();
                    bulkProcessor.add(new IndexRequest(index,type,id).source(jsonStr, XContentType.JSON));
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            bulkProcessor.flush();
            bulkProcessor.awaitClose(new Integer(IndexConfig.bulkTimeout), TimeUnit.SECONDS);//接口调用超时时间
            System.out.println(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 批量删除索引数据
     * @param client
     * @param index
     * @param type
     * @param objs
     * @return
     * 返回没有操作成功的数据ID
     */
    public static List<String> batchDeleteByStringId(TransportClient client, String index, String type , List<String> objs) {
        List<String> list = new ArrayList<String>();
        try{
            BulkProcessor bulkProcessor = getProcessor(client,list);
            objs.forEach(item ->{
                try{
                    bulkProcessor.add(new DeleteRequest(index,type,item));
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            bulkProcessor.flush();
            bulkProcessor.awaitClose(new Integer(IndexConfig.bulkTimeout), TimeUnit.SECONDS);//接口调用超时时间
            System.out.println(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 批量删除索引数据
     * @param client
     * @param index
     * @param type
     * @param objs
     * @return
     */
    public static List<String>  batchDeleteByLongId(TransportClient client, String index, String type , List<Long> objs){
        List<String> lists = new ArrayList<String>();
        objs.forEach(item->{
            lists.add(item.toString());
        });
        return batchDeleteByStringId(client,index,type,lists);
    }


    /**
     * 批量执行索引数据的混合操作 add / delete
     * @param client
     * @param index
     * @param type
     * @param objs
     * @return
     *
     */
    public static  List<String> batchOpByEntity(TransportClient client, String index, String type , List<BulkEntity> objs){
        List<String> list = new ArrayList<String>();
        try{
            BulkProcessor bulkProcessor = getProcessor(client,list);
            objs.forEach(item ->{
                try{
                    if(item.getOp().equals(OpEnum.ADD.getOp())){
                        String id = null;
                        String jsonStr = JsonUtils.obj2Str(item.getObj());
                        if(item.getId() == null){
                            id = ((Long)JsonUtils.getValue(jsonStr,"id")).toString();
                        }else{
                            id = item.getId().toString();
                        }
                        bulkProcessor.add(new IndexRequest(index,type,id).source(jsonStr,XContentType.JSON));
                    }else if(item.getOp().equals(OpEnum.DELETE.getOp())){
                        bulkProcessor.add(new DeleteRequest(index,type,item.getId().toString()));
                    }else if(item.getOp().equals(OpEnum.UPDATE.getOp())){
                        //bulkProcessor.add()
                        /*UpdateByQueryRequestBuilder ubqrb = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
                        Script script = new Script(item.getScriptStr());
                        ubqrb.source(index).script(script).filter(RequestBuilder.)*/
                        //TODO 更新索引信息

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            bulkProcessor.flush();
            bulkProcessor.awaitClose(new Integer(IndexConfig.bulkTimeout), TimeUnit.SECONDS);//接口调用超时时间
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }



    public static List<String> batchUpdate(TransportClient client, String index, String type , List<BulkUpdate> objs){
        List<String> failure = new ArrayList<String>();
        try{
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            for(int i = 0 ; i< objs.size();i++){
                BulkUpdate update = objs.get(i);
                UpdateRequest updateRequest = new UpdateRequest()
                        .index(index)
                        .type(type)
                        .id(update.getId())
                        .doc(update.getJsonStr(),XContentType.JSON);
                bulkRequest.add(updateRequest);
            }
            BulkResponse response = bulkRequest.execute().actionGet();

            if(response.hasFailures()){
                BulkItemResponse[] items = response.getItems();
                for(int j = 0 ; j< items.length ; j++){
                    if(items[j].getFailure() != null){
                        failure.add(items[j].getId());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return failure;
    }


    /**
     * 获取批量操作执行程序
     * @param client
     * @return
     */
    private static BulkProcessor getProcessor(TransportClient client , List<String> list){
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            //调用此方法之前执行。例如你可以看到numberOfActions request.numberOfActions()
            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {

            }
            //批量执行后调用此方法。例如你可以检查是否有一些失败的请求 response.hasFailures()
            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                System.out.println(bulkResponse);
                if(bulkResponse.hasFailures()){
                    bulkResponse.forEach(item -> {
                        if(item.getFailure() != null){
                            //错误记录
                            //System.out.println(item.getId());
                            list.add(item.getId());
                        }
                    });
                }
            }
            //调用此方法时,大部分失败了 Throwable
            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {

            }
        }).setBulkActions(new Integer(IndexConfig.blukActions))//我们想执行批量每1000个请求
                .setBulkSize(new ByteSizeValue(new Integer(IndexConfig.blukSize),ByteSizeUnit.MB))//我们想要刷新每10MB
                .setFlushInterval(TimeValue.timeValueSeconds(new Integer(IndexConfig.blukFlushTime)))//我们想要冲洗大部分每5秒钟任何请求的数量
                .setConcurrentRequests(new Integer(IndexConfig.blukCncurrent))//设置并发请求的数量。的值为0意味着只有一个单一的请求将被允许执行。值为1时表示1并发请求允许同时积累新的批量执行请求。
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100),3))
                .build();
        return bulkProcessor;
    }
}
