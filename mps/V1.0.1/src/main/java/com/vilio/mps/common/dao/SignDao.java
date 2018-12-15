package com.vilio.mps.common.dao;

import com.vilio.mps.common.pojo.Sign;

/**
 * 类名： SignDao<br>
 * 功能：签名Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：其他消息有可能也有用到签名实体和模板<br>
 */
public interface SignDao {

    public Sign getSignById(String innerSignNo);



}
