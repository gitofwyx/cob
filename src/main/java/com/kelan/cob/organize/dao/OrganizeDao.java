package com.kelan.cob.organize.dao;

import com.kelan.cob.organize.entity.Organize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface OrganizeDao {

    public int addOrganize(Organize organize);

    public Organize getOrganize(String id);

    public List<Organize> listOrganize(int pageStart, int pageSize,String type);

    public int updateOrganize(Organize organize);

    public int deleteListOrganize(List<String> listStr);

    public int deleteOrganize(List<String> listStr);
}
