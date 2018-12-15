use bms;

#清理数据，保证可重复执行
DELETE from bms_apply_material_type where code='16';

INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('16','抵押房评估单','抵押房评估单');