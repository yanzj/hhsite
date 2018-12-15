package com.vilio.pcfs.service.quartz;

import com.vilio.pcfs.dao.MessagesDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.service.BaseService;
import com.vilio.pcfs.util.DateUtil;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： PushMsgService<br>
 * 功能：推送信息<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月15日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class PushMsgService extends BaseService {

    private static Logger logger = Logger.getLogger(PushMsgService.class);

    @Resource
    private MessagesDao messagesDao;

    /**
     * 定时任务执行主流程实现
     */
    @Override
    public void execute() throws ErrorException {
        //直接更改推送信息表
        Map updateParam = new HashMap<>();
        updateParam.put("oldSendStatus", GlobDict.send_init.getKey());
        updateParam.put("newSendStatus", GlobDict.send_ing.getKey());
        updateParam.put("batchNo", getUUID());
        updateParam.put("sendMethod", GlobDict.send_method_delay.getKey());
        int ret = messagesDao.updateMessagesByBatchNo(updateParam);
        if (ret <= 0) {
            logger.info("没有需要处理的推送" + ret);
            return;
        }
        //查询所有推送信息
        List<Map> messagesList = messagesDao.queryMessagesByBatchNo(updateParam);
        //单条发送推送信息
        for (Map message : messagesList) {
            Map sendMap = new HashMap();
            sendMap.put(GlobParam.PARAM_USER_ID, message.get("userId"));
            //判断是安卓还是苹果
            if (GlobDict.system_type_android.getKey().equals(message.get("channel"))) {
                sendMap.put("appCode", GlobParam.pushAndroidAppCode);
            } else if (GlobDict.system_type_ios.getKey().equals(message.get("channel"))) {
                sendMap.put("appCode", GlobParam.pushIosAppCode);
            }
            sendMap.put("requestNo", message.get("messageId"));
            sendMap.put("senderName", message.get("sourceSystem"));
            //如果ticker为空，则和title相同
            sendMap.put("ticker", JudgeUtil.isNull(message.get("messageTicker")) ? message.get("messageTicker") : message.get("messageTitle"));
            sendMap.put("title", message.get("messageTitle"));
            sendMap.put("text", message.get("messageContent"));
            sendMap.put("subtitle", message.get("messageSubtitle"));
            sendMap.put("deviceTokens", message.get("deviceToken"));
            try {
                Map resultMap = PcfsUtil.pushMsg(sendMap);
                Map resultHead = (Map) resultMap.get(GlobParam.PARAM_MESSAGE_HEAD);
                String returnCode = resultHead.get("returnCode").toString();
                String returnMessage = resultHead.get("returnMessage").toString();
                //发送成功
                message.put("sendStatus", GlobDict.send_succ.getKey());
                message.put("retCode", returnCode);
                message.put("retMsg", returnMessage);
            } catch (ErrorException e) {
                e.printStackTrace();
                if (ReturnCode.TIME_OUT.equals(e.getErroCode())) {
                    //发送超时，状态未知
                    message.put("sendStatus", GlobDict.send_unknown.getKey());
                    message.put("retCode", e.getErroCode());
                    message.put("retMsg", e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
                } else {
                    message.put("sendStatus", GlobDict.send_fail.getKey());
                    message.put("retCode", e.getErroCode());
                    message.put("retMsg", e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                //发送失败
                message.put("sendStatus", GlobDict.send_fail.getKey());
                message.put("retCode", ReturnCode.SYSTEM_EXCEPTION);
                message.put("retMsg", e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
            }
            //修改发送状态，继续下一条
            message.put("realitySendTime", DateUtil.getCurrentDateTime());
            ret = messagesDao.updateMessagesById(message);
            if (ret <= 0) {
                logger.error("推送消息状态更改失败，请注意，程序继续处理：" + ret + "，标识为：" + sendMap.get("messageId"));
            }
        }


    }

}
