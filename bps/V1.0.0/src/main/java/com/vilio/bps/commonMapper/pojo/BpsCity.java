package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 城市表
 * @数表名称 BPS_CITY
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsCity implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id           = null;
    /**
     * 国标代码(必填项)
     */
    private String code          = null;
    /**
     * 城市简称(必填项)
     */
    private String abbrName      = null;
    /**
     * 城市全称(必填项)
     */
    private String fullName      = null;
    /**
     * 世联id
     */
    private String wuCode        = null;
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    private String status        = null;
    /**
     * 数据创建时间(必填项)
     */
    private Date dateCreated     = null;
    /**
     * 数据修改时间(必填项)
     */
    private Date dateModified    = null;
    /**
     * 排序
     */
    private String orderBy       = null;

    /*
     *--------------------------------------------------
     * Getter方法区
     *--------------------------------------------------
     */

    /**
     * 数据主键(必填项)(主键ID)
     */
    public Integer getId() {
        return id;
    }
    /**
     * 国标代码(必填项)
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 城市简称(必填项)
     */
    public String getAbbrName() {
        return trim(abbrName);
    }
    /**
     * 城市全称(必填项)
     */
    public String getFullName() {
        return trim(fullName);
    }
    /**
     * 世联id
     */
    public String getWuCode() {
        return trim(wuCode);
    }
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    public String getStatus() {
        return trim(status);
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
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 国标代码(必填项)
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 城市简称(必填项)
     */
    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }
    /**
     * 城市全称(必填项)
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * 世联id
     */
    public void setWuCode(String wuCode) {
        this.wuCode = wuCode;
    }
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    public void setStatus(String status) {
        this.status = status;
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

// 城市表
BpsCity bpsCity = new BpsCity();

// 数据主键(必填项)(主键ID)
bpsCity.setId(  );
// 国标代码(必填项)
bpsCity.setCode(  );
// 城市简称(必填项)
bpsCity.setAbbrName(  );
// 城市全称(必填项)
bpsCity.setFullName(  );
// 世联id
bpsCity.setWuCode(  );
// 状态(0不可用，1可用)(必填项)
bpsCity.setStatus(  );
// 数据创建时间(必填项)
bpsCity.setDateCreated(  );
// 数据修改时间(必填项)
bpsCity.setDateModified(  );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsCity.setDateCreatedChar(  );
// 数据修改时间(格式化)
bpsCity.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 城市表
BpsCity bpsCity = new BpsCity();

// 数据主键(必填项)(主键ID)
bpsCity.getId();
// 国标代码(必填项)
bpsCity.getCode();
// 城市简称(必填项)
bpsCity.getAbbrName();
// 城市全称(必填项)
bpsCity.getFullName();
// 世联id
bpsCity.getWuCode();
// 状态(0不可用，1可用)(必填项)
bpsCity.getStatus();
// 数据创建时间(必填项)
bpsCity.getDateCreated();
// 数据修改时间(必填项)
bpsCity.getDateModified();


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsCity.getDateCreatedChar();
// 数据修改时间(格式化)
bpsCity.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 城市表
BpsCity bpsCity = new BpsCity();

// 数据主键(必填项)(主键ID)
bpsCity.setId( bpsCity2.getId() );
// 国标代码(必填项)
bpsCity.setCode( bpsCity2.getCode() );
// 城市简称(必填项)
bpsCity.setAbbrName( bpsCity2.getAbbrName() );
// 城市全称(必填项)
bpsCity.setFullName( bpsCity2.getFullName() );
// 世联id
bpsCity.setWuCode( bpsCity2.getWuCode() );
// 状态(0不可用，1可用)(必填项)
bpsCity.setStatus( bpsCity2.getStatus() );
// 数据创建时间(必填项)
bpsCity.setDateCreated( bpsCity2.getDateCreated() );
// 数据修改时间(必填项)
bpsCity.setDateModified( bpsCity2.getDateModified() );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsCity.setDateCreatedChar( bpsCity2.getDateCreatedChar() );
// 数据修改时间(格式化)
bpsCity.setDateModifiedChar( bpsCity2.getDateModifiedChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="36"/>
<!-- 国标代码 -->
<input name="code" value="" type="text" maxlength="10"/>
<!-- 城市简称 -->
<input name="abbrName" value="" type="text" maxlength="20"/>
<!-- 城市全称 -->
<input name="fullName" value="" type="text" maxlength="100"/>
<!-- 世联id -->
<input name="wuCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="bpsCity.id" value="" type="text" maxlength="36"/>
<!-- 国标代码 -->
<input name="bpsCity.code" value="" type="text" maxlength="10"/>
<!-- 城市简称 -->
<input name="bpsCity.abbrName" value="" type="text" maxlength="20"/>
<!-- 城市全称 -->
<input name="bpsCity.fullName" value="" type="text" maxlength="100"/>
<!-- 世联id -->
<input name="bpsCity.wuCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input name="bpsCity.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="bpsCity.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="bpsCity.dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BC_ID" name="id" value="" type="text" maxlength="36"/>
<!-- 国标代码 -->
<input id="BC_CODE" name="code" value="" type="text" maxlength="10"/>
<!-- 城市简称 -->
<input id="BC_ABBR_NAME" name="abbrName" value="" type="text" maxlength="20"/>
<!-- 城市全称 -->
<input id="BC_FULL_NAME" name="fullName" value="" type="text" maxlength="100"/>
<!-- 世联id -->
<input id="BC_WU_CODE" name="wuCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input id="BC_STATUS" name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BC_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BC_DATE_MODIFIED" name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BC_ID" name="bpsCity.id" value="" type="text" maxlength="36"/>
<!-- 国标代码 -->
<input id="BC_CODE" name="bpsCity.code" value="" type="text" maxlength="10"/>
<!-- 城市简称 -->
<input id="BC_ABBR_NAME" name="bpsCity.abbrName" value="" type="text" maxlength="20"/>
<!-- 城市全称 -->
<input id="BC_FULL_NAME" name="bpsCity.fullName" value="" type="text" maxlength="100"/>
<!-- 世联id -->
<input id="BC_WU_CODE" name="bpsCity.wuCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input id="BC_STATUS" name="bpsCity.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BC_DATE_CREATED" name="bpsCity.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BC_DATE_MODIFIED" name="bpsCity.dateModified" value="" type="text"/>




--------------------------------------------------------
 */


    