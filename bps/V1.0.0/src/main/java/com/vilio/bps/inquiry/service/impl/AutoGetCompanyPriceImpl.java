package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsCompanyInquiryApplyMapper;
import com.vilio.bps.commonMapper.pojo.BpsAppraisalCompany;
import com.vilio.bps.commonMapper.pojo.BpsCompanyInquiryApply;
import com.vilio.bps.commonMapper.pojo.BpsInquiryApplyRelation;
import com.vilio.bps.inquiry.pojo.InquiryBaseValueBean;
import com.vilio.bps.inquiry.service.AutoGetCompanyPriceService;
import com.vilio.bps.inquiry.shchengshi.service.SCInquiryPrice;
import com.vilio.bps.inquiry.shchengshi.util.SCFields;
import com.vilio.bps.inquiry.worldunion.pojo.WUAutoPrice;
import com.vilio.bps.inquiry.worldunion.pojo.WUGetQueryPrice;
import com.vilio.bps.inquiry.worldunion.service.WUInquiryPrice;
import com.vilio.bps.inquiry.worldunion.util.WUConstants;
import com.vilio.bps.inquiry.worldunion.util.WUFields;
import com.vilio.bps.inquiry.worldunion.util.WURespCode;
import com.vilio.bps.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据本地库中等待询价公司回值的询价申请去询价公司查询，然后更新本地询价
 */
@Service("autoGetCompanyPriceService")
public class AutoGetCompanyPriceImpl implements AutoGetCompanyPriceService {
    private Logger logger = Logger.getLogger(AutoGetCompanyPriceImpl.class);
    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;
    @Resource
    SCInquiryPrice sCInquiryPrice;
    @Resource
    WUInquiryPrice wUInquiryPrice;
    @Resource
    CommonService commonService;

