package com.github.search.pub.str2page;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月25日
 * @modifytime:
 */
public class Position {

    private int start;

    private int end;

    private String str;

    public Position(int start, int end, String str) {
        this.start = start;
        this.end = end;
        this.str = str;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Position{" +
                "start=" + start +
                ", end=" + end +
                ", str='" + str + '\'' +
                '}';
    }
}
