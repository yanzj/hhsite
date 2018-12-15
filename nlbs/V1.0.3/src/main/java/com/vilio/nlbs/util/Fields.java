package com.vilio.nlbs.util;

/**
 * Created by dell on 2017/5/10/0010.
 */
public class Fields {
    /* 参数传递消息头 */
    public final static String PARAM_MESSAGE_HEAD = "head";
    /* 参数传递消息体 */
    public final static String PARAM_MESSAGE_BODY = "body";
    /* 参数传递消息，错误号字段 */
    public final static String PARAM_MESSAGE_ERR_CODE = "returnCode";
    /* 参数传递消息，错误信息 */
    public final static String PARAM_MESSAGE_ERR_MESG = "returnMessage";
    /* 参数 功能号 */
    public final static String PARAM_FUNCTION_NO = "functionNo";
    /* 参数 功能号 */
    public final static String PARAM_FUNCTION_TYPE = "functionType";
    /* 参数 用户列表 */
    public final static String PARAM_USER_LIST = "userList";
    /* 参数 用户No */
    public final static String PARAM_USER_NO = "userNo";
    /* 核心系统存储的NLBS的录单员的ID */
    public final static String PARAM_CHANNEL_RECORDER_ID = "channelRecorderID";
    /* 参数 exceptUserNo */
    public final static String PARAM_EXCEPT_USER_NO = "exceptUserNo";
    /* 参数 用户Id */
    public final static String PARAM_USER_ID = "userId";
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
    /* 参数 业务员编号 */
    public final static String PARAM_AGENT_ID = "agentId";
    public final static String PARAM_APPLY_PEOPLE = "applyPeople";
    /* NLBS的业务经理的ID */
    public final static String PARAM_BIZ_MANAGER_ID = "bizManagerID";
    public final static String PARAM_BIZ_MANAGER_NAME = "bizManagerName";
    /* 参数 业务员姓名 */
    public final static String PARAM_AGENT_NAME = "agentName";
    /* 参数 业务员列表 */
    public final static String PARAM_AGENT_LIST = "agentList";
    /* 参数 产品编号 */
    public final static String PARAM_PRODUCT_CODE = "productCode";
    /* 参数 产品名称 */
    public final static String PARAM_PRODUCT_NAME = "productName";
    /* 参数 申请金额(贷款金额) */
    public final static String PARAM_LOAN_AMOUNT = "loanAmount";
    /* 参数 放款时间 */
    public final static String PARAM_LOAN_TIME = "loanTime";
    /* 参数 产品-最高可贷额度 */
    public final static String PARAM_MAX_LOAN_AMOUNT = "maxLoanAmount";
    /* 参数 产品列表 */
    public final static String PARAM_PRODUCT_LIST = "productList";
    /* 参数 更新数据数目 */
    public final static String PARAM_UPDATE_COUNT = "updateCount";
    public final static String PARAM_COUNT = "count";
    /* 参数 父菜单代码 */
    public final static String PARAM_FATHERMENUCODE = "fatherMenuCode";
    /* 参数 代码 */
    public final static String PARAM_CODE = "code";
    /* 参数 第一级菜单列表 */
    public final static String PARAM_FIRSTMENULIST = "firstMenuList";
    /* 参数 第二级菜单列表 */
    public final static String PARAM_SECONDMENULIST = "secondMenuList";
    /* 参数 时间戳 */
    public final static String PARAM_CLIENTTIMESTAMP = "clientTimestamp";
    /* 数据创建时间 */
    public final static String DB_DATE_CREATED = "date_created";
    /* 数据修改时间 */
    public final static String DB_DATE_MODIFIED = "date_modified";
    /* 简称 */
    public final static String DB_ABBR_NAME = "abbr_name";
    /* 全名 */
    public final static String DB_FULL_NAME = "full_name";
    /* 字段名称 */
    public final static String PARAM_NAME = "name";
    /* 城市ID */
    public final static String PARAM_CITYID = "cityId";
    /* 状态 */
    public final static String PARAM_STATUS = "status";
    /* 状态 */
    public final static String PARAM_STATUS_LIST = "statusList";
    /* 状态名称 */
    public final static String PARAM_STATUS_NAME = "statusName";
    /* 消息类型 */
    public final static String PARAM_MESSAGETYPE = "messageType";
    /* 消息来源系统 */
    public final static String PARAM_ORIGINALSYSTEMNO = "originalSystemNo";
    /* 消息目标系统 */
    public final static String PARAM_TARGETSYSTEMNO = "targetSystemNo";
    /* 消息内容 */
    public final static String PARAM_CONTENT = "content";
    /* 消息创建时间 */
    public final static String PARAM_DATECREATED = "dateCreated";
    /* 消息触发者 */
    public final static String PARAM_OPERATEUSERNO = "operateUserNo";
    /* 消息触发者姓名 */
    public final static String PARAM_OPERATEUSERNAME = "operateUserName";
    /* 消息触发者公司 */
    public final static String PARAM_OPERATECOMPANY = "operateCompany";
    /* 消息触发者部门 */
    public final static String PARAM_OPERATEDEPARTMENT = "operateDepartment";
    /* 消息接收人列表 */
    public final static String PARAM_RECEIVEUSERLIST = "receiveUserList";
    /* 消息接收人用户名 */
    public final static String PARAM_RECEIVEUSERNO = "receiveUserNo";
    /* 菜单级别 */
    public final static String PARAM_MENU_LEVEL = "menuLevel";
    /* 菜单列表 */
    public final static String PARAM_MENU_LIST = "menuList";
    public final static String PARAM_MENUS = "menus";
    /* 是否是录单员 */
    public final static String PARAM_BE_RECORD_CLERK = "beRecordClerk";
    public final static String PARAM_RECORD_CLERK_LIST = "recordClerkList";
    /* 是否是价格录入员 */
    public final static String PARAM_DISPLAY_INQUIRY = "displayInquiry";
    /* 渠道列表 */
    public final static String PARAM_DISTRIBUTRO_LIST = "distributorList";
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
    /* 状态列表 */
    public final static String PARAM_LOAN_STATUS_LIST = "loanStatusList";
    /* 状态列表 */
    public final static String PARAM_LOAN_STATUS = "loanStatus";
    public final static String PARAM_LOAN_STATUS_CODE = "loanStatusCode";
    /* 状态列表 */
    public final static String PARAM_LOAN_STATUS_NAME = "loanStatusName";
    /* 借款期限列表 */
    public final static String PARAM_LOAN_PERIOD_LIST = "loanPeriodList";
    /* 借款期限代码 */
    public final static String PARAM_LOAN_PERIOD_CODE = "loanPeriodCode";
    public final static String PARAM_APPLY_PERIOD = "applyPeriod";
    /* 借款期限名称 */
    public final static String PARAM_LOAN_PERIOD_NAME = "loanPeriodName";
    /* 放款方式列表 */
    public final static String PARAM_CREDIT_TYPE_LIST = "creditTypeList";
    /* 放款方式编码 */
    public final static String PARAM_CREDIT_TYPE_CODE = "creditTypeCode";
    public final static String PARAM_CREDITYPE_CODE = "creditypeCode";
    /* 放款方式名称 */
    public final static String PARAM_CREDIT_TYPE_NAME = "creditTypeName";
    /* 抵押方式列表 */
    public final static String PARAM_MORTGAGE_TYPE_LIST = "mortgageTypeList";
    /* 抵押方式编码 */
    public final static String PARAM_MORTGAGE_TYPE_CODE = "mortgageTypeCode";
    public final static String PARAM_MORTGAGE_TYPE = "mortgageType";
    /* 抵押方式名称 */
    public final static String PARAM_MORTGAGE_TYPE_NAME = "mortgageTypeName";
    /* 操作类型 */
    public final static String PARAM_OPERATION_TYPE = "operationType";
    public final static String PARAM_OPERATE_NAME = "operateName";
    public final static String PARAM_OPERATION_NAME = "operationName";
    public final static String PARAM_OPERATOR_FLAG = "operatorFlag";
    /* 参数 进件材料类型列表 */
    public final static String PARAM_MATERIAL_TYPE_LIST = "materialTypeList";
    /* 参数 进件材料类型 */
    public final static String PARAM_MATERIAL_TYPE_CODE = "materialTypeCode";
    /* 参数 进件材料类型 */
    public final static String PARAM_MATERIAL_TYPE_NAME = "materialTypeName";
    /* 参数 进件状态列表 */
    public final static String PARAM_APPLY_RECORD_STATUS_LIST = "statusList";
    /* 参数 分页尺寸 */
    public final static String PARAM_PAGE_SIZE = "pageSize";
    /* 参数 分页页码 */
    public final static String PARAM_PAGE_NO = "pageNo";
    /* 参数 总页数 */
    public final static String PARAM_PAGES = "pages";
    /* 参数 总条数 */
    public final static String PARAM_TOTAL = "total";
    /* 参数 当前页 */
    public final static String PARAM_CURRENT_PAGE = "currentPage";

