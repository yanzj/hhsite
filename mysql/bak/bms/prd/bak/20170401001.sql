use bms;
insert into bms_role_user (code,role_code,user_no)
select UUID(),'005',user_no from bms_role_user where role_code = '006';