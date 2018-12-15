
#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('00000017',null,'310000','DGJ','贷管家','上海陇郦实业发展有限公司',1700);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000025','00000017',250,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000026','00000017',260,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000017','000000000000000000000000000000000023');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000017','000000000000000000000000000000000024');

#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13817930784','13817930784',md5('13817930784'),'13817930784',null,'刘晓峰','310000',null,null,'00000017',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','13817930784');

update bms_distributor set first_character_code = 'SL' where code = '00000016';
update bms_distributor set order_by_no = 1400 where code = '00000014';