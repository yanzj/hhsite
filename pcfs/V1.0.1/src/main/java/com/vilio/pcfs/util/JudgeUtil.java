package com.vilio.pcfs.util;

import org.apache.commons.lang3.ObjectUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeUtil {


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
        return "00";
    }



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
        for (int i = str.length(); --i >= 0; ) {
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
        for (int i = str.length(); --i >= 0; ) {
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
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为拼音数字，长度18位
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */

    public static boolean isNumberLetter_18(String str) {
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{1,18}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证手机号
     *
     * @param str
     * @return
     */
    public static boolean isNumberPhone(String str) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断数字，后面2位小数点
     *
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


    /**
     * 遍历map，所有不是数组或者键值对形式的参数全部变成字符串
     *
     * @param param
     */
    public static void MapValToString(Map param) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        for (Object key : param.keySet()) {
            if (param.get(key) instanceof String) {
            } else if (param.get(key)==null||"".equals(param.get(key))) {
                param.put(key, ObjectUtils.toString(param.get(key)));
            } else if (param.get(key) instanceof Map) {
                MapValToString((Map) param.get(key));
            } else if (param.get(key) instanceof List) {
                ListValToString((List) param.get(key));
            } else if(param.get(key) instanceof Boolean){
                param.put(key, ObjectUtils.toString(param.get(key)));
            } else if(param.get(key) instanceof Date){
                param.put(key, ObjectUtils.toString(param.get(key)));
            } else if(param.get(key) instanceof Character){
                param.put(key, ObjectUtils.toString(param.get(key)));
            } else if(param.get(key) instanceof Number){
                param.put(key, ObjectUtils.toString(param.get(key)));
            } else {
                System.out.println(param.get(key));
                //如果是其他类型对象，先将对象转换成map
                Map<String, Object> beanMap = BeanUtil.transBean2Map(param.get(key));
                param.put(key,beanMap);
                MapValToString((Map) param.get(key));
            }
        }
    }

    /**
     * 遍历List，所有不是数组或者键值对形式的参数全部变成字符串
     *
     * @param param
     */
    public static void ListValToString(List param) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        for (int i = 0; i < param.size(); i++) {
            if (param.get(i) instanceof String) {
            } else if (param.get(i)==null||"".equals(param.get(i))) {
                param.set(i, ObjectUtils.toString(param.get(i)));
            } else if (param.get(i) instanceof Map) {
                MapValToString((Map) param.get(i));
            } else if (param.get(i) instanceof List) {
                ListValToString((List) param.get(i));
            } else if(param.get(i) instanceof Boolean){
                param.set(i, ObjectUtils.toString(param.get(i)));
            } else if(param.get(i) instanceof Character){
                param.set(i, ObjectUtils.toString(param.get(i)));
            } else if(param.get(i) instanceof Number){
                param.set(i, ObjectUtils.toString(param.get(i)));
            } else {
                //如果是其他类型对象，先将对象转换成map
                Map<String, Object> beanMap = BeanUtil.transBean2Map(param.get(i));
                param.set(i,beanMap);
                MapValToString((Map) param.get(i));
            }
        }
    }


    public static void main(String[] args) {

//		int x = 0;
//
//		x = x + 1;
//		System.out.println(x);

        System.out.println(System.currentTimeMillis());

//        Map param = new HashMap();
//        Map param1 = new HashMap();
//        List param2 = new ArrayList();
//        param.put("a",Double.parseDouble("1"));
//        param.put("param1",param1);
//        param.put("param2",param2);
//        param1.put("b",2.00015468465432131324654654651351321654561651321321354654561135132);
        //param2.add(3);

//        JSONObject str = JSONObject.fromObject(param);
//        Map strMap = CommonUtil.toMap(str);
//        System.out.println(strMap);
//        JSONObject str =JSONObject.fromObject(param);
//        String str1 = str.toString();
//        System.out.println(str1);
//        Map strMap =  CommonUtil.toMap(JSONObject.fromObject(str1));
//        MapValToString(strMap);
//        System.out.println(JsonUtil.objectToJson(strMap));
//        param.put("a",0);
//        System.out.println(param.get("a") instanceof Integer);


        System.out.println("王雪峰".length());
    }

}
