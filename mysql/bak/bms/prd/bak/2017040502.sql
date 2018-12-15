#增加渠道

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000041','00000005',410,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000042','00000005',420,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000005','000000000000000000000000000000000041');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000005','000000000000000000000000000000000042');

#给陶妍、曹丽娜、饶彩玲、武舸、张麒、曹瀚文、卢俊彦、将国伟增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','15021000149');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','13817403697');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','18721675945');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','15202190793');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','13917228473');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','15900992562');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','18616774571');

#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15021131443','15021131443',md5('15021131443'),'15021131443',null,'朱智明','310000',null,null,'00000005',false,'0',true);