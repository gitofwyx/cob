package com.kelan.core.weixin.entity;

import com.kelan.core.entity.BaseInfoEntity;

/**
 * @Title: 用户ACCESS_TOKEN
 * 
 * @author mj
 *
 */
public class UserAccount extends BaseInfoEntity {

	private String userName; // 用户名
	private String userPhone; // 手机号码
	private String userTel; // 固话号码
	private String userPassWord; // 密码
	private String userEmail; // 邮箱地址
	private String userGender; // 性别
	private String userSign; // 签名
	private String userPicUrl; // 用户头像地址
	private String userFlag; // 用户标识
	private String isLocked; // 是否激活
	private String adminFlag; // 管理员标识
	private int userOrder; // 排序
	private String remark; // 备注
	private String platformId; // 用户平台ID
	private String userPlatformId; // 用户平台标识ID（openId）
	private String accessToken; // 网页授权接口调用凭证
	private long expiresEndTime; // 超时截止时间，单位（毫秒）
	private String refreshToken; // 用户刷新access_token
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserPassWord() {
		return userPassWord;
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserSign() {
		return userSign;
	}

	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public int getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(int userOrder) {
		this.userOrder = userOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getUserPlatformId() {
		return userPlatformId;
	}

	public void setUserPlatformId(String userPlatformId) {
		this.userPlatformId = userPlatformId;
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
