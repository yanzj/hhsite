package com.vilio.plms.service.quartz;

import com.vilio.plms.dao.AccountDetailDao;
import com.vilio.plms.dao.ReceiptsRecordDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.AccountDetail;
import com.vilio.plms.pojo.ReceiptsRecord;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
import com.vilio.plms.service.base.PayScheduleDetailForContract;
import com.vilio.plms.service.base.RollBackPaymentAndOverdueService;
import com.vilio.plms.util.JudgeUtil;
import com.vilio.plms.util.MathUtil;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： ReceiptsService<br>
 * 功能：资金入账删除业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class ReceiptsDeleteService extends BaseService {

    private static final Logger logger = Logger.getLogger(ReceiptsDeleteService.class);

    @Resource
    private ReceiptsRecordDao receiptsRecordDao;

    @Resource
    private AccountDetailDao accountDetailDao;

    @Resource
    private CommonService commonService;

    /**
     * 定时任务执行主流程实现
     */
    @Override
    public void execute() throws ErrorException {
        receiptsDelete();
    }

    /**
     * 资金入账删除操作
     */
    private void receiptsDelete() throws ErrorException {
        //将删除待处理状态修改为删除处理中状态
        Map dbLock = new HashMap<>();
        dbLock.put("dealBatchNo", getUUID());
        dbLock.put("oldDealStatus1", GlobDict.receipts_deal_stauts_delete.getKey());
        dbLock.put("oldDealStatus2", GlobDict.receipts_deal_stauts_delete.getKey());
        dbLock.put("newDealStatus", GlobDict.receipts_deal_stauts_delete_ing.getKey());
        int ret = receiptsRecordDao.updateReceiptsRecordStatusAndBatchNo(dbLock);
        if (ret <= 0) {
            //没有删除待处理的状态
            logger.info("没有删除待处理状态流水");
            return;
        }
        //获取当前批次号下面的所有流水，时间降序排序
        ReceiptsRecord receiptsRecordParam = new ReceiptsRecord();
        receiptsRecordParam.setDealBatchNo(dbLock.get("dealBatchNo").toString());
        List<ReceiptsRecord> receiptsRecords = receiptsRecordDao.queryReceiptsRecordByBatchNo(receiptsRecordParam);
        //开始单条资金入账删除开始处理
        String batchCode = commonService.getUUID();
        for (ReceiptsRecord receiptsRecord : receiptsRecords) {
            try {
                ((ReceiptsDeleteService) AopContext.currentProxy()).receiptsDeteleSingleTrans(batchCode,receiptsRecord);
                //删除资金入账记录
                receiptsRecord.setStatus(GlobDict.un_valid.getKey());
                ret = receiptsRecordDao.updateReceiptsRecordStatusByCode(receiptsRecord);
                if (ret <= 0) {
                    throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
                }
            } catch (ErrorException e) {
                e.printStackTrace();
                receiptsRecord.setDealStatus(GlobDict.receipts_deal_stauts_delete_fail.getKey());
                receiptsRecord.setDealMsg(e.getMessage() == null || "".equals(e.getMessage())
                        ? GlobParam.ERROR_CODE.get(e.getErroCode()) : e.getMessage());
                ret = receiptsRecordDao.updateReceiptsRecordDealStatusByCode(receiptsRecord);
                if (ret <= 0) {
                    throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
                }
                commonService.monitorEmail("单条资金入账删除定时任务", receiptsRecord.getCode() + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                //如果报错，当条流水标记为删除处理失败，并且记录错误信息
                receiptsRecord.setDealStatus(GlobDict.receipts_deal_stauts_delete_fail.getKey());
                receiptsRecord.setDealMsg(e.getMessage().length() > 500 ? e.getMessage().substring(0, 500) : e.getMessage());
                ret = receiptsRecordDao.updateReceiptsRecordDealStatusByCode(receiptsRecord);
                if (ret <= 0) {
                    throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
                }
                commonService.monitorEmail("单条资金入账删除定时任务", receiptsRecord.getCode() + e.getMessage());
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void receiptsDeteleSingleTrans(String batchCode,ReceiptsRecord receiptsRecord) throws ErrorException, ParseException, Exception {
        ((ReceiptsDeleteService) AopContext.currentProxy()).receiptsDeteleSingle(receiptsRecord.getReceiptsDate(), receiptsRecord.getContractCode(),batchCode, receiptsRecord);
    }

    /**
     * 单条处理入账流水
     *
     * @param receiptsRecord
     */

    public void receiptsDeteleSingle(String rollBachDate, String contractCode, String batchCode,ReceiptsRecord receiptsRecord) throws ErrorException, ParseException, Exception {
        //扣减账户余额
        AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(receiptsRecord.getContractCode());
        if (accountDetail == null) {
            throw new ErrorException(ReturnCode.ACCOUNT_DETAIL_FAIL, "");
        }
        //判断是宏获账户还是资方账户
        //判断入哪个账户里面
        if (GlobDict.account_type_vilio.getKey().equals(receiptsRecord.getAccountType())) {
            //宏获账户
            accountDetail.setHonghuoBalance(MathUtil.strSub(JudgeUtil.isNull(accountDetail.getHonghuoBalance()) ? accountDetail.getHonghuoBalance() : "0", receiptsRecord.getReceiptsAmount(), 2));
        } else if (GlobDict.account_type_fund_side.getKey().equals(receiptsRecord.getAccountType())) {
            //资方账户
            accountDetail.setFundSideBalance(MathUtil.strSub(JudgeUtil.isNull(accountDetail.getFundSideBalance()) ? accountDetail.getFundSideBalance() : "0", receiptsRecord.getReceiptsAmount(), 2));
        } else {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "账户类型错误");
        }
        //修改账户余额
        int ret = accountDetailDao.updateAccountDetailBalanceByCode(accountDetail);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }


}
