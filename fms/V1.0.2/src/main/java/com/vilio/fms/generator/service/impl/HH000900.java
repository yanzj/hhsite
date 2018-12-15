package com.vilio.fms.generator.service.impl;

import com.vilio.fms.common.service.BaseService;
import com.vilio.fms.common.service.CheckParam;
import com.vilio.fms.generator.dao.FmsModelBeanMapper;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.ReturnCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件模版基础数据维护
 * Created by dell on 2017/7/5/0005.
 */
@Service
public class HH000900 extends BaseService {

    @Resource
    CheckParam checkParam;

    @Resource
    FmsModelBeanMapper fmsModelBeanMapper;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        //Step 1 入参检查
        checkParam.hh000900(paramMap);
        //Step 2 存储
        fmsModelBeanMapper.insertModel(paramMap);

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "文件模板基础数据维护成功！");
        return returnMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "文件模板基础数据维护";
    }
}
