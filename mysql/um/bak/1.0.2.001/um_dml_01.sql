-- ----------------------------
-- um菜单信息表
-- ----------------------------
INSERT INTO `um_menu_info` VALUES (null, '2017100914150000000001', '贷后管理', '', '1', '进件系统贷后管理', null, 'nlbs', null, null, null, null);
INSERT INTO `um_menu_info` VALUES (null, '2017100914150000000002', '借款业务查询', '2017100914150000000001', '1', '进件系统借款业务查询', './lendedQuery', 'nlbs', null, null, null, null);
INSERT INTO `um_menu_info` VALUES (null, '2017100914150000000003', '还款计划查询', '2017100914150000000001', '1', '进件系统还款计划查询', './repaymentQuery', 'nlbs', null, null, null, null);
INSERT INTO `um_menu_info` VALUES (null, '2017100914150000000004', '逾期记录查询', '2017100914150000000001', '1', '进件系统逾期记录查询', './overdueQuery', 'nlbs', null, null, null, null);

-- ----------------------------
-- um角色菜单关系表
-- ----------------------------
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000001', '2017063014440000000001', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000002', '2017063014440000000001', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000003', '2017063014440000000001', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000004', '2017063014440000000001', '1', null, null, null, null);

INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000001', '2017063014440000000002', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000002', '2017063014440000000002', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000003', '2017063014440000000002', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000004', '2017063014440000000002', '1', null, null, null, null);

INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000001', '2017063014440000000008', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000002', '2017063014440000000008', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000003', '2017063014440000000008', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000004', '2017063014440000000008', '1', null, null, null, null);

INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000001', '2017063014440000000009', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000002', '2017063014440000000009', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000003', '2017063014440000000009', '1', null, null, null, null);
INSERT INTO `um_menu_role` VALUES (null, '2017100914150000000004', '2017063014440000000009', '1', null, null, null, null);
