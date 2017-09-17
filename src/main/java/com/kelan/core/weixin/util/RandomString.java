package com.kelan.core.weixin.util;

import java.util.Random;

/**
 * org.jinsheng.util.RandomString.java
 * <p>
 * 描述：此类用于获取随机字符串
 * </p>
 * <p>
 * 时间：2009-9-16
 * </p>
 * 
 * @author jinsheng
 * @version 1.0
 */
public class RandomString {
	private final String splitStr = " "; // 分割符

	// 取数字字符串 用 splitStr 分割
	private String getNumberString() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			buf.append(String.valueOf(i));
			buf.append(splitStr);
		}
		return buf.toString();
	}

	// 取大写字母字符串 用 splitStr 分割
	private String getUppercase() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 26; i++) {
			buf.append(String.valueOf((char) ('A' + i)));
			buf.append(splitStr);
		}
		return buf.toString();
	}

	// 取小写字母字符串 用 splitStr 分割
	private String getLowercase() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 26; i++) {
			buf.append(String.valueOf((char) ('a' + i)));
			buf.append(splitStr);
		}
		return buf.toString();
	}

	// 取特殊字符串 用 splitStr 分割
	private String getSpecialString() {
		String str = "~@#$%^&*()_+|\\=-`";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			buf.append(str.substring(i, i + 1));
			buf.append(splitStr);
		}
		return buf.toString();
	}

	// 根据所取的字符串类型连接相应的字符串并返回
	private String getString(String type) {
		StringBuffer pstr = new StringBuffer();
		if (type.length() > 0) {
			if (type.indexOf('i') != -1) {
				pstr.append(this.getNumberString());
			}
			if (type.indexOf('l') != -1) {
				pstr.append(this.getLowercase());
			}
			if (type.indexOf('u') != -1) {
				pstr.append(this.getUppercase());
			}
			if (type.indexOf('s') != -1) {
				pstr.append(this.getSpecialString());
			}

		}
		return pstr.toString();
	}

	/**
	 * 取随机字符串
	 * 
	 * @param length
	 *            返回随机字符串的长度
	 * @param type
	 *            要取的字符串类型:
	 *            i、取数字
	 *            l、取小写字母
	 *            u、取大写字母
	 *            s、取特殊字符
	 * @return String 随机字符串
	 */
	public String getRandomString(int length, String type) {
		String allStr = this.getString(type);
		String[] arrStr = allStr.split(splitStr);
		StringBuffer pstr = new StringBuffer();
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				pstr.append(arrStr[new Random().nextInt(arrStr.length)]);
			}
		}
		return pstr.toString();
	}

	// 测试
	public static void main(String[] args) {
		System.out.println("type=i:" + new RandomString().getRandomString(32, "i"));
		System.out.println("type=il:" + new RandomString().getRandomString(32, "il"));
		System.out.println("type=ilu:" + new RandomString().getRandomString(32, "ilu"));
		System.out.println("type=ilus:" + new RandomString().getRandomString(32, "ilus"));
		for (int i = 0; i < 100; i++) {
			System.out.println("type=ilus:" + new RandomString().getRandomString(32, "ilus"));
		}
	}

}
