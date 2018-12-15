package com.vilio.bps.inquiry.shchengshi.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsAppraisalCompanyMapper;
import com.vilio.bps.commonMapper.dao.IBpsCompanyInquiryApplyMapper;
import com.vilio.bps.commonMapper.dao.IBpsInquiryApplyRelationMapper;
import com.vilio.bps.commonMapper.pojo.BpsAppraisalCompany;
import com.vilio.bps.commonMapper.pojo.BpsCompanyInquiryApply;
import com.vilio.bps.commonMapper.pojo.BpsInquiryApplyRelation;
import com.vilio.bps.inquiry.pojo.InquiryBaseValueBean;
import com.vilio.bps.inquiry.service.InquiryBaseService;
import com.vilio.bps.inquiry.shchengshi.service.SCInquiryPrice;
import com.vilio.bps.inquiry.shchengshi.util.SCFields;
import com.vilio.bps.util.CommonUtil;
import com.vilio.bps.util.Constants;
import com.vilio.bps.util.DateUtil;
import com.vilio.bps.util.Fields;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/6/12.
 */
@Service("sCInquirySeriviceImpl")
public class SCInquirySeriviceImpl implements InquiryBaseService {
    Logger logger = Logger.getLogger(SCInquirySeriviceImpl.class);
    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;
    @Resource
    IBpsAppraisalCompanyMapper iBpsAppraisalCompanyMapper;
    @Resource
    IBpsInquiryApplyRelationMapper iBpsInquiryApplyRelationMapper;
    @Resource
    SCInquiryPrice sCInquiryPrice;
    @Resource
    CommonService commonService;

    public InquiryBaseValueBean sendInquiryRequ(BpsCompanyInquiryApply apply) throws Exception {
        InquiryBaseValueBean inquiryBaseValueBean = new InquiryBaseValueBean();

        //取时间阈值(小时)
        int timeThreshold = commonService.getTimeThresholdsByCityCode(apply.getCityCode());

        boolean manualHandling = false;//人工查询

        //根据当前时间计算一个判断记录是否有效的时间参考点：记录评估时间 + 时间阈值 > 当前时间
        Date comparedTime = DateUtil.getDateTimeBefore(new Date(), timeThreshold);
        apply.setPriceTime(comparedTime);
        apply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATED);
        List<BpsCompanyInquiryApply> list = iBpsCompanyInquiryApplyMapper.queryInquiryApplyByApplyParam(apply);
        apply.setStatus(null);
        if(null != list && list.size() > 0){
            //本地如果有有效结果，直接获取结果返回 return
            apply = list.get(0);
            inquiryBaseValueBean.setCode(apply.getCode());
            inquiryBaseValueBean.setPrice(apply.getPrice().toString());
            inquiryBaseValueBean.setPriceTime(apply.getPriceTime());
            inquiryBaseValueBean.setCompanyCode(apply.getCompanyCode());
            inquiryBaseValueBean.setStatus(Constants.BPS_ORDER_STATUS_EVALUATED);

            //新增关联
            BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
            bpsInquiryApplyRelation.setApplyCode(apply.getCode());
            bpsInquiryApplyRelation.setSerialNo(apply.getSerialNo());
            iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);

