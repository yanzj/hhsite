package com.vilio.plms.service.quartz;

import com.vilio.plms.dao.MessageInfoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.MessageInfo;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.HttpUtil;
import com.vilio.plms.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： PushMsgService<br>
 * 功能：推送消息业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月17日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class PushMsgService extends BaseService {
    private static Logger logger = Logger.getLogger(PushMsgService.class);

    @Resource
    private MessageInfoDao messageInfoDao;

    /**
     * 定时任务主方法
     *
     * @throws ErrorException
     */
    @Override
    public void execute() throws Exception {
        //直接将初始化的信息修改成发送处理中状态
        Map udpateParam = new HashMap<>();
        udpateParam.put("oldSendStatus", GlobDict.send_init.getKey());
        udpateParam.put("newSendStatus", GlobDict.send_ing.getKey());
        udpateParam.put("batchNo", getUUID());
        int ret = messageInfoDao.updateMessageInfoStatusByStatus(udpateParam);
        if (ret < 0) {
            logger.error("修改失败");
            return;
        }
        if (ret == 0) {
            logger.info("没有需要推送的信息!");
            return;
        }
        //查询出所有这个批次好的推送信息
        List<MessageInfo> messageInfos = messageInfoDao.queryMessageInfoByBatch(udpateParam);
        //根据配置文件分掠消息（还款提醒和逾期提醒的系统合并后，再进行分掠消息，系统相同，逾期的地址为准）
        Map<String, Object> repaymentSendSystem = GlobParam.repaymentSendSystem;
        Map<String, Object> overdueSendSystem = GlobParam.overdueSendSystem;
        Map<String, Object> sendSystem = new HashMap<>();
        sendSystem.putAll(repaymentSendSystem);
        sendSystem.putAll(overdueSendSystem);
        for (String key : sendSystem.keySet()) {
            udpateParam.put("sendSystem", key);
            List<MessageInfo> messageInfosTemp = new ArrayList<>();
            for (MessageInfo messageInfo : messageInfos) {
                if (messageInfo.getSendSystem().equals(key)) {
                    messageInfosTemp.add(messageInfo);
                }
            }
            if (messageInfosTemp.size() == 0) {
                logger.info("当前系统没有需要发送的信息，继续下个系统：" + key);
                return;
            }
            Map systemInfo = (Map) sendSystem.get(key);
            //开始打包发送
            Map sendMap = new HashMap();
            Map sendHead = new HashMap();
            Map sendBody = new HashMap();
            sendMap.put(Fields.PARAM_MESSAGE_HEAD, sendHead);
            sendMap.put(Fields.PARAM_MESSAGE_BODY, sendBody);
            sendHead.put(Fields.PARAM_FUNCTION_NO, systemInfo.get(Fields.PARAM_FUNCTION_NO));
            sendHead.put(Fields.PARAM_SOURCE_SYSTEM, GlobParam.SYSTEM_ID_PLMS);
            sendBody.put("messageList", messageInfosTemp);
            try {
                logger.info("发送报文：" + JsonUtil.objectToJson(sendMap));
                String resultStr = HttpUtil.sendHttp(String.valueOf(systemInfo.get("url")), JsonUtil.objectToJson(sendMap));
                Map resultMap = JsonUtil.jsonToMap(resultStr);
                Map resultHead = (Map) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
                String returnCode = resultHead.get("returnCode").toString();
                String returnMessage = resultHead.get("returnMessage").toString();
                if (!"0000".equals(returnCode)) {
                    //发送失败，更改状态为发送失败
                    logger.error("发送失败：" + returnMessage);
                    udpateParam.put("sendStatus", GlobDict.send_fail.getKey());
                } else {
                    //发送成功
                    udpateParam.put("sendStatus", GlobDict.send_succ.getKey());
                }
                udpateParam.put("retCode", returnCode);
                udpateParam.put("retMsg", returnMessage.length() > 500 ? returnMessage.substring(0, 500) : returnMessage);
            } catch (ErrorException e) {
                e.printStackTrace();
                if (ReturnCode.TIME_OUT.equals(e.getErroCode())) {
                    //超时，未知状态
                    logger.error("发送超时：" + e.getMessage());
                    udpateParam.put("sendStatus", GlobDict.send_unknown.getKey());
                } else {
                    //发送失败
                    logger.error("发送失败：" + e.getMessage());
                    udpateParam.put("sendStatus", GlobDict.send_fail.getKey());
                }
                udpateParam.put("retCode", e.getErroCode());
                udpateParam.put("retMsg", e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
            } catch (Exception e) {
                //发送失败
                logger.error("发送失败：" + e.getMessage());
                udpateParam.put("sendStatus", GlobDict.send_fail.getKey());
                udpateParam.put("retCode", ReturnCode.SYSTEM_EXCEPTION);
                udpateParam.put("retMsg", e.getMessage() == null ? "" : e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
            }
            //根据批次号和系统标识更改数据库
            try {
                ret = messageInfoDao.updateMessageInfoByBatchAndSystem(udpateParam);
                if (ret <= 0) {
                    logger.error("请注意，更新失败，继续执行下一个系统");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("请注意，更新失败，继续执行下一个系统");
            }
        }
        //全部处理完成后，转移到历史表中
        ((PushMsgService) AopContext.currentProxy()).shiftLog(udpateParam);
    }


    /**
     * 发送信息表转移到历史表中
     *
     * @param updateMap
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void shiftLog(Map updateMap) throws Exception {
        int ret = messageInfoDao.insertMessageInfoLogByBatch(updateMap);
        if (ret <= 0) {
            logger.error("插入出错：" + ret);
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //删除待发送表
        ret = messageInfoDao.deleteMessageInfoByBatch(updateMap);
        if (ret <= 0) {
            logger.error("删除出错：" + ret);
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

}
