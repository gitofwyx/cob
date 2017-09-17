/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kelan.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页后的数据结果
 *
 * @author Administrator
 */
public class Pager {

    private int pageSize = 10;//每页显示记录数 
    private List<?> items;//存放结果集
    private int totalCount; //总记录数     
    private int totalPage;//总页数 
    private int currentPage;//当前页

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize == 0) ? 10 : pageSize;
    }

    public List<?> getItems() {
        return items;
    }

    /*
     *分页结果
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> doPagination() {
        if (items == null) {
            throw new NullPointerException("未传入必须的List对象.");
        }
        List temp = new ArrayList();

        for (int i = this.getStart(); i < this.getEnd(); i++) {
            temp.add(items.get(i));
        }
        return temp;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = (currentPage == 0) ? 1 : currentPage;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {

        if (this.pageSize == 0) {
            this.totalPage =0;
        } else if (totalCount % this.pageSize == 0) {
            this.totalPage = totalCount / this.pageSize;
        } else {
            this.totalPage = totalCount / this.pageSize + 1;
        }
        return this.totalPage;
    }

    /*
    *分页起始位置
    */
    public int getStart() {
        if (currentPage > getTotalPage()) {
            currentPage = 1;
        }
        return (currentPage - 1) * pageSize;

    }
    
    /*
    *分页结束位置
    */
    public int getEnd(){
        if (this.getCurrentPage() >= this.getTotalPage()) {
            return this.getTotalCount();
        } else{
            return (this.getCurrentPage()) * this.getPageSize();
        }
    }

    /**
     * 分页导航
     *
     * @return
     */
    public String getPagerStr() {

        StringBuilder sb = new StringBuilder();

        int pageCount = getTotalPage();//总页数
        int a_total = 10;      //分页条中有多少个超链接
        sb.append("共" + totalCount + "条数据   页次" + currentPage + "/" + pageCount + "页");
        //实际应用中修改上面第三个参数即可

        int a_padding = (int) Math.ceil(a_total / 2);  //中间的那个超链接距离边缘链接的间隔a的个数 例如：共11个分页 那么这个就是5

        if (pageCount - currentPage <= a_padding && currentPage > a_padding + 1) {
            a_padding = a_total - (pageCount - currentPage);
        }

        int start = currentPage - a_padding,
                end = start + a_total;

        if (currentPage - 1 > 0) {
            sb.append("<a href=?page=1>首页</a>");
            sb.append("<a href=?page=" + (currentPage - 1) + ">上一页</a>");
        }

        for (int i = start; i <= end; i++) {

            if (i <= 0) {
                end += Math.abs(i);
                i = 1;
            }
            sb.append(" <a href=?page=" + i + " " + (i == currentPage ? "style=\"color:red;\"" : "") + "> " + i + " </a> ");

            if (i == pageCount) {
                break;
            }

        }

        if (currentPage - pageCount < 0) {
            sb.append("<a href=?page=" + (currentPage + 1) + ">下一页</a>");
            sb.append("<a href=?page=" + pageCount + ">尾页</a>");
        }

        return sb.toString();

    }
    
}
