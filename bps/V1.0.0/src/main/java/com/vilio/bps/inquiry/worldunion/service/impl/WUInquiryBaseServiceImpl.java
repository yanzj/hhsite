package com.vilio.bps.inquiry.worldunion.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsAppraisalCompanyMapper;
import com.vilio.bps.commonMapper.dao.IBpsCityMapper;
import com.vilio.bps.commonMapper.dao.IBpsCompanyInquiryApplyMapper;
import com.vilio.bps.commonMapper.dao.IBpsInquiryApplyRelationMapper;
import com.vilio.bps.commonMapper.pojo.BpsAppraisalCompany;
import com.vilio.bps.commonMapper.pojo.BpsCity;
import com.vilio.bps.commonMapper.pojo.BpsCompanyInquiryApply;
import com.vilio.bps.commonMapper.pojo.BpsInquiryApplyRelation;
import com.vilio.bps.inquiry.pojo.InquiryBaseValueBean;
import com.vilio.bps.inquiry.service.InquiryBaseService;
import com.vilio.bps.inquiry.worldunion.pojo.WUAutoPrice;
import com.vilio.bps.inquiry.worldunion.pojo.WUGetQueryPrice;
import com.vilio.bps.inquiry.worldunion.service.WUInquiryPrice;
import com.vilio.bps.inquiry.worldunion.util.WUConstants;
import com.vilio.bps.inquiry.worldunion.util.WUFields;
import com.vilio.bps.inquiry.worldunion.util.WURespCode;
import com.vilio.bps.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/12.
 */
@Service("wUInquiryBaseServiceImpl")
public class WUInquiryBaseServiceImpl implements InquiryBaseService{
    private static Logger logger = Logger.getLogger(WUInquiryBaseServiceImpl.class);

    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;
    @Resource
    IBpsAppraisalCompanyMapper iBpsAppraisalCompanyMapper;
    @Resource
    IBpsInquiryApplyRelationMapper iBpsInquiryApplyRelationMapper;
    @Resource
    WUInquiryPrice wUInquiryPrice;
    @Resource
    CommonService commonService;
    @Resource
    IBpsCityMapper iBpsCityMapper;


    public InquiryBaseValueBean sendInquiryRequ(BpsCompanyInquiryApply apply) throws Exception {
        InquiryBaseValueBean inquiryBaseValueBean = null;
        if(null == apply || null == apply.getHouseType()){
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING.getReturnCode(), ReturnCode.REQUIRED_FIELD_MISSING.getReturnMessage() + "[" + Fields.PARAM_HOUSE_TYPE_CODE +"]");
        }
        //房屋类型
        String houseTypeCode =  apply.getHouseType();

        if(Constants.HOUSE_TYPE_VILLADOM.equals(houseTypeCode)){
            //别墅
            inquiryBaseValueBean = inquiryVilla(apply);
        }else if(Constants.HOUSE_TYPE_ORDINARY.equals(houseTypeCode)){
            //公寓
            inquiryBaseValueBean = inquiryApartment(apply);
        }

