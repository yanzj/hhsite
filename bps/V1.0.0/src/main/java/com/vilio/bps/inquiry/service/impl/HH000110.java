package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.dao.CommonDao;
import com.vilio.bps.common.service.BaseService;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.*;
import com.vilio.bps.commonMapper.pojo.*;
import com.vilio.bps.inquiry.pojo.InquiryBaseValueBean;
import com.vilio.bps.inquiry.service.InquiryBaseService;
import com.vilio.bps.inquiry.util.AES;
import com.vilio.bps.inquiry.util.Sha1RSA;
import com.vilio.bps.inquiry.util.WebUtil;
import com.vilio.bps.util.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 公寓自动询价
 */
@Service
public class HH000110 extends BaseService {
    Logger logger = Logger.getLogger(HH000110.class);
    @Resource
    IBpsCompanyCityMapper iBpsCompanyCityMapper;
    @Resource
    IBpsHouseInfoMapper iBpsHouseInfoMapper;
    @Resource
    ConfigInfo configInfo;
    @Resource
    CommonService commonService;
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

    public static final String DATE_TIME_PATTERN3 = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Map subExcute(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
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

        // Step1 获取该城市的关联估价公司 ,如果无关联公司返回联系客服
        List<BpsCompanyCity> companyCitylist = iBpsCompanyCityMapper.queryCompanyCityByCityCode(cityCode);//获取该城市关联的估价公司
        if (null == companyCitylist || companyCitylist.size() < 1) {
            throw new HHBizException(ReturnCode.NO_CONFIG_APPRAISAL_COMPANY.getReturnCode(), ReturnCode.NO_CONFIG_APPRAISAL_COMPANY.getReturnMessage() + "请联系客服，电话" + Constants.CUSTOMER_SERVICE_PHONE);
        }

        //获取当前系统配置
        Map<String, BpsConfig> configMap = commonService.getValidSystemConfig();

        //是否要求所有询价公司都有结果
        boolean mustAllHavePrice = false;
        if(Constants.CONFIG_NEED_ALL_RESULT_VALUE_YES.equals(configMap.get(Constants.CONFIG_NEED_ALL_RESULT).getConfigValue())){
            mustAllHavePrice = true;
        }

        //获取当前系统配置的默认对接公司
        BpsCompanyCity mainCompany = null;
        for(BpsCompanyCity city: companyCitylist){
            if(city.getOrderNo() == 0){
                mainCompany = city;
            }
        }

        //获取前端传参：各公司询价参数列表
        List<Map> companyParamList = (List<Map>) paramMap.get(Fields.PARAM_COMPANY_PARAM_LIST);
        if(null == companyCitylist || companyParamList.size() <1){
            throw new HHBizException(ReturnCode.NO_ONE_COMPANY_PARAM.getReturnCode(), ReturnCode.NO_ONE_COMPANY_PARAM.getReturnMessage());
        }
        Map<String, BpsCompanyInquiryApply> applyParamMap = new HashMap();
        for(Map map : companyParamList){
            BpsCompanyInquiryApply ba = (BpsCompanyInquiryApply) CommonUtil.convertMapToEntity(BpsCompanyInquiryApply.class, map);
            applyParamMap.put(ba.getCompanyCode(), ba);
        }
        //主录单公司请求参数
        BpsCompanyInquiryApply  defaultInquiryParam = null;
        if(null != mainCompany){
            defaultInquiryParam = applyParamMap.get(mainCompany.getCompanyCode());
        }

        //如果要求所有公司都有结果，判断前端所传公司是否足够(暂时注销，待需求变化)
        /*if(mustAllHavePrice && companyParamList.size() < companyCitylist.size()){
            returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "所有公司都必须参数齐全！");
            return returnMap;
        }*/

        //新建询价请求流水
        String serialNo = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_SERIAL_NO_PREFIX, Fields.INQUIRY_SERIAL_NO_SUFFIX);
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
        bpsUserInquiry.setHouseType(houseTypeCode);
        bpsUserInquiry.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
        Date deadLine = commonService.getDeadLineTime(new Date());
        bpsUserInquiry.setDeadline(deadLine);
        iBpsUserInquiryMapper.saveUserInquiry(bpsUserInquiry);

        //是否在本地库中查找
        boolean queryFromDB = true;

        //取时间阈值(小时)
        int timeThreshold = commonService.getTimeThresholdsByCityCode(cityCode);

        queryFromDB = timeThreshold <= 0 ? false : true;

