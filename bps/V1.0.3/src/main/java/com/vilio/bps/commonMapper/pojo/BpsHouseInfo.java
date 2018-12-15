package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.*;


/**
 * @实体名称 房屋信息表
 * @数表名称 BPS_HOUSE_INFO
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsHouseInfo implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id              = null;
    /**
     * 
     */
    private String code             = null;
    /**
     * 关联城市(必填项)
     */
    private String cityCode         = null;
    /**
     * 关联城市名称
     */
    private String cityName         = null;
    /**
     * 关联小区id(各估价公司不同)(必填项)
     */
    private String plotsCode        = null;
    /**
     * 小区名称
     */
    private String plotName         = null;
    /**
     * 房屋地址
     */
    private String address          = null;
    /**
     * 楼栋id(各估价公司不同)
     */
    private String unitCode         = null;
    /**
     * 楼栋号(必填项)
     */
    private String unitNo           = null;
    /**
     * 房间id(各估价公司不同)
     */
    private String roomCode         = null;
    /**
     * 房号(必填项)
     */
    private String roomNo           = null;
    /**
     * 房屋面积(必填项)
     */
    private BigDecimal area         = null;
    /**
     * 房屋类型(别墅，公寓)(必填项)
     */
    private String houseType        = null;
    /**
     * 总楼层
     */
    private Integer totalFloor      = null;
    /**
     * 当前楼层
     */
    private Integer currentFloor    = null;
    /**
     * 询价公司编号
     */
    private String companyCode      = null;
    /**
     * 状态(1可用，0不可用)(必填项)
     */
    private String status           = null;
    /**
     * 数据创建时间(必填项)
     */
    private Date dateCreated        = null;
    /**
     * 数据修改时间(必填项)
     */
    private Date dateModified       = null;
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
     * 
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 关联城市(必填项)
     */
    public String getCityCode() {
        return trim(cityCode);
    }
    /**
     * 关联城市名称
     */
    public String getCityName() {
        return trim(cityName);
    }
    /**
     * 关联小区id(各估价公司不同)(必填项)
     */
    public String getPlotsCode() {
        return trim(plotsCode);
    }
    /**
     * 小区名称
     */
    public String getPlotName() {
        return trim(plotName);
    }
    /**
     * 房屋地址
     */
    public String getAddress() {
        return trim(address);
    }
    /**
     * 楼栋id(各估价公司不同)
     */
    public String getUnitCode() {
        return trim(unitCode);
    }
    /**
     * 楼栋号(必填项)
     */
    public String getUnitNo() {
        return trim(unitNo);
    }
    /**
     * 房间id(各估价公司不同)
     */
    public String getRoomCode() {
        return trim(roomCode);
    }
    /**
     * 房号(必填项)
     */
    public String getRoomNo() {
        return trim(roomNo);
    }
    /**
     * 房屋面积(必填项)
     */
    public BigDecimal getArea() {
        return area;
    }
    /**
     * 房屋类型(别墅，公寓)(必填项)
     */
    public String getHouseType() {
        return trim(houseType);
    }
    /**
     * 总楼层
     */
    public Integer getTotalFloor() {
        return totalFloor;
    }
    /**
     * 当前楼层
     */
    public Integer getCurrentFloor() {
        return currentFloor;
    }
    /**
     * 询价公司编号
     */
    public String getCompanyCode() {
        return trim(companyCode);
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
     * 
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 关联城市(必填项)
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    /**
     * 关联城市名称
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    /**
     * 关联小区id(各估价公司不同)(必填项)
     */
    public void setPlotsCode(String plotsCode) {
        this.plotsCode = plotsCode;
    }
    /**
     * 小区名称
     */
    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }
    /**
     * 房屋地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * 楼栋id(各估价公司不同)
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    /**
     * 楼栋号(必填项)
     */
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
    /**
     * 房间id(各估价公司不同)
     */
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
    /**
     * 房号(必填项)
     */
    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
    /**
     * 房屋面积(必填项)
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }
    /**
     * 房屋类型(别墅，公寓)(必填项)
     */
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
    /**
     * 总楼层
     */
    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }
    /**
     * 当前楼层
     */
    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }
    /**
     * 询价公司编号
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
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

// 房屋信息表
BpsHouseInfo bpsHouseInfo = new BpsHouseInfo();

// 数据主键(必填项)(主键ID)
bpsHouseInfo.setId(  );
// 
bpsHouseInfo.setCode(  );
// 关联城市(必填项)
bpsHouseInfo.setCityCode(  );
// 关联城市名称
bpsHouseInfo.setCityName(  );
// 关联小区id(各估价公司不同)(必填项)
bpsHouseInfo.setPlotsCode(  );
// 小区名称
bpsHouseInfo.setPlotName(  );
// 房屋地址
bpsHouseInfo.setAddress(  );
// 楼栋id(各估价公司不同)
bpsHouseInfo.setUnitCode(  );
// 楼栋号(必填项)
bpsHouseInfo.setUnitNo(  );
// 房间id(各估价公司不同)
bpsHouseInfo.setRoomCode(  );
// 房号(必填项)
bpsHouseInfo.setRoomNo(  );
// 房屋面积(必填项)
bpsHouseInfo.setArea(  );
// 房屋类型(别墅，公寓)(必填项)
bpsHouseInfo.setHouseType(  );
// 总楼层
bpsHouseInfo.setTotalFloor(  );
// 当前楼层
bpsHouseInfo.setCurrentFloor(  );
// 询价公司编号
bpsHouseInfo.setCompanyCode(  );
// 状态(1可用，0不可用)(必填项)
bpsHouseInfo.setStatus(  );
// 数据创建时间(必填项)
bpsHouseInfo.setDateCreated(  );
// 数据修改时间(必填项)
bpsHouseInfo.setDateModified(  );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsHouseInfo.setDateCreatedChar(  );
// 数据修改时间(格式化)
bpsHouseInfo.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 房屋信息表
BpsHouseInfo bpsHouseInfo = new BpsHouseInfo();

// 数据主键(必填项)(主键ID)
bpsHouseInfo.getId();
// 
bpsHouseInfo.getCode();
// 关联城市(必填项)
bpsHouseInfo.getCityCode();
// 关联城市名称
bpsHouseInfo.getCityName();
// 关联小区id(各估价公司不同)(必填项)
bpsHouseInfo.getPlotsCode();
// 小区名称
bpsHouseInfo.getPlotName();
// 房屋地址
bpsHouseInfo.getAddress();
// 楼栋id(各估价公司不同)
bpsHouseInfo.getUnitCode();
// 楼栋号(必填项)
bpsHouseInfo.getUnitNo();
// 房间id(各估价公司不同)
bpsHouseInfo.getRoomCode();
// 房号(必填项)
bpsHouseInfo.getRoomNo();
// 房屋面积(必填项)
bpsHouseInfo.getArea();
// 房屋类型(别墅，公寓)(必填项)
bpsHouseInfo.getHouseType();
// 总楼层
bpsHouseInfo.getTotalFloor();
// 当前楼层
bpsHouseInfo.getCurrentFloor();
// 询价公司编号
bpsHouseInfo.getCompanyCode();
// 状态(1可用，0不可用)(必填项)
bpsHouseInfo.getStatus();
// 数据创建时间(必填项)
bpsHouseInfo.getDateCreated();
// 数据修改时间(必填项)
bpsHouseInfo.getDateModified();


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsHouseInfo.getDateCreatedChar();
// 数据修改时间(格式化)
bpsHouseInfo.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 房屋信息表
BpsHouseInfo bpsHouseInfo = new BpsHouseInfo();

// 数据主键(必填项)(主键ID)
bpsHouseInfo.setId( bpsHouseInfo2.getId() );
// 
bpsHouseInfo.setCode( bpsHouseInfo2.getCode() );
// 关联城市(必填项)
bpsHouseInfo.setCityCode( bpsHouseInfo2.getCityCode() );
// 关联城市名称
bpsHouseInfo.setCityName( bpsHouseInfo2.getCityName() );
// 关联小区id(各估价公司不同)(必填项)
bpsHouseInfo.setPlotsCode( bpsHouseInfo2.getPlotsCode() );
// 小区名称
bpsHouseInfo.setPlotName( bpsHouseInfo2.getPlotName() );
// 房屋地址
bpsHouseInfo.setAddress( bpsHouseInfo2.getAddress() );
// 楼栋id(各估价公司不同)
bpsHouseInfo.setUnitCode( bpsHouseInfo2.getUnitCode() );
// 楼栋号(必填项)
bpsHouseInfo.setUnitNo( bpsHouseInfo2.getUnitNo() );
// 房间id(各估价公司不同)
bpsHouseInfo.setRoomCode( bpsHouseInfo2.getRoomCode() );
// 房号(必填项)
bpsHouseInfo.setRoomNo( bpsHouseInfo2.getRoomNo() );
// 房屋面积(必填项)
bpsHouseInfo.setArea( bpsHouseInfo2.getArea() );
// 房屋类型(别墅，公寓)(必填项)
bpsHouseInfo.setHouseType( bpsHouseInfo2.getHouseType() );
// 总楼层
bpsHouseInfo.setTotalFloor( bpsHouseInfo2.getTotalFloor() );
// 当前楼层
bpsHouseInfo.setCurrentFloor( bpsHouseInfo2.getCurrentFloor() );
// 询价公司编号
bpsHouseInfo.setCompanyCode( bpsHouseInfo2.getCompanyCode() );
// 状态(1可用，0不可用)(必填项)
bpsHouseInfo.setStatus( bpsHouseInfo2.getStatus() );
// 数据创建时间(必填项)
bpsHouseInfo.setDateCreated( bpsHouseInfo2.getDateCreated() );
// 数据修改时间(必填项)
bpsHouseInfo.setDateModified( bpsHouseInfo2.getDateModified() );


//------ 自定义部分 ------

// 数据创建时间(格式化)
bpsHouseInfo.setDateCreatedChar( bpsHouseInfo2.getDateCreatedChar() );
// 数据修改时间(格式化)
bpsHouseInfo.setDateModifiedChar( bpsHouseInfo2.getDateModifiedChar() );



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
<!-- 关联城市 -->
<input name="cityCode" value="" type="text" maxlength="36"/>
<!-- 关联城市名称 -->
<input name="cityName" value="" type="text" maxlength="100"/>
<!-- 关联小区id(各估价公司不同) -->
<input name="plotsCode" value="" type="text" maxlength="36"/>
<!-- 小区名称 -->
<input name="plotName" value="" type="text" maxlength="255"/>
<!-- 房屋地址 -->
<input name="address" value="" type="text" maxlength="100"/>
<!-- 楼栋id(各估价公司不同) -->
<input name="unitCode" value="" type="text" maxlength="16"/>
<!-- 楼栋号 -->
<input name="unitNo" value="" type="text" maxlength="100"/>
<!-- 房间id(各估价公司不同) -->
<input name="roomCode" value="" type="text" maxlength="16"/>
<!-- 房号 -->
<input name="roomNo" value="" type="text" maxlength="100"/>
<!-- 房屋面积 -->
<input name="area" value="" type="text" maxlength="18"/>
<!-- 房屋类型(别墅，公寓) -->
<input name="houseType" value="" type="text" maxlength="16"/>
<!-- 总楼层 -->
<input name="totalFloor" value="" type="text" maxlength="3"/>
<!-- 当前楼层 -->
<input name="currentFloor" value="" type="text" maxlength="3"/>
<!-- 询价公司编号 -->
<input name="companyCode" value="" type="text" maxlength="36"/>
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
<input name="bpsHouseInfo.id" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsHouseInfo.code" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input name="bpsHouseInfo.cityCode" value="" type="text" maxlength="36"/>
<!-- 关联城市名称 -->
<input name="bpsHouseInfo.cityName" value="" type="text" maxlength="100"/>
<!-- 关联小区id(各估价公司不同) -->
<input name="bpsHouseInfo.plotsCode" value="" type="text" maxlength="36"/>
<!-- 小区名称 -->
<input name="bpsHouseInfo.plotName" value="" type="text" maxlength="255"/>
<!-- 房屋地址 -->
<input name="bpsHouseInfo.address" value="" type="text" maxlength="100"/>
<!-- 楼栋id(各估价公司不同) -->
<input name="bpsHouseInfo.unitCode" value="" type="text" maxlength="16"/>
<!-- 楼栋号 -->
<input name="bpsHouseInfo.unitNo" value="" type="text" maxlength="100"/>
<!-- 房间id(各估价公司不同) -->
<input name="bpsHouseInfo.roomCode" value="" type="text" maxlength="16"/>
<!-- 房号 -->
<input name="bpsHouseInfo.roomNo" value="" type="text" maxlength="100"/>
<!-- 房屋面积 -->
<input name="bpsHouseInfo.area" value="" type="text" maxlength="18"/>
<!-- 房屋类型(别墅，公寓) -->
<input name="bpsHouseInfo.houseType" value="" type="text" maxlength="16"/>
<!-- 总楼层 -->
<input name="bpsHouseInfo.totalFloor" value="" type="text" maxlength="3"/>
<!-- 当前楼层 -->
<input name="bpsHouseInfo.currentFloor" value="" type="text" maxlength="3"/>
<!-- 询价公司编号 -->
<input name="bpsHouseInfo.companyCode" value="" type="text" maxlength="36"/>
<!-- 状态(1可用，0不可用) -->
<input name="bpsHouseInfo.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="bpsHouseInfo.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="bpsHouseInfo.dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BHI_ID" name="id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BHI_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input id="BHI_CITY_CODE" name="cityCode" value="" type="text" maxlength="36"/>
<!-- 关联城市名称 -->
<input id="BHI_CITY_NAME" name="cityName" value="" type="text" maxlength="100"/>
<!-- 关联小区id(各估价公司不同) -->
<input id="BHI_PLOTS_CODE" name="plotsCode" value="" type="text" maxlength="36"/>
<!-- 小区名称 -->
<input id="BHI_PLOT_NAME" name="plotName" value="" type="text" maxlength="255"/>
<!-- 房屋地址 -->
<input id="BHI_ADDRESS" name="address" value="" type="text" maxlength="100"/>
<!-- 楼栋id(各估价公司不同) -->
<input id="BHI_UNIT_CODE" name="unitCode" value="" type="text" maxlength="16"/>
<!-- 楼栋号 -->
<input id="BHI_UNIT_NO" name="unitNo" value="" type="text" maxlength="100"/>
<!-- 房间id(各估价公司不同) -->
<input id="BHI_ROOM_CODE" name="roomCode" value="" type="text" maxlength="16"/>
<!-- 房号 -->
<input id="BHI_ROOM_NO" name="roomNo" value="" type="text" maxlength="100"/>
<!-- 房屋面积 -->
<input id="BHI_AREA" name="area" value="" type="text" maxlength="18"/>
<!-- 房屋类型(别墅，公寓) -->
<input id="BHI_HOUSE_TYPE" name="houseType" value="" type="text" maxlength="16"/>
<!-- 总楼层 -->
<input id="BHI_TOTAL_FLOOR" name="totalFloor" value="" type="text" maxlength="3"/>
<!-- 当前楼层 -->
<input id="BHI_CURRENT_FLOOR" name="currentFloor" value="" type="text" maxlength="3"/>
<!-- 询价公司编号 -->
<input id="BHI_COMPANY_CODE" name="companyCode" value="" type="text" maxlength="36"/>
<!-- 状态(1可用，0不可用) -->
<input id="BHI_STATUS" name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BHI_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BHI_DATE_MODIFIED" name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BHI_ID" name="bpsHouseInfo.id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BHI_CODE" name="bpsHouseInfo.code" value="" type="text" maxlength="36"/>
<!-- 关联城市 -->
<input id="BHI_CITY_CODE" name="bpsHouseInfo.cityCode" value="" type="text" maxlength="36"/>
<!-- 关联城市名称 -->
<input id="BHI_CITY_NAME" name="bpsHouseInfo.cityName" value="" type="text" maxlength="100"/>
<!-- 关联小区id(各估价公司不同) -->
<input id="BHI_PLOTS_CODE" name="bpsHouseInfo.plotsCode" value="" type="text" maxlength="36"/>
<!-- 小区名称 -->
<input id="BHI_PLOT_NAME" name="bpsHouseInfo.plotName" value="" type="text" maxlength="255"/>
<!-- 房屋地址 -->
<input id="BHI_ADDRESS" name="bpsHouseInfo.address" value="" type="text" maxlength="100"/>
<!-- 楼栋id(各估价公司不同) -->
<input id="BHI_UNIT_CODE" name="bpsHouseInfo.unitCode" value="" type="text" maxlength="16"/>
<!-- 楼栋号 -->
<input id="BHI_UNIT_NO" name="bpsHouseInfo.unitNo" value="" type="text" maxlength="100"/>
<!-- 房间id(各估价公司不同) -->
<input id="BHI_ROOM_CODE" name="bpsHouseInfo.roomCode" value="" type="text" maxlength="16"/>
<!-- 房号 -->
<input id="BHI_ROOM_NO" name="bpsHouseInfo.roomNo" value="" type="text" maxlength="100"/>
<!-- 房屋面积 -->
<input id="BHI_AREA" name="bpsHouseInfo.area" value="" type="text" maxlength="18"/>
<!-- 房屋类型(别墅，公寓) -->
<input id="BHI_HOUSE_TYPE" name="bpsHouseInfo.houseType" value="" type="text" maxlength="16"/>
<!-- 总楼层 -->
<input id="BHI_TOTAL_FLOOR" name="bpsHouseInfo.totalFloor" value="" type="text" maxlength="3"/>
<!-- 当前楼层 -->
<input id="BHI_CURRENT_FLOOR" name="bpsHouseInfo.currentFloor" value="" type="text" maxlength="3"/>
<!-- 询价公司编号 -->
<input id="BHI_COMPANY_CODE" name="bpsHouseInfo.companyCode" value="" type="text" maxlength="36"/>
<!-- 状态(1可用，0不可用) -->
<input id="BHI_STATUS" name="bpsHouseInfo.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BHI_DATE_CREATED" name="bpsHouseInfo.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BHI_DATE_MODIFIED" name="bpsHouseInfo.dateModified" value="" type="text"/>




--------------------------------------------------------
 */


    