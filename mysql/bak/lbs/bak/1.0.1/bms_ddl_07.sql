use lbs;

#清理结构，保证可重复执行
DROP TABLE IF EXISTS `bms_role_user`;
DROP TABLE IF EXISTS `bms_role_menu`;
DROP TABLE IF EXISTS `bms_menu`;
DROP TABLE IF EXISTS `bms_role`;

#创建角色表
CREATE TABLE `bms_role` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(3) NOT NULL unique comment'编号',
  `role_name` varchar(20) comment'角色名称',
  `role_type` varchar(2)  comment'角色类别 01 管理员；02 管理层；03 风控；04资产渠道；',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_role` COMMENT='角色表';

#创建菜单表
CREATE TABLE `bms_menu` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(3) NOT NULL unique comment'编号',
  `menu_name` varchar(100) NOT NULL   comment'菜单名称',
  `menu_url` varchar(100)  comment'菜单url',
  `menu_order_no` varchar(10) NOT NULL  comment'排序号',
  `menu_level` int(1) comment'菜单级别',
  `father_menu_code` varchar(3) comment'父菜单编号',
  `empty_type` tinyint(1) comment'是否空菜单 1 是；0 否；',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_menu` COMMENT='菜单表';

#创建角色菜单表
CREATE TABLE `bms_role_menu` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `role_code` varchar(3) NOT NULL   comment'角色编号',
  `menu_code` varchar(3) NOT NULL comment'菜单编号',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`role_code`),
  FOREIGN KEY (`role_code`) REFERENCES bms_role(`code`),
  INDEX (`menu_code`),
  FOREIGN KEY (`menu_code`) REFERENCES bms_menu(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_role_menu` COMMENT='角色菜单表';

#创建角色用户表
CREATE TABLE `bms_role_user` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `role_code` varchar(3) NOT NULL   comment'角色编号',
  `user_no` varchar(11) NOT NULL comment'用户编号',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`role_code`),
  FOREIGN KEY (`role_code`) REFERENCES bms_role(`code`),
  INDEX (`user_no`),
  FOREIGN KEY (`user_no`) REFERENCES bms_user(`user_no`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_role_user` COMMENT='角色用户表';