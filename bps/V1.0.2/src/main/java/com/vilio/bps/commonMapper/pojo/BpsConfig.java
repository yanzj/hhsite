package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 系统配置表
 * @数表名称 BPS_CONFIG
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsConfig implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private String id             = null;
    /**
     * 配置名称(必填项)
     */
    private String configName     = null;
    /**
     * 配置值(必填项)
     */
    private String configValue    = null;
    /**
     * 配置描述
     */
    private String description    = null;
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    private String status         = null;
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
     * 配置名称(必填项)
     */
    public String getConfigName() {
        return trim(configName);
    }
    /**
     * 配置值(必填项)
     */
    public String getConfigValue() {
        return trim(configValue);
    }
    /**
     * 配置描述
     */
    public String getDescription() {
        return trim(description);
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
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 配置名称(必填项)
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }
    /**
     * 配置值(必填项)
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    /**
     * 配置描述
     */
    public void setDescription(String description) {
        this.description = description;
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

// 系统配置表
BpsConfig bpsConfig = new BpsConfig();

// 数据主键(必填项)(主键ID)
bpsConfig.setId(  );
// 配置名称(必填项)
bpsConfig.setConfigName(  );
// 配置值(必填项)
bpsConfig.setConfigValue(  );
// 配置描述
bpsConfig.setDescription(  );
// 状态(0不可用，1可用)(必填项)
bpsConfig.setStatus(  );
// 数据创建时间(必填项)
bpsConfig.setDateCreated(  );
// 数据修改时间(必填项)
bpsConfig.setDateModified(  );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsConfig.setDateCreatedChar(  );
// 数据修改时间(格式化)
bpsConfig.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 系统配置表
BpsConfig bpsConfig = new BpsConfig();

// 数据主键(必填项)(主键ID)
bpsConfig.getId();
// 配置名称(必填项)
bpsConfig.getConfigName();
// 配置值(必填项)
bpsConfig.getConfigValue();
// 配置描述
bpsConfig.getDescription();
// 状态(0不可用，1可用)(必填项)
bpsConfig.getStatus();
// 数据创建时间(必填项)
bpsConfig.getDateCreated();
// 数据修改时间(必填项)
bpsConfig.getDateModified();


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsConfig.getDateCreatedChar();
// 数据修改时间(格式化)
bpsConfig.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 系统配置表
BpsConfig bpsConfig = new BpsConfig();

// 数据主键(必填项)(主键ID)
bpsConfig.setId( bpsConfig2.getId() );
// 配置名称(必填项)
bpsConfig.setConfigName( bpsConfig2.getConfigName() );
// 配置值(必填项)
bpsConfig.setConfigValue( bpsConfig2.getConfigValue() );
// 配置描述
bpsConfig.setDescription( bpsConfig2.getDescription() );
// 状态(0不可用，1可用)(必填项)
bpsConfig.setStatus( bpsConfig2.getStatus() );
// 数据创建时间(必填项)
bpsConfig.setDateCreated( bpsConfig2.getDateCreated() );
// 数据修改时间(必填项)
bpsConfig.setDateModified( bpsConfig2.getDateModified() );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsConfig.setDateCreatedChar( bpsConfig2.getDateCreatedChar() );
// 数据修改时间(格式化)
bpsConfig.setDateModifiedChar( bpsConfig2.getDateModifiedChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="36"/>
<!-- 配置名称 -->
<input name="configName" value="" type="text" maxlength="50"/>
<!-- 配置值 -->
<input name="configValue" value="" type="text" maxlength="50"/>
<!-- 配置描述 -->
<input name="description" value="" type="text" maxlength="100"/>
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
<input name="bpsConfig.id" value="" type="text" maxlength="36"/>
<!-- 配置名称 -->
<input name="bpsConfig.configName" value="" type="text" maxlength="50"/>
<!-- 配置值 -->
<input name="bpsConfig.configValue" value="" type="text" maxlength="50"/>
<!-- 配置描述 -->
<input name="bpsConfig.description" value="" type="text" maxlength="100"/>
<!-- 状态(0不可用，1可用) -->
<input name="bpsConfig.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="bpsConfig.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="bpsConfig.dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BC_ID" name="id" value="" type="text" maxlength="36"/>
<!-- 配置名称 -->
<input id="BC_CONFIG_NAME" name="configName" value="" type="text" maxlength="50"/>
<!-- 配置值 -->
<input id="BC_CONFIG_VALUE" name="configValue" value="" type="text" maxlength="50"/>
<!-- 配置描述 -->
<input id="BC_DESCRIPTION" name="description" value="" type="text" maxlength="100"/>
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
<input id="BC_ID" name="bpsConfig.id" value="" type="text" maxlength="36"/>
<!-- 配置名称 -->
<input id="BC_CONFIG_NAME" name="bpsConfig.configName" value="" type="text" maxlength="50"/>
<!-- 配置值 -->
<input id="BC_CONFIG_VALUE" name="bpsConfig.configValue" value="" type="text" maxlength="50"/>
<!-- 配置描述 -->
<input id="BC_DESCRIPTION" name="bpsConfig.description" value="" type="text" maxlength="100"/>
<!-- 状态(0不可用，1可用) -->
<input id="BC_STATUS" name="bpsConfig.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BC_DATE_CREATED" name="bpsConfig.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BC_DATE_MODIFIED" name="bpsConfig.dateModified" value="" type="text"/>




--------------------------------------------------------
 */


    