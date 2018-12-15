#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('17317698007','17317698007',md5('17317698007'),'17317698007',null,'郭凤丽','310000',null,null,'10000001',false,'0',true);

INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15000774285','15000774285',md5('15000774285'),'15000774285',null,'阮沿军','310000',null,null,'10000001',false,'0',true);

INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13917819729','13917819729',md5('13917819729'),'13917819729',null,'孙尚江','310000',null,null,'10000001',false,'0',true);
#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'891','17317698007');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'891','15000774285');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'891','13917819729');

insert into bms_user_hierarchy (code,user_no,parent_user_no) values (UUID(),'17317698007','18516183181');
insert into bms_user_hierarchy (code,user_no,parent_user_no) values (UUID(),'15000774285','18516183181');
insert into bms_user_hierarchy (code,user_no,parent_user_no) values (UUID(),'13917819729','18516183181');