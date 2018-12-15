package com.vilio.wct.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vilio.wct.exception.ErrorException;
import com.vilio.wct.glob.GlobDict;
import com.vilio.wct.glob.GlobParam;
import com.vilio.wct.pojo.AppInfo;
import com.vilio.wct.service.ComnService;
import com.vilio.wct.util.WechatUtil;

/**
 * 类名： TokenTast<br>
 * 功能：定时刷新access_token<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月8日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：定时启动获取接口调用凭证，两个小时过期，暂时定义一个半小时刷新一次<br>
 */
public class TokenTast {

	private static final Logger logger = Logger.getLogger(TokenTast.class);

	/**
	 * 入口函数
	 * 
	 * @throws ErrorException
	 */
	public void execute() {
		logger.info("定时任务开始，获取access_token");
		// 查询出所有的应用信息，加载到缓存里
		try {
			ComnService comnService = new ComnService();
			Map<String, Object> sqlParam = new HashMap<String, Object>();
			sqlParam.put("app_status", GlobDict.app_status_suc.getKey());
			List<Map<String, Object>> appBaseList = comnService.commQuery("QUERY0002", sqlParam);
			for (Map<String, Object> appInfo : appBaseList) {
				String accessToken = "";
				String expiresIn = "";
				// 获取access_token
				try {
					Map<String, Object> accessMap = WechatUtil.getAccessToken(appInfo);
					accessToken = String.valueOf(accessMap.get("access_token"));
					expiresIn = String.valueOf(accessMap.get("expires_in"));
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(appInfo.get("app_id") + "-" + appInfo.get("app_secret") + "获取access_token出错："
							+ e.getMessage());
				}
				AppInfo appInfoPojo = new AppInfo(String.valueOf(appInfo.get("app_id")),
						String.valueOf(appInfo.get("app_secret")), String.valueOf(appInfo.get("token")),
						String.valueOf(appInfo.get("menu_id")), accessToken, expiresIn,
						String.valueOf(appInfo.get("app_user")));
				GlobParam.appInfoMap.put(String.valueOf(appInfo.get("app_user")), appInfoPojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("定时任务报错");
		}
		logger.info("定时任务结束");
	}
	
}
