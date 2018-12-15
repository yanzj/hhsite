use lbs;
DROP TABLE IF EXISTS `bms_spare_house_temp`;
DROP TABLE IF EXISTS `bms_apply_material_file_temp`;
DROP TABLE IF EXISTS `bms_file_temp`;
DROP TABLE IF EXISTS `bms_collateral_temp`;
DROP TABLE IF EXISTS `bms_apply_material_temp`;
DROP TABLE IF EXISTS `bms_loan_apply_temp`;
DROP TABLE IF EXISTS `bms_person_temp`;

CREATE TABLE `bms_person_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `temp_code` varchar(36) NOT NULL COMMENT '编号',
  `apply_temp_code` varchar(36) DEFAULT NULL COMMENT '进件申请编号',
  `person_name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `id_no` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `mobile_phone` varchar(20) DEFAULT NULL COMMENT '移动电话',
  `marital_status_code` varchar(20) DEFAULT NULL COMMENT '婚姻状况',
  `company` varchar(20) DEFAULT NULL COMMENT '工作单位',
  `job_position` varchar(20) DEFAULT NULL COMMENT '职位',
  `annual_income` decimal(8,2) DEFAULT NULL COMMENT '年收入',
  `home_address` varchar(20) DEFAULT NULL COMMENT '家庭地址',
  `person_type` varchar(2) DEFAULT NULL COMMENT '客户类型(01:申请人 02:借款人)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `temp_code` (`temp_code`),
  KEY `temp_code_2` (`temp_code`),
  KEY `marital_status_code` (`marital_status_code`),
  CONSTRAINT `bms_person_temp_ibfk_1` FOREIGN KEY (`marital_status_code`) REFERENCES `bms_marital_status` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=633 DEFAULT CHARSET=utf8 COMMENT='客户临时表';


CREATE TABLE `bms_loan_apply_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `temp_code` varchar(36) NOT NULL COMMENT '编号',
  `loan_amount` decimal(8,2) DEFAULT NULL COMMENT '申请金额',
  `loan_period_code` varchar(2) DEFAULT NULL COMMENT '借款期限',
  `apply_customer_no` varchar(36) DEFAULT NULL COMMENT '申请人',
  `family_asset` varchar(100) DEFAULT NULL COMMENT '家庭主要资产',
  `family_debt` varchar(100) DEFAULT NULL COMMENT '家庭主要负债',
  `family_inome_source` varchar(100) DEFAULT NULL COMMENT '家庭主要收入来源',
  `survey_desc` varchar(100) DEFAULT NULL COMMENT '实地调研情况',
  `loan_purpose` varchar(100) DEFAULT NULL COMMENT '借款用途',
  `repayment_source` varchar(100) DEFAULT NULL COMMENT '还款来源',
  `distributor_code` varchar(8) DEFAULT NULL COMMENT '渠道',
  `apply_code` varchar(36) DEFAULT NULL,
  `product_code` varchar(36) DEFAULT NULL COMMENT '产品编码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `temp_code` (`temp_code`),
  KEY `temp_code_2` (`temp_code`),
  KEY `loan_period_code` (`loan_period_code`),
  KEY `apply_customer_no` (`apply_customer_no`),
  KEY `distributor_code` (`distributor_code`),
  KEY `apply_code` (`apply_code`),
  KEY `product_code` (`product_code`),
  CONSTRAINT `bms_loan_apply_temp_ibfk_1` FOREIGN KEY (`loan_period_code`) REFERENCES `bms_loan_period` (`code`),
  CONSTRAINT `bms_loan_apply_temp_ibfk_2` FOREIGN KEY (`apply_customer_no`) REFERENCES `bms_person_temp` (`temp_code`),
  CONSTRAINT `bms_loan_apply_temp_ibfk_3` FOREIGN KEY (`distributor_code`) REFERENCES `bms_distributor` (`code`),
  CONSTRAINT `bms_loan_apply_temp_ibfk_4` FOREIGN KEY (`apply_code`) REFERENCES `bms_loan_apply` (`code`),
  CONSTRAINT `bms_loan_apply_temp_ibfk_5` FOREIGN KEY (`product_code`) REFERENCES `bms_product` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8 COMMENT='进件申请临时表';


CREATE TABLE `bms_apply_material_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `temp_code` varchar(36) NOT NULL COMMENT '编号',
  `apply_temp_code` varchar(36) NOT NULL COMMENT '进件申请编号',
  `material_type` varchar(2) NOT NULL COMMENT '材料类型',
  `status` varchar(2) DEFAULT NULL COMMENT '材料状态 01:无;02:后补;03:已上传文件',
  `file_number` int(3) DEFAULT NULL COMMENT '上传文件数',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `temp_code` (`temp_code`),
  KEY `temp_code_2` (`temp_code`),
  KEY `apply_temp_code` (`apply_temp_code`),
  KEY `material_type` (`material_type`),
  CONSTRAINT `bms_apply_material_temp_ibfk_1` FOREIGN KEY (`apply_temp_code`) REFERENCES `bms_loan_apply_temp` (`temp_code`),
  CONSTRAINT `bms_apply_material_temp_ibfk_2` FOREIGN KEY (`material_type`) REFERENCES `bms_apply_material_type` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=198 DEFAULT CHARSET=utf8 COMMENT='进件材料临时表';