    /* 进件列表 */
    public final static String PARAM_APPLY_LOAN_LIST = "applyLoanList";
    /* 进件编号 */
    public final static String PARAM_BMS_LOAN_CODE = "bmsLoanCode";
    public final static String PARAM_LOAN_CODE = "loanCode";
    public final static String PARAM_LOAN_SERIAL_NO = "loanSerialNo";
    public final static String PARAM_LOAN_ID = "loanId";
    /* 进件申请时间 */
    public final static String PARAM_APPLY_TIME = "applyTime";
    /* 进件状态 */
    public final static String PARAM_BUSINESS_STATUS = "businessStatus";
    public final static String PARAM_PRE_BUSINESS_STATUS = "preBusinessStatus";

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
    public final static String PARAM_CREATE_TIME = "createTime";
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
    /** 借款人 **/
    public final static String PARAM_CUSTOMER_NAME = "customerName";
    /** 借款金额 **/
    public final static String PARAM_APPLY_AMOUNT = "applyAmount";
    /** 借款时间 **/
    public final static String PARAM_APPLY_DATETIME = "applyDatetime";
    /** 手机加密验证码 **/
    public final static String PARAM_MOBILEPHONE_VALIDATE_NO = "mobilephoneValidateNo";
    public final static String PARAM_PHONE_VERIFICATE = "phoneVerificate";
    /** 意向金 **/
    public final static String PARAM_INTENTION_MONEY = "intentionMoney";
    /** 备注 **/
    public final static String PARAM_REMARK = "remark";
    /** 产证列表 **/
    public final static String PARAM_CERTIFICATE_LIST = "certificateList";
    public final static String PARAM_LOAN_PROPERTY_INFO_DTO_LIST = "loanPropertyInfoDtoList";
    /** 产证编号第一个字段 **/
    public final static String PARAM_CERTIFICATE_NUMBER_FIRST = "certificateNumberFirst";
    /** 产证编号第二个字段 **/
    public final static String PARAM_CERTIFICATE_NUMBER_SECOND = "certificateNumberSecond";
    /** 文件列表 **/
    public final static String PARAM_FILE_LIST = "fileList";
    /** 文件所属类型 **/
    public final static String PARAM_FILE_TYPE = "fileType";
    /** 文件后缀 **/
    public final static String PARAM_FILE_EXT = "fileExt";
    /** 文件名 **/
    public final static String PARAM_FILE_NAME = "fileName";
    public final static String PARAM_APPLY_FILE_NAME = "applyFileName";
    public final static String PARAM_APPLY_FILE_ID = "applyFileId";
    /** 文件原名 **/
    public final static String PARAM_ORIGINAL_FILE_NAME = "originalFileName";
    /** 文件ID **/
    public final static String PARAM_FILE_ID = "fileId";
    /** 文件上传时间 **/
    public final static String PARAM_UPLOAD_TIME = "uploadTime";
    /** 文件个数 **/
    public final static String PARAM_FILE_SIZE = "fileSize";
    /** 操作时间 **/
    public final static String PARAM_OPERATION_TIME = "operationTime";
    /** 操作用户 **/
    public final static String PARAM_OPERATION_USER = "operationUser";
    /** 操作历史列表 **/
    public final static String PARAM_OPERATION_HISTORY_LIST = "operationHistoryList";

