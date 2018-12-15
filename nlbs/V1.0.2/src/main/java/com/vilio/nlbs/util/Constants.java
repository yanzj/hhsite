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
    public final static String ROLE_SUPPER_MANAGER = "2017063014440000000001";
    /** 系统管理员 */
    public final static String ROLE_SYSTEM_MANAGER = "2017063014440000000002";
    /* 公司管理 */
    public final static String ROLE_COMPANY_MANAGER = "2017063014440000000003";
    /** 风控管理 */
    public final static String ROLE_RISK_MANAGER = "2017063014440000000004";
    /** 风控初审 */
    public final static String ROLE_RISK_FIRSTCHECK = "2017063014440000000005";
    /** 风控复审 */
    public final static String ROLE_RISK_SECONDCHECK = "2017063014440000000006";
    /** 风控终审 */
    public final static String ROLE_RISK_FINALCHECK = "2017063014440000000007";
    /** 渠道管理 */
    public final static String ROLE_CHANALE_MANAGER = "2017063014440000000008";
    /** 渠道经理 */
    public final static String ROLE_CHANALE_MASTER = "2017063014440000000009";
    /* 询价管理　*/
    public final static String ROLE_INQUIRY_MANAGER = "2017063014440000000010";
    /* 询价查询　*/
    public final static String ROLE_INQUIRY_QUERY = "2017063014440000000011";
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
    /** 业务管理 */
    public final static String ROLE_BUSINESS_MANAGER = "003";

    /** 操作类型--暂存 **/
    public final static String OPERATION_TEMP_SUBMIT = "01";
    /** 操作类型--提交 **/
    public final static String OPERATION_SUBMIT = "02";
    /** 操作类型--删除（作废） **/
    public final static String OPERATION_DELETE = "03";
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

    /* 消息发送状态  待发送 */
    public final static String SEND_MESSAGE_NO_SEND = "0";
    /* 消息发送状态  已发送 */
    public final static String SEND_MESSAGE_HAVE_SEND = "1";
    /* 消息发送状态  发送失败 */
    public final static String SEND_MESSAGE_SEND_FAIL = "2";
    /* 消息接收状态  未读 */
    public final static String RECEIVE_MESSAGE_NO_READ = "0";
    /* 消息接收状态  已读 */
    public final static String RECEIVE_MESSAGE_HAVE_READ = "1";

    /* 待办任务类型 -- 提交估价 */
    public final static String TODO_TYPE_TOINQUIRY = "001";


    /**
     * UM系统功能号
     */
    /* 用户登录 */
    public final static String UM_FUNCTION_LOGIN = "100001";
    /* 用户首次登录修改密码 */
    public final static String UM_FUNCTION_FIRST_CHANGEPWD = "100002";
    /* 用户角色获取 */
    public final static String UM_FUNCTION_GET_ROLES = "100003";
    /* 渠道（列表）获取 */
    public final static String UM_FUNCTION_GET_DISTRIBUTORS = "100004";
    /* 用户（列表）获取 */
    public final static String UM_FUNCTION_GET_USERS = "100005";
    /* 根据渠道号获取用户列表 */
    public final static String UM_FUNCTION_GET_USERS_BY_DISTRIBUTOR_CODE = "100006";

    /* 消息类型 --房产待评估通知 */
    public final static String INTERNAL_MSG_TYPE_001 = "001";
    /* 消息类型 --房产已评估通知 */
    public final static String INTERNAL_MSG_TYPE_002 = "002";
    /* 消息类型 --房产正在评估通知 */
    public final static String INTERNAL_MSG_TYPE_003 = "003";


    /* 系统编码 */
    public final static String SYSTEM_ID_NLBS = "nlbs";
    public final static String SYSTEM_ID_BPS = "bps";
    /* 请求系统类型 */
    public final static String UM_FUNCTION_TYPE = "um";

    //角色状态
    public final static String ROLE_STATUS_VALID = "1";//"有效"
    public final static String ROLE_STATUS_DISABLE = "0";//"停用"
    public final static String ROLE_STATUS_DELETE = "2";//"已删除"

    //机构查询子孙节点类型
    public final static String GROUP_TREE_SUPER = "0";//查询机构上级信息（包括本身，子孙节点）
    public final static String GROUP_TREE_SUBORDINATE = "1";//查询机构下级信息（包括本身，子孙节点）
    public final static String GROUP_TREE_SUPER_SUBORDINATE = "2";//查询包含当前机构的所有上下级节点
    public final static String GROUP_TREE_ALL = "3";//查询所有机构树形结构
    public final static String GROUP_TREE_ITSELF = "4";//查询自身

    //用户查询子孙节点类型
    public final static String USER_TREE_SUPER="0";//查询用户上级信息（包括本身，子孙节点）
    public final static String USER_TREE_SUBORDINATE="1";//查询用户下级信息（包括本身，子孙节点）
    public final static String USER_TREE_SUPER_SUBORDINATE="2";//查询包含当前用户的所有上下级节点
    public final static String USER_TREE_ALL="3";//查询所有用户树形结构
    public final static String USER_TREE_ITSELF="4";//查询自身
}
