package com.kelan.core.weixin.service.impl;

import com.kelan.core.weixin.dao.WeixinDao;
import com.kelan.core.weixin.entity.PlatformAccount;
import com.kelan.core.weixin.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 麦俊
 */
@Service
public class WeixinServiceImpl implements WeixinService {

	@Autowired
	private WeixinDao weixinDao;

	@Override
	public PlatformAccount selectPlatformAccount(String platformFlag) {
		return this.weixinDao.selectPlatformAccount(platformFlag);
	}

}