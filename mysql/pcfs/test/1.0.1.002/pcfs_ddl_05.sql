

alter table `pcfs_wait_send_message` drop column message_ticker ;

alter table `pcfs_messages` drop column message_ticker ;


-- ----------------------------
-- Table structure for pcfs_wait_send_message_log
-- ----------------------------
DROP TABLE IF EXISTS `pcfs_wait_send_message_log`;
CREATE TABLE `pcfs_wait_send_message_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(40) NOT NULL COMMENT '消息标识',
  `user_id` varchar(40) NOT NULL COMMENT 'umid',
  `message_title` varchar(500) NOT NULL COMMENT '通知标题',
  `message_content` varchar(500) NOT NULL COMMENT '通知文字描述 ',
  `send_time` varchar(20) NOT NULL COMMENT '发送时间（贷后系统生成时间）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `send_method` varchar(2) NOT NULL COMMENT '0-立刻发送 1-定时发送（每日9：00-17：00 整点发送）',
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_message_id` (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


alter table pcfs_messages add column send_status VARCHAR(2) DEFAULT NULL COMMENT '发送状态：0-初始，1-发送成功，2-状态未知，3-发送失败';
alter table pcfs_messages add column batch_no VARCHAR(40) DEFAULT NULL COMMENT '批次号：批处理时候用到';
alter table pcfs_messages add column reality_send_time VARCHAR(20) DEFAULT NULL COMMENT '实际发送时间';


alter table pcfs_wait_send_message add column batch_no VARCHAR(40) DEFAULT NULL COMMENT '批次号：批处理时候用到';
alter table pcfs_wait_send_message_log add column batch_no VARCHAR(40) DEFAULT NULL COMMENT '批次号：批处理时候用到';

alter table pcfs_login_info add column device_token VARCHAR(100) DEFAULT NULL COMMENT '推送消息设备标识号';

alter table pcfs_wait_send_message add column message_ticker VARCHAR(500) DEFAULT NULL COMMENT '通知栏提示文字（安卓专用）';
alter table pcfs_wait_send_message_log add column message_ticker VARCHAR(500) DEFAULT NULL COMMENT '通知栏提示文字（安卓专用）';
alter table pcfs_messages add column message_ticker VARCHAR(500) DEFAULT NULL COMMENT '通知栏提示文字（安卓专用）';

alter table pcfs_wait_send_message add column message_subtitle VARCHAR(500) DEFAULT NULL COMMENT '子标题（苹果专用）';
alter table pcfs_wait_send_message_log add column message_subtitle VARCHAR(500) DEFAULT NULL COMMENT '子标题（苹果专用）';
alter table pcfs_messages add column message_subtitle VARCHAR(500) DEFAULT NULL COMMENT '子标题（苹果专用）';


alter table pcfs_wait_send_message add column status VARCHAR(2) DEFAULT NULL COMMENT '状态：1-初始化 2-处理中 3-处理完成';
alter table pcfs_wait_send_message_log add column status VARCHAR(2) DEFAULT NULL COMMENT '状态：1-初始化 2-处理中 3-处理完成';


alter table pcfs_wait_send_message add column message_type VARCHAR(2) DEFAULT NULL COMMENT '消息类型：00-逾期，01-扣款';
alter table pcfs_wait_send_message_log add column message_type VARCHAR(2) DEFAULT NULL COMMENT '消息类型：00-逾期，01-扣款';

alter table pcfs_messages add column send_method VARCHAR(2) DEFAULT NULL COMMENT '0-立刻发送 1-定时发送（每日9：00-17：00 整点发送）';

alter table pcfs_login_info add column channel VARCHAR(20) DEFAULT NULL COMMENT '最新渠道';


alter table pcfs_wait_send_message add column source_system VARCHAR(20) DEFAULT NULL COMMENT '上游系统';
alter table pcfs_wait_send_message_log add column source_system VARCHAR(20) DEFAULT NULL COMMENT '上游系统';
alter table pcfs_messages add column source_system VARCHAR(20) DEFAULT NULL COMMENT '上游系统';

