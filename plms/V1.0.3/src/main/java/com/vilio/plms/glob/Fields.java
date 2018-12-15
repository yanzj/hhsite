package com.vilio.plms.glob;

/**
 * Created by dell on 2017/7/7.
 */
public class Fields {
    /* 当前系统编号 */
    public final static String SYSTEM_NO = "plms";

    /* 参数传递消息，错误号字段 */
    public final static String PARAM_MESSAGE_ERR_CODE = "returnCode";
    /* 参数传递消息，错误信息 */
    public final static String PARAM_MESSAGE_ERR_MESG = "returnMessage";


    public final static String PARAM_MESSAGE_HEAD = "head";
    /* 上游系统编码*/
    public final static String PARAM_SOURCE_SYSTEM = "sourceSystem";
    /* 接口功能号*/
    public final static String PARAM_FUNCTION_NO = "functionNo";
    /* 参数 功能号 */
    public final static String PARAM_FUNCTION_TYPE = "functionType";
    /* 用户id*/
    public final static String PARAM_USER_ID = "userId";
    /* UMid*/
    public final static String PARAM_UM_ID = "umId";
    /* 参数 用户No */
    public final static String PARAM_USER_NO = "userNo";
    /* 参数 时间戳 */
    public final static String PARAM_CLIENTTIMESTAMP = "clientTimestamp";

    public final static String PARAM_MESSAGE_BODY = "body";

    /* 参数 服务器时间 */
    public final static String PARAM_SYSTEM_TIME = "systemTime";

    //接收消息Code前缀
    public final static String RECEIVE_MESSAGE_CODE_PREFIX = "M";
    //接收消息Code后缀
    public final static String RECEIVE_MESSAGE_CODE_SUFFIX = null;

    /* 请求页码*/
    public final static String PARAM_PAGE_NO = "pageNo";
    /* 每页数量*/
    public final static String PARAM_PAGE_SIZE = "pageSize";
    /* 当前页*/
    public final static String PARAM_CURRENT_PAGE = "currentPage";
    /* 总页数*/
    public final static String PARAM_TOTAL_PAGE = "totalPage";
    /* 总条数*/
    public final static String PARAM_TOTAL = "total";
    /* 参数 总页数 */
    public final static String PARAM_PAGES = "pages";
    /** serial_no **/
    public final static String PARAM_SERIAL_NO = "serialNo";
    /** serial_no_List **/
    public final static String PARAM_SERIAL_NO_LIST = "serialNoList";

    /* 借款申请表*/
    public final static String PARAM_BORROW_APPLY_LIST = "orderlist";

    /* 还款计划列表*/
    public final static String PARAM_REPAYMENT_LIST = "repaymentList";
    /* 总期数*/
    public final static String PARAM_TOTAL_PERIOD = "totalPeriod";
    /* 当前期数*/
    public final static String PARAM_CURRENT_PERIOD = "currentPeriod";

    /* 应还总金额*/
    public final static String PARAM_TOTALAMOUNT = "totalAmount";
    /* 还款金额*/
    public final static String PARAM_AMOUNT = "amount";
    /* 还款时间*/
    public final static String PARAM_REPAYMENT_DATE = "repaymentDate";
    /* 当前还款流水号*/
    public final static String PARAM_APPLY_CODE = "applyCode";
    /* 当前还款流水号*/
    public final static String PARAM_ORDER_ID = "code";

    /* 参数：状态*/
    public final static String PARAM_STATUS = "status";
    /* 参数：code*/
    public final static String PARAM_CODE = "code";

    /* 系统编码 */
    public final static String PARAM_SYSTEM_ID = "systemId";
    /* 登录方式 */
    public final static String PARAM_LOGIN_TYPE = "loginType";
    /* 用户信息 */
    public final static String PARAM_USER_INFO = "userInfo";
    /* 机构信息 */
    public final static String PARAM_GROUP_INFO = "groupInfo";
    /* 机构id */
    public final static String PARAM_GROUP_ID = "groupId";
    /* 机构名称 */
    public final static String PARAM_GROUP_NAME = "groupName";
    /* 机构所在城市 */
    public final static String PARAM_GROUP_CITY = "groupCity";
    /* 角色编码 */
    public final static String PARAM_ROLE_ID = "roleId";
    /* 角色状态 */
    public final static String PARAM_ROLE_STATUS = "roleStatus";
    /* 角色列表 */
    public final static String PARAM_ROLES = "roles";

