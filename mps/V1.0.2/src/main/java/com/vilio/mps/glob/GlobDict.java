package com.vilio.mps.glob;

public enum GlobDict {
	//发送状态
	send_init("0","初始化成功"),
	send_succ("1","发送成功（请求成功）"),
	send_unknown("2","状态未知"),
	send_fail("3","发送失败"),

	//签名状态
	sign_status_valid("1","有效"),
	sign_status_disable("0","停用"),
	sign_status_delete("2","已删除"),

	//模板状态
	template_status_valid("1","有效"),
	template_status_disable("0","停用"),
	template_status_delete("2","已删除"),

	//模板状态
	app_status_valid("1","有效"),
	app_status_disable("0","停用"),
	app_status_delete("2","已删除"),

	//是否为及时消息
	timely_news("1","及时消息"),
	un_timely_news("0","非即时消息"),

	//系统类型
	system_type_android("android","安卓"),
	system_type_ios("iOS","苹果"),


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
