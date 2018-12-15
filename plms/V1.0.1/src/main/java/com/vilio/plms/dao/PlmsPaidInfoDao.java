package com.vilio.plms.dao;

import com.vilio.plms.pojo.BorrowApply;
import com.vilio.plms.pojo.PaidInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/19.
 */
public interface PlmsPaidInfoDao {

    //查询用户放款信息
    public List<BorrowApply> queryUserPaidInfo(BorrowApply borrowApply) throws Exception;

    public Map getPaidInfoAndContractByPaidCode(String paidCode)throws Exception;

    int updatePaidInfoByCode(PaidInfo paidInfo)throws Exception;

    int updateFinishedPaidInfoStatusByCodeAndStatus(PaidInfo paidInfo)throws Exception;

    int updatePaidInfoByCodeOriginalData(PaidInfo paidInfo)throws Exception;

    List<PaidInfo> queryPaidInfoNeedCancel(Map map)throws Exception;
}
