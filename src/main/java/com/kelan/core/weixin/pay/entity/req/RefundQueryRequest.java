package com.kelan.core.weixin.pay.entity.req;

/**
 * 微信支付接口——退款查询接口请求实体类
 *
 */
public class RefundQueryRequest extends BaseRequest {

	// 设备号 否 String(32) 微信支付分配的终端设备号
	private String device_info;

	// 微信订单号 否 String(32) 微信的订单号，优先使用
	private String transaction_id;
	// 商户订单号 是 String(32) 商户系统内部的订单号
	private String out_trade_no;
	// 商户退款单号 否 String(32) 商户退款单号
	private String out_refund_no;
	/*
	 * 微信退款单号 否 String(28) 微信退款单号refund_id、out_refund_no、out_trade_no、 transaction_id
	 * 四个参数必填一个，如果同事存在优先级为：refund_id>out_refund_no>transaction_id>out_trade_no
	 */
	private String refund_id;

	// ------ Getter & Setter ------

	/**
	 * @return the device_info
	 */
	public String getDevice_info() {
		return device_info;
	}

	/**
	 * @param device_info the device_info to set
	 */
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	/**
	 * @return the transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * @param transaction_id the transaction_id to set
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * @return the out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * @param out_trade_no the out_trade_no to set
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @return the out_refund_no
	 */
	public String getOut_refund_no() {
		return out_refund_no;
	}

	/**
	 * @param out_refund_no the out_refund_no to set
	 */
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	/**
	 * @return the refund_id
	 */
	public String getRefund_id() {
		return refund_id;
	}

	/**
	 * @param refund_id the refund_id to set
	 */
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RefundQueryRequest [device_info=" + device_info + ", transaction_id=" + transaction_id
				+ ", out_trade_no=" + out_trade_no + ", out_refund_no=" + out_refund_no + ", refund_id=" + refund_id
				+ ", super.toString()=" + super.toString() + "]";
	}
}