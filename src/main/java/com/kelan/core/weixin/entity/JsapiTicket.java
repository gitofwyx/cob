package com.kelan.core.weixin.entity;

/**
 * 调用微信JS接口的临时票据
 * 
 */
public class JsapiTicket {
	// 调用微信JS接口的临时票据
	private String ticket;
	// 凭证有效期，单位：秒
	private int expiresIn;

	private String errcode;
	private String errmsg;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}