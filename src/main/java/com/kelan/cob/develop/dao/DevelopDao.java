package com.kelan.cob.develop.dao;

import com.kelan.cob.develop.entity.Develop;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface DevelopDao {

    public int addDevelop(Develop develop);

    public Develop getDevelop(String id);

    public List<Develop> listDevelop(int pageStart, int pageSize);

    public int updateDevelop(Develop develop);

    public int deleteListDevelop(List<String> listStr);

    public int deleteDevelop(List<String> listStr);
}
