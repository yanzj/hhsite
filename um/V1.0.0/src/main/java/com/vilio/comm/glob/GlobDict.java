package com.vilio.comm.glob;

public enum GlobDict {
	//是否锁定
	hash_lock("0","用户锁定"),
	un_hash_lock("1","用户未锁定"),

	//登录类型
	login_type_name("0","用户名密码登录"),
	login_type_mobile("1","手机密码登录"),
	login_type_email("2","邮箱密码登陆"),

	//用户状态
	user_status_valid("1","有效"),
	user_status_disable("0","停用"),
	user_status_delete("2","已删除"),
	//机构状态
	group_status_valid("1","有效"),
	group_status_disable("0","停用"),
	group_status_delete("2","已删除"),

	//用户系统状态
	user_system_status_valid("1","有效"),
	user_system_status_disable("0","停用"),
	user_system_status_delete("2","已删除"),

	//系统状态
	system_status_valid("1","有效"),
	system_status_disable("0","停用"),
	system_status_delete("2","已删除"),

	//角色状态
	role_status_valid("1","有效"),
	role_status_disable("0","停用"),
	role_status_delete("2","已删除"),

	//用户角色状态
	user_role_status_valid("1","有效"),
	user_role_status_disable("0","停用"),
	user_role_status_delete("2","已删除"),

	//菜单状态
	menu_status_valid("1","有效"),
	menu_status_disable("0","停用"),
	menu_status_delete("2","已删除"),

	//角色菜单状态
	role_menu_status_valid("1","有效"),
	role_menu_status_disable("0","停用"),
	role_menu_status_delete("2","已删除"),

	//首次登陆
	first_login("1","首次登录"),
	un_first_login("0","非首次登录"),

	//性别数据字段
	sex_woman("0","女"),
	sex_man("1","男"),

	//用户查询子孙节点类型
	user_tree_super("0","查询用户上级信息（包括本身，子孙节点）"),
	user_tree_subordinate("1","查询用户下级信息（包括本身，子孙节点）"),
	user_tree_super_subordinate("2","查询包含当前用户的所有上下级节点"),
	user_tree_all("3","查询所有用户树形结构"),
	user_info("4","查询当前用户"),

	//机构查询子孙节点类型
	group_tree_super("0","查询机构上级信息（包括本身，子孙节点）"),
	group_tree_subordinate("1","查询机构下级信息（包括本身，子孙节点）"),
	group_tree_super_subordinate("2","查询包含当前机构的所有上下级节点"),
	group_tree_all("3","查询所有机构树形结构"),
	group_info("4","查询当前机构信息"),
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
