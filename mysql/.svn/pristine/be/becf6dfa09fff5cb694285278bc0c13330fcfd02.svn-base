use nlbs;


#城市表
DROP TABLE IF EXISTS `nlbs_city`;
CREATE TABLE `nlbs_city` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(8) NOT NULL COMMENT '编号',
  `abbr_name` varchar(20) NOT NULL COMMENT '简称',
  `full_name` varchar(100) NOT NULL COMMENT '全称',
  `order_by_no` varchar(20) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_city` COMMENT='城市表';

#渠道公司表
DROP TABLE IF EXISTS `nlbs_distributor`;
CREATE TABLE `nlbs_distributor` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `parent_code` varchar(20) DEFAULT NULL COMMENT '父公司编号',
  `city_code` varchar(8) NOT NULL COMMENT '城市编号',
  `first_character_code` varchar(8) DEFAULT NULL COMMENT '城市编号',
  `abbr_name` varchar(20) NOT NULL COMMENT '简称',
  `full_name` varchar(100) NOT NULL COMMENT '全称',
  `order_by_no` int(10) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`),
  KEY `city_code` (`city_code`),
  CONSTRAINT `nlbs_distributor_ibfk_1` FOREIGN KEY (`city_code`) REFERENCES `nlbs_city` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_distributor` COMMENT='渠道公司表';

#业务员表
DROP TABLE IF EXISTS `nlbs_agent`;
CREATE TABLE `nlbs_agent` (
  `id` int(36) NOT NULL AUTO_INCREMENT,
  `agent_id` varchar(36) DEFAULT NULL COMMENT '核心系统业务员主键',
  `name` varchar(50) DEFAULT NULL COMMENT '业务员名称',
  `be_record_clerk` varchar(1) DEFAULT NULL COMMENT '是否是录单员(Y是 ，N 不是)',
  `distributor_code` varchar(36) DEFAULT NULL COMMENT '渠道号',
  PRIMARY KEY (`id`),
  KEY `distributor_code` (`distributor_code`),
  KEY `agent_id` (`agent_id`),
  CONSTRAINT `nlbs_agent_ibfk_1` FOREIGN KEY (`distributor_code`) REFERENCES `nlbs_distributor` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `nlbs_agent`  COMMENT='业务员表';

#业务员-渠道表
DROP TABLE IF EXISTS `nlbs_agent_distributor`;
CREATE TABLE `nlbs_agent_distributor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `distributor_code` varchar(36) DEFAULT NULL COMMENT '渠道号',
  `agent_id` varchar(36) DEFAULT NULL COMMENT '业务员',
  PRIMARY KEY (`id`),
  KEY `distributor_code` (`distributor_code`),
  KEY `agent_id` (`agent_id`),
  CONSTRAINT `nlbs_agent_distributor_ibfk_1` FOREIGN KEY (`distributor_code`) REFERENCES `nlbs_distributor` (`code`),
  CONSTRAINT `nlbs_agent_distributor_ibfk_2` FOREIGN KEY (`agent_id`) REFERENCES `nlbs_agent` (`agent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_agent_distributor` COMMENT='业务员渠道表';

#进件状态表
DROP TABLE IF EXISTS `nlbs_apply_loan_status`;
CREATE TABLE `nlbs_apply_loan_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) DEFAULT NULL COMMENT '本地状态值',
  `bms_code` varchar(12) DEFAULT NULL COMMENT '核心系统状态',
  `remark` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `nlbs_apply_loan_status` COMMENT='进件状态表';

#进件材料类型表
DROP TABLE IF EXISTS `nlbs_apply_material_type`;
CREATE TABLE `nlbs_apply_material_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(16) NOT NULL COMMENT '编号',
  `abbr_name` varchar(50) NOT NULL COMMENT '简称',
  `full_name` varchar(50) NOT NULL COMMENT '全称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_apply_material_type` COMMENT='进件材料类型表';


#放款方式表
DROP TABLE IF EXISTS `nlbs_credit_type`;
CREATE TABLE `nlbs_credit_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '放款方式代码',
  `name` varchar(50) DEFAULT NULL COMMENT '放款方式名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_credit_type` COMMENT='放款方式表';


#借款期限表
DROP TABLE IF EXISTS `nlbs_loan_period`;
CREATE TABLE `nlbs_loan_period` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '借款期限代码',
  `period` int(2) DEFAULT NULL COMMENT '借款期限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_loan_period` COMMENT='借款期限表';

#抵押类型表
DROP TABLE IF EXISTS `nlbs_mortgage_type`;
CREATE TABLE `nlbs_mortgage_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(50) NOT NULL COMMENT '类型编码',
  `type` varchar(100) NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_mortgage_type` COMMENT='抵押类型表';

#进件历史操作表
DROP TABLE IF EXISTS `nlbs_operation_history`;
CREATE TABLE `nlbs_operation_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_no` varchar(36) DEFAULT NULL,
  `operate_type` varchar(2) DEFAULT NULL COMMENT '操作类型',
  `operate_description` varchar(255) DEFAULT NULL COMMENT '操作描述',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `nlbs_operation_history` COMMENT='进件历史操作表';

#产品表
DROP TABLE IF EXISTS `nlbs_product`;
CREATE TABLE `nlbs_product` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '产品全称',
  `max_loan_amount` varchar(12) DEFAULT NULL COMMENT '最高可贷金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_product` COMMENT='产品表';

#产品和渠道关联表
DROP TABLE IF EXISTS `nlbs_product_distributor`;
CREATE TABLE `nlbs_product_distributor` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `product_code` varchar(36) DEFAULT NULL COMMENT '产品编号',
  `distributor_code` varchar(36) DEFAULT NULL COMMENT '渠道编号',
  `status` varchar(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`),
  KEY `product_code` (`product_code`),
  KEY `distributor_code` (`distributor_code`),
  CONSTRAINT `nlbs_product_distributor_ibfk_1` FOREIGN KEY (`product_code`) REFERENCES `nlbs_product` (`code`),
  CONSTRAINT `nlbs_product_distributor_ibfk_2` FOREIGN KEY (`distributor_code`) REFERENCES `nlbs_distributor` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_product_distributor` COMMENT='产品和渠道关联表';

#消息表
DROP TABLE IF EXISTS `nlbs_message`;
CREATE TABLE `nlbs_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(50) NOT NULL COMMENT '消息序列号(消息平台序号)',
  `title` varchar(255) NOT NULL COMMENT '消息标题',
  `content` varchar(2000) NOT NULL COMMENT '消息内容',
  `sender_company_code` varchar(100) DEFAULT NULL COMMENT '发送方公司编号',
  `sender_company_name` varchar(255) DEFAULT NULL COMMENT '发送方公司名称',
  `sender_department_code` varchar(100) DEFAULT NULL COMMENT '发送方所在部门编号',
  `sender_department_name` varchar(255) DEFAULT NULL COMMENT '发送方所在部门名称',
  `sender_identity_id` varchar(100) NOT NULL COMMENT '发送用户编号',
  `sender_name` varchar(255) NOT NULL COMMENT '发送用户名',
  `receiver_user_id` varchar(100) NOT NULL COMMENT '接收用户编号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `read_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '信息读取时间',
  `read_channel` varchar(16) DEFAULT NULL COMMENT '消息读取终端(PC 电脑端；APP 手机客户端；WeChat 微信端)',
  `status` varchar(2) NOT NULL DEFAULT '0' COMMENT '状态(0创建；1已读取)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `nlbs_message` COMMENT='消息表';

#用户表
DROP TABLE IF EXISTS `nlbs_user`;
CREATE TABLE `nlbs_user` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_no` varchar(36) NOT NULL COMMENT '用户编号',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `user_name` varchar(16) DEFAULT NULL COMMENT '用户名',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `full_name` varchar(30) NOT NULL COMMENT '姓名',
  `agent_id` varchar(36) DEFAULT NULL COMMENT '业务员id(核心系统)',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `first_login` tinyint(1) NOT NULL DEFAULT '1' COMMENT '首次登录',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_no` (`user_no`),
  UNIQUE KEY `user_name` (`user_name`) USING BTREE,
  KEY `user_no_2` (`user_no`),
  KEY `distributor_code` (`agent_id`),
  CONSTRAINT `nlbs_user_ibfk_1` FOREIGN KEY (`agent_id`) REFERENCES `nlbs_agent` (`agent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_user` COMMENT='用户表';

#登录信息表
DROP TABLE IF EXISTS `nlbs_login_info`;
CREATE TABLE `nlbs_login_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `user_no` varchar(36) NOT NULL COMMENT '用户编号',
  `system_timestamp` varchar(100) DEFAULT NULL COMMENT '登录时主机端时间戳',
  `client_timestamp` varchar(100) DEFAULT NULL COMMENT '登录时客户端时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`),
  KEY `user_no` (`user_no`),
  CONSTRAINT `nlbs_login_info_ibfk_1` FOREIGN KEY (`user_no`) REFERENCES `nlbs_user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_login_info` COMMENT='登录信息表';

#登录次数表
DROP TABLE IF EXISTS `nlbs_login_num`;
CREATE TABLE `nlbs_login_num` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_no` varchar(36) NOT NULL COMMENT '手机号',
  `error_num` int(1) NOT NULL DEFAULT '1' COMMENT '错误次数',
  `hasLock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定',
  PRIMARY KEY (`id`),
  KEY `mobile` (`user_no`),
  CONSTRAINT `nlbs_login_num_ibfk_1` FOREIGN KEY (`user_no`) REFERENCES `nlbs_user` (`user_no`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_login_num` COMMENT='登录次数表';

#菜单表
DROP TABLE IF EXISTS `nlbs_menu`;
CREATE TABLE `nlbs_menu` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(3) NOT NULL COMMENT '编号',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `menu_order_no` varchar(10) NOT NULL COMMENT '排序号',
  `menu_level` int(1) DEFAULT NULL COMMENT '菜单级别',
  `father_menu_code` varchar(3) DEFAULT NULL COMMENT '父菜单编号',
  `empty_type` tinyint(1) DEFAULT NULL COMMENT '是否空菜单 1 是；0 否；',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_menu` COMMENT='菜单表';


#角色表
DROP TABLE IF EXISTS `nlbs_role`;
CREATE TABLE `nlbs_role` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(3) NOT NULL COMMENT '编号',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `role_type` varchar(2) DEFAULT NULL COMMENT '角色类别 01 管理员；02 管理层；03 风控；04资产渠道；',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_role` COMMENT='角色表';

#角色-菜单表
DROP TABLE IF EXISTS `nlbs_role_menu`;
CREATE TABLE `nlbs_role_menu` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `role_code` varchar(3) NOT NULL COMMENT '角色编号',
  `menu_code` varchar(3) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`),
  KEY `role_code` (`role_code`),
  KEY `menu_code` (`menu_code`),
  CONSTRAINT `nlbs_role_menu_ibfk_1` FOREIGN KEY (`role_code`) REFERENCES `nlbs_role` (`code`),
  CONSTRAINT `nlbs_role_menu_ibfk_2` FOREIGN KEY (`menu_code`) REFERENCES `nlbs_menu` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_role_menu` COMMENT='角色菜单表';

#角色-用户表
DROP TABLE IF EXISTS `nlbs_role_user`;
CREATE TABLE `nlbs_role_user` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '编号',
  `role_code` varchar(3) NOT NULL COMMENT '角色编号',
  `user_no` varchar(36) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`),
  KEY `role_code` (`role_code`),
  KEY `user_no` (`user_no`),
  CONSTRAINT `nlbs_role_user_ibfk_1` FOREIGN KEY (`role_code`) REFERENCES `nlbs_role` (`code`),
  CONSTRAINT `nlbs_role_user_ibfk_2` FOREIGN KEY (`user_no`) REFERENCES `nlbs_user` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
ALTER TABLE `nlbs_role_user` COMMENT='角色用户表';


#定时- 日历信息
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#定时- 已触发的触发器
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#定时- 任务信息
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#定时- 存储程序的悲观锁的信息
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#定时- 存放暂停掉的触发器
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#定时- 调度器状态
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#定时- 触发器的基本信息
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#定时-
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#定时- 简单触发器的信息
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#定时- cron类型的触发器
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#定时- Blob 类型存储的触发器
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggres_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;