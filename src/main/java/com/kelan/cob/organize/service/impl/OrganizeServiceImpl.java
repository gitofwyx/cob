package com.kelan.cob.organize.service.impl;

import com.kelan.cob.organize.dao.OrganizeDao;
import com.kelan.cob.organize.entity.Organize;
import com.kelan.cob.organize.service.OrganizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class OrganizeServiceImpl implements OrganizeService {

    @Autowired
    private OrganizeDao dao;

    @Override
    public boolean addOrganize(Organize organize) {
        return dao.addOrganize(organize)==1?true:false;
    }

    @Override
    public Organize getOrganize(String id) {
        return  dao.getOrganize(id);
    }

    @Override
    /**
     * 分页获取列表
     */
    public List<Organize> listOrganize(int pageStart, int pageSize,String type) {
        return dao.listOrganize((pageStart-1)*pageSize, pageSize,type);
        //return dao.listActivity(pageStart,pageSize);
    }

    @Override
    public boolean updateOrganize(Organize organize) {
        return dao.updateOrganize(organize)==1?true:false;
    }

    @Override
    public boolean deleteListOrganize(List<String> listStr)  {
        return dao.deleteListOrganize(listStr)>=1?true:false;
    }

    @Override
    public boolean deleteOrganize(List<String> listStr) {
        return dao.deleteOrganize(listStr)>1?true:false;
    }
}
