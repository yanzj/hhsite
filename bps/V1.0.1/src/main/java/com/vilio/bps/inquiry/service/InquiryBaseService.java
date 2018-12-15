package com.vilio.bps.inquiry.service;

import com.vilio.bps.commonMapper.pojo.BpsCompanyInquiryApply;
import com.vilio.bps.inquiry.pojo.InquiryBaseValueBean;

import java.util.Map;

/**
 * Created by lenovo on 2017/6/12.
 */
public interface InquiryBaseService {
    public InquiryBaseValueBean sendInquiryRequ(BpsCompanyInquiryApply apply) throws Exception;
}
