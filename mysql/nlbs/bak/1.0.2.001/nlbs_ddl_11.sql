use nlbs;

drop table if exists nlbs_agent_distributor;
drop table if exists nlbs_login_num;
drop table if exists nlbs_role_user;
drop table if exists nlbs_role_menu;
drop table if exists nlbs_menu;
drop table if exists nlbs_role;

alter table nlbs_login_info drop FOREIGN KEY nlbs_login_info_ibfk_1;
alter table nlbs_login_info add user_name varchar(40) comment '用户名';
alter table nlbs_login_info add full_name varchar(20) comment '姓名';
alter table nlbs_login_info add email varchar(50) comment '邮箱';
alter table nlbs_login_info add mobile varchar(15) comment '手机号';
alter table nlbs_login_info add city_code varchar(40) comment '城市';
alter table nlbs_login_info add city_name varchar(100);
alter table nlbs_login_info add distributor_code varchar(50) comment '渠道号';
alter table nlbs_login_info add distributor_name varchar(100) ;
alter table nlbs_user drop FOREIGN KEY nlbs_user_ibfk_1;
drop table if exists nlbs_agent;

alter table nlbs_distributor drop FOREIGN KEY nlbs_distributor_ibfk_1;
alter table nlbs_inquiry_apply drop FOREIGN KEY nlbs_inquiry_apply_ibfk_1;
alter table nlbs_inquiry_apply drop FOREIGN KEY nlbs_inquiry_apply_ibfk_2;

#城市表新增是否支持询价字段
alter table nlbs_city drop COLUMN order_by_no;
alter table nlbs_city add allow_inquiry varchar(1) DEFAULT '1' COMMENT '是否支持询价(1支持)';


alter table nlbs_pending_user_distributor drop FOREIGN KEY nlbs_pending_user_distributor_ibfk_1;
alter table nlbs_pending_user_distributor drop FOREIGN KEY nlbs_pending_user_distributor_ibfk_2;
alter table nlbs_pending_user_distributor add user_full_name  varchar(20) not null comment '用户名';
alter table nlbs_pending_user_distributor add distributor_name varchar(100) comment '渠道名称';

alter table nlbs_product_distributor drop FOREIGN KEY nlbs_product_distributor_ibfk_2;
drop table if exists nlbs_distributor;
alter table nlbs_todo drop FOREIGN KEY nlbs_todo_ibfk_2;
drop table if exists nlbs_user;

# 录单员和渠道关联表
DROP TABLE IF EXISTS `nlbs_recorders_distributor`;
CREATE TABLE `nlbs_recorders_distributor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_no` varchar(36) NOT NULL COMMENT '用户编号',
  `distributor_code` varchar(36) NOT NULL COMMENT '渠道编号',
  `status` varchar(1) ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='录单员和渠道关联表';
#用户管辖城市表
DROP TABLE IF EXISTS `nlbs_user_govern_city`;
CREATE TABLE `nlbs_user_govern_city` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `city_code` varchar(8) DEFAULT NULL COMMENT '城市',
  `user_no` varchar(36) DEFAULT NULL COMMENT '用户号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#操作历史表
alter table NLBS_OPERATION_HISTORY add operater_name  varchar(20) not null comment '用户名';
alter table NLBS_OPERATION_HISTORY add distributor_code varchar(36) comment '渠道号';
alter table NLBS_OPERATION_HISTORY add distributor_name varchar(100) comment '渠道名称';
#询价申请表
alter table NLBS_INQUIRY_APPLY add user_full_name  varchar(20) not null comment '询价用户名';
alter table NLBS_INQUIRY_APPLY add pending_user_name varchar(20) comment '待处理人名';
alter table NLBS_INQUIRY_APPLY add distributor_code varchar(36) comment '渠道号';
#删除旧消息表
drop table if exists nlbs_message;
#消息接收表
DROP TABLE IF EXISTS `nlbs_message_receive`;
CREATE TABLE `nlbs_message_receive` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '数据ID（nlbs存储）',
  `serial_no` varchar(50) DEFAULT NULL COMMENT '消息序列号(消息平台序号)',
  `title` varchar(255) NOT NULL COMMENT '消息标题',
  `content` varchar(2000) NOT NULL COMMENT '消息内容',
  `sender_company_code` varchar(100) DEFAULT NULL COMMENT '发送方公司编号',
  `sender_company_name` varchar(255) DEFAULT NULL,
  `sender_department_code` varchar(100) DEFAULT NULL COMMENT '发送方所在部门编号',
  `sender_department_name` varchar(255) DEFAULT NULL COMMENT '发送方所在部门名称',
  `sender_identity_id` varchar(100) NOT NULL COMMENT '发送用户编号',
  `sender_name` varchar(255) DEFAULT NULL COMMENT '发送用户名',
  `receiver_identity_id` varchar(100) NOT NULL COMMENT '接收用户编号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `internal_param` varchar(2000) DEFAULT NULL,
  `status` varchar(1) DEFAULT '0' COMMENT '状态(0未读1已读)',
  `read_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '消息读取时间',
  `read_channel` varchar(16) DEFAULT NULL COMMENT '消息读取终端(PC 电脑端；APP 手机客户端；WeChat 微信端) ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
#消息发送表
DROP TABLE IF EXISTS `nlbs_message_send`;
CREATE TABLE `nlbs_message_send` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '数据ID（nlbs存储）',
  `serial_no` varchar(50) DEFAULT NULL COMMENT '消息序列号(消息平台序号)',
  `title` varchar(255) NOT NULL COMMENT '消息标题',
  `content` varchar(2000) NOT NULL COMMENT '消息内容',
  `sender_company_code` varchar(100) DEFAULT NULL COMMENT '发送方公司编号',
  `sender_company_name` varchar(255) DEFAULT NULL COMMENT '发送方公司名称',
  `sender_department_code` varchar(100) DEFAULT NULL COMMENT '发送方所在部门编号',
  `sender_department_name` varchar(255) DEFAULT NULL COMMENT '发送方所在部门名称',
  `sender_identity_id` varchar(100) NOT NULL COMMENT '发送用户编号',
  `sender_name` varchar(255) DEFAULT NULL COMMENT '发送用户名',
  `receiver_identity_id` varchar(100) NOT NULL COMMENT '接收用户编号',
  `receiver_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `internal_param` varchar(2000) DEFAULT NULL,
  `status` varchar(1) DEFAULT '0' COMMENT '状态(0未发送1已发送2发送失败)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
#
alter table nlbs_todo add user_full_name varchar(20) comment'用户名';

