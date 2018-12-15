package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * @实体名称 询价上载材料表
 * @数表名称 BPS_INQUIRY_MATERIAL
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsInquiryMaterial implements Serializable {

    /**
     * (必填项)(主键ID)
     */
    private Integer id                 = null;
    /**
     * 
     */
    private String code                = null;
    /**
     * bps_user_inquiry表中serial_no
     */
    private String serialNo            = null;
    /**
     * 材料类型
     */
    private String materialTypeCode    = null;
    /**
     * 上载材料文件号(文件关联系统返回唯一编号)
     */
    private String fileNo              = null;
    /**
     * 上载文件名
     */
    private String fileName            = null;
    /**
     * 
     */
    private Date dateCreated           = null;
    /**
     * 排序
     */
    private String orderBy             = null;

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
     * bps_user_inquiry表中serial_no
     */
    public String getSerialNo() {
        return trim(serialNo);
    }
    /**
     * 材料类型
     */
    public String getMaterialTypeCode() {
        return trim(materialTypeCode);
    }
    /**
     * 上载材料文件号(文件关联系统返回唯一编号)
     */
    public String getFileNo() {
        return trim(fileNo);
    }
    /**
     * 上载文件名
     */
    public String getFileName() {
        return trim(fileName);
    }
    /**
     * 
     */
    public Date getDateCreated() {
        return dateCreated;
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
     * bps_user_inquiry表中serial_no
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    /**
     * 材料类型
     */
    public void setMaterialTypeCode(String materialTypeCode) {
        this.materialTypeCode = materialTypeCode;
    }
    /**
     * 上载材料文件号(文件关联系统返回唯一编号)
     */
    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }
    /**
     * 上载文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * 
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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
    public String getDateCreatedChar() {
        return getDate(dateCreated);
    }
    public void setDateCreatedChar(String dateCreatedChar) {
        this.dateCreated = getDate(dateCreatedChar);
    }
    /**
     * (格式化)
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

// 询价上载材料表
BpsInquiryMaterial bpsInquiryMaterial = new BpsInquiryMaterial();

// (必填项)(主键ID)
bpsInquiryMaterial.setId(  );
// 
bpsInquiryMaterial.setCode(  );
// bps_user_inquiry表中serial_no
bpsInquiryMaterial.setSerialNo(  );
// 材料类型
bpsInquiryMaterial.setMaterialTypeCode(  );
// 上载材料文件号(文件关联系统返回唯一编号)
bpsInquiryMaterial.setFileNo(  );
// 上载文件名
bpsInquiryMaterial.setFileName(  );
// 
bpsInquiryMaterial.setDateCreated(  );


//------ 自定义部分 ------

// (格式化)
bpsInquiryMaterial.setDateCreatedChar(  );


------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 询价上载材料表
BpsInquiryMaterial bpsInquiryMaterial = new BpsInquiryMaterial();

// (必填项)(主键ID)
bpsInquiryMaterial.getId();
// 
bpsInquiryMaterial.getCode();
// bps_user_inquiry表中serial_no
bpsInquiryMaterial.getSerialNo();
// 材料类型
bpsInquiryMaterial.getMaterialTypeCode();
// 上载材料文件号(文件关联系统返回唯一编号)
bpsInquiryMaterial.getFileNo();
// 上载文件名
bpsInquiryMaterial.getFileName();
// 
bpsInquiryMaterial.getDateCreated();


//------ 自定义部分 ------

// (格式化)
bpsInquiryMaterial.getDateCreatedChar();


------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 询价上载材料表
BpsInquiryMaterial bpsInquiryMaterial = new BpsInquiryMaterial();

// (必填项)(主键ID)
bpsInquiryMaterial.setId( bpsInquiryMaterial2.getId() );
// 
bpsInquiryMaterial.setCode( bpsInquiryMaterial2.getCode() );
// bps_user_inquiry表中serial_no
bpsInquiryMaterial.setSerialNo( bpsInquiryMaterial2.getSerialNo() );
// 材料类型
bpsInquiryMaterial.setMaterialTypeCode( bpsInquiryMaterial2.getMaterialTypeCode() );
// 上载材料文件号(文件关联系统返回唯一编号)
bpsInquiryMaterial.setFileNo( bpsInquiryMaterial2.getFileNo() );
// 上载文件名
bpsInquiryMaterial.setFileName( bpsInquiryMaterial2.getFileName() );
// 
bpsInquiryMaterial.setDateCreated( bpsInquiryMaterial2.getDateCreated() );


//------ 自定义部分 ------

// (格式化)
bpsInquiryMaterial.setDateCreatedChar( bpsInquiryMaterial2.getDateCreatedChar() );



------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="id" value="" type="text" maxlength="36"/>
<!--  -->
<input name="code" value="" type="text" maxlength="36"/>
<!-- bps_user_inquiry表中serial_no -->
<input name="serialNo" value="" type="text" maxlength="36"/>
<!-- 材料类型 -->
<input name="materialTypeCode" value="" type="text" maxlength="36"/>
<!-- 上载材料文件号(文件关联系统返回唯一编号) -->
<input name="fileNo" value="" type="text" maxlength="36"/>
<!-- 上载文件名 -->
<input name="fileName" value="" type="text" maxlength="100"/>
<!--  -->
<input name="dateCreated" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input name="bpsInquiryMaterial.id" value="" type="text" maxlength="36"/>
<!--  -->
<input name="bpsInquiryMaterial.code" value="" type="text" maxlength="36"/>
<!-- bps_user_inquiry表中serial_no -->
<input name="bpsInquiryMaterial.serialNo" value="" type="text" maxlength="36"/>
<!-- 材料类型 -->
<input name="bpsInquiryMaterial.materialTypeCode" value="" type="text" maxlength="36"/>
<!-- 上载材料文件号(文件关联系统返回唯一编号) -->
<input name="bpsInquiryMaterial.fileNo" value="" type="text" maxlength="36"/>
<!-- 上载文件名 -->
<input name="bpsInquiryMaterial.fileName" value="" type="text" maxlength="100"/>
<!--  -->
<input name="bpsInquiryMaterial.dateCreated" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BIM_ID" name="id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BIM_CODE" name="code" value="" type="text" maxlength="36"/>
<!-- bps_user_inquiry表中serial_no -->
<input id="BIM_SERIAL_NO" name="serialNo" value="" type="text" maxlength="36"/>
<!-- 材料类型 -->
<input id="BIM_MATERIAL_TYPE_CODE" name="materialTypeCode" value="" type="text" maxlength="36"/>
<!-- 上载材料文件号(文件关联系统返回唯一编号) -->
<input id="BIM_FILE_NO" name="fileNo" value="" type="text" maxlength="36"/>
<!-- 上载文件名 -->
<input id="BIM_FILE_NAME" name="fileName" value="" type="text" maxlength="100"/>
<!--  -->
<input id="BIM_DATE_CREATED" name="dateCreated" value="" type="text"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!--  -->
<input id="BIM_ID" name="bpsInquiryMaterial.id" value="" type="text" maxlength="36"/>
<!--  -->
<input id="BIM_CODE" name="bpsInquiryMaterial.code" value="" type="text" maxlength="36"/>
<!-- bps_user_inquiry表中serial_no -->
<input id="BIM_SERIAL_NO" name="bpsInquiryMaterial.serialNo" value="" type="text" maxlength="36"/>
<!-- 材料类型 -->
<input id="BIM_MATERIAL_TYPE_CODE" name="bpsInquiryMaterial.materialTypeCode" value="" type="text" maxlength="36"/>
<!-- 上载材料文件号(文件关联系统返回唯一编号) -->
<input id="BIM_FILE_NO" name="bpsInquiryMaterial.fileNo" value="" type="text" maxlength="36"/>
<!-- 上载文件名 -->
<input id="BIM_FILE_NAME" name="bpsInquiryMaterial.fileName" value="" type="text" maxlength="100"/>
<!--  -->
<input id="BIM_DATE_CREATED" name="bpsInquiryMaterial.dateCreated" value="" type="text"/>




--------------------------------------------------------
 */


    