package com.vilio.plms.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/8/9.
 */
public interface FundManagerDao {

    //还款到账登记列表查询
    public List queryReceiptsRecordList(Map map);

    //选择借款合同列表查询
    public List queryContractList(Map map);

    //还款到账登记-手工登记初始化
    public Map queryContractInfoForManualReceipts(String contractCode);

    public int insertReceiptsRecordUpload(Map map);

    public int updateReceiptsRecordUpload(Map map);

    public int updateReceiptsVoucherStatus(Map map);

    public List queryReceiptsRecordUploadList(Map map);

    public int createReceiptsVoucher(Map map);

    public Map checkContractNoForUser(Map map);
}
