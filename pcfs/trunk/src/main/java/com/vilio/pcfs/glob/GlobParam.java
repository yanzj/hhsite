package com.vilio.pcfs.glob;

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
    public static int login_error = 5;

    //##############################短信配置开始#################################
    public static int smsLoginVerifyNum = 6;
    public static String smsLoginVerifyTimeout = "15";
    public static String smsSignNo = "1000000000000000001";
    public static String smsLoginTemplateNo = "2000000000000000001";

    public static int smsBorrowNum = 6;
    public static String smsBorrowTimeout = "6";
    public static String smsBorrowTemplateNo = "1000000000000000001";

    public static int smsUpdatePwdNum = 6;
    public static String smsUpdatePwdTimeout = "15";
    public static String smsUpdatePwdTemplateNo = "2000000000000000003";

    public static int smsUpdateTransPwdNum = 6;
    public static String smsUpdateTransPwdTimeout = "15";
    public static String smsUpdateTransPwdTemplateNo = "2000000000000000003";

    public static int smsAuthenticationNum = 6;
    public static String smsAuthenticationTimeout = "15";
    public static String smsAuthenticationTemplateNo = "2000000000000000003";

    public static int smsSecondTimeOut = 60;



    public static String smsType = "SMS";
    public static String smsSenderIdentityId = "pcfs001";
    public static String smsFunctionNo = "HH000803";
    public static String mpsUrl = "http://localhost:8081/singMps.json";





    //##############################短信配置结束#################################
    /* 挡板开关 */
    public static String baffleSwitch = "0";
    /* 贷后系统的地址 */
    public static String plmsUrl = "http://localhost:8081/singMps.json";
    /* 文件系统路径 */
    public static String fmsUrl = "http://localhost:8080/api/fileLoad/uploadFile";
    /* 文件系统路径 */
    public static String fmsPath = "test/img/";
    /* 文件系统上传路径 */
    public static String fmsDownloadUrl = "http://localhost:8080/fms/api/fileLoad/getFile";


    /* 默认图片地址 */
    public static String imageDefault = "http://localhost:8080/umCore.json";
    /* UM系统地址 */
    public static String umUrl = "http://localhost:8080/umCore.json";
    /* UM系统类型 */
    public final static String umFunctionType = "custom";
    /* UM登录交易码 */
    public final static String umLoginFunctionNo = "200001";
    /* UM登录密码修改 */
    public final static String umUpdatePwdFunctionNo = "200002";
    /* UM交易密码修改 */
    public final static String umTransUpdatePwdFunctionNo = "200008";
    /* UM修改首次登录标识 */
    public final static String umUpdateFirstLoginFunctionNo = "200010";
    /* 忘记登录密码，修改登录密码 */
    public final static String umforgetUpdatePassword = "200011";
    /* 通过用户编码修改密码 */
    public final static String umUpdatePasswordByUserId = "200012";
    /* 设置交易密码 */
    public final static String umFirstUpdateTransPwd = "200013";
    /* 通过手机号修改交易密码 */
    public final static String umUpdateTransPwdByMobile = "200014";
    /* 用户验证手机号接口 */
    public final static String umVerityUserMobile = "200015";
    /* 交易密码验证接口 */
    public final static String umVerityTransPwd = "200007";
    /* 校验手机号 */
    public final static String umChechMobile = "200016";



    /* 当前系统编号 */
    public final static String SYSTEM_NO = "pcfs";
    /* 参数传递消息头 */
    public final static String PARAM_MESSAGE_HEAD = "head";
    /* 参数 功能号 */
    public final static String PARAM_FUNCTION_NO = "functionNo";
    /* 参数传递消息体 */
    public final static String PARAM_MESSAGE_BODY = "body";
    /* 参数传递消息，错误号字段 */
    public final static String PARAM_MESSAGE_ERR_CODE = "returnCode";
    /* 参数传递消息，错误信息 */
    public final static String PARAM_MESSAGE_ERR_MESG = "returnMessage";
    /* 参数 用户id */
    public final static String PARAM_USER_ID = "userId";
    /* 参数 交易令牌 */
    public final static String PARAM_TOKEN = "token";
    /* 参数 渠道IOS,ANDROID */
    public final static String PARAM_CHANNEL = "channel";
    /* 参数 手机标识 */
    public final static String PARAM_DEVICE_TOKEN = "deviceToken";
    /* 参数 版本号 */
    public final static String PARAM_APP_VERSION = "appVersion";
    /* 参数 系统编码 */
    public final static String PARAM_SYSTEM_NO = "sourceSystem";

    /* 参数 服务器时间 */
    public final static String PARAM_SYSTEM_TIME = "systemTime";

}
