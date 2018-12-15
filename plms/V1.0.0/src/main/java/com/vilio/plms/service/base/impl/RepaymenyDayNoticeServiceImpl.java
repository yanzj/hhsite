package com.vilio.plms.service.base.impl;

import com.vilio.plms.dao.CustomerDao;
import com.vilio.plms.dao.PlmsRepaymentScheduleDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.PlmsRepaymentScheduleBean;
import com.vilio.plms.service.base.RepaymenyDayNoticeService;
import com.vilio.plms.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dell on 2017/9/12.
 */
@Service
public class RepaymenyDayNoticeServiceImpl implements RepaymenyDayNoticeService {
    private static Logger logger = Logger.getLogger(RepaymenyDayNoticeServiceImpl.class);

    @Resource
    PlmsRepaymentScheduleDao plmsRepaymentScheduleDao;
    @Resource
    CustomerDao customerDao;

    @Override
    public void nlbsNotice() throws ErrorException {

    }

    @Override
    public void plmsNotice() throws ErrorException {
        //step1  获取系统配置参数列表，
        List<String> noticeDayList = new ArrayList();
        //todo  数据库获取配置天数
        //遍历所有配置天数,逐个发出
        if(null == noticeDayList || noticeDayList.size() == 0){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "还款日提醒配置参数缺失！");
        }
        for(String  strNoticeDay: noticeDayList){
            int noticeDay = Integer.parseInt(strNoticeDay);
            //提醒Map<用户id,还款计划相关信息>
            Map<String, Set<Map>> noticeMap = new HashedMap();
            //获取需要提醒的还款计划
            Map paramMap = new HashedMap();
            paramMap.put("status", GlobDict.repayment_schedule_status_paying_and_not_overdue.getKey());
            Date repaymentDate =  DateUtil.getDateDistanceInputDate(new Date(), noticeDay);
            paramMap.put("repaymentDate", repaymentDate);
            List<Map> scheduleList = plmsRepaymentScheduleDao.getScheduleInfoByRepaymentDateAndStatus(paramMap);
            if(null == scheduleList || scheduleList.size() < 1){
                logger.debug("没有" + noticeDay + "天后(即" + DateUtil.formatDate(repaymentDate) + ")日的还款计划");
                continue;
            }
            for(Map scheduleBean: scheduleList){
                //获取主借款人（主借款人多个吗？）
                Map customerReqMap = new HashMap();
                customerReqMap.put("applyCode", scheduleBean.get("applyCode"));
                customerReqMap.put("isMain", GlobDict.customer_is_main_yes.getKey());
                List<Map> customerList = customerDao.getCustomerInfoByApplyCode(customerReqMap);
                Map customer = customerList.get(0);
                scheduleBean.put("customerName", customer.get("name"));
                //需要提醒的还款计划分配给相关人员
                String distributorBmsCode = (String) scheduleBean.get("distributorBmsCode");
                List<String> receiveUserList = new ArrayList();
                //todo 根据当前还款计划关联的渠道获取需要提醒的用户
                if(null != receiveUserList && receiveUserList.size() > 0){
                    for(String receiveUser: receiveUserList){
                        if(!noticeMap.containsKey(receiveUser)){
                            Set<Map> scheduleSet = new HashSet();
                            scheduleSet.add(scheduleBean);
                            noticeMap.put(receiveUser, scheduleSet);
                        }
                    }
                }
            }
            //发送提醒
            Iterator it = noticeMap.keySet().iterator();
            while(it.hasNext()){
                String receiveUser = (String) it.next();
                Set<Map> scheduleSet = noticeMap.get(receiveUser);
                int count = scheduleSet.size();
                //站内信提醒

                //邮件提醒
            }

        }
    }
}
