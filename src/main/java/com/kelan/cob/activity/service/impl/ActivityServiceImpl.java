package com.kelan.cob.activity.service.impl;

import com.kelan.cob.activity.dao.ActivityDao;
import com.kelan.cob.activity.entity.Activity;
import com.kelan.cob.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao dao;

    @Override
    public boolean addActivity(Activity activity) {
        return dao.addActivity(activity)==1?true:false;
    }

    @Override
    public Activity getActivity(String id) {
        return  dao.getActivity(id);
    }

    @Override
    /**
     * 分页获取列表
     */
    public List<Activity> listActivity(int pageStart, int pageSize) {
        return dao.listActivity((pageStart-1)*pageSize, pageSize);
        //return dao.listActivity(pageStart,pageSize);
    }

    @Override
    public boolean updateActivity(Activity activity) {
        return dao.updateActivity(activity)==1?true:false;
    }

    @Override
    public boolean deleteListActivity(List<String> listStr)  {
        return dao.deleteListActivity(listStr)>=1?true:false;
    }

    @Override
    public boolean deleteActivity(List<String> listStr) {
        return dao.deleteActivity(listStr)>1?true:false;
    }
}
