package com.kelan.core.weixin.pay.entity.req;

/**
 * 微信支付接口——订单查询接口请求实体类
 *
 */
public class OrderQueryRequest extends BaseRequest {

	// 微信订单号 否 String(32) 微信的订单号，优先使用
	private String transaction_id;
	// 商户订单号 是 String(32) 商户系统内部的订单号, transaction_id、 out_trade_no 二 选一，如果同时存在优先级： transaction_id> out_trade_no
	private String out_trade_no;

	// ------ Getter & Setter ------

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderQueryRequest [transaction_id=" + transaction_id + ", out_trade_no=" + out_trade_no
				+ ", super.toString()=" + super.toString() + "]";
	}

}
