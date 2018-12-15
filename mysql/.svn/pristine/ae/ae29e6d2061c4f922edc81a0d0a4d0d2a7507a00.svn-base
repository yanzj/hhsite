use nlbs;

#插入城市信息
INSERT INTO `nlbs_city` ( `code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '000000', '全国', '全国', '1');
INSERT INTO `nlbs_city` ( `code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '310000', '上海', '上海市', '10');
INSERT INTO `nlbs_city` ( `code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '440100', '广州', '广州市', '20');
INSERT INTO `nlbs_city` ( `code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '330100', '杭州', '杭州市', '30');
#插入渠道信息
INSERT INTO `nlbs_distributor` ( `code`, `parent_code`, `city_code`, `first_character_code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '10000001', NULL, '000000', 'FDD', '房袋袋总部', '房袋袋股份有限公司', '10001');
INSERT INTO `nlbs_distributor` ( `code`, `parent_code`, `city_code`, `first_character_code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '10000011', '10000001', '310000', 'FDDSH', '房袋袋上海分部', '房袋袋股份有限公司上海分公司', '10030');
INSERT INTO `nlbs_distributor` ( `code`, `parent_code`, `city_code`, `first_character_code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '10000013', '10000011', '310000', 'FDDSHPD', '房袋袋浦东', '房袋袋浦东总坛', NULL);
INSERT INTO `nlbs_distributor` ( `code`, `parent_code`, `city_code`, `first_character_code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '10000014', '10000013', '310000', 'FDDSHGQ', '房袋袋浦东高桥', '房袋袋浦东高桥分舵', NULL);
INSERT INTO `nlbs_distributor` ( `code`, `parent_code`, `city_code`, `first_character_code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '10000015', '10000014', '310000', 'FDDSHLQ', '房袋袋浦东凌桥', '房袋袋浦东凌桥', NULL);
INSERT INTO `nlbs_distributor` ( `code`, `parent_code`, `city_code`, `first_character_code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '00000001', '00000101', '310000', 'TR', '庭睿', '上海庭睿金融服务有限公司', '1');
INSERT INTO `nlbs_distributor` ( `code`, `parent_code`, `city_code`, `first_character_code`, `abbr_name`, `full_name`, `order_by_no`) VALUES ( '00000101', NULL, '000000', 'TR', '庭睿全国', '上海庭睿金融服务有限公司全国', '1');
#业务员
INSERT INTO `nlbs_agent` ( `agent_id`, `name`, `be_record_clerk`, `distributor_code`) VALUES ( 'a000001', '夜华', 'Y', '10000001');
INSERT INTO `nlbs_agent` ( `agent_id`, `name`, `be_record_clerk`, `distributor_code`) VALUES ( 'a000002', '钱淑君', 'Y', '10000001');
INSERT INTO `nlbs_agent` ( `agent_id`, `name`, `be_record_clerk`, `distributor_code`) VALUES ( 'a000003', '刘春联', 'Y', '10000001');
INSERT INTO `nlbs_agent` ( `agent_id`, `name`, `be_record_clerk`, `distributor_code`) VALUES ( 'a000004', '朱兴智', 'Y', '10000001');
INSERT INTO `nlbs_agent` ( `agent_id`, `name`, `be_record_clerk`, `distributor_code`) VALUES ( 'a000005', '朱旭', 'Y', '10000001');
INSERT INTO `nlbs_agent` ( `agent_id`, `name`, `be_record_clerk`, `distributor_code`) VALUES ( 'a000006', '封刘柱', 'Y', '10000001');
#业务员-渠道
INSERT INTO `nlbs_agent_distributor` ( `distributor_code`, `agent_id`) VALUES ( '10000011', 'a000001');
INSERT INTO `nlbs_agent_distributor` ( `distributor_code`, `agent_id`) VALUES ( '10000014', 'a000002');
INSERT INTO `nlbs_agent_distributor` ( `distributor_code`, `agent_id`) VALUES ( '10000015', 'a000003');
INSERT INTO `nlbs_agent_distributor` ( `distributor_code`, `agent_id`) VALUES ( '10000013', 'a000004');
INSERT INTO `nlbs_agent_distributor` ( `distributor_code`, `agent_id`) VALUES ( '10000001', 'a000005');
INSERT INTO `nlbs_agent_distributor` ( `distributor_code`, `agent_id`) VALUES ( '10000001', 'a000006');
#进件状态
INSERT INTO `nlbs_apply_loan_status` ( `code`, `bms_code`, `remark`) VALUES ( 'A001', 'a001', '进件');
INSERT INTO `nlbs_apply_loan_status` ( `code`, `bms_code`, `remark`) VALUES ( 'A002', 'a002', '录单中');
INSERT INTO `nlbs_apply_loan_status` ( `code`, `bms_code`, `remark`) VALUES ( 'A003', 'a003', '作废');
#进件材料类型
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('01', '身份证复印件或照片', '身份证复印件或照片');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('02', '户口本复印件或照片（户口页均需提供）', '户口本复印件或照片（户口页均需提供）');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('03', '婚姻证明复印件或照片', '婚姻证明复印件或照片');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('04', '征信报告复印件或照片', '征信报告复印件或照片');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('05', '抵押房产证复印件或照片（产证所有页均需提供）', '抵押房产证复印件或照片（产证所有页均需提供）');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('06', '抵押房产调复印件或照片', '抵押房产调复印件或照片');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('07', '抵押房评估单', '抵押房评估单');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('08', '借款人近六个月银行流水', '借款人近六个月银行流水');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ('09', '抵押房照片（小区外景、大门、楼外景、单元号、门牌号、客厅、阳台、卧室、厨房等）', '抵押房照片（小区外景、大门、楼外景、单元号、门牌号、客厅、阳台、卧室、厨房等）');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ( '10', '备用房产证复印件或照片', '备用房产证复印件或照片');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ( '11', '经营企业的营业执照复印件或照片', '经营企业的营业执照复印件或照片');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ( '12', '法院网查询截图（借款人及配偶、产权人及配偶及经营公司的被执行信息、失信信息）', '法院网查询截图（借款人及配偶、产权人及配偶及经营公司的被执行信息、失信信息）');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ( '13', '借款申请书及外访调查报告', '借款申请书及外访调查报告');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ( '14', '放款卡（银行卡复印件，下方签署开户行、网点、卡号并签名）', '放款卡（银行卡复印件，下方签署开户行、网点、卡号并签名）');
INSERT INTO `nlbs_apply_material_type` ( `code`, `abbr_name`, `full_name`) VALUES ( '16', '其它', '抵押房评估单');

