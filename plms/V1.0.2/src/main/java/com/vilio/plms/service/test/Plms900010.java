package com.vilio.plms.service.test;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.impl.PayScheduleDetailForContractImpl;
import com.vilio.plms.service.quartz.BmsSynchronizeService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by martin on 2017/9/19.
 */
@Service
public class Plms900010 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms900009.class);

    @Resource
    PayScheduleDetailForContractImpl payScheduleDetailForContract;

    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        try{
            logger.info("开始测试重新扣款");
            String contractCode = (String)body.get("contractCode");
            String happenTimeString = (String)body.get("happenTime");
            String batchCode = (String)body.get("batchCode");
            String paymentMethod = (String)body.get("paymentMethod");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date happenTime = sdf.parse(happenTimeString);
            payScheduleDetailForContract.payContractByBatchCode(contractCode,happenTime,batchCode,paymentMethod);
            logger.info("完成测试重新扣款");
        }catch(Exception ex){
            logger.info("同步bms数据定时任务执行出现异常:" + ex);
            ex.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("同步bms数据定时任务", ex.getMessage());
        }
    }
}
