use bms;

#清理数据，保证可重复执行
DELETE from bms_user where 1= 1;
DELETE from bms_distributor where 1= 1;
DELETE from bms_department where 1= 1;
DELETE from bms_company where 1= 1;
DELETE from bms_district where 1= 1;
DELETE from bms_city where 1= 1;
DELETE from bms_apply_material_type where 1= 1;
DELETE from bms_loan_operate_type where 1= 1;
DELETE from bms_loan_main_status where 1= 1;
DELETE from bms_mortgage_type where 1= 1;
DELETE from bms_house_type where 1= 1;
DELETE from bms_marital_status where 1= 1;
DELETE from bms_loan_period where 1= 1;

#初始化借款期限表
INSERT INTO bms_loan_period(code,period) values ('01',1);
INSERT INTO bms_loan_period(code,period) values ('02',2);
INSERT INTO bms_loan_period(code,period) values ('03',3);
INSERT INTO bms_loan_period(code,period) values ('04',4);
INSERT INTO bms_loan_period(code,period) values ('05',5);
INSERT INTO bms_loan_period(code,period) values ('06',6);
INSERT INTO bms_loan_period(code,period) values ('07',7);
INSERT INTO bms_loan_period(code,period) values ('08',8);
INSERT INTO bms_loan_period(code,period) values ('09',9);
INSERT INTO bms_loan_period(code,period) values ('10',10);
INSERT INTO bms_loan_period(code,period) values ('11',11);
INSERT INTO bms_loan_period(code,period) values ('12',12);

#初始化婚姻状况表
INSERT INTO bms_marital_status(code,abbr_name,full_name) values ('01','未婚','未婚');
INSERT INTO bms_marital_status(code,abbr_name,full_name) values ('02','已婚','已婚');
INSERT INTO bms_marital_status(code,abbr_name,full_name) values ('03','离异','离异');
INSERT INTO bms_marital_status(code,abbr_name,full_name) values ('04','再婚','再婚');
INSERT INTO bms_marital_status(code,abbr_name,full_name) values ('05','丧偶','丧偶');

#初始化房屋类型表
INSERT INTO bms_house_type(code,abbr_name,full_name) values ('01','公寓','公寓');
INSERT INTO bms_house_type(code,abbr_name,full_name) values ('02','别墅','别墅');

#初始化抵押类型表
INSERT INTO bms_mortgage_type(code,abbr_name,full_name) values ('01','一抵','一抵');
INSERT INTO bms_mortgage_type(code,abbr_name,full_name) values ('02','二抵','二抵');
INSERT INTO bms_mortgage_type(code,abbr_name,full_name) values ('03','一抵转单','一抵转单');
INSERT INTO bms_mortgage_type(code,abbr_name,full_name) values ('04','二抵转单','二抵转单');

#初始化业务状态表
INSERT INTO bms_loan_main_status(code,abbr_name,full_name,distr_name) values ('00','待提交','待提交','待提交');
INSERT INTO bms_loan_main_status(code,abbr_name,full_name,distr_name) values ('01','待初审','审批中','待初审');
INSERT INTO bms_loan_main_status(code,abbr_name,full_name,distr_name) values ('02','退回修改','退回修改','退回修改');
INSERT INTO bms_loan_main_status(code,abbr_name,full_name,distr_name) values ('98','已关闭','已关闭','已关闭');
INSERT INTO bms_loan_main_status(code,abbr_name,full_name,distr_name) values ('99','作废','作废','作废');

#初始化操作类型表
INSERT INTO bms_loan_operate_type(code,abbr_name,full_name) values ('00','暂存','暂存');
INSERT INTO bms_loan_operate_type(code,abbr_name,full_name) values ('01','修改暂存','修改暂存');
INSERT INTO bms_loan_operate_type(code,abbr_name,full_name) values ('02','提交申请','提交申请');
INSERT INTO bms_loan_operate_type(code,abbr_name,full_name) values ('03','补充材料','补充材料');

#初始化进件材料类型表
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('01','借款申请书&外访调查报告','借款申请书&外访调查报告');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('02','身份证','身份证');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('03','户口本','户口本');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('04','婚姻证明','婚姻证明');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('05','产证','产证');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('06','产调','产调');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('07','备用房产证','备用房产证');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('08','征信报告','征信报告');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('09','银行流水','银行流水');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('10','抵押房照片','抵押房照片');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('11','网查风险信息','网查风险信息');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('12','经营企业营业执照','经营企业营业执照');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('13','放款卡','放款卡');
INSERT INTO bms_apply_material_type(code,abbr_name,full_name) values ('14','其它','其它');

#初始化城市数据
INSERT INTO bms_city(code,abbr_name,full_name,order_by_no) values ('000000','全国','全国',1);
INSERT INTO bms_city(code,abbr_name,full_name,order_by_no) values ('310000','上海','上海市',10);
INSERT INTO bms_city(code,abbr_name,full_name,order_by_no) values ('440100','广州','广州市',20);
INSERT INTO bms_city(code,abbr_name,full_name,order_by_no) values ('330100','杭州','杭州市',30);

