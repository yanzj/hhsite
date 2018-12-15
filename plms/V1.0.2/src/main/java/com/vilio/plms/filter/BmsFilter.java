package com.vilio.plms.filter;

import com.vilio.plms.service.base.LoginService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by martin on 2017/9/5.
 */
public class BmsFilter implements Filter {
    //FilterConfig可用于访问Filter的配置信息
    private FilterConfig config;
    LoginService loginService;

    private static Logger logger = Logger.getLogger(AuthorityFilter.class);

    //实现初始化方法
    public void init(FilterConfig config) {
        this.config = config;
    }

    //实现销毁方法
    public void destroy() {
        this.config = null;
    }

    //执行过滤的核心方法
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置request字符编码
        request.setCharacterEncoding("utf-8");
        // 将request转换为重写后的Request对象
        HttpServletRequest request2 = (HttpServletRequest) request;
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request2.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request2.getHeader(key);
            map.put(key, value);
        }
        System.out.println(map);

        String method= request2.getMethod();
        System.out.println("method"+": "+method);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
                } else {
                stringBuilder.append("");
               }
             } catch (IOException ex) {
            throw ex;
            } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    } catch (IOException ex) {
                    throw ex;
                    }
                 }
            }
        System.out.println(new String((stringBuilder.toString()).getBytes(),"utf-8"));
        chain.doFilter(request, response);
        return;
    }

}
