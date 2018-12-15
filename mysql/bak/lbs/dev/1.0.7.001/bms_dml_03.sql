use lbs;
# 清空表 bms_user_distributor
delete from bms_user_distributor;

# 根据bms_role_user，bms_user_distributor_temp关联关系补全bms_user_distributor新添字段role_code
insert into bms_user_distributor(code, distributor_code, user_no, role_code)  (SELECT UUID(), b.distributor_code, b.user_no, a.role_code FROM bms_role_user a, bms_user_distributor_temp b WHERE a.user_no = b.user_no and (a.role_code = '005' or a.role_code = '006' or a.role_code = '009'));

#同步角色
INSERT INTO bms_role (code, role_name, role_type) 
VALUES ('991', '业务管理岗', '99');
INSERT INTO bms_role (code, role_name, role_type) 
VALUES ('992', '业务操作岗', '99');
INSERT INTO bms_role (code, role_name, role_type) 
VALUES ('891', '经纪人', '89');
INSERT INTO bms_role (code, role_name, role_type) 
VALUES ('892', '团队长', '89');
INSERT INTO bms_role (code, role_name, role_type) 
VALUES ('893', '总监', '89');
INSERT INTO bms_role (code, role_name, role_type) 
VALUES ('894', '总经理', '89');
