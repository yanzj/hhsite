package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.*;


/**
 * @实体名称 各询价公司询价请求表
 * @数表名称 BPS_COMPANY_INQUIRY_APPLY
 * @开发日期 2017-06-13
 * @技术服务 www.fwjava.com
 */
public class BpsCompanyInquiryApply implements Serializable {

    //询价序列号
    private String serialNo;
    /**
     * (必填项)(主键ID)
     */
    private Integer id              = null;
    /**
     * 
     */
    private String code             = null;
    /**
     * 
     */
    private Date dateCreated        = null;
    /**
     * 
     */
    private String cityCode         = null;
    /**
     * 房屋类型(别墅，公寓)
     */
    private String houseType        = null;
    /**
     * 小区id(同询价公司一致)
     */
    private String plotsCode        = null;
    /**
     * 小区名
     */
    private String plotsName        = null;
    /**
     * 楼栋id(同询价公司一致)
     */
    private String unitCode         = null;
    /**
     * 楼栋号
     */
    private String unitName           = null;
    /**
     * 房间id(同询价公司一致)
     */
    private String houseCode         = null;
    /**
     * 房号
     */
    private String houseName           = null;
    /**
     * 房屋面积
     */
    private BigDecimal area         = null;
    /**
     * 总楼层
     */
    private Integer totalFloor      = null;
    /**
     * 所在层
     */
    private Integer currentFloor    = null;
    /**
     * 房屋朝向(东:1南:2西:3北:4)
     */
    private String towards          = null;
    /**
     * 竣工日期
     */
    private String yearBuilt    = null;
    /**
     * 询价公司编号
     */
    private String companyCode      = null;
    /**
     * 房屋总价
     */
    private BigDecimal price        = null;
    //房屋单价
    private BigDecimal unitPrice = null;
    /**
     * 获取价格时间
     */
    private Date priceTime          = null;
    /**
     * 是否是自动询价
     */
    private String autoPrice          = null;
    /**
     * 失效时间
     */
    private Date deadline           = null;
    /**
     *
     */
    private String status            = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //行政区域(公寓评估时)
    private String areaCode            = null;
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    private String areaName            = null;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    //房屋地址(公寓评估时)
    private String  address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 排序
     */
    private String orderBy          = null;

