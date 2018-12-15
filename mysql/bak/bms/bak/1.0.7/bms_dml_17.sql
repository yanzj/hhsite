use bms;

update bms_loan_main_status t set t.distr_name = t.full_name;
update bms_loan_main_status t set t.full_name = t.abbr_name;