package com.vilio.nlbs.login.controller;

import com.vilio.nlbs.login.service.LoginService;
import com.vilio.nlbs.util.CommonUtil;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.ReturnCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2016/12/29.
 */
@Controller
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @Resource
    private LoginService loginService;

    @RequestMapping(value = "/api/login",method = RequestMethod.POST)
    @ResponseBody
    public Map login(@RequestBody Map paramMap) throws Exception{

        logger.info("用户登录接口(LoginController.login)被调用开始,入参：" + paramMap );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            //调用服务，登录
            bodyMap = loginService.login(bodyMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
            //整理异常情况下的出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回
        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        logger.info("用户登录接口(LoginController.login)被调用结束,输出结果：" + result);
        return result;

    }

    @RequestMapping(value = "/api/changePsw4FirstLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map changePsw4FirstLogin(@RequestBody Map paramMap){

        logger.info("用户初次登录修改密码接口(LoginController.changePsw4FirstLogin)被调用开始,入参：" + paramMap );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            //调用服务，初次登陆修改密码
            bodyMap = loginService.changePsw4FirstLogin(bodyMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
            //整理异常情况下的出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回
        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        logger.info("用户初次登录修改密码接口(LoginController.changePsw4FirstLogin)被调用结束,输出结果：" + result);

        return result;
    }

    @RequestMapping(value = "/api/changeInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map changeInfo(@RequestBody Map paramMap){

        logger.info("用户修改个人信息（密码）接口(LoginController.changeInfo)被调用开始,入参：" + paramMap );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            //调用服务，修改个人信息（密码）
            bodyMap = loginService.changeInfo(bodyMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
            //整理异常情况下的出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回
        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        logger.info("用户修改个人信息（密码）接口(LoginController.changeInfo)被调用结束,输出结果：" + result);

        return result;
    }

    @RequestMapping(value = "/api/getMenu",method = RequestMethod.POST)
    @ResponseBody
    public Map getMenu(@RequestBody Map paramMap) throws Exception{

        logger.info("获取菜单数据(LoginController.getMenu)被调用开始,入参：" + paramMap );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            //调用服务，获取菜单
            bodyMap = loginService.getMenu(bodyMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
            //整理异常情况下的出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回
        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        logger.info("获取菜单数据(LoginController.getMenu)被调用结束,输出结果：" + result);

        return result;
    }

    @RequestMapping(value = "/api/logout",method = RequestMethod.POST)
    @ResponseBody
    public Map logout(@RequestBody Map paramMap) throws Exception{


        logger.info("退出系统(LoginController.getMenu)被调用开始,入参：" + paramMap );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            //调用服务，退出登录
            bodyMap = loginService.logout(bodyMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
            //整理异常情况下的出参
            bodyMap.clear();
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回
        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        logger.info("退出系统(LoginController.getMenu)被调用结束,输出结果：" + result);

        return result;
    }

}