#初始化区县数据
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310101','310000','黄浦','黄浦区',10);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310107','310000','静安','静安区',20);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310115','310000','浦东','浦东新区',30);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310104','310000','徐汇','徐汇区',40);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310109','310000','虹口','虹口区',50);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310110','310000','杨浦','杨浦区',60);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310113','310000','宝山','宝山区',70);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310112','310000','闵行','闵行区',80);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310118','310000','青浦','青浦区',90);
INSERT INTO bms_district(code,city_code,abbr_name,full_name,order_by_no) values ('310117','310000','松江','松江区',100);

#初始化公司数据
INSERT INTO bms_company(code,parent_code,city_code,abbr_name,full_name) 
values (lpad('1',8,'0'),null,'000000','宏获资产','上海宏获资产管理有限公司');
INSERT INTO bms_company(code,parent_code,city_code,abbr_name,full_name) 
values (lpad('2',8,'0'),lpad('1',8,'0'),'310000','宏获资产上海','上海宏获资产管理有限公司');
INSERT INTO bms_company(code,parent_code,city_code,abbr_name,full_name) 
values (lpad('3',8,'0'),lpad('1',8,'0'),'440100','宏获资产广州','上海宏获资产管理有限公司广州分公司');
INSERT INTO bms_company(code,parent_code,city_code,abbr_name,full_name) 
values (lpad('4',8,'0'),lpad('1',8,'0'),'330100','宏获资产杭州','上海宏获资产管理有限公司杭州分公司');

#初始化部门数据
INSERT INTO bms_department(code,company_code,abbr_name,full_name) values (lpad('1',8,'0'),lpad('1',8,'0'),'风控部','风险部');
INSERT INTO bms_department(code,company_code,abbr_name,full_name) values (lpad('2',8,'0'),lpad('1',8,'0'),'金融市场部','金融市场部');
INSERT INTO bms_department(code,company_code,abbr_name,full_name) values (lpad('3',8,'0'),lpad('1',8,'0'),'人事行政部','人事行政部');
INSERT INTO bms_department(code,company_code,abbr_name,full_name) values (lpad('4',8,'0'),lpad('1',8,'0'),'资产部','资产部');
INSERT INTO bms_department(code,company_code,abbr_name,full_name) values (lpad('5',8,'0'),lpad('1',8,'0'),'金融科技部','金融科技部');
INSERT INTO bms_department(code,company_code,abbr_name,full_name) values (lpad('6',8,'0'),lpad('1',8,'0'),'财务部','财务部');
INSERT INTO bms_department(code,company_code,abbr_name,full_name) values (lpad('7',8,'0'),lpad('1',8,'0'),'运营部','运营部');

#初始化渠道公司数据
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('1',8,'0'),null,'310000','TR','庭睿','上海庭睿金融服务有限公司',1);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('2',8,'0'),null,'310000','DSD','大实代','上海大实代金融信息服务有限公司',100);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('3',8,'0'),null,'310000','HP','合盘','上海合盘金融信息服务股份有限公司',200);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('4',8,'0'),null,'310000','HJ','瀚匠','上海瀚匠金融信息服务有限公司',300);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('5',8,'0'),null,'310000','KD','口袋','上海融笃资产管理有限公司',400);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('6',8,'0'),null,'310000','YW','盈旺','上海盈旺金融信息服务有限公司 ',500);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('7',8,'0'),null,'310000','ZL','质隆','上海质隆资产管理有限公司',600);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('8',8,'0'),null,'310000','ZK','舟凯','上海舟凯投资管理有限公司',700);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('9',8,'0'),null,'310000','BF','百丰','上海百丰金融服务有限公司',800);
INSERT INTO bms_distributor(code,parent_code,city_code,first_character_code,abbr_name,full_name,order_by_no) 
values (lpad('10',8,'0'),null,'310000','SY','首壹','上海首壹财富金融信息服务有限公司',900);


