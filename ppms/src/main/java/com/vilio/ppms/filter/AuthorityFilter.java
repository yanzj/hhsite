package com.vilio.ppms.filter;

import com.vilio.ppms.dao.common.RequestLogMapper;
import com.vilio.ppms.glob.Fields;
import com.vilio.ppms.glob.GlobDict;
import com.vilio.ppms.glob.GlobParam;
import com.vilio.ppms.glob.ReturnCode;
import com.vilio.ppms.pojo.common.RequestLogWithBLOBs;
import com.vilio.ppms.util.JsonUtil;
import com.vilio.ppms.util.JudgeUtil;
import com.vilio.ppms.util.PpmsUtil;
import com.vilio.ppms.util.SpringContextUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 类名： AuthorityFilter<br>
 * 功能：全局过滤器定义<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月19日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
public class AuthorityFilter implements Filter {
    //FilterConfig可用于访问Filter的配置信息
    private FilterConfig config;

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
        response.setContentType("application/json; charset=utf-8");
        //如果报错定义返回的参数结构
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultHead = new HashMap<String, Object>();
        Map<String, Object> resultBody = new HashMap<String, Object>();
        resultMap.put(Fields.PARAM_MESSAGE_HEAD, resultHead);
        resultMap.put(Fields.PARAM_MESSAGE_BODY, resultBody);
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
        //入接口日志表
        RequestLogWithBLOBs requestLogWithBLOBs = new RequestLogWithBLOBs();
        requestLogWithBLOBs.setCode(UUID.randomUUID().toString());
        requestLogWithBLOBs.setRequestMsg(ObjectUtils.toString(buffer));
        requestLogWithBLOBs.setStatus(GlobDict.status_valid.getKey());
        RequestLogMapper requestLogMapper = (RequestLogMapper)SpringContextUtil.getBean("RequestLogMapper");
        int ret = requestLogMapper.insert(requestLogWithBLOBs);
        if (ret<=0) {
            throw new IOException("系统操作，请重试");
        }
        //保留日志信息，后面更新返回信息
        request.setAttribute("requestLogWithBLOBs", requestLogWithBLOBs);
        String sourceSystem = "";
        String functionNo = "";
        //校验接受是否为空
        if (buffer != null && !"".equals(buffer.toString())) {
            logger.info("接收到的参数为："+buffer);
            Map<String, Object> params = null;
            try {
                params = (Map<String, Object>) JsonUtil.jsonToMap(buffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("json解析失败！");
                resultHead.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                resultHead.put(Fields.PARAM_MESSAGE_ERR_MESG, "json解析失败");
                PpmsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
                return;
            }
            Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
            sourceSystem = ObjectUtils.toString(head.get(Fields.PARAM_SOURCE_SYSTEM));
            functionNo = ObjectUtils.toString(head.get(Fields.PARAM_FUNCTION_NO));
            request.setAttribute("params", params);
        } else {
            logger.error("接受参数异常！");
            resultHead.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            resultHead.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            PpmsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }
        //校验公共参数
        if (!JudgeUtil.isNull(functionNo) || !JudgeUtil.isNull(sourceSystem)) {
            resultHead.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.PUBLIC_PARAM_FAIL);
            resultHead.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.PUBLIC_PARAM_FAIL));
            PpmsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }
        chain.doFilter(request, response);
        return;
    }

}