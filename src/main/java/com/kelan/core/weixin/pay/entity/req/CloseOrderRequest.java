package com.kelan.core.weixin.pay.entity.req;

/**
 * 微信支付接口——关闭订单接口请求实体类
 *
 */
public class CloseOrderRequest extends BaseRequest {

	// 商户订单号 是 String(32) 商户系统内部的订单号
	private String out_trade_no;

	// ------ Getter & Setter ------

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CloseOrderRequest [out_trade_no=" + out_trade_no + ", toString()=" + super.toString() + "]";
	}

}