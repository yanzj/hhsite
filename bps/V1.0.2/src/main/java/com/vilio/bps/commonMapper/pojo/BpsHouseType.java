package com.vilio.bps.commonMapper.pojo;


import java.io.Serializable;


/**
 * @实体名称 房屋类型表
 * @数表名称 BPS_HOUSE_TYPE
 * @开发日期 2017-06-12
 * @技术服务 www.fwjava.com
 */
public class BpsHouseType implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id     = null;
    /**
     * 类型代码(必填项)
     */
    private String code    = null;
    /**
     * 类型名称
     */
    private String name    = null;
    /**
     * 排序
     */
    private String orderBy = null;

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
     * 类型代码(必填项)
     */
    public String getCode() {
        return trim(code);
    }
    /**
     * 类型名称
     */
    public String getName() {
        return trim(name);
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
     * 类型代码(必填项)
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 类型名称
     */
    public void setName(String name) {
        this.name = name;
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

// 房屋类型表
BpsHouseType bpsHouseType = new BpsHouseType();

// 数据主键(必填项)(主键ID)
bpsHouseType.setId(  );
// 类型代码(必填项)
bpsHouseType.setCode(  );
// 类型名称
bpsHouseType.setName(  );


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 房屋类型表
BpsHouseType bpsHouseType = new BpsHouseType();

// 数据主键(必填项)(主键ID)
bpsHouseType.getId();
// 类型代码(必填项)
bpsHouseType.getCode();
// 类型名称
bpsHouseType.getName();


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 房屋类型表
BpsHouseType bpsHouseType = new BpsHouseType();

// 数据主键(必填项)(主键ID)
bpsHouseType.setId( bpsHouseType2.getId() );
// 类型代码(必填项)
bpsHouseType.setCode( bpsHouseType2.getCode() );
// 类型名称
bpsHouseType.setName( bpsHouseType2.getName() );


//------ 自定义部分 ------




------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="11"/>
<!-- 类型代码 -->
<input name="code" value="" type="text" maxlength="5"/>
<!-- 类型名称 -->
<input name="name" value="" type="text" maxlength="100"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="bpsHouseType.id" value="" type="text" maxlength="11"/>
<!-- 类型代码 -->
<input name="bpsHouseType.code" value="" type="text" maxlength="5"/>
<!-- 类型名称 -->
<input name="bpsHouseType.name" value="" type="text" maxlength="100"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BHT_ID" name="id" value="" type="text" maxlength="11"/>
<!-- 类型代码 -->
<input id="BHT_CODE" name="code" value="" type="text" maxlength="5"/>
<!-- 类型名称 -->
<input id="BHT_NAME" name="name" value="" type="text" maxlength="100"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="BHT_ID" name="bpsHouseType.id" value="" type="text" maxlength="11"/>
<!-- 类型代码 -->
<input id="BHT_CODE" name="bpsHouseType.code" value="" type="text" maxlength="5"/>
<!-- 类型名称 -->
<input id="BHT_NAME" name="bpsHouseType.name" value="" type="text" maxlength="100"/>




--------------------------------------------------------
 */


    