/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.5_3306
Source Server Version : 50716
Source Host           : 192.168.0.5:3306
Source Database       : plms

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-11-16 09:13:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for plms_collection_online_fee_config
-- ----------------------------
DROP TABLE IF EXISTS `plms_collection_online_fee_config`;
CREATE TABLE `plms_collection_online_fee_config` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `online_collect_code` varchar(36) DEFAULT NULL COMMENT '在线代收配置表code',
  `fee_type` varchar(1) DEFAULT NULL COMMENT '手续费收费方式（1：按比例，2：固定值）',
  `fee_toplimit` int(7) DEFAULT NULL COMMENT '手续费上限',
  `fee_bottomlimit` int(7) DEFAULT NULL COMMENT '手续费下限',
  `fee_rate` decimal(5,4) DEFAULT NULL COMMENT '手续费费率',
  `fee_value` decimal(9,2) DEFAULT NULL COMMENT '手续费固定值',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='代收手续费配置表';

-- ----------------------------
-- Table structure for plms_contract_fund_account
-- ----------------------------
DROP TABLE IF EXISTS `plms_contract_fund_account`;
CREATE TABLE `plms_contract_fund_account` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同编号',
  `detail_code` varchar(36) DEFAULT NULL COMMENT '账户明细表code',
  `fund_account_type_code` varchar(36) DEFAULT NULL COMMENT '资金账户类型编码',
  `fund_account_type_name` varchar(100) DEFAULT NULL COMMENT '资金账户类型名称',
  `fund_account_code` varchar(36) DEFAULT NULL COMMENT '资金账户表code',
  `status` varchar(1) DEFAULT NULL COMMENT '0、无效;1、有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_con_acc_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='合同资金账户关联表';

-- ----------------------------
-- Table structure for plms_fund_account_detail
-- ----------------------------
DROP TABLE IF EXISTS `plms_fund_account_detail`;
CREATE TABLE `plms_fund_account_detail` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `fund_account_code` varchar(36) DEFAULT NULL COMMENT '资金账户表code',
  `type` varchar(1) DEFAULT NULL COMMENT '出入类型（1：入账；2：出账）',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '金额',
  `description` varchar(255) DEFAULT NULL COMMENT '描述信息',
  `business_type` varchar(10) DEFAULT NULL COMMENT '业务类型（100001、还款计划在线代收；200001、还款计划扣款）',
  `business_id` varchar(36) DEFAULT NULL COMMENT '业务关联字段(关联业务表的code)',
  `receipts_record_code` varchar(36) DEFAULT NULL COMMENT '表 plms_receipts_record的code',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `status` varchar(1) DEFAULT NULL COMMENT '0、无效;1、有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='资金账户流水表';

-- ----------------------------
-- Table structure for plms_fund_account_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_fund_account_info`;
CREATE TABLE `plms_fund_account_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `balance` decimal(12,2) DEFAULT NULL COMMENT '账户剩余额度',
  `status` varchar(2) DEFAULT NULL COMMENT '状态（0、无效;1、有效）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='资金账户表';

