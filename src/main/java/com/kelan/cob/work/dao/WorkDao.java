package com.kelan.cob.work.dao;

import com.kelan.cob.work.entity.Work;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface WorkDao {

    public int addWork(Work work);

    public Work getWork(String id);

    public List<Work> listWork(int pageStart, int pageSize);

    public int updateWork(Work activity);

    public int deleteListWork(List<String> listStr);

    public int deleteWork(List<String> listStr);
}
