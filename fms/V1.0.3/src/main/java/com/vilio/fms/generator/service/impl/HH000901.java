package com.vilio.fms.generator.service.impl;

import com.vilio.fms.common.service.BaseService;
import com.vilio.fms.common.service.CheckParam;
import com.vilio.fms.generator.dao.FmsBuildModelRecordsMapper;
import com.vilio.fms.quartz.service.BuildFileByModelService;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Constants;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.ReturnCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/17/0017.
 */
@Service
public class HH000901 extends BaseService{

    @Resource
    CheckParam checkParam;

    @Resource
    FmsBuildModelRecordsMapper fmsBuildModelRecordsMapper;

    @Resource
    BuildFileByModelService buildFileByModelService;

    @Autowired
    ThreadPoolTaskExecutor poolTaskExecutor;

    Logger logger = Logger.getLogger(HH000901.class);

    /**
     * 接收生成模板的请求，记录并返回流水号
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        checkParam.hh000901(paramMap);
        String serialNo = CommonUtil.getCurrentTimeStr("FBMR-", null);
        //整理待存储字段
        Map forInsertMap = new HashMap();
        forInsertMap.put(Fields.PARAM_SERIAL_NO, serialNo);
        forInsertMap.put(Fields.PARAM_BUILDER_CODE, paramMap.get(Fields.PARAM_USER_NO));
        forInsertMap.put(Fields.PARAM_BUILDER_GROUP_CODE, paramMap.get(Fields.PARAM_EXTERNAL_INSTITUTIONS_CODE));
        forInsertMap.put(Fields.PARAM_BUILDER_GROUP_NAME, paramMap.get(Fields.PARAM_EXTERNAL_INSTITUTIONS_NAME));
        forInsertMap.put(Fields.PARAM_BUSINESS_ID, paramMap.get(Fields.PARAM_LOAN_ID));
        forInsertMap.put(Fields.PARAM_MORTGAGE_TYPE, paramMap.get(Fields.PARAM_MORTGAGE_TYPE));
        forInsertMap.put(Fields.PARAM_SOURCE_SYSTEM, paramMap.get(Fields.PARAM_SOURCE_SYSTEM));
        List<Map> modelList = (List<Map>)paramMap.get(Fields.PARAM_CONTRACT_LIST);
        JSONArray modelArray = modelList == null ? new JSONArray() :JSONArray.fromObject(modelList);
        forInsertMap.put(Fields.PARAM_MODEL_LIST, modelArray.toString());
        forInsertMap.put(Fields.PARAM_SEND_STATUS, Constants.OUT_SEND_STATUS_NO_NEED);
        forInsertMap.put(Fields.PARAM_STATUS, Constants.GENERATOR_STATUS_INIT);
//        JSONObject jsonParam = paramMap == null ? new JSONObject() : JSONObject.fromObject(paramMap);
//        forInsertMap.put(Fields.PARAM_PARAMS, jsonParam.toString());
        fmsBuildModelRecordsMapper.insertRecords(forInsertMap);

        logger.info(Thread.currentThread().getName() + "接收生成模板的请求，记录并返回流水号********************");
        buildFileByModelService.setSerialNo(serialNo);
        poolTaskExecutor.execute(buildFileByModelService);

        returnMap.put(Fields.PARAM_SERIAL_NO, serialNo);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "申请生成模版成功！");
        return returnMap;

    }

    @Override
    public String getInterfaceDescription() {
        return "申请生成模版";
    }
}
