package com.vilio.plms.service.bms;

import java.util.Map;

/**
 * Created by dell on 2017/8/23.
 */
public interface BmsRepaymentScheduleService {
    public void calculateRepaymentScheduleDependencyInfo(Map requestMap) throws Exception;
}
