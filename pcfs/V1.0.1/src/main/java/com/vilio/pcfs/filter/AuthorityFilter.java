package com.vilio.pcfs.filter;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.LoginInfo;
import com.vilio.pcfs.service.LoginComn;
import com.vilio.pcfs.util.JsonUtil;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import com.vilio.pcfs.util.SpringContextUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
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
 * 注：暂时不用客户端时间戳验证，登录生成token进行单点登录。过滤器自动生成时间戳与系统中的数据库中的时间戳进行超时验证，因为如果利用客户端生成时间戳验证不安全
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
        Long loginTimeout = new Long(config.getInitParameter("loginTimeout"));//有效时间
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
//        StringBuffer buffer = new StringBuffer();
//        BufferedReader reader = null;
//        try {
//            reader = request.getReader();
//            String str;
//            while ((str = reader.readLine()) != null) {
//                buffer.append(str);
//            }
//        } finally {
//            if (reader != null) {
//                reader.close();
//            }
//        }
        String userId = "";
        String functionNo = "";
        String token = "";
        String channel = "";
        String deviceToken = "";
        String appVersion = "";
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
            userId = String.valueOf(head.get(GlobParam.PARAM_USER_ID) == null ? "" : head.get(GlobParam.PARAM_USER_ID));
            functionNo = String.valueOf(head.get(GlobParam.PARAM_FUNCTION_NO) == null ? "" : head.get(GlobParam.PARAM_FUNCTION_NO)).toLowerCase();
            token = String.valueOf(head.get(GlobParam.PARAM_TOKEN) == null ? "" : head.get(GlobParam.PARAM_TOKEN));
            channel = String.valueOf(head.get(GlobParam.PARAM_CHANNEL) == null ? "" : head.get(GlobParam.PARAM_CHANNEL));
            deviceToken = String.valueOf(head.get(GlobParam.PARAM_DEVICE_TOKEN) == null ? "" : head.get(GlobParam.PARAM_DEVICE_TOKEN));
            appVersion = String.valueOf(head.get(GlobParam.PARAM_APP_VERSION) == null ? "" : head.get(GlobParam.PARAM_APP_VERSION));
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
        if (!JudgeUtil.isNull(functionNo)||!JudgeUtil.isNull(channel)||!JudgeUtil.isNull(deviceToken)||!JudgeUtil.isNull(appVersion)||!JudgeUtil.isNull(sourceSystem)) {
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.PUBLIC_PARAM_FAIL);
            resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.PUBLIC_PARAM_FAIL));
            PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            return;
        }
        //判断是否过滤校验时间戳和token
        if (!isFilterRequest(functionNo)) {
            //校验
            if (!JudgeUtil.isNull(userId) || !JudgeUtil.isNull(token)) {
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.PUBLIC_PARAM_FAIL);
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.PUBLIC_PARAM_FAIL));
                PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
            }
            logger.info("用户【" + userId + "】调用功能号【" + functionNo + "】，登录token为【" + token + "】，开始验证token是否有效！！！");
            //获取session
            LoginComn loginComn = (LoginComn) SpringContextUtil.getBean("loginComn");
            LoginInfo qrySession = new LoginInfo();
            qrySession.setUserId(userId);
            //查询非首次登录session
            //qrySession.setFirstLogin(GlobDict.un_first_login.getKey());
            LoginInfo session = loginComn.getSession(qrySession);
            if (session == null) {
                logger.error("用户登录信息未查到");
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_NOLOGIN);
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.USER_NOLOGIN));
                PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
                return;
            }
            //判断是否单点登录
            if (!token.equals(session.getToken())) {
                //token不相同，已在其他机器登录，需重新登录
                logger.error("令牌与session中的令牌不相同，需重新登录获取令牌");
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.USER_OTHER_MACHINE_LOGIN);
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.USER_OTHER_MACHINE_LOGIN));
                PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
                return;
            }
            //判断登录是否过期
            Long lastAccessTime = Long.parseLong(session.getSystemTimestamp());
            Long nowAccessTime = System.currentTimeMillis();
            if ((System.currentTimeMillis() - lastAccessTime) > loginTimeout) {
                //登录超时，请重新登录
                logger.error("登录超时，需重新登录");
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.LOGIN_TIMEOUT);
                resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.LOGIN_TIMEOUT));
                PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
                return;
            } else {
                //没有超时，则重置系统时间戳
                session.setSystemTimestamp(String.valueOf(nowAccessTime));
                session.setDeviceToken(deviceToken);
                session.setChannel(channel);
                try {
                    loginComn.setSession(session);
                } catch (ErrorException e) {
                    logger.error("更新session出错");
                    resultHead.put(GlobParam.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                    resultHead.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
                    PcfsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, resultMap);
                    return;
                }
            }
        } else {
            //不校验
        }
        chain.doFilter(requ, response);
        return;
    }

    private boolean isFilterRequest(String functionNo) {
        String noFilterNo = config.getInitParameter("noFilterNo");
        String[] noFilterArry = noFilterNo.split(",");
        for (String no : noFilterArry) {
            if (no.equals(functionNo)) {
                return true;
            }
        }
        return false;
    }

}