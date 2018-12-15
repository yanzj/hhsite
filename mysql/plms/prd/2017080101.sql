

INSERT INTO `plms_account` VALUES ('6', '6', '10000000.00', '80.00', '9999886.00', '2.00', '2.00', '2017-08-10 14:37:10', '1', '2017-07-19', '2017-07-19', null, null, '0.00', null, null, null, null, null, '3');


INSERT INTO `plms_account_detail` VALUES ('8', '8', '200.00', '180.00', '9999886.00', '2.00', '2.00', '2018-10-11 15:41:40', '6', '2018-11-18 15:41:45', '8', '1', '1000.00', '2017-07-18', '2017-07-18', null, null, null, null, null, null, null, null, null, null, '01');
INSERT INTO `plms_account_detail` VALUES ('9', '9', '200.00', '180.00', '10000000.00', '2.00', '2.00', '2018-10-11 15:41:40', '6', '2018-11-18 15:41:45', '9', '1', '1000.00', '2017-07-18', '2017-07-18', null, null, null, null, null, null, null, null, null, null, '01');



truncate table plms_account_info;
INSERT INTO `plms_account_info` VALUES ('1', 'c001', 'san', 'ny', '31000', '31000', '622299', '1', '2017-07-25 19:42:23', '2017-07-25 19:42:23');
INSERT INTO `plms_account_info` VALUES ('2', 'c002', '测试户名1', '测试银行1', '31000', '31000', '6222021908007623664', '1', '2017-07-25 19:47:36', '2017-07-25 19:47:36');
INSERT INTO `plms_account_info` VALUES ('3', 'c003', '测试户名2', '测试银行2', '31000', '31000', '6222021908007623663', '1', '2017-07-25 19:30:04', '2017-07-25 19:30:04');
INSERT INTO `plms_account_info` VALUES ('4', 'c004', '测试户名3', '测试银行3', '31000', '31000', '6222021908007623662', '1', '2017-07-25 19:30:04', '2017-07-25 19:30:04');
INSERT INTO `plms_account_info` VALUES ('5', 'c005', '测试户名4', '测试银行4', '31000', '31000', '6222021908007623661', '1', '2017-07-25 19:30:04', '2017-07-25 19:30:04');
INSERT INTO `plms_account_info` VALUES ('6', 'account001', '测试户11', '大中华银行', '31000', '31000', '6222021908007623660', '1', '2017-07-25 19:30:04', '2017-07-25 19:30:04');
INSERT INTO `plms_account_info` VALUES ('6', 'c006', '测试户名5', '测试银行5', '31000', '31000', '6222021908007623660', '1', '2017-07-25 19:30:04', '2017-07-25 19:30:04');



INSERT INTO `plms_apply_info` VALUES ('8', '8', '2017-07-18 15:43:00', '100.00', '1', '1', '1.00', '1', '1', '1', '2017-07-18 15:43:26', '2017-07-18 15:43:28', 'c005', null, '1', null, null);
INSERT INTO `plms_apply_info` VALUES ('9', '9', '2017-07-18 15:43:00', '100.00', '1', '1', '1.00', '1', '1', '1', '2017-07-18 15:43:26', '2017-07-18 15:43:28', 'c006', null, '1', null, null);



INSERT INTO `plms_apply_interesting` VALUES ('8', '8', '0.100000', '0.100000', '0.100000', '0.100000', '8', '2017-07-18 16:30:05', '2017-07-18 16:30:11', '1', null, null, null, null, null, null);
INSERT INTO `plms_apply_interesting` VALUES ('9', '9', '0.100000', '0.100000', '0.100000', '0.100000', '9', '2017-07-18 16:30:05', '2017-07-18 16:30:11', '1', null, null, null, null, null, null);


