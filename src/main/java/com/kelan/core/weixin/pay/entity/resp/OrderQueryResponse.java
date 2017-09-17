package com.kelan.core.weixin.pay.entity.resp;

/**
 * 微信支付接口——订单查询接口响应实体类
 * 适合微信支付接口规范V3.3.7
 *
 */
public class OrderQueryResponse extends BaseResponse {

	/*
	 * 交易状态 是 String(32)
	 * SUCCESS—支付成功
	 * REFUND—转入退款
	 * NOTPAY—未支付
	 * CLOSED—已关闭
	 * REVOKED—已撤销
	 * USERPAYING--用户支付中
	 * NOPAY--未支付(输入密码或确认支付超时)
	 * PAYERROR--支付失败(其他原因，如银行返回失败)
	 */
	private String trade_state;

	/*
	 * 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回
	 */
	// 设备号 否 String(32) 微信支付分配的终端设备号
	private String device_info;
	// 用户标识 是 String(128) 用户在商户 appid 下的唯一标识
	private String openid;
	// 是否关注公众账号 是 String(1) 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	private String is_subscribe;
	// 交易类型 是 String(16) JSAPI、NATIVE、MICROPAY、APP
	private String trade_type;
	// 付款银行 是 String(16) 银行类型，采用字符串类型的银行标识
	private String bank_type;
	// 总金额 是 Int 订单总金额，单位为分
	private int total_fee;
	// 现金券金额 否 Int 现金券支付金额<=订单总金额，订单总金额-现金券金额为现金支付金额
	private int coupon_fee;
	// 货币种类 否 String(8) 货币类型，符合 ISO 4217 标准的三位字母代码，默认人民币：CNY
	private String fee_type;
	// 微信支付订单号 否 String(32) 微信支付订单号
	private String transaction_id;
	// 商户订单号 否 String(32) 商户系统的订单号，与请求一致。
	private String out_trade_no;
	// 商家数据包 否 String(128) 商家数据包，原样返回
	private String attach;
	// 支付完成时间 是 String(14) 支 付 完 成 时 间 ， 格 式 为yyyyMMddhhmmss，如 2009 年12 月27日 9点 10分 10 秒表示为
	// 20091227091010。时区微信公众号支付接口文档为 GMT+8 beijing。该时间取自微信支付服务器
	private String time_end;

	/*
	 * 跟接口文档不一致的参数【接口联调报错发现需要新增这个参数】，以后如微信接口再做调整，本地系统接口需实时同步更新...
	 * 同步报文：(调试时间：2014-11-19 11:28)
	 * <xml>
	 * <return_code><![CDATA[SUCCESS]]></return_code>
	 * <return_msg><![CDATA[OK]]></return_msg>
	 * <appid><![CDATA[wxef27c3a3a2bcad93]]></appid>
	 * <mch_id><![CDATA[10028414]]></mch_id>
	 * <sub_mch_id><![CDATA[]]></sub_mch_id>
	 * <nonce_str><![CDATA[REu7SLMhvlDHS4dk]]></nonce_str>
	 * <sign><![CDATA[EF2E8B5786ADBE02E079D6820018E438]]></sign>
	 * <result_code><![CDATA[SUCCESS]]></result_code>
	 * <openid><![CDATA[oKSHAt9EXdNH3GgR2X6XK-RWZF-0]]></openid>
	 * <is_subscribe><![CDATA[Y]]></is_subscribe>
	 * <trade_type><![CDATA[JSAPI]]></trade_type>
	 * <bank_type><![CDATA[ICBC_DEBIT]]></bank_type>
	 * <total_fee>1</total_fee>
	 * <fee_type><![CDATA[CNY]]></fee_type>
	 * <transaction_id><![CDATA[1001410382201411190005996872]]></transaction_id>
	 * <out_trade_no><![CDATA[1416370756370000]]></out_trade_no>
	 * <attach><![CDATA[attch1]]></attach>
	 * <time_end><![CDATA[20141119121929]]></time_end>
	 * <trade_state><![CDATA[SUCCESS]]></trade_state>
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
	 * @return the trade_state
	 */
	public String getTrade_state() {
		return trade_state;
	}

	/**
	 * @param trade_state the trade_state to set
	 */
	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return the is_subscribe
	 */
	public String getIs_subscribe() {
		return is_subscribe;
	}

	/**
	 * @param is_subscribe the is_subscribe to set
	 */
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	/**
	 * @return the trade_type
	 */
	public String getTrade_type() {
		return trade_type;
	}

	/**
	 * @param trade_type the trade_type to set
	 */
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	/**
	 * @return the bank_type
	 */
	public String getBank_type() {
		return bank_type;
	}

	/**
	 * @param bank_type the bank_type to set
	 */
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	/**
	 * @return the total_fee
	 */
	public int getTotal_fee() {
		return total_fee;
	}

	/**
	 * @param total_fee the total_fee to set
	 */
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * @return the coupon_fee
	 */
	public int getCoupon_fee() {
		return coupon_fee;
	}

	/**
	 * @param coupon_fee the coupon_fee to set
	 */
	public void setCoupon_fee(int coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	/**
	 * @return the fee_type
	 */
	public String getFee_type() {
		return fee_type;
	}

	/**
	 * @param fee_type the fee_type to set
	 */
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
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
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the time_end
	 */
	public String getTime_end() {
		return time_end;
	}

	/**
	 * @param time_end the time_end to set
	 */
	public void setTime_end(String time_end) {
		this.time_end = time_end;
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
		return "OrderQueryResponse [trade_state=" + trade_state + ", device_info=" + device_info + ", openid=" + openid
				+ ", is_subscribe=" + is_subscribe + ", trade_type=" + trade_type + ", bank_type=" + bank_type
				+ ", total_fee=" + total_fee + ", coupon_fee=" + coupon_fee + ", fee_type=" + fee_type
				+ ", transaction_id=" + transaction_id + ", out_trade_no=" + out_trade_no + ", attach=" + attach
				+ ", time_end=" + time_end + ", super.toString()=" + super.toString() + "]";
	}

}
