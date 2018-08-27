package com.github.search.page;

import com.github.search.pub.ValuePackage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Bool查询 Basic Search
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:29
 */
public class BoolPager extends LucenePager{

    /**
     * must规则
     */
    private List<ValuePackage> query;


    public List<ValuePackage> getQuery() {
        return query;
    }

    public void setQuery(List<ValuePackage> query) {
        this.query = query;
    }


    @Override
    public String toString() {
        return "BoolPager{" +
                "query=" + query +
                '}';
    }
}
