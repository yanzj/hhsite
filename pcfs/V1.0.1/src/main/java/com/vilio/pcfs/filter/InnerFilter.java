package com.vilio.pcfs.filter;

import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.util.JsonUtil;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： InnerFilter<br>
 * 功能：对内过滤器定义<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
public class InnerFilter implements Filter {
    //FilterConfig可用于访问Filter的配置信息
    private FilterConfig config;

    private static Logger logger = Logger.getLogger(InnerFilter.class);

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
        response.setContentType("application/json; charset=utf-8");
        //如果报错定义返回的参数结构
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultHead = new HashMap<String, Object>();
        Map<String, Object> resultBody = new HashMap<String, Object>();
        resultMap.put(GlobParam.PARAM_MESSAGE_HEAD, resultHead);
        resultMap.put(GlobParam.PARAM_MESSAGE_BODY, resultBody);
        HttpServletRequest requ = (HttpServletRequest) request;
        // 接收参数
        String buffer;
        DataInputStream in = null;
        try {
            int totalbytes = request.getContentLength();
            byte[] dataOrigin = new byte[totalbytes];
            in = new DataInputStream(request.getInputStream());
            in.readFully(dataOrigin);

            buffer = new String(dataOrigin,"UTF-8");
        } finally {
            if (in!=null) {
                in.close();
            }
        }
        String functionNo = "";
        String sourceSystem = "";
        //校验接受是否为空
        if (buffer != null && !"".equals(buffer.toString())) {
            logger.info("接收到的参数为："+buffer);
            Map<String, Object> params = null;
            try {
                params = (Map<String, Object>) JsonUtil.jsonToMap(buffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("json解析失败！");
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, "json解析失败");
                PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
                return;
            }
            Map<String, Object> head = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_HEAD);
            functionNo = String.valueOf(head.get(GlobParam.PARAM_FUNCTION_NO) == null ? "" : head.get(GlobParam.PARAM_FUNCTION_NO)).toLowerCase();
            sourceSystem = String.valueOf(head.get(GlobParam.PARAM_SYSTEM_NO) == null ? "" : head.get(GlobParam.PARAM_SYSTEM_NO));
            requ.setAttribute("params", params);
            resultHead.putAll(head);
        } else {
            logger.error("接受参数异常！");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }
        //校验公共参数
        if (!JudgeUtil.isNull(functionNo)||!JudgeUtil.isNull(sourceSystem)) {
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.PUBLIC_PARAM_FAIL);
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.PUBLIC_PARAM_FAIL));
            PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }
        chain.doFilter(requ, response);
        return;
    }

}