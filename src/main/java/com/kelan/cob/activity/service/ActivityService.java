package com.kelan.cob.activity.service;

import com.kelan.cob.activity.entity.Activity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface ActivityService {

    boolean addActivity(Activity activity);

    Activity getActivity(String id);

    List<Activity> listActivity(int pageStart, int pageSize);

    public boolean updateActivity(Activity activity);

    public boolean deleteListActivity(List<String> listStr);

    public boolean deleteActivity(List<String> listStr);
}
