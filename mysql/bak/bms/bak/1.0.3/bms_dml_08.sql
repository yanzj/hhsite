use bms;

#清理数据，保证可重复执行
DELETE from bms_role_user;
DELETE from bms_role_menu;
DELETE from bms_role;
DELETE from bms_menu;

#初始化角色信息
INSERT INTO bms_role(code,role_name,role_type) values ('001','超级管理员','01');
INSERT INTO bms_role(code,role_name,role_type) values ('002','系统管理员','01');
INSERT INTO bms_role(code,role_name,role_type) values ('003','业务管理','02');
INSERT INTO bms_role(code,role_name,role_type) values ('004','风控管理','03');
INSERT INTO bms_role(code,role_name,role_type) values ('005','风控初审','03');
INSERT INTO bms_role(code,role_name,role_type) values ('006','风控复审','03');
INSERT INTO bms_role(code,role_name,role_type) values ('007','风控终审','03');
INSERT INTO bms_role(code,role_name,role_type) values ('008','渠道管理','04');
INSERT INTO bms_role(code,role_name,role_type) values ('009','渠道经理','04');

#初始化菜单信息
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('001','首页','/pcweb/toMain','0000000001',null,0,1);
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('002','进件管理',null,'0000000100',null,1,1);
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('003','进件申请提交','/pcweb/apply/toApply','0000000110','002',0,2);
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('004','进件申请查询','/pcweb/apply/toApplyQuery','0000000120','002',0,2);
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('005','风控管理',null,'0000000200',null,1,1);
INSERT INTO bms_menu(code,menu_name,menu_url,menu_order_no,father_menu_code,empty_type,menu_level) values ('006','风控初审','/pcweb/riskManage/firstApprove','0000000210','005',0,2);

#初始化角色菜单信息
#首页
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'001','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'002','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'003','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'004','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'005','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'006','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'007','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'008','001');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'009','001');
#进件管理
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'001','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'002','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'003','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'004','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'005','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'006','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'007','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'008','002');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'009','002');
#进件申请提交
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'001','003');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'002','003');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'004','003');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'005','003');
#进件申请查询
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'001','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'002','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'003','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'004','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'005','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'006','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'007','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'008','004');
INSERT INTO bms_role_menu(code,role_code,menu_code) values (uuid(),'009','004');

#初始化角色用户信息
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'001','18621783163');
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'002','15206117987');
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'009','18817880676');
INSERT INTO bms_role_user(code,role_code,user_no) values (uuid(),'001','15601936981');