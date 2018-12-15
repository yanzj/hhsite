package com.vilio.fms.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Created by xiezhilei on 2017/1/18.
 */
public class SimpleCORSFilter implements Filter{
    Logger logger = Logger.getLogger(SimpleCORSFilter.class);
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.info("*****************************************************************************");
        logger.info("【文件关联系统(fms)】接收到请求(SimpleCORSFilter.doFilter)");
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-type");
        chain.doFilter(req, res);
        logger.info("*****************************************************************************");
        logger.info("【文件关联系统(fms)】请求处理完成(SimpleCORSFilter.doFilter)");
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
