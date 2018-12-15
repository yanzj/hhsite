update bms_user set password = md5('15601936981') where user_no = '15921228421' and status = 1;

#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('00000012',null,'310000','MX','暮晓','上海暮晓资产管理有限公司',1200);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000015','00000012',150,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000016','00000012',160,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000012','000000000000000000000000000000000015');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000012','000000000000000000000000000000000016');

#给陶妍、曹丽娜、饶彩玲、武舸、张麒、曹瀚文、卢俊彦增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','13917228473');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','15900992562');

#给鲍龙、马伟增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','13636321925');

#给张毅增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000012','15221897018');

#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18918929551','18918929551',md5('18918929551'),'18918929551',null,'吉永胜','310000',null,null,'00000012',false,'0',true);