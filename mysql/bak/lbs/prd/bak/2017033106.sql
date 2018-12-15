
#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('00000016',null,'310000','XY','盛岭','上海盛岭投资中心',1600);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000023','00000016',230,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000024','00000016',240,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000016','000000000000000000000000000000000023');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000016','000000000000000000000000000000000024');

#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18939917806','18939917806',md5('18939917806'),'18939917806',null,'袁琤华','310000',null,null,'00000016',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','18939917806');