package com.vilio.nlbs.util;

/**
 * Created by xiezhilei on 2017/1/13.
 */
public class ReturnCode {
    /** 成功 **/
    public static String SUCCESS_CODE = "0000";
    /** 系统异常 **/
    public static String SYSTEM_EXCEPTION = "9999";
    /* 必填字段缺失 */
    public static String REQUIRED_FIELD_MISSING = "9998";
    //用户被锁定
    public static String USER_HAVE_LOCKED = "9001";
    //用户名或密码错误，超过5次锁定
    public static String USER_WRONG_PSD_AND_LOCKED = "9002";
    //用户名或密码错误，还剩下N次机会
    public static String USER_WRONG_PSD = "9003";
    //用户不存在
    public static String USER_WRONG_USER_NO = "9004";
    //两次密码输入不一致
    public static String USER_DIFF_PASSWORDRESET = "9008";
    /** 登录状态异常（未登录或登录超时） **/
    public static String SYSTEM_DISABLE_TOKEN = "1234";

    /** 接受消息为空 **/
    public static String MSG_EMPTY_RECEIVE = "8234";
    /* 估价不存在 */
    public static String INQUIRY_NO_EXIST = "8235";

    /* 重复录入评估价 */
    public static String  DUPLICATE_INPUT_PRICE = "5001";
}
