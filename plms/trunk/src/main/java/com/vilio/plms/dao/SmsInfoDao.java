package com.vilio.plms.dao;

import com.vilio.plms.pojo.SmsInfo;

import java.util.List;
import java.util.Map;

/**
 * 类名： SmsInfoDao<br>
 * 功能：发送短信Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface SmsInfoDao {

    //批量入库短信信息表
    public int insertSmsInfoBatch(List<Map> smsInfos);

    //修改状态
    public int updateSmsInfoStatusByStatus(Map param);

    //根据批次号查询短信信息
    public List<SmsInfo> querySmsInfoByBatch(Map param);

    //根据code更新推送平台返回信息
    public int updateSmsInfoRetByCode(SmsInfo smsInfo);

    public int insertSmsInfoLogByBatch(Map param);

    public int deleteSmsInfoByBatch(Map param);

}
