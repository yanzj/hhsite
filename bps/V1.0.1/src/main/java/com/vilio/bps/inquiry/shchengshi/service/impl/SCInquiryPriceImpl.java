package com.vilio.bps.inquiry.shchengshi.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.inquiry.shchengshi.service.SCInquiryPrice;
import com.vilio.bps.inquiry.shchengshi.util.SCConstants;
import com.vilio.bps.inquiry.shchengshi.util.SCFields;
import com.vilio.bps.inquiry.worldunion.util.WUConstants;
import com.vilio.bps.inquiry.worldunion.util.WUFields;
import com.vilio.bps.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/5/12.
 */
@Service
public class SCInquiryPriceImpl implements SCInquiryPrice {

    @Resource
    ConfigInfo configInfo;
    @Resource
    CommonService commonService;

    private static Logger logger = Logger.getLogger(com.vilio.bps.inquiry.shchengshi.service.impl.SCInquiryPriceImpl.class);

    /**
     * 房源估价，支持精确和模糊查询
     * @param paramMap
     * @return
     */
    public String getAppraisalPrice(Map paramMap) throws Exception {
        String apiUrl = commonService.getUrlByCode(AppraisalCompanys.SH_CHENGSHI.getCode());

        JSONObject result = null;
        try {
            String url = apiUrl + SCConstants.SC_API_GET_APPRAISAL_PRICE;
            logger.info("城市房屋询价请求参数：reqParam= " + paramMap + ",url=" + url);
            result = HttpRequestUtils.httpPost(url, paramMap, null, false);
            logger.info("城市房屋询价结果返回：unitPrice = " + result);
        }catch (Exception e){
            logger.error("房源询价出现异常：", e);
            throw new HHBizException("", "");
        }
        Object resultObj = result.get(SCFields.SC_A_PRICE);
        String price = resultObj == null ? null : resultObj.toString();
        return price;
    }

    public static void main(String[] args) throws Exception {
        Map paramMap = new HashMap();
        paramMap.put("ProjectId", "11564");
        paramMap.put("UnitId", "651451");
        paramMap.put("RoomNo", "101");
        paramMap.put("Towards", "2");
        paramMap.put("Floor", "2");
        paramMap.put("TotalFloor", "6");
        paramMap.put("Area", "100");
        paramMap.put("Year", "2014");
        new SCInquiryPriceImpl().getAppraisalPrice(paramMap);
    }
}