        String assessPrice = "-1";//评估价格
        String assessStatus = Constants.BPS_ORDER_STATUS_EVALUATING;//评估状态
        String assessDate = null;//评估时间(返回前端的评估时间格式)
        Date assessTime = null;//保存至数据库的时间

        // 查看本地库是否有时间阈值内，算法未失效的结果
        if (queryFromDB && null != defaultInquiryParam) {
            //当前时间减去时间阈值
            Date date = new Date();
            date = DateUtil.getDateTimeBefore(date, timeThreshold);
            defaultInquiryParam.setPriceTime(date);
            List<BpsAssessmentResult> assessmentResultList = iBpsAssessmentResultMapper.queryAssessmentResultInThresholdByApplyParam(defaultInquiryParam);
            if (null != assessmentResultList && assessmentResultList.size() > 0 && null != assessmentResultList.get(0)) {
                BpsAssessmentResult assessmentResult = assessmentResultList.get(0);
                //算法是否失效
                boolean avaliable = algorithmIsAvaliable(assessmentResult, configMap);
                if (avaliable) {
                    //判断依赖公司结果是否同此次请求公司吻合
                    List<BpsCompanyInquiryApply> applyList = iBpsCompanyInquiryApplyMapper.queryInquiryApplyByResultCode(assessmentResult.getCode());
                    if(null != applyList && applyList.size() == companyCitylist.size()){
                        for(int i = 0; i < applyList.size(); i++){
                            for(BpsCompanyCity companycity:companyCitylist){
                                if(companycity.getCompanyCode().equals(applyList.get(i).getCompanyCode())){
                                    applyList.remove(i);
                                    i--;
                                    break;
                                }
                            }
                        }
                        if(applyList.size() > 0) avaliable = false;
                    }else{
                        avaliable = false;
                    }
                }
                if(avaliable){
                    assessTime = new Date();
                    assessPrice = assessmentResult.getAssessmentPrice().toString();
                    assessStatus = Constants.BPS_ORDER_STATUS_EVALUATED;
                    assessDate =  DateUtil.convert(assessTime, HH000110.DATE_TIME_PATTERN3);
                    returnMap.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
                    returnMap.put(Fields.PARAM_ASSESSMENT_STATUS, assessStatus);
                    returnMap.put(Fields.PARAM_ASSESSMENT_TIME, assessDate);
                    returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
                    returnMap.put(Fields.PARAM_SERIAL_NO, serialNo);
                    //更新询价流水
                    Map param = new HashMap();
                    param.put(Fields.PARAM_SERIAL_NO, serialNo);
                    param.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
                    param.put(Fields.PARAM_STATUS, assessStatus);
                    param.put(Fields.PARAM_ASSESSMENT_TIME, assessDate);
                    iBpsUserInquiryMapper.saveInquiryPriceAndStatus(param);
                    return returnMap;
                }
            }
        }

        Date earlyDate = new Date();
        //针对每一家关联公司询价
        //有评估值的公司
        List<InquiryBaseValueBean> beanHasPrice = new ArrayList<InquiryBaseValueBean>();
        //评估中的
        List<InquiryBaseValueBean> inAssess = new ArrayList<InquiryBaseValueBean>();
        for (BpsCompanyCity companyCity : companyCitylist) {
            InquiryBaseValueBean inquiryBaseValueBean = null;
            String companyCode = companyCity.getCompanyCode();
            //获取当前公司参数
            BpsCompanyInquiryApply apply = applyParamMap.get(companyCode);
            if(null == apply){
                //未接收到该公司参数,新增请求记录和关联关系
                apply = new BpsCompanyInquiryApply();
                String code = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_APPLY_CODE_PREFIX, Fields.INQUIRY_APPLY_CODE_SUFFIX);
                apply.setCode(code);
                apply.setHouseType(houseTypeCode);
                apply.setCityCode(cityCode);
                apply.setCompanyCode(companyCode);
                apply.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
                //新增记录
                iBpsCompanyInquiryApplyMapper.saveApply(apply);
                BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
                bpsInquiryApplyRelation.setApplyCode(code);
                bpsInquiryApplyRelation.setSerialNo(serialNo);
                iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);

