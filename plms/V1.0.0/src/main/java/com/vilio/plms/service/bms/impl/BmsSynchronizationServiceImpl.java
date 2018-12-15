package com.vilio.plms.service.bms.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.BmsGlobDict;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.*;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.RemoteFmsService;
import com.vilio.plms.service.bms.BmsRepaymentScheduleService;
import com.vilio.plms.service.bms.BmsSynchronizationService;
import com.vilio.plms.service.quartz.PayRepaymentScheduleDetailService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JsonUtil;
import com.vilio.plms.util.PlmsUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.json.Json;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by martin on 2017/8/24.
 */
@Service
public class BmsSynchronizationServiceImpl implements BmsSynchronizationService {
    private static final Logger logger = Logger.getLogger(BmsSynchronizationServiceImpl.class);

    @Resource
    PayRepaymentScheduleDetailService payRepaymentScheduleDetailService;
    @Resource
    ContractDao contractDao;
    @Resource
    HouseDao houseDao;
    @Resource
    PlmsPropertyInvestigationDao plmsPropertyInvestigationDao;
    @Resource
    PlmsInvestDetailDao plmsInvestDetailDao;
    @Resource
    PlmsHouseholdRegistrationDao plmsHouseholdRegistrationDao;
    @Resource
    PlmsHouseApprovalDao plmsHouseApprovalDao;
    @Resource
    ApplyInterestingDao applyInterestingDao;
    @Resource
    FundSideDao fundSideDao;
    @Resource
    PlmsGuaranteeCorporationDao plmsGuaranteeCorporationDao;
    @Resource
    PlmsInsuranceCompanyDao plmsInsuranceCompanyDao;
    @Resource
    PlmsCustomerFeeInfoDao plmsCustomerFeeInfoDao;
    @Resource
    PlmsSignNotarialDao plmsSignNotarialDao;
    @Resource
    PlmsMortgageDao plmsMortgageDao;
    @Resource
    PlmsInvestQueryDao plmsInvestQueryDao;
    @Resource
    PlmsPigeonholeInfoDao plmsPigeonholeInfoDao;
    @Resource
    PlmsCompanyDao plmsCompanyDao;
    @Resource
    RemoteFmsService remoteFmsService;

    @Resource
    BmsSynchronousDao synchronousDao;
    @Resource
    AccountInfoDao accountInfoDao;
    @Resource
    ApplyInfoDao applyInfoDao;
    @Resource
    CustomerDao customerDao;
    @Resource
    UserInfoDao userInfoDao;
    @Resource
    ProductDao productDao;
    @Resource
    SpareHouseInfoDao spareHouseInfoDao;
    @Resource
    CreditInfoDao creditInfoDao;
    @Resource
    JudicialInfoDao judicialInfoDao;
    @Resource
    LoanCaseUseDao loanCaseUseDao;
    @Resource
    LoanAttachDao loanAttachDao;
    @Resource
    ApprovalDao loanRiskInfoDao;
    @Resource
    CommonService commonService;
    @Resource
    BaseTableDao baseTableDao;
    @Resource
    PlmsFundSideProductDao plmsFundSideProductDao;
    @Resource
    BmsRepaymentScheduleService bmsRepaymentScheduleService;
    @Resource
    BmsSynchronizateDao bmsSynchronizateDao;

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void synchronizeApplyInfo(Map map) throws Exception {
        logger.info(map);
        String jsonString = JsonUtil.objectToJson(map);
        System.out.println(jsonString);

        Map jsonMap = new HashMap();
//        JSONObject json = JSONObject.fromObject(jsonString);
        jsonMap.put("synchronizeInfo", jsonString);
        jsonMap.put("status", GlobDict.bms_synchronize_status_init.getKey());

        bmsSynchronizateDao.insert(jsonMap);
        return;
    }