CREATE TABLE `bms_collateral_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `temp_code` varchar(36) NOT NULL COMMENT '编号',
  `apply_temp_code` varchar(36) NOT NULL COMMENT '进件申请编号',
  `evaluation_price` decimal(8,2) DEFAULT NULL COMMENT '评估价',
  `house_type_code` varchar(2) DEFAULT NULL COMMENT '房屋类型',
  `mortgage_type` varchar(2) DEFAULT NULL COMMENT '抵押类型',
  `first_mortgage` varchar(2) DEFAULT NULL COMMENT '一抵余额类型 00: 最高额 01: 余额',
  `first_mortgage_balance` decimal(8,2) DEFAULT NULL COMMENT '一抵余额',
  `first_mortgage_amount` decimal(8,2) DEFAULT NULL COMMENT '一抵金额',
  `second_mortgage_amount` decimal(8,2) DEFAULT NULL COMMENT '二抵金额',
  `property_num` int(2) DEFAULT NULL COMMENT '产权人数',
  `adult_num` int(2) DEFAULT NULL COMMENT '成年人数',
  `aged_num` int(2) DEFAULT NULL COMMENT '老年人数',
  `minors_num` int(2) DEFAULT NULL COMMENT '未成年人数',
  `builded_time` varchar(20) DEFAULT NULL COMMENT '建成时间',
  `city_code` varchar(8) DEFAULT NULL COMMENT '城市',
  `district_code` varchar(8) DEFAULT NULL COMMENT '区县',
  `adress` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `residence_num` int(2) DEFAULT NULL COMMENT '户口人数',
  `residence_structure` varchar(100) DEFAULT NULL COMMENT '户口结构',
  `certificate_number` varchar(100) DEFAULT NULL COMMENT '产证编号',
  `minors_persent` varchar(20) DEFAULT NULL COMMENT '未成年人所占份额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `temp_code` (`temp_code`),
  KEY `temp_code_2` (`temp_code`),
  KEY `apply_temp_code` (`apply_temp_code`),
  KEY `house_type_code` (`house_type_code`),
  KEY `mortgage_type` (`mortgage_type`),
  KEY `city_code` (`city_code`),
  KEY `district_code` (`district_code`),
  CONSTRAINT `bms_collateral_temp_ibfk_1` FOREIGN KEY (`apply_temp_code`) REFERENCES `bms_loan_apply_temp` (`temp_code`),
  CONSTRAINT `bms_collateral_temp_ibfk_2` FOREIGN KEY (`house_type_code`) REFERENCES `bms_house_type` (`code`),
  CONSTRAINT `bms_collateral_temp_ibfk_3` FOREIGN KEY (`mortgage_type`) REFERENCES `bms_mortgage_type` (`code`),
  CONSTRAINT `bms_collateral_temp_ibfk_4` FOREIGN KEY (`city_code`) REFERENCES `bms_city` (`code`),
  CONSTRAINT `bms_collateral_temp_ibfk_5` FOREIGN KEY (`district_code`) REFERENCES `bms_district` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=335 DEFAULT CHARSET=utf8 COMMENT='抵押物信息临时表';


CREATE TABLE `bms_file_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `temp_code` varchar(36) NOT NULL COMMENT '编号',
  `file_name` varchar(100) NOT NULL COMMENT '文件名',
  `file_path` varchar(100) NOT NULL COMMENT '存储路径',
  `file_size` int(10) DEFAULT NULL,
  `file_suffix` varchar(20) DEFAULT NULL,
  `file_suffix_type` varchar(20) DEFAULT NULL,
  `original_file_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `temp_code` (`temp_code`),
  KEY `temp_code_2` (`temp_code`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COMMENT='文件临时表';

CREATE TABLE `bms_apply_material_file_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `temp_code` varchar(36) NOT NULL COMMENT '编号',
  `material_temp_code` varchar(36) NOT NULL COMMENT '进件材料编号',
  `temp_file_code` varchar(36) NOT NULL COMMENT '文件编号',
  `file_no` int(3) DEFAULT NULL COMMENT '文件序号(排序用)',
  `batch_no` varchar(36) DEFAULT NULL COMMENT '批次号',
  `upload_time` datetime DEFAULT NULL COMMENT '上载时间(以批次为准)',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名(用户定义)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `temp_code` (`temp_code`),
  KEY `temp_code_2` (`temp_code`),
  KEY `material_temp_code` (`material_temp_code`),
  KEY `temp_file_code` (`temp_file_code`),
  CONSTRAINT `bms_apply_material_file_temp_ibfk_1` FOREIGN KEY (`material_temp_code`) REFERENCES `bms_apply_material_temp` (`temp_code`),
  CONSTRAINT `bms_apply_material_file_temp_ibfk_2` FOREIGN KEY (`temp_file_code`) REFERENCES `bms_file_temp` (`temp_code`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='进件材料文件关联临时表';


CREATE TABLE `bms_spare_house_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `temp_code` varchar(36) NOT NULL COMMENT '编号',
  `apply_temp_code` varchar(36) NOT NULL COMMENT '进件申请编号',
  `property_person` varchar(100) DEFAULT NULL COMMENT '产权人',
  `adress` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `contain_relatives` tinyint(1) DEFAULT NULL COMMENT '是否包含直系亲属 1:是;0:否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `temp_code` (`temp_code`),
  KEY `temp_code_2` (`temp_code`),
  KEY `apply_temp_code` (`apply_temp_code`),
  CONSTRAINT `bms_spare_house_temp_ibfk_1` FOREIGN KEY (`apply_temp_code`) REFERENCES `bms_loan_apply_temp` (`temp_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='备用房信息临时表';