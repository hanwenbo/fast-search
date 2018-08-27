package com.github.search.pub;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-09-05
 * Time: 11:59
 */
public class CUDEntity {

    private String op;

    private String key;

    private Long id;

    private String jsonData;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public String toString() {
        return "CUDEntity{" +
                "op='" + op + '\'' +
                ", key='" + key + '\'' +
                ", id=" + id +
                ", jsonData='" + jsonData + '\'' +
                '}';
    }
}
