use oas;

-- ----------------------------
-- 登录信息缓存表
-- ----------------------------
DROP TABLE IF EXISTS `oas_login_info`;
CREATE TABLE `oas_login_info` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `user_id` varchar(40) NOT NULL COMMENT '用户编号',
  `user_name` varchar(40) DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `full_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `birth_addr` varchar(100) DEFAULT NULL COMMENT '出生地',
  `nick` varchar(50) DEFAULT NULL COMMENT '昵称',
  `head_img` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `system_timestamp` varchar(20) NOT NULL COMMENT '登录时主机端时间戳',
  `token` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `display_detail_set` varchar(2) DEFAULT '0' COMMENT '显示消息详情(0-显示 1-不显示)',
  `login_status` varchar(2) DEFAULT NULL COMMENT '登录状态：0-登出，1-登录',
  `device_token` varchar(100) DEFAULT NULL COMMENT '推送消息设备标识号',
  `channel` varchar(20) DEFAULT NULL COMMENT '最新渠道',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录信息表';

-- ----------------------------
-- 省份表
-- ----------------------------
DROP TABLE IF EXISTS `oas_province`;
CREATE TABLE `oas_province` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '国标码',
  `abbr_name` varchar(20) DEFAULT NULL COMMENT '省份简称',
  `full_name` varchar(20) DEFAULT NULL COMMENT '省份全称',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `order_by` varchar(36) DEFAULT NULL COMMENT '排序顺序位',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省份表';

-- ----------------------------
-- 城市表
-- ----------------------------
DROP TABLE IF EXISTS `oas_city`;
CREATE TABLE `oas_city` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '国标码',
  `abbr_name` varchar(20) DEFAULT NULL COMMENT '城市简称',
  `full_name` varchar(20) DEFAULT NULL COMMENT '城市全称',
  `parent_code` varchar(36) NOT NULL COMMENT '所属省份的国标码',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `order_by` varchar(36) DEFAULT NULL COMMENT '排序顺序位',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `parent_code` (`parent_code`),
  KEY `code` (`code`),
  CONSTRAINT `oas_city_ibfk_1` FOREIGN KEY (`parent_code`) REFERENCES `oas_province` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市表';

-- ----------------------------
-- 行政区表
-- ----------------------------
DROP TABLE IF EXISTS `oas_district`;
CREATE TABLE `oas_district` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '国标码',
  `abbr_name` varchar(20) DEFAULT NULL COMMENT '行政区简称',
  `full_name` varchar(20) DEFAULT NULL COMMENT '行政区全称',
  `parent_code` varchar(36) NOT NULL COMMENT '所属城市的国标码',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `order_by` varchar(36) DEFAULT NULL COMMENT '排序顺序位',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `parent_code` (`parent_code`),
  KEY `code` (`code`),
  CONSTRAINT `oas_district_ibfk_1` FOREIGN KEY (`parent_code`) REFERENCES `oas_city` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区表';


-- ----------------------------
-- 区域表
-- ----------------------------
DROP TABLE IF EXISTS `oas_area`;
CREATE TABLE `oas_area` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `city_code` varchar(36) NOT NULL COMMENT '所属城市编码',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `order_by` varchar(36) DEFAULT NULL COMMENT '排序顺序位',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `city_code` (`city_code`) USING BTREE,
  KEY `code` (`code`),
  CONSTRAINT `oas_area_ibfk_1` FOREIGN KEY (`city_code`) REFERENCES `oas_city` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- 小组表
-- ----------------------------
DROP TABLE IF EXISTS `oas_group`;
CREATE TABLE `oas_group` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `area_code` varchar(36) NOT NULL COMMENT '所属区域编码',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `order_by` varchar(36) DEFAULT NULL COMMENT '排序顺序位',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `area_code` (`area_code`),
  KEY `code` (`code`),
  CONSTRAINT `oas_group_ibfk_1` FOREIGN KEY (`area_code`) REFERENCES `oas_area` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小组表';

-- ----------------------------
-- 用户所属小组表
-- ----------------------------
DROP TABLE IF EXISTS `oas_user_group`;
CREATE TABLE `oas_user_group` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `user_id` varchar(36) NOT NULL COMMENT '用户编码(UM系统编码)',
  `group_id` varchar(36) NOT NULL COMMENT '小组编码',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `oas_user_group_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `oas_group` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员所属小组表';

