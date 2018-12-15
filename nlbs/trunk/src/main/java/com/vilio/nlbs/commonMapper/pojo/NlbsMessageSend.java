package com.vilio.nlbs.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 
 * @数表名称 NLBS_MESSAGE_SEND
 * @开发日期 2017-06-21
 * @技术服务 www.fwjava.com
 */
public class NlbsMessageSend implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id                     = null;
    /**
     * 数据主键
     */
    private String code                     = null;
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
     * 发送方公司名称
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
    private String receiverName            = null;
    /**
     * 
     */
    private Date createTime                = null;
    /**
     * 
     */
    private String internalParam           = null;
    /**
     * 状态(0未发送1已发送2发送失败)
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
     * 发送方公司名称
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
    public String getReceiverName() {
        return trim(receiverName);
    }
    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * 
     */
    public String getInternalParam() {
        return trim(internalParam);
    }
    /**
     * 状态(0未发送1已发送2发送失败)
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
     * 发送方公司名称
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
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 
     */
    public void setInternalParam(String internalParam) {
        this.internalParam = internalParam;
    }
    /**
     * 状态(0未发送1已发送2发送失败)
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
    /**
     * (格式化)
     */
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
NlbsMessageSend nlbsMessageSend = new NlbsMessageSend();

// 数据主键(必填项)(主键ID)
nlbsMessageSend.setId(  );
// 消息序列号(消息平台序号)
nlbsMessageSend.setSerialNo(  );
// 消息标题(必填项)
nlbsMessageSend.setTitle(  );
// 消息内容(必填项)
nlbsMessageSend.setContent(  );
// 发送方公司编号
nlbsMessageSend.setSenderCompanyCode(  );
// 发送方公司名称
nlbsMessageSend.setSenderCompanyName(  );
// 发送方所在部门编号
nlbsMessageSend.setSenderDepartmentCode(  );
// 发送方所在部门名称
nlbsMessageSend.setSenderDepartmentName(  );
// 发送用户编号(必填项)
nlbsMessageSend.setSenderIdentityId(  );
// 发送用户名
nlbsMessageSend.setSenderName(  );
// 接收用户编号(必填项)
nlbsMessageSend.setReceiverIdentityId(  );
// 
nlbsMessageSend.setReceiverName(  );
// 
nlbsMessageSend.setCreateTime(  );
// 
nlbsMessageSend.setInternalParam(  );
// 状态(0未发送1已发送2发送失败)
nlbsMessageSend.setStatus(  );


//------ 自定义部分 ------

// (格式化)
nlbsMessageSend.setCreateTimeChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 
NlbsMessageSend nlbsMessageSend = new NlbsMessageSend();

// 数据主键(必填项)(主键ID)
nlbsMessageSend.getId();
// 消息序列号(消息平台序号)
nlbsMessageSend.getSerialNo();
// 消息标题(必填项)
nlbsMessageSend.getTitle();
// 消息内容(必填项)
nlbsMessageSend.getContent();
// 发送方公司编号
nlbsMessageSend.getSenderCompanyCode();
// 发送方公司名称
nlbsMessageSend.getSenderCompanyName();
// 发送方所在部门编号
nlbsMessageSend.getSenderDepartmentCode();
// 发送方所在部门名称
nlbsMessageSend.getSenderDepartmentName();
// 发送用户编号(必填项)
nlbsMessageSend.getSenderIdentityId();
// 发送用户名
nlbsMessageSend.getSenderName();
// 接收用户编号(必填项)
nlbsMessageSend.getReceiverIdentityId();
// 
nlbsMessageSend.getReceiverName();
// 
nlbsMessageSend.getCreateTime();
// 
nlbsMessageSend.getInternalParam();
// 状态(0未发送1已发送2发送失败)
nlbsMessageSend.getStatus();


//------ 自定义部分 ------

// (格式化)
nlbsMessageSend.getCreateTimeChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 
NlbsMessageSend nlbsMessageSend = new NlbsMessageSend();

