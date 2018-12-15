package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.dao.CommonDao;
import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.*;
import com.vilio.bps.commonMapper.pojo.*;
import com.vilio.bps.inquiry.pojo.InquiryBaseValueBean;
import com.vilio.bps.inquiry.service.InquiryBaseService;
import com.vilio.bps.inquiry.worldunion.pojo.WUGetQueryPrice;
import com.vilio.bps.inquiry.worldunion.service.WUInquiryPrice;
import com.vilio.bps.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 别墅自动询价
 */
@Service
public class HH000109 extends BaseService {
    Logger logger = Logger.getLogger(HH000109.class);
    @Resource
    CommonService commonService;
    @Resource
    WUInquiryPrice wuInquiryPrice;
    @Resource
    IBpsCompanyCityMapper iBpsCompanyCityMapper;
    @Resource
    IBpsThresholdMapper iBpsThresholdMapper;
    @Resource
    IBpsHouseInfoMapper iBpsHouseInfoMapper;
    @Resource
    ConfigInfo configInfo;
    @Resource
    IBpsAssessmentResultMapper iBpsAssessmentResultMapper;
    @Resource
    CommonDao commonDao;
    @Resource
    IBpsAlgorithmMapper iBpsAlgorithmMapper;
    @Resource
    IBpsPlotsMapper iBpsPlotsMapper;
    @Resource
    IBpsUserInquiryMapper iBpsUserInquiryMapper;
    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;
    @Resource
    InquiryBaseService sCInquirySeriviceImpl;
    @Resource
    InquiryBaseService wUInquiryBaseServiceImpl;
    @Resource
    IBpsResultApplyRelationMapper iBpsResultApplyRelationMapper;
    @Resource
    IBpsInquiryResultRelationMapper iBpsInquiryResultRelationMapper;
    @Resource
    IBpsInquiryApplyRelationMapper iBpsInquiryApplyRelationMapper;



    private static final String DATE_TIME_PATTERN3 = "yyyy-MM-dd HH:mm:ss";

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map subExcute(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_CITY_CODE);
        requiredFieldList.add(Fields.PARAM_HOUSE_TYPE_CODE);
        requiredFieldList.add(Fields.PARAM_INQUIRY_USER_USERID);
        requiredFieldList.add(Fields.PARAM_INQUIRY_SOURCE_SYSTEM);
        requiredFieldList.add(Fields.PARAM_COMPANY_PARAM_LIST);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        //所在城市
        String cityCode = (String) paramMap.get(Fields.PARAM_CITY_CODE);
        //房屋类型
        String houseTypeCode =  (String) paramMap.get(Fields.PARAM_HOUSE_TYPE_CODE);

        //获取当前系统配置
        Map<String, BpsConfig> configMap = commonService.getValidSystemConfig();
        //获取该城市关联的估价公司
        List<BpsCompanyCity> companyCitylist = iBpsCompanyCityMapper.queryCompanyCityByCityCode(cityCode);
        //没有入询价记录表的公司
        Map<String, BpsCompanyCity> leftCompanyCityMap = new HashedMap();
        for(BpsCompanyCity companyCity: companyCitylist){
            leftCompanyCityMap.put(companyCity.getCompanyCode(), companyCity);
        }

        //是否要求所有询价公司都有结果
        boolean mustAllHavePrice = false;
        if(Constants.CONFIG_NEED_ALL_RESULT_VALUE_YES.equals(configMap.get(Constants.CONFIG_NEED_ALL_RESULT).getConfigValue())){
            mustAllHavePrice = true;
        }

        String assessPrice =  "-1";//评估价格
        String assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;//评估状态
        String assessDate = null;//评估时间(返回前端的评估时间格式)
        Date assessTime = null;//保存至数据库的时间

