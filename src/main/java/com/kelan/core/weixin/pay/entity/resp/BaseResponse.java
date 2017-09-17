package com.kelan.core.weixin.pay.entity.resp;

/**
 * 微信支付接口——接口相应基类
 *
 */
public class BaseResponse {
	/*
	 * 接口协议返回错误码
	 */
	// 返回状态码 是 String(16) SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看 result_code 来判断
	private String return_code;
	// 返回信息 否 String(128) 返回信息，如非空，为错误原因： 签名失败、 参数格式校验错误
	private String return_msg;

	/*
	 * 接口返回公共字段
	 * 以下字段在 return_code 为 为 SUCCESS
	 */
	// 公众账号 ID 是 String(32) 微信分配的公众账号 ID
	private String appid;
	// 商户号 是 String(32) 微信支付分配的商户号
	private String mch_id;
	// 随机字符串 是 String(32) 随机字符串，不长于 32 位
	private String nonce_str;
	// 签名 是 String(32) 签名,详细签名方法见3.2节
	private String sign;

	// 业务结果 是 String(16) SUCCESS/FAIL
	private String result_code;
	// 错误代码 否 String(32) 错误码见第 6 节
	private String err_code;
	// 错误代码描述 否 String(128) 结果信息描述
	private String err_code_des;

	// ------ Getter & Setter ------
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseResponse [return_code=" + return_code + ", return_msg=" + return_msg + ", appid=" + appid
				+ ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", sign=" + sign + ", result_code=" + result_code
				+ ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", super.toString()="
				+ super.toString() + "]";
	}

}
