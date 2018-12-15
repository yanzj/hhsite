package com.vilio.fms.common.controller;

import com.vilio.fms.common.service.PretreatmentService;
import com.vilio.fms.util.CommonUtil;
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
 *
 */
@Controller
public class FMSController {

    private static Logger logger = Logger.getLogger(FMSController.class);

    @Resource
    private PretreatmentService pretreatmentService;


    @RequestMapping(value = "/backendapi",method = RequestMethod.POST)
    @ResponseBody
    public Map fmsGeneralController(@RequestBody Map paramMap) throws Exception{
        logger.info("*********************************************************************************************************************************************");
        logger.info("【文件系统(fms)】接收到请求(FMSController.fmsGeneralController),入参：" + paramMap );
        //定义返回值的map
        Map result = new HashMap();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            //调用服务，分发请求
            result = pretreatmentService.dispatchServices(paramMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }
        logger.info("【文件系统(fms)】请求处理完成(FMSController.fmsGeneralController),输出结果：" + result);
        logger.info("*********************************************************************************************************************************************");
        return result;

    }

}
