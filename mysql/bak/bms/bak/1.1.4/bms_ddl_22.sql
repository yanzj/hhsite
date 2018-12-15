alter table bms_loan_apply modify column loan_amount decimal(10,2);

alter table bms_person modify column annual_income decimal(10,2);
alter table bms_person modify column home_address varchar(100);

alter table bms_collateral modify column evaluation_price decimal(10,2);
alter table bms_collateral modify column first_mortgage_balance decimal(10,2);
alter table bms_collateral modify column first_mortgage_amount decimal(10,2);
alter table bms_collateral modify column second_mortgage_amount decimal(10,2);