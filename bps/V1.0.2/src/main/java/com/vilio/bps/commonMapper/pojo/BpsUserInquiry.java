package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 用户询价请求表
 * @数表名称 BPS_USER_INQUIRY
 * @开发日期 2017-06-13
 * @技术服务 www.fwjava.com
 */
public class BpsUserInquiry implements Serializable {

    /**
     * (必填项)(主键ID)
     */
    private Integer id               = null;
    /**
     * 
     */
    private String serialNo          = null;
    /**
     * 
     */
    private Date dateCreated         = null;
    /**
     * 
     */
    private Date dateModified        = null;
    /**
     * 询价用户号
     */
    private String userId            = null;
    /**
     * 询价用户姓名
     */
    private String userName          = null;
    /**
     * 询价用户所在公司id
     */
    private String companyId         = null;
    /**
     * 询价用户所在公司名称
     */
    private String companyName       = null;
    /**
     * 询价用户所在部门id
     */
    private String departmentId      = null;
    /**
     * 询价用户所在部门名称
     */
    private String departmentName    = null;
    /**
     * 失效时间
     */
    private Date deadline            = null;
    /**
     * 状态(00创建，01失败，02完成，03人工询价中，04风控审批中)
     */
    private String status            = null;

    /**
     * 排序
     */
    private String orderBy           = null;

    //询价发起系统
    private String sourceSystem;
    //城市
    private String  cityCode;
    //地址
    private String address;
    //评估价格
    private BigDecimal assessmentPrice;
    //评估时间
    private Date assessmentTime;
    //房屋类型
    private String houseType;

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAssessmentPrice() {
        return assessmentPrice;
    }

    public void setAssessmentPrice(BigDecimal assessmentPrice) {
        this.assessmentPrice = assessmentPrice;
    }

    public Date getAssessmentTime() {
        return assessmentTime;
    }

