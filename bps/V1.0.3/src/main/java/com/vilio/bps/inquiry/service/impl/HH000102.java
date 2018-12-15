package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.util.CommonUtil;
import com.vilio.bps.util.Fields;
import com.vilio.bps.util.ReturnCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/8/0008.
 */
@Service
public class HH000102 extends BaseService{

    @Resource
    CommonService commonService;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        List<Map> returnCompanyList = new ArrayList<Map>();
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_CITY_CODE);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        String cityCode = (String) paramMap.get(Fields.PARAM_CITY_CODE);
        if(StringUtils.isNotBlank(cityCode)){
            //Step 2 调用公共服务--根据城市获取关联估价公司列表
            returnCompanyList = commonService.getCompanyListByCityCode(cityCode);
        }
        returnMap.put(Fields.PARAM_COMPANY_LIST, returnCompanyList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "根据城市获取关联估价公司列表成功！");
        return returnMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "根据城市获取关联估价公司列表";
    }

}
