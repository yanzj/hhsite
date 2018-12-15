package com.vilio.nlbs.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.*;


/**
 * @实体名称 用户申请房屋询价记录表
 * @数表名称 NLBS_INQUIRY_APPLY
 * @开发日期 2017-06-15
 * @技术服务 www.fwjava.com
 */
public class NlbsInquiryApply implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id              = null;
    /**
     * 请求编号(必填项)
     */
    private String code             = null;
    /**
     * 询价系统序列号
     */
    private String bpsCode          = null;
    /**
     * 申请询价时间
     */
    private Date dateCreated        = null;
    /**
     * 数据修改时间
     */
    private Date dateModified        = null;
    /**
     * 询价用户
     */
    private String userNo           = null;
    /**
     * 待处理人
     */
    private String pendingUserNo    = null;
    /**
     * 询价的房子所在城市
     */
    private String cityCode         = null;
    /**
     * 房屋类型
     */
    private String houseType        = null;
    /**
     * 房屋地址
     */
    private String address          = null;
    /**
     * 房屋面积
     */
    private BigDecimal area         = null;
    /**
     * 评估价格
     */
    private BigDecimal price        = null;
    /**
     * 评估时间
     */
    private Date priceTime          = null;
    /**
     * 是否自动询价
     */
    private String autoPrice        = null;
    /**
     * 失效时间
     */
    private Date deadLine           = null;
    /**
     * 状态(00待评估，01评估中，02已评估，99失效)
     */
    private String status           = null;
    /**
     * 排序
     */
    private String orderBy          = null;

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
     * 请求编号(必填项)
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 询价系统序列号
     */
    public String getBpsCode() {
        return trim(bpsCode);
    }
    /**
     * 申请询价时间
     */
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * 数据修改时间
     */
    public Date getDateModified() {
        return dateModified;
    }
    /**
     * 询价用户
     */
    public String getUserNo() {
        return trim(userNo);
    }
    /**
     * 待处理人
     */
    public String getPendingUserNo() {
        return trim(pendingUserNo);
    }
    /**
     * 询价的房子所在城市
     */
    public String getCityCode() {
        return trim(cityCode);
    }
    /**
     * 房屋类型
     */
    public String getHouseType() {
        return trim(houseType);
    }
    /**
     * 房屋地址
     */
    public String getAddress() {
        return trim(address);
    }
    /**
     * 房屋面积
     */
    public BigDecimal getArea() {
        return area;
    }
    /**
     * 评估价格
     */
    public BigDecimal getPrice() {
        return price;
    }
    /**
     * 评估时间
     */
    public Date getPriceTime() {
        return priceTime;
    }
    /**
     * 是否自动询价
     */
    public String getAutoPrice() {
        return trim(autoPrice);
    }
    /**
     * 失效时间
     */
    public Date getDeadLine() {
        return deadLine;
    }
    /**
     * 状态(00待评估，01评估中，02已评估，99失效)
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
     * 请求编号(必填项)
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 询价系统序列号
     */
    public void setBpsCode(String bpsCode) {
        this.bpsCode = bpsCode;
    }
    /**
     * 申请询价时间
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    /**
     * 数据修改时间
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
    /**
     * 询价用户
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    /**
     * 待处理人
     */
    public void setPendingUserNo(String pendingUserNo) {
        this.pendingUserNo = pendingUserNo;
    }
    /**
     * 询价的房子所在城市
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    /**
     * 房屋类型
     */
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
    /**
     * 房屋地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * 房屋面积
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }
    /**
     * 评估价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**
     * 评估时间
     */
    public void setPriceTime(Date priceTime) {
        this.priceTime = priceTime;
    }
    /**
     * 是否自动询价
     */
    public void setAutoPrice(String autoPrice) {
        this.autoPrice = autoPrice;
    }
    /**
     * 失效时间
     */
    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
    /**
     * 状态(00待评估，01评估中，02已评估，99失效)
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
     * 申请询价时间(格式化)
     */
    public String getDateCreatedChar() {
        return getDate(dateCreated);
    }
    public void setDateCreatedChar(String dateCreatedChar) {
        this.dateCreated = getDate(dateCreatedChar);
    }
    /**
     * 申请询价时间(格式化)
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
    /**
     * 评估时间(格式化)
     */
    public String getPriceTimeChar() {
        return getDate(priceTime);
    }
    public void setPriceTimeChar(String priceTimeChar) {
        this.priceTime = getDate(priceTimeChar);
    }
    /**
     * 评估时间(格式化)
     */
    public String getPriceTimeCharAll() {
        return getDateTime(priceTime);
    }
    public void setPriceTimeCharAll(String priceTimeCharAll) {
        this.priceTime = getDate(priceTimeCharAll);
    }
    /**
     * 失效时间(格式化)
     */
    public String getDeadLineChar() {
        return getDate(deadLine);
    }
    public void setDeadLineChar(String deadLineChar) {
        this.deadLine = getDate(deadLineChar);
    }
    /**
     * 失效时间(格式化)
     */
    public String getDeadLineCharAll() {
        return getDateTime(deadLine);
    }
    public void setDeadLineCharAll(String deadLineCharAll) {
        this.deadLine = getDate(deadLineCharAll);
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

// 用户申请房屋询价记录表
NlbsInquiryApply nlbsInquiryApply = new NlbsInquiryApply();

// 数据主键(必填项)(主键ID)
nlbsInquiryApply.setId(  );
// 请求编号(必填项)
nlbsInquiryApply.setCode(  );
// 询价系统序列号
nlbsInquiryApply.setBpsCode(  );
// 申请询价时间
nlbsInquiryApply.setDateCreated(  );
// 询价用户
nlbsInquiryApply.setUserNo(  );
// 待处理人
nlbsInquiryApply.setPendingUserNo(  );
// 询价的房子所在城市
nlbsInquiryApply.setCityCode(  );
// 房屋类型
nlbsInquiryApply.setHouseType(  );
// 房屋地址
nlbsInquiryApply.setAddress(  );
// 房屋面积
nlbsInquiryApply.setArea(  );
// 评估价格
nlbsInquiryApply.setPrice(  );
// 评估时间
nlbsInquiryApply.setPriceTime(  );
// 是否自动询价
nlbsInquiryApply.setAutoPrice(  );
// 失效时间
nlbsInquiryApply.setDeadLine(  );
// 状态(00待评估，01评估中，02已评估，99失效)
nlbsInquiryApply.setStatus(  );


//------ 自定义部分 ------

// 申请询价时间(格式化)
nlbsInquiryApply.setDateCreatedChar(  );
// 评估时间(格式化)
nlbsInquiryApply.setPriceTimeChar(  );
// 失效时间(格式化)
nlbsInquiryApply.setDeadLineChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 用户申请房屋询价记录表
NlbsInquiryApply nlbsInquiryApply = new NlbsInquiryApply();

// 数据主键(必填项)(主键ID)
nlbsInquiryApply.getId();
// 请求编号(必填项)
nlbsInquiryApply.getCode();
// 询价系统序列号
nlbsInquiryApply.getBpsCode();
// 申请询价时间
nlbsInquiryApply.getDateCreated();
// 询价用户
nlbsInquiryApply.getUserNo();
// 待处理人
nlbsInquiryApply.getPendingUserNo();
// 询价的房子所在城市
nlbsInquiryApply.getCityCode();
// 房屋类型
nlbsInquiryApply.getHouseType();
// 房屋地址
nlbsInquiryApply.getAddress();
// 房屋面积
nlbsInquiryApply.getArea();
// 评估价格
nlbsInquiryApply.getPrice();
// 评估时间
nlbsInquiryApply.getPriceTime();
// 是否自动询价
nlbsInquiryApply.getAutoPrice();
// 失效时间
nlbsInquiryApply.getDeadLine();
// 状态(00待评估，01评估中，02已评估，99失效)
nlbsInquiryApply.getStatus();


//------ 自定义部分 ------

// 申请询价时间(格式化)
nlbsInquiryApply.getDateCreatedChar();
// 评估时间(格式化)
nlbsInquiryApply.getPriceTimeChar();
// 失效时间(格式化)
nlbsInquiryApply.getDeadLineChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 用户申请房屋询价记录表
NlbsInquiryApply nlbsInquiryApply = new NlbsInquiryApply();

// 数据主键(必填项)(主键ID)
nlbsInquiryApply.setId( nlbsInquiryApply2.getId() );
// 请求编号(必填项)
nlbsInquiryApply.setCode( nlbsInquiryApply2.getCode() );
// 询价系统序列号
nlbsInquiryApply.setBpsCode( nlbsInquiryApply2.getBpsCode() );
// 申请询价时间
nlbsInquiryApply.setDateCreated( nlbsInquiryApply2.getDateCreated() );
// 询价用户
nlbsInquiryApply.setUserNo( nlbsInquiryApply2.getUserNo() );
// 待处理人
nlbsInquiryApply.setPendingUserNo( nlbsInquiryApply2.getPendingUserNo() );
// 询价的房子所在城市
nlbsInquiryApply.setCityCode( nlbsInquiryApply2.getCityCode() );
// 房屋类型
nlbsInquiryApply.setHouseType( nlbsInquiryApply2.getHouseType() );
// 房屋地址
nlbsInquiryApply.setAddress( nlbsInquiryApply2.getAddress() );
// 房屋面积
nlbsInquiryApply.setArea( nlbsInquiryApply2.getArea() );
// 评估价格
nlbsInquiryApply.setPrice( nlbsInquiryApply2.getPrice() );
// 评估时间
nlbsInquiryApply.setPriceTime( nlbsInquiryApply2.getPriceTime() );
// 是否自动询价
nlbsInquiryApply.setAutoPrice( nlbsInquiryApply2.getAutoPrice() );
// 失效时间
nlbsInquiryApply.setDeadLine( nlbsInquiryApply2.getDeadLine() );
// 状态(00待评估，01评估中，02已评估，99失效)
nlbsInquiryApply.setStatus( nlbsInquiryApply2.getStatus() );


//------ 自定义部分 ------

// 申请询价时间(格式化)
nlbsInquiryApply.setDateCreatedChar( nlbsInquiryApply2.getDateCreatedChar() );
// 评估时间(格式化)
nlbsInquiryApply.setPriceTimeChar( nlbsInquiryApply2.getPriceTimeChar() );
// 失效时间(格式化)
nlbsInquiryApply.setDeadLineChar( nlbsInquiryApply2.getDeadLineChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="11"/>
<!-- 请求编号 -->
<input name="code" value="" type="text" maxlength="36"/>
<!-- 询价系统序列号 -->
<input name="bpsCode" value="" type="text" maxlength="36"/>
<!-- 申请询价时间 -->
<input name="dateCreated" value="" type="text"/>
<!-- 询价用户 -->
<input name="userNo" value="" type="text" maxlength="36"/>
<!-- 待处理人 -->
<input name="pendingUserNo" value="" type="text" maxlength="36"/>
<!-- 询价的房子所在城市 -->
<input name="cityCode" value="" type="text" maxlength="36"/>
<!-- 房屋类型 -->
<input name="houseType" value="" type="text" maxlength="5"/>
<!-- 房屋地址 -->
<input name="address" value="" type="text" maxlength="255"/>
<!-- 房屋面积 -->
<input name="area" value="" type="text" maxlength="18"/>
<!-- 评估价格 -->
<input name="price" value="" type="text" maxlength="10"/>
<!-- 评估时间 -->
<input name="priceTime" value="" type="text"/>
<!-- 是否自动询价 -->
<input name="autoPrice" value="" type="text" maxlength="1"/>
<!-- 失效时间 -->
<input name="deadLine" value="" type="text"/>
<!-- 状态(00待评估，01评估中，02已评估，99失效) -->
<input name="status" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="nlbsInquiryApply.id" value="" type="text" maxlength="11"/>
<!-- 请求编号 -->
<input name="nlbsInquiryApply.code" value="" type="text" maxlength="36"/>
<!-- 询价系统序列号 -->
<input name="nlbsInquiryApply.bpsCode" value="" type="text" maxlength="36"/>
<!-- 申请询价时间 -->
<input name="nlbsInquiryApply.dateCreated" value="" type="text"/>
<!-- 询价用户 -->
<input name="nlbsInquiryApply.userNo" value="" type="text" maxlength="36"/>
<!-- 待处理人 -->
<input name="nlbsInquiryApply.pendingUserNo" value="" type="text" maxlength="36"/>
<!-- 询价的房子所在城市 -->
<input name="nlbsInquiryApply.cityCode" value="" type="text" maxlength="36"/>
<!-- 房屋类型 -->
<input name="nlbsInquiryApply.houseType" value="" type="text" maxlength="5"/>
<!-- 房屋地址 -->
<input name="nlbsInquiryApply.address" value="" type="text" maxlength="255"/>
<!-- 房屋面积 -->
<input name="nlbsInquiryApply.area" value="" type="text" maxlength="18"/>
<!-- 评估价格 -->
<input name="nlbsInquiryApply.price" value="" type="text" maxlength="10"/>
<!-- 评估时间 -->
<input name="nlbsInquiryApply.priceTime" value="" type="text"/>
<!-- 是否自动询价 -->
<input name="nlbsInquiryApply.autoPrice" value="" type="text" maxlength="1"/>
<!-- 失效时间 -->
<input name="nlbsInquiryApply.deadLine" value="" type="text"/>
<!-- 状态(00待评估，01评估中，02已评估，99失效) -->
<input name="nlbsInquiryApply.status" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NIA_ID" name="id" value="" type="text" maxlength="11"/>
<!-- 请求编号 -->
<input id="NIA_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- 询价系统序列号 -->
<input id="NIA_BPS_CODE" name="bpsCode" value="" type="text" maxlength="36"/>
<!-- 申请询价时间 -->
<input id="NIA_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 询价用户 -->
<input id="NIA_USER_NO" name="userNo" value="" type="text" maxlength="36"/>
<!-- 待处理人 -->
<input id="NIA_PENDING_USER_NO" name="pendingUserNo" value="" type="text" maxlength="36"/>
<!-- 询价的房子所在城市 -->
<input id="NIA_CITY_CODE" name="cityCode" value="" type="text" maxlength="36"/>
<!-- 房屋类型 -->
<input id="NIA_HOUSE_TYPE" name="houseType" value="" type="text" maxlength="5"/>
<!-- 房屋地址 -->
<input id="NIA_ADDRESS" name="address" value="" type="text" maxlength="255"/>
<!-- 房屋面积 -->
<input id="NIA_AREA" name="area" value="" type="text" maxlength="18"/>
<!-- 评估价格 -->
<input id="NIA_PRICE" name="price" value="" type="text" maxlength="10"/>
<!-- 评估时间 -->
<input id="NIA_PRICE_TIME" name="priceTime" value="" type="text"/>
<!-- 是否自动询价 -->
<input id="NIA_AUTO_PRICE" name="autoPrice" value="" type="text" maxlength="1"/>
<!-- 失效时间 -->
<input id="NIA_DEAD_LINE" name="deadLine" value="" type="text"/>
<!-- 状态(00待评估，01评估中，02已评估，99失效) -->
<input id="NIA_STATUS" name="status" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NIA_ID" name="nlbsInquiryApply.id" value="" type="text" maxlength="11"/>
<!-- 请求编号 -->
<input id="NIA_CODE" name="nlbsInquiryApply.code" value="" type="text" maxlength="36"/>
<!-- 询价系统序列号 -->
<input id="NIA_BPS_CODE" name="nlbsInquiryApply.bpsCode" value="" type="text" maxlength="36"/>
<!-- 申请询价时间 -->
<input id="NIA_DATE_CREATED" name="nlbsInquiryApply.dateCreated" value="" type="text"/>
<!-- 询价用户 -->
<input id="NIA_USER_NO" name="nlbsInquiryApply.userNo" value="" type="text" maxlength="36"/>
<!-- 待处理人 -->
<input id="NIA_PENDING_USER_NO" name="nlbsInquiryApply.pendingUserNo" value="" type="text" maxlength="36"/>
<!-- 询价的房子所在城市 -->
<input id="NIA_CITY_CODE" name="nlbsInquiryApply.cityCode" value="" type="text" maxlength="36"/>
<!-- 房屋类型 -->
<input id="NIA_HOUSE_TYPE" name="nlbsInquiryApply.houseType" value="" type="text" maxlength="5"/>
<!-- 房屋地址 -->
<input id="NIA_ADDRESS" name="nlbsInquiryApply.address" value="" type="text" maxlength="255"/>
<!-- 房屋面积 -->
<input id="NIA_AREA" name="nlbsInquiryApply.area" value="" type="text" maxlength="18"/>
<!-- 评估价格 -->
<input id="NIA_PRICE" name="nlbsInquiryApply.price" value="" type="text" maxlength="10"/>
<!-- 评估时间 -->
<input id="NIA_PRICE_TIME" name="nlbsInquiryApply.priceTime" value="" type="text"/>
<!-- 是否自动询价 -->
<input id="NIA_AUTO_PRICE" name="nlbsInquiryApply.autoPrice" value="" type="text" maxlength="1"/>
<!-- 失效时间 -->
<input id="NIA_DEAD_LINE" name="nlbsInquiryApply.deadLine" value="" type="text"/>
<!-- 状态(00待评估，01评估中，02已评估，99失效) -->
<input id="NIA_STATUS" name="nlbsInquiryApply.status" value="" type="text" maxlength="2"/>




--------------------------------------------------------
 */


    