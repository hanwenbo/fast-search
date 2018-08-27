package com.github.search.pub.str2page;


import com.github.search.enums.AndOrEnums;
import com.github.search.enums.NotFlagEnum;
import com.github.search.pub.ValueEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 对应ElasticSearch 中 ValuePackage  {@link com.github.search.pub.ValuePackage}
 * 默认为 正向 & 查询
 * @time: 2018年06月25日
 * @modifytime:
 */
public class VPEntity {

    /**
     * reference : 1 正向查询 2 反向查询
     *
     * {@link com.github.search.enums.NotFlagEnum}
     */
    private int notFlag;

    /**
     * reference : 1 与查询 2 或查询
     *
     * {@link com.github.search.enums.AndOrEnums}
     */
    private int andOrFlag;


    private String entitys;


    private List<ValueEntity> entityList ;


    /**
     * 默认为正向查询 并且为 & 查询
     */
    public VPEntity() {
        this.notFlag = NotFlagEnum.POSITIVE.getCode();
        this.andOrFlag = AndOrEnums.AND.getCode();
    }

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

    public String getEntitys() {
        return entitys;
    }

    public void setEntitys(String entitys) {
        this.entitys = entitys;
    }

    public List<ValueEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<ValueEntity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public String toString() {
        return "VPEntity{" +
                "notFlag=" + notFlag +
                ", andOrFlag=" + andOrFlag +
                ", entitys='" + entitys + '\'' +
                ", entityList=" + entityList +
                '}';
    }
}
