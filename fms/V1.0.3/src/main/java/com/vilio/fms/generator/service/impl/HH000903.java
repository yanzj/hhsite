package com.vilio.fms.generator.service.impl;

import com.vilio.fms.common.service.BaseService;
import com.vilio.fms.common.service.CheckParam;
import com.vilio.fms.generator.dao.FmsBuildModelDetailsMapper;
import com.vilio.fms.generator.dao.FmsBuildModelRecordsMapper;
import com.vilio.fms.generator.pojo.FmsBuildModelDetails;
import com.vilio.fms.generator.pojo.FmsBuildModelRecords;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Constants;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.ReturnCode;
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
public class HH000903 extends BaseService{

    @Resource
    CheckParam checkParam;

    @Resource
    FmsBuildModelRecordsMapper fmsBuildModelRecordsMapper;

    @Resource
    FmsBuildModelDetailsMapper fmsBuildModelDetailsMapper;

    /**
     * 返回材料发送结果
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        checkParam.hh000903(paramMap);
        String serialNo = paramMap.get(Fields.PARAM_SERIAL_NO).toString();

        //Step 1 查询序列号所关联的所有文件
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
                fileList.add(fbmdMap);
            }
        }
        //Step 2 查询序列号所关联的记录的外发状态
        FmsBuildModelRecords fmsBuildModelRecords = new FmsBuildModelRecords();
        fmsBuildModelRecords.setSerialNo(serialNo);
        List<FmsBuildModelRecords> fmsBuildModelRecordsList = fmsBuildModelRecordsMapper.queryRecords(fmsBuildModelRecords);
        if(fmsBuildModelRecordsList != null && fmsBuildModelRecordsList.size() > 0){
            returnMap.put(Fields.PARAM_SEND_RESULT, fmsBuildModelRecordsList.get(0).getSendStatus());
        }

        returnMap.put(Fields.PARAM_FILE_LIST, fileList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "返回材料发送结果成功！");
        return returnMap;

    }

    @Override
    public String getInterfaceDescription() {
        return "返回材料发送结果";
    }
}
