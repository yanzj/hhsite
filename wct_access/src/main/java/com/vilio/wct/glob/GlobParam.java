package com.vilio.wct.glob;

import java.util.HashMap;
import java.util.Map;

import com.vilio.wct.pojo.AppInfo;
import com.vilio.wct.pojo.SqlConfig;

/**
 * 类名： GlobParam<br>
 * 功能：全局参数定义<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class GlobParam {
	// sql全局配置
	public static Map<String, SqlConfig> sqlConfig = new HashMap<String, SqlConfig>();
	// 错误码全局配置
	public static Map<String, String> ERROR_CODE = new HashMap<String, String>();
	// 应用基础设置
	public static Map<String, AppInfo> appInfoMap = new HashMap<String, AppInfo>();

}
