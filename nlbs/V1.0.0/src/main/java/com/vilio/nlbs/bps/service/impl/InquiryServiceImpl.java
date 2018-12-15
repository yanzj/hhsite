package com.vilio.nlbs.bps.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.nlbs.bps.service.InquiryService;
import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.*;
import com.vilio.nlbs.commonMapper.pojo.*;
import com.vilio.nlbs.remote.service.RemoteBpsService;
import com.vilio.nlbs.todo.service.NlbsTODOService;
import com.vilio.nlbs.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/6/15/0015.
 */
@Service
public class InquiryServiceImpl implements InquiryService {

    @Resource
    RemoteBpsService remoteBpsService;

    @Resource
    NlbsInquiryApplyMapper nlbsInquiryApplyMapper;

    @Resource
    NlbsOperationHistoryMapper nlbsOperationHistoryMapper;

    @Resource
    NlbsDistributorMapper nlbsDistributorMapper;

    @Resource
    CommonService commonService;

    @Resource
    NlbsCityMapper nlbsCityMapper;

    @Resource
    NlbsPendingUserDistributorMapper nlbsPendingUserDistributorMapper;

    @Resource
    NlbsTODOService nlbsTODOService;

    /**
     * 公寓估价
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map apartmentInquiry(Map paramMap) throws Exception {

        //Step 1 解析入参
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
        bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
        //Step 1.1 整理参数存储本地库--NlbsInquiryApply、
        List<Map> paramList = (List<Map>) bodyMap.get(Fields.PARAM_COMPANY_PARAM_LIST);
        String userNo = (String) bodyMap.get(Fields.PARAM_USER_ID);
        String cityCode = (String) bodyMap.get(Fields.PARAM_CITY_CODE);
        String houseTypeCode = (String) bodyMap.get(Fields.PARAM_HOUSE_TYPE_CODE);
        String inquiryCode = CommonUtil.getCurrentTimeStr("NLBS-","");
        String address = "";
        NlbsInquiryApply nlbsInquiryApply = new NlbsInquiryApply();
        nlbsInquiryApply.setCode(inquiryCode);
        nlbsInquiryApply.setUserNo(userNo);
        nlbsInquiryApply.setCityCode(cityCode);
        nlbsInquiryApply.setHouseType(houseTypeCode);
        nlbsInquiryApply.setAutoPrice(Constants.INQUIRY_TYPE_AUTO_PRICE);
        nlbsInquiryApply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
        if (paramList != null) {
            for (Map map : paramList) {
                String companyCode = (String) map.get(Fields.PARAM_COMPANY_CODE);
                if(AppraisalCompanys.WORLD_UNION.getCode().equals(companyCode)){
                    String plotsName = (String) map.get(Fields.PARAM_PLOTS_NAME);
                    String unitName = (String) map.get(Fields.PARAM_UNIT_NAME);
                    String houseName = (String) map.get(Fields.PARAM_HOUSE_NAME);
                    String area = (String) map.get(Fields.PARAM_AREA);
                    address = (plotsName == null ? "" : plotsName) + (unitName == null ? "" : unitName) + (houseName == null ? "" : houseName);
                    nlbsInquiryApply.setAddress(address);
                    nlbsInquiryApply.setArea(new BigDecimal(area == null ? "0" : area));
                    bodyMap.put(Fields.PARAM_ADDRESS, address);//整理address给bps使用
                }
                if(AppraisalCompanys.SH_CHENGSHI.getCode().equals(companyCode)){
                    String unitName = (String) map.get(Fields.PARAM_UNIT_NAME);
                    String houseName = (String) map.get(Fields.PARAM_HOUSE_NAME);
                    String area = (String) map.get(Fields.PARAM_AREA);
                    address = (unitName == null ? "" : unitName) + (houseName == null ? "" : houseName);
                    nlbsInquiryApply.setAddress(address);
                    nlbsInquiryApply.setArea(new BigDecimal(area == null ? "0" : area));
                    bodyMap.put(Fields.PARAM_ADDRESS, address);//整理address给bps使用
                }
                break;//只取默认的第一个估价公司的地址
            }
        }
        nlbsInquiryApplyMapper.getInsert(nlbsInquiryApply);
        //Step 2 调用BPS，询价
        Map remoteParamMap = new HashMap();
        headMap.remove(Fields.PARAM_CLIENTTIMESTAMP);
        headMap.remove(Fields.PARAM_USER_NO);
        bodyMap.put(Fields.PARAM_SOURCE_SYSTEM, "nlbs");
        remoteParamMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        remoteParamMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        Map remoteReturnMap = remoteBpsService.callService(remoteParamMap);
        Map<String, Object> remoteHeadMap = new HashMap<String, Object>();
        Map<String, Object> remoteBodyMap = new HashMap<String, Object>();
        remoteHeadMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
        remoteBodyMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_BODY);
        String remoteReturnCode = (String) remoteHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
        //Step 3 调用BPS返回，更新本地库，保存操作历史，修改出参
        if(ReturnCode.SUCCESS_CODE.equals(remoteReturnCode)){
            //判断当前用户是不是超管或者风控等内部用户
            List<String> roleList = new ArrayList<String>();
            roleList.add(Constants.ROLE_SUPPER_MANAGER);
            roleList.add(Constants.ROLE_SYSTEM_MANAGER);
            roleList.add(Constants.ROLE_BUSINESS_MANAGER);
            boolean isAdmin = commonService.isAdministrator(userNo, roleList);
            if(!isAdmin) {
                NlbsDistributor nlbsDistributor = nlbsDistributorMapper.selectDistributorByUserNo(userNo);
                if (nlbsDistributor != null && Constants.DISTRIBUTOR_VILIO_CODE.equals(nlbsDistributor.getCode())) {
                    isAdmin = true;
                }
            }
            //整理BPS返回的关键参数
            String bpsCode = remoteBodyMap.get(Fields.PARAM_SERIAL_NO) == null ? "" : remoteBodyMap.get(Fields.PARAM_SERIAL_NO).toString();
            String status = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS) == null ? "" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS).toString();
            String price = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_PRICE) == null ? "" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_PRICE).toString();
            String priceTime = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_TIME) == null ? "" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_TIME).toString();

            //更新询价记录表
            nlbsInquiryApply.setBpsCode(bpsCode);
            nlbsInquiryApply.setStatus(status);
            nlbsInquiryApply.setPrice(new BigDecimal(price));
            nlbsInquiryApply.setPriceTime(DateUtil.parseDateTime3(priceTime));
            nlbsInquiryApplyMapper.getUpdate(nlbsInquiryApply);

            //插入询价操作历史表
            NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();
            nlbsOperationHistory.setOperater(userNo);
            nlbsOperationHistory.setSerialNo(bpsCode);
            nlbsOperationHistory.setSystemCode("bps");
            nlbsOperationHistory.setOperateType(Constants.OPERATION_SUBMIT_INQUIRY);
            nlbsOperationHistoryMapper.getInsert(nlbsOperationHistory);

            //如果返回的状态是待评估，则生成待办任务
            if(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION.equals(status)){
                nlbsTODOService.insertTodoTask(bpsCode, userNo, address);
            }

            List<Map> priceList = (List<Map>) remoteBodyMap.get(Fields.PARAM_COMPANY_PARAM_LIST);
            if(priceList != null){
                List<Map> priceWihtHandleList = new ArrayList<Map>();
                for(Map map : priceList){
                    String companyPriceStatus = (String) map.get(Fields.PARAM_STATUS);
                    //如果是人工的估价或者为风控或者管理员，则保留价格，否则对前端不显示单个估价公司的评估价格，只显示评估状态；
                    if(!isAdmin){
                        map.remove(Fields.PARAM_PRICE);//对前端不显示单个估价公司的评估价格，只显示评估状态；
                    }
                    map.put(Fields.PARAM_STATUS, BPSStatus.getNameByCode(companyPriceStatus));
                    priceWihtHandleList.add(map);
                }
                remoteBodyMap.put(Fields.PARAM_COMPANY_PARAM_LIST, priceWihtHandleList);
            }
            remoteBodyMap.put(Fields.PARAM_ASSESSMENT_STATUS, BPSStatus.getNameByCode(status));
        }

        return remoteReturnMap;
    }

    /**
     * 别墅估价
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map villaInquiry(Map paramMap) throws Exception {
        //Step 1 解析入参
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
        bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
        //Step 1.1 整理参数存储本地库--NlbsInquiryApply、
        List<Map> paramList = (List<Map>) bodyMap.get(Fields.PARAM_COMPANY_PARAM_LIST);
        String userNo = (String) bodyMap.get(Fields.PARAM_USER_ID);
        String cityCode = (String) bodyMap.get(Fields.PARAM_CITY_CODE);
        String houseTypeCode = (String) bodyMap.get(Fields.PARAM_HOUSE_TYPE_CODE);
        String inquiryCode = CommonUtil.getCurrentTimeStr("NLBS-","");
        String address = "";
        NlbsInquiryApply nlbsInquiryApply = new NlbsInquiryApply();
        nlbsInquiryApply.setCode(inquiryCode);
        nlbsInquiryApply.setUserNo(userNo);
        nlbsInquiryApply.setCityCode(cityCode);
        nlbsInquiryApply.setHouseType(houseTypeCode);
        nlbsInquiryApply.setAutoPrice(Constants.INQUIRY_TYPE_AUTO_PRICE);
        nlbsInquiryApply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
        if (paramList != null) {
            for (Map map : paramList) {
                String companyCode = (String) map.get(Fields.PARAM_COMPANY_CODE);
                if(AppraisalCompanys.WORLD_UNION.getCode().equals(companyCode)){
                    String paramAddress = (String) map.get(Fields.PARAM_ADDRESS);
                    String area = (String) map.get(Fields.PARAM_AREA);
                    address = paramAddress == null ? "" : paramAddress;
                    nlbsInquiryApply.setAddress(address);
                    nlbsInquiryApply.setArea(new BigDecimal(area == null ? "0" : area));
                }

            }
        }
        nlbsInquiryApplyMapper.getInsert(nlbsInquiryApply);
        //Step 2 调用BPS，询价
        Map remoteParamMap = new HashMap();
        headMap.remove(Fields.PARAM_CLIENTTIMESTAMP);
        headMap.remove(Fields.PARAM_USER_NO);
        bodyMap.put(Fields.PARAM_SOURCE_SYSTEM, "nlbs");
        remoteParamMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        remoteParamMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        Map remoteReturnMap = remoteBpsService.callService(remoteParamMap);
        Map<String, Object> remoteHeadMap = new HashMap<String, Object>();
        Map<String, Object> remoteBodyMap = new HashMap<String, Object>();
        remoteHeadMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
        remoteBodyMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_BODY);
        String remoteReturnCode = (String) remoteHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
        //Step 3 调用BPS返回，更新本地库，保存操作历史，修改出参
        if(ReturnCode.SUCCESS_CODE.equals(remoteReturnCode)){

            //判断当前用户是不是超管或者风控等内部用户
            List<String> roleList = new ArrayList<String>();
            roleList.add(Constants.ROLE_SUPPER_MANAGER);
            roleList.add(Constants.ROLE_SYSTEM_MANAGER);
            roleList.add(Constants.ROLE_BUSINESS_MANAGER);
            boolean isAdmin = commonService.isAdministrator(userNo, roleList);
            if(!isAdmin) {
                NlbsDistributor nlbsDistributor = nlbsDistributorMapper.selectDistributorByUserNo(userNo);
                if (nlbsDistributor != null && Constants.DISTRIBUTOR_VILIO_CODE.equals(nlbsDistributor.getCode())) {
                    isAdmin = true;
                }
            }

            String bpsCode = remoteBodyMap.get(Fields.PARAM_SERIAL_NO) == null ? "" : remoteBodyMap.get(Fields.PARAM_SERIAL_NO).toString();
            String status = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS) == null ? "" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS).toString();
            String price = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_PRICE) == null ? "0" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_PRICE).toString();
            String priceTime = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_TIME) == null ? "" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_TIME).toString();

            nlbsInquiryApply.setBpsCode(bpsCode);
            nlbsInquiryApply.setStatus(status);
            nlbsInquiryApply.setPrice(new BigDecimal(price));
            nlbsInquiryApply.setPriceTime(DateUtil.parseDateTime3(priceTime));
            nlbsInquiryApplyMapper.getUpdate(nlbsInquiryApply);

            NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();
            nlbsOperationHistory.setOperater(userNo);
            nlbsOperationHistory.setSerialNo(bpsCode);
            nlbsOperationHistory.setSystemCode("bps");
            nlbsOperationHistory.setOperateType(Constants.OPERATION_SUBMIT_INQUIRY);
            nlbsOperationHistoryMapper.getInsert(nlbsOperationHistory);

            //如果返回的状态是待评估，则生成待办任务
            if(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION.equals(status)){
                nlbsTODOService.insertTodoTask(bpsCode, userNo, address);
            }
            List<Map> priceList = (List<Map>) remoteBodyMap.get(Fields.PARAM_COMPANY_PARAM_LIST);
            if(priceList != null){
                List<Map> priceWihtHandleList = new ArrayList<Map>();
                for(Map map : priceList){
                    String companyPriceStatus = (String) map.get(Fields.PARAM_STATUS);
                    //如果是人工的估价或者为风控或者管理员，则保留价格，否则对前端不显示单个估价公司的评估价格，只显示评估状态；
                    if(!isAdmin){
                        map.remove(Fields.PARAM_PRICE);//对前端不显示单个估价公司的评估价格，只显示评估状态；
                    }
                    map.put(Fields.PARAM_STATUS, BPSStatus.getNameByCode(companyPriceStatus));
                    priceWihtHandleList.add(map);
                }
                remoteBodyMap.put(Fields.PARAM_COMPANY_PARAM_LIST, priceWihtHandleList);
            }
            remoteBodyMap.put(Fields.PARAM_ASSESSMENT_STATUS, BPSStatus.getNameByCode(status));
        }

        return remoteReturnMap;
    }

    /**
     * 人工询价
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map manualInquiry(Map paramMap) throws Exception {
        //Step 1 解析入参
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
        bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
        //Step 1.1 整理参数存储本地库--NlbsInquiryApply、
        String userNo = (String) bodyMap.get(Fields.PARAM_USER_ID);
        String cityCode = (String) bodyMap.get(Fields.PARAM_CITY_CODE);
        String address = (String) bodyMap.get(Fields.PARAM_ADDRESS);
        String inquiryCode = CommonUtil.getCurrentTimeStr("NLBS-","");
        NlbsInquiryApply nlbsInquiryApply = new NlbsInquiryApply();
        nlbsInquiryApply.setCode(inquiryCode);
        nlbsInquiryApply.setUserNo(userNo);
        nlbsInquiryApply.setCityCode(cityCode);
        nlbsInquiryApply.setAutoPrice(Constants.INQUIRY_TYPE_MANNAL_PRICE);
        nlbsInquiryApply.setAddress(address);
        nlbsInquiryApply.setStatus(Constants.BPS_ORDER_STATUS_EVALUATING);
        nlbsInquiryApplyMapper.getInsert(nlbsInquiryApply);
        //Step 2 调用BPS，询价
        Map remoteParamMap = new HashMap();
        headMap.remove(Fields.PARAM_CLIENTTIMESTAMP);
        headMap.remove(Fields.PARAM_USER_NO);
        bodyMap.put(Fields.PARAM_SOURCE_SYSTEM, "nlbs");
        remoteParamMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        remoteParamMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        Map remoteReturnMap = remoteBpsService.callService(remoteParamMap);
        Map<String, Object> remoteHeadMap = new HashMap<String, Object>();
        Map<String, Object> remoteBodyMap = new HashMap<String, Object>();
        remoteHeadMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
        remoteBodyMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_BODY);
        String remoteReturnCode = (String) remoteHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
        //Step 3 调用BPS返回，更新本地库，保存操作历史，修改出参
        if(ReturnCode.SUCCESS_CODE.equals(remoteReturnCode)){
            //整理BPS返回的关键参数
            String bpsCode = remoteBodyMap.get(Fields.PARAM_SERIAL_NO) == null ? "0" : remoteBodyMap.get(Fields.PARAM_SERIAL_NO).toString();
            String status = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS) == null ? "0" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS).toString();
            String price = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_PRICE) == null ? "0" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_PRICE).toString();
            String priceTime = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_TIME) == null ? "0" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_TIME).toString();
            //更新询价记录
            nlbsInquiryApply.setBpsCode(bpsCode);
            nlbsInquiryApply.setStatus(status);
            nlbsInquiryApply.setPrice(new BigDecimal(price));
            nlbsInquiryApply.setPriceTime(DateUtil.parseDateTime3(priceTime));
            nlbsInquiryApplyMapper.getUpdate(nlbsInquiryApply);
            //插入询价历史操作记录
            NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();
            nlbsOperationHistory.setOperater(userNo);
            nlbsOperationHistory.setSerialNo(bpsCode);
            nlbsOperationHistory.setSystemCode("bps");
            nlbsOperationHistory.setOperateType(Constants.OPERATION_SUBMIT_INQUIRY);
            nlbsOperationHistoryMapper.getInsert(nlbsOperationHistory);

            //如果返回的状态是待评估，则生成待办任务
            if(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION.equals(status)){
                nlbsTODOService.insertTodoTask(bpsCode, userNo, address);
            }

            remoteBodyMap.put(Fields.PARAM_ASSESSMENT_STATUS, BPSStatus.getNameByCode(status));
        }

        return remoteReturnMap;
    }

    /**
     * 查询询价记录列表
     * @param paramMap--只接受bodyMap即可
     * @return
     * @throws Exception
     */
    @Override
    public Map queryInquiryList(Map paramMap) throws Exception {

        Map returnMap = new HashMap();
        List<Map> inquiryApplyList = new ArrayList<Map>();
        String userNo = null != paramMap.get(Fields.PARAM_USER_NO) ? paramMap.get(Fields.PARAM_USER_NO).toString() : "";
        Integer pageNo = null != paramMap.get(Fields.PARAM_PAGE_NO) ? new Integer(paramMap.get(Fields.PARAM_PAGE_NO).toString()) : 1;
        Integer pageSize = null != paramMap.get(Fields.PARAM_PAGE_SIZE) ? new Integer(paramMap.get(Fields.PARAM_PAGE_SIZE).toString()) : 10;

        //Step 1判断当前用户是不是超管
        List<String> roleList = new ArrayList<String>();
        roleList.add(Constants.ROLE_SUPPER_MANAGER);
        roleList.add(Constants.ROLE_SYSTEM_MANAGER);
        roleList.add(Constants.ROLE_BUSINESS_MANAGER);
        boolean isAdmin = commonService.isAdministrator(userNo, roleList);
        //Step2 获取分页查询结果
        if(isAdmin){
            //Step 2.1如果是管理员，则所有询价记录可见
            PageHelper.startPage(pageNo, pageSize);
            inquiryApplyList = nlbsInquiryApplyMapper.getAllList(paramMap);
        } else {
            //Step 2.2如果不是管理员，则按需查找
            //Step 2.2.1查找当前用户所有下属用户
            List<String> userNoList = new ArrayList<String>();
            userNoList.add("");
            List<NlbsUser> nlbsUserList = commonService.selectChildrenListUser(userNo);
            if(nlbsUserList != null){
                for(NlbsUser nu : nlbsUserList){
                    userNoList.add(nu.getUserNo());
                }
            }
            paramMap.put(Fields.PARAM_USER_LIST, userNoList);
            //Step 2.2.2查询列表
            PageHelper.startPage(pageNo, pageSize);
            inquiryApplyList = nlbsInquiryApplyMapper.getInquiryApplyList(paramMap);
        }

        PageInfo pageInfo = new PageInfo(inquiryApplyList);

        //Step 3 查看当前用户是否具备录入询价结果权限
        NlbsPendingUserDistributor nlbsPendingUserDistributor = new NlbsPendingUserDistributor();
        nlbsPendingUserDistributor.setUserNo(userNo);
        List<NlbsPendingUserDistributor> distributorList = nlbsPendingUserDistributorMapper.getList(nlbsPendingUserDistributor);
        boolean displayInquiry = true;
        if (distributorList == null || distributorList.size() == 0) {
            displayInquiry = false;
        }
        List<Map> returnInquiryApplyList = new ArrayList<Map>();
        if (inquiryApplyList != null) {
            for (Map map : inquiryApplyList) {
                //转换状态码，更改price类型。
                String price = "";
                BigDecimal priceBigD = (BigDecimal) map.get(Fields.PARAM_PRICE);
                if(priceBigD.compareTo(BigDecimal.ZERO) == 1){
                    price = priceBigD.toString();
                }
                String status = map.get(Fields.PARAM_STATUS) != null ? (map.get(Fields.PARAM_STATUS).toString()) : "";
                String pendingUserNo = map.get(Fields.PARAM_PENDING_USER_NO) != null ? (map.get(Fields.PARAM_PENDING_USER_NO).toString()) : "";
                map.put(Fields.PARAM_STATUS, BPSStatus.getNameByCode(status));
                if(BPSStatus.ORDER_STATUS_EVALUATED.getCode().equals(status)){
                    map.put(Fields.PARAM_PRICE, price);
                } else {
                    map.put(Fields.PARAM_PRICE, "");//如果不是以评估状态，价格显示-
                }
                if(displayInquiry && ("".equals(pendingUserNo) || userNo.equals(pendingUserNo)) && BPSStatus.ORDER_STATUS_PENDING_EVALUATION.getCode().equals(status)){
                    map.put(Fields.PARAM_DISPLAY_INQUIRY, "Y");
                } else {
                    map.put(Fields.PARAM_DISPLAY_INQUIRY, "N");
                }
                returnInquiryApplyList.add(map);
            }
        }

        returnMap.put(Fields.PARAM_INQUIRY_APPLY_LIST,returnInquiryApplyList);
        returnMap.put(Fields.PARAM_PAGES,pageInfo.getPages());
        returnMap.put(Fields.PARAM_TOTAL,pageInfo.getTotal());
        returnMap.put(Fields.PARAM_CURRENT_PAGE,pageInfo.getPageNum());
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取询价记录列表成功！");

        return returnMap;
    }

