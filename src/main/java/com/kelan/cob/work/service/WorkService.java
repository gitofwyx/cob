package com.kelan.cob.work.service;

import com.kelan.cob.work.entity.Work;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface WorkService {

    boolean addWork(Work work);

    Work getWork(String id);

    List< Work> listWork(int pageStart, int pageSize);

    public boolean updateWork( Work work);

    public boolean deleteListWork(List<String> listStr);

    public boolean deleteWork(List<String> listStr);
}
