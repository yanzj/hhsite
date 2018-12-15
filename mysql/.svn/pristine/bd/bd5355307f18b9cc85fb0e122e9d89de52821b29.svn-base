use lbs;
alter table `bms_file` add `file_size` int(10) comment'文件大小';
alter table `bms_loan_apply` add `back_modify_desc` VARCHAR(200) comment'退回修改原因';
alter table `bms_loan_apply` add `reject_desc` VARCHAR(200) comment'拒单原因';
alter table `bms_loan_apply` add `close_desc` VARCHAR(200) comment'关闭原因';
alter table `bms_collateral` add `minors_persent` VARCHAR(20) comment'未成年人所占份额';


#创建业务节点变动历史表
CREATE TABLE `bms_business_change_hisrotry` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `main_code` varchar(36) NOT NULL   comment'进件申请编号',
  `operate_user_no` varchar(11)  comment'操作用户',
  `operate_time` datetime  comment'操作时间',
  `audit_flag` tinyint(1)  comment'是否审批 0否1是',
  `audit_desc` varchar(200)  comment'审批意见',
  `before_business_status_code` varchar(2)  comment'操作前业务状态码',
  `after_business_status_code` varchar(2)  comment'操作后业务状态码',
  `after_deal_user_no` varchar(11)  comment'操作后处理人',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`main_code`),
  FOREIGN KEY (`main_code`) REFERENCES bms_loan_main(`code`),
  INDEX (`operate_user_no`),
  FOREIGN KEY (`operate_user_no`) REFERENCES bms_user(`user_no`),
  INDEX (`before_business_status_code`),
  FOREIGN KEY (`before_business_status_code`) REFERENCES bms_loan_main_status(`code`),
  INDEX (`after_business_status_code`),
  FOREIGN KEY (`after_business_status_code`) REFERENCES bms_loan_main_status(`code`),
  INDEX (`after_deal_user_no`),
  FOREIGN KEY (`after_deal_user_no`) REFERENCES bms_user(`user_no`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_business_change_hisrotry` COMMENT='业务节点变动历史表';


#创建登录后业务操作标记表
CREATE TABLE `bms_user_session_business_flag` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `user_no` varchar(11) NOT NULL   comment'用户编号',
  `business_type` varchar(2)  comment'01:暂存;02:补充材料',
  `operate_flag` tinyint(1)  comment'是否已有操作 0否1是',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`user_no`),
  FOREIGN KEY (`user_no`) REFERENCES bms_user(`user_no`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_user_session_business_flag` COMMENT='登录后业务操作标记表';

#创建登录信息表
CREATE TABLE `bms_login_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `user_no` varchar(11) NOT NULL   comment'用户编号',
  `system_timestamp` varchar(100)  comment'登录时主机端时间戳',
  `client_timestamp` varchar(100)  comment'登录时客户端时间戳',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`user_no`),
  FOREIGN KEY (`user_no`) REFERENCES bms_user(`user_no`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_login_info` COMMENT='登录信息表';