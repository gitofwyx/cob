package com.kelan.cob.activity.dao;

import com.kelan.cob.activity.entity.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface ActivityDao {

    public int addActivity(Activity activity);

    public Activity getActivity(String id);

    public List<Activity> listActivity(int pageStart, int pageSize);

    public int updateActivity(Activity activity);

    public int deleteListActivity(List<String> listStr);

    public int deleteActivity(List<String> listStr);
}
