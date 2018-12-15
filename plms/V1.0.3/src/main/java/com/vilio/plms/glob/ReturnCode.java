package com.vilio.plms.glob;

/**
 * Created by xiezhilei on 2017/1/13.
 */
public class ReturnCode {
    /** 成功 **/
    public static String SUCCESS_CODE = "0000";
    /** 系统异常 **/
    public static String SYSTEM_EXCEPTION = "9999";
    /* 必填字段缺失 */
    public static String REQUIRED_FIELD_MISSING = "9998";
    /* 更新数据库失败 */
    public static String UPDATE_FAIL = "9997";
    /* 网络连接超时 */
    public static String TIME_OUT = "9992";
    /* 公共参数校验失败 */
    public static String PUBLIC_PARAM_FAIL = "0005";
    /* 用户未登录 */
    public static String USER_NOLOGIN = "0027";
    /* 用户已在其它设备登录 */
    public static String USER_OTHER_MACHINE_LOGIN = "0028";
    /* 登录超时 */
    public static String LOGIN_TIMEOUT = "0025";
    /* 不支持的业务类型 */
    public static String SMS_NO_BUSI_TYPE = "0026";
    /* 文件操作失败 */
    public static String FILE_HANDLE_FAIL = "0029";
    /* 文件上传失败 */
    public static String FILE_UPLOAD_FAIL = "0030";
    /* 证件到期 */
    public static String ID_EXPIRY_DATE = "0031";
    /* 借款人信息不存在 */
    public static String BORROWER_INFO_FAIL = "0032";
    /* 账户汇总信息不存在 */
    public static String ACCOUNT_INFO_FAIL = "0033";
    /* 有未缴罚金 */
    public static String UNPAID_FINES = "0034";
    /* 有未违约金 */
    public static String PENALTY_FINESS = "0035";
    /* 不在授信有效期内 */
    public static String CREDIT_END_DATE_FAIL = "0036";
    /* 额度不足 */
    public static String LACK_OF_CREDIT = "0037";
    /* 账户汇总信息不存在 */
    public static String ACCOUNT_COLLECT_FAIL = "0038";
    /* 账户汇明细信息不存在 */
    public static String ACCOUNT_DETAIL_FAIL = "0039";
    /* 抵押物信息错误 */
    public static String HOUSE_INFO_FAIL = "0040";
    /* 进件申请信息错误 */
    public static String APPLY_INFO_FAIL = "0041";
    /* 产品信息错误 */
    public static String PRODUCT_INFO_FAIL = "0042";
    /* 产品循环类型错误 */
    public static String PRODUCT_CIRCLE_FAIL = "0043";
    /* 借款最高期数错误 */
    public static String LOAN_COUNT_FAIL = "0044";
    /* 借款金额不合法，不是最小单位倍数 */
    public static String LOAN_MINIMUM_UNIT_FAIL = "0045";
    /* 借款金额不合法，大于最低借款金额 */
    public static String LOAN_MINIMUM_AMOUNT_FAIL = "0046";
    /* 借款封闭期，不能借款 */
    public static String BORROW_CLOSED_PERIOD_DATE = "0047";
    /* 产品不可循环，不能借款 */
    public static String UNCIRCLE = "0048";
    /* 借款金额不能大于首笔放款金额，放款金额为空 */
    public static String FIRST_AMOUNT_ISNULL = "0049";
    /* 借款金额不能大于首笔放款金额 */
    public static String FIRST_AMOUNT_FAIL = "0050";
    /* 合同信息错误 */
    public static String CONTRACT_INFO_FAIL = "0051";
    /* 渠道信息错误 */
    public static String DISTRIBUTOR_FAIL = "0068";
    /* 实际放款天数错误 */
    public static String PAID_DAYS_FAIL = "0069";
    /* 资方信息错误 */
    public static String FUND_SIDE_FAIL = "0070";
    /* 系统参数配置错误 */
    public static String SYS_PARAM_FAIL = "0071";
    /* 账务类操作已经锁定 */
    public static String ACCOUNT_LOCK = "0072";
    /* 还款计划明细信息错误 */
    public static String REPAYMENT_SCHEDULE_DETAIL_FAIL = "0073";


    /** 登录状态异常（未登录或登录超时） **/
    public static String SYSTEM_DISABLE_TOKEN = "1234";
    /** 接受消息为空 **/
    public static String MSG_EMPTY_RECEIVE = "8234";

    /** 调用UM异常 **/
    public static String UM_SYSTEMT_EXCEPTION = "1002";
    /** 姓名或证件号错误 **/
    public static String NAME_CARD_FAIL = "0057";
    /** 所选期数不能大于最高期数 **/
    public static String BORROW_MAX_FAIL = "0058";
    /** 所选期数必须等于最高期数 **/
    public static String BORROW_EQUAL_FAIL = "0059";
    /** 合同信息查询错误 **/
    public static String CONTRACT_QUERY_FAIL = "0060";
    /** 进件利息不存在 **/
    public static String APPLY_INTERESTING_FAIL = "0061";
    /** 不是审批中状态，不能进行审核 **/
    public static String BORROW_APPLY_STATUS_FAIL = "0062";
    /** 不是放款中状态，不能进行放款确认 **/
    public static String BORROW_APPLY_STATUS_LOAN_ING = "0063";
    /** 用户信息不存在 **/
    public static String USER_INFO_FAIL = "0065";
    /** 银行卡信息错误 **/
    public static String BANK_INFO_FAIL = "0066";
    /** 放款信息错误 **/
    public static String PAID_INFO_FAIL = "0074";
    /** 还款计划信息错误 **/
    public static String REPAYMENT_SCHEDULE_FAIL = "0075";
    /** 处理中状态，不能进行删除操作 **/
    public static String RECEIPTS_RECORD_STATUS_INF = "0078";
    /** 处理状态不正确，不能进行删除错误 **/
    public static String RECEIPTS_RECORD_STATUS_FAIL = "0079";
    /** 入账流水不存在 **/
    public static String RECEIPTS_RECORD_FAIL = "0080";
    /** 借款到期日计算为空 **/
    public static String BORROW_END_DATE_FAIL = "0085";
    /** 账户类型错误 **/
    public static String ACCOUNT_TYPE_FAIL = "0086";


    /** 还款日期选择错误**/
    public static String REPAYMENT_DATE_VILID = "1003";
    /** 还款日期已存在**/
    public static String REPAYMENT_DATE_EXITS = "1004";
    /** 应还本金金额大于放款金额**/
    public static String PRINCIPAL_GREATER_THAN_PAID_AMOUNT = "1005";
    /** 应还利息金额大于应还总利息**/
    public static String INTEREST_GREATER_THAN_TOTAL_INTEREST = "1006";
    /** 应还服务费金额大于应还总服务费**/
    public static String SERVICE_FEE_GREATER_THAN_TOTAL_SERVICE_FEE = "1007";
    /** 应还保证金金额大于应还总保证金**/
    public static String BAIL_GREATER_THAN_TOTAL_SERVICE_FEE = "1008";


}
