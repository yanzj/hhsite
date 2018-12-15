use nlbs;

SET foreign_key_checks = 0;

# 进件系统与核心系统状态对应表
DROP TABLE IF EXISTS `nlbs_apply_loan_status`;
CREATE TABLE `nlbs_apply_loan_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL COMMENT '本地状态值',
  `name` varchar(36) DEFAULT NULL COMMENT '本地状态名',
  `bms_code` varchar(16) DEFAULT NULL COMMENT '核心系统状态',
  `bms_name` varchar(36) DEFAULT NULL COMMENT '核心系统状态名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

# 房屋类型表
DROP TABLE IF EXISTS `nlbs_house_type`;
CREATE TABLE `nlbs_house_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(5) NOT NULL COMMENT '类型编码',
  `name` varchar(100) DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='房屋类型表';

# 用户申请房屋询价记录表
DROP TABLE IF EXISTS `nlbs_inquiry_apply`;
CREATE TABLE `nlbs_inquiry_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '请求编号',
  `bps_code` varchar(36) DEFAULT NULL COMMENT '询价系统序列号',
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请询价时间',
  `date_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  `user_no` varchar(36) DEFAULT NULL COMMENT '询价用户',
  `pending_user_no` varchar(36) DEFAULT NULL COMMENT '待处理人',
  `city_code` varchar(36) DEFAULT NULL COMMENT '询价的房子所在城市',
  `house_type` varchar(5) DEFAULT NULL COMMENT '房屋类型',
  `address` varchar(255) DEFAULT NULL COMMENT '房屋地址',
  `area` decimal(18,2) DEFAULT NULL COMMENT '房屋面积',
  `price` decimal(10,2) DEFAULT NULL COMMENT '评估价格',
  `price_time` datetime DEFAULT NULL COMMENT '评估时间',
  `auto_price` varchar(1) DEFAULT NULL COMMENT '是否自动询价',
  `dead_line` datetime DEFAULT NULL COMMENT '失效时间',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(00待评估，01评估中，02已评估，99失效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bps_code` (`bps_code`) USING BTREE,
  KEY `user_no` (`user_no`),
  KEY `city_code` (`city_code`),
  CONSTRAINT `nlbs_inquiry_apply_ibfk_1` FOREIGN KEY (`user_no`) REFERENCES `nlbs_user` (`user_no`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `nlbs_inquiry_apply_ibfk_2` FOREIGN KEY (`city_code`) REFERENCES `nlbs_city` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='用户申请房屋询价记录表';

# 操作类型表
DROP TABLE IF EXISTS `nlbs_operation_type`;
CREATE TABLE `nlbs_operation_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(10) DEFAULT NULL COMMENT '操作类型编码',
  `name` varchar(255) DEFAULT NULL COMMENT '操作类型描述',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

# 操作历史表
DROP TABLE IF EXISTS `nlbs_operation_history`;
CREATE TABLE `nlbs_operation_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `operater` varchar(36) DEFAULT NULL,
  `serial_no` varchar(36) DEFAULT NULL COMMENT '关联单号',
  `system_code` varchar(10) NOT NULL DEFAULT 'nlbs' COMMENT '序列号所属系统',
  `operate_type` varchar(2) DEFAULT NULL COMMENT '操作类型',
  PRIMARY KEY (`id`),
  KEY `operate_type` (`operate_type`),
  KEY `serial_no` (`serial_no`) USING BTREE,
  KEY `system_code` (`system_code`) USING BTREE,
  CONSTRAINT `nlbs_operation_history_ibfk_1` FOREIGN KEY (`operate_type`) REFERENCES `nlbs_operation_type` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='操作历史表';

# 待处理人和渠道关联表
DROP TABLE IF EXISTS `nlbs_pending_user_distributor`;
CREATE TABLE `nlbs_pending_user_distributor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_no` varchar(36) DEFAULT NULL COMMENT '待处理人',
  `distributor_code` varchar(36) DEFAULT NULL COMMENT '所属渠道',
  PRIMARY KEY (`id`),
  KEY `user_no` (`user_no`),
  KEY `distributor_code` (`distributor_code`),
  CONSTRAINT `nlbs_pending_user_distributor_ibfk_1` FOREIGN KEY (`user_no`) REFERENCES `nlbs_user` (`user_no`),
  CONSTRAINT `nlbs_pending_user_distributor_ibfk_2` FOREIGN KEY (`distributor_code`) REFERENCES `nlbs_distributor` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待处理人和渠道关联表';

# 增加字段上级用户  parent_user_no，外键关联本表的user_no
alter table nlbs_user add column parent_user_no varchar(36) DEFAULT NULL COMMENT '上级用户';
alter table nlbs_user add constraint nlbs_user_ibfk_2 foreign key(parent_user_no) references nlbs_user(user_no);

SET foreign_key_checks = 1;