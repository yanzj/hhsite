use lbs;

#清理结构，保证可重复执行
DROP TABLE IF EXISTS `bms_config`;

#系统业务参数配置表
CREATE TABLE `bms_config` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `config_name` varchar(20) NOT NULL comment'参数名',
  `config_value` varchar(200) NOT NULL comment'参数值',
  PRIMARY KEY (`id`),
  INDEX (`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_apply_material_file` COMMENT='系统业务参数配置表';