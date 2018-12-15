package com.vilio.mps.receivepush.service;

import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;
import com.vilio.mps.common.service.BaseService;
import com.vilio.mps.exception.ErrorException;
import com.vilio.mps.glob.Constants;
import com.vilio.mps.glob.Fields;
import com.vilio.mps.util.MessageUtil;
import com.vilio.mps.util.ReturnCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： RecvHH000803<br>
 * 功能：站内信单条推送<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class RecvHH000803 extends BaseService {

    private static Logger logger = Logger.getLogger(RecvHH000803.class);

    @Resource
    ReceiveMessage receiveMessage;
    @Resource
    PushMessage pushMessage;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {

    }

    /**
     * 单笔站内信发送流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //发送消息
        Map pushResult = null;
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        String pushType = (String) body.get("type");
        //保存消息到数据库
        bodyMap = receiveMessage.saveMessage(body);
        List responseUserList = new ArrayList();
        //发往nlbs的站内信
        List<MpsReceiveMessageInfo> nlbsInfoList = new ArrayList<MpsReceiveMessageInfo>();
        //发往plms的站内信
        List<MpsReceiveMessageInfo> plmsInfoList = new ArrayList<MpsReceiveMessageInfo>();
        //分拣消息接系统
        Map<String, List<MpsReceiveMessageInfo>> classifyInfo = MessageUtil.classifyInfoByReceiverSystem((List<MpsReceiveMessageInfo>) bodyMap.get(Fields.PARAM_MPS_MESSAGELIST));
        if (null != classifyInfo) {
            nlbsInfoList = classifyInfo.get(Constants.RECEIVER_SYSTEM_NLBS);
            plmsInfoList = classifyInfo.get(Constants.RECEIVER_SYSTEM_PLMS);
        }
        //发往nlbs系统的消息
        if (null != nlbsInfoList && nlbsInfoList.size() > 0) {
            Map requestMap = new HashMap();
            requestMap.put(Fields.PARAM_MPS_MESSAGELIST, nlbsInfoList);
            try {
                pushResult = pushMessage.pushMessageToNlbs(requestMap);
            } catch (Exception e) {
                logger.error("发往nlbs系统消息返回异常：" + e.getStackTrace());
                throw new ErrorException(ReturnCode.CONNECT_NLBS_EXCEPTION.getReturnCode(), ReturnCode.CONNECT_NLBS_EXCEPTION.getReturnMessage());
            }
            if (null != pushResult) {
                logger.info("发往nlbs系统消息返回：" + pushResult);
                responseUserList.add(pushResult.get(Fields.PARAM_MPS_RECEIVEUSERLIST));
            }
        }
        //发往plms的站内信
        if (null != plmsInfoList && plmsInfoList.size() > 0) {
            Map requestMap = new HashMap();
            requestMap.put(Fields.PARAM_MPS_MESSAGELIST, plmsInfoList);
            try {
                pushResult = pushMessage.pushMessageToPlms(requestMap);
            } catch (Exception e) {
                logger.error("发往plms系统消息返回异常：" + e.getStackTrace());
                throw new ErrorException(ReturnCode.CONNECT_NLBS_EXCEPTION.getReturnCode(), ReturnCode.CONNECT_NLBS_EXCEPTION.getReturnMessage());
            }
            if (null != pushResult) {
                logger.info("发往plms系统消息返回：" + pushResult);
                responseUserList.add(pushResult.get(Fields.PARAM_MPS_RECEIVEUSERLIST));
            }
        }
        resultMap.put(Fields.PARAM_MPS_RECEIVEUSERLIST, responseUserList);
    }


}
