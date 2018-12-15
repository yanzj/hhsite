package com.vilio.nlbs.apply.service.impl;
import com.vilio.nlbs.apply.service.ApplyService;
import com.vilio.nlbs.bms.service.BmsCommonService;
import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.*;
import com.vilio.nlbs.commonMapper.pojo.*;
import com.vilio.nlbs.dynamicdatasource.CustomerContextHolder;
import com.vilio.nlbs.util.*;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 进件申请相关的接口实现
 */
@Service("applyService")
public class ApplyServiceImpl implements ApplyService {
    private static Logger logger = Logger.getLogger(ApplyServiceImpl.class);

    @Resource
    NlbsLoanPeriodMapper nlbsLoanPeriodMapper;

    @Resource
    NlbsMortgageTypeMapper nlbsMortgageTypeMapper;

    @Resource
    NlbsCreditTypeMapper nlbsCreditTypeMapper;

    @Resource
    CommonService commonService;

    @Resource
    NlbsProductMapper nlbsProductMapper;

    @Resource
    NlbsApplyLoanStatusMapper nlbsApplyLoanStatusMapper;

    @Resource
    BmsCommonService bmsCommonService;

    @Resource
    ConfigInfo configInfo;

    @Resource
    NlbsRecordersDistributorMapper nlbsRecordersDistributorMapper;

    @Resource
    NlbsLoginInfoMapper nlbsLoginInfoMapper;

    private final int MANAGER_FLAG = 1;
    private final int AGENT_FLAG = 2;
    private final int RECORD_FLAG = 3;

