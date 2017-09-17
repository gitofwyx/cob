package com.kelan.cob.pic.entity;

import com.kelan.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */
public class CobPic extends BaseInfoEntity {
    private String id;
    private String objId;
    private String url;
    private String mixUrl;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMixUrl() {
        return mixUrl;
    }

    public void setMixUrl(String mixUrl) {
        this.mixUrl = mixUrl;
    }
}
