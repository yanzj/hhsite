
#���Ϻ�ʱ����ֵ��Ϊ0
update bps.bps_threshold t set t.`value`='0' where t.city_code='310000';
#�������ʱ����ֵ
INSERT INTO `bps`.`bps_threshold` (`code`, `value`, `city_code`, `status`, `date_created`, `date_modified`) VALUES ('4', '0', '440100', '1', '2017-06-24 00:20:32', '2017-06-23 16:25:34');
#����������Ч���޵���ֵ
INSERT INTO `bps`.`bps_config` (`config_name`, `config_value`, `description`, `status`, `date_created`, `date_modified`) VALUES ('effectiveHourLimit', '72', '������Ч���޵���ֵ', '1', '2017-06-23 16:34:00', '2017-06-23 16:34:05');