    /* 参数 用户列表 */
    public final static String PARAM_USER_LIST = "userList";
    /* 参数 exceptUserNo */
    public final static String PARAM_EXCEPT_USER_ID = "exceptUserId";
    /* 参数 用户名 */
    public final static String PARAM_USER_NAME = "userName";
    /* 参数 用户名 */
    public final static String PARAM_MOBILE = "mobile";
    /* 参数 邮箱 */
    public final static String PARAM_E_MAIL = "email";
    /* 参数 全名 */
    public final static String PARAM_FULL_NAME = "fullName";
    /* 参数 密码 */
    public final static String PARAM_PASSWORD = "password";
    public final static String PARAM_OLD_PASSWORD = "oldPassword";
    /* 参数 新密码 */
    public final static String PARAM_NEW_PASSWORD = "newPassword";
    /* 参数 新密码确认 */
    public final static String PARAM_NEW_PASSWORD_AGAIN = "newPasswordAgain";
    public final static String PARAM_RE_NEW_PASSWORD = "reNewPassword";
    /* 参数 用户对象 */
    public final static String PARAM_NLBS_USER = "nlbsUser";
    /* 参数 是否第一次登录 */
    public final static String PARAM_FIRSTLOGIN = "firstLogin";

    /* 渠道列表 */
    public final static String PARAM_DISTRIBUTRO_LIST = "distributorList";

    /* 贷款状态列表 */
    public final static String PARAM_LOAN_STATUS_LIST = "loanStatusList";

    /* 收款方列表 */
    public final static String PARAM_PAYEE_LIST = "payeeList";

    /* 渠道代码 */
    public final static String PARAM_DISTRIBUTRO_CODE = "distributorCode";
    /* 渠道名称 */
    public final static String PARAM_DISTRIBUTRO_NAME = "distributorName";
    /* 城市列表 */
    public final static String PARAM_CITY_LIST = "cityList";
    /* 城市代码 */
    public final static String PARAM_CITY_CODE = "cityCode";
    /* 城市名称 */
    public final static String PARAM_CITY_NAME = "cityName";
    /* 菜单级别 */
    public final static String PARAM_MENU_LEVEL = "menuLevel";
    /* 菜单列表 */
    public final static String PARAM_MENU_LIST = "menuList";
    public final static String PARAM_MENUS = "menus";

    /** 消息标题 **/
    public final static String PARAM_MSG_TITLE = "title";
    public final static String PARAM_TITLE = "title";
    /** 消息序列号(消息平台序号) serial_no **/
    public final static String PARAM_MSG_SERIAL_NO = "serialNo";
    /** 消息内容 **/
    public final static String PARAM_MSG_CONTENT = "content";
    /** 发送方公司编号 sender_company_code **/
    public final static String PARAM_MSG_SENDER_COMPANY_CODE = "senderCompanyCode";
    /** 发送方公司名称 sender_company_name **/
    public final static String PARAM_MSG_SENDER_COMPANY_NAME = "senderCompanyName";
    /** 发送方所在部门编号 sender_department_code **/
    public final static String PARAM_MSG_SENDER_DEPARTMENT_CODE = "senderDepartmentCode";
    /** 发送方所在部门名称 sender_department_name **/
    public final static String PARAM_MSG_SENDER_DEPARTMENT_NAME = "senderDepartmentName";
    /** 发送用户编号 sender_identity_id **/
    public final static String PARAM_MSG_SENDER_IDENTITY_ID = "senderIdentityId";
    /** 发送用户名 sender_name **/
    public final static String PARAM_MSG_SENDER_NAME = "senderName";
    /** 接收用户编号 receiver_user_id **/
    public final static String PARAM_MSG_RECEIVER_USER_ID = "receiverUserId";
    /** 接收用户编号 receiver_user_id **/
    public final static String PARAM_MSG_RECEIVER_USER_LIST = "receiverUserList";
    /** 信息创建时间 created_time **/
    public final static String PARAM_MSG_CREATE_TIME = "createTime";
    /**信息读取时间 read_time **/
    public final static String PARAM_MSG_READ_TIME = "readTime";
    /** 消息读取终端(PC 电脑端；APP 手机客户端；WeChat 微信端) read_channel **/
    public final static String PARAM_MSG_READ_CHANNEL = "readChannel";
    /** 状态(0创建；1已读取) **/
    public final static String PARAM_MSG_STATUS = "status";

    /** 消息模版 **/
    public final static String PARAM_MSG_MSG_MODEL_CODE = "msgModelCode";
    /** 系统内部参数传递 **/
    public final static String PARAM_MSG_INTERNAL_PARAM = "internalParam";
    /** 系统内部参数传递--msgType **/
    public final static String PARAM_MSG_MSG_TYPE = "msgType";
    /** 系统内部参数传递--keyWords **/
    public final static String PARAM_MSG_KEY_WORDS = "keyWords";



    /** 消息列表 **/
    public final static String PARAM_MESSAGE_LIST = "messageList";
    /** 待办任务列表 **/
    public final static String PARAM_TODO_TASK_LIST = "todoTaskList";
    /** 待办任务提醒 **/
    public final static String PARAM_TODO_TIPS_LIST = "todoTipsList";
    /** 待办任务个数 **/
    public final static String PARAM_TODO_COUNT = "todoCount";
    /** 未读消息个数 **/
    public final static String PARAM_MESSAGE_COUNT = "messageCount";

    public final static String PARAM_COUNT = "count";
    /** 消息提醒列表 **/
    public final static String PARAM_MESSAGE_TIPS_LIST = "messageTipsList";
    public final static String PARAM_CONTRACT_CODE = "contractCode";

}
