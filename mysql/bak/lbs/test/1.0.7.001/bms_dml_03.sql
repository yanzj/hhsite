use lbs;
# 清空表 bms_user_distributor
delete from bms_user_distributor;

# 根据bms_role_user，bms_user_distributor_temp关联关系补全bms_user_distributor新添字段role_code
insert into bms_user_distributor(code, distributor_code, user_no, role_code)  (SELECT UUID(), b.distributor_code, b.user_no, a.role_code FROM bms_role_user a, bms_user_distributor_temp b WHERE a.user_no = b.user_no and (a.role_code = '005' or a.role_code = '006' or a.role_code = '009'));
