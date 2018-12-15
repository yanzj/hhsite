package com.vilio.mps.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeUtil {

	/**
	 * 判断是否为金额数字格式方法
	 * 
	 * @param str
	 * @return 
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * java自带函数判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric_fun(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 正则表达式判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric_regular(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isNumeric_twelve(String str) {
		Pattern pattern = Pattern.compile("[0-9]{12}");
		return pattern.matcher(str).matches();
	}

	/**
	 * 用ascii码判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric_ascii(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(Object str) {
		if (str == null || str.equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 *            传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为拼音数字，长度18位
	 * 
	 * @param str
	 *            传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */

	public static boolean isNumberLetter_18(String str) {
		Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,18}$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 验证手机号
	 * @param str
	 * @return
	 */
	public static boolean isNumberPhone(String str) {
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//		Pattern pattern = Pattern.compile("^1[3|4|5|7|8][0-9]\\\\d{4,8}$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 判断数字，后面2位小数点
	 * @param str
	 * @return
	 */
	public static boolean isdoubleprice(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{2})?$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	public static void main(String[] args) {


		System.out.println(isMobile("17317709318"));
		System.out.println(isMobile("15242025845"));
		System.out.println(isMobile("15242025845"));
		System.out.println(isMobile("17603692659"));


	}

}