    public void execute(){
        //获取本地需要访问
        List<BpsCompanyInquiryApply> applyList = iBpsCompanyInquiryApplyMapper.queryInquiryApplyNeedRepeatRequest();

        if(null != applyList && applyList.size() >0){
            for(BpsCompanyInquiryApply apply:applyList){
                //将超出时间的置为待评估
                Date createDate = apply.getDateCreated();
                Date limitTime = null;
                try {
                    limitTime = commonService.getDeadLineTime(createDate);
                    double differHours = DateUtil.differHours(limitTime, new Date());
                    if(differHours < 0){
                        apply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATION_INVALID);
                        iBpsCompanyInquiryApplyMapper.updateApplyPriceByCode(apply);
                        continue;
                    }
                } catch (Exception e) {
                    logger.error("获取记录创建时间的过期时间异常：" + e.getStackTrace());
                    continue;
                }

                String companyCode = apply.getCompanyCode();
                try{
                    if (AppraisalCompanys.SH_CHENGSHI.getCode().equals(companyCode)) {
                        inquiryShChengShi(apply);
                    }if(AppraisalCompanys.WORLD_UNION.getCode().equals(companyCode)){
                        String houseType = apply.getHouseType();
                        if(houseType.equals(Constants.HOUSE_TYPE_VILLADOM)){
                            inquiryWUVilla(apply);
                        }else{
                            inquiryWUApartment(apply);
                        }
                    }
                }catch (Exception e){
                    logger.error("Code="+ apply.getCode() +"重新请求询价公司companyCode=" + companyCode + "出现异常:" + e);
                }
            }
        }




    }

    /**
     * 城市询价
     * @param apply
     */
    void inquiryShChengShi(BpsCompanyInquiryApply apply) throws Exception {
        //取时间阈值(小时)
        int timeThreshold = commonService.getTimeThresholdsByCityCode(apply.getCityCode());
        Map paramMap = new HashMap();
        paramMap.put(SCFields.PROJECT_ID, apply.getPlotsCode());
        paramMap.put(SCFields.UNIT_ID, apply.getUnitCode());
        paramMap.put(SCFields.ROOM_NO, apply.getHouseCode());
        paramMap.put(SCFields.TOWARDS, apply.getTowards());
        paramMap.put(SCFields.FLOOR, apply.getCurrentFloor());
        paramMap.put(SCFields.TOTAL_FLOOR, apply.getTotalFloor());
        paramMap.put(SCFields.AREA, apply.getArea());
        paramMap.put(SCFields.YEAR, apply.getYearBuilt());

        String status = null;
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

            }else{
                status = Constants.BPS_ORDER_STATUS_FALE_EVALUATED;
            }
            apply.setPrice(price);
            apply.setStatus(status);
            iBpsCompanyInquiryApplyMapper.updateApplyPriceByCode(apply);
        }catch (Exception e){
            logger.error("城市房屋询价getAutoPrice出现异常：" + e);
        }

    }

    /**
     * 世联询价 - 公寓
     * @param apply
     */
    void inquiryWUApartment(BpsCompanyInquiryApply apply) throws Exception {
        //取时间阈值(小时)
        int timeThreshold = commonService.getTimeThresholdsByCityCode(apply.getCityCode());

        //根据当前时间计算一个判断记录是否有效的时间参考点：记录评估时间 + 时间阈值 > 当前时间
        Date comparedTime = DateUtil.getDateTimeBefore(new Date(), timeThreshold);
        apply.setPriceTime(comparedTime);

        try{
            String status = null;
            WUAutoPrice bean = wUInquiryPrice.getAutoPrice(apply.getPlotsCode(), apply.getUnitCode(), apply.getHouseCode(),apply.getArea().toString(), apply.getCode());
            if(WUConstants.WU_AUTOPRICE_STATUS_AVALIABLE.equals(bean.getState())){
                BigDecimal price = new BigDecimal(bean.getTotalPrice());
                apply.setPrice(price);
                status = Constants.BPS_ORDER_STATUS_EVALUATED;
            }else{
                status = Constants.BPS_ORDER_STATUS_FALE_EVALUATED;
            }
            apply.setStatus(status);
            //更新数据
            iBpsCompanyInquiryApplyMapper.updateApplyPriceByCode(apply);
        }catch (Exception e){
            logger.error("世联房屋询价getAutoPrice出现异常：" + e);
        }

    }

    /**
     * 世联询价 - 别墅
     * @param apply
     */
    void inquiryWUVilla(BpsCompanyInquiryApply apply) throws Exception {
        //询价请求流水批次号
        String code = apply.getCode();
        //当前城市
        String cityCode = apply.getCityCode();
        //取时间阈值(小时)
        int timeThreshold = commonService.getTimeThresholdsByCityCode(apply.getCityCode());

        Map remoteParamMap = new HashMap();
        remoteParamMap.put(WUFields.WU_R_CASEID, code);
        remoteParamMap.put(WUFields.WU_R_ADDRESS, apply.getAddress());
        remoteParamMap.put(WUFields.WU_R_BUILDAREA, apply.getArea());
        remoteParamMap.put(WUFields.WU_R_CITYID, cityCode);
        remoteParamMap.put(WUFields.WU_R_AREAID, apply.getAreaCode());
        remoteParamMap.put(WUFields.WU_R_TYPECODE, Constants.WU_TYPE_CODE_NEW_REQUEST);

        try {
            String status = null;
            WUGetQueryPrice wuGetQueryPrice = wUInquiryPrice.getQueryPrice(remoteParamMap);
            String returnStatus = wuGetQueryPrice.getReturnCode();
            if(WURespCode.WAIT_HAND_PRICE.getCode().equals(returnStatus)){
                status = Constants.BPS_ORDER_STATUS_EVALUATING;
            }else if(WURespCode.AUTO_PRICE.getCode().equals(returnStatus) || WURespCode.HAND_PRICE.getCode().equals(returnStatus)){
                status = Constants.BPS_ORDER_STATUS_EVALUATED;

                apply.setPrice(new BigDecimal(wuGetQueryPrice.getTotalPrice()));
                apply.setPriceTime(new Date());
            }else if(WURespCode.INVALID_ADDRESS.getCode().equals(returnStatus)){
                status = Constants.BPS_ORDER_STATUS_FALE_EVALUATED;
            }else{
                status = Constants.BPS_ORDER_STATUS_FALE_EVALUATED;
            }
            apply.setStatus(status);
            //更新数据
            iBpsCompanyInquiryApplyMapper.updateApplyPriceByCode(apply);
        }catch (Exception e){
            logger.error("房屋询价出现异常：", e);

        }


    }

}