-- ----------------------------
-- 渠道表
-- ----------------------------
DROP TABLE IF EXISTS `oas_distributor`;
CREATE TABLE `oas_distributor` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `abbr_name` varchar(25) NOT NULL COMMENT '渠道简称',
  `full_name` varchar(100) NOT NULL COMMENT '渠道全称',
  `type` varchar(2) NOT NULL COMMENT '渠道类型(01、直营；02、外部)',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `order_by` varchar(36) DEFAULT NULL COMMENT '排序顺序位',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='渠道表';


-- ----------------------------
-- 产品表
-- ----------------------------
DROP TABLE IF EXISTS `oas_product`;
CREATE TABLE `oas_product` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `name` varchar(25) NOT NULL COMMENT '产品名称',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `order_by` varchar(36) DEFAULT NULL COMMENT '排序顺序位',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
-- 材料类型表
-- ----------------------------
DROP TABLE IF EXISTS `oas_material_type`;
CREATE TABLE `oas_material_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(2) NOT NULL COMMENT '唯一标识编号',
  `name` varchar(50) NOT NULL COMMENT '材料类型名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='材料类型表';

-- ----------------------------
-- 附件材料表
-- ----------------------------
DROP TABLE IF EXISTS `oas_material`;
CREATE TABLE `oas_material` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `batch_no` varchar(36) NOT NULL COMMENT '上传批次号',
  `file_code` varchar(36) NOT NULL COMMENT '材料（文件）编号',
  `file_name` varchar(100) NOT NULL COMMENT '原文件名',
  `file_suffix` varchar(10) DEFAULT NULL COMMENT '文件后缀名',
  `file_type` varchar(2) NOT NULL COMMENT '文件类型(01、图片；02、视频；03、其他)',
  `material_type` varchar(2) NOT NULL COMMENT '材料类型',
  `upload_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '材料上载时间',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `material_type` (`material_type`),
  KEY `code` (`code`),
  CONSTRAINT `oas_material_ibfk_1` FOREIGN KEY (`material_type`) REFERENCES `oas_material_type` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='附件材料表';

-- ----------------------------
-- 操作类型表
-- ----------------------------
DROP TABLE IF EXISTS `oas_operation_type`;
CREATE TABLE `oas_operation_type` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(2) NOT NULL COMMENT '唯一标识编号',
  `name` varchar(50) NOT NULL COMMENT '操作类型名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作类型表';

-- ----------------------------
-- 任务状态表
-- ----------------------------
DROP TABLE IF EXISTS `oas_task_status`;
CREATE TABLE `oas_task_status` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(2) NOT NULL COMMENT '唯一标识编号',
  `name` varchar(50) NOT NULL COMMENT '状态名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务状态表';

