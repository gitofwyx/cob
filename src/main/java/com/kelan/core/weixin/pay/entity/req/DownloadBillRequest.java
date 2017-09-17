package com.kelan.core.weixin.pay.entity.req;

/**
 * 微信支付接口——对账单接口请求实体类
 *
 */
public class DownloadBillRequest extends BaseRequest {

	// 设备号 否 String(32) 微信支付分配的终端设备号
	private String device_info;

	// 对账单日起 是 String(8) 下载对账单的日期，格式：20140603
	private String bill_date;
	/*
	 * 账单类型 否 String(8)
	 * ALL，返回当日所有订单信息 【 默认值】
	 * SUCCESS，返回当日成功支付的订单
	 * REFUND，返回当日退款订单
	 */
	private String bill_type;

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
	 * @return the bill_date
	 */
	public String getBill_date() {
		return bill_date;
	}

	/**
	 * @param bill_date the bill_date to set
	 */
	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
	}

	/**
	 * @return the bill_type
	 */
	public String getBill_type() {
		return bill_type;
	}

	/**
	 * @param bill_type the bill_type to set
	 */
	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DownloadBillRequest [device_info=" + device_info + ", bill_date=" + bill_date + ", bill_type="
				+ bill_type + ", super.toString()=" + super.toString() + "]";
	}

}