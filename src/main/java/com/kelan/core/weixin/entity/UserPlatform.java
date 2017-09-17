package com.kelan.core.weixin.entity;

import com.kelan.core.entity.BaseInfoEntity;

/**
 * @Title: 用户平台信息
 * 
 * @author mj
 *
 */
public class UserPlatform extends BaseInfoEntity {

	private String userPlatformId; // 用户平台标识ID
	private String platformFlag; // 平台标识
	private String userId; // 用户ID
	private String accessToken; // 网页授权接口调用凭证
	private long expiresEndTime; // 超时截止时间
	private String refreshToken; // 用户刷新access_token
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getUserPlatformId() {
		return userPlatformId;
	}

	public void setUserPlatformId(String userPlatformId) {
		this.userPlatformId = userPlatformId;
	}

	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getExpiresEndTime() {
		return expiresEndTime;
	}

	public void setExpiresEndTime(long expiresEndTime) {
		this.expiresEndTime = expiresEndTime;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
