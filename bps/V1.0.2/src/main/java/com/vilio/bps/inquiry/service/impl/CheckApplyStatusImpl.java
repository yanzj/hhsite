package com.vilio.bps.inquiry.service.impl;

import com.vilio.bps.common.service.CommonService;
import com.vilio.bps.commonMapper.dao.IBpsCompanyCityMapper;
import com.vilio.bps.commonMapper.dao.IBpsCompanyInquiryApplyMapper;
import com.vilio.bps.commonMapper.dao.IBpsUserInquiryMapper;
import com.vilio.bps.commonMapper.pojo.BpsCompanyCity;
import com.vilio.bps.commonMapper.pojo.BpsCompanyInquiryApply;
import com.vilio.bps.commonMapper.pojo.BpsConfig;
import com.vilio.bps.commonMapper.pojo.BpsUserInquiry;
import com.vilio.bps.inquiry.service.CheckApplyStatusService;
import com.vilio.bps.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell on 2017/6/17.
 */
@Service("checkApplyStatusService")
public class CheckApplyStatusImpl implements CheckApplyStatusService {
    public static final String DATE_TIME_PATTERN3 = "yyyy-MM-dd HH:mm:ss";
    Logger logger = Logger.getLogger(CheckApplyStatusImpl.class);
    @Resource
    IBpsUserInquiryMapper iBpsUserInquiryMapper;
    @Resource
    IBpsCompanyInquiryApplyMapper iBpsCompanyInquiryApplyMapper;
    @Resource
    CommonService commonService;
    @Resource
    ConfigInfo configInfo;
    @Resource
    IBpsCompanyCityMapper iBpsCompanyCityMapper;


    public void execute() throws Exception {
        //进件系统入参
        String url = configInfo.getNlbsUrl();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        headMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE.getReturnCode());
        headMap.put(Fields.PARAM_FUNCTION_NO, "HH000037");

