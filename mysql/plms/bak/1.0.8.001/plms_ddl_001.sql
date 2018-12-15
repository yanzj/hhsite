use plms;

alter table plms_paid_info add is_ahead_repayment tinyint(1) comment "是否提前还款(0、否;1、是)";

alter table plms_repayment_schedule_detail add repaymented_ahead_interest_penalty DECIMAL(20,2) comment "应还提前还款利息违约金";

alter table plms_repayment_schedule_detail add repaymented_ahead_service_fee_penalty DECIMAL(20,2) comment "应还提前还款服务费违约金";

alter table plms_repayment_schedule add repaymented_ahead_interest_penalty DECIMAL(20,2) comment "应还提前还款利息违约金";

alter table plms_repayment_schedule add repaymented_ahead_service_fee_penalty DECIMAL(20,2) comment "应还提前还款服务费违约金";

CREATE TABLE `plms_advance_repayment_voucher` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(36) NOT NULL,
  `file_code` varchar(36) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `contract_code` varchar(36) DEFAULT NULL,
  `file_name` varchar(100) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `file_suffix` varchar(15) DEFAULT NULL COMMENT '扩展名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;