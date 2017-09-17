package com.kelan.cob.user.service;

import com.kelan.cob.user.entity.CobUser;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface CobUserService {

    public boolean login(String account, String password);

    public boolean getUserAccount(String account);

    public CobUser getUserByAccount(String account);

    String getRoleByAccount(String account);

    public boolean addUser(CobUser User);
}
