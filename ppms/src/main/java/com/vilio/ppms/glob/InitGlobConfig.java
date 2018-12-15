package com.vilio.ppms.glob;

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
        GlobParam.fmsPath = configProperties.get("fms.filePath").toString();
    }


    public final void init(ServletConfig config) throws ServletException {
    }

    /**
     * 加载错误码信息到缓存
     */
    private void loadErrorCode(String filePath) {
    }


}
