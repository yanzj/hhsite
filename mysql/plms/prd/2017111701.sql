use plms;
insert into plms_fund_side_product(code,product_name,is_principal_instead,is_interest_instead,company_code
,bms_code,status,paid_days_computational_rule) values (UUID(),'P1-展期','1','1','00646f63-a021-42cd-bdd8-caf1a9342683',
'A00051','1','02');

insert into plms_fund_side_product(code,product_name,is_principal_instead,is_interest_instead,company_code
,bms_code,status,paid_days_computational_rule) values (UUID(),'P2-房钛','1','1','02cec25c-2a47-485d-8ccb-d432c64477d0',
'A00052','1','02');

delete from plms_company where code  not in ('67854214-971a-11e7-9978-1866dae83f00','6b00673d-04c5-4115-90dc-e7df99752d7d','3e810544-2e9a-4123-a7a1-ef9a193aeba6',
'089171d9-97a2-409c-ade1-08d10d92b808','00646f63-a021-42cd-bdd8-caf1a9342683','02cec25c-2a47-485d-8ccb-d432c64477d0');

update plms_company set bms_code = 'A00050' where code = '089171d9-97a2-409c-ade1-08d10d92b808';

update plms_company set bms_code = 'A00051' where code = '00646f63-a021-42cd-bdd8-caf1a9342683';

update plms_company set bms_code = 'A00052' where code = '02cec25c-2a47-485d-8ccb-d432c64477d0';