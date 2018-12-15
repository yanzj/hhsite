package com.vilio.plms.service.query;

import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms300012<br>
 * 功能：还款计划查询初始化接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月31日<br>
 * 作者： liuzhu.feng<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms300012 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms300012.class);

    @Resource
    CommonService commonService;

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

        //获取贷款状态列表
        List loanStatusList = commonService.queryLoanStatusList();
        resultMap.put(Fields.PARAM_LOAN_STATUS_LIST, loanStatusList);

    }

}
