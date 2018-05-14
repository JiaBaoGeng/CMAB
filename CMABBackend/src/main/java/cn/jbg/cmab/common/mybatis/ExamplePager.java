package cn.jbg.cmab.common.mybatis;

import cn.jbg.cmab.common.dao.Page;

/**
 * Created by jbg on 2018/3/24.
 */

public class ExamplePager {

    private  static ThreadLocal<ExamplePager> localVar = new ThreadLocal<ExamplePager>();


    private int pageIndex = 1;//页码，默认是第一页
    private int pageSize = 15;//每页显示的记录数，默认是15
    private int pageCount;//总页数

    private Page page; //普通的page对象，可以设置totalnum

    public static ExamplePager getInstancePager(){
        if(localVar.get()==null)
            localVar.set(new ExamplePager());
        return localVar.get();
    }

    public static boolean isExists(){
        if(localVar.get()==null)
            return false;

        return true;
    }

    public static void clear(){
        if(localVar.get()!=null){
            localVar.set(null);
        }
    }

    /**
     * 从page对象创建
     * @param _page
     * @return
     */
    public static ExamplePager buildByPage(Page _page){
        ExamplePager examplePager = getInstancePager();
        examplePager.pageIndex = _page.getPageIndex();
        examplePager.pageSize = _page.getPageSize();
        examplePager.pageCount = _page.getPageCount();
        examplePager.page = _page;
        return examplePager;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Page getPage() {
        return page;
    }
}