    /* 人员信息列表 */
    public final static String PARAM_PERSON_LIST = "personList";
    /* 借款人信息列表 */
    public final static String PARAM_BORROWER_LIST = "borrowerList";
    /* 人员--姓名 */
    public final static String PARAM_PERSON_NAME = "name";
    public final static String PARAM_PERSON_NAME_USERD = "nameUsed";
    /* 人员--证件号码 */
    public final static String PARAM_PERSON_ID_NO = "idNo";
    /* 人员--年龄 */
    public final static String PARAM_PERSON_AGE = "age";
    /* 人员--证件类型 */
    public final static String PARAM_PERSON_ID_TYPE = "idType";
    /* 人员--有效期 */
    public final static String PARAM_PERSON_PERIOD_OF_VALIDITY_START = "periodOfValidityStart";
    public final static String PARAM_PERSON_PERIOD_OF_VALIDITY_END = "periodOfValidityEnd";
    /* 人员--移动电话 */
    public final static String PARAM_PERSON_MOBILEPHONE = "mobilePhone";
    /* 人员--工作单位 */
    public final static String PARAM_PERSON_ORGANIZATION = "organization";
    /* 人员--职位 */
    public final static String PARAM_PERSON_POSITION = "position";
    /* 人员--年收入 */
    public final static String PARAM_PERSON_ANNUAL = "annual";
    /* 人员--家庭地址 */
    public final static String PARAM_PERSON_ADDRESS = "address";
    public final static String PARAM_PERSON_MARRIAGE_INFO = "marriageInfo";
    /* 人员--婚史 */
    public final static String PARAM_PERSON_MARRIAGE = "marriage";

