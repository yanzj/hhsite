package com.vilio.comm.glob;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： GlobParam<br>
 * 功能：全局参数定义<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class GlobParam {
    // 错误码全局配置
    public static Map<String, String> ERROR_CODE = new HashMap<String, String>();
    //登录错误锁定次数
    public static int umLoginError = 5;
    //外部用户登录错误锁定次数
    public static int customLoginError = 5;
    //系统类型
    public static String umFunctionType = "um";
    public static String customFunctionType = "custom";
    public static String customHashLockTime = "900000";
    public static String umHashLockTime = "900000";
    public static String customTransLockTime = "900000";
    public static int customTransError = 6;


    /* 参数传递消息头 */
    public final static String PARAM_MESSAGE_HEAD = "head";
    /* 参数 功能号 */
    public final static String PARAM_FUNCTION_NO = "functionNo";
    /* 参数 功能号 */
    public final static String PARAM_SYSTEM_ID = "systemId";
    /* 参数 交易类型 */
    public final static String PARAM_FUNCTION_TYPE = "functionType";
    /* 参数传递消息体 */
    public final static String PARAM_MESSAGE_BODY = "body";
    /* 参数传递消息，错误号字段 */
    public final static String PARAM_MESSAGE_ERR_CODE = "returnCode";
    /* 参数传递消息，错误信息 */
    public final static String PARAM_MESSAGE_ERR_MESG = "returnMessage";

}
