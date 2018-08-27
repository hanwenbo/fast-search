package com.github.search.page;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 基本索引查询 Basic Search
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:25
 */
public class LucenePager extends Pager implements Serializable{

    /**
     * 查询索引
     */
    private String _index;

    /**
     * 查询索引类型
     */
    private String _type;

    /**
     * query字段
     */
    private String queryField;

    /**
     * query值
     */
    private String queryValue;

    /**
     * filter字段
     */
    private String filterField;

    /**
     * filter值
     */
    private String filterValue;


    /**
     * 查询字段
     */
    private String[] fields;

    /**
     * 排序字段及排序规则
     */
    private Map<String,Integer> sortFields;

    /**
     * 设置高亮字段
     */
    private String[]  highFields;

    /**
     * 设置高亮标签前缀
     */
    private String preTag;

    /**
     * 设置高亮标签后缀
     */
    private String postTag;


    /**
     * 集合字符串
     */
    private List<String> result;

    /**
     * id的list集合
     */
    private List<Long> ids;


    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String getQueryField() {
        return queryField;
    }

    public void setQueryField(String queryField) {
        this.queryField = queryField;
    }

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public Map<String, Integer> getSortFields() {
        return sortFields;
    }

    public void setSortFields(Map<String, Integer> sortFields) {
        this.sortFields = sortFields;
    }

    public String[] getHighFields() {
        return highFields;
    }

    public void setHighFields(String[] highFields) {
        this.highFields = highFields;
    }

    public String getPreTag() {
        return preTag;
    }

    public void setPreTag(String preTag) {
        this.preTag = preTag;
    }

    public String getPostTag() {
        return postTag;
    }

    public void setPostTag(String postTag) {
        this.postTag = postTag;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "LucenePager{" +
                "_index='" + _index + '\'' +
                ", _type='" + _type + '\'' +
                ", queryField='" + queryField + '\'' +
                ", queryValue='" + queryValue + '\'' +
                ", filterField='" + filterField + '\'' +
                ", filterValue='" + filterValue + '\'' +
                ", fields=" + Arrays.toString(fields) +
                ", sortFields=" + sortFields +
                ", highFields=" + Arrays.toString(highFields) +
                ", preTag='" + preTag + '\'' +
                ", postTag='" + postTag + '\'' +
                ", result=" + result +
                ", ids=" + ids +
                '}';
    }
}
