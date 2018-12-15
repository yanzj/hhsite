
-- 修改短信签名描述
update mps_sign_info set sign_desc = '北京房袋袋短信' where inner_sign_no ='1000000000000000001';


-- 增加友盟推送类型
INSERT mps_message_type(message_type,description) VALUES('Umeng','友盟消息推送');



-- ----------------------------
-- Records of mps_app_info
-- ----------------------------
INSERT INTO `mps_app_info` VALUES (null, '3000000000000000002', '房袋袋', '电子商务', '598a6980aed1791779001734', '684b158502867cd5339f00095ebf6674', 'waxvaflidemjed05fqyw0vf7xh4qm1ko', '房袋袋安卓', '1', 'android', null, null, null, null);
INSERT INTO `mps_app_info` VALUES (null, '3000000000000000001', '房袋袋', '财务', '59536b29677baa621100114e', '', 'nv1tekso3tcnnecr13th8g8pvmlweucv', '房袋袋IOS', '1', 'iOS', null, null, null, null);



INSERT INTO `mps_template_info` VALUES (null, '2000000000000000006', 'SMS_86605074', '{\"date\":\"15\",\"days\":\"15\"}', '1', '【${sign_name}】您${date}有一笔账单未处理，已经${days}天，请及时处理。如已处理，请忽略。', '账单未处理提醒', null, null, null, null);

INSERT INTO `mps_template_info` VALUES (null, '2000000000000000007', 'SMS_86555087', '{\"date\":\"15\",\"amount\":\"15\"}', '1', '【${sign_name}】您${date}有一笔账单需处理，总额${amount}元，请及时处理。如已处理，请忽略。', '账单提醒', null, null, null, null);


