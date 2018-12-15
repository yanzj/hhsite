#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('10000100',null,'310000','HBZ','红鼻子','红鼻子金融',10100);

#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13564169229','13564169229',md5('13564169229'),'13564169229',null,'李勇','310000',null,null,'10000100',false,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18217279902','18217279902',md5('18217279902'),'18217279902',null,'陈晨','310000',null,null,'10000100',false,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15026781572','15026781572',md5('15026781572'),'15026781572',null,'朱琳','310000',null,null,'10000100',false,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15201918896','15201918896',md5('15201918896'),'15201918896',null,'邓桂萍','310000',null,null,'10000100',false,'0',true);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000049','10000100',490,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000050','10000100',500,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'10000100','000000000000000000000000000000000049');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'10000100','000000000000000000000000000000000050');

#给卢俊彦、曹瀚文、曹丽娜、饶彩玲、武舸、张麒、蒋国伟增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000100','15900992562');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000100','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000100','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000100','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000100','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000100','13917228473');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000100','18616774571');

#给鲍龙增加渠道查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000100','13968505268');