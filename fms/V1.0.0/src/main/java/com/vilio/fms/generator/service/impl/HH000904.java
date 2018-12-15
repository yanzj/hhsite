package com.vilio.fms.generator.service.impl;

import com.vilio.fms.common.service.BaseService;
import com.vilio.fms.common.service.CheckParam;
import com.vilio.fms.generator.dao.FmsBuildModelDetailsMapper;
import com.vilio.fms.generator.pojo.FmsBuildModelDetails;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.ReturnCode;
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
public class HH000904 extends BaseService{

    @Resource
    CheckParam checkParam;

    @Resource
    FmsBuildModelDetailsMapper fmsBuildModelDetailsMapper;

    /**
     * 在线合同生成结果
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        checkParam.hh000904(paramMap);
        String serialNo = paramMap.get(Fields.PARAM_SERIAL_NO).toString();
        FmsBuildModelDetails fmsBuildModelDetails = new FmsBuildModelDetails();
        fmsBuildModelDetails.setSerialNo(serialNo);
        List<FmsBuildModelDetails> fmsBuildModelDetailsList = fmsBuildModelDetailsMapper.queryDetails(fmsBuildModelDetails);
        List<Map> fileList = new ArrayList<>();
        if(fmsBuildModelDetailsList != null && fmsBuildModelDetailsList.size() > 0){
            for(FmsBuildModelDetails fbmd : fmsBuildModelDetailsList){
                Map fbmdMap = new HashMap();
                fbmdMap.put(Fields.PARAM_FILE_ID, fbmd.getFileCode());
                fbmdMap.put(Fields.PARAM_FILE_NAME, fbmd.getFileName());
                fbmdMap.put(Fields.PARAM_MODEL_FILE_CODE, fbmd.getModelCode());
                fbmdMap.put(Fields.PARAM_CREATE_RESULT, fbmd.getStatus());
                fbmdMap.put(Fields.PARAM_MORTGAGE_TYPE, fbmd.getMortgageType());
                fbmdMap.put(Fields.PARAM_CERTIFICATE_NUMBER_FIRST, fbmd.getCertificateNumberFirst());
                fbmdMap.put(Fields.PARAM_CERTIFICATE_NUMBER_SECOND, fbmd.getCertificateNumberSecond());
                fileList.add(fbmdMap);
            }
        }
        returnMap.put(Fields.PARAM_FILE_LIST, fileList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "在线合同生成结果查询成功！");
        return returnMap;

    }

    @Override
    public String getInterfaceDescription() {
        return "在线合同生成结果查询";
    }
}
