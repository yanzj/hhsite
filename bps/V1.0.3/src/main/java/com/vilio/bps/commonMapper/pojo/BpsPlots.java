package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 小区表
 * @数表名称 BPS_PLOTS
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsPlots implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id            = null;
    /**
     * 维护询价公司小区code(必填项)
     */
    private String code           = null;
    /**
     * 维护询价公司小区code
     */
    private String plotCode       = null;
    /**
     * 小区名称
     */
    private String name           = null;
    /**
     * 询价公司编号
     */
    private String companyCode    = null;
    /**
     * 关联城市(必填项)
     */
    private String cityCode       = null;
    /**
     * 小区地址
     */
    private String address        = null;
    /**
     * 建成年代
     */
    private String yearBuilt      = null;
    /**
     * 房屋类型(别墅，普通住宅，商业住宅)
     */
    private String houseType      = null;
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
    public Integer getId() {
        return id;
    }
    /**
     * 维护询价公司小区code(必填项)
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 维护询价公司小区code
     */
    public String getPlotCode() {
        return trim(plotCode);
    }
    /**
     * 小区名称
     */
    public String getName() {
        return trim(name);
    }
    /**
     * 询价公司编号
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
     * 小区地址
     */
    public String getAddress() {
        return trim(address);
    }
    /**
     * 建成年代
     */
    public String getYearBuilt() {
        return trim(yearBuilt);
    }
    /**
     * 房屋类型(别墅，普通住宅，商业住宅)
     */
    public String getHouseType() {
        return trim(houseType);
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
     * 维护询价公司小区code(必填项)
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 维护询价公司小区code
     */
    public void setPlotCode(String plotCode) {
        this.plotCode = plotCode;
    }
    /**
     * 小区名称
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 询价公司编号
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
     * 小区地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * 建成年代
     */
    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = yearBuilt;
    }
    /**
     * 房屋类型(别墅，普通住宅，商业住宅)
     */
    public void setHouseType(String houseType) {
        this.houseType = houseType;
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

// 小区表
BpsPlots bpsPlots = new BpsPlots();

// 数据主键(必填项)(主键ID)
bpsPlots.setId(  );
// 维护询价公司小区code(必填项)
bpsPlots.setCode(  );
// 维护询价公司小区code
bpsPlots.setPlotCode(  );
// 小区名称
bpsPlots.setName(  );
// 询价公司编号
bpsPlots.setCompanyCode(  );
// 关联城市(必填项)
bpsPlots.setCityCode(  );
// 小区地址
bpsPlots.setAddress(  );
// 建成年代
bpsPlots.setYearBuilt(  );
// 房屋类型(别墅，普通住宅，商业住宅)
bpsPlots.setHouseType(  );
// 状态(0不可用，1可用)(必填项)
bpsPlots.setStatus(  );
// 数据创建时间(必填项)
bpsPlots.setDateCreated(  );
// 数据修改时间(必填项)
bpsPlots.setDateModified(  );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsPlots.setDateCreatedChar(  );
// 数据修改时间(格式化)
bpsPlots.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 小区表
BpsPlots bpsPlots = new BpsPlots();

// 数据主键(必填项)(主键ID)
bpsPlots.getId();
// 维护询价公司小区code(必填项)
bpsPlots.getCode();
// 维护询价公司小区code
bpsPlots.getPlotCode();
// 小区名称
bpsPlots.getName();
// 询价公司编号
bpsPlots.getCompanyCode();
// 关联城市(必填项)
bpsPlots.getCityCode();
// 小区地址
bpsPlots.getAddress();
// 建成年代
bpsPlots.getYearBuilt();
// 房屋类型(别墅，普通住宅，商业住宅)
bpsPlots.getHouseType();
// 状态(0不可用，1可用)(必填项)
bpsPlots.getStatus();
// 数据创建时间(必填项)
bpsPlots.getDateCreated();
// 数据修改时间(必填项)
bpsPlots.getDateModified();


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsPlots.getDateCreatedChar();
// 数据修改时间(格式化)
bpsPlots.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 小区表
BpsPlots bpsPlots = new BpsPlots();

// 数据主键(必填项)(主键ID)
bpsPlots.setId( bpsPlots2.getId() );
// 维护询价公司小区code(必填项)
bpsPlots.setCode( bpsPlots2.getCode() );
// 维护询价公司小区code
bpsPlots.setPlotCode( bpsPlots2.getPlotCode() );
// 小区名称
bpsPlots.setName( bpsPlots2.getName() );
// 询价公司编号
bpsPlots.setCompanyCode( bpsPlots2.getCompanyCode() );
// 关联城市(必填项)
bpsPlots.setCityCode( bpsPlots2.getCityCode() );
// 小区地址
bpsPlots.setAddress( bpsPlots2.getAddress() );
// 建成年代
bpsPlots.setYearBuilt( bpsPlots2.getYearBuilt() );
// 房屋类型(别墅，普通住宅，商业住宅)
bpsPlots.setHouseType( bpsPlots2.getHouseType() );
// 状态(0不可用，1可用)(必填项)
bpsPlots.setStatus( bpsPlots2.getStatus() );
// 数据创建时间(必填项)
bpsPlots.setDateCreated( bpsPlots2.getDateCreated() );
// 数据修改时间(必填项)
bpsPlots.setDateModified( bpsPlots2.getDateModified() );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsPlots.setDateCreatedChar( bpsPlots2.getDateCreatedChar() );
// 数据修改时间(格式化)
bpsPlots.setDateModifiedChar( bpsPlots2.getDateModifiedChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="36"/>
<!-- 维护询价公司小区code -->
<input name="code" value="" type="text" maxlength="36"/>
<!-- 维护询价公司小区code -->
<input name="plotCode" value="" type="text" maxlength="36"/>
<!-- 小区名称 -->
<input name="name" value="" type="text" maxlength="50"/>
<!-- 询价公司编号 -->
<input name="companyCode" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input name="cityCode" value="" type="text" maxlength="36"/>
<!-- 小区地址 -->
<input name="address" value="" type="text" maxlength="100"/>
<!-- 建成年代 -->
<input name="yearBuilt" value="" type="text" maxlength="25"/>
<!-- 房屋类型(别墅，普通住宅，商业住宅) -->
<input name="houseType" value="" type="text" maxlength="20"/>
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
<input name="bpsPlots.id" value="" type="text" maxlength="36"/>
<!-- 维护询价公司小区code -->
<input name="bpsPlots.code" value="" type="text" maxlength="36"/>
<!-- 维护询价公司小区code -->
<input name="bpsPlots.plotCode" value="" type="text" maxlength="36"/>
<!-- 小区名称 -->
<input name="bpsPlots.name" value="" type="text" maxlength="50"/>
<!-- 询价公司编号 -->
<input name="bpsPlots.companyCode" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input name="bpsPlots.cityCode" value="" type="text" maxlength="36"/>
<!-- 小区地址 -->
<input name="bpsPlots.address" value="" type="text" maxlength="100"/>
<!-- 建成年代 -->
<input name="bpsPlots.yearBuilt" value="" type="text" maxlength="25"/>
<!-- 房屋类型(别墅，普通住宅，商业住宅) -->
<input name="bpsPlots.houseType" value="" type="text" maxlength="20"/>
<!-- 状态(0不可用，1可用) -->
<input name="bpsPlots.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="bpsPlots.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="bpsPlots.dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BP_ID" name="id" value="" type="text" maxlength="36"/>
<!-- 维护询价公司小区code -->
<input id="BP_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- 维护询价公司小区code -->
<input id="BP_PLOT_CODE" name="plotCode" value="" type="text" maxlength="36"/>
<!-- 小区名称 -->
<input id="BP_NAME" name="name" value="" type="text" maxlength="50"/>
<!-- 询价公司编号 -->
<input id="BP_COMPANY_CODE" name="companyCode" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input id="BP_CITY_CODE" name="cityCode" value="" type="text" maxlength="36"/>
<!-- 小区地址 -->
<input id="BP_ADDRESS" name="address" value="" type="text" maxlength="100"/>
<!-- 建成年代 -->
<input id="BP_YEAR_BUILT" name="yearBuilt" value="" type="text" maxlength="25"/>
<!-- 房屋类型(别墅，普通住宅，商业住宅) -->
<input id="BP_HOUSE_TYPE" name="houseType" value="" type="text" maxlength="20"/>
<!-- 状态(0不可用，1可用) -->
<input id="BP_STATUS" name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BP_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BP_DATE_MODIFIED" name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BP_ID" name="bpsPlots.id" value="" type="text" maxlength="36"/>
<!-- 维护询价公司小区code -->
<input id="BP_CODE" name="bpsPlots.code" value="" type="text" maxlength="36"/>
<!-- 维护询价公司小区code -->
<input id="BP_PLOT_CODE" name="bpsPlots.plotCode" value="" type="text" maxlength="36"/>
<!-- 小区名称 -->
<input id="BP_NAME" name="bpsPlots.name" value="" type="text" maxlength="50"/>
<!-- 询价公司编号 -->
<input id="BP_COMPANY_CODE" name="bpsPlots.companyCode" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input id="BP_CITY_CODE" name="bpsPlots.cityCode" value="" type="text" maxlength="36"/>
<!-- 小区地址 -->
<input id="BP_ADDRESS" name="bpsPlots.address" value="" type="text" maxlength="100"/>
<!-- 建成年代 -->
<input id="BP_YEAR_BUILT" name="bpsPlots.yearBuilt" value="" type="text" maxlength="25"/>
<!-- 房屋类型(别墅，普通住宅，商业住宅) -->
<input id="BP_HOUSE_TYPE" name="bpsPlots.houseType" value="" type="text" maxlength="20"/>
<!-- 状态(0不可用，1可用) -->
<input id="BP_STATUS" name="bpsPlots.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BP_DATE_CREATED" name="bpsPlots.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BP_DATE_MODIFIED" name="bpsPlots.dateModified" value="" type="text"/>




--------------------------------------------------------
 */


    