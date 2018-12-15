package com.vilio.pcfs.glob;

import com.vilio.pcfs.util.PropertiesManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

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

    @Value("#{errorCodeProperties}")
    public void setErrorCode(Map errorCodeProperties) {
        GlobParam.ERROR_CODE = errorCodeProperties;
    }

    @Value("#{configProperties}")
    public void setConfigProperties(Map configProperties) {
        GlobParam.smsLoginVerifyNum=Integer.parseInt(configProperties.get("sms.loginNum").toString());
        GlobParam.smsLoginVerifyTimeout=configProperties.get("sms.loginTimeout").toString();
        GlobParam.smsSignNo=configProperties.get("sms.signNo").toString();
        GlobParam.smsLoginTemplateNo=configProperties.get("sms.loginTemplateNo").toString();
        GlobParam.smsType=configProperties.get("sms.type").toString();
        GlobParam.smsSenderIdentityId=configProperties.get("sms.senderIdentityId").toString();
        GlobParam.smsFunctionNo=configProperties.get("sms.functionNo").toString();
        GlobParam.mpsUrl=configProperties.get("mps.url").toString();
        GlobParam.plmsUrl=configProperties.get("plms.url").toString();
        GlobParam.baffleSwitch=configProperties.get("baffleSwitch").toString();
        GlobParam.fmsUrl=configProperties.get("fms.url").toString();
        GlobParam.fmsPath=configProperties.get("fms.filePath").toString();
        GlobParam.umUrl=configProperties.get("um.url").toString();
        GlobParam.imageDefault=configProperties.get("imageDefault").toString();
        GlobParam.fmsDownloadUrl=configProperties.get("fms.downloadUrl").toString();
        GlobParam.smsBorrowNum=Integer.parseInt(configProperties.get("sms.borrowNum").toString());
        GlobParam.smsBorrowTimeout=configProperties.get("sms.borrowTimeout").toString();
        GlobParam.smsBorrowTemplateNo=configProperties.get("sms.borrowTemplateNo").toString();
        GlobParam.smsUpdatePwdTemplateNo=configProperties.get("sms.updatePwdTemplateNo").toString();
        GlobParam.smsUpdatePwdTimeout=configProperties.get("sms.updatePwdTimeout").toString();
        GlobParam.smsUpdatePwdNum=Integer.parseInt(configProperties.get("sms.updatePwdNum").toString());


        GlobParam.smsUpdateTransPwdTemplateNo=configProperties.get("sms.updateTransPwdTemplateNo").toString();
        GlobParam.smsUpdateTransPwdTimeout=configProperties.get("sms.updateTransPwdTimeout").toString();
        GlobParam.smsUpdateTransPwdNum=Integer.parseInt(configProperties.get("sms.updateTransPwdNum").toString());

        GlobParam.smsAuthenticationTemplateNo=configProperties.get("sms.authenticationTemplateNo").toString();
        GlobParam.smsAuthenticationTimeout=configProperties.get("sms.authenticationTimeout").toString();
        GlobParam.smsAuthenticationNum=Integer.parseInt(configProperties.get("sms.authenticationNum").toString());

        GlobParam.smsSecondTimeOut=Integer.parseInt(configProperties.get("sms.secondTimeOut").toString());


        GlobParam.pushType=configProperties.get("push.type").toString();
        GlobParam.pushSenderIdentityId=configProperties.get("push.senderIdentityId").toString();
        GlobParam.pushFunctionNo=configProperties.get("push.functionNo").toString();

        GlobParam.pushAndroidAppCode=configProperties.get("push.android.appCode").toString();
        GlobParam.pushIosAppCode=configProperties.get("push.ios.appCode").toString();

    }



    public final void init(ServletConfig config) throws ServletException {
//        String basePach = InitGlobConfig.class.getClassLoader().getResource("").getPath();
//        // 加载错误码信息
//        loadErrorCode(basePach + File.separator + File.separator + "error-code.properties");
    }

    /**
     * 加载错误码信息到缓存
     */
    private void loadErrorCode(String filePath) {
        logger.info("开始加载错误码");
        Properties propTmp = PropertiesManager.getProperties(filePath);
        Iterator it = propTmp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = String.valueOf(entry.getKey() == null ? "" : entry.getKey());
            String value = String.valueOf(entry.getValue() == null ? "" : entry.getValue());
            GlobParam.ERROR_CODE.put(key, value);
        }
        logger.info("加载错误码完成");
    }


}
