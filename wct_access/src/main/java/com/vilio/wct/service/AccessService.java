package com.vilio.wct.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.vilio.wct.exception.ErrorException;
import com.vilio.wct.glob.GlobDict;
import com.vilio.wct.glob.GlobParam;
import com.vilio.wct.pojo.AppInfo;
import com.vilio.wct.util.SignUtil;
import com.vilio.wct.util.WechatUtil;

/**
 * 类名： AccessService<br>
 * 功能：微信接入业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Service
public class AccessService extends BaseService {
	private static final Logger logger = Logger.getLogger(AccessService.class);

	@Resource
	ComnService comnService;

	/**
	 * 业务主流程
	 * 
	 * @throws ErrorException
	 * @throws NoSuchAlgorithmException
	 */
	@SuppressWarnings("unchecked")
	public String doWctService(Map<String, ?> reqParams) throws ErrorException, NoSuchAlgorithmException {
		Map<String, String[]> params = (Map<String, String[]>) reqParams;
		// 校验参数
		if (params.get("signature") == null || params.get("signature").length == 0) {
			logger.info("参数校验为空：signature");
			return "";
		} else if (params.get("timestamp") == null || params.get("timestamp").length == 0) {
			logger.info("参数校验为空：timestamp");
			return "";
		} else if (params.get("nonce") == null || params.get("nonce").length == 0) {
			logger.info("参数校验为空：nonce");
			return "";
		} else if (params.get("echostr") == null || params.get("echostr").length == 0) {
			logger.info("参数校验为空：echostr");
			return "";
		}
		// 初始化参数变量
		String signature = params.get("signature")[0];
		String timestamp = params.get("timestamp")[0];
		String nonce = params.get("nonce")[0];
		String echostr = params.get("echostr")[0];
		// 查询出所有的appid基础配置
		List<Map<String, Object>> appBaseList = comnService.commQuery("QUERY0001");
		// 循环所有的应用，开始校验，找到所匹配的应用
		boolean flag = false;
		for (Map<String, Object> appBaseInfo : appBaseList) {
			String token = String.valueOf(appBaseInfo.get("token"));
			if (SignUtil.checkSignature(token, signature, timestamp, nonce)) {
				// 校验通过
				// 判断当前接入状态是否为已删除
				if (GlobDict.app_status_del.getKey().equals(appBaseInfo.get("app_status"))) {
					throw new ErrorException("SYS0002", "");
				} else if (GlobDict.app_status_suc.getKey().equals(appBaseInfo.get("app_status"))) {
					// 数据库状态为已接入成功，不用更改数据库，返回接入成功
					flag = true;
					logger.info("接入成功：" + appBaseInfo.get("app_id") + "-" + appBaseInfo.get("app_secret"));
				} else {
					// 其他状态，更新数据库为已接入，返回接入成功
					appBaseInfo.put("app_status", GlobDict.app_status_suc.getKey());
					int ret = comnService.commUpdate("UPDATE0001", appBaseInfo);
					if (ret > 0) {
						logger.info(
								"验证成功，开始获取接口调用凭证缓存：" + appBaseInfo.get("app_id") + "-" + appBaseInfo.get("app_secret"));
						// 获取access_token
						String accessToken = "";
						String expiresIn = "";
						try {
							Map<String, Object> accessMap = WechatUtil.getAccessToken(appBaseInfo);
							accessToken = String.valueOf(accessMap.get("access_token"));
							expiresIn = String.valueOf(accessMap.get("expires_in"));
						} catch (ErrorException e) {
							e.printStackTrace();
							logger.error(appBaseInfo.get("app_id") + "-" + appBaseInfo.get("app_secret")
									+ "获取access_token出错，抛出异常，接入失败：" + e.getMessage());
							throw e;
						}
						AppInfo appInfoPojo = new AppInfo(String.valueOf(appBaseInfo.get("app_id")),
								String.valueOf(appBaseInfo.get("app_secret")), String.valueOf(appBaseInfo.get("token")),
								String.valueOf(appBaseInfo.get("menu_id")), accessToken, expiresIn,
								String.valueOf(appBaseInfo.get("app_user")));
						GlobParam.appInfoMap.put(String.valueOf(appBaseInfo.get("app_user")), appInfoPojo);
						flag = true;
					}
				}
			}
		}
		if (flag) {
			// 接入成功
			return echostr;
		} else {
			logger.info("接入失败！");
			return "";
		}
	}

}
