use bms;
# 插入系统管理，渠道管理，用户管理的菜单和相应url
INSERT INTO `bms`.`bms_menu` (`code`, `menu_name`, `menu_url`, `menu_order_no`, `menu_level`, `father_menu_code`, `empty_type`) VALUES ('007', '系统管理', NULL, '0000000300', '1', NULL, '1');
INSERT INTO `bms`.`bms_menu` (`code`, `menu_name`, `menu_url`, `menu_order_no`, `menu_level`, `father_menu_code`, `empty_type`) VALUES ('008', '渠道管理', '/pcweb/user/toDistributorQuery', '0000000310', '2', '007', '0');
INSERT INTO `bms`.`bms_menu` (`code`, `menu_name`, `menu_url`, `menu_order_no`, `menu_level`, `father_menu_code`, `empty_type`) VALUES ('009', '用户管理', '/pcweb/user/toUserQuery', '0000000320', '2', '007', '0');

# 赋予超级管理员001 菜单（系统管理，渠道管理，用户管理）权限
INSERT INTO `bms`.`bms_role_menu` (`code`, `role_code`, `menu_code`) VALUES (UUID(), '001', '007');
INSERT INTO `bms`.`bms_role_menu` (`code`, `role_code`, `menu_code`) VALUES (UUID(), '001', '008');
INSERT INTO `bms`.`bms_role_menu` (`code`, `role_code`, `menu_code`) VALUES (UUID(), '001', '009');


# 设置表bms_product_distributor新添字段status默认值0，代表可用
update bms_product_distributor set `status` = '0';

# 复制bms_user_distributor表数据到bms_user_distributor_temp
INSERT INTO bms_user_distributor_temp SELECT * FROM bms_user_distributor;