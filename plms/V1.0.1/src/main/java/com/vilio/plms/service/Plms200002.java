package com.vilio.plms.service;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.service.base.BaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/8/10.
 */
@Service
public class Plms200002 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms200002.class);

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

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //请求信息
        Map mainInfo = (HashMap)body.get("mainInfo");
        //进件信息
        Map applyInfo = (HashMap)body.get("applyInfo");
        //渠道信息
        Map distributeInfo = (HashMap)body.get("distrbuteInfo");
        //运营信息
        Map operationsInfo = (HashMap)body.get("operationsInfo");
        //运营登记信息
        Map operationsRegister = (HashMap)operationsInfo.get("operationsRegisterInfo");
        //借款人信息
        Map loanPeopleList = (HashMap)applyInfo.get("loanPeopleList");
        //备用房信息
        Map loanSepraroomlist = (HashMap)applyInfo.get("loanSepraroomlist");
        //征信信息
        List loanCreditInfoList = (ArrayList)applyInfo.get("loanCreditInfoList");
        //司法信息
        Map loanJsuticeInfoList = (HashMap)applyInfo.get("loanJsuticeInfoList");
        //案件评估信息
        Map loanCaseUse = (HashMap)applyInfo.get("loanCaseUse");
        //附件材料信息
        Map loanAttachList = (HashMap)applyInfo.get("loanAttachList");
        //风控审批信息
        Map riskControlInfo = (HashMap)applyInfo.get("riskControlInfo");
        //风险控制信息
        Map loanRiskInfo = (HashMap)riskControlInfo.get("loanRiskInfo");
        //审批通知单信息
        Map approvalNoticeInfo = (HashMap)riskControlInfo.get("approvalNoticeInfo");

        //业务员信息
        String agentCode = "";


        //同步银行账户信息
        String bmsAccountName = (String)operationsRegister.get("paidAccountName");
        String bmsAccountNo = (String)operationsRegister.get("paidAccountNo");
        String bmsAccountBank = (String)operationsRegister.get("paidAccountBank");

        Map plmsAccountInfo = new HashMap();
        String accountCode = getUUID();
        plmsAccountInfo.put("code",accountCode);
        plmsAccountInfo.put("accountName",bmsAccountName);
        plmsAccountInfo.put("accountNo",bmsAccountNo);
        plmsAccountInfo.put("bank",bmsAccountBank);
        plmsAccountInfo.put("status",GlobDict.valid.getKey());

        accountInfoDao.insert(plmsAccountInfo);

        //同步申请信息
        String applyDate = (String)body.get("applyDate");
        String applyAmount = (String)body.get("applyAmount");
        String applyPeriod = (String)body.get("applyPeriod");
        String paidMethod = (String)body.get("paidMethod");
        String intentionAmount = (String)body.get("intentionAmount");
        String mobilephoneValidateNo = (String)body.get("mobilephoneValidateNo");
        String remark = (String)body.get("remark");
        String applyAccountCode = accountCode;
        String applyAgentCode = agentCode;
        String distributeCode = (String)distributeInfo.get("code");
        String bmsCode = (String)body.get("code");

        Map plmsApplyInfo = new HashMap();
        String applyCode = getUUID();
        plmsApplyInfo.put("code",applyCode);
        plmsApplyInfo.put("applyDate",applyDate);
        BigDecimal applyAmountDecimal = new BigDecimal(applyAmount);
        applyAmountDecimal = applyAmountDecimal.multiply(new BigDecimal(10000));
        plmsApplyInfo.put("applyAmount",applyAmountDecimal);
        plmsApplyInfo.put("applyPeriod",applyPeriod);
        plmsApplyInfo.put("lendingMethods",paidMethod);
        plmsApplyInfo.put("intentionAmount",intentionAmount);
        plmsApplyInfo.put("identifyingCode",mobilephoneValidateNo);
        plmsApplyInfo.put("remark",remark);
        plmsApplyInfo.put("accountCode",applyAccountCode);
        plmsApplyInfo.put("agentCode",applyAgentCode);
        plmsApplyInfo.put("distributeCode",distributeCode);
        plmsApplyInfo.put("bmsCode",bmsCode);
        plmsApplyInfo.put("status",GlobDict.valid.getKey());

        applyInfoDao.insert(plmsApplyInfo);


        //同步借款人信息
        if (loanPeopleList != null && loanPeopleList.size() >0) {
            for (int i = 0; i < loanPeopleList.size(); i++) {
                Map loanPeople = (HashMap) loanPeopleList.get(i);
                String name = (String) loanPeople.get("name");
                String usedName = (String) loanPeople.get("usedName");
                String certificateType = (String) loanPeople.get("certificateType");
                String plmsIdType = "";
                if (certificateType != null && GlobDict.id_type_id_card.getDesc().equals(certificateType)) {
                    plmsIdType = GlobDict.id_type_id_card.getKey();
                }
                String certificateNumber = (String) loanPeople.get("certificateNumber");
                String certificateValidityStart = (String) loanPeople.get("certificateValidityStart");
                String certificateValidityEnd = (String) loanPeople.get("certificateValidityEnd");
                String age = (String) loanPeople.get("age");
                String cellphone = (String) loanPeople.get("cellphone");
                String workUnit = (String) loanPeople.get("workUnit");
                String annualIncome = (String) loanPeople.get("annualIncome");
                String post = (String) distributeInfo.get("post");
                String familyAddress = (String) loanPeople.get("familyAddress");
                String MaritalStatus = (String) distributeInfo.get("MaritalStatus");
                String marriageHistory = (String) loanPeople.get("marriageHistory");
                String Loaners = (String) distributeInfo.get("Loaners");
                String customerApplyCode = applyCode;

                Map plmsCustomerInfo = new HashMap();
                String customerInfoCode = getUUID();
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
                BigDecimal annualIncomeDecimal = new BigDecimal(annualIncome);
                annualIncomeDecimal = annualIncomeDecimal.multiply(new BigDecimal(10000));
                plmsCustomerInfo.put("annualIncome", annualIncomeDecimal);
                plmsCustomerInfo.put("titile", post);
                plmsCustomerInfo.put("address", familyAddress);
                plmsCustomerInfo.put("marriage", MaritalStatus);
                plmsCustomerInfo.put("marriageHistory", marriageHistory);
                plmsCustomerInfo.put("isMain", Loaners);
                plmsCustomerInfo.put("status", GlobDict.valid.getKey());
                plmsCustomerInfo.put("applyCode", customerApplyCode);

                customerDao.insert(plmsCustomerInfo);

                //更新用户信息表
                List<HashMap> userInfoList = (List<HashMap>) userInfoDao.queryUserInfoByNameAndIdNo(name, plmsIdType, certificateNumber);

                if (userInfoList == null) {
                    HashMap userInfo = (HashMap) userInfoList.get(0);
                    String code = getUUID();
                    userInfo.put("code", code);
                    userInfo.put("name", name);
                    userInfo.put("idType", plmsIdType);
                    userInfo.put("idNo", certificateNumber);
                    userInfo.put("status", GlobDict.valid.getKey());
                    //userInfo.put("umId",umId);
                    userInfoDao.insert(userInfo);
                    //更新申请信息的用户编码
                    plmsApplyInfo.put("applyUserCode", code);
                    applyInfoDao.update(plmsApplyInfo);
                }
            }
        }

        //同步产品信息
        String productName = (String)body.get("productName");
        String circle = (String)body.get("circle");
        String interestCycle = (String)body.get("interestCycle");
        String yearPeriod = (String)body.get("yearPeriod");
        String repaymentMethod = GlobDict.first_interest.getKey();
        String loanMinimumUnit = (String)body.get("loanMinimumUnit");
        String loanMinimumAmount = (String)body.get("loanMinimumAmount");
        String repaymentMinUnit = (String)body.get("repaymentMinUnit");
        String repaymentMinimumAmount = (String)distributeInfo.get("repaymentMinimumAmount");
        String borrowClosedPeriod = (String)body.get("borrowClosedPeriod");
        String repaymentClosedPeriod = (String)body.get("repaymentClosedPeriod");
        String isPenalty = (String)body.get("isPenalty");
        String penaltyPeriod = (String)body.get("penaltyPeriod");
        String isFirstMax = (String)body.get("isFirstMax");
        String depositPeriod = (String)body.get("depositPeriod");
        String interestCollectionDate = (String)body.get("interestCollectionDate");
        String interestCollectionMethod = (String)body.get("interestCollectionMethod");
        String graceDays = (String)body.get("graceDays");
        String principalDate = (String)body.get("principalDate");
        String principalRepaymentMethod = (String)body.get("principalRepaymentMethod");
        String isPeriodSelect = (String)body.get("isPeriodSelect");
        String overdueMethod= (String)body.get("overdueFormula");
        String serviceFeeMethod = (String)body.get("serviceFeeMethod");
        String isSpreadOneTime = (String)body.get("spreadsMethod");
        String maxLoanNum = (String)body.get("maxLoanNum");


        Map plmsProductInfo = new HashMap();
        String productCode = getUUID();
        plmsProductInfo.put("code",productCode);
        plmsProductInfo.put("productName",productName);
        plmsProductInfo.put("circle",circle);
        plmsProductInfo.put("interestCycle",interestCycle);
        plmsProductInfo.put("yearPeriod",yearPeriod);
        plmsProductInfo.put("repaymentMethods",repaymentMethod);
        plmsProductInfo.put("loanMinimumUnit",loanMinimumUnit);
        plmsProductInfo.put("loanMinimumAmount",loanMinimumAmount);
        plmsProductInfo.put("repaymentMinimumUnit",repaymentMinUnit);
        plmsProductInfo.put("repaymentMinimumAmount",repaymentMinimumAmount);
        plmsProductInfo.put("borrowClosedPeriod",borrowClosedPeriod);
        plmsProductInfo.put("repaymentClosedPeriod",repaymentClosedPeriod);
        plmsProductInfo.put("isPenalty",isPenalty);
        plmsProductInfo.put("penaltyPeriod",penaltyPeriod);
        plmsProductInfo.put("isFirstMax",isFirstMax);
        plmsProductInfo.put("interestRepaymentDay",interestCollectionDate);
        plmsProductInfo.put("mortgageInterestPeriod",depositPeriod);
        plmsProductInfo.put("interestCollectionMethod",interestCollectionMethod);
        plmsProductInfo.put("graceDays",graceDays);
        plmsProductInfo.put("principalDate",principalDate);
        plmsProductInfo.put("principalRepaymentMethod",principalRepaymentMethod);
        plmsProductInfo.put("isPeriodSelect",isPeriodSelect);
        plmsProductInfo.put("overdueMethod",overdueMethod);
        plmsProductInfo.put("serviceFeeMethod",serviceFeeMethod);
        plmsProductInfo.put("isSpreadOneTime",isSpreadOneTime);
        plmsProductInfo.put("maxLoanNum",maxLoanNum);
        plmsProductInfo.put("status",GlobDict.valid.getKey());
        plmsProductInfo.put("applyCode",applyCode);

        productDao.insert(plmsProductInfo);



        //同步备用房信息
        if (loanSepraroomlist!=null && loanSepraroomlist.size() > 0) {
            for (int i = 0;i < loanSepraroomlist.size(); i++) {
                Map loanSepraroom = (HashMap)loanSepraroomlist.get(i);
                String propertyOwner = (String) loanSepraroom.get("propertyOwner");
                String propertyAddress = (String) loanSepraroom.get("propertyAddress");

                Map plmsSpareInfo = new HashMap();
                String spareInfoCode = getUUID();
                plmsSpareInfo.put("propertyPerson", propertyOwner);
                plmsSpareInfo.put("propertyAddress", propertyAddress);
                plmsSpareInfo.put("applyCode", applyCode);
                plmsSpareInfo.put("status", GlobDict.valid.getKey());

                spareHouseInfoDao.insert(plmsSpareInfo);
            }
        }

        //同步征信信息
        if (loanCreditInfoList != null && loanCreditInfoList.size()>0) {
            for (int s = 0;s<loanCreditInfoList.size();s++) {
                Map loanCreditInfo = (HashMap)loanCreditInfoList.get(s);
                String name = (String) loanCreditInfo.get("name");
                String creditReportDate = (String) loanCreditInfo.get("creditReportDate");
                String creditRemark = (String) loanCreditInfo.get("remark");

                Map plmsCreditInfo = new HashMap();
                String creditInfoCode = getUUID();
                plmsCreditInfo.put("code", creditInfoCode);
                plmsCreditInfo.put("name", name);
                plmsCreditInfo.put("reportTime", creditReportDate);
                plmsCreditInfo.put("remark", creditRemark);
                plmsCreditInfo.put("status",GlobDict.valid.getKey());
                plmsCreditInfo.put("applyCode",applyCode);

                creditInfoDao.insert(plmsCreditInfo);

                //同步未结清贷款信息
                String outstandLoanCount = (String) loanCreditInfo.get("outstandLoanCount");
                String outstandLoanSum = (String) loanCreditInfo.get("outstandLoanSum");
                String outstandLoanBalance = (String) loanCreditInfo.get("outstandLoanBalance");
                String outstandLoanMonthAvgRepay = (String) loanCreditInfo.get("outstandLoanMonthAvgRepay");

                Map plmsLoanUnSettle = new HashMap();
                String loanUnSettleCode = getUUID();
                plmsLoanUnSettle.put("code", loanUnSettleCode);
                plmsLoanUnSettle.put("outstandLoanCount", outstandLoanCount);
                BigDecimal outstandLoanSumDecimal = new BigDecimal(outstandLoanSum);
                outstandLoanSumDecimal = outstandLoanSumDecimal.multiply(new BigDecimal(10000));
                plmsLoanUnSettle.put("outstandLoanSum", outstandLoanSumDecimal);
                BigDecimal balanceDecimal = new BigDecimal(outstandLoanBalance);
                balanceDecimal = balanceDecimal.multiply(new BigDecimal(10000));
                plmsLoanUnSettle.put("balance", balanceDecimal);
                BigDecimal outstandLoanMonthAvgRepayDecimal = new BigDecimal(outstandLoanMonthAvgRepay);
                outstandLoanMonthAvgRepayDecimal = outstandLoanMonthAvgRepayDecimal.multiply(new BigDecimal(10000));
                plmsLoanUnSettle.put("sixMonthRepayment", outstandLoanMonthAvgRepayDecimal);
                plmsLoanUnSettle.put("creditCode", creditInfoCode);
                plmsLoanUnSettle.put("status",GlobDict.valid.getKey());

                creditInfoDao.insertLoanUnSettle(plmsCreditInfo);

                //同步未销户贷记卡信息
                //账户数
                String usableCreditCardCount = (String) loanCreditInfo.get("usableCreditCardCount");
                //授信总额
                String usableCardSum = (String) loanCreditInfo.get("usableCardSum");
                //最近6个月平均使用额度
                String usableCardMonthQuotaUsed = (String) loanCreditInfo.get("usableCardMonthQuotaUsed");
                //已用额度
                String usableCardQuotaUsed = (String) loanCreditInfo.get("usableCardQuotaUsed");

                Map plmsUnclosingCard = new HashMap();
                String unclosingCardCode = getUUID();
                plmsUnclosingCard.put("code", unclosingCardCode);
                plmsUnclosingCard.put("usableCreditCardCount", usableCreditCardCount);

                BigDecimal usableCardSumDecimal = new BigDecimal(usableCardSum);
                usableCardSumDecimal = usableCardSumDecimal.multiply(new BigDecimal(10000));
                plmsUnclosingCard.put("usableCardSum", usableCardSumDecimal);

                BigDecimal usableCardQuotaUsedDecimal = new BigDecimal(usableCardQuotaUsed);
                usableCardQuotaUsedDecimal = usableCardQuotaUsedDecimal.multiply(new BigDecimal(10000));
                plmsUnclosingCard.put("usableCardQuotaUsed", usableCardQuotaUsedDecimal);

                BigDecimal usableCardMonthQuotaUsedDecimal = new BigDecimal(usableCardMonthQuotaUsed);
                usableCardMonthQuotaUsedDecimal = usableCardMonthQuotaUsedDecimal.multiply(new BigDecimal(10000));
                plmsUnclosingCard.put("usableCardMonthQuotaUsed", usableCardMonthQuotaUsedDecimal);

                plmsUnclosingCard.put("creditCode", creditInfoCode);
                plmsUnclosingCard.put("status",GlobDict.valid.getKey());

                creditInfoDao.insertUnclosingCard(plmsUnclosingCard);

                //同步查询记录表
                //近三个月查询次数
                String findThreemonthCount = (String) loanCreditInfo.get("findThreemonthCount");
                //贷后管理次数
                String loanAfterCount = (String) loanCreditInfo.get("loanAfterCount");
                //担保资格审查次数
                String guaranteeSeniorityCount = (String) loanCreditInfo.get("guaranteeSeniorityCount");

                Map plmsQueryRecord = new HashMap();
                String queryRecordCode = getUUID();
                plmsQueryRecord.put("code", queryRecordCode);
                plmsQueryRecord.put("findThreemonthCount", findThreemonthCount);
                plmsQueryRecord.put("loanAfterCount", loanAfterCount);
                plmsQueryRecord.put("guaranteeSeniorityCount", guaranteeSeniorityCount);
                plmsQueryRecord.put("status", GlobDict.valid.getKey());

                plmsUnclosingCard.put("creditInfoCode", creditInfoCode);

                creditInfoDao.insertQueryRecord(plmsQueryRecord);

                //针对逾期信息同步已贷款信息表
                List loanInfoList = (ArrayList) loanCreditInfo.get("loanInfoList");
                if (loanInfoList != null && loanInfoList.size() > 0) {
                    for (int c = 0; c < loanInfoList.size(); c++) {
                        Map loanInfo = (HashMap) loanInfoList.get(c);
                        //针对逾期信息同步已贷款信息表
                        List currentOverdueLoanList = (ArrayList) loanInfo.get("currentOverdueLoan");
                        if (currentOverdueLoanList != null && currentOverdueLoanList.size() > 0) {
                            for (int j = 0; j < currentOverdueLoanList.size(); j++) {
                                Map currentOverdueLoan = (HashMap) currentOverdueLoanList.get(j);
                                //贷款序号
                                String serialNo = (String) currentOverdueLoan.get("serialNo");
                                //逾期金额
                                String overdueAmount = (String) currentOverdueLoan.get("overdueAmount");

                                Map plmsLoanInfo = new HashMap();
                                String loanInfoCode = getUUID();
                                plmsLoanInfo.put("code", loanInfoCode);
                                plmsLoanInfo.put("loanNo", serialNo);
                                plmsLoanInfo.put("overdueAmount", overdueAmount);
                                plmsLoanInfo.put("creditCode", creditInfoCode);
                                plmsLoanInfo.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertLoanInfo(plmsLoanInfo);
                            }
                        }

                        //针对非正常贷款信息同步已贷款信息表
                        List aberrantLoanList = (ArrayList) loanInfo.get("aberrantLoan");
                        if (aberrantLoanList != null && aberrantLoanList.size() > 0) {
                            for (int j = 0; j < aberrantLoanList.size(); j++) {
                                Map aberrantLoan = (HashMap) aberrantLoanList.get(j);
                                //贷款序号
                                String serialNo = (String) aberrantLoan.get("serialNo");
                                //逾期金额
                                String status = (String) aberrantLoan.get("status");

                                Map plmsLoanInfo = new HashMap();
                                String aberrantLoanCode = getUUID();
                                plmsLoanInfo.put("code", aberrantLoanCode);
                                plmsLoanInfo.put("loanNo", serialNo);
                                plmsLoanInfo.put("loanStatus", status);
                                plmsLoanInfo.put("creditCode", creditInfoCode);
                                plmsLoanInfo.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertLoanInfo(plmsLoanInfo);
                            }
                        }
                        //针对近12个月累计最高逾期次数信息同步已贷款信息表
                        List topOverdueCountLoanList = (ArrayList) loanInfo.get("topOverdueCountLoan");
                        if (topOverdueCountLoanList != null && topOverdueCountLoanList.size() > 0) {
                            for (int j = 0; j < topOverdueCountLoanList.size(); j++) {
                                Map topOverdueCountLoan = (HashMap) topOverdueCountLoanList.get(j);
                                //贷款序号
                                String serialNo = (String) topOverdueCountLoan.get("serialNo");
                                //逾期状态
                                String overdueCount = (String) topOverdueCountLoan.get("overdueCount");

                                Map plmsLoanInfo = new HashMap();
                                String aberrantLoanCode = getUUID();
                                plmsLoanInfo.put("code", aberrantLoanCode);
                                plmsLoanInfo.put("loanNo", serialNo);
                                plmsLoanInfo.put("overdueNum", overdueCount);
                                plmsLoanInfo.put("creditCode", creditInfoCode);
                                plmsLoanInfo.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertLoanInfo(plmsLoanInfo);
                            }
                        }

                        //针对近12个月最高连续逾期期数信息同步已贷款信息表
                        List topContinuousOverdueLoanList = (ArrayList) loanInfo.get("topContinuousOverdueLoan");
                        if (topContinuousOverdueLoanList != null && topContinuousOverdueLoanList.size() > 0) {
                            for (int j = 0; j < topContinuousOverdueLoanList.size(); j++) {
                                Map topContinuousOverdueLoan = (HashMap) topContinuousOverdueLoanList.get(j);
                                //贷款序号
                                String serialNo = (String) topContinuousOverdueLoan.get("serialNo");
                                //逾期状态
                                String continuityOverduePeriods = (String) topContinuousOverdueLoan.get("continuityOverduePeriods");

                                Map plmsLoanInfo = new HashMap();
                                String topContinuousOverdueLoanCode = getUUID();
                                plmsLoanInfo.put("code", topContinuousOverdueLoanCode);
                                plmsLoanInfo.put("loanNo", serialNo);
                                plmsLoanInfo.put("period", continuityOverduePeriods);
                                plmsLoanInfo.put("creditCode", creditInfoCode);
                                plmsLoanInfo.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertLoanInfo(plmsLoanInfo);
                            }
                        }

                }

                List creditCardInfos = (ArrayList) loanCreditInfo.get("creditCardInfos");
                if (creditCardInfos != null && creditCardInfos.size() > 0) {
                    for (int i = 0;i<creditCardInfos.size();i++){
                        Map creditCardInfo = (HashMap)creditCardInfos.get(i);
                        //针对贷记卡信息列表下的当前逾期信息同步已贷款信息表
                        List cardCurrentOverdueList = (ArrayList) creditCardInfo.get("cardCurrentOverdue");
                        if (cardCurrentOverdueList != null && cardCurrentOverdueList.size() > 0) {
                            for (int j = 0; j < cardCurrentOverdueList.size(); j++) {
                                Map cardCurrentOverdue = (HashMap) cardCurrentOverdueList.get(j);
                                //贷款序号
                                String serialNo = (String) cardCurrentOverdue.get("serialNo");
                                //逾期状态
                                String overdueAmount = (String) cardCurrentOverdue.get("overdueAmount");

                                Map plmsCreaditCard = new HashMap();
                                String creaditCardCode = getUUID();
                                plmsCreaditCard.put("code", creaditCardCode);
                                plmsCreaditCard.put("cardNo", serialNo);
                                plmsCreaditCard.put("overdueAmount", overdueAmount);
                                plmsCreaditCard.put("creditCode", creditInfoCode);
                                plmsCreaditCard.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertCreaditCard(plmsCreaditCard);
                            }
                        }

                        //针对贷记卡信息列表下的非正常类贷记卡信息同步已贷款信息表
                        List cardAbnormalList = (ArrayList) creditCardInfo.get("cardAbnormal");
                        if (cardAbnormalList != null && cardAbnormalList.size() > 0) {
                            for (int j = 0; j < cardAbnormalList.size(); j++) {
                                Map cardAbnormal = (HashMap) cardAbnormalList.get(j);
                                //贷记卡序号
                                String serialNo = (String) cardAbnormal.get("serialNo");
                                //贷款状态
                                String loanStatus = (String) cardAbnormal.get("loanStatus");

                                Map plmsCreaditCard = new HashMap();
                                String cardAbnormalCode = getUUID();
                                plmsCreaditCard.put("code", cardAbnormalCode);
                                plmsCreaditCard.put("cardNo", serialNo);
                                plmsCreaditCard.put("loanStatus", loanStatus);
                                plmsCreaditCard.put("creditCode", creditInfoCode);
                                plmsCreaditCard.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertCreaditCard(plmsCreaditCard);
                            }
                        }
                        //针对贷记卡信息列表下的近12个月累计最高逾期次数
                        List cardTotalOverdueCountList = (ArrayList) creditCardInfo.get("cardTotalOverdueCount");
                        if (cardTotalOverdueCountList != null && cardTotalOverdueCountList.size() > 0) {
                            for (int j = 0; j < cardTotalOverdueCountList.size(); j++) {
                                Map cardTotalOverdueCount = (HashMap) cardTotalOverdueCountList.get(j);
                                //贷记卡序号
                                String serialNo = (String) cardTotalOverdueCount.get("serialNo");
                                //贷款状态
                                String overdueCount = (String) cardTotalOverdueCount.get("overdueCount");

                                Map plmsCreaditCard = new HashMap();
                                String cardTotalOverdueCode = getUUID();
                                plmsCreaditCard.put("code", cardTotalOverdueCode);
                                plmsCreaditCard.put("cardNo", serialNo);
                                plmsCreaditCard.put("overdueNo", overdueCount);
                                plmsCreaditCard.put("creditCode", creditInfoCode);
                                plmsCreaditCard.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertCreaditCard(plmsCreaditCard);
                            }
                        }

                        //针对贷记卡信息列表下的近12个月最高连续逾期期数
                        List cardContinuityOverdueCountList = (ArrayList) creditCardInfo.get("cardContinuityOverdueCount");
                        if (cardContinuityOverdueCountList != null && cardContinuityOverdueCountList.size() > 0) {
                            for (int j = 0; j < cardContinuityOverdueCountList.size(); j++) {
                                Map cardContinuityOverdueCount = (HashMap) cardContinuityOverdueCountList.get(j);
                                //贷记卡序号
                                String serialNo = (String) cardContinuityOverdueCount.get("serialNo");
                                //贷款状态
                                String continuityOverduePeriods = (String) cardContinuityOverdueCount.get("continuityOverduePeriods");

                                Map plmsCreaditCard = new HashMap();
                                String cardContinuityOverdueCountCode = getUUID();
                                plmsCreaditCard.put("code", cardContinuityOverdueCountCode);
                                plmsCreaditCard.put("cardNo", serialNo);
                                plmsCreaditCard.put("period", continuityOverduePeriods);
                                plmsCreaditCard.put("creditCode", creditInfoCode);
                                plmsCreaditCard.put("status", GlobDict.valid.getKey());

                                creditInfoDao.insertCreaditCard(plmsCreaditCard);
                            }
                        }
                    }
                    }
                }

                //同步担保信息表
                List loanGuaranteeInfos = (ArrayList) loanCreditInfo.get("loanGuaranteeInfos");
                if (loanGuaranteeInfos != null && loanGuaranteeInfos.size()>0) {
                    for (int i = 0;i<loanGuaranteeInfos.size();i++) {
                        Map loanGuaranteeInfo = (HashMap)loanGuaranteeInfos.get(i);
                        String guaranteeStatus = (String) loanGuaranteeInfo.get("guaranteeStatus");
                        String guaranteeAmount = (String) loanGuaranteeInfo.get("guaranteeAmount");
                        String guaranteeBalance = (String) loanGuaranteeInfo.get("guaranteeBalance");

                        Map plmsLoanGuaranteeInfo = new HashMap();
                        String loanGuaranteeInfoCode = getUUID();
                        plmsLoanGuaranteeInfo.put("code",loanGuaranteeInfoCode);
                        plmsLoanGuaranteeInfo.put("guaranteeStatus", guaranteeStatus);
                        plmsLoanGuaranteeInfo.put("guaranteeAmount", guaranteeAmount);
                        plmsLoanGuaranteeInfo.put("guaranteeBalance", guaranteeBalance);
                        plmsLoanGuaranteeInfo.put("creditCode", creditInfoCode);
                        plmsLoanGuaranteeInfo.put("status", GlobDict.valid.getKey());

                        creditInfoDao.insertLoanGuaranteeInfo(plmsLoanGuaranteeInfo);
                    }
                }
            }
        }
        //同步司法信息
        if (loanJsuticeInfoList!= null && loanJsuticeInfoList.size()>0) {
            for (int i = 0;i<loanJsuticeInfoList.size();i++) {
                Map loanJsuticeInfo = (HashMap)loanJsuticeInfoList.get(i);
                String name = (String) loanJsuticeInfo.get("name");
                String justiceInfo = (String) loanJsuticeInfo.get("justiceInfo");
                String caseNo = (String) loanJsuticeInfo.get("caseNo");
                String subjectExecution = (String) loanJsuticeInfo.get("subjectExecution");
                String flagLawsuit = (String) loanJsuticeInfo.get("flagLawsuit");
                String infoNote = (String) loanJsuticeInfo.get("infoNote");

                Map plmsLoanJsuticeInfo = new HashMap();
                String loanJsuticeInfoCode = getUUID();
                plmsLoanJsuticeInfo.put("code",loanJsuticeInfoCode);
                plmsLoanJsuticeInfo.put("name", name);
                plmsLoanJsuticeInfo.put("justiceInfo", justiceInfo);
                plmsLoanJsuticeInfo.put("caseNo", caseNo);
                plmsLoanJsuticeInfo.put("subjectExecution", subjectExecution);
                plmsLoanJsuticeInfo.put("flagLawsuit", flagLawsuit);
                plmsLoanJsuticeInfo.put("infoNote", infoNote);
                plmsLoanJsuticeInfo.put("applyCode", applyCode);
                plmsLoanJsuticeInfo.put("status", GlobDict.valid.getKey());

                judicialInfoDao.insert(plmsLoanJsuticeInfo);
            }
        }

        //同步案件评估信息
        if (loanCaseUse!= null) {
            String familyAssets = (String) loanCaseUse.get("familyAssets");
            String familyDebt = (String) loanCaseUse.get("familyDebt");
            String familyIncome = (String) loanCaseUse.get("familyIncome");
            String mortgaged = (String) loanCaseUse.get("mortgaged");
            String loanUse = (String) loanCaseUse.get("loanUse");
            String refundSource = (String) loanCaseUse.get("refundSource");

            Map plmsLoanCaseUse = new HashMap();
            String loanCaseUseCode = getUUID();
            plmsLoanCaseUse.put("code",loanCaseUseCode);
            plmsLoanCaseUse.put("familyAssets", familyAssets);
            plmsLoanCaseUse.put("familyDebt", familyDebt);
            plmsLoanCaseUse.put("familyIncome", familyIncome);
            plmsLoanCaseUse.put("mortgaged", mortgaged);
            plmsLoanCaseUse.put("loanUse", loanUse);
            plmsLoanCaseUse.put("refundSource", refundSource);
            plmsLoanCaseUse.put("applyCode", applyCode);
            plmsLoanCaseUse.put("status", GlobDict.valid.getKey());

            loanCaseUseDao.insert(plmsLoanCaseUse);
        }

        //同步附件信息
        if (loanAttachList != null && loanAttachList.size()>0){
            for (int i = 0;i < loanAttachList.size();i++){
                Map loanAttach = (HashMap)loanAttachList.get(i);
                String fileType = (String)loanAttach.get("fileType");
                List fileList = (ArrayList)loanAttach.get("fileList");
                for (int j = 0;j < fileList.size();j++) {
                    String fileId = (String)loanAttach.get("fileId");
                    String originalFileName = (String)loanAttach.get("originalFileName");
                    Map plmsFileMap = new HashMap();
                    String fileCode = getUUID();
                    plmsFileMap.put("code",fileCode);
                    plmsFileMap.put("fileId", fileId);
                    plmsFileMap.put("originalFileName", originalFileName);
                    plmsFileMap.put("fileType",fileType);
                    plmsFileMap.put("status", GlobDict.valid.getKey());

                    applyInfoDao.insertMaterial(plmsFileMap);
                }
            }
        }

        //同步审批信息表记录
        if (loanRiskInfo != null){
            String firstOpinion = (String)loanRiskInfo.get("firstOpinion");
            String secondOpinion = (String)loanRiskInfo.get("secondOpinion");

            String thirdOpinion = (String)loanRiskInfo.get("thirdOpinion");
            String proprietaryOption = (String)loanRiskInfo.get("proprietaryOption");

            String insuranceOption = (String)loanRiskInfo.get("insuranceOption");
            String guaranteeCorporationOption = (String)loanRiskInfo.get("guaranteeCorporationOption");

            String guaranteeTerms = (String)loanRiskInfo.get("guaranteeTerms");
            String lendingTerms = (String)loanRiskInfo.get("lendingTerms");

            String totalApprovalAmount = (String)loanRiskInfo.get("totalApprovalAmount");
            String guaranteeAmount = (String)loanRiskInfo.get("guaranteeAmount");

            String approvalPeriod = (String)loanRiskInfo.get("approvalPeriod");
            String approvalDate = (String)loanRiskInfo.get("approvalDate");

            Map plmsLoanRiskInfo = new HashMap();
            String loanRiskInfoCode = getUUID();
            plmsLoanRiskInfo.put("code",loanRiskInfoCode);
            plmsLoanRiskInfo.put("firstOpinion", firstOpinion);
            plmsLoanRiskInfo.put("secondOpinion", secondOpinion);
            plmsLoanRiskInfo.put("thirdOpinion",thirdOpinion);

            plmsLoanRiskInfo.put("proprietaryOption",proprietaryOption);
            plmsLoanRiskInfo.put("insuranceOption", insuranceOption);
            plmsLoanRiskInfo.put("guaranteeCorporationOption", guaranteeCorporationOption);

            plmsLoanRiskInfo.put("guaranteeCondition", guaranteeTerms);
            plmsLoanRiskInfo.put("lendingTerms", lendingTerms);
            plmsLoanRiskInfo.put("approvalQuota",totalApprovalAmount);

            plmsLoanRiskInfo.put("guaranteeLimit",guaranteeAmount);
            plmsLoanRiskInfo.put("approvalPeriod", approvalPeriod);
            plmsLoanRiskInfo.put("approvalTime", approvalDate);

            plmsLoanRiskInfo.put("status", GlobDict.valid.getKey());

            plmsLoanRiskInfo.put("applyCode",applyCode);

            loanRiskInfoDao.insert(plmsLoanRiskInfo);
        }


    }
}
