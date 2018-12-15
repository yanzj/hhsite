use bps;
#系统配置表
DROP TABLE IF EXISTS `bps_config`;
CREATE TABLE `bps_config` (
  `id` int(36) NOT NULL  AUTO_INCREMENT,
  `config_name` varchar(50) NOT NULL COMMENT '配置名称',
  `config_value` varchar(50) NOT NULL COMMENT '配置值',
  `description` varchar(100) DEFAULT NULL COMMENT '配置描述',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '状态(0不可用，1可用)',
  `date_created` datetime NOT NULL COMMENT '数据创建时间',
  `date_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `config_name` (`config_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

#询价流水状态表
DROP TABLE IF EXISTS `bps_inquiry_status`;
CREATE TABLE `bps_inquiry_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `value` varchar(3) DEFAULT NULL COMMENT '状态',
  `name` varchar(36) DEFAULT NULL COMMENT '状态名',
  `status` varchar(1) DEFAULT '1' COMMENT '是否可用(1可用，0不可用)',
  `remark` varchar(255) DEFAULT NULL COMMENT '说明字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `value` (`value`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='询价状态表';

#用户询价请求表
DROP TABLE IF EXISTS `bps_user_inquiry`;
CREATE TABLE `bps_user_inquiry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(36) DEFAULT NULL,
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP,
  `date_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_id` varchar(36) DEFAULT NULL COMMENT '询价用户号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '询价用户姓名',
  `company_id` varchar(36) DEFAULT NULL COMMENT '询价用户所在公司id',
  `company_name` varchar(50) DEFAULT NULL COMMENT '询价用户所在公司名称',
  `department_id` varchar(36) DEFAULT NULL COMMENT '询价用户所在部门id',
  `department_name` varchar(50) DEFAULT NULL COMMENT '询价用户所在部门名称',
  `deadline` datetime DEFAULT NULL COMMENT '失效时间',
  `source_system` varchar(36) DEFAULT NULL COMMENT '询价发起系统',
  `status` varchar(2) DEFAULT '00' COMMENT '状态(00待评估，01评估中，02已评估，99失效)',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `city_code` varchar(36) DEFAULT NULL COMMENT '城市',
  `assessment_price` decimal(10,2) DEFAULT NULL COMMENT '房屋最终估算价格',
  `assessment_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '出评估结果时间',
  `house_type` varchar(16) DEFAULT NULL COMMENT '房屋类型(别墅，公寓)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `serial_no` (`serial_no`),
  KEY `status` (`status`),
  CONSTRAINT `bps_user_inquiry_ibfk_1` FOREIGN KEY (`status`) REFERENCES `bps_inquiry_status` (`value`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户询价请求表';

#材料类型表
DROP TABLE IF EXISTS `bps_material_type`;
CREATE TABLE `bps_material_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `abbr_name` varchar(50) DEFAULT NULL COMMENT '简称',
  `full_name` varchar(50) DEFAULT NULL COMMENT '全称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bps_index_material_type_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='材料类型表';

#询价材料表
DROP TABLE IF EXISTS `bps_inquiry_material`;
CREATE TABLE `bps_inquiry_material` (
  `id` int(36) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `serial_no` varchar(36) DEFAULT NULL COMMENT 'bps_user_inquiry表中serial_no',
  `material_type_code` varchar(36) DEFAULT NULL COMMENT '材料类型',
  `file_no` varchar(36) DEFAULT NULL COMMENT '上载材料文件号(文件关联系统返回唯一编号)',
  `file_name` varchar(100) DEFAULT NULL COMMENT '上载文件名',
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `company_code` varchar(36) DEFAULT NULL COMMENT '询价公司编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bps_index_house_material_code` (`code`),
  KEY `house_code` (`serial_no`),
  KEY `material_type_code` (`material_type_code`),
  CONSTRAINT `bps_inquiry_material_ibfk_1` FOREIGN KEY (`serial_no`) REFERENCES `bps_user_inquiry` (`serial_no`),
  CONSTRAINT `bps_inquiry_material_ibfk_2` FOREIGN KEY (`material_type_code`) REFERENCES `bps_material_type` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='询价上载材料表';
#算法表
DROP TABLE IF EXISTS `bps_algorithm`;
CREATE TABLE `bps_algorithm` (
  `id` int(36) AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '算法名称',
  `description` varchar(100) DEFAULT NULL COMMENT '算法描述',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '状态(1可用，0不可用)',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `date_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `bps_index_algorithm_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='算法表';

#估算计算结果表
DROP TABLE IF EXISTS `bps_assessment_result`;
CREATE TABLE `bps_assessment_result` (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) DEFAULT NULL,
  `assessment_price` decimal(18,2) unsigned zerofill NOT NULL COMMENT '房屋最终估算价格',
  `min_apply_time` datetime NOT NULL COMMENT '依赖询价公司最早报价时间（用于对比是否在时间阈值内）',
  `algorithm_code` varchar(36) NOT NULL COMMENT '计算使用的算法',
  `algorithm_method` varchar(50) NOT NULL COMMENT '配置表有关算法的配置项名称',
  `percen_diff_threshold` varchar(5) NOT NULL COMMENT '依赖的差价阈值百分比',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `date_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`),
  KEY `algorithm_code` (`algorithm_code`),
  CONSTRAINT `bps_house_assessment_result_ibfk_1` FOREIGN KEY (`algorithm_code`) REFERENCES `bps_algorithm` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='估算计算结果表';

#询价流水与询价结果关联表
DROP TABLE IF EXISTS `bps_inquiry_result_relation`;
CREATE TABLE `bps_inquiry_result_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(36) DEFAULT NULL COMMENT 'bps_user_inquiry表中serial_no',
  `result_code` varchar(36) DEFAULT NULL COMMENT 'bps_house_assessment_result中code',
  PRIMARY KEY (`id`),
  KEY `serial_no` (`serial_no`),
  KEY `result_code` (`result_code`),
  CONSTRAINT `bps_inquiry_result_relation_ibfk_1` FOREIGN KEY (`serial_no`) REFERENCES `bps_user_inquiry` (`serial_no`),
  CONSTRAINT `bps_inquiry_result_relation_ibfk_2` FOREIGN KEY (`result_code`) REFERENCES `bps_assessment_result` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='询价请求与计算结果关联表';

#对各个公司询价请求状态表
DROP TABLE IF EXISTS `bps_inquiry_apply_status`;
CREATE TABLE `bps_inquiry_apply_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `value` varchar(3) DEFAULT NULL COMMENT '状态值',
  `name` varchar(36) DEFAULT NULL COMMENT '状态名',
  `status` varchar(1) DEFAULT '1' COMMENT '是否可用(1可用，0不可用)',
  `remark` varchar(255) DEFAULT NULL COMMENT '说明字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `value` (`value`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

#各询价公司询价请求表
DROP TABLE IF EXISTS `bps_company_inquiry_apply`;
CREATE TABLE `bps_company_inquiry_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) DEFAULT NULL,
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP,
  `city_code` varchar(36) DEFAULT NULL,
  `house_type` varchar(16) DEFAULT NULL COMMENT '房屋类型(别墅，公寓)',
  `plots_code` varchar(36) DEFAULT NULL COMMENT '小区id(同询价公司一致)',
  `plots_name` varchar(100) DEFAULT NULL COMMENT '小区名',
  `unit_code` varchar(36) DEFAULT NULL COMMENT '楼栋id(同询价公司一致)',
  `unit_no` varchar(100) DEFAULT NULL COMMENT '楼栋号',
  `room_code` varchar(16) DEFAULT NULL COMMENT '房间id(同询价公司一致)',
  `room_no` varchar(100) DEFAULT NULL COMMENT '房号',
  `area` decimal(18,2) DEFAULT NULL COMMENT '房屋面积',
  `total_floor` int(3) DEFAULT NULL COMMENT '总楼层',
  `current_floor` int(3) DEFAULT NULL COMMENT '所在层',
  `towards` varchar(255) DEFAULT NULL COMMENT '房屋朝向(东:1 南:2 西:3 北:4)',
  `completed_year` varchar(36) DEFAULT NULL COMMENT '竣工日期',
  `company_code` varchar(36) DEFAULT NULL COMMENT '询价公司编号',
  `price` decimal(10,2) DEFAULT NULL COMMENT '询价公司估值',
  `price_time` datetime DEFAULT NULL COMMENT '获取价格时间',
  `auto_price` varchar(1) NOT NULL DEFAULT '1' COMMENT '是否自动询价(0人工录入值,1 询价公司取值)',
  `deadline` datetime DEFAULT NULL COMMENT '失效时间',
  `status` varchar(2) DEFAULT '00' COMMENT '状态(00待评估，01评估中，02已评估，99失效)',
  `area_code` varchar(50) DEFAULT NULL COMMENT '行政区域',
  `address` varchar(100) DEFAULT NULL COMMENT '房屋地址',
  `area_name` varchar(50) DEFAULT NULL COMMENT '行政区域名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `status` (`status`),
  CONSTRAINT `bps_company_inquiry_apply_ibfk_1` FOREIGN KEY (`status`) REFERENCES `bps_inquiry_apply_status` (`value`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='各询价公司询价请求表';

#询价请求与询价公司结果关系表
DROP TABLE IF EXISTS `bps_inquiry_apply_relation`;
CREATE TABLE `bps_inquiry_apply_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial_no` varchar(36) DEFAULT NULL,
  `apply_code` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `serial_no` (`serial_no`),
  KEY `apply_code` (`apply_code`),
  CONSTRAINT `bps_inquiry_apply_relation_ibfk_1` FOREIGN KEY (`serial_no`) REFERENCES `bps_user_inquiry` (`serial_no`),
  CONSTRAINT `bps_inquiry_apply_relation_ibfk_2` FOREIGN KEY (`apply_code`) REFERENCES `bps_company_inquiry_apply` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='询价请求与询价公司结果关系表';

#计算结果-询价返回关系表
DROP TABLE IF EXISTS `bps_result_apply_relation`;
CREATE TABLE `bps_result_apply_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `result_code` varchar(36) DEFAULT NULL,
  `apply_code` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `result_code` (`result_code`),
  KEY `apply_code` (`apply_code`),
  CONSTRAINT `bps_result_apply_relation_ibfk_1` FOREIGN KEY (`result_code`) REFERENCES `bps_assessment_result` (`code`),
  CONSTRAINT `bps_result_apply_relation_ibfk_2` FOREIGN KEY (`apply_code`) REFERENCES `bps_company_inquiry_apply` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='计算结果-询价返回关系表';


#房屋类型表
DROP TABLE IF EXISTS `bps_house_type`;
CREATE TABLE `bps_house_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(5) NOT NULL COMMENT '类型代码',
  `name` varchar(100) DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='房屋类型表';

#估价公司表
DROP TABLE IF EXISTS `bps_appraisal_company`;
CREATE TABLE `bps_appraisal_company` (
  `id` int(36) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `date_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  `abbr_name` varchar(20) DEFAULT NULL COMMENT '估价公司简称',
  `full_name` varchar(100) DEFAULT NULL COMMENT '估价公司全称',
  `cooperation_type` varchar(1) NOT NULL DEFAULT '0' COMMENT '估价公司合作类型（0人工；1 极速，2 极速+人工）',
  `villa_turn_artificial` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '别墅是否转人工(1转人工)',
  `max_area` double(18,2) NOT NULL DEFAULT '140.00' COMMENT '最大面积转人工',
  `api_url` varchar(255) NOT NULL COMMENT '接口API地址',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '状态(0不可用，1可用)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `bps_index_app_company_code` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='估价公司表';

#城市表
DROP TABLE IF EXISTS `bps_city`;
CREATE TABLE `bps_city` (
  `id` int(36) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(10) NOT NULL COMMENT '国标代码',
  `abbr_name` varchar(20) NOT NULL COMMENT '城市简称',
  `full_name` varchar(100) NOT NULL COMMENT '城市全称',
  `wu_code` varchar(36) DEFAULT NULL COMMENT '世联id',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '状态(0不可用，1可用)',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `date_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `bps_index_city_code` (`code`) USING BTREE,
  UNIQUE KEY `wu_code` (`wu_code`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='城市表';

#估价公司与城市关联表
DROP TABLE IF EXISTS `bps_company_city`;
CREATE TABLE `bps_company_city` (
  `id` int(36) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `company_code` varchar(36) NOT NULL COMMENT '关联公司',
  `city_code` varchar(36) NOT NULL COMMENT '关联城市',
  `status` varchar(1) CHARACTER SET utf16le NOT NULL DEFAULT '1' COMMENT '状态(0不可用，1可用)',
  `order_no` int(2) DEFAULT NULL COMMENT '排序(0为选中录单公司)',
  `date_created` datetime NOT NULL COMMENT '数据创建时间',
  `date_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `company_code` (`company_code`),
  KEY `city_code` (`city_code`),
  CONSTRAINT `bps_company_city_ibfk_1` FOREIGN KEY (`company_code`) REFERENCES `bps_appraisal_company` (`code`),
  CONSTRAINT `bps_company_city_ibfk_2` FOREIGN KEY (`city_code`) REFERENCES `bps_city` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='估价公司与城市关联表';

#时间阈值表
DROP TABLE IF EXISTS `bps_threshold`;
CREATE TABLE `bps_threshold` (
  `id` int(36) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL,
  `value` int(5) unsigned NOT NULL DEFAULT '168' COMMENT '时间阈值',
  `city_code` varchar(36) NOT NULL COMMENT '关联城市',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '状态(0不可用，1可用)',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `date_modified` datetime NOT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bps_index_threshold_code` (`code`),
  KEY `city_code` (`city_code`),
  CONSTRAINT `bps_threshold_ibfk_1` FOREIGN KEY (`city_code`) REFERENCES `bps_city` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='时间阈值表';

#小区表
DROP TABLE IF EXISTS `bps_plots`;
CREATE TABLE `bps_plots` (
  `id` int(36) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) NOT NULL COMMENT '维护询价公司小区code',
  `plot_code` varchar(36) DEFAULT NULL COMMENT '维护询价公司小区code',
  `name` varchar(50) DEFAULT NULL COMMENT '小区名称',
  `company_code` varchar(36) DEFAULT NULL COMMENT '询价公司编号',
  `city_code` varchar(36) NOT NULL COMMENT '关联城市',
  `address` varchar(100) DEFAULT NULL COMMENT '小区地址',
  `year_built` varchar(25) DEFAULT NULL COMMENT '建成年代',
  `house_type` varchar(20) DEFAULT NULL COMMENT '房屋类型(别墅，普通住宅，商业住宅)',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '状态(0不可用，1可用)',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `date_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `plot_code` (`plot_code`,`company_code`),
  KEY `city_code` (`city_code`),
  CONSTRAINT `bps_plots_ibfk_1` FOREIGN KEY (`city_code`) REFERENCES `bps_city` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小区表';


#房屋信息表
DROP TABLE IF EXISTS `bps_house_info`;
CREATE TABLE `bps_house_info` (
  `id` int(36) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(36) DEFAULT NULL,
  `city_code` varchar(36) NOT NULL COMMENT '关联城市',
  `city_name` varchar(100) DEFAULT NULL COMMENT '关联城市名称',
  `plots_code` varchar(36) NOT NULL DEFAULT '' COMMENT '关联小区id(各估价公司不同)',
  `plot_name` varchar(255) DEFAULT NULL COMMENT '小区名称',
  `address` varchar(100) DEFAULT NULL COMMENT '房屋地址',
  `unit_code` varchar(16) DEFAULT NULL COMMENT '楼栋id(各估价公司不同)',
  `unit_no` varchar(100) NOT NULL COMMENT '楼栋号',
  `room_code` varchar(16) DEFAULT NULL COMMENT '房间id(各估价公司不同)',
  `room_no` varchar(100) NOT NULL COMMENT '房号',
  `area` decimal(18,2) unsigned NOT NULL COMMENT '房屋面积',
  `house_type` varchar(16) NOT NULL COMMENT '房屋类型(别墅，公寓)',
  `total_floor` int(3) DEFAULT NULL COMMENT '总楼层',
  `current_floor` int(3) DEFAULT NULL COMMENT '当前楼层',
  `company_code` varchar(36) DEFAULT NULL COMMENT '询价公司编号',
  `status` varchar(1) NOT NULL DEFAULT '1' COMMENT '状态(1可用，0不可用)',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `date_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `bps_index_house_code` (`code`),
  KEY `city_code` (`city_code`),
  KEY `bps_house_info_ibfk_2` (`plots_code`),
  CONSTRAINT `bps_house_info_ibfk_1` FOREIGN KEY (`city_code`) REFERENCES `bps_city` (`code`),
  CONSTRAINT `bps_house_info_ibfk_2` FOREIGN KEY (`plots_code`) REFERENCES `bps_plots` (`plot_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房屋信息表';

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

