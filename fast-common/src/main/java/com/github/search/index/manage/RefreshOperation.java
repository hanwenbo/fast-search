package com.github.search.index.manage;

import org.elasticsearch.client.transport.TransportClient;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 索引刷新
 * @time: 2018年06月21日
 * @modifytime:
 */
public class RefreshOperation {

    /**
     * 刷新单个索引
     * @param client
     * @param index
     */
    public static void refresh (TransportClient client, String index) {
        try{
            client.admin().indices().prepareRefresh(index).get();
        }finally {
            // client.close();
        }
    }

    /**
     * 刷新所有索引信息
     * @param client
     */
    public static void refreshAll(TransportClient client){
        try{
            client.admin().indices().prepareRefresh().get();
        }finally {
            // client.close();
        }
    }

    /**
     * 刷新部分的索引
     * @param client
     * @param str
     */
    public static void refresh(TransportClient client,String... str ){
        try{
            client.admin().indices().prepareRefresh(str);
        }finally {
            // client.close();
        }
    }

}
