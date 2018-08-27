package com.github.search.commons;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 初始属性封装
 * @time: 2018年05月26日
 * @modifytime:
 */
public class BasicProperties {

    protected String clusterName;

    protected String pingTimeout;

    protected boolean ignoreClusterName;

    protected boolean clusterSniff;

    protected List<String> address;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(String pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public boolean isIgnoreClusterName() {
        return ignoreClusterName;
    }

    public void setIgnoreClusterName(boolean ignoreClusterName) {
        this.ignoreClusterName = ignoreClusterName;
    }

    public boolean isClusterSniff() {
        return clusterSniff;
    }

    public void setClusterSniff(boolean clusterSniff) {
        this.clusterSniff = clusterSniff;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

}