#放款方式表
INSERT INTO `nlbs_credit_type` ( `code`, `name`) VALUES ( 'B001', '收件收据');
INSERT INTO `nlbs_credit_type` ( `code`, `name`) VALUES ( 'B002', '他证');
#借款期限表
INSERT INTO `nlbs_loan_period` (`code`, `period`) VALUES ('01', '1');
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
#抵押类型表
INSERT INTO `nlbs_mortgage_type` (`code`, `type`) VALUES ('001', '一抵');
INSERT INTO `nlbs_mortgage_type` (`code`, `type`) VALUES ('002', '二抵');
INSERT INTO `nlbs_mortgage_type` (`code`, `type`) VALUES ('003', '一抵转单');
INSERT INTO `nlbs_mortgage_type` (`code`, `type`) VALUES ('004', '二抵转单');
#产品表
INSERT INTO `nlbs_product` ( `code`, `name`, `max_loan_amount`) VALUES ('p001',  'P1', '0');
INSERT INTO `nlbs_product` ( `code`, `name`, `max_loan_amount`) VALUES ('p002',  'P2', '500000');
INSERT INTO `nlbs_product` ( `code`, `name`, `max_loan_amount`) VALUES ('p003',  'P1', '100000');
INSERT INTO `nlbs_product` ( `code`, `name`, `max_loan_amount`) VALUES ('p004',  'P2', '500000');
#产品和渠道关
INSERT INTO `nlbs_product_distributor` ( `code`, `product_code`, `distributor_code`, `status`) VALUES ('a001', 'p001', '10000001', '0');
INSERT INTO `nlbs_product_distributor` ( `code`, `product_code`, `distributor_code`, `status`) VALUES ('a002', 'p002', '10000001', '0');
INSERT INTO `nlbs_product_distributor` ( `code`, `product_code`, `distributor_code`, `status`) VALUES ('a003', 'p001', '10000011', '0');
INSERT INTO `nlbs_product_distributor` ( `code`, `product_code`, `distributor_code`, `status`) VALUES ('a004', 'p002', '10000011', '0');
INSERT INTO `nlbs_product_distributor` ( `code`, `product_code`, `distributor_code`, `status`) VALUES ('a005', 'p001', '10000013', '0');
INSERT INTO `nlbs_product_distributor` ( `code`, `product_code`, `distributor_code`, `status`) VALUES ('a006', 'p002', '10000013', '0');
INSERT INTO `nlbs_product_distributor` ( `code`, `product_code`, `distributor_code`, `status`) VALUES ('a007', 'p001', '00000001', '0');
INSERT INTO `nlbs_product_distributor` ( `code`, `product_code`, `distributor_code`, `status`) VALUES ('a008', 'p002', '00000001', '0');
#用户表
INSERT INTO `nlbs_user` (`user_no`, `mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '334559bb-49db-11e7-904c-1866dae83f00', '13681692659', '56901e1aa9792430241becbc7b3d12bf', '13681692659', 'liuzhu.feng@vilio.com.cn', '封刘柱', 'a000006', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, `mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '1ac5b2d1-49db-11e7-904c-1866dae83f00', '17317709318', '23c8c4b4907642899f1617ed8b1b8d85', '17317709318', 'xu.zhu@vilio.com.cn', '朱旭', 'a000005', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, `mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '1361340a-49d9-11e7-904c-1866dae83f00', '18521701740', 'e10adc3949ba59abbe56e057f20f883e', '18521701740', 'xingzhi.zhu@vilio.com.cn', '朱兴智', 'a000004', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, `mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '87bfcd03-46d9-11e7-904c-1866dae83f00', '18221445795', '1e07d421b34f18fb418112509eec21e7', '18221445795', 'chulian.liu@vilio.com.cn', '刘春联', 'a000003', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, `mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '23186064-46ba-11e7-904c-1866dae83f00', '15618396058', 'e700360993d992313dbe03ac862c5a8a', '15618396058', 'shujun.qian@vilio.com.cn', '钱淑君', 'a000002', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, `mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '13334658999', '13334658999', '762cbfc0acdd4b6f38a7ea643a3bb7ee', '13334658999', '12344@qq.com', '夜华', 'a000001', '0', '0');
INSERT INTO `nlbs_user` (`user_no`, `mobile`, `password`, `user_name`, `email`, `full_name`, `agent_id`, `status`, `first_login`) VALUES ( '23186064-46ba-11e7-904c-1866daO03f00', '15010706506', 'e10adc3949ba59abbe56e057f20f883e', '15010706506', '12344@qq.com', '天柱', null, '0', '1');

