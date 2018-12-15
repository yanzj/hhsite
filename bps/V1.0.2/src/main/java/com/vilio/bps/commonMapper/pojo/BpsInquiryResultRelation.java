package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;


/**
 * @实体名称 询价请求与计算结果关联表
 * @数表名称 BPS_INQUIRY_RESULT_RELATION
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsInquiryResultRelation implements Serializable {

    /**
     * (必填项)(主键ID)
     */
    private Integer id           = null;
    /**
     * bps_user_inquiry表中serial_no
     */
    private String serialNo      = null;
    /**
     * bps_house_assessment_result中code
     */
    private String resultCode    = null;
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
     * bps_user_inquiry表中serial_no
     */
    public String getSerialNo() {
        return trim(serialNo);
    }
    /**
     * bps_house_assessment_result中code
     */
    public String getResultCode() {
        return trim(resultCode);
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
     * bps_user_inquiry表中serial_no
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    /**
     * bps_house_assessment_result中code
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
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

// 询价请求与计算结果关联表
BpsInquiryResultRelation bpsInquiryResultRelation = new BpsInquiryResultRelation();

// (必填项)(主键ID)
bpsInquiryResultRelation.setId(  );
// bps_user_inquiry表中serial_no
bpsInquiryResultRelation.setSerialNo(  );
// bps_house_assessment_result中code
bpsInquiryResultRelation.setResultCode(  );


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 询价请求与计算结果关联表
BpsInquiryResultRelation bpsInquiryResultRelation = new BpsInquiryResultRelation();

// (必填项)(主键ID)
bpsInquiryResultRelation.getId();
// bps_user_inquiry表中serial_no
bpsInquiryResultRelation.getSerialNo();
// bps_house_assessment_result中code
bpsInquiryResultRelation.getResultCode();


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 询价请求与计算结果关联表
BpsInquiryResultRelation bpsInquiryResultRelation = new BpsInquiryResultRelation();

// (必填项)(主键ID)
bpsInquiryResultRelation.setId( bpsInquiryResultRelation2.getId() );
// bps_user_inquiry表中serial_no
bpsInquiryResultRelation.setSerialNo( bpsInquiryResultRelation2.getSerialNo() );
// bps_house_assessment_result中code
bpsInquiryResultRelation.setResultCode( bpsInquiryResultRelation2.getResultCode() );


//------ 自定义部分 ------




------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="id" value="" type="text" maxlength="11"/>
<!-- bps_user_inquiry表中serial_no -->
<input name="serialNo" value="" type="text" maxlength="36"/>
<!-- bps_house_assessment_result中code -->
<input name="resultCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="bpsInquiryResultRelation.id" value="" type="text" maxlength="11"/>
<!-- bps_user_inquiry表中serial_no -->
<input name="bpsInquiryResultRelation.serialNo" value="" type="text" maxlength="36"/>
<!-- bps_house_assessment_result中code -->
<input name="bpsInquiryResultRelation.resultCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BIRR_ID" name="id" value="" type="text" maxlength="11"/>
<!-- bps_user_inquiry表中serial_no -->
<input id="BIRR_SERIAL_NO" name="serialNo" value="" type="text" maxlength="36"/>
<!-- bps_house_assessment_result中code -->
<input id="BIRR_RESULT_CODE" name="resultCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BIRR_ID" name="bpsInquiryResultRelation.id" value="" type="text" maxlength="11"/>
<!-- bps_user_inquiry表中serial_no -->
<input id="BIRR_SERIAL_NO" name="bpsInquiryResultRelation.serialNo" value="" type="text" maxlength="36"/>
<!-- bps_house_assessment_result中code -->
<input id="BIRR_RESULT_CODE" name="bpsInquiryResultRelation.resultCode" value="" type="text" maxlength="36"/>




--------------------------------------------------------
 */


    