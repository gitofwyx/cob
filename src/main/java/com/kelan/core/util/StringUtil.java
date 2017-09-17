package com.kelan.core.util;

import com.kelan.core.constant.RegularString;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class StringUtil {

	static private final String NATIVE = "GBK";
	static private final String UNICODE = "ISO8859-1";

	public StringUtil() {
	}

	/**
	 * 格式化double值,
	 *
	 * @param dblValue
	 *            double值
	 *
	 * @param strFormat
	 *            格式串("00000.000")
	 * @return 返回格式串
	 */
	public static String format(double dblValue, String strFormat) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat);
		return df.format(dblValue);

	}

	/**
	 * 格式化String值,
	 *
	 * @param dblValue
	 *            double值
	 *
	 * @param strFormat
	 *            格式串("00000.000")
	 * @return 返回格式串
	 */
	public static String format(String strValue, String strFormat) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat);
		return df.format(strToDouble(strValue));
	}

	/**
	 * 字符转换成double
	 *
	 * @param str
	 *            字符串值
	 *
	 * @return double值
	 */
	public static double strToDouble(String str) {
		double ret = 0.00;
		try {
			boolean flag = (null == str);
			flag = flag || (str.trim().length() < 1);
			if (flag) {
				ret = 0.00;
			} else {
				ret = Double.parseDouble(str);
			}
		} catch (NumberFormatException e) {
			ret = 0.00;
		}
		return ret;
	}

	/**
	 * null对象转换为空字符串
	 *
	 *
	 * @param value
	 *            字符串的源对象
	 *
	 * @return 处理后的字符串
	 */
	public static String nullToEmpty(String value) {
		String strReturn = "";
		if ((value == null) || (value.equals("null"))) {
			strReturn = "";
		} else {
			strReturn = value.trim();
		}
		return strReturn;
	}

	/**
	 * 如果value==null或value="" 返回true,
	 *
	 * @param value
	 *            字符串的源对象
	 *
	 * @return true or false
	 */
	public static boolean isNullorEmpty(String value) {
		if (value == null || "".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 将object对象转化string
	 *
	 * @param obj
	 * @return
	 */
	public static String getStringFromObj(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	public static String nullOrEmptyToValue(String sourceString, String value) {
		if (sourceString == null || "".equals(sourceString)) {
			return value;
		}
		return sourceString;
	}

	/**
	 * 删除字符串前后空格
	 * 
	 * @param value
	 * @return String
	 */
	public static String trimBlank(String value) {
		if (value == null) {
			return null;
		} else {
			return value.trim();
		}
	}

	/**
	 * 加密一个字符串
	 *
	 * @param message
	 *            要加密的字符串信息
	 *
	 * @return 加密后的字符串信息
	 */
	static public String encode(String message) {
		try {
			MessageDigest alg = MessageDigest.getInstance("SHA-1");
			alg.update(message.getBytes());
			byte[] encoded = alg.digest();
			StringBuffer buf = new StringBuffer(encoded.length * 2);
			for (int n = 0; n < encoded.length; n++) {
				if ((encoded[n] & 0XFF) < 16) {
					buf.append('0');
				}
				buf.append(Integer.toHexString(encoded[n] & 0XFF));
			}
			return buf.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 过滤字符串<,>,',",&
	 *
	 * @param value
	 * @return
	 */
	public static String filter(String value) {

		if (value == null || "".equals(value)) {
			return (null);
		}

		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			default:
				result.append(content[i]);
			}
		}
		return (result.toString());

	}

	/**
	 * 将unicode字符转gbk为字符
	 *
	 *
	 * @param s
	 *            源字串
	 *
	 * @return unicode的字串
	 */
	public static String unicodeToNative(String s) {

		if (s == null) {
			return null;
		}
		String v = null;
		if (s != null) {
			try {
				byte[] bytes = s.getBytes(UNICODE);
				v = new String(bytes, NATIVE);
			} catch (Exception e) {
			}
		}
		return v;
	}

	/**
	 * 将gbk字符转为unicode字符
	 *
	 * @param s
	 *            源字串
	 *
	 * @return unicode的字串
	 */
	public static String nativeToUnicode(String s) {

		String v = null;
		if (s != null) {
			try {
				byte[] bytes = s.getBytes(NATIVE);
				v = new String(bytes, UNICODE);
			} catch (Exception e) {
			}
		}
		return v;
	}

	/**
	 * 取得带分割符号的字符串 例如：1,2,3,4,5或者,1,2,3 或者1,2,3,
	 *
	 * @param string
	 *            String 源字符串
	 * @param splitChar
	 *            String 分割符号
	 * @return ArrayList 返回去掉分割符号后的字符
	 * @see add by 杨帆
	 */
	public static ArrayList<String> getSplitString(String string, String splitChar) {
		if (string == null || string.equals("") || string.equals(splitChar)) {
			return new ArrayList<String>();
		}
		boolean flag = true;
		ArrayList<String> list = new ArrayList<String>();
		int count = 0;
		while (flag) {
			int i = string.indexOf(splitChar);
			int j = string.indexOf(splitChar, i + 1);
			if (j > -1) {
				if (count == 0 && i > 0) { // 特殊处理第一个字符不是分割符号的情况
					list.add(string.substring(0, i));
					string = string.substring(i);
				} else {
					list.add(string.substring(i + 1, j));
					string = string.substring(j);
				}
			} else {
				String str = string.substring(i + 1);
				if (!str.equals("")) {
					list.add(str);
				}
				flag = false;
			}
			count++;
		}
		return list;
	}

	/**
	 * 分割的字符串，转换成数组
	 * 
	 * @param separator
	 * @param str
	 * @return
	 */
	public static String[] split(String str, String separator) {
		return StringUtils.split(str, separator);
	}

	/**
	 * 转换成UTF-8编码
	 *
	 * @param s
	 *            String
	 * @return String
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= '\377') {
				sb.append(c);
			} else {
				byte b[];
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 处理sql注入
	 *
	 * @param value
	 * @return
	 */
	public static String dealQueryValue(String value) {
		if (isNullorEmpty(value)) {
			return value;
		}
		value = value.replaceAll("'", "‘");
		return value;
	}

	/**
	 * 用于处理金额数据
	 *
	 * @param money
	 * @return
	 */
	public static String dealMoney(String money) {
		int index = money.indexOf(".");
		if (index >= money.length() || index == -1) {
			return money;
		}
		money = money.substring(0, index);
		while (money.length() < 15) {
			money = "0" + money;
		}
		return money;
	}

	/**
	 * 判断字符串中是否包括字母
	 *
	 * @param param
	 *            需要判断的字符串
	 *
	 * @return
	 */
	public static boolean isContainLetter(String param) {
		String regex = ".*[a-zA-Z]+.*";
		Matcher m = Pattern.compile(regex).matcher(param);
		return m.matches();
	}

	public static boolean isContainStr(String param) {

		String regex = ".*[a-zA-Z]+.*";
		Matcher m = Pattern.compile(regex).matcher(param);
		String regex2 = "0+.*";
		Matcher ma = Pattern.compile(regex2).matcher(param);
		return m.matches() || ma.matches();
	}

	public static String getSerialNumber(int orderNum, int nums) {
		String orderNumStr = String.valueOf(orderNum);
		int ordeLen = orderNumStr.length();
		int zeroNums = 0;
		String zeroStr = "";
		if (ordeLen > nums) {
			return null;
		}
		for (zeroNums = nums - ordeLen; zeroNums > 0; zeroNums--) {
			zeroStr = (new StringBuilder(String.valueOf(zeroStr))).append("0").toString();
		}

		orderNumStr = (new StringBuilder(String.valueOf(zeroStr))).append(orderNumStr).toString();
		return orderNumStr;
	}

	/**
	 * Title : 获取随机数
	 *
	 * @param digits
	 *            随机数取值范围 默认为0123456789
	 * @param length
	 *            随机数位数 默认为1位
	 * @return
	 */
	public static String getRandom(String digits, int length) {
		if (null == digits) {
			digits = "0123456789";
		}
		if (length <= 0) {
			length = 1;
		}
		char[] code = new char[length];
		String temp = "";
		for (int i = 0; i < length; i++) {
			code[i] = digits.charAt((int) (Math.random() * digits.length()));
			temp += "0";
		}
		String verifyCode = new String(code);

		if (verifyCode.equals(temp)) {
			verifyCode = getRandom(digits, length);
		}
		return verifyCode;
	}

	public static String getUUIDGenerator() {
		return StringUtils.remove(java.util.UUID.randomUUID().toString(), '-');
	}

	/**
	 * 段落（中文、英文、数字或标点符号）校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isParagraph(String s, Boolean isRequired) {
    	return StringUtil.commonValidate(s, isRequired, "^[0-9a-zA-Z_\\u4e00-\\u9fa5。，、；：！？“”‘’.,:;!?''\"\"]+$");
	}

	/**
	 * 中文、英文、数字或下划线校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isChsEnNum(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$");
	}

	/**
	 * 中文校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isChs(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, "^[\\u4e00-\\u9fa5]+$");
	}

	/**
	 * URL校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isUrl(String s, Boolean isRequired) {
		return StringUtil
				.commonValidate(
						s,
						isRequired,
						"^(http|https)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$");
	}

	/**
	 * 长度校验及中文、英文、数字或下划线校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param minLength
	 *            最小长度
	 * @param maxLength
	 *            最大长度
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isChsEnNumLenght(String s, int minLength, int maxLength, Boolean isRequired) {

		if (isRequired) {
			if (s == null || s.length() < minLength || s.length() > maxLength) {
				return false;
			}
		} else {
			if (s != null && s.length() > maxLength) {
				return false;
			}
		}
		Pattern chsEnNumPattern = Pattern.compile("^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$");
		Matcher m = chsEnNumPattern.matcher(s);
		return m.matches();
	}

	/**
	 * 大小写英文校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isLetter(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.LETTER);
	}

	/**
	 * 手机号码校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isMobilePhone(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.PHONE);
	}

	/**
	 * 固定电话号码校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isTelphone(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.TELPHONE);
	}

	/**
	 * e-mail校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isEMail(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.EMAIL);
	}

	/**
	 * 身份证号码校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isIdCardNo(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.IDCARDNO);
	}

	/**
	 * 邮政编码校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isZipCode(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.ZIPCODE);
	}

	/**
	 * 数字下划线校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isLetterUuderline(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.LETTER_UNDERLINE);
	}

	/**
	 * 英数校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isLetterDigital(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.LETTER_DIGITAL);
	}

	/**
	 * 中文、英文、数字或下划线批量校验
	 * 
	 * @param strList
	 *            待校验字符串数组
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isChsEnNums(List<String> strList, Boolean isRequired) {
		Pattern chsEnNumPattern = Pattern.compile("^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$");
		Matcher m = null;
		for (String str : strList) {
			if (str == null || str.length() == 0) {
				if (isRequired) {
					return false;
				} else {
					return true;
				}
			}

			m = chsEnNumPattern.matcher(str);
			if (!m.matches()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 共同校验方法
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @param regular
	 * @return true 校验通过 false 校验失败
	 */
	private final static boolean commonValidate(String s, boolean isRequired, String regular) {
		if (s == null || s.length() == 0) {
			if (isRequired) {
				return false;
			} else {
				return true;
			}
		}

		Pattern chsEnNumPattern = Pattern.compile(regular);
		Matcher m = chsEnNumPattern.matcher(s);
		return m.matches();
	}

	/**
	 * 金额校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isMoney(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.money);
	}

	/**
	 * 生日
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isBirthday(String s, Boolean isRequired) {
		if (StringUtil.commonValidate(s, isRequired, "[0-9]{4}-[0-9]{2}-[0-9]{2}") || StringUtil.commonValidate(s, isRequired, "[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
			return true;
		}
		return false;
	}

	/**
	 * 英文与空格校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isEnAndBlank(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, "^[a-zA-Z\\s]+$");
	}

	/**
	 * 英文，数字，下划线，横线
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isEnZhAndBlank(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, "^[\\w\\d_-]+$");
	}

	/**
	 * 图片文件
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isImage(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.IMAGE_SUFFIXES);
	}

	/**
	 * 获取字符串utf-8编码字节长度
	 * 
	 * @param s
	 * @return
	 */
	public static int getBytesLength(String s) {
		try {
			return s.getBytes("utf-8").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 中文、英文校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isChsEn(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, "^[a-zA-Z\\u4e00-\\u9fa5]+$");
	}
	/**
	 * 中文、英文、数字、横线、/、.或下划线校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isChsEnNumLineOrDot(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, "^[0-9a-zA-Z_\\-\\/\\.\\u4e00-\\u9fa5]+$");
	}
	/**
	 * 大写英文、数字、长度
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isEnNum(String s, int minLength, int maxLength, Boolean isRequired) {
		if (isRequired) {
			if (s == null || s.length() < minLength || s.length() > maxLength) {
				return false;
			}
		} else {
			if (s != null && s.length() > maxLength) {
				return false;
			}
		}
		return StringUtil.commonValidate(s, isRequired, "^[0-9A-Z]+$");
	}
	/**
	 * qq校验
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isQQNum(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, RegularString.QQNUMBER);
	}
	/**
	 * 中文、英文、数字或下划线校验、一些符号()
	 * 
	 * @param s
	 *            待校验字符串
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return true 校验通过 false 校验失败
	 */
	public static boolean isChsEnNumMark(String s, Boolean isRequired) {
		return StringUtil.commonValidate(s, isRequired, "^[0-9a-zA-Z_\\u4e00-\\u9fa5()（）]+$");
	}
}