    /* 抵押物列表 */
    public final static String PARAM_MORTGAGE_LIST = "mortgageList";
    /* 产证编号 */
    public final static String PARAM_MORTGAGE_CERTIFICATE_NUMBER = "certificateNumber";
    /* 评估价 */
    public final static String PARAM_MORTGAGE_EVALUTION_PRICE = "evalutionPrice";
    /* 购买时间 */
    public final static String PARAM_MORTGAGE_BUYING_TIME = "buyingTime";
    /* 产权人 */
    public final static String PARAM_MORTGAGE_PROPERTY_OWNERS = "propertyOwners";
    /* 产权地址 */
    public final static String PARAM_MORTGAGE_PROPERTY_ADDRESS = "propertyAddress";
    /* 土地性质 */
    public final static String PARAM_MORTGAGE_LAND_CHARACTERISSTICS = "landCharacterisstics";
    /* 土地用途 */
    public final static String PARAM_MORTGAGE_LAND_USE = "landUse";
    /* 使用权取得方式 */
    public final static String PARAM_MORTGAGE_ACQUISITION_METHOD = "acquisitionMethod";
    /* 房屋所有权来源 */
    public final static String PARAM_SOURCE_OF_HOUSING_OWNERSHIP = "sourceOfHousingOwnership";
    /* 房屋使用情况 */
    public final static String PARAM_HOUSING_USAGE = "housingUsage";
    /* 是否唯一住房 */
    public final static String PARAM_IS_ONLY_HOUSING = "isOnlyHousing";
    /* 户口结构 */
    public final static String PARAM_HOUSEHOLD_REGISTRATION_STRUCTURE = "householdRegistrationStructure";
    /* 房屋类型 */
    public final static String PARAM_MORTGAGE_BUILDING_TYPE = "buildingType";
    /* 建筑面积 */
    public final static String PARAM_MORTGAGE_STRUCTURE_AREA = "structureArea";
    /* 车库地址 */
    public final static String PARAM_MORTGAGE_GARAGE_ADDRESS = "garageAddress";
    /* 车库面积 */
    public final static String PARAM_MORTGAGE_GARAGE_AREA = "garageArea";
    /* 总层数 */
    public final static String PARAM_MORTGAGE_TOTAL_FLOORS = "totalFloors";
    /* 竣工日期 */
    public final static String PARAM_MORTGAGE_END_DATE = "endDate";
    /* 共有类型 */
    public final static String PARAM_MORTGAGE_COMMON_TYPES = "commonTypes";
    /* 未成年人份额 */
    public final static String PARAM_MORTGAGE_MINOR_SHARE = "minorShare";
    /* 附记 */
    //public final static String PARAM_MORTGAGE_REMARK = "remark";//改为mortgageRemark
    /* 产调信息列表 */
    public final static String PARAM_MORTGAGE_INVESTINF_LIST = "investInfList";
    /* 产调信息记录列表 */
    public final static String PARAM_MORTGAGE_INVESTINF_RECORDS_LIST = "investInfRecordsList";
    /* 户口信息列表 */
    public final static String PARAM_MORTGAGE_RESIDENCEINF_LIST = "residenceInfList";
    /* 产调时间 */
    public final static String PARAM_INVESTINF_INVEST_TIME = "investTime";
    /*  在办案件 */
    public final static String PARAM_INVESTINF_DEALING_CASE = "dealingCase";
    /* 抵押权顺位 */
    public final static String PARAM_MORTGAGE_RANK = "mortgageRank";
    /* 债权类型 */
    public final static String PARAM_CREDITOR_RIGHT_TYPE = "creditorRightType";
    /* 债权性质 */
    public final static String PARAM_CREDITOR_RIGHT_NATURE = "creditorRightNature";
    /* 债权人 */
    public final static String PARAM_CREDITOR = "creditor";
    /* 债权总金额 */
    public final static String PARAM_TOTAL_DEBT_AMOUNT = "totalDebtAmount";
    /* 债权金额 */
    public final static String PARAM_DEBT_AMOUNT = "debtAmount";
    /* 一抵债权人 */
    public final static String PARAM_INVESTINF_FIRST_MORTGAGE_CREDITOR = "firstMortgageCreditor";
    /* 一抵债券金额 */
    public final static String PARAM_INVESTINF_FIRST_MORTGAGE_PRICE = "firstMortgagePrice";
    /* 一抵余额 */
    public final static String PARAM_INVESTINF_FIRST_MORTGAGE_BALANCE = "firstMortgageBalance";
    /* 一抵余额类型 */
    public final static String PARAM_INVESTINF_FIRST_MORTGAGE_BALANCE_TYPE = "firstMortgageBalanceType";
    /*  二抵债券人 */
    public final static String PARAM_INVESTINF_SECOND_MORTGAGE_CREDITOR = "secondMortgageCreditor";
    /* 二抵债券金额 */
    public final static String PARAM_INVESTINF_SECOND_MORTGAGE_PRICE = "secondMortgagePrice";
    /* 姓名 */
    public final static String PARAM_RESIDENCEINF_RESIDENCE_NAME = "residenceName";
    /* 身份证号 */
    public final static String PARAM_RESIDENCEINF_RESIDENCEID_NO = "residenceIdNo";

