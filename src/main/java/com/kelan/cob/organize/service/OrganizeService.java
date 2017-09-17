package com.kelan.cob.organize.service;

import com.kelan.cob.organize.entity.Organize;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface OrganizeService {

    boolean addOrganize(Organize organize);

    Organize getOrganize(String id);

    List<Organize> listOrganize(int pageStart, int pageSize,String type);

    public boolean updateOrganize(Organize organize);

    public boolean deleteListOrganize(List<String> listStr);

    public boolean deleteOrganize(List<String> listStr);
}
