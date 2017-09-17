package com.kelan.core.weixin.entity;

import com.kelan.core.entity.UUIdEntity;

/**
 * @Title: 平台账号信息
 *
 */
public class PlatformAccount extends UUIdEntity {

	private String platformFlag; // 平台标识
	private String appId; // 公众号的唯一标识
	private String mchId; // 商户号
	private String appSecret; // 公众账号密钥
	private String paySecret; // 商户账号密钥
	protected String createDate; // 创建时间
	private String deleteFlag; // 删除标识，0：有效；1：无效
	
	public String getPlatformFlag() {
		return platformFlag;
	}

	public void setPlatformFlag(String platformFlag) {
		this.platformFlag = platformFlag;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getPaySecret() {
		return paySecret;
	}

	public void setPaySecret(String paySecret) {
		this.paySecret = paySecret;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
