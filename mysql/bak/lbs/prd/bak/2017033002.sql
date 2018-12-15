
#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('10000001',null,'000000','FDD','房袋袋','北京房袋袋信息科技有限公司',10000);

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000013','10000001',130,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000014','10000001',140,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'10000001','000000000000000000000000000000000013');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'10000001','000000000000000000000000000000000014');

#增加用户
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13817362435','13817362435',md5('13817362435'),'13817362435',null,'马坤龙','000000',null,null,'10000001',false,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18501667457','18501667457',md5('18501667457'),'18501667457',null,'张瑶莹','000000',null,null,'10000001',false,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18621766691','18621766691',md5('18621766691'),'18621766691',null,'唐哲','000000',null,null,'10000001',false,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18516183181','18516183181',md5('18516183181'),'18516183181',null,'陆志斌','000000',null,null,'10000001',false,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13818205470','13818205470',md5('13818205470'),'13818205470',null,'贺军','000000',null,null,'10000001',false,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18501663340','18501663340',md5('18501663340'),'18501663340',null,'陈腾飞','000000',null,null,'10000001',false,'0',true);

#授权
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'894','13817362435');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'893','18501667457');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'892','18621766691');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'892','18516183181');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'892','13818205470');
INSERT INTO bms_role_user (code,role_code,user_no) values (UUID(),'895','18501663340');

#设置全国的参数
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('13', 'maxAged', '65', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('14', 'maxLoanAmount', '1000', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('15', 'minorEnable', 'Y', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('16', 'villaDiscounted', '0.5', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('17', 'hasSpareDiscounted', '0.7', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('18', 'noSpareDiscounted', '0.6', '000000');

INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('19', 'certificateTime', '4', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('20', 'certificateTime', '4', '310000');


