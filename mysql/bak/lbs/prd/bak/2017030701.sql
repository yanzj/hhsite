
delete from bms_role_user where user_no in('18621783163','15206117987','18817880676','15601936981');

delete from bms_user where user_no in ('13802926519','18512105234','18817318679','13651792666','13484602606','13818012792',
'18621998006','18507002086','18817880676','15618084828','13636508118','15221617063','15021880353','15601692686',
'13262819936','18706220523','13642773943','13758135848','13305713745','15001887745');



INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('15202190793','15202190793',md5('15202190793'),'15202190793','','饶彩玲  ','310000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('18721176924','18721176924',md5('18721176924'),'18721176924','','武舸  ','310000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);
INSERT INTO bms_user (user_no,mobile,password,user_name,email,full_name,city_code,company_code,department_code,distributor_code,internal_user,status,first_login) values
('13917228473','13917228473',md5('13917228473'),'13917228473','','张麒  ','310000',lpad('1',8,'0'),lpad('1',8,'0'),null,true,'0',true);


update bms_user set city_code = '310000' where user_no in ('15921228421','15021000149','13817403697','13636321925','15001839495','15221897018');

