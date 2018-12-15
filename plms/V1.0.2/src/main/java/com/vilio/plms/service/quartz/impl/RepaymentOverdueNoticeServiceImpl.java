package com.vilio.plms.service.quartz.impl;

import com.vilio.plms.service.base.OverdueNoticeService;
import com.vilio.plms.service.base.RepaymenyDayNoticeService;
import com.vilio.plms.service.base.impl.OverdueNoticeServiceImpl;
import com.vilio.plms.service.quartz.RepaymentOverdueNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dell on 2017/9/20.
 */
@Service("repaymentOverdueNoticeService")
public class RepaymentOverdueNoticeServiceImpl implements RepaymentOverdueNoticeService{
    @Resource
    OverdueNoticeService overdueNoticeService;


    @Override
    public void execute() throws Exception {
        overdueNoticeService.nlbsNotice();
        overdueNoticeService.plmsNotice();
    }
}
