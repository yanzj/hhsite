-- ----------------------------
-- 消息类型
-- ----------------------------
INSERT INTO `mps_message_type` VALUES (null, 'email', '邮件');
INSERT INTO `mps_message_type` VALUES (null, 'SMS', '短信');
INSERT INTO `mps_message_type` VALUES (null, 'WeChat', '微信');
INSERT INTO `mps_message_type` VALUES (null, 'Instation', '站内信');


-- ----------------------------
-- 签名信息表
-- ----------------------------
INSERT INTO `mps_sign_info` VALUES (null, '1000000000000000001', '北京房袋袋', '北京房袋袋', '北京房袋袋短信验证码', '1', '2017-06-29 19:33:28', '2017-06-29 19:40:42', null, null);



-- ----------------------------
-- 模板信息表
-- ----------------------------
INSERT INTO `mps_template_info` VALUES (null, '2000000000000000001', 'SMS_72925065', '{\"code\":\"15\",\"time\":\"15\"}', '1', '【${sign_name}】您正在登录验证，验证码${code}，请在${time}分钟内按提示提交验证码，切勿将验证码泄露于他人。', '验证码', '2017-06-29 11:56:32', '2017-06-30 19:23:47', null, null);



DROP FUNCTION IF EXISTS `currval`;  
DROP FUNCTION IF EXISTS `nextval`; 

-- ----------------------------
-- 序列
-- ----------------------------
INSERT INTO `sequence` VALUES ('SERIAL_NO', '47', '1');

DELIMITER //  
  
CREATE  FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS int(11)  
  
    READS SQL DATA  
  
    DETERMINISTIC  
  
BEGIN  
  
DECLARE VALUE INTEGER;  
  
SET VALUE = 0;  
  
SELECT current_value INTO VALUE FROM sequence WHERE NAME = seq_name;  
  
RETURN VALUE;  
  
END//  
  
DELIMITER ;



  
DELIMITER //  
  
CREATE  FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int(11)  
  
    DETERMINISTIC  
  
BEGIN  
  
UPDATE sequence SET current_value = current_value + increment WHERE NAME = seq_name;  
  
RETURN currval(seq_name);  
  
END//  
  

DELIMITER ;