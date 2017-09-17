package com.kelan.cob.user.service.impl;

import com.kelan.cob.user.dao.CobUserDao;
import com.kelan.cob.user.entity.CobUser;
import com.kelan.cob.user.service.CobUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class CobUserServiceImpl implements CobUserService {

    private static Logger log = Logger.getLogger(CobUserServiceImpl.class);

    @Autowired
    private CobUserDao dao;

    @Override
    public boolean login(String account, String password) {
        return account.equals(dao.login(account,password));
    }

    @Override
    public boolean getUserAccount(String account) {
        return account.equals(dao.getUserAccount(account));
    }

    @Override
    public CobUser getUserByAccount(String account) {
        return dao.getUserByAccount(account);
    }

    //<!--根据账号获取角色信息 -->
    @Override
    public String getRoleByAccount(String account) {
        return dao.getRoleByAccount(account);
    }

    @Override
    public boolean addUser(CobUser user) {
        return dao.addUser(user)==1?true:false;
    }
}
