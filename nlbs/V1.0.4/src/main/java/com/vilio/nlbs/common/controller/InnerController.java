package com.vilio.nlbs.common.controller;/**
 * Created by dell on 2017/9/14/0014.
 */

import com.vilio.nlbs.common.service.PretreatmentService;
import com.vilio.nlbs.util.CommonUtil;
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
 * 类名： InnerController<br>
 * 功能：供内部各系统调用进件系统使用，无需过滤token等<br>
 * 版本： 1.0<br>
 * 日期： 2017/9/14/0014<br>
 * 作者： liuzhu.feng<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Controller
public class InnerController {

    private static Logger logger = Logger.getLogger(InnerController.class);

    @Resource
    private PretreatmentService pretreatmentService;


    @RequestMapping(value = "/innerservice",method = RequestMethod.POST)
    @ResponseBody
    public Map nlbsInnerController(@RequestBody Map paramMap) throws Exception{
        logger.info("*****************************************************************************");
        logger.info("【进件系统(nlbs)】接收到请求(InnerController.nlbsInnerController),入参：" + paramMap );
        //定义返回值的map
        Map result = new HashMap();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            //调用服务，分发请求
            result = pretreatmentService.dispatchServices(paramMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }
        logger.info("【进件系统(nlbs)】请求处理完成(InnerController.nlbsInnerController),输出结果：" + result);
        logger.info("*****************************************************************************");
        return result;

    }
}
