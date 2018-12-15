package com.vilio.mps.glob;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Map;

/**
 * 类名： InitGlobConfig<br>
 * 功能：初始化全局变量<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Component("initGlob")
public class InitGlobConfig extends HttpServlet {

    protected static Logger logger = Logger.getLogger(InitGlobConfig.class);
    //##############################阿里云短信配置开始##############################
//    @Value("#{smsDefaultConnectTimeout}")
//    public void setDefaultConnectTimeout(String defaultConnectTimeout) {
//        ConfigInfo.defaultConnectTimeout = defaultConnectTimeout;
//    }
//    @Value("#{smsDefaultReadTimeout}")
//    public void setDefaultReadTimeout(String defaultReadTimeout) {
//        ConfigInfo.defaultReadTimeout = defaultReadTimeout;
//    }

//    @Value("#{smsRegionId}")
//    public void setRegionId(String regionId) {
//        ConfigInfo.regionId = regionId;
//    }
//    @Value("#{smsAccessKeyId}")
//    public void setAccessKeyId(String accessKeyId) {
//        ConfigInfo.accessKeyId = accessKeyId;
//    }
//    @Value("#{smsAccessKeySecret}")
//    public void setAccessKeySecret(String accessKeySecret) {
//        ConfigInfo.accessKeySecret = accessKeySecret;
//    }
//    @Value("#{smsProduct}")
//    public void setProduct(String product) {
//        ConfigInfo.product = product;
//    }
//    @Value("#{smsDomain}")
//    public void setDomain(String domain) {
//        ConfigInfo.domain = domain;
//    }

//    @Value("#{smsBatchSendNum}")
//    public void setBatchSendNum(String batchSendNum) {
//        ConfigInfo.batchSendNum = batchSendNum;
//    }
    //##############################阿里云短信配置结束##############################


    @Value("#{configProperties}")
    public void setConfigProperties(Map<String, String> configProperties) {
        //##############################阿里云短信配置开始##############################
        ConfigInfo.defaultConnectTimeout = configProperties.get("smsDefaultConnectTimeout");
        ConfigInfo.defaultReadTimeout = configProperties.get("smsDefaultReadTimeout");
        ConfigInfo.regionId = configProperties.get("smsRegionId");
        ConfigInfo.accessKeyId = configProperties.get("smsAccessKeyId");
        ConfigInfo.accessKeySecret = configProperties.get("smsAccessKeySecret");
        ConfigInfo.product = configProperties.get("smsProduct");
        ConfigInfo.domain = configProperties.get("smsDomain");
        ConfigInfo.batchSendNum = configProperties.get("smsBatchSendNum");
        //##############################阿里云短信配置结束##############################
        ConfigInfo.nlbsUrl = configProperties.get("nlbsUrl");
        ConfigInfo.plmsUrl = configProperties.get("plmsUrl");
        System.setProperty("sun.net.client.defaultConnectTimeout", ConfigInfo.defaultConnectTimeout);
        System.setProperty("sun.net.client.defaultConnectTimeout", ConfigInfo.defaultReadTimeout);
        ConfigInfo.umengProductionMode = configProperties.get("umengProductionMode");


        ConfigInfo.mailSmtpServer = configProperties.get("mailSmtpServer");
    }

    //错误码配置
    @Value("#{errorCodeProperties}")
    public void setErrorCode(Map errorCodeProperties) {
        ConfigInfo.ERROR_CODE = errorCodeProperties;
    }

    public final void init(ServletConfig config) throws ServletException {
    }

}
