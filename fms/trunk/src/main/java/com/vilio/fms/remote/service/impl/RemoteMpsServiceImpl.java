package com.vilio.fms.remote.service.impl;

import com.vilio.fms.generator.dao.FmsSendRecordsMapper;
import com.vilio.fms.generator.pojo.FmsSendRecords;
import com.vilio.fms.remote.service.RemoteBmsService;
import com.vilio.fms.remote.service.RemoteMpsService;
import com.vilio.fms.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/14/0014.
 */
@Service
public class RemoteMpsServiceImpl implements RemoteMpsService {

    @Resource
    ConfigInfo configInfo;

    @Resource
    FmsSendRecordsMapper fmsSendRecordsMapper;

    private static Logger logger = Logger.getLogger(RemoteMpsServiceImpl.class);

    /**
     * @Description: 发送邮件信息
     * @param: HH000805
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/8/28/0028
     */
    @Override
    public Map sendEmailService(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        JSONObject result = null;
        try {
            String url = configInfo.getMpsUrl();
            Map remoteMap = new HashMap();
            Map headMap = new HashMap();
            headMap.put("functionNo", "HH000805");
            remoteMap.put("head", headMap);
            remoteMap.put("body", paramMap);

            JSONObject jsonParam = JSONObject.fromObject(remoteMap);
            logger.info("----文件系统【FMS】请求消息中心【mps】，参数为：\n" + jsonParam.toString());
            result = HttpRequestUtils.httpPost(url, jsonParam, new HashMap<String, String>());
            logger.info("----文件系统【FMS】请求消息中心【mps】，应答为：\n" + (result == null ? "空" : result.toString()));
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
            if (ReturnCode.SUCCESS_CODE.equals(remoteReturnCode)) {
                if (remoteBodyMap != null) {
                    try {
                        FmsSendRecords fmsSendRecords = new FmsSendRecords();
                        fmsSendRecords.setCode(paramMap.get("") == null ? "" : paramMap.get("").toString());
                        fmsSendRecords.setRecordNo(remoteBodyMap.get("serialNo") == null ? "" : remoteBodyMap.get("serialNo").toString());
                        fmsSendRecordsMapper.updateSendRecord(fmsSendRecords);
                    } catch (Exception e) {
                        logger.info("发送邮件时--回写消息中心返回的序列号失败。。。");
                    }
                }
            } else {
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
