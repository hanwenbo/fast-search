package com.github.search.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 指标
 * @time: 2018年07月04日
 * @modifytime:
 */
public enum  MetricsEnums {

    MAX(1,"_max"),
    MIN(2,"_min"),
    AVG(3,"_avg"),
    SUM(4,"_sum");

    private int code;

    private String msg;

    MetricsEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static MetricsEnums getByCode(int code) {
        if (code == 0) {
            return null;
        }
        for (MetricsEnums flag : MetricsEnums.values()) {
            if (flag.getCode() == code) {
                return flag;
            }
        }
        return null;
    }
}
