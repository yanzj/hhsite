#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18516282522','18516282522',md5('18516282522'),'18516282522',null,'徐凯','000000',null,null,'10000001',false,'0',true);

#层级
insert into bms_user_hierarchy (code,user_no,parent_user_no) values (UUID(),'18516282522','18516183181');

#权限
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'895','18516282522');
