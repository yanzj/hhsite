


INSERT INTO `ppms_route_info` VALUES (null, 'baofoopay', 'baofoopay', '宝付', null, null, '1', '000000', '0', '1', null, null, null, null);


INSERT INTO `ppms_merchant_info` VALUES (null, '55963923-bab9-11e7-aed9-1866dae83f00', '100000178', '宝付测试商户号', 'baofoopay', null, null, null, null, '1000000001', '1', '2017-10-16 18:47:43', '2017-11-01 10:01:01', null, '', null, '100000859');





INSERT INTO ppms_route_card_bin
(select null,UUID(),b.route_code,b.issuer_code,b.single_min_amount,b.single_max_amount,b.day_max_amount,b.month_max_amount
,b.day_max_count,b.month_max_count,b.trans_type,'1',b.status,b.create_time,b.update_time
,b.remark1,b.route_bank_code,'',b.route_bank_name from ppms_route_card_bin b WHERE b.trans_type='02' and b.route_code='baofoopay' and b.card_type='D');




INSERT INTO ppms_route_card_bin
(select null,UUID(),b.route_code,b.issuer_code,b.single_min_amount,b.single_max_amount,b.day_max_amount,b.month_max_amount
,b.day_max_count,b.month_max_count,b.trans_type,'1',b.status,b.create_time,b.update_time
,b.remark1,b.route_bank_code,'',b.route_bank_name from ppms_route_card_bin b WHERE b.trans_type='02' and b.route_code='kjtpay' and b.card_type='D');





INSERT INTO `ppms_rate_info` VALUES (null, 'bb653ad3-beaf-11e7-aed9-1866dae83f00', 'kjtpay', '01', '02', '03', '0.00', '0.00', '2.00', '01', '01', '1', '2017-11-01 11:22:02', '2017-11-01 11:22:02', null, '200000151635', '0');
INSERT INTO `ppms_rate_info` VALUES (null, '46a177b2-beb5-11e7-aed9-1866dae83f00', 'kjtpay', '02', '02', '03', '0.00', '0.00', '1.00', '01', '01', '1', '2017-11-01 11:22:02', '2017-11-01 11:22:02', null, '200000151635', '0');
INSERT INTO `ppms_rate_info` VALUES (null, '69eae702-beb5-11e7-aed9-1866dae83f00', 'kjtpay', '02', '02', '03', '0.00', '0.00', '2.00', '01', '01', '1', '2017-11-01 11:22:02', '2017-11-01 11:22:02', null, '200000151635', '1');
INSERT INTO `ppms_rate_info` VALUES (null, '12b8f198-beb8-11e7-aed9-1866dae83f00', 'baofoopay', '01', '02', '03', '0.00', '0.00', '5.00', '01', '01', '1', '2017-11-01 11:22:02', '2017-11-01 11:55:03', null, '100000178', '0');
INSERT INTO `ppms_rate_info` VALUES (null, '5baedec1-beb8-11e7-aed9-1866dae83f00', 'baofoopay', '02', '02', '03', '0.00', '0.00', '1.00', '01', '01', '1', '2017-11-01 11:22:02', '2017-11-01 11:55:04', null, '100000178', '0');
INSERT INTO `ppms_rate_info` VALUES (null, 'e6bb52fb-beb8-11e7-aed9-1866dae83f00', 'baofoopay', '02', '02', '03', '0.00', '0.00', '1.00', '01', '01', '1', '2017-11-01 11:22:02', '2017-11-01 11:55:04', null, '100000178', '1');


INSERT INTO `ppms_liqu_step_info` VALUES ('5', 'e2649c97-bae7-11e7-aed9-1866dae83f00', '2017-10-18 00:00:00', 'baofoopay', '解析对账数据', '0', '4c345c9d-bae8-11e7-aed9-1866dae83f00', '1', '02', '处理成功', '1', '2017-10-19 16:37:35', '2017-10-30 11:29:20', null, 'baofoopayLoadLiquFile');
INSERT INTO `ppms_liqu_step_info` VALUES ('6', 'ebf2d8bd-bae7-11e7-aed9-1866dae83f00', '2017-10-18 00:00:00', 'baofoopay', '清算对账', '0', 'e2649c97-bae7-11e7-aed9-1866dae83f00', '1', '02', '处理成功', '1', '2017-10-19 16:37:35', '2017-10-30 16:39:56', null, 'baofoopayLiquClear');
INSERT INTO `ppms_liqu_step_info` VALUES ('7', 'f0b9725b-bae7-11e7-aed9-1866dae83f00', '2017-10-18 00:00:00', 'baofoopay', '历史数据生成差错', '0', 'ebf2d8bd-bae7-11e7-aed9-1866dae83f00', '1', '02', '处理成功', '1', '2017-10-19 16:37:35', '2017-10-30 16:40:16', null, 'baofoopayTransLogToError');
INSERT INTO `ppms_liqu_step_info` VALUES ('8', 'f41121df-bae7-11e7-aed9-1866dae83f00', '2017-10-18 00:00:00', 'baofoopay', '数据统计', '0', 'f0b9725b-bae7-11e7-aed9-1866dae83f00', '1', '02', '处理成功', '1', '2017-10-19 16:37:35', '2017-11-01 18:03:10', null, 'baofoopayDataStatistics');
INSERT INTO `ppms_liqu_step_info` VALUES ('9', '4c345c9d-bae8-11e7-aed9-1866dae83f00', '2017-10-18 00:00:00', 'baofoopay', '文件下载', '0', null, '1', '02', '处理成功', '1', '2017-10-19 16:37:35', '2017-10-30 11:28:09', null, 'baofoopayFileDownload');



