package com.kelan.core.weixin.pay.entity.resp;

/**
 * 微信支付接口——关闭订单接口响应实体类
 *
 */
public class CloseOrderResponse extends BaseResponse {

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
		return "CloseOrderResponse [sub_mch_id=" + sub_mch_id + ", super.toString()=" + super.toString() + "]";
	}

}