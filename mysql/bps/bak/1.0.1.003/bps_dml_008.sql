use bps;
update bps_city t set t.abbr_name='�Ϻ�',t.full_name='�Ϻ���' where t.`code`='310100';
update bps_config t set t.config_value='Y' where t.config_name='needAllResult';