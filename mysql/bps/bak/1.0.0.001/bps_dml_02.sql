
#初始化算法表
INSERT INTO `bps`.`bps_algorithm` (`code`, `name`, `description`, `status`, `date_created`, `date_modified`) VALUES ('c64180d2-34db-11e7-bfa4-1866dae83f00', 'arithmeticMean', '平均值算法', '1', '2017-05-10 01:20:18', '2017-06-08 04:10:00');
INSERT INTO `bps`.`bps_algorithm` (`code`, `name`, `description`, `status`, `date_created`, `date_modified`) VALUES ('c648f4d8-34db-11e7-bfa4-1866dae83f00', 'floatPercent', '最低价 + 最低价上浮百分比参数值算法', '1', '2017-05-10 01:20:18', '2017-06-08 04:10:03');
#初始化询价公司表
INSERT INTO `bps`.`bps_appraisal_company` (`code`, `date_created`, `date_modified`, `abbr_name`, `full_name`, `cooperation_type`, `villa_turn_artificial`, `max_area`, `api_url`, `status`) VALUES ('001', '2017-06-13 01:08:44', '2017-06-14 21:23:48', '世联', '世联估价公司', '2', '1', '140.00', 'http://wuio.worldunion.cn:9015', '1');
INSERT INTO `bps`.`bps_appraisal_company` (`code`, `date_created`, `date_modified`, `abbr_name`, `full_name`, `cooperation_type`, `villa_turn_artificial`, `max_area`, `api_url`, `status`) VALUES ('002', '2017-06-13 01:09:44', '2017-06-14 21:23:51', '城市', '城市估价公司', '1', '1', '140.00', 'http://118.126.4.153:8009/api/Appraisal/', '1');
#初始化城市表
INSERT INTO `bps`.`bps_city` (`id`, `code`, `abbr_name`, `full_name`, `wu_code`, `status`, `date_created`, `date_modified`) VALUES ('1', '410100', '郑州', '郑州市', '4101', '0', '2017-06-08 03:33:31', '2017-06-13 22:51:09');
INSERT INTO `bps`.`bps_city` (`id`, `code`, `abbr_name`, `full_name`, `wu_code`, `status`, `date_created`, `date_modified`) VALUES ('2', '310000', '上海', '上海市', '3101', '1', '2017-05-08 17:32:17', '2017-06-13 17:49:29');
INSERT INTO `bps`.`bps_city` (`id`, `code`, `abbr_name`, `full_name`, `wu_code`, `status`, `date_created`, `date_modified`) VALUES ('3', '440100', '广州', '广州市', '4401', '1', '2017-05-08 17:33:57', '2017-06-13 17:50:18');
INSERT INTO `bps`.`bps_city` (`id`, `code`, `abbr_name`, `full_name`, `wu_code`, `status`, `date_created`, `date_modified`) VALUES ('4', '330100', '杭州', '杭州市', '3301', '0', '2017-05-08 17:33:57', '2017-06-13 17:50:43');
#初始化询价公司和城市关联表
INSERT INTO `bps`.`bps_company_city` (`id`, `company_code`, `city_code`, `status`, `order_no`, `date_created`, `date_modified`) VALUES ('1', '001', '310000', '1', '0', '2017-05-10 15:34:24', '2017-06-14 19:07:27');
INSERT INTO `bps`.`bps_company_city` (`id`, `company_code`, `city_code`, `status`, `order_no`, `date_created`, `date_modified`) VALUES ('2', '002', '310000', '1', '1', '2017-05-10 15:34:24', '2017-06-14 19:07:30');
INSERT INTO `bps`.`bps_company_city` (`id`, `company_code`, `city_code`, `status`, `order_no`, `date_created`, `date_modified`) VALUES ('3', '001', '440100', '1', '1', '2017-05-10 15:34:24', '2017-06-13 22:51:42');
#初始化配置表
INSERT INTO `bps`.`bps_config` (`config_name`, `config_value`, `description`, `status`, `date_created`, `date_modified`) VALUES ('needAllResult', 'N', '是否要求所有关联询价公司有结果', '1', '2017-05-08 19:41:57', '2017-06-20 19:13:42');
INSERT INTO `bps`.`bps_config` (`config_name`, `config_value`, `description`, `status`, `date_created`, `date_modified`) VALUES ('percentOfDiffThreshold', '0.4', '差价百分比阈值', '1', '2017-05-08 19:41:57', '2017-06-20 19:13:44');
INSERT INTO `bps`.`bps_config` (`config_name`, `config_value`, `description`, `status`, `date_created`, `date_modified`) VALUES ('overPercentTurnArtificial', 'N', '大于等于差价百分比阈值是否转人工', '1', '2017-05-08 19:41:57', '2017-06-20 19:13:46');
INSERT INTO `bps`.`bps_config` (`config_name`, `config_value`, `description`, `status`, `date_created`, `date_modified`) VALUES ('lowestUpPercent', '0.075', '最低价上浮百分比', '1', '2017-05-08 19:41:57', '2017-06-20 19:13:50');
INSERT INTO `bps`.`bps_config` (`config_name`, `config_value`, `description`, `status`, `date_created`, `date_modified`) VALUES ('lessPercentOfDiffThreshold', 'c64180d2-34db-11e7-bfa4-1866dae83f00', '平均值算法', '1', '2017-05-10 04:02:14', '2017-06-20 19:13:53');
INSERT INTO `bps`.`bps_config` (`config_name`, `config_value`, `description`, `status`, `date_created`, `date_modified`) VALUES ('morePercentOfDiffThreshold', 'c648f4d8-34db-11e7-bfa4-1866dae83f00', '最低价 + 最低价上浮百分比参数值算法', '1', '2017-05-10 04:02:35', '2017-06-20 19:13:58');
#初始化房屋类型
INSERT INTO `bps`.`bps_house_type` (`code`, `name`) VALUES ( '001', '公寓');
INSERT INTO `bps`.`bps_house_type` (`code`, `name`) VALUES ( '002', '别墅');
#初始化公司请求状态表
INSERT INTO `bps`.`bps_inquiry_apply_status` (`code`, `value`, `name`, `status`, `remark`) VALUES ('S001', '02', '已评估', '1', NULL);
INSERT INTO `bps`.`bps_inquiry_apply_status` (`code`, `value`, `name`, `status`, `remark`) VALUES ('S003', '01', '评估中', '1', '等待询价公司回值');
INSERT INTO `bps`.`bps_inquiry_apply_status` (`code`, `value`, `name`, `status`, `remark`) VALUES ('S005', '99', '失效', '1', '1超过设定取值评估时间；2无评估值且不要求所有公司都有结果时');
INSERT INTO `bps`.`bps_inquiry_apply_status` (`code`, `value`, `name`, `status`, `remark`) VALUES ('S002', '03', '评估失败', '1', '询价公司评估失败');
INSERT INTO `bps`.`bps_inquiry_apply_status` (`code`, `value`, `name`, `status`, `remark`) VALUES ('S004', '00', '待评估', '1', '为开通询价接口需要人工输入时');
#初始化询价流水状态表
INSERT INTO `bps`.`bps_inquiry_status` ( `code`, `value`, `name`, `status`, `remark`) VALUES ('S001', '02', '已评估', '1', NULL);
INSERT INTO `bps`.`bps_inquiry_status` ( `code`, `value`, `name`, `status`, `remark`) VALUES ('S003', '01', '评估中', '1', '等待询价公司出结果');
INSERT INTO `bps`.`bps_inquiry_status` ( `code`, `value`, `name`, `status`, `remark`) VALUES ('S004', '00', '待评估', '1', '未完成评估且没有超出时间设定');
INSERT INTO `bps`.`bps_inquiry_status` ( `code`, `value`, `name`, `status`, `remark`) VALUES ('S005', '99', '失效', '1', '设定时间内未完成评估');
#初始化时间阈值
INSERT INTO `bps`.`bps_threshold` (`code`, `value`, `city_code`, `status`, `date_created`, `date_modified`) VALUES ('1', '10', '310000', '0', '2017-05-10 04:08:23', '2017-06-08 04:02:09');
INSERT INTO `bps`.`bps_threshold` (`code`, `value`, `city_code`, `status`, `date_created`, `date_modified`) VALUES ('2', '168', '310000', '0', '2017-05-10 04:08:23', '2017-06-08 04:02:10');
INSERT INTO `bps`.`bps_threshold` (`code`, `value`, `city_code`, `status`, `date_created`, `date_modified`) VALUES ('3', '168', '310000', '1', '2017-05-10 04:08:23', '2017-06-08 04:02:13');
