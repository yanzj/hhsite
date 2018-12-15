package com.vilio.plms.service.quartz.impl;

import com.vilio.plms.dao.PlmsPaidInfoDao;
import com.vilio.plms.pojo.PaidInfo;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.PaidInfoService;
import com.vilio.plms.service.quartz.PaidInfoDeleteService;
import com.vilio.plms.service.quartz.PayRepaymentScheduleDetailService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dell on 2017/8/30.
 */
@Service("paidInfoDeleteService")
public class PaidInfoDeleteServiceImpl implements PaidInfoDeleteService{
    private static Logger logger = Logger.getLogger(PaidInfoDeleteServiceImpl.class);

    @Resource
    PaidInfoService paidInfoService;
    @Resource
    PlmsPaidInfoDao plmsPaidInfoDao;

    @Override
    public void execute() throws Exception {
        List<PaidInfo> list = plmsPaidInfoDao.queryPaidInfoNeedCancel(null);
        if(null == list || list.size() <1){
            logger.info("_______PaidInfoDeleteServiceImpl  has no data need deal!_____");
        }
        for(PaidInfo paidInfo: list){
            paidInfoService.cancelPaidInfo(paidInfo.getCode());

        }
    }
}
