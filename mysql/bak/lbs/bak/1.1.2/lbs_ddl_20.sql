use lbs;
#增加业务流水号
alter table bms_user_session_business_flag add column main_code varchar(36) COMMENT "业务流水号";
alter table bms_user_session_business_flag add constraint foreign  key(main_code) REFERENCES bms_loan_main(code);
ALTER TABLE `bms_user_session_business_flag` ADD INDEX  (`main_code`);