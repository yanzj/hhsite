use bms;
#创建产品表
CREATE TABLE `bms_product` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `product_code` varchar(50) comment'产品编号',
  `order_by_no` int(4) comment'产品序号',
  `full_name` varchar(100) comment'产品全称',
  `abbr_name` varchar(100) comment'产品简称',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`product_code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_product` COMMENT='产品表';

#创建产品和渠道关联表
CREATE TABLE `bms_product_distributor` (
  `id` int(12) NOT NULL AUTO_INCREMENT comment'数据主键',
  `code` varchar(36) NOT NULL unique comment'编号',
  `product_code` varchar(36) comment'产品编号',
  `distributor_code` varchar(36) comment'渠道编号',
  PRIMARY KEY (`id`),
  INDEX (`code`),
  INDEX (`product_code`),
  FOREIGN KEY (`product_code`) REFERENCES bms_product(`code`),
  INDEX (`distributor_code`),
  FOREIGN KEY (`distributor_code`) REFERENCES bms_distributor(`code`)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
ALTER TABLE `bms_product_distributor` COMMENT='产品和渠道关联表';


#增加产品编码
alter table bms_loan_apply add column product_code varchar(36) COMMENT "产品编码";
alter table bms_loan_apply add constraint foreign  key(product_code) REFERENCES bms_product(code);
ALTER TABLE `bms_loan_apply` ADD INDEX  (`product_code`);