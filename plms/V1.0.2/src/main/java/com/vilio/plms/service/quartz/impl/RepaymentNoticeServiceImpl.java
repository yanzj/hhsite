package com.vilio.plms.service.quartz.impl;

import com.vilio.plms.service.base.RepaymenyDayNoticeService;
import com.vilio.plms.service.quartz.RepaymentNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dell on 2017/9/18.
 */
@Service("repaymentNoticeService")
public class RepaymentNoticeServiceImpl implements RepaymentNoticeService {
    @Resource
    RepaymenyDayNoticeService repaymenyDayNoticeService;


    @Override
    public void execute() throws Exception {
        repaymenyDayNoticeService.nlbsNotice();
        repaymenyDayNoticeService.plmsNotice();
    }
}
