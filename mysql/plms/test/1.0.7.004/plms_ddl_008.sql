

alter table plms_pay_apply_info add column receiver_abbr_name VARCHAR(100) NULL COMMENT '收款方名称简称';

alter table plms_pay_apply_temp add column receiver_abbr_name VARCHAR(100) NULL COMMENT '收款方名称简称';