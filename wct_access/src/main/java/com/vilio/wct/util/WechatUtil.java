package com.vilio.wct.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vilio.wct.exception.ErrorException;

/**
 * 类名： WechatUtil<br>
 * 功能：微信公共方法<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月8日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：公共调用微信方法<br>
 */
public class WechatUtil {

	private static final Logger logger = Logger.getLogger(WechatUtil.class);

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 * @throws ErrorException
	 */

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getAccessToken(Map<String, Object> params) throws ErrorException {
		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${app_id}&secret=${app_secret}";
		accessTokenUrl = MatchUtil.matchValue(accessTokenUrl, params);
		HttpUtil httpUtil = new HttpUtil();
		String retJosn = httpUtil.sendHttps(accessTokenUrl, null, httpUtil.METHOD_GET);
		logger.info("获取access_token返回：" + retJosn);
		Map<String, Object> retParam = (Map<String, Object>) JsonUtil.jsonToMap(retJosn);
		if (retParam.containsKey("access_token") && retParam.containsKey("expires_in")) {
			// 如果有access_token参数，则获取成功。
			return retParam;
		} else {
			throw new ErrorException("SYS0003", "");
		}
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param params
	 * @return
	 * @throws ErrorException
	 */
	public static Map<String, Object> getUserInfo(Map<String, Object> params) throws ErrorException {
		String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=${access_token}&openid=${open_id}&lang=zh_CN";
		userInfoUrl = MatchUtil.matchValue(userInfoUrl, params);
		HttpUtil httpUtil = new HttpUtil();
		String retJosn = httpUtil.sendHttps(userInfoUrl, null, httpUtil.METHOD_GET);
		logger.info("获取用户信息返回：" + retJosn);
		Map<String, Object> retParam = (Map<String, Object>) JsonUtil.jsonToMap(retJosn);
		if (retParam.containsKey("errcode")) {
			// 如果出现errcode，则获取用户失败
			logger.error("获取用户信息失败");
			throw new ErrorException("SYS0004", "");
		}
		return retParam;
	}

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("app_id", "wx2e62173673166653");
//		params.put("app_secret", "9fd6992a37b0369d843f5b5c27275cf1");
//		
//		try {
//			System.out.println(getAccessToken(params));
//		} catch (ErrorException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		params.put("access_token", "ep6aq9YFEH0WxyuhMXHU5gfkn6dliR3aQzrhWgYnGxxVMQ6CpsYbCiGanyLUCcdKSmNGSsZ9S6h0Gl4b18suC-ZWvux0z0PRnO3mVy5frDoFDMeAIAXVE");
		params.put("openid", "opOyF1pZJHcs5HroTijr5DfDwFIQ");
		try {
			System.out.println(getUserInfo(params));
		} catch (ErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
