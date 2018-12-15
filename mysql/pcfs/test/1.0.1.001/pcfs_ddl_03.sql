-- ----------------------------
-- Table structure for pcfs_android_version
-- ----------------------------
DROP TABLE IF EXISTS `pcfs_android_version`;
CREATE TABLE `pcfs_android_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` varchar(3) NOT NULL COMMENT '版本号',
  `version_detail` varchar(10) NOT NULL COMMENT '版本描述',
  `title` varchar(500) NOT NULL COMMENT '标题',
  `detail` varchar(500) NOT NULL COMMENT '版本详情',
  `force` varchar(2) NOT NULL COMMENT '强制更新 0-强制 1-可选',
  `download_url` varchar(100) NOT NULL COMMENT '下载地址',
  `apk_size` varchar(10) NOT NULL COMMENT '大小',
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_version` (`version`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='app版本信息表';


-- ----------------------------
-- Table structure for pcfs_messages
-- ----------------------------
DROP TABLE IF EXISTS `pcfs_messages`;
CREATE TABLE `pcfs_messages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(40) NOT NULL COMMENT '消息标识',
  `user_id` varchar(40) NOT NULL COMMENT 'umid',
  `message_ticker` varchar(500) NOT NULL COMMENT '通知栏提示文字',
  `message_title` varchar(500) NOT NULL COMMENT '通知标题',
  `message_content` varchar(500) NOT NULL COMMENT '通知文字描述 ',
  `send_time` varchar(20) NOT NULL COMMENT '发送时间',
  `read_flag` varchar(2) NOT NULL COMMENT '读取标识 0-未读 1-已读',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  `status` varchar(2) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_message_id` (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='消息表';



-- ----------------------------
-- Table structure for pcfs_wait_send_message
-- ----------------------------
DROP TABLE IF EXISTS `pcfs_wait_send_message`;
CREATE TABLE `pcfs_wait_send_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(40) NOT NULL COMMENT '消息标识',
  `user_id` varchar(40) NOT NULL COMMENT 'umid',
  `message_ticker` varchar(500) NOT NULL COMMENT '通知栏提示文字',
  `message_title` varchar(500) NOT NULL COMMENT '通知标题',
  `message_content` varchar(500) NOT NULL COMMENT '通知文字描述 ',
  `send_time` varchar(20) NOT NULL COMMENT '发送时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `send_method` varchar(2) NOT NULL COMMENT '0-立刻发送 1-定时发送（每日9：00-17：00 整点发送）',
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_message_id` (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


alter table pcfs_login_info add column overdue_set VARCHAR(2) DEFAULT '0' COMMENT '逾期通知设置(0-开通 1-关闭)';

alter table pcfs_login_info add column payment_set VARCHAR(2) DEFAULT '0' COMMENT '还款通知设置 (0-开通 1-关闭)';

alter table pcfs_login_info add column display_detail_set VARCHAR(2) DEFAULT '0' COMMENT '显示消息详情(0-显示 1-不显示)';



