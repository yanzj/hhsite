package com.vilio.comm.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobParam;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名： BaseService<br>
 * 功能：所有service的父类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class BaseService {

    private static final Logger logger = Logger.getLogger(BaseService.class);

    /**
     * @param params
     * @return
     */
    public Map<String, Object> doMain(Map<String, Object> params) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> head = new HashMap<String, Object>();
        Map<String, Object> body = new HashMap<String, Object>();
        respMap.put(GlobParam.PARAM_MESSAGE_BODY, params.get(GlobParam.PARAM_MESSAGE_BODY));
        try {
            head.putAll((Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_HEAD));
            respMap.put(GlobParam.PARAM_MESSAGE_HEAD, head);
            body = doService(params);
            respMap.put(GlobParam.PARAM_MESSAGE_BODY, body);
            head.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "0000");
            head.put(GlobParam.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get("0000"));
        } catch (ErrorException e) {
            String returnCode = e.getErroCode() == null || "".equals(e.getErroCode()) ? "9999" : e.getErroCode();
            head.put(GlobParam.PARAM_MESSAGE_ERR_CODE, returnCode);
            head.put(GlobParam.PARAM_MESSAGE_ERR_MESG, e.getMessage() == null || "".equals(e.getMessage())
                    ? GlobParam.ERROR_CODE.get(returnCode) : e.getMessage());
            logger.error("错误码" + head.get(GlobParam.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(GlobParam.PARAM_MESSAGE_ERR_MESG));
        } catch (Exception e) {
            e.printStackTrace();
            head.put(GlobParam.PARAM_MESSAGE_ERR_CODE, "9999");
            head.put(GlobParam.PARAM_MESSAGE_ERR_MESG, e.getMessage());
            logger.error("错误码" + head.get(GlobParam.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(GlobParam.PARAM_MESSAGE_ERR_MESG));
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
        Map<String, Object> body = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_BODY);
        Map<String, Object> head = (Map<String, Object>) params.get(GlobParam.PARAM_MESSAGE_HEAD);
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
