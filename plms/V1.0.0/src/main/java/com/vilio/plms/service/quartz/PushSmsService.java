package com.vilio.plms.service.quartz;

import com.vilio.plms.dao.SmsInfoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.SmsInfo;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.HttpUtil;
import com.vilio.plms.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： PushSmsService<br>
 * 功能：短信消息业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月17日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class PushSmsService extends BaseService {

    private static Logger logger = Logger.getLogger(PushSmsService.class);

    @Resource
    private SmsInfoDao smsInfoDao;


    /**
     * 定时任务主方法
     *
     * @throws Exception
     */
    @Override
    public void execute() throws Exception {
        //直接将初始化的信息修改成发送处理中状态
        Map udpateParam = new HashMap<>();
        udpateParam.put("oldSendStatus", GlobDict.send_init.getKey());
        udpateParam.put("newSendStatus", GlobDict.send_ing.getKey());
        udpateParam.put("batchNo", getUUID());
        int ret = smsInfoDao.updateSmsInfoStatusByStatus(udpateParam);
        if (ret < 0) {
            logger.error("修改失败");
            return;
        }
        if (ret == 0) {
            logger.info("没有需要发送短信的信息!");
            return;
        }
        //查询出这个批次号下的所有短信信息
        List<SmsInfo> smsInfoList = smsInfoDao.querySmsInfoByBatch(udpateParam);
        for (SmsInfo smsInfo : smsInfoList) {
            //循环发送到推送平台
            Map sendMap = new HashMap();
            Map sendHead = new HashMap();
            Map sendBody = new HashMap();
            sendMap.put(Fields.PARAM_MESSAGE_HEAD, sendHead);
            sendMap.put(Fields.PARAM_MESSAGE_BODY, sendBody);
            sendHead.put(Fields.PARAM_FUNCTION_NO, GlobParam.mpsSmsFunctionNo);
            sendBody.put("type", GlobParam.mpsSmsType);
            sendBody.put("senderIdentityId", GlobParam.mpsSmsSenderIdentityId);
            sendBody.put("mobile", smsInfo.getMobile());
            sendBody.put("senderSystem", GlobParam.SYSTEM_ID_PLMS);
            sendBody.put("requestNo", smsInfo.getRequestNo());
            sendBody.put("templateParam", JsonUtil.jsonToMap(smsInfo.getTemplateParam()));
            sendBody.put("senderName", smsInfo.getSenderName());
            sendBody.put("signNo", smsInfo.getSignNo());
            sendBody.put("templateCode", smsInfo.getTemplateCode());
            try {
                logger.info("发送报文：" + JsonUtil.objectToJson(sendMap));
                String resultStr = HttpUtil.sendHttp(GlobParam.mpsUrl, JsonUtil.objectToJson(sendMap));
                Map resultMap = JsonUtil.jsonToMap(resultStr);
                Map resultHead = (Map) resultMap.get(Fields.PARAM_MESSAGE_HEAD);
                String returnCode = resultHead.get("returnCode").toString();
                String returnMessage = resultHead.get("returnMessage").toString();
                if (!"0000".equals(returnCode)) {
                    //发送失败，更改状态为发送失败
                    logger.error("发送失败：" + returnMessage);
                    smsInfo.setSendStatus(GlobDict.send_fail.getKey());
                } else {
                    //发送成功
                    smsInfo.setSendStatus(GlobDict.send_succ.getKey());
                }
                smsInfo.setRetCode(returnCode);
                smsInfo.setRetMsg(returnMessage.length() > 500 ? returnMessage.substring(0, 500) : returnMessage);
            } catch (ErrorException e) {
                e.printStackTrace();
                if (ReturnCode.TIME_OUT.equals(e.getErroCode())) {
                    //超时，未知状态
                    logger.error("发送超时：" + e.getMessage());
                    smsInfo.setSendStatus(GlobDict.send_unknown.getKey());
                } else {
                    //发送失败
                    logger.error("发送失败：" + e.getMessage());
                    smsInfo.setSendStatus(GlobDict.send_fail.getKey());
                }
                smsInfo.setRetCode(e.getErroCode());
                smsInfo.setRetMsg(e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
            } catch (Exception e) {
                //发送失败
                logger.error("发送失败：" + e.getMessage());
                smsInfo.setSendStatus(GlobDict.send_fail.getKey());
                smsInfo.setRetCode(ReturnCode.SYSTEM_EXCEPTION);
                smsInfo.setRetMsg(e.getMessage() == null ? "" : e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
            }
            //根据code更改状态和错误信息
            smsInfo.setRealSendTime(DateUtil.getCurrentShortDateTime());
            //根据批次号和系统标识更改数据库
            try {
                ret = smsInfoDao.updateSmsInfoRetByCode(smsInfo);
                if (ret <= 0) {
                    logger.error("请注意，更新失败，继续执行下一条短信");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("请注意，更新失败，继续执行下一个系统");
            }
        }
        //全部处理完成后，转移到历史表中
        ((PushSmsService) AopContext.currentProxy()).shiftLog(udpateParam);
    }


    /**
     * 发送信息表转移到历史表中
     *
     * @param udpateParam
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void shiftLog(Map udpateParam) throws Exception {
        int ret = smsInfoDao.insertSmsInfoLogByBatch(udpateParam);
        if (ret <= 0) {
            logger.error("插入出错：" + ret);
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //删除待发送表
        ret = smsInfoDao.deleteSmsInfoByBatch(udpateParam);
        if (ret <= 0) {
            logger.error("删除出错：" + ret);
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }
}
