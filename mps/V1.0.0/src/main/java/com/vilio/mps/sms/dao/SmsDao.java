package com.vilio.mps.sms.dao;

import com.vilio.mps.sms.pojo.Sms;

/**
 * 类名： SmsDao<br>
 * 功能：模板Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface SmsDao {

    public int insertSmsInfo(Sms sms);

    public int updateSmsStatusBySerialNo(Sms sms);

}
