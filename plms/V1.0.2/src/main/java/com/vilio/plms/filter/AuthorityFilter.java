package com.vilio.plms.filter;

import com.github.pagehelper.StringUtil;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.InterfaceCode;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.LoginService;
import com.vilio.plms.util.SpringContextUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
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

        //获取该Filter的配置参数
        String encoding = config.getInitParameter("encoding");//编码规则
        Integer loginTimeout = new Integer(config.getInitParameter("loginTimeout"));//有效时间

        //设置request编码用的字符集
        request.setCharacterEncoding(encoding);
        HttpServletRequest requ = (HttpServletRequest) request;
        //获取客户请求的接口
        String requestPath = requ.getServletPath();

        // 白名单IP过滤
        if(isWhiteListIpAddress(requ)){
            chain.doFilter(request, response);
            return;
        }


        //根据请求URL，判断是否需要复制请求进行过滤，如果是内部系统间调用，则直接放行
        if (isInternalRequest(requestPath)) {

            //复制请求
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(requ);
            String body = HttpHelper.getBodyString(requestWrapper);
            //使用 %25 替换字符串中的%
            String param = body.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            //如果是POST请求则需要获取 param 参数
            param = URLDecoder.decode(param, "utf-8");
//            //json串 转换为Map
//            if (param != null && param.contains("=")) {
//                param = param.split("=")[1];
//            }
            String userNo = "";
            String clientTimestamp = "";
            String functionNo = "";
            try {
                if (StringUtil.isNotEmpty(param)) {
                    JSONObject paramJSON = JSONObject.fromObject(body);
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
                    logger.info("用户【" + userNo + "】调用功能号【" + InterfaceCode.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，开始验证token是否有效！！！");
                    //获取后台服务对象，不能直接注入
                    if (loginService == null) {
//                        ServletContext sc = requ.getSession().getServletContext();
//                        XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sc);
//                        if (cxt != null && cxt.getBean("loginService") != null) {
//                            loginService = (LoginService) cxt.getBean("loginService");
//                        }
                        loginService = (LoginService) SpringContextUtil.getBean("loginService");
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
                            logger.info("用户【" + userNo + "】调用功能号【" + InterfaceCode.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，验证token无效（用户未登录-session为空）！！！");
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
                                logger.info("用户【" + userNo + "】调用功能号【" + InterfaceCode.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，验证token无效（session不一致）！！！");
                                return;
                            } else if ((System.currentTimeMillis() - lastAccessTime) > loginTimeout) {
                                //超时退出到登录页面
                                response.setContentType("application/json");
                                String write = createDisableTokenReturn();
                                response.setCharacterEncoding("utf-8");
                                response.getWriter().write(write);
                                logger.info("用户【" + userNo + "】调用功能号【" + InterfaceCode.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，验证token无效（session超时）！！！");
                                return;
                            } else {//没超时，则重置时间
                                sessionMap.put(Fields.PARAM_USER_NO, userNo);
                                sessionMap.put("systemTimestamp", System.currentTimeMillis() + "");
                                sessionMap.put("clientTimestamp", clientTimestamp);
                                loginService.setSession(sessionMap);
                                logger.info("用户【" + userNo + "】调用功能号【" + InterfaceCode.getHHInterface(functionNo).toString() + "】，登录token为【" + clientTimestamp + "】，验证token有效（session更新）！！！");
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

        if (InterfaceCode.PLMS_USER_LOGIN.getFunctionNo().equals(functionNo)) {
            return true;
        }

        return false;
    }

    private boolean isInternalRequest(String requestPath){

        if("/backendapi.json".equals(requestPath)) {
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


    public static boolean isWhiteListIpAddress(HttpServletRequest request){

        /*String ipAddress = request.getHeader("x-forwarded-for");

        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknow".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();

            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡获取本机配置的IP地址
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
        if(null != ipAddress && ipAddress.length() > 15){
            //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",") > 0){
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        if("127.0.0.1".equals(ipAddress) || "192.168.0.133".equals(ipAddress) || "192.168.0.136".equals(ipAddress)){
            return true;
        }*/

        return false;
    }
}