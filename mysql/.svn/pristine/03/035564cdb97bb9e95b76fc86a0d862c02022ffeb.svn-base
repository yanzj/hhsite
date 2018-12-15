
#增加渠道
insert into bms_distributor (code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no)
values('10000001',null,'000000','FDD','房袋袋','北京房袋袋信息科技有限公司',10000);

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

#增加两款产品P1 P2,并配置给渠道
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000013','10000001',130,'P1','P1');
insert into bms_product (code,product_code,order_by_no,full_name,abbr_name) values ('000000000000000000000000000000000014','10000001',140,'P2','P2');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'10000001','000000000000000000000000000000000013');
insert into bms_product_distributor (code,distributor_code,product_code) values (UUID(),'10000001','000000000000000000000000000000000014');

#给武舸、蒋国炜、卢俊彦增加渠道的查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000001','18721176924');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000001','18616774571');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000001','15900992562');

#给鲍龙增加渠道查询权限
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000001','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000007','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000009','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000010','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000011','13968505268');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'10000001','13968505268');

insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000001','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000002','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000003','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000004','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000005','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000006','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000007','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000008','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000009','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000010','13636321925');
insert into bms_user_distributor (code,distributor_code,user_no) values (UUID(),'00000011','13636321925');

#设置全国的参数
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('13', 'maxAged', '65', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('14', 'maxLoanAmount', '1000', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('15', 'minorEnable', 'Y', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('16', 'villaDiscounted', '0.5', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('17', 'hasSpareDiscounted', '0.7', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('18', 'noSpareDiscounted', '0.6', '000000');

INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('19', 'certificateTime', '4', '000000');
INSERT INTO `bms_config` (`code`, `config_name`, `config_value`, `city_code`) VALUES ('20', 'certificateTime', '4', '310000');