-- ----------------------------
-- 任务信息表
-- ----------------------------
DROP TABLE IF EXISTS `oas_task`;
CREATE TABLE `oas_task` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `certificate_number` varchar(50) DEFAULT NULL COMMENT '产证编号',
  `province_code` varchar(36) NOT NULL COMMENT '省份编号',
  `city_code` varchar(36) NOT NULL COMMENT '城市编号',
  `district_code` varchar(36) NOT NULL COMMENT '行政区编号',
  `address` varchar(250) NOT NULL COMMENT '房产详细地址',
  `product_code` varchar(36) DEFAULT NULL COMMENT '产品编号',
  `loan_amount` varchar(50) DEFAULT NULL COMMENT '贷款金额（万元）',
  `distributor_code` varchar(36) NOT NULL COMMENT '渠道编号',
  `apply_agent_name` varchar(50) DEFAULT NULL COMMENT '进件业务员姓名',
  `apply_agent_phone` varchar(20) DEFAULT NULL COMMENT '进件业务员电话',
  `loaner_name` varchar(50) NOT NULL COMMENT '借款人姓名',
  `loaner_phone` varchar(20) NOT NULL COMMENT '借款人电话',
  `task_creater_code` varchar(36) NOT NULL COMMENT '任务创建者编码',
  `task_creater_name` varchar(50) NOT NULL COMMENT '任务创建者姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '任务创建时间',
  `appointment_time` datetime DEFAULT NULL COMMENT '预约时间',
  `assigner_code` varchar(36) DEFAULT NULL COMMENT '指派人编码',
  `assigner_name` varchar(20) DEFAULT NULL COMMENT '指派人姓名',
  `assigner_phone` varchar(20) DEFAULT NULL COMMENT '指派人电话',
  `assigne_time` datetime DEFAULT NULL COMMENT '指派时间',
  `assignee_code` varchar(36) DEFAULT NULL COMMENT '被指派人编码',
  `assignee_name` varchar(20) DEFAULT NULL COMMENT '被指派人姓名',
  `assignee_phone` varchar(20) DEFAULT NULL COMMENT '被指派人电话',
  `close_reason` varchar(400) DEFAULT NULL COMMENT '关闭原因',
  `change_assigne_reason` varchar(400) DEFAULT NULL COMMENT '任务改派原因',
  `change_date_reason` varchar(400) DEFAULT NULL COMMENT '任务改约原因',
  `activate_reason` varchar(400) DEFAULT NULL COMMENT '任务激活原因',
  `have_linked` varchar(2) NOT NULL DEFAULT '00' COMMENT '是否已链接（00-未链接，01-已链接）',
  `loan_id` varchar(15) DEFAULT NULL COMMENT '进件编号',
  `task_remark` varchar(400) DEFAULT NULL COMMENT '任务备注',
  `verification_remark` varchar(400) DEFAULT NULL COMMENT '下户备注',
  `task_status` varchar(2) NOT NULL COMMENT '任务状态',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `province_code` (`province_code`),
  KEY `city_code` (`city_code`),
  KEY `district_code` (`district_code`),
  KEY `product_code` (`product_code`),
  KEY `distributor_code` (`distributor_code`),
  KEY `code` (`code`),
  CONSTRAINT `oas_task_ibfk_1` FOREIGN KEY (`province_code`) REFERENCES `oas_province` (`code`),
  CONSTRAINT `oas_task_ibfk_2` FOREIGN KEY (`city_code`) REFERENCES `oas_city` (`code`),
  CONSTRAINT `oas_task_ibfk_3` FOREIGN KEY (`district_code`) REFERENCES `oas_district` (`code`),
  CONSTRAINT `oas_task_ibfk_4` FOREIGN KEY (`product_code`) REFERENCES `oas_product` (`code`),
  CONSTRAINT `oas_task_ibfk_5` FOREIGN KEY (`distributor_code`) REFERENCES `oas_distributor` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务基本信息表';
