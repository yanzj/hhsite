-- ----------------------------
-- Table structure for mps_app_info
-- ----------------------------
DROP TABLE IF EXISTS `mps_app_info`;
CREATE TABLE `mps_app_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_code` varchar(36) NOT NULL,
  `app_name` varchar(50) NOT NULL COMMENT '应用名',
  `app_type` varchar(50) DEFAULT NULL COMMENT '应用类型',
  `app_key` varchar(255) DEFAULT NULL COMMENT '应用三方标识',
  `app_message_secret` varchar(255) DEFAULT NULL COMMENT '应用客户端密钥',
  `app_master_secret` varchar(255) DEFAULT NULL COMMENT '应用服务器密钥',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` varchar(2) DEFAULT NULL COMMENT '状态:0-无效  1-有效',
  `system_type` varchar(20) DEFAULT NULL COMMENT '系统类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `remark2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_code` (`app_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;




-- ----------------------------
-- Table structure for mps_umeng_info
-- ----------------------------
DROP TABLE IF EXISTS `mps_umeng_info`;
CREATE TABLE `mps_umeng_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(50) NOT NULL COMMENT '信息标识',
  `app_code` varchar(50) NOT NULL COMMENT '应用标识',
  `subtitle` varchar(500) DEFAULT NULL COMMENT '子标题（ios专用）',
  `ticker` varchar(500) NOT NULL,
  `title` varchar(500) NOT NULL,
  `text` varchar(1000) NOT NULL,
  `device_token` varchar(100) NOT NULL,
  `system_type` varchar(20) NOT NULL,
  `chl_ret` varchar(10) DEFAULT NULL,
  `chl_ret_data` varchar(255) DEFAULT NULL COMMENT '返回data信息',
  `chl_error_code` varchar(50) DEFAULT NULL COMMENT '渠道返回错误码',
  `chl_msg_id` varchar(50) DEFAULT NULL COMMENT '渠道返回信息标识',
  `task_id` varchar(50) DEFAULT NULL COMMENT '任务编码',
  `timely_news` varchar(2) DEFAULT NULL COMMENT '是否为及时消息',
  `send_status` varchar(2) DEFAULT NULL COMMENT '发送状态',
  `send_num` varchar(2) DEFAULT NULL COMMENT '发送次数',
  `lock_no` varchar(50) DEFAULT NULL COMMENT '锁定标识号（用于批量发送进行锁定）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(255) DEFAULT NULL,
  `remark2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_no` (`serial_no`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;



