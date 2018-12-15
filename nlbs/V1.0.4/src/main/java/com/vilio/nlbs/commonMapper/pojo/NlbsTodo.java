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
    private String userFullName       = null;
    /**
     * 询价系统序列号
     */
    private String serialNo      = null;
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
     * 排序
     */
    public String getOrderBy() {
        return trim(orderBy);
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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

