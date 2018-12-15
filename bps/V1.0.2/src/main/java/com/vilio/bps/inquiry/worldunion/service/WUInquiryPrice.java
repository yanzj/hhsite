package com.vilio.bps.inquiry.worldunion.service;

import com.vilio.bps.inquiry.pojo.InquiryBaseValueBean;
import com.vilio.bps.inquiry.worldunion.pojo.WUAutoPrice;
import com.vilio.bps.inquiry.worldunion.pojo.WUGetQueryPrice;

import java.util.Map;

/**
 * Created by dell on 2017/5/12/0012.
 */
public interface WUInquiryPrice {

    public WUAutoPrice getAutoPrice(String cid, String bid, String hid, String area, String caseId) throws Exception;

    public WUGetQueryPrice getQueryPrice(Map paramMap) throws Exception;
}
