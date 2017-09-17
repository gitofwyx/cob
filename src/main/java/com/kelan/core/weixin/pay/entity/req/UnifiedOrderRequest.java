package com.kelan.core.weixin.pay.entity.req;

/**
 * 微信支付接口——统一支付接口请求实体类
 *
 */
public class UnifiedOrderRequest extends BaseRequest {

	// 设备号 否 String(32) 微信支付分配的终端设备号
	private String device_info;
	// 商品描述 是 String(127) 商品描述
	private String body;
	// 附加数据 否 String(127) 附加数据，原样返回
	private String attach;
	// 商户订单号 是 String(32) 商户系统内部的订单号,32个字符内、可包含字母,确保在商户系统唯一,详细说明微信公众号支付接口文档见 7.3 节第四项
	private String out_trade_no;
	// 总金额 是 Int 订单总金额，单位为分，不能带小数点
	private int total_fee;
	// 终端 IP 是 String(16) 订单生成的机器 IP
	private String spbill_create_ip;
	// 交易起始时间 否 String(14) 订 单 生 成 时 间 ， 格 式 为 yyyyMMddHHmmss，如 2009 年12 月25日 9点 10分 10 秒表示为 20091225091010。时区为 GMT+8
	// beijing。该时间取自商户服务器
	private String time_start;
	// 交易结束时间 否 String(14) 订 单 失 效 时 间 ， 格 式 为yyyyMMddHHmmss，如 2009 年12 月27日 9点 10分 10 秒表示为 20091227091010。时区为 GMT+8
	// beijing。该时间取自商户服务器
	private String time_expire;
	// 商品标记 否 String(32) 商品标记，该字段不能随便填，不使用请填空，使用说明详见第 5 节
	private String goods_tag;
	// 通知地址是 String(256) 接收微信支付成功通知
	private String notify_url;
	// 交易类型 是 String(16) JSAPI、NATIVE、APP
	private String trade_type;
	// 用户标识 否 String(128) 用户在商户 appid 下的唯一标识，trade_type 为JSAPI时，此参数必传，获取方式见表头说明。
	private String openid;
	// 商品 ID 否 String(32) 只在 trade_type 为NATIVE时需要填写。此 id 为二维码中包含的商品 ID，商户自行维护。
	private String product_id;

	// ------ Getter & Setter ------
	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UnifiedOrderRequest [device_info=" + device_info + ", body=" + body + ", attach=" + attach
				+ ", out_trade_no=" + out_trade_no + ", total_fee=" + total_fee + ", spbill_create_ip="
				+ spbill_create_ip + ", time_start=" + time_start + ", time_expire=" + time_expire + ", goods_tag="
				+ goods_tag + ", notify_url=" + notify_url + ", trade_type=" + trade_type + ", openid=" + openid
				+ ", product_id=" + product_id + ", super.toString()=" + super.toString() + "]";
	}
}
