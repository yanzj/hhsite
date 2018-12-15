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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： OverdueCollectService<br>
 * 功能：逾期提醒收集<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class OverdueCollectService extends BaseService {

    private static Logger logger = Logger.getLogger(OverdueCollectService.class);

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
    public void execute() throws ErrorException {

        try {
            //友盟推送信息表入库
            insertMessage();
        } catch (Exception e) {
            logger.info("逾期提醒友盟推送信息收集入库失败");
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
     *短信推送信息数据处理入库
     */
    private void insertSms() throws ErrorException {
        //获取到所有应发渠道，获取应发渠道下的所有用户code
        List<String> channelList = new ArrayList<>();
        for (String key : GlobParam.overdueSmsSignNo.keySet()) {
            channelList.add(key);
        }
        //查询有逾期的，多少天后提醒（配置文件配置）
        List dateList = new ArrayList();
        for (String days : GlobParam.overdueTimeListStr) {
            dateList.add(DateUtil.getDateDistanceNow(-Integer.parseInt(days)));
        }
        //查询
        Map queryParam = new HashMap();
        queryParam.put("status", GlobDict.repayment_schedule_status_overdue.getKey());
        queryParam.put("dateList", dateList);
        queryParam.put("channelList", channelList);
        List<Map> repaymentScheduleList = plmsRepaymentScheduleDao.queryRepaymentScheduleByDateAndChannel(queryParam);
        if (repaymentScheduleList == null || repaymentScheduleList.size() == 0) {
            logger.info("短信推送，没有需要逾期提醒的数据！");
            return;
        }
        //处理数据，批量入库
        for (Map repaymentSchedule : repaymentScheduleList) {
            repaymentSchedule.put("code", getUUID());
            repaymentSchedule.put("requestNo",getDateSeq(GlobParam.SEQUENCE_SERIAL_NO, 6));
            Map templateParam = new HashMap();
            //content需要替换其中的参数，定义需要替换的参数
            int diffDate = DateUtil.dateCompare(String.valueOf(repaymentSchedule.get("repaymentDate")));
            templateParam.put("days", ObjectUtils.toString(Math.abs(diffDate)));
            templateParam.put("date", DateUtil.convert(String.valueOf(repaymentSchedule.get("repaymentDate")), "yyyyMMdd", "M月d日"));
            repaymentSchedule.put("templateParam", JsonUtil.objectToJson(templateParam));
            repaymentSchedule.put("senderName",repaymentSchedule.get("customerName"));
            repaymentSchedule.put("signNo",GlobParam.overdueSmsSignNo.get(repaymentSchedule.get("distributorCode")));
            repaymentSchedule.put("smsType",GlobDict.sms_type_overdue.getKey());
            repaymentSchedule.put("sendStatus",GlobDict.send_init.getKey());
            repaymentSchedule.put("sendMethod",GlobDict.send_method_delay.getKey());
            repaymentSchedule.put("repaymentScheduleCode",repaymentSchedule.get("repaymentScheduleCode"));
            repaymentSchedule.put("templateCode",GlobParam.overdueSmsTemplateCode);
        }
        //批量入库
        int ret = smsInfoDao.insertSmsInfoBatch(repaymentScheduleList);
        if (ret <= 0) {
            logger.error("短信逾期提醒消息插入失败，请注意，程序继续处理：" + ret);
        }
    }

    /**
     * 友盟推送信息表入库
     */
    private void insertMessage() {
        //查询有逾期的，多少天后提醒（配置文件配置）
        List dateList = new ArrayList();
        for (String days : GlobParam.overdueTimeListStr) {
            dateList.add(DateUtil.getDateDistanceNow(-Integer.parseInt(days)));
        }
        //查询
        Map queryParam = new HashMap();
        queryParam.put("status", GlobDict.repayment_schedule_status_overdue.getKey());
        queryParam.put("dateList", dateList);
        List<Map> repaymentScheduleList = plmsRepaymentScheduleDao.queryRepaymentScheduleByDate(queryParam);
        if (repaymentScheduleList == null || repaymentScheduleList.size() == 0) {
            logger.info("没有需要逾期提醒的数据！");
            return;
        }
        //处理数据，批量入库
        for (Map repaymentSchedule : repaymentScheduleList) {
            repaymentSchedule.put("code", getUUID());
            repaymentSchedule.put("messageTitle", GlobParam.overdueTitle);
            repaymentSchedule.put("messageTicker", GlobParam.overdueTicker);
            repaymentSchedule.put("messageSubtitle", GlobParam.overdueSubtitle);
            //content需要替换其中的参数，定义需要替换的参数
            int diffDate = DateUtil.dateCompare(String.valueOf(repaymentSchedule.get("repaymentDate")));
            Map metchParam = new HashMap();
            metchParam.put("days", Math.abs(diffDate));
            metchParam.put("date", DateUtil.convert(String.valueOf(repaymentSchedule.get("repaymentDate")), "yyyyMMdd", "M月d日"));
            repaymentSchedule.put("messageContent", MatchUtil.matchValue(GlobParam.overdueContent, metchParam));
            repaymentSchedule.put("messageType", GlobDict.message_type_overdue.getKey());
            repaymentSchedule.put("sendMethod", GlobDict.send_method_delay.getKey());
            repaymentSchedule.put("sendStatus", GlobDict.send_init.getKey());
            repaymentSchedule.put("sendTime", DateUtil.getCurrentDate());
        }
        //循环需要推送的的系统，批量插入
        for (String key : GlobParam.overdueSendSystem.keySet()) {
            for (Map repaymentSchedule : repaymentScheduleList) {
                repaymentSchedule.put("sendSystem", key);
            }
            //批量插入推送信息表
            int ret = messageInfoDao.insertMessageInfoBatch(repaymentScheduleList);
            if (ret <= 0) {
                logger.error("逾期提醒消息插入失败，请注意，程序继续处理：" + ret + "，系统标识为：" + key);
            }
        }
    }


}