        //获取需要置为失效的询价
        List<BpsUserInquiry> list = iBpsUserInquiryMapper.queryNeedOutDateInquiry(new Date());
        if(null != list && list.size()  > 0){
            try{
                for(BpsUserInquiry in: list){
                    Map map = new HashedMap();
                    map.put(Fields.PARAM_SERIAL_NO, in.getSerialNo());
                    map.put(Fields.PARAM_STATUS, Constants.BPS_ORDER_STATUS_EVALUATION_INVALID);
                    iBpsUserInquiryMapper.updateInquiryBySerialNo(map);

                    Map paramMap = new HashMap();
                    paramMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
                    paramMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);

                    //同步nlbs
                    bodyMap.put(Fields.PARAM_SERIAL_NO, in.getSerialNo());
                    bodyMap.put(Fields.PARAM_ASSESSMENT_STATUS, Constants.BPS_ORDER_STATUS_EVALUATION_INVALID);
                    JSONObject json = JSONObject.fromObject(paramMap);
                    logger.info("失效订单请求nlbs_HH000037同步状态,入参:" + json);
                    JSONObject result = HttpRequestUtils.httpPost(url,json , new HashMap<String, String>());
                    logger.info("失效订单请求nlbs_HH000037同步状态,返回:" + result);

                }
            }catch (Exception e){
                logger.error("处理完失效的单子异常:" + e);
            }
            logger.info("处理完失效的单子。。。:" + (null == list ? "0" :list.size()));
        }


        //获取评估中订单
        List<BpsUserInquiry>  inquiryList = iBpsUserInquiryMapper.queryInAssessInquiry();
        logger.info("处理评估中的单子:" + (null == inquiryList ? "0" :inquiryList.size()));
        if(null != inquiryList && inquiryList.size() > 0){
            //获取当前系统配置
            Map<String, BpsConfig> configMap = commonService.getValidSystemConfig();
            //是否要求都必须有结果
            boolean mustAllHavePrice = false;
            if(Constants.CONFIG_NEED_ALL_RESULT_VALUE_YES.equals(configMap.get(Constants.CONFIG_NEED_ALL_RESULT).getConfigValue())){
                mustAllHavePrice = true;
            }

            //查看流水是否都处理完成
            for(BpsUserInquiry inquiry : inquiryList){
                String assessPrice = null;//评估价格
                String assessStatus = Constants.BPS_ORDER_STATUS_EVALUATING;
                Date assessTime = null;//保存至数据库的时间
                String strAssessTime = null;
                String serialNo = inquiry.getSerialNo();
                //获取该城市的关联估价公司
                List<BpsCompanyCity> companyCitylist = iBpsCompanyCityMapper.queryCompanyCityByCityCode(inquiry.getCityCode());
                //获取当前询价各公司询价记录
                List<BpsCompanyInquiryApply> applyList = iBpsCompanyInquiryApplyMapper.getInquiryInfoBySerialNo(serialNo);

                //判断是否有待评估
                boolean waitAssess = false;
                //判断是否有评估中的
                boolean evaluating = false;

                //有价格的公司价格列表
                List<BigDecimal> priceList = new ArrayList();
                for(BpsCompanyInquiryApply apply:applyList){
                    if(Constants.BPS_ORDER_STATUS_EVALUATED.equals(apply.getStatus()) && null != apply.getPrice() && (apply.getPrice()).doubleValue() > 0){
                        priceList.add(apply.getPrice());
                    }else if(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION.equals(apply.getStatus()) || Constants.BPS_ORDER_STATUS_FALE_EVALUATED.equals(apply.getStatus())){
                        waitAssess = true;
                    }else if (companyCitylist.size() > applyList.size()){
                        //转待评估
                        assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
                    }else if(Constants.BPS_ORDER_STATUS_EVALUATING.equals(apply.getStatus())){
                        evaluating = true;
                    }
                }
                if(mustAllHavePrice){
                    if(waitAssess){
                        //转待评估
                        assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
                    }else if (priceList.size() < 1 || priceList.size() < companyCitylist.size()){
                        continue;
                    }
                }else{
                    if(evaluating){
                        //有评估中订单，等待
                        continue;
                    }else if (priceList.size() < 1){
                        //转待评估
                        assessStatus = Constants.BPS_ORDER_STATUS_PENDING_EVALUATION;
                    }
                }

                if(Constants.BPS_ORDER_STATUS_EVALUATING.equals(assessStatus)){
                    //计算价格
                    Map priceMap = commonService.getFinalPrice(priceList);
                    assessPrice = (String) priceMap.get(Fields.PARAM_ASSESSMENT_PRICE);
                    assessStatus = Constants.BPS_ORDER_STATUS_EVALUATED;
                    assessTime = new Date();
                    strAssessTime = DateUtil.formatDateTime(assessTime, DATE_TIME_PATTERN3);
                }

                //更新询价流水
                Map param = new HashMap();
                param.put(Fields.PARAM_SERIAL_NO, serialNo);
                param.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
                param.put(Fields.PARAM_STATUS, assessStatus);
                param.put(Fields.PARAM_ASSESSMENT_TIME, assessTime);
                iBpsUserInquiryMapper.saveInquiryPriceAndStatus(param);
                //同步至进见系统
                try{
                    bodyMap.put(Fields.PARAM_SERIAL_NO, serialNo);
                    bodyMap.put(Fields.PARAM_ASSESSMENT_STATUS, assessStatus);
                    bodyMap.put(Fields.PARAM_ASSESSMENT_PRICE, assessPrice);
                    bodyMap.put(Fields.PARAM_ASSESSMENT_TIME, strAssessTime);

                    Map paramMap = new HashMap();
                    paramMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
                    paramMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);

                    JSONObject json = JSONObject.fromObject(paramMap);
                    JSONObject result = HttpRequestUtils.httpPost(url,json , new HashMap<String, String>());
                    logger.info("同步进件系统询价流水nlbs_HH000037,返回:" + result);
                }catch (Exception e){
                    logger.error("访问进件系统异常:" + e);
                }


            }
        }
    }
}
