/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kelan.core.util;

/**
 * 数据分页参数 从画面获取到EasyUI的分页控件的分页信息参数
 *
 */
public class DataGridModel implements java.io.Serializable {

    private static final long serialVersionUID = 7232798260610351343L;
    private int page; //当前页,名字必须为page
    private int rows; //每页大小,名字必须为rows
    private String sort; //排序字段
    private String order; //排序规则

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
