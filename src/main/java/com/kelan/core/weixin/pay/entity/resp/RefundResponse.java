package com.kelan.core.weixin.pay.entity.resp;

/**
 * 微信支付接口——退款申请接口响应实体类
 * 适合微信支付接口规范V3.3.7
 *
 */
public class RefundResponse extends BaseResponse {

	/*
	 * 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回
	 */
	// 设备号 否 String(32) 微信支付分配的终端设备号
	private String device_info;

	// 微信支付订单号 否 String(32) 微信支付订单号
	private String transaction_id;
	// 商户订单号 否 String(32) 商户系统内部的订单号
	private String out_trade_no;
	// 商户退款单号 是 String(32) 商户退款单号
	private String out_refund_no;
	// 微信退款单号 是 String(28) 微信退款单号
	private String refund_id;
	// 退款渠道 否 String(16) ORIGINAL—原路退款 BALANCE—退回到余额
	private String refund_channel;
	// 退款金额 是 Int 退款总金额,单位为分,可以做部分退款
	private int refund_fee;
	// 现金券退款金额 否 Int 现金券退款金额<=退款金额，退款金额-现金券退款金额为现金
	private int coupon_refund_fee;

	/*
	 * 跟接口文档不一致的参数【接口联调报错发现需要新增这个参数】，以后如微信接口再做调整，本地系统接口需实时同步更新...
	 * 同步报文：(调试时间：2014-11-19 17:40)
	 * <xml>
	 * <appid><![CDATA[wxef27c3a3a2bcad93]]></appid>
	 * <err_code><![CDATA[REFUNDNOTEXIST]]></err_code>
	 * <err_code_des><![CDATA[not exist]]></err_code_des>
	 * <mch_id><![CDATA[10028414]]></mch_id>
	 * <nonce_str><![CDATA[BFpdPMSBauq2oH1W]]></nonce_str>
	 * <result_code><![CDATA[FAIL]]></result_code>
	 * <return_code><![CDATA[SUCCESS]]></return_code>
	 * <return_msg><![CDATA[OK]]></return_msg>
	 * <sign><![CDATA[3DC033A9E42E5DC771E0CF1DA69F4ED3]]></sign>
	 * <sub_mch_id><![CDATA[]]>
	 * </sub_mch_id>
	 * </xml>
	 */
	private String sub_mch_id;

	// ------ Getter & Setter ------

	public String getDevice_info() {
		return device_info;
	}

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

	/**
	 * @return the refund_channel
	 */
	public String getRefund_channel() {
		return refund_channel;
	}

	/**
	 * @param refund_channel the refund_channel to set
	 */
	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
	}

	/**
	 * @return the refund_fee
	 */
	public int getRefund_fee() {
		return refund_fee;
	}

	/**
	 * @param refund_fee the refund_fee to set
	 */
	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

	/**
	 * @return the coupon_refund_fee
	 */
	public int getCoupon_refund_fee() {
		return coupon_refund_fee;
	}

	/**
	 * @param coupon_refund_fee the coupon_refund_fee to set
	 */
	public void setCoupon_refund_fee(int coupon_refund_fee) {
		this.coupon_refund_fee = coupon_refund_fee;
	}

	/**
	 * @return the sub_mch_id
	 */
	public String getSub_mch_id() {
		return sub_mch_id;
	}

	/**
	 * @param sub_mch_id the sub_mch_id to set
	 */
	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RefundResponse [device_info=" + device_info + ", transaction_id=" + transaction_id + ", out_trade_no="
				+ out_trade_no + ", out_refund_no=" + out_refund_no + ", refund_id=" + refund_id + ", refund_channel="
				+ refund_channel + ", refund_fee=" + refund_fee + ", coupon_refund_fee=" + coupon_refund_fee
				+ ", sub_mch_id=" + sub_mch_id + ", super.toString()=" + super.toString() + "]";
	}

}