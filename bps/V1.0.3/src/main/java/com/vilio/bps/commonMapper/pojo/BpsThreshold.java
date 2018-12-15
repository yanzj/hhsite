package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 时间阈值表
 * @数表名称 BPS_THRESHOLD
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsThreshold implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id           = null;
    /**
     * (必填项)
     */
    private String code          = null;
    /**
     * 时间阈值(必填项)
     */
    private Integer value        = null;
    /**
     * 关联城市(必填项)
     */
    private String cityCode      = null;
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    private String status        = null;
    /**
     * 数据创建时间(必填项)
     */
    private Date dateCreated     = null;
    /**
     * 数据更新时间(必填项)
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
     * (必填项)
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 时间阈值(必填项)
     */
    public Integer getValue() {
        return value;
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
     * 数据创建时间(必填项)
     */
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * 数据更新时间(必填项)
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
     * (必填项)
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 时间阈值(必填项)
     */
    public void setValue(Integer value) {
        this.value = value;
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
     * 数据创建时间(必填项)
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    /**
     * 数据更新时间(必填项)
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
     * 数据更新时间(格式化)
     */
    public String getDateModifiedChar() {
        return getDate(dateModified);
    }
    public void setDateModifiedChar(String dateModifiedChar) {
        this.dateModified = getDate(dateModifiedChar);
    }
    /**
     * 数据更新时间(格式化)
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

// 时间阈值表
BpsThreshold bpsThreshold = new BpsThreshold();

// 数据主键(必填项)(主键ID)
bpsThreshold.setId(  );
// (必填项)
bpsThreshold.setCode(  );
// 时间阈值(必填项)
bpsThreshold.setValue(  );
// 关联城市(必填项)
bpsThreshold.setCityCode(  );
// 状态(0不可用，1可用)(必填项)
bpsThreshold.setStatus(  );
// 数据创建时间(必填项)
bpsThreshold.setDateCreated(  );
// 数据更新时间(必填项)
bpsThreshold.setDateModified(  );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsThreshold.setDateCreatedChar(  );
// 数据更新时间(格式化)
bpsThreshold.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 时间阈值表
BpsThreshold bpsThreshold = new BpsThreshold();

// 数据主键(必填项)(主键ID)
bpsThreshold.getId();
// (必填项)
bpsThreshold.getCode();
// 时间阈值(必填项)
bpsThreshold.getValue();
// 关联城市(必填项)
bpsThreshold.getCityCode();
// 状态(0不可用，1可用)(必填项)
bpsThreshold.getStatus();
// 数据创建时间(必填项)
bpsThreshold.getDateCreated();
// 数据更新时间(必填项)
bpsThreshold.getDateModified();


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsThreshold.getDateCreatedChar();
// 数据更新时间(格式化)
bpsThreshold.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 时间阈值表
BpsThreshold bpsThreshold = new BpsThreshold();

// 数据主键(必填项)(主键ID)
bpsThreshold.setId( bpsThreshold2.getId() );
// (必填项)
bpsThreshold.setCode( bpsThreshold2.getCode() );
// 时间阈值(必填项)
bpsThreshold.setValue( bpsThreshold2.getValue() );
// 关联城市(必填项)
bpsThreshold.setCityCode( bpsThreshold2.getCityCode() );
// 状态(0不可用，1可用)(必填项)
bpsThreshold.setStatus( bpsThreshold2.getStatus() );
// 数据创建时间(必填项)
bpsThreshold.setDateCreated( bpsThreshold2.getDateCreated() );
// 数据更新时间(必填项)
bpsThreshold.setDateModified( bpsThreshold2.getDateModified() );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsThreshold.setDateCreatedChar( bpsThreshold2.getDateCreatedChar() );
// 数据更新时间(格式化)
bpsThreshold.setDateModifiedChar( bpsThreshold2.getDateModifiedChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="36"/>
<!--  -->
<input name="code" value="" type="text" maxlength="36"/>
<!-- 时间阈值 -->
<input name="value" value="" type="text" maxlength="5"/>
<!-- 关联城市 -->
<input name="cityCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="dateCreated" value="" type="text"/>
<!-- 数据更新时间 -->
<input name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="bpsThreshold.id" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsThreshold.code" value="" type="text" maxlength="36"/>
<!-- 时间阈值 -->
<input name="bpsThreshold.value" value="" type="text" maxlength="5"/>
<!-- 关联城市 -->
<input name="bpsThreshold.cityCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input name="bpsThreshold.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="bpsThreshold.dateCreated" value="" type="text"/>
<!-- 数据更新时间 -->
<input name="bpsThreshold.dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BT_ID" name="id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BT_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- 时间阈值 -->
<input id="BT_VALUE" name="value" value="" type="text" maxlength="5"/>
<!-- 关联城市 -->
<input id="BT_CITY_CODE" name="cityCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input id="BT_STATUS" name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BT_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 数据更新时间 -->
<input id="BT_DATE_MODIFIED" name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BT_ID" name="bpsThreshold.id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BT_CODE" name="bpsThreshold.code" value="" type="text" maxlength="36"/>
<!-- 时间阈值 -->
<input id="BT_VALUE" name="bpsThreshold.value" value="" type="text" maxlength="5"/>
<!-- 关联城市 -->
<input id="BT_CITY_CODE" name="bpsThreshold.cityCode" value="" type="text" maxlength="36"/>
<!-- 状态(0不可用，1可用) -->
<input id="BT_STATUS" name="bpsThreshold.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BT_DATE_CREATED" name="bpsThreshold.dateCreated" value="" type="text"/>
<!-- 数据更新时间 -->
<input id="BT_DATE_MODIFIED" name="bpsThreshold.dateModified" value="" type="text"/>




--------------------------------------------------------
 */


    