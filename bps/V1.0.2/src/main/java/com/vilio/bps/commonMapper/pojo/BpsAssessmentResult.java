package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.*;


/**
 * @实体名称 估算计算结果表
 * @数表名称 BPS_ASSESSMENT_RESULT
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsAssessmentResult implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private String id                     = null;
    /**
     * 
     */
    private String code                   = null;
    /**
     * 房屋最终估算价格(必填项)
     */
    private BigDecimal assessmentPrice    = null;
    /**
     * 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(必填项)
     */
    private Date minApplyTime             = null;
    /**
     * 计算使用的算法(必填项)
     */
    private String algorithmCode          = null;
    /**
     * 配置表有关算法的配置项名称(必填项)
     */
    private String algorithmMethod        = null;
    /**
     * 依赖的差价阈值百分比(必填项)
     */
    private String percenDiffThreshold    = null;
    /**
     * 状态(必填项)
     */
    private String status                 = null;
    /**
     * 数据创建时间(必填项)
     */
    private Date dateCreated              = null;
    /**
     * 数据修改时间(必填项)
     */
    private Date dateModified             = null;
    /**
     * 排序
     */
    private String orderBy                = null;

    /*
     *--------------------------------------------------
     * Getter方法区
     *--------------------------------------------------
     */

    /**
     * 数据主键(必填项)(主键ID)
     */
    public String getId() {
        return trim(id);
    }
    /**
     * 
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 房屋最终估算价格(必填项)
     */
    public BigDecimal getAssessmentPrice() {
        return assessmentPrice;
    }
    /**
     * 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(必填项)
     */
    public Date getMinApplyTime() {
        return minApplyTime;
    }
    /**
     * 计算使用的算法(必填项)
     */
    public String getAlgorithmCode() {
        return trim(algorithmCode);
    }
    /**
     * 配置表有关算法的配置项名称(必填项)
     */
    public String getAlgorithmMethod() {
        return trim(algorithmMethod);
    }
    /**
     * 依赖的差价阈值百分比(必填项)
     */
    public String getPercenDiffThreshold() {
        return trim(percenDiffThreshold);
    }
    /**
     * 状态(必填项)
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
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 房屋最终估算价格(必填项)
     */
    public void setAssessmentPrice(BigDecimal assessmentPrice) {
        this.assessmentPrice = assessmentPrice;
    }
    /**
     * 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(必填项)
     */
    public void setMinApplyTime(Date minApplyTime) {
        this.minApplyTime = minApplyTime;
    }
    /**
     * 计算使用的算法(必填项)
     */
    public void setAlgorithmCode(String algorithmCode) {
        this.algorithmCode = algorithmCode;
    }
    /**
     * 配置表有关算法的配置项名称(必填项)
     */
    public void setAlgorithmMethod(String algorithmMethod) {
        this.algorithmMethod = algorithmMethod;
    }
    /**
     * 依赖的差价阈值百分比(必填项)
     */
    public void setPercenDiffThreshold(String percenDiffThreshold) {
        this.percenDiffThreshold = percenDiffThreshold;
    }
    /**
     * 状态(必填项)
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
     * 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(格式化)
     */
    public String getMinApplyTimeChar() {
        return getDate(minApplyTime);
    }
    public void setMinApplyTimeChar(String minApplyTimeChar) {
        this.minApplyTime = getDate(minApplyTimeChar);
    }
    /**
     * 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(格式化)
     */
    public String getMinApplyTimeCharAll() {
        return getDateTime(minApplyTime);
    }
    public void setMinApplyTimeCharAll(String minApplyTimeCharAll) {
        this.minApplyTime = getDate(minApplyTimeCharAll);
    }
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

// 估算计算结果表
BpsAssessmentResult bpsAssessmentResult = new BpsAssessmentResult();

// 数据主键(必填项)(主键ID)
bpsAssessmentResult.setId(  );
// 
bpsAssessmentResult.setCode(  );
// 房屋最终估算价格(必填项)
bpsAssessmentResult.setAssessmentPrice(  );
// 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(必填项)
bpsAssessmentResult.setMinApplyTime(  );
// 计算使用的算法(必填项)
bpsAssessmentResult.setAlgorithmCode(  );
// 配置表有关算法的配置项名称(必填项)
bpsAssessmentResult.setAlgorithmMethod(  );
// 依赖的差价阈值百分比(必填项)
bpsAssessmentResult.setPercenDiffThreshold(  );
// 状态(必填项)
bpsAssessmentResult.setStatus(  );
// 数据创建时间(必填项)
bpsAssessmentResult.setDateCreated(  );
// 数据修改时间(必填项)
bpsAssessmentResult.setDateModified(  );


