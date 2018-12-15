package com.vilio.nlbs.commonMapper.pojo;


import java.io.Serializable;


/**
 * @实体名称 待处理人和渠道关联表
 * @数表名称 NLBS_PENDING_USER_DISTRIBUTOR
 * @开发日期 2017-06-15
 * @技术服务 www.fwjava.com
 */
public class NlbsPendingUserDistributor implements Serializable {

    /**
     * 数据主键(必填项)(主键ID)
     */
    private Integer id                = null;
    /**
     * 待处理人
     */
    private String userNo             = null;
    /**
     * 所属渠道
     */
    private String distributorCode    = null;
    /**
     * 排序
     */
    private String orderBy            = null;

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
     * 待处理人
     */
    public String getUserNo() {
        return trim(userNo);
    }
    /**
     * 所属渠道
     */
    public String getDistributorCode() {
        return trim(distributorCode);
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
     * 待处理人
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    /**
     * 所属渠道
     */
    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
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

// 待处理人和渠道关联表
NlbsPendingUserDistributor nlbsPendingUserDistributor = new NlbsPendingUserDistributor();

// 数据主键(必填项)(主键ID)
nlbsPendingUserDistributor.setId(  );
// 待处理人
nlbsPendingUserDistributor.setUserNo(  );
// 所属渠道
nlbsPendingUserDistributor.setDistributorCode(  );


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter方法
------------------------------------------------------------------------------------------------------------

// 待处理人和渠道关联表
NlbsPendingUserDistributor nlbsPendingUserDistributor = new NlbsPendingUserDistributor();

// 数据主键(必填项)(主键ID)
nlbsPendingUserDistributor.getId();
// 待处理人
nlbsPendingUserDistributor.getUserNo();
// 所属渠道
nlbsPendingUserDistributor.getDistributorCode();


//------ 自定义部分 ------



------------------------------------------------------------------------------------------------------------
  Getter Setter方法
------------------------------------------------------------------------------------------------------------

// 待处理人和渠道关联表
NlbsPendingUserDistributor nlbsPendingUserDistributor = new NlbsPendingUserDistributor();

// 数据主键(必填项)(主键ID)
nlbsPendingUserDistributor.setId( nlbsPendingUserDistributor2.getId() );
// 待处理人
nlbsPendingUserDistributor.setUserNo( nlbsPendingUserDistributor2.getUserNo() );
// 所属渠道
nlbsPendingUserDistributor.setDistributorCode( nlbsPendingUserDistributor2.getDistributorCode() );


//------ 自定义部分 ------




------------------------------------------------------------------------------------------------------------
 HTML标签区
------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------
  属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="id" value="" type="text" maxlength="11"/>
<!-- 待处理人 -->
<input name="userNo" value="" type="text" maxlength="36"/>
<!-- 所属渠道 -->
<input name="distributorCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input name="nlbsPendingUserDistributor.id" value="" type="text" maxlength="11"/>
<!-- 待处理人 -->
<input name="nlbsPendingUserDistributor.userNo" value="" type="text" maxlength="36"/>
<!-- 所属渠道 -->
<input name="nlbsPendingUserDistributor.distributorCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NPUD_ID" name="id" value="" type="text" maxlength="11"/>
<!-- 待处理人 -->
<input id="NPUD_USER_NO" name="userNo" value="" type="text" maxlength="36"/>
<!-- 所属渠道 -->
<input id="NPUD_DISTRIBUTOR_CODE" name="distributorCode" value="" type="text" maxlength="36"/>


------------------------------------------------------------------------------------------------------------
  ID + 表名 + 属性区
------------------------------------------------------------------------------------------------------------

<!-- 数据主键 -->
<input id="NPUD_ID" name="nlbsPendingUserDistributor.id" value="" type="text" maxlength="11"/>
<!-- 待处理人 -->
<input id="NPUD_USER_NO" name="nlbsPendingUserDistributor.userNo" value="" type="text" maxlength="36"/>
<!-- 所属渠道 -->
<input id="NPUD_DISTRIBUTOR_CODE" name="nlbsPendingUserDistributor.distributorCode" value="" type="text" maxlength="36"/>




--------------------------------------------------------
 */


    