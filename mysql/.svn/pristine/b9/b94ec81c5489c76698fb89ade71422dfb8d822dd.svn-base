
-- ----------------------------
-- Table structure for ppms_account_turnover_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_account_turnover_info`;
CREATE TABLE `ppms_account_turnover_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `merchant_code` varchar(40) NOT NULL COMMENT '商户编码',
  `amount` decimal(15,2) NOT NULL COMMENT '金额',
  `account_flag` varchar(2) NOT NULL COMMENT '出入帐标识 0：入账 1：出账',
  `trans_date` datetime NOT NULL COMMENT '交易日期',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `account_type` varchar(2) NOT NULL COMMENT '账务类型 01：代收 02：代付',
  `trans_code` varchar(40) NOT NULL COMMENT '交易流水code',
  `subject` varchar(2) NOT NULL COMMENT '科目',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='26.	账务出入明细表';

-- ----------------------------
-- Table structure for ppms_bank_card_black
-- ----------------------------
DROP TABLE IF EXISTS `ppms_bank_card_black`;
CREATE TABLE `ppms_bank_card_black` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `card_no` varchar(20) NOT NULL COMMENT '银行卡号',
  `black_type` varchar(2) NOT NULL COMMENT '风险类型',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_card_no` (`card_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='11.	银行卡黑名单';

-- ----------------------------
-- Table structure for ppms_call_route_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_call_route_info`;
CREATE TABLE `ppms_call_route_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `sys_serno` varchar(40) NOT NULL COMMENT '系统流水号',
  `route_code` varchar(40) NOT NULL COMMENT '路由编码',
  `trans_serno` varchar(40) NOT NULL COMMENT '交易流水号',
  `route_serno` varchar(40) NOT NULL COMMENT '汇路订单号',
  `trans_time` datetime DEFAULT NULL COMMENT '汇路交易时间',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型 01:大额代收',
  `route_ret_code` varchar(50) DEFAULT NULL COMMENT '汇路相应码',
  `route_ret_msg` varchar(500) DEFAULT NULL COMMENT '汇路相应信息',
  `route_trans_result` varchar(20) DEFAULT NULL COMMENT '汇路交易结果',
  `trans_status` varchar(2) NOT NULL COMMENT '交易状态 00：处理失败 01：处理成功 02：处理中',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `send_json` mediumtext COMMENT '发送报文',
  `response_json` mediumtext COMMENT '响应报文',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='23.	汇路调用信息表';

-- ----------------------------
-- Table structure for ppms_card_bin_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_card_bin_info`;
CREATE TABLE `ppms_card_bin_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `issuer_code` varchar(14) NOT NULL COMMENT '发卡行机构代码',
  `issuser_name` varchar(120) NOT NULL COMMENT '发卡机构名称',
  `card_name` varchar(60) DEFAULT NULL COMMENT '卡名称',
  `card_no_start_pos` varchar(10) NOT NULL COMMENT '卡bin起始字节',
  `card_no_len` varchar(2) NOT NULL COMMENT '主账号长度',
  `bin_len` varchar(2) NOT NULL COMMENT '卡bin长度',
  `bin_num` varchar(12) NOT NULL COMMENT '卡bin号',
  `card_type` varchar(6) NOT NULL COMMENT '卡种 C:贷记卡 D:借记卡',
  `status` varchar(2) NOT NULL COMMENT '1：可用  0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7942 DEFAULT CHARSET=utf8 COMMENT='2.	平台卡bin信息表';

