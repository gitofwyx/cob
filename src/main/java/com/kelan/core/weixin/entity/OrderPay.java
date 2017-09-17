package com.kelan.core.weixin.entity;

import com.kelan.core.entity.BaseInfoEntity;

/**
 * @Title: 订单支付信息
 * 
 * @author mj
 *
 */
public class OrderPay extends BaseInfoEntity {

	private String orderId; // 订单ID
	private String platformId; // 平台信息ID
	private String reqOrNotifyFlag; // 请求或接口通知
	private String reqOrNotifyUrl; // 请求或通知URL
	private byte[] reqOrNotifyParams; // 请求或通知参数
	private byte[] backParams; // 返回参数
	private String notifyFlag; // 异常通知标识
	private String payFlag; // 支付状态标识
	private String remark; // 备注
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getReqOrNotifyFlag() {
		return reqOrNotifyFlag;
	}

	public void setReqOrNotifyFlag(String reqOrNotifyFlag) {
		this.reqOrNotifyFlag = reqOrNotifyFlag;
	}

	public String getReqOrNotifyUrl() {
		return reqOrNotifyUrl;
	}

	public void setReqOrNotifyUrl(String reqOrNotifyUrl) {
		this.reqOrNotifyUrl = reqOrNotifyUrl;
	}

	public byte[] getReqOrNotifyParams() {
		return reqOrNotifyParams;
	}

	public void setReqOrNotifyParams(byte[] reqOrNotifyParams) {
		this.reqOrNotifyParams = reqOrNotifyParams;
	}

	public byte[] getBackParams() {
		return backParams;
	}

	public void setBackParams(byte[] backParams) {
		this.backParams = backParams;
	}

	public String getNotifyFlag() {
		return notifyFlag;
	}

	public void setNotifyFlag(String notifyFlag) {
		this.notifyFlag = notifyFlag;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
