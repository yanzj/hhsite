package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsCityMapper;
import com.vilio.bps.commonMapper.dao.IBpsHouseTypeMapper;
import com.vilio.bps.commonMapper.pojo.BpsCity;
import com.vilio.bps.commonMapper.pojo.BpsHouseType;
import com.vilio.bps.util.CommonUtil;
import com.vilio.bps.util.Constants;
import com.vilio.bps.util.Fields;
import com.vilio.bps.util.ReturnCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 询价列表初始化
 */
@Service
public class HH000121  extends BaseService {

    @Resource
    CommonService commonService;

    @Resource
    IBpsCityMapper iBpsCityMapper;

    @Resource
    IBpsHouseTypeMapper iBpsHouseTypeMapper;

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        List<Map> returnCityList = new ArrayList<Map>();
        List<Map> returnHouseTypeList = new ArrayList<Map>();

        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_CITY_CODE);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        //Step 2 获取用户所在渠道的城市
        String cityCode = (String) paramMap.get(Fields.PARAM_CITY_CODE);
        //Step 3 获取所有城市列表
        //Step 3.1 如果城市是全国
        if(Constants.CITY_CODE_QUANGUO.equals(cityCode)){
            List<BpsCity> bpsCityList = commonService.getValidCitys();
            if(bpsCityList != null){
                //整理城市列表出参
                for(BpsCity bc : bpsCityList){
                    Map bpsCityMap = new HashMap();
                    bpsCityMap.put(Fields.PARAM_CITY_CODE, bc.getCode());
                    bpsCityMap.put(Fields.PARAM_CITY_NAME, bc.getAbbrName());
                    returnCityList.add(bpsCityMap);
                }
            }
        } else { //Step 3.2 如果城市不是
            BpsCity bpsCity = iBpsCityMapper.getBeanByCode(cityCode);
            if(bpsCity != null){
                Map bpsCityMap = new HashMap();
                bpsCityMap.put(Fields.PARAM_CITY_CODE, bpsCity.getCode());
                bpsCityMap.put(Fields.PARAM_CITY_NAME, bpsCity.getAbbrName());
                returnCityList.add(bpsCityMap);
            }
        }
        //Step 4 获取状态
        List statusList = commonService.getAvaliableInquiryStatus();


        returnMap.put(Fields.PARAM_CITY_LIST, returnCityList);
        returnMap.put("statusList", statusList);

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "询价列表初始化（城市、状态列表）成功！");
        return returnMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "询价列表初始化（城市、状态列表）";
    }
}
