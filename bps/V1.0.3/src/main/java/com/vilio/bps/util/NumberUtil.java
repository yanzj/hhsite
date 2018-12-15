package com.vilio.bps.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	public static final String money_status_success = "00";
	/**
	 * 判断字符串是否为整数
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value) {
		try{
			Long.parseLong(value);
			return true;
			} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 判断字符串是否为小数
	 * @param value
	 * @return
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains(".")){
				return true;
			}			
		    return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		String reg="";
		if(str.indexOf(".")>-1){
			reg="-{0,1}[0-9]{1,}\\.[0-9]{1,}";
		}else{
			reg="-{0,1}[0-9]{1,}";
		}
	    Pattern pattern = Pattern.compile(reg);
	    return pattern.matcher(str).matches();
    }
	/**
	 * double转换成BigDecimal
	 * @param number
	 * @return
	 */
	public static BigDecimal double2BigDecimal(Double number){
		if(number==null||"".equals(number)){
			number=0.00;
		}
		return new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * 判断金额格式
	 * @param moneyStr 金额字符串
	 * @param intNum 整数位数
	 * @param doubleNum 小树位数
	 * @return
	 */
	public static String isMoney(String moneyStr, int intNum, int doubleNum){
		try{
			Pattern pattern = Pattern.compile("[-]{0,1}[0-9]{1,}\\.[0-9]{1,}|[-]{0,1}[0-9]{1,}");
			Matcher match = pattern.matcher(moneyStr);
			if (match.matches() == false){
				return "01";//非数字
			} else if (Double.parseDouble(moneyStr) < 0){
				return "02";//小于0
			} else {
				if (moneyStr.contains(".")){
					String[] moneyArr = moneyStr.split("\\.");
					if (moneyArr[0].length() > intNum){
						return "03";//整数部分
					} else if (moneyArr.length > 1 && moneyArr[1].length() > doubleNum){
						return "04";//小数部分
					}
				} else {
					if (moneyStr.length() > intNum){
						return "03";//整数部分
					}
				}
			}
		} catch (Exception e){
			return "99";
		}
		return money_status_success;
	}

}
