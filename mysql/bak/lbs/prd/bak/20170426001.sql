#修改最高可贷金额上限10000万元，房屋类型是别墅时乘数为1，有备用房时乘数为1，无备用房时乘数为1
update bms_config set config_value = '10000' where config_name = 'maxLoanAmount';

update bms_config set config_value = '1' where config_name = 'villaDiscounted';

update bms_config set config_value = '1' where config_name = 'hasSpareDiscounted';

update bms_config set config_value = '1' where config_name = 'noSpareDiscounted';