
#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('00000014',null,'310000','YGJF','阳光金服','上海翊滔商务信息咨询有限公司',1300);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000019','00000014',190,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000020','00000014',200,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000014','000000000000000000000000000000000019');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'00000014','000000000000000000000000000000000020');

#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13764087995','13764087995',md5('13764087995'),'13764087995',null,'赵艳','310000',null,null,'00000014',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','13764087995');