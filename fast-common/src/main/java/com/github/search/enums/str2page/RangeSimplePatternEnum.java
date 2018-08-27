package com.github.search.enums.str2page;


import com.github.search.pub.SearchType;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月27日
 * @modifytime:
 */
public enum  RangeSimplePatternEnum {

    RANGE_INNER_QUERY_LEFT_CLOSE(">=", SearchType.NUMBER_RANGE_INNER_QUERY_LEFT_CLOSE, SearchType.DATE_STRING_RANGE_INNER_QUERY_LEFT_CLOSE),
    RANGE_INNER_QUERY_LEFT_OPEN(">", SearchType.NUMBER_RANGE_INNER_QUERY_LEFT_OPEN, SearchType.DATE_STRING_RANGE_INNER_QUERY_LEFT_OPEN),
    RANGE_INNER_QUERY_RIGHT_CLOSE("<=", SearchType.NUMBER_RANGE_INNER_QUERY_RIGHT_CLOSE, SearchType.DATE_STRING_RANGE_INNER_QUERY_RIGHT_CLOSE),
    RANGE_INNER_QUERY_RIGHT_OPEN("<", SearchType.NUMBER_RANGE_INNER_QUERY_RIGHT_OPEN, SearchType.DATE_STRING_RANGE_INNER_QUERY_RIGHT_OPEN);

    private String p1;
    private int numCode;
    private int dateCode;

    RangeSimplePatternEnum(String p1, int numCode, int dateCode) {
        this.p1 = p1;
        this.numCode = numCode;
        this.dateCode = dateCode;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public int getDateCode() {
        return dateCode;
    }

    public void setDateCode(int dateCode) {
        this.dateCode = dateCode;
    }

    public static RangeSimplePatternEnum getByMethod(String p1) {
        if (StringUtils.isBlank(p1)) {
            return null;
        }
        for (RangeSimplePatternEnum flag : RangeSimplePatternEnum.values()) {
            if (flag.getP1().equals(p1)) {
                return flag;
            }
        }
        return null;
    }
}
