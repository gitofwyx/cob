package com.kelan.core.weixin.pay.entity.resp;

/**
 * 微信支付接口——对账单接口（失败时）响应实体类
 *
 */
public class DownloadBillResponse {

	// 返回状态码 是 String(16) FAIL
	private String return_code;
	/*
	 * 返回信息 否 String(128) 返回信息，如非空，为错误
	 * 原因
	 * 签名失败
	 * 参数格式校验错误
	 * 该日期订单未生成
	 */
	private String return_msg;

	// ------ Getter & Setter ------
	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DownloadBillResponse [return_code=" + return_code + ", return_msg=" + return_msg
				+ ", super.toString()=" + super.toString() + "]";
	}

}