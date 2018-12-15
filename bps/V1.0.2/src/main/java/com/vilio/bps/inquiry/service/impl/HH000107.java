package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.common.service.impl.CommonServiceImpl;
import com.vilio.bps.commonMapper.dao.*;
import com.vilio.bps.commonMapper.pojo.*;
import com.vilio.bps.inquiry.worldunion.pojo.WUCity;
import com.vilio.bps.inquiry.worldunion.service.WUGetCitys;
import com.vilio.bps.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 人工询价
 */
@Service
public class HH000107 extends BaseService {
    private static final String DATE_TIME_PATTERN3 = "yyyy-MM-dd HH:mm:ss";

    @Resource
    CommonService commonService;
    @Resource
    IBpsUserInquiryMapper iBpsUserInquiryMapper;
    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;
    @Resource
    IBpsInquiryApplyRelationMapper iBpsInquiryApplyRelationMapper;
    @Resource
    IBpsInquiryMaterialMapper iBpsInquiryMaterialMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map subExcute(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_CITY_CODE);
        requiredFieldList.add(Fields.PARAM_ADDRESS);
        requiredFieldList.add(Fields.PARAM_INQUIRY_USER_USERID);
        requiredFieldList.add(Fields.PARAM_INQUIRY_SOURCE_SYSTEM);
        requiredFieldList.add(Fields.PARAM_COMPANY_PARAM_LIST);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        Map returnMap = new HashMap();

