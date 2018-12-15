package com.vilio.bps.common.service.impl;

import com.vilio.bps.common.dao.CommonDao;
import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.*;
import com.vilio.bps.commonMapper.pojo.*;
import com.vilio.bps.common.dao.IBpsSystemConfigMapper;
import com.vilio.bps.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell on 2017/5/5.
 */
@Service
public class CommonServiceImpl implements CommonService {
    Logger logger = Logger.getLogger(CommonServiceImpl.class);

    @Resource
    CommonDao commonDao;

    @Resource
    IBpsSystemConfigMapper iBpsSystemConfigMapper;

    @Resource
    IBpsCityMapper iBpsCityMapper;

    @Resource
    IBpsCompanyCityMapper iBpsCompanyCityMapper;

    @Resource
    IBpsAppraisalCompanyMapper iBpsAppraisalCompanyMapper;

    @Resource
    IBpsAlgorithmMapper iBpsAlgorithmMapper;
    @Resource
    BpsInquiryStatusMapper bpsInquiryStatusMapper;
    @Resource
    IBpsThresholdMapper iBpsThresholdMapper;
    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;

    public final static int THRESHOLD_CITY = 0;
    public final static int THRESHOLD_COMPANY = 1;

    /**
     * 查找所有的系统配置
     *
     * @param
     * @return
     */
    public Map<String, Object> getAllSystemConfig() throws Exception {
        Map data = new HashMap();
        List<BpsConfig> bpsConfigList = iBpsSystemConfigMapper.getAllSystemConfig();

        if (null == bpsConfigList || bpsConfigList.size() < 1) {
            return null;
        }
        for (BpsConfig config : bpsConfigList) {
            data.put(config.getConfigName(), config.getConfigValue());
        }

        Map result = new HashMap();
        result.putAll(data);
        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功查询到所有系统配置项！");

        return result;
    }

    /**
     * 查找所有有效的系统配置
     *
     * @param
     * @return
     */
    public Map<String, BpsConfig> getValidSystemConfig() throws Exception {
        List<BpsConfig> bpsConfigList = iBpsSystemConfigMapper.getValidSystemConfig();

        if (null == bpsConfigList || bpsConfigList.size() < 1) {
            return null;
        }
        Map<String, BpsConfig> configMap = new HashMap();
        for (BpsConfig config : bpsConfigList) {
            configMap.put(config.getConfigName(), config);
        }
        return configMap;
    }

    /**
     * 获取所有城市列表
     * @return
     * @throws Exception
     */
    public List<BpsCity> getValidCitys() throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(Fields.PARAM_STATUS, Constants.BPS_STATUS_AVALIABLE);
        List<BpsCity> bpsCityList = iBpsCityMapper.getListPrmMapRtnBean(paramMap);

        if (null == bpsCityList || bpsCityList.size() < 1) {
            return null;
        }

