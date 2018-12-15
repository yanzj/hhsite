use plms;
update plms_paid_info set interest = '52144.42' where code = '2a9d6282-2a7c-4ac3-9c5e-2f92652babe3';

update plms_account_detail set remaining_quota = '0',interest = '52144.42' where code = '1e441885-deda-4236-bae6-3ae8f5308424';

update plms_account set service_fee = '5600',principal = '500000.00',interest = '52144.42' where code = '32a07f46-e2b3-4a47-a22e-fe9df9eccbd7';
