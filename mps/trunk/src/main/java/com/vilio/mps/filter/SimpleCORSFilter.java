package com.vilio.mps.filter;

import com.vilio.mps.glob.ConfigInfo;
import com.vilio.mps.glob.Fields;
import com.vilio.mps.util.CommonUtil;
import com.vilio.mps.util.JsonUtil;
import com.vilio.mps.util.JudgeUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/18.
 */
public class SimpleCORSFilter implements Filter{

    private static Logger logger = Logger.getLogger(SimpleCORSFilter.class);

    //执行过滤的核心方法
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        res.setContentType("application/json; charset=utf-8");
        //如果报错定义返回的参数结构
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultHead = new HashMap<String, Object>();
        Map<String, Object> resultBody = new HashMap<String, Object>();
        resultMap.put(Fields.PARAM_MESSAGE_HEAD, resultHead);
        resultMap.put(Fields.PARAM_MESSAGE_BODY, resultBody);
        HttpServletRequest requ = (HttpServletRequest) req;
        // 接收参数
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = req.getReader();
            String str;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        String functionNo = "";
        //校验接受是否为空
        if (buffer != null && !"".equals(buffer.toString())) {
            Map<String, Object> params = (Map<String, Object>) JsonUtil.jsonToMap(buffer.toString());
            //判断传过来的报文结构是否正确(body、head是否存在，是否为空，是否是字符串)
            if (!params.containsKey(Fields.PARAM_MESSAGE_HEAD) || params.get(Fields.PARAM_MESSAGE_HEAD) == null
                    || params.get(Fields.PARAM_MESSAGE_HEAD) instanceof String
                    || !params.containsKey(Fields.PARAM_MESSAGE_BODY) || params.get(Fields.PARAM_MESSAGE_BODY) == null
                    || params.get(Fields.PARAM_MESSAGE_BODY) instanceof String) {
                resultHead.put(Fields.PARAM_MESSAGE_ERR_CODE, "0006");
                resultHead.put(Fields.PARAM_MESSAGE_ERR_MESG, ConfigInfo.ERROR_CODE.get("0006"));
                CommonUtil.returnData((HttpServletRequest) req, (HttpServletResponse) res, resultMap);
                return;
            }
            Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
            functionNo = head.get(Fields.PARAM_FUNCTION_NO) == null ? null : head.get(Fields.PARAM_FUNCTION_NO).toString();
            requ.setAttribute("params", params);
            resultHead.putAll(head);
        } else {
            logger.error("接受参数异常！");
            resultHead.put(Fields.PARAM_MESSAGE_ERR_CODE, "9999");
            resultHead.put(Fields.PARAM_MESSAGE_ERR_MESG, ConfigInfo.ERROR_CODE.get("9999"));
            CommonUtil.returnData((HttpServletRequest) req, (HttpServletResponse) res, resultMap);
            return;
        }
        //校验公共参数
        if (!JudgeUtil.isNull(functionNo)) {
            logger.error("公共参数校验失败");
            resultHead.put(Fields.PARAM_MESSAGE_ERR_CODE, "0005");
            resultHead.put(Fields.PARAM_MESSAGE_ERR_MESG, ConfigInfo.ERROR_CODE.get("0005"));
            CommonUtil.returnData((HttpServletRequest) req, (HttpServletResponse) res, resultMap);
            return;
        }
        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}
