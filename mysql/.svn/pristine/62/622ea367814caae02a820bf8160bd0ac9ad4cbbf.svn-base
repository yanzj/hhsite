-- ----------------------------
-- 城市表
-- ----------------------------
DROP TABLE IF EXISTS `city_code`;
CREATE TABLE `city_code` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(8) NOT NULL COMMENT '编号',
  `abbr_name` varchar(20) NOT NULL COMMENT '简称',
  `full_name` varchar(100) NOT NULL COMMENT '全称',
  `level` varchar(5) NOT NULL COMMENT '级别',
  `super_code` varchar(8) DEFAULT NULL COMMENT '上级上级地市',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `code_2` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='城市表';

-- ----------------------------
-- 客户菜单信息表
-- ----------------------------
DROP TABLE IF EXISTS `custom_menu_info`;
CREATE TABLE `custom_menu_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(40) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(40) NOT NULL COMMENT '菜单名称',
  `super_id` varchar(40) DEFAULT NULL COMMENT '上级菜单',
  `status` varchar(2) NOT NULL COMMENT '菜单状态',
  `menu_desc` varchar(100) DEFAULT NULL COMMENT '菜单描述',
  `url` varchar(200) DEFAULT NULL COMMENT '连接地址',
  `system_id` varchar(40) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='菜单信息表';


-- ----------------------------
-- 客户角色菜单关系表
-- ----------------------------
DROP TABLE IF EXISTS `custom_menu_role`;
CREATE TABLE `custom_menu_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(40) NOT NULL COMMENT '菜单编码',
  `role_id` varchar(40) NOT NULL COMMENT '角色编码',
  `status` varchar(2) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_menu_role` (`menu_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';


-- ----------------------------
-- 客户角色信息表
-- ----------------------------
DROP TABLE IF EXISTS `custom_role_info`;
CREATE TABLE `custom_role_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(40) NOT NULL COMMENT '角色编码',
  `role_name` varchar(40) NOT NULL COMMENT '角色名称',
  `status` varchar(2) NOT NULL COMMENT '角色状态',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `system_id` varchar(40) DEFAULT NULL COMMENT '系统编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色信息表';



-- ----------------------------
-- 客户系统信息表
-- ----------------------------
DROP TABLE IF EXISTS `custom_system_info`;
CREATE TABLE `custom_system_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `system_id` varchar(40) NOT NULL COMMENT '系统编码',
  `system_name` varchar(40) NOT NULL COMMENT '系统名称',
  `status` varchar(2) NOT NULL COMMENT '系统状态',
  `system_desc` varchar(100) DEFAULT NULL COMMENT '系统描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_system_id` (`system_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统信息表';



-- ----------------------------
-- 客户信息表
-- ----------------------------
DROP TABLE IF EXISTS `custom_user_info`;
CREATE TABLE `custom_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_id` varchar(40) NOT NULL COMMENT '用户编号',
  `user_name` varchar(40) DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `first_login` varchar(2) NOT NULL COMMENT '首次登录',
  `full_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `birth_addr` varchar(100) DEFAULT NULL COMMENT '出生地',
  `nick` varchar(50) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `register_chl` varchar(10) NOT NULL COMMENT '注册系统',
  `login_error` varchar(5) NOT NULL COMMENT '登陆失败次数',
  `hash_lock` varchar(2) NOT NULL COMMENT '是否锁定',
  `system_id` varchar(40) NOT NULL COMMENT '系统编码',
  `lock_time` varchar(40) DEFAULT NULL COMMENT '用户锁定时间戳',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL COMMENT '备用字段1',
  `remark2` varchar(100) DEFAULT NULL COMMENT '备用字段2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;



-- ----------------------------
-- 系统用户角色关系表
-- ----------------------------
DROP TABLE IF EXISTS `custom_user_role`;
CREATE TABLE `custom_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(40) NOT NULL COMMENT '用户编码',
  `role_id` varchar(40) NOT NULL COMMENT '角色编码',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_role_system` (`user_id`,`role_id`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户角色关系表';



DROP TABLE IF EXISTS `qrtz_blob_triggres`;
DROP TABLE IF EXISTS `qrtz_calendars`;
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
DROP TABLE IF EXISTS `qrtz_locks`;
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
DROP TABLE IF EXISTS `qrtz_triggers`;
DROP TABLE IF EXISTS `qrtz_job_details`;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- 定时任务系统表
-- ----------------------------
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggres_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- ----------------------------
-- 定时任务系统表
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;





-- ----------------------------
-- 序列表
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- um机构信息表
-- ----------------------------
DROP TABLE IF EXISTS `um_group_info`;
CREATE TABLE `um_group_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(40) NOT NULL COMMENT '机构编码',
  `group_name` varchar(40) NOT NULL COMMENT '机构名称',
  `group_abbr` varchar(40) DEFAULT NULL COMMENT '机构简称',
  `english_abbr` varchar(40) DEFAULT NULL COMMENT '英文缩写',
  `super_id` varchar(40) DEFAULT NULL COMMENT '上级机构',
  `status` varchar(2) NOT NULL COMMENT '组织状态',
  `group_desc` varchar(100) DEFAULT NULL COMMENT '组织描述',
  `group_type` varchar(2) NOT NULL COMMENT '机构类型',
  `group_city` varchar(40) NOT NULL COMMENT '所在城市',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='机构信息表';



