package com.kelan.cob.pic.dao;

import com.kelan.cob.pic.entity.CobPic;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface CobPicDao {

    public int addCobPic(CobPic cobPic);

    public CobPic getCobPic(String id);

    public List<CobPic> listCobPic(int pageStart, int pageSize,String objId);

    public List<String> listCobPicId(int pageStart, int pageSize,String objId);

    public int getPicCount(String objId);

    public int updateCobPic(CobPic cobPic);

    public int deleteCobPic(List<String> listStr);

    public int deletePicById(String id);
}
