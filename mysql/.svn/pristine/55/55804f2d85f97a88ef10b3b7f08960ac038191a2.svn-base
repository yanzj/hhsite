use lbs;
# 删除超级管理员001 菜单（系统管理，渠道管理，用户管理）权限
DELETE FROM `lbs`.`bms_role_menu` WHERE `role_code` = '001' AND `menu_code` = '007';
DELETE FROM `lbs`.`bms_role_menu` WHERE `role_code` = '001' AND `menu_code` = '008';
DELETE FROM `lbs`.`bms_role_menu` WHERE `role_code` = '001' AND `menu_code` = '009';

# 删除系统管理，渠道管理，用户管理的菜单和相应url
DELETE FROM `lbs`.`bms_menu` WHERE `code` = '007';
DELETE FROM `lbs`.`bms_menu` WHERE `code` = '008';
DELETE FROM `lbs`.`bms_menu` WHERE `code` = '009';

