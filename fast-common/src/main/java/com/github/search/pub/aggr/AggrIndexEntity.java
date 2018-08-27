package com.github.search.pub.aggr;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年07月04日
 * @modifytime:
 */
public class AggrIndexEntity {

    private String index ;

    private String type;

    private AggrSearchEntity aggrSearchEntity;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AggrSearchEntity getAggrSearchEntity() {
        return aggrSearchEntity;
    }

    public void setAggrSearchEntity(AggrSearchEntity aggrSearchEntity) {
        this.aggrSearchEntity = aggrSearchEntity;
    }
}
