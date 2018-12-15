package com.vilio.plms.service.test;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.quartz.PayRepaymentScheduleDetailService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.Serializers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by martin on 2017/8/9.
 */
@Service
public class Plms900003 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms900002.class);
    @Resource
    PayRepaymentScheduleDetailService payRepaymentScheduleDetailService;

    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        try {
            payRepaymentScheduleDetailService.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