truncate table plms_borrow_apply;
INSERT INTO `plms_borrow_apply` VALUES ('1', '1', '100.00', '2017-07-18 15:53:09', '01', '11111111', '1', '2', '1', '2017-07-18 15:53:28', '2017-07-18 15:53:32', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('2', '2017071917503600000028', '1.00', '2017-07-19 17:50:36', '1', '201707191750360029', '3', null, '2', '2017-07-19 17:50:36', '2017-07-19 17:50:36', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('3', '2017072319215600000030', '1.00', '2017-07-23 19:21:56', '1', '201707231921560031', '3', null, '2', '2017-07-23 19:21:56', '2017-07-23 19:21:56', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('4', '2017072319220500000032', '1.00', '2017-07-23 19:22:05', '1', '201707231922050033', '3', null, '2', '2017-07-23 19:22:05', '2017-07-23 19:22:05', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('5', '2017072319251600000034', '1.00', '2017-07-23 19:25:16', '1', '201707231925160035', '3', '6', '2', '2017-07-23 19:25:16', '2017-07-23 19:25:16', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('6', '2017072320405200000036', '1.00', '2017-07-23 20:40:52', '1', '201707232040520037', '3', '6', '2', '2017-07-23 20:40:52', '2017-07-23 20:40:52', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('7', '2017072320593300000038', '1.00', '2017-07-23 20:59:33', '1', '201707232059330039', '3', '6', '2', '2017-07-23 20:59:33', '2017-07-23 20:59:33', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('8', '2017072321073100000040', '10.00', '2017-07-23 21:07:31', '1', '201707232107310041', '1', '7', '1', '2017-07-23 21:07:31', '2017-07-23 21:07:31', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('9', '2017072511091600000042', '1.00', '2017-07-25 11:09:16', '02', '201707251109160043', '3', '6', '2', '2017-07-25 11:09:16', '2017-07-25 11:09:16', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('10', '2017072511093100000044', '1.00', '2017-07-25 11:09:31', '02', '201707251109310045', '3', '6', '2', '2017-07-25 11:09:31', '2017-07-25 11:09:31', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('11', '2017072512034300000046', '1.00', '2017-07-25 12:03:43', '02', '201707251203430047', '3', '6', '2', '2017-07-25 12:03:43', '2017-07-25 12:03:43', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('12', '2017072512051900000048', '1.00', '2017-07-25 12:05:19', '02', '201707251205190049', '3', '6', '2', '2017-07-25 12:05:19', '2017-07-25 12:05:19', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('13', '2017072515014800000050', '1.00', '2017-07-25 15:01:52', '02', '201707251501520051', '3', '6', '2', '2017-07-25 15:01:55', '2017-07-25 15:01:55', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('14', 'borrow0001', '500.00', '2017-07-26 02:58:59', '02', 'b201707251501520051', 'contract001', '2', 'apply001', '2017-07-25 15:01:55', '2017-07-25 15:01:55', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('15', '314a6b52-90bf-43b8-87af-096c8187dc09', '1.00', '2017-07-26 17:04:27', '02', '201707261704270116', '3', '6', '2', '2017-07-26 17:04:27', '2017-07-26 17:04:27', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('16', 'af39ec67-a726-4620-a319-ca915b158086', '467.00', '2017-07-30 15:58:58', '02', '201707301558580208', 'contract001', '3', 'apply001', '2017-07-30 15:58:58', '2017-07-30 15:58:58', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('17', 'bad55b27-0f19-43ce-a58d-1228d1c32087', '14.00', '2017-08-01 10:10:20', '02', '201708011010200218', '8', '6', '8', '2017-08-01 10:10:20', '2017-08-01 10:10:20', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('18', 'eb9d32c2-4b50-41d5-81e0-6d2dbb537a87', '100.00', '2017-08-01 10:34:11', '02', '201708011034110219', '8', '6', '8', '2017-08-01 10:34:11', '2017-08-01 10:34:11', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('19', 'bd3a16d7-94a8-45b9-a9b4-13ed35c583d0', '12.00', '2017-08-01 11:15:44', '02', '201708011115440220', '1', '6', '1', '2017-08-01 11:15:44', '2017-08-01 11:15:44', null, null);
INSERT INTO `plms_borrow_apply` VALUES ('20', '3aa69ea4-23a7-474a-b51c-cafaea3d3a83', '23.00', '2017-08-01 11:23:03', '02', '201708011123030221', '1', '6', '1', '2017-08-01 11:23:03', '2017-08-01 11:23:03', null, null);



INSERT INTO `plms_contract_info` VALUES ('9', '8', '8', '1', '2017-07-18 15:54:56', '2017-07-18 15:54:59', '8', '2017-07-25 19:55:41', '2018-07-25 19:55:39', null);
INSERT INTO `plms_contract_info` VALUES ('10', '9', '9', '1', '2017-07-19 13:47:17', '2017-07-19 13:47:19', '9', '2017-07-25 19:55:42', '2018-07-25 19:55:39', null);


INSERT INTO `plms_customer` VALUES ('3', '3', '蔡超', '蔡超', '01', '642226199319641587', '2017-06-06 13:37:44', '2017-08-01 13:37:58', '24', '15206117987', '222', '222', '222.00', '222', '01', '222', '1', '456', '6', '1', '2017-07-19', '2017-07-19');


truncate table plms_distributor;
INSERT INTO `plms_distributor` VALUES ('1', null, '1', '测试总渠道1', '测试总渠道1', null, null, '000000', '1', '2017-07-27 12:57:36', '2017-07-27 12:57:41', null);
INSERT INTO `plms_distributor` VALUES ('2', null, '2', '测试分渠道1', '测试分渠道1', null, '1', '310000', '1', '2017-07-27 12:58:36', '2017-07-27 12:58:40', null);
INSERT INTO `plms_distributor` VALUES ('3', null, '3', '测试分渠道2', '测试分渠道2', null, '1', '410000', '1', '2017-07-27 12:58:53', '2017-07-27 12:58:48', null);
INSERT INTO `plms_distributor` VALUES ('4', null, '4', '测试总渠道2', '测试总渠道2', null, null, '000000', '1', '2017-07-27 12:59:45', '2017-07-27 12:59:48', null);
INSERT INTO `plms_distributor` VALUES ('5', null, '5', '测试分渠道3', '测试分渠道3', null, '4', '310000', '1', '2017-07-27 12:59:56', '2017-07-27 12:59:53', null);

INSERT INTO `plms_house` VALUES ('10', '10', '1', '1', '2017-07-18 15:33:26', '01', '上海市浦东大道651弄小区5号104', '01', '01', '01', '01', '100.00', '111', '111.00', '8', '2014', '01', '1', '01', '1', '1', '01', '1', '1', '1', '1.00', '1.00', '1.00', '1.00', '1.00', '8', '1', '2017-07-18 15:36:44', '2017-07-18 15:36:50', null, null);
INSERT INTO `plms_house` VALUES ('11', '11', '1', '1', '2017-07-18 15:33:26', '01', '上海市浦东大道651弄小区5号105', '01', '01', '01', '01', '100.00', '111', '111.00', '8', '2014', '01', '1', '01', '1', '1', '01', '1', '1', '1', '1.00', '1.00', '1.00', '1.00', '1.00', '9', '1', '2017-07-18 15:36:44', '2017-07-18 15:36:50', null, null);


truncate table plms_paid_info;
INSERT INTO `plms_paid_info` VALUES ('1', '001', '100.00', '2017-07-19 19:28:20', null, '1', null, null, null, null, '0.00', '0.00', null, null, '01', '2017-07-19 19:30:52', '2017-07-19 19:30:55', null, null, null, '1', null, null);
INSERT INTO `plms_paid_info` VALUES ('2', 'paid001', '500.00', '2017-07-19 19:28:20', null, 'borrow0001', null, null, null, null, '0.00', '0.00', null, null, '01', '2017-07-19 19:30:52', '2017-07-19 19:30:55', null, null, null, '1', null, null);
INSERT INTO `plms_paid_info` VALUES ('3', 'paid002', '500.00', '2017-07-19 19:28:20', null, 'borrow0001', null, null, null, null, '0.00', '0.00', null, null, '01', '2017-07-19 19:30:52', '2017-07-19 19:30:55', null, null, null, '1', null, null);





truncate table plms_product;
INSERT INTO `plms_product` VALUES ('1', '1', '1', '随借随还', '1', '01', '2014', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', null, null, null);
INSERT INTO `plms_product` VALUES ('2', '2', '2', '随借随还', '1', '01', '2014', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', null, null, null);
INSERT INTO `plms_product` VALUES ('3', '3', '3', '随借随还', '1', '01', '2014', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', null, null, null);
INSERT INTO `plms_product` VALUES ('4', '4', '4', '随借随还', '1', '01', '2014', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', null, null, null);
INSERT INTO `plms_product` VALUES ('5', '5', '5', '随借随还', '1', '01', '2014', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', null, null, null);
INSERT INTO `plms_product` VALUES ('6', '6', '6', '随借随还', '1', '01', '2014', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', null, null, null);
INSERT INTO `plms_product` VALUES ('7', 'pro001', 'apply001', '随借随还001', '1', '01', '360', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', '1', null, null);
INSERT INTO `plms_product` VALUES ('8', '7', '8', '随借随还', '1', '01', '2014', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', null, null, null);
INSERT INTO `plms_product` VALUES ('9', '8', '9', '随借随还', '1', '01', '2014', '01', '1.00', '1.00', '1.00', '1.00', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2017-07-18 16:01:24', '2017-07-18 16:01:27', null, null, null);




INSERT INTO `plms_repayment_date` VALUES ('40', '127', '2017-07-25', '8', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('41', '128', '2017-08-25', '8', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('42', '129', '2017-09-25', '8', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('43', '130', '2017-10-25', '8', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('44', '131', '2017-11-25', '8', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('45', '132', '2017-12-25', '8', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('46', '1271', '2017-07-25', '9', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('47', '1281', '2017-08-25', '9', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('48', '1291', '2017-09-25', '9', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('49', '1301', '2017-10-25', '9', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('50', '1311', '2017-11-25', '9', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');
INSERT INTO `plms_repayment_date` VALUES ('51', '1321', '2017-12-25', '9', '1', '2017-07-23 18:03:53', '2017-07-23 18:03:57');



truncate table plms_repayment_schedule;
INSERT INTO `plms_repayment_schedule` VALUES ('1', '1', '1', '2', '2017-07-21', '102.11', '80.00', '20.01', '2.10', '02', '1', '1', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('2', '2', '2', '2', '2017-10-01', '102.22', '80.00', '20.02', '2.20', '02', '1', '1', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('3', '3', '1', '1', '2017-07-21', '102.22', '80.00', '20.02', '2.20', '02', '1', '1', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('4', '4', '1', '2', '2017-07-21', '102.11', '80.00', '20.01', '2.10', '02', '1', '123', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('5', '5', '2', '2', '2017-11-17', '102.22', '80.00', '20.02', '2.20', '02', '1', '123', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('6', '6', '1', '1', '2017-07-21', '102.22', '80.00', '20.02', '2.20', '02', '1', '123', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('7', '7', '1', '2', '2017-07-21', '102.11', '80.00', '20.01', '2.10', '02', '1', '2', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('8', '8', '2', '2', '2017-10-14', '102.22', '80.00', '20.02', '2.20', '02', '1', '2', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('9', '9', '1', '1', '2017-07-21', '102.22', '80.00', '20.02', '2.20', '02', '1', '2', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('10', '10', '1', '2', '2017-07-21', '102.11', '80.00', '20.01', '2.10', '02', '1', '125', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('11', '11', '2', '2', '2017-10-04', '102.22', '80.00', '20.02', '2.20', '02', '1', '125', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('12', '12', '1', '1', '2017-07-21', '102.22', '80.00', '20.02', '2.20', '02', '1', '125', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('13', 'sDetail001', '1', '2', '2017-07-21', '102.22', '80.00', '20.02', '2.20', '02', 'accDetail001', '125', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('15', '000118', null, null, null, null, '0.00', '0.00', null, '02', null, '125', '2017-07-27 21:12:27', '2017-07-27 21:12:27', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('16', '000120', null, null, null, null, '0.00', '0.00', null, '02', null, '125', '2017-07-27 21:15:48', '2017-07-27 21:15:48', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('17', '13', '1', '2', '2017-08-01', '102.11', '80.00', '20.01', '2.10', '02', '9', '3', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('18', '14', '2', '2', '2017-10-13', '102.22', '80.00', '20.02', '2.20', '02', '9', '3', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('19', '15', '1', '1', '2017-07-21', '102.22', '80.00', '20.02', '2.20', '02', '9', '3', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('20', '16', '1', '2', '2017-08-01', '102.11', '80.00', '20.01', '2.10', '02', '8', '3', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('21', '17', '2', '2', '2017-10-13', '102.22', '80.00', '20.02', '2.20', '02', '8', '3', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule` VALUES ('22', '18', '1', '1', '2017-07-21', '102.22', '80.00', '20.02', '2.20', '02', '8', '3', '2017-07-18 17:15:45', '2017-07-18 17:15:48', null, null, null, null, null, null, null);



truncate table plms_repayment_schedule_detail;
INSERT INTO `plms_repayment_schedule_detail` VALUES ('1', '000001', '1', '2', '2017-08-01 18:11:05', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '001', '2017-07-24 23:07:52', '2017-07-24 23:07:52', '1', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('2', '000002', '2', '2', '2017-08-01 18:11:05', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '001', '2017-07-24 23:07:52', '2017-07-24 23:07:52', '1', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('3', 'sDetail001', '1', '2', '2017-08-01 18:10:50', '100.00', '80.00', '2.00', '1.00', '80.10', '2.00', '1.00', '02', 'paid001', '2017-07-24 23:07:52', '2017-07-24 23:07:52', '1', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('4', 'sDetail002', '2', '2', '2017-08-01 18:11:05', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', 'paid001', '2017-07-24 23:07:52', '2017-07-24 23:07:52', '1', null, null, null, null, null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES (null, '000117', '0', '0', '2017-08-01 18:10:46', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', 'paid002', null, null, '000118', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES (null, '000119', '0', '0', '2017-08-01 18:08:44', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', 'paid002', null, null, '13', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('7', '000217', '0', '0', '2017-08-01 18:10:46', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', '13', null, null, '000118', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('8', '000219', '0', '0', '2017-08-01 18:08:44', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', '13', null, null, '13', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('5', '000217', '0', '0', '2017-08-01 18:10:46', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', '13', null, null, '000118', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('6', '000219', '0', '0', '2017-08-01 18:08:45', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', '13', null, null, '13', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('11', '000319', '0', '0', '2017-08-01 18:10:46', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', 'paid002', null, null, '14', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('12', '000319', '0', '0', '2017-08-01 18:10:46', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', '13', null, null, '14', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('13', '000319', '0', '0', '2017-08-01 18:10:46', '100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '02', '13', null, null, '14', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('14', '000311', '0', '0', '2017-08-01 18:12:25', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', 'paid002', null, null, '15', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('15', '000312', '0', '0', '2017-08-01 18:12:25', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '13', null, null, '15', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('16', '000313', '0', '0', '2017-08-01 18:12:25', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '13', null, null, '15', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('17', '000314', '0', '0', '2017-08-01 18:13:17', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', 'paid002', null, null, '16', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('18', '000315', '0', '0', '2017-08-01 18:13:17', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '13', null, null, '16', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('19', '000316', '0', '0', '2017-08-01 18:13:17', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '13', null, null, '16', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('20', '000317', '0', '0', '2017-08-01 18:13:53', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', 'paid002', null, null, '17', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('21', '000318', '0', '0', '2017-08-01 18:13:53', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '13', null, null, '17', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('22', '000319', '0', '0', '2017-08-01 18:13:53', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '13', null, null, '17', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('23', '000417', '0', '0', '2017-08-01 18:14:35', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', 'paid002', null, null, '18', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('24', '000418', '0', '0', '2017-08-01 18:14:35', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '13', null, null, '18', null, null, null, '0.00', null, null, null);
INSERT INTO `plms_repayment_schedule_detail` VALUES ('25', '000419', '0', '0', '2017-08-01 18:14:35', '100.00', '80.00', '2.00', '1.00', '1.00', '2.00', '3.00', '02', '13', null, null, '18', null, null, null, '0.00', null, null, null);



truncate table plms_user_distributor;
INSERT INTO `plms_user_distributor` VALUES ('1', '2017063015180000000025', '1', '1');
INSERT INTO `plms_user_distributor` VALUES ('2', '2017063015180000000024', '1', '1');
INSERT INTO `plms_user_distributor` VALUES ('3', '2017063015180000000027', '1', '1');
INSERT INTO `plms_user_distributor` VALUES ('4', '2017063015180000000026', '1', '1');
INSERT INTO `plms_user_distributor` VALUES ('5', '20170630151800000028', '1', '1');
INSERT INTO `plms_user_distributor` VALUES ('6', '20170630151800000029', '1', '1');
INSERT INTO `plms_user_distributor` VALUES ('7', '20170630151800000030', '1', '1');
INSERT INTO `plms_user_distributor` VALUES ('8', '20170630151800000031', '1', '1');



INSERT INTO `plms_user_info` VALUES ('5', '3', '2017063015180000000036', '蔡超', '01', '642226199319641587', '1', '2017-07-25 19:42:37', '2017-07-25 19:42:37');













