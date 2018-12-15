package com.vilio.nlbs.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 
 * @数表名称 NLBS_MESSAGE_RECEIVE
 * @开发日期 2017-06-21
 * @技术服务 www.fwjava.com
 */
public class NlbsMessageReceive implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id                     = null;
    /**
     *
     */
    private String code                = null;
    /**
     * 消息序列号(消息平台序号)
     */
    private String serialNo                = null;
    /**
     * 消息标题(必填项)
     */
    private String title                   = null;
    /**
     * 消息内容(必填项)
     */
    private String content                 = null;
    /**
     * 发送方公司编号
     */
    private String senderCompanyCode       = null;
    /**
     * 
     */
    private String senderCompanyName       = null;
    /**
     * 发送方所在部门编号
     */
    private String senderDepartmentCode    = null;
    /**
     * 发送方所在部门名称
     */
    private String senderDepartmentName    = null;
    /**
     * 发送用户编号(必填项)
     */
    private String senderIdentityId        = null;
    /**
     * 发送用户名
     */
    private String senderName              = null;
    /**
     * 接收用户编号(必填项)
     */
    private String receiverIdentityId      = null;
    /**
     * 
     */
    private Date createTime                = null;
    //消息读取时间
    private Date readTime = null;
    //消息读取渠道
    private String readChannel = null;
    /**
     * 
     */
    private String internalParam           = null;
    /**
     * 状态(0未读1已读)
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
     *
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 消息序列号(消息平台序号)
     */
    public String getSerialNo() {
        return trim(serialNo);
    }
    /**
     * 消息标题(必填项)
     */
    public String getTitle() {
        return trim(title);
    }
    /**
     * 消息内容(必填项)
     */
    public String getContent() {
        return trim(content);
    }
    /**
     * 发送方公司编号
     */
    public String getSenderCompanyCode() {
        return trim(senderCompanyCode);
    }
    /**
     * 
     */
    public String getSenderCompanyName() {
        return trim(senderCompanyName);
    }
    /**
     * 发送方所在部门编号
     */
    public String getSenderDepartmentCode() {
        return trim(senderDepartmentCode);
    }
    /**
     * 发送方所在部门名称
     */
    public String getSenderDepartmentName() {
        return trim(senderDepartmentName);
    }
    /**
     * 发送用户编号(必填项)
     */
    public String getSenderIdentityId() {
        return trim(senderIdentityId);
    }
    /**
     * 发送用户名
     */
    public String getSenderName() {
        return trim(senderName);
    }
    /**
     * 接收用户编号(必填项)
     */
    public String getReceiverIdentityId() {
        return trim(receiverIdentityId);
    }
    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public String getReadChannel() {
        return readChannel;
    }

    /**
     * 
     */
    public String getInternalParam() {
        return trim(internalParam);
    }
    /**
     * 状态(0未读1已读)
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
     *
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 消息序列号(消息平台序号)
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    /**
     * 消息标题(必填项)
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * 消息内容(必填项)
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 发送方公司编号
     */
    public void setSenderCompanyCode(String senderCompanyCode) {
        this.senderCompanyCode = senderCompanyCode;
    }
    /**
     * 
     */
    public void setSenderCompanyName(String senderCompanyName) {
        this.senderCompanyName = senderCompanyName;
    }
    /**
     * 发送方所在部门编号
     */
    public void setSenderDepartmentCode(String senderDepartmentCode) {
        this.senderDepartmentCode = senderDepartmentCode;
    }
    /**
     * 发送方所在部门名称
     */
    public void setSenderDepartmentName(String senderDepartmentName) {
        this.senderDepartmentName = senderDepartmentName;
    }
    /**
     * 发送用户编号(必填项)
     */
    public void setSenderIdentityId(String senderIdentityId) {
        this.senderIdentityId = senderIdentityId;
    }
    /**
     * 发送用户名
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    /**
     * 接收用户编号(必填项)
     */
    public void setReceiverIdentityId(String receiverIdentityId) {
        this.receiverIdentityId = receiverIdentityId;
    }
    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public void setReadChannel(String readChannel) {
        this.readChannel = readChannel;
    }

    /**
     * 
     */
    public void setInternalParam(String internalParam) {
        this.internalParam = internalParam;
    }
    /**
     * 状态(0未读1已读)
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
    public String getCreateTimeChar() {
        return getDate(createTime);
    }
    public void setCreateTimeChar(String createTimeChar) {
        this.createTime = getDate(createTimeChar);
    }
    public String getReadTimeChar() {
        return getDate(readTime);
    }
    public void setReadTimeChar(String readTime) {
        this.readTime = getDate(readTime);
    }
    /**
     * (格式化)
     */
    public String getReadTimeCharAll() {
        return getDateTime(readTime);
    }
    public void setReadTimeCharAll(String readTime) {
        this.readTime = getDate(readTime);
    }
    public String getCreateTimeCharAll() {
        return getDateTime(createTime);
    }
    public void setCreateTimeCharAll(String createTimeCharAll) {
        this.createTime = getDate(createTimeCharAll);
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

// 
NlbsMessageReceive nlbsMessageReceive = new NlbsMessageReceive();

// 数据主键(必填项)(主键ID)
nlbsMessageReceive.setId(  );
// 消息序列号(消息平台序号)
nlbsMessageReceive.setSerialNo(  );
// 消息标题(必填项)
nlbsMessageReceive.setTitle(  );
// 消息内容(必填项)
nlbsMessageReceive.setContent(  );
// 发送方公司编号
nlbsMessageReceive.setSenderCompanyCode(  );
// 
nlbsMessageReceive.setSenderCompanyName(  );
// 发送方所在部门编号
nlbsMessageReceive.setSenderDepartmentCode(  );
// 发送方所在部门名称
nlbsMessageReceive.setSenderDepartmentName(  );
// 发送用户编号(必填项)
nlbsMessageReceive.setSenderIdentityId(  );
// 发送用户名
nlbsMessageReceive.setSenderName(  );
// 接收用户编号(必填项)
nlbsMessageReceive.setReceiverIdentityId(  );
// 
nlbsMessageReceive.setCreateTime(  );
// 
nlbsMessageReceive.setInternalParam(  );
// 状态(0未读1已读)
nlbsMessageReceive.setStatus(  );


//------ 自定义部分 ------

// (格式化)
nlbsMessageReceive.setCreateTimeChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 
NlbsMessageReceive nlbsMessageReceive = new NlbsMessageReceive();

// 数据主键(必填项)(主键ID)
nlbsMessageReceive.getId();
// 消息序列号(消息平台序号)
nlbsMessageReceive.getSerialNo();
// 消息标题(必填项)
nlbsMessageReceive.getTitle();
// 消息内容(必填项)
nlbsMessageReceive.getContent();
// 发送方公司编号
nlbsMessageReceive.getSenderCompanyCode();
// 
nlbsMessageReceive.getSenderCompanyName();
// 发送方所在部门编号
nlbsMessageReceive.getSenderDepartmentCode();
// 发送方所在部门名称
nlbsMessageReceive.getSenderDepartmentName();
// 发送用户编号(必填项)
nlbsMessageReceive.getSenderIdentityId();
// 发送用户名
nlbsMessageReceive.getSenderName();
// 接收用户编号(必填项)
nlbsMessageReceive.getReceiverIdentityId();
// 
nlbsMessageReceive.getCreateTime();
// 
nlbsMessageReceive.getInternalParam();
// 状态(0未读1已读)
nlbsMessageReceive.getStatus();


//------ 自定义部分 ------

// (格式化)
nlbsMessageReceive.getCreateTimeChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 
NlbsMessageReceive nlbsMessageReceive = new NlbsMessageReceive();

// 数据主键(必填项)(主键ID)
nlbsMessageReceive.setId( nlbsMessageReceive2.getId() );
// 消息序列号(消息平台序号)
nlbsMessageReceive.setSerialNo( nlbsMessageReceive2.getSerialNo() );
// 消息标题(必填项)
nlbsMessageReceive.setTitle( nlbsMessageReceive2.getTitle() );
// 消息内容(必填项)
nlbsMessageReceive.setContent( nlbsMessageReceive2.getContent() );
// 发送方公司编号
nlbsMessageReceive.setSenderCompanyCode( nlbsMessageReceive2.getSenderCompanyCode() );
// 
nlbsMessageReceive.setSenderCompanyName( nlbsMessageReceive2.getSenderCompanyName() );
// 发送方所在部门编号
nlbsMessageReceive.setSenderDepartmentCode( nlbsMessageReceive2.getSenderDepartmentCode() );
// 发送方所在部门名称
nlbsMessageReceive.setSenderDepartmentName( nlbsMessageReceive2.getSenderDepartmentName() );
// 发送用户编号(必填项)
nlbsMessageReceive.setSenderIdentityId( nlbsMessageReceive2.getSenderIdentityId() );
// 发送用户名
nlbsMessageReceive.setSenderName( nlbsMessageReceive2.getSenderName() );
// 接收用户编号(必填项)
nlbsMessageReceive.setReceiverIdentityId( nlbsMessageReceive2.getReceiverIdentityId() );
// 
nlbsMessageReceive.setCreateTime( nlbsMessageReceive2.getCreateTime() );
// 
nlbsMessageReceive.setInternalParam( nlbsMessageReceive2.getInternalParam() );
// 状态(0未读1已读)
nlbsMessageReceive.setStatus( nlbsMessageReceive2.getStatus() );


//------ 自定义部分 ------

// (格式化)
nlbsMessageReceive.setCreateTimeChar( nlbsMessageReceive2.getCreateTimeChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="11"/>
<!-- 消息序列号(消息平台序号) -->
<input name="serialNo" value="" type="text" maxlength="50"/>
<!-- 消息标题 -->
<input name="title" value="" type="text" maxlength="255"/>
<!-- 消息内容 -->
<input name="content" value="" type="text" maxlength="2000"/>
<!-- 发送方公司编号 -->
<input name="senderCompanyCode" value="" type="text" maxlength="100"/>
<!--  -->
<input name="senderCompanyName" value="" type="text" maxlength="255"/>
<!-- 发送方所在部门编号 -->
<input name="senderDepartmentCode" value="" type="text" maxlength="100"/>
<!-- 发送方所在部门名称 -->
<input name="senderDepartmentName" value="" type="text" maxlength="255"/>
<!-- 发送用户编号 -->
<input name="senderIdentityId" value="" type="text" maxlength="100"/>
<!-- 发送用户名 -->
<input name="senderName" value="" type="text" maxlength="255"/>
<!-- 接收用户编号 -->
<input name="receiverIdentityId" value="" type="text" maxlength="100"/>
<!--  -->
<input name="createTime" value="" type="text"/>
<!--  -->
<input name="internalParam" value="" type="text" maxlength="2000"/>
<!-- 状态(0未读1已读) -->
<input name="status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="nlbsMessageReceive.id" value="" type="text" maxlength="11"/>
<!-- 消息序列号(消息平台序号) -->
<input name="nlbsMessageReceive.serialNo" value="" type="text" maxlength="50"/>
<!-- 消息标题 -->
<input name="nlbsMessageReceive.title" value="" type="text" maxlength="255"/>
<!-- 消息内容 -->
<input name="nlbsMessageReceive.content" value="" type="text" maxlength="2000"/>
<!-- 发送方公司编号 -->
<input name="nlbsMessageReceive.senderCompanyCode" value="" type="text" maxlength="100"/>
<!--  -->
<input name="nlbsMessageReceive.senderCompanyName" value="" type="text" maxlength="255"/>
<!-- 发送方所在部门编号 -->
<input name="nlbsMessageReceive.senderDepartmentCode" value="" type="text" maxlength="100"/>
<!-- 发送方所在部门名称 -->
<input name="nlbsMessageReceive.senderDepartmentName" value="" type="text" maxlength="255"/>
<!-- 发送用户编号 -->
<input name="nlbsMessageReceive.senderIdentityId" value="" type="text" maxlength="100"/>
<!-- 发送用户名 -->
<input name="nlbsMessageReceive.senderName" value="" type="text" maxlength="255"/>
<!-- 接收用户编号 -->
<input name="nlbsMessageReceive.receiverIdentityId" value="" type="text" maxlength="100"/>
<!--  -->
<input name="nlbsMessageReceive.createTime" value="" type="text"/>
<!--  -->
<input name="nlbsMessageReceive.internalParam" value="" type="text" maxlength="2000"/>
<!-- 状态(0未读1已读) -->
<input name="nlbsMessageReceive.status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NMR_ID" name="id" value="" type="text" maxlength="11"/>
<!-- 消息序列号(消息平台序号) -->
<input id="NMR_SERIAL_NO" name="serialNo" value="" type="text" maxlength="50"/>
<!-- 消息标题 -->
<input id="NMR_TITLE" name="title" value="" type="text" maxlength="255"/>
<!-- 消息内容 -->
<input id="NMR_CONTENT" name="content" value="" type="text" maxlength="2000"/>
<!-- 发送方公司编号 -->
<input id="NMR_SENDER_COMPANY_CODE" name="senderCompanyCode" value="" type="text" maxlength="100"/>
<!--  -->
<input id="NMR_SENDER_COMPANY_NAME" name="senderCompanyName" value="" type="text" maxlength="255"/>
<!-- 发送方所在部门编号 -->
<input id="NMR_SENDER_DEPARTMENT_CODE" name="senderDepartmentCode" value="" type="text" maxlength="100"/>
<!-- 发送方所在部门名称 -->
<input id="NMR_SENDER_DEPARTMENT_NAME" name="senderDepartmentName" value="" type="text" maxlength="255"/>
<!-- 发送用户编号 -->
<input id="NMR_SENDER_IDENTITY_ID" name="senderIdentityId" value="" type="text" maxlength="100"/>
<!-- 发送用户名 -->
<input id="NMR_SENDER_NAME" name="senderName" value="" type="text" maxlength="255"/>
<!-- 接收用户编号 -->
<input id="NMR_RECEIVER_IDENTITY_ID" name="receiverIdentityId" value="" type="text" maxlength="100"/>
<!--  -->
<input id="NMR_CREATE_TIME" name="createTime" value="" type="text"/>
<!--  -->
<input id="NMR_INTERNAL_PARAM" name="internalParam" value="" type="text" maxlength="2000"/>
<!-- 状态(0未读1已读) -->
<input id="NMR_STATUS" name="status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NMR_ID" name="nlbsMessageReceive.id" value="" type="text" maxlength="11"/>
<!-- 消息序列号(消息平台序号) -->
<input id="NMR_SERIAL_NO" name="nlbsMessageReceive.serialNo" value="" type="text" maxlength="50"/>
<!-- 消息标题 -->
<input id="NMR_TITLE" name="nlbsMessageReceive.title" value="" type="text" maxlength="255"/>
<!-- 消息内容 -->
<input id="NMR_CONTENT" name="nlbsMessageReceive.content" value="" type="text" maxlength="2000"/>
<!-- 发送方公司编号 -->
<input id="NMR_SENDER_COMPANY_CODE" name="nlbsMessageReceive.senderCompanyCode" value="" type="text" maxlength="100"/>
<!--  -->
<input id="NMR_SENDER_COMPANY_NAME" name="nlbsMessageReceive.senderCompanyName" value="" type="text" maxlength="255"/>
<!-- 发送方所在部门编号 -->
<input id="NMR_SENDER_DEPARTMENT_CODE" name="nlbsMessageReceive.senderDepartmentCode" value="" type="text" maxlength="100"/>
<!-- 发送方所在部门名称 -->
<input id="NMR_SENDER_DEPARTMENT_NAME" name="nlbsMessageReceive.senderDepartmentName" value="" type="text" maxlength="255"/>
<!-- 发送用户编号 -->
<input id="NMR_SENDER_IDENTITY_ID" name="nlbsMessageReceive.senderIdentityId" value="" type="text" maxlength="100"/>
<!-- 发送用户名 -->
<input id="NMR_SENDER_NAME" name="nlbsMessageReceive.senderName" value="" type="text" maxlength="255"/>
<!-- 接收用户编号 -->
<input id="NMR_RECEIVER_IDENTITY_ID" name="nlbsMessageReceive.receiverIdentityId" value="" type="text" maxlength="100"/>
<!--  -->
<input id="NMR_CREATE_TIME" name="nlbsMessageReceive.createTime" value="" type="text"/>
<!--  -->
<input id="NMR_INTERNAL_PARAM" name="nlbsMessageReceive.internalParam" value="" type="text" maxlength="2000"/>
<!-- 状态(0未读1已读) -->
<input id="NMR_STATUS" name="nlbsMessageReceive.status" value="" type="text" maxlength="1"/>




--------------------------------------------------------
 */


    