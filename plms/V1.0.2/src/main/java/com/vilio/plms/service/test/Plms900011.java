package com.vilio.plms.service.test;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.RollBackPaymentAndOverdueService;
import com.vilio.plms.service.base.impl.PayScheduleDetailForContractImpl;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by martin on 2017/9/20.
 */
@Service
public class Plms900011 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms900011.class);

    @Resource
    RollBackPaymentAndOverdueService rollBackPaymentAndOverdueService;

    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        try{
            logger.info("开始测试回滚逾期和扣款");
            String contractCode = (String)body.get("contractCode");
            String batchCode = (String)body.get("batchCode");
            String rollBachDate = (String)body.get("rollBachDate");
            rollBackPaymentAndOverdueService.mainJob(rollBachDate, contractCode, batchCode);
            logger.info("完成测试回滚逾期和扣款");
        }catch(Exception ex){
            logger.info("测试回滚逾期和扣款执行出现异常:" + ex);
            ex.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("测试回滚逾期和扣款", ex.getMessage());
        }
    }
}
