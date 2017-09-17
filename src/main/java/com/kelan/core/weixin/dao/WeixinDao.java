package com.kelan.core.weixin.dao;

import com.kelan.core.weixin.entity.PlatformAccount;

/**
 * 角色管理DAO接口
 * 
 * @author mj
 *
 */
public interface WeixinDao {

	// 查询平台信息
	public PlatformAccount selectPlatformAccount(String platformFlag);

}
