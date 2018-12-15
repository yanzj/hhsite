#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18616774571','18616774571',md5('18616774571'),'18616774571',null,'蒋国炜','310000','00000001','00000001',null,true,'0',true);

#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18017579606','18017579606',md5('18017579606'),'18017579606',null,'张兵','310000',null,null,'00000004',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','18017579606');

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000009','00000009',90,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000010','00000010',100,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000004','000000000000000000000000000000000009');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000004','000000000000000000000000000000000010');

#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13816522543','13816522543',md5('13816522543'),'13816522543',null,'白靖','310000',null,null,'00000002',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','13816522543');

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000011','00000011',110,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000012','00000012',120,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000002','000000000000000000000000000000000011');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000002','000000000000000000000000000000000012');