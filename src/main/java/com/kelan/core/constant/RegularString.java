package com.kelan.core.constant;

/**
 * Title: 正则表达式
 * 
 * Description: 各种正则表达式
 * 
 */
public class RegularString {

	/**
	 * 匹配中文字符的正则表达式
	 */
	public static final String CHINESE = "[\\u4e00-\\u9fa5]";

	/**
	 * 匹配双字节字符(包括汉字在内)：
	 */
	public static final String DOUBLECHINESE = "[^\\x00-\\xff]";

	/**
	 * 验证年龄
	 */
	public static final String AGE = "^[1-9]\\d?$";

	/**
	 * 中文或英文
	 */
	public static final String CNOREN = "^([\\u4e00-\\u9fa5]{2,})$|^([a-zA-Z0-9]{4,})$";

	/**
	 * 匹配HTML标记的正则表达式：
	 */
	public static final String HTML = "<(\\S*?)[^>]*>.*?</\\1>|<.*? />";

	/**
	 * 匹配首尾空白字符的正则表达式： 评注：可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式
	 */
	public static final String BESPACE = "^\\s*|\\s*$";

	/**
	 * 匹配Email地址的正则表达式：
	 */
	public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

	/**
	 * 匹配网址URL的正则表达式： 网上流传的版本功能很有限，上面这个基本可以满足需求
	 */
	public static final String URL = "[a-zA-z]+://[^\\s]*";

	/**
	 * 匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：
	 */
	public static final String ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{2,15}$";

	/**
	 * 匹配国内固定电话号码：
	 */
	// public static final String TELPHONE = "^\\d{3}-\\d{8}|\\d{4}-\\d{7,8}$";
	public static final String TELPHONE = "^\\d{3}-\\d{8}|\\d{4}-\\d{7,8}|\\d{7,8}$";

	/**
	 * 腾讯QQ号从10000开始
	 */
	public static final String QQNUMBER = "^[1-9][0-9]{4,}$";

	/**
	 * 匹配手机号码：
	 */
	// public static final String PHONE = "^[1][3]\\d{9}|[1][5][7-9]\\d{8}$";
	public static final String PHONE = "^[1][0-9]{10}$";

	/**
	 * 匹配中国邮政编码：
	 */
	public static final String ZIPCODE = "^[1-9]\\d{5}(?!\\d)$";

	/**
	 * 匹配身份证：
	 */
	public static final String IDCARDNO = "^\\d{15}|\\d{18}$";

	/**
	 * 匹配ip地址：
	 */
	public static final String IPADDRESS = "\\d+\\.\\d+\\.\\d+\\.\\d+";

	/**
	 * 匹配由26个英文字母组成的字符串
	 */
	public static final String LETTER = "^[A-Za-z]+$";

	/**
	 * 匹配由26个英文字母的大写组成的字符串
	 */
	public static final String UPPERCASE = "^[A-Z]+$";

	/**
	 * 匹配由26个英文字母的小写组成的字符串
	 */
	public static final String LOWERCASE = "^[a-z]+$";

	/**
	 * 匹配密码的字符串
	 */
	public static final String SIXNUM = "^\\d{6}$";

	/**
	 * 匹配由数字和26个英文字母组成的字符串
	 */
	public static final String LETTER_DIGITAL = "^[A-Za-z0-9]+$";

	/**
	 * 匹配由数字和下划线组成的字符串
	 */
	public static final String LETTER_UNDERLINE = "^[0-9_]+$";

	/**
	 * 匹配由数字和26个小写英文字母组成的字符串
	 */
	public static final String LOWER_LETTER_DIGITAL = "^[a-z0-9]+$";

	/**
	 * 匹配由数字和26个大写英文字母组成的字符串
	 */
	public static final String UPPER_LETTER_DIGITAL = "^[A-Z0-9]+$";

	/**
	 * 验证用资金帐户编号非负整数，不包括0
	 */
	// ^[1-9]+\d*$
	public static final String ACCOUNTID = "^[1-9]+\\d*$";

	/**
	 * 验证密码 6个数字
	 *
	 */
	public static final String pwd = "^\\d{6}$";

	/**
	 * 验证金额
	 *
	 */
	// ^\d+(\.\d+)?$
	public static final String money = "^[0-9]+\\d*(\\.\\d+)?$";

	/**
	 * 非空字符 *
	 */
	public static final String Null = "^$";

	/**
	 * 验证用户名，2-5个汉字
	 *
	 */
	public static final String CUSTOMERNAME = "^[\u4e00-\u9fa5]{2,5}$";

	/**
	 * 验证手机号码 *
	 */
	public static final String Phone = "^(13|15)\\d{9}$";

	/**
	 * 验证18位的身份证号码 *
	 */
	public static final String CUSTOMERID = "^\\d{18}$";

	/**
	 * 验证地址 \w{10,50}$ 有待改正 *
	 */
	public static final String ADDRESS = "^([\\u4e00-\\u9fa5]{2,})$|^([a-zA-Z0-9]{4,})$";

	/**
	 * 验证没有中划线的UUID
	 */
	public static final String UUID = "^[a-z0-9]{32}$";

	/**
	 * 验证带有中划线的UUID
	 */
	public static final String MIDDLELINEUUID = "^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$";

	/**
	 * 验证是否图片文件 ".+(.jpeg|.jpg|.bmp|.png)$"
	 */
	public static final String IMAGE_SUFFIXES = ".*\\.(?i)(jpg|jpeg|bmp|png)";
}
