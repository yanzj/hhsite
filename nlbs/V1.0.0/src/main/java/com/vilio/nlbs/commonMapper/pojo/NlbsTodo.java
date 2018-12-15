package com.vilio.nlbs.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 待办任务列表
 * @数表名称 NLBS_TODO
 * @开发日期 2017-06-20
 * @技术服务 www.fwjava.com
 */
public class NlbsTodo implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id          = null;
    /**
     * 任务创建时间（分配时间）
     */
    private Date dateCreated    = null;
    /**
     * 待办任务详情
     */
    private String content      = null;
    /**
     * 待办任务类型
     */
    private String todoType     = null;
    /**
     * 待处理人
     */
    private String userNo       = null;
    /**
     * 询价系统序列号
     */
    private String bpsCode      = null;
    /**
     * 排序
     */
    private String orderBy      = null;

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
     * 任务创建时间（分配时间）
     */
    public Date getDateCreated() {
        return dateCreated;
    }
    /**
     * 待办任务详情
     */
    public String getContent() {
        return trim(content);
    }
    /**
     * 待办任务类型
     */
    public String getTodoType() {
        return trim(todoType);
    }
    /**
     * 待处理人
     */
    public String getUserNo() {
        return trim(userNo);
    }
    /**
     * 询价系统序列号
     */
    public String getBpsCode() {
        return trim(bpsCode);
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
     * 任务创建时间（分配时间）
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    /**
     * 待办任务详情
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * 待办任务类型
     */
    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }
    /**
     * 待处理人
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    /**
     * 询价系统序列号
     */
    public void setBpsCode(String bpsCode) {
        this.bpsCode = bpsCode;
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
     * 任务创建时间（分配时间）(格式化)
     */
    public String getDateCreatedChar() {
        return getDate(dateCreated);
    }
    public void setDateCreatedChar(String dateCreatedChar) {
        this.dateCreated = getDate(dateCreatedChar);
    }
    /**
     * 任务创建时间（分配时间）(格式化)
     */
    public String getDateCreatedCharAll() {
        return getDateTime(dateCreated);
    }
    public void setDateCreatedCharAll(String dateCreatedCharAll) {
        this.dateCreated = getDate(dateCreatedCharAll);
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

// 待办任务列表
NlbsTodo nlbsTodo = new NlbsTodo();

// 数据主键(必填项)(主键ID)
nlbsTodo.setId(  );
// 任务创建时间（分配时间）
nlbsTodo.setDateCreated(  );
// 待办任务详情
nlbsTodo.setContent(  );
// 待办任务类型
nlbsTodo.setTodoType(  );
// 待处理人
nlbsTodo.setUserNo(  );
// 询价系统序列号
nlbsTodo.setBpsCode(  );


//------ 自定义部分 ------

// 任务创建时间（分配时间）(格式化)
nlbsTodo.setDateCreatedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 待办任务列表
NlbsTodo nlbsTodo = new NlbsTodo();

// 数据主键(必填项)(主键ID)
nlbsTodo.getId();
// 任务创建时间（分配时间）
nlbsTodo.getDateCreated();
// 待办任务详情
nlbsTodo.getContent();
// 待办任务类型
nlbsTodo.getTodoType();
// 待处理人
nlbsTodo.getUserNo();
// 询价系统序列号
nlbsTodo.getBpsCode();


//------ 自定义部分 ------

// 任务创建时间（分配时间）(格式化)
nlbsTodo.getDateCreatedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 待办任务列表
NlbsTodo nlbsTodo = new NlbsTodo();

// 数据主键(必填项)(主键ID)
nlbsTodo.setId( nlbsTodo2.getId() );
// 任务创建时间（分配时间）
nlbsTodo.setDateCreated( nlbsTodo2.getDateCreated() );
// 待办任务详情
nlbsTodo.setContent( nlbsTodo2.getContent() );
// 待办任务类型
nlbsTodo.setTodoType( nlbsTodo2.getTodoType() );
// 待处理人
nlbsTodo.setUserNo( nlbsTodo2.getUserNo() );
// 询价系统序列号
nlbsTodo.setBpsCode( nlbsTodo2.getBpsCode() );


//------ 自定义部分 ------

// 任务创建时间（分配时间）(格式化)
nlbsTodo.setDateCreatedChar( nlbsTodo2.getDateCreatedChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="11"/>
<!-- 任务创建时间（分配时间） -->
<input name="dateCreated" value="" type="text"/>
<!-- 待办任务详情 -->
<input name="content" value="" type="text" maxlength="255"/>
<!-- 待办任务类型 -->
<input name="todoType" value="" type="text" maxlength="3"/>
<!-- 待处理人 -->
<input name="userNo" value="" type="text" maxlength="36"/>
<!-- 询价系统序列号 -->
<input name="bpsCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="nlbsTodo.id" value="" type="text" maxlength="11"/>
<!-- 任务创建时间（分配时间） -->
<input name="nlbsTodo.dateCreated" value="" type="text"/>
<!-- 待办任务详情 -->
<input name="nlbsTodo.content" value="" type="text" maxlength="255"/>
<!-- 待办任务类型 -->
<input name="nlbsTodo.todoType" value="" type="text" maxlength="3"/>
<!-- 待处理人 -->
<input name="nlbsTodo.userNo" value="" type="text" maxlength="36"/>
<!-- 询价系统序列号 -->
<input name="nlbsTodo.bpsCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NT_ID" name="id" value="" type="text" maxlength="11"/>
<!-- 任务创建时间（分配时间） -->
<input id="NT_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 待办任务详情 -->
<input id="NT_CONTENT" name="content" value="" type="text" maxlength="255"/>
<!-- 待办任务类型 -->
<input id="NT_TODO_TYPE" name="todoType" value="" type="text" maxlength="3"/>
<!-- 待处理人 -->
<input id="NT_USER_NO" name="userNo" value="" type="text" maxlength="36"/>
<!-- 询价系统序列号 -->
<input id="NT_BPS_CODE" name="bpsCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NT_ID" name="nlbsTodo.id" value="" type="text" maxlength="11"/>
<!-- 任务创建时间（分配时间） -->
<input id="NT_DATE_CREATED" name="nlbsTodo.dateCreated" value="" type="text"/>
<!-- 待办任务详情 -->
<input id="NT_CONTENT" name="nlbsTodo.content" value="" type="text" maxlength="255"/>
<!-- 待办任务类型 -->
<input id="NT_TODO_TYPE" name="nlbsTodo.todoType" value="" type="text" maxlength="3"/>
<!-- 待处理人 -->
<input id="NT_USER_NO" name="nlbsTodo.userNo" value="" type="text" maxlength="36"/>
<!-- 询价系统序列号 -->
<input id="NT_BPS_CODE" name="nlbsTodo.bpsCode" value="" type="text" maxlength="36"/>




--------------------------------------------------------
 */


    