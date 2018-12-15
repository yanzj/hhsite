package com.vilio.plms.service.base;

import com.vilio.plms.exception.ErrorException;

/**
 * 还款日消息提醒业务
 */
public interface RepaymenyDayNoticeService {
    //进件还款日消息提醒
    public void nlbsNotice() throws ErrorException;
    //贷后还款日消息提醒
    public void plmsNotice() throws ErrorException;
}
