package com.vilio.plms.service;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.PaidInfo;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 放款删除
 */
@Service
public class Plms100201 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms100201.class);

    @Resource
    PlmsPaidInfoDao plmsPaidInfoDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (!JudgeUtil.isNull(body.get("paidCode"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "请传入放款编号！");
        }
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String paidCode = (String) body.get("paidCode");
        //将放款状态改为放款删除中
        PaidInfo paidInfo = new PaidInfo();
        paidInfo.setCode(paidCode);
        paidInfo.setStatus(GlobDict.paid_info_status_delete.getKey());
        int flag = plmsPaidInfoDao.updatePaidInfoByCode(paidInfo);
    }
}
