package com.vilio.nlbs.filter;

import antlr.StringUtils;
import com.github.pagehelper.StringUtil;
import com.vilio.nlbs.apply.controller.ApplyController;
import com.vilio.nlbs.commonMapper.pojo.NlbsUser;
import com.vilio.nlbs.login.service.LoginService;
import com.vilio.nlbs.user.dao.UserDao;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.HHInterface;
import com.vilio.nlbs.util.ReturnCode;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/6.
 */
public class AuthorityFilter implements Filter {
    //FilterConfig可用于访问Filter的配置信息
    private FilterConfig config;
    LoginService loginService;

    private static Logger logger = Logger.getLogger(ApplyController.class);

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

        //获取该Filter的配置参数
        String encoding = config.getInitParameter("encoding");//编码规则
        Integer loginTimeout = new Integer(config.getInitParameter("loginTimeout"));//有效时间

        //设置request编码用的字符集
        request.setCharacterEncoding(encoding);
        HttpServletRequest requ = (HttpServletRequest) request;
        //获取客户请求的接口
        String requestPath = requ.getServletPath();

        //根据请求URL，判断是否需要复制请求进行过滤，如果是内部系统间调用，则直接放行
        if (isInternalRequest(requestPath)) {

            //复制请求
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(requ);
            String body = HttpHelper.getBodyString(requestWrapper);
            //如果是POST请求则需要获取 param 参数
            String param = URLDecoder.decode(body, "utf-8");
//            //json串 转换为Map
//            if (param != null && param.contains("=")) {
//                param = param.split("=")[1];
//            }
            String userNo = "";
            String clientTimestamp = "";
            String functionNo = "";
            try {
                if (StringUtil.isNotEmpty(param)) {
                    JSONObject paramJSON = JSONObject.fromObject(param);
                    JSONObject headJson = (JSONObject) paramJSON.get(Fields.PARAM_MESSAGE_HEAD);
                    Object userNoJS = headJson.get(Fields.PARAM_USER_NO);
                    Object clientTimestampJS = headJson.get(Fields.PARAM_CLIENTTIMESTAMP);
                    Object functionNoJS = headJson.get(Fields.PARAM_FUNCTION_NO);

                    userNo = userNoJS == null ? null : userNoJS.toString();
                    clientTimestamp = clientTimestampJS == null ? null : clientTimestampJS.toString();
                    functionNo = functionNoJS == null ? null : functionNoJS.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //如果不是请求登录，就要看是不是登录过
            if (!isFilterRequest(functionNo) && !"/".equals(requestPath)) {
                String reqMethod = requ.getMethod();
                if ("POST".equals(reqMethod)) {
                    logger.info("用户【" + userNo + "】调用功能号【" + HHInterface.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，开始验证token是否有效！！！");
                    //获取后台服务对象，不能直接注入
                    if (loginService == null) {
                        ServletContext sc = requ.getSession().getServletContext();
                        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
                        if (cxt != null && cxt.getBean("loginService") != null) {
                            loginService = (LoginService) cxt.getBean("loginService");
                        }
                    }
                    Map sessionMap = new HashMap();
                    Object userSession = null;
                    try {
                        sessionMap.put(Fields.PARAM_USER_NO, userNo);
                        userSession = loginService.getSession(sessionMap);

                        //session范围的user为null，即表明没有登录
                        if (userSession == null) {
                            response.setContentType("application/json; charset=utf-8");
                            String write = createDisableTokenReturn();
                            response.setCharacterEncoding("utf-8");
                            response.getWriter().write(write);
                            logger.info("用户【" + userNo + "】调用功能号【" + HHInterface.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，验证token无效（用户未登录-session为空）！！！");
                            return;
                        } else {

                            Map userMap = (Map) userSession;

                            Long lastAccessTime = new Long((String) userMap.get("systemTimestamp"));
                            if (clientTimestamp == null || !clientTimestamp.equals((String) userMap.get("clientTimestamp"))) {
                                //浏览器端的时间戳，和登录时的时间戳不一致
                                response.setContentType("application/json");
                                String write = createDisableTokenReturn();
                                response.setCharacterEncoding("utf-8");
                                response.getWriter().write(write);
                                logger.info("用户【" + userNo + "】调用功能号【" + HHInterface.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，验证token无效（session不一致）！！！");
                                return;
                            } else if ((System.currentTimeMillis() - lastAccessTime) > loginTimeout) {
                                //超时退出到登录页面
                                response.setContentType("application/json");
                                String write = createDisableTokenReturn();
                                response.setCharacterEncoding("utf-8");
                                response.getWriter().write(write);
                                logger.info("用户【" + userNo + "】调用功能号【" + HHInterface.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，验证token无效（session超时）！！！");
                                return;
                            } else {//没超时，则重置时间
                                sessionMap.put(Fields.PARAM_USER_NO, userNo);
                                sessionMap.put("systemTimestamp", System.currentTimeMillis() + "");
                                sessionMap.put("clientTimestamp", clientTimestamp);
                                loginService.setSession(sessionMap);
                                logger.info("用户【" + userNo + "】调用功能号【" + HHInterface.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，验证token有效（session更新）！！！");
                                //放行
                                chain.doFilter(requestWrapper, response);
                                return;
                            }
                        }
                    } catch (Exception e) {

                    }
                }//如果是get请求直接放行
            }
            chain.doFilter(requestWrapper, response);
            return;
        }
        chain.doFilter(request, response);
        return;
    }

    /**
     * 是否是过滤请求
     *
     * @param functionNo
     * @return
     */
    private boolean isFilterRequest(String functionNo) {

        if (HHInterface.HH_USER_LOGIN.getFunctionNo().equals(functionNo) || HHInterface.HH_RECEIVE_MESSAGE.getFunctionNo().equals(functionNo)) {
            return true;
        }

        return false;
    }

    private boolean isInternalRequest(String requestPath){

        if("/backendapi".equals(requestPath)) {
            return true;
        }
        return false;
    }

    private String createDisableTokenReturn(){
        Map returnMap = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();

        headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_DISABLE_TOKEN);
        headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "token失效！");

        returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);

        return JSONObject.fromObject(returnMap).toString();
    }
}