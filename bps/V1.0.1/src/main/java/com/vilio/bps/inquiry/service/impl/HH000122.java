package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsCompanyInquiryApplyMapper;
import com.vilio.bps.commonMapper.dao.IBpsHouseTypeMapper;
import com.vilio.bps.commonMapper.dao.IBpsInquiryMaterialMapper;
import com.vilio.bps.commonMapper.dao.IBpsUserInquiryMapper;
import com.vilio.bps.commonMapper.pojo.*;
import com.vilio.bps.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 录入评估价初始化
 */
@Service
public class HH000122  extends BaseService {
    //前端显示模型-公寓
    final String modelType_apartment = "1";
    //前端显示模型-别墅
    final String modelType_villa = "2";
    //前端显示模型-人工
    final String modelType_hand = "3";
    @Resource
    IBpsUserInquiryMapper iBpsUserInquiryMapper;
    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;
    @Resource
    CommonService commonService;
    @Resource
    IBpsHouseTypeMapper iBpsHouseTypeMapper;
    @Resource
    IBpsInquiryMaterialMapper iBpsInquiryMaterialMapper;


    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map result = new HashMap();

        //参数校验
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_SERIAL_NO);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        String serialNo = (String) paramMap.get(Fields.PARAM_SERIAL_NO);
        //获取询价流水参数
        BpsUserInquiry inquiry = iBpsUserInquiryMapper.getInquiryInfoBySerialNo(serialNo);
        //获取各公司询价记录
        List<BpsCompanyInquiryApply> applyList = iBpsCompanyInquiryApplyMapper.getInquiryInfoBySerialNo(serialNo);

        // 获取该城市的关联估价公司
        Map<String, BpsCompanyCity> companyCityMap = commonService.getCompanyCityMapByCityCode(inquiry.getCityCode());

        result.put(Fields.PARAM_CITY_CODE, inquiry.getCityCode());
        Map<String, BpsCity> citymap = commonService.queryAllCity();
        if(citymap.containsKey(inquiry.getCityCode())){
            result.put(Fields.PARAM_CITY_NAME, citymap.get(inquiry.getCityCode()).getAbbrName());
        }
        result.put(Fields.PARAM_ASSESSMENT_STATUS, inquiry.getStatus());

        if(null != inquiry.getHouseType()) {
            //房屋类型：人工评房时不填
            result.put(Fields.PARAM_HOUSE_TYPE_CODE, inquiry.getHouseType());
            BpsHouseType houseType = iBpsHouseTypeMapper.getHouseTypeByCode(inquiry.getHouseType());
            if(null != houseType){
                result.put(Fields.PARAM_HOUSE_TYPE_NAME, houseType.getName());
            }
        }else{
            //房屋地址：只有人工评房时返回
            result.put(Fields.PARAM_ADDRESS, inquiry.getAddress());
        }

        List resultApplyList = new ArrayList();
        if(null != applyList){
            for(BpsCompanyInquiryApply apply: applyList){
                companyCityMap.remove(apply.getCompanyCode());
                Map companyMap = new HashMap();
                companyMap.put(Fields.PARAM_COMPANY_CODE, apply.getCompanyCode());
                //公寓时字段
                if(null != apply.getPlotsCode())  companyMap.put(Fields.PARAM_PLOTS_CODE, apply.getPlotsCode());
                if(null != apply.getPlotsName())  companyMap.put(Fields.PARAM_PLOTS_NAME, apply.getPlotsName());
                if(null != apply.getUnitCode())  companyMap.put(Fields.PARAM_UNIT_CODE, apply.getUnitCode());
                if(null != apply.getUnitName())  companyMap.put(Fields.PARAM_UNIT_NAME, apply.getUnitName());
                if(null != apply.getHouseCode())  companyMap.put(Fields.PARAM_HOUSE_CODE, apply.getHouseCode());
                if(null != apply.getHouseName())  companyMap.put(Fields.PARAM_HOUSE_NAME, apply.getHouseName());
                if(null != apply.getTotalFloor())  companyMap.put(Fields.PARAM_TOTAL_FLOOR, apply.getTotalFloor().toString());
                if(null != apply.getCurrentFloor())  companyMap.put(Fields.PARAM_CURRENT_FLOOR, apply.getCurrentFloor().toString());
                //共有字段
                if(null != apply.getArea())  companyMap.put(Fields.PARAM_AREA, apply.getArea().toString());
                if(null != apply.getAutoPrice())  companyMap.put(Fields.PARAM_AUTO_PRICE, apply.getAutoPrice());
                if(null != apply.getPrice())  companyMap.put(Fields.PARAM_ASSESSMENT_COMPANY_PRICE, apply.getPrice().toString());
                //别墅时
                if(null != apply.getAddress()) companyMap.put(Fields.PARAM_ADDRESS, apply.getAddress());
                if(null != apply.getAreaCode()) companyMap.put(Fields.PARAM_AREA_CODE, apply.getAreaCode());
                if(null != apply.getAreaName()) companyMap.put(Fields.PARAM_AREA_NAME, apply.getAreaName());

                String companyStatus = apply.getStatus();
                //失效
                if(Constants.BPS_ORDER_STATUS_EVALUATION_INVALID.equals(inquiry.getStatus())){
                    companyStatus = Constants.BPS_ORDER_STATUS_EVALUATION_INVALID;
                }
                if(null != companyStatus)  companyMap.put(Fields.PARAM_STATUS, companyStatus);

                //if(null != apply.getUnitPrice()) companyMap.put(Fields.PARAM_ASSESSMENT_COMPANY_UNIT_PRICE, apply.getUnitPrice());

                //图片
                Map map = new HashedMap();
                map.put(Fields.PARAM_COMPANY_CODE,apply.getCompanyCode());
                map.put(Fields.PARAM_SERIAL_NO,serialNo);
                List<BpsInquiryMaterial> materialList = iBpsInquiryMaterialMapper.queryMaterialBySerialNo(map);
                if(null != materialList && materialList.size() > 0){
                    BpsInquiryMaterial material = materialList.get(0);
                    companyMap.put(Fields.PARAM_FILE_NO, "/fileLoad/getFile?serialNo=" + material.getFileNo());
                    companyMap.put(Fields.PARAM_FILE_NAME, material.getFileName());
                    String time = DateUtil.formatDateTime(material.getDateCreated(),HH000110.DATE_TIME_PATTERN3);
                    companyMap.put(Fields.PARAM_FILE_UPLOAD_TIME, time);
                }


                resultApplyList.add(companyMap);
            }
        }

        //没有存入本地库的公司询价返回
        Iterator it  = companyCityMap.keySet().iterator();
        while(it.hasNext()){

            BpsCompanyCity bpsCompanyCity = companyCityMap.get(it.next());
            Map companyMap = new HashMap();
            companyMap.put(Fields.PARAM_COMPANY_CODE, bpsCompanyCity.getCompanyCode());
            companyMap.put(Fields.PARAM_STATUS, Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
            resultApplyList.add(companyMap);
        }

        if(null != inquiry.getHouseType() && Constants.HOUSE_TYPE_VILLADOM.equals(inquiry.getHouseType())){
            result.put(Fields.PARAM_MODEL_TYPE, modelType_villa);
        }else if(null != inquiry.getHouseType() && Constants.HOUSE_TYPE_ORDINARY.equals(inquiry.getHouseType())){
            result.put(Fields.PARAM_MODEL_TYPE, modelType_apartment);
        }else{
            result.put(Fields.PARAM_MODEL_TYPE, modelType_hand);
        }

        result.put(Fields.PARAM_COMPANY_PARAM_LIST, resultApplyList);
        result.put(Fields.PARAM_ASSESSMENT_TIME, inquiry.getAssessmentTime());
        result.put(Fields.PARAM_ASSESSMENT_PRICE, null == inquiry.getAssessmentPrice() ? "-1": inquiry.getAssessmentPrice().toString());

        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, ReturnCode.SUCCESS_CODE.getReturnMessage());

        return result;
    }

    @Override
    public String getInterfaceDescription() {
        return "录入评估价初始化";
    }
}

