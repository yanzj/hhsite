
#增加两款产品P1 P2，并配置给合盘
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000001','00000001',1,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000002','00000002',10,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000003','000000000000000000000000000000000001');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000003','000000000000000000000000000000000002');

#合盘增加用户李左文
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15000000442','15000000442',md5('15000000442'),'15000000442',null,'李左文','310000',null,null,'00000003',false,'0',true);
#给李左文授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','15000000442');

#增加两款产品P1 P2，并配置给舟凯
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000003','00000003',20,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000004','00000004',30,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000008','000000000000000000000000000000000003');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000008','000000000000000000000000000000000004');

#舟凯增加用户孟晨毅
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13611660283','13611660283',md5('13611660283'),'13611660283',null,'孟晨毅','310000',null,null,'00000008',false,'0',true);
#给孟晨毅授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','13611660283');