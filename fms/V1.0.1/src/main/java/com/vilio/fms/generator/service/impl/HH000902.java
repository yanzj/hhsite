package com.vilio.fms.generator.service.impl;

import com.vilio.fms.common.service.BaseService;
import com.vilio.fms.common.service.CheckParam;
import com.vilio.fms.generator.dao.FmsBuildModelRecordsMapper;
import com.vilio.fms.generator.dao.FmsModelBeanMapper;
import com.vilio.fms.generator.pojo.FmsModelBean;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Constants;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.ReturnCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/17/0017.
 */
@Service
public class HH000902 extends BaseService{

    @Resource
    CheckParam checkParam;

    @Resource
    FmsModelBeanMapper fmsModelBeanMapper;

    @Resource
    FmsBuildModelRecordsMapper fmsBuildModelRecordsMapper;

    /**
     * 打包发送外部机构材料
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        checkParam.hh000902(paramMap);
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
        List<Map> attachFileList = (List<Map>)paramMap.get("accessoryMaterialList");
        JSONArray attachFileArray = attachFileList == null ? new JSONArray() :JSONArray.fromObject(attachFileList);
        forInsertMap.put(Fields.PARAM_ATTACH_FILE_LIST, attachFileArray.toString());
        //此处需整理该外部机构下需要外发的模板，并保存到contractList里
        FmsModelBean fmsModelBean = new FmsModelBean();
        fmsModelBean.setNeedOutSend("Y");
        fmsModelBean.setPartnerGroupCode(paramMap.get(Fields.PARAM_EXTERNAL_INSTITUTIONS_CODE) == null ? "" : paramMap.get(Fields.PARAM_EXTERNAL_INSTITUTIONS_CODE).toString());
        List<FmsModelBean> fmsModelBeanList = fmsModelBeanMapper.queryModelFiles(fmsModelBean);
        JSONArray contractArray = new JSONArray();
        if(fmsModelBeanList != null && fmsModelBeanList.size() > 0){
            List<Map> contractList = new ArrayList<>();
            for(FmsModelBean fmb : fmsModelBeanList){
                Map contractMap = new HashMap();
                contractMap.put(Fields.PARAM_MODEL_FILE_NAME, fmb.getModelFileName());
                contractMap.put(Fields.PARAM_MODEL_FILE_CODE, fmb.getModelFileCode());
                contractList.add(contractMap);
            }
            contractArray = contractList == null ? contractArray :JSONArray.fromObject(contractList);
        }
        forInsertMap.put(Fields.PARAM_CONTRACT_LIST, contractArray.toString());

        forInsertMap.put(Fields.PARAM_SEND_STATUS, Constants.OUT_SEND_STATUS_DOING);
        forInsertMap.put(Fields.PARAM_STATUS, Constants.GENERATOR_STATUS_INIT);
        fmsBuildModelRecordsMapper.insertRecords(forInsertMap);

        returnMap.put(Fields.PARAM_SERIAL_NO, serialNo);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "打包发送外部机构材料成功！");
        return returnMap;

    }

    @Override
    public String getInterfaceDescription() {
        return "打包发送外部机构材料";
    }
}
