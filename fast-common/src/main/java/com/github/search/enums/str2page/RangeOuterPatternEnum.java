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
public enum  RangeOuterPatternEnum {


    RANGE_OUTER_QUERY("<=",">=", SearchType.NUMBER_RANGE_OUTER_QUERY, SearchType.DATE_STRING_RANGE_OUTER_QUERY),
    RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_OPEN("<",">", SearchType.NUMBER_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_OPEN, SearchType.DATE_STRING_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_OPEN),
    RANGE_OUTER_QUERY_LEFT_CLOSE_RIGHT_OPEN("<",">=", SearchType.NUMBER_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_CLOSE, SearchType.DATE_STRING_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_CLOSE),
    RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_CLOSE("<=",">", SearchType.NUMBER_RANGE_OUTER_QUERY_LEFT_CLOSE_RIGHT_OPEN, SearchType.DATE_STRING_RANGE_OUTER_QUERY_LEFT_CLOSE_RIGHT_OPEN);

    private String p1;
    private String p2;
    private int numCode;
    private int dateCode;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
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

    RangeOuterPatternEnum(String p1, String p2, int numCode, int dateCode) {
        this.p1 = p1;
        this.p2 = p2;
        this.numCode = numCode;
        this.dateCode = dateCode;
    }


    public static RangeOuterPatternEnum getByMethod(String p1, String p2) {
        if (StringUtils.isBlank(p1)|| StringUtils.isBlank(p2)) {
            return null;
        }
        for (RangeOuterPatternEnum flag : RangeOuterPatternEnum.values()) {
            if (flag.getP1().equals(p1) && flag.getP2().equals(p2)) {
                return flag;
            }
        }
        return null;
    }

}
