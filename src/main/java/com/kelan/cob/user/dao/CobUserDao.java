package com.kelan.cob.user.dao;

import com.kelan.cob.user.entity.CobUser;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/3/15.
 */
@Repository
public interface CobUserDao {

    public String login(String account, String password);

    public String getUserAccount(String account);

    public CobUser getUserByAccount(String account);

    //根据账号获取角色信息
    String getRoleByAccount(String account);

    public int addUser(CobUser user);
}
