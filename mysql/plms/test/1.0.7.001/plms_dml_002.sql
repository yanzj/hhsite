#账户类型初始化数据
INSERT INTO `plms`.`plms_fund_account_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('00', '宏获还款账户', '1', '2017-10-30 15:42:04', '2017-10-30 15:42:04');
INSERT INTO `plms`.`plms_fund_account_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('01', '资方账户', '1', '2017-10-30 16:44:22', '2017-10-30 15:42:04');
INSERT INTO `plms`.`plms_fund_account_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('02', '宏获保证金账户', '1', '2017-10-30 16:44:23', '2017-10-30 15:42:04');
INSERT INTO `plms`.`plms_fund_account_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('03', '渠道账户', '1', '2017-10-30 16:44:24', '2017-10-30 15:42:04');
INSERT INTO `plms`.`plms_fund_account_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('04', '业务经理账户', '1', '2017-10-30 15:43:07', '2017-10-30 15:43:07');
INSERT INTO `plms`.`plms_fund_account_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('05', '差额手续费账户', '1', '2017-10-30 16:44:24', '2017-10-30 15:42:04');
INSERT INTO `plms`.`plms_fund_account_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('06', '宏获垫付资方账户', '1', '2017-10-30 16:44:25', '2017-10-30 15:42:04');
#科目账户类型映射初始化数据
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1001', '应收保证金违约金', '00', '宏获还款账户', '1');
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1002', '应收服务费罚金', '00', '宏获还款账户', '1');
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1003', '应收利息罚金', '00', '宏获还款账户', '1');
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1004', '应收本金罚金', '00', '宏获还款账户', '1');
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1005', '应收保证金', '02', '宏获保证金账户', '1');
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1006', '应收服务费', '00', '宏获还款账户', '1');
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1007', '应收利息', '01', '资方账户', '1');
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1008', '应收本金', '01', '资方账户', '1');
INSERT INTO `plms`.`plms_subject_account_type_config` (`subject`, `subject_name`, `account_type_code`, `account_type_name`, `status`) VALUES ('1009', '应收差额手续费', '05', '差额手续费账户', '1');
#汇路信息初始化
INSERT INTO `plms`.`plms_route_info` (`code`, `english_abbr`, `route_name`, `status`, `create_date`, `modify_date`) VALUES ('kjtpay', 'kjtpay', '快捷通', '1', '2017-11-16 11:23:25', '2017-11-16 11:23:25');
INSERT INTO `plms`.`plms_route_info` (`code`, `english_abbr`, `route_name`, `status`, `create_date`, `modify_date`) VALUES ('baofoopay', 'baofoopay', '宝付', '1', '2017-11-16 11:23:37', '2017-11-16 11:23:37');
#收款方类型
INSERT INTO `plms`.`plms_pay_receiver_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('03', '资方', '1', '2017-11-12 13:46:35', '2017-11-15 11:13:08');
INSERT INTO `plms`.`plms_pay_receiver_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('02', '担保公司', '1', '2017-11-12 13:46:47', '2017-11-12 13:46:47');
INSERT INTO `plms`.`plms_pay_receiver_type` (`code`, `name`, `status`, `create_date`, `modify_date`) VALUES ('01', '保险公司', '1', '2017-11-12 13:46:57', '2017-11-15 11:13:09');
#代付费用科目表
INSERT INTO `plms`.`plms_pay_payment_fee_type` (`code`, `name`, `pay_receiver_type_code`, `status`) VALUES ('101', '保证金', '01', '1');
INSERT INTO `plms`.`plms_pay_payment_fee_type` (`code`, `name`, `pay_receiver_type_code`, `status`) VALUES ('102', '履约保证保险费', '01', '1');
INSERT INTO `plms`.`plms_pay_payment_fee_type` (`code`, `name`, `pay_receiver_type_code`, `status`) VALUES ('103', '险种2费用', '01', '1');
INSERT INTO `plms`.`plms_pay_payment_fee_type` (`code`, `name`, `pay_receiver_type_code`, `status`) VALUES ('201', '担保费', '02', '1');
INSERT INTO `plms`.`plms_pay_payment_fee_type` (`code`, `name`, `pay_receiver_type_code`, `status`) VALUES ('202', '保证金', '02', '1');
INSERT INTO `plms`.`plms_pay_payment_fee_type` (`code`, `name`, `pay_receiver_type_code`, `status`) VALUES ('301', '服务费', '03', '1');
INSERT INTO `plms`.`plms_pay_payment_fee_type` (`code`, `name`, `pay_receiver_type_code`, `status`) VALUES ('302', '保证金', '03', '1');
INSERT INTO `plms`.`plms_pay_payment_fee_type` (`code`, `name`, `pay_receiver_type_code`, `status`) VALUES ('303', '收件收据服务费', '03', '1');
#新增支付申请单提交锁
INSERT INTO `plms_sysparam_info` (`code`, `syscode`, `sysname`, `item_id`, `item_name`, `item_cval`, `item_ival`, `execute_time`, `item_desc`, `remark`, `remark1`, `remark2`) VALUES ('20171123171212000000000000001', 'plms', '贷后系统', 'payApplyInfo_Submit_Lock', '提交支付申请单加锁', 'N', '0', '2017-11-23 17:05:43', 'Y加锁成功，N未被锁', '', '', '');
#用户角色支付申请单状态映射