    /**
     * 获取进件初始化信息
     * 进件公司列表、放款方式列表、借款期限列表、角色
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map initApplyInfo(Map paramMap) throws Exception {
        String userNo = (String) paramMap.get(Fields.PARAM_USER_NO);

        Map<String, Object> resultMap = new HashMap();
        List<Map> returnDistributorList = new ArrayList<Map>();
        List<Map> resultLoanPeriodList = new ArrayList<Map>();
        List<Map> resultCreditTypeList = new ArrayList<Map>();
        List<Map> resultMortgageTypeList = new ArrayList<Map>();

        Map returnMap = getAvaliableDistributorList(userNo);
        List<Map> distributorList = (List<Map>) returnMap.get(Fields.PARAM_DISTRIBUTRO_LIST);
        if (null != returnMap && null != distributorList && distributorList.size() > 0) {
            for (Map nb : distributorList) {
                Map nbMap = new HashMap();
                nbMap.put(Fields.PARAM_DISTRIBUTRO_CODE, nb.get(Fields.PARAM_DISTRIBUTRO_CODE));
                nbMap.put(Fields.PARAM_DISTRIBUTRO_NAME, nb.get(Fields.PARAM_DISTRIBUTRO_NAME));
                returnDistributorList.add(nbMap);
            }
        }
        resultMap.put(Fields.PARAM_DISTRIBUTRO_LIST, returnDistributorList);
        resultMap.put(Fields.PARAM_BE_RECORD_CLERK, returnMap.get(Fields.PARAM_BE_RECORD_CLERK));

        //Step 3 借款期限列表
        List<NlbsLoanPeriod> loanPeriodList = nlbsLoanPeriodMapper.selectAll();
        for (NlbsLoanPeriod nlbsLoanPeriod : loanPeriodList) {
            Map loanPeriodMap = new HashMap();
            loanPeriodMap.put(Fields.PARAM_LOAN_PERIOD_CODE, nlbsLoanPeriod.getCode());
            loanPeriodMap.put(Fields.PARAM_LOAN_PERIOD_NAME, nlbsLoanPeriod.getPeriod());
            resultLoanPeriodList.add(loanPeriodMap);
        }
        resultMap.put(Fields.PARAM_LOAN_PERIOD_LIST, resultLoanPeriodList);

        //Step 4 放款方式列表
        List<NlbsCreditType> creditTypeList = nlbsCreditTypeMapper.selectAll();
        for (NlbsCreditType nlbsCreditType : creditTypeList) {
            Map creditTypeMap = new HashMap();
            creditTypeMap.put(Fields.PARAM_CREDIT_TYPE_CODE, nlbsCreditType.getCode());
            creditTypeMap.put(Fields.PARAM_CREDIT_TYPE_NAME, nlbsCreditType.getName());
            resultCreditTypeList.add(creditTypeMap);
        }
        resultMap.put(Fields.PARAM_CREDIT_TYPE_LIST, resultCreditTypeList);

        //Step 5 抵押类型列表
        List<NlbsMortgageType> mortgageTypeList = nlbsMortgageTypeMapper.selectAll();
        for (NlbsMortgageType nlbsMortgageType : mortgageTypeList) {
            Map mortgageTypeMap = new HashMap();
            mortgageTypeMap.put(Fields.PARAM_MORTGAGE_TYPE_CODE, nlbsMortgageType.getCode());
            mortgageTypeMap.put(Fields.PARAM_MORTGAGE_TYPE_NAME, nlbsMortgageType.getType());
            resultMortgageTypeList.add(mortgageTypeMap);
        }
        resultMap.put(Fields.PARAM_MORTGAGE_TYPE_LIST, resultMortgageTypeList);

        //Step 6 获取默认的业务经理列表和产品列表，默认取第一条
        if(returnDistributorList.size() > 0){
            Map paramDistributorMap = returnDistributorList.get(0);
            Map agentAndProductMap = getAgentAndProductList(paramDistributorMap);
            resultMap.putAll(agentAndProductMap);
        }

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "进件申请初始化成功！");
        return resultMap;
    }

    /**
     * 根据渠道，动态加载业务经理和产品列表
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map getAgentAndProductList(Map paramMap) throws Exception {
        String distributorCode = (String) paramMap.get(Fields.PARAM_DISTRIBUTRO_CODE);

        Map<String, Object> resultMap = new HashMap();
        List<Map> returnAgentList = new ArrayList<Map>();
        List<Map> returnProductList = new ArrayList<Map>();

        //Step 1 根据渠道编码获取业务员列表
        List<Map> nlbsAgentList = commonService.selectUsersByDistributorCode(distributorCode);
        if(nlbsAgentList != null){
            for(Map ngentMap: nlbsAgentList){
                Map nlbsAgentMap = new HashMap();
                nlbsAgentMap.put(Fields.PARAM_AGENT_ID, ngentMap.get(Fields.PARAM_USER_ID));
                nlbsAgentMap.put(Fields.PARAM_AGENT_NAME, ngentMap.get(Fields.PARAM_FULL_NAME));
                returnAgentList.add(nlbsAgentMap);
            }
        }
        resultMap.put(Fields.PARAM_AGENT_LIST, returnAgentList);

        //Step 2 根据渠道编码获取产品列表
        List<NlbsProduct> nlbsProductList = nlbsProductMapper.selectProductByDistributorCode(distributorCode);
        if (nlbsProductList != null) {
            for (NlbsProduct np : nlbsProductList) {
                Map nlbsProductMap = new HashMap();
                nlbsProductMap.put(Fields.PARAM_PRODUCT_CODE, np.getCode());
                nlbsProductMap.put(Fields.PARAM_PRODUCT_NAME, np.getName());
                nlbsProductMap.put(Fields.PARAM_MAX_LOAN_AMOUNT, np.getMaxLoanAmount());
                returnProductList.add(nlbsProductMap);
            }
        }
        resultMap.put(Fields.PARAM_PRODUCT_LIST, returnProductList);


        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "动态加载业务经理和产品列表成功！");
        return resultMap;
    }

    /**
     * 调用核心系统保存进件信息的接口
     * （提交，暂存，修改提交）
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map saveApplyRecord(Map paramMap) throws Exception {
        paramMap.put(Fields.PARAM_OPERATION_TYPE, Constants.OPERATION_SUBMIT);
        Map<String, Object> resultMap = new HashMap();
        //TODO 1 转换参数
        //TODO 2 本地记录操作历史
        //TODO 3 调用核心系统接口，保存进件信息
        //TODO 4 整理出参
        if(!Constants.BMS_SYSTEM_ISOK){
            resultMap.put(Fields.PARAM_BMS_LOAN_CODE, CommonUtil.getCurrentTimeStr(null, null));
        }
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "进件申请已成功提交！");
        return resultMap;
    }

    /**
     * 查询进件详情
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map queryApplyRecord(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();
        //TODO 1 查询核心系统数据库，查询进件信息
        //切换数据源到核心系统
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        //数据源切回来
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        //TODO 2 整理出参
        if(!Constants.BMS_SYSTEM_ISOK){
            resultMap.put(Fields.PARAM_APPLY_TIME, "2017-05-08 15:33:44");
            resultMap.put(Fields.PARAM_BUSINESS_STATUS, "01");

            resultMap.put(Fields.PARAM_DISTRIBUTRO_CODE, "10000001");
            resultMap.put(Fields.PARAM_DISTRIBUTRO_NAME, "房袋袋总部");
            resultMap.put(Fields.PARAM_AGENT_ID, "a000004");
            resultMap.put(Fields.PARAM_AGENT_NAME, "朱兴智");
            resultMap.put(Fields.PARAM_LOAN_PERIOD_CODE, "07");
            resultMap.put(Fields.PARAM_LOAN_PERIOD_NAME, "7");
            resultMap.put(Fields.PARAM_CREDIT_TYPE_CODE, "B001");
            resultMap.put(Fields.PARAM_CREDIT_TYPE_NAME, "收件收据");
            resultMap.put(Fields.PARAM_MOBILEPHONE_VALIDATE_NO, CommonUtil.getCurrentTimeStr("Tel-", null));
            resultMap.put(Fields.PARAM_PRODUCT_CODE, "1");
            resultMap.put(Fields.PARAM_PRODUCT_NAME, "P1");
            resultMap.put(Fields.PARAM_LOAN_AMOUNT, "5000000");
            resultMap.put(Fields.PARAM_INTENTION_MONEY, "1000000");
            resultMap.put(Fields.PARAM_CUSTOMER_NAME, "王筱华");
            resultMap.put(Fields.PARAM_REMARK, "此处是备注···");

            //抵押物信息
            List<Map> certificateList = new ArrayList<Map>();
            Map certificateMap = new HashMap();
            certificateMap.put(Fields.PARAM_CERTIFICATE_NUMBER_FIRST, "沪房地普字2012");
            certificateMap.put(Fields.PARAM_CERTIFICATE_NUMBER_SECOND, "00XX24");
            certificateMap.put(Fields.PARAM_MORTGAGE_TYPE_CODE, "001");
            certificateMap.put(Fields.PARAM_MORTGAGE_TYPE_NAME, "一抵");
            certificateList.add(certificateMap);
            certificateMap = new HashMap();
            certificateMap.put(Fields.PARAM_CERTIFICATE_NUMBER_FIRST, "沪房地普字2014");
            certificateMap.put(Fields.PARAM_CERTIFICATE_NUMBER_SECOND, "00XX25");
            certificateMap.put(Fields.PARAM_MORTGAGE_TYPE_CODE, "002");
            certificateMap.put(Fields.PARAM_MORTGAGE_TYPE_NAME, "二抵");
            certificateList.add(certificateMap);
            resultMap.put(Fields.PARAM_CERTIFICATE_LIST, certificateList);

            //案件概况
            resultMap.put(Fields.PARAM_MAIN_HOUSEHOLD_ASSET, "主要资产测试数据");
            resultMap.put(Fields.PARAM_MAIN_HOUSEHOLD_LIABILITIES, "主要负债测试数据");
            resultMap.put(Fields.PARAM_MAIN_HOUSEHOLD_INCOME_SOURCE, "主要收入测试数据");
            resultMap.put(Fields.PARAM_FIELD_INVESTIGATION_OF_COLLATERAL, "实地调研测试数据");
            resultMap.put(Fields.PARAM_USAGE_OF_LOAN, "买车买房");
            resultMap.put(Fields.PARAM_PAYMENT_SOURCE, "投资理财");
            //文件列表
            resultMap.putAll(getTestFileData());
            //人员信息列表
            resultMap.putAll(getTestPersonList());
            //抵押物信息列表
            resultMap.putAll(getTestMortgageList());

            //备用房信息
            List<Map> spareHouseList = new ArrayList<Map>();
            Map spareHouseMap = new HashMap();
            spareHouseMap.put(Fields.PARAM_SPARE_HOUSE_OWNER, "某某某、某某、某");
            spareHouseMap.put(Fields.PARAM_SPARE_HOUSE_ADDRESS, "上海市花木路444号901");
            spareHouseList.add(spareHouseMap);
            spareHouseMap = new HashMap();
            spareHouseMap.put(Fields.PARAM_SPARE_HOUSE_OWNER, "XXX、XX、X");
            spareHouseMap.put(Fields.PARAM_SPARE_HOUSE_ADDRESS, "上海市花木路454号902");
            spareHouseList.add(spareHouseMap);
            resultMap.put(Fields.PARAM_SPARE_HOUSE_LIST, spareHouseList);

            //征信信息列表creditInfList
            resultMap.putAll(getTestCreditInfList());
            //司法信息列表
            resultMap.putAll(getTestJudicialInfList());

            //材料类型和文件列表
            resultMap.putAll(initSupplyApplyMaterial(paramMap));

            resultMap.put(Fields.PARAM_BMS_LOAN_CODE, CommonUtil.getCurrentTimeStr("HH-FDD-", null));

        }
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "进件详情已查询成功！");
        return resultMap;
    }

    /**
     * 查询进件列表（含暂存）
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map queryApplyRecordList(Map paramMap) throws Exception {
        Integer pageNo = null != paramMap.get(Fields.PARAM_PAGE_NO) ? new Integer(paramMap.get(Fields.PARAM_PAGE_NO).toString()) : 1;
        Integer pageSize = null != paramMap.get(Fields.PARAM_PAGE_SIZE) ? new Integer(paramMap.get(Fields.PARAM_PAGE_SIZE).toString()) : 10;
        //切换数据源到核心系统
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        //TODO 1 查询核心系统进件列表
        Map returnMap = new HashMap();
        //Map returnMap = bmsCommonService.pageQueryApplyList(pageNo, pageSize, paramMap);
        returnMap = getTestData(pageNo, pageSize);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取进件列表！");

        //数据源切回来
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);

        return returnMap;
    }

    /**
     * 获取所有进件材料类型
     *
     * @return
     * @throws Exception
     */
    public Map queryAllMaterialTypeList() throws Exception {
        Map<String, Object> resultMap = new HashMap();

        List<Map> nlbsMaterialTypeList = commonService.queryAllMaterialTypeList();
        if (nlbsMaterialTypeList == null) {
            nlbsMaterialTypeList = new ArrayList<Map>();
        }

        resultMap.put(Fields.PARAM_MATERIAL_TYPE_LIST, nlbsMaterialTypeList);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取所有进件材料类型！");

        return resultMap;
    }

