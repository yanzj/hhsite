use plms;

CREATE TABLE `plms_overdue_reduce_voucher` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `file_code` varchar(36) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `schedule_detail_code` varchar(36) DEFAULT NULL,
  `file_name` varchar(100) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `file_suffix` varchar(15) DEFAULT NULL COMMENT '扩展名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;