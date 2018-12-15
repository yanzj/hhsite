package com.vilio.fms.generator.service.impl;

import com.vilio.fms.generator.service.ApplyService;
import com.vilio.fms.remote.service.RemoteBmsService;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.HHBizException;
import com.vilio.fms.util.ReturnCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 进件申请相关的接口实现
 */
@Service("applyService")
public class ApplyServiceImpl implements ApplyService {

    @Resource
    RemoteBmsService remoteBmsService;

    /**
     * 查询进件详情
     *
     * @param businessId
     * @return
     * @throws Exception
     */
    public Map queryApplyRecord(String businessId) throws Exception {
        Map<String, Object> resultMap = new HashMap();

        //Step 1 整理参数，调用BMS核心系统接口
        Map<String, Object> forBmsParamMap = new HashMap();
        forBmsParamMap.put(Fields.PARAM_LOAN_ID, businessId);
        Map bmsResultMap = remoteBmsService.callOnlineloanMasterService(forBmsParamMap);

        //bmsResultMap不会为null，故此处不再判断空值
        if (ReturnCode.SUCCESS_CODE.equals(bmsResultMap.get(Fields.PARAM_MESSAGE_ERR_CODE))) {
            resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "查询成功");
        } else {
            throw new HHBizException(bmsResultMap.get(Fields.PARAM_MESSAGE_ERR_CODE).toString(), bmsResultMap.get(Fields.PARAM_MESSAGE_ERR_MESG).toString());
        }
        bmsResultMap.remove(Fields.PARAM_MESSAGE_ERR_CODE);
        bmsResultMap.remove(Fields.PARAM_MESSAGE_ERR_MESG);
        return resultMap;
    }

}