    /**
     * 获取所有申请单的状态
     * @return
     * @throws Exception
     */
    public Map queryAllApplyRecordStatusList() throws Exception {
        Map<String, Object> resultMap = new HashMap();

        List<Map> nlbsApplyRecordStatusList = commonService.queryAllApplyRecordStatusList();
        if (nlbsApplyRecordStatusList == null) {
            nlbsApplyRecordStatusList = new ArrayList<Map>();
        }

        resultMap.put(Fields.PARAM_APPLY_RECORD_STATUS_LIST, nlbsApplyRecordStatusList);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取所有申请单状态！");

        return resultMap;
    }


    /**
     * 初始化列表查询
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map initApplyList(Map paramMap) throws Exception {
        String userNo = (String) paramMap.get(Fields.PARAM_USER_NO);

        Map<String, Object> resultMap = new HashMap();
        List<Map> cityList = new ArrayList<Map>();
        List<Map> loanStatusList = new ArrayList<Map>();

        //获取城市列表
        Map returnMap = getAvaliableDistributorList(userNo);

        List<Map> distributorList = (List<Map>) returnMap.get(Fields.PARAM_DISTRIBUTRO_LIST);
        if (null != returnMap && null != distributorList && distributorList.size() > 0) {
            List<NlbsCity> allCityList = commonService.queryAllCity();
            Map<String, NlbsCity> allCityMap = new HashMap<String, NlbsCity>();
            for(NlbsCity city: allCityList){
                allCityMap.put(city.getCode(), city);
            }

            for(Map distributor: distributorList){
                String cityCode = distributor.get(Fields.PARAM_GROUP_CITY) == null ? "" : distributor.get(Fields.PARAM_GROUP_CITY).toString();
                if(allCityMap.containsKey(cityCode)){
                    NlbsCity city = allCityMap.get(cityCode);
                    Map cityMap = new HashMap();
                    cityMap.put(Fields.PARAM_CITY_CODE, city.getCode());
                    cityMap.put(Fields.PARAM_CITY_NAME, city.getAbbrName());
                    cityList.add(cityMap);
                    allCityMap.remove(cityCode);
                }
            }
        }
        resultMap.put(Fields.PARAM_CITY_LIST, cityList);

        //获取进件状态
        List<NlbsApplyLoanStatus> statusList = nlbsApplyLoanStatusMapper.queryAllLocalStatus();
        resultMap.put(Fields.PARAM_LOAN_STATUS_LIST, statusList);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取城市列表和状态列表！");

        return resultMap;
    }

    /**
     * 修改进件初始化
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map initModifyApplyRecord(Map paramMap) throws Exception {
        String userNo = (String) paramMap.get(Fields.PARAM_USER_NO);

        Map<String, Object> resultMap = new HashMap();
        List<Map> returnDistributorList = new ArrayList<Map>();
        List<Map> resultLoanPeriodList = new ArrayList<Map>();
        List<Map> resultCreditTypeList = new ArrayList<Map>();
        List<Map> resultMortgageTypeList = new ArrayList<Map>();

        //借款期限列表
        List<NlbsLoanPeriod> loanPeriodList = nlbsLoanPeriodMapper.selectAll();
        for (NlbsLoanPeriod nlbsLoanPeriod : loanPeriodList) {
            Map loanPeriodMap = new HashMap();
            loanPeriodMap.put(Fields.PARAM_LOAN_PERIOD_CODE, nlbsLoanPeriod.getCode());
            loanPeriodMap.put(Fields.PARAM_LOAN_PERIOD_NAME, nlbsLoanPeriod.getPeriod());
            resultLoanPeriodList.add(loanPeriodMap);
        }
        resultMap.put(Fields.PARAM_LOAN_PERIOD_LIST, resultLoanPeriodList);

        //放款方式列表
        List<NlbsCreditType> creditTypeList = nlbsCreditTypeMapper.selectAll();
        for (NlbsCreditType nlbsCreditType : creditTypeList) {
            Map creditTypeMap = new HashMap();
            creditTypeMap.put(Fields.PARAM_CREDIT_TYPE_CODE, nlbsCreditType.getCode());
            creditTypeMap.put(Fields.PARAM_CREDIT_TYPE_NAME, nlbsCreditType.getName());
            resultCreditTypeList.add(creditTypeMap);
        }
        resultMap.put(Fields.PARAM_CREDIT_TYPE_LIST, resultCreditTypeList);

        //抵押类型列表
        List<NlbsMortgageType> mortgageTypeList = nlbsMortgageTypeMapper.selectAll();
        for (NlbsMortgageType nlbsMortgageType : mortgageTypeList) {
            Map mortgageTypeMap = new HashMap();
            mortgageTypeMap.put(Fields.PARAM_MORTGAGE_TYPE_CODE, nlbsMortgageType.getCode());
            mortgageTypeMap.put(Fields.PARAM_MORTGAGE_TYPE_NAME, nlbsMortgageType.getType());
            resultMortgageTypeList.add(mortgageTypeMap);
        }
        resultMap.put(Fields.PARAM_MORTGAGE_TYPE_LIST, resultMortgageTypeList);

        //渠道列表
        Map returnMap = getAvaliableDistributorList(userNo);
        List<Map> distributorList = (List<Map>) returnMap.get(Fields.PARAM_DISTRIBUTRO_LIST);
        if (null != returnMap && null != distributorList && distributorList.size() > 0) {
            for (Map nb : distributorList) {
                Map nbMap = new HashMap();
                nbMap.put(Fields.PARAM_DISTRIBUTRO_CODE, nb.get(Fields.PARAM_DISTRIBUTRO_CODE));
                nbMap.put(Fields.PARAM_DISTRIBUTRO_NAME, nb.get(Fields.PARAM_DISTRIBUTRO_NAME));
                returnDistributorList.add(nbMap);
            }
        }
        resultMap.put(Fields.PARAM_DISTRIBUTRO_LIST, returnDistributorList);
        resultMap.put(Fields.PARAM_BE_RECORD_CLERK, returnMap.get(Fields.PARAM_BE_RECORD_CLERK));

        //通过核心接口获取当前进件信息
        // TODO 查询进件详情
        resultMap.putAll(queryApplyRecord(paramMap));


        //TODO 根据查询结果，初始化业务员列表和产品列表
        Map paramDistributorMap = returnDistributorList.get(0);//根据查询结果，生成distributormap，此处需修改 TODO
        Map agentAndProductMap = getAgentAndProductList(paramDistributorMap);
        resultMap.putAll(agentAndProductMap);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "进件申请初始化成功！");
        return resultMap;
    }

    /**
     * 修改进件申请
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map modifyApplyRecord(Map paramMap) throws Exception {
        paramMap.put(Fields.PARAM_OPERATION_TYPE, Constants.OPERATION_SUBMIT);
        Map<String, Object> resultMap = new HashMap();
        //TODO 1 转换参数
        //TODO 2 本地记录操作历史
        //TODO 3 调用核心系统接口，保存进件信息
        //TODO 4 整理出参
        if(!Constants.BMS_SYSTEM_ISOK){
            resultMap.put(Fields.PARAM_BMS_LOAN_CODE, CommonUtil.getCurrentTimeStr(null, null));
        }
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "修改进件申请已成功提交！");
        return resultMap;
    }

    @Override
    public Map deleteApplyRecord(Map paramMap) throws Exception {
        paramMap.put(Fields.PARAM_OPERATION_TYPE, Constants.OPERATION_DELETE);
        Map<String, Object> resultMap = new HashMap();
        //TODO 1 转换参数
        //TODO 2 本地记录操作历史
        //TODO 3 调用核心系统接口，保存进件信息
        //TODO 4 整理出参

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "已作废进件申请！");
        return resultMap;
    }

    /**
     * 初始化补充进件材料
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map initSupplyApplyMaterial(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();
        String bmsLoanCode = null != paramMap.get(Fields.PARAM_BMS_LOAN_CODE) ? paramMap.get(Fields.PARAM_BMS_LOAN_CODE).toString() : null;
        //Step 1 获取材料类型
        Map materialTypeMap = queryAllMaterialTypeList();
        if(ReturnCode.SUCCESS_CODE.equals(materialTypeMap.get(Fields.PARAM_MESSAGE_ERR_CODE))){
            resultMap.putAll(materialTypeMap);
            resultMap.remove(Fields.PARAM_MESSAGE_ERR_CODE);
            resultMap.remove(Fields.PARAM_MESSAGE_ERR_MESG);
        }
        //TODO Step2 查询BMS获取进件关联的材料（文件）情况
        if(!Constants.BMS_SYSTEM_ISOK){
            resultMap.put(Fields.PARAM_BMS_LOAN_CODE, bmsLoanCode);
            resultMap.putAll(getTestFileData());
        }
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "初始化补充材料成功！");
        return resultMap;
    }
    /**
     * 补充进件材料
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map supplyApplyMaterial(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();
        //TODO 调用BMS补充材料接口，直接透传即可

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "补充材料已成功提交！");
        return resultMap;
    }

    /**
     * 查询进件单操作历史
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map queryOperationHistory(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();
        String bmsLoanCode = null != paramMap.get(Fields.PARAM_BMS_LOAN_CODE) ? paramMap.get(Fields.PARAM_BMS_LOAN_CODE).toString() : null;

        //TODO 查询BMS获取进件关联的材料（文件）情况
        if(!Constants.BMS_SYSTEM_ISOK){
            resultMap.put(Fields.PARAM_BMS_LOAN_CODE, bmsLoanCode);
            resultMap.putAll(getTestOperateHistory());
        }
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "查询进件单操作历史成功！");
        return resultMap;
    }

    /**
     * 返回当前用户可见渠道
     * @param userNo 当前用户
     * @return  beRecordClerk 是否是录单员；
     */
    private Map getAvaliableDistributorList(String userNo) throws Exception {
        Map returnMap = new HashMap();

        int role = MANAGER_FLAG;

        boolean beAdminFlag = false;
        List<String> roleList = new ArrayList<String>();
        roleList.add(Constants.ROLE_SUPPER_MANAGER);
        roleList.add(Constants.ROLE_SYSTEM_MANAGER);
        beAdminFlag = commonService.isAdministrator(userNo, roleList);

        List<NlbsRecordersDistributor> nlbsRecordersDistributorList = nlbsRecordersDistributorMapper.selectRecordersDistributorByUserNo(userNo);
        if(!beAdminFlag){
            //如果不是管理员，看是否是录单员
            if (null != nlbsRecordersDistributorList && nlbsRecordersDistributorList.size() > 0) {
                role = RECORD_FLAG;
            } else {
                role = AGENT_FLAG;
            }
        }

        List<Map> returnDistributorList = new ArrayList<Map>();
        if (MANAGER_FLAG == role) {
            returnMap.put(Fields.PARAM_BE_RECORD_CLERK, "Y");
            //Step 2 获取进件公司（渠道）列表
            //Step 2.1 管理员，则获取全部渠道
            Map forAllDistributorMap = new HashMap();
            forAllDistributorMap.put(Fields.PARAM_TYPE, Constants.GROUP_TREE_ALL);
            List<Map> nlbsDistributorList = commonService.queryDistributors(forAllDistributorMap);
            if(nlbsDistributorList != null && nlbsDistributorList.size() > 0){
                for(Map nb : nlbsDistributorList){
                    Map standNb = new HashMap();
                    standNb.put(Fields.PARAM_DISTRIBUTRO_CODE, nb.get(Fields.PARAM_GROUP_ID));
                    standNb.put(Fields.PARAM_DISTRIBUTRO_NAME, nb.get(Fields.PARAM_GROUP_NAME));
                    standNb.put(Fields.PARAM_GROUP_CITY, nb.get(Fields.PARAM_GROUP_CITY));
                    returnDistributorList.add(standNb);
                }
            }
        } else if(AGENT_FLAG == role){
            returnMap.put(Fields.PARAM_BE_RECORD_CLERK, "N");
            //Step 2.2 业务员，则单独把自身渠道放入List即可，无需额外操作
            //--获取当前用户所在渠道，并直接添加进list
            NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
            if(null != nlbsLoginInfo){
                Map standNb = new HashMap();
                standNb.put(Fields.PARAM_DISTRIBUTRO_CODE, nlbsLoginInfo.getDistributorCode());
                standNb.put(Fields.PARAM_DISTRIBUTRO_NAME, nlbsLoginInfo.getDistributorName());
                standNb.put(Fields.PARAM_GROUP_CITY, nlbsLoginInfo.getCityCode());
                returnDistributorList.add(standNb);
            }

        }else if(RECORD_FLAG == role){
            //Step 1.3 如果是录单员
            returnMap.put(Fields.PARAM_BE_RECORD_CLERK, "Y");
            //Step 2.3 录单员，则返回所在渠道，以及所在渠道的子渠道
            //--录单员直接查询《业务员-渠道关联表》查找渠道及子渠道
            List<Map> nlbsDistributorList = new ArrayList<Map>();
            //Step 2.3.1先查找关联表的数据
            if(nlbsRecordersDistributorList != null && nlbsRecordersDistributorList.size() > 0){
                for(NlbsRecordersDistributor nrd : nlbsRecordersDistributorList){
                    Map forChildDistributorMap = new HashMap();
                    forChildDistributorMap.put(Fields.PARAM_GROUP_ID, nrd.getDistributorCode());
                    forChildDistributorMap.put(Fields.PARAM_TYPE, Constants.GROUP_TREE_SUBORDINATE);
                    nlbsDistributorList.addAll(commonService.queryDistributors(forChildDistributorMap));
                }
                //去除重复项
                HashSet hs = new HashSet(nlbsDistributorList);
                nlbsDistributorList.clear();
                nlbsDistributorList.addAll(hs);
            }
            //Step 2.3.3 整理distributerList的参数
            if(nlbsDistributorList != null && nlbsDistributorList.size() > 0){
                //整理为标准的distributorCode distributorName的List
                for(Map map : nlbsDistributorList){
                    Map standNb = new HashMap();
                    standNb.put(Fields.PARAM_DISTRIBUTRO_CODE, map.get(Fields.PARAM_DISTRIBUTRO_CODE));
                    standNb.put(Fields.PARAM_DISTRIBUTRO_NAME, map.get(Fields.PARAM_DISTRIBUTRO_NAME));
                    standNb.put(Fields.PARAM_GROUP_CITY, map.get(Fields.PARAM_GROUP_CITY));
                    returnDistributorList.add(standNb);
                }
            }
        }

        returnMap.put(Fields.PARAM_DISTRIBUTRO_LIST, returnDistributorList);

        return returnMap;
    }


