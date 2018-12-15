package com.vilio.plms.service.base;

import com.vilio.plms.dao.AccountDetailDao;
import com.vilio.plms.dao.ReceiptsRecordDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.AccountDetail;
import com.vilio.plms.pojo.ReceiptsRecord;
import com.vilio.plms.service.base.impl.OverdueByContractServiceImpl;
import com.vilio.plms.service.quartz.ReceiptsService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JudgeUtil;
import com.vilio.plms.util.MathUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类名： RecomputationPaymentAndOverdueService<br>
 * 功能：重新计算扣款和逾期<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月25日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Service
public class RecomputationPaymentAndOverdueService {
    private static final Logger logger = Logger.getLogger(ReceiptsService.class);

    @Resource
    private PayScheduleDetailForContract payScheduleDetailForContract;

    @Resource
    private ReceiptsRecordDao receiptsRecordDao;

    @Resource
    private AccountDetailDao accountDetailDao;

    @Resource
    private OverdueByContractServiceImpl overdueByContractService;


    /**
     * 重新计算扣款和逾期
     *
     * @param recomputationDate yyyy-MM-DD 今天扣昨天的款，今天生成昨天的逾期，从所传日期开始重新计算
     * @param contractCode
     */
    public void mainJob(String recomputationDate, String contractCode, String batchCode) throws Exception {
        if (recomputationDate.length() != 10) {
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "日期格式不正确");
        }
        //重新计算扣款和逾期
        while (DateUtil.dateCompareNow(recomputationDate) >= 0) {
            logger.info("开始跑扣款和逾期：" + recomputationDate);
            //跑扣款
            String paymentMethod = GlobDict.payment_method_auto.getKey();
            payScheduleDetailForContract.payContract(contractCode, DateUtil.parseDate2(recomputationDate));
            //跑逾期
            overdueByContractService.overdue(contractCode, recomputationDate);
            //判断当天是否有成功的资金入账，如果有，则进行资金入账
            //根据日期查询资金入账流水表，看是否有当前日期的资金入账流水
            ReceiptsRecord receiptsRecordParam = new ReceiptsRecord();
            receiptsRecordParam.setReceiptsDate(recomputationDate);
            receiptsRecordParam.setContractCode(contractCode);
            receiptsRecordParam.setDealStatus(GlobDict.receipts_deal_stauts_succ.getKey());
            List<ReceiptsRecord> receiptsRecords = receiptsRecordDao.queryReceiptsRecordByReceiptsDateAndContractCode(receiptsRecordParam);
            if (receiptsRecords == null || receiptsRecords.size() == 0) {
                //日期加1
                paymentMethod = GlobDict.payment_method_manual.getKey();
                payScheduleDetailForContract.payContractByBatchCode(contractCode, DateUtil.parseDate2(recomputationDate),batchCode,paymentMethod);
                recomputationDate = DateUtil.getDaysDate2(recomputationDate, 1);
                continue;
            }
            //根据合同查询账户明细
            //查询当前账户明细
            AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(contractCode);
            if (accountDetail == null) {
                throw new ErrorException(ReturnCode.ACCOUNT_DETAIL_FAIL, "");
            }
            //循环入账
            for (ReceiptsRecord receiptsRecord : receiptsRecords) {
                //判断入哪个账户里面
                if (GlobDict.receipts_record_account_type_honghuo.getKey().equals(receiptsRecord.getAccountType())) {
                    //宏获账户
                    accountDetail.setHonghuoBalance(MathUtil.strAdd(JudgeUtil.isNull(accountDetail.getHonghuoBalance()) ? accountDetail.getHonghuoBalance() : "0", receiptsRecord.getReceiptsAmount(), 2));
                } else if (GlobDict.receipts_record_account_type_fund_side.getKey().equals(receiptsRecord.getAccountType())) {
                    //资方账户
                    accountDetail.setFundSideBalance(MathUtil.strAdd(JudgeUtil.isNull(accountDetail.getFundSideBalance()) ? accountDetail.getFundSideBalance() : "0", receiptsRecord.getReceiptsAmount(), 2));
                } else if (GlobDict.receipts_record_account_type_honghuo_bail.getKey().equals(receiptsRecord.getAccountType())) {
                    //宏获保证金账户
                    accountDetail.setHonghuoBailAccountBalance(MathUtil.strAdd(JudgeUtil.isNull(accountDetail.getHonghuoBailAccountBalance()) ? accountDetail.getHonghuoBailAccountBalance() : "0", receiptsRecord.getReceiptsAmount(), 2));
                } else {
                    throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "账户类型错误");
                }
            }
            //添加账户余额
            int ret = accountDetailDao.updateAccountDetailBalanceByCode(accountDetail);
            if (ret <= 0) {
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }

            paymentMethod = GlobDict.payment_method_manual.getKey();
            payScheduleDetailForContract.payContractByBatchCode(contractCode, DateUtil.parseDate2(recomputationDate),batchCode,paymentMethod);
            //日期加1
            recomputationDate = DateUtil.getDaysDate2(recomputationDate, 1);
        }

    }


}
