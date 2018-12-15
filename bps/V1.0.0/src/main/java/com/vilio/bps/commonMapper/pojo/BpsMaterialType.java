package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;


/**
 * @实体名称 材料类型表
 * @数表名称 BPS_MATERIAL_TYPE
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsMaterialType implements Serializable {

    /**
     * (必填项)(主键ID)
     */
    private Integer id         = null;
    /**
     * 
     */
    private String code        = null;
    /**
     * 简称
     */
    private String abbrName    = null;
    /**
     * 全称
     */
    private String fullName    = null;
    /**
     * 排序
     */
    private String orderBy     = null;

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
    public String getCode() {
        return trim(code);
    }
    /**
     * 简称
     */
    public String getAbbrName() {
        return trim(abbrName);
    }
    /**
     * 全称
     */
    public String getFullName() {
        return trim(fullName);
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
     * 简称
     */
    public void setAbbrName(String abbrName) {
        this.abbrName = abbrName;
    }
    /**
     * 全称
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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

}


/** 
------------------------------------------------------
 Copy专用区
------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  Setter方法
------------------------------------------------------------------------------------------------------------

// 材料类型表
BpsMaterialType bpsMaterialType = new BpsMaterialType();

// (必填项)(主键ID)
bpsMaterialType.setId(  );
// 
bpsMaterialType.setCode(  );
// 简称
bpsMaterialType.setAbbrName(  );
// 全称
bpsMaterialType.setFullName(  );


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 材料类型表
BpsMaterialType bpsMaterialType = new BpsMaterialType();

// (必填项)(主键ID)
bpsMaterialType.getId();
// 
bpsMaterialType.getCode();
// 简称
bpsMaterialType.getAbbrName();
// 全称
bpsMaterialType.getFullName();


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 材料类型表
BpsMaterialType bpsMaterialType = new BpsMaterialType();

// (必填项)(主键ID)
bpsMaterialType.setId( bpsMaterialType2.getId() );
// 
bpsMaterialType.setCode( bpsMaterialType2.getCode() );
// 简称
bpsMaterialType.setAbbrName( bpsMaterialType2.getAbbrName() );
// 全称
bpsMaterialType.setFullName( bpsMaterialType2.getFullName() );


//------ 自定义部分 ------




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
<!-- 简称 -->
<input name="abbrName" value="" type="text" maxlength="50"/>
<!-- 全称 -->
<input name="fullName" value="" type="text" maxlength="50"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="bpsMaterialType.id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="bpsMaterialType.code" value="" type="text" maxlength="36"/>
<!-- 简称 -->
<input name="bpsMaterialType.abbrName" value="" type="text" maxlength="50"/>
<!-- 全称 -->
<input name="bpsMaterialType.fullName" value="" type="text" maxlength="50"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BMT_ID" name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BMT_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- 简称 -->
<input id="BMT_ABBR_NAME" name="abbrName" value="" type="text" maxlength="50"/>
<!-- 全称 -->
<input id="BMT_FULL_NAME" name="fullName" value="" type="text" maxlength="50"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BMT_ID" name="bpsMaterialType.id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BMT_CODE" name="bpsMaterialType.code" value="" type="text" maxlength="36"/>
<!-- 简称 -->
<input id="BMT_ABBR_NAME" name="bpsMaterialType.abbrName" value="" type="text" maxlength="50"/>
<!-- 全称 -->
<input id="BMT_FULL_NAME" name="bpsMaterialType.fullName" value="" type="text" maxlength="50"/>




--------------------------------------------------------
 */


    