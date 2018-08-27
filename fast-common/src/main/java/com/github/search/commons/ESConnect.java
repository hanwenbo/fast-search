package com.github.search.commons;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: ES服务器链接工具类
 * @time: 2017年08月29日
 * @modifytime:
 * 2018年05月26日 Schema 化改造，接口化改造
 */
public class ESConnect extends BasicProperties implements InitializingBean ,DisposableBean{

    Logger logger = LoggerFactory.getLogger(ESConnect.class);

    private TransportClient transportClient;

    public ESConnect(){

    }
    /**
     * client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，这样做的好处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器
     * @throws Exception
     */
    private void initTransportClient() throws Exception{
        try{
            Settings settings = Settings.builder()
                    .put("cluster.name", clusterName)
                    .put("client.transport.ignore_cluster_name", ignoreClusterName)
                    .put("client.transport.ping_timeout", pingTimeout)
                    .put("client.transport.sniff", clusterSniff)
                    .build();
            transportClient = new PreBuiltTransportClient(settings);
            for (String addr:address) {
                if(StringUtils.isNotBlank(addr)){
                    String[] strs = addr.split(":");
                    transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(strs[0]), Integer.parseInt(strs[1])));
                }
            }
            logger.info("成功初始化数据源信息!! , address:[ {} ].",getAddress());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("初始化ES数据源 address:[ {} ]失败!",getAddress());
        }
    }

    /**
     * 双检保证单例
     * @return
     * @throws Exception
     */
    public TransportClient getTransportClient() throws Exception{
        if(transportClient == null){
            synchronized (this){
                if(transportClient == null){
                    initTransportClient();
                }
            }
        }
        return transportClient;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.initTransportClient();
    }

    @Override
    public void destroy() throws Exception {
        transportClient.close();
    }

    public static void closeClient(TransportClient client){
        if(client != null){
            //TODO 因为单例模式 所以无需关闭
            //client.close();
        }
    }

}