-- ----------------------------
-- 任务变更历史表
-- ----------------------------
DROP TABLE IF EXISTS `oas_task_history`;
CREATE TABLE `oas_task_history` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `task_code` varchar(36) NOT NULL COMMENT '任务编码',
  `certificate_number` varchar(50) DEFAULT NULL COMMENT '产证编号',
  `province_code` varchar(36) NOT NULL COMMENT '省份编号',
  `city_code` varchar(36) NOT NULL COMMENT '城市编号',
  `district_code` varchar(36) NOT NULL COMMENT '行政区编号',
  `address` varchar(250) NOT NULL COMMENT '房产详细地址',
  `product_code` varchar(36) DEFAULT NULL COMMENT '产品编号',
  `loan_amount` varchar(50) DEFAULT NULL COMMENT '贷款金额（万元）',
  `distributor_code` varchar(36) NOT NULL COMMENT '渠道编号',
  `apply_agent_name` varchar(50) DEFAULT NULL COMMENT '进件业务员姓名',
  `apply_agent_phone` varchar(20) DEFAULT NULL COMMENT '进件业务员电话',
  `loaner_name` varchar(50) NOT NULL COMMENT '借款人姓名',
  `loaner_phone` varchar(20) NOT NULL COMMENT '借款人电话',
  `task_creater_code` varchar(36) NOT NULL COMMENT '任务创建者编码',
  `task_creater_name` varchar(50) NOT NULL COMMENT '任务创建者姓名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '任务创建时间',
  `appointment_time` datetime DEFAULT NULL COMMENT '预约时间',
  `assigner_code` varchar(36) DEFAULT NULL COMMENT '指派人编码',
  `assigner_name` varchar(20) DEFAULT NULL COMMENT '指派人姓名',
  `assigner_phone` varchar(20) DEFAULT NULL COMMENT '指派人电话',
  `assigne_time` datetime DEFAULT NULL COMMENT '指派时间',
  `assignee_code` varchar(36) DEFAULT NULL COMMENT '被指派人编码',
  `assignee_name` varchar(20) DEFAULT NULL COMMENT '被指派人姓名',
  `assignee_phone` varchar(20) DEFAULT NULL COMMENT '被指派人电话',
  `close_reason` varchar(400) DEFAULT NULL COMMENT '关闭原因',
  `change_assigne_reason` varchar(400) DEFAULT NULL COMMENT '任务改派原因',
  `change_date_reason` varchar(400) DEFAULT NULL COMMENT '任务改约原因',
  `activate_reason` varchar(400) DEFAULT NULL COMMENT '任务激活原因',
  `have_linked` varchar(2) NOT NULL DEFAULT '00' COMMENT '是否已链接（00-未链接，01-已链接）',
  `loan_id` varchar(15) DEFAULT NULL COMMENT '进件编号',
  `task_remark` varchar(400) DEFAULT NULL COMMENT '任务备注',
  `verification_remark` varchar(400) DEFAULT NULL COMMENT '下户备注',
  `task_status` varchar(2) NOT NULL COMMENT '任务状态',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `province_code` (`province_code`),
  KEY `city_code` (`city_code`),
  KEY `district_code` (`district_code`),
  KEY `product_code` (`product_code`),
  KEY `distributor_code` (`distributor_code`),
  KEY `task_code` (`task_code`),
  KEY `code` (`code`),
  CONSTRAINT `oas_task_history_ibfk_1` FOREIGN KEY (`province_code`) REFERENCES `oas_province` (`code`),
  CONSTRAINT `oas_task_history_ibfk_2` FOREIGN KEY (`city_code`) REFERENCES `oas_city` (`code`),
  CONSTRAINT `oas_task_history_ibfk_3` FOREIGN KEY (`district_code`) REFERENCES `oas_district` (`code`),
  CONSTRAINT `oas_task_history_ibfk_4` FOREIGN KEY (`product_code`) REFERENCES `oas_product` (`code`),
  CONSTRAINT `oas_task_history_ibfk_5` FOREIGN KEY (`distributor_code`) REFERENCES `oas_distributor` (`code`),
  CONSTRAINT `oas_task_history_ibfk_6` FOREIGN KEY (`task_code`) REFERENCES `oas_task` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='任务历史变更表';

-- ----------------------------
-- 任务材料关联表
-- ----------------------------
DROP TABLE IF EXISTS `oas_task_material`;
CREATE TABLE `oas_task_material` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `material_code` varchar(36) NOT NULL COMMENT '关联的材料编码',
  `task_code` varchar(36) NOT NULL COMMENT '所属任务编码',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `material_code` (`material_code`),
  KEY `task_code` (`task_code`),
  CONSTRAINT `oas_task_material_ibfk_1` FOREIGN KEY (`material_code`) REFERENCES `oas_material` (`code`),
  CONSTRAINT `oas_task_material_ibfk_2` FOREIGN KEY (`task_code`) REFERENCES `oas_task` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务关联材料表';

