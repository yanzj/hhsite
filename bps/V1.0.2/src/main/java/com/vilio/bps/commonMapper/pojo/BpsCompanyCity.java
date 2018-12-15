package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 估价公司与城市关联表
 * @数表名称 BPS_COMPANY_CITY
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsCompanyCity implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private String id             = null;
    /**
     * 关联公司(必填项)
     */
    private String companyCode    = null;
    /**
     * 关联城市(必填项)
     */
    private String cityCode       = null;
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    private String status         = null;
    /**
     * 排序(0为选中录单公司)
     */
    private Integer orderNo       = null;
    /**
     * 数据创建时间(必填项)
     */
    private Date dateCreated      = null;
    /**
     * 数据修改时间(必填项)
     */
    private Date dateModified     = null;
    /**
     * 排序
     */
    private String orderBy        = null;

    /*
     *--------------------------------------------------
     * Getter方法区
     *--------------------------------------------------
     */

    /**
     * 数据主键(必填项)(主键ID)
     */
    public String getId() {
        return trim(id);
    }
    /**
     * 关联公司(必填项)
     */
    public String getCompanyCode() {
        return trim(companyCode);
    }
    /**
     * 关联城市(必填项)
     */
    public String getCityCode() {
        return trim(cityCode);
    }
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    public String getStatus() {
        return trim(status);
    }
    /**
     * 排序(0为选中录单公司)
     */
    public Integer getOrderNo() {
        return orderNo;
    }
    /**
     * 数据创建时间(必填项)
     */
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * 数据修改时间(必填项)
     */
    public Date getDateModified() {
        return dateModified;
    }
    /**
     * 排序
     */
    public String getOrderBy() {
        return trim(orderBy);
    }

    /*
     *--------------------------------------------------
     * Setter方法区
     *--------------------------------------------------
     */

    /**
     * 数据主键(必填项)(主键ID)
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 关联公司(必填项)
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    /**
     * 关联城市(必填项)
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * 排序(0为选中录单公司)
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
    /**
     * 数据创建时间(必填项)
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    /**
     * 数据修改时间(必填项)
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
    /**
     * 排序
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


    /*
     *--------------------------------------------------
     * 常用自定义字段
     *--------------------------------------------------
     */

    /**
     * 数据创建时间(格式化)
     */
    public String getDateCreatedChar() {
        return getDate(dateCreated);
    }
    public void setDateCreatedChar(String dateCreatedChar) {
        this.dateCreated = getDate(dateCreatedChar);
    }
    /**
     * 数据创建时间(格式化)
     */
    public String getDateCreatedCharAll() {
        return getDateTime(dateCreated);
    }
    public void setDateCreatedCharAll(String dateCreatedCharAll) {
        this.dateCreated = getDate(dateCreatedCharAll);
    }
    /**
     * 数据修改时间(格式化)
     */
    public String getDateModifiedChar() {
        return getDate(dateModified);
    }
    public void setDateModifiedChar(String dateModifiedChar) {
        this.dateModified = getDate(dateModifiedChar);
    }
    /**
     * 数据修改时间(格式化)
     */
    public String getDateModifiedCharAll() {
        return getDateTime(dateModified);
    }
    public void setDateModifiedCharAll(String dateModifiedCharAll) {
        this.dateModified = getDate(dateModifiedCharAll);
    }

    /*
     *--------------------------------------------------
     * 应用小方法
     *--------------------------------------------------
     */

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    public String trim(String input)
    {
        return input==null?null:input.trim();
    }
    
    public String getDate(Date date)
    {
        if( null == date ) return "";
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    public String getDateTime(Date date)
    {
        if( null == date ) return "";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    public Date getDate(String date)
    {
        if( null == date || date.trim().isEmpty() ) return null;
        date = date.trim();
        Date result = null;
        try {
            if( date.length() >= 19 )
            {
                result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            }
            else if( date.length() == 10 )
            {
                result = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            }
        }
        catch (ParseException e) 
        {
            
        }
        return result;
    }

}


/** 
------------------------------------------------------
 Copy专用区
------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  Setter方法
------------------------------------------------------------------------------------------------------------

// 估价公司与城市关联表
BpsCompanyCity bpsCompanyCity = new BpsCompanyCity();

// 数据主键(必填项)(主键ID)
bpsCompanyCity.setId(  );
// 关联公司(必填项)
bpsCompanyCity.setCompanyCode(  );
// 关联城市(必填项)
bpsCompanyCity.setCityCode(  );
// 状态(0不可用，1可用)(必填项)
bpsCompanyCity.setStatus(  );
// 排序(0为选中录单公司)
bpsCompanyCity.setOrderNo(  );
// 数据创建时间(必填项)
bpsCompanyCity.setDateCreated(  );
// 数据修改时间(必填项)
bpsCompanyCity.setDateModified(  );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsCompanyCity.setDateCreatedChar(  );
// 数据修改时间(格式化)
bpsCompanyCity.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 估价公司与城市关联表
BpsCompanyCity bpsCompanyCity = new BpsCompanyCity();

// 数据主键(必填项)(主键ID)
bpsCompanyCity.getId();
// 关联公司(必填项)
bpsCompanyCity.getCompanyCode();
// 关联城市(必填项)
bpsCompanyCity.getCityCode();
// 状态(0不可用，1可用)(必填项)
bpsCompanyCity.getStatus();
// 排序(0为选中录单公司)
bpsCompanyCity.getOrderNo();
// 数据创建时间(必填项)
bpsCompanyCity.getDateCreated();
// 数据修改时间(必填项)
bpsCompanyCity.getDateModified();


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsCompanyCity.getDateCreatedChar();
// 数据修改时间(格式化)
bpsCompanyCity.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 估价公司与城市关联表
BpsCompanyCity bpsCompanyCity = new BpsCompanyCity();

// 数据主键(必填项)(主键ID)
bpsCompanyCity.setId( bpsCompanyCity2.getId() );
// 关联公司(必填项)
bpsCompanyCity.setCompanyCode( bpsCompanyCity2.getCompanyCode() );
// 关联城市(必填项)
bpsCompanyCity.setCityCode( bpsCompanyCity2.getCityCode() );
// 状态(0不可用，1可用)(必填项)
bpsCompanyCity.setStatus( bpsCompanyCity2.getStatus() );
// 排序(0为选中录单公司)
bpsCompanyCity.setOrderNo( bpsCompanyCity2.getOrderNo() );
// 数据创建时间(必填项)
bpsCompanyCity.setDateCreated( bpsCompanyCity2.getDateCreated() );
// 数据修改时间(必填项)
bpsCompanyCity.setDateModified( bpsCompanyCity2.getDateModified() );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsCompanyCity.setDateCreatedChar( bpsCompanyCity2.getDateCreatedChar() );
// 数据修改时间(格式化)
bpsCompanyCity.setDateModifiedChar( bpsCompanyCity2.getDateModifiedChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="36"/>
<!-- 关联公司 -->
<input name="companyCode" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input name="cityCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input name="status" value="" type="text" maxlength="1"/>
<!-- 排序(0为选中录单公司) -->
<input name="orderNo" value="" type="text" maxlength="2"/>
<!-- 数据创建时间 -->
<input name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="bpsCompanyCity.id" value="" type="text" maxlength="36"/>
<!-- 关联公司 -->
<input name="bpsCompanyCity.companyCode" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input name="bpsCompanyCity.cityCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input name="bpsCompanyCity.status" value="" type="text" maxlength="1"/>
<!-- 排序(0为选中录单公司) -->
<input name="bpsCompanyCity.orderNo" value="" type="text" maxlength="2"/>
<!-- 数据创建时间 -->
<input name="bpsCompanyCity.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="bpsCompanyCity.dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BCC_ID" name="id" value="" type="text" maxlength="36"/>
<!-- 关联公司 -->
<input id="BCC_COMPANY_CODE" name="companyCode" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input id="BCC_CITY_CODE" name="cityCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input id="BCC_STATUS" name="status" value="" type="text" maxlength="1"/>
<!-- 排序(0为选中录单公司) -->
<input id="BCC_ORDER_NO" name="orderNo" value="" type="text" maxlength="2"/>
<!-- 数据创建时间 -->
<input id="BCC_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BCC_DATE_MODIFIED" name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BCC_ID" name="bpsCompanyCity.id" value="" type="text" maxlength="36"/>
<!-- 关联公司 -->
<input id="BCC_COMPANY_CODE" name="bpsCompanyCity.companyCode" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input id="BCC_CITY_CODE" name="bpsCompanyCity.cityCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input id="BCC_STATUS" name="bpsCompanyCity.status" value="" type="text" maxlength="1"/>
<!-- 排序(0为选中录单公司) -->
<input id="BCC_ORDER_NO" name="bpsCompanyCity.orderNo" value="" type="text" maxlength="2"/>
<!-- 数据创建时间 -->
<input id="BCC_DATE_CREATED" name="bpsCompanyCity.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BCC_DATE_MODIFIED" name="bpsCompanyCity.dateModified" value="" type="text"/>




--------------------------------------------------------
 */


    