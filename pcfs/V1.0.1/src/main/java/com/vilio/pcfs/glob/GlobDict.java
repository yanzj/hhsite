package com.vilio.pcfs.glob;

public enum GlobDict {
	//是否首次登录
	first_login("1","首次登录"),
	un_first_login("0","非首次登录"),
	//是否锁定
	hash_lock("0","用户锁定"),

	//短信业务类型
	sms_login_type("00","登录短信验证码"),
	sms_borrow_type("01","借款短信验证码"),
	sms_login_pwd_type("02","忘记密码"),
	sms_trans_pwd_type("03","修改交易密码"),
	sms_authentication("04","身份验证"),

	//验证码是否有效
	verify_effective("01","有效"),
	verify_invalid("00","无效"),
	verify_succ("02","验证通过（比如忘记密码，先验证一次验证码才能跳转到设置密码页面，设置新密码需要第二次验证验证码是否已经为验证通过状态）"),

	//验证码是否有效
	baffle_switch_valid("1","有效"),
	baffle_switch_invalid("0","无效"),

	//登录类型
	login_type_name("0","用户名密码登录"),
	login_type_mobile("1","手机密码登录"),
	login_type_email("2","邮箱密码登陆"),


	pwd_update_trans("0","修改密码"),
	pwd_first_trans("1","首次登录修改密码"),

	//交易密码标识
	trans_pwd_exist("1","交易密码存在"),
	trans_pwd_unexist("0","交易密码不存在"),

	//登录状态
	login_status_effective("1","登录中"),
	login_status_invalid("0","登录已退出"),


	//待处理信息表状态
	wait_send_message_init("1","初始化"),
	wait_send_message_ing("2","处理中"),
	wait_send_message_finish("3","处理完成"),

	//待处理信息表发送方式
	send_method_delay("1","定时发送"),
	send_method_timely("0","立刻发送"),

	//推送信息表发送状态
	send_init("0","初始化成功"),
	send_succ("1","发送成功（请求成功）"),
	send_unknown("2","状态未知"),
	send_fail("3","发送失败"),
	send_ing("4","发送处理中"),

	//系统类型
	system_type_android("android","安卓"),
	system_type_ios("iOS","苹果"),

	//读取标识
	unread("0","未读"),
	read("1","已读"),


	;

	private String key;
	private String desc;

	GlobDict(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
