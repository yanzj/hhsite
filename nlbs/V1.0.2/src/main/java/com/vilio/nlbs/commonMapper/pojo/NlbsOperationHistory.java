package com.vilio.nlbs.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 操作历史表
 * @数表名称 NLBS_OPERATION_HISTORY
 * @开发日期 2017-06-15
 * @技术服务 www.fwjava.com
 */
public class NlbsOperationHistory implements Serializable {

    /**
     * (必填项)(主键ID)
     */
    private Integer id            = null;
    /**
     * 
     */
    private Date createTime       = null;
    /**
     * 
     */
    private String operater       = null;
    private String operaterName       = null;
    private String distributorCode       = null;
    private String distributorName       = null;
    /**
     * 关联单号
     */
    private String serialNo       = null;
    /**
     * 序列号所属系统(必填项)
     */
    private String systemCode     = null;
    /**
     * 操作类型
     */
    private String operateType    = null;
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
     * (必填项)(主键ID)
     */
    public Integer getId() {
        return id;
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
    public String getOperater() {
        return trim(operater);
    }
    /**
     * 关联单号
     */
    public String getSerialNo() {
        return trim(serialNo);
    }
    /**
     * 序列号所属系统(必填项)
     */
    public String getSystemCode() {
        return trim(systemCode);
    }
    /**
     * 操作类型
     */
    public String getOperateType() {
        return trim(operateType);
    }
    /**
     * 排序
     */
    public String getOrderBy() {
        return trim(orderBy);
    }

    public String getOperaterName() {
        return operaterName;
    }

    public void setOperaterName(String operaterName) {
        this.operaterName = operaterName;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
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
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }
    /**
     * 关联单号
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    /**
     * 序列号所属系统(必填项)
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
    /**
     * 操作类型
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
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

// 操作历史表
NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();

// (必填项)(主键ID)
nlbsOperationHistory.setId(  );
// 
nlbsOperationHistory.setCreateTime(  );
// 
nlbsOperationHistory.setOperater(  );
// 关联单号
nlbsOperationHistory.setSerialNo(  );
// 序列号所属系统(必填项)
nlbsOperationHistory.setSystemCode(  );
// 操作类型
nlbsOperationHistory.setOperateType(  );


//------ 自定义部分 ------

// (格式化)
nlbsOperationHistory.setCreateTimeChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 操作历史表
NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();

// (必填项)(主键ID)
nlbsOperationHistory.getId();
// 
nlbsOperationHistory.getCreateTime();
// 
nlbsOperationHistory.getOperater();
// 关联单号
nlbsOperationHistory.getSerialNo();
// 序列号所属系统(必填项)
nlbsOperationHistory.getSystemCode();
// 操作类型
nlbsOperationHistory.getOperateType();


//------ 自定义部分 ------

// (格式化)
nlbsOperationHistory.getCreateTimeChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 操作历史表
NlbsOperationHistory nlbsOperationHistory = new NlbsOperationHistory();

// (必填项)(主键ID)
nlbsOperationHistory.setId( nlbsOperationHistory2.getId() );
// 
nlbsOperationHistory.setCreateTime( nlbsOperationHistory2.getCreateTime() );
// 
nlbsOperationHistory.setOperater( nlbsOperationHistory2.getOperater() );
// 关联单号
nlbsOperationHistory.setSerialNo( nlbsOperationHistory2.getSerialNo() );
// 序列号所属系统(必填项)
nlbsOperationHistory.setSystemCode( nlbsOperationHistory2.getSystemCode() );
// 操作类型
nlbsOperationHistory.setOperateType( nlbsOperationHistory2.getOperateType() );


//------ 自定义部分 ------

// (格式化)
nlbsOperationHistory.setCreateTimeChar( nlbsOperationHistory2.getCreateTimeChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="createTime" value="" type="text"/>
<!--  -->
<input name="operater" value="" type="text" maxlength="36"/>
<!-- 关联单号 -->
<input name="serialNo" value="" type="text" maxlength="36"/>
<!-- 序列号所属系统 -->
<input name="systemCode" value="" type="text" maxlength="10"/>
<!-- 操作类型 -->
<input name="operateType" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="nlbsOperationHistory.id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="nlbsOperationHistory.createTime" value="" type="text"/>
<!--  -->
<input name="nlbsOperationHistory.operater" value="" type="text" maxlength="36"/>
<!-- 关联单号 -->
<input name="nlbsOperationHistory.serialNo" value="" type="text" maxlength="36"/>
<!-- 序列号所属系统 -->
<input name="nlbsOperationHistory.systemCode" value="" type="text" maxlength="10"/>
<!-- 操作类型 -->
<input name="nlbsOperationHistory.operateType" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="NOH_ID" name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="NOH_CREATE_TIME" name="createTime" value="" type="text"/>
<!--  -->
<input id="NOH_OPERATER" name="operater" value="" type="text" maxlength="36"/>
<!-- 关联单号 -->
<input id="NOH_SERIAL_NO" name="serialNo" value="" type="text" maxlength="36"/>
<!-- 序列号所属系统 -->
<input id="NOH_SYSTEM_CODE" name="systemCode" value="" type="text" maxlength="10"/>
<!-- 操作类型 -->
<input id="NOH_OPERATE_TYPE" name="operateType" value="" type="text" maxlength="2"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="NOH_ID" name="nlbsOperationHistory.id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="NOH_CREATE_TIME" name="nlbsOperationHistory.createTime" value="" type="text"/>
<!--  -->
<input id="NOH_OPERATER" name="nlbsOperationHistory.operater" value="" type="text" maxlength="36"/>
<!-- 关联单号 -->
<input id="NOH_SERIAL_NO" name="nlbsOperationHistory.serialNo" value="" type="text" maxlength="36"/>
<!-- 序列号所属系统 -->
<input id="NOH_SYSTEM_CODE" name="nlbsOperationHistory.systemCode" value="" type="text" maxlength="10"/>
<!-- 操作类型 -->
<input id="NOH_OPERATE_TYPE" name="nlbsOperationHistory.operateType" value="" type="text" maxlength="2"/>




--------------------------------------------------------
 */


    