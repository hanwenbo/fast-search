package com.github.search.commons;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-08-29
 * Time: 20:01
 */
public class IndexConfig {


    /**
     * ES 执行批量操作的对象个数
     */
    public static final String blukActions = ESConfig.getValue("bluk.actions");

    /**
     * ES 执行批量操作一次的对象大小
     */
    public static final String blukSize = ESConfig.getValue("bluk.size");

    /**
     * ES es缓存刷新时间
     */
    public static final String blukFlushTime = ESConfig.getValue("bluk.flush.time");

    /**
     * ES 批量操作并发个数
     */
    public static final String blukCncurrent = ESConfig.getValue("bluk.concurrent");

    /**
     * ES 批量操作是的超时时间
     */
    public static final String bulkTimeout = ESConfig.getValue("bulk.timeout");






}
