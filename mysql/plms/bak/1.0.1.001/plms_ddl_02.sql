/*
Navicat MySQL Data Transfer

Source Server         : dev
Source Server Version : 50716
Source Host           : 192.168.0.5:3306
Source Database       : plms

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-09-11 17:47:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for plms_account
-- ----------------------------
DROP TABLE IF EXISTS `plms_account`;
CREATE TABLE `plms_account` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `total_quota` decimal(20,2) DEFAULT NULL COMMENT '授信总额',
  `principal` decimal(20,2) DEFAULT NULL COMMENT '已贷本金',
  `remaining_quota` decimal(20,2) DEFAULT NULL COMMENT '剩余额度',
  `interest` decimal(20,2) DEFAULT NULL COMMENT '应还利息',
  `overdue` decimal(20,2) DEFAULT NULL COMMENT '应还罚金',
  `credit_end_date` datetime DEFAULT NULL COMMENT '授信有效期',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `repaymented_principal` decimal(20,2) DEFAULT NULL,
  `repaymented_interest` decimal(20,2) DEFAULT NULL,
  `repaymented_overdue` decimal(20,2) DEFAULT NULL,
  `repaymented_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还提前还款违约金',
  `service_fee` decimal(20,2) DEFAULT NULL COMMENT '应还服务费',
  `service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还服务费违约金',
  `repaymented_ahead_interest_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还提前还款利息违约金',
  `repaymented_ahead_service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还提前还款服务费违约金',
  `user_code` varchar(36) DEFAULT NULL COMMENT '用户信息表code',
  `bail` decimal(20,2) DEFAULT NULL COMMENT '应还保证金',
  `bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还保证金违约金',
  `repaymented_bail` decimal(20,2) DEFAULT NULL COMMENT '已还保证金',
  `repaymented_bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还保证金违约金',
  `repaymented_service_fee` decimal(20,2) DEFAULT NULL COMMENT '已还服务费',
  `repaymented_service_fee_penalty` varchar(255) DEFAULT NULL COMMENT '已还服务费违约金',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `plms_account_fk1` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='账户汇总信息表';

-- ----------------------------
-- Table structure for plms_account_detail
-- ----------------------------
DROP TABLE IF EXISTS `plms_account_detail`;
CREATE TABLE `plms_account_detail` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `total_quota` decimal(20,2) DEFAULT NULL COMMENT '授信总额',
  `principal` decimal(20,2) DEFAULT NULL COMMENT '已贷本金',
  `remaining_quota` decimal(20,2) DEFAULT NULL COMMENT '剩余额度',
  `interest` decimal(20,2) DEFAULT NULL COMMENT '应还利息',
  `overdue` decimal(20,2) DEFAULT NULL COMMENT '应还罚金',
  `credit_end_date` datetime DEFAULT NULL COMMENT '授信有效期',
  `account_code` varchar(36) DEFAULT NULL COMMENT '账户汇总信息表code',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `first_amount` decimal(20,2) DEFAULT NULL COMMENT '首次贷款金额',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `repaymented_principal` decimal(20,2) DEFAULT NULL COMMENT '已还本金',
  `repaymented_interest` decimal(20,2) DEFAULT NULL COMMENT '已还利息',
  `repaymented_overdue` decimal(20,2) DEFAULT NULL COMMENT '已还罚金',
  `repaymented_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还违约金',
  `service_fee` decimal(20,2) DEFAULT NULL,
  `service_fee_penalty` decimal(20,2) DEFAULT NULL,
  `fund_side_balance` decimal(20,2) DEFAULT NULL COMMENT '资方账户余额',
  `honghuo_balance` decimal(20,2) DEFAULT NULL COMMENT '宏获账户余额',
  `repaymented_ahead_interest_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还提前还款利息违约金',
  `repaymented_ahead_service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还提前还款服务费违约金',
  `confirmed` varchar(2) NOT NULL,
  `bail` decimal(20,2) DEFAULT NULL COMMENT '应还保证金',
  `bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还保证金违约金',
  `repaymented_bail` decimal(20,2) DEFAULT NULL COMMENT '已还保证金',
  `repaymented_bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还保证金违约金',
  `principal_date` date DEFAULT NULL,
  `repaymented_service_fee` decimal(20,2) DEFAULT NULL COMMENT '已还服务费',
  `repaymented_service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还服务费违约金',
  `honghuo_bail_account_balance` decimal(20,2) DEFAULT NULL COMMENT '宏获保证金账户余额',
  PRIMARY KEY (`id`),
  KEY `contract_code` (`contract_code`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='账户明细信息表';

-- ----------------------------
-- Table structure for plms_account_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_account_info`;
CREATE TABLE `plms_account_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `bank` varchar(200) DEFAULT NULL COMMENT '开户行',
  `province` varchar(10) DEFAULT NULL COMMENT '省',
  `city` varchar(10) DEFAULT NULL COMMENT '市',
  `account_no` varchar(30) DEFAULT NULL COMMENT '申请人收款账户',
  `status` varchar(2) NOT NULL COMMENT '状态（0、无效；1、有效）',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=655 DEFAULT CHARSET=utf8 COMMENT='银行账户表';

-- ----------------------------
-- Table structure for plms_agent
-- ----------------------------
DROP TABLE IF EXISTS `plms_agent`;
CREATE TABLE `plms_agent` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `title` varchar(20) DEFAULT NULL COMMENT '职务',
  `mobile_no` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `parent_user` varchar(36) DEFAULT NULL COMMENT '上级用户',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `bms_code` varchar(36) DEFAULT NULL COMMENT '核心系统code',
  `distributor_code` varchar(36) DEFAULT NULL COMMENT '渠道表code',
  PRIMARY KEY (`id`,`code`),
  UNIQUE KEY `code` (`code`),
  KEY `parent_user` (`parent_user`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='业务经理表';

-- ----------------------------
-- Table structure for plms_apply_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_apply_info`;
CREATE TABLE `plms_apply_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `apply_date` datetime DEFAULT NULL COMMENT '申请日期',
  `apply_amount` decimal(20,2) DEFAULT NULL COMMENT '申请金额',
  `apply_period` int(11) DEFAULT NULL COMMENT '申请期限',
  `lending_methods` varchar(2) DEFAULT NULL COMMENT '放款方式（01、他证；02、收件收据）',
  `intention_money` decimal(20,2) DEFAULT NULL COMMENT '意向金',
  `identifying_code` varchar(36) DEFAULT NULL COMMENT '手机验证码',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `account_code` varchar(36) DEFAULT NULL COMMENT '申请人收款账户',
  `agent_code` varchar(36) DEFAULT NULL COMMENT '业务员经理表code',
  `distribute_code` varchar(36) DEFAULT NULL COMMENT '渠道表code',
  `bms_code` varchar(36) DEFAULT NULL COMMENT '核心系统code',
  `user_code` varchar(36) DEFAULT NULL COMMENT '用户信息表code',
  `clerk` varchar(36) DEFAULT NULL COMMENT '录单员编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=274 DEFAULT CHARSET=utf8 COMMENT='申请信息表';

-- ----------------------------
-- Table structure for plms_apply_interesting
-- ----------------------------
DROP TABLE IF EXISTS `plms_apply_interesting`;
CREATE TABLE `plms_apply_interesting` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `annualized_interest` decimal(20,6) DEFAULT NULL COMMENT '年化利率',
  `principal_over_interest` decimal(20,6) DEFAULT NULL COMMENT '本金逾期年化利率',
  `interest_over_interest` decimal(20,6) DEFAULT NULL COMMENT '利息逾期年化利率',
  `default_interest_rate` decimal(20,6) DEFAULT NULL COMMENT '违约金利率',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `is_principal_instead` tinyint(1) DEFAULT NULL COMMENT '本金是否代收代付(0 否，1 是)',
  `is_interest_instead` tinyint(1) DEFAULT NULL COMMENT '利息是否代收代付（0、否;1、是）',
  `is_full_repurchase` tinyint(1) DEFAULT NULL COMMENT '是否全额回购（0、否;1、是）',
  `service_fee_interest_rate` decimal(20,6) DEFAULT NULL COMMENT '服务费违约金利率',
  `fund_side_code` varchar(36) DEFAULT NULL,
  `honghuo_code` varchar(36) DEFAULT NULL,
  `honghuo_bail_account_code` varchar(36) DEFAULT NULL COMMENT '宏获保证金账户code',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `contract_code` (`contract_code`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COMMENT='进件利息信息表';

-- ----------------------------
-- Table structure for plms_approval
-- ----------------------------
DROP TABLE IF EXISTS `plms_approval`;
CREATE TABLE `plms_approval` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `first_approval_suggestion` varchar(1000) DEFAULT NULL COMMENT '初审意见',
  `guarantee_condition` varchar(1000) DEFAULT NULL COMMENT '担保条件',
  `lending_terms` varchar(1000) DEFAULT NULL COMMENT '放款条件',
  `approval_quota` decimal(20,2) DEFAULT NULL COMMENT '批复额度',
  `guarantee_limit` decimal(20,2) DEFAULT NULL COMMENT '担保额度',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `second_approval_suggesion` varchar(1000) DEFAULT '' COMMENT '复审意见',
  `final_approval_suggesion` varchar(1000) DEFAULT NULL COMMENT '终审意见',
  `fund_side_suggesion` varchar(1000) DEFAULT NULL,
  `guarantee_suggesion` varchar(1000) DEFAULT NULL,
  `insurance_suggesion` varchar(1000) DEFAULT NULL,
  `approval_period` int(4) DEFAULT NULL,
  `approval_time` datetime DEFAULT NULL COMMENT '批复时间',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '审批信息表code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='审批信息表';

-- ----------------------------
-- Table structure for plms_area
-- ----------------------------
DROP TABLE IF EXISTS `plms_area`;
CREATE TABLE `plms_area` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `area_name` varchar(10) DEFAULT NULL COMMENT '区域名称',
  `status` tinyint(1) NOT NULL COMMENT '状态（0、无效；1、有效）',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `bms_code` varchar(36) DEFAULT NULL COMMENT '核心系统code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Table structure for plms_backend_user
-- ----------------------------
DROP TABLE IF EXISTS `plms_backend_user`;
CREATE TABLE `plms_backend_user` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `um_id` varchar(22) DEFAULT NULL COMMENT 'umId',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `mobile_no` varchar(11) NOT NULL COMMENT '移动电话',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `parent_code` varchar(36) DEFAULT NULL COMMENT '上级用户code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='贷后用户信息表';

-- ----------------------------
-- Table structure for plms_bms_synchronize
-- ----------------------------
DROP TABLE IF EXISTS `plms_bms_synchronize`;
CREATE TABLE `plms_bms_synchronize` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `synchronize_info` mediumtext COMMENT '同步信息',
  `status` varchar(2) NOT NULL COMMENT '状态(01、未同步;02、同步中;03、已同步;04、同步异常;00、无效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_borrow_apply
-- ----------------------------
DROP TABLE IF EXISTS `plms_borrow_apply`;
CREATE TABLE `plms_borrow_apply` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '借款金额',
  `apply_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '申请时间',
  `apply_status` varchar(2) NOT NULL COMMENT '申请状态(01、放款中;02、审核中;03、放款成功;04、审核未通过)',
  `business_code` varchar(20) DEFAULT NULL COMMENT '业务编号',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息编号',
  `borrow_period` int(2) DEFAULT NULL COMMENT '借款期数',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(100) DEFAULT NULL COMMENT '审批意见',
  `user_code` varchar(36) DEFAULT NULL,
  `borrow_end_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='借款申请表';

-- ----------------------------
-- Table structure for plms_borrow_voucher
-- ----------------------------
DROP TABLE IF EXISTS `plms_borrow_voucher`;
CREATE TABLE `plms_borrow_voucher` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `file_code` varchar(36) DEFAULT NULL COMMENT '文件系统code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `paid_info_code` varchar(36) DEFAULT NULL COMMENT '放款信息表code',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='借款凭证表';

-- ----------------------------
-- Table structure for plms_case_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_case_info`;
CREATE TABLE `plms_case_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `major_asset` varchar(1000) DEFAULT NULL COMMENT '家庭主要资产',
  `major_liabilities` varchar(1000) DEFAULT NULL COMMENT '家庭主要负债',
  `income_sources` varchar(1000) DEFAULT NULL COMMENT '家庭主要收入来源',
  `collateral_investigation` varchar(1000) DEFAULT NULL COMMENT '抵押物实地调研情况',
  `loan_usage` varchar(1000) DEFAULT NULL COMMENT '借款用途',
  `repayment_source` varchar(1000) DEFAULT NULL COMMENT '还款来源',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `apply_code` (`apply_code`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8 COMMENT='案件信息表';

-- ----------------------------
-- Table structure for plms_city
-- ----------------------------
DROP TABLE IF EXISTS `plms_city`;
CREATE TABLE `plms_city` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `abbr_name` varchar(10) DEFAULT NULL COMMENT '简称',
  `full_name` varchar(20) DEFAULT NULL COMMENT '全称',
  `order_by_no` varchar(4) DEFAULT NULL COMMENT '序号',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `area_code` varchar(36) DEFAULT NULL COMMENT '区域表code',
  `bms_code` varchar(36) DEFAULT NULL COMMENT '核心系统code',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='城市表';

-- ----------------------------
-- Table structure for plms_company
-- ----------------------------
DROP TABLE IF EXISTS `plms_company`;
CREATE TABLE `plms_company` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `company_name` varchar(36) DEFAULT NULL COMMENT '公司名称',
  `company_type` varchar(2) DEFAULT NULL COMMENT '公司类型(01、保险公司;02、担保公司;03、资方)',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `abbr_name` varchar(20) DEFAULT NULL COMMENT '简称',
  `bms_code` varchar(36) DEFAULT NULL COMMENT '核心系统code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=605 DEFAULT CHARSET=utf8 COMMENT='公司信息表';

-- ----------------------------
-- Table structure for plms_contract_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_contract_info`;
CREATE TABLE `plms_contract_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `contract_no` varchar(100) DEFAULT NULL,
  `credit_start_date` datetime DEFAULT NULL,
  `credit_end_date` datetime DEFAULT NULL,
  `credit_period` int(4) DEFAULT NULL COMMENT '授信期限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8 COMMENT='合同信息表';

-- ----------------------------
-- Table structure for plms_creadit_card
-- ----------------------------
DROP TABLE IF EXISTS `plms_creadit_card`;
CREATE TABLE `plms_creadit_card` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `card_no` varchar(10) DEFAULT NULL COMMENT '贷记卡序号',
  `overdue_amount` decimal(20,2) DEFAULT NULL COMMENT '逾期金额',
  `card_status` varchar(20) DEFAULT NULL COMMENT '贷记卡状态',
  `overdue_num` int(11) DEFAULT NULL COMMENT '逾期次数',
  `period` int(11) DEFAULT NULL COMMENT '期数',
  `credit_code` varchar(36) DEFAULT NULL COMMENT '征信信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `type` varchar(2) DEFAULT NULL COMMENT '类型（01、当前逾期；02、非正常类贷款；03、近12个月累计最高逾期次数；04、近12个月最高连续逾期期数）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `credit_code` (`credit_code`)
) ENGINE=InnoDB AUTO_INCREMENT=457 DEFAULT CHARSET=utf8 COMMENT='贷记卡信息表';

-- ----------------------------
-- Table structure for plms_credit_investigation
-- ----------------------------
DROP TABLE IF EXISTS `plms_credit_investigation`;
CREATE TABLE `plms_credit_investigation` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `name` varchar(40) DEFAULT NULL COMMENT '姓名',
  `id_type` varchar(2) DEFAULT NULL COMMENT '身份证类型',
  `id_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `report_time` datetime DEFAULT NULL COMMENT '征信报告时间',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `apply_code` (`apply_code`)
) ENGINE=InnoDB AUTO_INCREMENT=198 DEFAULT CHARSET=utf8 COMMENT='征信信息表';

-- ----------------------------
-- Table structure for plms_customer
-- ----------------------------
DROP TABLE IF EXISTS `plms_customer`;
CREATE TABLE `plms_customer` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `name` varchar(50) DEFAULT NULL COMMENT '借款人名称',
  `old_name` varchar(1000) DEFAULT NULL COMMENT '曾用名',
  `id_type` varchar(2) DEFAULT NULL COMMENT '证件类型(01、身份证)',
  `id_no` varchar(18) DEFAULT NULL COMMENT '证件号码',
  `start_time` varchar(20) DEFAULT NULL COMMENT '证件有效期（起）',
  `end_time` varchar(20) DEFAULT NULL COMMENT '证件有效期（止）',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `mobile` varchar(11) DEFAULT NULL COMMENT '移动电话',
  `organization` varchar(200) DEFAULT NULL COMMENT '工作单位',
  `titile` varchar(40) DEFAULT NULL COMMENT '职位',
  `annual_income` decimal(20,2) DEFAULT NULL COMMENT '年收入',
  `address` varchar(400) DEFAULT NULL COMMENT '家庭地址',
  `marriage` varchar(2) DEFAULT NULL COMMENT '当前婚姻状况(01、未婚;02、已婚;03、离异;04、丧偶;05、重婚;06、再婚)',
  `marriage_history` varchar(1000) DEFAULT NULL COMMENT '婚史信息',
  `is_main` tinyint(1) DEFAULT NULL COMMENT '是否主借款人(0、否;1、是)',
  `account_no` varchar(20) DEFAULT NULL COMMENT '打款账号',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `apply_code` (`apply_code`)
) ENGINE=InnoDB AUTO_INCREMENT=411 DEFAULT CHARSET=utf8 COMMENT='借款人信息表';

-- ----------------------------
-- Table structure for plms_customer_fee_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_customer_fee_info`;
CREATE TABLE `plms_customer_fee_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `service_charge_rate` decimal(10,2) DEFAULT NULL COMMENT '手续费比例',
  `service_charge_amount` decimal(20,2) DEFAULT NULL COMMENT '手续费金额',
  `service_charge_receive_time` datetime DEFAULT NULL COMMENT '手续费实收时间',
  `guarantee_fee_rate` decimal(10,2) DEFAULT NULL COMMENT '担保费率',
  `guarantee_fee_amount` decimal(20,2) DEFAULT NULL COMMENT '担保费金额',
  `guarantee_fee_receive_time` datetime DEFAULT NULL COMMENT '担保费实收时间',
  `bail_rate` decimal(10,2) DEFAULT NULL COMMENT '保证金比例',
  `bail_amount` decimal(20,2) DEFAULT NULL COMMENT '保证金金额',
  `bail_receive_time` datetime DEFAULT NULL COMMENT '保证金实收时间',
  `performance_bond_rate` decimal(10,2) DEFAULT NULL COMMENT '履约保证保险费率',
  `performance_bond_amount` decimal(20,2) DEFAULT NULL COMMENT '履约保证保险费金额',
  `second_insurance_name` varchar(50) DEFAULT NULL COMMENT '险种2名称',
  `second_insurance_fee_rate` decimal(10,2) DEFAULT NULL COMMENT '险种2费率',
  `second_insurance_fee_amount` decimal(20,2) DEFAULT NULL COMMENT '险种2费用金额',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `service_charge_receive_amouont` decimal(20,2) DEFAULT NULL,
  `guarantee_fee_receive_amount` decimal(20,2) DEFAULT NULL,
  `bail_receive_amount` decimal(20,2) DEFAULT NULL,
  `performance_bond_receive_amount` decimal(20,2) DEFAULT NULL,
  `second_insurance_fee_receive_amount` decimal(20,2) DEFAULT NULL,
  `performance_bond_receive_time` datetime DEFAULT NULL COMMENT '履约保证金实收时间',
  `second_insurance_fee_receive_time` datetime DEFAULT NULL COMMENT '险种2费用实收时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 COMMENT='客户费用信息表';

-- ----------------------------
-- Table structure for plms_distributor
-- ----------------------------
DROP TABLE IF EXISTS `plms_distributor`;
CREATE TABLE `plms_distributor` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `order_no` int(10) DEFAULT NULL COMMENT '序号',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `full_name` varchar(100) DEFAULT NULL COMMENT '渠道名称',
  `abbr_name` varchar(20) DEFAULT NULL COMMENT '渠道简称',
  `type` varchar(2) DEFAULT NULL COMMENT '渠道类型(01、直营;02、外部)',
  `parent_distributor` varchar(36) DEFAULT NULL COMMENT '上级渠道编号',
  `city_no` varchar(36) DEFAULT NULL COMMENT '城市表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `bms_code` varchar(36) DEFAULT NULL COMMENT '核心系统code',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `parent_distributor` (`parent_distributor`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='渠道信息表';

-- ----------------------------
-- Table structure for plms_email_info_log
-- ----------------------------
DROP TABLE IF EXISTS `plms_email_info_log`;
CREATE TABLE `plms_email_info_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `system_no` varchar(40) NOT NULL COMMENT '系统调用流水号',
  `display_name` varchar(255) DEFAULT NULL COMMENT '发件人名称（可空）',
  `user_name` varchar(50) NOT NULL COMMENT '发件人',
  `to_user_list` varchar(2000) NOT NULL COMMENT '发送人列表（最大支持20个）',
  `to_cc_list` varchar(2000) DEFAULT NULL COMMENT '抄送列表',
  `subject` varchar(100) NOT NULL COMMENT '标题',
  `content` varchar(2000) NOT NULL COMMENT '发送内容',
  `url_file_list` varchar(2000) DEFAULT NULL COMMENT '文件列表，支持多个文件',
  `ret_code` varchar(40) DEFAULT NULL COMMENT '返回码',
  `ret_msg` varchar(500) DEFAULT NULL COMMENT '返回信息',
  `email_type` varchar(2) NOT NULL COMMENT '邮件类型：00-定时任务监控邮件',
  `send_method` varchar(2) NOT NULL COMMENT '0-立刻发送 1-定时发送',
  `batch_no` varchar(40) DEFAULT NULL,
  `send_status` varchar(2) DEFAULT NULL COMMENT '发送状态：0-初始化成功,1-发送成功（请求成功）,2-状态未知,3-发送失败,4-发送处理中',
  `send_time` varchar(20) DEFAULT NULL COMMENT '发送时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT 'CURRENT_TIMESTAMP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_file_models
-- ----------------------------
DROP TABLE IF EXISTS `plms_file_models`;
CREATE TABLE `plms_file_models` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `type` varchar(36) DEFAULT NULL COMMENT '模板文件编码',
  `file_code` varchar(50) DEFAULT NULL COMMENT '模板文件id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注（模板描述）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='模板文件表';

-- ----------------------------
-- Table structure for plms_fund_side
-- ----------------------------
DROP TABLE IF EXISTS `plms_fund_side`;
CREATE TABLE `plms_fund_side` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `company_code` varchar(36) DEFAULT NULL COMMENT '公司表code',
  `lenders` varchar(50) DEFAULT NULL COMMENT '放款人',
  `bail_rate` decimal(9,6) DEFAULT NULL COMMENT '保证金比例',
  `bail_amount` decimal(20,2) DEFAULT NULL COMMENT '保证金金额',
  `bail_payed_amount` decimal(20,2) DEFAULT NULL COMMENT '保证金实付金额',
  `bail_payed_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '保证金实付时间',
  `year_period` int(3) DEFAULT NULL COMMENT '年模型天数',
  `default_interest_rate` decimal(20,6) DEFAULT NULL COMMENT '提前还款资方违约金利率',
  `penalty_period` int(5) DEFAULT NULL COMMENT '提前还款加收利息期数',
  `over_interest` decimal(20,6) DEFAULT NULL COMMENT '逾期年化利率',
  `contract_interest` decimal(20,6) DEFAULT NULL COMMENT '合同年化利率',
  `actual_interest` decimal(20,6) DEFAULT NULL COMMENT '实际年化利率',
  `receive_interest_method` int(2) DEFAULT NULL COMMENT '收息方式(01、前置；02、后置；03、固定日)',
  `receive_interest_date` int(2) DEFAULT NULL COMMENT '收息日',
  `mortgage_interest_period` int(3) DEFAULT NULL COMMENT '押息期数',
  `interest_grace_date` int(4) DEFAULT NULL COMMENT '利息宽限天数',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `service_charge_rate` decimal(20,6) DEFAULT NULL COMMENT '服务费比例',
  `service_charge_amount` decimal(20,2) DEFAULT NULL COMMENT '服务费金额',
  `service_charge_paid_amount` decimal(20,2) DEFAULT NULL COMMENT '服务费实付金额',
  `service_charge_paid_time` datetime DEFAULT NULL COMMENT '服务费实付时间',
  `receipt_fee_rate` decimal(20,6) DEFAULT NULL COMMENT '收件收据服务费比例',
  `receipt_fee_amount` decimal(20,2) DEFAULT NULL COMMENT '收件收据服务费金额',
  `receipt_fee_paid_amount` decimal(20,2) DEFAULT NULL COMMENT '收件收据服务费实付金额',
  `receipt_fee_paid_time` datetime DEFAULT NULL COMMENT '收件收据服务费实付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COMMENT='资方信息表';

-- ----------------------------
-- Table structure for plms_fund_side_product
-- ----------------------------
DROP TABLE IF EXISTS `plms_fund_side_product`;
CREATE TABLE `plms_fund_side_product` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `product_name` varchar(50) DEFAULT NULL COMMENT '资方产品名称',
  `is_principal_instead` tinyint(1) DEFAULT NULL COMMENT '本金是否代收代付',
  `is_interest_instead` tinyint(1) DEFAULT NULL COMMENT '利息是否代收代付',
  `company_code` varchar(36) DEFAULT NULL COMMENT '公司表code',
  `bms_code` varchar(36) DEFAULT NULL COMMENT '核心系统code',
  `status` varchar(255) NOT NULL COMMENT '状态',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_guarantee_corporation
-- ----------------------------
DROP TABLE IF EXISTS `plms_guarantee_corporation`;
CREATE TABLE `plms_guarantee_corporation` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `company_code` varchar(36) DEFAULT NULL COMMENT '公司表code',
  `guarantee_rate` decimal(10,2) DEFAULT NULL COMMENT '担保费率',
  `guarantee_amount` decimal(20,2) DEFAULT NULL COMMENT '担保费金额',
  `guarantee_payed_amount` decimal(20,0) DEFAULT NULL COMMENT '担保费实付金额',
  `guarantee_payed_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '担保费实付时间',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `bail_rate` decimal(10,2) DEFAULT NULL COMMENT '保证金比例',
  `bail_amount` decimal(20,2) DEFAULT NULL COMMENT '保证金金额',
  `bail_payed_amount` decimal(20,2) DEFAULT NULL COMMENT '保证金实付金额',
  `bail_payed_time` datetime DEFAULT NULL COMMENT '保证金实付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='担保公司信息表';

-- ----------------------------
-- Table structure for plms_guarantee_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_guarantee_info`;
CREATE TABLE `plms_guarantee_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `guarantee_status` varchar(2) DEFAULT NULL COMMENT '担保状态',
  `guarantee_amount` decimal(20,2) DEFAULT NULL COMMENT '担保金额',
  `guarantee_balance` decimal(20,2) DEFAULT NULL COMMENT '担保余额',
  `credit_code` varchar(36) DEFAULT NULL COMMENT '征信信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `credit_code` (`credit_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='担保信息表';

-- ----------------------------
-- Table structure for plms_house
-- ----------------------------
DROP TABLE IF EXISTS `plms_house`;
CREATE TABLE `plms_house` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `certificate_number_first` varchar(70) DEFAULT NULL COMMENT '产证编号字段一',
  `certificate_number_second` varchar(30) DEFAULT NULL COMMENT '产证编号字段二',
  `register_date` varchar(36) DEFAULT NULL COMMENT '登记日期',
  `property_area` varchar(36) DEFAULT NULL COMMENT '房产区域',
  `certificate_address` varchar(200) DEFAULT NULL COMMENT '产证地址',
  `land_property` varchar(2) DEFAULT NULL COMMENT '土地性质(01、国有;02、集体)',
  `acquisition_form` varchar(2) DEFAULT NULL COMMENT '使用权取得方式(01、出让;02、划拨;03、转让)',
  `land_usage` varchar(50) DEFAULT NULL COMMENT '土地用途',
  `type` varchar(2) DEFAULT NULL COMMENT '房屋类型(01、公寓;02、别墅)',
  `covered_area` varchar(36) DEFAULT NULL COMMENT '建筑面积',
  `garage_address` varchar(100) DEFAULT NULL COMMENT '车库地址',
  `garage_area` varchar(36) DEFAULT NULL COMMENT '车库面积',
  `total_floor` int(11) DEFAULT NULL COMMENT '总层数',
  `build_year` varchar(4) DEFAULT NULL COMMENT '竣工年份',
  `share_type` varchar(2) DEFAULT NULL COMMENT '共有类型(01、共同共有；02、按份共有)',
  `minors_units` varchar(10) DEFAULT NULL COMMENT '未成年人产权份额',
  `owner_source` varchar(50) DEFAULT NULL COMMENT '房屋所有权来源',
  `usage_detail` varchar(2) DEFAULT NULL COMMENT '房屋使用情况(01、自住;02、已备案出租;03、未备案出租;04、空置)',
  `is_unique` varchar(2) DEFAULT NULL COMMENT '是否唯一住房(01、是;02、否)',
  `registered_residence_structure` varchar(50) DEFAULT NULL COMMENT '户口结构',
  `remark` varchar(400) DEFAULT NULL COMMENT '附记',
  `mortgage_type` varchar(2) DEFAULT NULL COMMENT '抵押类型(01、一抵;02、二抵;03、一抵转单;04、二抵转单)',
  `first_balance_type` varchar(2) DEFAULT NULL COMMENT '一抵余额类型(01、余额;02、最高额)',
  `first_balance_amount` decimal(20,2) DEFAULT NULL,
  `first_mortgage_amount` decimal(20,2) DEFAULT NULL,
  `second_mortgage_amount` decimal(20,2) DEFAULT NULL,
  `mortgage_rate` decimal(5,2) DEFAULT NULL COMMENT '抵押率',
  `inquiry_information` decimal(12,2) DEFAULT NULL COMMENT '评估价',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `city_no` varchar(36) DEFAULT NULL COMMENT '城市',
  `property_owner` varchar(50) DEFAULT NULL COMMENT '产权人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `contract_code` (`contract_code`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8 COMMENT='抵押物信息表';

-- ----------------------------
-- Table structure for plms_household_registration
-- ----------------------------
DROP TABLE IF EXISTS `plms_household_registration`;
CREATE TABLE `plms_household_registration` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `id_no` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `house_code` varchar(36) DEFAULT NULL COMMENT '抵押物编码',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `house_code` (`house_code`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 COMMENT='户口信息表';

-- ----------------------------
-- Table structure for plms_house_approval
-- ----------------------------
DROP TABLE IF EXISTS `plms_house_approval`;
CREATE TABLE `plms_house_approval` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `approve_amount` decimal(20,2) DEFAULT NULL COMMENT '批复金额',
  `approve_peroid` int(3) DEFAULT NULL COMMENT '批复期限',
  `house_value` decimal(20,2) DEFAULT NULL COMMENT '合同抵押物价值',
  `house_code` varchar(36) DEFAULT NULL COMMENT '抵押物编码',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `guarantee_limit` decimal(20,2) DEFAULT NULL COMMENT '担保额度',
  `mortgage_total_amount` decimal(20,2) DEFAULT NULL COMMENT '抵押权债券总额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='抵押物审批信息表';

-- ----------------------------
-- Table structure for plms_insurance_company
-- ----------------------------
DROP TABLE IF EXISTS `plms_insurance_company`;
CREATE TABLE `plms_insurance_company` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `company_code` varchar(36) DEFAULT NULL COMMENT '公司表code',
  `bail_rate` decimal(10,2) DEFAULT NULL COMMENT '保证金比例',
  `bail_amount` decimal(20,2) DEFAULT NULL COMMENT '保证金金额',
  `bail_payed_time` datetime DEFAULT NULL COMMENT '保证金实付时间',
  `performance_bond_rate` decimal(4,0) DEFAULT NULL COMMENT '履约保证保险费率',
  `performance_bond_amount` decimal(20,2) DEFAULT NULL COMMENT '履约保证保险费金额',
  `performance_bond_payed_time` datetime DEFAULT NULL COMMENT '履约保证保险费实付时间',
  `second_insurance_name` varchar(50) DEFAULT NULL COMMENT '险种2名称',
  `second_insurance_fee_rate` decimal(10,2) DEFAULT NULL COMMENT '险种2费率',
  `second_insurance_fee_amount` decimal(20,2) DEFAULT NULL COMMENT '险种2费用金额',
  `second_insurance_payed_time` datetime DEFAULT NULL COMMENT '险种2费用实付时间',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `bail_payed_amount` decimal(20,2) DEFAULT NULL,
  `performance_bond_payed_amount` decimal(20,2) DEFAULT NULL,
  `second_insurance_payed_amount` varchar(255) DEFAULT NULL,
  `bail_payed_method` varchar(2) DEFAULT NULL COMMENT '保证金支付方式',
  `performance_bond_payed_method` varchar(2) DEFAULT NULL COMMENT '履约保证保险费支付方式',
  `second_insurance_payed_method` varchar(2) DEFAULT NULL COMMENT '险种2费用支付方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='保险公司信息表';

-- ----------------------------
-- Table structure for plms_invest_detail
-- ----------------------------
DROP TABLE IF EXISTS `plms_invest_detail`;
CREATE TABLE `plms_invest_detail` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `mortgage_rank` varchar(100) DEFAULT NULL COMMENT '抵押权顺位',
  `creditor_rights_type` varchar(2) DEFAULT NULL COMMENT '债权类型',
  `creditor_rights_person` varchar(20) DEFAULT NULL COMMENT '债权人',
  `creditor_property` varchar(36) DEFAULT NULL COMMENT '债权性质',
  `creditor_amount` decimal(20,2) DEFAULT NULL COMMENT '债券金额',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态',
  `investigation_code` varchar(36) DEFAULT NULL COMMENT '产调信息表code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COMMENT='产调明细表';

-- ----------------------------
-- Table structure for plms_invest_query
-- ----------------------------
DROP TABLE IF EXISTS `plms_invest_query`;
CREATE TABLE `plms_invest_query` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `invest_query_time` datetime DEFAULT NULL COMMENT '产调查询时间',
  `invest_query_result` varchar(400) DEFAULT NULL COMMENT '产调查询结果',
  `remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态（0、无效；1、有效）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COMMENT='产调查询表';

-- ----------------------------
-- Table structure for plms_iou
-- ----------------------------
DROP TABLE IF EXISTS `plms_iou`;
CREATE TABLE `plms_iou` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `loan_amount` decimal(20,0) DEFAULT NULL COMMENT '贷款金额',
  `sign_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '签约日期',
  `paid_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '放款日期',
  `bussiness_code` varchar(22) DEFAULT NULL COMMENT '流水号',
  `customer` varchar(20) DEFAULT NULL COMMENT '借款人',
  `account_no` varchar(30) DEFAULT NULL COMMENT '收款账号',
  `account_name` varchar(20) DEFAULT NULL COMMENT '收款人',
  `currency` varchar(3) DEFAULT NULL COMMENT '借款币种',
  `annual_interesting` decimal(20,6) DEFAULT NULL COMMENT '年化利率',
  `overdue_interesting` decimal(20,6) DEFAULT NULL COMMENT '逾期年华利率',
  `contract_no` varchar(36) DEFAULT NULL COMMENT '合同编号',
  `interest_start_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '起息日期',
  `interest_end_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '到期日期',
  `repayment_method` varchar(2) DEFAULT NULL COMMENT '还款方式',
  `interest_cycle` varchar(10) DEFAULT NULL COMMENT '结息周期',
  `interesting_adjust_method` varchar(2) DEFAULT NULL COMMENT '利率调整方式',
  `promise` varchar(200) DEFAULT NULL COMMENT '客户承诺',
  `elec_sign` varchar(200) DEFAULT NULL COMMENT '电子签章',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='借款借据表';

-- ----------------------------
-- Table structure for plms_judicial_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_judicial_info`;
CREATE TABLE `plms_judicial_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名/公司名称',
  `has_litigation_info` tinyint(1) DEFAULT NULL COMMENT '是否有司法诉讼信息',
  `litigation_no` varchar(50) DEFAULT NULL COMMENT '立案号',
  `execution` varchar(100) DEFAULT NULL COMMENT '执行标的',
  `is_closed` tinyint(1) DEFAULT NULL COMMENT '身份证类型',
  `details` varchar(500) DEFAULT NULL COMMENT '情况说明',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8 COMMENT='司法信息表';

-- ----------------------------
-- Table structure for plms_loaned_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_loaned_info`;
CREATE TABLE `plms_loaned_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `loan_no` varchar(10) DEFAULT NULL COMMENT '贷款序号',
  `overdue_amount` decimal(20,2) DEFAULT NULL COMMENT '逾期金额',
  `loan_status` varchar(2) DEFAULT NULL COMMENT '贷款状态',
  `overdue_num` int(5) DEFAULT NULL COMMENT '逾期次数',
  `period` int(4) DEFAULT NULL COMMENT '期数',
  `credit_code` varchar(36) DEFAULT NULL COMMENT '征信信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态（0、无效;1、有效）',
  `type` varchar(2) DEFAULT NULL COMMENT '类型(01、当前逾期;02、非正常类贷款;03、近12个月累计最高逾期次数;04、近12个月最高连续)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=542 DEFAULT CHARSET=utf8 COMMENT='已贷款信息表';

-- ----------------------------
-- Table structure for plms_loan_unsettle
-- ----------------------------
DROP TABLE IF EXISTS `plms_loan_unsettle`;
CREATE TABLE `plms_loan_unsettle` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `loan_times` int(10) DEFAULT NULL COMMENT '贷款笔数',
  `contract_amount` decimal(20,2) DEFAULT NULL COMMENT '合同金额',
  `balance` decimal(20,2) DEFAULT NULL COMMENT '余额',
  `six_month_repayment` decimal(20,2) DEFAULT NULL COMMENT '最近6个月平均应还款',
  `credit_code` varchar(36) DEFAULT NULL COMMENT '征信信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8 COMMENT='未结清贷款信息汇总表';

-- ----------------------------
-- Table structure for plms_login_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_login_info`;
CREATE TABLE `plms_login_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `user_no` varchar(36) NOT NULL COMMENT '用户编号',
  `system_timestamp` varchar(100) DEFAULT NULL COMMENT '登录时主机端时间戳',
  `client_timestamp` varchar(100) DEFAULT NULL COMMENT '登录时客户端时间戳',
  `user_name` varchar(40) DEFAULT NULL COMMENT '用户名',
  `full_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
  `city_code` varchar(40) DEFAULT NULL COMMENT '城市',
  `city_name` varchar(100) DEFAULT NULL,
  `group_id` varchar(50) DEFAULT NULL COMMENT '组织编码',
  `group_name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='登录信息表';

-- ----------------------------
-- Table structure for plms_material
-- ----------------------------
DROP TABLE IF EXISTS `plms_material`;
CREATE TABLE `plms_material` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `file_code` varchar(100) DEFAULT NULL COMMENT '文件编号',
  `file_type` varchar(2) DEFAULT NULL COMMENT '文件类型',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `file_name` varchar(50) DEFAULT NULL COMMENT '文件名',
  `upload_time` datetime DEFAULT NULL COMMENT '上载时间',
  `file_suffix` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=958 DEFAULT CHARSET=utf8 COMMENT='进件材料表';

-- ----------------------------
-- Table structure for plms_material_type
-- ----------------------------
DROP TABLE IF EXISTS `plms_material_type`;
CREATE TABLE `plms_material_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `type_name` varchar(100) DEFAULT NULL COMMENT '文件类型名称',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='材料类型表';

-- ----------------------------
-- Table structure for plms_message_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_message_info`;
CREATE TABLE `plms_message_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `um_id` varchar(40) NOT NULL,
  `message_title` varchar(500) NOT NULL COMMENT '推送标题',
  `message_ticker` varchar(500) DEFAULT NULL COMMENT '标题栏提示语',
  `message_subtitle` varchar(500) DEFAULT NULL COMMENT '子标题',
  `message_content` varchar(500) NOT NULL COMMENT '发送内容',
  `send_time` varchar(20) DEFAULT NULL COMMENT '发送时间',
  `message_type` varchar(2) DEFAULT NULL COMMENT '消息类型：00-逾期，01-扣款',
  `send_method` varchar(2) DEFAULT NULL COMMENT '0-立刻发送 1-定时发送',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '处理批次号',
  `send_status` varchar(2) DEFAULT NULL COMMENT '发送状态：0-初始化成功,1-发送成功（请求成功）,2-状态未知,3-发送失败,4-发送处理中',
  `send_system` varchar(20) DEFAULT NULL COMMENT '发送系统',
  `ret_code` varchar(20) DEFAULT NULL COMMENT '返回码',
  `ret_msg` varchar(500) DEFAULT NULL COMMENT '返回信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `remark2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_message_info_log
-- ----------------------------
DROP TABLE IF EXISTS `plms_message_info_log`;
CREATE TABLE `plms_message_info_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `um_id` varchar(40) NOT NULL,
  `message_title` varchar(500) NOT NULL COMMENT '推送标题',
  `message_ticker` varchar(500) DEFAULT NULL COMMENT '标题栏提示语',
  `message_subtitle` varchar(500) DEFAULT NULL COMMENT '子标题',
  `message_content` varchar(500) NOT NULL COMMENT '发送内容',
  `send_time` varchar(20) DEFAULT NULL COMMENT '发送时间',
  `message_type` varchar(2) DEFAULT NULL COMMENT '消息类型：00-逾期，01-扣款',
  `send_method` varchar(2) DEFAULT NULL COMMENT '0-立刻发送 1-定时发送',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '处理批次号',
  `send_status` varchar(2) DEFAULT NULL COMMENT '发送状态：0-初始化成功,1-发送成功（请求成功）,2-状态未知,3-发送失败,4-发送处理中',
  `send_system` varchar(20) DEFAULT NULL COMMENT '发送系统',
  `ret_code` varchar(20) DEFAULT NULL COMMENT '返回码',
  `ret_msg` varchar(500) DEFAULT NULL COMMENT '返回信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `remark2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_message_receive
-- ----------------------------
DROP TABLE IF EXISTS `plms_message_receive`;
CREATE TABLE `plms_message_receive` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '数据ID（nlbs存储）',
  `serial_no` varchar(50) DEFAULT NULL COMMENT '消息序列号(消息平台序号)',
  `title` varchar(255) NOT NULL COMMENT '消息标题',
  `content` varchar(2000) NOT NULL COMMENT '消息内容',
  `sender_company_code` varchar(100) DEFAULT NULL COMMENT '发送方公司编号',
  `sender_company_name` varchar(255) DEFAULT NULL,
  `sender_department_code` varchar(100) DEFAULT NULL COMMENT '发送方所在部门编号',
  `sender_department_name` varchar(255) DEFAULT NULL COMMENT '发送方所在部门名称',
  `sender_identity_id` varchar(100) NOT NULL COMMENT '发送用户编号',
  `sender_name` varchar(255) DEFAULT NULL COMMENT '发送用户名',
  `receiver_identity_id` varchar(100) NOT NULL COMMENT '接收用户编号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `internal_param` varchar(2000) DEFAULT NULL,
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '状态(0未读1已读)',
  `read_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '消息读取时间',
  `read_channel` varchar(16) DEFAULT NULL COMMENT '消息读取终端(PC 电脑端；APP 手机客户端；WeChat 微信端) ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_message_send
-- ----------------------------
DROP TABLE IF EXISTS `plms_message_send`;
CREATE TABLE `plms_message_send` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '数据ID（nlbs存储）',
  `serial_no` varchar(50) DEFAULT NULL COMMENT '消息序列号(消息平台序号)',
  `title` varchar(255) NOT NULL COMMENT '消息标题',
  `content` varchar(2000) NOT NULL COMMENT '消息内容',
  `sender_company_code` varchar(100) DEFAULT NULL COMMENT '发送方公司编号',
  `sender_company_name` varchar(255) DEFAULT NULL COMMENT '发送方公司名称',
  `sender_department_code` varchar(100) DEFAULT NULL COMMENT '发送方所在部门编号',
  `sender_department_name` varchar(255) DEFAULT NULL COMMENT '发送方所在部门名称',
  `sender_identity_id` varchar(100) NOT NULL COMMENT '发送用户编号',
  `sender_name` varchar(255) DEFAULT NULL COMMENT '发送用户名',
  `receiver_identity_id` varchar(100) NOT NULL COMMENT '接收用户编号',
  `receiver_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `internal_param` varchar(2000) DEFAULT NULL,
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '状态(0未发送1已发送2发送失败)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_mortgage
-- ----------------------------
DROP TABLE IF EXISTS `plms_mortgage`;
CREATE TABLE `plms_mortgage` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `registration_time` datetime DEFAULT NULL COMMENT '抵押登记时间',
  `registration_result` varchar(400) DEFAULT NULL COMMENT '抵押登记结果',
  `remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='抵押登记信息表';

-- ----------------------------
-- Table structure for plms_operate_record
-- ----------------------------
DROP TABLE IF EXISTS `plms_operate_record`;
CREATE TABLE `plms_operate_record` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `type` varchar(3) DEFAULT NULL COMMENT '操作类型(01、提交申请；02、审批通过；03、审批未通过)',
  `operate_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `operate_user` varchar(36) DEFAULT NULL COMMENT '操作用户',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `key_code` varchar(36) DEFAULT NULL COMMENT '关联业务表的code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='操作历史表';

-- ----------------------------
-- Table structure for plms_overdue
-- ----------------------------
DROP TABLE IF EXISTS `plms_overdue`;
CREATE TABLE `plms_overdue` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id,主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `overdue_days` int(5) DEFAULT NULL COMMENT '逾期天数',
  `overdue_amount` decimal(20,2) DEFAULT NULL COMMENT '发生逾期的账户金额',
  `schedule_detail_code` varchar(36) DEFAULT NULL COMMENT '还款计划明细表code',
  `repayment_detail_code` varchar(36) DEFAULT NULL COMMENT '还款明细表code',
  `status` varchar(2) NOT NULL COMMENT '状态(01、已结清;02、未结清)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `subject` varchar(2) DEFAULT NULL COMMENT '科目（01、本金罚息；02、利息罚息；03、服务费违约金）',
  `amount` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 COMMENT='罚息表';

-- ----------------------------
-- Table structure for plms_overdue_detail
-- ----------------------------
DROP TABLE IF EXISTS `plms_overdue_detail`;
CREATE TABLE `plms_overdue_detail` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id,主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `overdue_amount` decimal(20,2) DEFAULT NULL COMMENT '新增金额',
  `time` datetime DEFAULT NULL COMMENT '时间',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `overdue_code` varchar(36) NOT NULL COMMENT '罚息表code',
  `overdue_days` int(5) DEFAULT NULL COMMENT '新增逾期天数',
  PRIMARY KEY (`id`,`code`,`overdue_code`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 COMMENT='罚息流水表';

-- ----------------------------
-- Table structure for plms_paid_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_paid_info`;
CREATE TABLE `plms_paid_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `paid_amount` decimal(20,2) DEFAULT NULL COMMENT '放款金额',
  `paid_time` datetime DEFAULT NULL COMMENT '放款时间',
  `iou_code` varchar(36) DEFAULT NULL COMMENT '借款借据code',
  `borrow_code` varchar(36) DEFAULT NULL COMMENT '借款申请表code',
  `interest` decimal(20,2) DEFAULT NULL,
  `overdue` decimal(20,2) DEFAULT NULL COMMENT '应还罚息',
  `service_fee` decimal(20,2) DEFAULT NULL COMMENT '应还服务费',
  `service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还服务费违约金',
  `repaymented_principal` decimal(20,2) DEFAULT NULL COMMENT '已还本金',
  `repaymented_interest` decimal(20,2) DEFAULT NULL COMMENT '已还利息',
  `repaymented_service_fee` decimal(20,2) DEFAULT NULL COMMENT '已还服务费',
  `repaymented_service_fee_penalty` decimal(20,0) DEFAULT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态（01、已结清;02、正常还款中;03、逾期;00、无效）',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `repaymented_overdue` decimal(20,2) DEFAULT NULL COMMENT '已还罚息',
  `business_code` varchar(20) DEFAULT NULL COMMENT '流水号',
  `user_code` varchar(36) DEFAULT NULL COMMENT '借款人信息表code',
  `contract_code` varchar(36) DEFAULT NULL,
  `remark` varchar(400) DEFAULT NULL,
  `account_code` varchar(36) DEFAULT NULL,
  `repaymented_bail` decimal(20,2) DEFAULT NULL COMMENT '已还保证金',
  `repaymented_bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还保证金违约金',
  `repaymented_ahead_interest_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还提前还款利息违约金',
  `repaymented_ahead_service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还提前还款服务费违约金',
  `bail` decimal(20,2) DEFAULT NULL COMMENT '应还保证金',
  `bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还保证金违约金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='放款信息表';

-- ----------------------------
-- Table structure for plms_paid_voucher
-- ----------------------------
DROP TABLE IF EXISTS `plms_paid_voucher`;
CREATE TABLE `plms_paid_voucher` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `file_code` varchar(36) DEFAULT NULL COMMENT '文件系统code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `repayment_apply_code` varchar(36) DEFAULT NULL COMMENT '还款申请表code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=604 DEFAULT CHARSET=utf8 COMMENT='还款凭证表';

-- ----------------------------
-- Table structure for plms_pigeonhole_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_pigeonhole_info`;
CREATE TABLE `plms_pigeonhole_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `file_code` varchar(36) DEFAULT NULL COMMENT '文件code',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `status` varchar(255) NOT NULL COMMENT '状态(0、无效；1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `type` varchar(2) DEFAULT NULL COMMENT '类型',
  `upload_time` datetime DEFAULT NULL COMMENT '上载时间',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `file_suffix` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='归档材料表';

-- ----------------------------
-- Table structure for plms_product
-- ----------------------------
DROP TABLE IF EXISTS `plms_product`;
CREATE TABLE `plms_product` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `product_name` varchar(20) DEFAULT NULL COMMENT '产品名称',
  `circle` tinyint(1) DEFAULT NULL COMMENT '是否循环',
  `interest_cycle` varchar(10) DEFAULT NULL COMMENT '计息周期(01、天；02、月;03、年;04、季)',
  `year_period` int(3) DEFAULT NULL COMMENT '年模型',
  `repayment_methods` varchar(2) DEFAULT NULL COMMENT '还款方式（01、先息后本）',
  `loan_minimum_unit` decimal(20,2) DEFAULT NULL COMMENT '借款最小单位',
  `loan_minimum_amount` decimal(20,2) DEFAULT NULL COMMENT '借款最低金额',
  `repayment_minimum_unit` decimal(20,2) DEFAULT NULL COMMENT '还款最小单位',
  `repayment_minimum_amount` decimal(20,2) DEFAULT NULL COMMENT '还款最低金额',
  `part_repayment` tinyint(1) DEFAULT NULL COMMENT '是否支持部分还款',
  `borrow_closed_period` int(3) DEFAULT NULL COMMENT '借款封闭期',
  `repayment_closed_period` int(3) DEFAULT NULL COMMENT '还款封闭期',
  `is_penalty` tinyint(1) DEFAULT NULL COMMENT '超过封闭期是否收取违约金',
  `penalty_period` int(5) DEFAULT NULL COMMENT '提前还款加收违约金期数',
  `is_first_max` tinyint(1) DEFAULT NULL COMMENT '首笔放款是否提现最高额',
  `interest_collection_method` varchar(2) NOT NULL COMMENT '收息方式（01、前置;02、后置;03、固定日）',
  `interest_repayment_day` varchar(2) DEFAULT NULL COMMENT '还息日',
  `mortgage_interest_period` tinyint(1) DEFAULT NULL COMMENT '押息期数',
  `grace_days` int(3) DEFAULT NULL COMMENT '利息宽限天数',
  `principal_repayment_period` int(3) DEFAULT NULL COMMENT '归还本金周期(月)',
  `principal_repayment_method` varchar(2) DEFAULT NULL COMMENT '归还本金按比例还是按全额(01、比例;02、全额)',
  `is_period_select` tinyint(1) DEFAULT NULL COMMENT '贷款期限是否可选(0、不可选；1、可选)',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `single_service_charge` tinyint(1) DEFAULT NULL COMMENT '是否一次性收取息差（作废）',
  `overdue_method` varchar(2) DEFAULT NULL COMMENT '罚息计算方式（01、实际逾期金额单利;02、实际逾期金额复利；03、本金单利；04、本息单利）',
  `service_fee_method` varchar(2) DEFAULT NULL COMMENT '服务费计算方式',
  `is_spread_one_time` tinyint(1) DEFAULT NULL COMMENT '息差是否一次性收取（0、否；1、是）',
  `paid_date_grace_days` int(3) DEFAULT NULL,
  `max_loan_num` int(5) DEFAULT NULL COMMENT '最高借款次数',
  `principal_date` date DEFAULT NULL,
  `paid_days_computational_rule` varchar(2) DEFAULT NULL COMMENT '放款天数计算规则（01、算头算尾；02、算头不算尾）',
  PRIMARY KEY (`id`,`interest_collection_method`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8 COMMENT='产品信息表';

-- ----------------------------
-- Table structure for plms_property_investigation
-- ----------------------------
DROP TABLE IF EXISTS `plms_property_investigation`;
CREATE TABLE `plms_property_investigation` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `investigation_time` datetime DEFAULT NULL COMMENT '产调时间',
  `in_case_information` varchar(100) DEFAULT NULL COMMENT '在办案件信息',
  `house_code` varchar(36) DEFAULT NULL COMMENT '抵押物编码',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='产调信息表';

-- ----------------------------
-- Table structure for plms_query_record
-- ----------------------------
DROP TABLE IF EXISTS `plms_query_record`;
CREATE TABLE `plms_query_record` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `query_num` int(11) DEFAULT NULL COMMENT '近三个月查询次数',
  `manage_num` int(11) DEFAULT NULL COMMENT '贷后管理次数',
  `check_num` int(11) DEFAULT NULL COMMENT '担保资格审查次数',
  `credit_code` varchar(36) DEFAULT NULL COMMENT '征信信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COMMENT='查询记录表';

-- ----------------------------
-- Table structure for plms_receipts_record
-- ----------------------------
DROP TABLE IF EXISTS `plms_receipts_record`;
CREATE TABLE `plms_receipts_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `receipts_date` datetime DEFAULT NULL COMMENT '入账日期',
  `receipts_amount` decimal(20,2) DEFAULT NULL COMMENT '入账金额',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同编码',
  `account_type` varchar(2) DEFAULT NULL COMMENT '账户类型(00、宏获还款账户;01、资方账户；02、宏获保证金账户)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(2) NOT NULL COMMENT '状态',
  `deal_status` varchar(2) DEFAULT NULL COMMENT '处理状态',
  `deal_batch_no` varchar(36) DEFAULT NULL COMMENT '处理批次号',
  `deal_msg` varchar(500) DEFAULT NULL COMMENT '处理状态',
  `repayment_apply_code` varchar(36) DEFAULT NULL COMMENT '还款申请表code',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `deal_user` varchar(36) DEFAULT NULL COMMENT '处理人',
  `deal_time` datetime DEFAULT NULL COMMENT '处理时间',
  `fund_source` varchar(2) DEFAULT NULL COMMENT '资金来源（01、用户；02、宏获；03）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='资金入账流水表';

-- ----------------------------
-- Table structure for plms_receipts_record_upload
-- ----------------------------
DROP TABLE IF EXISTS `plms_receipts_record_upload`;
CREATE TABLE `plms_receipts_record_upload` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) DEFAULT NULL COMMENT 'UUID',
  `upload_user` varchar(36) DEFAULT NULL COMMENT '上载人',
  `upload_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上载时间',
  `status` varchar(2) NOT NULL COMMENT '状态 00、无效;01、校验中;02、校验成功;03、校验失败;',
  `file_code` varchar(36) DEFAULT NULL COMMENT '上载文件编码',
  `file_name` varchar(100) DEFAULT NULL COMMENT '上载文件名',
  `error_file_code` varchar(36) DEFAULT NULL COMMENT '错误文件编码',
  `error_file_name` varchar(100) DEFAULT NULL COMMENT '错误文件名',
  `error_file_created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '错误文件创建时间',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='资金入账上载记录表';

-- ----------------------------
-- Table structure for plms_receipts_voucher
-- ----------------------------
DROP TABLE IF EXISTS `plms_receipts_voucher`;
CREATE TABLE `plms_receipts_voucher` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `file_code` varchar(36) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `receipts_record_code` varchar(36) DEFAULT NULL,
  `file_name` varchar(100) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_repayment_apply
-- ----------------------------
DROP TABLE IF EXISTS `plms_repayment_apply`;
CREATE TABLE `plms_repayment_apply` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '还款金额',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `apply_status` varchar(2) NOT NULL COMMENT '申请状态',
  `business_code` varchar(36) DEFAULT NULL COMMENT '业务编号',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_code` varchar(36) DEFAULT NULL,
  `repayment_method` varchar(2) DEFAULT NULL COMMENT '还款方式(01、按还款计划；02、提前归还本金)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8 COMMENT='还款申请表';

-- ----------------------------
-- Table structure for plms_repayment_date
-- ----------------------------
DROP TABLE IF EXISTS `plms_repayment_date`;
CREATE TABLE `plms_repayment_date` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `repayment_date` date DEFAULT NULL COMMENT '还款日',
  `detail_code` varchar(36) DEFAULT NULL COMMENT '账户明细表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8 COMMENT='还款日表';

-- ----------------------------
-- Table structure for plms_repayment_detail
-- ----------------------------
DROP TABLE IF EXISTS `plms_repayment_detail`;
CREATE TABLE `plms_repayment_detail` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `subject` int(5) DEFAULT NULL COMMENT '科目(1保证金违约金，2 服务费罚金，3利息罚金，4本金罚金，5 保证金，6 服务费，7利息，8本金)',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '金额',
  `schedule_detail_code` varchar(36) NOT NULL COMMENT '还款计划明细表code',
  `status` varchar(2) NOT NULL COMMENT '状态（1服务费罚金，2利息罚金，3本金罚金，4服务费，5利息，6本金，7保证金违约金,8保证金,9、提前还款利息违约金,10、提前还款服务费违约）',
  `time_happened` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发生时间',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`,`code`,`schedule_detail_code`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COMMENT='还款明细表';

-- ----------------------------
-- Table structure for plms_repayment_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_repayment_info`;
CREATE TABLE `plms_repayment_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `repayment_date` datetime DEFAULT NULL COMMENT '还款时间',
  `repayment_amount` decimal(20,2) DEFAULT NULL COMMENT '还款金额',
  `repayment_principal` decimal(20,2) DEFAULT NULL COMMENT '还款本金',
  `repayment_interest` decimal(20,2) DEFAULT NULL COMMENT '还款利息',
  `repayment_overdue` decimal(20,2) DEFAULT NULL COMMENT '还款罚息',
  `repayment_penalty` decimal(20,2) DEFAULT NULL COMMENT '违约金金额',
  `overrepay` decimal(20,2) DEFAULT NULL COMMENT '溢缴金',
  `service_charge` decimal(20,2) DEFAULT NULL COMMENT '手续费',
  `repayment_apply_code` varchar(36) DEFAULT NULL COMMENT '还款申请表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_code` varchar(36) DEFAULT NULL COMMENT '借款人信息code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='还款信息表';

-- ----------------------------
-- Table structure for plms_repayment_schedule
-- ----------------------------
DROP TABLE IF EXISTS `plms_repayment_schedule`;
CREATE TABLE `plms_repayment_schedule` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `current_period` int(3) DEFAULT NULL COMMENT '当前期数',
  `total_period` int(3) DEFAULT NULL COMMENT '总期数',
  `repayment_date` date DEFAULT NULL COMMENT '应还日期',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '应还金额',
  `principal` decimal(20,2) DEFAULT NULL COMMENT '应还本金',
  `interest` decimal(20,2) DEFAULT NULL COMMENT '应还利息',
  `overdue` decimal(20,2) DEFAULT NULL COMMENT '应还罚息',
  `status` varchar(2) NOT NULL COMMENT '状态(01、已结清;02、正常还款中;03、逾期;00、无效）',
  `detail_code` varchar(36) DEFAULT NULL COMMENT '账户明细表code',
  `user_code` varchar(36) DEFAULT NULL COMMENT '用户信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `repaymented_principal` decimal(20,2) DEFAULT NULL COMMENT '已还本金',
  `repaymented_interest` decimal(20,2) DEFAULT NULL COMMENT '已还利息',
  `repaymented_overdue` decimal(20,2) DEFAULT NULL COMMENT '已还罚息',
  `service_fee` decimal(20,2) DEFAULT NULL COMMENT '应还服务费',
  `service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还服务费违约金',
  `repaymented_service_fee` decimal(20,2) DEFAULT NULL COMMENT '已还服务费',
  `repaymented_service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还服务费违约金',
  `bail` decimal(20,2) DEFAULT NULL COMMENT '应还保证金',
  `bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还保证金违约金',
  `repaymented_bail` decimal(20,2) DEFAULT NULL COMMENT '已还保证金',
  `repaymented_bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还保证金违约金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8 COMMENT='还款计划表';

-- ----------------------------
-- Table structure for plms_repayment_schedule_detail
-- ----------------------------
DROP TABLE IF EXISTS `plms_repayment_schedule_detail`;
CREATE TABLE `plms_repayment_schedule_detail` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) DEFAULT NULL COMMENT 'UUID',
  `current_period` varchar(2) DEFAULT NULL COMMENT '当前期数',
  `total_period` varchar(2) DEFAULT NULL COMMENT '总期数',
  `repayment_date` datetime DEFAULT NULL COMMENT '应还日期',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '应还金额',
  `principal` decimal(20,2) DEFAULT NULL COMMENT '应还本金',
  `interest` decimal(20,2) DEFAULT NULL COMMENT '应还利息',
  `overdue` decimal(20,2) DEFAULT NULL COMMENT '应还罚金',
  `repaymented_principal` decimal(20,2) DEFAULT NULL,
  `repaymented_interest` decimal(20,2) DEFAULT NULL,
  `repaymented_overdue` decimal(20,2) DEFAULT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态（01、已结清;02、正常还款中;03、逾期;00、无效）',
  `paid_code` varchar(36) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `schedule_code` varchar(36) DEFAULT NULL,
  `principal_account_code` varchar(36) DEFAULT NULL,
  `interest_account_code` varchar(36) DEFAULT NULL,
  `total_overdue_days` int(5) DEFAULT NULL COMMENT '本息累计逾期天数',
  `service_fee_total_overdue_days` int(5) DEFAULT NULL COMMENT '服务费累计逾期天数',
  `service_fee` decimal(20,2) DEFAULT NULL,
  `service_fee_penalty` decimal(20,2) DEFAULT NULL,
  `repaymented_service_fee` decimal(20,2) DEFAULT NULL,
  `repaymented_service_fee_penalty` decimal(20,2) DEFAULT NULL,
  `bail` decimal(20,2) DEFAULT NULL COMMENT '应还保证金',
  `bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还保证金违约金',
  `repaymented_bail` decimal(20,2) DEFAULT NULL COMMENT '已还保证金',
  `repaymented_bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还保证金违约金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8 COMMENT='还款计划明细表';

-- ----------------------------
-- Table structure for plms_repayment_schedule_detail_change_control
-- ----------------------------
DROP TABLE IF EXISTS `plms_repayment_schedule_detail_change_control`;
CREATE TABLE `plms_repayment_schedule_detail_change_control` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) DEFAULT NULL COMMENT 'UUID',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `change_status` varchar(2) NOT NULL COMMENT '变更状态 01:待处理;02:处理中;03:处理成功;04:处理失败;',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同信息表code',
  `change_um_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=510 DEFAULT CHARSET=utf8 COMMENT='还款计划明细临时表';

-- ----------------------------
-- Table structure for plms_repayment_schedule_detail_tmp
-- ----------------------------
DROP TABLE IF EXISTS `plms_repayment_schedule_detail_tmp`;
CREATE TABLE `plms_repayment_schedule_detail_tmp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) DEFAULT NULL COMMENT 'UUID',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `repayment_schedule_detail_code` varchar(36) DEFAULT NULL COMMENT '还款计划明细表的code',
  `current_period` varchar(2) DEFAULT NULL COMMENT '当前期数',
  `total_period` varchar(2) DEFAULT NULL COMMENT '总期数',
  `repayment_date` datetime DEFAULT NULL COMMENT '应还日期',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '应还金额',
  `principal` decimal(20,2) DEFAULT NULL COMMENT '应还本金',
  `interest` decimal(20,2) DEFAULT NULL COMMENT '应还利息',
  `overdue` decimal(20,2) DEFAULT NULL COMMENT '应还罚息',
  `repaymented_principal` decimal(20,2) DEFAULT NULL COMMENT '已还本金',
  `repaymented_interest` decimal(20,2) DEFAULT NULL COMMENT '已还利息',
  `repaymented_overdue` decimal(20,2) DEFAULT NULL COMMENT '已还服务费',
  `repayment_schedule_detail_status` varchar(2) DEFAULT NULL COMMENT '还款计划明细表的状态 01、已结清;02、正常还款中;03、逾期;00、无效',
  `paid_code` varchar(36) DEFAULT NULL COMMENT '放款信息表code',
  `repayment_schedule_detail_create_date` datetime DEFAULT NULL COMMENT '还款计划明细表的创建时间',
  `repayment_schedule_detail_modify_date` datetime DEFAULT NULL COMMENT '还款计划明细表的修改时间',
  `schedule_code` varchar(36) DEFAULT NULL COMMENT '还款计划表code',
  `principal_account_code` varchar(36) DEFAULT NULL COMMENT '本金收款账户表code',
  `interest_account_code` varchar(36) DEFAULT NULL COMMENT '利息收款账户表code',
  `total_overdue_days` int(5) DEFAULT NULL COMMENT '累计逾期天数',
  `service_fee` decimal(20,2) DEFAULT NULL COMMENT '应还服务费',
  `service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还服务费违约金',
  `repaymented_service_fee` decimal(20,2) DEFAULT NULL COMMENT '已还服务费',
  `repaymented_service_fee_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还服务费违约金',
  `service_fee_total_overdue_days` int(5) DEFAULT NULL COMMENT '服务费累计逾期天数',
  `bail` decimal(20,2) DEFAULT NULL COMMENT '应还保证金',
  `bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '应还保证金违约金',
  `repaymented_bail` decimal(20,2) DEFAULT NULL COMMENT '已还违约金',
  `repaymented_bail_penalty` decimal(20,2) DEFAULT NULL COMMENT '已还保证金违约金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27048 DEFAULT CHARSET=utf8 COMMENT='还款计划明细临时表';

-- ----------------------------
-- Table structure for plms_sign_notarial
-- ----------------------------
DROP TABLE IF EXISTS `plms_sign_notarial`;
CREATE TABLE `plms_sign_notarial` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `sign_time` datetime DEFAULT NULL COMMENT '签约公证时间',
  `sign_result` varchar(400) DEFAULT NULL COMMENT '签约公证结果',
  `remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `sign_place` varchar(100) DEFAULT NULL COMMENT '签约公证地点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='签约及公证信息表';

-- ----------------------------
-- Table structure for plms_sms_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_sms_info`;
CREATE TABLE `plms_sms_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `mobile` varchar(15) NOT NULL COMMENT '手机号',
  `request_no` varchar(40) DEFAULT NULL COMMENT '请求流水号',
  `template_param` varchar(1000) DEFAULT NULL COMMENT '请求参数',
  `sender_name` varchar(100) DEFAULT NULL COMMENT '发送人姓名',
  `sign_no` varchar(40) NOT NULL COMMENT '签名code',
  `template_code` varchar(40) NOT NULL COMMENT '模板code',
  `sms_type` varchar(5) NOT NULL COMMENT '短信类型：00-逾期，01-扣款',
  `real_send_time` varchar(20) DEFAULT NULL COMMENT '实际发送时间',
  `send_status` varchar(5) NOT NULL COMMENT '发送状态：0-初始化成功,1-发送成功（请求成功）,2-状态未知,3-发送失败,4-发送处理中',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批处理标识号',
  `send_method` varchar(5) NOT NULL COMMENT '0-立刻发送 1-定时发送',
  `ret_code` varchar(20) DEFAULT NULL COMMENT '返回码',
  `ret_msg` varchar(500) DEFAULT NULL COMMENT '返回信息',
  `repayment_schedule_code` varchar(40) DEFAULT NULL COMMENT '还款计划表code',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_sms_info_log
-- ----------------------------
DROP TABLE IF EXISTS `plms_sms_info_log`;
CREATE TABLE `plms_sms_info_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `mobile` varchar(15) NOT NULL COMMENT '手机号',
  `request_no` varchar(40) DEFAULT NULL COMMENT '请求流水号',
  `template_param` varchar(1000) DEFAULT NULL COMMENT '请求参数',
  `sender_name` varchar(100) DEFAULT NULL COMMENT '发送人姓名',
  `sign_no` varchar(40) NOT NULL COMMENT '签名code',
  `template_code` varchar(40) NOT NULL COMMENT '模板code',
  `sms_type` varchar(5) NOT NULL COMMENT '短信类型：00-逾期，01-扣款',
  `real_send_time` varchar(20) DEFAULT NULL COMMENT '实际发送时间',
  `send_status` varchar(5) NOT NULL COMMENT '发送状态：0-初始化成功,1-发送成功（请求成功）,2-状态未知,3-发送失败,4-发送处理中',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批处理标识号',
  `send_method` varchar(5) NOT NULL COMMENT '0-立刻发送 1-定时发送',
  `ret_code` varchar(20) DEFAULT NULL COMMENT '返回码',
  `ret_msg` varchar(500) DEFAULT NULL COMMENT '返回信息',
  `repayment_schedule_code` varchar(40) DEFAULT NULL COMMENT '还款计划表code',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_spare_house
-- ----------------------------
DROP TABLE IF EXISTS `plms_spare_house`;
CREATE TABLE `plms_spare_house` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `property_person` varchar(50) DEFAULT NULL COMMENT '产权人',
  `property_address` varchar(400) DEFAULT NULL COMMENT '产证地址',
  `apply_code` varchar(36) DEFAULT NULL COMMENT '申请信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8 COMMENT='备用房信息表';

-- ----------------------------
-- Table structure for plms_sysparam_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_sysparam_info`;
CREATE TABLE `plms_sysparam_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `syscode` varchar(10) DEFAULT '' COMMENT '系统编号',
  `sysname` varchar(60) DEFAULT '' COMMENT '系统名称',
  `item_id` varchar(60) DEFAULT '' COMMENT '参数代码',
  `item_name` varchar(120) DEFAULT '' COMMENT '参数名称',
  `item_cval` varchar(256) DEFAULT '' COMMENT '参数代码字符值',
  `item_ival` bigint(20) DEFAULT '0' COMMENT '参数代码整型值',
  `execute_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '定时任务执行日期',
  `item_desc` varchar(256) DEFAULT '' COMMENT '参数维护描述',
  `remark` varchar(100) DEFAULT '' COMMENT '备注',
  `remark1` varchar(100) DEFAULT '' COMMENT '备注1',
  `remark2` varchar(100) DEFAULT '' COMMENT '备注2',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_sysparam_info_1` (`syscode`,`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='公共参数配置表';

-- ----------------------------
-- Table structure for plms_todo
-- ----------------------------
DROP TABLE IF EXISTS `plms_todo`;
CREATE TABLE `plms_todo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '任务创建时间（分配时间）',
  `content` varchar(255) DEFAULT NULL COMMENT '待办任务详情',
  `todo_type` varchar(3) DEFAULT NULL COMMENT '待办任务类型',
  `user_id` varchar(36) DEFAULT NULL COMMENT '待处理人',
  `user_full_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `serial_no` varchar(36) DEFAULT NULL COMMENT '业务数据主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待办任务列表';

-- ----------------------------
-- Table structure for plms_todo_type
-- ----------------------------
DROP TABLE IF EXISTS `plms_todo_type`;
CREATE TABLE `plms_todo_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(3) NOT NULL COMMENT '待办任务类型编码',
  `name` varchar(255) DEFAULT NULL COMMENT '待办任务类型描述',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待办任务类型表';

-- ----------------------------
-- Table structure for plms_unclosing_card
-- ----------------------------
DROP TABLE IF EXISTS `plms_unclosing_card`;
CREATE TABLE `plms_unclosing_card` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `account_number` int(11) DEFAULT NULL COMMENT '账户数',
  `total_credit` decimal(20,2) DEFAULT NULL COMMENT '授信总额',
  `contract_amount` decimal(20,2) DEFAULT NULL COMMENT '已用额度',
  `six_month_loan_amount` decimal(20,2) DEFAULT NULL COMMENT '最近6个月平均使用额度',
  `credit_code` varchar(36) DEFAULT NULL COMMENT '征信信息表code',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(0、无效;1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COMMENT='未销户贷记卡信息汇总表';

-- ----------------------------
-- Table structure for plms_user_distributor
-- ----------------------------
DROP TABLE IF EXISTS `plms_user_distributor`;
CREATE TABLE `plms_user_distributor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_id` varchar(36) NOT NULL COMMENT '用户编号',
  `distributor_code` varchar(36) NOT NULL COMMENT '渠道编号',
  `status` varchar(1) DEFAULT NULL COMMENT '0-无效 1-有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='录单员和渠道关联表';

-- ----------------------------
-- Table structure for plms_user_govern_city
-- ----------------------------
DROP TABLE IF EXISTS `plms_user_govern_city`;
CREATE TABLE `plms_user_govern_city` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `city_code` varchar(8) DEFAULT NULL COMMENT '城市',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户编号',
  `status` varchar(1) DEFAULT NULL COMMENT '0-无效 1-有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for plms_user_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_user_info`;
CREATE TABLE `plms_user_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `um_id` varchar(22) DEFAULT NULL COMMENT 'umId',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `id_type` varchar(2) DEFAULT NULL COMMENT '证件类型',
  `id_no` varchar(18) DEFAULT NULL COMMENT '证件号码',
  `status` varchar(2) NOT NULL COMMENT '状态（0、无效；1、有效）',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='借款用户信息表';

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;