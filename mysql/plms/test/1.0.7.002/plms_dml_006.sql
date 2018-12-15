delete from plms_pay_online_collect_config ;
#首次上版需要初始化代收配置
#代收配置
INSERT INTO plms_pay_online_collect_config (`code`,`contract_code`,`customer_type`,`pc_flag`,`deposit_name`,`deposit_bank_name`,`deposit_account_no`,`status`)
(select (select UUID() from dual),pci.code,'1','0',m.name,m.bank,m.account_no,'1'
from plms_contract_info pci join plms_apply_info t on t.code = pci.apply_code
join plms_account_info m on t.account_code=m.code)   ;
#代收手续费
insert into plms_collection_online_fee_config (code,online_collect_code,fee_type,fee_value,status)
( select (select UUID() from dual),t.code,'2','5','1' from plms_pay_online_collect_config t  )
;