-- ----------------------------
-- um菜单信息表
-- ----------------------------
DROP TABLE IF EXISTS `um_menu_info`;
CREATE TABLE `um_menu_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(40) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(40) NOT NULL COMMENT '菜单名称',
  `super_id` varchar(40) DEFAULT NULL COMMENT '上级菜单',
  `status` varchar(2) NOT NULL COMMENT '菜单状态',
  `menu_desc` varchar(100) DEFAULT NULL COMMENT '菜单描述',
  `url` varchar(200) DEFAULT NULL COMMENT '连接地址',
  `system_id` varchar(40) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='菜单信息表';


-- ----------------------------
-- um角色菜单关系表
-- ----------------------------
DROP TABLE IF EXISTS `um_menu_role`;
CREATE TABLE `um_menu_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(40) NOT NULL COMMENT '菜单编码',
  `role_id` varchar(40) NOT NULL COMMENT '角色编码',
  `status` varchar(2) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_menu_role` (`menu_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';



-- ----------------------------
-- um角色信息表
-- ----------------------------
DROP TABLE IF EXISTS `um_role_info`;
CREATE TABLE `um_role_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(40) NOT NULL COMMENT '角色编码',
  `role_name` varchar(40) NOT NULL COMMENT '角色名称',
  `status` varchar(2) NOT NULL COMMENT '角色状态',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `system_id` varchar(40) DEFAULT NULL COMMENT '系统编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='角色信息表';


-- ----------------------------
-- um系统信息表
-- ----------------------------
DROP TABLE IF EXISTS `um_system_info`;
CREATE TABLE `um_system_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `system_id` varchar(40) NOT NULL COMMENT '系统编码',
  `system_name` varchar(40) NOT NULL COMMENT '系统名称',
  `status` varchar(2) NOT NULL COMMENT '系统状态',
  `system_desc` varchar(100) DEFAULT NULL COMMENT '系统描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_system_id` (`system_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统信息表';

-- ----------------------------
-- um用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `um_user_info`;
CREATE TABLE `um_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_id` varchar(40) NOT NULL COMMENT '用户编号',
  `user_name` varchar(40) DEFAULT NULL COMMENT '用户名',
  `city_code` varchar(40) DEFAULT NULL COMMENT '城市',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `post` varchar(100) DEFAULT NULL COMMENT '职务',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `first_login` varchar(2) NOT NULL COMMENT '首次登录',
  `full_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `birth_addr` varchar(100) DEFAULT NULL COMMENT '出生地',
  `nick` varchar(50) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `register_chl` varchar(10) NOT NULL COMMENT '注册系统',
  `login_error` varchar(5) DEFAULT NULL COMMENT '登陆失败次数',
  `hash_lock` varchar(2) NOT NULL COMMENT '是否锁定',
  `group_id` varchar(50) DEFAULT NULL COMMENT '机构id',
  `super_user_id` varchar(40) DEFAULT NULL,
  `lock_time` varchar(40) DEFAULT NULL COMMENT '用户锁定时间戳',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='用户信息表';


-- ----------------------------
-- um系统用户角色关系表
-- ----------------------------
DROP TABLE IF EXISTS `um_user_role`;
CREATE TABLE `um_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(40) NOT NULL COMMENT '用户编码',
  `role_id` varchar(40) NOT NULL COMMENT '角色编码',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_role_system` (`user_id`,`role_id`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='系统用户角色关系表';



-- ----------------------------
-- um用户系统权限表
-- ----------------------------
DROP TABLE IF EXISTS `um_user_system`;
CREATE TABLE `um_user_system` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(40) NOT NULL COMMENT '用户编码',
  `system_id` varchar(40) NOT NULL COMMENT '系统编码',
  `status` varchar(2) NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark1` varchar(100) DEFAULT NULL,
  `remark2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;






