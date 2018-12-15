-- ----------------------------
-- 安卓APK版本信息表
-- ----------------------------
DROP TABLE IF EXISTS `oas_android_version`;
CREATE TABLE `oas_android_version` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id，主键',
  `version` varchar(3) NOT NULL COMMENT '版本号',
  `version_detail` varchar(10) NOT NULL COMMENT '版本描述',
  `title` varchar(500) NOT NULL COMMENT '标题',
  `detail` varchar(500) NOT NULL COMMENT '版本详情',
  `force` varchar(2) NOT NULL COMMENT '强制更新 0-强制 1-可选',
  `download_url` varchar(100) NOT NULL COMMENT '下载地址',
  `apk_size` varchar(10) NOT NULL COMMENT '大小',
  `status` varchar(2) NOT NULL COMMENT '状态(00、无效;01、有效;)',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `remark` varchar(150) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_version` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app版本信息表';