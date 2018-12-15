alter table mps_receive_message_info modify column receiver_identity_id varchar(2000) DEFAULT NULL COMMENT '信息接收方id';

alter table mps_sms_info add column send_status VARCHAR(2) DEFAULT NULL COMMENT '发送状态';



-- ----------------------------
-- Table structure for mps_email_info
-- ----------------------------
DROP TABLE IF EXISTS `mps_email_info`;
CREATE TABLE `mps_email_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(255) NOT NULL COMMENT '消息标识',
  `display_name` varchar(255) DEFAULT NULL COMMENT '发件人名称（可空）',
  `user_name` varchar(50) NOT NULL COMMENT '发件人',
  `md5_pwd` varchar(50) DEFAULT NULL COMMENT '发件人密码（MD5加密）',
  `to_user_list` varchar(2000) NOT NULL COMMENT '发送人列表（最大支持20个）',
  `to_cc_list` varchar(2000) DEFAULT NULL COMMENT '抄送列表',
  `subject` varchar(100) NOT NULL COMMENT '标题',
  `content` varchar(2000) NOT NULL COMMENT '发送内容',
  `chl_state` varchar(20) DEFAULT NULL COMMENT '发送状态',
  `url_file_list` varchar(2000) DEFAULT NULL COMMENT '文件列表，支持多个文件',
  `chl_message` varchar(255) DEFAULT NULL COMMENT '发送',
  `task_id` varchar(50) DEFAULT NULL COMMENT '任务标识',
  `timely_news` varchar(2) DEFAULT NULL COMMENT '是否为及时消息',
  `send_status` varchar(2) DEFAULT NULL COMMENT '发送状态',
  `lock_no` varchar(50) DEFAULT NULL COMMENT '锁定标识',
  `send_num` varchar(2) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;