    /* 备用房信息列表 */
    public final static String PARAM_SPARE_HOUSE_LIST = "spareHouseList";
    /* 产权人 */
    public final static String PARAM_SPARE_HOUSE_OWNER = "owner";
    /* 备用房地址 */
    public final static String PARAM_SPARE_HOUSE_ADDRESS = "address";

    /* 征信信息列表 */
    public final static String PARAM_CREDITINF_LIST = "creditInfList";
    /*姓名 */
    public final static String PARAM_CREDITINF_NAME = "name";
    /* 征信报告时间*/
    public final static String PARAM_CREDITINF_REPORT_TIME = "reportTime";
    /*贷款笔数 */
    public final static String PARAM_CREDITINF_LOAN_COUNT = "loanCount";
    /* 合同总额*/
    public final static String PARAM_CREDITINF_TOTAL_CONTRACT = "totalContract";
    /* 余额*/
    public final static String PARAM_CREDITINF_BALANCE = "balance";
    /* 近6个月平均应还款*/
    public final static String PARAM_CREDITINF_SIXMONTH_AVERAGE_QUOTA_REPAY = "sixMonthAverageQuotaRepay";
    /*账户数 */
    public final static String PARAM_CREDITINF_ACCOUNT_NUM = "accountNum";
    /*授信总额 */
    public final static String PARAM_CREDITINF_TOTAL_CREDIT = "totalCredit";
    /* 已用额度*/
    public final static String PARAM_CREDITINF_QUOTA_USED = "quotaUsed";
    /*近6个月平均使用额度 */
    public final static String PARAM_CREDITINF_SIXMONTH_AVERAGE_QUOTA_USED = "sixMonthAverageQuotaUsed";
    /*贷款信息列表 */
    public final static String PARAM_CREDITINF_LOAN_INFO_LIST = "loanInfoList";
    /*担保信息列表 */
    public final static String PARAM_CREDITINF_GUARANTEE_LIST = "guaranteeList";
    /*贷款信息-序号 */
    public final static String PARAM_CREDITINF_LOAN_INFO_SERIALNO = "loanInfoSerialNo";
    /*贷款信息-类型 */
    public final static String PARAM_CREDITINF_LOAN_INFO_TYPE = "loanInfoType";
    /*贷款信息-详情 */
    public final static String PARAM_CREDITINF_LOAN_INFO_DETAILS = "loanInfoDetails";
    /*贷记卡信息列表 */
    public final static String PARAM_CREDITINF_LOAN_CARD_INFO_LIST = "loanCardInfoList";
    /*贷款信息-序号 */
    public final static String PARAM_CREDITINF_LOAN_CARD_INFO_SERIALNO = "loanCardInfoSerialNo";
    /*贷款信息-类型 */
    public final static String PARAM_CREDITINF_LOAN_CARD_INFO_TYPE = "loanCardInfoType";
    /*贷款信息-详情 */
    public final static String PARAM_CREDITINF_LOAN_CARD_INFO_DETAILS = "loanCardInfoDetails";
    /* 担保状态*/
    public final static String PARAM_CREDITINF_SECURED_STATUS = "securedStatus";
    /* 担保金额*/
    public final static String PARAM_CREDITINF_SECURED_PRICE = "securedPrice";
    /* 担保余额 */
    public final static String PARAM_CREDITINF_SECURED_BALANCE = "securedBalance";
    /* 近三个月查询次数 */
    public final static String PARAM_CREDITINF_THREEMONTH_QUERY_COUNT = "threeMonthQueryCount";
    /* 贷后管理次数 */
    public final static String PARAM_CREDITINF_AFTER_LOAN_MANAGE_COUNT = "afterLoanManageCount";
    /* 担保资格审查次数 */
    public final static String PARAM_CREDITINF_SECURED_CHECKUP_COUNT = "securedCheckupCount";
    /* 备注 */
    public final static String PARAM_CREDITINF_CREDIT_INFO_REMARK = "creditInfoRemark";

