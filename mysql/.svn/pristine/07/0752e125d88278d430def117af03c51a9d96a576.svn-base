


#贷后系统-运营管理-新增提前还款
INSERT INTO `um`.`um_menu_info` ( `menu_id`, `menu_name`, `super_id`, `status`, `menu_desc`, `url`, `system_id`, `create_time`, `update_time`) 
SELECT '2017101314150000000101','提前还款',t.menu_id,'0','贷后系统提前还款','./prepaymentQuery','plms','2017-10-13 09:55:00','2017-10-13 09:55:00' FROM um_menu_info t	WHERE	t.menu_name = '运营管理' and t.system_id='plms';
#贷后系统-运营管理-新增提前还款绑定角色
INSERT INTO `um`.`um_menu_role` (`menu_id`, `role_id`, `status`, `create_time`, `update_time`)
select '2017101314150000000101', r.role_id, '1', '2017-10-13 09:55:01', '2017-10-13 09:55:01' from um_role_info r where r.role_name='超级管理员' and r.system_id='plms';
INSERT INTO `um`.`um_menu_role` (`menu_id`, `role_id`, `status`, `create_time`, `update_time`)
select '2017101314150000000101', r.role_id, '1', '2017-10-13 09:55:01', '2017-10-13 09:55:01' from um_role_info r where r.role_name='运营管理' and r.system_id='plms';
#贷后系统-运营管理-新增罚息减免
INSERT INTO `um`.`um_menu_info` ( `menu_id`, `menu_name`, `super_id`, `status`, `menu_desc`, `url`, `system_id`, `create_time`, `update_time`) 
SELECT '2017101314150000000102','罚息减免',t.menu_id,'0','贷后系统罚息减免','./penaltyReliefQuery','plms','2017-10-13 09:55:00','2017-10-13 09:55:00' FROM um_menu_info t	WHERE	t.menu_name = '运营管理' and t.system_id='plms';
#贷后系统-运营管理-新增罚息减免
INSERT INTO `um`.`um_menu_role` (`menu_id`, `role_id`, `status`, `create_time`, `update_time`)
select '2017101314150000000102', r.role_id, '1', '2017-10-13 09:55:01', '2017-10-13 09:55:01' from um_role_info r where r.role_name='超级管理员' and r.system_id='plms';
INSERT INTO `um`.`um_menu_role` (`menu_id`, `role_id`, `status`, `create_time`, `update_time`)
select '2017101314150000000102', r.role_id, '1', '2017-10-13 09:55:01', '2017-10-13 09:55:01' from um_role_info r where r.role_name='运营管理' and r.system_id='plms';


DELETE FROM um_menu_info WHERE MENU_NAME = '个人中心' ;
#贷后-个人中心
INSERT INTO `um`.`um_menu_info` ( `menu_id`, `menu_name`, `super_id`, `status`, `menu_desc`, `url`, `system_id`, `create_time`, `update_time`) 
VALUES ('2017090814440000000001', '个人中心', NULL, '1', '个人中心', '', 'plms', '2017-07-03 14:10:36', '2017-09-13 01:58:19');
#进件-个人中心
INSERT INTO `um`.`um_menu_info` ( `menu_id`, `menu_name`, `super_id`, `status`, `menu_desc`, `url`, `system_id`, `create_time`, `update_time`) 
VALUES ('2017070315180000000009', '个人中心', '', '1', '进件系统用户中心', NULL, 'nlbs', '2017-07-03 14:10:36', '2017-10-13 11:11:36');
