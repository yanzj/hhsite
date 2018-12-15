package com.vilio.comm.glob;

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

    @Value("#{umProperties}")
    public void setUmConfig(Map umProperties) {
        GlobParam.umLoginError = Integer.parseInt(umProperties.get("umLoginError").toString());
        GlobParam.customLoginError = Integer.parseInt(umProperties.get("customLoginError").toString());
        GlobParam.umFunctionType = umProperties.get("umFunctionType").toString();
        GlobParam.customFunctionType = umProperties.get("customFunctionType").toString();
        GlobParam.customHashLockTime = umProperties.get("customHashLockTime").toString();
        GlobParam.umHashLockTime = umProperties.get("umHashLockTime").toString();
        GlobParam.customTransError = Integer.parseInt(umProperties.get("customTransError").toString());
        GlobParam.customTransLockTime = umProperties.get("customTransLockTime").toString();
    }

    public final void init(ServletConfig config) throws ServletException {
    }

}
