package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.common.service.impl.CommonServiceImpl;
import com.vilio.bps.commonMapper.dao.*;
import com.vilio.bps.commonMapper.pojo.*;
import com.vilio.bps.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 录入评估价提交(待评估提交)
 */
@Service
public class HH000123  extends BaseService {

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
    @Resource
    IBpsCompanyCityMapper iBpsCompanyCityMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map subExcute(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_COMPANY_PARAM_LIST);
        requiredFieldList.add(Fields.PARAM_SERIAL_NO);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        Map returnMap = new HashMap();

        String serialNo = (String) paramMap.get(Fields.PARAM_SERIAL_NO);
        List<Map> companyParamList = (List<Map>) paramMap.get(Fields.PARAM_COMPANY_PARAM_LIST);

        //获取当前系统配置
        Map<String, BpsConfig> configMap = commonService.getValidSystemConfig();
        //是否要求所有询价公司都有结果
        boolean mustAllHavePrice = false;
        if(Constants.CONFIG_NEED_ALL_RESULT_VALUE_YES.equals(configMap.get(Constants.CONFIG_NEED_ALL_RESULT).getConfigValue())){
            mustAllHavePrice = true;
        }

        //获取询价流水参数
        BpsUserInquiry inquiry = iBpsUserInquiryMapper.getInquiryInfoBySerialNo(serialNo);

        returnMap.put(Fields.PARAM_HOUSE_TYPE_CODE, inquiry.getHouseType());
        returnMap.put(Fields.PARAM_CITY_CODE, inquiry.getCityCode());

        String assessPrice = "-1";//评估价格
        String assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;//评估状态
        String assessDate = null;//评估时间(返回前端的评估时间格式)
        Date assessTime = null;//保存至数据库的时间

        //获取各公司询价记录
        Map<String, BpsCompanyInquiryApply> applyMap = commonService.queryAllCompanyApplyForOneInquiry(serialNo);

        List  companyPriceList = new ArrayList();
        for (Map map : companyParamList) {
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

            BpsCompanyInquiryApply apply = applyMap.get(companyCode);
            if(null == apply){
                //新增请求记录和关联关系
                apply = new BpsCompanyInquiryApply();
                String code = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_APPLY_CODE_PREFIX, Fields.INQUIRY_APPLY_CODE_SUFFIX);
                apply.setCode(code);
                apply.setHouseType(inquiry.getHouseType());
                apply.setCityCode(inquiry.getCityCode());
                apply.setCompanyCode(companyCode);
                apply.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
                //新增记录
                iBpsCompanyInquiryApplyMapper.saveApply(apply);
                BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
                bpsInquiryApplyRelation.setApplyCode(code);
                bpsInquiryApplyRelation.setSerialNo(serialNo);
                iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);
            }
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
            //更新单个公司询价结果
            Map applyParam =  new HashMap();
            applyParam.put(Fields.PARAM_STATUS,Constants.BPS_ORDER_STATUS_EVALUATED);
            applyParam.put(Fields.PARAM_ASSESSMENT_COMPANY_PRICE,priceBigDecimal);
            applyParam.put(Fields.PARAM_PRICE_TIME,new Date());
            applyParam.put(Fields.PARAM_SERIAL_NO,serialNo);
            applyParam.put(Fields.PARAM_COMPANY_CODE,companyCode);
            applyParam.put(Fields.PARAM_AUTO_PRICE,Constants.APPLY_AUTO_PRICE_MAN);
            iBpsCompanyInquiryApplyMapper.updateApplyByInquirySerialNoAndCompanyCode(applyParam);

            Map companyMap = new HashMap();
            companyMap.put(Fields.PARAM_ASSESSMENT_COMPANY_PRICE, companyPrice);
            companyMap.put(Fields.PARAM_STATUS, Constants.BPS_ORDER_STATUS_EVALUATED);
            companyPriceList.add(companyMap);
        }

        //评估中的个数
        int evaluatingNum = 0;
        List<BigDecimal> priceList = new ArrayList<BigDecimal>();
        //数据库取出所有记录
        List<BpsCompanyInquiryApply> applyList = iBpsCompanyInquiryApplyMapper.getInquiryInfoBySerialNo(serialNo);
        if(null != applyList && applyList.size() >0){
            for(BpsCompanyInquiryApply appl: applyList){
                if(Constants.BPS_ORDER_STATUS_EVALUATED.equals(appl.getStatus()) && null != appl.getPrice() && (appl.getPrice()).doubleValue() > 0){
                    priceList.add(appl.getPrice());
                }else if(Constants.BPS_ORDER_STATUS_EVALUATING.equals(appl.getStatus())){
                    evaluatingNum = evaluatingNum + 1;
                }
            }
        }

        if(mustAllHavePrice){
            //获取该城市关联的估价公司
            List<BpsCompanyCity> companyCitylist = iBpsCompanyCityMapper.queryCompanyCityByCityCode(inquiry.getCityCode());
            if(priceList.size() == companyCitylist.size()){
                Map priceMap = commonService.getFinalPrice(priceList);
                assessPrice = (String) priceMap.get(Fields.PARAM_ASSESSMENT_PRICE);
                assessTime = new Date();
                assessDate =  DateUtil.convert(assessTime, HH000110.DATE_TIME_PATTERN3);
                assessStatus = Constants.BPS_ORDER_STATUS_EVALUATED;
            }else if ((evaluatingNum + priceList.size()) == companyCitylist.size()){
                assessStatus = Constants.BPS_ORDER_STATUS_EVALUATING;
            }else{
                assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
            }
        }else{
            if(evaluatingNum > 0){
                assessStatus = Constants.BPS_ORDER_STATUS_EVALUATING;
            }else if (priceList.size() > 0){
                Map priceMap = commonService.getFinalPrice(priceList);
                assessPrice = (String) priceMap.get(Fields.PARAM_ASSESSMENT_PRICE);
                assessTime = new Date();
                assessDate =  DateUtil.convert(assessTime, HH000110.DATE_TIME_PATTERN3);
                assessStatus = Constants.BPS_ORDER_STATUS_EVALUATED;
            }else{
                assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
            }
        }

        if (null == assessPrice) {
            assessPrice = "-1";
        }

        //更新询价流水记录
        Map inquiryMap = new HashMap();
        inquiryMap.put(Fields.PARAM_SERIAL_NO, serialNo);
        inquiryMap.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
        inquiryMap.put(Fields.PARAM_ASSESSMENT_TIME, assessTime);
        inquiryMap.put(Fields.PARAM_STATUS,assessStatus);
        iBpsUserInquiryMapper.updateInquiryBySerialNo(inquiryMap);

        returnMap.put(Fields.PARAM_COMPANY_PARAM_LIST, companyPriceList);
        returnMap.put(Fields.PARAM_SERIAL_NO, serialNo);
        returnMap.put(Fields.PARAM_ASSESSMENT_STATUS, assessStatus);
        returnMap.put(Fields.PARAM_ASSESSMENT_TIME, assessDate);
        returnMap.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "询价成功！");
        return returnMap;

    }

    @Override
    public String getInterfaceDescription() {
        return "录入评估价提交(待评估提交)";
    }
}
