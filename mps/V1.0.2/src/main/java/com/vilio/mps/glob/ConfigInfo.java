package com.vilio.mps.glob;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/13.
 */

public class ConfigInfo {
    //##############################阿里云短信配置开始##############################
    public static String defaultConnectTimeout;
    public static String defaultReadTimeout;
    public static String regionId;
    public static String accessKeyId;
    public static String accessKeySecret;
    public static String product;
    public static String domain;
    public static String batchSendNum;
    //##############################阿里云短信配置结束##############################
    public static Map ERROR_CODE = new HashMap<String, String>();
    public static String nlbsUrl;

    public static String umengProductionMode = "false";



    public static String mailSmtpServer = "smtp.exmail.qq.com";
}