        String cityCode = (String) paramMap.get(Fields.PARAM_CITY_CODE);
        String address = (String) paramMap.get(Fields.PARAM_ADDRESS);
        String serialNo = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_SERIAL_NO_PREFIX, Fields.INQUIRY_SERIAL_NO_SUFFIX);
        List<Map> companyParamList = (List<Map>) paramMap.get(Fields.PARAM_COMPANY_PARAM_LIST);

        returnMap.put(Fields.PARAM_ADDRESS, address);
        returnMap.put(Fields.PARAM_CITY_CODE, cityCode);

        String assessPrice = "-1";//评估价格
        String assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;//评估状态
        String assessDate = null;//评估时间(返回前端的评估时间格式)
        Date assessTime = new Date();//保存至数据库的时间
        assessDate =  DateUtil.convert(assessTime, HH000107.DATE_TIME_PATTERN3);

        //新建询价请求流水
        BpsUserInquiry bpsUserInquiry = new BpsUserInquiry ();
        bpsUserInquiry.setSerialNo(serialNo);
        bpsUserInquiry.setCompanyId((String) paramMap.get(Fields.PARAM_INQUIRY_USER_COMPANYID));
        bpsUserInquiry.setCompanyName((String) paramMap.get(Fields.PARAM_INQUIRY_USER_COMPANYNAME));
        bpsUserInquiry.setDepartmentId((String) paramMap.get(Fields.PARAM_INQUIRY_USER_DEPARTMENTID));
        bpsUserInquiry.setDepartmentName((String) paramMap.get(Fields.PARAM_INQUIRY_USER_DEPARTMENTNAME));
        bpsUserInquiry.setUserName((String) paramMap.get(Fields.PARAM_INQUIRY_USER_USERNAME));
        bpsUserInquiry.setUserId((String) paramMap.get(Fields.PARAM_INQUIRY_USER_USERID));
        bpsUserInquiry.setCityCode((String) paramMap.get(Fields.PARAM_CITY_CODE));
        bpsUserInquiry.setSourceSystem((String) paramMap.get(Fields.PARAM_INQUIRY_SOURCE_SYSTEM));
        bpsUserInquiry.setAddress((String) paramMap.get(Fields.PARAM_ADDRESS));
        bpsUserInquiry.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
        Date deadLine = commonService.getDeadLineTime(new Date());
        bpsUserInquiry.setDeadline(deadLine);
        iBpsUserInquiryMapper.saveUserInquiry(bpsUserInquiry);

        List companyPriceList = new ArrayList();
        List<BigDecimal> priceList = new ArrayList<BigDecimal>();
        for(Map map : companyParamList){
            requiredFieldList.clear();
            requiredFieldList.add(Fields.PARAM_COMPANY_CODE);
            requiredFieldList.add(Fields.PARAM_ASSESSMENT_COMPANY_PRICE);
            CommonUtil.checkRequiredFields(map, requiredFieldList);

            String companyCode = (String) map.get(Fields.PARAM_COMPANY_CODE);
            String companyPrice = (String) map.get(Fields.PARAM_ASSESSMENT_COMPANY_PRICE);
            //校验价格
            String moneyStatus = NumberUtil.isMoney(companyPrice, 9, 2);
            if(!NumberUtil.money_status_success.equals(moneyStatus)){
                throw new HHBizException(ReturnCode.MONEY_ERROR.getReturnCode(), ReturnCode.MONEY_ERROR.getReturnMessage());
            }
            String fileSerialNo = (String) map.get(Fields.PARAM_INQUIRY_FILE_SERIAL_NO);
            BigDecimal priceBigDecimal = new BigDecimal(companyPrice);
            priceList.add(priceBigDecimal);
            //请求序列号
            String applyCode = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_APPLY_CODE_PREFIX, Fields.INQUIRY_APPLY_CODE_SUFFIX);

            //新增公司流水记录
            BpsCompanyInquiryApply bpsCompanyInquiryApply = new BpsCompanyInquiryApply();
            bpsCompanyInquiryApply.setCode(applyCode);
            bpsCompanyInquiryApply.setCityCode(cityCode);
            bpsCompanyInquiryApply.setCompanyCode(companyCode);
            bpsCompanyInquiryApply.setPrice(priceBigDecimal);
            bpsCompanyInquiryApply.setPriceTime(new Date());
            bpsCompanyInquiryApply.setAutoPrice(Constants.APPLY_AUTO_PRICE_MAN);
            bpsCompanyInquiryApply.setAddress((String) paramMap.get(Fields.PARAM_ADDRESS));
            bpsCompanyInquiryApply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATED);
            iBpsCompanyInquiryApplyMapper.saveApply(bpsCompanyInquiryApply);
            //关联关系
            BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
            bpsInquiryApplyRelation.setApplyCode(applyCode);
            bpsInquiryApplyRelation.setSerialNo(serialNo);
            iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);
            //图片
            if(null != fileSerialNo){
                String code = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_PICTURE_CODE_PREFIX, Fields.INQUIRY_PICTURE_CODE_SUFFIX);
                Map fileMap = new HashMap();
                fileMap.put(Fields.PARAM_CODE, code);
                fileMap.put(Fields.PARAM_SERIAL_NO, serialNo);
                fileMap.put(Fields.PARAM_FILE_NO, fileSerialNo);
                fileMap.put(Fields.PARAM_FILE_NAME, (String) map.get(Fields.PARAM_FILE_NAME));
                fileMap.put(Fields.PARAM_COMPANY_CODE, companyCode);
                iBpsInquiryMaterialMapper.saveMaterial(fileMap);
            }

            Map companyMap = new HashMap();
            companyMap.put(Fields.PARAM_ASSESSMENT_COMPANY_PRICE, companyPrice);
            companyMap.put(Fields.PARAM_STATUS, Constants.BPS_ORDER_STATUS_EVALUATED);
            companyPriceList.add(companyMap);
        }


        Map returnPriceMap = commonService.getFinalPrice(priceList);
        assessPrice = (String) returnPriceMap.get(Fields.PARAM_ASSESSMENT_PRICE);
        if(null == assessPrice){
            assessPrice = "-1";
        }
        if("0".equals(assessPrice) || "-1".equals(assessPrice)){
            assessStatus = Constants.BPS_ORDER_STATUS_EVALUATION_INVALID;
        }else{
            assessStatus = Constants.BPS_ORDER_STATUS_EVALUATED;
        }
        //更新询价流水
        Map param = new HashMap();
        param.put(Fields.PARAM_SERIAL_NO, serialNo);
        param.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
        param.put(Fields.PARAM_STATUS, assessStatus);
        param.put(Fields.PARAM_ASSESSMENT_TIME, assessTime);
        iBpsUserInquiryMapper.saveInquiryPriceAndStatus(param);

        returnMap.put(Fields.PARAM_COMPANY_PARAM_LIST, companyPriceList);
        returnMap.put(Fields.PARAM_SERIAL_NO, serialNo);
        returnMap.put(Fields.PARAM_ASSESSMENT_STATUS, assessStatus);
        returnMap.put(Fields.PARAM_ASSESSMENT_TIME, assessDate);

        returnMap.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "人工询价成功！");
        return returnMap;
    }

    @Override
    public String getInterfaceDescription() {
        return "人工询价";
    }
}
