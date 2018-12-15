package com.vilio.ppms.service.base;

import com.vilio.ppms.exception.ErrorException;

import java.util.Map;

/**
 * 类名： BaseService<br>
 * 功能：所有service必须继承的接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月19日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface BaseServiceInterface {

    public Map<String, Object> doService(Map<String, Object> params) throws ErrorException, Exception ;

}
