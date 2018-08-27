package com.github.search.pub.settings;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 用于展示 Plugins 插件前台展示
 * @time: 2018年06月28日
 * @modifytime:
 */
public class PluginsInfo {

    private String name;

    private String desc;

    private String className;

    private String version;

    public PluginsInfo(String name, String desc, String className, String version) {
        this.name = name;
        this.desc = desc;
        this.className = className;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AnalyzerInfo{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
