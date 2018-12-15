use bps;
#是否要求所有关联询价公司有结果(Y必须都有结果；N 不要求所有公司都有结果)
update bps_config t set t.config_value='Y' where t.config_name='needAllResult';
#差价百分比阈值（例如 0.4）
update bps_config t set t.config_value='0.1' where t.config_name='percentOfDiffThreshold';
#大于等于差价百分比阈值是否转人工（N不转人工； Y转人工）
update bps_config t set t.config_value='N' where t.config_name='overPercentTurnArtificial';
#最低价上浮百分比（例 0.075）
update bps_config t set t.config_value='0' where t.config_name='lowestUpPercent';

update bps_threshold t set t.value='0' where t.city_code='310000';

update bps_appraisal_company t set t.villa_turn_artificial = '0' where t.code = '001';
update bps_appraisal_company t set t.villa_turn_artificial = '0' where t.code = '002';