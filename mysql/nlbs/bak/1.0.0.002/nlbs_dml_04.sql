use nlbs;

# 插入进件状态
INSERT INTO `nlbs`.`nlbs_apply_loan_status` (`id`, `code`, `name`, `bms_code`, `bms_name`) VALUES ('1', '01', '暂存', 'a001', NULL);
INSERT INTO `nlbs`.`nlbs_apply_loan_status` (`id`, `code`, `name`, `bms_code`, `bms_name`) VALUES ('2', '02', '录单中', 'a002', NULL);
INSERT INTO `nlbs`.`nlbs_apply_loan_status` (`id`, `code`, `name`, `bms_code`, `bms_name`) VALUES ('3', '03', '审批中', 'a003', NULL);
INSERT INTO `nlbs`.`nlbs_apply_loan_status` (`id`, `code`, `name`, `bms_code`, `bms_name`) VALUES ('4', '04', '作废', 'a004', NULL);

# 插入房屋类型
INSERT INTO `nlbs`.`nlbs_house_type` (`id`, `code`, `name`) VALUES ('1', '001', '公寓');
INSERT INTO `nlbs`.`nlbs_house_type` (`id`, `code`, `name`) VALUES ('2', '002', '别墅');

# 插入操作类型
INSERT INTO `nlbs`.`nlbs_operation_type` (`id`, `code`, `name`) VALUES ('1', '01', '暂存');
INSERT INTO `nlbs`.`nlbs_operation_type` (`id`, `code`, `name`) VALUES ('2', '02', '提交');
INSERT INTO `nlbs`.`nlbs_operation_type` (`id`, `code`, `name`) VALUES ('3', '11', '提交评估');
INSERT INTO `nlbs`.`nlbs_operation_type` (`id`, `code`, `name`) VALUES ('4', '12', '录入评估价');
