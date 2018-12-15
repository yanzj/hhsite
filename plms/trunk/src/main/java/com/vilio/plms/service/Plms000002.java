package com.vilio.plms.service;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.*;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * 类名： Plms000002<br>
 * 功能：借款接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月13日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000002 extends BaseService {


    @Resource
    private CustomerDao customerDao;

    @Resource
    private AccountDao accountDao;

    @Resource
    private AccountDetailDao accountDetailDao;

    @Resource
    private HouseDao houseDao;

    @Resource
    private ApplyInfoDao applyInfoDao;

    @Resource
    private ProductDao productDao;

    @Resource
    private ContractDao contractDao;

    @Resource
    private BorrowApplyDao borrowApplyDao;

    @Resource
    private ApplyInterestingDao applyInterestingDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private AccountInfoDao accountInfoDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //判断借款金额
        checkField(ObjectUtils.toString(body.get("amount")), "借款金额", null, 18);
        //判断借款金额
        if (!"00".equals(JudgeUtil.isMoney(ObjectUtils.toString(body.get("amount")), 9, 2))) {
            //金额校验失败
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "借款金额错误");
        }
        //判断抵押物code是否存在
        checkField(ObjectUtils.toString(body.get("contractCode")), "合同编码", null, 36);
        //判断期数不能为空，切必须大于0
        checkField(ObjectUtils.toString(body.get("borrowPeriod")), "借款月数", null, 2);

        if (Integer.parseInt(ObjectUtils.toString(body.get("borrowPeriod"))) <= 0) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "借款期数错误");
        }
    }


    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //判断借款人是否有效
        //Customer customer = checkCustomer(head, body);

        //修改为查询用户信息表  wangxf by 20170725
        UserInfo userInfo = checkUserInfo(head, body);

        //判断汇总信息表
        Account account = checkAccount(userInfo, body);
        //判断账户明细表
        AccountDetail accountDetail = checkAccountDetail(account, body);
        //判断抵押物是否有效（合同对应多个抵押物，以合同为准）
        //House house = checkHouse(body);
        //判断合同是否有效
        Contract contract = checkContract(body);
        //判断进件申请信息是否有效
        ApplyInfo applyInfo = checkApplyInfo(contract);
        //判断产品信息表
        Map productInfo = checkProduct(applyInfo, accountDetail, body);
        //校验利率
        ApplyInteresting applyInteresting = checkInteresting(contract);

        //查询收款银行卡账号信息
        AccountInfo accountInfo = checkAcountInfo(applyInfo);

        //申请表入库，并且更改账户汇总表和账户明细表中的剩余额度
        BorrowApply borrowApply = insertAndUpdateDB(contract, applyInfo, productInfo, account, accountDetail, body);

        if (JudgeUtil.isNull(productInfo.get("repaymentMethods"))) {
            if (GlobDict.first_interest.getKey().equals(productInfo.get("repaymentMethods"))) {
                resultMap.put("repaymentMethods", GlobDict.first_interest.getDesc());
            } else if (GlobDict.confirm_interest.getKey().equals(productInfo.get("repaymentMethods"))) {
                resultMap.put("repaymentMethods", GlobDict.confirm_interest.getDesc());
            } else if (GlobDict.after_interest.getKey().equals(productInfo.get("repaymentMethods"))) {
                resultMap.put("repaymentMethods", GlobDict.after_interest.getDesc());
            }
        }
        //增加银行卡账号信息
        resultMap.put("accountNo", accountInfo.getAccountNo());

        resultMap.put("businessCode", borrowApply.getBusinessCode());
        resultMap.put("applyTime", borrowApply.getApplyTime());
        resultMap.put("amount", borrowApply.getAmount());
        resultMap.put("borrowPeriod", borrowApply.getBorrowPeriod());
        resultMap.put("loanByDate", productInfo.get("loanByDate"));
        if (JudgeUtil.isNull(applyInteresting.getPrincipalOverInterest())) {
            String principalOverInterest = MathUtil.strMul2(applyInteresting.getPrincipalOverInterest(), "100", 2);
            resultMap.put("principalOverInterest", principalOverInterest);
        }
        if (JudgeUtil.isNull(applyInteresting.getAnnualizedInterest())) {
            String annualizedInterest = MathUtil.strMul2(applyInteresting.getAnnualizedInterest(), "100", 2);
            resultMap.put("annualizedInterest", annualizedInterest);
        }
    }

    /**
     * 查询银行卡信息
     *
     * @param applyInfo
     * @return
     */
    private AccountInfo checkAcountInfo(ApplyInfo applyInfo) throws ErrorException {
        AccountInfo accountInfoParam = new AccountInfo();
        accountInfoParam.setCode(applyInfo.getAccountCode());
        AccountInfo accountInfo = accountInfoDao.qryAccountInfo(accountInfoParam);
        if (accountInfo == null) {
            throw new ErrorException(ReturnCode.BANK_INFO_FAIL, "");
        }
        return accountInfo;
    }

    /**
     * 检查用户信息是否有效
     *
     * @param head
     * @param body
     * @return
     */
    private UserInfo checkUserInfo(Map<String, Object> head, Map<String, Object> body) throws ErrorException {
        UserInfo queryParam = new UserInfo();
        queryParam.setUmId(ObjectUtils.toString(head.get(Fields.PARAM_USER_ID)));
        UserInfo userInfo = userInfoDao.queryUserInfo(queryParam);
        if (userInfo == null) {
            throw new ErrorException(ReturnCode.USER_INFO_FAIL, "");
        }
        return userInfo;
    }

    /**
     * 检查利率是否存在
     *
     * @param contract
     */
    private ApplyInteresting checkInteresting(Contract contract) throws ErrorException {
        //根据产品代码查询进件利息信息表
        ApplyInteresting applyInterestingParam = new ApplyInteresting();
        applyInterestingParam.setContractCode(contract.getCode());
        ApplyInteresting applyInteresting = applyInterestingDao.qryApplyInteresting(applyInterestingParam);
        if (applyInteresting == null) {
            //进件利息信息表不存在
            throw new ErrorException(ReturnCode.APPLY_INTERESTING_FAIL, "");
        }
        return applyInteresting;
    }

    /**
     * 判断合同是否有效
     *
     * @param body
     */
    private Contract checkContract(Map<String, Object> body) throws ErrorException, ParseException {
        Contract contractParam = new Contract();
        contractParam.setCode(ObjectUtils.toString(body.get("contractCode")));
        Contract contract = contractDao.qryContract(contractParam);
        if (contract == null) {
            throw new ErrorException(ReturnCode.CONTRACT_INFO_FAIL, "");
        }
        if (DateUtil.dateThanNow(contract.getCreditEndDate())) {
            //当前时间在授信有效期之后
            throw new ErrorException(ReturnCode.CREDIT_END_DATE_FAIL, "");
        }
        return contract;

    }

    /**
     * 申请表入库，并且更改账户汇总表和账户明细表中的剩余额度
     *
     * @param contract
     * @param applyInfo
     * @param productInfo
     * @param body        @throws ErrorException
     */
    private BorrowApply insertAndUpdateDB(Contract contract, ApplyInfo applyInfo, Map productInfo, Account account, AccountDetail accountDetail, Map<String, Object> body) throws ErrorException {
        //上面校验全部通过后，入库借款申请表，申请状态为审批中
        BorrowApply borrowApplyInsert = new BorrowApply();
        borrowApplyInsert.setCode(getUUID());
        borrowApplyInsert.setAmount(ObjectUtils.toString(body.get("amount")));
        borrowApplyInsert.setApplyTime(DateUtil.getCurrentDateTime2());
        borrowApplyInsert.setApplyStatus(GlobDict.order_status_audity_ing.getKey());
        borrowApplyInsert.setBusinessCode(getDateSeq("SERNO", 4));
        borrowApplyInsert.setContractCode(contract.getCode());
        borrowApplyInsert.setApplyCode(applyInfo.getCode());
        borrowApplyInsert.setCreateDate(DateUtil.getCurrentDateTime());
        borrowApplyInsert.setModifyDate(DateUtil.getCurrentDateTime());
        borrowApplyInsert.setBorrowPeriod(ObjectUtils.toString(body.get("borrowPeriod")));
        borrowApplyInsert.setBorrowEndDate(productInfo.get("borrowEndDate").toString());
        int ret = borrowApplyDao.insertBorrowApply(borrowApplyInsert);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //精度放大为分，进行计算
        BigDecimal amountFen = MathUtil.mul(ObjectUtils.toString(body.get("amount")), "100");
        BigDecimal remainingQuotaFenAccount = MathUtil.mul(account.getRemainingQuota(), "100");
        BigDecimal remainingQuotaFenDetail = MathUtil.mul(accountDetail.getRemainingQuota(), "100");
        BigDecimal precision = new BigDecimal(100);
        //修改账户汇总表
        account.setRemainingQuota(remainingQuotaFenAccount.subtract(amountFen).divide(precision, 2, BigDecimal.ROUND_HALF_UP).toString());
        ret = accountDao.updateAccount(account);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //修改账户明细表
        accountDetail.setRemainingQuota(remainingQuotaFenDetail.subtract(amountFen).divide(precision, 2, BigDecimal.ROUND_HALF_UP).toString());
        ret = accountDetailDao.updateAccountDetail(accountDetail);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }

        return borrowApplyInsert;
    }

    /**
     * 检查产品是否有效
     *
     * @param applyInfo
     */
    private Map checkProduct(ApplyInfo applyInfo, AccountDetail accountDetail, Map<String, Object> body) throws ErrorException, ParseException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        //查询产品信息表
        Product productParam = new Product();
        productParam.setApplyCode(applyInfo.getCode());
        Product product = productDao.qryProduct(productParam);
        if (product == null) {
            //产品信息错误
            throw new ErrorException(ReturnCode.PRODUCT_INFO_FAIL, "");
        }
        //借款金额精度放大为分，进行计算
        BigDecimal amountFen = MathUtil.mul(ObjectUtils.toString(body.get("amount")), "100");
        //判断借款最小单位是否存在
        if (product.getLoanMinimumUnit() != null && !"".equals(product.getLoanMinimumUnit())) {
            //判断借款金额对最小单位求余，余数为0
            //最小单位精度放大为分
            BigDecimal loanMinimumUnitFen = MathUtil.mul(product.getLoanMinimumUnit(), "100");
            //开始求余数
            BigDecimal remainder = MathUtil.bigRemainder(amountFen, loanMinimumUnitFen, 0);
            if (remainder.compareTo(BigDecimal.valueOf(0)) != 0) {
                //余数不等于0，不是最小单位的倍数，驳回
                throw new ErrorException(ReturnCode.LOAN_MINIMUM_UNIT_FAIL, "");
            }
        }

        //判断借款最低金额是否存在
        if (product.getLoanMinimumAmount() != null && !"".equals(product.getLoanMinimumAmount())) {
            //判断是否小于最低金额
            if (MathUtil.strcompareTo(product.getLoanMinimumAmount(), ObjectUtils.toString(body.get("amount")))) {
                //最低金额大于借款金额，驳回
                throw new ErrorException(ReturnCode.LOAN_MINIMUM_AMOUNT_FAIL, "");
            }
        }

        //判断期数有效性，先算出最高期数，然后期数必须小于最高期数
        //当前时间加上本金归还周期算出一个日期，跟授信截止日期作比较（根据算头算尾参数决定是否减一天），如果大于授信截止日期，则本金归还周期递减1，继续算，直到满足条件
        //最高期数
        String loanCount = product.getPrincipalRepaymentPeriod();
        //借款到期日
        String maxBorrowEndDate = "";
        for (int i = Integer.parseInt(product.getPrincipalRepaymentPeriod()); i > 0; i--) {
            String date = DateUtil.convert(PlmsUtil.getDueDate(new Date(),i),"yyyy-MM-dd");
            //算出来的日期，和授信日期作比较，如果小于授信日期，则跳出循环
            if (DateUtil.dateCompare(date, accountDetail.getCreditEndDate(), "yyyy-MM-dd") >= 0) {
                //判断放款天数计算规则
                if (GlobDict.paid_days_computational_rule_end.getKey().equals(product.getPaidDaysComputationalRule())) {
                    //算头算尾
                } else {
                    //算头不算尾，算出来的日期加一天
                    date = DateUtil.getDaysDate2(date, 1);
                }
                //借款到期日
                maxBorrowEndDate = date;
                loanCount = String.valueOf(i);
                break;
            }
        }
        if ("".equals(maxBorrowEndDate)) {
            //借款到期日错误
            throw new ErrorException(ReturnCode.BORROW_END_DATE_FAIL, "");
        }

        if (GlobDict.is_period_select.getKey().equals(product.getIsPeriodSelect())) {
            //可选
            if (MathUtil.strcompareTo(ObjectUtils.toString(body.get("borrowPeriod")), loanCount)) {
                //传过来的期数大于最大期数报错
                throw new ErrorException(ReturnCode.BORROW_MAX_FAIL, "");
            }
        } else {
            if (!MathUtil.strcompareTo3(ObjectUtils.toString(body.get("borrowPeriod")), loanCount)) {
                //传过来的期数不等于最大期数报错
                throw new ErrorException(ReturnCode.BORROW_EQUAL_FAIL, "");
            }
        }
        //计算真实的借款到期日
        String borrowEndDate = DateUtil.convert(PlmsUtil.getDueDate(new Date(),Integer.parseInt(body.get("borrowPeriod").toString())),"yyyy-MM-dd");
        if (GlobDict.paid_days_computational_rule_no_end.getKey().equals(product.getPaidDaysComputationalRule())) {
            //算头不算尾，日期加1
            borrowEndDate = DateUtil.getDaysDate2(borrowEndDate, 1);
        }
        if (GlobDict.product_circle.getKey().equals(product.getCircle())) {
            //随借随还产品
            //判断首笔放款是否借款最高额
            if (GlobDict.is_first_max.getKey().equals(product.getIsFirstMax())) {
                //判断是否有首笔放款
                if (accountDetail.getFirstAmount() == null || "".equals(accountDetail.getFirstAmount())) {
                    throw new ErrorException(ReturnCode.FIRST_AMOUNT_ISNULL, "");
                }
                //判断借款金额是否大于首次放款金额
                //首次放款金额变成分
                BigDecimal firstAmountFen = MathUtil.mul(accountDetail.getFirstAmount(), "100");
                if (amountFen.compareTo(firstAmountFen) > 0) {
                    //借款金额大于首笔放款金额
                    throw new ErrorException(ReturnCode.FIRST_AMOUNT_FAIL, "");
                }
            }
        } else if (GlobDict.product_uncircle.getKey().equals(product.getCircle())) {
            //非随借随还产品（直接驳回）
            throw new ErrorException(ReturnCode.UNCIRCLE, "");
        } else {
            throw new ErrorException(ReturnCode.PRODUCT_CIRCLE_FAIL, "");
        }

        //随借随还产品肯定有借款封闭期参数
        //最近一次本金还款日减去借款封闭期，必须大于当前日期     修改为授信截止日    wangxf by 20170830
        String borrowClosedPeriodDate = DateUtil.getDateAfterNumMonth(accountDetail.getCreditEndDate(), -Integer.parseInt(product.getBorrowClosedPeriod()));
        //和当前日期作比较（当日是否可以借款）
        if (DateUtil.dateCompareNow(borrowClosedPeriodDate) >= 0) {
            throw new ErrorException(ReturnCode.BORROW_CLOSED_PERIOD_DATE, "");
        }
        Map retMap = BeanUtil.transBean2Map(product);
        retMap.put("loanByDate", DateUtil.convert(borrowClosedPeriodDate, "yyyyMMdd", "yyyy-MM-dd"));
        retMap.put("borrowEndDate", borrowEndDate);
        return retMap;
    }


    /**
     * 判断进件申请信息是否有效
     *
     * @param contract
     */
    private ApplyInfo checkApplyInfo(Contract contract) throws ErrorException, ParseException {
        //查询进件申请信息表
        ApplyInfo applyInfoParam = new ApplyInfo();
        applyInfoParam.setCode(contract.getApplyCode());
        ApplyInfo applyInfo = applyInfoDao.qryApplyInfo(applyInfoParam);
        if (applyInfo == null) {
            //进件信息错误
            throw new ErrorException(ReturnCode.APPLY_INFO_FAIL, "");
        }
        return applyInfo;
    }


    /**
     * 判断抵押物是否有效
     *
     * @param body
     */
    private House checkHouse(Map<String, Object> body) throws ErrorException {
        //查询当前借款抵押物
        House houseParam = new House();
        houseParam.setCode(ObjectUtils.toString(body.get("houseCode")));
        House house = houseDao.qryHouse(houseParam);
        if (house == null) {
            //抵押物信息错误
            throw new ErrorException(ReturnCode.HOUSE_INFO_FAIL, "");
        }
        return house;
    }


    /**
     * 判断账户明细表
     *
     * @param account
     * @param body
     */
    private AccountDetail checkAccountDetail(Account account, Map<String, Object> body) throws ErrorException, ParseException {
        //根据借款人编码和抵押物编码查询账户明细表
        AccountDetail accountDetailParam = new AccountDetail();
        accountDetailParam.setAccountCode(account.getCode());
        accountDetailParam.setContractCode(ObjectUtils.toString(body.get("contractCode")));
        AccountDetail accountDetail = accountDetailDao.qryAccountDetail(accountDetailParam);
        if (accountDetail == null) {
            //账户明细信息不存在
            throw new ErrorException(ReturnCode.ACCOUNT_DETAIL_FAIL, "");
        }
//        BigDecimal overdue = BigDecimal.valueOf(Double.parseDouble(StringUtils.defaultIfEmpty(accountDetail.getOverdue(), "0")));
//        if (overdue.compareTo(BigDecimal.valueOf(0)) != 0) {
//            //有未缴罚金
//            throw new ErrorException(ReturnCode.UNPAID_FINES, "");
//        }
//        BigDecimal penalty = BigDecimal.valueOf(Double.parseDouble(StringUtils.defaultIfEmpty(accountDetail.getPenalty(), "0")));
//        if (penalty.compareTo(BigDecimal.valueOf(0)) != 0) {
//            //有未缴违约金
//            throw new ErrorException(ReturnCode.PENALTY_FINESS, "");
//        }
        if (DateUtil.dateThanNow(accountDetail.getCreditEndDate())) {
            //当前时间在授信有效期之后
            throw new ErrorException(ReturnCode.CREDIT_END_DATE_FAIL, "");
        }
        BigDecimal remainingQuota = BigDecimal.valueOf(Double.parseDouble(StringUtils.defaultIfEmpty(accountDetail.getRemainingQuota(), "0")));
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(StringUtils.defaultIfEmpty(body.get("amount").toString(), "0")));
        if (amount.compareTo(remainingQuota) > 0) {
            //借款金额大于剩余额度
            throw new ErrorException(ReturnCode.LACK_OF_CREDIT, "");
        }
        return accountDetail;
    }


    /**
     * 判断汇总信息表
     *
     * @param userInfo
     * @throws ErrorException
     * @throws ParseException
     */
    private Account checkAccount(UserInfo userInfo, Map<String, Object> body) throws ErrorException, ParseException {
        //根据借款人信息查询账户汇总表
        Account accountParam = new Account();
        accountParam.setUserCode(userInfo.getCode());
        Account account = accountDao.qryAccount(accountParam);
        if (account == null) {
            //账户信息不存在
            throw new ErrorException(ReturnCode.ACCOUNT_COLLECT_FAIL, "");
        }
//        BigDecimal overdue = BigDecimal.valueOf(Double.parseDouble(StringUtils.defaultIfEmpty(account.getOverdue(), "0")));
//        if (overdue.compareTo(BigDecimal.valueOf(0)) != 0) {
//            //有未缴罚金
//            throw new ErrorException(ReturnCode.UNPAID_FINES, "");
//        }
//        BigDecimal penalty = BigDecimal.valueOf(Double.parseDouble(StringUtils.defaultIfEmpty(account.getPenalty(), "0")));
//        if (penalty.compareTo(BigDecimal.valueOf(0)) != 0) {
//            //有未缴违约金
//            throw new ErrorException(ReturnCode.PENALTY_FINESS, "");
//        }
        if (DateUtil.dateThanNow(account.getCreditEndDate())) {
            //当前时间在授信有效期之后
            throw new ErrorException(ReturnCode.CREDIT_END_DATE_FAIL, "");
        }
        BigDecimal remainingQuota = BigDecimal.valueOf(Double.parseDouble(StringUtils.defaultIfEmpty(account.getRemainingQuota(), "0")));
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(StringUtils.defaultIfEmpty(body.get("amount").toString(), "0")));
        if (amount.compareTo(remainingQuota) > 0) {
            //借款金额大于剩余额度
            throw new ErrorException(ReturnCode.LACK_OF_CREDIT, "");
        }
        return account;
    }


    /**
     * 判断借款人是否有效
     *
     * @param body
     * @throws ErrorException
     */
    private Customer checkCustomer(Map<String, Object> head, Map<String, Object> body) throws ErrorException, ParseException {
        //查询借款人信息表
        Customer customerParam = new Customer();
        customerParam.setUmId(head.get(Fields.PARAM_USER_ID).toString());
        Customer customer = customerDao.qryCustomer(customerParam);
        if (customer == null) {
            //借款人信息不存在
            throw new ErrorException(ReturnCode.BORROWER_INFO_FAIL, "");
        }
        //判断证件有效期
        if (DateUtil.dateThanNow(customer.getEndTime())) {
            //证件日期晚与今天
            throw new ErrorException(ReturnCode.ID_EXPIRY_DATE, "");
        }
        return customer;
    }


}
