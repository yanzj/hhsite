package com.vilio.plms.service.query;

import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.service.base.BaseService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： Plms300060<br>
 * 功能：查看逾期记录详细<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月31日<br>
 * 作者： fengliuzhu<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms300060 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms300060.class);

    @Resource
    QueryDao queryDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    /**
     * 业务流程实现
     *
     * @param head
     * @param body
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String repaymentScheduleCode = body.get("repaymentScheduleCode").toString();

        //根据还款计划code查找还款计划信息
        Map repaymentScheduleInfo = queryDao.queryOverDueRepaymentScheduleDetail(repaymentScheduleCode);

        if (StringUtils.isBlank(repaymentScheduleCode) || repaymentScheduleInfo == null || repaymentScheduleInfo.isEmpty()) {
            return;
        }

        //查找还款计划明细（详情）
        resultMap.putAll(repaymentScheduleInfo);

    }

}
