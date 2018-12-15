package com.vilio.bps.inquiry.worldunion.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsAppraisalCompanyMapper;
import com.vilio.bps.commonMapper.dao.IBpsCompanyInquiryApplyMapper;
import com.vilio.bps.commonMapper.dao.IBpsInquiryApplyRelationMapper;
import com.vilio.bps.commonMapper.dao.IBpsThresholdMapper;
import com.vilio.bps.commonMapper.pojo.BpsAppraisalCompany;
import com.vilio.bps.commonMapper.pojo.BpsCompanyInquiryApply;
import com.vilio.bps.commonMapper.pojo.BpsInquiryApplyRelation;
import com.vilio.bps.commonMapper.pojo.BpsThreshold;
import com.vilio.bps.inquiry.pojo.InquiryBaseValueBean;
import com.vilio.bps.inquiry.worldunion.pojo.WUGetQueryPrice;
import com.vilio.bps.inquiry.worldunion.util.WUConstants;
import com.vilio.bps.inquiry.worldunion.util.WUFields;
import com.vilio.bps.inquiry.worldunion.pojo.WUAutoPrice;
import com.vilio.bps.inquiry.worldunion.service.WUInquiryPrice;
import com.vilio.bps.inquiry.worldunion.util.WURespCode;
import com.vilio.bps.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/12/0012.
 */
@Service
public class WUInquiryPriceImpl implements WUInquiryPrice {

    private static Logger logger = Logger.getLogger(com.vilio.bps.inquiry.worldunion.service.impl.WUGetHousingInfoImpl.class);

    @Resource
    CommonService commonService;
    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;
    @Resource
    IBpsAppraisalCompanyMapper iBpsAppraisalCompanyMapper;
    @Resource
    IBpsInquiryApplyRelationMapper iBpsInquiryApplyRelationMapper;
    @Resource
    IBpsThresholdMapper iBpsThresholdMapper;

    public WUAutoPrice getAutoPrice(String cid, String bid, String hid, String area, String caseId) throws Exception {
        //请求头
        Map<String, String> headers = new HashedMap();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        //请求参数
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        apiUrl += WUConstants.WU_API_AUTOPRICE;
        apiUrl += "/" + cid + "/" + bid + "/" + hid + "/" + area + "/" + caseId;

        Object result = null;
        try {
            logger.info("世联房屋询价请求参数：AutoPrice_WUreqParam=" + apiUrl + ",heads=" + headers);
            result = HttpRequestUtils.httpGet(apiUrl, headers);
            logger.info("世联房屋询价结果返回：AutoPrice_WUresp=" + result);
        }catch (Exception e){
            logger.error("房屋询价出现异常：", e);
            throw e;
        }
        WUAutoPrice wuAutoPrice = (WUAutoPrice) CommonUtil.convertJSONToEntity(WUAutoPrice.class, result);
        return wuAutoPrice;
    }

    @Override
    public WUGetQueryPrice getQueryPrice(Map paramMap) throws Exception {
        //请求头
        Map<String, String> headers = new HashedMap();
        headers.put(WUFields.WU_AUTHORIZATION, WUConstants.WU_API_HEADER_PREFIX + WUConstants.WU_API_LOGIN_TOKEN);
        //请求参数
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.WORLD_UNION.getCode());
        apiUrl += WUConstants.WU_API_QUERY_PRICE;

        paramMap.put(WUFields.WU_R_HOUSECARDNO, "");
        paramMap.put(WUFields.WU_R_PROJECTNAME, "");
        paramMap.put(WUFields.WU_R_HOUSETYPE, "");
        paramMap.put(WUFields.WU_R_ENDDATE, "");
        paramMap.put(WUFields.WU_R_REMARK, "别墅");
        paramMap.put(WUFields.WU_R_TOTALFLOOR, "");
        paramMap.put(WUFields.WU_R_ONFLOOR, "");

        Object result = null;
        try {
            logger.info("世联房屋询价请求参数：QueryPrice_WUreqParam=" + paramMap + ",url=" + apiUrl + ",heads=" + headers);
            result = HttpRequestUtils.httpPost(apiUrl, JSONObject.fromObject(paramMap), headers);
            logger.info("世联房屋询价结果返回：QueryPrice_WUresp=" + result);
        }catch (Exception e){
            logger.error("房屋询价出现异常：", e);
            throw e;
        }

        WUGetQueryPrice wuGetQueryPrice = null;
        if(null != result){
            wuGetQueryPrice = (WUGetQueryPrice) CommonUtil.convertJSONToEntity(WUGetQueryPrice.class, result);
        }

            return wuGetQueryPrice;
        }


    public static void main(String[] args) throws Exception {



        new WUInquiryPriceImpl().getAutoPrice("10979","440748","16676856","200.00","HH20170512180125333");
    }
}
