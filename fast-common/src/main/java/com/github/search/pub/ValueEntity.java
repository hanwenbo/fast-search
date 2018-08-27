package com.github.search.pub;

import com.github.search.enums.AndOrEnums;
import com.github.search.enums.NotFlagEnum;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description: 查询值的操作类
 * User: zhubo
 * Date: 2017-09-28
 * Time: 16:34
 */
public class ValueEntity extends LogicOperation implements Serializable{

    private String field;
    /**
     * reference : 每个搜索功能的索引规则
     *
     * {@link com.github.search.pub.SearchType}
     */
    private int type;

    private Object[] value;

    private ValueEntity(String field , int type, Object[] value) {
        this.field = field;
        this.notFlag = NotFlagEnum.POSITIVE.getCode();
        // 单字段的多条件查询 默认为或关系
        this.andOrFlag = AndOrEnums.AND.getCode();
        this.type = type;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    private void setField(String field) {
        this.field = field;
    }

    public int getType() {
        return type;
    }

    private void setType(int type) {
        this.type = type;
    }

    public Object[] getValue() {
        return value;
    }

    public void setValue(Object[] value) {
        this.value = value;
    }

    public static class Builder{

        ValueEntity ve = null;

        public Builder(String field , Object[] objs) {
            ve = new ValueEntity(field,SearchType.TERM_QUERY,objs);
        }

        public Builder rule(int rule) {
            ve.setType(rule);
            return this;
        }

        public Builder setOr() {
            ve.setAndOrFlag(AndOrEnums.OR.getCode());
            return this;
        }

        public Builder setNot() {
            ve.setNotFlag(NotFlagEnum.NEGATIVE.getCode());
            return this;
        }
        public ValueEntity build() {
            return ve;
        }
    }




    @Override
    public String toString() {
        return "ValueEntity{" +
                "field='" + field + '\'' +
                ", notFlag=" + notFlag +
                ", andOrFlag=" + andOrFlag +
                ", type=" + type +
                ", value=" + Arrays.toString(value) +
                '}';
    }
}
