package com.vilio.mps.common.service;

import com.vilio.mps.exception.ErrorException;
import com.vilio.mps.glob.ConfigInfo;
import com.vilio.mps.glob.Fields;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有服务的父类
 */
public class BaseService {


    private static Logger logger = Logger.getLogger(BaseService.class);

    public Map excute(Map paramMap) throws Exception {
        logger.info("********************************************《" + getInterfaceDescription() + "》开始调用服务********************************************");
        Map subExcuteResultMap = subExcute(paramMap);
        logger.info("********************************************《" + getInterfaceDescription() + "》结束调用服务********************************************");
        return subExcuteResultMap;
    }

    /**
     * 各个接口自己的实现
     * @return
     * @throws Exception
     */
    public Map subExcute(Map paramMap) throws Exception {
        return null;
    }
    /**
     * 接口描述
     * @return
     */
    public String getInterfaceDescription() {
        return "";
    }




    /**
     * 主方法入口
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
            body = doService(params);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, "0000");
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, ConfigInfo.ERROR_CODE.get("0000"));
        } catch (ErrorException e) {
            String returnCode = e.getErroCode() == null || "".equals(e.getErroCode()) ? "9999" : e.getErroCode();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, returnCode);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage() == null || "".equals(e.getMessage())
                    ? ConfigInfo.ERROR_CODE.get(returnCode) : e.getMessage());
            logger.error("错误码" + head.get(Fields.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(Fields.PARAM_MESSAGE_ERR_MESG));
        } catch (Exception e) {
            e.printStackTrace();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, "9999");
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
            logger.error("错误码" + head.get(Fields.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(Fields.PARAM_MESSAGE_ERR_MESG));
        }
        return respMap;
    }




    /**
     * 流程方法
     *
     * @param params
     * @return
     * @throws ErrorException
     */
    public Map<String, Object> doService(Map<String, Object> params) throws ErrorException, Exception {
        //初始化参数
        Map<String, Object> body = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_BODY);
        Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 参数校验
        checkParam(body);
        //登录业务处理
        busiService(head, body, resultMap);
        //返回参数
        return resultMap;
    }

    /**
     * 参数验证空实现
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException,Exception {
    }


}
