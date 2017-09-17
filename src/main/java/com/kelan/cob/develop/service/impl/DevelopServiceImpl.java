package com.kelan.cob.develop.service.impl;

import com.kelan.cob.develop.dao.DevelopDao;
import com.kelan.cob.develop.entity.Develop;
import com.kelan.cob.develop.service.DevelopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class DevelopServiceImpl implements DevelopService {

    @Autowired
    private DevelopDao dao;

    @Override
    public boolean addDevelop(Develop develop) {
        return dao.addDevelop(develop)==1?true:false;
    }

    @Override
    public Develop getDevelop(String id) {
        return  dao.getDevelop(id);
    }

    @Override
    public List<Develop> listDevelop(int pageStart, int pageSize) {
        return dao.listDevelop((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean updateDevelop(Develop develop) {
        return dao.updateDevelop(develop)==1?true:false;
    }

    @Override
    public boolean deleteListDevelop(List<String> listStr) {
        return dao.deleteListDevelop(listStr)>=1?true:false;
    }

    @Override
    public boolean deleteDevelop(List<String> listStr) {
        return dao.deleteDevelop(listStr)==1?true:false;
    }
}
