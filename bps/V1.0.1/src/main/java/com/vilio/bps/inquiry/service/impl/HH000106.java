package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.commonMapper.dao.IBpsCityMapper;
import com.vilio.bps.commonMapper.pojo.BpsCity;
import com.vilio.bps.inquiry.worldunion.pojo.WUCity;
import com.vilio.bps.inquiry.worldunion.service.WUGetCitys;
import com.vilio.bps.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取行政区域列表（仅限世联）
 */
@Service
public class HH000106 extends BaseService {

    @Resource
    ConfigInfo configInfo;

    @Resource
    IBpsCityMapper iBpsCityMapper;

    @Resource
    WUGetCitys wuGetCitys;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_CITY_CODE);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        Map returnMap = new HashMap();
        List<Map> returnAreaList = new ArrayList<Map>();
        String cityCode = (String) paramMap.get(Fields.PARAM_CITY_CODE);

        //Step 2 准备调用世联服务的参数，获取列表
        BpsCity bpsCity = iBpsCityMapper.getBeanByCode(cityCode);
        String wuCityCode = bpsCity.getWuCode();
        List<WUCity> wuCityList = wuGetCitys.getAreasByCityCode(wuCityCode);
        if (wuCityList != null) {
            //Step 2.2 整理出参，标准化出参
            for (WUCity wc : wuCityList) {
                Map areaMap = new HashMap();
                areaMap.put(Fields.PARAM_AREA_CODE, wc.getId());
                areaMap.put(Fields.PARAM_AREA_NAME, wc.getName());
                returnAreaList.add(areaMap);
            }
        }
        returnMap.put(Fields.PARAM_AREA_LIST, returnAreaList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "根据城市获取行政区域列表成功！");
        return returnMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "根据城市获取行政区域列表";
    }
}
