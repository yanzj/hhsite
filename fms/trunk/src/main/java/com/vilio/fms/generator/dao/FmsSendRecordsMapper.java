package com.vilio.fms.generator.dao;/**
 * Created by dell on 2017/8/28/0028.
 */

import com.vilio.fms.generator.pojo.FmsSendRecords;

import java.util.List;
import java.util.Map;

/**
 * 类名： FmsSendRecordsMapper<br>
 * 功能：<br>
 * 版本： 1.0<br>
 * 日期： 2017/8/28/0028<br>
 * 作者： liuzhu.feng<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface FmsSendRecordsMapper {

    public int insertSendRecords(FmsSendRecords fmsSendRecords) throws Exception;

    public int updateSendRecord(FmsSendRecords fmsSendRecords) throws Exception;

    public FmsSendRecords querySendRecordByRecordNo(String recordNo) throws Exception;

    public List<FmsSendRecords> querySendRecords(FmsSendRecords fmsSendRecords) throws Exception;

}