                continue;
            }
            apply.setSerialNo(serialNo);
            apply.setCityCode(cityCode);
            apply.setHouseType(houseTypeCode);
            //Step 3.1 公共必填参数判断
            boolean linkCompany = true;
            Map resourceMap = new HashMap();
            resourceMap.put(Fields.PARAM_COMPANY_CODE, apply.getCompanyCode());
            resourceMap.put(Fields.PARAM_AREA, apply.getArea().toString());
            requiredFieldList.clear();
            requiredFieldList.add(Fields.PARAM_COMPANY_CODE);
            requiredFieldList.add(Fields.PARAM_AREA);
            try{
                CommonUtil.checkRequiredFields(resourceMap, requiredFieldList);
            }catch (Exception e){
                logger.error("公寓询价校验公司参数出现异常：" + e);
                linkCompany = false;
            }
            InquiryBaseService inquiryBaseService = null;
            if(linkCompany){
                if (AppraisalCompanys.SH_CHENGSHI.getCode().equals(companyCode)) {
                    resourceMap.put(Fields.PARAM_TOWARDS, apply.getTowards());
                    resourceMap.put(Fields.PARAM_CURRENT_FLOOR, null == apply.getCurrentFloor() ? null : apply.getCurrentFloor().toString());
                    resourceMap.put(Fields.PARAM_TOTAL_FLOOR, null == apply.getTotalFloor() ? null : apply.getTotalFloor().toString());
                    resourceMap.put(Fields.PARAM_YEAR_BUILT, apply.getYearBuilt());
                    requiredFieldList.add(Fields.PARAM_TOWARDS);
                    requiredFieldList.add(Fields.PARAM_CURRENT_FLOOR);
                    requiredFieldList.add(Fields.PARAM_TOTAL_FLOOR);
                    requiredFieldList.add(Fields.PARAM_YEAR_BUILT);
                    try{
                        CommonUtil.checkRequiredFields(resourceMap, requiredFieldList);
                    }catch (Exception e){
                        logger.error("公寓询价校验公司参数出现异常：" + e);
                        linkCompany = false;
                    }
                    inquiryBaseService = sCInquirySeriviceImpl;
                }if(AppraisalCompanys.WORLD_UNION.getCode().equals(companyCode)){
                    resourceMap.put(Fields.PARAM_UNIT_CODE, apply.getUnitCode());
                    resourceMap.put(Fields.PARAM_HOUSE_CODE, apply.getHouseCode());
                    requiredFieldList.add(Fields.PARAM_UNIT_CODE);
                    requiredFieldList.add(Fields.PARAM_HOUSE_CODE);
                    try{
                        CommonUtil.checkRequiredFields(resourceMap, requiredFieldList);
                    }catch (Exception e){
                        logger.error("公寓询价校验公司参数出现异常：" + e);
                        linkCompany = false;
                    }
                    inquiryBaseService = wUInquiryBaseServiceImpl;
                }
            }
            if(linkCompany){
                inquiryBaseValueBean = inquiryBaseService.sendInquiryRequ(apply);
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

        //默认非人工（自动)查询
        boolean manualHandling = false;
        if (beanHasPrice.size() + inAssess.size() == 0) {
            //所有询价公司都没结果
            manualHandling = true;
        }
        //所有询价公司是否都有结果，默认都有
        boolean allHaveResult = true;
        if (beanHasPrice.size() < companyCitylist.size()) {
            //不是所有公司都有结果
            allHaveResult = false;
        }
        //是否为评估中
        boolean evaluatingFlag = false;
        if (mustAllHavePrice && inAssess.size() > 0 && ( inAssess.size() + beanHasPrice.size() ) == companyCitylist.size()) {
            assessStatus = Constants.BPS_ORDER_STATUS_EVALUATING;
        }else if(!mustAllHavePrice && inAssess.size() > 0){
            assessStatus = Constants.BPS_ORDER_STATUS_EVALUATING;
        }

        if (!manualHandling && !allHaveResult) {
            //是否要求所有公司都有结果
            if (null != configMap && mustAllHavePrice) {
                manualHandling = true;
            }
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
                assessDate =  DateUtil.convert(assessTime, HH000110.DATE_TIME_PATTERN3);

            } else {
                //大于差价百分比是否转人工
                if (null != configMap && null != configMap.get(Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL)) {
                    String value = configMap.get(Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL).getConfigValue();
                    if (Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL_VALUE_YES.equals(value)) {
                        manualHandling = true;
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
                    assessDate =  DateUtil.convert(assessTime, HH000110.DATE_TIME_PATTERN3);
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

        return returnMap;
    }


    @Override
    public String getInterfaceDescription() {
        return "根据房屋信息进行询价";
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
