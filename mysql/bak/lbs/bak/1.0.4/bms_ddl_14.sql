use lbs;
#创建登录信息表
CREATE TABLE `bms_create_file_status` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment '数据主键',
  `code` varchar(36) NOT NULL unique comment '编号',
  `apply_code` varchar(36) NOT NULL   comment '申请编号',
  `status` varchar(100)  comment '状态',
  `file_code` varchar(36) comment '文件编码',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`apply_code`),
  INDEX (`file_code`),
  FOREIGN KEY (`apply_code`) REFERENCES bms_loan_apply(`code`),
  FOREIGN KEY (`file_code`) REFERENCES bms_file(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_create_file_status` COMMENT='文件生成状态表';