        return bpsCityList;
    }

    /**
     * 更新系统配置
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map<String, Object> updateConfig(Map<String, Object> map) throws Exception {

        int updateCount = 0;
        for (Map.Entry<String, Object> m : map.entrySet()) {
            Map<String, Object> paramMap = new HashedMap();
            paramMap.put("configName", m.getKey());
            paramMap.put("configValue", m.getValue());
            updateCount += iBpsSystemConfigMapper.updateSystemConfig(paramMap);
        }


        Map result = new HashMap();
        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, "系统设置更新成功！");

        return result;
    }

    /**
     * 更新时间阈值
     *
     * @param
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map<String, Object> updateCityThreshold(Map<String, Object> map) throws Exception {
        int updateCount = iBpsSystemConfigMapper.updateCityThreshold(map);
        Map result = new HashMap();
        result.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        result.put(Fields.PARAM_MESSAGE_ERR_MESG, "时间阈值设置成功！");

        return result;
    }

    /**
     * 根据城市代码获取关联估价公司的列表信息
     * @param cityCode
     * @return
     * @throws Exception
     */
    public List<Map> getCompanyListByCityCode(String cityCode) throws Exception {
        List<Map> returnCompanyList = new ArrayList<Map>();
        if(StringUtils.isNotBlank(cityCode)){
            Map paramMap = new HashMap();
            paramMap.put(Fields.PARAM_CITY_CODE, cityCode);
            paramMap.put(Fields.PARAM_STATUS, Constants.BPS_STATUS_AVALIABLE);
            paramMap.put(Fields.PARAM_ORDER_BY, Fields.PARAM_ORDER_NO);
            List<Map> bpsCompanyCityList = iBpsCompanyCityMapper.getListPrmMapRtnMap(paramMap);
            if(bpsCompanyCityList != null){
                for(Map bcc : bpsCompanyCityList){
                    Map bpsCompanyCityMap = new HashMap();
                    bpsCompanyCityMap.put(Fields.PARAM_COMPANY_CODE, bcc.get(Fields.PARAM_COMPANY_CODE));
                    bpsCompanyCityMap.put(Fields.PARAM_COMPANY_NAME, bcc.get(Fields.PARAM_COMPANY_NAME));
                    returnCompanyList.add(bpsCompanyCityMap);
                }
            }
        }

        return returnCompanyList;
    }

    /**
     * 根据估价公司code获取估价公司实体
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public BpsAppraisalCompany getInquiryCompanyByCode(String code) throws Exception {
        return iBpsAppraisalCompanyMapper.getBeanByCode(code);
    }

    /**
     * 根据估价公司code获取估价公司的API URL
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public String getUrlByCode(String code) throws Exception {
        BpsAppraisalCompany bpsAppraisalCompany = getInquiryCompanyByCode(code);
        if(bpsAppraisalCompany != null){
            return bpsAppraisalCompany.getApiUrl();
        }

        return "";
    }

    @Override
    public Map getFinalPrice(List<BigDecimal> priceList) throws Exception {
        Map returnMap = new HashMap();

        //获取所有估价的差价百分比
        double percent = CommonUtil.getPercentOfDiffThreshold(priceList);
        Map<String, BpsConfig> configMap = getValidSystemConfig();
        String dbPercent = configMap.get(Constants.CONFIG__PERCENT_OF_DIFF_THRESHOLD).getConfigValue();
        boolean manualHandling = false;
        Map calcParamMap = new HashMap();
        calcParamMap.put(Fields.PARAM_PRICE_LIST, priceList);
        calcParamMap.put("config", configMap);
        if(Double.parseDouble(dbPercent) < percent){
            //大于差价百分比是否转人工
            if (null != configMap.get(Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL)) {
                String turnManualHand = configMap.get(Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL).getConfigValue();
                if (Constants.CONFIG__OVER_PERCENT_TURN_ARTIFICIAL_VALUE_YES.equals(turnManualHand)) {
                    manualHandling = true;
                }
            }

            if (!manualHandling) {
                //大于等于阈值时算法
                String algorithmName = iBpsAlgorithmMapper.findAlgorithmByAlgorithmCode(configMap.get(Constants.CONFIG__GREAT_OR_EQU__PERCENT_OF_DIFF_THRESHOLD).getConfigValue());
                returnMap.put(Fields.PARAM_CURRENT_PERCENT, Constants.CONFIG__GREAT_OR_EQU__PERCENT_OF_DIFF_THRESHOLD);
                returnMap.put(Fields.PARAM_ALGORITHM, configMap.get(Constants.CONFIG__GREAT_OR_EQU__PERCENT_OF_DIFF_THRESHOLD).getConfigValue());
                returnMap.put(Fields.PARAM_ASSESSMENT_PRICE, String.valueOf(AlgorithmUtil.matchAlgorithm(algorithmName, calcParamMap)));
            }
        } else {
            //小于阈值时的算
            String algorithmName = iBpsAlgorithmMapper.findAlgorithmByAlgorithmCode(configMap.get(Constants.CONFIG__LESS_THAN_PERCENT_OF_DIFF_THRESHOLD).getConfigValue());
            returnMap.put(Fields.PARAM_CURRENT_PERCENT, Constants.CONFIG__LESS_THAN_PERCENT_OF_DIFF_THRESHOLD);
            returnMap.put(Fields.PARAM_ALGORITHM, configMap.get(Constants.CONFIG__LESS_THAN_PERCENT_OF_DIFF_THRESHOLD).getConfigValue());
            returnMap.put(Fields.PARAM_ASSESSMENT_PRICE, String.valueOf(AlgorithmUtil.matchAlgorithm(algorithmName, calcParamMap)));
        }
        return returnMap;
    }

    /**
     * 获取某段时间后的时间，作为deadline存储
     * @return
     * @throws Exception
     */
    @Override
    public Date getDeadLineTime(Date currentDate) throws Exception {
        if(null == currentDate){
            return null;
        }
        Map<String, BpsConfig> configMap = getValidSystemConfig();

        //系统级参数：评估有效时间阈值（超出该时间将不进行评估）
        int effectiveHourLimit = 72;
        /* 获取 评估有效时间阈值 */
        BpsConfig hourLimitConfig = configMap.get(Constants.CONFIG__EFFECTIVE_HOUR_LIMIT);
        if(null != hourLimitConfig && StringUtils.isNotBlank(hourLimitConfig.getConfigValue())){
            try{
                effectiveHourLimit = Integer.parseInt(hourLimitConfig.getConfigValue());
            }catch (Exception e){
                logger.error(">>>转换系统时间参数（评估有效时间阈值）effectiveHourLimit=" + effectiveHourLimit + "异常：" + e);
            }
        }
        return DateUtil.getDateTimeBefore(currentDate, - effectiveHourLimit);
    }

    @Override
    public List<Map> getAvaliableInquiryStatus() throws Exception {
        List returnList = new ArrayList();
        List<BpsInquiryStatus> list = bpsInquiryStatusMapper.queryAllInquiryStatus("1");
        if(null != list){
            for(BpsInquiryStatus status: list){
                Map statusMap = new HashMap();
                statusMap.put("status", status.getValue());
                statusMap.put("name", status.getName());
                returnList.add(statusMap);
            }
        }

        return returnList;
    }

    @Override
    public int getTimeThresholdsByCityCode(String cityCode) throws Exception {
        //取时间阈值(小时)
        List<BpsThreshold> thresholdList = iBpsThresholdMapper.queryAvaliableThresholdCityCode(cityCode);
        int timeThreshold = 0;
        if (null != thresholdList && thresholdList.size() > 0) {
            timeThreshold = thresholdList.get(0).getValue();
        }
        return timeThreshold;
    }

    @Override
    public Map<String, BpsCity> queryAllCity() throws Exception {
        Map returnMap = new HashMap();
        List<BpsCity> list = iBpsCityMapper.queryAllCity();
        if(null != list){
            for(BpsCity city: list){
                returnMap.put(city.getCode(), city);
            }
        }
        return returnMap;
    }

    @Override
    public Map<String, BpsCompanyInquiryApply> queryAllCompanyApplyForOneInquiry(String serialNo) throws Exception {
        Map returnMap = new HashMap();
        List<BpsCompanyInquiryApply> applyList = iBpsCompanyInquiryApplyMapper.getInquiryInfoBySerialNo(serialNo);
        if(null != applyList){
            for(BpsCompanyInquiryApply apply: applyList){
                returnMap.put(apply.getCompanyCode(), apply);
            }
        }
        return returnMap;
    }

    /**
     *
     * @param cityCode
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, BpsCompanyCity> getCompanyCityMapByCityCode(String cityCode) throws Exception {
        Map<String, BpsCompanyCity> returnCompanyList = new HashedMap();

        if(StringUtils.isNotBlank(cityCode)){
            //获取该城市关联的估价公司
            List<BpsCompanyCity> companyCitylist = iBpsCompanyCityMapper.queryCompanyCityByCityCode(cityCode);
            if(companyCitylist != null){
                for(BpsCompanyCity companyCity : companyCitylist){
                    returnCompanyList.put(companyCity.getCompanyCode(), companyCity);
                }
            }
        }
        return  returnCompanyList;
    }
}

