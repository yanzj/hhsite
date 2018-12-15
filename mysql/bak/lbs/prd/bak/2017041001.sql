
#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('00000025',null,'310000','YCJR','熠岑金融','上海熠岑金融信息服务有限公司',2400);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000045','00000025',450,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000046','00000025',460,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000025','000000000000000000000000000000000045');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000025','000000000000000000000000000000000046');

#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13764598450','13764598450',md5('13764598450'),'13764598450',null,'曾良','310000',null,null,'00000025',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','13764598450');
