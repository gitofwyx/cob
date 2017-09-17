package com.kelan.core.weixin.util;

import com.kelan.core.util.BeanUtil;
import com.kelan.core.util.DecriptUtil;
import com.kelan.core.util.MD5Util;
import com.kelan.core.weixin.entity.Location;
import com.kelan.core.weixin.pay.entity.notify.NotifyReceive;
import com.kelan.core.weixin.pay.entity.notify.NotifyReply;
import com.kelan.core.weixin.pay.entity.req.*;
import com.kelan.core.weixin.pay.entity.resp.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.*;
import java.util.*;

/**
 * 微信支付工具类
 * 
 * @author wangdr
 * @date 2014-11-18
 */
public class WXPayUtil {
	/**
     * 当前类自己的logger
     */
    private static Logger logger = Logger.getLogger(WXPayUtil.class);

	// 微信分配的公众账号 ID
	public final static String appId = "wx320aa887baaadfb7";
	// public final static String appId = "wxef27c3a3a2bcad93";
	// 微信支付分配的商户号
	public final static String mchId = "1311717601";
	// public final static String mchId = "10028414";
	// 商户支付密钥
	public final static String key = "lZLiFGEXAiTl1wBaVNWluj3xvsbLUt2W";
	// public final static String key = "17815c067183150190b09f3488d5dfc7";
	// 支付结果异步通知地址
	public final static String notifyURL = "http://khyd.duomi.me/wxpayIface/notify";
	
	public final static String domailURL = "http://khyd.duomi.me/";
	// 商户证书存放路径
	// public final static String CALIB_PATH = "E:/CALib/WeiXin_cert/@mchId/apiclient_cert.p12";
	public final static String CALIB_PATH = "/data/appServer/DuoMi-WeiZhan/CALib/WeiXin_cert/@mchId/apiclient_cert.p12";

