#调整复审用户
DELETE FROM  bms_role_user where user_no = '15021000149' and role_code = '006';

delete from bms_user_distributor where code = 'ac9cfd3a-150c-11e7-951a-d89d672b5244';


insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','15900992562');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','15900992562');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','15900992562');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','15900992562');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000011','15900992562');