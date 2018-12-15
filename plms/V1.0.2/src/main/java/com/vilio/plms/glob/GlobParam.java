package com.vilio.plms.glob;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： GlobParam<br>
 * 功能：全局参数定义<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class GlobParam {
    // 错误码全局配置
    public static Map<String, String> ERROR_CODE = new HashMap<String, String>();
    // fms默认存储地址
    public static String fmsPath = "/plms";
    // fms地址
    public static String fmsUrl = "http://localhost:8080/api";
    // mps地址
    public static String mpsUrl = "http://localhost:8083/mps/backendapi.json";
    // um地址
    public static String umUrl = "http://localhost:8084/um/umCore.json";
    // 生成临时文件路径
    public static String downloadTemp = "";
    // nlbs地址
    public static String nlbsUrl = "";


    public final static String DEFAULT_CURRENCY = "CNY";

    //还款申请Code前缀
    public final static String REPAYMENT_APPLY_CODE_PREFIX = "R";
    //还款凭证Code前缀
    public final static String VOUCHER_CODE_PREFIX = "V";
    //业务编码Code前缀
    public final static String BUSINESS_CODE_PREFIX = "B";
    //数据库序列
    public final static String SEQUENCE_SERIAL_NO = "SERNO";

    //还款申请凭证 状态_有效
    public final static String PLMS_PAID_VOUCHER_STATUS_AVALIABLE = "1";
    //还款申请凭证 状态_无效
    public final static String PLMS_PAID_VOUCHER_STATUS_UNAVALIABLE = "0";

    /* 请求系统类型 */
    public final static String UM_FUNCTION_TYPE = "um";

    //角色状态
    public final static String ROLE_STATUS_VALID = "1";//"有效"
    public final static String ROLE_STATUS_DISABLE = "0";//"停用"
    public final static String ROLE_STATUS_DELETE = "2";//"已删除"

    /* 系统编码 */
    public final static String SYSTEM_ID_PLMS = "plms";
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
    /* 根据业务员编号获取UM用户信息 */
    public final static String UM_FUNCTION_GET_USERS_BY_AGENT_CODE = "100007";


    /*  渠道代码，全国 */
    public final static String CITY_CODE_QUANGUO = "000000";

    //岗位编码
    /** 超级管理员 */
    public final static String ROLE_SUPPER_MANAGER = "2017063014440000000001";
    /** 系统管理员 */
    public final static String ROLE_SYSTEM_MANAGER = "2017063014440000000002";

    // 精度计算常量值
    public static int SCALE_2 = 2;

    public static BigDecimal ZERO = new BigDecimal("0");


    //还款提醒收集数据
    public static String[] repaymentTimeListStr = null;
    public static String repaymentTitle = null;
    public static String repaymentTicker = null;
    public static String repaymentSubtitle = null;
    public static String repaymentContent = null;
    public static Map<String, Object> repaymentSendSystem = null;

    //逾期提醒收集数据
    public static String[] overdueTimeListStr = null;
    public static String overdueTitle = null;
    public static String overdueTicker = null;
    public static String overdueSubtitle = null;
    public static String overdueContent = null;
    public static Map<String, Object> overdueSendSystem = null;
    public static Map<String, Object> repaymentSmsSignNo = null;
    public static Map<String, Object> overdueSmsSignNo = null;


    public static String repaymentSmsTemplateCode = null;
    public static String overdueSmsTemplateCode = null;
    public static String mpsSmsFunctionNo = null;
    public static String mpsSmsType = null;
    public static String mpsSmsSenderIdentityId = "plms001";
    //站内信
    public static String mpsInstationFunctionNo = "";
    public static String mpsInstationType = "";

    //监控邮件配置
    public static String monitorToUserList = "";
    public static String monitorSubject = "";
    public static String monitorDisplayName = "";
    public static String monitorUserName = "";
    public static String monitorContent = "";
    public static String monitorSenderName = "";
    public static String monitorPassword = "";
    public static String monitorToCcList = "";
    public static String monitorSwitch = "";
    //mps邮件类型
    public final static String MPS_EMAIL_TYPE = "Email";

    public final static String MPS_EMAIL_FUNCTION_NO = "HH000805";

    //还款提醒邮件设置
    public static String systemEmailManagentName = "";
    public static String systemManagentEmailUserName = "";
    public static String systemManagentEmailPassword = "";

}
