use plms;

#待办任务类型表
DROP TABLE IF EXISTS `plms_todo_type`;
CREATE TABLE `plms_todo_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(3) NOT NULL COMMENT '待办任务类型编码',
  `name` varchar(255) DEFAULT NULL COMMENT '待办任务类型描述',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='待办任务类型表';

#待办任务列表
DROP TABLE IF EXISTS `plms_todo`;
CREATE TABLE `plms_todo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '任务创建时间（分配时间）',
  `content` varchar(255) DEFAULT NULL COMMENT '待办任务详情',
  `todo_type` varchar(3) DEFAULT NULL COMMENT '待办任务类型',
  `user_id` varchar(36) DEFAULT NULL COMMENT '待处理人',
  `user_full_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `serial_no` varchar(36) DEFAULT NULL COMMENT '业务数据主键',
  PRIMARY KEY (`id`),
  CONSTRAINT `plms_todo_ibfk_1` FOREIGN KEY (`todo_type`) REFERENCES `plms_todo_type` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待办任务列表';

# 用户和渠道关联表
DROP TABLE IF EXISTS `plms_user_distributor`;
CREATE TABLE `plms_user_distributor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_id` varchar(36) NOT NULL COMMENT '用户编号',
  `distributor_code` varchar(36) NOT NULL COMMENT '渠道编号',
  `status` varchar(1) COMMENT '0-无效 1-有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='录单员和渠道关联表';

#用户管辖城市表
DROP TABLE IF EXISTS `plms_user_govern_city`;
CREATE TABLE `plms_user_govern_city` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `city_code` varchar(8) DEFAULT NULL COMMENT '城市',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户编号',
  `status` varchar(1) COMMENT '0-无效 1-有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#消息接收表
DROP TABLE IF EXISTS `plms_message_receive`;
CREATE TABLE `plms_message_receive` (
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
DROP TABLE IF EXISTS `plms_message_send`;
CREATE TABLE `plms_message_send` (
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