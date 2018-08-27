package com.github.search.page;

/**
 * Created with IntelliJ IDEA.
 * Description: ES搜索基础Pager
 * User: zhubo
 * Date: 2017-08-29
 * Time: 16:54
 */
public class Pager {

    /**每页显示*/
    private long pageSize = 10;
    /**页码*/
    private long pageNo = 1;
    /**开始数*/
    private long start = 0;
    /**总条数*/
    private long totalRows = 0;


    public Pager(){

    }
    public Pager(long pageSize, long pageNo) {
        if(pageSize != 0){
            this.pageSize = pageSize;
        }
        if(pageNo != 0){
            this.pageNo = pageNo;
        }
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getStart() {
        this.start = (pageNo - 1) * pageSize;
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public long getPageCount(){
        return getTotalRows() % getPageSize() == 0 ? getTotalRows() / getPageSize() : getTotalRows() / getPageSize() + 1;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", start=" + start +
                ", totalRows=" + totalRows +
                ", pageCount=" + getPageCount() +
                '}';
    }
}
