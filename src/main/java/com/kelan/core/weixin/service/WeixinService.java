package com.kelan.core.weixin.service;

import com.kelan.core.weixin.entity.PlatformAccount;

/**
 * 
 * @author 麦俊
 *
 */
public interface WeixinService {

	// 查询平台信息
	public PlatformAccount selectPlatformAccount(String platformFlag);



}
