use nlbs;


#待办任务表 字段修改
ALTER TABLE nlbs_todo CHANGE COLUMN bps_code serial_no VARCHAR(36) NOT NULL COMMENT "待办任务所属系统序列号";
ALTER TABLE nlbs_todo MODIFY COLUMN user_full_name VARCHAR(20) COMMENT "用户姓名";

# 缓存业务员编号
alter table nlbs_login_info add column agent_id varchar(36) COMMENT "业务员编号"; 