        //Step 2 定义出参容器，解析入参
        Map returnMap = new HashMap();
        List<Map> companyParamList = (List<Map>) paramMap.get(Fields.PARAM_COMPANY_PARAM_LIST);
        if(companyParamList != null){
            //新建询价请求流水
            String serialNo = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_SERIAL_NO_PREFIX, Fields.INQUIRY_SERIAL_NO_SUFFIX);
            BpsUserInquiry bpsUserInquiry = new BpsUserInquiry ();
            bpsUserInquiry.setSerialNo(serialNo);
            bpsUserInquiry.setCompanyId((String) paramMap.get(Fields.PARAM_INQUIRY_USER_COMPANYID));
            bpsUserInquiry.setCompanyName((String) paramMap.get(Fields.PARAM_INQUIRY_USER_COMPANYNAME));
            bpsUserInquiry.setDepartmentId((String) paramMap.get(Fields.PARAM_INQUIRY_USER_DEPARTMENTID));
            bpsUserInquiry.setDepartmentName((String) paramMap.get(Fields.PARAM_INQUIRY_USER_DEPARTMENTNAME));
            bpsUserInquiry.setUserName((String) paramMap.get(Fields.PARAM_INQUIRY_USER_USERNAME));
            bpsUserInquiry.setHouseType((String) paramMap.get(Fields.PARAM_HOUSE_TYPE_CODE));
            bpsUserInquiry.setUserId((String) paramMap.get(Fields.PARAM_INQUIRY_USER_USERID));
            bpsUserInquiry.setCityCode((String) paramMap.get(Fields.PARAM_CITY_CODE));
            bpsUserInquiry.setSourceSystem((String) paramMap.get(Fields.PARAM_INQUIRY_SOURCE_SYSTEM));
            bpsUserInquiry.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
            Date deadLine = commonService.getDeadLineTime(new Date());
            bpsUserInquiry.setDeadline(deadLine);
            iBpsUserInquiryMapper.saveUserInquiry(bpsUserInquiry);

