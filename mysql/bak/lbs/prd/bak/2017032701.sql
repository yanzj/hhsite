
#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18717858562','18717858562',md5('18717858562'),'18717858562',null,'彭访','310000',null,null,'00000006',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','18717858562');

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000007','00000005',60,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000008','00000006',70,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000006','000000000000000000000000000000000007');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000006','000000000000000000000000000000000008');