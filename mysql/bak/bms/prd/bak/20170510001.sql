#给陆志斌重置密码
update bms_user set password = md5(123456) where user_no = '18516183181';