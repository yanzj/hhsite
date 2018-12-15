use bms;

DELETE from bms_apply_material_type where code='15';

INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('15','抵押房评估单','抵押房评估单');