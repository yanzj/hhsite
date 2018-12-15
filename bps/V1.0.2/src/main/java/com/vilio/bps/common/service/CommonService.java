package com.vilio.bps.common.service;

import com.vilio.bps.commonMapper.pojo.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/5.
 */
public interface CommonService {


    public Map<String, Object> getAllSystemConfig() throws Exception;

    public Map<String, BpsConfig> getValidSystemConfig() throws Exception;

    public List<BpsCity> getValidCitys() throws Exception;

    public Map<String, Object> updateConfig(Map<String, Object> map) throws Exception;

    public Map<String, Object> updateCityThreshold(Map<String, Object> map) throws Exception;
    //根据城市代码获取关联估价公司的列表信息
    public List<Map> getCompanyListByCityCode(String cityCode) throws Exception;

    public BpsAppraisalCompany getInquiryCompanyByCode(String code) throws Exception;

    public String getUrlByCode(String code) throws Exception;
    //计算询价结果
    public Map getFinalPrice(List<BigDecimal> priceList) throws Exception;
    //获取失效时间
    public Date getDeadLineTime(Date currentDate) throws Exception;
    //获取所有可用的询价状态(针对询价主流水)
    public List<Map> getAvaliableInquiryStatus() throws Exception;
    //获取当前城市时间阈值
    public int getTimeThresholdsByCityCode(String cityCode) throws Exception;
    //获取所有城市列表
    Map<String, BpsCity> queryAllCity() throws Exception;
    //获取一笔询价的相关公司流水
    Map<String, BpsCompanyInquiryApply> queryAllCompanyApplyForOneInquiry(String serialNo) throws Exception ;

    public Map<String, BpsCompanyCity> getCompanyCityMapByCityCode(String cityCode) throws Exception;


}
