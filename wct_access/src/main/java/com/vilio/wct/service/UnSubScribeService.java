package com.vilio.wct.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.vilio.wct.exception.ErrorException;
import com.vilio.wct.glob.GlobDict;
import com.vilio.wct.glob.GlobParam;
import com.vilio.wct.pojo.AppInfo;

/**
 * 类名： UnSubScribeService<br>
 * 功能：取消订阅事件推送业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月9日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Service("unsubscribe")
public class UnSubScribeService extends BaseService {

	private static final Logger logger = Logger.getLogger(UnSubScribeService.class);

	@Resource
	ComnService comnService;

	@SuppressWarnings("unchecked")
	public String doWctService(Map<String, ?> reqParams) throws ErrorException {
		// 初始化参数变量
		Map<String, Object> params = (Map<String, Object>) reqParams;
		// 初始化参数变量
		String toUserName = String.valueOf(params.get("ToUserName"));
		String fromUserName = String.valueOf(params.get("FromUserName"));
		String createTime = String.valueOf(params.get("CreateTime"));

		// 查询用户所属应用id
		AppInfo appInfo = GlobParam.appInfoMap.get(toUserName);
		params.put("app_id", appInfo.getAppId());
		params.put("access_token", appInfo.getAccessToken());
		params.put("open_id", fromUserName);
		params.put("subscribe", GlobDict.subscribe_del.getKey());
		// 不管数据库状态是什么或者有没有这个用户，直接更改数据
		int ret = comnService.commUpdate("UPDATE0004", params);
		if (ret>0) {
			logger.info("更新数据库成功！");
		}else {
			logger.info("更新数据库失败！");
		}
		return "";
	}

}