-- ----------------------------
-- Table structure for ppms_collect_batch
-- ----------------------------
DROP TABLE IF EXISTS `ppms_collect_batch`;
CREATE TABLE `ppms_collect_batch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `trans_info_code` varchar(40) NOT NULL COMMENT '交易主表code',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型 01:大额代收',
  `req_file_name` varchar(60) DEFAULT NULL COMMENT '批次文件名称',
  `req_file_id` varchar(40) DEFAULT NULL COMMENT '批次文件编码',
  `resp_file_name` varchar(60) DEFAULT NULL COMMENT '回盘文件名称',
  `resp_file_id` varchar(40) DEFAULT NULL COMMENT '回盘文件id',
  `resp_time` datetime DEFAULT NULL COMMENT '回盘时间',
  `resp_count` varchar(2) DEFAULT NULL COMMENT '回盘次数',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `total_count` bigint(20) NOT NULL COMMENT '交易总笔数',
  `total_amount` decimal(15,2) NOT NULL COMMENT '交易总金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=utf8 COMMENT='21.	代收批量主表';

-- ----------------------------
-- Table structure for ppms_collect_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_collect_info`;
CREATE TABLE `ppms_collect_info` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `trans_info_code` varchar(40) NOT NULL COMMENT '交易主表code',
  `detail_serno` varchar(40) DEFAULT NULL COMMENT '明细流水号 批量专用',
  `route_type` varchar(2) NOT NULL COMMENT '路由类型 0：平台路由 1：指定路由',
  `route_list` varchar(200) DEFAULT NULL COMMENT '路由编码',
  `route_num` varchar(3) DEFAULT NULL COMMENT '路由序号',
  `trans_serno` varchar(40) DEFAULT NULL COMMENT '交易流水号',
  `route_serno` varchar(40) DEFAULT NULL COMMENT '汇路流水号',
  `trans_date` datetime DEFAULT NULL COMMENT '汇路交易日期',
  `trans_time` varchar(6) DEFAULT NULL COMMENT '汇路交易时间',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型 01:大额代收',
  `payer_card_no` varchar(20) NOT NULL COMMENT '付款人银行卡号',
  `payer_card_type` varchar(2) DEFAULT NULL COMMENT '付款人银行卡类型 C:贷记卡 D:借记卡',
  `payer_card_mobile` varchar(20) NOT NULL COMMENT '付款人银行卡手机',
  `payer_name` varchar(64) NOT NULL COMMENT '付款人姓名',
  `payer_cert_type` varchar(3) NOT NULL COMMENT '付款人证件类型 身份证：101',
  `payer_cer_no` varchar(20) NOT NULL COMMENT '付款人证件号码',
  `payer_exp_year` varchar(2) DEFAULT NULL COMMENT '付款银行卡过期时间（年）  YY 部分银行信用卡',
  `payer_exp_month` varchar(2) DEFAULT NULL COMMENT '付款银行卡过期时间',
  `payer_cvv` varchar(4) DEFAULT NULL COMMENT '付款银行卡校验值',
  `trans_status` varchar(3) NOT NULL COMMENT '交易状态 00：处理失败 01：处理成功 02：处理中',
  `step_status` varchar(3) NOT NULL COMMENT '业务步骤',
  `check_flag` varchar(2) NOT NULL COMMENT '对账标识 00：未对账  01：已对账',
  `route_ret_code` varchar(50) DEFAULT NULL COMMENT '汇路响应码',
  `route_ret_msg` varchar(500) DEFAULT NULL COMMENT '汇路响应信息',
  `route_trans_result` varchar(20) DEFAULT NULL COMMENT '汇路交易结果',
  `resp_code` varchar(20) DEFAULT NULL COMMENT '平台响应码',
  `resp_msg` varchar(500) DEFAULT NULL COMMENT '平台相应信息',
  `estimate_fee` decimal(15,2) DEFAULT NULL COMMENT '预估手续费',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `real_fee` decimal(15,2) DEFAULT NULL COMMENT '实际手续费',
  `trans_amount` decimal(15,2) NOT NULL COMMENT '交易金额',
  `real_trans_amount` decimal(15,2) DEFAULT NULL COMMENT '汇路实际交易金额',
  `custom_type` varchar(3) NOT NULL COMMENT '客户类型 1：个人 2：企业',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批处理标识号',
  `sub_acct_flag` varchar(2) DEFAULT NULL COMMENT '分账标识0：不分账 1：分账',
  `pc_flag` varchar(2) DEFAULT NULL COMMENT '0：对私 1：对公',
  `chl_serno` varchar(40) NOT NULL COMMENT '上游系统流水号',
  `batch_code` varchar(40) DEFAULT NULL COMMENT '批量表code',
  `payee_group_id` varchar(40) DEFAULT NULL COMMENT '收款UM机构编码',
  `payer_bank` varchar(14) DEFAULT NULL COMMENT '付款人开户行号',
  `payer_bank_name` varchar(50) DEFAULT NULL COMMENT '付款人开户行名称',
  `payer_sub_bank` varchar(14) DEFAULT NULL COMMENT '付款人开户分支行号',
  `payer_sub_bank_name` varchar(50) DEFAULT NULL COMMENT '付款人开户分支行名称',
  `prov` varchar(20) DEFAULT NULL COMMENT '省',
  `city` varchar(40) DEFAULT NULL COMMENT '城市',
  `issuer_code` varchar(120) DEFAULT NULL COMMENT '发卡行机构代码',
  `chl_route_list` varchar(200) DEFAULT NULL COMMENT '渠道路由列表',
  `route_code` varchar(40) DEFAULT NULL COMMENT '当前路由编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=260005 DEFAULT CHARSET=utf8 COMMENT='18.	代收交易信息表';

-- ----------------------------
-- Table structure for ppms_custom_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_custom_info`;
CREATE TABLE `ppms_custom_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `custom_name` varchar(64) NOT NULL COMMENT '姓名（户名）',
  `cert_type` varchar(3) NOT NULL COMMENT '证件类型 身份证：101',
  `cert_no` varchar(255) NOT NULL COMMENT '证件号',
  `first_card_code` varchar(40) NOT NULL COMMENT '首次交易银行卡信息表code',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_name_cert_` (`custom_name`,`cert_type`,`cert_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='4.	会员基本信息表';

-- ----------------------------
-- Table structure for ppms_cutom_card_no
-- ----------------------------
DROP TABLE IF EXISTS `ppms_cutom_card_no`;
CREATE TABLE `ppms_cutom_card_no` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `card_no` varchar(32) NOT NULL COMMENT '银行卡号',
  `mobile_no` varchar(20) NOT NULL COMMENT '银行卡预留手机',
  `custom_code` varchar(40) NOT NULL COMMENT '客户code',
  `card_type` varchar(2) NOT NULL COMMENT '卡类型 C:贷记卡 D:借记卡',
  `exp_year` varchar(2) DEFAULT NULL COMMENT '过期时间（年） 部分银行信用卡',
  `exp_month` varchar(2) DEFAULT NULL COMMENT '过期时间（月） 部分银行信用卡',
  `cvv` varchar(4) DEFAULT NULL COMMENT '银行卡校验值 部分银行信用卡',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_card_mobile_custom_type` (`card_no`,`mobile_no`,`custom_code`,`card_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='5.	会员银行卡信息表';

