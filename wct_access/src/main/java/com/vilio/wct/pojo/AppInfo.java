package com.vilio.wct.pojo;

public class AppInfo {

	private String appId;
	private String appSecret;
	private String token;
	private String menuId;
	private String accessToken;
	private String expiresIn;
	private String appUser;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getAppUser() {
		return appUser;
	}

	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}

	public AppInfo() {
		super();
	}

	public AppInfo(String appId, String appSecret, String token, String menuId, String accessToken, String expiresIn,
			String appUser) {
		super();
		this.appId = appId;
		this.appSecret = appSecret;
		this.token = token;
		this.menuId = menuId;
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.appUser = appUser;
	}

}
