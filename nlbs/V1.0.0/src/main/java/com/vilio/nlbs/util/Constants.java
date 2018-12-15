package com.vilio.nlbs.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/16.
 */
public class Constants {

    /** 大域系统是否可以联调 **/
    public static boolean BMS_SYSTEM_ISOK = false;

    //总共允许的登录次数
    public final static int USER_VALID_LOGINTIME = 5;

//    /* 虚拟渠道 宏获自身 */
    public final static String DISTRIBUTOR_VILIO_CODE = "99999999";
//    public final static String DISTRIBUTOR_VILIO_NAME = "上海宏获资产管理有限公司";

    /*  渠道代码，全国 */
    public final static String CITY_CODE_QUANGUO = "000000";

    /* 城市 -- 上海 */
    public final static String CITY_CODE_SHANGHAI = "310000";

    //状态 ：0 可用
    public final static String NLBS_STATUS_AVALIABLE = "0";
    //状态 ：1 可用
    public final static String NLBS_STATUS_UNAVALIABLE = "1";

    //岗位编码
    /** 超级管理员 */
    public final static String ROLE_SUPPER_MANAGER = "001";
    /** 系统管理员 */
    public final static String ROLE_SYSTEM_MANAGER = "002";
    /** 业务管理 */
    public final static String ROLE_BUSINESS_MANAGER = "003";
    /** 风控管理 */
    public final static String ROLE_RISK_MANAGER = "004";
    /** 风控初审 */
    public final static String ROLE_RISK_FIRSTCHECK = "005";
    /** 风控复审 */
    public final static String ROLE_RISK_SECONDCHECK = "006";
    /** 风控终审 */
    public final static String ROLE_RISK_FINALCHECK = "007";
    /** 渠道管理 */
    public final static String ROLE_CHANALE_MANAGER = "008";
    /** 渠道经理 */
    public final static String ROLE_CHANALE_MASTER = "009";
    /** 经纪人 */
    public final static String ROLE_BROKER = "891";
    /** 团队长 */
    public final static String ROLE_SENIOR_PATROL_LEADER = "892";
    /** 总监 */
    public final static String ROLE_DIRECOTOR = "893";
    /** 总经理 */
    public final static String ROLE_TOP_MANAGER = "894";
    /**录单员 */
    public final static String ROLE_RECORD_CLERK = "895";

    /** 操作类型--暂存 **/
    public final static String OPERATION_TEMP_SUBMIT = "01";
    /** 操作类型--提交 **/
    public final static String OPERATION_SUBMIT = "02";
    /** 操作类型--提交评估 **/
    public final static String OPERATION_SUBMIT_INQUIRY = "11";
    /** 操作类型--录入评估价 **/
    public final static String OPERATION_INPUT_INQUIRY_PRICE = "12";

    /* 自动（极速）询价---1 */
    public final static String INQUIRY_TYPE_AUTO_PRICE = "1";
    /* 人工询价---0 */
    public final static String INQUIRY_TYPE_MANNAL_PRICE = "0";

    /* 询价订单状态  待评估  */
    public final static String BPS_ORDER_STATUS_PENDING_EVALUATION = "00";
    /* 询价订单状态  评估中  */
    public final static String BPS_ORDER_STATUS_EVALUATING = "01";
    /* 询价订单状态  已评估  */
    public final static String BPS_ORDER_STATUS_EVALUATED = "02";
    /* 询价订单状态  评估失效  */
    public final static String BPS_ORDER_STATUS_EVALUATION_INVALID = "99";

    /* 待办任务类型 -- 提交估价 */
    public final static String TODO_TYPE_TOINQUIRY = "001";


}