#菜单表
INSERT INTO `nlbs_menu` ( `code`, `menu_name`, `menu_url`, `menu_order_no`, `menu_level`, `father_menu_code`, `empty_type`) VALUES ('001', '首页', '/nlbs', '0000000001', '1', NULL, '0');
INSERT INTO `nlbs_menu` ( `code`, `menu_name`, `menu_url`, `menu_order_no`, `menu_level`, `father_menu_code`, `empty_type`) VALUES ('002', '进件管理', NULL, '0000000100', '1', NULL, '1');
INSERT INTO `nlbs_menu` ( `code`, `menu_name`, `menu_url`, `menu_order_no`, `menu_level`, `father_menu_code`, `empty_type`) VALUES ('003', '进件申请提交', '/nlbs', '0000000110', '2', '002', '0');
INSERT INTO `nlbs_menu` ( `code`, `menu_name`, `menu_url`, `menu_order_no`, `menu_level`, `father_menu_code`, `empty_type`) VALUES ('004', '进件申请查询', '/nlbs', '0000000120', '2', '002', '0');
#角色表
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ('003', '业务管理', '02');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ('004', '风控管理', '03');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ('005', '风控初审', '03');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ('006', '风控复审', '03');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ('007', '风控终审', '03');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ('008', '渠道管理', '04');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ('009', '渠道经理', '04');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '001', '超级管理员', '01');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '002', '系统管理员', '01');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '991', '业务管理岗', '99');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '992', '业务操作岗', '99');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '891', '经纪人', '89');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '892', '团队长', '89');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '893', '总监', '89');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '894', '总经理', '89');
INSERT INTO `nlbs_role` (`code`, `role_name`, `role_type`) VALUES ( '895', '录单员', '89');
#角色-菜单表
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '440021df-f390-11e6-a5b4-1866dae83f00', '001', '001');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '44066f7a-f390-11e6-a5b4-1866dae83f00', '002', '001');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '440d0ce6-f390-11e6-a5b4-1866dae83f00', '991', '001');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '44130ab4-f390-11e6-a5b4-1866dae83f00', '992', '001');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '4419409b-f390-11e6-a5b4-1866dae83f00', '001', '002');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '441e8963-f390-11e6-a5b4-1866dae83f00', '002', '002');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '4424de9e-f390-11e6-a5b4-1866dae83f00', '991', '002');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '4429171d-f390-11e6-a5b4-1866dae83f00', '992', '002');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '442f2d17-f390-11e6-a5b4-1866dae83f00', '001', '003');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '4435363d-f390-11e6-a5b4-1866dae83f00', '002', '003');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '443b6a7d-f390-11e6-a5b4-1866dae83f00', '991', '003');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '44419056-f390-11e6-a5b4-1866dae83f00', '992', '003');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '4447896c-f390-11e6-a5b4-1866dae83f00', '001', '004');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '444e59ce-f390-11e6-a5b4-1866dae83f00', '002', '004');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '44535af1-f390-11e6-a5b4-1866dae83f00', '991', '004');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '4459dd72-f390-11e6-a5b4-1866dae83f00', '992', '004');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6aba8fe8-0ea6-11e7-adf8-1866dae83f00', '891', '001');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6ac55992-0ea6-11e7-adf8-1866dae83f00', '892', '001');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6acef602-0ea6-11e7-adf8-1866dae83f00', '893', '001');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6ada6930-0ea6-11e7-adf8-1866dae83f00', '894', '001');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6ae5bde3-0ea6-11e7-adf8-1866dae83f00', '891', '002');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6af20b0d-0ea6-11e7-adf8-1866dae83f00', '892', '002');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6afe8ee0-0ea6-11e7-adf8-1866dae83f00', '893', '002');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b0921b3-0ea6-11e7-adf8-1866dae83f00', '894', '002');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b139e94-0ea6-11e7-adf8-1866dae83f00', '891', '003');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b1df1fa-0ea6-11e7-adf8-1866dae83f00', '892', '003');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b261371-0ea6-11e7-adf8-1866dae83f00', '893', '003');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b31690f-0ea6-11e7-adf8-1866dae83f00', '894', '003');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b3c7bd8-0ea6-11e7-adf8-1866dae83f00', '891', '004');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b472fa7-0ea6-11e7-adf8-1866dae83f00', '892', '004');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b52cbfa-0ea6-11e7-adf8-1866dae83f00', '893', '004');
INSERT INTO `nlbs_role_menu` (`code`, `role_code`, `menu_code`) VALUES ( '6b5d77ed-0ea6-11e7-adf8-1866dae83f00', '894', '004');
#角色-用户表
INSERT INTO `nlbs_role_user` (`code`, `role_code`, `user_no`) VALUES ('9566fsf49w1f6w4f9we4fs6f65', '894', '13334658999');																																											                
INSERT INTO `nlbs_role_user` (`code`, `role_code`, `user_no`) VALUES ('9566fsf49w1f6w4f9we4fs6f44', '002', '23186064-46ba-11e7-904c-1866daO03f00');

