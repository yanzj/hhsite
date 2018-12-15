
-- 增加公司管理员角色
INSERT INTO `um_role_info` VALUES (null, '2017112017370000000013', '公司管理', '1', '公司管理', 'plms', null, null, NULL, NULL);


INSERT INTO um_menu_role(menu_id,role_id,status) select menu_id,'2017112017370000000013',status from um_menu_role WHERE role_id='2017071914440000000001' and status='1';



update um_user_role SET role_id='2017112017370000000013' WHERE user_id='2017111714510000000013' and role_id='2017071914440000000001';


update um_user_role SET role_id='2017063014440000000003' WHERE user_id='2017063015180000000002' and role_id='2017063014440000000010';



INSERT INTO `um_user_system` VALUES (null, '2017063015180000000002', 'plms', '1', null, null, null, null);



INSERT INTO `um_user_role` VALUES (null, '2017063015180000000002', '2017112017370000000013', '1', null, null, null, null);








