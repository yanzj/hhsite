package com.vilio.plms.service.quartz.impl;

import com.vilio.plms.dao.AccountDetailDao;
import com.vilio.plms.dao.SysInfoParamDao;
import com.vilio.plms.service.quartz.UpdateCreditEndDateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by martin on 2017/8/24.
 */
@Service("updateCreditEndDateService")
public class UpdateCreditEndDateServiceImpl implements UpdateCreditEndDateService{
    @Resource
    SysInfoParamDao sysInfoParamDao;
    @Resource
    AccountDetailDao accountDetailDao;

    private static final Logger logger = Logger.getLogger("UpdateCreditEndDateService.class");

    public void execute() throws Exception{
        List creditEndDateLockList = (ArrayList)sysInfoParamDao.qryCreditEndDateLockList();
        if (creditEndDateLockList != null){
            Map creditEndDateLock = (HashMap)creditEndDateLockList.get(0);
            String executeDate = (String)creditEndDateLock.get("executeDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date creditEndDate = sdf.parse(executeDate);
            List accountDetailList = (ArrayList)sysInfoParamDao.qryAccountDetailByCreditEndDate(creditEndDate);

            if (accountDetailList != null && accountDetailList.size() >0) {
                for (int j = 0; j < accountDetailList.size(); j++) {
                    Map accountDetail = (HashMap) accountDetailList.get(j);
                    String accountCode = (String)accountDetail.get("accountCode");
                    BigDecimal oldAccountDetailRemainingQuota = (BigDecimal) accountDetail.get("remainingQuota");
                    accountDetail.put("remainingQuota",0);
                    sysInfoParamDao.updateAccountDetail(accountDetail);

                    List accountList = (ArrayList)sysInfoParamDao.qryAccount(accountCode);
                    if (accountList != null){
                        Map account = (HashMap)accountList.get(0);
                        BigDecimal totalQuota = (BigDecimal) account.get("totalQuota");
                        BigDecimal accountRemainingQuota = (BigDecimal)account.get("remainingQuota");
                        Date minCreditEndDate = sysInfoParamDao.qryMinCreditEndDateByAccountCode(accountCode);

                        Map conditionMap = new HashMap();
                        if (minCreditEndDate != null) {
                            conditionMap.put("accountDetailRemainingQuota", oldAccountDetailRemainingQuota);
                            conditionMap.put("creditEndDate", minCreditEndDate);
                        }else{
                            conditionMap.put("accountDetailRemainingQuota", oldAccountDetailRemainingQuota);
                            conditionMap.put("creditEndDate", creditEndDate);
                        }

                        sysInfoParamDao.updateAccountByRemainingQuotaAndCreditEndDate(conditionMap);
                    }
                }

            }
        }
    }

}
