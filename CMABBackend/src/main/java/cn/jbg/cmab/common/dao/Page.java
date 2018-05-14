package cn.jbg.cmab.common.dao;

import cn.jbg.cmab.common.util.MapParamUtil;

import java.util.*;

/**
 * Created by jbg on 2018/3/24.
 * 分页组件封装SQL查询的分页信息
 */
public class Page<T> implements Map<String, Object> {

    private int pageIndex = 1;//页码，默认是第一页
    private int pageSize = 15;//每页显示的记录数，默认是15
    private int total;//总记录数
    private int pageCount;//总页数
    private List<T> rows = new ArrayList<T>();//对应的当前页记录
    private Map<String, Object> params = new HashMap<String, Object>();

    public Page(){}

    public Page(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    /**
     * 直接通过MAP初始化
     * @param params
     */
    public Page(Map params){
        initByMapParam(params);

    }

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
        int totalPage = total %pageSize==0 ? total /pageSize : total /pageSize + 1;
        this.setPageCount(totalPage);
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public PageInfo getPageInfo(Page page) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageIndex(page.getPageIndex());
        pageInfo.setPageCount(page.getPageCount());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    /**
     * 统一返回给前台的分页格式
     * @return
     */
    public Map getResultMap(){
        Map result = new HashMap();
        result.put("rows", rows);
        result.put("pageInfo", getPageInfo(this));
        result.put("page",pageIndex);  //fish的格式
        result.put("records",total);  //fish的格式
        return result;
    }

    @Override
    public int size() {
        return params.size();
    }

    @Override
    public boolean isEmpty() {
        return params.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return params.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return params.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return params.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return params.put((String) key,value);
    }

    @Override
    public Object remove(Object key) {
        return params.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        params.putAll(m);
    }

    @Override
    public void clear() {
        params.clear();

    }

    @Override
    public Set<String> keySet() {
        return params.keySet();
    }

    @Override
    public Collection<Object> values() {
        return params.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return params.entrySet();
        //return null;
    }
   /* @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Page [pageIndex=").append(pageIndex).append(", pageSize=")
                .append(pageSize).append(", rows=").append(rows).append(
                ", pageCount=").append(pageCount).append(
                ", total=").append(total).append("]");
        return builder.toString();
    }*/

    /**
     * 根据传入的数据列表，分页页数和分页大小
     * 返回分页数据
     * @param list
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static Page popupPageModel(List list, int pageIndex, int pageSize) {
        Page pageModel = new Page();

        if(list==null) return pageModel;

        int totalCount = list.size(); // long  to  int
        pageModel.setTotal(totalCount);

        if (totalCount == 0) {
            return new Page();
        }
        // pageCount
        if (totalCount % pageSize > 0) {
            pageModel.setPageCount(totalCount / pageSize + 1);
        } else {
            pageModel.setPageCount(totalCount / pageSize);
        }

        if (pageIndex < 0) {
            pageModel.setPageIndex(1);
        } else if(pageIndex>pageModel.getPageCount()){
            pageModel.setPageIndex(pageModel.getPageCount());
        }else {
            pageModel.setPageIndex(pageIndex);
        }

        if (pageSize < 0) {
            pageModel.setPageSize(totalCount);
        } else {
            pageModel.setPageSize(pageSize);
        }

        int index = 0;
        int count = 0;
        int locationInt = (pageModel.getPageIndex() - 1) * pageModel.getPageSize();

        Iterator iter = list.iterator();
        ArrayList dataList = new ArrayList();
        while(iter.hasNext() && count < pageSize){
            index++;
            if(index > locationInt){
                count++;
                dataList.add(iter.next());
            }
            else
                iter.next();
        }
        pageModel.setRows(dataList);
        return pageModel;
    }

    /**
     * 根据MAP产生，返回PAGE对象
     * @return
     */
    public  void initByMapParam(Map params){
        this.pageIndex = MapParamUtil.getIntValue(params,"pageIndex");
        this.pageSize = MapParamUtil.getIntValue(params,"pageSize");
        this.params=params;

    }

}

