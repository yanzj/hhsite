package com.vilio.nlbs.apply.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.nlbs.apply.service.ApplyService;
import com.vilio.nlbs.bms.dao.*;
import com.vilio.nlbs.bms.service.BmsCommonService;
import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.*;
import com.vilio.nlbs.commonMapper.pojo.*;
import com.vilio.nlbs.dynamicdatasource.CustomerContextHolder;
import com.vilio.nlbs.remote.service.RemoteBmsService;
import com.vilio.nlbs.todo.service.NlbsTODOService;
import com.vilio.nlbs.util.*;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
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

    @Resource
    NlbsOperationHistoryMapper nlbsOperationHistoryMapper;

    @Resource
    BmsOperationHistoryDao bmsOperationHistoryDao;

    @Resource
    NlbsTODOService nlbsTODOService;

    @Resource
    BmsChannelsDao bmsChannelsDao;

    @Resource
    BmsChannelMembersDao bmsChannelMembersDao;

    @Resource
    BmsDictionariesDao bmsDictionariesDao;

    @Resource
    BmsProductDao bmsProductDao;

    @Resource
    NlbsUserGovernCityMapper nlbsUserGovernCityMapper;

    @Resource
    BmsLoanMasterDao bmsLoanMasterDao;

    @Resource
    RemoteBmsService remoteBmsService;

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
    public Map initApplyInfo(Map paramMap) throws Exception {
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();

        Map<String, Object> resultMap = new HashMap();
        List<Map> returnDistributorList = new ArrayList<Map>();
        List<Map> resultLoanPeriodList = new ArrayList<Map>();
        List<Map> resultCreditTypeList = new ArrayList<Map>();
        List<Map> resultMortgageTypeList = new ArrayList<Map>();

        //Step 2 获取可见渠道列表
        Map returnMap = getAvaliableDistributorList(userNo);
        List<Map> distributorList = (List<Map>) returnMap.get(Fields.PARAM_DISTRIBUTRO_LIST);
        if (null != returnMap && null != distributorList && distributorList.size() > 0) {
            for (Map nb : distributorList) {
                Map nbMap = new HashMap();
                nbMap.put(Fields.PARAM_DISTRIBUTRO_CODE, nb.get(Fields.PARAM_DISTRIBUTRO_CODE) == null ? "" :nb.get(Fields.PARAM_DISTRIBUTRO_CODE).toString());
                nbMap.put(Fields.PARAM_DISTRIBUTRO_NAME, nb.get(Fields.PARAM_DISTRIBUTRO_NAME));
                returnDistributorList.add(nbMap);
            }
        }
        resultMap.put(Fields.PARAM_DISTRIBUTRO_LIST, returnDistributorList);
        resultMap.put(Fields.PARAM_BE_RECORD_CLERK, returnMap.get(Fields.PARAM_BE_RECORD_CLERK));

        //Step 3 借款期限列表
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        List<Map> loanPeriodList = bmsDictionariesDao.queryDictionariesByType(Constants.BMS_DICTIONARY_LOAN_TERM);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        if (loanPeriodList != null) {
            for (Map bmsloanPeriodMap : loanPeriodList) {
                Map loanPeriodMap = new HashMap();
                loanPeriodMap.put(Fields.PARAM_LOAN_PERIOD_CODE, bmsloanPeriodMap.get(Fields.PARAM_CODE) == null ? "" : bmsloanPeriodMap.get(Fields.PARAM_CODE).toString());
                loanPeriodMap.put(Fields.PARAM_LOAN_PERIOD_NAME, bmsloanPeriodMap.get(Fields.PARAM_TITLE));
                resultLoanPeriodList.add(loanPeriodMap);
            }
        }
        resultMap.put(Fields.PARAM_LOAN_PERIOD_LIST, resultLoanPeriodList);

        //Step 4 放款方式列表
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        List<Map> creditTypeList = bmsDictionariesDao.queryDictionariesByType(Constants.BMS_DICTIONARY_LOAN_METHOD);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        if (creditTypeList != null) {
            for (Map bmsCreditTypeMap : creditTypeList) {
                Map creditTypeMap = new HashMap();
                creditTypeMap.put(Fields.PARAM_CREDIT_TYPE_CODE, bmsCreditTypeMap.get(Fields.PARAM_CODE) == null ? "" : bmsCreditTypeMap.get(Fields.PARAM_CODE).toString());
                creditTypeMap.put(Fields.PARAM_CREDIT_TYPE_NAME, bmsCreditTypeMap.get(Fields.PARAM_TITLE));
                resultCreditTypeList.add(creditTypeMap);
            }
        }
        resultMap.put(Fields.PARAM_CREDIT_TYPE_LIST, resultCreditTypeList);

        //Step 5 抵押类型列表
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        List<Map> mortgageTypeList = bmsDictionariesDao.queryDictionariesByType(Constants.BMS_DICTIONARY_LOAN_MORTGAGED_TYPE);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        for (Map bmsMortgageTypeMap : mortgageTypeList) {
            Map mortgageTypeMap = new HashMap();
            mortgageTypeMap.put(Fields.PARAM_MORTGAGE_TYPE_CODE, bmsMortgageTypeMap.get(Fields.PARAM_CODE) == null ? "" : bmsMortgageTypeMap.get(Fields.PARAM_CODE).toString());
            mortgageTypeMap.put(Fields.PARAM_MORTGAGE_TYPE_NAME, bmsMortgageTypeMap.get(Fields.PARAM_TITLE));
            resultMortgageTypeList.add(mortgageTypeMap);
        }
        resultMap.put(Fields.PARAM_MORTGAGE_TYPE_LIST, resultMortgageTypeList);

        //Step 6 获取默认的业务经理列表和产品列表，默认取第一条
        if (returnDistributorList.size() > 0) {
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
        String distributorCode = paramMap.get(Fields.PARAM_DISTRIBUTRO_CODE) == null ? "" : paramMap.get(Fields.PARAM_DISTRIBUTRO_CODE).toString();

        Map<String, Object> resultMap = new HashMap();
        List<Map> returnAgentList = new ArrayList<Map>();
        List<Map> returnProductList = new ArrayList<Map>();

        //Step 1 根据渠道编码获取业务员列表
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        List<Map> nlbsAgentList = bmsChannelMembersDao.queryAgentsByDistributorCode(distributorCode);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        if (nlbsAgentList != null) {
            for (Map agentMap : nlbsAgentList) {
                Map nlbsAgentMap = new HashMap();
                nlbsAgentMap.put(Fields.PARAM_AGENT_ID, agentMap.get(Fields.PARAM_AGENT_ID) == null ? "" : agentMap.get(Fields.PARAM_AGENT_ID).toString());
                nlbsAgentMap.put(Fields.PARAM_AGENT_NAME, agentMap.get(Fields.PARAM_FULL_NAME));
                returnAgentList.add(nlbsAgentMap);
            }
        }
        resultMap.put(Fields.PARAM_AGENT_LIST, returnAgentList);

        //Step 2 根据渠道编码获取产品列表
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        List<Map> nlbsProductList = bmsProductDao.queryProductListByDistributorCode(distributorCode);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        if (nlbsProductList != null) {
            for (Map bmsProductMap : nlbsProductList) {
                Map nlbsProductMap = new HashMap();
                nlbsProductMap.put(Fields.PARAM_PRODUCT_CODE, bmsProductMap.get(Fields.PARAM_PRODUCT_CODE) == null ? "" : bmsProductMap.get(Fields.PARAM_PRODUCT_CODE).toString());
                nlbsProductMap.put(Fields.PARAM_PRODUCT_NAME, bmsProductMap.get(Fields.PARAM_PRODUCT_NAME));
                nlbsProductMap.put(Fields.PARAM_MAX_LOAN_AMOUNT, bmsProductMap.get(Fields.PARAM_MAX_LOAN_AMOUNT));
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

        String loanSerialNo = paramMap.get(Fields.PARAM_LOAN_SERIAL_NO) == null ? "" : paramMap.get(Fields.PARAM_LOAN_SERIAL_NO).toString();
        String operatorFlag = paramMap.get(Fields.PARAM_OPERATION_TYPE) == null ? "" : paramMap.get(Fields.PARAM_OPERATION_TYPE).toString();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        String customerName = paramMap.get(Fields.PARAM_CUSTOMER_NAME) == null ? "" : paramMap.get(Fields.PARAM_CUSTOMER_NAME).toString();

        Map forBmsParamMap = new HashMap();
        forBmsParamMap.put(Fields.PARAM_LOAN_ID, loanSerialNo);
        forBmsParamMap.put(Fields.PARAM_OPERATOR_FLAG, operatorFlag);
        forBmsParamMap.put(Fields.PARAM_DISTRIBUTRO_CODE, paramMap.get(Fields.PARAM_DISTRIBUTRO_CODE));
        forBmsParamMap.put(Fields.PARAM_APPLY_PERIOD, paramMap.get(Fields.PARAM_LOAN_PERIOD_CODE));
        forBmsParamMap.put(Fields.PARAM_CREDITYPE_CODE, paramMap.get(Fields.PARAM_CREDIT_TYPE_CODE));
        forBmsParamMap.put(Fields.PARAM_MOBILEPHONE_VALIDATE_NO, paramMap.get(Fields.PARAM_PHONE_VERIFICATE));
        forBmsParamMap.put(Fields.PARAM_PRODUCT_CODE, paramMap.get(Fields.PARAM_PRODUCT_CODE));
        forBmsParamMap.put(Fields.PARAM_LOAN_AMOUNT, paramMap.get(Fields.PARAM_LOAN_AMOUNT));
        forBmsParamMap.put(Fields.PARAM_INTENTION_MONEY, paramMap.get(Fields.PARAM_INTENTION_MONEY));
        forBmsParamMap.put(Fields.PARAM_REMARK, paramMap.get(Fields.PARAM_REMARK));
        forBmsParamMap.put(Fields.PARAM_CUSTOMER_NAME, customerName);//主借款人姓名
        forBmsParamMap.put(Fields.PARAM_BIZ_MANAGER_ID, paramMap.get(Fields.PARAM_AGENT_ID));//业务经理
        //nlbs的录单员
        NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
        if (nlbsLoginInfo != null) {
            forBmsParamMap.put(Fields.PARAM_CHANNEL_RECORDER_ID, nlbsLoginInfo.getAgentId());//当前用户--涉及取agent
        }
        // 整理抵押物列表
        List<Map> mortgageTypeList = (List<Map>) paramMap.get(Fields.PARAM_MORTGAGE_TYPE_LIST);
        List<Map> forBmsMortgageTypeList = new ArrayList<>();
        if (mortgageTypeList != null && mortgageTypeList.size() > 0) {
            for (Map mortgageTypeMap : mortgageTypeList) {
                Map forBmsMortgageTypeMap = new HashMap();
                forBmsMortgageTypeMap.put(Fields.PARAM_CERTIFICATE_NUMBER_FIRST, mortgageTypeMap.get(Fields.PARAM_CERTIFICATE_NUMBER_FIRST));
                forBmsMortgageTypeMap.put(Fields.PARAM_CERTIFICATE_NUMBER_SECOND, mortgageTypeMap.get(Fields.PARAM_CERTIFICATE_NUMBER_SECOND));
                forBmsMortgageTypeMap.put(Fields.PARAM_MORTGAGE_TYPE, mortgageTypeMap.get(Fields.PARAM_MORTGAGE_TYPE_CODE));
                forBmsMortgageTypeList.add(forBmsMortgageTypeMap);
            }
        }
        forBmsParamMap.put(Fields.PARAM_LOAN_PROPERTY_INFO_DTO_LIST, forBmsMortgageTypeList);

        // 整理上传的文件
        List<Map> forBmsFileList = new ArrayList<>();
        Map forBmsFileMap = new HashMap();
        forBmsFileMap.put(Fields.PARAM_FILE_ID, paramMap.get(Fields.PARAM_APPLY_FILE_ID));
        forBmsFileMap.put(Fields.PARAM_FILE_NAME, paramMap.get(Fields.PARAM_APPLY_FILE_NAME));
        forBmsFileList.add(forBmsFileMap);
        forBmsParamMap.put(Fields.PARAM_FILE_LIST, forBmsFileList);

        Map bmsReturnMap = remoteBmsService.callOnlineloanMasterService(forBmsParamMap, StringUtils.isNotBlank(loanSerialNo) ? "PUT" : "POST");
        if (ReturnCode.SUCCESS_CODE.equals(bmsReturnMap.get(Fields.PARAM_MESSAGE_ERR_CODE))) {
            //如果是暂存，则生成待办任务
            if (Constants.OPERATION_TEMP_SUBMIT.equals(operatorFlag)) {
                String retLoanSerialNo = bmsReturnMap.get(Fields.PARAM_LOAN_SERIAL_NO) == null ? "" : bmsReturnMap.get(Fields.PARAM_LOAN_SERIAL_NO).toString();
                //生成待办任务
                try {
                    if (StringUtils.isNotBlank(retLoanSerialNo)) {
                        loanSerialNo = retLoanSerialNo;
                        nlbsTODOService.insertApplyTodoTask(retLoanSerialNo, userNo, customerName);
                    }
                } catch (Exception e) {
                    logger.error("创建待办任务失败了----请查看错误：");
                    e.printStackTrace();
                }
            }

            //记录暂存或提交操作历史表
            NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();
            nlbsOperationHistory.setOperater(userNo);
            nlbsOperationHistory.setSerialNo(loanSerialNo);
            nlbsOperationHistory.setSystemCode(Constants.SYSTEM_ID_NLBS);
            nlbsOperationHistory.setOperateType(operatorFlag);
            if (nlbsLoginInfo != null) {
                nlbsOperationHistory.setOperaterName(nlbsLoginInfo.getFullName());
                nlbsOperationHistory.setDistributorCode(nlbsLoginInfo.getDistributorCode());
                nlbsOperationHistory.setDistributorName(nlbsLoginInfo.getDistributorName());
            }
            nlbsOperationHistoryMapper.getInsert(nlbsOperationHistory);
        } else {
            throw new HHBizException(bmsReturnMap.get(Fields.PARAM_MESSAGE_ERR_CODE).toString(), bmsReturnMap.get(Fields.PARAM_MESSAGE_ERR_MESG).toString());
        }

        return bmsReturnMap;
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

        //Step 1 整理参数，调用BMS核心系统接口
        Map<String, Object> forBmsParamMap = new HashMap();
        forBmsParamMap.put(Fields.PARAM_LOAN_ID, paramMap.get(Fields.PARAM_LOAN_SERIAL_NO));
        Map bmsResultMap = remoteBmsService.callOnlineloanMasterService(forBmsParamMap, "GET");

        //bmsResultMap不会为null，故此处不再判断空值
        if (ReturnCode.SUCCESS_CODE.equals(bmsResultMap.get(Fields.PARAM_MESSAGE_ERR_CODE))) {
            Map bmsOperator = (Map) bmsResultMap.get("operator");//运营信息
            List<Map> subOperationList = new ArrayList<>();
            if(bmsOperator != null){
                subOperationList = (List<Map>) bmsOperator.get("subOperationList");
            }
            //Step 3.1 普通进件信息《进件提交或暂存时的信息》
            resultMap.put(Fields.PARAM_LOAN_SERIAL_NO, paramMap.get(Fields.PARAM_LOAN_SERIAL_NO));
            resultMap.put(Fields.PARAM_BMS_LOAN_CODE, bmsResultMap.get(Fields.PARAM_BMS_LOAN_CODE));
            resultMap.put(Fields.PARAM_APPLY_TIME, bmsResultMap.get(Fields.PARAM_APPLY_TIME));
            String bmsStatusCode = bmsResultMap.get(Fields.PARAM_LOAN_STATUS) == null ? "" : bmsResultMap.get(Fields.PARAM_LOAN_STATUS).toString();//需必定输出
            String bmsPreStatusCode = bmsResultMap.get("preLoanStatus") == null ? "" : bmsResultMap.get("preLoanStatus").toString();//需必定输出
            Map nlbsStatusMap = commonService.getNlbsStatusMapByBmsStatusCode(null, bmsStatusCode, subOperationList, true);
            Map nlbsPreStatusMap = commonService.getNlbsStatusMapByBmsStatusCode(null, bmsPreStatusCode, null, false);
            resultMap.put(Fields.PARAM_BUSINESS_STATUS, nlbsStatusMap.get(Fields.PARAM_LOAN_STATUS_CODE));
            resultMap.put(Fields.PARAM_PRE_BUSINESS_STATUS, nlbsPreStatusMap.get(Fields.PARAM_LOAN_STATUS_CODE));

            resultMap.put(Fields.PARAM_DISTRIBUTRO_CODE, bmsResultMap.get(Fields.PARAM_DISTRIBUTRO_CODE));
            resultMap.put(Fields.PARAM_DISTRIBUTRO_NAME, bmsResultMap.get(Fields.PARAM_DISTRIBUTRO_NAME));
            resultMap.put(Fields.PARAM_AGENT_NAME, bmsResultMap.get("applyManageName"));
            resultMap.put(Fields.PARAM_AGENT_ID, bmsResultMap.get("bizManagerId"));
            resultMap.put(Fields.PARAM_LOAN_PERIOD_CODE, bmsResultMap.get(Fields.PARAM_LOAN_PERIOD_CODE));
            resultMap.put(Fields.PARAM_LOAN_PERIOD_NAME, bmsResultMap.get(Fields.PARAM_LOAN_PERIOD_NAME));
            resultMap.put(Fields.PARAM_CREDIT_TYPE_CODE, bmsResultMap.get(Fields.PARAM_CREDIT_TYPE_CODE));
            resultMap.put(Fields.PARAM_CREDIT_TYPE_NAME, bmsResultMap.get(Fields.PARAM_CREDIT_TYPE_NAME));
            resultMap.put(Fields.PARAM_MOBILEPHONE_VALIDATE_NO, bmsResultMap.get(Fields.PARAM_MOBILEPHONE_VALIDATE_NO));
            resultMap.put(Fields.PARAM_PRODUCT_CODE, bmsResultMap.get(Fields.PARAM_PRODUCT_CODE));
            resultMap.put(Fields.PARAM_PRODUCT_NAME, bmsResultMap.get(Fields.PARAM_PRODUCT_NAME));
            resultMap.put(Fields.PARAM_LOAN_AMOUNT, bmsResultMap.get(Fields.PARAM_LOAN_AMOUNT));
            resultMap.put(Fields.PARAM_INTENTION_MONEY, bmsResultMap.get(Fields.PARAM_INTENTION_MONEY));
            resultMap.put(Fields.PARAM_CUSTOMER_NAME, bmsResultMap.get(Fields.PARAM_CUSTOMER_NAME));
            resultMap.put(Fields.PARAM_REMARK, bmsResultMap.get(Fields.PARAM_REMARK));
            resultMap.put(Fields.PARAM_APPLY_FILE_ID, bmsResultMap.get(Fields.PARAM_FILE_ID));
            resultMap.put(Fields.PARAM_APPLY_FILE_NAME, bmsResultMap.get(Fields.PARAM_ORIGINAL_FILE_NAME));

            //Step 3.2 案件概况--后台核心系统状态为“待录单”(0022)和“初审驳回”(0021)状态时，显示案件概况；否则不显示。
            if ("0021".equals(bmsStatusCode) || "0022".equals(bmsStatusCode)) {
                resultMap.put(Fields.PARAM_DISPLAY_CASE_INFO, "Y");
                Map loanCaseUseMap = (Map) bmsResultMap.get("loanCaseUse");
                Map caseInfMap = new HashMap();
                if (loanCaseUseMap != null) {
                    caseInfMap.put(Fields.PARAM_MAIN_HOUSEHOLD_ASSET, loanCaseUseMap.get("familyAssets"));
                    caseInfMap.put(Fields.PARAM_MAIN_HOUSEHOLD_LIABILITIES, loanCaseUseMap.get("familyDebt"));
                    caseInfMap.put(Fields.PARAM_MAIN_HOUSEHOLD_INCOME_SOURCE, loanCaseUseMap.get("familyIncome"));
                    caseInfMap.put(Fields.PARAM_FIELD_INVESTIGATION_OF_COLLATERAL, loanCaseUseMap.get("mortgaged"));
                    caseInfMap.put(Fields.PARAM_USAGE_OF_LOAN, loanCaseUseMap.get("loanUse"));
                    caseInfMap.put(Fields.PARAM_PAYMENT_SOURCE, loanCaseUseMap.get("refundSource"));
                }
                resultMap.put(Fields.PARAM_CASE_INFO, caseInfMap);
            } else {
                resultMap.put(Fields.PARAM_DISPLAY_CASE_INFO, "N");
            }
            //Step 3.3 人员信息列表 && Step 3.4 借款人信息列表
            List<Map> bmsPeopleList = (List<Map>) bmsResultMap.get("loanPeopleList");
            List<Map> peopleList = new ArrayList<>();
            List<Map> borrowerList = new ArrayList<>();
            String loaners = "";//定义借款人，用顿号分隔
            if (bmsPeopleList != null) {
                for (Map bmsPeopleMap : bmsPeopleList) {
                    Map peopleMap = new HashMap();
                    boolean isBorrower = Boolean.valueOf(bmsPeopleMap.get("loaners") == null ? "false" : bmsPeopleMap.get("loaners").toString());
                    String loaner = bmsPeopleMap.get(Fields.PARAM_NAME) == null ? "" : bmsPeopleMap.get(Fields.PARAM_NAME).toString();

                    peopleMap.put(Fields.PARAM_PERSON_NAME, loaner);
                    peopleMap.put(Fields.PARAM_PERSON_NAME_USERD, bmsPeopleMap.get("usedName"));
                    peopleMap.put(Fields.PARAM_PERSON_ID_TYPE, bmsPeopleMap.get("certificateType"));
                    peopleMap.put(Fields.PARAM_PERSON_ID_NO, bmsPeopleMap.get("certificateNumber"));
                    peopleMap.put(Fields.PARAM_PERSON_AGE, bmsPeopleMap.get(Fields.PARAM_PERSON_AGE));
                    peopleMap.put(Fields.PARAM_PERSON_PERIOD_OF_VALIDITY_START, bmsPeopleMap.get("certificateValidityStart"));
                    peopleMap.put(Fields.PARAM_PERSON_PERIOD_OF_VALIDITY_END, bmsPeopleMap.get("certificateValidityEnd"));
                    peopleMap.put(Fields.PARAM_PERSON_MOBILEPHONE, bmsPeopleMap.get("cellphone"));
                    peopleMap.put(Fields.PARAM_PERSON_ORGANIZATION, bmsPeopleMap.get("workUnit"));
                    peopleMap.put(Fields.PARAM_PERSON_POSITION, bmsPeopleMap.get("post"));
                    peopleMap.put(Fields.PARAM_PERSON_ANNUAL, bmsPeopleMap.get("annualIncome"));
                    peopleMap.put(Fields.PARAM_PERSON_ADDRESS, bmsPeopleMap.get("familyAddress"));
                    //婚姻状态
                    peopleMap.put(Fields.PARAM_PERSON_MARRIAGE, bmsPeopleMap.get("maritalStatus"));
                    peopleMap.put(Fields.PARAM_PERSON_MARRIAGE_INFO, bmsPeopleMap.get("marriageHistory"));

                    //是借款人
                    if (isBorrower) {
                        loaners = StringUtils.isBlank(loaner) ? loaners : loaners + loaner + "、";
                        borrowerList.add(peopleMap);
                        //不是借款人，只是普通的人员信息
                    }
                    peopleList.add(peopleMap);
                }
            }
            loaners = StringUtils.isBlank(loaners) ? loaners : loaners.substring(0, loaners.length() - 1);
            resultMap.put(Fields.PARAM_PERSON_LIST, peopleList);
            resultMap.put(Fields.PARAM_BORROWER_LIST, borrowerList);

            //Step 3.5 抵押物信息列表&&产证信息列表
            List<Map> bmsMortgageList = (List<Map>) bmsResultMap.get("loanPawnList");
            List<Map> bmsCertificateList = (List<Map>) bmsResultMap.get("certificateList");
            List<Map> mortgageList = new ArrayList<>();
            if(bmsCertificateList != null){
                for (Map bmsCertificateMap : bmsCertificateList) {
                    Map mortgageMap = new HashMap();
                    mortgageMap.put(Fields.PARAM_CERTIFICATE_NUMBER_FIRST, bmsCertificateMap.get(Fields.PARAM_CERTIFICATE_NUMBER_FIRST));
                    mortgageMap.put(Fields.PARAM_CERTIFICATE_NUMBER_SECOND, bmsCertificateMap.get(Fields.PARAM_CERTIFICATE_NUMBER_SECOND));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_TYPE_CODE, bmsCertificateMap.get(Fields.PARAM_MORTGAGE_TYPE));//01一抵02二抵03一抵转单04二抵转单
                    mortgageList.add(mortgageMap);
                }
            }
            if (bmsMortgageList != null) {
                mortgageList.clear();//如果抵押物信息有，则清除产证列表，使用抵押物信息里的值
                for (Map bmsMortgageMap : bmsMortgageList) {
                    Map mortgageMap = new HashMap();

                    mortgageMap.put(Fields.PARAM_CERTIFICATE_NUMBER_FIRST, bmsMortgageMap.get(Fields.PARAM_CERTIFICATE_NUMBER_FIRST));
                    mortgageMap.put(Fields.PARAM_CERTIFICATE_NUMBER_SECOND, bmsMortgageMap.get(Fields.PARAM_CERTIFICATE_NUMBER_SECOND));

                    mortgageMap.put(Fields.PARAM_MORTGAGE_CERTIFICATE_NUMBER, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_CERTIFICATE_NUMBER));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_EVALUTION_PRICE, bmsMortgageMap.get("valuation"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_BUYING_TIME, bmsMortgageMap.get("purchasingDate"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_PROPERTY_OWNERS, bmsMortgageMap.get("propertyOwner"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_PROPERTY_ADDRESS, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_PROPERTY_ADDRESS));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_LAND_CHARACTERISSTICS, bmsMortgageMap.get("landProperty"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_LAND_USE, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_LAND_USE));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_ACQUISITION_METHOD, bmsMortgageMap.get("landMakeWay"));
                    mortgageMap.put(Fields.PARAM_SOURCE_OF_HOUSING_OWNERSHIP, bmsMortgageMap.get("homeOwnershipCodeName"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_BUILDING_TYPE, bmsMortgageMap.get("propertyType"));
                    mortgageMap.put(Fields.PARAM_HOUSING_USAGE, bmsMortgageMap.get(Fields.PARAM_HOUSING_USAGE));
                    boolean onlyHouse = Boolean.valueOf(bmsMortgageMap.get("onlyHouse") == null ? "false" : bmsMortgageMap.get("onlyHouse").toString());
                    mortgageMap.put(Fields.PARAM_IS_ONLY_HOUSING, onlyHouse ? "是" : "否");
                    mortgageMap.put(Fields.PARAM_HOUSEHOLD_REGISTRATION_STRUCTURE, bmsMortgageMap.get("housingStructure"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_TYPE_CODE, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_TYPE));//01一抵02二抵03一抵转单04二抵转单
                    mortgageMap.put(Fields.PARAM_MORTGAGE_TYPE_NAME, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_TYPE_NAME));
                    mortgageMap.put(Fields.PARAM_INVESTINF_FIRST_MORTGAGE_PRICE, bmsMortgageMap.get("mortgageFirstAmount"));
                    mortgageMap.put(Fields.PARAM_INVESTINF_FIRST_MORTGAGE_BALANCE_TYPE, bmsMortgageMap.get("balanceType") == null ? "" : bmsMortgageMap.get("balanceType").toString());
                    mortgageMap.put(Fields.PARAM_INVESTINF_FIRST_MORTGAGE_BALANCE, bmsMortgageMap.get("mortgageFirstBalance") == null ? "" : bmsMortgageMap.get("mortgageFirstBalance").toString());
                    mortgageMap.put(Fields.PARAM_INVESTINF_SECOND_MORTGAGE_PRICE, bmsMortgageMap.get("mortgageFirstAmount"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_STRUCTURE_AREA, bmsMortgageMap.get("coveredArea"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_GARAGE_ADDRESS, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_GARAGE_ADDRESS));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_GARAGE_AREA, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_GARAGE_AREA));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_TOTAL_FLOORS, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_TOTAL_FLOORS));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_END_DATE, bmsMortgageMap.get("completionDate"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_COMMON_TYPES, bmsMortgageMap.get("publicType"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_MINOR_SHARE, bmsMortgageMap.get("propertyMinors"));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_REMARK, bmsMortgageMap.get("pragmaticRemark"));
                    //以下内容是为了审批通知中使用
                    mortgageMap.put(Fields.PARAM_REPLY_AMOUNT, bmsMortgageMap.get("approvalAmount"));//批复金额,额度
                    mortgageMap.put(Fields.PARAM_SECURED_AMOUNT, bmsMortgageMap.get("guaranteeAmount"));//单个的担保额度
                    mortgageMap.put(Fields.PARAM_YEAR_RATE, bmsMortgageMap.get("interestRate"));//--年化利率
                    mortgageMap.put(Fields.PARAM_MORTGAGE_RATE, bmsMortgageMap.get(Fields.PARAM_MORTGAGE_RATE));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_ASSESS_AMOUNT, bmsMortgageMap.get("contractPawnPrice"));//合同抵押物价值
                    mortgageMap.put(Fields.PARAM_TOTAL_DEBT_AMOUNT, bmsMortgageMap.get("pawnMortgageAmount"));//抵押权债权总额
                    mortgageMap.put(Fields.PARAM_REPLY_TIME_LIMIT, bmsMortgageMap.get("approvalLimit"));//批复期限

                    //Step 3.5.1 产调信息列表
                    List<Map> bmsInvestinfList = null;//产调信息列表
                    List<Map> investinfList = new ArrayList();
                    bmsInvestinfList = (List<Map>) bmsMortgageMap.get("propertyAdjustmentInfoList");
                    if (bmsInvestinfList != null) {
                        for (Map bmsInvestinfMap : bmsInvestinfList) {
                            Map investinfMap = new HashMap();
                            investinfMap.put(Fields.PARAM_INVESTINF_INVEST_TIME, bmsInvestinfMap.get("propertyTransferDate"));
                            investinfMap.put(Fields.PARAM_INVESTINF_DEALING_CASE, bmsInvestinfMap.get("inCaseInfo"));
                            investinfMap.put(Fields.PARAM_MORTGAGE_RANK, bmsInvestinfMap.get("mortgageWeight"));
                            investinfMap.put(Fields.PARAM_CREDITOR_RIGHT_TYPE, bmsInvestinfMap.get(Fields.PARAM_MORTGAGE_TYPE));
                            investinfMap.put(Fields.PARAM_CREDITOR, bmsInvestinfMap.get("mortgagePeople"));
                            //investinfMap.put(Fields.PARAM_DEBT_AMOUNT, debtAmountRecord);
                            investinfMap.put(Fields.PARAM_CREDITOR_RIGHT_NATURE, bmsInvestinfMap.get("mortgageProperty"));//债权性质
                            investinfMap.put(Fields.PARAM_PECUNIA_CREDITA, bmsInvestinfMap.get("mortgagePrice"));
                            investinfList.add(investinfMap);
                        }
                        //按照产调时间倒序排序
                        CommonUtil.listSort(investinfList, Fields.PARAM_INVESTINF_INVEST_TIME, false);
                    }
                    mortgageMap.put(Fields.PARAM_MORTGAGE_INVESTINF_LIST, investinfList);

                    //Step 3.5.2 户口信息列表
                    List<Map> bmsResidenceInfList = null;//户口信息列表
                    List residenceInfList = new ArrayList();
                    bmsResidenceInfList = (List<Map>) bmsMortgageMap.get("householdInfoList");
                    if (bmsResidenceInfList != null) {
                        for (Map bmsResidenceInfMap : bmsResidenceInfList) {
                            Map residenceInfMap = new HashMap();
                            residenceInfMap.put(Fields.PARAM_RESIDENCEINF_RESIDENCE_NAME, bmsResidenceInfMap.get(Fields.PARAM_NAME));
                            residenceInfMap.put(Fields.PARAM_RESIDENCEINF_RESIDENCEID_NO, bmsResidenceInfMap.get("idCardNum"));
                            residenceInfList.add(residenceInfMap);
                        }
                    }
                    mortgageMap.put(Fields.PARAM_MORTGAGE_RESIDENCEINF_LIST, residenceInfList);
                    mortgageList.add(mortgageMap);
                }
            }
            resultMap.put(Fields.PARAM_MORTGAGE_LIST, mortgageList);
            //Step 3.6 备用房信息
            List<Map> bmsSpareHouseList = (List<Map>) bmsResultMap.get("loanSepraroomList");
            List<Map> spareHouseList = new ArrayList<>();
            if (bmsSpareHouseList != null) {
                for (Map bmsSpareHouseMap : bmsSpareHouseList) {
                    Map spareHouseMap = new HashMap();
                    spareHouseMap.put(Fields.PARAM_SPARE_HOUSE_OWNER, bmsSpareHouseMap.get("propertyOwner"));
                    spareHouseMap.put(Fields.PARAM_SPARE_HOUSE_ADDRESS, bmsSpareHouseMap.get("propertyAddress"));
                    spareHouseList.add(spareHouseMap);
                }
            }
            resultMap.put(Fields.PARAM_SPARE_HOUSE_LIST, spareHouseList);

            //Step 3.8 征信信息列表
            List<Map> bmsLoanCreditInfoList = (List<Map>) bmsResultMap.get("loanCreditInfoList");
            List<Map> creditInfList = new ArrayList<Map>();
            if (bmsLoanCreditInfoList != null && bmsLoanCreditInfoList.size() > 0) {
                for (Map bmsLoanCreditInfoMap : bmsLoanCreditInfoList) {
                    Map creditInfMap = new HashMap();
                    creditInfMap.put(Fields.PARAM_CREDITINF_NAME, bmsLoanCreditInfoMap.get(Fields.PARAM_NAME));
                    creditInfMap.put(Fields.PARAM_CREDITINF_REPORT_TIME, bmsLoanCreditInfoMap.get("creditReportDate"));
                    creditInfMap.put(Fields.PARAM_CREDITINF_LOAN_COUNT, bmsLoanCreditInfoMap.get("outstandLoanCount"));//未结清贷款笔数
                    creditInfMap.put(Fields.PARAM_CREDITINF_TOTAL_CONTRACT, bmsLoanCreditInfoMap.get("outstandLoanSum"));//未结清贷款合同总额
                    creditInfMap.put(Fields.PARAM_CREDITINF_BALANCE, bmsLoanCreditInfoMap.get("outstandLoanBalance"));//未结清贷款余额
                    creditInfMap.put(Fields.PARAM_CREDITINF_SIXMONTH_AVERAGE_QUOTA_REPAY, bmsLoanCreditInfoMap.get("outstandLoanMonthAvgRepay"));//未结清贷款最近6个月平均应还款
                    creditInfMap.put(Fields.PARAM_CREDITINF_ACCOUNT_NUM, bmsLoanCreditInfoMap.get("usableCreditCardCount"));
                    creditInfMap.put(Fields.PARAM_CREDITINF_TOTAL_CREDIT, bmsLoanCreditInfoMap.get("usableCardSum"));
                    creditInfMap.put(Fields.PARAM_CREDITINF_QUOTA_USED, bmsLoanCreditInfoMap.get("usableCardQuotaUsed"));
                    creditInfMap.put(Fields.PARAM_CREDITINF_SIXMONTH_AVERAGE_QUOTA_USED, bmsLoanCreditInfoMap.get("usableCardMonthQuotaUsed"));

                    creditInfMap.put(Fields.PARAM_CREDITINF_THREEMONTH_QUERY_COUNT, bmsLoanCreditInfoMap.get("findThreemonthCount"));
                    creditInfMap.put(Fields.PARAM_CREDITINF_AFTER_LOAN_MANAGE_COUNT, bmsLoanCreditInfoMap.get("loanAfterCount"));
                    creditInfMap.put(Fields.PARAM_CREDITINF_SECURED_CHECKUP_COUNT, bmsLoanCreditInfoMap.get("guaranteeSeniorityCount"));
                    creditInfMap.put(Fields.PARAM_CREDITINF_CREDIT_INFO_REMARK, bmsLoanCreditInfoMap.get("remark"));


                    //贷款信息列表
                    List<Map> bmsLoanInfoList = (List<Map>) bmsLoanCreditInfoMap.get("loanInfoList");
                    List loanInfoList = new ArrayList();
                    if (bmsLoanInfoList != null && bmsLoanInfoList.size() > 0) {
                        for (Map bmsLoanInfoMap : bmsLoanInfoList) {
                            Map bmsCurrentOverdueLoanMap = (Map) bmsLoanInfoMap.get("currentOverdueLoan");
                            if (bmsCurrentOverdueLoanMap != null) {
                                Map currentOverdueLoanMap = new HashMap();
                                currentOverdueLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, bmsCurrentOverdueLoanMap.get("serialNo"));
                                currentOverdueLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "当前逾期");
                                currentOverdueLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, bmsCurrentOverdueLoanMap.get("overdueAmount"));
                                loanInfoList.add(currentOverdueLoanMap);
                            }

                            Map bmsAberrantLoanMap = (Map) bmsLoanInfoMap.get("aberrantLoan");
                            if (bmsAberrantLoanMap != null) {
                                Map aberrantLoanMap = new HashMap();
                                aberrantLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, bmsAberrantLoanMap.get("serialNo"));
                                aberrantLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "非正常类贷款");
                                aberrantLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, bmsAberrantLoanMap.get("status"));
                                loanInfoList.add(aberrantLoanMap);
                            }

                            Map bmsTopOverdueCountLoanMap = (Map) bmsLoanInfoMap.get("topOverdueCountLoan");
                            if (bmsTopOverdueCountLoanMap != null) {
                                Map topOverdueCountLoanMap = new HashMap();
                                topOverdueCountLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, bmsTopOverdueCountLoanMap.get("serialNo"));
                                topOverdueCountLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "近12个月累计最高逾期次数");
                                topOverdueCountLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, bmsTopOverdueCountLoanMap.get("overdueCount"));
                                loanInfoList.add(topOverdueCountLoanMap);
                            }

                            Map bmsTopContinuousOverdueLoanMap = (Map) bmsLoanInfoMap.get("topContinuousOverdueLoan");
                            if (bmsTopContinuousOverdueLoanMap != null) {
                                Map topContinuousOverdueLoanMap = new HashMap();
                                topContinuousOverdueLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, bmsTopContinuousOverdueLoanMap.get("serialNo"));
                                topContinuousOverdueLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "近12个月最高连续逾期期数");
                                topContinuousOverdueLoanMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, bmsTopContinuousOverdueLoanMap.get("continuityOverduePeriods"));
                                loanInfoList.add(topContinuousOverdueLoanMap);
                            }
                        }
                    }

                    creditInfMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_LIST, loanInfoList);

                    //贷记卡信息列表
                    List<Map> bmsLoanCardInfoList = (List<Map>) bmsLoanCreditInfoMap.get("creditCardInfos");
                    List loanInfoCardList = new ArrayList();
                    if (bmsLoanCardInfoList != null && bmsLoanCardInfoList.size() > 0) {
                        for (Map bmsLoanCardInfoMap : bmsLoanCardInfoList) {
                            Map bmsCardCurrentOverdueMap = (Map) bmsLoanCardInfoMap.get("cardCurrentOverdue");
                            if (bmsCardCurrentOverdueMap != null) {
                                Map cardCurrentOverdueMap = new HashMap();
                                cardCurrentOverdueMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, bmsCardCurrentOverdueMap.get("serialNo"));
                                cardCurrentOverdueMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "当前逾期");
                                cardCurrentOverdueMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, bmsCardCurrentOverdueMap.get("overdueAmount"));
                                loanInfoCardList.add(cardCurrentOverdueMap);
                            }

                            Map bmsCardAbnormalMap = (Map) bmsLoanCardInfoMap.get("cardAbnormal");
                            if (bmsCardAbnormalMap != null && bmsCardAbnormalMap.size() > 0) {
                                Map cardAbnormalMap = new HashMap();
                                cardAbnormalMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, bmsCardAbnormalMap.get("serialNo"));
                                cardAbnormalMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "非正常类贷记卡");
                                cardAbnormalMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, bmsCardAbnormalMap.get("loanStatus"));
                                loanInfoCardList.add(cardAbnormalMap);
                            }

                            Map bmsCardTotalOverdueCountMap = (Map) bmsLoanCardInfoMap.get("cardTotalOverdueCount");
                            if (bmsCardTotalOverdueCountMap != null && bmsCardTotalOverdueCountMap.size() > 0) {
                                Map cardTotalOverdueCountMap = new HashMap();
                                cardTotalOverdueCountMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, bmsCardTotalOverdueCountMap.get("serialNo"));
                                cardTotalOverdueCountMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "近12个月累计最高逾期次数");
                                cardTotalOverdueCountMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, bmsCardTotalOverdueCountMap.get("overdueCount"));
                                loanInfoCardList.add(cardTotalOverdueCountMap);
                            }

                            Map bmsCardContinuityOverdueCountMap = (Map) bmsLoanCardInfoMap.get("cardContinuityOverdueCount");
                            if (bmsCardContinuityOverdueCountMap != null && bmsCardContinuityOverdueCountMap.size() > 0) {
                                Map cardContinuityOverdueCountMap = new HashMap();
                                cardContinuityOverdueCountMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_SERIALNO, bmsCardContinuityOverdueCountMap.get("serialNo"));
                                cardContinuityOverdueCountMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_TYPE, "近12个月最高连续逾期期数");
                                cardContinuityOverdueCountMap.put(Fields.PARAM_CREDITINF_LOAN_INFO_DETAILS, bmsCardContinuityOverdueCountMap.get("continuityOverduePeriods"));
                                loanInfoCardList.add(cardContinuityOverdueCountMap);
                            }
                        }
                    }

                    creditInfMap.put(Fields.PARAM_CREDITINF_LOAN_CARD_INFO_LIST, loanInfoCardList);

                    //担保信息列表
                    List<Map> bmsGuaranteeList = (List<Map>) bmsLoanCreditInfoMap.get("loanGuaranteeInfos");
                    List<Map> guaranteeList = new ArrayList<Map>();
                    if (bmsGuaranteeList != null && bmsGuaranteeList.size() > 0) {
                        for (Map bmsGuaranteeMap : bmsGuaranteeList) {
                            Map guaranteeMap = new HashMap();
                            guaranteeMap.put(Fields.PARAM_CREDITINF_SECURED_STATUS, bmsGuaranteeMap.get("guaranteeStatus"));
                            guaranteeMap.put(Fields.PARAM_CREDITINF_SECURED_PRICE, bmsGuaranteeMap.get("guaranteeAmount"));
                            guaranteeMap.put(Fields.PARAM_CREDITINF_SECURED_BALANCE, bmsGuaranteeMap.get("guaranteeBalance"));
                            guaranteeList.add(guaranteeMap);
                        }
                        creditInfMap.put(Fields.PARAM_CREDITINF_GUARANTEE_LIST, guaranteeList);
                    }
                    creditInfList.add(creditInfMap);
                }
            }
            resultMap.put(Fields.PARAM_CREDITINF_LIST, creditInfList);
            //Step 3.9 司法信息列表
            List<Map> bmsJudicialInfList = (List<Map>) bmsResultMap.get("loanJsuticeInfoList");
            List<Map> judicialInfList = new ArrayList<>();
            if (bmsJudicialInfList != null && bmsJudicialInfList.size() > 0) {
                for (Map bmsJudicialInfMap : bmsJudicialInfList) {
                    Map judicialInfMap = new HashMap();
                    judicialInfMap.put(Fields.PARAM_JUDICIALINF_NAME, bmsJudicialInfMap.get(Fields.PARAM_NAME));

                    judicialInfMap.put(Fields.PARAM_JUDICIALINF_LITIGATION, "1".equals(bmsJudicialInfMap.get("justiceInfoCode")) ? "是" : "否");//是否有司法诉讼
                    judicialInfMap.put(Fields.PARAM_JUDICIALINF_REGISTRATION_NO, bmsJudicialInfMap.get("caseNo"));
                    judicialInfMap.put(Fields.PARAM_JUDICIALINF_EXECUTE_TARGET, bmsJudicialInfMap.get("subjectExecution"));
                    judicialInfMap.put(Fields.PARAM_JUDICIALINF_IS_CLOSED, "1".equals(bmsJudicialInfMap.get("flagLawsuitCode")) ? "是" : "否");//是否结案
                    judicialInfMap.put(Fields.PARAM_JUDICIALINF_STATUS_STATEMENT, bmsJudicialInfMap.get("infoNote"));//情况说明
                    judicialInfList.add(judicialInfMap);
                }
            }
            resultMap.put(Fields.PARAM_JUDICIALINF_LIST, judicialInfList);

            //Step 3.10 审批通知信息--来自BMS返回的抵押物信息列表和风控审批信息
            Map approvalNoticeMap = new HashMap();
            //Step 3.10.1 取自3.5的抵押物信息列表
            List approvalMortgageList = new ArrayList<>();
            if (mortgageList != null) {
                for (Map appMortgageMap : mortgageList) {
                    Map mortgageMap = new HashMap();
                    mortgageMap.put(Fields.PARAM_MORTGAGE_CERTIFICATE_NUMBER, appMortgageMap.get(Fields.PARAM_MORTGAGE_CERTIFICATE_NUMBER));
                    mortgageMap.put(Fields.PARAM_EVALUATION_PRICE, appMortgageMap.get(Fields.PARAM_MORTGAGE_EVALUTION_PRICE));
                    mortgageMap.put(Fields.PARAM_REPLY_AMOUNT, appMortgageMap.get(Fields.PARAM_REPLY_AMOUNT));//批复金额,额度
                    mortgageMap.put(Fields.PARAM_SECURED_AMOUNT, appMortgageMap.get(Fields.PARAM_SECURED_AMOUNT));//单个的担保额度
                    mortgageMap.put(Fields.PARAM_YEAR_RATE, appMortgageMap.get(Fields.PARAM_YEAR_RATE));//--年化利率
                    mortgageMap.put(Fields.PARAM_MORTGAGE_TYPE_NAME, appMortgageMap.get(Fields.PARAM_MORTGAGE_TYPE_NAME));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_RATE, appMortgageMap.get(Fields.PARAM_MORTGAGE_RATE));
                    mortgageMap.put(Fields.PARAM_MORTGAGE_ASSESS_AMOUNT, appMortgageMap.get(Fields.PARAM_MORTGAGE_ASSESS_AMOUNT));//合同抵押物价值
                    mortgageMap.put(Fields.PARAM_PECUNIA_CREDITA, appMortgageMap.get(Fields.PARAM_TOTAL_DEBT_AMOUNT));//--债权金额
                    approvalNoticeMap.put(Fields.PARAM_REPLY_TIME_LIMIT, appMortgageMap.get(Fields.PARAM_REPLY_TIME_LIMIT));//批复期限
                    approvalMortgageList.add(mortgageMap);
                }
            }
            approvalNoticeMap.put(Fields.PARAM_MORTGAGE_LIST, approvalMortgageList);
            approvalNoticeMap.put(Fields.PARAM_PRODUCT_NAME, bmsResultMap.get(Fields.PARAM_PRODUCT_NAME));
            approvalNoticeMap.put(Fields.PARAM_AGENT_NAME, bmsResultMap.get("applyManageName"));
            //Step 3.10.2 风控审批信息
            Map bmsApprovalMap = (Map) bmsResultMap.get("loanRiskInfo");//风控审批信息
            if (bmsApprovalMap != null) {
                approvalNoticeMap.put(Fields.PARAM_APPROVAL_DATE, bmsApprovalMap.get(Fields.PARAM_APPROVAL_DATE));
                approvalNoticeMap.put(Fields.PARAM_LOANERS, loaners);//借款人，多个用顿号分隔
                approvalNoticeMap.put(Fields.PARAM_REPLY_AMOUNT, bmsApprovalMap.get("totalApprovalAmount"));//批复额度
                approvalNoticeMap.put(Fields.PARAM_SECURED_AMOUNT, bmsApprovalMap.get("guaranteeAmount"));//担保总额
                approvalNoticeMap.put(Fields.PARAM_SECURED_FEE, bmsApprovalMap.get("guaranteeFee"));//担保费
                approvalNoticeMap.put(Fields.PARAM_SECURED_RATE, bmsApprovalMap.get("guaranteeFeeRatio"));//担保费率
                approvalNoticeMap.put(Fields.PARAM_CASH_DEPOSIT_RATE, bmsApprovalMap.get("marginRatio"));//保证金比例
                approvalNoticeMap.put(Fields.PARAM_CASH_DEPOSIT_AMOUNT, bmsApprovalMap.get("depositAmount"));//保证金金额
                BigDecimal totalSeriveAmount = new BigDecimal(BigInteger.ZERO);
                if (subOperationList != null && subOperationList.size() > 0) {
                    for(Map subOperationMap : subOperationList){
                        Map registrationMap = (Map) subOperationMap.get("registration");
                        if(registrationMap != null){
                            String seriveAmount = registrationMap.get("serviceAmount") == null ? "0" : registrationMap.get("serviceAmount").toString();
                            totalSeriveAmount = totalSeriveAmount.add(new BigDecimal(seriveAmount));
                        }
                    }
                    approvalNoticeMap.put(Fields.PARAM_SERVICE_CHARGE, totalSeriveAmount);//服务费
                }
                approvalNoticeMap.put(Fields.PARAM_OFFER_LOANS_CONDITION, bmsApprovalMap.get("lendingTerms"));//放款条件
                approvalNoticeMap.put(Fields.PARAM_SECURED_CONDITION, bmsApprovalMap.get("guaranteeTerms"));//担保条件
                approvalNoticeMap.put(Fields.PARAM_FIRST_TRIAL_OPINION, bmsApprovalMap.get("firstOpinion"));//风控初审意见
                approvalNoticeMap.put(Fields.PARAM_RETRIAL_OPINION, bmsApprovalMap.get("secondOpinion"));//风控复审意见
                approvalNoticeMap.put(Fields.PARAM_FINAL_TRIAL_OPINION, bmsApprovalMap.get("thirdOpinion"));//风控终审意见

                //补全只有一套抵押物时，map的头的信息，供前端使用
                if (mortgageList != null && mortgageList.size() == 1) {
                    approvalNoticeMap.put(Fields.PARAM_YEAR_RATE, mortgageList.get(0).get(Fields.PARAM_YEAR_RATE));//--年化利率
                    approvalNoticeMap.put(Fields.PARAM_EVALUATION_PRICE, mortgageList.get(0).get(Fields.PARAM_MORTGAGE_EVALUTION_PRICE));//--估值
                    approvalNoticeMap.put(Fields.PARAM_MORTGAGE_TYPE_NAME, mortgageList.get(0).get(Fields.PARAM_MORTGAGE_TYPE_NAME));
                    approvalNoticeMap.put(Fields.PARAM_PECUNIA_CREDITA, mortgageList.get(0).get(Fields.PARAM_TOTAL_DEBT_AMOUNT));//--债权金额
                    approvalNoticeMap.put(Fields.PARAM_MORTGAGE_ASSESS_AMOUNT, mortgageList.get(0).get(Fields.PARAM_MORTGAGE_ASSESS_AMOUNT));//合同抵押物价值
                    approvalNoticeMap.put(Fields.PARAM_MORTGAGE_RATE, bmsMortgageList.get(0).get(Fields.PARAM_MORTGAGE_RATE));//综合抵押率（单个抵押物时，只有头有，多个时叫抵押率）
                }
            }
            resultMap.put(Fields.PARAM_APPROVAL_NOTICE, approvalNoticeMap);
            //Step 3.11 签约公证信息
            if (bmsOperator != null) {//3.7已获取
                Map bmsSigningMap = (Map) bmsOperator.get("signing");
                Map signingNotarizationMap = new HashMap();
                if (bmsSigningMap != null) {
                    signingNotarizationMap.put(Fields.PARAM_SIGN_TIME, bmsSigningMap.get("signingApproveTime"));
                    signingNotarizationMap.put(Fields.PARAM_SIGN_REMARK, bmsSigningMap.get("signingRemark"));
                }
                Map bmsNotarizationMap = (Map) bmsOperator.get("notarization");
                if (bmsNotarizationMap != null) {
                    signingNotarizationMap.put(Fields.PARAM_MORTGAGE_TIME, bmsNotarizationMap.get("notarizationApproveTime"));
                    signingNotarizationMap.put(Fields.PARAM_MORTGAGE_REMARK, bmsNotarizationMap.get("notarizationRemark"));
                }

                //默认抵押材料和产调查询材料均不展示
                signingNotarizationMap.put(Fields.PARAM_HAVE_GUARANTEE_COMPANY, "N");
                signingNotarizationMap.put(Fields.PARAM_HAVE_SECURE_COMPANY, "N");

                List<Map> bmsPeratingArchiveList = (List<Map>) bmsResultMap.get("peratingArchiveList");
                List<Map> signFileList = new ArrayList<>();//16-签约公证材料
                List<Map> guaranteeFileList = new ArrayList<>();//17-担保材料
                List<Map> secureFileList = new ArrayList<>();//18-保险材料
                List<Map> mortgageFileList = new ArrayList<>();//19-抵押材料
                List<Map> investFileList = new ArrayList<>();//20-产调查询材料

                if(bmsPeratingArchiveList != null && bmsPeratingArchiveList.size() > 0){
                    //先批量获取文件详情
                    List<Map> serialNoList = new ArrayList<>();
                    for(Map bmsPeratingArchiveMap : bmsPeratingArchiveList){
                        Object bmsFileId = bmsPeratingArchiveMap.get(Fields.PARAM_FILE_ID);
                        Map serialMap = new HashMap();
                        serialMap.put(Fields.PARAM_SERIAL_NO, bmsFileId);
                        serialNoList.add(serialMap);
                    }
                    //批量获取文件的详细信息
                    Map forFmsParamMap = new HashMap();
                    Map serialNoListParamMap = new HashMap();
                    serialNoListParamMap.put(Fields.PARAM_SERIAL_NO_LIST, serialNoList);
                    forFmsParamMap.put(Fields.PARAM_MESSAGE_HEAD, new HashMap<>());
                    forFmsParamMap.put(Fields.PARAM_MESSAGE_BODY, serialNoListParamMap);
                    Map fmsReturnMap = getUrlList(forFmsParamMap);
                    if (fmsReturnMap != null) {
                        Map fmsBodyReturnMap = (Map) fmsReturnMap.get(Fields.PARAM_MESSAGE_BODY);
                        if (fmsBodyReturnMap != null) {
                            List<Map> fmsFileList = (List<Map>) fmsBodyReturnMap.get("fileMaps");
                            if (fmsFileList != null && fmsFileList.size() > 0) {
                                for(Map fmsFileMap : fmsFileList) {
                                    Object fmsFileId = fmsFileMap.get(Fields.PARAM_SERIAL_NO);
                                    Object fmsFileName = fmsFileMap.get(Fields.PARAM_FILE_NAME);
                                    Object fmsFileUploadTime = fmsFileMap.get(Fields.PARAM_UPLOAD_TIME);
                                    Map fileMap = new HashMap();
                                    fileMap.put(Fields.PARAM_FILE_NAME, fmsFileName);
                                    fileMap.put(Fields.PARAM_FILE_ID, fmsFileId);
                                    fileMap.put(Fields.PARAM_UPLOAD_TIME, fmsFileUploadTime);
                                    for (Map bmsPeratingArchiveMap : bmsPeratingArchiveList) {
                                        String fileId = bmsPeratingArchiveMap.get("fileId") == null ? "" : bmsPeratingArchiveMap.get("fileId").toString();
//                                        String fileName = bmsPeratingArchiveMap.get("fileName") == null ? "" : bmsPeratingArchiveMap.get("fileName").toString();
//                                        fileMap.put(Fields.PARAM_FILE_NAME, fileName);
                                        if (fileId.equals(fmsFileId)) {
                                            String fileType = bmsPeratingArchiveMap.get("fileType") == null ? "" : bmsPeratingArchiveMap.get("fileType").toString();
                                            if ("16".equals(fileType)) {
                                                signFileList.add(fileMap);
                                            } else if ("17".equals(fileType)) {
                                                signingNotarizationMap.put(Fields.PARAM_HAVE_GUARANTEE_COMPANY, "Y");
                                                guaranteeFileList.add(fileMap);
                                            } else if ("18".equals(fileType)) {
                                                signingNotarizationMap.put(Fields.PARAM_HAVE_SECURE_COMPANY, "Y");
                                                secureFileList.add(fileMap);
                                            } else if ("19".equals(fileType)) {
                                                mortgageFileList.add(fileMap);
                                            } else if ("20".equals(fileType)) {
                                                investFileList.add(fileMap);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                signingNotarizationMap.put(Fields.PARAM_SIGN_FILE_LIST, signFileList);
                signingNotarizationMap.put(Fields.PARAM_GUARANTEE_FILE_LIST, guaranteeFileList);
                signingNotarizationMap.put(Fields.PARAM_SECURE_FILE_LIST, secureFileList);
                signingNotarizationMap.put(Fields.PARAM_MORTGAGE_FILE_LIST, mortgageFileList);
                signingNotarizationMap.put(Fields.PARAM_INVEST_FILE_LIST, investFileList);


                resultMap.put(Fields.PARAM_SIGNING_NOTARIZATION, signingNotarizationMap);
            }
            //Step 3.7 放款账户信息 &&   //Step 3.12 放款信息
            if (subOperationList != null && subOperationList.size() > 0) {
                Map loanAccountMap = new HashMap();
                List<Map> loanInfoList = new ArrayList<>();
                for(Map subOperationMap : subOperationList){
                    loanAccountMap.put(Fields.PARAM_LOAN_ACCOUNT, subOperationMap.get("lendingAcount"));
                    loanAccountMap.put(Fields.PARAM_LOAN_ACCOUNT_NAME, subOperationMap.get("lendingAcountName"));
                    loanAccountMap.put(Fields.PARAM_LOAN_ACCOUNT_BANK, subOperationMap.get("lendingBank"));

                    Map loanInfoMap = new HashMap();
                    loanInfoMap.put(Fields.PARAM_LOAN_TIME, subOperationMap.get("creditTime"));
                    loanInfoMap.put(Fields.PARAM_LOAN_AMOUNT, subOperationMap.get("creditAmount"));
                    loanInfoList.add(loanInfoMap);
                }
                resultMap.put(Fields.PARAM_LOAN_ACCOUNT_INFO, loanAccountMap);
                resultMap.put(Fields.PARAM_LOAN_INFO_LIST, loanInfoList);
            }

            //Step 3.13 材料类型和文件列表
            // 获取材料类型
            Map getMaterialTypeMap = queryAllMaterialTypeList();
            List<Map> materialTypeList = new ArrayList<>();
            if (ReturnCode.SUCCESS_CODE.equals(getMaterialTypeMap.get(Fields.PARAM_MESSAGE_ERR_CODE))) {
                materialTypeList = (List<Map>) getMaterialTypeMap.get(Fields.PARAM_MATERIAL_TYPE_LIST);
                boolean needFillBlanks = true;//是否需要给进件材料填充空
                if (materialTypeList != null && materialTypeList.size() > 0) {
                    //获取核心系统返回的材料附件列表
                    List<Map> loanAttachList = (List<Map>) bmsResultMap.get("loanAttachList");
                    if (loanAttachList != null && loanAttachList.size() > 0) {
                        List<Map> serialNoList = new ArrayList<>();
                        for (Map bmsLoanAttachMap : loanAttachList) {
                            //获取核心系统返回的材料附件列表中的材料类型和每个类型下的文件列表
                            Object bmsFileId = bmsLoanAttachMap.get(Fields.PARAM_FILE_ID);
                            Map serialMap = new HashMap();
                            serialMap.put(Fields.PARAM_SERIAL_NO, bmsFileId);
                            serialNoList.add(serialMap);
                        }
                        //批量获取文件的详细信息
                        Map forFmsParamMap = new HashMap();
                        Map serialNoListParamMap = new HashMap();
                        serialNoListParamMap.put(Fields.PARAM_SERIAL_NO_LIST, serialNoList);
                        forFmsParamMap.put(Fields.PARAM_MESSAGE_HEAD, new HashMap<>());
                        forFmsParamMap.put(Fields.PARAM_MESSAGE_BODY, serialNoListParamMap);
                        Map fmsReturnMap = getUrlList(forFmsParamMap);
                        if (fmsReturnMap != null) {
                            Map fmsBodyReturnMap = (Map) fmsReturnMap.get(Fields.PARAM_MESSAGE_BODY);
                            if (fmsBodyReturnMap != null) {
                                List<Map> fmsFileList = (List<Map>) fmsBodyReturnMap.get("fileMaps");
                                if (fmsFileList != null && fmsFileList.size() > 0) {
                                    needFillBlanks = false;
                                    for (Map materialTypeMap : materialTypeList) {
                                        List<Map> fileList = new ArrayList<>();
                                        String fileType = materialTypeMap.get(Fields.PARAM_MATERIAL_TYPE_CODE) == null ? "" : materialTypeMap.get(Fields.PARAM_MATERIAL_TYPE_CODE).toString();
                                        for (Map bmsLoanAttachMap : loanAttachList) {
                                            Object bmsFileId = bmsLoanAttachMap.get(Fields.PARAM_FILE_ID);
                                            Object bmsFileType = bmsLoanAttachMap.get(Fields.PARAM_FILE_TYPE);
                                            Object bmsFileName = bmsLoanAttachMap.get(Fields.PARAM_FILE_NAME);
                                            if (fileType.equals(bmsFileType)) {
                                                for (Map fmsFileMap : fmsFileList) {
                                                    if (fmsFileMap.get(Fields.PARAM_SERIAL_NO).equals(bmsFileId)) {
                                                        Map fileMap = new HashMap();
                                                        fileMap.put(Fields.PARAM_FILE_ID, fmsFileMap.get(Fields.PARAM_SERIAL_NO));
                                                        fileMap.put(Fields.PARAM_ORIGINAL_FILE_NAME, fmsFileMap.get(Fields.PARAM_FILE_NAME));
                                                        fileMap.put(Fields.PARAM_UPLOAD_TIME, fmsFileMap.get(Fields.PARAM_UPLOAD_TIME));
                                                        fileMap.put(Fields.PARAM_FILE_TYPE, bmsFileType);
                                                        fileList.add(fileMap);
                                                    }
                                                }
                                            }
                                        }
                                        materialTypeMap.put(Fields.PARAM_FILE_SIZE, fileList.size() + "");
                                        materialTypeMap.put(Fields.PARAM_FILE_LIST, fileList);
                                    }
                                }
                            }
                        }
                    }
                    if(needFillBlanks){
                        for (Map materialTypeMap : materialTypeList) {
                            materialTypeMap.put(Fields.PARAM_FILE_SIZE, "0");
                            materialTypeMap.put(Fields.PARAM_FILE_LIST, new ArrayList<>());
                        }
                    }
                }
                resultMap.put(Fields.PARAM_MATERIAL_TYPE_LIST, materialTypeList);
            }

            resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "查询成功");
        } else {
            throw new HHBizException(bmsResultMap.get(Fields.PARAM_MESSAGE_ERR_CODE).toString(), "查询详情失败！");
        }

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
        String loanStatusCode = null != paramMap.get(Fields.PARAM_LOAN_STATUS_CODE) ? paramMap.get(Fields.PARAM_LOAN_STATUS_CODE).toString() : "";
        String userNo = null != paramMap.get(Fields.PARAM_USER_NO) ? paramMap.get(Fields.PARAM_USER_NO).toString() : "";
        Map returnMap = new HashMap();

        //Step 1 判断是否是超级管理员
        boolean beAdminFlag = false;
        List<String> roleList = new ArrayList<String>();
        roleList.add(Constants.ROLE_SUPPER_MANAGER);
        beAdminFlag = commonService.isAdministrator(userNo, roleList);

        //Step 2 获取当前用户及其所有下级用户
        NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
        List<Map> agentList = new ArrayList<>();
        if (nlbsLoginInfo != null) {
            agentList = commonService.queryBmsAgentsIncludeItself(nlbsLoginInfo.getAgentId());
        }
        paramMap.put(Fields.PARAM_AGENT_LIST, agentList);

        //Step 3 状态的切换，切换为BMS的状态码
        List<NlbsApplyLoanStatus> nlbsApplyLoanStatusList = nlbsApplyLoanStatusMapper.queryApplyStatusMap();
        List<Map> statusList = new ArrayList<>();
        if (StringUtils.isNotBlank(loanStatusCode)) {
            statusList = commonService.getBmsStatusListByNlbsStatusCode(nlbsApplyLoanStatusList, loanStatusCode);
        }
        paramMap.put(Fields.PARAM_STATUS_LIST, statusList);

        //Step 2 切换数据源到核心系统，并获取进件列表
        List<Map> applyList = new ArrayList<>();
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        PageHelper.startPage(pageNo, pageSize);
        if (beAdminFlag) {
            applyList = bmsLoanMasterDao.queryAllApplyList(paramMap);
        } else {
            applyList = bmsLoanMasterDao.queryApplyList(paramMap);
        }
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        PageInfo pageInfo = new PageInfo(applyList);

        //Step 3 整理出参，包括城市和状态码转换
        if (nlbsApplyLoanStatusList != null) {
            for (Map applyMap : applyList) {
                String bmsStatus = applyMap.get(Fields.PARAM_LOAN_STATUS_CODE) == null ? "" : applyMap.get(Fields.PARAM_LOAN_STATUS_CODE).toString();
                Map nlbsStatusMap = commonService.getNlbsStatusMapByBmsStatusCode(nlbsApplyLoanStatusList, bmsStatus, null, false);
                if (nlbsStatusMap != null) {
                    applyMap.put(Fields.PARAM_LOAN_STATUS_CODE, nlbsStatusMap.get(Fields.PARAM_LOAN_STATUS_CODE));
                    applyMap.put(Fields.PARAM_LOAN_STATUS_NAME, nlbsStatusMap.get(Fields.PARAM_LOAN_STATUS_NAME));
                }
            }
        }

        returnMap.put(Fields.PARAM_APPLY_LOAN_LIST, applyList);
        returnMap.put(Fields.PARAM_PAGES, pageInfo.getPages());
        returnMap.put(Fields.PARAM_TOTAL, pageInfo.getTotal());
        returnMap.put(Fields.PARAM_CURRENT_PAGE, pageInfo.getPageNum());

        //测试数据
        //returnMap = getTestData(pageNo, pageSize);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取进件列表！");

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
        List<Map> nlbsMaterialTypeList = new ArrayList<>();

        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
        List<Map> bmsMaterialTypeList = bmsDictionariesDao.queryDictionariesByType(Constants.BMS_DICTIONARY_FILE_TYPE);
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
        if (bmsMaterialTypeList != null) {
            for (Map bmsMaterialTypeMap : bmsMaterialTypeList) {
                Map materialTypeMapMap = new HashMap();
                materialTypeMapMap.put(Fields.PARAM_MATERIAL_TYPE_CODE, bmsMaterialTypeMap.get(Fields.PARAM_CODE));
                materialTypeMapMap.put(Fields.PARAM_MATERIAL_TYPE_NAME, bmsMaterialTypeMap.get(Fields.PARAM_TITLE));
                nlbsMaterialTypeList.add(materialTypeMapMap);
            }
        }

        resultMap.put(Fields.PARAM_MATERIAL_TYPE_LIST, nlbsMaterialTypeList);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取所有进件材料类型！");

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
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();

        Map<String, Object> resultMap = new HashMap();
        List<Map> cityList = new ArrayList<Map>();
        List<Map> loanStatusList = new ArrayList<Map>();

        //获取城市列表
        List<NlbsUserGovernCity> nlbsUserGovernCityList = nlbsUserGovernCityMapper.queryCityByUserNo(userNo);
        if (nlbsUserGovernCityList != null && nlbsUserGovernCityList.size() > 0) {
            for (NlbsUserGovernCity nugc : nlbsUserGovernCityList) {
                //如果存在全国的业务管辖，则直接查找全部城市即可
                if (Constants.CITY_CODE_QUANGUO.equals(nugc.getCityCode())) {
                    List<NlbsCity> nlbsCityList = commonService.queryAllCity();
                    cityList.clear();
                    if (nlbsCityList != null && nlbsCityList.size() > 0) {
                        for (NlbsCity nc : nlbsCityList) {
                            if (Constants.CITY_CODE_QUANGUO.equals(nc.getCode())) {
                                continue;
                            }
                            Map cityMap = new HashMap();
                            cityMap.put(Fields.PARAM_CITY_CODE, nc.getCode());
                            cityMap.put(Fields.PARAM_CITY_NAME, nc.getAbbrName());
                            cityList.add(cityMap);
                        }
                    }
                    break;
                }
                Map cityMap = new HashMap();
                cityMap.put(Fields.PARAM_CITY_CODE, nugc.getCityCode());
                cityMap.put(Fields.PARAM_CITY_NAME, nugc.getCityName());
                cityList.add(cityMap);
            }
        }

        resultMap.put(Fields.PARAM_CITY_LIST, cityList);

        //获取进件状态
        List<Map> statusList = nlbsApplyLoanStatusMapper.queryAllLocalStatus();
        resultMap.put(Fields.PARAM_LOAN_STATUS_LIST, statusList);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "成功获取城市列表和状态列表！");

        return resultMap;
    }

    /**
     * 修改进件初始化
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map initModifyApplyRecord(Map paramMap) throws Exception {
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();

        Map<String, Object> resultMap = new HashMap();

        //Step 1 先调用申请时的初始化，然后清除业务经理和产品列表，防止后续覆盖失败
        resultMap.putAll(initApplyInfo(paramMap));
        resultMap.remove(Fields.PARAM_AGENT_LIST);
        resultMap.remove(Fields.PARAM_PRODUCT_LIST);

        //Step 2 通过核心接口获取当前进件信息
        // TODO 查询进件详情
        resultMap.putAll(queryApplyRecord(paramMap));

        //Step 3 根据查询结果,获取默认的业务经理列表和产品列表
        //TODO 根据查询结果，初始化业务员列表和产品列表
        Map agentAndProductMap = getAgentAndProductList(resultMap);
        resultMap.putAll(agentAndProductMap);

        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "进件申请初始化成功！");
        return resultMap;
    }

    /**
     * 修改进件申请<----废弃---></>
     *
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
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "修改进件申请已成功提交！");
        return resultMap;
    }

    /**
     * 删除一条状态为暂存的进件单
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map deleteApplyRecord(Map paramMap) throws Exception {
        paramMap.put(Fields.PARAM_OPERATION_TYPE, Constants.OPERATION_DELETE);
        //TODO 1 转换参数
        String loanSerialNo = paramMap.get(Fields.PARAM_LOAN_SERIAL_NO) == null ? "" : paramMap.get(Fields.PARAM_LOAN_SERIAL_NO).toString();
        String operatorFlag = paramMap.get(Fields.PARAM_OPERATION_TYPE) == null ? "" : paramMap.get(Fields.PARAM_OPERATION_TYPE).toString();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();

        Map forBmsParamMap = new HashMap();
        forBmsParamMap.put(Fields.PARAM_LOAN_ID, loanSerialNo);
        forBmsParamMap.put(Fields.PARAM_OPERATOR_FLAG, operatorFlag);

        Map bmsReturnMap = remoteBmsService.callOnlineloanMasterService(forBmsParamMap, "PUT");
        if (ReturnCode.SUCCESS_CODE.equals(bmsReturnMap.get(Fields.PARAM_MESSAGE_ERR_CODE))) {

            //记录暂存或提交操作历史表
            NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();
            nlbsOperationHistory.setOperater(userNo);
            nlbsOperationHistory.setSerialNo(loanSerialNo);
            nlbsOperationHistory.setSystemCode(Constants.SYSTEM_ID_NLBS);
            nlbsOperationHistory.setOperateType(operatorFlag);
            NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
            if (nlbsLoginInfo != null) {
                nlbsOperationHistory.setOperaterName(nlbsLoginInfo.getFullName());
                nlbsOperationHistory.setDistributorCode(nlbsLoginInfo.getDistributorCode());
                nlbsOperationHistory.setDistributorName(nlbsLoginInfo.getDistributorName());
            }
            nlbsOperationHistoryMapper.getInsert(nlbsOperationHistory);
        } else {
            throw new HHBizException(bmsReturnMap.get(Fields.PARAM_MESSAGE_ERR_CODE).toString(), "作废失败！");
        }

        return bmsReturnMap;
    }

    /**
     * 补充进件材料
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map supplyApplyMaterial(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        String loanSerialNo = paramMap.get(Fields.PARAM_LOAN_SERIAL_NO) == null ? "" : paramMap.get(Fields.PARAM_LOAN_SERIAL_NO).toString();
        // 调用BMS补充材料接口，直接透传即可
        Map forBmsParamMap = new HashMap();
        forBmsParamMap.put(Fields.PARAM_LOAN_ID, paramMap.get(Fields.PARAM_LOAN_SERIAL_NO));

        // 整理进件材料列表
        List<Map> materialList = (List<Map>) paramMap.get(Fields.PARAM_UPLOAD_FILE_LIST);
        List<Map> forBmsMaterialList = new ArrayList<>();
        if (materialList != null && materialList.size() > 0) {
            for (Map materialMap : materialList) {
                Map forBmsMaterialMap = new HashMap();
                Object fileName = materialMap.get(Fields.PARAM_FILE_NAME);
                String fileExt = "";
                if (fileName != null) {
                    String fileNameStr = fileName.toString();
                    if (StringUtils.isNotBlank(fileNameStr)) {
                        fileExt = fileNameStr.substring(fileNameStr.lastIndexOf(".") + 1);
                    }
                }
                forBmsMaterialMap.put(Fields.PARAM_FILE_ID, materialMap.get(Fields.PARAM_SERIAL_NO));
                forBmsMaterialMap.put(Fields.PARAM_ORIGINAL_FILE_NAME, fileName);
                forBmsMaterialMap.put(Fields.PARAM_FILE_EXT, fileExt);
                forBmsMaterialMap.put(Fields.PARAM_FILE_TYPE, paramMap.get(Fields.PARAM_FILE_TYPE));
                forBmsMaterialList.add(forBmsMaterialMap);
            }
        }
        forBmsParamMap.put(Fields.PARAM_FILE_LIST, forBmsMaterialList);

        Map bmsReturnMap = remoteBmsService.callAddOnlineloanAttachService(forBmsParamMap);
        if (ReturnCode.SUCCESS_CODE.equals(bmsReturnMap.get(Fields.PARAM_MESSAGE_ERR_CODE))) {

            //记录暂存或提交操作历史表
            NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();
            nlbsOperationHistory.setOperater(userNo);
            nlbsOperationHistory.setSerialNo(loanSerialNo);
            nlbsOperationHistory.setSystemCode(Constants.SYSTEM_ID_NLBS);
            nlbsOperationHistory.setOperateType(Constants.OPERATION_SUPPLEMENT);
            NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
            if (nlbsLoginInfo != null) {
                nlbsOperationHistory.setOperaterName(nlbsLoginInfo.getFullName());
                nlbsOperationHistory.setDistributorCode(nlbsLoginInfo.getDistributorCode());
                nlbsOperationHistory.setDistributorName(nlbsLoginInfo.getDistributorName());
            }
            nlbsOperationHistoryMapper.getInsert(nlbsOperationHistory);
        } else {
            throw new HHBizException(bmsReturnMap.get(Fields.PARAM_MESSAGE_ERR_CODE).toString(), "保存失败！");
        }

        return bmsReturnMap;
    }

    /**
     * 查询进件单操作历史
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map queryOperationHistory(Map paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap();
        String loanSerialNo = null != paramMap.get(Fields.PARAM_LOAN_SERIAL_NO) ? paramMap.get(Fields.PARAM_LOAN_SERIAL_NO).toString() : null;

        List<Map> returnHistoryList = new ArrayList<>();
        List<Map> historyList = new ArrayList<>();
        if (StringUtils.isNotBlank(loanSerialNo)) {
            //Step 1 查询本地的操作历史
            List<Map<String, Object>> localHistoryList = nlbsOperationHistoryMapper.getListBySerialNo(loanSerialNo);
            if (localHistoryList != null) {
                historyList.addAll(localHistoryList);
            }

            //Step 2 查询BMS数据库获取操作历史
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
            List<Map> bmsHistoryList = bmsOperationHistoryDao.queryOperateListByLoanSerialNo(loanSerialNo);
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
            if (bmsHistoryList != null) {
                historyList.addAll(bmsHistoryList);
            }

            //Step 3 按日期排序
            if (historyList.size() > 1) {
                CommonUtil.listSort(historyList, Fields.PARAM_CREATE_TIME, false);
            }

        } else {
            throw new HHBizException(ReturnCode.REQUIRED_FIELD_MISSING, "必填字段缺失！[loanSerialNo]");
        }
        for (Map historyMap : historyList) {
            Map reHistoryMap = new HashMap();
            reHistoryMap.put(Fields.PARAM_OPERATION_TIME, historyMap.get(Fields.PARAM_CREATE_TIME));
            reHistoryMap.put(Fields.PARAM_DISTRIBUTRO_NAME, historyMap.get(Fields.PARAM_DISTRIBUTRO_NAME));
            reHistoryMap.put(Fields.PARAM_OPERATION_USER, historyMap.get(Fields.PARAM_USER_NAME));
            reHistoryMap.put(Fields.PARAM_OPERATION_NAME, historyMap.get(Fields.PARAM_OPERATE_NAME));
            returnHistoryList.add(reHistoryMap);
        }
        resultMap.put(Fields.PARAM_OPERATION_HISTORY_LIST, returnHistoryList);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        resultMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "查询进件单操作历史成功！");
        return resultMap;
    }

    /**
     * 返回当前用户可见渠道，以及是否是录单员
     *
     * @param userNo 当前用户
     * @return
     */
    private Map getAvaliableDistributorList(String userNo) throws Exception {
        Map returnMap = new HashMap();

        int role = MANAGER_FLAG;

        boolean beAdminFlag = false;
        List<String> roleList = new ArrayList<String>();
        roleList.add(Constants.ROLE_SUPPER_MANAGER);
        beAdminFlag = commonService.isAdministrator(userNo, roleList);

        //查询用户渠道表（<业务员渠道表><录单员渠道表>，即用户可以录入哪些渠道的单子）
        List<NlbsRecordersDistributor> nlbsRecordersDistributorList = nlbsRecordersDistributorMapper.selectRecordersDistributorByUserNo(userNo);
        if (!beAdminFlag) {
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
            //Step 2.1 管理员，则获取全部渠道--bms的
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
            List<Map> nlbsDistributorList = bmsChannelsDao.queryAllChannels();
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
            if (nlbsDistributorList != null && nlbsDistributorList.size() > 0) {
                for (Map nb : nlbsDistributorList) {
                    Map standNb = new HashMap();
                    standNb.put(Fields.PARAM_DISTRIBUTRO_CODE, nb.get(Fields.PARAM_DISTRIBUTRO_CODE));
                    standNb.put(Fields.PARAM_DISTRIBUTRO_NAME, nb.get(Fields.PARAM_DISTRIBUTRO_NAME));
                    returnDistributorList.add(standNb);
                }
            }
        } else if (AGENT_FLAG == role) {
            returnMap.put(Fields.PARAM_BE_RECORD_CLERK, "N");
            //Step 2.2 业务员，则单独把自身渠道放入List即可，无需额外操作
            //--获取当前用户所在渠道，并直接添加进list
            NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
            if (null != nlbsLoginInfo) {
                CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_BMS);
                Map bmsChannel = bmsChannelsDao.queryChannelByAgentId(nlbsLoginInfo.getAgentId());
                CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_NLBS);
                if (bmsChannel != null) {
                    Map standNb = new HashMap();
                    standNb.put(Fields.PARAM_DISTRIBUTRO_CODE, bmsChannel.get(Fields.PARAM_DISTRIBUTRO_CODE));
                    standNb.put(Fields.PARAM_DISTRIBUTRO_NAME, bmsChannel.get(Fields.PARAM_DISTRIBUTRO_NAME));
                    returnDistributorList.add(standNb);
                }
            }

        } else if (RECORD_FLAG == role) {
            //Step 1.3 如果是录单员
            returnMap.put(Fields.PARAM_BE_RECORD_CLERK, "Y");
            //Step 2.3 录单员，则返回所在渠道，以及所在渠道的子渠道
            //--录单员直接查询《业务员-渠道关联表》查找渠道及子渠道
            List<Map> nlbsDistributorList = new ArrayList<Map>();
            //Step 2.3.1先查找关联表的数据
            if (nlbsRecordersDistributorList != null && nlbsRecordersDistributorList.size() > 0) {
                for (NlbsRecordersDistributor nrd : nlbsRecordersDistributorList) {
                    nlbsDistributorList.addAll(commonService.queryBmsDistributorsIncludeItself(nrd.getDistributorCode()));
                }
                //去除重复项
                HashSet hs = new HashSet(nlbsDistributorList);
                nlbsDistributorList.clear();
                nlbsDistributorList.addAll(hs);
            }
            //Step 2.3.3 整理distributerList的参数
            if (nlbsDistributorList != null && nlbsDistributorList.size() > 0) {
                //整理为标准的distributorCode distributorName的List
                for (Map map : nlbsDistributorList) {
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
     * 造数据--放款信息
     *
     * @return
     */
    private Map getTestLoanInfo() {
        Map returnMap = new HashMap();
        Map loanInfoMap = new HashMap();
        loanInfoMap.put(Fields.PARAM_LOAN_TIME, "2017-07-17 12:34");
        loanInfoMap.put(Fields.PARAM_LOAN_AMOUNT, "560万元");

        Map loanAccountInfo = new HashMap();
        loanAccountInfo.put(Fields.PARAM_LOAN_ACCOUNT_NAME, "XXX");
        loanAccountInfo.put(Fields.PARAM_LOAN_ACCOUNT_BANK, "工商银行沪太路支行");
        loanAccountInfo.put(Fields.PARAM_LOAN_ACCOUNT, "6217859669851237895");
        loanAccountInfo.put(Fields.PARAM_REMARK, "备注");
        loanInfoMap.put(Fields.PARAM_LOAN_ACCOUNT_INFO, loanAccountInfo);

        Map interestAccountInfo = new HashMap();
        interestAccountInfo.put(Fields.PARAM_INTEREST_ACCOUNT_NAME, "XXX");
        interestAccountInfo.put(Fields.PARAM_INTEREST_ACCOUNT_BANK, "招商银行沪太路支行");
        interestAccountInfo.put(Fields.PARAM_INTEREST_ACCOUNT, "6217859669851234895");
        loanInfoMap.put(Fields.PARAM_INTEREST_ACCOUNT_INFO, interestAccountInfo);

        Map principalAccountInfo = new HashMap();
        principalAccountInfo.put(Fields.PARAM_PRINCIPAL_ACCOUNT_NAME, "XXX");
        principalAccountInfo.put(Fields.PARAM_PRINCIPAL_ACCOUNT_BANK, "华夏银行沪太路支行");
        principalAccountInfo.put(Fields.PARAM_PRINCIPAL_ACCOUNT, "6217859669851234555");
        loanInfoMap.put(Fields.PARAM_PRINCIPAL_ACCOUNT_INFO, principalAccountInfo);


        returnMap.put(Fields.PARAM_LOAN_INFO, loanInfoMap);
        return returnMap;
    }

    public Map uploadFile(Map paramMap) {
        Map map = new HashMap();
        MultipartFile[] files = (MultipartFile[]) paramMap.get("files");
        String applyFilePath = (String) paramMap.get("filePath");
        String[] paths = {applyFilePath};
        map.put("filePath", paths);

        JSONObject result = null;
        try {
            String url = configInfo.getFmsUrl() + "/fileLoad/uploadFile";
            result = HttpRequestUtils.httpPost(url, map, files, false);
        } catch (Exception e) {
            logger.error("上传文件异常：", e);
        }
        return result;
    }


    public Map getFile(Map map, HttpServletResponse rsp) throws Exception {
        Map result = new HashMap();
        String serialNo = (String) map.get("serialNo");

        //JSONObject response = null;
        InputStream in = null;
        try {
            String url = configInfo.getFmsUrl() + "/fileLoad/getFile" + "?serialNo=" + serialNo;
            in = HttpRequestUtils.httpGetFile(url, rsp);
            result.put("is", in);
        } catch (Exception e) {
            logger.error("下载文件异常：", e);
        }

        return result;
    }

    public Map getUrlList(Map map) throws Exception {
        Map result = new HashMap();
        JSONObject response = null;
        try {
            JSONObject jsonParam = JSONObject.fromObject(map);
            String url = configInfo.getFmsUrl() + "/fileLoad/getUrlList";
            response = HttpRequestUtils.httpPost(url, jsonParam);

        } catch (Exception e) {
            logger.error("获取文件列表异常：", e);
        }

        result = CommonUtil.toMap(response);
        return result;
    }

    public static void main(String[] args) throws Exception {
//        getTestFileData();
    }
}