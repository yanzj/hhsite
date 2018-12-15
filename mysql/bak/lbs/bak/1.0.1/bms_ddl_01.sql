use lbs;

#清理结构，保证可重复执行
DROP TABLE IF EXISTS `bms_apply_material_file`;
DROP TABLE IF EXISTS `bms_apply_material`;
DROP TABLE IF EXISTS `bms_file`;
DROP TABLE IF EXISTS `bms_spare_house`;
DROP TABLE IF EXISTS `bms_collateral`;
DROP TABLE IF EXISTS `bms_loan_customer`;
DROP TABLE IF EXISTS `bms_loan_apply`;
DROP TABLE IF EXISTS `bms_person`;
DROP TABLE IF EXISTS `bms_loan_operate_history`;
DROP TABLE IF EXISTS `bms_loan_main`;
DROP TABLE IF EXISTS `bms_login_num`;
DROP TABLE IF EXISTS `bms_user`;
DROP TABLE IF EXISTS `bms_distributor`;
DROP TABLE IF EXISTS `bms_department`;
DROP TABLE IF EXISTS `bms_company`;
DROP TABLE IF EXISTS `bms_district`;
DROP TABLE IF EXISTS `bms_city`;
DROP TABLE IF EXISTS `bms_apply_material_type`;
DROP TABLE IF EXISTS `bms_loan_operate_type`;
DROP TABLE IF EXISTS `bms_loan_main_status`;
DROP TABLE IF EXISTS `bms_mortgage_type`;
DROP TABLE IF EXISTS `bms_house_type`;
DROP TABLE IF EXISTS `bms_marital_status`;
DROP TABLE IF EXISTS `bms_loan_period`;
DROP TABLE IF EXISTS `bms_apply_material_type`;
DROP TABLE IF EXISTS `bms_loan_operate_type`;
DROP TABLE IF EXISTS `bms_loan_main_status`;
DROP TABLE IF EXISTS `bms_mortgage_type`;
DROP TABLE IF EXISTS `bms_house_type`;
DROP TABLE IF EXISTS `bms_marital_status`;
DROP TABLE IF EXISTS `bms_loan_period`;

#创建借款期限表
CREATE TABLE `bms_loan_period` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(12) NOT NULL unique comment'编号',
  `period` int(2) NOT NULL comment'简称',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_loan_period` COMMENT='借款期限表';

#婚姻状况表
CREATE TABLE `bms_marital_status` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(2) NOT NULL unique comment'编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(20) NOT NULL comment'全称',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_marital_status` COMMENT='婚姻状况表';

#房屋类型表
CREATE TABLE `bms_house_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(2) NOT NULL unique comment'编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(20) NOT NULL comment'全称',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_house_type` COMMENT='房屋类型表';

#抵押类型表
CREATE TABLE `bms_mortgage_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(2) NOT NULL unique comment'编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(20) NOT NULL comment'全称',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_mortgage_type` COMMENT='抵押类型表';

#业务状态表
CREATE TABLE `bms_loan_main_status` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(2) NOT NULL unique comment'编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(20) NOT NULL comment'全称',
  `distr_name` varchar(20) NOT NULL comment'渠道方状态名',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_loan_main_status` COMMENT='业务状态表';

#操作类型表
CREATE TABLE `bms_loan_operate_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(2) NOT NULL unique comment'编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(20) NOT NULL comment'全称',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_loan_operate_type` COMMENT='操作类型表';

#进件材料类型表
CREATE TABLE `bms_apply_material_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(2) NOT NULL unique comment'编号',
  `abbr_name` varchar(50) NOT NULL comment'简称',
  `full_name` varchar(50) NOT NULL comment'全称',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_apply_material_type` COMMENT='件材料类型表';

#创建城市表
CREATE TABLE `bms_city` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(8) NOT NULL unique comment'编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(100) NOT NULL comment'全称',
  `order_by_no` varchar(20) comment'排序号',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_city` COMMENT='城市表';

#创建区县表
CREATE TABLE `bms_district` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(8) NOT NULL unique comment'编号',
  `city_code` varchar(8) NOT NULL  comment'城市编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(20) NOT NULL comment'全称',
  `order_by_no` int(10) comment'排序号',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`city_code`),
  FOREIGN KEY (`city_code`) REFERENCES bms_city(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_district` COMMENT='区县表';

