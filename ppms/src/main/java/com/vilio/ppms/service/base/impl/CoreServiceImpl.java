package com.vilio.ppms.service.base.impl;

import com.vilio.ppms.exception.ErrorException;
import com.vilio.ppms.exception.SeriousErrorException;
import com.vilio.ppms.glob.Fields;
import com.vilio.ppms.glob.GlobParam;
import com.vilio.ppms.glob.ReturnCode;
import com.vilio.ppms.service.base.BaseServiceInterface;
import com.vilio.ppms.service.base.CoreService;
import com.vilio.ppms.util.JudgeUtil;
import com.vilio.ppms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： CoreServiceImpl<br>
 * 功能：核心业务处理实现类<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月19日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service("coreService")
public class CoreServiceImpl implements CoreService {

    private static final Logger logger = Logger.getLogger(CoreServiceImpl.class);

    /**
     * 核心业务处理
     * @param params
     * @return
     */
    public Map<String, Object> doMain(Map<String, Object> params) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> head = new HashMap<String, Object>();
        Map<String, Object> body = new HashMap<String, Object>();
        respMap.put(Fields.PARAM_MESSAGE_BODY, params.get(Fields.PARAM_MESSAGE_BODY));
        try {
            head.putAll((Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD));
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            String transCode = String.valueOf(head.get(Fields.PARAM_FUNCTION_NO));
            transCode = transCode.toLowerCase();
            //调用相应流程
            BaseServiceInterface bService = (BaseServiceInterface) SpringContextUtil.getBean(transCode);
            body.putAll(bService.doService(params));
            JudgeUtil.MapValToString(body);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SUCCESS_CODE));
        } catch (ErrorException e) {
            if (e instanceof SeriousErrorException) {
                e.printStackTrace();
            }
            String returnCode = e.getErroCode() == null || "".equals(e.getErroCode()) ? ReturnCode.SYSTEM_EXCEPTION : e.getErroCode();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, returnCode);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage() == null || "".equals(e.getMessage())
                    ? GlobParam.ERROR_CODE.get(returnCode) : e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
            logger.error("错误码" + head.get(Fields.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(Fields.PARAM_MESSAGE_ERR_MESG));
        } catch (Exception e) {
            e.printStackTrace();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
            logger.error("错误码" + head.get(Fields.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(Fields.PARAM_MESSAGE_ERR_MESG));
        }
        return respMap;
    }

}
