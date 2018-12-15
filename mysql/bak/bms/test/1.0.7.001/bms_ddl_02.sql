# 创建bms_user_distributor的临时表bms_user_distributor_temp
CREATE TABLE bms_user_distributor_temp LIKE bms_user_distributor;


# 用户层级表
CREATE TABLE `bms_user_hierarchy` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `user_no` varchar(11) NOT NULL COMMENT '用户编号',
  `parent_user_no` varchar(11) NOT NULL COMMENT '上级用户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `bms_user_hierarchy` COMMENT='用户层级表';

#修改字段长度
alter table bms_collateral_temp modify first_mortgage_balance decimal(10,2);

alter table bms_collateral_temp modify first_mortgage_amount decimal(10,2);

alter table bms_collateral_temp modify second_mortgage_amount decimal(10,2);

alter table bms_loan_apply_temp modify loan_amount decimal(10,2);

alter table bms_person_temp modify annual_income decimal(10,2);