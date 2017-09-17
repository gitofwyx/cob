package com.kelan.cob.work.service.impl;

import com.kelan.cob.work.dao.WorkDao;
import com.kelan.cob.work.entity.Work;
import com.kelan.cob.work.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkDao dao;

    @Override
    public boolean addWork(Work work) {
        return dao.addWork(work)==1?true:false;
    }

    @Override
    public Work getWork(String id) {
        return  dao.getWork(id);
    }

    @Override
    public List<Work> listWork(int pageStart, int pageSize) {
        return dao.listWork((pageStart-1)*pageSize, pageSize);
    }

    @Override
    public boolean updateWork(Work work) {
        return dao.updateWork(work)==1?true:false;
    }

    @Override
    public boolean deleteListWork(List<String> listStr) {
        return dao.deleteListWork(listStr)>=1?true:false;
    }

    @Override
    public boolean deleteWork(List<String> listStr) {
        return dao.deleteWork(listStr)==1?true:false;
    }
}
