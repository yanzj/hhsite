package com.vilio.pcfs.glob;

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
    /* 更新数据库失败 */
    public static String UPDATE_FAIL = "9997";
    /* 网络连接超时 */
    public static String TIME_OUT = "9992";
    /* 公共参数校验失败 */
    public static String PUBLIC_PARAM_FAIL = "0005";
    /* 用户未登录 */
    public static String USER_NOLOGIN = "0027";
    /* 用户已在其它设备登录 */
    public static String USER_OTHER_MACHINE_LOGIN = "0028";
    /* 登录超时 */
    public static String LOGIN_TIMEOUT = "0025";
    /* 不支持的业务类型 */
    public static String SMS_NO_BUSI_TYPE = "0026";
    /* 文件操作失败 */
    public static String FILE_HANDLE_FAIL = "0029";
    /* 文件上传失败 */
    public static String FILE_UPLOAD_FAIL = "0030";
    /* 验证码不存在 */
    public static String VREIFY_CODE_ISNULL = "0052";
    /* 验证码已失效，请重新获取 */
    public static String VREIFY_CODE_NULLIFY = "0053";
    /* 用户非首次登录 */
    public static String UN_FIRST_LOGIN = "0054";
    /* 验证码未经过验证 */
    public static String NO_VERIFY = "0055";
    /* 交易密码已存在 */
    public static String TRANS_PWD_EXIST = "0056";
    /* 发送短信太过频繁，请稍后再试 */
    public static String SMS_FREQUENTLY_FAIL = "0064";








}