#初始化用户数据
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13802926519','13802926519',md5('13802926519'),'13802926519','caichao@vilio.com.cn','蔡超  ','000000',lpad('1',8,'0'),null,null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13666601676','13666601676',md5('13666601676'),'13666601676','rise.qi@vilio.com.cn','祁治详','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15900992562','15900992562',md5('15900992562'),'15900992562','junyan.lu@vilio.com.cn','卢俊彦','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18512105234','18512105234',md5('18512105234'),'18512105234','simon.tang@vilio.com.cn','唐庆龙','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18817318679','18817318679',md5('18817318679'),'18817318679','lotus.piao@vilio.com.cn','朴莲花','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15921228421','15921228421',md5('15921228421'),'15921228421','kevin.jian@vilio.com.cn','简易  ','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15021000149','15021000149',md5('15021000149'),'15021000149','tanya.tao@vilio.com.cn','陶妍  ','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13651792666','13651792666',md5('13651792666'),'13651792666','mingrui.zhang@vilio.com.cn','张铭瑞','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13817403697','13817403697',md5('13817403697'),'13817403697','lina.cao@vilio.com.cn','曹丽娜','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13484602606','13484602606',md5('13484602606'),'13484602606','sigma.wang@vilio.com.cn','王琦  ','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18721675945','18721675945',md5('18721675945'),'18721675945','hanwen.cao@vilio.com.cn','曹瀚文','000000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13818012792','13818012792',md5('13818012792'),'13818012792','jerry.xu@vilio.com.cn','徐佳昊','000000',lpad('1',8,'0'),lpad('2',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18621998006','18621998006',md5('18621998006'),'18621998006','mandy.sun@vilio.com.cn','孙敏杰','000000',lpad('1',8,'0'),lpad('3',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18507002086','18507002086',md5('18507002086'),'18507002086','rong.wu@vilio.com.cn','吴榕  ','000000',lpad('1',8,'0'),lpad('3',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13968505268','13968505268',md5('13968505268'),'13968505268','vincent.bao@vilio.com.cn','鲍龙  ','000000',lpad('1',8,'0'),lpad('4',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13636321925','13636321925',md5('13636321925'),'13636321925','wei.ma@vilio.com.cn','马伟  ','000000',lpad('1',8,'0'),lpad('4',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15001839495','15001839495',md5('15001839495'),'15001839495','qingtao.li@vilio.com.cn','李庆涛','000000',lpad('1',8,'0'),lpad('4',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15221897018','15221897018',md5('15221897018'),'15221897018','eason.zhang@vilio.com.cn','张毅  ','000000',lpad('1',8,'0'),lpad('4',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18621783163','18621783163',md5('18621783163'),'18621783163','joel.lin@vilio.com.cn','林兆  ','000000',lpad('1',8,'0'),lpad('5',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15601936981','15601936981',md5('15601936981'),'15601936981','layne.xie@vilio.com.cn','谢之磊','000000',lpad('1',8,'0'),lpad('5',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15206117987','15206117987',md5('15206117987'),'15206117987','yi.zhang@vilio.com.cn','张一  ','000000',lpad('1',8,'0'),lpad('5',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18817880676','18817880676',md5('18817880676'),'18817880676','speakshare.zhu@vilio.com.cn','朱鹏聪','000000',lpad('1',8,'0'),lpad('5',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15618084828','15618084828',md5('15618084828'),'15618084828','lily.li@vilio.com.cn','李景平','000000',lpad('1',8,'0'),lpad('6',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13636508118','13636508118',md5('13636508118'),'13636508118','stella.tan@vilio.com.cn','谭晓华','000000',lpad('1',8,'0'),lpad('6',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15221617063','15221617063',md5('15221617063'),'15221617063','jiayun.kuang@vilio.com.cn','匡佳云','000000',lpad('1',8,'0'),lpad('7',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15021880353','15021880353',md5('15021880353'),'15021880353','faqin.su@vilio.com.cn','苏发勤','000000',lpad('1',8,'0'),lpad('7',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15601692686','15601692686',md5('15601692686'),'15601692686','yanan.song@vilio.com.cn','宋雅南','000000',lpad('1',8,'0'),lpad('7',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13262819936','13262819936',md5('13262819936'),'13262819936','zoe.huang@vilio.com.cn','黄慧云','000000',lpad('1',8,'0'),lpad('7',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18706220523','18706220523',md5('18706220523'),'18706220523','yi.xiong@vilio.com.cn','熊毅  ','000000',lpad('1',8,'0'),lpad('7',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15914375421','15914375421',md5('15914375421'),'15914375421','weidong.huang@vilio.com.cn','黄伟东','440100',lpad('3',8,'0'),null,null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13642773943','13642773943',md5('13642773943'),'13642773943','jiancong.qu@vilio.com.cn','屈建聪','440100',lpad('3',8,'0'),null,null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13758135848','13758135848',md5('13758135848'),'13758135848','jing.he@vilio.com.cn','何靖  ','330100',lpad('4',8,'0'),null,null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('13305713745','13305713745',md5('13305713745'),'13305713745','jianjun.zhu@vilio.com.cn','朱建军','330100',lpad('4',8,'0'),null,null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('18668481110','18668481110',md5('18668481110'),'18668481110','lin.zhang@vilio.com.cn','张林  ','330100',lpad('4',8,'0'),null,null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values 
('15001887745','15001887745',md5('15001887745'),'15001887745','weijian.zhu@vilio.com.cn','朱伟健','000000',lpad('1',8,'0'),null,null,true,'0',true);
#模拟几个渠道用户
