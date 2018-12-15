package com.vilio.ppms.glob;

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
    /* 不支持的业务类型 */
    public static String SMS_NO_BUSI_TYPE = "0026";
    /* 文件操作失败 */
    public static String FILE_HANDLE_FAIL = "0029";
    /* 文件上传失败 */
    public static String FILE_UPLOAD_FAIL = "0030";

}
