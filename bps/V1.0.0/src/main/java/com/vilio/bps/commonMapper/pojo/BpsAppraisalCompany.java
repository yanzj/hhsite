package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 估价公司表
 * @数表名称 BPS_APPRAISAL_COMPANY
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsAppraisalCompany implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id                     = null;
    /**
     * (必填项)
     */
    private String code                    = null;
    /**
     * 数据创建时间(必填项)
     */
    private Date dateCreated               = null;
    /**
     * 数据修改时间(必填项)
     */
    private Date dateModified              = null;
    /**
     * 估价公司简称
     */
    private String abbrName                = null;
    /**
     * 估价公司全称
     */
    private String fullName                = null;
    /**
     * 估价公司合作类型（0人工；1极速，2极速+人工）(必填项)
     */
    private String cooperationType         = null;
    /**
     * 别墅是否转人工(1转人工)(必填项)
     */
    private Integer villaTurnArtificial    = null;
    /**
     * 最大面积转人工(必填项)
     */
    private Double maxArea                 = null;
    /**
     * 接口API地址(必填项)
     */
    private String apiUrl                  = null;
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    private String status                  = null;
    /**
     * 排序
     */
    private String orderBy                 = null;

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
     * 估价公司简称
     */
    public String getAbbrName() {
        return trim(abbrName);
    }
    /**
     * 估价公司全称
     */
    public String getFullName() {
        return trim(fullName);
    }
    /**
     * 估价公司合作类型（0人工；1极速，2极速+人工）(必填项)
     */
    public String getCooperationType() {
        return trim(cooperationType);
    }
    /**
     * 别墅是否转人工(1转人工)(必填项)
     */
    public Integer getVillaTurnArtificial() {
        return villaTurnArtificial;
    }
    /**
     * 最大面积转人工(必填项)
     */
    public Double getMaxArea() {
        return maxArea;
    }
    /**
     * 接口API地址(必填项)
     */
    public String getApiUrl() {
        return trim(apiUrl);
    }
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    public String getStatus() {
        return trim(status);
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
     * 估价公司简称
     */
    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }
    /**
     * 估价公司全称
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * 估价公司合作类型（0人工；1极速，2极速+人工）(必填项)
     */
    public void setCooperationType(String cooperationType) {
        this.cooperationType = cooperationType;
    }
    /**
     * 别墅是否转人工(1转人工)(必填项)
     */
    public void setVillaTurnArtificial(Integer villaTurnArtificial) {
        this.villaTurnArtificial = villaTurnArtificial;
    }
    /**
     * 最大面积转人工(必填项)
     */
    public void setMaxArea(Double maxArea) {
        this.maxArea = maxArea;
    }
    /**
     * 接口API地址(必填项)
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
    /**
     * 状态(0不可用，1可用)(必填项)
     */
    public void setStatus(String status) {
        this.status = status;
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

// 估价公司表
BpsAppraisalCompany bpsAppraisalCompany = new BpsAppraisalCompany();

// 数据主键(必填项)(主键ID)
bpsAppraisalCompany.setId(  );
// (必填项)
bpsAppraisalCompany.setCode(  );
// 数据创建时间(必填项)
bpsAppraisalCompany.setDateCreated(  );
// 数据修改时间(必填项)
bpsAppraisalCompany.setDateModified(  );
// 估价公司简称
bpsAppraisalCompany.setAbbrName(  );
// 估价公司全称
bpsAppraisalCompany.setFullName(  );
// 估价公司合作类型（0人工；1极速，2极速+人工）(必填项)
bpsAppraisalCompany.setCooperationType(  );
// 别墅是否转人工(1转人工)(必填项)
bpsAppraisalCompany.setVillaTurnArtificial(  );
// 最大面积转人工(必填项)
bpsAppraisalCompany.setMaxArea(  );
// 接口API地址(必填项)
bpsAppraisalCompany.setApiUrl(  );
// 状态(0不可用，1可用)(必填项)
bpsAppraisalCompany.setStatus(  );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsAppraisalCompany.setDateCreatedChar(  );
// 数据修改时间(格式化)
bpsAppraisalCompany.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 估价公司表
BpsAppraisalCompany bpsAppraisalCompany = new BpsAppraisalCompany();

// 数据主键(必填项)(主键ID)
bpsAppraisalCompany.getId();
// (必填项)
bpsAppraisalCompany.getCode();
// 数据创建时间(必填项)
bpsAppraisalCompany.getDateCreated();
// 数据修改时间(必填项)
bpsAppraisalCompany.getDateModified();
// 估价公司简称
bpsAppraisalCompany.getAbbrName();
// 估价公司全称
bpsAppraisalCompany.getFullName();
// 估价公司合作类型（0人工；1极速，2极速+人工）(必填项)
bpsAppraisalCompany.getCooperationType();
// 别墅是否转人工(1转人工)(必填项)
bpsAppraisalCompany.getVillaTurnArtificial();
// 最大面积转人工(必填项)
bpsAppraisalCompany.getMaxArea();
// 接口API地址(必填项)
bpsAppraisalCompany.getApiUrl();
// 状态(0不可用，1可用)(必填项)
bpsAppraisalCompany.getStatus();


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsAppraisalCompany.getDateCreatedChar();
// 数据修改时间(格式化)
bpsAppraisalCompany.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 估价公司表
BpsAppraisalCompany bpsAppraisalCompany = new BpsAppraisalCompany();

// 数据主键(必填项)(主键ID)
bpsAppraisalCompany.setId( bpsAppraisalCompany2.getId() );
// (必填项)
bpsAppraisalCompany.setCode( bpsAppraisalCompany2.getCode() );
// 数据创建时间(必填项)
bpsAppraisalCompany.setDateCreated( bpsAppraisalCompany2.getDateCreated() );
// 数据修改时间(必填项)
bpsAppraisalCompany.setDateModified( bpsAppraisalCompany2.getDateModified() );
// 估价公司简称
bpsAppraisalCompany.setAbbrName( bpsAppraisalCompany2.getAbbrName() );
// 估价公司全称
bpsAppraisalCompany.setFullName( bpsAppraisalCompany2.getFullName() );
// 估价公司合作类型（0人工；1极速，2极速+人工）(必填项)
bpsAppraisalCompany.setCooperationType( bpsAppraisalCompany2.getCooperationType() );
// 别墅是否转人工(1转人工)(必填项)
bpsAppraisalCompany.setVillaTurnArtificial( bpsAppraisalCompany2.getVillaTurnArtificial() );
// 最大面积转人工(必填项)
bpsAppraisalCompany.setMaxArea( bpsAppraisalCompany2.getMaxArea() );
// 接口API地址(必填项)
bpsAppraisalCompany.setApiUrl( bpsAppraisalCompany2.getApiUrl() );
// 状态(0不可用，1可用)(必填项)
bpsAppraisalCompany.setStatus( bpsAppraisalCompany2.getStatus() );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsAppraisalCompany.setDateCreatedChar( bpsAppraisalCompany2.getDateCreatedChar() );
// 数据修改时间(格式化)
bpsAppraisalCompany.setDateModifiedChar( bpsAppraisalCompany2.getDateModifiedChar() );



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
<!-- 数据创建时间 -->
<input name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="dateModified" value="" type="text"/>
<!-- 估价公司简称 -->
<input name="abbrName" value="" type="text" maxlength="20"/>
<!-- 估价公司全称 -->
<input name="fullName" value="" type="text" maxlength="100"/>
<!-- 估价公司合作类型（0人工；1极速，2极速+人工） -->
<input name="cooperationType" value="" type="text" maxlength="1"/>
<!-- 别墅是否转人工(1转人工) -->
<input name="villaTurnArtificial" value="" type="text" maxlength="1"/>
<!-- 最大面积转人工 -->
<input name="maxArea" value="" type="text" maxlength="18"/>
<!-- 接口API地址 -->
<input name="apiUrl" value="" type="text" maxlength="255"/>
<!-- 状态(0不可用，1可用) -->
<input name="status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="bpsAppraisalCompany.id" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsAppraisalCompany.code" value="" type="text" maxlength="36"/>
<!-- 数据创建时间 -->
<input name="bpsAppraisalCompany.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="bpsAppraisalCompany.dateModified" value="" type="text"/>
<!-- 估价公司简称 -->
<input name="bpsAppraisalCompany.abbrName" value="" type="text" maxlength="20"/>
<!-- 估价公司全称 -->
<input name="bpsAppraisalCompany.fullName" value="" type="text" maxlength="100"/>
<!-- 估价公司合作类型（0人工；1极速，2极速+人工） -->
<input name="bpsAppraisalCompany.cooperationType" value="" type="text" maxlength="1"/>
<!-- 别墅是否转人工(1转人工) -->
<input name="bpsAppraisalCompany.villaTurnArtificial" value="" type="text" maxlength="1"/>
<!-- 最大面积转人工 -->
<input name="bpsAppraisalCompany.maxArea" value="" type="text" maxlength="18"/>
<!-- 接口API地址 -->
<input name="bpsAppraisalCompany.apiUrl" value="" type="text" maxlength="255"/>
<!-- 状态(0不可用，1可用) -->
<input name="bpsAppraisalCompany.status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BAC_ID" name="id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BAC_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- 数据创建时间 -->
<input id="BAC_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BAC_DATE_MODIFIED" name="dateModified" value="" type="text"/>
<!-- 估价公司简称 -->
<input id="BAC_ABBR_NAME" name="abbrName" value="" type="text" maxlength="20"/>
<!-- 估价公司全称 -->
<input id="BAC_FULL_NAME" name="fullName" value="" type="text" maxlength="100"/>
<!-- 估价公司合作类型（0人工；1极速，2极速+人工） -->
<input id="BAC_COOPERATION_TYPE" name="cooperationType" value="" type="text" maxlength="1"/>
<!-- 别墅是否转人工(1转人工) -->
<input id="BAC_VILLA_TURN_ARTIFICIAL" name="villaTurnArtificial" value="" type="text" maxlength="1"/>
<!-- 最大面积转人工 -->
<input id="BAC_MAX_AREA" name="maxArea" value="" type="text" maxlength="18"/>
<!-- 接口API地址 -->
<input id="BAC_API_URL" name="apiUrl" value="" type="text" maxlength="255"/>
<!-- 状态(0不可用，1可用) -->
<input id="BAC_STATUS" name="status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BAC_ID" name="bpsAppraisalCompany.id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BAC_CODE" name="bpsAppraisalCompany.code" value="" type="text" maxlength="36"/>
<!-- 数据创建时间 -->
<input id="BAC_DATE_CREATED" name="bpsAppraisalCompany.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BAC_DATE_MODIFIED" name="bpsAppraisalCompany.dateModified" value="" type="text"/>
<!-- 估价公司简称 -->
<input id="BAC_ABBR_NAME" name="bpsAppraisalCompany.abbrName" value="" type="text" maxlength="20"/>
<!-- 估价公司全称 -->
<input id="BAC_FULL_NAME" name="bpsAppraisalCompany.fullName" value="" type="text" maxlength="100"/>
<!-- 估价公司合作类型（0人工；1极速，2极速+人工） -->
<input id="BAC_COOPERATION_TYPE" name="bpsAppraisalCompany.cooperationType" value="" type="text" maxlength="1"/>
<!-- 别墅是否转人工(1转人工) -->
<input id="BAC_VILLA_TURN_ARTIFICIAL" name="bpsAppraisalCompany.villaTurnArtificial" value="" type="text" maxlength="1"/>
<!-- 最大面积转人工 -->
<input id="BAC_MAX_AREA" name="bpsAppraisalCompany.maxArea" value="" type="text" maxlength="18"/>
<!-- 接口API地址 -->
<input id="BAC_API_URL" name="bpsAppraisalCompany.apiUrl" value="" type="text" maxlength="255"/>
<!-- 状态(0不可用，1可用) -->
<input id="BAC_STATUS" name="bpsAppraisalCompany.status" value="" type="text" maxlength="1"/>




--------------------------------------------------------
 */


    