    /**
     * 造数据--进件申请单列表
     * @return
     */
    private Map getTestData(int pageNo, int pageSize){
        Map returnMap = new HashMap();
        int count = 50;
        int i = 0;
        List<Map> applyList = new ArrayList<Map>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cityCode_1 = "c0001";
        String cityName_1 = "上海";

        String cityCode_2 = "c0002";
        String cityName_2 = "北京";

        for(; i < count; i++){
            Map applyMap = new HashMap();
            if (i < 5) {
                applyMap.put(Fields.PARAM_CITY_CODE, cityCode_1);
                applyMap.put(Fields.PARAM_CITY_NAME, cityName_1);
            }else{
                applyMap.put(Fields.PARAM_CITY_CODE, cityCode_2);
                applyMap.put(Fields.PARAM_CITY_NAME, cityName_2);
            }

            String status = "0" + ((((int) (Math.random() * 100)) % 4) + 1);
            applyMap.put(Fields.PARAM_BMS_LOAN_CODE, CommonUtil.getCurrentTimeStr("HH-", ""));
            applyMap.put(Fields.PARAM_CUSTOMER_NAME, "张三" + (int) (Math.random() * 100) + "号");
            applyMap.put(Fields.PARAM_LOAN_PERIOD_CODE, "06");
            applyMap.put(Fields.PARAM_APPLY_AMOUNT, (int) ((Math.random() * 10000) % 150) + "");
            applyMap.put(Fields.PARAM_APPLY_DATETIME, df.format(new Date()));
            applyMap.put(Fields.PARAM_STATUS, status);
            applyMap.put(Fields.PARAM_STATUS_NAME, "待定（" + status + "）");

            applyList.add(applyMap);
        }


        returnMap.put(Fields.PARAM_APPLY_LOAN_LIST,applyList.subList((pageNo - 1)*pageSize, pageNo * pageSize));
        returnMap.put(Fields.PARAM_PAGES, count % pageSize > 0 ? (((int) count / pageSize) + 1) + "" : ((count / pageSize)) + "");
        returnMap.put(Fields.PARAM_TOTAL, count + "");
        returnMap.put(Fields.PARAM_CURRENT_PAGE, pageNo + "");

        return returnMap;
    }

