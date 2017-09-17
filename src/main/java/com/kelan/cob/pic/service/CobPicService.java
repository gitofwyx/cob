package com.kelan.cob.pic.service;

import com.kelan.cob.pic.entity.CobPic;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface CobPicService {

    boolean addCobPic(CobPic cobPic);

    CobPic getCobPic(String id);

    List<CobPic> listCobPic(int pageStart, int pageSize,String objId);

    public List<String> listCobPicId(int pageStart, int pageSize, String objId);

    public int getPicCount(String objId);

    public boolean updateCobPic(CobPic cobPic);

    public boolean deleteCobPic(List<String> listStr);

    public boolean deletePicById(String id);
}