-- ----------------------------
-- Table structure for plms_fund_account_type
-- ----------------------------
DROP TABLE IF EXISTS `plms_fund_account_type`;
CREATE TABLE `plms_fund_account_type` (
  `id` int(14) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '账户名称(00宏获还款账户;01资方账户;02宏获保证金账户;03渠道账户;04业务经理账户;05差额手续费账户；06、宏获垫付资方账户)',
  `status` varchar(1) DEFAULT NULL COMMENT '0、无效;1、有效',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='资金账户类型表';

-- ----------------------------
-- Table structure for plms_gb_city
-- ----------------------------
DROP TABLE IF EXISTS `plms_gb_city`;
CREATE TABLE `plms_gb_city` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(8) DEFAULT NULL COMMENT '城市国标编码',
  `abbr_name` varchar(100) DEFAULT NULL COMMENT '城市简称',
  `complete_spelling` varchar(100) DEFAULT NULL COMMENT '城市简称全拼',
  `full_name` varchar(255) DEFAULT NULL COMMENT '城市全程',
  `level` int(2) DEFAULT NULL COMMENT '城市级别(省级，地区级，县级)',
  `parent_code` varchar(8) DEFAULT NULL COMMENT '上级城市code',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(1、有效；0、无效)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4097 DEFAULT CHARSET=utf8 COMMENT='国标码城市表';

-- ----------------------------
-- Table structure for plms_pay_apply_audit
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_apply_audit`;
CREATE TABLE `plms_pay_apply_audit` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `pay_apply_code` varchar(36) DEFAULT NULL COMMENT '支付申请单code',
  `audit_type` varchar(2) DEFAULT NULL COMMENT '审批类型(1，运营审批；2财务审批)',
  `verifier_code` varchar(36) DEFAULT NULL COMMENT '审核人code',
  `verifier_name` varchar(40) DEFAULT NULL COMMENT '审核人名称',
  `audit_result` varchar(1) DEFAULT NULL COMMENT '审核结果（1同意，0拒绝）',
  `audit_opinion` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` varchar(2) DEFAULT NULL COMMENT '状态（0、无效;1、有效）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付申请单审核表';

-- ----------------------------
-- Table structure for plms_pay_apply_detail
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_apply_detail`;
CREATE TABLE `plms_pay_apply_detail` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `pay_apply_info_code` varchar(36) DEFAULT NULL COMMENT '支付申请单code',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '借款合同code',
  `payment_fee_type_code` varchar(36) DEFAULT NULL COMMENT '费用科目(plms_pay_payment_fee_type)',
  `pay_article` varchar(255) DEFAULT NULL COMMENT '支付事由',
  `pay_content` varchar(255) DEFAULT NULL COMMENT '支付内容',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '支付金额',
  `fee_rate` decimal(5,4) DEFAULT NULL COMMENT '费用比例',
  `main_borrower` varchar(100) DEFAULT NULL COMMENT '主借款人姓名',
  `borrow_amount` decimal(12,2) DEFAULT NULL COMMENT '授信金额',
  `paid_time` datetime DEFAULT NULL COMMENT '授信日期',
  `borrow_period` varchar(8) DEFAULT NULL COMMENT '借款期限',
  `annual_interest_rate` decimal(5,4) DEFAULT NULL COMMENT '年化利率',
  `status` varchar(1) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `remark` varchar(255) DEFAULT NULL COMMENT '支付备注',
  `apply_detail_temp_code` varchar(36) DEFAULT NULL COMMENT '支付申请单明细临时表Code',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='支付申请单明细表';

-- ----------------------------
-- Table structure for plms_pay_apply_detail_temp
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_apply_detail_temp`;
CREATE TABLE `plms_pay_apply_detail_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `apply_temp_code` varchar(36) DEFAULT NULL COMMENT '支付申请单临时表code',
  `contract_code` varchar(36) DEFAULT NULL COMMENT '借款合同code',
  `payment_fee_type_code` varchar(36) DEFAULT NULL COMMENT '费用科目(plms_pay_payment_fee_type)',
  `pay_article` varchar(255) DEFAULT NULL COMMENT '支付事由',
  `pay_content` varchar(255) DEFAULT NULL COMMENT '支付内容',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '支付金额',
  `fee_rate` decimal(5,4) DEFAULT NULL COMMENT '费用比例',
  `main_borrower` varchar(100) DEFAULT NULL COMMENT '主借款人姓名',
  `borrow_amount` decimal(12,2) DEFAULT NULL COMMENT '授信金额',
  `paid_time` datetime DEFAULT NULL COMMENT '授信日期',
  `borrow_period` varchar(8) DEFAULT NULL COMMENT '借款期限',
  `annual_interest_rate` decimal(5,4) DEFAULT NULL COMMENT '年化利率',
  `status` varchar(1) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `remark` varchar(255) DEFAULT NULL COMMENT '支付备注',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `apply_detail_code` varchar(36) DEFAULT NULL COMMENT '支付申请单明细表code',
  `is_checked` varchar(1) DEFAULT NULL COMMENT '是否被选中（1.被选中；0.未选中）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='支付申请单明细临时表';

-- ----------------------------
-- Table structure for plms_pay_apply_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_apply_info`;
CREATE TABLE `plms_pay_apply_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `credit_groupid` varchar(36) DEFAULT NULL COMMENT '收款机构代码（UM代码）',
  `receiver_type_code` varchar(36) DEFAULT NULL COMMENT '收款方类别code',
  `receiver_type_name` varchar(100) DEFAULT NULL COMMENT '收款方类别名称',
  `receiver_code` varchar(50) DEFAULT NULL COMMENT '收款方code',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '收款方名称',
  `credit_name` varchar(100) DEFAULT NULL COMMENT '收款卡账户名',
  `credit_bank_no` varchar(100) DEFAULT NULL COMMENT '收款卡账号',
  `credit_bank_code` varchar(14) DEFAULT NULL COMMENT '收款卡开户行代码',
  `credit_bank_name` varchar(50) DEFAULT NULL COMMENT '收款卡开户行名称',
  `credit_bank_branch_code` varchar(14) DEFAULT NULL COMMENT '收款卡开户分支行号',
  `credit_bank_branch_name` varchar(50) DEFAULT NULL COMMENT '收款卡开户行支行名称',
  `credit_province` varchar(20) DEFAULT NULL COMMENT '收款卡省份',
  `credit_city` varchar(40) DEFAULT NULL COMMENT '收款卡市',
  `credit_id_type` varchar(3) DEFAULT NULL COMMENT '收款卡证件类型',
  `credit_id_no` varchar(32) DEFAULT NULL COMMENT '财务审核结果(1同意，0拒绝)',
  `customer_type` varchar(1) DEFAULT NULL COMMENT '客户类型',
  `pc_flag` varchar(1) DEFAULT NULL COMMENT '对公对私标志',
  `applicant_name` varchar(100) DEFAULT NULL COMMENT '申请人名称',
  `applicant_code` varchar(36) DEFAULT NULL COMMENT '申请人code',
  `apply_department` varchar(255) DEFAULT NULL COMMENT '申请部门',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `apply_status` varchar(2) DEFAULT NULL COMMENT '支付申请单状态 申请单状态（00草稿、01待运营审核、02运营拒单、03待财务审核、04财务拒单、05待总经理审批、06总经理拒单、07支付中、08支付完成、09支付失败）',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '申请支付金额',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `actual_fee` decimal(9,2) DEFAULT NULL COMMENT '实付手续费',
  `status` varchar(1) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `return_id` varchar(36) DEFAULT NULL COMMENT '代付支付表返回code',
  `route_code` varchar(100) DEFAULT NULL COMMENT '汇路设置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='支付申请单';

-- ----------------------------
-- Table structure for plms_pay_apply_temp
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_apply_temp`;
CREATE TABLE `plms_pay_apply_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `credit_groupid` varchar(36) DEFAULT NULL COMMENT '收款机构代码（UM代码）',
  `pay_apply_code` varchar(36) DEFAULT NULL COMMENT '支付申请单code',
  `receiver_type_code` varchar(36) DEFAULT NULL COMMENT '收款方类别code',
  `receiver_type_name` varchar(100) DEFAULT NULL COMMENT '收款方类别名称',
  `receiver_code` varchar(50) DEFAULT NULL COMMENT '收款方code',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '收款方名称',
  `credit_name` varchar(100) DEFAULT NULL COMMENT '收款卡账户名',
  `credit_bank_no` varchar(100) DEFAULT NULL COMMENT '收款卡账号',
  `credit_bank_code` varchar(14) DEFAULT NULL COMMENT '收款卡开户行代码',
  `credit_bank_name` varchar(50) DEFAULT NULL COMMENT '收款卡开户行名称',
  `credit_bank_branch_code` varchar(14) DEFAULT NULL COMMENT '收款卡开户分支行号',
  `credit_bank_branch_name` varchar(50) DEFAULT NULL COMMENT '收款卡开户行支行名称',
  `credit_province` varchar(20) DEFAULT NULL COMMENT '收款卡省份',
  `credit_city` varchar(40) DEFAULT NULL COMMENT '收款卡市',
  `credit_id_type` varchar(3) DEFAULT NULL COMMENT '收款卡证件类型',
  `credit_id_no` varchar(32) DEFAULT NULL COMMENT '财务审核结果(1同意，0拒绝)',
  `customer_type` varchar(1) DEFAULT NULL COMMENT '客户类型',
  `pc_flag` varchar(1) DEFAULT NULL COMMENT '对公对私标志',
  `applicant_name` varchar(100) DEFAULT NULL COMMENT '申请人名称',
  `applicant_code` varchar(36) DEFAULT NULL COMMENT '申请人code',
  `apply_department` varchar(255) DEFAULT NULL COMMENT '申请部门',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '申请支付金额',
  `status` varchar(1) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='支付申请单临时表';

-- ----------------------------
-- Table structure for plms_pay_collection_trans_his
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_collection_trans_his`;
CREATE TABLE `plms_pay_collection_trans_his` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `batch_number` varchar(36) DEFAULT NULL COMMENT '支付批次号',
  `serial_no` varchar(36) DEFAULT NULL COMMENT '支付流水号',
  `apply_time` datetime DEFAULT NULL COMMENT '发起支付时间',
  `trans_status` varchar(2) DEFAULT NULL COMMENT '交易结果(0：等待发起代扣或代付，1：交易成功，2：处理中，3：交易失败)',
  `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '应付金额',
  `fee` decimal(10,2) DEFAULT NULL COMMENT '应付手续费金额',
  `pay_amount` decimal(12,2) DEFAULT NULL COMMENT '代付总金额(应付金额+应付手续费金额)',
  `actual_fee` decimal(10,2) DEFAULT NULL COMMENT '实际代付手续费金额',
  `reconciliation_status` varchar(1) DEFAULT NULL COMMENT '是否对账(1：已对账，2：未对账)',
  `reconcile_time` datetime DEFAULT NULL COMMENT '对账时间',
  `return_serial_no` varchar(50) DEFAULT NULL COMMENT '支付平台流水号',
  `return_code` varchar(36) DEFAULT NULL COMMENT '支付平台应答码',
  `return_work_time` datetime DEFAULT NULL COMMENT '支付平台工作日期',
  `return_message` varchar(1024) DEFAULT NULL COMMENT '支付平台应答消息',
  `error_code` varchar(50) DEFAULT NULL COMMENT '错误号',
  `error_message` varchar(255) DEFAULT NULL COMMENT '错误信息',
  `business_type` varchar(10) DEFAULT NULL COMMENT '调用方业务类型(100001、还款计划在线代收)',
  `business_id` varchar(36) DEFAULT NULL COMMENT '调用方关联字段',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `deposit_name` varchar(50) DEFAULT NULL COMMENT '还款卡姓名',
  `deposit_bank_code` varchar(36) DEFAULT NULL COMMENT '还款卡开户行代码',
  `deposit_bank_name` varchar(200) DEFAULT NULL COMMENT '还款卡开户行名称',
  `deposit_bank_branch_code` varchar(50) DEFAULT NULL COMMENT '还款卡开户分支行号',
  `deposit_bank_branch_name` varchar(200) DEFAULT NULL COMMENT '还款卡开户行支行名称',
  `deposit_province` varchar(50) DEFAULT NULL COMMENT '还款卡省份',
  `deposit_city` varchar(50) DEFAULT NULL COMMENT '还款卡市',
  `deposit_account_no` varchar(50) DEFAULT NULL COMMENT '还款卡账号',
  `deposit_card_type` varchar(1) DEFAULT NULL COMMENT '还款卡类型（1：借记卡，2：贷记卡）',
  `deposit_id_type` varchar(3) DEFAULT NULL COMMENT '还款卡证件类型(101：身份证 201：营业执照)',
  `deposit_id_no` varchar(100) DEFAULT NULL COMMENT '还款卡证件号码',
  `deposit_card_mobile` varchar(20) DEFAULT NULL COMMENT '还款卡银行预留手机号',
  `deposit_expire_month` varchar(2) DEFAULT NULL COMMENT '还款卡到期-月',
  `deposit_expire_year` varchar(4) DEFAULT NULL COMMENT '还款卡到期-年',
  `customer_type` varchar(1) DEFAULT NULL COMMENT '客户类型(1：个人；2：企业)',
  `pc_flag` varchar(1) DEFAULT NULL COMMENT '对公对私标志(1-对公 0-对私)',
  `pay_route_code` varchar(50) DEFAULT NULL COMMENT '支付使用通道（支付平台返回）',
  `deposit_cvv` varchar(10) DEFAULT NULL COMMENT '还款银行卡ccv2（card verification value）部分银行信用卡必传该 字段',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `credit_groupid` varchar(50) DEFAULT NULL COMMENT '收款机构代码（UM代码）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='代扣支付历史表';

-- ----------------------------
-- Table structure for plms_pay_online_collect_config
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_online_collect_config`;
CREATE TABLE `plms_pay_online_collect_config` (
  `id` int(14) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `contract_code` varchar(36) DEFAULT NULL COMMENT '借款合同code',
  `customer_type` varchar(1) DEFAULT NULL DEFAULT '1' COMMENT '客户类型（1：个人；2：企业）',
  `pc_flag` varchar(1) DEFAULT NULL DEFAULT '0' COMMENT '对公对私标志（1：对公，0：对私）',
  `deposit_name` varchar(50) DEFAULT NULL COMMENT '还款卡姓名',
  `deposit_bank_code` varchar(36) DEFAULT NULL COMMENT '还款卡开户行代码',
  `deposit_bank_name` varchar(200) DEFAULT NULL COMMENT '还款卡开户行名称',
  `deposit_bank_branch_code` varchar(50) DEFAULT NULL COMMENT '还款卡开户分支行号',
  `deposit_bank_branch_name` varchar(200) DEFAULT NULL COMMENT '还款卡开户行支行名称',
  `deposit_province` varchar(50) DEFAULT NULL COMMENT '还款卡省份',
  `deposit_city` varchar(50) DEFAULT NULL COMMENT '还款卡市',
  `deposit_account_no` varchar(50) DEFAULT NULL COMMENT '还款卡账号',
  `deposit_card_type` varchar(1) DEFAULT NULL COMMENT '还款卡类型（1：借记卡，2：贷记卡）',
  `deposit_id_no` varchar(100) DEFAULT NULL COMMENT '还款卡证件号码',
  `deposit_id_type` varchar(3) DEFAULT NULL COMMENT '还款卡证件类型(101：身份证 201：营业执照)',
  `deposit_card_mobile` varchar(20) DEFAULT NULL COMMENT '还款卡银行预留手机号',
  `deposit_expire_year` varchar(4) DEFAULT NULL COMMENT '还款卡到期-年',
  `deposit_expire_month` varchar(2) DEFAULT NULL COMMENT '还款卡到期-月',
  `deposit_cvv` varchar(10) DEFAULT NULL COMMENT '还款银行卡ccv2（card verification value）部分银行信用卡必传该 字段',
  `is_online_deposit` varchar(1) DEFAULT NULL COMMENT '是否支持在线付款(1支持；0不支持)',
  `status` varchar(2) NOT NULL COMMENT '1 可用;0 不可用;3 核验中',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='在线代收配置表';

-- ----------------------------
-- Table structure for plms_pay_online_pay_config
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_online_pay_config`;
CREATE TABLE `plms_pay_online_pay_config` (
  `id` int(14) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `receiver_type_code` varchar(36) DEFAULT NULL COMMENT '收款方类别code(plms_pay_receiver_type）',
  `receiver_code` varchar(36) DEFAULT NULL COMMENT '收款方code（根据选择类型对应相应业务表里的业务主键）',
  `customer_type` varchar(1) DEFAULT NULL DEFAULT '1' COMMENT '客户类型（1：个人；2：企业）',
  `pc_flag` varchar(1) DEFAULT NULL DEFAULT '0' COMMENT '对公对私标志（1：对公，0：对私）',
  `credit_name` varchar(36) DEFAULT '' COMMENT '收款卡姓名',
  `credit_bank_code` varchar(36) DEFAULT NULL COMMENT '收款卡开户行代码',
  `credit_bank_name` varchar(200) DEFAULT NULL COMMENT '收款卡开户行名称',
  `credit_bank_branch_code` varchar(50) DEFAULT NULL COMMENT '收款卡开户分支行号',
  `credit_bank_branch_name` varchar(200) DEFAULT NULL COMMENT '收款卡开户行支行名称',
  `credit_province` varchar(50) DEFAULT NULL COMMENT '收款卡省份',
  `credit_city` varchar(50) DEFAULT NULL COMMENT '收款卡市',
  `credit_account_no` varchar(50) DEFAULT NULL COMMENT '收款卡账号',
  `credit_card_type` varchar(1) DEFAULT NULL COMMENT '收款卡类型（1：借记卡，2：贷记卡）',
  `credit_id_no` varchar(100) DEFAULT NULL COMMENT '收款卡证件号码',
  `credit_id_type` varchar(3) DEFAULT NULL COMMENT '收款卡证件类型(101：身份证 201：营业执照)',
  `credit_card_mobile` varchar(20) DEFAULT NULL COMMENT '收款卡银行预留手机号',
  `credit_expire_year` varchar(4) DEFAULT NULL COMMENT '收款卡到期-年(yyyy）',
  `credit_expire_month` varchar(2) DEFAULT NULL COMMENT '收款卡到期-月(MM)',
  `credit_cvv` varchar(10) DEFAULT NULL COMMENT '收款银行卡ccv2（card verification value）部分银行信用卡必传该 字段',
  `is_online_deposit` varchar(1) DEFAULT NULL COMMENT '是否支持在线付款(1支持；0不支持)',
  `status` varchar(2) NOT NULL COMMENT '1 可用;0 不可用;3 核验中',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='在线代付配置表';

-- ----------------------------
-- Table structure for plms_pay_payment_fee_type
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_payment_fee_type`;
CREATE TABLE `plms_pay_payment_fee_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '费用名称',
  `pay_receiver_type_code` varchar(36) DEFAULT NULL COMMENT 'plms_pay_receiver_type表code',
  `status` varchar(1) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代付费用科目表';

-- ----------------------------
-- Table structure for plms_pay_payment_trans_his
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_payment_trans_his`;
CREATE TABLE `plms_pay_payment_trans_his` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `batch_number` varchar(36) DEFAULT NULL COMMENT '支付批次号',
  `serial_no` varchar(36) DEFAULT NULL COMMENT '支付流水号',
  `apply_time` datetime DEFAULT NULL COMMENT '发起支付时间',
  `trans_status` varchar(2) DEFAULT NULL COMMENT '交易结果(0：等待发起代扣或代付，1：交易成功，2：处理中，3：交易失败)',
  `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '应付金额',
  `actual_fee` decimal(10,2) DEFAULT NULL COMMENT '实际代付手续费金额',
  `reconciliation_status` varchar(1) DEFAULT NULL COMMENT '是否对账(1：已对账，2：未对账)',
  `reconcile_time` datetime DEFAULT NULL COMMENT '对账时间',
  `return_serial_no` varchar(50) DEFAULT NULL COMMENT '支付平台流水号',
  `return_code` varchar(36) DEFAULT NULL COMMENT '支付平台应答码',
  `return_work_time` datetime DEFAULT NULL COMMENT '支付平台工作日期',
  `return_message` varchar(1024) DEFAULT NULL COMMENT '支付平台应答消息',
  `error_code` varchar(50) DEFAULT NULL COMMENT '错误号',
  `error_message` varchar(255) DEFAULT NULL COMMENT '错误信息',
  `business_type` varchar(10) DEFAULT NULL COMMENT '调用方业务类型(500001：代付支付申请单)',
  `business_id` varchar(36) DEFAULT NULL COMMENT '调用方关联字段',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `credit_name` varchar(100) DEFAULT '' COMMENT '收款卡账户名',
  `credit_bank_no` varchar(100) DEFAULT NULL COMMENT '收款卡账号',
  `credit_bank_code` varchar(14) DEFAULT NULL COMMENT '收款卡开户行代码',
  `credit_bank_name` varchar(255) DEFAULT NULL,
  `credit_bank_branch_code` varchar(14) DEFAULT NULL,
  `credit_bank_branch_name` varchar(50) DEFAULT NULL,
  `credit_province` varchar(20) DEFAULT NULL,
  `credit_city` varchar(40) DEFAULT NULL COMMENT '收款卡省份',
  `credit_id_type` varchar(3) DEFAULT NULL COMMENT '收款卡证件类型101：身份证 201：营业执照)',
  `credit_id_no` varchar(32) DEFAULT NULL COMMENT '收款卡证件号码',
  `customer_type` varchar(1) DEFAULT NULL COMMENT '客户类型(1：个人；2：企业)',
  `pc_flag` varchar(1) DEFAULT NULL COMMENT '对公对私标志(1-对公 0-对私)',
  `route_code` varchar(100) DEFAULT NULL COMMENT '汇路设置(汇路为空时默认使用支付平台路由(kjtpay,baofoopay))',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='代付支付历史表';

-- ----------------------------
-- Table structure for plms_pay_receiver_type
-- ----------------------------
DROP TABLE IF EXISTS `plms_pay_receiver_type`;
CREATE TABLE `plms_pay_receiver_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态（1服务费罚金，2利息罚金，3本金罚金，4服务费，5利息，6本金，7保证金违约金,8保证金,9、提前还款利息违约金,10、提前还款服务费违约）',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='收款方类型表';

-- ----------------------------
-- Table structure for plms_route_info
-- ----------------------------
DROP TABLE IF EXISTS `plms_route_info`;
CREATE TABLE `plms_route_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) DEFAULT NULL,
  `english_abbr` varchar(40) DEFAULT NULL COMMENT '汇路英文简称',
  `route_name` varchar(40) DEFAULT NULL COMMENT '汇路名称',
  `status` varchar(1) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇路基本信息表';

-- ----------------------------
-- Table structure for plms_schedule_collection_his
-- ----------------------------
DROP TABLE IF EXISTS `plms_schedule_collection_his`;
CREATE TABLE `plms_schedule_collection_his` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `contract_code` varchar(36) DEFAULT NULL COMMENT '借款合同code',
  `repayment_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '应还日期',
  `principal` decimal(12,2) DEFAULT NULL COMMENT '代收本金',
  `interest` decimal(12,2) DEFAULT NULL COMMENT '代收利息',
  `service_fee` decimal(12,2) DEFAULT NULL COMMENT '代收服务费',
  `bail` decimal(12,2) DEFAULT NULL COMMENT '代收保证金',
  `overdue` decimal(12,2) DEFAULT NULL COMMENT '代收罚息',
  `service_fee_penalty` decimal(12,2) DEFAULT NULL COMMENT '代收服务费违约金',
  `bail_penalty` decimal(12,2) DEFAULT NULL COMMENT '代收保证金违约金',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '代收金额',
  `fee` decimal(10,2) DEFAULT NULL COMMENT '代收手续费金额',
  `pay_amount` decimal(12,2) DEFAULT NULL COMMENT '代收总金额(代收金额+代收手续费金额)',
  `actual_fee` decimal(10,2) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL COMMENT '申请发起时间',
  `trans_status` varchar(2) DEFAULT NULL COMMENT '交易结果(0：等待发起代扣或代付，1：交易成功，2：处理中，3：交易失败)',
  `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `reconciliation_status` varchar(1) DEFAULT NULL COMMENT '是否对账(1：已对账，2：未对账)',
  `reconcile_time` datetime DEFAULT NULL COMMENT '对账时间',
  `status` varchar(1) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `return_id` varchar(40) DEFAULT NULL COMMENT '代扣支付表返回code',
  `be_hand_operate` varchar(1) DEFAULT NULL COMMENT '是否是手工代收(1是，0否)',
  `hand_operator_code` varchar(50) DEFAULT NULL COMMENT '手工发起人code',
  `hand_operator_name` varchar(50) DEFAULT NULL COMMENT '手工发起人',
  `department_name` varchar(50) DEFAULT NULL COMMENT '手工代收部门名字',
  `pay_route_code` varchar(50) DEFAULT NULL COMMENT '支付使用通道（支付平台返回）',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='还款计划代收历史表';

-- ----------------------------
-- Table structure for plms_schedule_detail_collection_his
-- ----------------------------
DROP TABLE IF EXISTS `plms_schedule_detail_collection_his`;
CREATE TABLE `plms_schedule_detail_collection_his` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `schedule_detail_code` varchar(36) DEFAULT NULL COMMENT '还款计划明细code',
  `collection_his_code` varchar(36) DEFAULT NULL COMMENT '还款计划代收历史表code',
  `repayment_date` datetime DEFAULT NULL COMMENT '应还日期',
  `principal` decimal(11,2) DEFAULT NULL COMMENT '代收本金',
  `interest` decimal(11,2) DEFAULT NULL COMMENT '代收利息',
  `service_fee` decimal(11,2) DEFAULT NULL COMMENT '代收服务费',
  `bail` decimal(11,2) DEFAULT NULL COMMENT '代收保证金',
  `overdue` decimal(11,2) DEFAULT NULL COMMENT '代收罚息',
  `service_fee_penalty` decimal(11,2) DEFAULT NULL COMMENT '代收服务费违约金',
  `bail_penalty` decimal(11,2) DEFAULT NULL COMMENT '代收保证金违约金',
  `paid_code` varchar(36) DEFAULT NULL COMMENT '放款信息表code',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='还款计划明细和代收历史关系表';

-- ----------------------------
-- Table structure for plms_subject_account_type_config
-- ----------------------------
DROP TABLE IF EXISTS `plms_subject_account_type_config`;
CREATE TABLE `plms_subject_account_type_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(10) DEFAULT NULL COMMENT '科目(1001应收保证金违约金，1002应收服务费罚金，1003应收利息罚金，1004应收本金罚金，1005 应收保证金，1006 应收服务费，1007应收利息，1008应收本金,1009应收差额手续费；2001应付保证金违约金，2002 应付服务费罚金，2003应付利息罚金，2004应付本金罚金，2005 应付保证金，2006 应付服务费，2007应付利息，2008应付本金，2009应付渠道费用，2010 应付业务经理费用)',
  `subject_name` varchar(255) DEFAULT NULL COMMENT '科目名称',
  `account_type_code` varchar(36) DEFAULT NULL COMMENT '资金账户类型表code',
  `account_type_name` varchar(255) DEFAULT NULL COMMENT '资金账户类型名称',
  `status` varchar(1) DEFAULT NULL COMMENT '0、无效;1、有效',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='科目账户类型映射默认配置模板';

-- ----------------------------
-- Table structure for plms_subject_account_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS `plms_subject_account_type_mapping`;
CREATE TABLE `plms_subject_account_type_mapping` (
  `id` int(14) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `contract_code` varchar(36) DEFAULT NULL COMMENT '合同编号',
  `subject` varchar(10) DEFAULT NULL COMMENT '科目(1001应收保证金违约金，1002应收服务费罚金，1003应收利息罚金，1004应收本金罚金，1005 应收保证金，1006 应收服务费，1007应收利息，1008应收本金,1009应收差额手续费；2001应付保证金违约金，2002 应付服务费罚金，2003应付利息罚金，2004应付本金罚金，2005 应付保证金，2006 应付服务费，2007应付利息，2008应付本金，2009应付渠道费用，2010 应付业务经理费用)',
  `account_type_code` varchar(36) DEFAULT NULL COMMENT '账户类型',
  `status` varchar(100) DEFAULT NULL COMMENT '0、无效;1、有效',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='科目账户映射表';

-- ----------------------------
-- Table structure for plms_user_role_pay_apply_status_map
-- ----------------------------
DROP TABLE IF EXISTS `plms_user_role_pay_apply_status_map`;
CREATE TABLE `plms_user_role_pay_apply_status_map` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `role_code` varchar(50) DEFAULT NULL,
  `apply_status` varchar(2) DEFAULT NULL COMMENT '支付申请单状态',
  `status` varchar(1) DEFAULT NULL COMMENT '状态(0、无效;1、有效)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色支付申请单状态映射表';

DROP TABLE IF EXISTS `plms_receipts_calling_relation`;
CREATE TABLE `plms_receipts_calling_relation` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `receipts_record_code` varchar(36) DEFAULT NULL COMMENT 'plms_receipts_record表code',
  `business_type` varchar(36) DEFAULT NULL COMMENT '调用方业务类型(100001、还款计划在线代收)',
  `business_id` varchar(36) DEFAULT NULL COMMENT '调用方业务主键',
  `status` varchar(2) DEFAULT '1' COMMENT '状态(0、无效;1、有效)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;