    /**
     * 造数据--文件列表
     * @return
     */
    private Map getTestFileData(){
        Map returnMap = new HashMap();
        List<Map> fileList = new ArrayList<Map>();

        Map fileMap = new HashMap();
        fileMap.put(Fields.PARAM_FILE_ID, "b96948bb-47bf-11e7-904c-1866dae83f00");
        fileMap.put(Fields.PARAM_ORIGINAL_FILE_NAME, "身份证_f.jpg");
        fileMap.put(Fields.PARAM_FILE_TYPE, "01");
        fileMap.put(Fields.PARAM_FILE_SIZE, "1");
        fileList.add(fileMap);

        fileMap = new HashMap();
        fileMap.put(Fields.PARAM_FILE_ID, "e66eaf25-47bf-11e7-904c-1866dae83f00");
        fileMap.put(Fields.PARAM_ORIGINAL_FILE_NAME, "结婚证.jpg");
        fileMap.put(Fields.PARAM_FILE_TYPE, "03");
        fileMap.put(Fields.PARAM_FILE_SIZE, "2");
        fileList.add(fileMap);

        fileMap = new HashMap();
        fileMap.put(Fields.PARAM_FILE_ID, "2a153840-4205-11e7-904c-1866dae83f00");
        fileMap.put(Fields.PARAM_ORIGINAL_FILE_NAME, "QQ截图20170526112334.png");
        fileMap.put(Fields.PARAM_FILE_TYPE, "03");
        fileMap.put(Fields.PARAM_FILE_SIZE, "2");
        fileList.add(fileMap);

        fileMap = new HashMap();
        fileMap.put(Fields.PARAM_FILE_ID, "a89e6274-47bb-11e7-904c-1866dae83f00");
        fileMap.put(Fields.PARAM_ORIGINAL_FILE_NAME, "表.txt");
        fileMap.put(Fields.PARAM_FILE_TYPE, "08");
        fileMap.put(Fields.PARAM_FILE_SIZE, "1");
        fileList.add(fileMap);

        fileMap = new HashMap();
        fileMap.put(Fields.PARAM_FILE_ID, "383c3593-47bc-11e7-904c-1866dae83f00");
        fileMap.put(Fields.PARAM_ORIGINAL_FILE_NAME, "npm-debug.log");
        fileMap.put(Fields.PARAM_FILE_TYPE, "11");
        fileMap.put(Fields.PARAM_FILE_SIZE, "1");
        fileList.add(fileMap);

        returnMap.put(Fields.PARAM_FILE_LIST, fileList);


        return returnMap;
    }

