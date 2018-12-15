package com.vilio.fms.generator.controller;

import com.vilio.fms.generator.dao.FmsModelBeanMapper;
import com.vilio.fms.generator.pojo.FmsModelBean;
import com.vilio.fms.common.service.BaseService;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.ReturnCode;
import com.vilio.fms.util.SpringContextUtil;
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
 * Created by dell on 2017/5/17/0017.
 */
@Controller
public class HandleFileModel {

    private static Logger logger = Logger.getLogger(HandleFileModel.class);

    @Resource
    FmsModelBeanMapper fmsModelBeanMapper;


    @RequestMapping(value = "HH900001",method = RequestMethod.POST)
    @ResponseBody
    public Map generatorTargetFileByTemplate(@RequestBody Map map) throws Exception{
        logger.info("根据文件模版生成目标文件接口(HandleFileModel.generatorTargetFileByTemplate)被调用开始,入参：" + map );
        //定义返回值的map，消息头和消息体map
        Map result = new HashMap();
        /*Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        Map resultMap = new HashMap();
        try {
            CommonUtil.dealEmpty2Null(map);
            headMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) map.get(Fields.PARAM_MESSAGE_BODY);
            //获取文件模版和bean对应关系
            String fileCode = (String) bodyMap.get(Fields.PARAM_FILE_CODE);
            FmsModelBean fmsModelClass = fmsModelBeanMapper.getFmsModelClassByFileCode(fileCode);
            if(fmsModelClass != null){
                String beanName = fmsModelClass.getBeanName();
                String fileModelName = fmsModelClass.getFileModelName();
                String fileType = fmsModelClass.getFileType();
                bodyMap.put(Fields.PARAM_FILE_MODEL_NAME, fileModelName);
                bodyMap.put(Fields.PARAM_FILE_TYPE, fileType);
                BaseService baseService = (BaseService) SpringContextUtil.getBean(beanName);
                resultMap = baseService.excute(bodyMap);

                resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
                resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "生成文件成功！");
            } else {
                resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "未找到对应文件模版！");
            }

            bodyMap = resultMap;
        }catch (Exception e){
            logger.error("系统异常：",e);
            //整理异常情况下的出参
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            bodyMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回
        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        logger.info("根据文件模版生成目标文件接口(HandleFileModel.generatorTargetFileByTemplate)被调用结束,输出结果：" + result);*/
        return result;
    }
}
