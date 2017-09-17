package com.kelan.cob.system.entity;

import com.kelan.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */
public class Side_menu extends BaseInfoEntity {
    private String id;
    private String node_name;
    private String order_number;
    private String model;
    private String list_url;
    private String add_url;
    private String update_url;
    private String delete_url;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getList_url() {
        return list_url;
    }

    public void setList_url(String list_url) {
        this.list_url = list_url;
    }

    public String getAdd_url() {
        return add_url;
    }

    public void setAdd_url(String add_url) {
        this.add_url = add_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public String getDelete_url() {
        return delete_url;
    }

    public void setDelete_url(String delete_url) {
        this.delete_url = delete_url;
    }
}
