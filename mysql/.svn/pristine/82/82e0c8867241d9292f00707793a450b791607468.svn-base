
#将上海时间阈值设为0
update bps.bps_threshold t set t.`value`='0' where t.city_code='310000';
#加入广州时间阈值
INSERT INTO `bps`.`bps_threshold` (`code`, `value`, `city_code`, `status`, `date_created`, `date_modified`) VALUES ('4', '0', '440100', '1', '2017-06-24 00:20:32', '2017-06-23 16:25:34');
#新增评估有效期限的阈值
INSERT INTO `bps`.`bps_config` (`config_name`, `config_value`, `description`, `status`, `date_created`, `date_modified`) VALUES ('effectiveHourLimit', '72', '评估有效期限的阈值', '1', '2017-06-23 16:34:00', '2017-06-23 16:34:05');
