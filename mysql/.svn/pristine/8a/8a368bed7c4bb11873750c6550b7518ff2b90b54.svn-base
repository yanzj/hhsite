#首次上版需要初始化代收配置
INSERT INTO plms_pay_online_collect_config (`code`,`contract_code`,`customer_type`,`pc_flag`,`deposit_name`,`deposit_bank_name`,`deposit_account_no`,`status`)
(select (select UUID() from dual),pci.code,'1','0',m.name,m.bank,m.account_no,'1'
from plms_contract_info pci join plms_apply_info t on t.code = pci.apply_code
join plms_account_info m on t.account_code=m.code)   