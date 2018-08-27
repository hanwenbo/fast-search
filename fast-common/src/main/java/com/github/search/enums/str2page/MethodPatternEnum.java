package com.github.search.enums.str2page;

import com.github.search.pub.SearchType;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月25日
 * @modifytime:
 */
public enum MethodPatternEnum {

    TERN("tern","精确匹配", SearchType.TERM_QUERY),
    MATCH("match","匹配查询", SearchType.MATCH_QUERY),
    PREFIX("prefix","前缀查询", SearchType.PREFIX_QUERY),
    WILDCARD("wildcard","通配符匹配", SearchType.WILDCARD_QUERY),
    REGEXP("regexp","正则匹配", SearchType.REGEXP_QUERY);

    private String method;
    private String text;
    private int code;

    MethodPatternEnum(String method , String text , int code){
        this.method = method;
        this.text = text;
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static MethodPatternEnum getByMethod(String method) {
        if (StringUtils.isBlank(method)) {
            return null;
        }
        for (MethodPatternEnum flag : MethodPatternEnum.values()) {
            if (flag.getMethod().equals(method)) {
                return flag;
            }
        }
        return TERN;
    }
}
