use plms;
--修改业务员手机号码为20位
alter table plms_agent modify mobile_no varchar(20);
--修改借款人手机号码为20位
alter table plms_customer modify mobile varchar(20);