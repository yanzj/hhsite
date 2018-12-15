package com.vilio.plms.service.quartz;

import com.vilio.plms.dao.MessageInfoDao;
import com.vilio.plms.dao.PlmsRepaymentScheduleDao;
import com.vilio.plms.dao.SmsInfoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JsonUtil;
import com.vilio.plms.util.MatchUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： RepaymentCollectService<br>
 * 功能：还款提醒收集<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月17日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class RepaymentCollectService extends BaseService {

    private static Logger logger = Logger.getLogger(RepaymentCollectService.class);

    @Resource
    private PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;

    @Resource
    private MessageInfoDao messageInfoDao;

    @Resource
    private SmsInfoDao smsInfoDao;


    /**
     * 定时任务主方法
     *
     * @throws ErrorException
     */
    @Override
    public void execute() throws ErrorException, ParseException {

        try {
            //友盟推送信息表入库
            insertMessage();
        } catch (Exception e) {
            logger.info("还款提醒友盟推送信息收集入库失败");
            e.printStackTrace();
        }
        //短信推送信息数据处理入库
        try {
            insertSms();
        } catch (Exception e) {
            logger.info("还款提醒短信推送信息收集入库失败");
            e.printStackTrace();
        }


    }


    /**
     * 短信推送信息数据处理入库
     *
     */
    private void insertSms() throws ErrorException {
        //获取到所有应发渠道，获取应发渠道下的所有用户code
        List<String> channelList = new ArrayList<>();
        for (String key : GlobParam.repaymentSmsSignNo.keySet()) {
            channelList.add(key);
        }
        //查询三天前，昨天，和今天的还款信息（配置文件配置）
        List dateList = new ArrayList();
        for (String days : GlobParam.repaymentTimeListStr) {
            dateList.add(DateUtil.getDateDistanceNow(Integer.parseInt(days)));
        }
        //查询
        Map queryParam = new HashMap();
        queryParam.put("status", GlobDict.repayment_schedule_status_paying_and_not_overdue.getKey());
        queryParam.put("dateList", dateList);
        queryParam.put("channelList", channelList);
        List<Map> repaymentScheduleList = plmsRepaymentScheduleDao.queryRepaymentScheduleByDateAndChannel(queryParam);
        if (repaymentScheduleList == null || repaymentScheduleList.size() == 0) {
            logger.info("短信推送，没有需要还款提醒的数据！");
            return;
        }
        //处理数据，批量入库
        for (Map repaymentSchedule : repaymentScheduleList) {
            repaymentSchedule.put("code", getUUID());
            repaymentSchedule.put("requestNo",getDateSeq(GlobParam.SEQUENCE_SERIAL_NO, 6));
            Map templateParam = new HashMap();
            //content需要替换其中的参数，定义需要替换的参数
            int diffDate = DateUtil.dateCompare(String.valueOf(repaymentSchedule.get("repaymentDate")));
            if (diffDate >= 3) {
                templateParam.put("date", diffDate + "天后" + DateUtil.convert(String.valueOf(repaymentSchedule.get("repaymentDate")), "yyyyMMdd", "M月d日"));
            } else if (diffDate == 1) {
                templateParam.put("date", "明天" + DateUtil.convert(String.valueOf(repaymentSchedule.get("repaymentDate")), "yyyyMMdd", "M月d日"));
            } else if (diffDate == 0) {
                templateParam.put("date", "今天" + DateUtil.convert(String.valueOf(repaymentSchedule.get("repaymentDate")), "yyyyMMdd", "M月d日"));
            }
            templateParam.put("amount", repaymentSchedule.get("amount"));
            repaymentSchedule.put("templateParam", JsonUtil.objectToJson(templateParam));
            repaymentSchedule.put("senderName",repaymentSchedule.get("customerName"));
            repaymentSchedule.put("signNo",GlobParam.repaymentSmsSignNo.get(repaymentSchedule.get("distributorCode")));
            repaymentSchedule.put("smsType",GlobDict.sms_type_repayment.getKey());
            repaymentSchedule.put("sendStatus",GlobDict.send_init.getKey());
            repaymentSchedule.put("sendMethod",GlobDict.send_method_delay.getKey());
            repaymentSchedule.put("repaymentScheduleCode",repaymentSchedule.get("repaymentScheduleCode"));
            repaymentSchedule.put("templateCode",GlobParam.repaymentSmsTemplateCode);
        }
        //批量入库
        int ret = smsInfoDao.insertSmsInfoBatch(repaymentScheduleList);
        if (ret <= 0) {
            logger.error("短信还款提醒消息插入失败，请注意，程序继续处理：" + ret);
        }
    }


    /**
     * 友盟推送信息表入库
     *
     */
    private void insertMessage() {
        //查询三天前，昨天，和今天的还款信息（配置文件配置）
        List dateList = new ArrayList();
        for (String days : GlobParam.repaymentTimeListStr) {
            dateList.add(DateUtil.getDateDistanceNow(Integer.parseInt(days)));
        }
        //查询
        Map queryParam = new HashMap();
        queryParam.put("status", GlobDict.repayment_schedule_status_paying_and_not_overdue.getKey());
        queryParam.put("dateList", dateList);
        List<Map> repaymentScheduleList = plmsRepaymentScheduleDao.queryRepaymentScheduleByDate(queryParam);
        if (repaymentScheduleList == null || repaymentScheduleList.size() == 0) {
            logger.info("没有需要还款提醒的数据！");
            return;
        }
        //处理数据，批量入库
        for (Map repaymentSchedule : repaymentScheduleList) {
            repaymentSchedule.put("code", getUUID());
            repaymentSchedule.put("messageTitle", GlobParam.repaymentTitle);
            repaymentSchedule.put("messageTicker", GlobParam.repaymentTicker);
            repaymentSchedule.put("messageSubtitle", GlobParam.repaymentSubtitle);
            //content需要替换其中的参数，定义需要替换的参数
            int diffDate = DateUtil.dateCompare(String.valueOf(repaymentSchedule.get("repaymentDate")));
            Map metchParam = new HashMap();
            if (Math.abs(diffDate) >= 3) {
                metchParam.put("date", Math.abs(diffDate) + "天后" + DateUtil.convert(String.valueOf(repaymentSchedule.get("repaymentDate")), "yyyyMMdd", "M月d日"));
            } else if (Math.abs(diffDate) == 1) {
                metchParam.put("date", "明天" + DateUtil.convert(String.valueOf(repaymentSchedule.get("repaymentDate")), "yyyyMMdd", "M月d日"));
            } else if (Math.abs(diffDate) == 0) {
                metchParam.put("date", "今天" + DateUtil.convert(String.valueOf(repaymentSchedule.get("repaymentDate")), "yyyyMMdd", "M月d日"));
            }
            metchParam.put("amount", repaymentSchedule.get("amount"));
            repaymentSchedule.put("messageContent", MatchUtil.matchValue(GlobParam.repaymentContent, metchParam));
            repaymentSchedule.put("messageType", GlobDict.message_type_repayment.getKey());
            repaymentSchedule.put("sendMethod", GlobDict.send_method_delay.getKey());
            repaymentSchedule.put("sendStatus", GlobDict.send_init.getKey());
            repaymentSchedule.put("sendTime", DateUtil.getCurrentDate());
        }

        //循环需要推送的的系统，批量插入
        for (String key : GlobParam.repaymentSendSystem.keySet()) {
            for (Map repaymentSchedule : repaymentScheduleList) {
                repaymentSchedule.put("sendSystem", key);
            }
            //批量插入推送信息表
            int ret = messageInfoDao.insertMessageInfoBatch(repaymentScheduleList);
            if (ret <= 0) {
                logger.error("友盟还款提醒消息插入失败，请注意，程序继续处理：" + ret + "，系统标识为：" + key);
            }
        }

    }
}
