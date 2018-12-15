use nlbs;


# 进件状态映射关系

INSERT INTO nlbs.nlbs_apply_loan_status (code, name, bms_code, bms_name) VALUES ('07', '放款中', '0081', '待查询产调');

UPDATE nlbs.nlbs_apply_loan_status SET bms_code = '0061' WHERE code = '04' AND  bms_name = '待业务报备';
UPDATE nlbs.nlbs_apply_loan_status SET bms_code = '0062' WHERE code = '04' AND  bms_name = '待生成合同';
UPDATE nlbs.nlbs_apply_loan_status SET bms_code = '0063' WHERE code = '04' AND  bms_name = '驳回重新复核审批通知单';
UPDATE nlbs.nlbs_apply_loan_status SET bms_code = '0070' WHERE code = '05' AND  bms_name = '待签约公证';
UPDATE nlbs.nlbs_apply_loan_status SET bms_code = '0090' WHERE code = '07' AND  bms_name = '待检查放款条件';
UPDATE nlbs.nlbs_apply_loan_status SET bms_code = '0091' WHERE code = '07' AND  bms_name = '待放款';
UPDATE nlbs.nlbs_apply_loan_status SET bms_code = '0100' WHERE code = '08' AND  bms_name = '放款成功';


UPDATE nlbs.nlbs_apply_loan_status SET name = '授信放款中' WHERE code = '07';
UPDATE nlbs.nlbs_apply_loan_status SET name = '授信成功' WHERE code = '08';
