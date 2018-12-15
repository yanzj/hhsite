package com.vilio.plms.service.base;

/**
 * Created by dell on 2017/9/20.
 */
public interface OverdueNoticeService {
    //进件还款日消息提醒
    public void nlbsNotice() throws Exception;
    //贷后还款日消息提醒
    public void plmsNotice() throws Exception;
}
