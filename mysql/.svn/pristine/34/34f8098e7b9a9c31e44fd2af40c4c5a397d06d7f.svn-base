
#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('00000024',null,'310000','RY','瑞钰','上海瑞钰金融信息服务有限公司',2300);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000043','00000024',430,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000044','00000024',440,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000024','000000000000000000000000000000000043');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000024','000000000000000000000000000000000044');

#给陶妍、曹丽娜、饶彩玲、武舸、张麒、蒋国炜、曹瀚文、卢俊彦增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','13917228473');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','15900992562');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','18616774571');

#给鲍龙、马伟增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','13636321925');

#增加用户王安定
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15721280016','15721280016',md5('15721280016'),'15721280016',null,'王安定','310000','00000001','00000004',null,true,'0',true);
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'009','15721280016');

#给王安定增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000024','15721280016');

#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18917921377','18917921377',md5('18917921377'),'18917921377','18917921377@qq.com','权晓霞','310000',null,null,'00000024',false,'0',true);

#作废简易的状态
UPDATE bms_user SET status = '9' where user_no = '15921228421';