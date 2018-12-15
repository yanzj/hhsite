package com.vilio.comm.filter;

import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.pojo.SubSystem;
import com.vilio.comm.util.JsonUtil;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.comm.util.SpringContextUtil;
import com.vilio.comm.util.UmUtil;
import com.vilio.custom.dao.CustomSubSystemDao;
import com.vilio.custom.pojo.CustomSubSystem;
import com.vilio.um.dao.UmSubSystemDao;
import com.vilio.um.pojo.UmSubSystem;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： AuthorityFilter<br>
 * 功能：全局过滤器定义<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月14日<br>
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
        resultMap.put(GlobParam.PARAM_MESSAGE_HEAD, resultHead);
        resultMap.put(GlobParam.PARAM_MESSAGE_BODY, resultBody);
        HttpServletRequest requ = (HttpServletRequest) request;
        // 接收参数
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = request.getReader();
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
        String systemId = "";
        String functionType = "";
        //校验接受是否为空
        if (buffer != null && !"".equals(buffer.toString())) {
            logger.info("接收到的参数为："+buffer);
            Map<String, Object> params = (Map<String, Object>) JsonUtil.jsonToMap(buffer.toString());
            //判断传过来的报文结构是否正确(body、head是否存在，是否为空，是否是字符串)
            if (!params.containsKey(GlobParam.PARAM_MESSAGE_HEAD) || params.get(GlobParam.PARAM_MESSAGE_HEAD) == null
                    || params.get(GlobParam.PARAM_MESSAGE_HEAD) instanceof String
                    || !params.containsKey(GlobParam.PARAM_MESSAGE_BODY) || params.get(GlobParam.PARAM_MESSAGE_BODY) == null
                    || params.get(GlobParam.PARAM_MESSAGE_BODY) instanceof String) {
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "0006");
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get("0006"));
                UmUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
                return;
            }
            Map<String, Object> head = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_HEAD);
            functionNo = head.get(GlobParam.PARAM_FUNCTION_NO) == null ? null : head.get(GlobParam.PARAM_FUNCTION_NO).toString();
            systemId = head.get(GlobParam.PARAM_SYSTEM_ID) == null ? null : head.get(GlobParam.PARAM_SYSTEM_ID).toString();
            functionType = head.get(GlobParam.PARAM_FUNCTION_TYPE) == null ? null : head.get(GlobParam.PARAM_FUNCTION_TYPE).toString();
            requ.setAttribute("params", params);
            resultHead.putAll(head);
        } else {
            logger.error("接受参数异常！");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "9999");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get("9999"));
            UmUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }
        //校验公共参数
        if (!JudgeUtil.isNull(functionNo) || !JudgeUtil.isNull(systemId) || !JudgeUtil.isNull(functionType)) {
            logger.error("公共参数校验失败");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "0005");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get("0005"));
            UmUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }

        SubSystem subSystem = null;
        //判断当前系统是否有效
        if (GlobParam.umFunctionType.equals(functionType)) {
            //um系统的请求
            UmSubSystemDao umSubSystemDao = (UmSubSystemDao) SpringContextUtil.getBean("umSubSystemDao");
            //um系统的请求
            UmSubSystem qrySystemParam = new UmSubSystem();
            qrySystemParam.setSystemId(systemId);
            subSystem = umSubSystemDao.querySubSystem(qrySystemParam);
        }else if (GlobParam.customFunctionType.equals(functionType)){
            //custom系统过来的请求
            //um系统的请求
            CustomSubSystemDao customSubSystemDao = (CustomSubSystemDao) SpringContextUtil.getBean("customSubSystemDao");
            //um系统的请求
            CustomSubSystem qrySystemParam = new CustomSubSystem();
            qrySystemParam.setSystemId(systemId);
            subSystem = customSubSystemDao.querySubSystem(qrySystemParam);
        }else{
            //暂时不支持的请求类型
            logger.error("不支持的请求类型：" + functionType);
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "9999");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, "不支持的请求：" + functionType);
            UmUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }
        //开始判断子系统状态
        if (subSystem == null || GlobDict.system_status_delete.getKey().equals(subSystem.getStatus())) {
            logger.error("子系统不存在");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "9995");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get("9995"));
            UmUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        } else if (GlobDict.system_status_disable.getKey().equals(subSystem.getStatus())) {
            logger.error("子系统已停用");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "9993");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get("9993"));
            UmUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        } else if (!GlobDict.system_status_valid.getKey().equals(subSystem.getStatus())) {
            logger.error("子系统状态不正确");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "9994");
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get("9994"));
            UmUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }
        chain.doFilter(requ, response);
        return;
    }

}