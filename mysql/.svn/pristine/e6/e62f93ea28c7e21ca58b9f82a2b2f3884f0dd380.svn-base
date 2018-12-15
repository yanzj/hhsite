use nlbs;


#城市表新增是否支持评估
update nlbs_city t set t.allow_inquiry='0';
update nlbs_city t set t.allow_inquiry='1' where t.code in ('310100','440100');
update nlbs_city t set t.code='310100' where t.code='310000';
#
update nlbs_apply_material_type t set t.full_name='其它'  where t.code='16';

#借款期限
delete from nlbs_loan_period;
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('02', '2');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('03', '3');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('04', '4');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('05', '5');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('06', '6');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('07', '7');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('08', '8');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('09', '9');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ( '10', '10');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ( '11', '11');
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ( '12', '12');



# 添加渠道的待处理人
# 上海宏获资产管理有限公司风控部
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000007', '1000000002', '卢俊彦', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000008', '1000000002', '曹丽娜', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000009', '1000000002', '汤娅', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000010', '1000000002', '许昕', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000011', '1000000002', '杨林光', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000012', '1000000002', '彭琳雅', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000013', '1000000002', '王宇霄', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000014', '1000000002', '武舸', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000015', '1000000002', '吴松', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000016', '1000000002', '邵伟康', '上海宏获资产管理有限公司风控部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000017', '1000000002', '蒋国炜', '上海宏获资产管理有限公司风控部');
# 上海宏获资产管理有限公司资产部
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000006', '1000000005', '熊杨阳', '上海宏获资产管理有限公司资产部');
# 上海宏获资产管理有限公司金融科技部
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000027', '1000000006', '张一', '上海宏获资产管理有限公司金融科技部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000024', '1000000006', '林兆', '上海宏获资产管理有限公司金融科技部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000025', '1000000006', '谢之磊', '上海宏获资产管理有限公司金融科技部');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000026', '1000000006', '幸炜', '上海宏获资产管理有限公司金融科技部');
# 上海宏获资产管理有限公司广州
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000007', '1000000011', '卢俊彦', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000008', '1000000011', '曹丽娜', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000009', '1000000011', '汤娅', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000010', '1000000011', '许昕', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000011', '1000000011', '杨林光', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000012', '1000000011', '彭琳雅', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000013', '1000000011', '王宇霄', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000014', '1000000011', '武舸', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000015', '1000000011', '吴松', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000016', '1000000011', '邵伟康', '上海宏获资产管理有限公司广州');
INSERT INTO nlbs.nlbs_pending_user_distributor (user_no, distributor_code, user_full_name, distributor_name) VALUES ('2017063015180000000017', '1000000011', '蒋国炜', '上海宏获资产管理有限公司广州');

# 添加用户的业务管辖城市
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000001');#蔡超
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000002');#鲍龙
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('310100', '2017063015180000000003');#马伟
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('310100', '2017063015180000000004');#李庆涛
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('310100', '2017063015180000000005');#张毅
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('310100', '2017063015180000000006');#熊杨阳
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000007');#卢俊彦
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000008');#曹丽娜
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000009');#汤娅
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000010');#许昕
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000011');#杨林光
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000012');#彭琳雅
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000013');#王宇霄
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000014');#武舸
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000015');#吴松
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000016');#邵伟康
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000017');#蒋国炜
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000018');#唐庆龙
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('440100', '2017063015180000000019');#李双有
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('440100', '2017063015180000000020');#屈建聪
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('440100', '2017063015180000000021');#黄伟东
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('440100', '2017063015180000000022');#江露
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('440100', '2017063015180000000023');#李鉴培
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000025');#谢之磊
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000026');#幸炜
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000027');#张一
INSERT INTO nlbs.nlbs_user_govern_city (city_code, user_no) VALUES ('000000', '2017063015180000000024');#林兆





