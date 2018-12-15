
#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18717858562','18717858562',md5('18717858562'),'18717858562',null,'彭访','310000',null,null,'00000006',false,'0',true);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000007','00000005',60,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000008','00000006',70,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000006','000000000000000000000000000000000007');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000006','000000000000000000000000000000000008');

#增加复审用户陶妍
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'006','15021000149');

#给简易、陶妍、曹丽娜、饶彩玲、武舸、张麒、曹瀚文增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','15921228421');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','13917228473');

#给渠道经理增加录单角色
insert into bms_role_menu (code,role_code,menu_code) values (UUID(),'009','003');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','15221897018');