    /* 司法信息列表 */
    public final static String PARAM_JUDICIALINF_LIST = "judicialInfList";
    /* 司法信息-姓名 */
    public final static String PARAM_JUDICIALINF_NAME = "judicialInfName";
    /* 是否有司法诉讼 */
    public final static String PARAM_JUDICIALINF_LITIGATION = "litigation";
    /* 立案号 */
    public final static String PARAM_JUDICIALINF_REGISTRATION_NO = "registrationNo";
    /* 是否结案 */
    public final static String PARAM_JUDICIALINF_IS_CLOSED = "isClosed";
    /* 执行标的 */
    public final static String PARAM_JUDICIALINF_EXECUTE_TARGET = "executeTarget";
    /* 情况说明 */
    public final static String PARAM_JUDICIALINF_STATUS_STATEMENT = "statusStatement";

    /* 是否显示案件概况 */
    public final static String PARAM_DISPLAY_CASE_INFO = "displayCaseInfo";
    /* 案件概况 */
    public final static String PARAM_CASE_INFO = "caseInfo";
    /* 家庭主要资产 */
    public final static String PARAM_MAIN_HOUSEHOLD_ASSET = "mainHouseholdAsset";
    /* 家庭主要负债 */
    public final static String PARAM_MAIN_HOUSEHOLD_LIABILITIES = "mainHouseholdLiabilities";
    /* 家庭主要收入来源 */
    public final static String PARAM_MAIN_HOUSEHOLD_INCOME_SOURCE = "mainHouseholdIncomeSource";
    /* 抵押物实地调研情况 */
    public final static String PARAM_FIELD_INVESTIGATION_OF_COLLATERAL = "fieldInvestigationOfCollateral";
    /* 借款用途 */
    public final static String PARAM_USAGE_OF_LOAN = "usageOfLoan";
    /* 还款来源 */
    public final static String PARAM_PAYMENT_SOURCE = "paymentSource";

    //请求参数
    public final static String PARAM_COMPANY_PARAM_LIST = "companyParamList";
    //返回价格列
    public final static String PARAM_COMPANY_PRICE_LIST = "companyPriceList";
    /* 渠道列表 */
    public final static String PARAM_COMPANY_LIST = "companyList";
    /* 渠道代码 */
    public final static String PARAM_COMPANY_CODE = "companyCode";
    /* 渠道名称 */
    public final static String PARAM_COMPANY_NAME = "companyName";
    /* 小区列表 */
    public final static String PARAM_ALL_PLOTS_LIST = "allPlotsList";
    /* 小区列表 */
    public final static String PARAM_PLOTS_LIST = "plotsList";
    /* 小区代码 */
    public final static String PARAM_PLOTS_CODE = "plotsCode";
    /* 小区名称 */
    public final static String PARAM_PLOTS_NAME = "plotsName";
    /* 楼栋列表 */
    public final static String PARAM_UNIT_LIST = "unitList";
    /* 楼栋列表 */
    public final static String PARAM_UNIT_CODE = "unitCode";
    /* 楼栋列表 */
    public final static String PARAM_UNIT_NAME = "unitName";
    /* 房屋列表 */
    public final static String PARAM_HOUSE_LIST = "houseList";
    /* 房屋代码 */
    public final static String PARAM_HOUSE_CODE = "houseCode";
    /* 房屋名称 */
    public final static String PARAM_HOUSE_NAME = "houseName";

    /* 价格 */
    public final static String PARAM_PRICE = "price";
    /* 面积 */
    public final static String PARAM_AREA = "area";
    /* 朝向 */
    public final static String PARAM_TOWARDS = "towards";
    /* 建成年代 */
    public final static String PARAM_YEAR_BUILT = "yearBuilt";
    /* 建成年代 */
    public final static String PARAM_TYPE_CODE = "TypeCode";

