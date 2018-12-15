package com.vilio.nlbs.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiezhilei on 2017/8/30.
 */
public class HttpForward2HttpsFilter implements Filter{
    private static Logger logger = Logger.getLogger(HttpForward2HttpsFilter.class);
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        String url = request.getRequestURL().toString();
        String proto = request.getHeader("x-forwarded-proto");

        if(null != proto && proto.equals("http")){
            url = url.replace("http:","https:");
            logger.info("~~~~~~~~~~~~~~~~~~~~~changed url is:" + url);
            response.sendRedirect(url);
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
