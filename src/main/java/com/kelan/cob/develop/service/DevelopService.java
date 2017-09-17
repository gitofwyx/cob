package com.kelan.cob.develop.service;

import com.kelan.cob.develop.entity.Develop;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface DevelopService {

    public boolean addDevelop(Develop develop);

    public Develop getDevelop(String id);

    public List<Develop> listDevelop(int pageStart, int pageSize);

    public boolean updateDevelop(Develop develop);

    public boolean deleteListDevelop(List<String> listStr);

    public boolean deleteDevelop(List<String> listStr);
}
