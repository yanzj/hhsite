use fms;

#上传文件信息表
DROP TABLE IF EXISTS `fms_file_load`;
CREATE TABLE `fms_file_load` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interface_type` varchar(255) DEFAULT NULL COMMENT '接口类型（OSS 阿里）',
  `serial_no` varchar(255) DEFAULT NULL COMMENT '流水号',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(255) DEFAULT NULL COMMENT '存储路径',
  `file_suffix` varchar(50) DEFAULT NULL COMMENT '文件后缀名称',
  `original_file_name` varchar(100) DEFAULT NULL COMMENT '原始文件名',
  `status` varchar(2) DEFAULT '0' COMMENT '状态(0 创建，1上传失败，2上传成功，3上传未知)',
  `url` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

ALTER TABLE `fms_file_load` COMMENT='上传文件信息表';