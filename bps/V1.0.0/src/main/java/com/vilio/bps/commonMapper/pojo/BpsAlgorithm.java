package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 算法表
 * @数表名称 BPS_ALGORITHM
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsAlgorithm implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id            = null;
    /**
     * (必填项)
     */
    private String code           = null;
    /**
     * 算法名称(必填项)
     */
    private String name           = null;
    /**
     * 算法描述
     */
    private String description    = null;
    /**
     * 状态(1可用，0不可用)(必填项)
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
     * 算法名称(必填项)
     */
    public String getName() {
        return trim(name);
    }
    /**
     * 算法描述
     */
    public String getDescription() {
        return trim(description);
    }
    /**
     * 状态(1可用，0不可用)(必填项)
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
     * (必填项)
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 算法名称(必填项)
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 算法描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * 状态(1可用，0不可用)(必填项)
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

// 算法表
BpsAlgorithm bpsAlgorithm = new BpsAlgorithm();

// 数据主键(必填项)(主键ID)
bpsAlgorithm.setId(  );
// (必填项)
bpsAlgorithm.setCode(  );
// 算法名称(必填项)
bpsAlgorithm.setName(  );
// 算法描述
bpsAlgorithm.setDescription(  );
// 状态(1可用，0不可用)(必填项)
bpsAlgorithm.setStatus(  );
// 数据创建时间(必填项)
bpsAlgorithm.setDateCreated(  );
// 数据修改时间(必填项)
bpsAlgorithm.setDateModified(  );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsAlgorithm.setDateCreatedChar(  );
// 数据修改时间(格式化)
bpsAlgorithm.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 算法表
BpsAlgorithm bpsAlgorithm = new BpsAlgorithm();

// 数据主键(必填项)(主键ID)
bpsAlgorithm.getId();
// (必填项)
bpsAlgorithm.getCode();
// 算法名称(必填项)
bpsAlgorithm.getName();
// 算法描述
bpsAlgorithm.getDescription();
// 状态(1可用，0不可用)(必填项)
bpsAlgorithm.getStatus();
// 数据创建时间(必填项)
bpsAlgorithm.getDateCreated();
// 数据修改时间(必填项)
bpsAlgorithm.getDateModified();


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsAlgorithm.getDateCreatedChar();
// 数据修改时间(格式化)
bpsAlgorithm.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 算法表
BpsAlgorithm bpsAlgorithm = new BpsAlgorithm();

// 数据主键(必填项)(主键ID)
bpsAlgorithm.setId( bpsAlgorithm2.getId() );
// (必填项)
bpsAlgorithm.setCode( bpsAlgorithm2.getCode() );
// 算法名称(必填项)
bpsAlgorithm.setName( bpsAlgorithm2.getName() );
// 算法描述
bpsAlgorithm.setDescription( bpsAlgorithm2.getDescription() );
// 状态(1可用，0不可用)(必填项)
bpsAlgorithm.setStatus( bpsAlgorithm2.getStatus() );
// 数据创建时间(必填项)
bpsAlgorithm.setDateCreated( bpsAlgorithm2.getDateCreated() );
// 数据修改时间(必填项)
bpsAlgorithm.setDateModified( bpsAlgorithm2.getDateModified() );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsAlgorithm.setDateCreatedChar( bpsAlgorithm2.getDateCreatedChar() );
// 数据修改时间(格式化)
bpsAlgorithm.setDateModifiedChar( bpsAlgorithm2.getDateModifiedChar() );



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
<!-- 算法名称 -->
<input name="name" value="" type="text" maxlength="50"/>
<!-- 算法描述 -->
<input name="description" value="" type="text" maxlength="100"/>
<!-- 状态(1可用，0不可用) -->
<input name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="bpsAlgorithm.id" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsAlgorithm.code" value="" type="text" maxlength="36"/>
<!-- 算法名称 -->
<input name="bpsAlgorithm.name" value="" type="text" maxlength="50"/>
<!-- 算法描述 -->
<input name="bpsAlgorithm.description" value="" type="text" maxlength="100"/>
<!-- 状态(1可用，0不可用) -->
<input name="bpsAlgorithm.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="bpsAlgorithm.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="bpsAlgorithm.dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BA_ID" name="id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BA_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- 算法名称 -->
<input id="BA_NAME" name="name" value="" type="text" maxlength="50"/>
<!-- 算法描述 -->
<input id="BA_DESCRIPTION" name="description" value="" type="text" maxlength="100"/>
<!-- 状态(1可用，0不可用) -->
<input id="BA_STATUS" name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BA_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BA_DATE_MODIFIED" name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BA_ID" name="bpsAlgorithm.id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BA_CODE" name="bpsAlgorithm.code" value="" type="text" maxlength="36"/>
<!-- 算法名称 -->
<input id="BA_NAME" name="bpsAlgorithm.name" value="" type="text" maxlength="50"/>
<!-- 算法描述 -->
<input id="BA_DESCRIPTION" name="bpsAlgorithm.description" value="" type="text" maxlength="100"/>
<!-- 状态(1可用，0不可用) -->
<input id="BA_STATUS" name="bpsAlgorithm.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BA_DATE_CREATED" name="bpsAlgorithm.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BA_DATE_MODIFIED" name="bpsAlgorithm.dateModified" value="" type="text"/>




--------------------------------------------------------
 */


    