            //Step 3 各估价公司别墅评估
            Date earlyDate = new Date();
            //有评估值的公司
            List<InquiryBaseValueBean> beanHasPrice = new ArrayList<InquiryBaseValueBean>();
            //评估中的
            List<InquiryBaseValueBean> inAssess = new ArrayList<InquiryBaseValueBean>();
            for(Map compPramMap : companyParamList){
                //获取必要参数
                String companyCode = (String) compPramMap.get(Fields.PARAM_COMPANY_CODE);
                leftCompanyCityMap.remove(companyCode);
                String address = (String) compPramMap.get(Fields.PARAM_ADDRESS);//此处指房屋地址
                String area = (String) compPramMap.get(Fields.PARAM_AREA);
                String areaCode = (String) compPramMap.get(Fields.PARAM_AREA_CODE);

                boolean linkCompany = true;
                if(null == companyCode){
                    logger.error("公司编号为空");
                    linkCompany = false;
                }
                //封装请求参数
                BpsCompanyInquiryApply apply = new BpsCompanyInquiryApply();
                apply.setSerialNo(serialNo);
                apply.setCityCode(cityCode);
                apply.setHouseType(houseTypeCode);
                apply.setCompanyCode(companyCode);
                apply.setAddress(address);
                try{
                    apply.setArea(new BigDecimal(area));
                }catch(Exception e){
                    logger.error("用户"+ paramMap.get(Fields.PARAM_INQUIRY_USER_USERID) + "别墅询价companyCode=" + companyCode + ",面积转换异常area=" + area);
                }

                apply.setAreaCode(areaCode);
                apply.setAreaName((String) compPramMap.get(Fields.PARAM_AREA_NAME));
                if(linkCompany){
                    //Step 3.2 别墅评估--世联
                    if(AppraisalCompanys.WORLD_UNION.getCode().equals(companyCode)){
                        //Step 3.2.1 个性化必填参数判断
                        requiredFieldList.clear();
                        requiredFieldList.add(Fields.PARAM_AREA);
                        requiredFieldList.add(Fields.PARAM_AREA_CODE);
                        requiredFieldList.add(Fields.PARAM_ADDRESS);
                        Map resourceMap = new HashMap();
                        resourceMap.put(Fields.PARAM_AREA, area);
                        resourceMap.put(Fields.PARAM_AREA_CODE, areaCode);
                        resourceMap.put(Fields.PARAM_ADDRESS, address);
                        try{
                            CommonUtil.checkRequiredFields(resourceMap, requiredFieldList);
                        }catch (Exception e){
                            logger.error("公寓询价校验公司参数出现异常：" + e);
                            linkCompany = false;
                        }

                        if(linkCompany){
                            InquiryBaseValueBean inquiryBaseValueBean = wUInquiryBaseServiceImpl.sendInquiryRequ(apply);
                            if (null != inquiryBaseValueBean) {
                                if(Constants.BPS_ORDER_STATUS_EVALUATED.equals(inquiryBaseValueBean.getStatus())){
                                    //统计有价格返回的公司
                                    beanHasPrice.add(inquiryBaseValueBean);
                                }else if(Constants.BPS_ORDER_STATUS_EVALUATING.equals(inquiryBaseValueBean.getStatus())){
                                    inAssess.add(inquiryBaseValueBean);
                                }
                                if(null != inquiryBaseValueBean.getPriceTime() && DateUtil.differHours(earlyDate, inquiryBaseValueBean.getPriceTime()) > 0){
                                    earlyDate = inquiryBaseValueBean.getPriceTime();
                                }
                            }
                        }


                    }else{
                        linkCompany = false;
                    }
                }
                if(!linkCompany){
                    //新增请求记录和关联关系
                    String code = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_APPLY_CODE_PREFIX, Fields.INQUIRY_APPLY_CODE_SUFFIX);
                    apply.setCode(code);
                    apply.setAutoPrice(Constants.APPLY_AUTO_PRICE_MAN);
                    apply.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
                    iBpsCompanyInquiryApplyMapper.saveApply(apply);
                    BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
                    bpsInquiryApplyRelation.setApplyCode(code);
                    bpsInquiryApplyRelation.setSerialNo(apply.getSerialNo());
                    iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);
                }

            }

            //
            Iterator leftCompanyKeys = leftCompanyCityMap.keySet().iterator();
            while(leftCompanyKeys.hasNext()){
                String key = (String) leftCompanyKeys.next();
                //未接收到该公司参数,新增请求记录和关联关系
                BpsCompanyInquiryApply apply = new BpsCompanyInquiryApply();
                String code = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_APPLY_CODE_PREFIX, Fields.INQUIRY_APPLY_CODE_SUFFIX);
                apply.setCode(code);
                apply.setHouseType(houseTypeCode);
                apply.setCityCode(cityCode);
                apply.setCompanyCode(key);
                apply.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
                //新增记录
                iBpsCompanyInquiryApplyMapper.saveApply(apply);
                BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
                bpsInquiryApplyRelation.setApplyCode(code);
                bpsInquiryApplyRelation.setSerialNo(serialNo);
                iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);
            }

            //默认非人工（自动)查询
            boolean manualHandling = false;
            if (beanHasPrice.size()  + inAssess.size() == 0) {
                //所有询价公司都没结果
                manualHandling = true;
            }
            //所有询价公司是否都有结果，默认都有
            boolean allHaveResult = true;
            if (beanHasPrice.size() < companyCitylist.size()) {
                //不是所有公司都有结果
                allHaveResult = false;
            }
            //是否要求所有公司都有结果
            if (!manualHandling && !allHaveResult && mustAllHavePrice) {
                manualHandling = true;
            }

            //是否为评估中
            boolean evaluatingFlag = false;
            if (mustAllHavePrice && inAssess.size() > 0 && ( inAssess.size() + beanHasPrice.size() ) == companyCitylist.size()) {
                assessStatus = Constants.BPS_ORDER_STATUS_EVALUATING;
                evaluatingFlag = true;
            }else if(!mustAllHavePrice && inAssess.size() > 0){
                assessStatus = Constants.BPS_ORDER_STATUS_EVALUATING;
                evaluatingFlag = true;
            }

            if (!manualHandling && !evaluatingFlag) {
                Map parameter = new HashMap();
                parameter.put("config", configMap);
                //计算差价百分比
                List<BigDecimal> priceList = new ArrayList<BigDecimal>();
                parameter.put("priceList", priceList);
                BigDecimal minPrice = new BigDecimal("0.0");
                BigDecimal maxPrice = new BigDecimal("0.0");
                for (int i = 0; i < beanHasPrice.size(); i++) {
                    InquiryBaseValueBean baseValueBean = beanHasPrice.get(i);
                    BigDecimal price = new BigDecimal(baseValueBean.getPrice());
                    priceList.add(price);
                    if (i == 0) {
                        minPrice = price;
                        maxPrice = price;
                    } else {
                        minPrice = minPrice.compareTo(price) > 0 ? price : minPrice;
                        maxPrice = maxPrice.compareTo(price) > 0 ? maxPrice : price;
                    }
                }
                parameter.put("minPrice", minPrice);
                BigDecimal differPercent = maxPrice.subtract(minPrice).divide(minPrice, 3, BigDecimal.ROUND_HALF_DOWN);
                //差价百分比是否大于阈值
                String strDifferThreshold = configMap.get(Constants.CONFIG__PERCENT_OF_DIFF_THRESHOLD).getConfigValue();
                BigDecimal differThreshold = null;
                if (null != configMap && null != configMap.get(Constants.CONFIG__PERCENT_OF_DIFF_THRESHOLD)) {
                    differThreshold = new BigDecimal(strDifferThreshold);
                }
                BpsAssessmentResult bpsAssessmentResult = new BpsAssessmentResult();
                bpsAssessmentResult.setPercenDiffThreshold(strDifferThreshold);
                Long priceResult = 0l;
                if (differPercent.compareTo(differThreshold) < 0) {
                    //小于阈值时的算
                    String algorithmName = iBpsAlgorithmMapper.findAlgorithmByAlgorithmCode(configMap.get(Constants.CONFIG__LESS_THAN_PERCENT_OF_DIFF_THRESHOLD).getConfigValue());
                    priceResult = AlgorithmUtil.matchAlgorithm(algorithmName, parameter);
                    bpsAssessmentResult.setAlgorithmMethod(Constants.CONFIG__LESS_THAN_PERCENT_OF_DIFF_THRESHOLD);
                    bpsAssessmentResult.setAlgorithmCode(configMap.get(Constants.CONFIG__LESS_THAN_PERCENT_OF_DIFF_THRESHOLD).getConfigValue());

                    assessTime = new Date();
                    assessStatus = Constants.BPS_ORDER_STATUS_EVALUATED;
                    assessDate =  DateUtil.convert(assessTime, HH000109.DATE_TIME_PATTERN3);

                } else {
                    //大于差价百分比是否转人工
                    if (null != configMap && null != configMap.get(Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL)) {
                        String value = configMap.get(Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL).getConfigValue();
                        if (Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL_VALUE_YES.equals(value)) {
                            manualHandling = true;
                            assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
                        }
                    }

                    if (!manualHandling) {
                        //大于等于阈值时算法
                        String algorithmName = iBpsAlgorithmMapper.findAlgorithmByAlgorithmCode(configMap.get(Constants.CONFIG__GREAT_OR_EQU__PERCENT_OF_DIFF_THRESHOLD).getConfigValue());
                        priceResult = AlgorithmUtil.matchAlgorithm(algorithmName, parameter);
                        bpsAssessmentResult.setAlgorithmMethod(Constants.CONFIG__GREAT_OR_EQU__PERCENT_OF_DIFF_THRESHOLD);
                        bpsAssessmentResult.setAlgorithmCode(configMap.get(Constants.CONFIG__GREAT_OR_EQU__PERCENT_OF_DIFF_THRESHOLD).getConfigValue());

                        assessTime = new Date();
                        assessStatus = Constants.BPS_ORDER_STATUS_EVALUATED;
                        assessDate =  DateUtil.convert(assessTime, HH000109.DATE_TIME_PATTERN3);
                    }
                }
                if (!manualHandling) {
                    //结果入库
                    String resultCode = CommonUtil.getCurrentTimeStr("I", "P");
                    bpsAssessmentResult.setCode(resultCode);
                    bpsAssessmentResult.setAssessmentPrice(new BigDecimal(priceResult));
                    bpsAssessmentResult.setMinApplyTime(earlyDate);
                    iBpsAssessmentResultMapper.saveAssessmentResult(bpsAssessmentResult);
                    //结果和请求数据关联
                    for(InquiryBaseValueBean b: beanHasPrice){
                        BpsResultApplyRelation resultApplyRelation = new BpsResultApplyRelation();
                        resultApplyRelation.setApplyCode(b.getCode());
                        resultApplyRelation.setResultCode(resultCode);
                        iBpsResultApplyRelationMapper.saveResultApply(resultApplyRelation);
                    }
                    //结果和请求流水关联
                    BpsInquiryResultRelation inquiryResultRelation = new BpsInquiryResultRelation();
                    inquiryResultRelation.setResultCode(resultCode);
                    inquiryResultRelation.setSerialNo(serialNo);
                    iBpsInquiryResultRelationMapper.saveInquiryResult(inquiryResultRelation);

                    //保存小区信息

                    //保存房屋信息

                }
                assessPrice = priceResult.toString();

            }

            returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
            returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, ReturnCode.SUCCESS_CODE.getReturnMessage());

            if (manualHandling) {
                //转人工流程代码编写
                returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "已转人工，请耐心等待");
                assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
            }
            //更新询价流水
            Map param = new HashMap();
            param.put(Fields.PARAM_SERIAL_NO, serialNo);
            param.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
            param.put(Fields.PARAM_STATUS, assessStatus);
            param.put(Fields.PARAM_ASSESSMENT_TIME, assessTime);
            iBpsUserInquiryMapper.saveInquiryPriceAndStatus(param);

            if(null != beanHasPrice && beanHasPrice.size() > 0){
                List companyPriceList = new ArrayList();
                for(InquiryBaseValueBean b: beanHasPrice){
                    Map comapnyPriceMap = new HashMap();
                    comapnyPriceMap.put(Fields.PARAM_ASSESSMENT_COMPANY_PRICE, b.getPrice());
                    comapnyPriceMap.put(Fields.PARAM_COMPANY_CODE, b.getCompanyCode());
                    comapnyPriceMap.put(Fields.PARAM_STATUS, b.getStatus());
                    companyPriceList.add(comapnyPriceMap);
                }
                returnMap.put(Fields.PARAM_COMPANY_PARAM_LIST, companyPriceList);
            }
            returnMap.put(Fields.PARAM_SERIAL_NO, serialNo);
            returnMap.put(Fields.PARAM_ASSESSMENT_STATUS, assessStatus);
            if(null == assessPrice){
                assessPrice = "-1";
            }
            returnMap.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
            returnMap.put(Fields.PARAM_ASSESSMENT_TIME, assessDate);


        }else{
        //无请求参数列表
        }

        return returnMap;
    }


    @Override
    public String getInterfaceDescription() {
        return "别墅自动询价";
    }

    /**
     * 算法是否可用
     *
     * @param assessmentResult
     * @param configMap
     * @return
     */
    private boolean algorithmIsAvaliable(BpsAssessmentResult assessmentResult, Map<String, BpsConfig> configMap) {
        //判断差价百分比阈值是否变化
        String differThreshold = configMap.get(Constants.CONFIG__PERCENT_OF_DIFF_THRESHOLD).getConfigValue();
        if (!differThreshold.equals(assessmentResult.getPercenDiffThreshold())) {
            return false;
        }
        //算法是否同上次相同
        String configAlgorithmId = configMap.get(assessmentResult.getAlgorithmMethod()).getConfigValue();
        if (configAlgorithmId != assessmentResult.getAlgorithmCode()) {
            return false;
        }

        return true;
    }

    //讲配置信息转成 map
    Map<String, BpsConfig> getConfigMap(List<BpsConfig> configList) {
        if (null == configList || configList.size() < 1) {
            return null;
        }
        Map<String, BpsConfig> configMap = new HashMap();
        for (BpsConfig config : configList) {
            configMap.put(config.getId(), config);
        }
        return configMap;
    }


}