// 数据主键(必填项)(主键ID)
nlbsMessageSend.setId( nlbsMessageSend2.getId() );
// 消息序列号(消息平台序号)
nlbsMessageSend.setSerialNo( nlbsMessageSend2.getSerialNo() );
// 消息标题(必填项)
nlbsMessageSend.setTitle( nlbsMessageSend2.getTitle() );
// 消息内容(必填项)
nlbsMessageSend.setContent( nlbsMessageSend2.getContent() );
// 发送方公司编号
nlbsMessageSend.setSenderCompanyCode( nlbsMessageSend2.getSenderCompanyCode() );
// 发送方公司名称
nlbsMessageSend.setSenderCompanyName( nlbsMessageSend2.getSenderCompanyName() );
// 发送方所在部门编号
nlbsMessageSend.setSenderDepartmentCode( nlbsMessageSend2.getSenderDepartmentCode() );
// 发送方所在部门名称
nlbsMessageSend.setSenderDepartmentName( nlbsMessageSend2.getSenderDepartmentName() );
// 发送用户编号(必填项)
nlbsMessageSend.setSenderIdentityId( nlbsMessageSend2.getSenderIdentityId() );
// 发送用户名
nlbsMessageSend.setSenderName( nlbsMessageSend2.getSenderName() );
// 接收用户编号(必填项)
nlbsMessageSend.setReceiverIdentityId( nlbsMessageSend2.getReceiverIdentityId() );
// 
nlbsMessageSend.setReceiverName( nlbsMessageSend2.getReceiverName() );
// 
nlbsMessageSend.setCreateTime( nlbsMessageSend2.getCreateTime() );
// 
nlbsMessageSend.setInternalParam( nlbsMessageSend2.getInternalParam() );
// 状态(0未发送1已发送2发送失败)
nlbsMessageSend.setStatus( nlbsMessageSend2.getStatus() );


//------ 自定义部分 ------

