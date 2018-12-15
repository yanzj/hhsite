use nlbs;

#待办任务类型表
DROP TABLE IF EXISTS `nlbs_todo_type`;
CREATE TABLE `nlbs_todo_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `code` varchar(3) NOT NULL COMMENT '待办任务类型编码',
  `name` varchar(255) DEFAULT NULL COMMENT '待办任务类型描述',
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='待办任务类型表';

#待办任务列表
DROP TABLE IF EXISTS `nlbs_todo`;
CREATE TABLE `nlbs_todo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `date_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '任务创建时间（分配时间）',
  `content` varchar(255) DEFAULT NULL COMMENT '待办任务详情',
  `todo_type` varchar(3) DEFAULT NULL COMMENT '待办任务类型',
  `user_no` varchar(36) DEFAULT NULL COMMENT '待处理人',
  `bps_code` varchar(36) DEFAULT NULL COMMENT '询价系统序列号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_no_bps_code` (`user_no`,`bps_code`) USING BTREE,
  KEY `todo_type` (`todo_type`),
  KEY `bps_code` (`bps_code`),
  CONSTRAINT `nlbs_todo_ibfk_1` FOREIGN KEY (`todo_type`) REFERENCES `nlbs_todo_type` (`code`),
  CONSTRAINT `nlbs_todo_ibfk_2` FOREIGN KEY (`user_no`) REFERENCES `nlbs_user` (`user_no`),
  CONSTRAINT `nlbs_todo_ibfk_3` FOREIGN KEY (`bps_code`) REFERENCES `nlbs_inquiry_apply` (`bps_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='待办任务列表';