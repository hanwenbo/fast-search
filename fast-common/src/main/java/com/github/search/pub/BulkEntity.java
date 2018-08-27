package com.github.search.pub;

/**
 * Created with IntelliJ IDEA.
 * Description:混合批量添加或删除数据的处理类
 * User: zhubo
 * Date: 2017-09-22
 * Time: 14:21
 */
public class BulkEntity {

    private String op;//OpEnum中的类型

    private Object obj;//当Op为add时存储该值

    private Long id;//当Op为add或者delete时存储该值

    public BulkEntity() {
    }

    public BulkEntity(String op, Object obj, Long id) {
        this.op = op;
        this.obj = obj;
        this.id = id;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BulkEntity{" +
                "op='" + op + '\'' +
                ", obj=" + obj +
                ", id=" + id +
                '}';
    }
}
