
-- ----------------------------
-- Table structure for ppms_baofoopay_trans_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_baofoopay_trans_info`;
CREATE TABLE `ppms_baofoopay_trans_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `terminal_id` varchar(40) DEFAULT NULL COMMENT '终端号',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户号',
  `route_trans_type` varchar(10) DEFAULT NULL COMMENT '汇路交易类型',
  `trans_sub_type` varchar(10) DEFAULT NULL COMMENT '交易子类型',
  `order_no` varchar(40) DEFAULT NULL COMMENT '宝付订单号',
  `merchant_order_no` varchar(40) DEFAULT NULL COMMENT '商户订单号',
  `clear_date` varchar(20) DEFAULT NULL COMMENT '清算日期',
  `order_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `trans_amt` varchar(17) DEFAULT NULL COMMENT '交易金额',
  `fee` varchar(17) DEFAULT NULL COMMENT '手续费',
  `baofoo_trans_no` varchar(40) DEFAULT NULL COMMENT '宝付交易号',
  `pay_create_time` varchar(20) DEFAULT NULL COMMENT '支付订单创建时间',
  `refund_order_no` varchar(40) DEFAULT NULL COMMENT '商户退款订单号',
  `refund_create_time` varchar(40) DEFAULT NULL COMMENT '退款订单创建时间',
  `route_batch_no` varchar(40) DEFAULT NULL COMMENT '汇路批次号',
  `payee_account` varchar(40) DEFAULT NULL COMMENT '收款人账号',
  `payee_name` varchar(50) DEFAULT NULL COMMENT '收款人姓名',
  `pay_another_time` varchar(20) DEFAULT NULL COMMENT 'pay_another_time',
  `trans_status` varchar(50) NOT NULL COMMENT '交易状态 00：处理失败 01：处理成功 02：处理中',
  `trans_time` varchar(8) NOT NULL COMMENT '结算日期',
  `check_flag` varchar(2) DEFAULT NULL COMMENT '对账标识 00：未对账  01：已对账',
  `trans_type` varchar(40) DEFAULT NULL COMMENT '交易类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ppms_baofoopay_trans_log
-- ----------------------------
DROP TABLE IF EXISTS `ppms_baofoopay_trans_log`;
CREATE TABLE `ppms_baofoopay_trans_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `terminal_id` varchar(40) DEFAULT NULL COMMENT '终端号',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户号',
  `route_trans_type` varchar(10) DEFAULT NULL COMMENT '汇路交易类型',
  `trans_sub_type` varchar(10) DEFAULT NULL COMMENT '交易子类型',
  `order_no` varchar(40) DEFAULT NULL COMMENT '宝付订单号',
  `merchant_order_no` varchar(40) DEFAULT NULL COMMENT '商户订单号',
  `clear_date` varchar(20) DEFAULT NULL COMMENT '清算日期',
  `order_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `trans_amt` varchar(17) DEFAULT NULL COMMENT '交易金额',
  `fee` varchar(17) DEFAULT NULL COMMENT '手续费',
  `baofoo_trans_no` varchar(40) DEFAULT NULL COMMENT '宝付交易号',
  `pay_create_time` varchar(20) DEFAULT NULL COMMENT '支付订单创建时间',
  `refund_order_no` varchar(40) DEFAULT NULL COMMENT '商户退款订单号',
  `refund_create_time` varchar(40) DEFAULT NULL COMMENT '退款订单创建时间',
  `route_batch_no` varchar(40) DEFAULT NULL COMMENT '汇路批次号',
  `payee_account` varchar(40) DEFAULT NULL COMMENT '收款人账号',
  `payee_name` varchar(50) DEFAULT NULL COMMENT '收款人姓名',
  `pay_another_time` varchar(20) DEFAULT NULL COMMENT 'pay_another_time',
  `trans_status` varchar(50) NOT NULL COMMENT '交易状态 00：处理失败 01：处理成功 02：处理中',
  `trans_time` varchar(8) NOT NULL COMMENT '结算日期',
  `check_flag` varchar(2) DEFAULT NULL COMMENT '对账标识 00：未对账  01：已对账',
  `trans_type` varchar(40) DEFAULT NULL COMMENT '交易类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ppms_liqu_file_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_liqu_file_info`;
CREATE TABLE `ppms_liqu_file_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `file_name` varchar(100) NOT NULL COMMENT '文件名',
  `file_id` varchar(100) NOT NULL COMMENT '文件id',
  `route_code` varchar(40) NOT NULL COMMENT '汇路编码',
  `settle_date` datetime NOT NULL COMMENT '清算日期',
  `file_type` varchar(3) DEFAULT NULL COMMENT '文件类型 01：出款 02：收单',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;





alter table ppms_rate_info add column merchant_code VARCHAR(40) NOT NULL COMMENT '商户编码';

alter table ppms_rate_info add column pc_flag VARCHAR(2) NOT NULL COMMENT '0：对私 1：对公';


alter table ppms_rate_info add unique key agd(route_code,trans_type,merchant_code,pc_flag);



alter table ppms_error_info change trans_code trans_code varchar(40) NULL comment '交易信息表code';
alter table ppms_error_info change route_trans_code route_trans_code varchar(40) NULL comment '汇路交易信息表code';
alter table ppms_error_info change work_date work_date datetime NULL comment '工作日期';

alter table ppms_error_info change trans_amount trans_amount decimal(15,2) NULL comment '原平台交易金额';
alter table ppms_error_info change route_trans_amount route_trans_amount decimal(15,2) NULL comment '原汇路交易金额';

alter table ppms_account_turnover_info add column route_code VARCHAR(40) NOT NULL COMMENT '汇路编码';

alter table ppms_account_turnover_info change trans_code trans_code varchar(40) NULL comment '交易流水code';

alter table ppms_account_turnover_info add column route_trans_code VARCHAR(40) NULL COMMENT '汇路交易表code';