-- ----------------------------
-- 操作历史表
-- ----------------------------
DROP TABLE IF EXISTS `oas_operation_history`;
CREATE TABLE `oas_operation_history` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `task_code` varchar(36) NOT NULL COMMENT '任务编码',
  `task_history_code` varchar(36) NOT NULL COMMENT '任务历史编码',
  `operation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operation_timestamp` varchar(20) NOT NULL COMMENT '操作时间戳',
  `operater_code` varchar(36) NOT NULL COMMENT '操作人编码',
  `operater_name` varchar(20) NOT NULL COMMENT '操作人姓名',
  `operater_phone` varchar(20) NOT NULL COMMENT '操作人电话',
  `operation_type_code` varchar(2) NOT NULL COMMENT '操作类型编码',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `task_code` (`task_code`),
  KEY `task_history_code` (`task_history_code`),
  KEY `operation_type_code` (`operation_type_code`),
  KEY `code` (`code`),
  CONSTRAINT `oas_operation_history_ibfk_1` FOREIGN KEY (`task_code`) REFERENCES `oas_task` (`code`),
  CONSTRAINT `oas_operation_history_ibfk_2` FOREIGN KEY (`task_history_code`) REFERENCES `oas_task_history` (`code`),
  CONSTRAINT `oas_operation_history_ibfk_3` FOREIGN KEY (`operation_type_code`) REFERENCES `oas_operation_type` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作历史表';

-- ----------------------------
-- 审批表
-- ----------------------------
DROP TABLE IF EXISTS `oas_approval`;
CREATE TABLE `oas_approval` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `task_code` varchar(36) NOT NULL COMMENT '任务编码',
  `operation_history_code` varchar(36) NOT NULL COMMENT '操作历史编码',
  `approval_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审批时间',
  `approver_code` varchar(36) NOT NULL COMMENT '审批人编码',
  `approver_name` varchar(20) NOT NULL COMMENT '审批人姓名',
  `approver_phone` varchar(20) NOT NULL COMMENT '审批人电话',
  `approval_result` varchar(20) NOT NULL COMMENT '审批结果（01-通过；02-驳回）',
  `approval_opinions` varchar(400) DEFAULT NULL COMMENT '审批意见',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `task_code` (`task_code`),
  KEY `operation_history_code` (`operation_history_code`),
  CONSTRAINT `oas_approval_ibfk_1` FOREIGN KEY (`task_code`) REFERENCES `oas_task` (`code`),
  CONSTRAINT `oas_approval_ibfk_2` FOREIGN KEY (`operation_history_code`) REFERENCES `oas_operation_history` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批表';


-- ----------------------------
-- 验证码信息表
-- ----------------------------
DROP TABLE IF EXISTS `oas_verify_info`;
CREATE TABLE `oas_verify_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL COMMENT '唯一标识编号',
  `mobile_no` varchar(18) NOT NULL COMMENT '手机号码',
  `verify_code` varchar(10) NOT NULL COMMENT '验证码',
  `verify_type` varchar(10) NOT NULL COMMENT '验证码类型',
  `effective_time` varchar(40) NOT NULL COMMENT '有效时间（单位：分钟）',
  `sign_no` varchar(40) NOT NULL COMMENT '签名',
  `template_no` varchar(40) NOT NULL COMMENT '模板号',
  `sms_status` varchar(2) NOT NULL COMMENT '短信状态',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_serno` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='验证码信息表';

-- ----------------------------
-- 序列号表
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL COMMENT '序列键名',
  `current_value` int(11) NOT NULL COMMENT '当前值',
  `increment` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
DROP TABLE IF EXISTS `qrtz_blob_triggres`;
CREATE TABLE `qrtz_blob_triggres` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggres_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



