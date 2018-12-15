#增加渠道用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15821139340','15821139340',md5('15821139340'),'15821139340',null,'王永生','310000',null,null,'10000001',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'992','15821139340');


insert into bms_user_hierarchy (code,user_no,parent_user_no) values (UUID(),'15821139340','18516183181');