    /**
     * 造数据--操作历史
     * @return
     */
    private Map getTestOperateHistory(){
        Map returnMap = new HashMap();
        List<Map> operationList = new ArrayList<Map>();

        Map operationMap = new HashMap();
        operationMap.put(Fields.PARAM_OPERATION_TIME, "2017-05-06 12:12:55");
        operationMap.put(Fields.PARAM_DISTRIBUTRO_NAME, "上海合盘金融信息服务股份有限公司");
        operationMap.put(Fields.PARAM_OPERATION_USER, "张三");
        operationMap.put(Fields.PARAM_OPERATION_TYPE, "暂存");
        operationList.add(operationMap);

        operationMap = new HashMap();
        operationMap.put(Fields.PARAM_OPERATION_TIME, "2017-05-06 18:12:55");
        operationMap.put(Fields.PARAM_DISTRIBUTRO_NAME, "上海合盘金融信息服务股份有限公司");
        operationMap.put(Fields.PARAM_OPERATION_USER, "张三");
        operationMap.put(Fields.PARAM_OPERATION_TYPE, "暂存");
        operationList.add(operationMap);

        operationMap = new HashMap();
        operationMap.put(Fields.PARAM_OPERATION_TIME, "2017-05-06 12:12:55");
        operationMap.put(Fields.PARAM_DISTRIBUTRO_NAME, "上海合盘金融信息服务股份有限公司");
        operationMap.put(Fields.PARAM_OPERATION_USER, "张三");
        operationMap.put(Fields.PARAM_OPERATION_TYPE, "提交");
        operationList.add(operationMap);

        operationMap = new HashMap();
        operationMap.put(Fields.PARAM_OPERATION_TIME, "2017-05-06 12:12:55");
        operationMap.put(Fields.PARAM_DISTRIBUTRO_NAME, "上海宏获资产管理有限公司");
        operationMap.put(Fields.PARAM_OPERATION_USER, "老张");
        operationMap.put(Fields.PARAM_OPERATION_TYPE, "初审");
        operationList.add(operationMap);

        returnMap.put(Fields.PARAM_OPERATION_HISTORY_LIST, operationList);
        return returnMap;
    }

