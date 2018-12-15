#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'891','17317698007');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'891','15000774285');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'891','13917819729');

insert into bms_user_hierarchy (code,user_no,parent_user_no) values (UUID(),'17317698007','18516183181');
insert into bms_user_hierarchy (code,user_no,parent_user_no) values (UUID(),'15000774285','18516183181');
insert into bms_user_hierarchy (code,user_no,parent_user_no) values (UUID(),'13917819729','18516183181');