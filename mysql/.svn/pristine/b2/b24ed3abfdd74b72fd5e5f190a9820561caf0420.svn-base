use fms;

# 添加是否与借款人有关
ALTER TABLE fms_model_bean ADD column relate_to_customer VARCHAR (1) COMMENT "是否与借款人有关";

ALTER TABLE fms_model_bean ADD column email_model VARCHAR (255) COMMENT "邮件模板";
ALTER TABLE fms_model_bean ADD column msg_model VARCHAR (255) COMMENT "短信模板";
ALTER TABLE fms_model_bean ADD column wechat_model VARCHAR (255) COMMENT "微信模板";

# 添加附件文件列表
ALTER TABLE fms_build_model_records ADD column attach_file_list VARCHAR (255) COMMENT "附件文件列表";

# 材料外发记录表
DROP TABLE IF EXISTS `fms_send_records`;
CREATE TABLE `fms_send_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `date_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发送成功时间',
  `code` varchar(36) DEFAULT NULL COMMENT '申请发送序号',
  `serial_no` varchar(36) DEFAULT NULL COMMENT '消息中心流水号',
  `record_no` varchar(36) DEFAULT NULL COMMENT '所发送申请的流水号',
  `mail_receiver_address` varchar(255) DEFAULT NULL COMMENT '接收邮件地址',
  `mail_cc_address` varchar(255) DEFAULT NULL COMMENT '抄送邮件地址',
  `short_msg_receiver` varchar(255) DEFAULT NULL COMMENT '短信接收号码',
  `wechat_receiver` varchar(255) DEFAULT NULL COMMENT '微信消息接收号码',
  `content` varchar(255) DEFAULT NULL COMMENT '发送内容',
  `send_type` varchar(50) DEFAULT NULL COMMENT '发送方式',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='材料外发记录表';

# 发送记录与材料关联表
DROP TABLE IF EXISTS `fms_send_files`;
CREATE TABLE `fms_send_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) DEFAULT NULL COMMENT '流水号',
  `send_code` varchar(36) DEFAULT NULL COMMENT '发送流水号',
  `file_id` varchar(36) DEFAULT NULL COMMENT '文件id',
  PRIMARY KEY (`id`),
  KEY `send_code` (`send_code`),
  CONSTRAINT `fms_send_files_ibfk_1` FOREIGN KEY (`send_code`) REFERENCES `fms_send_records` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发送记录与材料关联表';


