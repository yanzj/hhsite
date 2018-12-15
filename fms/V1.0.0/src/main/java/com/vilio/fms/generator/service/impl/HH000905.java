package com.vilio.fms.generator.service.impl;

import com.vilio.fms.common.service.BaseService;
import com.vilio.fms.generator.dao.FmsModelBeanMapper;
import com.vilio.fms.generator.pojo.FmsModelBean;
import com.vilio.fms.remote.service.RemoteBmsService;
import com.vilio.fms.util.Constants;
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
public class HH000905 extends BaseService{

    @Resource
    FmsModelBeanMapper fmsModelBeanMapper;

    @Resource
    RemoteBmsService remoteBmsService;

    /**
     * 调用BMS接口，把fms的所有模板发送给BMS</br>
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        List<Map> forBmsModelList = new ArrayList<>();
        //Step 1 查询所有模板
        List<FmsModelBean> fmsModelBeanList = fmsModelBeanMapper.queryModelsWithGroup();
        //Step 2 按照bms接口整理参数
        Map forBmsParamMap = new HashMap();
        if(fmsModelBeanList != null && fmsModelBeanList.size() > 0){
            for(FmsModelBean fmb : fmsModelBeanList){
                Map groupMap = new HashMap();
                groupMap.put(Fields.PARAM_EXTERNAL_INSTITUTIONS_CODE, fmb.getPartnerGroupCode());
                groupMap.put(Fields.PARAM_EXTERNAL_INSTITUTIONS_NAME, fmb.getPartnerGroupName());
                groupMap.put(Fields.PARAM_EXTERNAL_INSTITUTIONS_TYPE, fmb.getPartnerGroupType());
                groupMap.put(Fields.PARAM_LOANER, fmb.getPartnerName());
                groupMap.put(Fields.PARAM_MAIL_RECEIVER_ADDRESS, fmb.getMailReceiverAddress());
                groupMap.put(Fields.PARAM_MAIL_CC_ADDRESS, fmb.getMailCcAddress());
                groupMap.put(Fields.PARAM_SHORT_MSG_RECEIVER, fmb.getShortMsgReceiver());
                groupMap.put(Fields.PARAM_WECHAT_RECEIVER, fmb.getWechatReceiver());
                FmsModelBean fmsModelBean = new FmsModelBean();
                fmsModelBean.setPartnerGroupCode(fmb.getPartnerGroupCode());
                fmsModelBean.setPartnerName(fmb.getPartnerName());
                List<FmsModelBean> forFileModelBeanList = fmsModelBeanMapper.queryModelFiles(fmsModelBean);
                List<Map> fileList = new ArrayList<>();
                if(forFileModelBeanList != null && forFileModelBeanList.size() > 0){
                    for(FmsModelBean forFileFmb : forFileModelBeanList){
                        Map forFileMap = new HashMap();
                        forFileMap.put(Fields.PARAM_MODEL_FILE_NAME, forFileFmb.getModelName());
                        forFileMap.put(Fields.PARAM_MODEL_FILE_CODE, forFileFmb.getModelCode());
                        forFileMap.put(Fields.PARAM_LOAN_CONTRACT_FLAG, Constants.IS_LOAN_CONTRACT.equals(forFileFmb.getLoanContractFlag()) ? Constants.IS_LOAN_CONTRACT : Constants.ISNOT_LOAN_CONTRACT);//01：借款合同 02：非借款合同
                        fileList.add(forFileMap);
                    }
                    groupMap.put(Fields.PARAM_FILE_LIST, fileList);
                }
                forBmsModelList.add(groupMap);
            }
        }
        forBmsParamMap.put(Fields.PARAM_EXTERNAL_INSTITUTIONS_LIST, forBmsModelList);
        //Step 3 调用BMS接口，同步
        Map bmsReturnMap = remoteBmsService.callAddOnlinenstitutionsService(forBmsParamMap);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "同步模板信息到核心系统成功！");
        return returnMap;

    }

    @Override
    public String getInterfaceDescription() {
        return "同步模板信息到核心系统";
    }
}
