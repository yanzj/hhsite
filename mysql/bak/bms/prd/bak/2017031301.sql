
#陶妍增加复审权限
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'006','15021000149');

#增加两款产品P1 P2，并配置给合盘
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000001','00000001',1,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000002','00000002',10,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000003','000000000000000000000000000000000001');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000003','000000000000000000000000000000000002');

#增加两款产品P1 P2，并配置给舟凯
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000003','00000003',20,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000004','00000004',30,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000008','000000000000000000000000000000000003');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000008','000000000000000000000000000000000004');

#给简易、陶妍、曹丽娜、饶彩玲、武舸、张麒、曹瀚文增加合盘、舟凯的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','15921228421');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','13917228473');

insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','15921228421');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','13917228473');