	// 统一支付接口地址
	public final static String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 订单查询接口地址
	public final static String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 关闭订单接口地址
	public final static String CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	// 退款接口地址
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 退款查询接口地址
	public final static String REFUNDQUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	// 对账单接口地址
	public final static String DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputXML 提交的XML格式数据
	 * @return String XML格式的字符串
	 */
	public static String HttpsRequest4XML(String requestUrl, String requestMethod, String outputXML) {
		HttpsURLConnection httpsUrlConn = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		String result = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			httpsUrlConn = (HttpsURLConnection) url.openConnection();
			httpsUrlConn.setSSLSocketFactory(ssf);

			httpsUrlConn.setDoOutput(true);
			httpsUrlConn.setDoInput(true);
			httpsUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpsUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputXML) {
				OutputStream outputStream = httpsUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputXML.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			inputStream = httpsUrlConn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str).append("\n");
			}

			result = buffer.toString();
		} catch (ConnectException ce) {
			logger.error(ce);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			// 释放资源
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}

			if (null != inputStreamReader) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}

			if (null != inputStream) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					logger.error(e);
				}
			}

			if (null != httpsUrlConn) {
				httpsUrlConn.disconnect();
			}
		}
		return result;
	}

	/**
	 * 发起安全的双向认证https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param calibPath 商户证书存放路径
	 * @param mchId 商户号
	 * @param outputXML 提交的XML格式数据
	 * @return String XML格式的字符串
	 */
	public static String HttpsSecureRequest4XML(String requestUrl, String calibPath, String mchId, String outputXML) {
		KeyStore keyStore = null;
		FileInputStream instream = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			instream = new FileInputStream(new File(calibPath.replace("@mchId", mchId)));
			if (null != keyStore) {
				keyStore.load(instream, mchId.toCharArray());
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				if (null != instream) {
					instream.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}

		String responseBody = "";
		CloseableHttpClient httpClient = null;
		try {
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = null;
			try {
				sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
			} catch (KeyManagementException e1) {
				logger.error(e1);
			} catch (UnrecoverableKeyException e2) {
				logger.error(e2);
			} catch (NoSuchAlgorithmException e3) {
				logger.error(e3);
			} catch (KeyStoreException e4) {
				logger.error(e4);
			}
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

			HttpPost httpPost = new HttpPost(requestUrl);
			try {
				StringEntity stringEntity = new StringEntity(outputXML, "utf-8");
				httpPost.setHeader("charset", "utf-8");
				httpPost.setEntity(stringEntity);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity entity = httpResponse.getEntity();
				responseBody = EntityUtils.toString(entity, "UTF-8").trim();// 解决相应报文中文乱码问题

				EntityUtils.consume(entity);
			} catch (Exception e) {
				logger.error(e);
			} finally {
				httpPost.abort();
			}
		} finally {
			if (null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}

		return responseBody;
	}

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String parseXml(HttpServletRequest request) {

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		String result = null;
		StringBuffer buffer = new StringBuffer();

		try {
			// 从request中取得输入流
			inputStream = request.getInputStream();
			// 将返回的输入流转换成字符串
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			result = buffer.toString();
		} catch (ConnectException ce) {
			logger.error(ce);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			// 释放资源
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}

			if (null != inputStreamReader) {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}

			if (null != inputStream) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					logger.error(e);
				}
			}

		}

		return result;
	}

	/**
	 * 统一支付接口请求参数对象转换成xml
	 * 
	 * @param unifiedOrderRequest 统一支付接口请求参数对象
	 * @return xml
	 */
	public static String unifiedOrderRequestToXml(UnifiedOrderRequest unifiedOrderRequest) {
		xstream.alias("xml", unifiedOrderRequest.getClass());
		return xstream.toXML(unifiedOrderRequest).replace("__", "_");
	}

	/**
	 * xml转换成统一支付接口相应结果对象
	 * 
	 * @param String responseXML 统一支付接口相应XML格式字符串数据
	 * @return UnifiedOrderResponse
	 */
	public static UnifiedOrderResponse xmlToUnifiedOrderResponse(String responseXML) {
		XStream xs = getXStreamInstance();
		xs.alias("xml", UnifiedOrderResponse.class);
		return (UnifiedOrderResponse) xs.fromXML(responseXML);
	}

	/**
	 * xml转换成支付结果通知接口 接收请求 报文对象
	 * 
	 * @param String notifyReceiveXML 支付结果通知接口 接收请求 报文XML格式字符串数据
	 * @return NotifyReceive
	 */
	public static NotifyReceive xmlToNotifyReceive(String notifyReceiveXML) {
		XStream xs = getXStreamInstance();
		xs.alias("xml", NotifyReceive.class);
		return (NotifyReceive) xs.fromXML(notifyReceiveXML);
	}
	
	/**
	 * xml转换成地理位置消息通知接口 接收请求 报文对象
	 * 
	 * @param String locationXML 地理位置消息通知接口 接收请求 报文XML格式字符串数据
	 * @return Location
	 */
	public static Location xmlToLocation(String locationXML) {
		XStream xs = getXStreamInstance();
		xs.alias("xml", NotifyReceive.class);
		return (Location) xs.fromXML(locationXML);
	}

	/**
	 * 支付结果通知接口 响应 对象 报文转换成xml
	 * 
	 * @param notifyReply 支付结果通知接口 响应 对象 报文对象
	 * @return xml
	 */
	public static String notifyReplyToXml(NotifyReply notifyReply) {
		xstream.alias("xml", notifyReply.getClass());
		return xstream.toXML(notifyReply).replace("__", "_");
	}

	/**
	 * 订单查询接口请求参数对象转换成xml
	 * 
	 * @param orderQueryRequest 订单查询接口请求参数对象
	 * @return xml
	 */
	public static String orderQueryRequestToXml(OrderQueryRequest orderQueryRequest) {
		xstream.alias("xml", orderQueryRequest.getClass());
		return xstream.toXML(orderQueryRequest).replace("__", "_");
	}

	/**
	 * xml转换成订单查询接口相应结果对象
	 * 
	 * @param String responseXML 订单查询接口相应XML格式字符串数据
	 * @return OrderQueryResponse
	 */
	public static OrderQueryResponse xmlToOrderQueryResponse(String responseXML) {
		XStream xs = getXStreamInstance();
		xs.alias("xml", OrderQueryResponse.class);
		return (OrderQueryResponse) xs.fromXML(responseXML);
	}

	/**
	 * 关闭订单接口请求参数对象转换成xml
	 * 
	 * @param closeOrderRequest 关闭订单接口请求参数对象
	 * @return xml
	 */
	public static String closeOrderRequestToXml(CloseOrderRequest closeOrderRequest) {
		xstream.alias("xml", closeOrderRequest.getClass());
		return xstream.toXML(closeOrderRequest).replace("__", "_");
	}

	/**
	 * xml转换成关闭订单接口相应结果对象
	 * 
	 * @param String responseXML 关闭订单接口相应XML格式字符串数据
	 * @return CloseOrderResponse
	 */
	public static CloseOrderResponse xmlToCloseOrderResponse(String responseXML) {
		XStream xs = getXStreamInstance();
		xs.alias("xml", CloseOrderResponse.class);
		return (CloseOrderResponse) xs.fromXML(responseXML);
	}

	/**
	 * 退款申请接口请求参数对象转换成xml
	 * 
	 * @param refundRequest 退款申请接口请求参数对象
	 * @return xml
	 */
	public static String refundRequestToXml(RefundRequest refundRequest) {
		xstream.alias("xml", refundRequest.getClass());
		return xstream.toXML(refundRequest).replace("__", "_");
	}

	/**
	 * xml转换成退款申请接口相应结果对象
	 * 
	 * @param String responseXML 退款申请接口相应XML格式字符串数据
	 * @return RefundResponse
	 */
	public static RefundResponse xmlToRefundResponse(String responseXML) {
		XStream xs = getXStreamInstance();
		xs.alias("xml", RefundResponse.class);
		return (RefundResponse) xs.fromXML(responseXML);
	}

	/**
	 * 退款查询接口请求参数对象转换成xml
	 * 
	 * @param refundQueryRequest 退款查询接口请求参数对象
	 * @return xml
	 */
	public static String refundQueryRequestToXml(RefundQueryRequest refundQueryRequest) {
		xstream.alias("xml", refundQueryRequest.getClass());
		return xstream.toXML(refundQueryRequest).replace("__", "_");
	}

	/**
	 * xml转换成退款查询接口相应结果对象
	 * 
	 * @param String responseXML 退款查询接口相应XML格式字符串数据
	 * @return RefundQueryResponse
	 */
	public static RefundQueryResponse xmlToRefundQueryResponse(String responseXML) {
		XStream xs = getXStreamInstance();
		xs.alias("xml", RefundQueryResponse.class);
		return (RefundQueryResponse) xs.fromXML(responseXML);
	}

	/**
	 * 对账单接口请求参数对象转换成xml
	 * 
	 * @param refundRequest 对账单接口请求参数对象
	 * @return xml
	 */
	public static String downloadBillRequestToXml(DownloadBillRequest downloadBillRequest) {
		xstream.alias("xml", downloadBillRequest.getClass());
		return xstream.toXML(downloadBillRequest).replace("__", "_");
	}

	/**
	 * xml转换成对账单接口相应结果对象
	 * 
	 * @param String responseXML 对账单接口相应XML格式字符串数据
	 * @return DownloadBillResponse
	 */
	public static DownloadBillResponse xmlToDownloadBillResponse(String responseXML) {
		XStream xs = getXStreamInstance();
		xs.alias("xml", DownloadBillResponse.class);
		return (DownloadBillResponse) xs.fromXML(responseXML);
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String doSign(Object object, String key) {
		Map<String, Object> parameterMap = BeanUtil.transBean2TreeMap(object);
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String, Object>> es = parameterMap.entrySet();
		Iterator<Map.Entry<String, Object>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if ((null != v) && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Util.getMD5(sb.toString().getBytes()).toUpperCase();
		return sign;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String doSign(SortedMap<String, Object> parameterMap, String key) {
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String, Object>> es = parameterMap.entrySet();
		Iterator<Map.Entry<String, Object>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if ((null != v) && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Util.getMD5(sb.toString().getBytes()).toUpperCase();
		return sign;
	}

	/**
	 * 创建SHA1加密串,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String doSign4SHA1(SortedMap<String, Object> parameterMap) {
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String, Object>> es = parameterMap.entrySet();
		Iterator<Map.Entry<String, Object>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if ((null != v) && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String sign = DecriptUtil.SHA1(sb.substring(0, (sb.length() - 1)).toString());
		return sign;
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 单例模式
	 * 
	 * @date 2014-11-18
	 */
	private static XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")) {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("rawtypes")
				@Override
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2014-11-18
	 */

	private static XStream getXStreamInstance() {
		XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")) {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					// 对所有xml节点的转换都增加CDATA标记
					boolean cdata = true;

					@SuppressWarnings("rawtypes")
					@Override
					public void startNode(String name, Class clazz) {
						super.startNode(name, clazz);
					}

					@Override
					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
				};
			}
		});
		return xstream;
	}

	public static void main(String args[]) {
		/**
		 * 统一支付接口 测试
		 */
		/*String appId = "wxc78c381105b7ff7c";
		String mchId = "1225881902";
		String key = "LFhHRKUZfcLW7PFnfie9izZ6m9EcR1nG";
		UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
		unifiedOrderRequest.setAppid(appId);
		unifiedOrderRequest.setAttach(">>>1225881902" + ">>>wxc78c381105b7ff7c" + ">>>1541");
		unifiedOrderRequest.setBody("JSAPI 支付测试");
		unifiedOrderRequest.setMch_id(mchId);
		unifiedOrderRequest.setNonce_str(new RandomString().getRandomString(32, "ilu"));
		unifiedOrderRequest.setNotify_url(notifyURL);
		unifiedOrderRequest.setOut_trade_no(IdGenerator.genOrderId16());
		unifiedOrderRequest.setSpbill_create_ip("127.0.0.1");
		unifiedOrderRequest.setTotal_fee(1);
		unifiedOrderRequest.setTrade_type("JSAPI");
		unifiedOrderRequest.setProduct_id(IdGenerator.genOrdId14());
		unifiedOrderRequest.setOpenid("oIHjSsgFv_zInU4NE90slyGuLaj0");
		// 统一支付签名
		String sign = WXPayUtil.doSign(unifiedOrderRequest, key);
		unifiedOrderRequest.setSign(sign);
		// 转换统一支付请求报文
		String requestXML = WXPayUtil.unifiedOrderRequestToXml(unifiedOrderRequest);
		System.out.println(">>>requestXML:\n" + requestXML);
		// 提交统一支付接口请求
		String responseXML = WXPayUtil.HttpsRequest4XML(WXPayUtil.UNIFIEDORDER_URL, "POST", requestXML);
		System.out.println(">>>responseXML:\n" + responseXML);*/
		
		String responseXML = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"+
"<return_msg><![CDATA[OK]]></return_msg>"+
"<appid><![CDATA[wx320aa887baaadfb7]]></appid>"+
"<mch_id><![CDATA[1311717601]]></mch_id>"+
"<nonce_str><![CDATA[O5My9gqKPujyj2OX]]></nonce_str>"+
"<sign><![CDATA[895104F5FD99CBBE7968D0E3A2DBCF0E]]></sign>"+
"<result_code><![CDATA[SUCCESS]]></result_code>"+
"<prepay_id><![CDATA[wx20160201091455a608e520780457987404]]></prepay_id>"+
"<trade_type><![CDATA[JSAPI]]></trade_type>"+
"</xml>";
		

		if (StringUtils.isNotEmpty(responseXML)) {
			// 转换统一支付接口响应报文为对应的Java对象
			UnifiedOrderResponse unifiedOrderResponse = WXPayUtil.xmlToUnifiedOrderResponse(responseXML);
			if ((null != unifiedOrderResponse) && "SUCCESS".equals(unifiedOrderResponse.getReturn_code())
					&& "SUCCESS".equals(unifiedOrderResponse.getResult_code())) {
				/*
				 * 统一支付（下预支付订单）成功，根据返回的"返回预支付订单号"封装H5页面JSAPI提交需要的数据
				 */
				SortedMap<String, Object> JSAPIParameters = new TreeMap<>();
				// 公众号 id 是 String(16) 商户注册具有支付权限的公众号成功后即可获得
				JSAPIParameters.put("appId", appId);
				// 时间戳 是 String(32) 商户生成，从 1970 年 1 月 1日 00：00：00 至今的秒数，即当前的时间，且最终需要转换为字符串形式；
				JSAPIParameters.put("timeStamp", (new Date()).getTime());
				// 随机字符串 是 String(32) 商户生成的随机字符串；
				JSAPIParameters.put("nonceStr", new RandomString().getRandomString(32, "ilu"));
				// 订单详情扩展字符串 是 String(128) 统 一 支 付 接 口 返 回 的prepay_id 参数值， 提交格式如：prepay_id=***
				JSAPIParameters.put("package", "prepay_id=" + unifiedOrderResponse.getPrepay_id());
				// 签名方式 是 String(32) 按照文档中所示填入 MD5；
				JSAPIParameters.put("signType", "MD5");
				// 签名 是 String(64) 签名方式与其他接口中 sign的生成方式一致， 详见第 3.2节
				JSAPIParameters.put("paySign", WXPayUtil.doSign(JSAPIParameters, key));
			}
		}

		/**
		 * 订单查询接口 测试
		 */
		// OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
		// orderQueryRequest.setAppid(appId);
		// orderQueryRequest.setMch_id(mchId);
		// // orderQueryRequest.setTransaction_id("1001410382201411190005989758");
		// orderQueryRequest.setOut_trade_no("1416385992370002");
		// orderQueryRequest.setNonce_str(new RandomString().getRandomString(32, "ilu"));
		// // 订单查询签名
		// String sign = WXPayUtil.doSign(orderQueryRequest, key);
		// orderQueryRequest.setSign(sign);
		// // 转换订单查询请求报文
		// String requestXML = WXPayUtil.orderQueryRequestToXml(orderQueryRequest);
		// System.out.println(">>>requestXML:\n" + requestXML);
		// // 提交订单查询接口请求
		// String responseXML = WXPayUtil.HttpsRequest4XML(WXPayUtil.ORDERQUERY_URL, "POST", requestXML);
		// System.out.println(">>>responseXML:\n" + responseXML);
		//
		// if (StringUtils.isNotEmpty(responseXML)) {
		// // 转换订单查询接口响应报文为对应的Java对象
		// OrderQueryResponse orderQueryResponse = WXPayUtil.xmlToOrderQueryResponse(responseXML);
		// System.out.println(">>>" + orderQueryResponse);
		// if ((null != orderQueryResponse) && "SUCCESS".equals(orderQueryResponse.getReturn_code())
		// && "SUCCESS".equals(orderQueryResponse.getResult_code())) {
		//
		// System.out.println(">>>【订单查询成功】<<<");
		// }
		// }

		/**
		 * 关闭订单接口 测试
		 */
		// CloseOrderRequest closeOrderRequest = new CloseOrderRequest();
		// closeOrderRequest.setAppid(appId);
		// closeOrderRequest.setMch_id(mchId);
		// closeOrderRequest.setOut_trade_no("1416386318370006");
		// closeOrderRequest.setNonce_str(new RandomString().getRandomString(32, "ilu"));
		// // 关闭订单签名
		// String sign = WXPayUtil.doSign(closeOrderRequest, key);
		// closeOrderRequest.setSign(sign);
		// // 转换关闭订单请求报文
		// String requestXML = WXPayUtil.closeOrderRequestToXml(closeOrderRequest);
		// System.out.println(">>>requestXML:\n" + requestXML);
		// // 提交关闭订单接口请求
		// String responseXML = WXPayUtil.HttpsRequest4XML(WXPayUtil.CLOSEORDER_URL, "POST", requestXML);
		// System.out.println(">>>responseXML:\n" + responseXML);
		//
		// if (StringUtils.isNotEmpty(responseXML)) {
		// // 转换关闭订单接口响应报文为对应的Java对象
		// CloseOrderResponse closeOrderResponse = WXPayUtil.xmlToCloseOrderResponse(responseXML);
		// System.out.println(">>>" + closeOrderResponse);
		// if ((null != closeOrderResponse) && "SUCCESS".equals(closeOrderResponse.getReturn_code())
		// && "SUCCESS".equals(closeOrderResponse.getResult_code())) {
		//
		// System.out.println(">>>【关闭订单成功】<<<");
		// }
		// }

		/**
		 * 退款申请 测试
		 */
		// RefundRequest refundRequest = new RefundRequest();
		// refundRequest.setAppid(appId);
		// refundRequest.setMch_id(mchId);
		// refundRequest.setNonce_str(new RandomString().getRandomString(32, "ilu"));
		// // refundQueryRequest.setTransaction_id("1001410382201411190005996872");
		// refundRequest.setOut_trade_no("1421717958370002");
		// refundRequest.setOut_refund_no(IdGenerator.genOrderId16());
		// refundRequest.setTotal_fee(1);
		// refundRequest.setRefund_fee(1);
		// refundRequest.setOp_user_id("wdr");
		// // 退款查询签名
		// String sign = WXPayUtil.doSign(refundRequest, key);
		// refundRequest.setSign(sign);
		// // 转换退款申请请求报文
		// String requestXML = WXPayUtil.refundRequestToXml(refundRequest);
		// System.out.println(">>>requestXML:\n" + requestXML);
		// // 提交退款申请接口请求
		// String responseXML = WXPayUtil.HttpsSecureRequest4XML(WXPayUtil.REFUND_URL, WXPayUtil.CALIB_PATH,
		// WXPayUtil.mchId, requestXML);
		// System.out.println(">>>responseXML:\n" + responseXML);
		//
		// if (StringUtils.isNotEmpty(responseXML)) {
		// // 转换退款申请接口响应报文为对应的Java对象
		// RefundResponse refundResponse = WXPayUtil.xmlToRefundResponse(responseXML);
		// System.out.println(">>>" + refundResponse);
		// if ((null != refundResponse) && "SUCCESS".equals(refundResponse.getReturn_code())
		// && "SUCCESS".equals(refundResponse.getResult_code())) {
		//
		// System.out.println(">>>【退款申请成功】<<<");
		// }
		// }

		/**
		 * 退款查询 测试
		 */
		// RefundQueryRequest refundQueryRequest = new RefundQueryRequest();
		// refundQueryRequest.setAppid(appId);
		// refundQueryRequest.setMch_id(mchId);
		// // refundQueryRequest.setTransaction_id("1001410382201411190005996872");
		// refundQueryRequest.setOut_trade_no("1416370756370000");
		// refundQueryRequest.setNonce_str(new RandomString().getRandomString(32, "ilu"));
		// // 退款查询签名
		// String sign = WXPayUtil.doSign(refundQueryRequest, key);
		// refundQueryRequest.setSign(sign);
		// // 转换退款查询请求报文
		// String requestXML = WXPayUtil.refundQueryRequestToXml(refundQueryRequest);
		// System.out.println(">>>requestXML:\n" + requestXML);
		// // 提交退款查询接口请求
		// String responseXML = WXPayUtil.HttpsRequest4XML(WXPayUtil.REFUNDQUERY_URL, "POST", requestXML);
		// System.out.println(">>>responseXML:\n" + responseXML);
		//
		// if (StringUtils.isNotEmpty(responseXML)) {
		// // 转换退款查询接口响应报文为对应的Java对象
		// RefundQueryResponse refundQueryResponse = WXPayUtil.xmlToRefundQueryResponse(responseXML);
		// System.out.println(">>>" + refundQueryResponse);
		// if ((null != refundQueryResponse) && "SUCCESS".equals(refundQueryResponse.getReturn_code())
		// && "SUCCESS".equals(refundQueryResponse.getResult_code())) {
		//
		// System.out.println(">>>【退款查询成功】<<<");
		// }
		// }

		/**
		 * 对账单 测试
		 */
		// DownloadBillRequest downloadBillRequest = new DownloadBillRequest();
		// downloadBillRequest.setAppid(appId);
		// downloadBillRequest.setMch_id(mchId);
		// downloadBillRequest.setNonce_str(new RandomString().getRandomString(32, "ilu"));
		// downloadBillRequest.setBill_date("20141119");
		// downloadBillRequest.setBill_type("ALL");
		// // 对账单 签名
		// String sign = WXPayUtil.doSign(downloadBillRequest, key);
		// downloadBillRequest.setSign(sign);
		// // 转换 对账单 请求报文
		// String requestXML = WXPayUtil.downloadBillRequestToXml(downloadBillRequest);
		// System.out.println(">>>requestXML:\n" + requestXML);
		// // 提交 对账单 接口请求
		// String responseXML = WXPayUtil.HttpsRequest4XML(WXPayUtil.DOWNLOADBILL_URL, "POST", requestXML);
		// System.out.println(">>>responseXML:\n" + responseXML);
		//
		// if (StringUtils.isNotEmpty(responseXML)) {
		// if (responseXML.contains("<xml><return_code>") && responseXML.contains("<return_msg>")) {
		// // 转换 对账单 接口响应报文为对应的Java对象
		// DownloadBillResponse downloadBillResponse = WXPayUtil.xmlToDownloadBillResponse(responseXML);
		// System.out.println(">>>" + downloadBillResponse);
		// if ((null != downloadBillResponse) && "FAIL".equals(downloadBillResponse.getReturn_code())) {
		// System.out.println(">>>【对账单失败】<<< 原因：" + downloadBillResponse.getReturn_msg());
		// }
		// } else {
		// System.out.println(">>>【对账单成功】<<<");
		// }
		// }

		/**
		 * 支付结果通知测试
		 */
		// String notifyReceiveXML =
		// "<xml><appid><![CDATA[wxc78c381105b7ff7c]]></appid><attach><![CDATA[>>>1225881902+>>>wxc78c381105b7ff7c+>>>1541]]></attach><bank_type><![CDATA[ICBC_DEBIT]]></bank_type><cash_fee><![CDATA[84900]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1225881902]]></mch_id><nonce_str><![CDATA[RyQHnwI9ZMmTLBO0MA5ok83rnQH4LPms]]></nonce_str><openid><![CDATA[oIHjSsgFv_zInU4NE90slyGuLaj0]]></openid><out_trade_no><![CDATA[1430299896673743]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[68B542F75C6ABE1AEE65071AD5450A82]]></sign><time_end><![CDATA[20150429173146]]></time_end><total_fee>84900</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1002320931201504290098124690]]></transaction_id></xml>";
		// NotifyReceive notifyReceive = WXPayUtil.xmlToNotifyReceive(notifyReceiveXML);
		//
		// String sign = WXPayUtil.doSign(notifyReceive, "LFhHRKUZfcLW7PFnfie9izZ6m9EcR1nG");
	}
}