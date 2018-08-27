package com.github.search.pub;

import com.github.search.enums.AndOrEnums;
import com.github.search.enums.NotFlagEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月23日
 * @modifytime:
 */
public class ValuePackage extends LogicOperation implements Serializable{



    private List<ValueEntity> entitys;

    public ValuePackage() {
        this.notFlag = NotFlagEnum.POSITIVE.getCode();
        this.andOrFlag = AndOrEnums.AND.getCode();
    }

    public List<ValueEntity> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<ValueEntity> entitys) {
        this.entitys = entitys;
    }

    @Override
    public String toString() {
        return "ValuePackage{" +
                "entitys=" + entitys +
                '}';
    }
}
