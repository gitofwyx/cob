package com.kelan.core.weixin.pay.entity.req;

/**
 * 微信支付接口——接口请求基类
 *
 */
public class BaseRequest {
	// 公众账号 ID 是 String(32) 微信分配的公众账号 ID
	private String appid;
	// 商户号 是 String(32) 微信支付分配的商户号
	private String mch_id;
	// 随机字符串 是 String(32) 随机字符串，不长于 32 位
	private String nonce_str;
	// 签名 是 String(32) 签名,详细签名方法见3.2节
	private String sign;

	// ------ Getter & Setter ------
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseRequest [appid=" + appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", sign=" + sign
				+ ", super.toString()=" + super.toString() + "]";
	}
}
