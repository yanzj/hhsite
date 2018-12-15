#变更上海市城市code
update bps.bps_city t set t.wu_code=null where t.code = '310000';
INSERT INTO `bps`.`bps_city` (`code`, `abbr_name`, `full_name`, `wu_code`, `status`, `date_created`, `date_modified`) VALUES ('310100', '上海', '上海市', '3101', '1', '2017-05-08 17:32:17', '2017-06-13 17:49:29');
update bps.bps_threshold t set t.city_code='310100' where t.city_code='310000';
update bps.bps_company_city t set t.city_code='310100' where t.city_code='310000';
delete from bps.bps_city where code = '310000';
