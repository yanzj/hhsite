package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsCompanyInquiryApplyMapper;
import com.vilio.bps.commonMapper.dao.IBpsInquiryApplyRelationMapper;
import com.vilio.bps.commonMapper.dao.IBpsUserInquiryMapper;
import com.vilio.bps.commonMapper.pojo.BpsCompanyInquiryApply;
import com.vilio.bps.commonMapper.pojo.BpsInquiryApplyRelation;
import com.vilio.bps.commonMapper.pojo.BpsUserInquiry;
import com.vilio.bps.inquiry.shchengshi.pojo.SCUnit;
import com.vilio.bps.inquiry.shchengshi.service.SCGetHousingInfo;
import com.vilio.bps.inquiry.worldunion.pojo.SearchBean;
import com.vilio.bps.inquiry.worldunion.service.WUGetHousingInfo;
import com.vilio.bps.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/12/0008.
 */
@Service
public class HH000104 extends BaseService {

    @Resource
    CommonService commonService;

    @Resource
    WUGetHousingInfo wuGetHousingInfo;

    @Resource
    SCGetHousingInfo scGetHousingInfo;

    @Resource
    IBpsUserInquiryMapper iBpsUserInquiryMapper;

    @Resource
    IBpsInquiryApplyRelationMapper iBpsInquiryApplyRelationMapper;

    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_COMPANY_CODE);
        requiredFieldList.add(Fields.PARAM_PLOTS_CODE);

        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        Map returnMap = new HashMap();
        List<Map> returnUnitList = new ArrayList<Map>();
        String companyCode = (String) paramMap.get(Fields.PARAM_COMPANY_CODE);
        String plotsCode = (String) paramMap.get(Fields.PARAM_PLOTS_CODE);
        String unitName = "";

        //Step 2 根据当前对接的估价公司以及相关参数获取楼栋列表
        returnMap.put(Fields.PARAM_COMPANY_CODE, companyCode);
        if (AppraisalCompanys.WORLD_UNION.getCode().equals(companyCode)) {//--世联
            // Step 2.1 准备调用世联服务的参数，获取楼栋列表
            List<SearchBean> searchBeanList = wuGetHousingInfo.getBuild(plotsCode);
            if(searchBeanList != null) {
                //Step 2.2 整理出参，标准化出参
                for (SearchBean sb : searchBeanList) {
                    Map plotsMap = new HashMap();
                    plotsMap.put(Fields.PARAM_UNIT_CODE, sb.getId());
                    plotsMap.put(Fields.PARAM_UNIT_NAME, sb.getName());
                    returnUnitList.add(plotsMap);
                }
            }
        }
        if (AppraisalCompanys.SH_CHENGSHI.getCode().equals(companyCode)) {//--城市
            // Step 2.1 准备调用城市服务的参数，需有楼栋关键字
            unitName = (String) paramMap.get(Fields.PARAM_UNIT_NAME);
            if(StringUtils.isBlank(unitName)){
                throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING.getReturnCode(), ReturnCode.REQUIRED_FIELD_MISSING.getReturnMessage() + "[" + Fields.PARAM_UNIT_NAME +"]");
            }
            List<SCUnit> scUnitList = scGetHousingInfo.getUnitList(plotsCode, unitName);
            if(scUnitList != null){
                //Step 2.2 整理出参，标准化出参
                for(SCUnit su : scUnitList){
                    Map plotsMap = new HashMap();
                    plotsMap.put(Fields.PARAM_UNIT_CODE, su.getId());
                    plotsMap.put(Fields.PARAM_UNIT_NAME, su.getUnitNo());
                    plotsMap.put(Fields.PARAM_TOWARDS, su.getTowards());
                    plotsMap.put(Fields.PARAM_YEAR_BUILT, su.getYear());
                    returnUnitList.add(plotsMap);
                }
            }
        }

        returnMap.put(Fields.PARAM_UNIT_LIST, returnUnitList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "根据小区（楼盘）获取楼栋列表成功！");
        return returnMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "根据小区（楼盘）获取楼栋列表";
    }
}
