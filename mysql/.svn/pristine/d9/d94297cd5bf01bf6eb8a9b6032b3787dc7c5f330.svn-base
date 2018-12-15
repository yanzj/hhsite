use plms;
/*还款计划明细表增加扣款类型*/
alter table plms_repayment_detail add payment_method varchar(2) comment "还款操作方式(01、自动；02、手动)";

/*新增还款明细临时表*/
DROP TABLE IF EXISTS `plms_repayment_detail_temp`;
CREATE TABLE `plms_repayment_detail_temp` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id,主键',
  `code` varchar(36) NOT NULL COMMENT 'UUID',
  `subject` int(5) DEFAULT NULL COMMENT '科目',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '金额',
  `detail_code` varchar(36) NOT NULL COMMENT '还款明细表',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(0、无效；1、有效)',
  `time_happened` datetime DEFAULT NULL  COMMENT '发生时间',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `batch_code` varchar(36) DEFAULT NULL,
  `payment_method` varchar(2) DEFAULT NULL COMMENT '还款操作方式(01、自动；02、手动)',
  `schedule_detail_code` varchar(36) DEFAULT NULL COMMENT '还款计划明细表code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='还款计划明细表';