        return inquiryBaseValueBean;

    }

    /**
     * 公寓询价
     * @param apply
     * @return
     * @throws Exception
     */
    private InquiryBaseValueBean inquiryApartment(BpsCompanyInquiryApply apply) throws Exception{
        InquiryBaseValueBean inquiryBaseValueBean = new InquiryBaseValueBean();
        inquiryBaseValueBean.setCompanyCode(apply.getCompanyCode());
        //取时间阈值(小时)
        int timeThreshold = commonService.getTimeThresholdsByCityCode(apply.getCityCode());

        boolean manualHandling = false;//人工查询

        //根据当前时间计算一个判断记录是否有效的时间参考点：记录评估时间 + 时间阈值 > 当前时间
        Date comparedTime = DateUtil.getDateTimeBefore(new Date(), timeThreshold);
        apply.setPriceTime(comparedTime);
        //查询本地记录中该房屋的成功的记录（查后清掉）
        apply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATED);
        List<BpsCompanyInquiryApply> list = iBpsCompanyInquiryApplyMapper.queryInquiryApplyByApplyParam(apply);
        apply.setStatus(null);
        apply.setPriceTime(null);

        if(null != list && list.size() > 0){
            //本地如果有有效结果，直接获取结果返回 return
            BpsCompanyInquiryApply applyBefore = list.get(0);
            inquiryBaseValueBean.setCode(applyBefore.getCode());
            inquiryBaseValueBean.setPrice(null == applyBefore.getPrice() ? null : applyBefore.getPrice().toString());
            inquiryBaseValueBean.setPriceTime(applyBefore.getPriceTime());
            inquiryBaseValueBean.setCompanyCode(applyBefore.getCompanyCode());
            inquiryBaseValueBean.setStatus(Constants.BPS_ORDER_STATUS_EVALUATED);

            //新增关联
            BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
            bpsInquiryApplyRelation.setApplyCode(applyBefore.getCode());
            bpsInquiryApplyRelation.setSerialNo(apply.getSerialNo());
            iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);
            return inquiryBaseValueBean;
        }else{
            //新增请求记录和关联关系
            String code = CommonUtil.getCurrentTimeStr("I", "C");
            apply.setCode(code);
            apply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
            //使用当前时间
            apply.setPriceTime(new Date());
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
            status = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
            autoPrice = Constants.APPLY_AUTO_PRICE_MAN;
        }

        if(!manualHandling){
            int a = apply.getArea().subtract((new BigDecimal(company.getMaxArea()))).intValue();
            if(a >= 0 ){
                manualHandling = true;
                inquiryBaseValueBean.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
                status = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
                autoPrice = Constants.APPLY_AUTO_PRICE_MAN;
            }
        }

        //发送询价请求
        if(!manualHandling){
            try{
                WUAutoPrice bean = wUInquiryPrice.getAutoPrice(apply.getPlotsCode(), apply.getUnitCode(), apply.getHouseCode(),apply.getArea().toString(), apply.getCode());
                if(WUConstants.WU_AUTOPRICE_STATUS_AVALIABLE.equals(bean.getState())){
                    BigDecimal price = new BigDecimal(bean.getTotalPrice());

                    apply.setPrice(price);
                    status = Constants.BPS_ORDER_STATUS_EVALUATED;
                    apply.setPriceTime(new Date());

                    inquiryBaseValueBean.setPrice(apply.getPrice().toString());
                    inquiryBaseValueBean.setPriceTime(apply.getPriceTime());
                }else{
                    status = Constants.BPS_ORDER_STATUS_FALE_EVALUATED;
                }
            }catch (Exception e){
                logger.error("房屋询价getAutoPrice出现异常：" + e);
                //更新询价状态为处理中
                status = Constants.BPS_ORDER_STATUS_EVALUATING;
            }
        }
        inquiryBaseValueBean.setCode(apply.getCode());
        inquiryBaseValueBean.setStatus(status);
        apply.setStatus(status);
        apply.setAutoPrice(autoPrice);
        //更新数据
        iBpsCompanyInquiryApplyMapper.updateApplyByCode(apply);

        return inquiryBaseValueBean;
    }

    /**
     * 别墅询价
     * @param apply
     * @return
     * @throws Exception
     */
    private InquiryBaseValueBean inquiryVilla(BpsCompanyInquiryApply apply) throws Exception{
        InquiryBaseValueBean inquiryBaseValueBean = new InquiryBaseValueBean();
        inquiryBaseValueBean.setCompanyCode(apply.getCompanyCode());
        //当前请求code
        String code = "";
        //询价请求流水批次号
        String serialNo = apply.getSerialNo();
        //当前公司
        String companyCode = apply.getCompanyCode();
        //当前城市
        String cityCode = apply.getCityCode();

        //取时间阈值(小时)
        int timeThreshold = commonService.getTimeThresholdsByCityCode(apply.getCityCode());

        //面积
        BigDecimal bigArea = apply.getArea();

        //根据当前时间计算一个判断记录是否有效的时间参考点：记录评估时间 + 时间阈值 > 当前时间
        Date comparedTime = DateUtil.getDateTimeBefore(new Date(), timeThreshold);

        //查询本地记录中该房屋的成功的记录（查后清掉）
        apply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATED);
        List<BpsCompanyInquiryApply> list = iBpsCompanyInquiryApplyMapper.queryInquiryApplyByApplyParam(apply);
        apply.setStatus(null);
        apply.setPriceTime(null);

        if(null != list && list.size() > 0){
            BpsCompanyInquiryApply applyBefore = list.get(0);
            inquiryBaseValueBean.setCode(applyBefore.getCode());
            inquiryBaseValueBean.setPrice(null == applyBefore.getPrice() ? null : applyBefore.getPrice().toString());
            inquiryBaseValueBean.setPriceTime(applyBefore.getPriceTime());
            inquiryBaseValueBean.setCompanyCode(applyBefore.getCompanyCode());
            inquiryBaseValueBean.setStatus(Constants.BPS_ORDER_STATUS_EVALUATED);

            //新增关联
            BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
            bpsInquiryApplyRelation.setApplyCode(applyBefore.getCode());
            bpsInquiryApplyRelation.setSerialNo(serialNo);
            iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);
            return inquiryBaseValueBean;
        }else{
            //新增请求记录和关联关系
            code = CommonUtil.getCurrentTimeStr(Fields.INQUIRY_APPLY_CODE_PREFIX, Fields.INQUIRY_APPLY_CODE_SUFFIX);
            apply.setCode(code);
            apply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
            //使用当前时间
            apply.setPriceTime(new Date());
            apply.setAutoPrice(Constants.APPLY_AUTO_PRICE_COMPANY);
            iBpsCompanyInquiryApplyMapper.saveApply(apply);
            BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();
            bpsInquiryApplyRelation.setApplyCode(code);
            bpsInquiryApplyRelation.setSerialNo(apply.getSerialNo());
            iBpsInquiryApplyRelationMapper.saveApplyRelation(bpsInquiryApplyRelation);
        }

        BpsAppraisalCompany company = iBpsAppraisalCompanyMapper.getCompanyByCode(companyCode);
        String status = Constants.BPS_ORDER_STATUS_EVALUATING;
        String autoPrice = Constants.APPLY_AUTO_PRICE_COMPANY;
        boolean manualHandling = false;//人工查询
        //是否大于等于面积参数，大于转人工
        if(bigArea.compareTo(new BigDecimal(company.getMaxArea())) > 0){
            manualHandling = true;
            inquiryBaseValueBean.setStatus(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION);
            status = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
            autoPrice = Constants.APPLY_AUTO_PRICE_MAN;
        }else{
            Map remoteParamMap = new HashMap();
            remoteParamMap.put(WUFields.WU_R_CASEID, code);
            remoteParamMap.put(WUFields.WU_R_ADDRESS, apply.getAddress());
            remoteParamMap.put(WUFields.WU_R_BUILDAREA, apply.getArea());
            BpsCity bpsCity = iBpsCityMapper.getBeanByCode(cityCode);
            String wuCityCode = bpsCity == null ? "" : bpsCity.getWuCode();
            remoteParamMap.put(WUFields.WU_R_CITYID, wuCityCode);
            remoteParamMap.put(WUFields.WU_R_AREAID, apply.getAreaCode());
            remoteParamMap.put(WUFields.WU_R_TYPECODE, Constants.WU_TYPE_CODE_NEW_REQUEST);


            Object result = null;
            try {
                status = Constants.BPS_ORDER_STATUS_EVALUATING;
                /*WUGetQueryPrice wuGetQueryPrice = wUInquiryPrice.getQueryPrice(remoteParamMap);
                String returnStatus = wuGetQueryPrice.getReturnCode();
                if(WURespCode.WAIT_HAND_PRICE.getCode().equals(returnStatus)){
                    status = Constants.BPS_ORDER_STATUS_EVALUATING;
                }else if(WURespCode.AUTO_PRICE.getCode().equals(returnStatus)){
                    status = Constants.BPS_ORDER_STATUS_EVALUATED;
                    inquiryBaseValueBean.setPrice(wuGetQueryPrice.getTotalPrice());

                    apply.setPrice(new BigDecimal(wuGetQueryPrice.getTotalPrice()));
                    apply.setPriceTime(new Date());
                }else if(WURespCode.HAND_PRICE.getCode().equals(returnStatus)){
                    status = Constants.BPS_ORDER_STATUS_EVALUATED;
                    inquiryBaseValueBean.setPrice(wuGetQueryPrice.getTotalPrice());

                    apply.setPrice(new BigDecimal(wuGetQueryPrice.getTotalPrice()));
                    apply.setPriceTime(new Date());
                }else if(WURespCode.INVALID_ADDRESS.getCode().equals(returnStatus)){
                    status = Constants.BPS_ORDER_STATUS_FALE_EVALUATED;
                }else{
                    status = Constants.BPS_ORDER_STATUS_FALE_EVALUATED;
                }*/
            }catch (Exception e){
                logger.error("房屋询价出现异常：", e);
                //更新询价状态为处理中
                status = Constants.BPS_ORDER_STATUS_EVALUATING;
            }
        }

        inquiryBaseValueBean.setStatus(status);

        apply.setStatus(status);
        apply.setAutoPrice(autoPrice);
        iBpsCompanyInquiryApplyMapper.updateApplyPriceByCode(apply);

        return inquiryBaseValueBean;

    }
}