        @Transactional(propagation = Propagation.REQUIRED,
                isolation = Isolation.READ_COMMITTED,
                rollbackFor = Exception.class)
        public void synchronize(Map map) throws Exception {
        //核心系统进价编码
        String BMSLoanCode = (String) map.get("bmsLoanCode");
        logger.info("本次解析的bmsLoanCode是："+BMSLoanCode+" 。");
        if (BMSLoanCode != null && !"".equals(BMSLoanCode)) {
            ApplyInfo plmApplyInfo = applyInfoDao.qryApplyInfoByBmsCode(BMSLoanCode);
            if (plmApplyInfo!=null){
                logger.info("无需同步进件数据。");
            }
            if (plmApplyInfo == null) {
                //进件信息
                Map applyInfo = (Map) map.get("applyInfo");
                //渠道信息
                Map distributeInfo = (Map) map.get("distrbuteInfo");
                //渠道产品信息
                Map distributeProductInfo = (Map) map.get("distributeProductInfo");
                //抵押物信息
                List loanPawnList = (List<Map>) map.get("loanPawnList");
                //运营登记信息
                Map operationsInfo = (Map) map.get("operationsInfo");
                //借款人信息
                List loanPeopleList = (List<Map>) applyInfo.get("customerInfo");
                //备用房信息
                List loanSepraroomList = (List<Map>) applyInfo.get("spareHouseInfo");
                //征信信息
                List loanCreditInfoList = (List<Map>) applyInfo.get("creditInvestigationInfo");
                //司法信息
                List loanJsuticeInfoList = (List<Map>) applyInfo.get("judicialInfo");
                //案件评估信息
                Map loanCaseUse = (Map) map.get("loanCaseUse");
                //附件材料信息
                List loanAttachList = (List<Map>) applyInfo.get("materialInfo");
                //风控审批信息
                Map riskControlInfo = (Map) map.get("riskControlInfo");
                //风险控制信息
                Map loanRiskInfo = (Map) map.get("loanRiskInfo");
                //审批通知单信息
                Map approvalNoticeInfo = (Map) riskControlInfo.get("approvalNoticeInfo");

                //业务员信息
                String bmsAgentCode = (String)map.get("agentId");
                Map agent = (HashMap)baseTableDao.qryAgent(bmsAgentCode);
                String agentCode = (String)agent.get("code");

                String bmsDistributeCode = (String) map.get("distributorCode");
                Map distribute = (HashMap)baseTableDao.qryDistribute(bmsDistributeCode);
                String distributeCode = (String)distribute.get("code");


                //同步银行账户信息
                Map operationsRegisterInfo = (Map) operationsInfo.get("operationsRegisterInfo");
                Map lendingRegistration = (Map)operationsRegisterInfo.get("lendingRegistration");
                String bmsAccountName = (String) lendingRegistration.get("accountName");
                String bmsAccountNo = (String) lendingRegistration.get("accountNumber");
                String bmsAccountBank = (String) lendingRegistration.get("openingBank");

                Map plmsAccountInfo = new HashMap();
                String accountCode = commonService.getUUID();
                plmsAccountInfo.put("code", accountCode);
                plmsAccountInfo.put("name", bmsAccountName);
                plmsAccountInfo.put("accountNo", bmsAccountNo);
                plmsAccountInfo.put("bank", bmsAccountBank);
                plmsAccountInfo.put("status", GlobDict.valid.getKey());

                accountInfoDao.insert(plmsAccountInfo);

                //同步申请信息
                String applyDate = (String) map.get("applyTime");
                Object applyAmount =  map.get("loanAmount");
                String applyAmountString = null;
                if (applyAmount != null) {
                    applyAmountString = String.valueOf(applyAmount);
                }
                BigDecimal applyAmountDecimal = new BigDecimal(applyAmountString);
                String applyPeriod = (String) map.get("loanPeriodName");
                String paidMethod = (String) map.get("creditTypeCode");
                if (paidMethod != null && GlobDict.bms_paid_method_permits_loan.getDesc().equals(paidMethod)) {
                    paidMethod = GlobDict.bms_paid_method_permits_loan.getKey();
                } else if (paidMethod != null && GlobDict.bms_paid_method_collection_receipt_loan.getDesc().equals(paidMethod)) {
                    paidMethod = GlobDict.bms_paid_method_collection_receipt_loan.getKey();
                }
                BigDecimal intentionMoneyDecimal = null;
                if (null != approvalNoticeInfo.get("intentionMoney")){
                    String intentionMoney = String.valueOf(approvalNoticeInfo.get("intentionMoney"));
                    intentionMoneyDecimal = new BigDecimal(intentionMoney);
                }
                String mobilephoneValidateNo = (String) map.get("mobilephoneValidateNo");
                String remark = (String) map.get("remark");
                String applyAccountCode = accountCode;
                String applyAgentCode = agentCode;
                String bmsCode = (String) map.get("bmsLoanCode");

                Map plmsApplyInfo = new HashMap();
                String applyCode = commonService.getUUID();
                plmsApplyInfo.put("code", applyCode);
                plmsApplyInfo.put("applyDate", applyDate);
                //applyAmountDecimal = applyAmountDecimal.multiply(new BigDecimal(10000));
                plmsApplyInfo.put("applyAmount", applyAmountDecimal);
                plmsApplyInfo.put("applyPeriod", applyPeriod);
                plmsApplyInfo.put("lendingMethods", paidMethod);
                plmsApplyInfo.put("intentionMoney", intentionMoneyDecimal);
                plmsApplyInfo.put("identifyingCode", mobilephoneValidateNo);
                plmsApplyInfo.put("remark", remark);
                plmsApplyInfo.put("accountCode", applyAccountCode);
                plmsApplyInfo.put("agentCode", applyAgentCode);
                plmsApplyInfo.put("distributeCode", distributeCode);
                plmsApplyInfo.put("bmsCode", bmsCode);
                plmsApplyInfo.put("status", GlobDict.valid.getKey());

                applyInfoDao.insert(plmsApplyInfo);

                //同步借款人信息
                if (loanPeopleList != null && loanPeopleList.size() > 0) {
                    for (int i = 0; i < loanPeopleList.size(); i++) {
                        Map loanPeople = (Map) loanPeopleList.get(i);
                        String name = (String) loanPeople.get("name");
                        String usedName = (String) loanPeople.get("usedName");
                        String certificateType = (String) loanPeople.get("certificateType");
                        String plmsIdType = "";
                        if (certificateType != null && GlobDict.id_type_id_card.getDesc().equals(certificateType)) {
                            plmsIdType = GlobDict.id_type_id_card.getKey();
                        }
                        if (certificateType != null && GlobDict.id_type_temp_id_card.getDesc().equals(certificateType)) {
                            plmsIdType = GlobDict.id_type_temp_id_card.getKey();
                        }
                        if (certificateType != null && GlobDict.id_type_birth_card.getDesc().equals(certificateType)) {
                            plmsIdType = GlobDict.id_type_birth_card.getKey();
                        }
                        String certificateNumber = (String) loanPeople.get("certificateNumber");
                        String certificateValidityStart = (String) loanPeople.get("certificateValidityStart");
                        if (null != certificateValidityStart && "null".equals(certificateValidityStart)){
                            certificateValidityStart = null;
                        }
                        String certificateValidityEnd = (String) loanPeople.get("certificateValidityEnd");
                        if (null != certificateValidityEnd && "null".equals(certificateValidityEnd)){
                            certificateValidityEnd = null;
                        }
                        Integer age = (Integer) loanPeople.get("age");
                        String cellphone = (String) loanPeople.get("cellphone");
                        String workUnit = (String) loanPeople.get("workUnit");
                        String annualIncome = null;
                        if (loanPeople.get("annualIncome")!= null) {
                            annualIncome = loanPeople.get("annualIncome").toString();
                        }
                        String post = (String) loanPeople.get("post");
                        String familyAddress = (String) loanPeople.get("familyAddress");
                        String maritalStatusCode = (String) loanPeople.get("maritalStatusCode");
                        String maritalStatusString = "";
                        String marriageHistory = (String) loanPeople.get("marriageHistory");
                        Boolean mainLoanner = (Boolean) loanPeople.get("mainLoanner");
                        String mainLoannerString = "";
                        if (mainLoanner != null && !mainLoanner) {
                            mainLoannerString = GlobDict.bms_main_loanner_false.getKey();
                        } else if (mainLoanner != null && mainLoanner) {
                            mainLoannerString = GlobDict.bms_main_loanner_true.getKey();
                        }
                        String customerApplyCode = applyCode;

                        Map plmsCustomerInfo = new HashMap();
                        String customerInfoCode = commonService.getUUID();
                        plmsCustomerInfo.put("code", customerInfoCode);
                        plmsCustomerInfo.put("name", name);
                        plmsCustomerInfo.put("oldName", usedName);
                        plmsCustomerInfo.put("idType", plmsIdType);
                        plmsCustomerInfo.put("idNo", certificateNumber);
                        plmsCustomerInfo.put("startTime", certificateValidityStart);
                        plmsCustomerInfo.put("endTime", certificateValidityEnd);
                        plmsCustomerInfo.put("age", age);
                        plmsCustomerInfo.put("mobile", cellphone);
                        plmsCustomerInfo.put("organization", workUnit);
                        BigDecimal annualIncomeDecimal = null;
                        if (annualIncome != null) {
                            annualIncomeDecimal = new BigDecimal(annualIncome);
                        }
                        //annualIncomeDecimal = annualIncomeDecimal.multiply(new BigDecimal(10000));
                        plmsCustomerInfo.put("annualIncome", annualIncomeDecimal);
                        plmsCustomerInfo.put("titile", post);
                        plmsCustomerInfo.put("address", familyAddress);
                        plmsCustomerInfo.put("marriage", maritalStatusCode);
                        plmsCustomerInfo.put("marriageHistory", marriageHistory);
                        plmsCustomerInfo.put("isMain", mainLoanner);
                        plmsCustomerInfo.put("accountCode", mainLoanner);
                        plmsCustomerInfo.put("status", GlobDict.valid.getKey());
                        plmsCustomerInfo.put("applyCode", customerApplyCode);

                        customerDao.insert(plmsCustomerInfo);


                        //更新用户信息表
                        if (GlobDict.bms_main_loanner_true.getKey().equals(mainLoannerString)) {
                            Map customerMap = new HashMap();
                            customerMap.put("name", name);
                            customerMap.put("idType", plmsIdType);
                            customerMap.put("idNo", certificateNumber);
                            Map userInfo = (HashMap) userInfoDao.queryUserInfoMapByNameAndIdNo(customerMap);

                            if (userInfo == null) {
                                userInfo = new HashMap();
                                String code = commonService.getUUID();
                                userInfo.put("code", code);
                                userInfo.put("name", name);
                                userInfo.put("idType", plmsIdType);
                                userInfo.put("idNo", certificateNumber);
                                userInfo.put("status", GlobDict.valid.getKey());
                                //userInfo.put("umId",umId);
                                userInfoDao.insert(userInfo);
                                //更新申请信息的用户编码
                                plmsApplyInfo.put("userCode", code);
                                applyInfoDao.update(plmsApplyInfo);
                            }else {
                                String code = (String)userInfo.get("code");
                                plmsApplyInfo.put("userCode", code);
                                applyInfoDao.update(plmsApplyInfo);
                            }
                        }
                    }
                }

                //同步产品信息
                String productName = (String) distributeProductInfo.get("productName");
                String circle = (String) distributeProductInfo.get("circle");
                String interestCycle = (String) distributeProductInfo.get("interestCycle");
                if (interestCycle != null && GlobDict.bms_interest_circle_day.getDesc().equals(interestCycle)) {
                    interestCycle = GlobDict.bms_interest_circle_day.getKey();
                } else if (interestCycle != null && GlobDict.bms_interest_circle_month.getDesc().equals(interestCycle)) {
                    interestCycle = GlobDict.bms_interest_circle_month.getKey();
                } else if (interestCycle != null && GlobDict.bms_interest_circle_year.getDesc().equals(interestCycle)) {
                    interestCycle = GlobDict.bms_interest_circle_year.getKey();
                }
                String yearPeriod = (String) distributeProductInfo.get("yearPeriod");
                String repaymentMethod = GlobDict.first_interest.getKey();
                String loanMinimumUnit = (String) distributeProductInfo.get("loanMinimumUnit");
                BigDecimal loanMinimumUnitDecimal = null;
                if (loanMinimumUnit != null) {
                    loanMinimumUnitDecimal = new BigDecimal(loanMinimumUnit);
                }
                String loanMinimumAmount = (String) distributeProductInfo.get("loanMinimumAmount");
                BigDecimal loanMinimumAmountDecimal = null;
                if (loanMinimumAmount != null) {
                    loanMinimumAmountDecimal = new BigDecimal(loanMinimumAmount);
                }
                String repaymentMinUnit = (String) distributeProductInfo.get("repaymentMinUnit");
                BigDecimal repaymentMinUnitDecimal = null;
                if (repaymentMinUnit != null) {
                    repaymentMinUnitDecimal = new BigDecimal(repaymentMinUnit);
                }
                String repaymentMinimumAmount = (String) distributeProductInfo.get("repaymentMinimumAmount");
                BigDecimal repaymentMinimumAmountDecimal = null;
                if (repaymentMinimumAmount != null) {
                    repaymentMinimumAmountDecimal = new BigDecimal(repaymentMinimumAmount);
                }
                String borrowClosedPeriod = (String) distributeProductInfo.get("borrowClosedPeriod");
                String repaymentClosedPeriod = (String) distributeProductInfo.get("repaymentClosedPeriod");
                String isPenalty = (String) distributeProductInfo.get("isPenalty");
                String penaltyPeriod = (String) distributeProductInfo.get("penaltyPeriod");
                String isFirstMax = (String) distributeProductInfo.get("isFirstMax");
                String interestCollectionDate = (String) distributeProductInfo.get("interestCollectionDate");
                Map interestCollectionMethodMap = (Map) distributeProductInfo.get("interestCollectionMethod");
                String interestCollectionMethod = (String)interestCollectionMethodMap.get("method");
                if (null !=interestCollectionMethod && GlobDict.bms_interest_collection_method_pre.getDesc().equals(interestCollectionMethod)){
                    interestCollectionMethod = GlobDict.bms_interest_collection_method_pre.getKey();
                }
                else if (null !=interestCollectionMethod && GlobDict.bms_interest_collection_method_back.getDesc().equals(interestCollectionMethod)){
                    interestCollectionMethod = GlobDict.bms_interest_collection_method_back.getKey();
                }
                else if (null !=interestCollectionMethod && GlobDict.bms_interest_collection_method_fix.getDesc().equals(interestCollectionMethod)){
                    interestCollectionMethod = GlobDict.bms_interest_collection_method_fix.getKey();
                }
                System.out.println("interestCollectionMethod:"+interestCollectionMethod);
                Integer mortgageInterestPeriod = (Integer)interestCollectionMethodMap.get("term");
                Integer graceDays = (Integer) interestCollectionMethodMap.get("moratorium");
                String principalDate = (String) distributeProductInfo.get("principalDate");
                String principalRepaymentMethod = (String) distributeProductInfo.get("principalRepaymentMethod");
                if (principalRepaymentMethod != null && GlobDict.bms_repayment_method_rate.getDesc().equals(principalRepaymentMethod)) {
                    principalRepaymentMethod = GlobDict.bms_repayment_method_rate.getKey();
                } else if (principalRepaymentMethod != null && GlobDict.bms_repayment_method_amount.getDesc().equals(principalRepaymentMethod)) {
                    principalRepaymentMethod = GlobDict.bms_repayment_method_amount.getKey();
                }
                String partRepayment = null;
                if (distributeProductInfo.get("partRepayment") != null) {
                    partRepayment = String.valueOf(distributeProductInfo.get("partRepayment"));
                }
                String isPeriodSelect = (String) distributeProductInfo.get("isPeriodSelect");
                String overdueFormula = (String) distributeProductInfo.get("overdueFormula");
                String overdueMethod = null;
                if (overdueFormula != null) {
                    overdueMethod = (String) overdueFormula.substring(0, 2);
                }
                String serviceFeeMethod = null;
                String spreadsFormula = (String) distributeProductInfo.get("spreadsFormula");
                if (spreadsFormula != null) {
                    serviceFeeMethod = (String) spreadsFormula.substring(0, 2);
                }
                String isSpreadOneTime = (String) distributeProductInfo.get("spreadsMethod");
                if (isSpreadOneTime != null && GlobDict.bms_is_spread_one_time_once.getDesc().equals(isSpreadOneTime)){
                    isSpreadOneTime = GlobDict.bms_is_spread_one_time_once.getKey();
                }
                else if (isSpreadOneTime != null && GlobDict.bms_is_spread_one_time_period.getDesc().equals(isSpreadOneTime)){
                    isSpreadOneTime = GlobDict.bms_is_spread_one_time_period.getKey();
                }
                Integer maxLoanNum = (Integer) distributeProductInfo.get("maxLoanNum");
                String paidDaysComputationalRule = GlobDict.product_paid_days_computational_rule_both.getKey();

                //bms2.0版本添加，暂时先取利3。
                Integer paidDateGraceDays = 3;


                Map plmsProductInfo = new HashMap();
                String productCode = commonService.getUUID();
                plmsProductInfo.put("code", productCode);
                plmsProductInfo.put("productName", productName);
                plmsProductInfo.put("circle", circle);
                plmsProductInfo.put("interestCycle", interestCycle);
                plmsProductInfo.put("yearPeriod", yearPeriod);
                plmsProductInfo.put("repaymentMethods", repaymentMethod);
                plmsProductInfo.put("loanMinimumUnit", loanMinimumUnitDecimal);
                plmsProductInfo.put("loanMinimumAmount", loanMinimumAmountDecimal);
                plmsProductInfo.put("repaymentMinimumUnit", repaymentMinUnitDecimal);
                plmsProductInfo.put("repaymentMinimumAmount", repaymentMinimumAmountDecimal);
                plmsProductInfo.put("borrowClosedPeriod", borrowClosedPeriod);
                plmsProductInfo.put("repaymentClosedPeriod", repaymentClosedPeriod);
                plmsProductInfo.put("isPenalty", isPenalty);
                plmsProductInfo.put("penaltyPeriod", penaltyPeriod);
                plmsProductInfo.put("isFirstMax", isFirstMax);
                plmsProductInfo.put("partRepayment",partRepayment);
                plmsProductInfo.put("interestRepaymentDay", interestCollectionDate);
                plmsProductInfo.put("mortgageInterestPeriod", mortgageInterestPeriod);
                plmsProductInfo.put("interestCollectionMethod", interestCollectionMethod);
                plmsProductInfo.put("graceDays", graceDays);
                plmsProductInfo.put("paidDateGraceDays", paidDateGraceDays);
                plmsProductInfo.put("principalRepaymentPeriod", principalDate);
                plmsProductInfo.put("principalRepaymentMethod", principalRepaymentMethod);
                plmsProductInfo.put("isPeriodSelect", isPeriodSelect);
                plmsProductInfo.put("overdueMethod", overdueMethod);
                plmsProductInfo.put("serviceFeeMethod", serviceFeeMethod);
                plmsProductInfo.put("isSpreadOneTime", isSpreadOneTime);
                plmsProductInfo.put("maxLoanNum", maxLoanNum);
                plmsProductInfo.put("status", GlobDict.valid.getKey());
                plmsProductInfo.put("applyCode", applyCode);
                plmsProductInfo.put("paidDaysComputationalRule", paidDaysComputationalRule);

                productDao.insert(plmsProductInfo);

                //同步备用房信息
                if (loanSepraroomList != null && loanSepraroomList.size() > 0) {
                    for (int i = 0; i < loanSepraroomList.size(); i++) {
                        Map loanSepraroom = (Map) loanSepraroomList.get(i);
                        String propertyOwner = (String) loanSepraroom.get("propertyOwner");
                        String propertyAddress = (String) loanSepraroom.get("propertyAddress");

                        Map plmsSpareInfo = new HashMap();
                        String spareInfoCode = commonService.getUUID();
                        plmsSpareInfo.put("code",spareInfoCode);
                        plmsSpareInfo.put("propertyPerson", propertyOwner);
                        plmsSpareInfo.put("propertyAddress", propertyAddress);
                        plmsSpareInfo.put("applyCode", applyCode);
                        plmsSpareInfo.put("status", GlobDict.valid.getKey());

                        spareHouseInfoDao.insert(plmsSpareInfo);
                    }
                }
                //同步征信信息
                if (loanCreditInfoList != null && loanCreditInfoList.size() > 0) {
                    for (int s = 0; s < loanCreditInfoList.size(); s++) {
                        Map loanCreditInfo = (Map) loanCreditInfoList.get(s);
                        String name = (String) loanCreditInfo.get("name");
                        String creditReportDate = (String) loanCreditInfo.get("creditReportDate");
                        String creditRemark = (String) loanCreditInfo.get("remark");

                        Map plmsCreditInfo = new HashMap();
                        String creditInfoCode = commonService.getUUID();
                        plmsCreditInfo.put("code", creditInfoCode);
                        plmsCreditInfo.put("name", name);
                        plmsCreditInfo.put("reportTime", creditReportDate);
                        plmsCreditInfo.put("remark", creditRemark);
                        plmsCreditInfo.put("status", GlobDict.valid.getKey());
                        plmsCreditInfo.put("applyCode", applyCode);

                        creditInfoDao.insert(plmsCreditInfo);

                        //同步未结清贷款信息
                        Integer outstandLoanCount = (Integer) loanCreditInfo.get("outstandLoanCount");
                        String outstandLoanSum = null;
                        if (loanCreditInfo.get("outstandLoanSum") != null) {
                            outstandLoanSum = String.valueOf(loanCreditInfo.get("outstandLoanSum"));
                        }

                        String outstandLoanBalance = null;
                        if (loanCreditInfo.get("outstandLoanBalance") != null) {
                            outstandLoanBalance = String.valueOf(loanCreditInfo.get("outstandLoanBalance"));
                        }
                        String outstandLoanMonthAvgRepay = null;
                        if (loanCreditInfo.get("outstandLoanMonthAvgRepay")!=null) {
                            outstandLoanMonthAvgRepay = String.valueOf(loanCreditInfo.get("outstandLoanMonthAvgRepay"));
                        }

                        Map plmsLoanUnSettle = new HashMap();
                        String loanUnSettleCode = commonService.getUUID();
                        plmsLoanUnSettle.put("code", loanUnSettleCode);
                        plmsLoanUnSettle.put("loanTimes", outstandLoanCount);
                        BigDecimal outstandLoanSumDecimal = null;
                        if (outstandLoanSum != null) {
                            outstandLoanSumDecimal = new BigDecimal(outstandLoanSum);
                        }
                        //outstandLoanSumDecimal = outstandLoanSumDecimal.multiply(new BigDecimal(10000));
                        plmsLoanUnSettle.put("contractAmount", outstandLoanSumDecimal);
                        BigDecimal balanceDecimal = null;
                        if (outstandLoanBalance != null) {
                            balanceDecimal = new BigDecimal(outstandLoanBalance);
                        }

                        //balanceDecimal = balanceDecimal.multiply(new BigDecimal(10000));
                        plmsLoanUnSettle.put("balance", balanceDecimal);
                        BigDecimal outstandLoanMonthAvgRepayDecimal = null;
                        if (outstandLoanMonthAvgRepay != null){
                            outstandLoanMonthAvgRepayDecimal = new BigDecimal(outstandLoanMonthAvgRepay);
                        }
                        //outstandLoanMonthAvgRepayDecimal = outstandLoanMonthAvgRepayDecimal.multiply(new BigDecimal(10000));
                        plmsLoanUnSettle.put("sixMonthRepayment", outstandLoanMonthAvgRepayDecimal);
                        plmsLoanUnSettle.put("creditCode", creditInfoCode);
                        plmsLoanUnSettle.put("status", GlobDict.valid.getKey());

                        creditInfoDao.insertLoanUnSettle(plmsLoanUnSettle);

                        //同步未销户贷记卡信息
                        //账户数
                        Integer usableCreditCardCount = (Integer) loanCreditInfo.get("usableCreditCardCount");
                        //授信总额
                        String usableCardSum = null;
                        if (loanCreditInfo.get("usableCardSum") != null) {
                            usableCardSum = String.valueOf(loanCreditInfo.get("usableCardSum"));
                        }
                        //最近6个月平均使用额度
                        String usableCardMonthQuotaUsed = null;
                        if (loanCreditInfo.get("usableCardMonthQuotaUsed") != null) {
                            usableCardMonthQuotaUsed = String.valueOf(loanCreditInfo.get("usableCardMonthQuotaUsed"));
                        }
                        //已用额度
                        String usableCardQuotaUsed = null;
                        if (loanCreditInfo.get("usableCardQuotaUsed") != null) {
                            usableCardQuotaUsed = String.valueOf(loanCreditInfo.get("usableCardQuotaUsed"));
                        }

                        Map plmsUnclosingCard = new HashMap();
                        String unclosingCardCode = commonService.getUUID();
                        plmsUnclosingCard.put("code", unclosingCardCode);
                        plmsUnclosingCard.put("accountNumber", usableCreditCardCount);

                        BigDecimal usableCardSumDecimal = null;
                        if (usableCardSum != null){
                            usableCardSumDecimal = new BigDecimal(usableCardSum);
                        }
                        //usableCardSumDecimal = usableCardSumDecimal.multiply(new BigDecimal(10000));
                        plmsUnclosingCard.put("totalCredit", usableCardSumDecimal);

                        BigDecimal usableCardQuotaUsedDecimal = null;
                        if (usableCardQuotaUsed != null){
                            usableCardQuotaUsedDecimal = new BigDecimal(usableCardQuotaUsed);
                        }
                        //usableCardQuotaUsedDecimal = usableCardQuotaUsedDecimal.multiply(new BigDecimal(10000));
                        plmsUnclosingCard.put("contractAmount", usableCardQuotaUsedDecimal);

                        BigDecimal usableCardMonthQuotaUsedDecimal = null;
                        if (usableCardMonthQuotaUsed != null){
                            usableCardMonthQuotaUsedDecimal = new BigDecimal(usableCardMonthQuotaUsed);
                        }
                        //usableCardMonthQuotaUsedDecimal = usableCardMonthQuotaUsedDecimal.multiply(new BigDecimal(10000));
                        plmsUnclosingCard.put("sixMonthLoanAmount", usableCardMonthQuotaUsedDecimal);

                        plmsUnclosingCard.put("creditCode", creditInfoCode);
                        plmsUnclosingCard.put("status", GlobDict.valid.getKey());

                        creditInfoDao.insertUnclosingCard(plmsUnclosingCard);

                        //同步查询记录表
                        //近三个月查询次数
                        Integer findThreemonthCount = (Integer) loanCreditInfo.get("findThreemonthCount");
                        //贷后管理次数
                        Integer loanAfterCount = (Integer) loanCreditInfo.get("loanAfterCount");
                        //担保资格审查次数
                        Integer guaranteeSeniorityCount = (Integer) loanCreditInfo.get("guaranteeSeniorityCount");

                        Map plmsQueryRecord = new HashMap();
                        String queryRecordCode = commonService.getUUID();
                        plmsQueryRecord.put("code", queryRecordCode);
                        plmsQueryRecord.put("queryNum", findThreemonthCount);
                        plmsQueryRecord.put("manageNum", loanAfterCount);
                        plmsQueryRecord.put("checkNum", guaranteeSeniorityCount);
                        plmsQueryRecord.put("status", GlobDict.valid.getKey());

                        plmsQueryRecord.put("creditCode", creditInfoCode);

                        creditInfoDao.insertQueryRecord(plmsQueryRecord);

                        //针对逾期信息同步已贷款信息表
                        List loanInfoList = (List<Map>) loanCreditInfo.get("loanInfoList");
                        if (loanInfoList != null && loanInfoList.size() > 0) {
                            for (int c = 0; c < loanInfoList.size(); c++) {
                                Map loanInfo = (Map) loanInfoList.get(c);
                                //针对逾期信息同步已贷款信息表
                                Map currentOverdueLoan = (Map) loanInfo.get("currentOverdueLoan");
                                if (currentOverdueLoan != null) {
                                    //贷款序号
                                    String serialNo = (String) currentOverdueLoan.get("serialNo");
                                    //逾期金额
                                    String overdueAmount = null;
                                    if (currentOverdueLoan.get("overdueAmount") != null) {
                                        overdueAmount = String.valueOf(currentOverdueLoan.get("overdueAmount"));
                                    }

                                    Map plmsLoanInfo = new HashMap();
                                    String loanInfoCode = commonService.getUUID();
                                    plmsLoanInfo.put("code", loanInfoCode);
                                    plmsLoanInfo.put("loanNo", serialNo);
                                    BigDecimal overdueAmountDecimal = null;
                                    if (overdueAmount != null){
                                        overdueAmountDecimal = new BigDecimal(overdueAmount);
                                    }

                                    //overdueAmountDecimal = overdueAmountDecimal.multiply(new BigDecimal(10000));
                                    plmsLoanInfo.put("overdueAmount", overdueAmountDecimal);
                                    plmsLoanInfo.put("creditCode", creditInfoCode);
                                    plmsLoanInfo.put("type","01");
                                    plmsLoanInfo.put("status", GlobDict.valid.getKey());

                                    creditInfoDao.insertLoanInfo(plmsLoanInfo);
                                }

                                //针对非正常贷款信息同步已贷款信息表
                                Map aberrantLoan = (Map) loanInfo.get("aberrantLoan");
                                if (aberrantLoan != null) {
                                    //贷款序号
                                    String serialNo = (String) aberrantLoan.get("serialNo");
                                    //贷款状态
                                    String status = (String) aberrantLoan.get("status");

                                    Map plmsLoanInfo = new HashMap();
                                    String aberrantLoanCode = commonService.getUUID();
                                    plmsLoanInfo.put("code", aberrantLoanCode);
                                    plmsLoanInfo.put("loanNo", serialNo);
                                    plmsLoanInfo.put("loanStatus", status);
                                    plmsLoanInfo.put("creditCode", creditInfoCode);
                                    plmsLoanInfo.put("type","02");
                                    plmsLoanInfo.put("status", GlobDict.valid.getKey());

                                    creditInfoDao.insertLoanInfo(plmsLoanInfo);
                                }
                                //针对近12个月累计最高逾期次数信息同步已贷款信息表
                                Map topOverdueCountLoan = (Map) loanInfo.get("topOverdueCountLoan");
                                if (topOverdueCountLoan!= null) {
                                    //贷款序号
                                    String serialNo = (String) topOverdueCountLoan.get("serialNo");
                                    //逾期次数
                                    String overdueCount = (String) topOverdueCountLoan.get("overdueCount");

                                    Map plmsLoanInfo = new HashMap();
                                    String aberrantLoanCode = commonService.getUUID();
                                    plmsLoanInfo.put("code", aberrantLoanCode);
                                    plmsLoanInfo.put("loanNo", serialNo);
                                    plmsLoanInfo.put("overdueNum", overdueCount);
                                    plmsLoanInfo.put("creditCode", creditInfoCode);
                                    plmsLoanInfo.put("type","03");
                                    plmsLoanInfo.put("status", GlobDict.valid.getKey());

                                    creditInfoDao.insertLoanInfo(plmsLoanInfo);
                                }

                                //针对近12个月最高连续逾期期数信息同步已贷款信息表
                                Map topContinuousOverdueLoan = (Map) loanInfo.get("topContinuousOverdueLoan");
                                if (topContinuousOverdueLoan!= null) {
                                    //贷款序号
                                    String serialNo = (String) topContinuousOverdueLoan.get("serialNo");
                                    //期数
                                    String continuityOverduePeriods = (String) topContinuousOverdueLoan.get("continuityOverduePeriods");

                                    Map plmsLoanInfo = new HashMap();
                                    String topContinuousOverdueLoanCode = commonService.getUUID();
                                    plmsLoanInfo.put("code", topContinuousOverdueLoanCode);
                                    plmsLoanInfo.put("loanNo", serialNo);
                                    plmsLoanInfo.put("period", continuityOverduePeriods);
                                    plmsLoanInfo.put("creditCode", creditInfoCode);
                                    plmsLoanInfo.put("type","04");
                                    plmsLoanInfo.put("status", GlobDict.valid.getKey());

                                    creditInfoDao.insertLoanInfo(plmsLoanInfo);
                                }
                            }
                        }

                        List creditCardInfos = (List<Map>) loanCreditInfo.get("creditCardInfos");
                        if (creditCardInfos != null && creditCardInfos.size() > 0) {
                            for (int i = 0; i < creditCardInfos.size(); i++) {
                                Map creditCardInfo = (Map) creditCardInfos.get(i);
                                String type = (String)creditCardInfo.get("type");
                                if (null != type && "非正常类贷记卡".equals(type)){
                                    //贷记卡序号
                                    String serialNo = (String) creditCardInfo.get("serialNumber");
                                    //贷款状态
                                    String loanStatus = (String) creditCardInfo.get("content");

                                    Map plmsCreaditCard = new HashMap();
                                    String cardAbnormalCode = commonService.getUUID();
                                    plmsCreaditCard.put("code", cardAbnormalCode);
                                    plmsCreaditCard.put("cardNo", serialNo);
                                    plmsCreaditCard.put("cardStatus", loanStatus);
                                    plmsCreaditCard.put("type","02");
                                    plmsCreaditCard.put("creditCode", creditInfoCode);
                                    plmsCreaditCard.put("status", GlobDict.valid.getKey());

                                    creditInfoDao.insertCreaditCard(plmsCreaditCard);
                                }
                                //针对贷记卡信息列表下的当前逾期信息同步已贷款信息表
                                Map cardCurrentOverdue = (Map) creditCardInfo.get("cardCurrentOverdue");
                                if (cardCurrentOverdue != null){
                                    //贷款序号
                                    String serialNo = (String) cardCurrentOverdue.get("serialNo");
                                    //逾期状态
                                    String overdueAmount = null;
                                    if (cardCurrentOverdue.get("overdueAmount") != null) {
                                        overdueAmount = String.valueOf(cardCurrentOverdue.get("overdueAmount"));
                                    }

                                    Map plmsCreaditCard = new HashMap();
                                    String creaditCardCode = commonService.getUUID();
                                    plmsCreaditCard.put("code", creaditCardCode);
                                    plmsCreaditCard.put("cardNo", serialNo);
                                    BigDecimal overdueAmountDecimal = null;
                                    if (overdueAmount != null){
                                        overdueAmountDecimal = new BigDecimal(overdueAmount);
                                    }
                                    //overdueAmountDecimal = overdueAmountDecimal.multiply(new BigDecimal(10000));
                                    plmsCreaditCard.put("overdueAmount", overdueAmount);
                                    plmsCreaditCard.put("type","01");
                                    plmsCreaditCard.put("creditCode", creditInfoCode);
                                    plmsCreaditCard.put("status", GlobDict.valid.getKey());

                                    creditInfoDao.insertCreaditCard(plmsCreaditCard);
                                }

                                //针对贷记卡信息列表下的非正常类贷记卡信息同步已贷款信息表
//                                    Map cardAbnormal = (HashMap) creditCardInfo.get("cardAbnormal");
//                                    if (cardAbnormal != null) {
//                                        //贷记卡序号
//                                        String serialNo = (String) cardAbnormal.get("serialNo");
//                                        //贷款状态
//                                        String loanStatus = (String) cardAbnormal.get("loanStatus");
//
//                                        Map plmsCreaditCard = new HashMap();
//                                        String cardAbnormalCode = commonService.getUUID();
//                                        plmsCreaditCard.put("code", cardAbnormalCode);
//                                        plmsCreaditCard.put("cardNo", serialNo);
//                                        plmsCreaditCard.put("cardStatus", loanStatus);
//                                        plmsCreaditCard.put("type","02");
//                                        plmsCreaditCard.put("creditCode", creditInfoCode);
//                                        plmsCreaditCard.put("status", GlobDict.valid.getKey());
//
//                                        creditInfoDao.insertCreaditCard(plmsCreaditCard);
//                                    }
                                //针对贷记卡信息列表下的近12个月累计最高逾期次数
                                Map cardTotalOverdueCount = (Map) creditCardInfo.get("cardTotalOverdueCount");
                                if (cardTotalOverdueCount != null) {
                                    //贷记卡序号
                                    String serialNo = (String) cardTotalOverdueCount.get("serialNo");
                                    //贷款状态
                                    String overdueCount = (String) cardTotalOverdueCount.get("overdueCount");

                                    Map plmsCreaditCard = new HashMap();
                                    String cardTotalOverdueCode = commonService.getUUID();
                                    plmsCreaditCard.put("code", cardTotalOverdueCode);
                                    plmsCreaditCard.put("cardNo", serialNo);
                                    plmsCreaditCard.put("overdueNum", overdueCount);
                                    plmsCreaditCard.put("type","03");
                                    plmsCreaditCard.put("creditCode", creditInfoCode);
                                    plmsCreaditCard.put("status", GlobDict.valid.getKey());

                                    creditInfoDao.insertCreaditCard(plmsCreaditCard);
                                }

                                //针对贷记卡信息列表下的近12个月最高连续逾期期数
                                Map cardContinuityOverdueCount = (Map) creditCardInfo.get("cardContinuityOverdueCount");
                                if (cardContinuityOverdueCount != null ) {
                                    //贷记卡序号
                                    String serialNo = (String) cardContinuityOverdueCount.get("serialNo");
                                    //贷款状态
                                    String continuityOverduePeriods = (String) cardContinuityOverdueCount.get("continuityOverduePeriods");

                                    Map plmsCreaditCard = new HashMap();
                                    String cardContinuityOverdueCountCode = commonService.getUUID();
                                    plmsCreaditCard.put("code", cardContinuityOverdueCountCode);
                                    plmsCreaditCard.put("cardNo", serialNo);
                                    plmsCreaditCard.put("period", continuityOverduePeriods);
                                    plmsCreaditCard.put("type","04");
                                    plmsCreaditCard.put("creditCode", creditInfoCode);
                                    plmsCreaditCard.put("status", GlobDict.valid.getKey());

                                    creditInfoDao.insertCreaditCard(plmsCreaditCard);
                                }
                            }
                        }

                        //同步担保信息表
                        List loanGuaranteeInfos = (List<Map>) loanCreditInfo.get("loanGuaranteeInfos");
                        if (loanGuaranteeInfos != null && loanGuaranteeInfos.size() > 0) {
                            for (int i = 0; i < loanGuaranteeInfos.size(); i++) {
                                Map loanGuaranteeInfo = (Map) loanGuaranteeInfos.get(i);
                                String guaranteeStatus = (String) loanGuaranteeInfo.get("guaranteeStatus");
                                String guaranteeAmount = null;
                                if (loanGuaranteeInfo.get("guaranteeAmount") != null) {
                                    guaranteeAmount = String.valueOf(loanGuaranteeInfo.get("guaranteeAmount"));
                                }
                                String guaranteeBalance = null;
                                if (loanGuaranteeInfo.get("guaranteeBalance") != null) {
                                    guaranteeBalance = String.valueOf(loanGuaranteeInfo.get("guaranteeBalance"));
                                }

                                Map plmsLoanGuaranteeInfo = new HashMap();
                                String loanGuaranteeInfoCode = commonService.getUUID();
                                plmsLoanGuaranteeInfo.put("code", loanGuaranteeInfoCode);
                                plmsLoanGuaranteeInfo.put("guaranteeStatus", guaranteeStatus);
                                BigDecimal guaranteeAmountDecimal = null;
                                if (guaranteeAmount != null){
                                    guaranteeAmountDecimal = new BigDecimal(guaranteeAmount);
                                }
                                plmsLoanGuaranteeInfo.put("guaranteeAmount", guaranteeAmountDecimal);
                                BigDecimal guaranteeBalanceDecimal = null;
                                if (guaranteeBalance != null){
                                    guaranteeBalanceDecimal = new BigDecimal(guaranteeBalance);
                                }
                                plmsLoanGuaranteeInfo.put("guaranteeBalance", guaranteeBalanceDecimal);
                                plmsLoanGuaranteeInfo.put("creditCode", creditInfoCode);
                                plmsLoanGuaranteeInfo.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertLoanGuaranteeInfo(plmsLoanGuaranteeInfo);
                            }
                        }
                    }
                    //同步司法信息
                    if (loanJsuticeInfoList != null && loanJsuticeInfoList.size() > 0) {
                        for (int i = 0; i < loanJsuticeInfoList.size(); i++) {
                            Map loanJsuticeInfo = (Map) loanJsuticeInfoList.get(i);
                            String name = (String) loanJsuticeInfo.get("name");
                            String justiceInfo = (String) loanJsuticeInfo.get("justiceInfoCode");
                            String caseNo = (String) loanJsuticeInfo.get("caseNo");
                            String subjectExecution = (String) loanJsuticeInfo.get("subjectExecution");
                            String flagLawsuit = (String) loanJsuticeInfo.get("flagLawsuit");
                            String flagLawsuitCode = null;
                            if (flagLawsuit != null && GlobDict.bms_is_closed_false.getDesc().equals(flagLawsuit)) {
                                flagLawsuitCode = GlobDict.bms_is_closed_false.getKey();
                            }
                            if (flagLawsuit != null && GlobDict.bms_is_closed_true.getDesc().equals(flagLawsuit)) {
                                flagLawsuitCode = GlobDict.bms_is_closed_true.getKey();
                            }
                            String infoNote = (String) loanJsuticeInfo.get("infoNote");

                            Map plmsLoanJsuticeInfo = new HashMap();
                            String loanJsuticeInfoCode = commonService.getUUID();
                            plmsLoanJsuticeInfo.put("code", loanJsuticeInfoCode);
                            plmsLoanJsuticeInfo.put("name", name);
                            plmsLoanJsuticeInfo.put("hasLitigationInfo", justiceInfo);
                            plmsLoanJsuticeInfo.put("litigationNo", caseNo);
                            plmsLoanJsuticeInfo.put("execution", subjectExecution);
                            plmsLoanJsuticeInfo.put("isClosed", flagLawsuitCode);
                            plmsLoanJsuticeInfo.put("details", infoNote);
                            plmsLoanJsuticeInfo.put("applyCode", applyCode);
                            plmsLoanJsuticeInfo.put("status", GlobDict.valid.getKey());

                            judicialInfoDao.insert(plmsLoanJsuticeInfo);
                        }
                    }
                    //同步案件评估信息
                    if (loanCaseUse != null) {
                        String familyAssets = (String) loanCaseUse.get("familyAssets");
                        String familyDebt = (String) loanCaseUse.get("familyDebt");
                        String familyIncome = (String) loanCaseUse.get("familyIncome");
                        String mortgaged = (String) loanCaseUse.get("mortgaged");
                        String loanUse = (String) loanCaseUse.get("loanUse");
                        String refundSource = (String) loanCaseUse.get("refundSource");

                        Map plmsLoanCaseUse = new HashMap();
                        String loanCaseUseCode = commonService.getUUID();
                        plmsLoanCaseUse.put("code", loanCaseUseCode);
                        plmsLoanCaseUse.put("majorAsset", familyAssets);
                        plmsLoanCaseUse.put("majorLiabilities", familyDebt);
                        plmsLoanCaseUse.put("incomeSources", familyIncome);
                        plmsLoanCaseUse.put("collateralInvestigation", mortgaged);
                        plmsLoanCaseUse.put("loanUsage", loanUse);
                        plmsLoanCaseUse.put("repaymentSource", refundSource);
                        plmsLoanCaseUse.put("applyCode", applyCode);
                        plmsLoanCaseUse.put("status", GlobDict.valid.getKey());

                        loanCaseUseDao.insert(plmsLoanCaseUse);
                    }

                    //同步附件信息
                    if (loanAttachList != null && loanAttachList.size() > 0) {
                        for (int i = 0; i < loanAttachList.size(); i++) {
                            Map loanAttach = (Map) loanAttachList.get(i);
                            List fileList = new ArrayList();
                            Map fileMap = new HashMap();
                            fileMap.put("serialNo",(String) loanAttach.get("fileId"));
                            fileList.add(fileMap);
                            Map result = new HashMap();

                            map.put("serialNoList",fileList);
                            try {
                                result = remoteFmsService.getUrlList(map);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Map body = (Map)result.get(Fields.PARAM_MESSAGE_BODY);
                            List fileMaps = (List)body.get("fileMaps");
                            String fileSuffix = null;
                            String uploadTime = null;
                            String fileCode = null;
                            if (fileMaps!=null && fileMaps.size()>0) {
                                fileMap = (Map) fileMaps.get(0);
                                fileCode = (String) fileMap.get("serialNo");
                                fileSuffix = (String) fileMap.get("fileSuffix");
                                uploadTime = (String) fileMap.get("uploadTime");
                            }
                            String fileType = (String) loanAttach.get("fileType");
                            if (fileType != null && fileType.length() == 1){
                                fileType = "0"+fileType;
                            }
                            String fileId = (String) loanAttach.get("fileId");
                            String fileName = (String) loanAttach.get("fileName");
                            Boolean isDelete = (Boolean) loanAttach.get("isDeleteFlag");
                            String status = (String)GlobDict.valid.getKey();
                            if (isDelete){
                                status = GlobDict.un_valid.getKey();
                            }
                            Map plmsFileMap = new HashMap();
                            String code = commonService.getUUID();
                            plmsFileMap.put("code", code);
                            plmsFileMap.put("fileId", fileId);
                            plmsFileMap.put("fileCode",fileCode);
                            plmsFileMap.put("fileName", fileName);
                            plmsFileMap.put("fileSuffix", fileSuffix);
                            plmsFileMap.put("fileType", fileType);
                            plmsFileMap.put("isDelete",isDelete);
                            plmsFileMap.put("status",status);
                            plmsFileMap.put("applyCode",applyCode);
                            plmsFileMap.put("uploadTime",DateUtil.parseDateTimeForPattern(uploadTime, DateUtil.DATE_TIME_PATTERN3));
                            applyInfoDao.insertMaterial(plmsFileMap);
                        }
                    }

                    //同步审批信息表记录
                    if (loanRiskInfo != null) {
                        String firstOpinion = (String) loanRiskInfo.get("firstOpinion");
                        String secondOpinion = (String) loanRiskInfo.get("secondOpinion");

                        String thirdOpinion = (String) loanRiskInfo.get("thirdOpinion");
                        String proprietaryOption = (String) loanRiskInfo.get("proprietaryOption");

                        String insuranceOption = (String) loanRiskInfo.get("insuranceOption");
                        String guaranteeCorporationOption = (String) loanRiskInfo.get("guaranteeCorporationOption");

                        String guaranteeTerms = (String) loanRiskInfo.get("guaranteeTerms");
                        String lendingTerms = (String) loanRiskInfo.get("lendingTerms");

                        String totalApprovalAmount = null;
                        if (loanRiskInfo.get("totalApprovalAmount") != null) {
                            totalApprovalAmount = String.valueOf(loanRiskInfo.get("totalApprovalAmount"));
                        }
                        BigDecimal totalApprovalAmountDecimal = null;
                        if (totalApprovalAmount != null){
                            totalApprovalAmountDecimal = new BigDecimal(totalApprovalAmount);
                        }
                        String guaranteeAmount = null;
                        if (loanRiskInfo.get("guaranteeAmount") != null) {
                            guaranteeAmount = String.valueOf(loanRiskInfo.get("guaranteeAmount"));
                        }
                        BigDecimal guaranteeAmountDecimal = null;
                        if (guaranteeAmount != null){
                            guaranteeAmountDecimal = new BigDecimal(guaranteeAmount);
                        }


                        Integer approvalPeriod = null;
                        if (loanPawnList != null && loanPawnList.size() >0) {
                            Map loanPawn = (Map)loanPawnList.get(0);
                            approvalPeriod = (Integer) loanPawn.get("approvalPeriod");
                        }
                        String approvalDate = (String) loanRiskInfo.get("approvalDate");

                        Map plmsLoanRiskInfo = new HashMap();
                        String loanRiskInfoCode = commonService.getUUID();
                        plmsLoanRiskInfo.put("code", loanRiskInfoCode);
                        plmsLoanRiskInfo.put("firstApprovalSuggestion", firstOpinion);
                        plmsLoanRiskInfo.put("secondApprovalSuggesion", secondOpinion);
                        plmsLoanRiskInfo.put("finalApprovalSuggesion", thirdOpinion);

                        plmsLoanRiskInfo.put("fundSideSuggesion", proprietaryOption);
                        plmsLoanRiskInfo.put("insuranceSuggesion", insuranceOption);
                        plmsLoanRiskInfo.put("guaranteeSuggesion", guaranteeCorporationOption);

                        plmsLoanRiskInfo.put("guaranteeCondition", guaranteeTerms);
                        plmsLoanRiskInfo.put("lendingTerms", lendingTerms);
                        plmsLoanRiskInfo.put("approvalQuota", totalApprovalAmountDecimal);

                        plmsLoanRiskInfo.put("guaranteeLimit", guaranteeAmountDecimal);
                        plmsLoanRiskInfo.put("approvalPeriod", approvalPeriod);
                        plmsLoanRiskInfo.put("approvalTime", approvalDate);

                        plmsLoanRiskInfo.put("status", GlobDict.valid.getKey());

                        plmsLoanRiskInfo.put("applyCode", applyCode);

                        loanRiskInfoDao.insert(plmsLoanRiskInfo);
                    }
                }
            }
            //同步合同信息
            synchronizeContract(map);
            //还款计划相关
            bmsRepaymentScheduleService.calculateRepaymentScheduleDependencyInfo(map);
        }
    }

    /**
     * 同步核心合同信息
     * @param data
     */
    void synchronizeContract(Map data) throws ErrorException {
        //核心系统code
        String bmsCode = (String) data.get("bmsLoanCode");
        //运营信息
        Map operationsInfo = (Map) data.get("operationsInfo");
        //合同信息记录
        List<Map> contractInfoList = (List<Map>) operationsInfo.get("contractInfo");
        String contractCode = null;
        //实收实付信息
        Map receiveAndPaidInfo = (Map) operationsInfo.get("receiveAndPaidInfo");
        //运营登记信息
        Map operationsRegisterInfo = (Map) operationsInfo.get("operationsRegisterInfo");
        Map lendingRegistration = (Map) operationsRegisterInfo.get("lendingRegistration");
        //运营材料信息
        List<Map> operationsMaterialInfoList = (List<Map>) operationsInfo.get("operationsMaterialInfo");

        //风控审批信息
        Map riskControlInfo = (Map) data.get("riskControlInfo");
        //审批通知单
        Map approvalNoticeInfo = (Map) riskControlInfo.get("approvalNoticeInfo");


        //进件信息
        Map applyInfo = (Map) data.get("applyInfo");
        //抵押物信息列表(进件所有抵押物)
       List<Map> collateralInfoList = (List<Map>) data.get("loanPawnList");
        Map collateralInfoMap = new HashedMap();
        if (collateralInfoList != null && collateralInfoList.size() > 0) {
            for (Map collateralInfo : collateralInfoList) {
                collateralInfoMap.put(collateralInfo.get("id"), collateralInfo);
            }
        }

        //渠道信息
        Map distrbuteInfo = (Map) data.get("distrbuteInfo");

        //渠道产品信息
        Map distributeProductInfo = (Map) data.get("distributeProductInfo");

        //资方信息
        Map fundSideInfo = (Map) data.get("fundSideInfo");
        //收息方式
        Map interestCollectionMethod = (Map) distributeProductInfo.get("interestCollectionMethod");

        //资方产品信息
        Map fundSideProductInfo = (Map) data.get("fundSideProductInfo");

        //担保公司信息
        Map guaranteeInfo = (Map) data.get("guaranteeInfo");

        //保险公司
        Map insuranceInfo = (Map) data.get("insuranceInfo");

        //合同抵押物列表
        List<Map> collateralList = null;
        //创建借款合同信息记录
        if (contractInfoList != null && contractInfoList.size() > 0) {
            for (Map contractInfo : contractInfoList) {
                boolean isLoanContract = (boolean) contractInfo.get("isLoanContract");
                if (!isLoanContract) {
                    continue;
                }

                collateralList = (List<Map>) contractInfo.get("collateralList");

                contractCode = commonService.getUUID();
                //保存合同
                Contract contract = new Contract();
                contract.setCode(contractCode);
                String contractNo = (String) contractInfo.get("contractNo");
                logger.info("本次同步的合同编号是：" + contractNo);
                if (StringUtils.isBlank(contractNo)) {
                    logger.info("合同编号存在异常！");
                    throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "合同编号异常");
                }
                contract.setContractNo(contractNo);
                //获取进件编号
                ApplyInfo applyInfoBean = applyInfoDao.qryApplyInfoByBmsCode(bmsCode);
                if (null == applyInfoBean) {
                    logger.info("获取进件bmsCode=" + bmsCode + "出现异常!");
                    throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "对应进件信息异常");
                }
                contract.setApplyCode(applyInfoBean.getCode());

                String strCreditStartDate = (String) lendingRegistration.get("creditTime");
                contract.setCreditStartDate(strCreditStartDate);

                Map collateralInfo = (Map) collateralInfoMap.get(collateralList.get(0).get("id"));
                Integer approvalPeriod = null;
                if (collateralInfo != null) {
                    approvalPeriod = (Integer) collateralInfo.get("approvalPeriod");
                    if (approvalPeriod != null) {
                        contract.setCreditPeriod(approvalPeriod.toString());
                    }
                }

                if (approvalPeriod != null && strCreditStartDate!= null) {
                    Date creditEndDate = PlmsUtil.getDueDate(DateUtil.parseDateTimeForPattern(strCreditStartDate, DateUtil.DATE_PATTERN2), approvalPeriod);

                    String strCreditEndDate = DateUtil.formatDateTime(creditEndDate, DateUtil.DATE_PATTERN2);
                    contract.setCreditEndDate(strCreditEndDate);
                }

                String today = DateUtil.getCurrentDateTime2();
                contract.setCreateDate(today);
                contract.setModifyDate(today);

                contract.setStatus(GlobDict.contract_status_valid.getKey());

                contractDao.saveContractInfo(contract);
                break;
            }
            //抵押类型（同一个合同抵押类型相同）
            String mortgageTypeCode = null;
            //保存抵押物信息
            if (collateralList != null && collateralList.size() > 0) {
                for (int i = 0; i < collateralList.size(); i++) {
                    Map collateralInfo = (Map) collateralInfoMap.get(collateralList.get(i).get("id"));
                    if (collateralInfo != null) {
                        mortgageTypeCode = (String) collateralInfo.get("mortgageType");
                        collateralInfo.put("contractCode", contractCode);
                        createHouseInfo(collateralInfo);
                    }
                }
            }

            AccountInfo accountInfo = new AccountInfo();
            //为资方还款账户创建银行账户表记录
            String fundSideAccountCode = commonService.getUUID();
            accountInfo.setCode(fundSideAccountCode);
            accountInfo.setName((String) fundSideInfo.get("receiveAccountName"));
            accountInfo.setBank((String) fundSideInfo.get("receiveBank"));
            accountInfo.setAccountNo((String) fundSideInfo.get("receiveAccountNo"));
            accountInfo.setStatus(GlobDict.account_info_status_valid.getKey());
            accountInfo.setCreateDate(DateUtil.getCurrentDateTime2());
            accountInfo.setModifyDate(DateUtil.getCurrentDateTime2());
            accountInfoDao.saveAccountInfo(accountInfo);
            //为宏获还款账户创建银行账户表记录
            String honghuoAccountCode = commonService.getUUID();
            accountInfo.setCode(honghuoAccountCode);
            accountInfo.setName((String) distrbuteInfo.get("interestAccountName"));
            accountInfo.setBank((String) distrbuteInfo.get("interestAccountBank"));
            accountInfo.setAccountNo((String) distrbuteInfo.get("interestAccountNo"));
            accountInfo.setStatus(GlobDict.account_info_status_valid.getKey());
            accountInfo.setCreateDate(DateUtil.getCurrentDateTime2());
            accountInfo.setModifyDate(DateUtil.getCurrentDateTime2());
            accountInfoDao.saveAccountInfo(accountInfo);
            //为宏获保证金账户创建银行账户表记录
            String honghuoDepositAccountCode = commonService.getUUID();
            accountInfo.setCode(honghuoDepositAccountCode);
            accountInfo.setName((String) distrbuteInfo.get("interestAccountName"));
            accountInfo.setBank((String) distrbuteInfo.get("interestAccountBank"));
            accountInfo.setAccountNo((String) distrbuteInfo.get("interestAccountNo"));
            accountInfo.setStatus(GlobDict.account_info_status_valid.getKey());
            accountInfo.setCreateDate(DateUtil.getCurrentDateTime2());
            accountInfo.setModifyDate(DateUtil.getCurrentDateTime2());
            accountInfoDao.saveAccountInfo(accountInfo);

            //创建进件利息信息表记录
            ApplyInteresting applyInteresting = new ApplyInteresting();
            String applyInterestingCode = commonService.getUUID();
            applyInteresting.setCode(applyInterestingCode);

            Map collateralInfo = (Map) collateralInfoMap.get(collateralList.get(0).get("id"));
            if (collateralInfo != null) {
                applyInteresting.setAnnualizedInterest(null == collateralInfo.get("interestRate") ? "0" : collateralInfo.get("interestRate").toString());
            }

            //本金逾期年化利率
            applyInteresting.setPrincipalOverInterest((String) distributeProductInfo.get("principalOverInterest"));
            //利息逾期年化利率
            applyInteresting.setInterestOverInterest((String) distributeProductInfo.get("interestOverInterest"));
            applyInteresting.setServiceFeeInterestRate((String) distributeProductInfo.get("serviceFeeOverInterest"));
            applyInteresting.setDefaultInterestRate((String) distributeProductInfo.get("plentyRate"));
            applyInteresting.setContractCode(contractCode);

            //资方机构代码
            String organizationCode = (String) fundSideInfo.get("organizationCode");
            //资方产品名称
            String productName = (String) fundSideProductInfo.get("productName");
            Map fundSideReq = new HashedMap();
            fundSideReq.put("bmsCode", organizationCode);
            fundSideReq.put("productName", productName);
            List<Map> fundSideProductList = plmsFundSideProductDao.queryFundSideProductByParms(fundSideReq);
            Map fundSideProduct = fundSideProductList.get(0);
            applyInteresting.setIsPrincipalInstead(null == fundSideProduct.get("isPrincipalInstead") ? null : fundSideProduct.get("isPrincipalInstead").toString());
            applyInteresting.setIsInterestInstead(null == fundSideProduct.get("isInterestInstead") ? null : fundSideProduct.get("isInterestInstead").toString());


            applyInteresting.setIsFullRepurchase((String) distrbuteInfo.get("isFullRepurchase"));

            applyInteresting.setFundSideCode(fundSideAccountCode);
            applyInteresting.setHonghuoCode(honghuoAccountCode);
            applyInteresting.setHonghuoBailAccountCode(honghuoDepositAccountCode);

            applyInteresting.setStatus(GlobDict.apply_interesting_status_valid.getKey());
            applyInteresting.setCreateDate(DateUtil.getCurrentDateTime2());
            applyInteresting.setModifyDate(DateUtil.getCurrentDateTime2());
            applyInterestingDao.saveApplyInteresting(applyInteresting);

            //创建资方信息表记录
            String companyCode = null;
            PlmsCompany company = new PlmsCompany();
            company.setBmsCode(organizationCode);
            company.setCompanyType(GlobDict.company_type_fundside.getKey());
            company = plmsCompanyDao.getCompanyByBmsCode(company);
            if (null == company) {
                //创建公司信息
                company = new PlmsCompany();
                companyCode = commonService.getUUID();
                company.setCode(companyCode);
                company.setBmsCode(organizationCode);
                company.setCompanyName((String) fundSideInfo.get("organizationName"));
                company.setAbbrName((String) fundSideInfo.get("organizationAbbr"));
                company.setCompanyType(GlobDict.company_type_fundside.getKey());
                company.setStatus(GlobDict.company_status_valid.getKey());
                company.setCreateDate(new Date());
                company.setModifyDate(new Date());
                plmsCompanyDao.saveCompanyInfo(company);
            } else {
                companyCode = company.getCode();
            }
            FundSide fundSide = new FundSide();
            String fundSideCode = commonService.getUUID();
            fundSide.setCode(fundSideCode);
            fundSide.setCompanyCode(companyCode);

            fundSide.setLenders((String) fundSideProductInfo.get("lenders"));
            fundSide.setYearPeriod((String) fundSideProductInfo.get("yearPeriod"));
            fundSide.setDefaultInterestRate((String) fundSideProductInfo.get("penaltyRate"));
            fundSide.setPenaltyPeriod((String) fundSideProductInfo.get("penaltyPeriod"));
            fundSide.setOverInterest((String) fundSideProductInfo.get("overdueAnnualizedInterest"));
            //利率
            List<Map> interestRate = (List<Map>) fundSideProductInfo.get("interestRate");

            if (interestRate != null && interestRate.size() > 0) {
                for (Map rate : interestRate) {
                    String type = (String) rate.get("type");
                    //todo
                    String mortgageType = "";
                    if (mortgageTypeCode!=null && mortgageTypeCode.equals("01")) {
                        mortgageType = "一抵";
                    } else if (mortgageTypeCode!=null && mortgageTypeCode.equals("02")) {
                        mortgageType = "二抵";
                    } else if (mortgageTypeCode!=null && mortgageTypeCode.equals("03")) {
                        mortgageType = "一抵转单";
                    } else if (mortgageTypeCode!=null && mortgageTypeCode.equals("04")) {
                        mortgageType = "二抵转单";
                    }
                    //todo
                    if (!mortgageType.equals(type)) {
                        continue;
                    }
                    fundSide.setActualInterest(null == rate.get("actRate") ? "0" : rate.get("actRate").toString());
                    fundSide.setContractInterest(null == rate.get("rate") ? "0" : rate.get("rate").toString());
                }
            }

            //收息方式
            String method = (String) interestCollectionMethod.get("method");
            if (BmsGlobDict.fund_side_receive_interest_method_pre.getDesc().equals(method)) {
                fundSide.setReceiveInterestMethod(GlobDict.fund_side_receive_interest_method_pre.getKey());
            } else if (BmsGlobDict.fund_side_receive_interest_method_back.getDesc().equals(method)) {
                fundSide.setReceiveInterestMethod(GlobDict.fund_side_receive_interest_method_back.getKey());
            } else if (BmsGlobDict.fund_side_receive_interest_method_fix.getDesc().equals(method)) {
                fundSide.setReceiveInterestMethod(GlobDict.fund_side_receive_interest_method_fix.getKey());
            }
            fundSide.setReceiveInterestDate(null == interestCollectionMethod.get("interestDate") ? "0" : interestCollectionMethod.get("interestDate").toString());
            fundSide.setMortgageInterestPeriod(null == interestCollectionMethod.get("term") ? "0" : interestCollectionMethod.get("term").toString());
            fundSide.setInterestGraceDate(null == interestCollectionMethod.get("moratorium") ? "0" : interestCollectionMethod.get("moratorium").toString());

            fundSide.setServiceChargeRate(null == receiveAndPaidInfo.get("proprietaryProductServiceFeeRatio") ? "0" : receiveAndPaidInfo.get("proprietaryProductServiceFeeRatio").toString());
            fundSide.setServiceChargeAmount(null == receiveAndPaidInfo.get("proprietaryProductServiceFeeRatioAmount") ? "0" : receiveAndPaidInfo.get("proprietaryProductServiceFeeRatioAmount").toString());
            fundSide.setServiceChargePaidAmount(null == receiveAndPaidInfo.get("proprietaryProductServiceFeeRatioReviceAmount") ? "0" : receiveAndPaidInfo.get("proprietaryProductServiceFeeRatioReviceAmount").toString());
            fundSide.setServiceChargePaidTime(null == receiveAndPaidInfo.get("proprietaryProductServiceFeeRatioDate") ? null : receiveAndPaidInfo.get("proprietaryProductServiceFeeRatioDate").toString());
            fundSide.setBailRate(null == receiveAndPaidInfo.get("proprietaryProductMarginRatio") ? "0" : receiveAndPaidInfo.get("proprietaryProductMarginRatio").toString());
            fundSide.setBailAmount(null == receiveAndPaidInfo.get("proprietaryProductMarginAmount") ? "0" : receiveAndPaidInfo.get("proprietaryProductMarginAmount").toString());
            fundSide.setBailPayedAmount(null == receiveAndPaidInfo.get("proprietaryProductMarginRevice") ? "0" : receiveAndPaidInfo.get("proprietaryProductMarginRevice").toString());
            fundSide.setBailPayedTime((String) receiveAndPaidInfo.get("proprietaryProductMarginDate"));
            fundSide.setReceiptFeeRate(null == receiveAndPaidInfo.get("proprietaryProductReceiptRatio") ? "0" : receiveAndPaidInfo.get("proprietaryProductReceiptRatio").toString());
            fundSide.setReceiptFeeAmount(null == receiveAndPaidInfo.get("proprietaryProductReceiptRatioAmount") ? "0" : receiveAndPaidInfo.get("proprietaryProductReceiptRatioAmount").toString());
            fundSide.setReceiptFeePaidAmount(null == receiveAndPaidInfo.get("proprietaryProductReceiptRatioReviceAmount") ? "0" : receiveAndPaidInfo.get("proprietaryProductReceiptRatioReviceAmount").toString());
            fundSide.setReceiptFeePaidTime((String) receiveAndPaidInfo.get("proprietaryProductReceiptRatioDate"));
            fundSide.setContractCode(contractCode);
            fundSide.setStatus(GlobDict.fund_side_status_valid.getKey());
            fundSide.setCreateDate(DateUtil.getCurrentDateTime2());
            fundSide.setModifyDate(DateUtil.getCurrentDateTime2());
            fundSideDao.saveFundSideInfo(fundSide);

            //创建担保公司
            createGuaranteeCorporation(receiveAndPaidInfo, guaranteeInfo, contractCode);
            //创建保险公司
            createInsuranceCompany(receiveAndPaidInfo, insuranceInfo, contractCode);
            //创建客户费用信息
            createCustomerFeeInfo(receiveAndPaidInfo, contractCode);
            //创建签约公正信息
            createSignNotarial(operationsRegisterInfo, contractCode);
            //创建抵押登记信息
            createMortgage(operationsRegisterInfo, contractCode);
            //创建产调查询表记录
            createInvestQuery(operationsRegisterInfo, contractCode);
            //创建归档材料信息
            if (operationsMaterialInfoList != null && operationsMaterialInfoList.size() > 0) {
                for (Map operationsMaterialInfo : operationsMaterialInfoList) {
                    createPigeonholeInfo(operationsMaterialInfo, contractCode);
                }
            }
        }
    }

    /**
     * 抵押物信息保存
     * @param collateralInfo  抵押物信息
     */
    private void createHouseInfo(Map collateralInfo){
        String mortgageTypeCode = (String) collateralInfo.get("mortgageType");
        House house = new House();
        String code = commonService.getUUID();
        house.setCode(code);
        house.setCertificateNumberFirst((String) collateralInfo.get("certificateNumberFirst"));
        house.setCertificateNumberSecond((String) collateralInfo.get("certificateNumberSecond"));

        //登记日期
        String strRegisterDate = (String) collateralInfo.get("purchasingDate");
        house.setRegisterDate(strRegisterDate);

        //评估价
        house.setInquiryInformation(collateralInfo.get("valuation") == null ? "0" :collateralInfo.get("valuation").toString());

        house.setPropertyOwner((String) collateralInfo.get("propertyOwner"));
        house.setCityNo((String) collateralInfo.get("cityName"));
        house.setPropertyArea((String) collateralInfo.get("propertyArea"));
        house.setCertificateAddress((String) collateralInfo.get("propertyAddress"));

        //土地性质
        String landProperty = (String) collateralInfo.get("landProperty");
        String landPropertyCode = null;
        if(BmsGlobDict.house_land_property_national.getDesc().equals(landProperty)){
            landPropertyCode = GlobDict.house_land_property_national.getKey();
        }else if(BmsGlobDict.house_land_property_collective.getDesc().equals(landProperty)){
            landPropertyCode = GlobDict.house_land_property_collective.getKey();
        }
        house.setLandProperty(landPropertyCode);

        //土地使用权取得方式
        String acquisitionForm = (String) collateralInfo.get("landMakeWay");
        String acquisitionFormCode = null;
        if(BmsGlobDict.house_acquisition_form_sell.getDesc().equals(acquisitionForm)){
            acquisitionFormCode = GlobDict.house_acquisition_form_sell.getKey();
        }else if(BmsGlobDict.house_acquisition_form_allotted.getDesc().equals(acquisitionForm)){
            acquisitionFormCode = GlobDict.house_acquisition_form_allotted.getKey();
        }else if(BmsGlobDict.house_acquisition_form_transfer.getDesc().equals(acquisitionForm)){
            acquisitionFormCode = GlobDict.house_acquisition_form_transfer.getKey();
        }
        house.setAcquisitionForm(acquisitionFormCode);

        //房屋类型
        String type = (String) collateralInfo.get("propertyType");
        String typeCode = null;
        if(BmsGlobDict.house_type_apartment.getDesc().equals(type)){
            typeCode = GlobDict.house_type_apartment.getKey();
        }else if(BmsGlobDict.house_type_villa.getDesc().equals(type)){
            typeCode = GlobDict.house_type_villa.getKey();
        }
        house.setType(typeCode);

        house.setOwnerSource((String) collateralInfo.get("homeOwnership"));

        //建筑面积
        String coveredArea = (String) collateralInfo.get("coveredArea");
        house.setCoveredArea(coveredArea);

        house.setGarageAddress((String) collateralInfo.get("garageAddress"));

        //车库面积
        String garageArea = (String) collateralInfo.get("garageArea");
        house.setGarageArea(garageArea);

        if (null != collateralInfo.get("totalFloors")) {
            house.setTotalFloor(((Integer) collateralInfo.get("totalFloors")).toString());
        }
        house.setBuildYear((String) collateralInfo.get("completionDate"));

        //共有类型
        String publicType = (String) collateralInfo.get("publicType");
        String shareTypeCode = null;
        if(BmsGlobDict.house_share_type_common.getDesc().equals(publicType)){
            shareTypeCode = GlobDict.house_share_type_common.getKey();
        }else if(BmsGlobDict.house_share_type_several.getDesc().equals(publicType)){
            shareTypeCode = GlobDict.house_share_type_several.getKey();
        }
        house.setShareType(shareTypeCode);

        house.setMinorsUnits((String) collateralInfo.get("propertyMinors"));
        house.setLandUsage((String) collateralInfo.get("landUse"));

        //房屋使用情况
        String houseUse = (String) collateralInfo.get("housingUsage");
        if(BmsGlobDict.house_usage_detail_self.getDesc().equals(houseUse)){
            houseUse = GlobDict.house_usage_detail_self.getKey();
        }else if(BmsGlobDict.house_usage_detail_record_rent.getDesc().equals(houseUse)){
            houseUse = GlobDict.house_usage_detail_record_rent.getKey();
        }else if(BmsGlobDict.house_usage_detail_unrecord_rent.getDesc().equals(houseUse)){
            houseUse = GlobDict.house_usage_detail_unrecord_rent.getKey();
        }else if(BmsGlobDict.house_usage_detail_vacancy.getDesc().equals(houseUse)){
            houseUse = GlobDict.house_usage_detail_vacancy.getKey();
        }
        house.setUsageDetail(houseUse);

        //是否唯一住房
        Boolean isUnique = (Boolean) collateralInfo.get("onlyHouse");
        if(isUnique != null &&isUnique){
            house.setIsUnique(GlobDict.house_is_unique_yes.getKey());
        }else if (isUnique != null && !isUnique){
            house.setIsUnique(GlobDict.house_is_unique_no.getKey());
        }

        house.setRegisteredResidenceStructure((String) collateralInfo.get("housingStructure"));
        house.setRemark((String) collateralInfo.get("pragmaticRemark"));

        //抵押类型
        if(BmsGlobDict.house_mortgage_type_first.getKey().equals(mortgageTypeCode)){
            mortgageTypeCode = GlobDict.house_mortgage_type_first.getKey();
        }else if(BmsGlobDict.house_mortgage_type_second.getKey().equals(mortgageTypeCode)){
            mortgageTypeCode = GlobDict.house_mortgage_type_second.getKey();
        }else if(BmsGlobDict.house_mortgage_type_first_transfer.getKey().equals(mortgageTypeCode)){
            mortgageTypeCode = GlobDict.house_mortgage_type_first_transfer.getKey();
        }else if(BmsGlobDict.house_mortgage_type_second_transfer.getKey().equals(mortgageTypeCode)){
            mortgageTypeCode = GlobDict.house_mortgage_type_second_transfer.getKey();
        }
        house.setMortgageType(mortgageTypeCode);

        //一抵余额类型
        String firstBalanceTypeCode = (String) collateralInfo.get("balanceType");
        if(BmsGlobDict.first_balance_type_left.getDesc().equals(firstBalanceTypeCode)){
            house.setFirstBalanceType(GlobDict.house_first_balance_type_left.getKey());
        }else if(BmsGlobDict.first_balance_type_top.getDesc().equals(firstBalanceTypeCode)){
            house.setFirstBalanceType(GlobDict.house_first_balance_type_max.getKey());
        }

        //一抵余额金额
        house.setFirstBalanceAmount(collateralInfo.get("mortgageFirstBalance") == null ? "0" : collateralInfo.get("mortgageFirstBalance").toString());
        //一抵金额
        house.setFirstMortgageAmount(collateralInfo.get("mortgageFirstAmount") == null ? "0" : collateralInfo.get("mortgageFirstAmount").toString());
        //二抵金额
        house.setSecondMortgageAmount(collateralInfo.get("mortgageSecondAmount") == null ? "0" : collateralInfo.get("mortgageSecondAmount").toString());

        house.setMortgageRate(collateralInfo.get("mortgageRate") == null ? "0" : collateralInfo.get("mortgageRate").toString());
        house.setContractCode((String) collateralInfo.get("contractCode"));
        String today = DateUtil.getCurrentDateTime2();
        house.setCreateDate(today);
        house.setModifyDate(today);

        house.setStatus(GlobDict.house_status_valid.getKey());

        houseDao.saveHouseInfo(house);

        //保存产调信息
        List<Map> dispatchingInformationList = (List<Map>)collateralInfo.get("propertyAdjustmentInfoList");

        if (dispatchingInformationList != null && dispatchingInformationList.size()>0) {
            createPropertyInvestigation(dispatchingInformationList, code);
        }

        //保存户口信息
        List<Map> accountInformationList = (List<Map>)collateralInfo.get("householdInfoList");
        if (accountInformationList != null && accountInformationList.size()>0) {
            for (Map accountInformation : accountInformationList) {
                accountInformation.put("houseCode", code);
                createHouseholdRegistration(accountInformation);
            }
        }

        //创建抵押物审批信息记录
        collateralInfo.put("code", code);
        createHouseholdHouseApproval(collateralInfo);
    }

    /**
     * 产调信息保存
     * @param dispatchingInformationList 产调信息列表
     * @param houseCode 抵押物 code
     */
    private void createPropertyInvestigation(List<Map> dispatchingInformationList, String houseCode){
        Map dispatchingInformation = dispatchingInformationList.get(0);
        PlmsPropertyInvestigation propertyInvestigation = new PlmsPropertyInvestigation();
        String code = commonService.getUUID();
        propertyInvestigation.setCode(code);
        propertyInvestigation.setHouseCode(houseCode);
        propertyInvestigation.setInCaseInformation((String) dispatchingInformation.get("inCaseInfo"));
        String strInvestigationTime = (String) dispatchingInformation.get("propertyTransferDate");
        propertyInvestigation.setInvestigationTime(DateUtil.parseDateTimeForPattern(strInvestigationTime, DateUtil.DATE_PATTERN2));
        propertyInvestigation.setStatus(GlobDict.property_investigation_status_valid.getKey());
        propertyInvestigation.setCreateDate(new Date());
        propertyInvestigation.setModifyDate(new Date());
        plmsPropertyInvestigationDao.savePlmsPropertyInvestigationInfo(propertyInvestigation);

        //保存产调明细
        if (dispatchingInformationList!= null && dispatchingInformationList.size() >0) {
            for (Map record : dispatchingInformationList) {
                record.put("investigationCode", code);
                createInvestDetail(record);
            }
        }
    }

    /**
     * 产调明细保存
     * @param record 产调明细
     */
    private void createInvestDetail(Map record){
        PlmsInvestDetail investDetail = new PlmsInvestDetail();
        String code = commonService.getUUID();
        investDetail.setCode(code);
        investDetail.setMortgageRank((String) record.get("mortgageWeight"));
        //债权类型
        String mortgageType = (String) record.get("mortgageType");
        if(BmsGlobDict.creditor_rights_type.getDesc().equals(mortgageType)){
            investDetail.setCreditorRightsType((String)BmsGlobDict.creditor_rights_type.getKey());
        }else{
            investDetail.setCreditorRightsType((String)BmsGlobDict.creditor_rights_max.getKey());
        }
        investDetail.setCreditorRightsPerson((String) record.get("mortgagePeople"));
        //债权性质
        //债券性质：银行借贷、民间借贷，
        String creditorProperty = (String) record.get("mortgageProperty");
        if(BmsGlobDict.creditor_property_bank.getDesc().equals(creditorProperty)){
            investDetail.setCreditorProperty(GlobDict.invest_detail_creditor_property_bank.getKey());
        }else if(BmsGlobDict.creditor_property_private.getDesc().equals(creditorProperty)){
            investDetail.setCreditorProperty(GlobDict.invest_detail_creditor_property_private.getKey());
        }
        //债权金额
        String strCreditorAmount = (null == record.get("mortgagePrice") ? "0" : record.get("mortgagePrice").toString());
        investDetail.setCreditorAmount(new BigDecimal(strCreditorAmount));
        investDetail.setInvestigationCode((String) record.get("investigationCode"));
        investDetail.setCreateDate(new Date());
        investDetail.setModifyDate(new Date());
        investDetail.setStatus(GlobDict.invest_detail_status_valid.getKey());
        plmsInvestDetailDao.saveInvestDetail(investDetail);
    }

    /**
     * 户口信息保存
     * @param accountInformation
     */
    private void createHouseholdRegistration(Map accountInformation){
        PlmsHouseholdRegistration householdRegistration = new PlmsHouseholdRegistration();
        String code = commonService.getUUID();
        householdRegistration.setCode(code);
        householdRegistration.setName((String) accountInformation.get("name"));
        householdRegistration.setIdNo((String) accountInformation.get("idCardNum"));
        householdRegistration.setHouseCode((String) accountInformation.get("houseCode"));
        householdRegistration.setCreateDate(new Date());
        householdRegistration.setModifyDate(new Date());
        householdRegistration.setStatus(GlobDict.household_registration_status_valid.getKey());

        plmsHouseholdRegistrationDao.saveHouseholdRegistration(householdRegistration);
    }

    /**
     * 保存抵押物审批信息
     * @param collateralInfo  抵押物信息
     */
    private void createHouseholdHouseApproval(Map collateralInfo){
        PlmsHouseApproval houseApproval = new PlmsHouseApproval();
        String code = commonService.getUUID();
        houseApproval.setCode(code);

        //批复金额
        String strApproveAmount = null == collateralInfo.get("approvalAmount") ? "0" : collateralInfo.get("approvalAmount").toString();
        houseApproval.setApproveAmount(new BigDecimal(strApproveAmount));

        //批复期限
        String approvePeroid = (null == collateralInfo.get("approvalPeriod") ? "0" : collateralInfo.get("approvalPeriod").toString() );
        houseApproval.setApprovePeroid(Integer.parseInt(approvePeroid));

        //合同抵押物价值
        String strHouseValue = (null == collateralInfo.get("contractPawnPrice") ? "0" : collateralInfo.get("contractPawnPrice").toString());
        houseApproval.setHouseValue(new BigDecimal(strHouseValue));

        houseApproval.setHouseCode((String) collateralInfo.get("code"));
        houseApproval.setStatus(GlobDict.house_approval_status_valid.getKey());

        //担保额度
        String strGuaranteeLimit = (null == collateralInfo.get("pawnGuaranteeAmount") ? "0" : collateralInfo.get("pawnGuaranteeAmount").toString());
        houseApproval.setGuaranteeLimit(new BigDecimal(strGuaranteeLimit));

        //抵押权债权总额
        String strMortgageTotalAmount = (null == collateralInfo.get("pawnMortgageAmount") ? "0" : collateralInfo.get("pawnMortgageAmount").toString());
        houseApproval.setMortgageTotalAmount(new BigDecimal(strMortgageTotalAmount));

        houseApproval.setCreateDate(new Date());
        houseApproval.setModifyDate(new Date());
        plmsHouseApprovalDao.savePlmsHouseApproval(houseApproval);
    }

    /**
     * 保存担保公司信息
     * @param receiveAndPaidInfo 实收实付信息
     * @param guaranteeInfo 担保公司信息
     * @param contractCode 合同编号
     */
    private void createGuaranteeCorporation(Map receiveAndPaidInfo , Map guaranteeInfo, String contractCode){
        PlmsGuaranteeCorporation guaranteeCorporation = new PlmsGuaranteeCorporation();
        String code = commonService.getUUID();
        guaranteeCorporation.setCode(code);

        String companyCode = null;
        PlmsCompany company = new PlmsCompany();
        company.setBmsCode((String) guaranteeInfo.get("organizationCode"));
        company.setCompanyType(GlobDict.company_type_guarantee.getKey());
        company = plmsCompanyDao.getCompanyByBmsCode(company);
        if(null == company){
            //创建公司信息
            company = new PlmsCompany();
            companyCode = commonService.getUUID();
            company.setCode(companyCode);
            company.setBmsCode((String) guaranteeInfo.get("organizationCode"));
            company.setCompanyName((String) guaranteeInfo.get("organizationName"));
            company.setAbbrName((String) guaranteeInfo.get("organizationAbbr"));
            company.setCompanyType(GlobDict.company_type_guarantee.getKey());
            company.setStatus(GlobDict.company_status_valid.getKey());
            company.setCreateDate(new Date());
            company.setModifyDate(new Date());
            plmsCompanyDao.saveCompanyInfo(company);
        }else{
            companyCode = company.getCode();
        }
        guaranteeCorporation.setCompanyCode(companyCode);
        //担保费率
        String strGuaranteeRate = (null == receiveAndPaidInfo.get("guaranteeCorporationProductGuaranteeRate") ? "0" : receiveAndPaidInfo.get("guaranteeCorporationProductGuaranteeRate").toString() );
        guaranteeCorporation.setGuaranteeRate(new BigDecimal(strGuaranteeRate));
        //担保费金额
        String strGuaranteeAmount = (null == receiveAndPaidInfo.get("guaranteeCorporationProductGuaranteeRateAmount") ? "0" : receiveAndPaidInfo.get("guaranteeCorporationProductGuaranteeRateAmount").toString());
        guaranteeCorporation.setGuaranteeAmount(new BigDecimal(strGuaranteeAmount));
        //担保费实付金额
        String strGuaranteePayedAmount = (null == receiveAndPaidInfo.get("guaranteeCorporationProductGuaranteeRateReviceAmount") ? "0" : receiveAndPaidInfo.get("guaranteeCorporationProductGuaranteeRateReviceAmount").toString());
        guaranteeCorporation.setGuaranteePayedAmount(new BigDecimal(strGuaranteePayedAmount));
        //担保费实付时间
        String strGuaranteePayedTime = (String) receiveAndPaidInfo.get("guaranteeCorporationProductGuaranteeRateDate");
        guaranteeCorporation.setGuaranteePayedTime(DateUtil.parseDateTimeForPattern(strGuaranteePayedTime, DateUtil.DATE_TIME_PATTERN3));
        //保证金比例
        String strBailRate = (null ==  receiveAndPaidInfo.get("guaranteeCorporationProductMarginRatio") ? "0" : receiveAndPaidInfo.get("guaranteeCorporationProductMarginRatio").toString());
        guaranteeCorporation.setBailRate(new BigDecimal(strBailRate));
        //保证金金额
        String strBailAmount = (null ==  receiveAndPaidInfo.get("guaranteeCorporationProductMarginRatioAmount") ? "0" : receiveAndPaidInfo.get("guaranteeCorporationProductMarginRatioAmount").toString());
        guaranteeCorporation.setBailAmount(new BigDecimal(strBailAmount));
        //保证金实付金额
        String strBailPayedAmount = (null ==  receiveAndPaidInfo.get("guaranteeCorporationProductMarginReviceAmount") ? "0" : receiveAndPaidInfo.get("guaranteeCorporationProductMarginReviceAmount").toString());
        guaranteeCorporation.setBailPayedAmount(new BigDecimal(strBailPayedAmount));
        //保证金实付时间
        String strBailPayedTime = (String) receiveAndPaidInfo.get("guaranteeCorporationProductMarginDate");
        guaranteeCorporation.setBailPayedTime(DateUtil.parseDateTimeForPattern(strBailPayedTime, DateUtil.DATE_TIME_PATTERN3));

        guaranteeCorporation.setContractCode(contractCode);
        guaranteeCorporation.setStatus(GlobDict.guarantee_corporation_status_valid.getKey());
        guaranteeCorporation.setCreateDate(new Date());
        guaranteeCorporation.setModifyDate(new Date());
        plmsGuaranteeCorporationDao.saveGuaranteeCorporation(guaranteeCorporation);
    }

    /**
     * 保存保险公司信息
     * @param receiveAndPaidInfo 实收实付信息
     * @param insuranceInfo 保险公司信息
     * @param contractCode 合同编号
     */
    private void createInsuranceCompany(Map receiveAndPaidInfo , Map insuranceInfo, String contractCode){
        PlmsInsuranceCompany insuranceCompany= new PlmsInsuranceCompany();
        String code = commonService.getUUID();
        insuranceCompany.setCode(code);

        String companyCode = null;
        PlmsCompany company = new PlmsCompany();
        company.setBmsCode((String) insuranceInfo.get("organizationCode"));
        company.setCompanyType(GlobDict.company_type_insurance.getKey());
        company = plmsCompanyDao.getCompanyByBmsCode(company);
        if(null == company){
            //创建公司信息
            company = new PlmsCompany();
            companyCode = commonService.getUUID();
            company.setCode(companyCode);
            company.setBmsCode((String) insuranceInfo.get("organizationCode"));
            company.setCompanyName((String) insuranceInfo.get("organizationName"));
            company.setAbbrName((String) insuranceInfo.get("organizationAbbr"));
            company.setCompanyType(GlobDict.company_type_insurance.getKey());
            company.setStatus(GlobDict.company_status_valid.getKey());
            company.setCreateDate(new Date());
            company.setModifyDate(new Date());
            plmsCompanyDao.saveCompanyInfo(company);
        }else{
            companyCode = company.getCode();
        }
        insuranceCompany.setCompanyCode(companyCode);
        //保证金比例
        String strBailRate = (null == receiveAndPaidInfo.get("insuranceProductMarginRatio") ? "0" : receiveAndPaidInfo.get("insuranceProductMarginRatio").toString());
        insuranceCompany.setBailRate(new BigDecimal(strBailRate));
        //保证金应付金额
        String strBailAmount = (null == receiveAndPaidInfo.get("insuranceProductMarginAmount") ? "0" : receiveAndPaidInfo.get("insuranceProductMarginAmount").toString());
        insuranceCompany.setBailAmount(new BigDecimal(strBailAmount));
        //保证金实付金额
        String strBailPayedPaidAmount = (null == receiveAndPaidInfo.get("insuranceProductMarginReviceAmount") ? "0" : receiveAndPaidInfo.get("insuranceProductMarginReviceAmount").toString());
        insuranceCompany.setBailPayedPaidAmount(new BigDecimal(strBailPayedPaidAmount));
        //保证金实付完成时间
        String strBailPayedTime = (String) receiveAndPaidInfo.get("insuranceProductMarginReviceDate");
        insuranceCompany.setBailPayedTime(DateUtil.parseDateTimeForPattern(strBailPayedTime, DateUtil.DATE_TIME_PATTERN3));
        insuranceCompany.setBailPayedMethod((String) receiveAndPaidInfo.get("insuranceProductMarginType"));

        //履约保证保险费率
        String strPerformanceBondRate = (null == receiveAndPaidInfo.get("insuranceProductInsurance1Ratio") ? "0" : receiveAndPaidInfo.get("insuranceProductInsurance1Ratio").toString());
        insuranceCompany.setPerformanceBondRate(new BigDecimal(strPerformanceBondRate));
        //履约保证保险费应付金额
        String strPerformanceBondAmount = (null == receiveAndPaidInfo.get("insuranceProductInsurance1Amount") ? "0" : receiveAndPaidInfo.get("insuranceProductInsurance1Amount").toString());
        insuranceCompany.setPerformanceBondAmount(new BigDecimal(strPerformanceBondAmount));
        //履约保证保险费实付金额
        String strPerformanceBondPaidAmount = (null == receiveAndPaidInfo.get("insuranceProductInsurance1ReviceAmount") ? "0" : receiveAndPaidInfo.get("insuranceProductInsurance1ReviceAmount").toString());
        insuranceCompany.setPerformanceBondPaidAmount(new BigDecimal(strPerformanceBondPaidAmount));
        //履约保证保险费实付时间
        String strPerformanceBondPayedTime = (String) receiveAndPaidInfo.get("insuranceProductInsurance1Date");
        insuranceCompany.setPerformanceBondPayedTime(DateUtil.parseDateTimeForPattern(strPerformanceBondPayedTime, DateUtil.DATE_TIME_PATTERN3));
        insuranceCompany.setPerformanceBondPayedMethod((String) receiveAndPaidInfo.get("insuranceProductInsurance1Type"));

        insuranceCompany.setSecondInsuranceName((String) receiveAndPaidInfo.get("insurance2"));
        //险种2费率
        String strSecondInsuranceFeeRate = (null == receiveAndPaidInfo.get("insuranceProductInsurance2Ratio") ? "0" : receiveAndPaidInfo.get("insuranceProductInsurance2Ratio").toString());
        insuranceCompany.setSecondInsuranceFeeRate(new BigDecimal(strSecondInsuranceFeeRate));
        //险种2费用应付金额
        String strSecondInsuranceFeeAmount = (null == receiveAndPaidInfo.get("insuranceProductInsurance2Amount") ? "0" : receiveAndPaidInfo.get("insuranceProductInsurance2Amount").toString());
        insuranceCompany.setSecondInsuranceFeeAmount(new BigDecimal(strSecondInsuranceFeeAmount));
        //险种2费用实付金额
        String strSecondInsurancePaidAmount = (null == receiveAndPaidInfo.get("insuranceProductInsurance2ReviceAmount") ? "0" : receiveAndPaidInfo.get("insuranceProductInsurance2ReviceAmount").toString());
        insuranceCompany.setSecondInsurancePaidAmount(new BigDecimal(strSecondInsurancePaidAmount) );
        //险种2费用实付时间
        String strSecondInsurancePayedTime = (String) receiveAndPaidInfo.get("insuranceProductInsurance2Date");
        insuranceCompany.setSecondInsurancePayedTime(DateUtil.parseDateTimeForPattern(strSecondInsurancePayedTime, DateUtil.DATE_TIME_PATTERN3));
        insuranceCompany.setSecondInsurancePayedMethod((String) receiveAndPaidInfo.get("insuranceProductInsurance2Type"));

        insuranceCompany.setContractCode(contractCode);
        insuranceCompany.setStatus(GlobDict.insurance_company_status_valid.getKey());
        insuranceCompany.setCreateDate(new Date());
        insuranceCompany.setModifyDate(new Date());
        plmsInsuranceCompanyDao.saveInsuranceCompanyInfo(insuranceCompany);
    }

    /**
     * 保存客户费用信息
     * @param receiveAndPaidInfo 实收实付信息
     * @param contractCode 合同编号
     */
    private void  createCustomerFeeInfo(Map receiveAndPaidInfo, String contractCode){
        PlmsCustomerFeeInfo  customerFeeInfo = new PlmsCustomerFeeInfo();
        String code = commonService.getUUID();
        customerFeeInfo.setCode(code);

        //手续费比例
        String strServiceChargeRate = (null == receiveAndPaidInfo.get("serviceRatio") ? "0" : receiveAndPaidInfo.get("serviceRatio").toString());
        customerFeeInfo.setServiceChargeRate(new BigDecimal(strServiceChargeRate));
        //手续费应收金额
        String strServiceChargeAmount = (null == receiveAndPaidInfo.get("serviceAmount") ? "0" : receiveAndPaidInfo.get("serviceAmount").toString());
        customerFeeInfo.setServiceChargeAmount(new BigDecimal(strServiceChargeAmount));
        //手续费实收金额
        String strServiceChargeReceiveAmount = (null == receiveAndPaidInfo.get("serviceReviceAmout") ? "0" : receiveAndPaidInfo.get("serviceReviceAmout").toString());
        customerFeeInfo.setServiceChargeReceiveAmount(new BigDecimal(strServiceChargeReceiveAmount));
        //手续费实收完成时间
        String strServiceChargeReceiveTime = (String) receiveAndPaidInfo.get("serviceReviceDate");
        customerFeeInfo.setServiceChargeReceiveTime(DateUtil.parseDateTimeForPattern(strServiceChargeReceiveTime, DateUtil.DATE_TIME_PATTERN3));

        //担保费率
        String strGuaranteeFeeRate = (null == receiveAndPaidInfo.get("guaranteeFeeRatio") ? "0" : receiveAndPaidInfo.get("guaranteeFeeRatio").toString());
        customerFeeInfo.setGuaranteeFeeRate(new BigDecimal(strGuaranteeFeeRate));
        //担保费应收金额
        String strGuaranteeFeeAmount = (null == receiveAndPaidInfo.get("guaranteeFee") ? "0" : receiveAndPaidInfo.get("guaranteeFee").toString());
        customerFeeInfo.setGuaranteeFeeAmount(new BigDecimal(strGuaranteeFeeAmount));
        //担保费实收金额
        String strGuaranteeFeeReceiveAmount = (null == receiveAndPaidInfo.get("guaranteeFeeRevice") ? "0" : receiveAndPaidInfo.get("guaranteeFeeRevice").toString());
        customerFeeInfo.setGuaranteeFeeReceiveAmount(new BigDecimal(strGuaranteeFeeReceiveAmount));
        //担保费实收时间
        String strGuaranteeFeeReceiveTime = (String) receiveAndPaidInfo.get("guaranteeFeeDate");
        customerFeeInfo.setGuaranteeFeeReceiveTime(DateUtil.parseDateTimeForPattern(strGuaranteeFeeReceiveTime, DateUtil.DATE_TIME_PATTERN3));

        //保证金比例
        String strBailRate = (String) receiveAndPaidInfo.get("marginRatio");
        customerFeeInfo.setBailRate(new BigDecimal(strBailRate));
        //保证金应收金额
        String strBailAmount = (String) receiveAndPaidInfo.get("marginFee");
        customerFeeInfo.setBailAmount(new BigDecimal(strBailAmount));
        //保证金实收金额
        String strBailReceiveAmount = (null == receiveAndPaidInfo.get("marginFeeReviceAmout") ? "0" : receiveAndPaidInfo.get("marginFeeReviceAmout").toString());
        customerFeeInfo.setBailReceiveAmount(new BigDecimal(strBailReceiveAmount));
        //保证金实收时间
        String strBailReceiveTime = (String) receiveAndPaidInfo.get("guaranteeFeeDate");
        customerFeeInfo.setBailReceiveTime(DateUtil.parseDateTimeForPattern(strBailReceiveTime, DateUtil.DATE_TIME_PATTERN3));

        //履约保证保险费率
        String strPerformanceBondRate = (String) receiveAndPaidInfo.get("fontPropertyInsuranceRate");
        customerFeeInfo.setPerformanceBondRate(new BigDecimal(strPerformanceBondRate));
        //履约保证保险费应收金额
        String strPerformanceBondAmount = (String) receiveAndPaidInfo.get("fontPropertyInsuranceFee");
        customerFeeInfo.setPerformanceBondAmount(new BigDecimal(strPerformanceBondAmount));
        //履约保证保险费实收金额
        String strPerformanceBondReceiveAmount = (null == receiveAndPaidInfo.get("fontPropertyInsuranceReciveAmount") ? "0" : receiveAndPaidInfo.get("fontPropertyInsuranceReciveAmount").toString());
        customerFeeInfo.setPerformanceBondReceiveAmount(new BigDecimal(strPerformanceBondReceiveAmount));
        //履约保证金实收时间
        String strPerformanceBondReceiveTime = (String) receiveAndPaidInfo.get("fontPropertyInsuranceDate");
        customerFeeInfo.setPerformanceBondReceiveTime(DateUtil.parseDateTimeForPattern(strPerformanceBondReceiveTime, DateUtil.DATE_TIME_PATTERN3));

        customerFeeInfo.setContractCode(contractCode);
        customerFeeInfo.setStatus(GlobDict.customer_fee_info_status_valid.getKey());
        customerFeeInfo.setCreateDate(new Date());
        customerFeeInfo.setModifyDate(new Date());
        plmsCustomerFeeInfoDao.saveCustomerFeeInfo(customerFeeInfo);
    }

    /**
     * 保存签约公正信息
     * @param operationsRegisterInfo 运营登记信息
     * @param contractCode 合同code
     */
    private void createSignNotarial(Map operationsRegisterInfo, String contractCode){
        //签约信息
        Map signing = (Map) operationsRegisterInfo.get("signing");

        PlmsSignNotarial signNotarial = new PlmsSignNotarial ();
        String code = commonService.getUUID();
        signNotarial.setCode(code);
        signNotarial.setSignResult((String) signing.get("signingResult"));
        signNotarial.setRemark((String) signing.get("signingRemark"));
        signNotarial.setSignPlace((String) signing.get("signingPlace"));
        //签约公证时间
        String strBailReceiveTime = (String) signing.get("signingApproveTime");
        signNotarial.setSignTime(DateUtil.parseDateTimeForPattern(strBailReceiveTime, DateUtil.DATE_TIME_PATTERN3));

        signNotarial.setContractCode(contractCode);
        signNotarial.setStatus(GlobDict.sign_notarial_status_valid.getKey());
        signNotarial.setCreateDate(new Date());
        signNotarial.setModifyDate(new Date());
        plmsSignNotarialDao.saveSignNotarialInfo(signNotarial);
    }
    /**
     * 保存抵押登记信息
     * @param operationsRegisterInfo 运营登记信息
     * @param contractCode 合同code
     */
    private void createMortgage(Map operationsRegisterInfo, String contractCode){
        //抵押登记信息
        Map notarization = (Map) operationsRegisterInfo.get("notarization");

        PlmsMortgage mortgage = new PlmsMortgage();
        String code = commonService.getUUID();
        mortgage.setCode(code);

        mortgage.setRegistrationResult((String) notarization.get("notarizationResult"));
        mortgage.setRemark((String) notarization.get("notarizationRemark"));
        mortgage.setRegistrationPlace((String) notarization.get("notarizationPlace"));
        //抵押登记时间
        String strRegistrationTime = (String) notarization.get("notarizationApproveTime");
        mortgage.setRegistrationTime(DateUtil.parseDateTimeForPattern(strRegistrationTime, DateUtil.DATE_TIME_PATTERN3));

        mortgage.setContractCode(contractCode);
        mortgage.setStatus(GlobDict.mortgage_status_valid.getKey());
        mortgage.setCreateDate(new Date());
        mortgage.setModifyDate(new Date());
        plmsMortgageDao.saveMortgageInfo(mortgage);
    }
    /**
     * 保存产调查询
     * @param operationsRegisterInfo 运营登记信息
     * @param contractCode 合同code
     */
    private void createInvestQuery(Map operationsRegisterInfo, String contractCode){
        List propertyInquireList = (List<Map>)operationsRegisterInfo.get("propertyInquireList");
        if (propertyInquireList != null) {
            for (int i = 0; i < propertyInquireList.size();i++) {
                Map propertyInquire = (Map) propertyInquireList.get(i);
                PlmsInvestQuery investQuery = new PlmsInvestQuery();
                String code = commonService.getUUID();
                investQuery.setCode(code);
                investQuery.setInvestQueryResult((String) propertyInquire.get("propertyInquireRemark"));
                investQuery.setRemark((String) propertyInquire.get("propertyInquireRemark"));
                String strInvestQueryTime = (String) propertyInquire.get("selectTime");
                investQuery.setInvestQueryTime(DateUtil.parseDateTimeForPattern(strInvestQueryTime, DateUtil.DATE_TIME_PATTERN3));

                investQuery.setContractCode(contractCode);
                investQuery.setStatus(GlobDict.invest_query_status_valid.getKey());
                investQuery.setCreateTime(new Date());
                investQuery.setModifyTime(new Date());
                plmsInvestQueryDao.saveInvestQuery(investQuery);
            }
        }
    }

    private void createPigeonholeInfo(Map operationsMaterialInfo, String contractCode){
        PlmsPigeonholeInfo pigeonholeInfo = new PlmsPigeonholeInfo();
        String code = commonService.getUUID();
        pigeonholeInfo.setCode(code);

        pigeonholeInfo.setFileCode((String) operationsMaterialInfo.get("fileId"));
        pigeonholeInfo.setFileName((String) operationsMaterialInfo.get("fileName"));

        //归档文件类别
        String type = (String) operationsMaterialInfo.get("fileType");
        if(BmsGlobDict.pigeonhole_info_type_notarization.getKey().equals(type)){
            pigeonholeInfo.setType(GlobDict.pigeonhole_info_type_notarization.getKey());
        }else if(BmsGlobDict.pigeonhole_info_type_guarantee.getKey().equals(type)){
            pigeonholeInfo.setType(GlobDict.pigeonhole_info_type_guarantee.getKey());
        }else if(BmsGlobDict.pigeonhole_info_type_insurance.getKey().equals(type)) {
            pigeonholeInfo.setType(GlobDict.pigeonhole_info_type_insurance.getKey());
        }else if(BmsGlobDict.pigeonhole_info_type_mortgage.getKey().equals(type)) {
            pigeonholeInfo.setType(GlobDict.pigeonhole_info_type_mortgage.getKey());
        }else if(BmsGlobDict.pigeonhole_info_type_invest_query.getKey().equals(type)) {
            pigeonholeInfo.setType(GlobDict.pigeonhole_info_type_invest_query.getKey());
        }else if(BmsGlobDict.pigeonhole_info_type_loan_voucher.getKey().equals(type)) {
            pigeonholeInfo.setType(GlobDict.pigeonhole_info_type_loan_voucher.getKey());
        }else{
            pigeonholeInfo.setType(type);
        }
        //去文件系统获取上载时间
        List fileList = new ArrayList();
        Map fileMap = new HashMap();
        fileMap.put("serialNo",(String) operationsMaterialInfo.get("fileId"));
        fileList.add(fileMap);
        Map map = new HashMap();
        map.put("serialNoList",fileList);
        Map result = null;
        try {
            result = remoteFmsService.getUrlList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取返回结果
        Map body = (Map)result.get(Fields.PARAM_MESSAGE_BODY);
        List fileMaps = (List)body.get("fileMaps");
        if (fileMaps!=null && fileMaps.size()>0) {
            fileMap = (Map) fileMaps.get(0);
            String uploadTime = (String) fileMap.get("uploadTime");
            String fileSuffix = (String) fileMap.get("fileSuffix");
            pigeonholeInfo.setUploadTime(DateUtil.parseDateTimeForPattern(uploadTime, DateUtil.DATE_TIME_PATTERN3));

            pigeonholeInfo.setContractCode(contractCode);
            pigeonholeInfo.setFileSuffix(fileSuffix);
            pigeonholeInfo.setStatus(GlobDict.pigeonhole_info_status_valid.getKey());
            pigeonholeInfo.setCreateDate(new Date());
            pigeonholeInfo.setModifyDate(new Date());
            plmsPigeonholeInfoDao.savePigeonholeInfo(pigeonholeInfo);
        }
    }
}
