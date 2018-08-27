package com.github.search.pub.str2page;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月25日
 * @modifytime:
 */
public class StrContent {

    private Map<String,Position> keyMatcher;

    private String originalStr ;

    private String convertStr ;

    public Map<String, Position> getKeyMatcher() {
        return keyMatcher;
    }

    public void setKeyMatcher(Map<String, Position> keyMatcher) {
        this.keyMatcher = keyMatcher;
    }

    public String getOriginalStr() {
        return originalStr;
    }

    public void setOriginalStr(String originalStr) {
        this.originalStr = originalStr;
    }

    public String getConvertStr() {
        return convertStr;
    }

    public void setConvertStr(String convertStr) {
        this.convertStr = convertStr;
    }

    @Override
    public String toString() {
        return "StrContent{" +
                "keyMatcher=" + keyMatcher +
                ", originalStr='" + originalStr + '\'' +
                ", convertStr='" + convertStr + '\'' +
                '}';
    }
}