    public void setAssessmentTime(Date assessmentTime) {
        this.assessmentTime = assessmentTime;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
/*
     *--------------------------------------------------
     * Getter方法区
     *--------------------------------------------------
     */

    /**
     * (必填项)(主键ID)
     */
    public Integer getId() {
        return id;
    }
    /**
     * 
     */
    public String getSerialNo() {
        return trim(serialNo);
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
    public Date getDateModified() {
        return dateModified;
    }
    /**
     * 询价用户号
     */
    public String getUserId() {
        return trim(userId);
    }
    /**
     * 询价用户姓名
     */
    public String getUserName() {
        return trim(userName);
    }
    /**
     * 询价用户所在公司id
     */
    public String getCompanyId() {
        return trim(companyId);
    }
    /**
     * 询价用户所在公司名称
     */
    public String getCompanyName() {
        return trim(companyName);
    }
    /**
     * 询价用户所在部门id
     */
    public String getDepartmentId() {
        return trim(departmentId);
    }
    /**
     * 询价用户所在部门名称
     */
    public String getDepartmentName() {
        return trim(departmentName);
    }
    /**
     * 失效时间
     */
    public Date getDeadline() {
        return deadline;
    }
    /**
     * 状态(00创建，01失败，02完成，03人工询价中，04风控审批中)
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
     * (必填项)(主键ID)
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
    /**
     * 询价用户号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * 询价用户姓名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * 询价用户所在公司id
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    /**
     * 询价用户所在公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * 询价用户所在部门id
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    /**
     * 询价用户所在部门名称
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    /**
     * 失效时间
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    /**
     * 状态(00创建，01失败，02完成，03人工询价中，04风控审批中)
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
     * (格式化)
     */
    public String getDateModifiedChar() {
        return getDate(dateModified);
    }
    public void setDateModifiedChar(String dateModifiedChar) {
        this.dateModified = getDate(dateModifiedChar);
    }
    /**
     * (格式化)
     */
    public String getDateModifiedCharAll() {
        return getDateTime(dateModified);
    }
    public void setDateModifiedCharAll(String dateModifiedCharAll) {
        this.dateModified = getDate(dateModifiedCharAll);
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

// 用户询价请求表
BpsUserInquiry bpsUserInquiry = new BpsUserInquiry();

// (必填项)(主键ID)
bpsUserInquiry.setId(  );
// 
bpsUserInquiry.setSerialNo(  );
// 
bpsUserInquiry.setDateCreated(  );
// 
bpsUserInquiry.setDateModified(  );
// 询价用户号
bpsUserInquiry.setUserId(  );
// 询价用户姓名
bpsUserInquiry.setUserName(  );
// 询价用户所在公司id
bpsUserInquiry.setCompanyId(  );
// 询价用户所在公司名称
bpsUserInquiry.setCompanyName(  );
// 询价用户所在部门id
bpsUserInquiry.setDepartmentId(  );
// 询价用户所在部门名称
bpsUserInquiry.setDepartmentName(  );
// 失效时间
bpsUserInquiry.setDeadline(  );
// 状态(00创建，01失败，02完成，03人工询价中，04风控审批中)
bpsUserInquiry.setStatus(  );


//------ 自定义部分 ------

// (格式化)
bpsUserInquiry.setDateCreatedChar(  );
// (格式化)
bpsUserInquiry.setDateModifiedChar(  );
// 失效时间(格式化)
bpsUserInquiry.setDeadlineChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 用户询价请求表
BpsUserInquiry bpsUserInquiry = new BpsUserInquiry();

// (必填项)(主键ID)
bpsUserInquiry.getId();
// 
bpsUserInquiry.getSerialNo();
// 
bpsUserInquiry.getDateCreated();
// 
bpsUserInquiry.getDateModified();
// 询价用户号
bpsUserInquiry.getUserId();
// 询价用户姓名
bpsUserInquiry.getUserName();
// 询价用户所在公司id
bpsUserInquiry.getCompanyId();
// 询价用户所在公司名称
bpsUserInquiry.getCompanyName();
// 询价用户所在部门id
bpsUserInquiry.getDepartmentId();
// 询价用户所在部门名称
bpsUserInquiry.getDepartmentName();
// 失效时间
bpsUserInquiry.getDeadline();
// 状态(00创建，01失败，02完成，03人工询价中，04风控审批中)
bpsUserInquiry.getStatus();


//------ 自定义部分 ------

// (格式化)
bpsUserInquiry.getDateCreatedChar();
// (格式化)
bpsUserInquiry.getDateModifiedChar();
// 失效时间(格式化)
bpsUserInquiry.getDeadlineChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 用户询价请求表
BpsUserInquiry bpsUserInquiry = new BpsUserInquiry();

// (必填项)(主键ID)
bpsUserInquiry.setId( bpsUserInquiry2.getId() );
// 
bpsUserInquiry.setSerialNo( bpsUserInquiry2.getSerialNo() );
// 
bpsUserInquiry.setDateCreated( bpsUserInquiry2.getDateCreated() );
// 
bpsUserInquiry.setDateModified( bpsUserInquiry2.getDateModified() );
// 询价用户号
bpsUserInquiry.setUserId( bpsUserInquiry2.getUserId() );
// 询价用户姓名
bpsUserInquiry.setUserName( bpsUserInquiry2.getUserName() );
// 询价用户所在公司id
bpsUserInquiry.setCompanyId( bpsUserInquiry2.getCompanyId() );
// 询价用户所在公司名称
bpsUserInquiry.setCompanyName( bpsUserInquiry2.getCompanyName() );
// 询价用户所在部门id
bpsUserInquiry.setDepartmentId( bpsUserInquiry2.getDepartmentId() );
// 询价用户所在部门名称
bpsUserInquiry.setDepartmentName( bpsUserInquiry2.getDepartmentName() );
// 失效时间
bpsUserInquiry.setDeadline( bpsUserInquiry2.getDeadline() );
// 状态(00创建，01失败，02完成，03人工询价中，04风控审批中)
bpsUserInquiry.setStatus( bpsUserInquiry2.getStatus() );


//------ 自定义部分 ------

// (格式化)
bpsUserInquiry.setDateCreatedChar( bpsUserInquiry2.getDateCreatedChar() );
// (格式化)
bpsUserInquiry.setDateModifiedChar( bpsUserInquiry2.getDateModifiedChar() );
// 失效时间(格式化)
bpsUserInquiry.setDeadlineChar( bpsUserInquiry2.getDeadlineChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="serialNo" value="" type="text" maxlength="36"/>
<!--  -->
<input name="dateCreated" value="" type="text"/>
<!--  -->
<input name="dateModified" value="" type="text"/>
<!-- 询价用户号 -->
<input name="userId" value="" type="text" maxlength="36"/>
<!-- 询价用户姓名 -->
<input name="userName" value="" type="text" maxlength="50"/>
<!-- 询价用户所在公司id -->
<input name="companyId" value="" type="text" maxlength="36"/>
<!-- 询价用户所在公司名称 -->
<input name="companyName" value="" type="text" maxlength="50"/>
<!-- 询价用户所在部门id -->
<input name="departmentId" value="" type="text" maxlength="36"/>
<!-- 询价用户所在部门名称 -->
<input name="departmentName" value="" type="text" maxlength="50"/>
<!-- 失效时间 -->
<input name="deadline" value="" type="text"/>
<!-- 状态(00创建，01失败，02完成，03人工询价中，04风控审批中) -->
<input name="status" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="bpsUserInquiry.id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="bpsUserInquiry.serialNo" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsUserInquiry.dateCreated" value="" type="text"/>
<!--  -->
<input name="bpsUserInquiry.dateModified" value="" type="text"/>
<!-- 询价用户号 -->
<input name="bpsUserInquiry.userId" value="" type="text" maxlength="36"/>
<!-- 询价用户姓名 -->
<input name="bpsUserInquiry.userName" value="" type="text" maxlength="50"/>
<!-- 询价用户所在公司id -->
<input name="bpsUserInquiry.companyId" value="" type="text" maxlength="36"/>
<!-- 询价用户所在公司名称 -->
<input name="bpsUserInquiry.companyName" value="" type="text" maxlength="50"/>
<!-- 询价用户所在部门id -->
<input name="bpsUserInquiry.departmentId" value="" type="text" maxlength="36"/>
<!-- 询价用户所在部门名称 -->
<input name="bpsUserInquiry.departmentName" value="" type="text" maxlength="50"/>
<!-- 失效时间 -->
<input name="bpsUserInquiry.deadline" value="" type="text"/>
<!-- 状态(00创建，01失败，02完成，03人工询价中，04风控审批中) -->
<input name="bpsUserInquiry.status" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BUI_ID" name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BUI_SERIAL_NO" name="serialNo" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BUI_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!--  -->
<input id="BUI_DATE_MODIFIED" name="dateModified" value="" type="text"/>
<!-- 询价用户号 -->
<input id="BUI_USER_ID" name="userId" value="" type="text" maxlength="36"/>
<!-- 询价用户姓名 -->
<input id="BUI_USER_NAME" name="userName" value="" type="text" maxlength="50"/>
<!-- 询价用户所在公司id -->
<input id="BUI_COMPANY_ID" name="companyId" value="" type="text" maxlength="36"/>
<!-- 询价用户所在公司名称 -->
<input id="BUI_COMPANY_NAME" name="companyName" value="" type="text" maxlength="50"/>
<!-- 询价用户所在部门id -->
<input id="BUI_DEPARTMENT_ID" name="departmentId" value="" type="text" maxlength="36"/>
<!-- 询价用户所在部门名称 -->
<input id="BUI_DEPARTMENT_NAME" name="departmentName" value="" type="text" maxlength="50"/>
<!-- 失效时间 -->
<input id="BUI_DEADLINE" name="deadline" value="" type="text"/>
<!-- 状态(00创建，01失败，02完成，03人工询价中，04风控审批中) -->
<input id="BUI_STATUS" name="status" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BUI_ID" name="bpsUserInquiry.id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BUI_SERIAL_NO" name="bpsUserInquiry.serialNo" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BUI_DATE_CREATED" name="bpsUserInquiry.dateCreated" value="" type="text"/>
<!--  -->
<input id="BUI_DATE_MODIFIED" name="bpsUserInquiry.dateModified" value="" type="text"/>
<!-- 询价用户号 -->
<input id="BUI_USER_ID" name="bpsUserInquiry.userId" value="" type="text" maxlength="36"/>
<!-- 询价用户姓名 -->
<input id="BUI_USER_NAME" name="bpsUserInquiry.userName" value="" type="text" maxlength="50"/>
<!-- 询价用户所在公司id -->
<input id="BUI_COMPANY_ID" name="bpsUserInquiry.companyId" value="" type="text" maxlength="36"/>
<!-- 询价用户所在公司名称 -->
<input id="BUI_COMPANY_NAME" name="bpsUserInquiry.companyName" value="" type="text" maxlength="50"/>
<!-- 询价用户所在部门id -->
<input id="BUI_DEPARTMENT_ID" name="bpsUserInquiry.departmentId" value="" type="text" maxlength="36"/>
<!-- 询价用户所在部门名称 -->
<input id="BUI_DEPARTMENT_NAME" name="bpsUserInquiry.departmentName" value="" type="text" maxlength="50"/>
<!-- 失效时间 -->
<input id="BUI_DEADLINE" name="bpsUserInquiry.deadline" value="" type="text"/>
<!-- 状态(00创建，01失败，02完成，03人工询价中，04风控审批中) -->
<input id="BUI_STATUS" name="bpsUserInquiry.status" value="" type="text" maxlength="2"/>




--------------------------------------------------------
 */


    