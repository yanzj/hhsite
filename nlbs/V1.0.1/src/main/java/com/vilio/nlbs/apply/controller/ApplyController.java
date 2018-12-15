package com.vilio.nlbs.apply.controller;

import com.vilio.nlbs.apply.service.ApplyService;
import com.vilio.nlbs.commonMapper.dao.NlbsUserMapper;
import com.vilio.nlbs.user.dao.UserDao;
import com.vilio.nlbs.util.CommonUtil;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.ReturnCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/13.
 */
@Controller("applyController")
public class ApplyController implements ServletContextAware {

    private static Logger logger = Logger.getLogger(ApplyController.class);

    @Resource
    ApplyService applyService;

    @Resource
    NlbsUserMapper nlbsUserMapper;

    //Spring这里是通过实现ServletContextAware接口来注入ServletContext对象
    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @RequestMapping(value = "/api/initApplyInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map initApplyInfo(@RequestBody Map paramMap) throws Exception{

        logger.info("进件初始化接口(ApplyController.initApplyInfo)被调用开始,入参：" + paramMap );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            //调用服务，获取初始化信息
            result = applyService.initApplyInfo(bodyMap);
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
        logger.info("进件初始化接口(ApplyController.initApplyInfo)被调用结束,输出结果：" + result);
        return result;

    }






    @RequestMapping(value = "/api/chuliantest",method = RequestMethod.POST)
    @ResponseBody
    public Map chuliantest(@RequestBody Map paramMap) throws Exception{

        logger.info("测试(ApplyController.chuliantest)被调用开始,入参：" + paramMap );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            //调用服务，获取初始化信息
            List userList = nlbsUserMapper.selectAll();
            bodyMap.put("userList", userList);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "");
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
        logger.info("测试(ApplyController.chuliantest)被调用结束,输出结果：" + result);
        return result;

    }




}