    /* 行政区域列表 */
    public final static String PARAM_AREA_LIST = "areaList";
    /* 行政区域代码 */
    public final static String PARAM_AREA_CODE = "areaCode";
    /* 行政区域名称 */
    public final static String PARAM_AREA_NAME = "areaName";
    /* 房屋类型代码 */
    public final static String PARAM_HOUSE_TYPE_CODE = "houseTypeCode";
    /* 房屋类型名称 */
    public final static String PARAM_HOUSE_TYPE_NAME = "houseTypeName";
    /* 房屋类型列表 */
    public final static String PARAM_HOUSE_TYPE_LIST = "houseTypeList";

    /* 状态 */
    public final static String PARAM_ASSESSMENT_STATUS = "assessmentStatus";
    /* 状态 */
    public final static String PARAM_ASSESSMENT_PRICE = "assessmentPrice";
    /* 状态 */
    public final static String PARAM_ASSESSMENT_TIME = "assessmentTime";
    /** serial_no **/
    public final static String PARAM_SERIAL_NO = "serialNo";
    /** serial_no_List **/
    public final static String PARAM_SERIAL_NO_LIST = "serialNoList";
    /** 系统号 **/
    public final static String PARAM_SOURCE_SYSTEM = "sourceSystem";

    /* 地址 */
    public final static String PARAM_ADDRESS = "address";

    /** 待办任务列表 **/
    public final static String PARAM_TODO_TASK_LIST = "todoTaskList";
    /** 待办任务提醒 **/
    public final static String PARAM_TODO_TIPS_LIST = "todoTipsList";
    /** 待办任务个数 **/
    public final static String PARAM_TODO_COUNT = "todoCount";
    /** 未读消息个数 **/
    public final static String PARAM_MESSAGE_COUNT = "messageCount";
    /** 消息提醒列表 **/
    public final static String PARAM_MESSAGE_TIPS_LIST = "messageTipsList";
    /** 询价记录列表 **/
    public final static String PARAM_INQUIRY_APPLY_LIST = "inquiryApplyList";
    /** 是否极速询价 **/
    public final static String PARAM_AUTO_PRICE = "autoPrice";
    /** 待办任务类型 **/
    public final static String PARAM_TODO_TYPE = "todoType";

    //接收消息Code前缀
    public final static String RECEIVE_MESSAGE_CODE_PREFIX = "M";
    //接收消息Code后缀
    public final static String RECEIVE_MESSAGE_CODE_SUFFIX = null;

    //pendingUserNo
    public final static String PARAM_PENDING_USER_NO = "pendingUserNo";

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
    public final static String PARAM_ROLE_LIST = "roleList";
    /* 类型 */
    public final static String PARAM_TYPE = "type";

    /* 用户列表 */
    public final static String PARAM_USERS = "users";
    /* 机构列表 */
    public final static String PARAM_GROUPS = "groups";
    /* 子用户列表 */
    public final static String PARAM_CHILD_USERS = "childUsers";
    /* 子机构列表 */
    public final static String PARAM_CHILD_GROUPS = "childGroups";


    /* 估值 */
    public final static String PARAM_EVALUATION_PRICE = "evaluationPrice";