//------ 自定义部分 ------

// 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(格式化)
bpsAssessmentResult.setMinApplyTimeChar(  );
// 数据创建时间(格式化)
bpsAssessmentResult.setDateCreatedChar(  );
// 数据修改时间(格式化)
bpsAssessmentResult.setDateModifiedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 估算计算结果表
BpsAssessmentResult bpsAssessmentResult = new BpsAssessmentResult();

// 数据主键(必填项)(主键ID)
bpsAssessmentResult.getId();
// 
bpsAssessmentResult.getCode();
// 房屋最终估算价格(必填项)
bpsAssessmentResult.getAssessmentPrice();
// 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(必填项)
bpsAssessmentResult.getMinApplyTime();
// 计算使用的算法(必填项)
bpsAssessmentResult.getAlgorithmCode();
// 配置表有关算法的配置项名称(必填项)
bpsAssessmentResult.getAlgorithmMethod();
// 依赖的差价阈值百分比(必填项)
bpsAssessmentResult.getPercenDiffThreshold();
// 状态(必填项)
bpsAssessmentResult.getStatus();
// 数据创建时间(必填项)
bpsAssessmentResult.getDateCreated();
// 数据修改时间(必填项)
bpsAssessmentResult.getDateModified();


//------ 自定义部分 ------

// 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(格式化)
bpsAssessmentResult.getMinApplyTimeChar();
// 数据创建时间(格式化)
bpsAssessmentResult.getDateCreatedChar();
// 数据修改时间(格式化)
bpsAssessmentResult.getDateModifiedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 估算计算结果表
BpsAssessmentResult bpsAssessmentResult = new BpsAssessmentResult();

// 数据主键(必填项)(主键ID)
bpsAssessmentResult.setId( bpsAssessmentResult2.getId() );
// 
bpsAssessmentResult.setCode( bpsAssessmentResult2.getCode() );
// 房屋最终估算价格(必填项)
bpsAssessmentResult.setAssessmentPrice( bpsAssessmentResult2.getAssessmentPrice() );
// 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(必填项)
bpsAssessmentResult.setMinApplyTime( bpsAssessmentResult2.getMinApplyTime() );
// 计算使用的算法(必填项)
bpsAssessmentResult.setAlgorithmCode( bpsAssessmentResult2.getAlgorithmCode() );
// 配置表有关算法的配置项名称(必填项)
bpsAssessmentResult.setAlgorithmMethod( bpsAssessmentResult2.getAlgorithmMethod() );
// 依赖的差价阈值百分比(必填项)
bpsAssessmentResult.setPercenDiffThreshold( bpsAssessmentResult2.getPercenDiffThreshold() );
// 状态(必填项)
bpsAssessmentResult.setStatus( bpsAssessmentResult2.getStatus() );
// 数据创建时间(必填项)
bpsAssessmentResult.setDateCreated( bpsAssessmentResult2.getDateCreated() );
// 数据修改时间(必填项)
bpsAssessmentResult.setDateModified( bpsAssessmentResult2.getDateModified() );


//------ 自定义部分 ------

