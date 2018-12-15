#增加渠道
insert into bms_distributor (CODE,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) values('00000027','','310000','RH','如亨','上海如亨金融信息服务有限公司','2500');

#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15214399302','15214399302',md5('15214399302'),'15214399302',null,'史清河','310000',null,null,'00000027',false,'0',true);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000051','00000027',510,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000052','00000027',520,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000027','000000000000000000000000000000000051');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000027','000000000000000000000000000000000052');

#给曹瀚文、淘妍、曹丽娜、饶彩玲、武舸、张麒增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','13917228473');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','18616774571');

#给鲍龙、王安定、马伟增加渠道查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','15721280016');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000027','13636321925');