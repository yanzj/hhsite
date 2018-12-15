use plms;

#登录信息表
DROP TABLE IF EXISTS `plms_login_info`;
CREATE TABLE `plms_login_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `user_no` varchar(36) NOT NULL COMMENT '用户编号',
  `system_timestamp` varchar(100) DEFAULT NULL COMMENT '登录时主机端时间戳',
  `client_timestamp` varchar(100) DEFAULT NULL COMMENT '登录时客户端时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `user_no` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `plms_login_info` COMMENT='登录信息表';

alter table plms_login_info add user_name varchar(40) comment '用户名';
alter table plms_login_info add full_name varchar(20) comment '姓名';
alter table plms_login_info add email varchar(50) comment '邮箱';
alter table plms_login_info add mobile varchar(15) comment '手机号';
alter table plms_login_info add city_code varchar(40) comment '城市';
alter table plms_login_info add city_name varchar(100);
alter table plms_login_info add group_id varchar(50) comment '组织编码';
alter table plms_login_info add group_name varchar(100) comment '组织名称';