package com.github.search.pub;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-11-20
 * Time: 14:29
 */
public class BulkUpdate {

    private String id;

    private String jsonStr;


    public BulkUpdate() {
    }

    public BulkUpdate(String id, String jsonStr) {
        this.id = id;
        this.jsonStr = jsonStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