// (格式化)
nlbsMessageSend.setCreateTimeChar( nlbsMessageSend2.getCreateTimeChar() );



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
<!-- 发送方公司名称 -->
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
<input name="receiverName" value="" type="text" maxlength="255"/>
<!--  -->
<input name="createTime" value="" type="text"/>
<!--  -->
<input name="internalParam" value="" type="text" maxlength="2000"/>
<!-- 状态(0未发送1已发送2发送失败) -->
<input name="status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="nlbsMessageSend.id" value="" type="text" maxlength="11"/>
<!-- 消息序列号(消息平台序号) -->
<input name="nlbsMessageSend.serialNo" value="" type="text" maxlength="50"/>
<!-- 消息标题 -->
<input name="nlbsMessageSend.title" value="" type="text" maxlength="255"/>
<!-- 消息内容 -->
<input name="nlbsMessageSend.content" value="" type="text" maxlength="2000"/>
<!-- 发送方公司编号 -->
<input name="nlbsMessageSend.senderCompanyCode" value="" type="text" maxlength="100"/>
<!-- 发送方公司名称 -->
<input name="nlbsMessageSend.senderCompanyName" value="" type="text" maxlength="255"/>
<!-- 发送方所在部门编号 -->
<input name="nlbsMessageSend.senderDepartmentCode" value="" type="text" maxlength="100"/>
<!-- 发送方所在部门名称 -->
<input name="nlbsMessageSend.senderDepartmentName" value="" type="text" maxlength="255"/>
<!-- 发送用户编号 -->
<input name="nlbsMessageSend.senderIdentityId" value="" type="text" maxlength="100"/>
<!-- 发送用户名 -->
<input name="nlbsMessageSend.senderName" value="" type="text" maxlength="255"/>
<!-- 接收用户编号 -->
<input name="nlbsMessageSend.receiverIdentityId" value="" type="text" maxlength="100"/>
<!--  -->
<input name="nlbsMessageSend.receiverName" value="" type="text" maxlength="255"/>
<!--  -->
<input name="nlbsMessageSend.createTime" value="" type="text"/>
<!--  -->
<input name="nlbsMessageSend.internalParam" value="" type="text" maxlength="2000"/>
<!-- 状态(0未发送1已发送2发送失败) -->
<input name="nlbsMessageSend.status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NMS_ID" name="id" value="" type="text" maxlength="11"/>
<!-- 消息序列号(消息平台序号) -->
<input id="NMS_SERIAL_NO" name="serialNo" value="" type="text" maxlength="50"/>
<!-- 消息标题 -->
<input id="NMS_TITLE" name="title" value="" type="text" maxlength="255"/>
<!-- 消息内容 -->
<input id="NMS_CONTENT" name="content" value="" type="text" maxlength="2000"/>
<!-- 发送方公司编号 -->
<input id="NMS_SENDER_COMPANY_CODE" name="senderCompanyCode" value="" type="text" maxlength="100"/>
<!-- 发送方公司名称 -->
<input id="NMS_SENDER_COMPANY_NAME" name="senderCompanyName" value="" type="text" maxlength="255"/>
<!-- 发送方所在部门编号 -->
<input id="NMS_SENDER_DEPARTMENT_CODE" name="senderDepartmentCode" value="" type="text" maxlength="100"/>
<!-- 发送方所在部门名称 -->
<input id="NMS_SENDER_DEPARTMENT_NAME" name="senderDepartmentName" value="" type="text" maxlength="255"/>
<!-- 发送用户编号 -->
<input id="NMS_SENDER_IDENTITY_ID" name="senderIdentityId" value="" type="text" maxlength="100"/>
<!-- 发送用户名 -->
<input id="NMS_SENDER_NAME" name="senderName" value="" type="text" maxlength="255"/>
<!-- 接收用户编号 -->
<input id="NMS_RECEIVER_IDENTITY_ID" name="receiverIdentityId" value="" type="text" maxlength="100"/>
<!--  -->
<input id="NMS_RECEIVER_NAME" name="receiverName" value="" type="text" maxlength="255"/>
<!--  -->
<input id="NMS_CREATE_TIME" name="createTime" value="" type="text"/>
<!--  -->
<input id="NMS_INTERNAL_PARAM" name="internalParam" value="" type="text" maxlength="2000"/>
<!-- 状态(0未发送1已发送2发送失败) -->
<input id="NMS_STATUS" name="status" value="" type="text" maxlength="1"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NMS_ID" name="nlbsMessageSend.id" value="" type="text" maxlength="11"/>
<!-- 消息序列号(消息平台序号) -->
<input id="NMS_SERIAL_NO" name="nlbsMessageSend.serialNo" value="" type="text" maxlength="50"/>
<!-- 消息标题 -->
<input id="NMS_TITLE" name="nlbsMessageSend.title" value="" type="text" maxlength="255"/>
<!-- 消息内容 -->
<input id="NMS_CONTENT" name="nlbsMessageSend.content" value="" type="text" maxlength="2000"/>
<!-- 发送方公司编号 -->
<input id="NMS_SENDER_COMPANY_CODE" name="nlbsMessageSend.senderCompanyCode" value="" type="text" maxlength="100"/>
<!-- 发送方公司名称 -->
<input id="NMS_SENDER_COMPANY_NAME" name="nlbsMessageSend.senderCompanyName" value="" type="text" maxlength="255"/>
<!-- 发送方所在部门编号 -->
<input id="NMS_SENDER_DEPARTMENT_CODE" name="nlbsMessageSend.senderDepartmentCode" value="" type="text" maxlength="100"/>
<!-- 发送方所在部门名称 -->
<input id="NMS_SENDER_DEPARTMENT_NAME" name="nlbsMessageSend.senderDepartmentName" value="" type="text" maxlength="255"/>
<!-- 发送用户编号 -->
<input id="NMS_SENDER_IDENTITY_ID" name="nlbsMessageSend.senderIdentityId" value="" type="text" maxlength="100"/>
<!-- 发送用户名 -->
<input id="NMS_SENDER_NAME" name="nlbsMessageSend.senderName" value="" type="text" maxlength="255"/>
<!-- 接收用户编号 -->
<input id="NMS_RECEIVER_IDENTITY_ID" name="nlbsMessageSend.receiverIdentityId" value="" type="text" maxlength="100"/>
<!--  -->
<input id="NMS_RECEIVER_NAME" name="nlbsMessageSend.receiverName" value="" type="text" maxlength="255"/>
<!--  -->
<input id="NMS_CREATE_TIME" name="nlbsMessageSend.createTime" value="" type="text"/>
<!--  -->
<input id="NMS_INTERNAL_PARAM" name="nlbsMessageSend.internalParam" value="" type="text" maxlength="2000"/>
<!-- 状态(0未发送1已发送2发送失败) -->
<input id="NMS_STATUS" name="nlbsMessageSend.status" value="" type="text" maxlength="1"/>




--------------------------------------------------------
 */


    