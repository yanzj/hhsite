
alter table pcfs_login_info add column login_status VARCHAR(2) DEFAULT NULL COMMENT '登录状态：0-登出，1-登录';

alter table pcfs_messages add column message_type VARCHAR(5) DEFAULT NULL COMMENT '消息类型：00-逾期，01-扣款';

