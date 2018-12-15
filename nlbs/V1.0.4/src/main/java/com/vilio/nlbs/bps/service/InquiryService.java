package com.vilio.nlbs.bps.service;

import java.util.Map;

/**
 * Created by dell on 2017/6/15/0015.
 */
public interface InquiryService {

    public Map initInquiry(Map paramMap) throws Exception;

    public Map initInquiryList(Map paramMap) throws Exception;

    public Map apartmentInquiry(Map paramMap) throws Exception;

    public Map villaInquiry(Map paramMap) throws Exception;

    public Map manualInquiry(Map paramMap) throws Exception;

    public Map queryInquiryList(Map paramMap) throws Exception;

    public Map queryInquiryInfo(Map paramMap) throws Exception;

    public Map queryOperateHistory(Map paramMap) throws Exception;

    public Map updateInquiryResult(Map paramMap) throws Exception;

    public Map inputInquiryPrice(Map paramMap) throws Exception;

    public Map claimInquiryTask(Map paramMap) throws Exception;


}
