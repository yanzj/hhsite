package com.vilio.plms.service.task;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 类名： ReceiptsService<br>
 * 功能：资金入账异步任务业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月18日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class ReceiptsTask implements Runnable,Serializable{

    private static Logger logger = Logger.getLogger(ReceiptsTask.class);

    @Override
    public void run() {
        logger.info("资金入账异步任务启动！");
        BaseService quartz = (BaseService) SpringContextUtil.getBean("receiptsService");
        try {
            quartz.execute();
        } catch (ErrorException e) {
            e.printStackTrace();
            if (ReturnCode.ACCOUNT_LOCK.equals(e.getErroCode())) {
                return;
            }
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("资金入账异步任务", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            CommonService commonService = (CommonService) SpringContextUtil.getBean("commonService");
            commonService.monitorEmail("资金入账异步任务", e.getMessage());
        }
        logger.info("资金入账异步任务结束！");
    }
}