-- ----------------------------
-- Table structure for ppms_error_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_error_info`;
CREATE TABLE `ppms_error_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `clear_date` datetime NOT NULL COMMENT '对账日期',
  `route_code` varchar(40) NOT NULL COMMENT '汇路编码',
  `error_msg` varchar(500) NOT NULL COMMENT '差错说明',
  `work_date` datetime NOT NULL COMMENT '工作日期',
  `trans_code` varchar(40) NOT NULL COMMENT '交易信息表code',
  `error_type` varchar(2) NOT NULL COMMENT '差错类型 01：代收 02：代付',
  `trans_amount` decimal(15,2) NOT NULL COMMENT '原平台交易金额',
  `check_status` varchar(2) NOT NULL COMMENT '对账状态 00：平台交易缺失 01：汇路交易缺失 02：金额差异 03：交易状态不符',
  `error_amount` decimal(15,2) DEFAULT NULL COMMENT '差异金额',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `route_trans_amount` decimal(15,2) NOT NULL COMMENT '原汇路交易金额',
  `route_trans_code` varchar(40) DEFAULT NULL COMMENT '汇路交易信息表code',
  `trans_status` varchar(3) DEFAULT NULL COMMENT '原平台交易状态 00：处理失败 01：处理成功 02：处理中',
  `route_trans_status` varchar(3) DEFAULT NULL COMMENT '原汇路交易状态 00：处理失败 01：处理成功 02：处理中',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1024 DEFAULT CHARSET=utf8 COMMENT='24.	对账差错信息表';

-- ----------------------------
-- Table structure for ppms_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_goods_info`;
CREATE TABLE `ppms_goods_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `goods_code` varchar(100) NOT NULL COMMENT '商品编码(上游提供)',
  `route_goods_code` varchar(40) NOT NULL COMMENT '渠道商品编码',
  `goods_name` varchar(100) NOT NULL COMMENT '商品名称',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户编码',
  `goods_type` varchar(40) DEFAULT NULL COMMENT '商品类型 汇路要求',
  `goods_price` decimal(15,2) DEFAULT NULL COMMENT '商品单价',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_goodsroute_route_merchant` (`goods_code`,`route_goods_code`,`merchant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='7.	商品基本信息表';

-- ----------------------------
-- Table structure for ppms_kjtpay_trans_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_kjtpay_trans_info`;
CREATE TABLE `ppms_kjtpay_trans_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(40) NOT NULL COMMENT '业务主键',
  `merchant_trans_code` varchar(40) DEFAULT '' COMMENT '商户订单号',
  `original_merchant_trans_code` varchar(40) DEFAULT NULL COMMENT '原商户订单号(退款的原交易原始凭证号)',
  `route_serno` varchar(40) DEFAULT NULL COMMENT '交易订单号',
  `trans_type` varchar(40) DEFAULT NULL COMMENT '交易类型  01：代收  02：代付',
  `trans_order_time` varchar(50) DEFAULT NULL COMMENT '交易下单时间',
  `pay_time` varchar(50) DEFAULT NULL COMMENT '支付时间',
  `trans_amount` varchar(50) DEFAULT NULL COMMENT '交易金额',
  `trans_fee` varchar(50) DEFAULT NULL COMMENT '手续费',
  `trans_status` varchar(50) DEFAULT NULL COMMENT '交易状态',
  `trans_time` varchar(8) NOT NULL COMMENT '结算日期',
  `check_flag` varchar(2) NOT NULL COMMENT '对账标识 00：未对账 01：已对账',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `route_trans_type` varchar(40) DEFAULT NULL COMMENT '汇路交易类型 1:即时到账;2:担保交易;3:退款;4银行代扣',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `route_trans_status` varchar(50) NOT NULL COMMENT '汇路交易状态 00：处理失败 01：处理成功 02：处理中',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='快捷通交易信息表';

-- ----------------------------
-- Table structure for ppms_kjtpay_trans_log
-- ----------------------------
DROP TABLE IF EXISTS `ppms_kjtpay_trans_log`;
CREATE TABLE `ppms_kjtpay_trans_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(40) NOT NULL COMMENT '业务主键',
  `merchant_trans_code` varchar(40) DEFAULT '' COMMENT '商户订单号',
  `original_merchant_trans_code` varchar(40) DEFAULT NULL COMMENT '原商户订单号(退款的原交易原始凭证号)',
  `route_serno` varchar(40) DEFAULT NULL COMMENT '交易订单号',
  `trans_type` varchar(40) DEFAULT NULL COMMENT '交易类型  01：代收  02：代付',
  `trans_order_time` varchar(50) DEFAULT NULL COMMENT '交易下单时间',
  `pay_time` varchar(50) DEFAULT NULL COMMENT '支付时间',
  `trans_amount` varchar(50) DEFAULT NULL COMMENT '交易金额',
  `trans_fee` varchar(50) DEFAULT NULL COMMENT '手续费',
  `trans_status` varchar(50) DEFAULT NULL COMMENT '交易状态',
  `trans_time` varchar(8) NOT NULL COMMENT '结算日期',
  `check_flag` varchar(2) NOT NULL COMMENT '对账标识 00：未对账 01：已对账',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `route_trans_type` varchar(40) DEFAULT NULL COMMENT '汇路交易类型 1:即时到账;2:担保交易;3:退款;4银行代扣',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批次号',
  `route_trans_status` varchar(50) NOT NULL COMMENT '汇路交易状态 00：处理失败 01：处理成功 02：处理中',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='快捷通交易信息表';

-- ----------------------------
-- Table structure for ppms_liqu_record
-- ----------------------------
DROP TABLE IF EXISTS `ppms_liqu_record`;
CREATE TABLE `ppms_liqu_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `task_id` varchar(40) NOT NULL COMMENT '任务编号',
  `task_name` varchar(100) NOT NULL COMMENT '任务名称',
  `liqu_date` datetime NOT NULL COMMENT '对账日期',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `resp_code` varchar(20) DEFAULT NULL COMMENT '任务处理码',
  `resp_msg` varchar(500) DEFAULT NULL COMMENT '任务处理信息',
  `route_code` varchar(40) DEFAULT NULL COMMENT '汇路编码',
  `deal_status` varchar(2) NOT NULL COMMENT '处理状态 00：失败 01：成功',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='20.	平台对账流水表';