    /**
     * 查询询价记录详情
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map queryInquiryInfo(Map paramMap) throws Exception {
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
        bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);

        String userNo = null != headMap.get(Fields.PARAM_USER_NO) ? headMap.get(Fields.PARAM_USER_NO).toString() : "";
        Map remoteParamMap = new HashMap();
        headMap.remove(Fields.PARAM_CLIENTTIMESTAMP);
        headMap.remove(Fields.PARAM_USER_NO);
        bodyMap.put(Fields.PARAM_SOURCE_SYSTEM, "nlbs");
        remoteParamMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        remoteParamMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        Map remoteReturnMap = remoteBpsService.callService(remoteParamMap);
        Map<String, Object> remoteHeadMap = new HashMap<String, Object>();
        Map<String, Object> remoteBodyMap = new HashMap<String, Object>();
        remoteHeadMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
        remoteBodyMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_BODY);
        String remoteReturnCode = (String) remoteHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
        //调用BPS返回，更新本地库，保存操作历史，修改出参
        if(ReturnCode.SUCCESS_CODE.equals(remoteReturnCode)){
            //判断当前用户是不是超管或者风控等内部用户
            List<String> roleList = new ArrayList<String>();
            roleList.add(Constants.ROLE_SUPPER_MANAGER);
            roleList.add(Constants.ROLE_SYSTEM_MANAGER);
            roleList.add(Constants.ROLE_BUSINESS_MANAGER);
            boolean isAdmin = commonService.isAdministrator(userNo, roleList);
            if(!isAdmin) {
                NlbsDistributor nlbsDistributor = nlbsDistributorMapper.selectDistributorByUserNo(userNo);
                if (nlbsDistributor != null && Constants.DISTRIBUTOR_VILIO_CODE.equals(nlbsDistributor.getCode())) {
                    isAdmin = true;
                }
            }


            String status = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS) == null ? "" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS).toString();
            List<Map> priceList = (List<Map>) remoteBodyMap.get(Fields.PARAM_COMPANY_PARAM_LIST);
            if(priceList != null){
                for(Map map : priceList){
                    String companyPriceStatus = (String) map.get(Fields.PARAM_STATUS);
                    String autoPrice = (String) map.get(Fields.PARAM_AUTO_PRICE);
                    //如果是人工的估价或者为风控或者管理员，则保留价格，否则对前端不显示单个估价公司的评估价格，只显示评估状态；
                    if(!Constants.INQUIRY_TYPE_MANNAL_PRICE.equals(autoPrice) && !isAdmin){
                        map.remove(Fields.PARAM_PRICE);
                    }
                    map.put(Fields.PARAM_STATUS, BPSStatus.getNameByCode(companyPriceStatus));

                }
            }
            remoteBodyMap.put(Fields.PARAM_ASSESSMENT_STATUS, BPSStatus.getNameByCode(status));
        }

        return remoteReturnMap;
    }

    /**
     * 查询操作历史列表--只接受bodyMap
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map queryOperateHistory(Map paramMap) throws Exception {
        Map returnMap = new HashMap();

        String serialNo = (String) paramMap.get(Fields.PARAM_SERIAL_NO);
        List<Map<String, Object>> historyList = nlbsOperationHistoryMapper.getListBySerialNo(serialNo);
        if(historyList == null){
            historyList = new ArrayList<Map<String, Object>>();
        }
        returnMap.put(Fields.PARAM_OPERATION_HISTORY_LIST, historyList);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功查询询价历史操作列表！");
        return returnMap;
    }

    /**
     * 更新询价结果，包括状态，价格，时间等 -- 只传bodyMap即可
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map updateInquiryResult(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_SERIAL_NO);
        requiredFieldList.add(Fields.PARAM_ASSESSMENT_STATUS);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        Map returnMap = new HashMap();
        String bpsCode = paramMap.get(Fields.PARAM_SERIAL_NO) == null ? "" : paramMap.get(Fields.PARAM_SERIAL_NO).toString();
        String status = paramMap.get(Fields.PARAM_ASSESSMENT_STATUS) == null ? "" : paramMap.get(Fields.PARAM_ASSESSMENT_STATUS).toString();
        String price = paramMap.get(Fields.PARAM_ASSESSMENT_PRICE) == null ? "-1" : paramMap.get(Fields.PARAM_ASSESSMENT_PRICE).toString();
        String priceTime = paramMap.get(Fields.PARAM_ASSESSMENT_TIME) == null ? "" : paramMap.get(Fields.PARAM_ASSESSMENT_TIME).toString();
        String autoPrice = paramMap.get(Fields.PARAM_AUTO_PRICE) == null ? "" : paramMap.get(Fields.PARAM_AUTO_PRICE).toString();

        NlbsInquiryApply nlbsInquiryApply = new NlbsInquiryApply();
        nlbsInquiryApply.setBpsCode(bpsCode);
        nlbsInquiryApply.setStatus(status);
        nlbsInquiryApply.setPrice(new BigDecimal(price));
        nlbsInquiryApply.setPriceTime(DateUtil.parseDateTime3(priceTime));
        nlbsInquiryApply.setAutoPrice(autoPrice);
        nlbsInquiryApplyMapper.getUpdateByBPSCode(nlbsInquiryApply);

        //如果返回的状态是待评估，则生成待办任务
        if(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION.equals(status) && StringUtils.isNotBlank(bpsCode)){
            nlbsInquiryApply = nlbsInquiryApplyMapper.getBeanBySerialNo(bpsCode);
            nlbsTODOService.insertTodoTask(bpsCode, nlbsInquiryApply.getUserNo(), nlbsInquiryApply.getAddress());
        }


        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功查询询价历史操作列表！");
        return returnMap;
    }

    /**
     * 录入评估价---提交
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map inputInquiryPrice(Map paramMap) throws Exception {
        //Step 1 解析入参
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
        bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
        String userNo = (String) bodyMap.get(Fields.PARAM_USER_ID);
        String serialNo = (String) bodyMap.get(Fields.PARAM_SERIAL_NO);
        NlbsInquiryApply nlbsInquiryApply = nlbsInquiryApplyMapper.getBeanBySerialNo(serialNo);
        String pendingUserNo = nlbsInquiryApply == null ? null : nlbsInquiryApply.getPendingUserNo();

        if (StringUtils.isNotBlank(pendingUserNo) && !(pendingUserNo.equals(userNo))) {
            NlbsUser nlbsUser = commonService.queryNlbsUserByUserNoIgnoreStatus(pendingUserNo);
            throw new HHBizException(ReturnCode.DUPLICATE_INPUT_PRICE, "该笔查询已由用户" + nlbsUser.getFullName() + "评估。");
        }
        //Step 2 调用BPS，询价
        Map remoteParamMap = new HashMap();
        headMap.remove(Fields.PARAM_CLIENTTIMESTAMP);
        headMap.remove(Fields.PARAM_USER_NO);
        bodyMap.put(Fields.PARAM_SOURCE_SYSTEM, "nlbs");
        remoteParamMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        remoteParamMap.put(Fields.PARAM_MESSAGE_BODY, bodyMap);
        Map remoteReturnMap = remoteBpsService.callService(remoteParamMap);
        Map<String, Object> remoteHeadMap = new HashMap<String, Object>();
        Map<String, Object> remoteBodyMap = new HashMap<String, Object>();
        remoteHeadMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_HEAD);
        remoteBodyMap = (Map<String, Object>) remoteReturnMap.get(Fields.PARAM_MESSAGE_BODY);
        String remoteReturnCode = (String) remoteHeadMap.get(Fields.PARAM_MESSAGE_ERR_CODE);
        //Step 3 调用BPS返回，更新本地库，保存操作历史，修改出参
        if(ReturnCode.SUCCESS_CODE.equals(remoteReturnCode)){
            //判断当前用户是不是超管或者风控等内部用户
            List<String> roleList = new ArrayList<String>();
            roleList.add(Constants.ROLE_SUPPER_MANAGER);
            roleList.add(Constants.ROLE_SYSTEM_MANAGER);
            roleList.add(Constants.ROLE_BUSINESS_MANAGER);
            boolean isAdmin = commonService.isAdministrator(userNo, roleList);
            if(!isAdmin) {
                NlbsDistributor nlbsDistributor = nlbsDistributorMapper.selectDistributorByUserNo(userNo);
                if (nlbsDistributor != null && Constants.DISTRIBUTOR_VILIO_CODE.equals(nlbsDistributor.getCode())) {
                    isAdmin = true;
                }
            }
            //整理BPS返回的关键参数
            String bpsCode = remoteBodyMap.get(Fields.PARAM_SERIAL_NO) == null ? "" : remoteBodyMap.get(Fields.PARAM_SERIAL_NO).toString();
            String status = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS) == null ? "" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_STATUS).toString();
            String price = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_PRICE) == null ? "-1" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_PRICE).toString();
            String autoPrice = remoteBodyMap.get(Fields.PARAM_AUTO_PRICE) == null ? "" : remoteBodyMap.get(Fields.PARAM_AUTO_PRICE).toString();
            String priceTime = remoteBodyMap.get(Fields.PARAM_ASSESSMENT_TIME) == null ? "" : remoteBodyMap.get(Fields.PARAM_ASSESSMENT_TIME).toString();

            //更新询价记录表
            nlbsInquiryApply = new NlbsInquiryApply();
            nlbsInquiryApply.setBpsCode(bpsCode);
            nlbsInquiryApply.setStatus(status);
            nlbsInquiryApply.setStatus(status);
            nlbsInquiryApply.setPrice(new BigDecimal(price));
            nlbsInquiryApply.setAutoPrice(autoPrice);
            nlbsInquiryApply.setPriceTime(DateUtil.parseDateTime3(priceTime));
            nlbsInquiryApplyMapper.getUpdateByBPSCode(nlbsInquiryApply);

            //插入询价操作历史表
            NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();
            nlbsOperationHistory.setOperater(userNo);
            nlbsOperationHistory.setSerialNo(bpsCode);
            nlbsOperationHistory.setSystemCode("bps");
            nlbsOperationHistory.setOperateType(Constants.OPERATION_INPUT_INQUIRY_PRICE);
            nlbsOperationHistoryMapper.getInsert(nlbsOperationHistory);

            //如果返回的状态是待评估，则生成待办任务
            if(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION.equals(status)){
                nlbsTODOService.insertTodoTask(nlbsInquiryApply.getBpsCode(), nlbsInquiryApply.getUserNo(), nlbsInquiryApply.getAddress());
            }
            //如果返回的状态为已评估，则可以删除自己的待办任务
            if(Constants.BPS_ORDER_STATUS_PENDING_EVALUATION.equals(status)){
                nlbsTODOService.deleteTask(nlbsInquiryApply.getBpsCode(), nlbsInquiryApply.getUserNo(), false);
            }

            List<Map> priceList = (List<Map>) remoteBodyMap.get(Fields.PARAM_COMPANY_PARAM_LIST);
            if(priceList != null){
                List<Map> priceWihtHandleList = new ArrayList<Map>();
                for(Map map : priceList){
                    String companyPriceStatus = (String) map.get(Fields.PARAM_STATUS);
                    //如果是人工的估价或者为风控或者管理员，则保留价格，否则对前端不显示单个估价公司的评估价格，只显示评估状态；
                    if(!Constants.INQUIRY_TYPE_MANNAL_PRICE.equals(autoPrice) && !isAdmin){
                        map.remove(Fields.PARAM_PRICE);//对前端不显示单个估价公司的评估价格，只显示评估状态；
                    }
                    map.put(Fields.PARAM_STATUS, BPSStatus.getNameByCode(companyPriceStatus));
                    priceWihtHandleList.add(map);
                }
                remoteBodyMap.put(Fields.PARAM_COMPANY_PARAM_LIST, priceWihtHandleList);
            }
            remoteBodyMap.put(Fields.PARAM_ASSESSMENT_STATUS, BPSStatus.getNameByCode(status));
        }

        return remoteReturnMap;
    }

    /**
     * 认领任务
     * @param paramMap ---只传bodyMap
     * @return
     * @throws Exception
     */
    @Override
    public Map claimInquiryTask(Map paramMap) throws Exception {
        //Step 1 入参检查
        List<String> requiredFieldList = new ArrayList<String>();
        requiredFieldList.add(Fields.PARAM_SERIAL_NO);
        requiredFieldList.add(Fields.PARAM_USER_NO);
        CommonUtil.checkRequiredFields(paramMap, requiredFieldList);

        Map returnMap = new HashMap();
        String bpsCode = (String) paramMap.get(Fields.PARAM_SERIAL_NO);
        String userNo = (String) paramMap.get(Fields.PARAM_USER_NO);

        NlbsInquiryApply nlbsInquiryApply = nlbsInquiryApplyMapper.getBeanBySerialNo(bpsCode);
        if(nlbsInquiryApply != null){
            String pendingUserNo = nlbsInquiryApply.getPendingUserNo();
            if(StringUtils.isBlank(pendingUserNo)){
                nlbsInquiryApply.setPendingUserNo(userNo);
                nlbsInquiryApplyMapper.getUpdateForClaim(nlbsInquiryApply);
                nlbsTODOService.deleteTask(nlbsInquiryApply.getBpsCode(), nlbsInquiryApply.getUserNo(), true);
            }
            // 极端情况下会出现以下情况（决定在列表页是否允许点进详情）
            if(StringUtils.isNotBlank(pendingUserNo) && !userNo.equals(pendingUserNo)){
                //查询究竟是谁先领了任务
                NlbsUser nlbsUser = commonService.queryNlbsUserByUserNoIgnoreStatus(pendingUserNo);
                throw new HHBizException(ReturnCode.DUPLICATE_INPUT_PRICE, "该笔查询已由用户" + nlbsUser.getFullName() + "评估。");
            }

        } else {
            throw new HHBizException(ReturnCode.INQUIRY_NO_EXIST, "该笔估价单不存在");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "认领成功！");
        return returnMap;
    }

}
