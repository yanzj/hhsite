package com.vilio.plms.service.base;

import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/7/19.
 */
@Service
public class RemoteMpsService {
    private static Logger logger = Logger.getLogger(RemoteMpsService.class);
    /**
     * 调用MPS服务
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map callService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        JSONObject result = null;
        try {
            String url = GlobParam.mpsUrl;
            Map remoteMap = new HashMap();
            Map headMap = new HashMap();
            headMap.put("functionNo", paramMap.get("functionNo"));
            paramMap.remove("functionNo");
            remoteMap.put("head", headMap);
            remoteMap.put("body", paramMap);

            JSONObject jsonParam = JSONObject.fromObject(remoteMap);
            result = HttpUtil.httpPost(url, jsonParam);
            returnMap = CommonUtil.toMap(result);
        }catch (Exception e){
            logger.error("调用消息中心系统异常：",e);
            Map headMap = new HashMap();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求消息中心系统失败！");
            Map bodyMap = new HashMap();
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        }

        return returnMap;
    }


    /**
     * @Description: 发送邮件信息
     * @param: HH000805
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/8/28/0028
     */
    public Map sendEmailService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        JSONObject result = null;
        try {
            String url = GlobParam.mpsUrl;
            Map remoteMap = new HashMap();
            Map headMap = new HashMap();
            headMap.put("functionNo", "HH000805");
            remoteMap.put("head", headMap);
            remoteMap.put("body", paramMap);

            JSONObject jsonParam = JSONObject.fromObject(remoteMap);
            logger.info("----贷后系统【PLMS】请求消息中心【mps】，参数为：\n" + jsonParam.toString());
            result = HttpUtil.httpPost(url, jsonParam);
            logger.info("----贷后系统【PLMS】请求消息中心【mps】，应答为：\n" + (result == null ? "空" : result.toString()));
            returnMap = CommonUtil.toMap(result);
        } catch (Exception e) {
            logger.error("调用消息中心系统异常：", e);
            Map headMap = new HashMap();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求消息中心系统失败！");
            Map bodyMap = new HashMap();
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        }

        Map<String, Object> remoteHeadMap = null;
        Map<String, Object> remoteBodyMap = null;
        remoteHeadMap = (Map<String, Object>) returnMap.get(Fields.PARAM_MESSAGE_HEAD);
        remoteBodyMap = (Map<String, Object>) returnMap.get(Fields.PARAM_MESSAGE_BODY);
        if(remoteHeadMap == null || remoteBodyMap == null){
            logger.error("消息中心处理异常，返回为空\n");
            Map headMap = new HashMap();
            headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求消息中心系统失败！");
            Map bodyMap = new HashMap();
            returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
            returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        } else {
            String remoteReturnCode = (String) remoteHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
            // 回写消息状态和流水号
            if (!ReturnCode.SUCCESS_CODE.equals(remoteReturnCode)){
                logger.error("消息中心处理异常，错误号为：" + remoteReturnCode + "\n");
                Map headMap = new HashMap();
                headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
                headMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "请求消息中心系统失败！");
                Map bodyMap = new HashMap();
                returnMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
                returnMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
            }
        }
        return returnMap;
    }
}