-- ----------------------------
-- Table structure for ppms_liqu_step_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_liqu_step_info`;
CREATE TABLE `ppms_liqu_step_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `clear_date` datetime NOT NULL COMMENT '清算日期',
  `route_code` varchar(40) DEFAULT NULL COMMENT '汇路编码',
  `step_type` varchar(20) NOT NULL COMMENT '对账步骤',
  `total_flag` varchar(2) NOT NULL COMMENT '是否为汇总对账',
  `pre_liqu_code` varchar(40) DEFAULT NULL COMMENT '前置任务编码',
  `is_repeat` varchar(2) NOT NULL COMMENT '是否可重复 0：不可重复 1：可重复',
  `deal_status` varchar(2) NOT NULL COMMENT '处理状态',
  `deal_msg` varchar(500) DEFAULT NULL COMMENT '处理说明',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `run_service` varchar(50) DEFAULT NULL COMMENT '处理服务',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='8.	平台对账步骤表';

-- ----------------------------
-- Table structure for ppms_liqu_trans_statis
-- ----------------------------
DROP TABLE IF EXISTS `ppms_liqu_trans_statis`;
CREATE TABLE `ppms_liqu_trans_statis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `clear_date` datetime NOT NULL COMMENT '对账日期',
  `route_code` varchar(20) NOT NULL COMMENT '汇路编码',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户编码',
  `succ_count` bigint(20) NOT NULL COMMENT '成功总笔数',
  `succ_amount` decimal(15,2) NOT NULL COMMENT '成功总金额',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `clear_type` varchar(3) NOT NULL COMMENT '对账类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='25.	汇路交易对账汇总表';

-- ----------------------------
-- Table structure for ppms_merchant_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_merchant_info`;
CREATE TABLE `ppms_merchant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `merchant_code` varchar(40) NOT NULL COMMENT '商户号',
  `merchant_name` varchar(100) NOT NULL COMMENT '商户名',
  `route_code` varchar(40) NOT NULL COMMENT '汇路编码',
  `pub_key_file` varchar(100) DEFAULT NULL COMMENT '公钥文件id',
  `pub_key` varchar(1000) DEFAULT NULL COMMENT '公钥字符编码',
  `private_key_file` varchar(100) DEFAULT NULL COMMENT '私钥文件id',
  `private_key` varchar(1000) DEFAULT NULL COMMENT '私钥文件编码',
  `um_group_code` varchar(40) NOT NULL COMMENT 'UM机构编码',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `public_key_index` varchar(2) DEFAULT NULL COMMENT '商户索引',
  `identity_no` varchar(40) DEFAULT NULL COMMENT '会员标识号',
  `terminal_id` varchar(40) DEFAULT NULL COMMENT '终端号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_merchant_route` (`merchant_code`,`route_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='6.	商户基本信息表';

-- ----------------------------
-- Table structure for ppms_payment_batch
-- ----------------------------
DROP TABLE IF EXISTS `ppms_payment_batch`;
CREATE TABLE `ppms_payment_batch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `trans_info_code` varchar(40) NOT NULL COMMENT '交易主表code',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型 01:大额代收',
  `req_file_name` varchar(60) DEFAULT NULL COMMENT '批次文件名称',
  `req_file_id` varchar(40) DEFAULT NULL COMMENT '批次文件编码',
  `resp_file_name` varchar(60) DEFAULT NULL COMMENT '回盘文件名称',
  `resp_file_id` varchar(40) DEFAULT NULL COMMENT '回盘文件id',
  `resp_time` datetime DEFAULT NULL COMMENT '回盘时间',
  `resp_count` bigint(20) DEFAULT NULL COMMENT '回盘次数',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `total_count` bigint(20) NOT NULL COMMENT '交易总笔数',
  `total_amount` decimal(15,2) NOT NULL COMMENT '交易总金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8 COMMENT='22.	代付批量主表';

-- ----------------------------
-- Table structure for ppms_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_payment_info`;
CREATE TABLE `ppms_payment_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `trans_info_code` varchar(40) NOT NULL COMMENT '交易主表code',
  `detail_serno` varchar(40) DEFAULT NULL COMMENT '明细流水号 批量专用',
  `route_type` varchar(2) DEFAULT NULL COMMENT '路由类型 0：平台路由 1：指定路由',
  `trans_serno` varchar(40) DEFAULT NULL COMMENT '交易流水号',
  `route_serno` varchar(40) DEFAULT NULL COMMENT '汇路流水号',
  `trans_date` datetime DEFAULT NULL COMMENT '汇路交易日期',
  `trans_time` varchar(6) DEFAULT NULL COMMENT '汇路交易时间',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型 01:大额代收',
  `payee_card_no` varchar(20) NOT NULL COMMENT '收款人银行卡号',
  `payee_card_type` varchar(2) DEFAULT NULL COMMENT '收款人银行卡类型 C:贷记卡 D:借记卡',
  `payee_card_mobile` varchar(20) NOT NULL COMMENT '收款人银行卡手机',
  `payee_name` varchar(64) NOT NULL COMMENT '收款人姓名',
  `payee_cert_type` varchar(3) NOT NULL COMMENT '收款人证件类型',
  `payee_cer_no` varchar(20) NOT NULL COMMENT '收款人证件号码',
  `payee_exp_year` varchar(2) DEFAULT NULL COMMENT '收款银行卡过期时间（年）',
  `payee_exp_month` varchar(2) DEFAULT NULL COMMENT '收款银行卡过期时间（月）',
  `payee_cvv` varchar(4) DEFAULT NULL COMMENT '收款银行卡校验值',
  `trans_status` varchar(3) NOT NULL COMMENT '交易状态 00：处理失败 01：处理成功 02：处理中',
  `step_status` varchar(3) NOT NULL COMMENT '业务步骤',
  `check_flag` varchar(2) NOT NULL COMMENT '对账标识',
  `route_ret_code` varchar(50) DEFAULT NULL COMMENT '汇路相应码',
  `route_ret_msg` varchar(500) DEFAULT NULL COMMENT '汇路相应信息',
  `route_trans_result` varchar(20) DEFAULT NULL COMMENT '汇路交易结果',
  `resp_code` varchar(20) DEFAULT NULL COMMENT '平台响应码',
  `resp_msg` varchar(500) DEFAULT NULL COMMENT '平台相应信息',
  `estimate_fee` decimal(15,2) DEFAULT NULL COMMENT '预估手续费',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `real_fee` decimal(15,2) DEFAULT NULL COMMENT '实际手续费',
  `trans_amount` decimal(15,2) NOT NULL COMMENT '交易金额',
  `real_trans_amount` decimal(15,2) DEFAULT NULL COMMENT '汇路实际交易金额',
  `route_list` varchar(200) DEFAULT NULL COMMENT '路由编码',
  `route_num` varchar(3) DEFAULT NULL COMMENT '路由序号',
  `custom_type` varchar(3) NOT NULL COMMENT '客户类型',
  `batch_no` varchar(40) DEFAULT NULL COMMENT '批处理标识号',
  `sub_acct_flag` varchar(2) DEFAULT NULL COMMENT '分账标识 0：不分账 1：分账',
  `pc_flag` varchar(2) DEFAULT NULL COMMENT '0：对私 1：对公',
  `chl_serno` varchar(40) NOT NULL COMMENT '上游系统流水号',
  `batch_code` varchar(40) DEFAULT NULL COMMENT '批量表code',
  `payer_group_id` varchar(40) DEFAULT NULL COMMENT '付款UM机构编码',
  `payee_bank` varchar(14) DEFAULT NULL COMMENT '收款人开户行号',
  `payee_bank_name` varchar(50) DEFAULT NULL COMMENT '收款人开户行名称',
  `payee_sub_bank` varchar(14) DEFAULT NULL COMMENT '收款人开户分支行号',
  `payee_sub_bank_name` varchar(50) DEFAULT NULL COMMENT '收款人开户分支行名称',
  `prov` varchar(20) DEFAULT NULL COMMENT '省',
  `city` varchar(40) DEFAULT NULL COMMENT '城市',
  `issuer_code` varchar(120) DEFAULT NULL COMMENT '发卡行机构代码',
  `chl_route_list` varchar(200) DEFAULT NULL COMMENT '渠道路由列表',
  `route_code` varchar(40) DEFAULT NULL COMMENT '当前路由编码',
  `purpose` varchar(100) DEFAULT NULL COMMENT '付款备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='19.	代付交易信息表';

-- ----------------------------
-- Table structure for ppms_rate_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_rate_info`;
CREATE TABLE `ppms_rate_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `route_code` varchar(40) NOT NULL COMMENT '汇路编码',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型',
  `charge_type` varchar(2) NOT NULL COMMENT '收费类型 01：金额收取  02：笔数收取',
  `stair_type` varchar(2) NOT NULL COMMENT '阶梯类型 01：笔数 02：金额 03：固定费率',
  `fee_max_amount` decimal(15,2) NOT NULL COMMENT '手续费最大值',
  `fee_min_amount` decimal(15,2) NOT NULL COMMENT '手续费最小值',
  `fee_rate` decimal(15,2) DEFAULT NULL COMMENT '固定费率',
  `total_count_type` varchar(2) NOT NULL COMMENT '累计计算方式 01：汇路累计 02：商户累计',
  `total_type` varchar(2) NOT NULL COMMENT '累计类型 01：按日 02：按月 03：累计',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='15.	费率信息表';

-- ----------------------------
-- Table structure for ppms_rate_stair
-- ----------------------------
DROP TABLE IF EXISTS `ppms_rate_stair`;
CREATE TABLE `ppms_rate_stair` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `rate_code` varchar(40) NOT NULL COMMENT '费率信息code',
  `amount_min` decimal(15,2) DEFAULT NULL COMMENT '金额下限',
  `amount_max` decimal(15,2) DEFAULT NULL COMMENT '金额上限',
  `count_min` bigint(20) DEFAULT NULL COMMENT '笔数下限',
  `count_max` bigint(20) DEFAULT NULL COMMENT '笔数上限',
  `fee_type` varchar(2) NOT NULL COMMENT '手续费类型 00：费率 01：金额',
  `rate` bigint(20) DEFAULT NULL COMMENT '费率',
  `amount` decimal(15,2) DEFAULT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='16.	费率阶梯表';

-- ----------------------------
-- Table structure for ppms_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_refund_info`;
CREATE TABLE `ppms_refund_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `sys_serno` varchar(40) NOT NULL COMMENT '平台系统流水号',
  `trans_serno` varchar(40) DEFAULT NULL COMMENT '交易流水号',
  `route_serno` varchar(40) DEFAULT NULL COMMENT '汇路流水号',
  `trans_date` datetime DEFAULT NULL COMMENT '汇路交易日期',
  `trans_time` varchar(6) DEFAULT NULL COMMENT '汇路交易时间',
  `trans_type` varchar(2) DEFAULT NULL COMMENT '交易类型 01:大额代收',
  `refund_sys_serno` varchar(40) NOT NULL COMMENT '退款系统流水号',
  `refund_trans_serno` varchar(40) NOT NULL COMMENT '原始商户订单号',
  `refund_amount` decimal(15,2) NOT NULL COMMENT '退款金额',
  `route_ret_code` varchar(50) DEFAULT NULL COMMENT '汇路相应码',
  `route_ret_msg` varchar(500) DEFAULT NULL COMMENT '汇路相应信息',
  `route_trans_result` varchar(20) DEFAULT NULL COMMENT '汇路交易结果',
  `resp_code` varchar(20) DEFAULT NULL COMMENT '平台响应码',
  `resp_msg` varchar(500) DEFAULT NULL COMMENT '平台相应信息',
  `check_flag` varchar(2) DEFAULT NULL COMMENT '对账标识',
  `estimate_fee` decimal(15,2) DEFAULT NULL COMMENT '预估手续费',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `trans_status` varchar(3) NOT NULL COMMENT '交易状态 00：处理失败 01：处理成功 02：处理中',
  `step_status` varchar(3) NOT NULL COMMENT '业务步骤',
  `sys_status` varchar(2) NOT NULL COMMENT '业务状态',
  `source_fee` decimal(15,2) DEFAULT NULL COMMENT '上游手续费',
  `real_fee` decimal(15,2) DEFAULT NULL COMMENT '实际手续费',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='20.	退款交易信息表';

-- ----------------------------
-- Table structure for ppms_request_log
-- ----------------------------
DROP TABLE IF EXISTS `ppms_request_log`;
CREATE TABLE `ppms_request_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `request_msg` mediumtext COMMENT '请求报文',
  `response_msg` mediumtext COMMENT '响应报文',
  `return_code` varchar(5) DEFAULT NULL COMMENT '响应码',
  `return_message` varchar(256) DEFAULT NULL COMMENT '响应信息',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=372 DEFAULT CHARSET=utf8 COMMENT='17.	请求日志表';

-- ----------------------------
-- Table structure for ppms_route_bank_day_total
-- ----------------------------
DROP TABLE IF EXISTS `ppms_route_bank_day_total`;
CREATE TABLE `ppms_route_bank_day_total` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `route_code` varchar(40) NOT NULL COMMENT '路由编码',
  `issuer_code` varchar(120) DEFAULT NULL,
  `work_date` datetime NOT NULL COMMENT '汇路交易日期',
  `total_amount_day` decimal(15,2) NOT NULL COMMENT '本日累计金额',
  `total_count_day` bigint(20) NOT NULL COMMENT '本日累计笔数',
  `total_amount_month` decimal(15,2) NOT NULL COMMENT '本月累计金额',
  `total_count_ month` bigint(20) NOT NULL COMMENT '本月累计笔数',
  `total_amount_year` decimal(15,2) NOT NULL COMMENT '本年累计金额',
  `total_count_year` bigint(20) NOT NULL COMMENT '本年累计笔数',
  `total_amount` decimal(15,2) NOT NULL COMMENT '总累计金额',
  `total_count` bigint(20) NOT NULL COMMENT '总累计笔数',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `total_type` varchar(2) DEFAULT NULL COMMENT '累计业务类型 01：代收 02：代付',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='29.	汇路银行日累计记录表';

-- ----------------------------
-- Table structure for ppms_route_card_bin
-- ----------------------------
DROP TABLE IF EXISTS `ppms_route_card_bin`;
CREATE TABLE `ppms_route_card_bin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `route_code` varchar(10) NOT NULL COMMENT '汇路编码',
  `issuer_code` varchar(120) NOT NULL COMMENT '发卡行机构代码',
  `single_min_amount` decimal(15,2) DEFAULT NULL COMMENT '单笔金额下限',
  `single_max_amount` decimal(15,2) DEFAULT NULL COMMENT '单笔金额上限',
  `day_max_amount` decimal(15,2) DEFAULT NULL COMMENT '单日金额上限',
  `month_max_amount` decimal(15,2) DEFAULT NULL COMMENT '单月金额上限',
  `day_max_count` bigint(20) DEFAULT NULL COMMENT '单日笔数上限',
  `month_max_count` bigint(20) DEFAULT NULL COMMENT '单月笔数上限',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型 01:批量代收',
  `pc_flag` varchar(2) NOT NULL COMMENT '对公对私标志 0：对私 1：对公',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `route_bank_code` varchar(50) DEFAULT NULL COMMENT '汇路银行标识',
  `card_type` varchar(6) NOT NULL,
  `route_bank_name` varchar(100) DEFAULT NULL COMMENT '汇路银行名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_route_issuer_trans_pc` (`route_code`,`issuer_code`,`trans_type`,`pc_flag`,`card_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=572 DEFAULT CHARSET=utf8 COMMENT='3.	汇路卡bin关系信息表';

-- ----------------------------
-- Table structure for ppms_route_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_route_info`;
CREATE TABLE `ppms_route_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL COMMENT '业务主键',
  `english_abbr` varchar(40) NOT NULL COMMENT '汇路英文简称',
  `route_name` varchar(32) NOT NULL COMMENT '汇路名称',
  `pub_key_file` varchar(100) DEFAULT NULL COMMENT '公钥文件地址',
  `pub_key` varchar(1000) DEFAULT NULL COMMENT '公钥字符编码',
  `batch_flag` varchar(2) NOT NULL COMMENT '是否支持批量标志 1：是0：否',
  `cut_time` varchar(6) NOT NULL COMMENT '日切时间 HHMMSS',
  `sign_check_flag` varchar(2) NOT NULL COMMENT '是否签约检查标识 1：是 0：否',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `updator` varchar(255) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_english_abbr` (`english_abbr`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='1.	汇路基本信息表';

-- ----------------------------
-- Table structure for ppms_route_total
-- ----------------------------
DROP TABLE IF EXISTS `ppms_route_total`;
CREATE TABLE `ppms_route_total` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `route_code` varchar(40) NOT NULL COMMENT '路由编码',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户编码',
  `work_date` datetime NOT NULL COMMENT '汇路交易日期',
  `total_amount_day` decimal(15,2) NOT NULL COMMENT '本日累计金额',
  `total_count_day` bigint(20) NOT NULL COMMENT '本日累计笔数',
  `total_amount_month` decimal(15,2) NOT NULL COMMENT '本月累计金额',
  `total_count_month` bigint(20) NOT NULL COMMENT '本月累计笔数',
  `total_amount_year` decimal(15,2) NOT NULL COMMENT '本年累计金额',
  `total_count_year` bigint(20) NOT NULL COMMENT '本年累计笔数',
  `total_amount` decimal(15,2) NOT NULL COMMENT '总累计金额',
  `total_count` bigint(20) DEFAULT NULL COMMENT '总累计笔数',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `total_type` varchar(2) DEFAULT NULL COMMENT '累计业务类型 01：代收 02：代付',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='27.	汇路累计记录表';

-- ----------------------------
-- Table structure for ppms_route_total_log
-- ----------------------------
DROP TABLE IF EXISTS `ppms_route_total_log`;
CREATE TABLE `ppms_route_total_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `route_code` varchar(40) NOT NULL COMMENT '路由编码',
  `merchant_code` varchar(40) NOT NULL COMMENT '商户编码',
  `work_date` datetime NOT NULL COMMENT '汇路交易日期',
  `total_amount_day` decimal(15,2) NOT NULL COMMENT '本日累计金额',
  `total_count_day` bigint(20) NOT NULL COMMENT '本日累计笔数',
  `total_amount_month` decimal(15,2) NOT NULL COMMENT '本月累计金额',
  `total_count_month` bigint(20) NOT NULL COMMENT '本月累计笔数',
  `total_amount_year` decimal(15,2) NOT NULL COMMENT '本年累计金额',
  `total_count_year` bigint(20) NOT NULL COMMENT '本年累计笔数',
  `total_amount` decimal(15,2) NOT NULL COMMENT '总累计金额',
  `total_count` bigint(20) DEFAULT NULL COMMENT '总累计笔数',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `total_type` varchar(2) DEFAULT NULL COMMENT '累计业务类型 01：代收 02：代付',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='27.	汇路累计记录表';

-- ----------------------------
-- Table structure for ppms_route_trans_quota
-- ----------------------------
DROP TABLE IF EXISTS `ppms_route_trans_quota`;
CREATE TABLE `ppms_route_trans_quota` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `route_code` varchar(40) NOT NULL COMMENT '汇路编码',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型 01：大额代收',
  `card_type` varchar(2) NOT NULL COMMENT '银行卡类型 C:贷记卡 D:借记卡',
  `pc_flag` varchar(2) NOT NULL COMMENT '对公对私标志 0：对私 1：对公',
  `single_min_amount` decimal(15,2) DEFAULT NULL COMMENT '单笔金额下限',
  `single_max_amount` decimal(15,2) DEFAULT NULL COMMENT '单笔金额上限',
  `day_max_amount` decimal(15,2) DEFAULT NULL COMMENT '单日金额上限',
  `month_max_amount` decimal(15,2) DEFAULT NULL COMMENT '单月金额上限',
  `day_max_count` bigint(20) DEFAULT NULL COMMENT '单日笔数上限',
  `month_max_count` varchar(20) DEFAULT NULL COMMENT '单月笔数上限',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_route_trans_card_pc` (`route_code`,`trans_type`,`card_type`,`pc_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='14.	汇路交易限额配置表';

-- ----------------------------
-- Table structure for ppms_route_weight
-- ----------------------------
DROP TABLE IF EXISTS `ppms_route_weight`;
CREATE TABLE `ppms_route_weight` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `rule_name` varchar(100) NOT NULL COMMENT '规则名称 手续费 客户到账时效',
  `rule_weight` varchar(2) NOT NULL COMMENT '规则权重 1-10个等级',
  `rule_method` varchar(100) NOT NULL COMMENT '规则方法',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='12.	汇路权重信息表';

-- ----------------------------
-- Table structure for ppms_sub_account
-- ----------------------------
DROP TABLE IF EXISTS `ppms_sub_account`;
CREATE TABLE `ppms_sub_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `sys_serno` varchar(40) NOT NULL COMMENT '平台系统流水号',
  `trans_amount` decimal(15,2) NOT NULL COMMENT '交易金额',
  `sub_acc_amount` decimal(15,2) NOT NULL COMMENT '分账金额',
  `sub_acc_status` varchar(2) NOT NULL COMMENT '交易状态 00：处理失败 01：处理成功 02：处理中',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='25.	分账信息表';

-- ----------------------------
-- Table structure for ppms_suningpay_trans_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_suningpay_trans_info`;
CREATE TABLE `ppms_suningpay_trans_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `sn_trans_type` varchar(20) DEFAULT NULL COMMENT '类型',
  `sn_acct_serno` varchar(40) DEFAULT NULL COMMENT '账务流水号',
  `sn_trans_serno` varchar(40) DEFAULT NULL COMMENT '交易流水号',
  `sn_goods_order_no` varchar(40) DEFAULT NULL COMMENT '商品订单号',
  `sn_order_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `sn_opposite` varchar(40) DEFAULT NULL COMMENT '对方',
  `sn_order_no` varchar(40) DEFAULT NULL COMMENT '订单号',
  `sn_order_name` varchar(40) DEFAULT NULL COMMENT '订单名称',
  `sn_receipts_time` datetime DEFAULT NULL COMMENT '收支时间',
  `sn_receipts_amount` decimal(15,2) DEFAULT NULL COMMENT '收入金额',
  `sn_expense_amount` decimal(15,2) DEFAULT NULL COMMENT '支出金额',
  `sn_aount_balance` decimal(15,2) DEFAULT NULL COMMENT '账户余额',
  `sn_pay_chl` varchar(40) DEFAULT NULL COMMENT '支付渠道',
  `sn_remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `sn_merhant_order_no` varchar(40) DEFAULT NULL COMMENT '商户订单ID',
  `check_flag` varchar(2) DEFAULT NULL COMMENT '对账标识',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='21.	易付宝交易信息表';

-- ----------------------------
-- Table structure for ppms_suningpay_trans_info_log
-- ----------------------------
DROP TABLE IF EXISTS `ppms_suningpay_trans_info_log`;
CREATE TABLE `ppms_suningpay_trans_info_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `sn_trans_type` varchar(20) DEFAULT NULL COMMENT '类型',
  `sn_acct_serno` varchar(40) DEFAULT NULL COMMENT '账务流水号',
  `sn_trans_serno` varchar(40) DEFAULT NULL COMMENT '交易流水号',
  `sn_goods_order_no` varchar(40) DEFAULT NULL COMMENT '商品订单号',
  `sn_order_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `sn_opposite` varchar(40) DEFAULT NULL COMMENT '对方',
  `sn_order_no` varchar(40) DEFAULT NULL COMMENT '订单号',
  `sn_order_name` varchar(40) DEFAULT NULL COMMENT '订单名称',
  `sn_receipts_time` datetime DEFAULT NULL COMMENT '收支时间',
  `sn_receipts_amount` decimal(15,2) DEFAULT NULL COMMENT '收入金额',
  `sn_expense_amount` decimal(15,2) DEFAULT NULL COMMENT '支出金额',
  `sn_aount_balance` decimal(15,2) DEFAULT NULL COMMENT '账户余额',
  `sn_pay_chl` varchar(40) DEFAULT NULL COMMENT '支付渠道',
  `sn_remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `sn_merhant_order_no` varchar(40) DEFAULT NULL COMMENT '商户订单ID',
  `check_flag` varchar(2) DEFAULT NULL COMMENT '对账标识',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='21.	易付宝交易信息表';

-- ----------------------------
-- Table structure for ppms_sysparam_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_sysparam_info`;
CREATE TABLE `ppms_sysparam_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `item_id` varchar(60) NOT NULL COMMENT '参数代码',
  `item_name` varchar(120) NOT NULL COMMENT '参数名称',
  `item_cval` varchar(256) DEFAULT NULL COMMENT '参数代码字符值',
  `item_ival` bigint(20) DEFAULT NULL COMMENT '参数代码整型值',
  `item_date_time` datetime DEFAULT NULL COMMENT '参数代码日期值',
  `item_desc` varchar(256) DEFAULT NULL COMMENT '参数维护描述',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_item_id` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='13.	系统参数表';

-- ----------------------------
-- Table structure for ppms_system_route
-- ----------------------------
DROP TABLE IF EXISTS `ppms_system_route`;
CREATE TABLE `ppms_system_route` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `source_system` varchar(40) NOT NULL COMMENT '上游系统编码',
  `route_code` varchar(40) NOT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='10.	系统路由配置表';

-- ----------------------------
-- Table structure for ppms_trans_info
-- ----------------------------
DROP TABLE IF EXISTS `ppms_trans_info`;
CREATE TABLE `ppms_trans_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(40) NOT NULL,
  `chl_serno` varchar(40) NOT NULL COMMENT '上游系统流水号',
  `req_time` datetime(6) NOT NULL COMMENT '请求时间',
  `work_date` datetime NOT NULL COMMENT '工作日期',
  `sys_time` datetime NOT NULL COMMENT '系统时间',
  `source_system` varchar(40) NOT NULL COMMENT '上游系统编码',
  `trans_code` varchar(40) NOT NULL COMMENT '交易码',
  `trans_type` varchar(2) NOT NULL COMMENT '交易类型 01:大额代收',
  `request_json` mediumtext NOT NULL COMMENT '请求报文',
  `status` varchar(2) NOT NULL COMMENT '状态 1：可用 0：不可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_serno_system` (`chl_serno`,`source_system`)
) ENGINE=InnoDB AUTO_INCREMENT=276 DEFAULT CHARSET=utf8 COMMENT='17.	交易流水信息主表';

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





DROP TABLE IF EXISTS `qrtz_blob_triggres`;
DROP TABLE IF EXISTS `qrtz_calendars`;
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
DROP TABLE IF EXISTS `qrtz_locks`;
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
DROP TABLE IF EXISTS `qrtz_triggers`;
DROP TABLE IF EXISTS `qrtz_job_details`;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

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
-- 定时任务系统表
-- ----------------------------
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
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggres_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- ----------------------------
-- 定时任务系统表
-- ----------------------------

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
-- 定时任务系统表
-- ----------------------------

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
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

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
-- 定时任务系统表
-- ----------------------------

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



























