use bms;

#清理数据，保证可重复执行
DELETE from bms_config;

#初始化借款期限表
INSERT INTO bms_config(code,config_name,config_value) values ('01','maxAged','65');
INSERT INTO bms_config(code,config_name,config_value) values ('02','maxLoanAmount','1000');