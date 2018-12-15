alter table plms_collection_online_fee_config modify column status varchar(2) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_contract_fund_account modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_fund_account_detail modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_fund_account_info modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_fund_account_type modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_gb_city modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_apply_audit modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_apply_detail modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_apply_detail_temp modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_apply_info modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_apply_temp modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_collection_trans_his modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_online_collect_config modify column status varchar(2) not null DEFAULT '1' COMMENT '状态(1 可用;0 不可用;3 核验中)';
alter table plms_pay_online_pay_config modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(1 可用;0 不可用;3 核验中)';
alter table plms_pay_payment_fee_type modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_payment_trans_his modify column status varchar(2) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_pay_receiver_type modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_route_info modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_schedule_collection_his modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_schedule_detail_collection_his modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_subject_account_type_config modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_subject_account_type_mapping modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
alter table plms_user_role_pay_apply_status_map modify column status varchar(1) not null DEFAULT '1' COMMENT '状态(0、无效;1、有效)';
