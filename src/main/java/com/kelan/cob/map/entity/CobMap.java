package com.kelan.cob.map.entity;

import com.kelan.core.entity.BaseInfoEntity;

/**
 * Created by Administrator on 2017/3/15.
 */
public class CobMap extends BaseInfoEntity{

    private String id;
    private String name;
    private String coordinate_x;
    private String coordinate_y;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinate_x() {
        return coordinate_x;
    }

    public void setCoordinate_x(String coordinate_x) {
        this.coordinate_x = coordinate_x;
    }

    public String getCoordinate_y() {
        return coordinate_y;
    }

    public void setCoordinate_y(String coordinate_y) {
        this.coordinate_y = coordinate_y;
    }

}