#创建公司表
CREATE TABLE `bms_company` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(8) NOT NULL unique comment'编号',
  `parent_code` varchar(20)  comment'父公司编号',
  `city_code` varchar(8) NOT NULL  comment'城市编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(100) NOT NULL comment'全称',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`city_code`),
  FOREIGN KEY (`city_code`) REFERENCES bms_city(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_company` COMMENT='公司表';

#创建部门表
CREATE TABLE `bms_department` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `company_code` varchar(8) NOT NULL comment'公司编号',
  `code` varchar(8) NOT NULL unique comment'编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(100) NOT NULL comment'全称',
  PRIMARY KEY (`id`),
  INDEX (`company_code`),
  FOREIGN KEY (`company_code`) REFERENCES bms_company(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_department` COMMENT='部门表';

#创建渠道公司表
CREATE TABLE `bms_distributor` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(8) NOT NULL unique comment'编号',
  `parent_code` varchar(20)  comment'父公司编号',
  `city_code` varchar(8) NOT NULL  comment'城市编号',
  `first_character_code` varchar(8)  comment'城市编号',
  `abbr_name` varchar(20) NOT NULL comment'简称',
  `full_name` varchar(100) NOT NULL comment'全称',
  `order_by_no` int(10) comment'排序号',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`city_code`),
  FOREIGN KEY (`city_code`) REFERENCES bms_city(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_distributor` COMMENT='渠道公司表';

#创建用户表
CREATE TABLE `bms_user` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `user_no` varchar(11) NOT NULL unique comment'用户编号',
  `mobile` varchar(11) NOT NULL comment'手机号',
  `password` varchar(32) NOT NULL comment'密码',
  `user_name` varchar(16) comment'用户名',
  `email` varchar(64) comment'邮箱',
  `full_name` varchar(30) NOT NULL comment'姓名',
  `city_code` varchar(8) comment'城市编号',
  `company_code` varchar(8) comment'公司编号',
  `department_code` varchar(8) comment'部门编号',
  `distributor_code` varchar(8) comment'渠道编号',
  `internal_user` tinyint(1) NOT NULL default 1 comment'是否内部用户',
  `status` varchar(1) NOT NULL default '0' comment'状态',
  `first_login` tinyint(1) NOT NULL default 1 comment'首次登录',
  PRIMARY KEY (`id`),
  INDEX (`user_no`),
  INDEX (`company_code`),
  FOREIGN KEY (`company_code`) REFERENCES bms_company(`code`),
  INDEX (`city_code`),
  FOREIGN KEY (`city_code`) REFERENCES bms_city(`code`),
  INDEX (`department_code`),
  FOREIGN KEY (`department_code`) REFERENCES bms_department(`code`),
  INDEX (`distributor_code`),
  FOREIGN KEY (`distributor_code`) REFERENCES bms_distributor(`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_user` COMMENT='用户表';

#创建登录次数表
CREATE TABLE `bms_login_num` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `mobile` varchar(11) NOT NULL comment'手机号',
  `error_num` int(1) NOT NULL default 1 comment'错误次数',
  `hasLock` tinyint(1) NOT NULL default 0 comment'是否锁定',
  PRIMARY KEY (`id`),
  INDEX (`mobile`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_login_num` COMMENT='登录次数表';

#创建业务流水表
CREATE TABLE `bms_loan_main` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `distributor_code` varchar(8) comment'渠道',
  `business_status_code` varchar(2) comment'业务状态',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`distributor_code`),
  FOREIGN KEY (`distributor_code`) REFERENCES bms_distributor(`code`),
  INDEX (`business_status_code`),
  FOREIGN KEY (`business_status_code`) REFERENCES bms_loan_main_status(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_loan_main` COMMENT='业务流水表';

#创建业务操作历史表
CREATE TABLE `bms_loan_operate_history` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `main_code` varchar(36) NOT NULL comment'主表编号',
  `occur_time` datetime NOT NULL comment'发生时间',
  `distributor_code` varchar(8) comment'渠道公司编号',
  `company_code` varchar(8) comment'公司编号',
  `user_no` varchar(11) NOT NULL comment'操作人',
  `operate_type` varchar(2) NOT NULL comment'操作',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`main_code`),
  FOREIGN KEY (`main_code`) REFERENCES bms_loan_main(`code`),
  INDEX (`distributor_code`),
  FOREIGN KEY (`distributor_code`) REFERENCES bms_distributor(`code`),
  INDEX (`company_code`),
  FOREIGN KEY (`company_code`) REFERENCES bms_company(`code`),
  INDEX (`user_no`),
  FOREIGN KEY (`user_no`) REFERENCES bms_user(`user_no`),
  INDEX (`operate_type`),
  FOREIGN KEY (`operate_type`) REFERENCES bms_loan_operate_type(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_loan_operate_history` COMMENT='业务操作历史表';

#创建客户表
CREATE TABLE `bms_person` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `customer_no` varchar(36) NOT NULL unique comment'编号',
  `person_name` varchar(30) comment'姓名',
  `id_no` varchar(20) comment'身份证号',
  `mobile_phone` varchar(20) comment'移动电话',
  `marital_status_code` varchar(20) comment'婚姻状况',
  `company` varchar(20) comment'工作单位',
  `job_position` varchar(20) comment'职位',
  `annual_income` decimal(8,2) comment'年收入',
  `home_address` varchar(20) comment'家庭地址',
  PRIMARY KEY (`id`),
  INDEX (`customer_no`),
  INDEX (`marital_status_code`),
  FOREIGN KEY (`marital_status_code`) REFERENCES bms_marital_status(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_person` COMMENT='客户表';

#创建进件申请表
CREATE TABLE `bms_loan_apply` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `main_code` varchar(36) NOT NULL comment'主表编号',
  `commit_user_no` varchar(11) comment'提交人',
  `business_code` varchar(20) comment'业务编号',
  `apply_time` datetime comment'申请时间',
  `apply_user_no` varchar(11) comment'渠道提交人',
  `deal_user_no` varchar(11) comment'待处理人',
  `loan_amount` decimal(8,2) comment'申请金额',
  `loan_period_code` varchar(2) comment'借款期限',
  `apply_customer_no` varchar(36) comment'申请人',
  `family_asset` varchar(100) comment'家庭主要资产',
  `family_debt` varchar(100) comment'家庭主要负债',
  `family_inome_source` varchar(100) comment'家庭主要收入来源',
  `survey_desc` varchar(100) comment'实地调研情况',
  `loan_purpose` varchar(100) comment'借款用途',
  `repayment_source` varchar(100) comment'还款来源',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`main_code`),
  FOREIGN KEY (`main_code`) REFERENCES bms_loan_main(`code`),
  INDEX (`commit_user_no`),
  FOREIGN KEY (`commit_user_no`) REFERENCES bms_user(`user_no`),
  INDEX (`apply_user_no`),
  FOREIGN KEY (`apply_user_no`) REFERENCES bms_user(`user_no`),
  INDEX (`deal_user_no`),
  FOREIGN KEY (`deal_user_no`) REFERENCES bms_user(`user_no`),
  INDEX (`loan_period_code`),
  FOREIGN KEY (`loan_period_code`) REFERENCES bms_loan_period(`code`),
  INDEX (`apply_customer_no`),
  FOREIGN KEY (`apply_customer_no`) REFERENCES bms_person(`customer_no`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_loan_apply` COMMENT='进件申请表';

#创建借款人表
CREATE TABLE `bms_loan_customer` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `apply_code` varchar(36) NOT NULL comment'进件申请编号',
  `customer_no` varchar(36) NOT NULL comment'客户编号',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`apply_code`),
  FOREIGN KEY (`apply_code`) REFERENCES bms_loan_apply(`code`),
  INDEX (`customer_no`),
  FOREIGN KEY (`customer_no`) REFERENCES bms_person(`customer_no`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_loan_customer` COMMENT='借款人表';

#创建抵押物信息表
CREATE TABLE `bms_collateral` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `apply_code` varchar(36) NOT NULL comment'进件申请编号',
  `evaluation_price` decimal(8,2) comment'评估价',
  `house_type_code` varchar(2) comment'房屋类型',
  `mortgage_type` varchar(2) comment'抵押类型',
  `first_mortgage` varchar(2) comment'一抵余额类型 00: 最高额 01: 余额',
  `first_mortgage_balance` decimal(8,2) comment'一抵余额',
  `first_mortgage_amount` decimal(8,2) comment'一抵金额',
  `second_mortgage_amount` decimal(8,2) comment'二抵金额',
  `property_num` int(2) comment'产权人数',
  `adult_num` int(2) comment'成年人数',
  `aged_num` int(2) comment'老年人数',
  `minors_num` int(2) comment'未成年人数',
  `builded_time` datetime comment'建成时间',
  `city_code` varchar(8) comment'城市',
  `district_code` varchar(8) comment'区县',
  `adress` varchar(100) comment'详细地址',
  `residence_num` int(2) comment'户口人数',
  `residence_structure` varchar(100) comment'户口结构',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`apply_code`),
  FOREIGN KEY (`apply_code`) REFERENCES bms_loan_apply(`code`),
  INDEX (`house_type_code`),
  FOREIGN KEY (`house_type_code`) REFERENCES bms_house_type(`code`),
  INDEX (`mortgage_type`),
  FOREIGN KEY (`mortgage_type`) REFERENCES bms_mortgage_type(`code`),
  INDEX (`city_code`),
  FOREIGN KEY (`city_code`) REFERENCES bms_city(`code`),
  INDEX (`district_code`),
  FOREIGN KEY (`district_code`) REFERENCES bms_district(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_collateral` COMMENT='抵押物信息表';

#创备用房信息表
CREATE TABLE `bms_spare_house` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `apply_code` varchar(36) NOT NULL comment'进件申请编号',
  `property_person` varchar(100) comment'产权人',
  `adress` varchar(100) comment'详细地址',
  `contain_relatives` tinyint(1) comment'是否包含直系亲属 1:是;0:否',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`apply_code`),
  FOREIGN KEY (`apply_code`) REFERENCES bms_loan_apply(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_spare_house` COMMENT='备用房信息表';

#创建文件表
CREATE TABLE `bms_file` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `file_name` varchar(100) NOT NULL comment'文件名',
  `file_path` varchar(100) NOT NULL comment'存储路径',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_file` COMMENT='文件表';

#创建进件材料表
CREATE TABLE `bms_apply_material` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `apply_code` varchar(36) NOT NULL comment'进件申请编号',
  `material_type` varchar(2) NOT NULL comment'材料类型',
  `status` varchar(2) comment'材料状态 01:无;02:后补;03:已上传文件',
  `file_number` int(3) comment'上传文件数',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`apply_code`),
  FOREIGN KEY (`apply_code`) REFERENCES bms_loan_apply(`code`),
  INDEX (`material_type`),
  FOREIGN KEY (`material_type`) REFERENCES bms_apply_material_type(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_apply_material` COMMENT='进件材料表';

#创建进件材料文件关联表
CREATE TABLE `bms_apply_material_file` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `material_code` varchar(36) NOT NULL comment'进件材料编号',
  `file_code` varchar(36) NOT NULL comment'文件编号',
  `file_no` int(3) comment'文件序号(排序用)',
  `batch_no` varchar(36) comment'批次号',
  `upload_time` datetime comment'上载时间(以批次为准)',
  `file_name` varchar(100) comment'文件名(用户定义)',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`material_code`),
  FOREIGN KEY (`material_code`) REFERENCES bms_apply_material(`code`),
  INDEX (`file_code`),
  FOREIGN KEY (`file_code`) REFERENCES bms_file(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_apply_material_file` COMMENT='进件材料文件关联表';