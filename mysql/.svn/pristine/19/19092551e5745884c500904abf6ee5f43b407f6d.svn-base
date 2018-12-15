use um;
update um_role_info set role_name = '还款计划确认',role_desc = '还款计划确认' where role_id = '2017090814440000000007';

insert into um_menu_role(menu_id,role_id,status) values('2017080714510000000003','2017090814440000000006','1');

insert into um_menu_role(menu_id,role_id,status) values('2017080714510000000004','2017090814440000000006','1');

update um_menu_role set status = '2' where role_id = '2017090814440000000007';

insert into um_menu_role(menu_id,role_id,status) values('2017073114510000000001','2017090814440000000007','1');

insert into um_menu_role(menu_id,role_id,status) values('2017073114510000000002','2017090814440000000007','1');

update um_user_role set status = '2' where role_id = '2017090814440000000007' and user_id in
(select user_id from um_user_info where user_name in ('唐庆龙','秦谷香','朴莲花','曹维佳','陈红'));

update um_user_role set status = '2' where role_id = '2017090814440000000006' and user_id in
(select user_id from um_user_info where user_name in ('胡大玮','严瑞洁'));

insert into um_user_role (user_id,role_id,status) values ('2017091110570000000011','2017090814440000000007','1');

insert into um_user_role (user_id,role_id,status) values ('2017091110570000000012','2017090814440000000007','1');