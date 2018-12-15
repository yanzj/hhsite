package com.vilio.ppms.service.common;

import com.vilio.ppms.exception.ErrorException;

/**
 * 类名： CommonService<br>
 * 功能：公共基础类<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月19日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface CommonService {


    public void checkField(String field, String name, Integer needLength, Integer maxLength) throws ErrorException ;

    public String getSeq(String seqName, int num) throws ErrorException;

    public String getUUID();


}