    /**
     * 造数据--人员信息
     * @return
     */
    private Map getTestPersonList(){
        Map returnMap = new HashMap();
        List<Map> personList = new ArrayList<Map>();
        int i = 0;
        for(;i < 5; i++) {
            Map personMap = new HashMap();
            personMap.put(Fields.PARAM_PERSON_NAME, "张三" + ((int)(Math.random() * 100)) % 10);
            personMap.put(Fields.PARAM_PERSON_ID_NO, CommonUtil.getCurrentTimeStr("ID-", "X"));
            personMap.put(Fields.PARAM_PERSON_ID_TYPE, "身份证");
            personMap.put(Fields.PARAM_PERSON_PERIOD_OF_VALIDITY, "2010-08至2030-08");
            personMap.put(Fields.PARAM_PERSON_MOBILEPHONE, "1380000000" + ((int)(Math.random() * 100)) % 10);
            personMap.put(Fields.PARAM_PERSON_ORGANIZATION, "上海市XXXX有限公司");
            personMap.put(Fields.PARAM_PERSON_POSITION, "业务总监");
            personMap.put(Fields.PARAM_PERSON_ANNUAL, (((int)(Math.random() * 100)) % 10 + 1) +"0");
            personMap.put(Fields.PARAM_PERSON_ADDRESS, "XX路1" + ((int)(Math.random() * 100)) % 10 + "0号");
            personMap.put(Fields.PARAM_PERSON_MARRIAGE, "未婚");
            personList.add(personMap);
        }

        returnMap.put(Fields.PARAM_PERSON_LIST, personList);
        return returnMap;
    }

    /**
     * 造数据--抵押物列表
     * @return
     */
    private Map getTestMortgageList(){
        Map returnMap = new HashMap();
        List<Map> mortgageList = new ArrayList<Map>();
        int i = 0;
        for(;i < 5; i++) {
            Map mortgageMap = new HashMap();
            mortgageMap.put(Fields.PARAM_MORTGAGE_CERTIFICATE_NUMBER, "沪ABC（2010）第345" + ((int)(Math.random() * 100)) % 10 + "号");
            mortgageMap.put(Fields.PARAM_MORTGAGE_EVALUTION_PRICE, "10" + ((int)(Math.random() * 100)) % 10);
            mortgageMap.put(Fields.PARAM_MORTGAGE_BUYING_TIME, "2015-02-14");
            mortgageMap.put(Fields.PARAM_MORTGAGE_PROPERTY_OWNERS, "XXX、某某某");
            mortgageMap.put(Fields.PARAM_MORTGAGE_PROPERTY_ADDRESS, "上海市花木路444号901");
            mortgageMap.put(Fields.PARAM_MORTGAGE_LAND_CHARACTERISSTICS, "国有");
            mortgageMap.put(Fields.PARAM_MORTGAGE_ACQUISITION_METHOD, "出让");
            mortgageMap.put(Fields.PARAM_MORTGAGE_BUILDING_TYPE, "公寓");
            mortgageMap.put(Fields.PARAM_MORTGAGE_STRUCTURE_AREA, "123.45平方米");
            mortgageMap.put(Fields.PARAM_MORTGAGE_GARAGE_ADDRESS, "");
            mortgageMap.put(Fields.PARAM_MORTGAGE_GARAGE_AREA, "25平方米");
            mortgageMap.put(Fields.PARAM_MORTGAGE_TOTAL_FLOORS, "22");
            mortgageMap.put(Fields.PARAM_MORTGAGE_END_DATE, "2010年");
            mortgageMap.put(Fields.PARAM_MORTGAGE_COMMON_TYPES, "公同共有");
            mortgageMap.put(Fields.PARAM_MORTGAGE_MINOR_SHARE, "");
            mortgageMap.put(Fields.PARAM_MORTGAGE_REMARK, "");

            List investinfList = new ArrayList();
            Map investinfMap = new HashMap();
            investinfMap.put(Fields.PARAM_INVESTINF_INVEST_TIME, "2017年5月8日");
            investinfMap.put(Fields.PARAM_INVESTINF_DEALING_CASE, "无");
            investinfMap.put(Fields.PARAM_INVESTINF_FIRST_MORTGAGE_CREDITOR, "某某某");
            investinfMap.put(Fields.PARAM_INVESTINF_FIRST_MORTGAGE_PRICE, "最高额抵押550万元");
            investinfMap.put(Fields.PARAM_INVESTINF_SECOND_MORTGAGE_CREDITOR, "某某某");
            investinfMap.put(Fields.PARAM_INVESTINF_SECOND_MORTGAGE_PRICE, "一般抵押100万元");
            investinfList.add(investinfMap);
            mortgageMap.put(Fields.PARAM_MORTGAGE_INVESTINF_LIST, investinfList);

            List residenceInfList = new ArrayList();
            Map residenceInfMap = new HashMap();
            residenceInfMap.put(Fields.PARAM_RESIDENCEINF_RESIDENCE_NAME, "某某某");
            residenceInfMap.put(Fields.PARAM_RESIDENCEINF_RESIDENCEID_NO, "123456789012345678");
            residenceInfList.add(residenceInfMap);
            residenceInfMap = new HashMap();
            residenceInfMap.put(Fields.PARAM_RESIDENCEINF_RESIDENCE_NAME, "某某");
            residenceInfMap.put(Fields.PARAM_RESIDENCEINF_RESIDENCEID_NO, "123456789012345678");
            residenceInfList.add(residenceInfMap);
            residenceInfMap = new HashMap();
            residenceInfMap.put(Fields.PARAM_RESIDENCEINF_RESIDENCE_NAME, "某");
            residenceInfMap.put(Fields.PARAM_RESIDENCEINF_RESIDENCEID_NO, "123456789012345678");
            residenceInfList.add(residenceInfMap);
            mortgageMap.put(Fields.PARAM_MORTGAGE_RESIDENCEINF_LIST, residenceInfList);

            mortgageList.add(mortgageMap);
        }

        returnMap.put(Fields.PARAM_MORTGAGE_LIST, mortgageList);
        return returnMap;
    }


