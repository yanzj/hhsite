package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;


/**
 * @实体名称 计算结果-询价返回关系表
 * @数表名称 BPS_RESULT_APPLY_RELATION
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsResultApplyRelation implements Serializable {

    /**
     * (必填项)(主键ID)
     */
    private Integer id           = null;
    /**
     * 
     */
    private String resultCode    = null;
    /**
     * 
     */
    private String applyCode     = null;
    /**
     * 排序
     */
    private String orderBy       = null;

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
    public String getResultCode() {
        return trim(resultCode);
    }
    /**
     * 
     */
    public String getApplyCode() {
        return trim(applyCode);
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
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    /**
     * 
     */
    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
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

// 计算结果-询价返回关系表
BpsResultApplyRelation bpsResultApplyRelation = new BpsResultApplyRelation();

// (必填项)(主键ID)
bpsResultApplyRelation.setId(  );
// 
bpsResultApplyRelation.setResultCode(  );
// 
bpsResultApplyRelation.setApplyCode(  );


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 计算结果-询价返回关系表
BpsResultApplyRelation bpsResultApplyRelation = new BpsResultApplyRelation();

// (必填项)(主键ID)
bpsResultApplyRelation.getId();
// 
bpsResultApplyRelation.getResultCode();
// 
bpsResultApplyRelation.getApplyCode();


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 计算结果-询价返回关系表
BpsResultApplyRelation bpsResultApplyRelation = new BpsResultApplyRelation();

// (必填项)(主键ID)
bpsResultApplyRelation.setId( bpsResultApplyRelation2.getId() );
// 
bpsResultApplyRelation.setResultCode( bpsResultApplyRelation2.getResultCode() );
// 
bpsResultApplyRelation.setApplyCode( bpsResultApplyRelation2.getApplyCode() );


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
<input name="resultCode" value="" type="text" maxlength="36"/>
<!--  -->
<input name="applyCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="bpsResultApplyRelation.id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="bpsResultApplyRelation.resultCode" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsResultApplyRelation.applyCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BRAR_ID" name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BRAR_RESULT_CODE" name="resultCode" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BRAR_APPLY_CODE" name="applyCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BRAR_ID" name="bpsResultApplyRelation.id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BRAR_RESULT_CODE" name="bpsResultApplyRelation.resultCode" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BRAR_APPLY_CODE" name="bpsResultApplyRelation.applyCode" value="" type="text" maxlength="36"/>




--------------------------------------------------------
 */


    