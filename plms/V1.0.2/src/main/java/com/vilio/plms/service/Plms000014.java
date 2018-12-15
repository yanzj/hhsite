package com.vilio.plms.service;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.*;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.RemoteFmsService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JudgeUtil;
import com.vilio.plms.util.MathUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 类名： Plms000014<br>
 * 功能：放款确认<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月24日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000014 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000014.class);

    @Resource
    private BorrowApplyDao borrowApplyDao;

    @Resource
    private ProductDao productDao;

    @Resource
    private DistributorDao distributorDao;

    @Resource
    private ApplyInterestingDao applyInterestingDao;

    @Resource
    private AccountDetailDao accountDetailDao;

    @Resource
    private FundSideDao fundSideDao;

    @Resource
    private AccountDao accountDao;

    @Resource
    private ApplyInfoDao applyInfoDao;

    @Resource
    private PaidInfoDao paidInfoDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private AccountInfoDao accountInfoDao;

    @Resource
    private BorrowVoucherDao borrowVoucherDao;

    @Resource
    private IouDao iouDao;

    @Resource
    private CommonService commonService;
    @Resource
    RemoteFmsService remoteFmsService;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //借款订单标识号
        checkField(ObjectUtils.toString(body.get("borrowCode")), "业务标识", null, 36);
        //审批意见
        //checkField(ObjectUtils.toString(body.get("comments")), "审批意见", null, 100);
        //判断放款金额
        if (!"00".equals(JudgeUtil.isMoney(ObjectUtils.toString(body.get("paidAmount")), 9, 2))) {
            //金额校验失败
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "放款金额错误");
        }
        //判断放款时间
        checkField(ObjectUtils.toString(body.get("paidTime")), "放款时间", null, 10);
        //客户承诺
        checkField(ObjectUtils.toString(body.get("promise")), "客户承诺", null, 200);

    }

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
        String datePattern = "yyyy-MM-dd";
        //yyyy-MM-dd
        String strPaidTime = (String)body.get("paidTime");
        Date paidTime = DateUtil.parseDateTimeForPattern(strPaidTime, datePattern);
        //查询当前借款订单信息
        BorrowApply borrowApplyQuery = new BorrowApply();
        borrowApplyQuery.setCode(ObjectUtils.toString(body.get("borrowCode")));
        BorrowApply borrowApply = borrowApplyDao.queryBorrowApplyByCode(borrowApplyQuery);
        //判断是否为放款中状态，如果不是放款中状态不能修改
        if (!GlobDict.order_status_loan_ing.getKey().equals(borrowApply.getApplyStatus())) {
            //不是放款中，不能放款确认
            throw new ErrorException(ReturnCode.BORROW_APPLY_STATUS_LOAN_ING, "");
        }

        //查询产品表信息
        Product product = getProduct(borrowApply);
        //查询渠道信息
        //Map distributor = getDistributor(borrowApply);
        //查询进件利息表，获取当前利率
        ApplyInteresting applyInteresting = getApplyInteresting(borrowApply);
        //获取账户明细表
        AccountDetail accountDetail = getAccountDetail(borrowApply);
        //获取资方信息表
        FundSide fundSide = getFundSide(borrowApply);
        //根据账户明细表，查询账户汇总表
        Account account = getAccount(accountDetail);
        //查询进件申请表
        ApplyInfo applyInfo = getApplyInfo(borrowApply);
        //获取用户信息（借款借据需要客户姓名）
        UserInfo userInfo = getUserInfo(account);
        //查询银行账户表
        AccountInfo accountInfo = getBankAccountInfo(applyInfo);
        //更改借款订单状态,并且保存审批意见
        updateBorrowApply(body);

        //应还利息、应还服务费和应还保证金计算
        Map returnInterestMap = commonService.calculateInterestForPaidinfo(new BigDecimal((String) body.get("paidAmount")), paidTime, DateUtil.parseDateTimeForPattern(borrowApply.getBorrowEndDate(), DateUtil.DATE_PATTERN2) , borrowApply.getContractCode());
        if(null == returnInterestMap){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "该合同contractCode=" + borrowApply.getContractCode() + "计算出问题！");
        }
        int repayModel = (int) returnInterestMap.get("repayModel");
        BigDecimal totalInterest = (BigDecimal) returnInterestMap.get("totalInterest");
        BigDecimal dayInterest = (BigDecimal) returnInterestMap.get("dayInterest");
        BigDecimal monthAvgInterest = (BigDecimal) returnInterestMap.get("monthAvgInterest");
        BigDecimal totalServiceFee = (BigDecimal) returnInterestMap.get("totalServiceFee");
        BigDecimal dayServiceFee = (BigDecimal) returnInterestMap.get("dayServiceFee");
        BigDecimal monthAvgServiceFee = (BigDecimal) returnInterestMap.get("monthAvgServiceFee");
        BigDecimal bail = (BigDecimal) returnInterestMap.get("bigBail");

        //定义放款信息表入库参数
        PaidInfo paidInfo = new PaidInfo();
        paidInfo.setBusinessCode(getDateSeq("SERNO", 4));

        //创建借款借据
        Iou iou = inserIou(product, body, paidInfo, userInfo, accountInfo,applyInteresting,borrowApply);
        //创建放款记录
        insertPaidInfo(borrowApply, product, applyInteresting, accountDetail, fundSide, account, applyInfo, iou, body, paidInfo, totalInterest, totalServiceFee, bail);

        //判断如果放款凭证有，则放款凭证入库
        if (body.get("borrowVoucher") != null && ((List) body.get("borrowVoucher")).size() > 0) {
            //放款凭证入库
            saveBorrowVoucher(paidInfo,(List) body.get("borrowVoucher"));
        }
        Date borrowEndDate = DateUtil.parseDate2(borrowApply.getBorrowEndDate());
        //创建还款计划
        commonService.buildRepaymentScheduleDetail(borrowEndDate, paidInfo.getCode());

        //更新账户明细
        accountDetail.setTotalQuota(null);
        accountDetail.setPrincipal((String) body.get("paidAmount"));
        accountDetail.setRemainingQuota("-" + (String) body.get("paidAmount"));
        accountDetail.setInterest(totalInterest.toString());
        accountDetail.setBail(bail.toString());
        accountDetail.setServiceFee(totalServiceFee.toString());
        accountDetail.setOverdue(null);
        accountDetail.setServiceFeePenalty(null);
        accountDetail.setBailPenalty(null);
        accountDetail.setRepaymentedPrincipal(null);
        accountDetail.setRepaymentedInterest(null);
        accountDetail.setRepaymentedServiceFee(null);
        accountDetail.setRepaymentedBail(null);
        accountDetail.setRepaymentedOverdue(null);
        accountDetail.setRepaymentedServiceFeePenalty(null);
        accountDetail.setRepaymentedBailPenalty(null);
        accountDetail.setRepaymentedAheadInterestPenalty(null);
        accountDetail.setRepaymentedAheadServiceFeePenalty(null);
        accountDetailDao.updateAccountDetailAmountAdd(accountDetail);
        //更新账户汇总
        account.setTotalQuota(null);
        account.setPrincipal((String) body.get("paidAmount"));
        account.setRemainingQuota("-" + (String) body.get("paidAmount"));
        account.setInterest(totalInterest.toString());
        account.setBail(bail.toString());
        account.setServiceFee(totalServiceFee.toString());
        account.setOverdue(null);
        account.setServiceFeePenalty(null);
        account.setBailPenalty(null);
        account.setRepaymentedPrincipal(null);
        account.setRepaymentedInterest(null);
        account.setRepaymentedServiceFee(null);
        account.setRepaymentedBail(null);
        account.setRepaymentedOverdue(null);
        account.setRepaymentedServiceFeePenalty(null);
        account.setRepaymentedBailPenalty(null);
        account.setRepaymentedAheadInterestPenalty(null);
        account.setRepaymentedAheadServiceFeePenalty(null);
        accountDao.updateAccountAmountAdd(account);
    }

    /**
     * 保存放款凭证
     * @param paidInfo
     * @param borrowVoucherList
     */
    private void saveBorrowVoucher(PaidInfo paidInfo, List<Map> borrowVoucherList) throws ErrorException {
        List<BorrowVoucher> borrowVouchers = new ArrayList<BorrowVoucher>();
        for (Map borrowVoucherMap: borrowVoucherList) {
            String serialNo = (String) borrowVoucherMap.get("serialNo");
            BorrowVoucher borrowVoucher = new BorrowVoucher();
            borrowVoucher.setCode(getUUID());
            borrowVoucher.setFileCode(serialNo);
            borrowVoucher.setStatus(GlobDict.valid.getKey());
            borrowVoucher.setCreateDate(DateUtil.getCurrentDateTime2());
            borrowVoucher.setModifyDate(DateUtil.getCurrentDateTime2());
            borrowVoucher.setPaidInfoCode(paidInfo.getCode());

            //去文件系统获取上载时间
            List fileList = new ArrayList();
            Map fileMap = new HashMap();
            fileMap.put("serialNo",serialNo);
            fileList.add(fileMap);
            Map map = new HashMap();
            map.put("serialNoList",fileList);
            Map result = null;
            try {
                result = remoteFmsService.getUrlList(map);
                //获取返回结果
                Map body = (Map)result.get(Fields.PARAM_MESSAGE_BODY);
                List fileMaps = (List)body.get("fileMaps");
                fileMap = (Map)fileMaps.get(0);
                String uploadTime = (String)fileMap.get("uploadTime");
                borrowVoucher.setUploadTime(uploadTime);
                borrowVoucher.setFileName((String)fileMap.get("fileName"));
            } catch (Exception e) {
                logger.error("访问文件系统出错" + e);
            }
            borrowVouchers.add(borrowVoucher);
        }
        int ret = borrowVoucherDao.saveBorrowVoucher(borrowVouchers);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 查询银行账户表
     *
     * @param applyInfo
     */
    private AccountInfo getBankAccountInfo(ApplyInfo applyInfo) throws ErrorException {
        AccountInfo accountInfoParam = new AccountInfo();
        accountInfoParam.setCode(applyInfo.getAccountCode());
        AccountInfo accountInfo = accountInfoDao.qryAccountInfo(accountInfoParam);
        if (accountInfo == null) {
            throw new ErrorException(ReturnCode.BANK_INFO_FAIL, "");
        }
        return accountInfo;
    }

    /**
     * 获取用户信息
     *
     * @param account
     */
    private UserInfo getUserInfo(Account account) throws ErrorException {
        UserInfo userInfoParam = new UserInfo();
        userInfoParam.setCode(account.getUserCode());
        UserInfo userInfo = userInfoDao.queryUserInfo(userInfoParam);
        if (userInfo == null) {
            throw new ErrorException(ReturnCode.USER_INFO_FAIL, "");
        }
        return userInfo;
    }

    /**
     * 获取进件申请信息
     *
     * @param borrowApply
     */
    private ApplyInfo getApplyInfo(BorrowApply borrowApply) throws ErrorException {
        //查询进件申请信息表
        ApplyInfo applyInfoParam = new ApplyInfo();
        applyInfoParam.setCode(borrowApply.getApplyCode());
        ApplyInfo applyInfo = applyInfoDao.qryApplyInfo(applyInfoParam);
        if (applyInfo == null) {
            //进件信息错误
            throw new ErrorException(ReturnCode.APPLY_INFO_FAIL, "");
        }
        return applyInfo;
    }

    /**
     * 查询账户汇总表
     *
     * @param accountDetail
     */
    private Account getAccount(AccountDetail accountDetail) throws ErrorException {
        Account accountParam = new Account();
        accountParam.setCode(accountDetail.getAccountCode());
        Account account = accountDao.qryAccount(accountParam);
        if (account == null) {
            //账户信息不存在
            throw new ErrorException(ReturnCode.ACCOUNT_COLLECT_FAIL, "");
        }
        return account;
    }

    /**
     * 获取资方信息表
     *
     * @param borrowApply
     */
    private FundSide getFundSide(BorrowApply borrowApply) throws ErrorException {
        FundSide fundSide = new FundSide();
        fundSide.setContractCode(borrowApply.getContractCode());
        FundSide fundSide1 = fundSideDao.queryFundSideByContract(fundSide);
        if (fundSide1 == null) {
            throw new ErrorException(ReturnCode.FUND_SIDE_FAIL, "");
        }
        return fundSide;
    }

    /**
     * 获取账户明细表
     *
     * @param borrowApply
     */
    private AccountDetail getAccountDetail(BorrowApply borrowApply) throws ErrorException {
        AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(borrowApply.getContractCode());
        if (accountDetail == null) {
            //账户明细信息不存在
            throw new ErrorException(ReturnCode.ACCOUNT_DETAIL_FAIL, "");
        }
        return accountDetail;
    }

    /**
     * 获取当前进件利息表信息
     *
     * @param borrowApply
     */
    private ApplyInteresting getApplyInteresting(BorrowApply borrowApply) throws ErrorException {
        ApplyInteresting applyInterestingParam = new ApplyInteresting();
        applyInterestingParam.setContractCode(borrowApply.getContractCode());
        ApplyInteresting applyInteresting = applyInterestingDao.qryApplyInteresting(applyInterestingParam);
        if (applyInteresting == null) {
            //进件利息信息表不存在
            throw new ErrorException(ReturnCode.APPLY_INTERESTING_FAIL, "");
        }
        return applyInteresting;
    }

    /**
     * 查询渠道信息
     *
     * @param borrowApply
     */
    private Map getDistributor(BorrowApply borrowApply) throws ErrorException {
        Map qryParam = new HashMap();
        qryParam.put("applyCode", borrowApply.getApplyCode());
        Map distributor = distributorDao.queryDistributorByApplyNo(qryParam);
        if (distributor == null) {
            throw new ErrorException(ReturnCode.DISTRIBUTOR_FAIL, "");
        }
        return distributor;
    }

    /**
     * 创建借款借据表
     * @param product
     * @param body
     * @param paidInfo
     * @param userInfo
     * @param accountInfo
     * @param applyInteresting
     * @param borrowApply
     */
    private Iou inserIou(Product product, Map<String, Object> body, PaidInfo paidInfo, UserInfo userInfo, AccountInfo accountInfo, ApplyInteresting applyInteresting, BorrowApply borrowApply) throws ErrorException {
        Iou iouParam = new Iou();
        iouParam.setCode(getUUID());
        iouParam.setLoanAmount(ObjectUtils.toString(body.get("paidAmount")));
        iouParam.setSignDate(ObjectUtils.toString(body.get("paidTime")));
        iouParam.setPaidDate(ObjectUtils.toString(body.get("paidTime")));
        iouParam.setBussinessCode(paidInfo.getBusinessCode());
        iouParam.setCustomer(userInfo.getName());
        iouParam.setAccountNo(accountInfo.getAccountNo());
        iouParam.setAccountName(accountInfo.getName());
        //默认人民币
        iouParam.setCurrency(GlobParam.DEFAULT_CURRENCY);
        iouParam.setAnnualInteresting(applyInteresting.getAnnualizedInterest());
        iouParam.setOverdueInteresting(applyInteresting.getInterestOverInterest());
        iouParam.setContractNo(borrowApply.getContractCode());
        iouParam.setInterestStartDate(ObjectUtils.toString(body.get("paidTime")));
        iouParam.setInterestEndDate(borrowApply.getBorrowEndDate());
        iouParam.setRepaymentMethod(product.getRepaymentMethods());
        iouParam.setInterestCycle(product.getInterestCycle());
        //固定利率
        iouParam.setInterestingAdjustMethod(GlobDict.interesting_adjust_fixation.getKey());
        iouParam.setPromise(ObjectUtils.toString(body.get("promise")));
        iouParam.setElecSign("");
        iouParam.setStatus(GlobDict.valid.getKey());
        iouParam.setCreateDate(DateUtil.getCurrentDateTime2());
        iouParam.setModifyDate(DateUtil.getCurrentDateTime2());
        int ret = iouDao.saveIou(iouParam);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        return iouParam;
    }

    /**
     * 获取产品信息
     *
     * @param borrowApply
     */
    private Product getProduct(BorrowApply borrowApply) throws ErrorException {
        Product productParam = new Product();
        productParam.setApplyCode(borrowApply.getApplyCode());
        Product product = productDao.qryProduct(productParam);
        if (product == null) {
            throw new ErrorException(ReturnCode.PRODUCT_INFO_FAIL, "");
        }
        return product;
    }

    /**
     * 创建放款记录
     * @param borrowApply
     * @param product
     * @param applyInteresting
     * @param accountDetail
     * @param fundSide
     * @param account
     * @param applyInfo
     * @param iou
     * @param body
     * @param paidInfo
     */
    private void insertPaidInfo(BorrowApply borrowApply, Product product, ApplyInteresting applyInteresting, AccountDetail accountDetail, FundSide fundSide, Account account, ApplyInfo applyInfo, Iou iou, Map<String, Object> body, PaidInfo paidInfo, BigDecimal totalInterest, BigDecimal totalServiceFee, BigDecimal bail) throws Exception {
        paidInfo.setCode(getUUID());
        paidInfo.setPaidAmount(ObjectUtils.toString(body.get("paidAmount")));
        paidInfo.setPaidTime((String) body.get("paidTime"));
        paidInfo.setIouCode(iou.getCode());
        paidInfo.setBorrowCode(ObjectUtils.toString(body.get("borrowCode")));
        paidInfo.setUserCode(account.getUserCode());
        paidInfo.setContractCode(accountDetail.getContractCode());

        paidInfo.setInterest(totalInterest.toString());
        paidInfo.setServiceFee(null == totalServiceFee ? "0" : totalServiceFee.toString());
        paidInfo.setBail(null == bail ? "0" : bail.toString());
        paidInfo.setOverdue("0.00");
        paidInfo.setServiceFeePenalty("0");
        paidInfo.setBailPenalty("0");
        paidInfo.setRepaymentedPrincipal("0.00");
        paidInfo.setRepaymentedInterest("0.00");
        paidInfo.setRepaymentedBail("0");
        paidInfo.setRepaymentedOverdue("0.00");
        paidInfo.setRepaymentedServiceFee("0");
        paidInfo.setRepaymentedServiceFeePenalty("0");
        paidInfo.setRepaymentedBailPenalty("0");
        paidInfo.setRepaymentedAheadInterestPenalty("0");
        paidInfo.setRepaymentedAheadServiceFeePenalty("0");

        paidInfo.setRemark(ObjectUtils.toString(body.get("remark")));
        paidInfo.setAccountCode(applyInfo.getAccountCode());
        paidInfo.setCreateDate(DateUtil.getCurrentDateTime2());
        paidInfo.setModifyDate(DateUtil.getCurrentDateTime2());
        paidInfo.setStatus(GlobDict.paid_info_status_paying_and_not_overdue.getKey());
        int ret = paidInfoDao.savePaidInfo(paidInfo);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

    /**
     * 更改订单状态，并保留审批意见
     *
     * @param body
     * @throws ErrorException
     */
    private void updateBorrowApply(Map<String, Object> body) throws ErrorException {
        BorrowApply borrowApplyParam = new BorrowApply();
        borrowApplyParam.setCode(ObjectUtils.toString(body.get("borrowCode")));
        borrowApplyParam.setApplyStatus(GlobDict.order_status_loan_succ.getKey());
        borrowApplyParam.setModifyDate(DateUtil.getCurrentDateTime());
        //borrowApplyParam.setComments(ObjectUtils.toString(body.get("comments")));
        int ret = borrowApplyDao.updateBorrowApplyForStatus(borrowApplyParam);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }
}
