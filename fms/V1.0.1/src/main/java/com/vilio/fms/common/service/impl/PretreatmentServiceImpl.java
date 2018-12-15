package com.vilio.fms.common.service.impl;

import com.vilio.fms.common.service.CommonService;
import com.vilio.fms.common.service.PretreatmentService;
import com.vilio.fms.common.service.BaseService;
import com.vilio.fms.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/5/24/0024.
 */
@Service
public class PretreatmentServiceImpl implements PretreatmentService {

    private static Logger logger = Logger.getLogger(PretreatmentServiceImpl.class);

    @Resource
    CommonService commonService;

    /**
     * 分发接口服务，根据头map中的functionNo直行后台相关接口服务；
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map dispatchServices(Map paramMap) throws Exception {

        Map resultMap = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            //先检查消息头
            filterHeadParam(headMap);
            //消息头检查通过
            if (ReturnCode.SUCCESS_CODE.equals(headMap.get(Fields.PARAM_MESSAGE_ERR_CODE))) {
                bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
                String functionNo = (String) headMap.get(Fields.PARAM_FUNCTION_NO);
                logger.info("********************************************[" + functionNo + "]开始调用服务********************************************");
                Map serviceMap = null;
                BaseService baseService = (BaseService) SpringContextUtil.getBean(functionNo);
                serviceMap = baseService.excute(bodyMap);

                logger.info("********************************************[" + functionNo + "]结束调用服务********************************************");

                headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, serviceMap.get(Fields.PARAM_MESSAGE_ERR_CODE));
                headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, serviceMap.get(Fields.PARAM_MESSAGE_ERR_MESG));

                serviceMap.remove(Fields.PARAM_MESSAGE_ERR_CODE);
                serviceMap.remove(Fields.PARAM_MESSAGE_ERR_MESG);
                bodyMap = serviceMap;
            }
        } catch (HHBizException hhBizEx){
            logger.error("业务异常：", hhBizEx);
            //整理异常情况下的出参
            bodyMap.clear();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, hhBizEx.getErrorNo());
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, hhBizEx.getErrorMessage());
        } catch (Exception e) {
            logger.error("系统异常：", e);
            //整理异常情况下的出参
            bodyMap.clear();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
        }
        //封装返回的map，使用入参的消息头，原样返回
        resultMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        resultMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);

        return resultMap;
    }

    /**
     * 处理头map中的参数，后续版本控制等，当前不做处理
     *
     * @param headMap
     * @return
     * @throws Exception
     */
    public Map filterHeadParam(Map headMap) throws Exception {
        if (null == headMap) {
            headMap = new HashMap();
            //该返回仅作测试用，后续删除该return
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "");
            return headMap;
        }
        //执行一系列检查

        headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "");
        return headMap;
    }

}
