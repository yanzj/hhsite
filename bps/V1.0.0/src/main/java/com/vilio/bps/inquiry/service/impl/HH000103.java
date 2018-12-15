package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsCityMapper;
import com.vilio.bps.commonMapper.pojo.BpsCity;
import com.vilio.bps.inquiry.service.*;
import com.vilio.bps.inquiry.shchengshi.pojo.SCProject;
import com.vilio.bps.inquiry.shchengshi.service.SCGetHousingInfo;
import com.vilio.bps.inquiry.worldunion.pojo.SearchBean;
import com.vilio.bps.inquiry.worldunion.service.WUGetHousingInfo;
import com.vilio.bps.util.*;
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
public class HH000103 extends BaseService {

    @Resource
    CommonService commonService;

    @Resource
    IBpsCityMapper iBpsCityMapper;

    @Resource
    WUGetHousingInfo wuGetHousingInfo;

    @Resource
    SCGetHousingInfo scGetHousingInfo;



    @Override
    public Map subExcute(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_CITY_CODE);
        requiredFieldList.add(Fields.PARAM_PLOTS_NAME);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        Map returnMap = new HashMap();
        List<Map> returnPlotsList = new ArrayList<Map>();
        String plotsName = (String) paramMap.get(Fields.PARAM_PLOTS_NAME);
        String cityCode = (String) paramMap.get(Fields.PARAM_CITY_CODE);

        //Step 2 获取当前城市对接的估价公司列表
        List<Map> companyList = commonService.getCompanyListByCityCode(cityCode);//无需判断出参是否为null
        for(Map map : companyList){
            String companyCode = (String) map.get(Fields.PARAM_COMPANY_CODE);
            Map companyPlotsMap = new HashMap();
            List<Map> companyPlotsList = new ArrayList<Map>();
            companyPlotsMap.put(Fields.PARAM_COMPANY_CODE, companyCode);
            //Step 3 判断取哪家估价公司的小区信息
            if (AppraisalCompanys.WORLD_UNION.getCode().equals(companyCode)) {//--世联
                // Step 3.1 准备调用世联服务的参数，城市字段取数据库中对应字段
                BpsCity bpsCity = iBpsCityMapper.getBeanByCode(cityCode);
                if(bpsCity != null) {
                    List<SearchBean> searchBeanList = wuGetHousingInfo.getConstruction(bpsCity.getWuCode(), plotsName);
                    if(searchBeanList != null){
                        //Step 3.2 整理出参，标准化出参
                        for(SearchBean sb : searchBeanList){
                            Map plotsMap = new HashMap();
                            plotsMap.put(Fields.PARAM_PLOTS_CODE, sb.getId());
                            plotsMap.put(Fields.PARAM_PLOTS_NAME, sb.getName());
                            companyPlotsList.add(plotsMap);
                        }
                    }
                }
            }
            if (AppraisalCompanys.SH_CHENGSHI.getCode().equals(companyCode)) {//--城市
                // Step 3.1 准备调用城市服务的参数，目前仅支持上海
                if(Constants.CITY_CODE_SHANGHAI.equals(cityCode)){
                    List<SCProject> scProjectList = scGetHousingInfo.getProjectList(plotsName);
                    if(scProjectList != null){
                        //Step 3.2 整理出参，标准化出参
                        for(SCProject sp : scProjectList){
                            Map plotsMap = new HashMap();
                            plotsMap.put(Fields.PARAM_PLOTS_CODE, sp.getId());
                            plotsMap.put(Fields.PARAM_PLOTS_NAME, sp.getAddress());
                            companyPlotsList.add(plotsMap);
                        }
                    }
                }
            }
            companyPlotsMap.put(Fields.PARAM_PLOTS_LIST, companyPlotsList);
            returnPlotsList.add(companyPlotsMap);
        }

        returnMap.put(Fields.PARAM_ALL_PLOTS_LIST, returnPlotsList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "根据城市获取小区（楼盘列表）成功！");
        return returnMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "根据城市获取小区（楼盘列表）";
    }
}