            return inquiryBaseValueBean;
        }else{
            //新增请求记录和关联关系
            String code = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_APPLY_CODE_PREFIX, Fields.INQUIRY_APPLY_CODE_SUFFIX);
            apply.setCode(code);
            apply.setPriceTime(new Date());
            apply.setAutoPrice(Constants.APPLY_AUTO_PRICE_COMPANY);
            apply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
            apply.setAutoPrice(Constants.APPLY_AUTO_PRICE_COMPANY);
            iBpsCompanyInquiryApplyMapper.saveApply(apply);
            BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
            bpsInquiryApplyRelation.setApplyCode(code);
            bpsInquiryApplyRelation.setSerialNo(apply.getSerialNo());
            iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);
        }

        BpsAppraisalCompany company = iBpsAppraisalCompanyMapper.getCompanyByCode(apply.getCompanyCode());
        String status = Constants.BPS_ORDER_STATUS_EVALUATING;
        String autoPrice = Constants.APPLY_AUTO_PRICE_COMPANY;
        //是否支持极速查询
        if(!Constants.APPRAISAL_COMAPNY_COOPERATION_TYPE_QUICK.equals(company.getCooperationType()) && !Constants.APPRAISAL_COMAPNY_COOPERATION_TYPE_MIX.equals(company.getCooperationType())){
            manualHandling = true;
            inquiryBaseValueBean.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
            apply.setAutoPrice(Constants.APPLY_AUTO_PRICE_MAN);
            status = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
            autoPrice = Constants.APPLY_AUTO_PRICE_MAN;
        }

        if(!manualHandling){
            //是否是别墅,别墅是否转人工
            if(Constants.HOUSE_TYPE_VILLADOM == apply.getHouseType()){
                //别墅是否转人工
                if("1".equals(company.getVillaTurnArtificial())){
                    manualHandling = true;
                    inquiryBaseValueBean.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
                    status = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
                    autoPrice = Constants.APPLY_AUTO_PRICE_MAN;
                }
            }else{
                //是否大于等于面积参数，大于转人工
                int a = apply.getArea().subtract((new BigDecimal(company.getMaxArea()))).intValue();
                if(a >= 0 ){
                    manualHandling = true;
                    inquiryBaseValueBean.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
                    status = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
                    autoPrice = Constants.APPLY_AUTO_PRICE_MAN;
                }
            }
        }

        //发送询价请求
        if(!manualHandling){
            Map paramMap = new HashMap();
            paramMap.put(SCFields.PROJECT_ID, apply.getPlotsCode());
            paramMap.put(SCFields.UNIT_ID, apply.getUnitCode());
            paramMap.put(SCFields.ROOM_NO, apply.getHouseCode());
            paramMap.put(SCFields.TOWARDS, apply.getTowards());
            paramMap.put(SCFields.FLOOR, apply.getCurrentFloor());
            paramMap.put(SCFields.TOTAL_FLOOR, apply.getTotalFloor());
            paramMap.put(SCFields.AREA, apply.getArea());
            paramMap.put(SCFields.YEAR, apply.getYearBuilt());
            try{
                String StringPrice = sCInquiryPrice.getAppraisalPrice(paramMap);
                BigDecimal unitPrice = new BigDecimal(StringPrice);
                BigDecimal price = unitPrice.multiply(apply.getArea()).setScale(2);
                apply.setPrice(price);

                if(price.longValue() > 0){
                    Date deadLine = DateUtil.getDateTimeBefore(new Date(), -timeThreshold);
                    status = Constants.BPS_ORDER_STATUS_EVALUATED;
                    apply.setDeadline(deadLine);
                    apply.setPriceTime(new Date());
                    apply.setUnitPrice(unitPrice);

                    inquiryBaseValueBean.setPriceTime(apply.getPriceTime());
                    inquiryBaseValueBean.setCompanyCode(apply.getCompanyCode());
                }else{
                    status = Constants.BPS_ORDER_STATUS_FALE_EVALUATED;
                }

            }catch (Exception e){
                logger.error("房屋询价getAutoPrice出现异常：" + e);
                //更新询价状态为处理中
                status = Constants.BPS_ORDER_STATUS_EVALUATING;
            }

            inquiryBaseValueBean.setCode(apply.getCode());
            inquiryBaseValueBean.setPrice(null == apply.getPrice() ? null : apply.getPrice().toString());
            inquiryBaseValueBean.setStatus(status);
        }

        apply.setAutoPrice(autoPrice);
        apply.setStatus(status);
        //更新数据
        iBpsCompanyInquiryApplyMapper.updateApplyByCode(apply);

        return inquiryBaseValueBean;

    }



}
