package com.vilio.plms.service.test;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.quartz.BmsSynchronizeService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by martin on 2017/9/16.
 */
@Service
public class Plms900009 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms900009.class);

    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        try{
            logger.info("开始同步bms数据定时任务");
//            SchedulerContext skedCtx = jobExecutionContext.getScheduler().getContext();
            BmsSynchronizeService bmsSynchronizeService = (BmsSynchronizeService) SpringContextUtil.getBean("bmsSynchronizeService");
            bmsSynchronizeService.execute();
            logger.info("完成同步bms数据定时任务");
        }catch(Exception ex){
            logger.info("同步bms数据定时任务执行出现异常:" + ex);
            ex.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("同步bms数据定时任务", ex.getMessage());
        }
    }

}
