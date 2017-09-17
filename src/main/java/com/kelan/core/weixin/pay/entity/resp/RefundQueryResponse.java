package com.kelan.core.weixin.pay.entity.resp;

/**
 * 微信支付接口——退款查询接口响应实体类
 * 适合微信支付接口规范V3.3.7
 *
 */
public class RefundQueryResponse extends BaseResponse {

	/*
	 * 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回
	 */
	// 设备号 否 String(32) 微信支付分配的终端设备号
	private String device_info;

	// 微信支付订单号 否 String(32) 微信支付订单号
	private String transaction_id;
	// 商户订单号 否 String(32) 商户系统内部的订单号
	private String out_trade_no;
	/*
	 * 退款笔数 是 Int 退款记录数
	 */
	private int refund_count;

	// 商户退款单号 是 String(32) 商户退款单号
	private String out_refund_no_0;
	// 微信退款单号 是 String(28) 微信退款单号
	private String refund_id_0;
	// 退款渠道 否 String(16) ORIGINAL—原路退款 BALANCE—退回到余额
	private String refund_channel_0;
	// 退款金额 是 Int 退款总金额,单位为分,可以做部分退款
	private int refund_fee_0;
	// 现金券退款金额 否 Int 现金券退款金额<=退款金额，退款金额-现金券退款金额为现金
	private int coupon_refund_fee_0;
	/*
	 * 退款状态 是 String(16) 退款状态：
	 * SUCCES—退款成功
	 * FAIL—退款失败
	 * PROCESSING—退款处理中
	 * NOTSURE—未确定，需要商户原退款单号重新发起
	 * CHANGE—转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款
	 */private String refund_status_0;

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
	 * @return the refund_count
	 */
	public int getRefund_count() {
		return refund_count;
	}

	/**
	 * @param refund_count the refund_count to set
	 */
	public void setRefund_count(int refund_count) {
		this.refund_count = refund_count;
	}

	/**
	 * @return the out_refund_no_0
	 */
	public String getOut_refund_no_0() {
		return out_refund_no_0;
	}

	/**
	 * @param out_refund_no_0 the out_refund_no_0 to set
	 */
	public void setOut_refund_no_0(String out_refund_no_0) {
		this.out_refund_no_0 = out_refund_no_0;
	}

	/**
	 * @return the refund_id_0
	 */
	public String getRefund_id_0() {
		return refund_id_0;
	}

	/**
	 * @param refund_id_0 the refund_id_0 to set
	 */
	public void setRefund_id_0(String refund_id_0) {
		this.refund_id_0 = refund_id_0;
	}

	/**
	 * @return the refund_channel_0
	 */
	public String getRefund_channel_0() {
		return refund_channel_0;
	}

	/**
	 * @param refund_channel_0 the refund_channel_0 to set
	 */
	public void setRefund_channel_0(String refund_channel_0) {
		this.refund_channel_0 = refund_channel_0;
	}

	/**
	 * @return the refund_fee_0
	 */
	public int getRefund_fee_0() {
		return refund_fee_0;
	}

	/**
	 * @param refund_fee_0 the refund_fee_0 to set
	 */
	public void setRefund_fee_0(int refund_fee_0) {
		this.refund_fee_0 = refund_fee_0;
	}

	/**
	 * @return the coupon_refund_fee_0
	 */
	public int getCoupon_refund_fee_0() {
		return coupon_refund_fee_0;
	}

	/**
	 * @param coupon_refund_fee_0 the coupon_refund_fee_0 to set
	 */
	public void setCoupon_refund_fee_0(int coupon_refund_fee_0) {
		this.coupon_refund_fee_0 = coupon_refund_fee_0;
	}

	/**
	 * @return the refund_status_0
	 */
	public String getRefund_status_0() {
		return refund_status_0;
	}

	/**
	 * @param refund_status_0 the refund_status_0 to set
	 */
	public void setRefund_status_0(String refund_status_0) {
		this.refund_status_0 = refund_status_0;
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
		return "RefundQueryResponse [device_info=" + device_info + ", transaction_id=" + transaction_id
				+ ", out_trade_no=" + out_trade_no + ", refund_count=" + refund_count + ", out_refund_no_0="
				+ out_refund_no_0 + ", refund_id_0=" + refund_id_0 + ", refund_channel_0=" + refund_channel_0
				+ ", refund_fee_0=" + refund_fee_0 + ", coupon_refund_fee_0=" + coupon_refund_fee_0
				+ ", refund_status_0=" + refund_status_0 + ", sub_mch_id=" + sub_mch_id + ", super.toString()="
				+ super.toString() + "]";
	}

}
