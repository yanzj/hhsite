package com.vilio.mps.common.dao;


import com.vilio.mps.common.pojo.Template;

/**
 * 类名： TemplateDao<br>
 * 功能：模板Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：其他消息有可能也有用到签名实体和模板<br>
 */
public interface TemplateDao {

    public Template getTemplateById(String innerTemplateCode);


}
