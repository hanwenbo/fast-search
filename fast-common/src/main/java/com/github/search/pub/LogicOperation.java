package com.github.search.pub;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 逻辑运算实体
 * @time: 2018年08月01日
 * @modifytime:
 */
public class LogicOperation  implements Serializable{

    /**
     * reference : 1 正向查询 2 反向查询
     *
     * {@link com.qf58.search.enums.NotFlagEnum}
     */
    protected int notFlag;

    /**
     * reference : 1 与查询 2 或查询
     *
     * {@link com.qf58.search.enums.AndOrEnums}
     */
    protected int andOrFlag;


    public int getNotFlag() {
        return notFlag;
    }

    public void setNotFlag(int notFlag) {
        this.notFlag = notFlag;
    }

    public int getAndOrFlag() {
        return andOrFlag;
    }

    public void setAndOrFlag(int andOrFlag) {
        this.andOrFlag = andOrFlag;
    }

    @Override
    public String toString() {
        return "LogicOperation{" +
                "notFlag=" + notFlag +
                ", andOrFlag=" + andOrFlag +
                '}';
    }
}
