package com.github.search.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月23日
 * @modifytime:
 */
public enum  AndOrEnums {

    AND(1,"与查询"),
    OR(2,"或查询");

    private int code;
    private String text;

    AndOrEnums(int code , String text){
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static AndOrEnums getByCode(int code) {
        if (code == 0) {
            return null;
        }
        for (AndOrEnums flag : AndOrEnums.values()) {
            if (flag.getCode() == code) {
                return flag;
            }
        }
        return null;
    }


}
