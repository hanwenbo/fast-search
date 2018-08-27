package com.github.search.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-09-05
 * Time: 13:01
 */
public enum OpEnum {
    ADD("add","索引新增操作"),
    UPDATE("update","索引修改操作"),
    DELETE("delete","索引删除操作"),
    ;

    private String op;
    private String desc;

    OpEnum(String op , String desc){
        this.op = op;
        this.desc = desc;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static OpEnum getByOp(String op) {
        if (op == null) {
            return null;
        }
        for (OpEnum city : OpEnum.values()) {
            if (city.getOp().equals(op)) {
                return city;
            }
        }
        return null;
    }
}
