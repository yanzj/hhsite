package com.vilio.plms.service.fundManagement;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.FundManagerDao;
import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.dao.QueryDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms100040<br>
 * 功能：还款到账登记-手工登记初始化<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100040 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100040.class);

    @Resource
    FundManagerDao fundManagerDao;
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
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String userNo = body.get(Fields.PARAM_USER_NO) == null ? "" : body.get(Fields.PARAM_USER_NO).toString();

        String contractCode = body.get("contractCode").toString();
        Map map = fundManagerDao.queryContractInfoForManualReceipts(contractCode);

        if(map == null){
            map = new HashMap();
        }

        resultMap.putAll(map);

        //收款方列表
        resultMap.put("accountTypeList", commonService.queryAccountTypeList());

        //还款来源列表
        resultMap.put("fundSourceList", commonService.querFundSourceList());
    }

}
