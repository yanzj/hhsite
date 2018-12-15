package com.vilio.plms.dao;

import com.vilio.plms.pojo.ReceiptsRecord;

import java.util.List;
import java.util.Map;

/**
 * 类名： ReceiptsRecordDao<br>
 * 功能：入账流水Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface ReceiptsRecordDao {

    //保存入账流水操作
    public int saveReceiptsRecord(ReceiptsRecord receiptsRecord);
    //修改状态和更改处理批次号
    public int updateReceiptsRecordStatusAndBatchNo(Map param);
    //根据批次号获取入账流水列表
    public List<ReceiptsRecord> queryReceiptsRecordByBatchNo(ReceiptsRecord receiptsRecord);
    //更改状态
    public int updateReceiptsRecordDealStatusByCode(ReceiptsRecord receiptsRecord);
    //根据code查询入账流水
    public ReceiptsRecord queryReceiptsRecordByCode(ReceiptsRecord receiptsRecord);
    //修改状态
    public Integer updateReceiptsRecordStatusByCode(ReceiptsRecord receiptsRecord);

    //入账日期大于此次拟入账资金的入账日期，且小于等于当前日期；
    public List<ReceiptsRecord> queryReceiptsRecordByReceiptsDate(Map date);

    //根据日期查询资金入账(有可能多条)
    public List<ReceiptsRecord> queryReceiptsRecordByReceiptsDateAndContractCode(ReceiptsRecord receiptsRecord);



}
