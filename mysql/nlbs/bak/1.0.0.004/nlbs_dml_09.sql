use nlbs;
#经纪人表
insert into nlbs_agent (agent_id,name,be_record_clerk,distributor_code) values ('a000024','林兆','N','00000005');
insert into nlbs_agent (agent_id,name,be_record_clerk,distributor_code) values ('a000025','谢之磊','N','00000005');
insert into nlbs_agent (agent_id,name,be_record_clerk,distributor_code) values ('a000026','幸炜','N','00000005');
insert into nlbs_agent (agent_id,name,be_record_clerk,distributor_code) values ('a000027','张一','N','00000005');
#用户表
INSERT INTO `nlbs_user` (`user_no`, parent_user_no,`mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '88f345c2-583a-11e7-a91f-1866dae83f00', 'c7f037d0-582e-11e7-a91f-1866dae83f00' ,'18621783163', md5(18621783163), '18621783163', 'joel.lin@vilio.com.cn', '林兆', 'a000024', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, parent_user_no,`mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '9f32c0ff-583a-11e7-a91f-1866dae83f00', '88f345c2-583a-11e7-a91f-1866dae83f00','15601936981', md5(15601936981), '15601936981', 'layne.xie@vilio.com.cn', '谢之磊', 'a000025', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, parent_user_no,`mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( 'c06f6294-583a-11e7-a91f-1866dae83f00', '88f345c2-583a-11e7-a91f-1866dae83f00','17721198476', md5(17721198476), '17721198476', 'martin.xing@vilio.com.cn', '幸炜', 'a000026', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, parent_user_no,`mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '7bc25719-59b8-11e7-951a-d89d672b5244', '88f345c2-583a-11e7-a91f-1866dae83f00','15206117987', md5(15206117987), '15206117987', 'yi.zhang@vilio.com.cn', '张一', 'a000027', '0', '0');

#用户角色
insert into nlbs_role_user(code,role_code,user_no) values (UUID(),'001','88f345c2-583a-11e7-a91f-1866dae83f00');
insert into nlbs_role_user(code,role_code,user_no) values (UUID(),'001','9f32c0ff-583a-11e7-a91f-1866dae83f00');
insert into nlbs_role_user(code,role_code,user_no) values (UUID(),'001','c06f6294-583a-11e7-a91f-1866dae83f00');
insert into nlbs_role_user(code,role_code,user_no) values (UUID(),'001','7bc25719-59b8-11e7-951a-d89d672b5244');