use plms;
insert into plms_company (
	code,
	company_name,
	company_type,
	status,
	abbr_name,
	bms_code
)value (
	'67854214-971a-11e7-9978-1866dae83f00','海尔小贷','03','1','海尔小贷','A00047'
);

INSERT INTO plms_fund_side_product (
	CODE,
	product_name,
	is_principal_instead,
	is_interest_instead,
	company_code,
	bms_code,
	STATUS
)
VALUES
	(
		UUID(),
		'P4',
		'0',
		'0',
		'67854214-971a-11e7-9978-1866dae83f00',
		'A00047',
		'1'
	);

INSERT INTO `plms_material_type` VALUES ('1', '01', '身份证', '2017-08-11 19:18:09', '2017-09-01 22:44:30', '1');
INSERT INTO `plms_material_type` VALUES ('2', '02', '户口本', '2017-08-11 19:18:50', '2017-09-01 22:44:16', '1');
INSERT INTO `plms_material_type` VALUES ('3', '03', '婚姻证明', '2017-08-11 19:19:00', '2017-09-01 22:43:54', '1');
INSERT INTO `plms_material_type` VALUES ('4', '04', '征信报告', '2017-09-01 22:43:40', '2017-09-01 22:43:40', '1');
INSERT INTO `plms_material_type` VALUES ('5', '05', '抵押房产证', '2017-09-01 22:44:41', '2017-09-12 01:58:56', '1');
INSERT INTO `plms_material_type` VALUES ('6', '06', '产调', '2017-09-01 22:44:52', '2017-09-01 22:44:52', '1');
INSERT INTO `plms_material_type` VALUES ('7', '07', '评估单', '2017-09-01 22:45:07', '2017-09-12 01:58:59', '1');
INSERT INTO `plms_material_type` VALUES ('8', '08', '银行流水', '2017-09-01 22:45:23', '2017-09-01 22:45:23', '1');
INSERT INTO `plms_material_type` VALUES ('9', '09', '看房照片', '2017-09-01 22:45:37', '2017-09-12 01:59:40', '1');
INSERT INTO `plms_material_type` VALUES ('10', '10', '备用房产证', '2017-09-01 22:45:49', '2017-09-12 02:00:16', '1');
INSERT INTO `plms_material_type` VALUES ('11', '11', '企业材料', '2017-09-01 22:45:59', '2017-09-12 02:00:25', '1');
INSERT INTO `plms_material_type` VALUES ('12', '12', '风险信息查询', '2017-09-01 22:46:10', '2017-09-12 02:00:33', '1');
INSERT INTO `plms_material_type` VALUES ('13', '13', '借款申请书及外访调查报告', '2017-09-01 22:46:24', '2017-09-12 02:00:39', '1');
INSERT INTO `plms_material_type` VALUES ('14', '14', '放款银行卡', '2017-09-01 22:46:35', '2017-09-01 22:46:35', '1');
INSERT INTO `plms_material_type` VALUES ('15', '15', '其他', '2017-09-01 22:46:53', '2017-09-01 22:46:53', '1');

-- ----------------------------
-- Records of plms_sysparam_info
-- ----------------------------
INSERT INTO `plms_sysparam_info` VALUES ('1', UUID(), 'plms', '贷后系统', 'ACCOUNT_LOCK', '账务类操所锁定标识', 'N', '0', '2017-09-13 21:23:24', '资金入账、扣款、逾期', '', '', '', '2017-08-09 22:54:08', '2017-09-13 21:23:24');
INSERT INTO `plms_sysparam_info` VALUES ('2', UUID(), 'plms', '贷后系统', 'PAY_SCHEDULE_JOB', '扣款操作定时任务', 'N', '0', '2017-09-13 17:15:07', '扣款', '', '', '', '2017-08-09 18:33:28', '2017-09-04 17:46:43');
INSERT INTO `plms_sysparam_info` VALUES ('3', UUID(), 'plms', '贷后系统', 'CALCULATE_OVERDUE_INTEREST_JOB', '计算逾期操作定时任务', null, '0', '2017-09-13 00:00:00', '逾期', '', '', '', '2017-08-09 18:33:35', '2017-09-01 23:50:14');