    /* 审批通知 */
    public final static String PARAM_APPROVAL_NOTICE = "approvalNotice";
    /* 批复日期 */
    public final static String PARAM_APPROVAL_DATE = "approvalDate";
    /* 批复额度 */
    public final static String PARAM_REPLY_AMOUNT = "replyAmount";
    /* 批复期限 */
    public final static String PARAM_REPLY_TIME_LIMIT = "replyTimeLimit";
    /* 担保额度 */
    public final static String PARAM_SECURED_AMOUNT = "securedAmount";
    /* 担保费率 */
    public final static String PARAM_SECURED_RATE = "securedRate";
    /* 担保费 */
    public final static String PARAM_SECURED_FEE = "securedFee";
    /* 保证金比例 */
    public final static String PARAM_CASH_DEPOSIT_RATE = "cashDepositRate";
    /* 保证金金额 */
    public final static String PARAM_CASH_DEPOSIT_AMOUNT = "cashDepositAmount";
    /* 手续费率 */
    public final static String PARAM_FACTORAGE_RATE = "factorageRate";
    /* 手续费金额 */
    public final static String PARAM_FACTORAGE_AMOUNT = "factorageAmount";
    /* 放款条件 */
    public final static String PARAM_OFFER_LOANS_CONDITION = "offerLoansCondition";
    /* 担保条件 */
    public final static String PARAM_SECURED_CONDITION = "securedCondition";
    /* 年化利率 */
    public final static String PARAM_YEAR_RATE = "yearRate";
    /* 债权金额 */
    public final static String PARAM_PECUNIA_CREDITA = "pecuniaCredita";
    /* 借款人 */
    public final static String PARAM_LOANERS = "loaners";
    /* 抵押率 */
    public final static String PARAM_MORTGAGE_RATE = "mortgageRate";
    /* 服务费 */
    public final static String PARAM_SERVICE_CHARGE = "serviceCharge";
    /* 合同抵押物价值 */
    public final static String PARAM_MORTGAGE_ASSESS_AMOUNT = "mortgageAssessAmount";
    /* 风控初审意见 */
    public final static String PARAM_FIRST_TRIAL_OPINION = "firstTrialOpinion";
    /* 风控复审意见 */
    public final static String PARAM_RETRIAL_OPINION = "retrialOpinion";
    /* 风控终审意见 */
    public final static String PARAM_FINAL_TRIAL_OPINION = "finalTrialOpinion";

    /* 放款账户信息 */
    public final static String PARAM_LOAN_ACCOUNT_INFO = "loanAccountInfo";
    /* 放款账户 */
    public final static String PARAM_LOAN_ACCOUNT = "loanAccount";
    /* 放款账户名 */
    public final static String PARAM_LOAN_ACCOUNT_NAME = "loanAccountName";
    /* 放款账户开户行 */
    public final static String PARAM_LOAN_ACCOUNT_BANK = "loanAccountBank";
    /* 利息归还账户信息 */
    public final static String PARAM_INTEREST_ACCOUNT_INFO = "interestAccountInfo";
    /* 利息归还账户 */
    public final static String PARAM_INTEREST_ACCOUNT = "interestAccount";
    /* 利息归还账户名 */
    public final static String PARAM_INTEREST_ACCOUNT_NAME = "interestAccountName";
    /* 利息归还账户开户行 */
    public final static String PARAM_INTEREST_ACCOUNT_BANK = "interestAccountBank";
    /* 本金归还账户信息 */
    public final static String PARAM_PRINCIPAL_ACCOUNT_INFO = "principalAccountInfo";
    /* 本金归还账户 */
    public final static String PARAM_PRINCIPAL_ACCOUNT = "principalAccount";
    /* 本金归还账户名 */
    public final static String PARAM_PRINCIPAL_ACCOUNT_NAME = "principalAccountName";
    /* 本金归还账户开户行 */
    public final static String PARAM_PRINCIPAL_ACCOUNT_BANK = "principalAccountBank";

    /* 材料文件列表 */
    public final static String PARAM_UPLOAD_FILE_LIST = "uploadFileList";

    /* 签约公证 */
    public final static String PARAM_SIGNING_NOTARIZATION = "signingNotarization";
    /* 放款信息 */
    public final static String PARAM_LOAN_INFO = "loanInfo";
    public final static String PARAM_LOAN_INFO_LIST = "loanInfoList";
    /* 签约公证时间 */
    public final static String PARAM_SIGN_TIME = "signTime";
    /* 签约备注 */
    public final static String PARAM_SIGN_REMARK = "signRemark";
    /* 签约公证材料 */
    public final static String PARAM_SIGN_FILE_LIST = "signFileList";
    /* 担保材料 */
    public final static String PARAM_GUARANTEE_FILE_LIST = "guaranteeFileList";
    /* 是否选择担保公司 */
    public final static String PARAM_HAVE_GUARANTEE_COMPANY = "haveGuaranteeCompany";
    /* 保险材料 */
    public final static String PARAM_SECURE_FILE_LIST = "secureFileList";
    /* 是否选择保险公司 */
    public final static String PARAM_HAVE_SECURE_COMPANY = "haveSecureCompany";
    /* 抵押材料 */
    public final static String PARAM_MORTGAGE_FILE_LIST = "mortgageFileList";
    /* 抵押时间 */
    public final static String PARAM_MORTGAGE_TIME = "mortgageTime";
    /* 抵押备注 */
    public final static String PARAM_MORTGAGE_REMARK = "mortgageRemark";
    /* 产调查询材料 */
    public final static String PARAM_INVEST_FILE_LIST = "investFileList";

}
