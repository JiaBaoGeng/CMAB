package cn.jbg.cmab.common.dao;


/**
 * Created by jbg on 2018/3/24.
 */
public class PageInfo {
    private int pageIndex = 1;//页码，默认是第一页
    private int pageSize = 15;//每页显示的记录数，默认是15
    private int total;//总记录数
    private int pageCount;//总页数


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


}
