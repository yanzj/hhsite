package com.vilio.plms.glob;

import com.vilio.plms.util.JsonUtil;
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

    @Value("#{errorCodeProperties}")
    public void setErrorCode(Map errorCodeProperties) {
        GlobParam.ERROR_CODE = errorCodeProperties;
    }

    @Value("#{configProperties}")
    public void setConfigProperties(Map configProperties) {
        GlobParam.fmsUrl = configProperties.get("fms.url").toString();
        GlobParam.mpsUrl = configProperties.get("mps.url").toString();
        GlobParam.umUrl = configProperties.get("um.url").toString();
        GlobParam.fmsPath = configProperties.get("fms.filePath").toString();
        GlobParam.downloadTemp = configProperties.get("downloadTemp").toString();


        String repaymentTimeListStr = configProperties.get("repayment.timeList").toString();
        GlobParam.repaymentTimeListStr = repaymentTimeListStr.split(",");

        GlobParam.repaymentTitle = configProperties.get("repayment.title").toString();
        GlobParam.repaymentTicker = configProperties.get("repayment.ticker").toString();
        GlobParam.repaymentSubtitle = configProperties.get("repayment.subtitle").toString();
        GlobParam.repaymentContent = configProperties.get("repayment.content").toString();

        GlobParam.repaymentSendSystem = (Map<String, Object>) JsonUtil.jsonToMap(configProperties.get("repayment.sendSystem").toString());


        String overdueTimeListStr = configProperties.get("overdue.timeList").toString();
        GlobParam.overdueTimeListStr = overdueTimeListStr.split(",");
        GlobParam.overdueTitle = configProperties.get("overdue.title").toString();
        GlobParam.overdueTicker = configProperties.get("overdue.ticker").toString();
        GlobParam.overdueSubtitle = configProperties.get("overdue.subtitle").toString();
        GlobParam.overdueContent = configProperties.get("overdue.content").toString();

        GlobParam.overdueSendSystem = (Map<String, Object>) JsonUtil.jsonToMap(configProperties.get("overdue.sendSystem").toString());

        GlobParam.repaymentSmsSignNo = (Map<String, Object>) JsonUtil.jsonToMap(configProperties.get("repayment.sms.signNo").toString());

        GlobParam.overdueSmsSignNo = (Map<String, Object>) JsonUtil.jsonToMap(configProperties.get("overdue.sms.signNo").toString());


        GlobParam.repaymentSmsTemplateCode = configProperties.get("repayment.sms.templateCode").toString();
        GlobParam.overdueSmsTemplateCode = configProperties.get("overdue.sms.templateCode").toString();
        GlobParam.mpsSmsFunctionNo = configProperties.get("mps.sms.functionNo").toString();
        GlobParam.mpsSmsType = configProperties.get("mps.sms.type").toString();



        GlobParam.monitorToUserList = configProperties.get("monitor.toUserList").toString();
        GlobParam.monitorSubject = configProperties.get("monitor.subject").toString();
        GlobParam.monitorDisplayName = configProperties.get("monitor.displayName").toString();
        GlobParam.monitorUserName = configProperties.get("monitor.userName").toString();
        GlobParam.monitorContent = configProperties.get("monitor.content").toString();
        GlobParam.monitorSenderName = configProperties.get("monitor.senderName").toString();
        GlobParam.monitorPassword = configProperties.get("monitor.password").toString();
        GlobParam.monitorToCcList = configProperties.get("monitor.toCcList").toString();
        GlobParam.monitorSwitch = configProperties.get("monitor.switch").toString();

    }


    public final void init(ServletConfig config) throws ServletException {
    }

    /**
     * 加载错误码信息到缓存
     */
    private void loadErrorCode(String filePath) {
    }


}
