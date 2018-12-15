use lbs;
alter table `bms_loan_main` add `business_status_update_time` datetime;


#创建宏获用户和渠道关联表
CREATE TABLE `bms_user_distributor` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `distributor_code` varchar(8) NOT NULL   comment'渠道编号',
  `user_no` varchar(11) NOT NULL comment'用户编号',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`distributor_code`),
  FOREIGN KEY (`distributor_code`) REFERENCES bms_distributor(`code`),
  INDEX (`user_no`),
  FOREIGN KEY (`user_no`) REFERENCES bms_user(`user_no`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_user_distributor` COMMENT='宏获用户和渠道关联表';

alter table `bms_collateral` modify column `builded_time` VARCHAR(20);