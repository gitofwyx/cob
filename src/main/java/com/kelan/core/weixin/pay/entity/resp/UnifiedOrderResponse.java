package com.kelan.core.weixin.pay.entity.resp;

/**
 * 微信支付接口——统一支付接口响应实体类
 * 适合微信支付接口规范V3.3.7
 *
 */
public class UnifiedOrderResponse extends BaseResponse {

	// 设备号 否 String(32) 微信支付分配的终端设备号
	private String device_info;

	/*
	 * 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回
	 */
	// 交易类型 是 String(16) JSAPI、NATIVE、APP
	private String trade_type;
	// 预支付 ID 是 String(64) 微信生成的预支付 ID，用于后续接口调用中使用
	private String prepay_id;
	// 二维码链接 否 String(64) trade_type 为 NATIVE 是有返回，此参数可直接生成二维码展示出来进行扫码支付
	private String code_url;

	// ------ Getter & Setter ------

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UnifiedOrderResponse [device_info=" + device_info + ", trade_type=" + trade_type + ", prepay_id="
				+ prepay_id + ", code_url=" + code_url + ", super.toString()=" + super.toString() + "]";
	}

}
