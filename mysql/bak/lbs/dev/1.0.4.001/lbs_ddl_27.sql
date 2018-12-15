use lbs;
create table bms_user_hierarchy(
id int(12) not null auto_increment comment '数据主键',
code varchar(36) not null unique comment '编号',
user_no varchar(11) not null comment '用户编号',
parent_user_no varchar(11) not null comment '上级用户编号',
primary key (id),
index (code)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table bms_user_hierarchy comment = '用户层级表';