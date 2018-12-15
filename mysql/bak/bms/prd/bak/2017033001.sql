
#调整复审用户
DELETE FROM  bms_role_user where user_no = '15021000149' and role_code = '009';
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'006','15900992562');

#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18616774571','18616774571',md5('18616774571'),'18616774571',null,'蒋国炜','310000','00000001','00000001',null,true,'0',true);

#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18017579606','18017579606',md5('18017579606'),'18017579606',null,'张兵','310000',null,null,'00000004',false,'0',true);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000009','00000009',90,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000010','00000010',100,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000004','000000000000000000000000000000000009');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000004','000000000000000000000000000000000010');

#给简易、陶妍、曹丽娜、饶彩玲、武舸、张麒、曹瀚文、卢俊彦增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','15921228421');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','13917228473');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','15900992562');


#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13816522543','13816522543',md5('13816522543'),'13816522543',null,'白靖','310000',null,null,'00000002',false,'0',true);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000011','00000011',110,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000012','00000012',120,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000002','000000000000000000000000000000000011');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000002','000000000000000000000000000000000012');


#给简易、陶妍、曹丽娜、饶彩玲、武舸、张麒、曹瀚文、卢俊彦增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','15921228421');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','13917228473');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','15900992562');
