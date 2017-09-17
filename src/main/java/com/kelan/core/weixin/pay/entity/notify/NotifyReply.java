package com.kelan.core.weixin.pay.entity.notify;

/**
 * 微信支付接口——支付结果通知接口 响应 报文实体类
 *
 */
public class NotifyReply {

	// 返回状态码 是 String(16) SUCCESS/FAIL SUCCESS 表示商户接收通知成功并校验成功
	private String return_code;
	// 返回信息 否 String(128) 返回信息，如非空，为错误原因： 签名失败、 参数格式校验错误
	private String return_msg;

	// ------ Getter & Setter ------

	/**
	 * @return the return_code
	 */
	public String getReturn_code() {
		return return_code;
	}

	/**
	 * @param return_code the return_code to set
	 */
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	/**
	 * @return the return_msg
	 */
	public String getReturn_msg() {
		return return_msg;
	}

	/**
	 * @param return_msg the return_msg to set
	 */
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
		return "NotifyReply [return_code=" + return_code + ", return_msg=" + return_msg + ", super.toString()="
				+ super.toString() + "]";
	}
}
