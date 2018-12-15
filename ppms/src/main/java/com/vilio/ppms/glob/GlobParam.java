package com.vilio.ppms.glob;

import java.math.BigDecimal;
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
    /* 系统编码 */
    public final static String SYSTEM_ID_PLMS = "ppms";
    public static BigDecimal ZERO = new BigDecimal("0");
    // fms地址
    public static String fmsUrl = "http://192.168.0.4:8081/fms/api";
    // fms默认存储地址
    public static String fmsPath = "ppms/";



}
