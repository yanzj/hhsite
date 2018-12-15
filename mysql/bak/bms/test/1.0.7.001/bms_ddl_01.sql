use bms;
# 表bms_product_distributor添加字段 status
alter table bms_product_distributor add status varchar(1) not Null;

# 表bms_user_distributor添加字段 role_code
alter table bms_user_distributor add role_code varchar(3) not Null;