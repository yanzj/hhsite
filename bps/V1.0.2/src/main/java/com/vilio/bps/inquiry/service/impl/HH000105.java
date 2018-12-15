package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsCityMapper;
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
public class HH000105 extends BaseService {

    @Resource
    ConfigInfo configInfo;

    @Resource
    CommonService commonService;

    @Resource
    WUGetHousingInfo wuGetHousingInfo;

    @Resource
    SCGetHousingInfo scGetHousingInfo;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_COMPANY_CODE);
        requiredFieldList.add(Fields.PARAM_UNIT_CODE);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        Map returnMap = new HashMap();
        List<Map> returnHouseList = new ArrayList<Map>();
        String companyCode = (String) paramMap.get(Fields.PARAM_COMPANY_CODE);
        String unitCode = (String) paramMap.get(Fields.PARAM_UNIT_CODE);

        //Step 2 根据当前对接的估价公司以及相关参数获取房号列表
        returnMap.put(Fields.PARAM_COMPANY_CODE, companyCode);
        if (AppraisalCompanys.WORLD_UNION.getCode().equals(companyCode)) {//--世联
            // Step 2.1 准备调用世联服务的参数，获取房号列表
            List<SearchBean> searchBeanList = wuGetHousingInfo.getHouse(unitCode);
            if(searchBeanList != null) {
                //Step 2.2 整理出参，标准化出参
                for (SearchBean sb : searchBeanList) {
                    Map houseMap = new HashMap();
                    houseMap.put(Fields.PARAM_HOUSE_CODE, sb.getId());
                    houseMap.put(Fields.PARAM_HOUSE_NAME, sb.getName());
                    returnHouseList.add(houseMap);
                }
            }
        }
        returnMap.put(Fields.PARAM_HOUSE_LIST, returnHouseList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "根据楼栋号获取房号列表成功！");
        return returnMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "根据楼栋号获取房号列表";
    }
}