    /**
     * 造数据--征信信息列表
     * @return
     */
    private Map getTestCreditInfList(){
        Map returnMap = new HashMap();
        List<Map> creditInfList = new ArrayList<Map>();
        int i = 0;
        for(;i < 5; i++) {
            Map creditInfMap = new HashMap();
            creditInfMap.put(Fields.PARAM_CREDITINF_NAME, "张三" + ((int)(Math.random() * 100)) % 10 + "号");
            creditInfMap.put(Fields.PARAM_CREDITINF_REPORT_TIME, "2010年" + ((int)(Math.random() * 100)) % 10 + "月");
            creditInfMap.put(Fields.PARAM_CREDITINF_LOAN_COUNT, "3");
            creditInfMap.put(Fields.PARAM_CREDITINF_TOTAL_CONTRACT, "50万元");
            creditInfMap.put(Fields.PARAM_CREDITINF_BALANCE, "1万元");
            creditInfMap.put(Fields.PARAM_CREDITINF_SIXMONTH_AVERAGE_QUOTA_REPAY, "0.2万元");
            creditInfMap.put(Fields.PARAM_CREDITINF_ACCOUNT_NUM, ((int)(Math.random() * 100)) % 10);
            creditInfMap.put(Fields.PARAM_CREDITINF_TOTAL_CREDIT, "3000万元");
            creditInfMap.put(Fields.PARAM_CREDITINF_QUOTA_USED, "20万元");
            creditInfMap.put(Fields.PARAM_CREDITINF_SIXMONTH_AVERAGE_QUOTA_USED, "0.2万元");


            List loanInfoList = new ArrayList();
            Map loanInfoMap = new HashMap();
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, "1");
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "当前逾期");
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, "200万元");
            loanInfoList.add(loanInfoMap);
            loanInfoMap = new HashMap();
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, "1");
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "非正常类贷款");
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, "可疑");
            loanInfoList.add(loanInfoMap);
            loanInfoMap = new HashMap();
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, "2");
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "近12个月累计最高逾期次数");
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, "4");
            loanInfoList.add(loanInfoMap);
            loanInfoMap = new HashMap();
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, "2");
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "近12个月最高连续逾期期数");
            loanInfoMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, "3");
            loanInfoList.add(loanInfoMap);
            creditInfMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_LIST, loanInfoList);

            List loanInfoCardList = new ArrayList();
            Map loanInfoCardMap = new HashMap();
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_SERIALNO, "1");
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_TYPE, "当前逾期");
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_DETAILS, "200万元");
            loanInfoCardList.add(loanInfoCardMap);
            loanInfoCardMap = new HashMap();
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_SERIALNO, "1");
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_TYPE, "非正常类贷记卡");
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_DETAILS, "可疑");
            loanInfoCardList.add(loanInfoCardMap);
            loanInfoCardMap = new HashMap();
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_SERIALNO, "2");
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_TYPE, "近12个月累计最高逾期次数");
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_DETAILS, "4");
            loanInfoCardList.add(loanInfoCardMap);
            loanInfoCardMap = new HashMap();
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_SERIALNO, "2");
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_TYPE, "近12个月最高连续逾期期数");
            loanInfoCardMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_DETAILS, "3");
            loanInfoCardList.add(loanInfoCardMap);
            creditInfMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_LIST, loanInfoCardList);



            creditInfMap.put(Fields.PARAM_CREDITINF_SECURED_STATUS, "关注");
            creditInfMap.put(Fields.PARAM_CREDITINF_SECURED_PRICE, "500万元");
            creditInfMap.put(Fields.PARAM_CREDITINF_SECURED_BALANCE, "200万元");
            creditInfMap.put(Fields.PARAM_CREDITINF_THREEMONTH_QUERY_COUNT, "6");
            creditInfMap.put(Fields.PARAM_CREDITINF_AFTER_LOAN_MANAGE_COUNT, "2");
            creditInfMap.put(Fields.PARAM_CREDITINF_SECURED_CHECKUP_COUNT, "2");
            creditInfMap.put(Fields.PARAM_CREDITINF_CREDIT_INFO_REMARK, "测试数据--这里是备注");
            creditInfList.add(creditInfMap);
        }

        returnMap.put(Fields.PARAM_CREDITINF_LIST, creditInfList);
        return returnMap;
    }
    /**
     * 造数据--司法信息列表
     * @return
     */
    private Map getTestJudicialInfList(){
        Map returnMap = new HashMap();
        List<Map> judicialInfList = new ArrayList<Map>();
        int i = 0;
        for(;i < 5; i++) {
            Map judicialInfMap = new HashMap();
            judicialInfMap.put(Fields.PARAM_JUDICIALINF_NAME, "张三" + ((int)(Math.random() * 100)) % 10 + "号");
            judicialInfMap.put(Fields.PARAM_JUDICIALINF_LITIGATION, "否");
            judicialInfMap.put(Fields.PARAM_JUDICIALINF_REGISTRATION_NO, CommonUtil.getCurrentTimeStr("A",""));
            judicialInfMap.put(Fields.PARAM_JUDICIALINF_IS_CLOSED, "");
            judicialInfMap.put(Fields.PARAM_JUDICIALINF_EXECUTE_TARGET, "");
            judicialInfMap.put(Fields.PARAM_JUDICIALINF_STATUS_STATEMENT, "");
            judicialInfList.add(judicialInfMap);
        }

        returnMap.put(Fields.PARAM_JUDICIALINF_LIST, judicialInfList);
        return returnMap;
    }

    public Map uploadFile(Map paramMap) {
        Map map = new HashMap();
        MultipartFile[] files = (MultipartFile[])paramMap.get("files");
        String applyFilePath = (String) paramMap.get("filePath");
        String[] paths = {applyFilePath};
        map.put("filePath", paths);

        JSONObject result = null;
        try {
            String url = configInfo.getFmsUrl() + "/fileLoad/uploadFile";
            result = HttpRequestUtils.httpPost(url, map, files,false);
        }catch (Exception e){
            logger.error("上传文件异常：",e);
        }
        return result;
    }


    public Map getFile(Map map) throws Exception{
        Map result = new HashMap();
        String serialNo = (String)map.get("serialNo");

        //JSONObject response = null;
        InputStream in = null;
        try {
            String url = configInfo.getFmsUrl() + "/fileLoad/getFile" + "?serialNo=" + serialNo;
            in = HttpRequestUtils.httpGetFile(url);
            result.put("is", in);
        }catch (Exception e){
            logger.error("下载文件异常：",e);
        }

        return result;
    }

    public Map getUrlList(Map map) throws Exception {
        Map result = new HashMap();
        JSONObject response = null;
        try {
            JSONObject jsonParam = JSONObject.fromObject(map);
            String url = configInfo.getFmsUrl() + "/fileLoad/getUrlList";
            response = HttpRequestUtils.httpPost(url,jsonParam);

        }catch (Exception e){
            logger.error("获取文件列表异常：",e);
        }

        result = CommonUtil.toMap(response);
        return result;
    }
    public static void main(String[] args) throws Exception {
//        getTestFileData();
    }
}