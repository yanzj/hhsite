-- ----------------------------
-- 消息类型表
-- ----------------------------
DROP TABLE IF EXISTS `mps_receive_message_info`;
DROP TABLE IF EXISTS `mps_message_type`;
CREATE TABLE `mps_message_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_type` varchar(50) NOT NULL COMMENT '消息类型',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `message_type` (`message_type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


-- ----------------------------
-- 消息流水表主表
-- ----------------------------
CREATE TABLE `mps_receive_message_info` (
  `id` int(12) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `serial_no` varchar(50) NOT NULL COMMENT '序列号',
  `type` varchar(50) NOT NULL COMMENT '接受平台消息类型',
  `sender_company_code` varchar(100) DEFAULT NULL COMMENT '信息发送方公司编号',
  `sender_company_name` varchar(255) DEFAULT NULL COMMENT '信息发送方公司名称',
  `sender_department_code` varchar(100) DEFAULT '' COMMENT '信息发送方所在部门编号',
  `sender_department_name` varchar(255) DEFAULT NULL COMMENT '信息发送方所在部门名称',
  `sender_identity_id` varchar(100) NOT NULL DEFAULT '' COMMENT '信息发送方身份Id',
  `sender_name` varchar(255) DEFAULT NULL COMMENT '信息发送方名称',
  `sender_system` varchar(20) DEFAULT NULL COMMENT '消息来源系统',
  `receiver_company_code` varchar(100) DEFAULT NULL COMMENT '信息接收方公司编号',
  `receiver_company_name` varchar(255) DEFAULT NULL COMMENT '信息接收方公司名称',
  `receiver_department_code` varchar(100) DEFAULT NULL COMMENT '信息接收方公司部门',
  `receiver_department_name` varchar(255) DEFAULT NULL COMMENT '信息接收方所在部门名称',
  `receiver_identity_id` varchar(100) DEFAULT NULL COMMENT '信息接收方身份Id',
  `receiver_name` varchar(255) DEFAULT NULL COMMENT '信息接收方名称',
  `receiver_system` varchar(255) DEFAULT NULL COMMENT '目标系统',
  `title` varchar(255) NOT NULL COMMENT '信息标题',
  `content` varchar(2000) NOT NULL DEFAULT '' COMMENT '信息内容',
  `status` varchar(255) CHARACTER SET utf32 NOT NULL DEFAULT '0' COMMENT '状态(0 创建成功，1发送成功，2状态未知，3发送失败)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `internal_param` varchar(2000) DEFAULT NULL COMMENT '特殊参数信息（内部使用）',
  `batch_no` varchar(50) DEFAULT NULL COMMENT '批次号，标识那些是一批次的',
  `request_no` varchar(50) DEFAULT NULL COMMENT '单笔请求流水号',
  PRIMARY KEY (`id`),
  KEY `FK_ENTERPRISE_TYPE` (`type`) USING BTREE,
  CONSTRAINT `FK_ENTERPRISE_TYPE` FOREIGN KEY (`type`) REFERENCES `mps_message_type` (`message_type`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COMMENT='消息接收表';



-- ----------------------------
-- 签名信息表
-- ----------------------------
DROP TABLE IF EXISTS `mps_sign_info`;
CREATE TABLE `mps_sign_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inner_sign_no` varchar(50) NOT NULL COMMENT '内部签名编码',
  `chl_sign_no` varchar(50) NOT NULL COMMENT '渠道签名编码',
  `sign_name` varchar(50) DEFAULT NULL COMMENT '签名名称',
  `sign_desc` varchar(100) DEFAULT NULL COMMENT '签名说明',
  `status` varchar(2) DEFAULT NULL COMMENT '签名状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `inedex_inner_sign_no` (`inner_sign_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- ----------------------------
-- 短信信息子表
-- ----------------------------
DROP TABLE IF EXISTS `mps_sms_info`;
CREATE TABLE `mps_sms_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(50) NOT NULL COMMENT '主序列号',
  `inner_sign_no` varchar(50) NOT NULL COMMENT '签名',
  `inner_template_code` varchar(50) NOT NULL COMMENT '内部模板id',
  `template_param` varchar(1000) NOT NULL COMMENT '参数列表',
  `out_id` varchar(255) DEFAULT NULL COMMENT '外部流水扩展字段',
  `receipt_no` varchar(100) DEFAULT NULL COMMENT '回执编号',
  `chl_ret_code` varchar(100) DEFAULT NULL COMMENT '阿里云返回码',
  `chl_ret_msg` varchar(100) DEFAULT NULL COMMENT '阿里云返回信息',
  `timely_news` varchar(2) NOT NULL COMMENT '是否为及时消息',
  `content` varchar(500) NOT NULL COMMENT '发送内容',
  `mobile` varchar(18) DEFAULT NULL COMMENT '手机号',
  `status` varchar(2) DEFAULT NULL COMMENT '发送状态',
  `send_num` varchar(2) DEFAULT NULL COMMENT '发送次数',
  `lock_no` varchar(50) DEFAULT NULL COMMENT '锁定标识号（用于批量发送进行锁定）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;




-- ----------------------------
-- 模板信息表
-- ----------------------------
DROP TABLE IF EXISTS `mps_template_info`;
CREATE TABLE `mps_template_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inner_template_code` varchar(50) NOT NULL COMMENT '内部模板id',
  `chl_template_code` varchar(50) NOT NULL COMMENT '渠道的模板编码',
  `template_param` varchar(500) DEFAULT NULL COMMENT '参数列表',
  `status` varchar(2) DEFAULT NULL COMMENT '模板状态',
  `content` varchar(500) DEFAULT NULL,
  `template_desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_inner_template_code` (`inner_template_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;



-- ----------------------------
-- 序列表
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 
  



