#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15801866956','15801866956',md5('15801866956'),'15801866956',null,'吴松','000000','00000001','00000001',null,true,'0',true);

#增加嘉定区
insert into bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310119','310000','嘉定','嘉定区','110');