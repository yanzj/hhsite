package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;


/**
 * @实体名称 询价请求与询价公司结果关系表
 * @数表名称 BPS_INQUIRY_APPLY_RELATION
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsInquiryApplyRelation implements Serializable {

    /**
     * (必填项)(主键ID)
     */
    private Integer id          = null;
    /**
     * 
     */
    private String serialNo     = null;
    /**
     * 
     */
    private String applyCode    = null;
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
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
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

// 询价请求与询价公司结果关系表
BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();

// (必填项)(主键ID)
bpsInquiryApplyRelation.setId(  );
// 
bpsInquiryApplyRelation.setSerialNo(  );
// 
bpsInquiryApplyRelation.setApplyCode(  );


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 询价请求与询价公司结果关系表
BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();

// (必填项)(主键ID)
bpsInquiryApplyRelation.getId();
// 
bpsInquiryApplyRelation.getSerialNo();
// 
bpsInquiryApplyRelation.getApplyCode();


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 询价请求与询价公司结果关系表
BpsInquiryApplyRelation bpsInquiryApplyRelation = new BpsInquiryApplyRelation();

// (必填项)(主键ID)
bpsInquiryApplyRelation.setId( bpsInquiryApplyRelation2.getId() );
// 
bpsInquiryApplyRelation.setSerialNo( bpsInquiryApplyRelation2.getSerialNo() );
// 
bpsInquiryApplyRelation.setApplyCode( bpsInquiryApplyRelation2.getApplyCode() );


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
<input name="serialNo" value="" type="text" maxlength="36"/>
<!--  -->
<input name="applyCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="bpsInquiryApplyRelation.id" value="" type="text" maxlength="11"/>
<!--  -->
<input name="bpsInquiryApplyRelation.serialNo" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsInquiryApplyRelation.applyCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BIAR_ID" name="id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BIAR_SERIAL_NO" name="serialNo" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BIAR_APPLY_CODE" name="applyCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BIAR_ID" name="bpsInquiryApplyRelation.id" value="" type="text" maxlength="11"/>
<!--  -->
<input id="BIAR_SERIAL_NO" name="bpsInquiryApplyRelation.serialNo" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BIAR_APPLY_CODE" name="bpsInquiryApplyRelation.applyCode" value="" type="text" maxlength="36"/>




--------------------------------------------------------
 */


    