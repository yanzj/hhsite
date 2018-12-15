-- 增加系统
INSERT INTO `um_system_info` VALUES (null, 'plms', '贷后管理系统', '1', '', null, null, null, null);


-- 增加系统用户关系
INSERT INTO `um_user_system` VALUES (null, '2017063015180000000025', 'plms', '1', null, null, null, null);
INSERT INTO `um_user_system` VALUES (null, '2017063015180000000026', 'plms', '1', null, null, null, null);
INSERT INTO `um_user_system` VALUES (null, '2017063015180000000027', 'plms', '1', null, null, null, null);
INSERT INTO `um_user_system` VALUES (null, '2017063015180000000024', 'plms', '1', null, null, null, null);

-- 增加角色信息表
INSERT INTO `um_role_info` VALUES (null, '2017071914440000000001', '超级管理员', '1', '超级管理员', 'plms', null, null, null, null);

-- 增加用户角色关系
INSERT INTO `um_user_role` VALUES (null, '2017063015180000000025', '2017071914440000000001', '1', null, null, null, null);
INSERT INTO `um_user_role` VALUES (null, '2017063015180000000026', '2017071914440000000001', '1', null, null, null, null);
INSERT INTO `um_user_role` VALUES (null, '2017063015180000000027', '2017071914440000000001', '1', null, null, null, null);
INSERT INTO `um_user_role` VALUES (null, '2017063015180000000024', '2017071914440000000001', '1', null, null, null, null);

-- ----------------------------
-- um菜单信息表
-- ----------------------------
INSERT INTO `um_menu_info` VALUES ('51', '2017071914440000000001', '首页', null, '1', '贷后管理系统首页', './main', 'plms', '2017-07-03 14:10:36', '2017-07-05 03:29:45', null, null);

-- ----------------------------
-- um角色菜单关系表
-- ----------------------------
INSERT INTO `um_menu_role` VALUES (null, '2017071914440000000001', '2017071914440000000001', '1', '2017-07-03 17:55:57', '2017-07-03 18:04:45', null, null);


INSERT INTO `um_menu_role` VALUES (null, '2017070315180000000007', '2017063014440000000008', '1', '2017-07-03 18:09:04', '2017-07-03 18:09:04', null, null);