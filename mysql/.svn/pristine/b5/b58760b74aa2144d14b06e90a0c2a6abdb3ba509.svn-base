
#增加渠道
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values ('00000011',null,'310000','FBK','房帮客','上海艺匠金融信息服务有限公司',1000);

#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13801762081','13801762081',md5('13801762081'),'13801762081',null,'胡雅唯','310000',null,null,'00000011',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','13801762081');

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000005','00000005',40,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000006','00000006',50,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000011','000000000000000000000000000000000005');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000011','000000000000000000000000000000000006');