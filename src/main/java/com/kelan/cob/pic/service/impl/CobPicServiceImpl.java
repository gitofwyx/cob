package com.kelan.cob.pic.service.impl;

import com.kelan.cob.pic.dao.CobPicDao;
import com.kelan.cob.pic.entity.CobPic;
import com.kelan.cob.pic.service.CobPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class  CobPicServiceImpl implements CobPicService {

    @Autowired
    private CobPicDao dao;

    @Override
    public boolean addCobPic(CobPic cobPic) {
        return dao.addCobPic(cobPic)==1?true:false;
    }

    @Override
    public CobPic getCobPic(String id) {
        return  dao.getCobPic(id);
    }

    @Override
    public List<CobPic> listCobPic(int pageStart, int pageSize,String objId) {
        return dao.listCobPic((pageStart-1)*pageSize, pageSize,objId);
    }

    @Override
    public List<String> listCobPicId(int pageStart, int pageSize, String objId) {
        return dao.listCobPicId((pageStart-1)*pageSize, pageSize,objId);
    }

    @Override
    public int getPicCount(String objId){
        return dao.getPicCount(objId);
    }

    @Override
    public boolean updateCobPic(CobPic cobPic) {
        return dao.updateCobPic(cobPic)==1?true:false;
    }

    @Override
    public boolean deleteCobPic(List<String> listStr) {
        return dao.deleteCobPic(listStr)==1?true:false;
    }

    @Override
    public boolean deletePicById(String id) {
        return dao.deletePicById(id)==1?true:false;
    }
}
