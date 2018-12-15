
alter table bms_file add column file_suffix varchar(20)  comment'文件后缀名称';
alter table bms_file add column file_suffix_type varchar(20)  comment'文件后缀分类';
alter table bms_file add column original_file_name varchar(100)  comment'原始文件名';