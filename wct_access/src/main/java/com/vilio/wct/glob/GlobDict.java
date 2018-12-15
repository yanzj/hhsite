package com.vilio.wct.glob;

public enum GlobDict {

	// 应用状态
	app_status_del("2", "已删除"), app_status_suc("1", "已接入"),

	// 消息类型
	event("event", "事件推送"),

	// 事件类型
	event_subscribe("subscribe", "订阅事件推送"), event_unsubscribe("unsubscribe", "取消订阅事件推送"),

	// 关注状态
	subscribe_suc("1", "已关注"), subscribe_del("2", "取消关注"),

	// 欢迎信息状态
	wel_status_succ("1", "有效"),

	// 信息类型
	msg_type_text("text", "文本信息")

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
