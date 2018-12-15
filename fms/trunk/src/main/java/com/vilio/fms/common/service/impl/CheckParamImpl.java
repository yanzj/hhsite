package com.vilio.fms.common.service.impl;

import com.vilio.fms.common.service.CheckParam;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.HHBizException;
import com.vilio.fms.util.ReturnCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/7/0007.
 */
@Service
public class CheckParamImpl implements CheckParam {

    @Override
    public void hh000900(Map paramMap) throws Exception {
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_MODEL_CODE))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_MODEL_CODE + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_PARTNER_GROUP_CODE))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_PARTNER_GROUP_CODE + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_MODEL_FILE_CODE))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_MODEL_FILE_CODE + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_MODEL_FILE_NAME))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_MODEL_FILE_NAME + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_MODEL_NAME))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_MODEL_NAME + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_FILE_SUFFIX))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_FILE_SUFFIX + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_SERVICE_FUNCTION_NO))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_SERVICE_FUNCTION_NO + "]。");
        }
    }

    @Override
    public void hh000901(Map paramMap) throws Exception {
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_LOAN_ID))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_LOAN_ID + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_MORTGAGE_TYPE))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_MORTGAGE_TYPE + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_SOURCE_SYSTEM))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_SOURCE_SYSTEM + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_CONTRACT_LIST))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_CONTRACT_LIST + "]。");
        } else {
            List<Map> contractList = (List<Map>) paramMap.get(Fields.PARAM_CONTRACT_LIST);
            if(contractList == null || contractList.size() == 0){
                throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "合同列表不能为空！");
            }
        }
    }

    @Override
    public void hh000902(Map paramMap) throws Exception {
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_LOAN_ID))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_LOAN_ID + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_SOURCE_SYSTEM))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_SOURCE_SYSTEM + "]。");
        }
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_CONTRACT_LIST))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_CONTRACT_LIST + "]。");
        } else {
            List<Map> contractList = (List<Map>) paramMap.get(Fields.PARAM_CONTRACT_LIST);
            if(contractList == null || contractList.size() == 0){
                throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "合同列表不能为空！");
            }
        }

        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_SEND_TYPE))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_SEND_TYPE + "]。");
        }
    }

    @Override
    public void hh000903(Map paramMap) throws Exception {
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_SERIAL_NO))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_SERIAL_NO + "]。");
        }
    }

    @Override
    public void hh000904(Map paramMap) throws Exception {
        if(CommonUtil.isNullOrSpace(paramMap.get(Fields.PARAM_SERIAL_NO))){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失[" + Fields.PARAM_SERIAL_NO + "]。");
        }
    }
}
