package com.github.search.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月23日
 * @modifytime:
 */
public enum NotFlagEnum {
    POSITIVE(1,"正向查询"),
    NEGATIVE(2,"反向查询");

    private int code;
    private String text;

    NotFlagEnum(int code , String text){
        this.code = code;
        this.code = code;
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

    public static NotFlagEnum getByCode(int code) {
        if (code == 0) {
            return null;
        }
        for (NotFlagEnum flag : NotFlagEnum.values()) {
            if (flag.getCode() == code) {
                return flag;
            }
        }
        return null;
    }
}
