use lbs;

#清理数据，保证可重复执行
DELETE from bms_role_user;
DELETE from bms_role_menu;
DELETE from bms_role;
DELETE from bms_menu;

#初始化角色信息
INSERT INTO bms_role(code,role_name,role_type) values ('001','超级管理员','01');
INSERT INTO bms_role(code,role_name,role_type) values ('002','系统管理员','01');
INSERT INTO bms_role(code,role_name,role_type) values ('991','业务管理岗','99');
INSERT INTO bms_role(code,role_name,role_type) values ('992','业务操作岗','99');

#初始化菜单信息
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('001','首页','/pcweb/toMain','0000000001',null,0,1);
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('002','进件管理',null,'0000000100',null,1,1);
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('003','进件申请提交','/pcweb/apply/toApply','0000000110','002',0,2);
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('004','进件申请查询','/pcweb/apply/toApplyQuery','0000000120','002',0,2);

#初始化角色菜单信息
#首页
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'001','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'002','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'991','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'992','001');
#进件管理
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'001','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'002','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'991','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'992','002');
#进件申请提交
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'001','003');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'002','003');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'991','003');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'992','003');
#进件申请查询
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'001','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'002','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'991','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'992','004');

#初始化角色用户信息
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'001','18621783163');
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'001','15206117987');
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'001','18817880676');
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'001','15601936981');