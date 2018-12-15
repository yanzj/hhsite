set @rownum=0;
update um_menu_info
SET order_num = (
select @rownum := @rownum +1 as nid)
WHERE system_id='nlbs';

set @rownum=0;
update um_menu_info
SET order_num = (
select @rownum := @rownum +1 as nid)
WHERE system_id='plms';


update um_menu_info SET order_num='30' WHERE menu_id='2017090814440000000001'