// 依赖询价公司最早报价时间（用于对比是否在时间阈值内）(格式化)
bpsAssessmentResult.setMinApplyTimeChar( bpsAssessmentResult2.getMinApplyTimeChar() );
// 数据创建时间(格式化)
bpsAssessmentResult.setDateCreatedChar( bpsAssessmentResult2.getDateCreatedChar() );
// 数据修改时间(格式化)
bpsAssessmentResult.setDateModifiedChar( bpsAssessmentResult2.getDateModifiedChar() );



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
<!-- 房屋最终估算价格 -->
<input name="assessmentPrice" value="" type="text" maxlength="18"/>
<!-- 依赖询价公司最早报价时间（用于对比是否在时间阈值内） -->
<input name="minApplyTime" value="" type="text"/>
<!-- 计算使用的算法 -->
<input name="algorithmCode" value="" type="text" maxlength="36"/>
<!-- 配置表有关算法的配置项名称 -->
<input name="algorithmMethod" value="" type="text" maxlength="50"/>
<!-- 依赖的差价阈值百分比 -->
<input name="percenDiffThreshold" value="" type="text" maxlength="5"/>
<!-- 状态 -->
<input name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="bpsAssessmentResult.id" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsAssessmentResult.code" value="" type="text" maxlength="36"/>
<!-- 房屋最终估算价格 -->
<input name="bpsAssessmentResult.assessmentPrice" value="" type="text" maxlength="18"/>
<!-- 依赖询价公司最早报价时间（用于对比是否在时间阈值内） -->
<input name="bpsAssessmentResult.minApplyTime" value="" type="text"/>
<!-- 计算使用的算法 -->
<input name="bpsAssessmentResult.algorithmCode" value="" type="text" maxlength="36"/>
<!-- 配置表有关算法的配置项名称 -->
<input name="bpsAssessmentResult.algorithmMethod" value="" type="text" maxlength="50"/>
<!-- 依赖的差价阈值百分比 -->
<input name="bpsAssessmentResult.percenDiffThreshold" value="" type="text" maxlength="5"/>
<!-- 状态 -->
<input name="bpsAssessmentResult.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input name="bpsAssessmentResult.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input name="bpsAssessmentResult.dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BAR_ID" name="id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BAR_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- 房屋最终估算价格 -->
<input id="BAR_ASSESSMENT_PRICE" name="assessmentPrice" value="" type="text" maxlength="18"/>
<!-- 依赖询价公司最早报价时间（用于对比是否在时间阈值内） -->
<input id="BAR_MIN_APPLY_TIME" name="minApplyTime" value="" type="text"/>
<!-- 计算使用的算法 -->
<input id="BAR_ALGORITHM_CODE" name="algorithmCode" value="" type="text" maxlength="36"/>
<!-- 配置表有关算法的配置项名称 -->
<input id="BAR_ALGORITHM_METHOD" name="algorithmMethod" value="" type="text" maxlength="50"/>
<!-- 依赖的差价阈值百分比 -->
<input id="BAR_PERCEN_DIFF_THRESHOLD" name="percenDiffThreshold" value="" type="text" maxlength="5"/>
<!-- 状态 -->
<input id="BAR_STATUS" name="status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BAR_DATE_CREATED" name="dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BAR_DATE_MODIFIED" name="dateModified" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BAR_ID" name="bpsAssessmentResult.id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BAR_CODE" name="bpsAssessmentResult.code" value="" type="text" maxlength="36"/>
<!-- 房屋最终估算价格 -->
<input id="BAR_ASSESSMENT_PRICE" name="bpsAssessmentResult.assessmentPrice" value="" type="text" maxlength="18"/>
<!-- 依赖询价公司最早报价时间（用于对比是否在时间阈值内） -->
<input id="BAR_MIN_APPLY_TIME" name="bpsAssessmentResult.minApplyTime" value="" type="text"/>
<!-- 计算使用的算法 -->
<input id="BAR_ALGORITHM_CODE" name="bpsAssessmentResult.algorithmCode" value="" type="text" maxlength="36"/>
<!-- 配置表有关算法的配置项名称 -->
<input id="BAR_ALGORITHM_METHOD" name="bpsAssessmentResult.algorithmMethod" value="" type="text" maxlength="50"/>
<!-- 依赖的差价阈值百分比 -->
<input id="BAR_PERCEN_DIFF_THRESHOLD" name="bpsAssessmentResult.percenDiffThreshold" value="" type="text" maxlength="5"/>
<!-- 状态 -->
<input id="BAR_STATUS" name="bpsAssessmentResult.status" value="" type="text" maxlength="1"/>
<!-- 数据创建时间 -->
<input id="BAR_DATE_CREATED" name="bpsAssessmentResult.dateCreated" value="" type="text"/>
<!-- 数据修改时间 -->
<input id="BAR_DATE_MODIFIED" name="bpsAssessmentResult.dateModified" value="" type="text"/>




--------------------------------------------------------
 */


    