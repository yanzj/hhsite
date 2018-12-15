#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15801866956','15801866956',md5('15801866956'),'15801866956',null,'吴松','000000','00000001','00000001',null,true,'0',true);

#所有渠道的初审权限
insert into bms_user_distributor (code,distributor_code,user_no)
select UUID(),code,'15801866956' from bms_distributor;

#添加角色
insert into bms_role_user (code,role_code,user_no) values (UUID(),'005','15801866956');

#增加嘉定区
insert into bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310119','310000','嘉定','嘉定区','110');