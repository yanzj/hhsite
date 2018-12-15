use bms;
#把已关闭改为撤单
update bms_loan_main_status set abbr_name = "撤单",full_name = "撤单",distr_name = "撤单" where `code` = '98';

update bms_loan_operate_type set abbr_name = "撤单",full_name = "撤单" where `code` = '06';