    /*
     *--------------------------------------------------
     * Getter方法区
     *--------------------------------------------------
     */

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * (必填项)(主键ID)
     */
    public Integer getId() {
        return id;
    }
    /**
     * 
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 
     */
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * 
     */
    public String getCityCode() {
        return trim(cityCode);
    }
    /**
     * 房屋类型(别墅，公寓)
     */
    public String getHouseType() {
        return trim(houseType);
    }
    /**
     * 小区id(同询价公司一致)
     */
    public String getPlotsCode() {
        return trim(plotsCode);
    }
    /**
     * 小区名
     */
    public String getPlotsName() {
        return trim(plotsName);
    }
    /**
     * 楼栋id(同询价公司一致)
     */
    public String getUnitCode() {
        return trim(unitCode);
    }
    /**
     * 楼栋号
     */
    public String getUnitName() {
        return trim(unitName);
    }
    /**
     * 房间id(同询价公司一致)
     */
    public String getHouseCode() {
        return trim(houseCode);
    }
    /**
     * 房号
     */
    public String getHouseName() {
        return trim(houseName);
    }
    /**
     * 房屋面积
     */
    public BigDecimal getArea() {
        return area;
    }
    /**
     * 总楼层
     */
    public Integer getTotalFloor() {
        return totalFloor;
    }
    /**
     * 所在层
     */
    public Integer getCurrentFloor() {
        return currentFloor;
    }
    /**
     * 房屋朝向(东:1南:2西:3北:4)
     */
    public String getTowards() {
        return trim(towards);
    }
    /**
     * 竣工日期
     */
    public String getYearBuilt() {
        return trim(yearBuilt);
    }
    /**
     * 询价公司编号
     */
    public String getCompanyCode() {
        return trim(companyCode);
    }
    /**
     * 询价公司估值
     */
    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * 获取价格时间
     */
    public Date getPriceTime() {
        return priceTime;
    }
    /**
     * 获取是否自动询价
     */
    public String getAutoPrice() {
        return trim(autoPrice);
    }
    /**
     * 失效时间
     */
    public Date getDeadline() {
        return deadline;
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
     * (必填项)(主键ID)
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    /**
     * 
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    /**
     * 房屋类型(别墅，公寓)
     */
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
    /**
     * 小区id(同询价公司一致)
     */
    public void setPlotsCode(String plotsCode) {
        this.plotsCode = plotsCode;
    }
    /**
     * 小区名
     */
    public void setPlotsName(String plotsName) {
        this.plotsName = plotsName;
    }
    /**
     * 楼栋id(同询价公司一致)
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    /**
     * 楼栋号
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    /**
     * 房间id(同询价公司一致)
     */
    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }
    /**
     * 房号
     */
    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }
    /**
     * 房屋面积
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }
    /**
     * 总楼层
     */
    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }
    /**
     * 所在层
     */
    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }
    /**
     * 房屋朝向(东:1南:2西:3北:4)
     */
    public void setTowards(String towards) {
        this.towards = towards;
    }
    /**
     * 竣工日期
     */
    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = yearBuilt;
    }
    /**
     * 询价公司编号
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    /**
     * 询价公司估值
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * 获取价格时间
     */
    public void setPriceTime(Date priceTime) {
        this.priceTime = priceTime;
    }

    public void setAutoPrice(String autoPrice) {
        this.autoPrice = autoPrice;
    }

    /**
     * 失效时间
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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
     * (格式化)
     */
    public String getDateCreatedChar() {
        return getDate(dateCreated);
    }
    public void setDateCreatedChar(String dateCreatedChar) {
        this.dateCreated = getDate(dateCreatedChar);
    }
    /**
     * (格式化)
     */
    public String getDateCreatedCharAll() {
        return getDateTime(dateCreated);
    }
    public void setDateCreatedCharAll(String dateCreatedCharAll) {
        this.dateCreated = getDate(dateCreatedCharAll);
    }
    /**
     * 获取价格时间(格式化)
     */
    public String getPriceTimeChar() {
        return getDate(priceTime);
    }
    public void setPriceTimeChar(String priceTimeChar) {
        this.priceTime = getDate(priceTimeChar);
    }
    /**
     * 获取价格时间(格式化)
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
    public String getDeadlineChar() {
        return getDate(deadline);
    }
    public void setDeadlineChar(String deadlineChar) {
        this.deadline = getDate(deadlineChar);
    }
    /**
     * 失效时间(格式化)
     */
    public String getDeadlineCharAll() {
        return getDateTime(deadline);
    }
    public void setDeadlineCharAll(String deadlineCharAll) {
        this.deadline = getDate(deadlineCharAll);
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

// 各询价公司询价请求表
BpsCompanyInquiryApply bpsCompanyInquiryApply = new BpsCompanyInquiryApply();

// (必填项)(主键ID)
bpsCompanyInquiryApply.setId(  );
// 
bpsCompanyInquiryApply.setCode(  );
// 
bpsCompanyInquiryApply.setDateCreated(  );
// 
bpsCompanyInquiryApply.setCityCode(  );
// 房屋类型(别墅，公寓)
bpsCompanyInquiryApply.setHouseType(  );
// 小区id(同询价公司一致)
bpsCompanyInquiryApply.setPlotsCode(  );
// 小区名
bpsCompanyInquiryApply.setPlotsName(  );
// 楼栋id(同询价公司一致)
bpsCompanyInquiryApply.setUnitCode(  );
// 楼栋号
bpsCompanyInquiryApply.setUnitName(  );
// 房间id(同询价公司一致)
bpsCompanyInquiryApply.setHouseCode(  );
// 房号
bpsCompanyInquiryApply.setHouseName(  );
// 房屋面积
bpsCompanyInquiryApply.setArea(  );
// 总楼层
bpsCompanyInquiryApply.setTotalFloor(  );
// 所在层
bpsCompanyInquiryApply.setCurrentFloor(  );
// 房屋朝向(东:1南:2西:3北:4)
bpsCompanyInquiryApply.setTowards(  );
// 竣工日期
bpsCompanyInquiryApply.setYearBuilt(  );
// 询价公司编号
bpsCompanyInquiryApply.setCompanyCode(  );
// 询价公司估值
bpsCompanyInquiryApply.setPrice(  );
// 获取价格时间
bpsCompanyInquiryApply.setPriceTime(  );
// 失效时间
bpsCompanyInquiryApply.setDeadline(  );


//------ 自定义部分 ------

// (格式化)
bpsCompanyInquiryApply.setDateCreatedChar(  );
// 获取价格时间(格式化)
bpsCompanyInquiryApply.setPriceTimeChar(  );
// 失效时间(格式化)
bpsCompanyInquiryApply.setDeadlineChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 各询价公司询价请求表
BpsCompanyInquiryApply bpsCompanyInquiryApply = new BpsCompanyInquiryApply();

// (必填项)(主键ID)
bpsCompanyInquiryApply.getId();
// 
bpsCompanyInquiryApply.getCode();
// 
bpsCompanyInquiryApply.getDateCreated();
// 
bpsCompanyInquiryApply.getCityCode();
// 房屋类型(别墅，公寓)
bpsCompanyInquiryApply.getHouseType();
// 小区id(同询价公司一致)
bpsCompanyInquiryApply.getPlotsCode();
// 小区名
bpsCompanyInquiryApply.getPlotsName();
// 楼栋id(同询价公司一致)
bpsCompanyInquiryApply.getUnitCode();
// 楼栋号
bpsCompanyInquiryApply.getUnitName();
// 房间id(同询价公司一致)
bpsCompanyInquiryApply.getHouseCode();
// 房号
bpsCompanyInquiryApply.getHouseName();
// 房屋面积
bpsCompanyInquiryApply.getArea();
// 总楼层
bpsCompanyInquiryApply.getTotalFloor();
// 所在层
bpsCompanyInquiryApply.getCurrentFloor();
// 房屋朝向(东:1南:2西:3北:4)
bpsCompanyInquiryApply.getTowards();
// 竣工日期
bpsCompanyInquiryApply.getYearBuilt();
// 询价公司编号
bpsCompanyInquiryApply.getCompanyCode();
// 询价公司估值
bpsCompanyInquiryApply.getPrice();
// 获取价格时间
bpsCompanyInquiryApply.getPriceTime();
// 失效时间
bpsCompanyInquiryApply.getDeadline();


//------ 自定义部分 ------

// (格式化)
bpsCompanyInquiryApply.getDateCreatedChar();
// 获取价格时间(格式化)
bpsCompanyInquiryApply.getPriceTimeChar();
// 失效时间(格式化)
bpsCompanyInquiryApply.getDeadlineChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 各询价公司询价请求表
BpsCompanyInquiryApply bpsCompanyInquiryApply = new BpsCompanyInquiryApply();

// (必填项)(主键ID)
bpsCompanyInquiryApply.setId( bpsCompanyInquiryApply2.getId() );
// 
bpsCompanyInquiryApply.setCode( bpsCompanyInquiryApply2.getCode() );
// 
bpsCompanyInquiryApply.setDateCreated( bpsCompanyInquiryApply2.getDateCreated() );
// 
bpsCompanyInquiryApply.setCityCode( bpsCompanyInquiryApply2.getCityCode() );
// 房屋类型(别墅，公寓)
bpsCompanyInquiryApply.setHouseType( bpsCompanyInquiryApply2.getHouseType() );
// 小区id(同询价公司一致)
bpsCompanyInquiryApply.setPlotsCode( bpsCompanyInquiryApply2.getPlotsCode() );
// 小区名
bpsCompanyInquiryApply.setPlotsName( bpsCompanyInquiryApply2.getPlotsName() );
// 楼栋id(同询价公司一致)
bpsCompanyInquiryApply.setUnitCode( bpsCompanyInquiryApply2.getUnitCode() );
// 楼栋号
bpsCompanyInquiryApply.setUnitName( bpsCompanyInquiryApply2.getUnitName() );
// 房间id(同询价公司一致)
bpsCompanyInquiryApply.setHouseCode( bpsCompanyInquiryApply2.getHouseCode() );
// 房号
bpsCompanyInquiryApply.setHouseName( bpsCompanyInquiryApply2.getHouseName() );
// 房屋面积
bpsCompanyInquiryApply.setArea( bpsCompanyInquiryApply2.getArea() );
// 总楼层
bpsCompanyInquiryApply.setTotalFloor( bpsCompanyInquiryApply2.getTotalFloor() );
// 所在层
bpsCompanyInquiryApply.setCurrentFloor( bpsCompanyInquiryApply2.getCurrentFloor() );
// 房屋朝向(东:1南:2西:3北:4)
bpsCompanyInquiryApply.setTowards( bpsCompanyInquiryApply2.getTowards() );
// 竣工日期
bpsCompanyInquiryApply.setYearBuilt( bpsCompanyInquiryApply2.getYearBuilt() );
// 询价公司编号
bpsCompanyInquiryApply.setCompanyCode( bpsCompanyInquiryApply2.getCompanyCode() );
// 询价公司估值
bpsCompanyInquiryApply.setPrice( bpsCompanyInquiryApply2.getPrice() );
// 获取价格时间
bpsCompanyInquiryApply.setPriceTime( bpsCompanyInquiryApply2.getPriceTime() );
// 失效时间
bpsCompanyInquiryApply.setDeadline( bpsCompanyInquiryApply2.getDeadline() );


//------ 自定义部分 ------

// (格式化)
bpsCompanyInquiryApply.setDateCreatedChar( bpsCompanyInquiryApply2.getDateCreatedChar() );
// 获取价格时间(格式化)
bpsCompanyInquiryApply.setPriceTimeChar( bpsCompanyInquiryApply2.getPriceTimeChar() );
// 失效时间(格式化)
bpsCompanyInquiryApply.setDeadlineChar( bpsCompanyInquiryApply2.getDeadlineChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="code" value="" type="text" maxlength="36"/>
<!--  -->
<input name="dateCreated" value="" type="text"/>
<!--  -->
<input name="cityCode" value="" type="text" maxlength="36"/>
<!-- 房屋类型(别墅，公寓) -->
<input name="houseType" value="" type="text" maxlength="16"/>
<!-- 小区id(同询价公司一致) -->
<input name="plotsCode" value="" type="text" maxlength="36"/>
<!-- 小区名 -->
<input name="plotsName" value="" type="text" maxlength="100"/>
<!-- 楼栋id(同询价公司一致) -->
<input name="unitCode" value="" type="text" maxlength="36"/>
<!-- 楼栋号 -->
<input name="unitName" value="" type="text" maxlength="100"/>
<!-- 房间id(同询价公司一致) -->
<input name="houseCode" value="" type="text" maxlength="16"/>
<!-- 房号 -->
<input name="houseName" value="" type="text" maxlength="100"/>
<!-- 房屋面积 -->
<input name="area" value="" type="text" maxlength="18"/>
<!-- 总楼层 -->
<input name="totalFloor" value="" type="text" maxlength="3"/>
<!-- 所在层 -->
<input name="currentFloor" value="" type="text" maxlength="3"/>
<!-- 房屋朝向(东:1南:2西:3北:4) -->
<input name="towards" value="" type="text" maxlength="255"/>
<!-- 竣工日期 -->
<input name="yearBuilt" value="" type="text" maxlength="36"/>
<!-- 询价公司编号 -->
<input name="companyCode" value="" type="text" maxlength="36"/>
<!-- 询价公司估值 -->
<input name="price" value="" type="text" maxlength="10"/>
<!-- 获取价格时间 -->
<input name="priceTime" value="" type="text"/>
<!-- 失效时间 -->
<input name="deadline" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="bpsCompanyInquiryApply.id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="bpsCompanyInquiryApply.code" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsCompanyInquiryApply.dateCreated" value="" type="text"/>
<!--  -->
<input name="bpsCompanyInquiryApply.cityCode" value="" type="text" maxlength="36"/>
<!-- 房屋类型(别墅，公寓) -->
<input name="bpsCompanyInquiryApply.houseType" value="" type="text" maxlength="16"/>
<!-- 小区id(同询价公司一致) -->
<input name="bpsCompanyInquiryApply.plotsCode" value="" type="text" maxlength="36"/>
<!-- 小区名 -->
<input name="bpsCompanyInquiryApply.plotsName" value="" type="text" maxlength="100"/>
<!-- 楼栋id(同询价公司一致) -->
<input name="bpsCompanyInquiryApply.unitCode" value="" type="text" maxlength="36"/>
<!-- 楼栋号 -->
<input name="bpsCompanyInquiryApply.unitName" value="" type="text" maxlength="100"/>
<!-- 房间id(同询价公司一致) -->
<input name="bpsCompanyInquiryApply.houseCode" value="" type="text" maxlength="16"/>
<!-- 房号 -->
<input name="bpsCompanyInquiryApply.houseName" value="" type="text" maxlength="100"/>
<!-- 房屋面积 -->
<input name="bpsCompanyInquiryApply.area" value="" type="text" maxlength="18"/>
<!-- 总楼层 -->
<input name="bpsCompanyInquiryApply.totalFloor" value="" type="text" maxlength="3"/>
<!-- 所在层 -->
<input name="bpsCompanyInquiryApply.currentFloor" value="" type="text" maxlength="3"/>
<!-- 房屋朝向(东:1南:2西:3北:4) -->
<input name="bpsCompanyInquiryApply.towards" value="" type="text" maxlength="255"/>
<!-- 竣工日期 -->
<input name="bpsCompanyInquiryApply.yearBuilt" value="" type="text" maxlength="36"/>
<!-- 询价公司编号 -->
<input name="bpsCompanyInquiryApply.companyCode" value="" type="text" maxlength="36"/>
<!-- 询价公司估值 -->
<input name="bpsCompanyInquiryApply.price" value="" type="text" maxlength="10"/>
<!-- 获取价格时间 -->
<input name="bpsCompanyInquiryApply.priceTime" value="" type="text"/>
<!-- 失效时间 -->
<input name="bpsCompanyInquiryApply.deadline" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BCIA_ID" name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BCIA_CODE" name="code" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BCIA_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!--  -->
<input id="BCIA_CITY_CODE" name="cityCode" value="" type="text" maxlength="36"/>
<!-- 房屋类型(别墅，公寓) -->
<input id="BCIA_HOUSE_TYPE" name="houseType" value="" type="text" maxlength="16"/>
<!-- 小区id(同询价公司一致) -->
<input id="BCIA_PLOTS_CODE" name="plotsCode" value="" type="text" maxlength="36"/>
<!-- 小区名 -->
<input id="BCIA_PLOTS_NAME" name="plotsName" value="" type="text" maxlength="100"/>
<!-- 楼栋id(同询价公司一致) -->
<input id="BCIA_UNIT_CODE" name="unitCode" value="" type="text" maxlength="36"/>
<!-- 楼栋号 -->
<input id="BCIA_UNIT_NO" name="unitName" value="" type="text" maxlength="100"/>
<!-- 房间id(同询价公司一致) -->
<input id="BCIA_ROOM_CODE" name="houseCode" value="" type="text" maxlength="16"/>
<!-- 房号 -->
<input id="BCIA_ROOM_NO" name="houseName" value="" type="text" maxlength="100"/>
<!-- 房屋面积 -->
<input id="BCIA_AREA" name="area" value="" type="text" maxlength="18"/>
<!-- 总楼层 -->
<input id="BCIA_TOTAL_FLOOR" name="totalFloor" value="" type="text" maxlength="3"/>
<!-- 所在层 -->
<input id="BCIA_CURRENT_FLOOR" name="currentFloor" value="" type="text" maxlength="3"/>
<!-- 房屋朝向(东:1南:2西:3北:4) -->
<input id="BCIA_TOWARDS" name="towards" value="" type="text" maxlength="255"/>
<!-- 竣工日期 -->
<input id="BCIA_COMPLETED_YEAR" name="yearBuilt" value="" type="text" maxlength="36"/>
<!-- 询价公司编号 -->
<input id="BCIA_COMPANY_CODE" name="companyCode" value="" type="text" maxlength="36"/>
<!-- 询价公司估值 -->
<input id="BCIA_PRICE" name="price" value="" type="text" maxlength="10"/>
<!-- 获取价格时间 -->
<input id="BCIA_PRICE_TIME" name="priceTime" value="" type="text"/>
<!-- 失效时间 -->
<input id="BCIA_DEADLINE" name="deadline" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BCIA_ID" name="bpsCompanyInquiryApply.id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BCIA_CODE" name="bpsCompanyInquiryApply.code" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BCIA_DATE_CREATED" name="bpsCompanyInquiryApply.dateCreated" value="" type="text"/>
<!--  -->
<input id="BCIA_CITY_CODE" name="bpsCompanyInquiryApply.cityCode" value="" type="text" maxlength="36"/>
<!-- 房屋类型(别墅，公寓) -->
<input id="BCIA_HOUSE_TYPE" name="bpsCompanyInquiryApply.houseType" value="" type="text" maxlength="16"/>
<!-- 小区id(同询价公司一致) -->
<input id="BCIA_PLOTS_CODE" name="bpsCompanyInquiryApply.plotsCode" value="" type="text" maxlength="36"/>
<!-- 小区名 -->
<input id="BCIA_PLOTS_NAME" name="bpsCompanyInquiryApply.plotsName" value="" type="text" maxlength="100"/>
<!-- 楼栋id(同询价公司一致) -->
<input id="BCIA_UNIT_CODE" name="bpsCompanyInquiryApply.unitCode" value="" type="text" maxlength="36"/>
<!-- 楼栋号 -->
<input id="BCIA_UNIT_NO" name="bpsCompanyInquiryApply.unitName" value="" type="text" maxlength="100"/>
<!-- 房间id(同询价公司一致) -->
<input id="BCIA_ROOM_CODE" name="bpsCompanyInquiryApply.houseCode" value="" type="text" maxlength="16"/>
<!-- 房号 -->
<input id="BCIA_ROOM_NO" name="bpsCompanyInquiryApply.houseName" value="" type="text" maxlength="100"/>
<!-- 房屋面积 -->
<input id="BCIA_AREA" name="bpsCompanyInquiryApply.area" value="" type="text" maxlength="18"/>
<!-- 总楼层 -->
<input id="BCIA_TOTAL_FLOOR" name="bpsCompanyInquiryApply.totalFloor" value="" type="text" maxlength="3"/>
<!-- 所在层 -->
<input id="BCIA_CURRENT_FLOOR" name="bpsCompanyInquiryApply.currentFloor" value="" type="text" maxlength="3"/>
<!-- 房屋朝向(东:1南:2西:3北:4) -->
<input id="BCIA_TOWARDS" name="bpsCompanyInquiryApply.towards" value="" type="text" maxlength="255"/>
<!-- 竣工日期 -->
<input id="BCIA_COMPLETED_YEAR" name="bpsCompanyInquiryApply.yearBuilt" value="" type="text" maxlength="36"/>
<!-- 询价公司编号 -->
<input id="BCIA_COMPANY_CODE" name="bpsCompanyInquiryApply.companyCode" value="" type="text" maxlength="36"/>
<!-- 询价公司估值 -->
<input id="BCIA_PRICE" name="bpsCompanyInquiryApply.price" value="" type="text" maxlength="10"/>
<!-- 获取价格时间 -->
<input id="BCIA_PRICE_TIME" name="bpsCompanyInquiryApply.priceTime" value="" type="text"/>
<!-- 失效时间 -->
<input id="BCIA_DEADLINE" name="bpsCompanyInquiryApply.deadline" value="" type="text"/>




--------------------------------------------------------
 */


    