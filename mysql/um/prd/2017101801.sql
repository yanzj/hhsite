-- 增加用户
INSERT INTO `um_user_info` VALUES (null, '201710181022000000013', NULL, '310100', '18019217513', '5530d8ff98762b4894033d81398c5823', 'yong.li@vilio.com.cn', NULL, '1', '1', '李勇', '1', NULL, NULL, NULL, NULL, 'nlbs', '0', '1', '1000000101', '2017063015180000000001', NULL, null, null, NULL, NULL, '125');
-- 增加角色
INSERT INTO `um_role_info` VALUES (null, '2017101811070000000012', '业务经理', '1', '业务经理', 'nlbs', null, null, NULL, NULL);


-- 用户赋值角色
INSERT INTO `um_user_role` VALUES (null, '201710181022000000013', '2017101811070000000012', '1', null, null, NULL, NULL);


-- 角色添加菜单
INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000001', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000006', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000007', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000008', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000010', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000011', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000012', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000001', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000002', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000003', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000004', '2017101811070000000012', '1', null, null, NULL, NULL);
INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000009', '2017101811070000000012', '1', null, null, NULL, NULL);




