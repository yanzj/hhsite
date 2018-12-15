package com.vilio.wct.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.vilio.wct.exception.ErrorException;
import com.vilio.wct.glob.GlobDict;
import com.vilio.wct.glob.GlobParam;
import com.vilio.wct.pojo.AppInfo;
import com.vilio.wct.util.WechatUtil;
import com.vilio.wct.util.XmlUtil;

/**
 * 类名： SubscribeService<br>
 * 功能：订阅事件推送业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月9日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Service("subscribe")
public class SubscribeService extends BaseService {

	private static final Logger logger = Logger.getLogger(SubscribeService.class);

	@Resource
	ComnService comnService;

	/**
	 * 业务主流程
	 * 
	 * @throws ErrorException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */

	@SuppressWarnings("unchecked")
	public String doWctService(Map<String, ?> reqParams) throws ErrorException, IOException {
		String respMessage = "";
		// 初始化参数变量
		Map<String, Object> params = (Map<String, Object>) reqParams;
		String toUserName = String.valueOf(params.get("ToUserName"));
		String fromUserName = String.valueOf(params.get("FromUserName"));
		String createTime = String.valueOf(params.get("CreateTime"));
		// 查询用户所属应用id
		AppInfo appInfo = GlobParam.appInfoMap.get(toUserName);
		params.put("app_id", appInfo.getAppId());
		params.put("access_token", appInfo.getAccessToken());
		params.put("open_id", fromUserName);
		// 查询数据库中是否存在当前订阅用户（重复订阅情况）
		List<Map<String, Object>> userList = comnService.commQuery("QUERY0003", params);
		if (userList == null || userList.size() == 0) {
			// 用户第一次订阅，直接入库
			params.put("subscribe", GlobDict.subscribe_suc.getKey());
			// 直接入库
			int ret = comnService.commUpdate("UPDATE0002", params);
			if (ret <= 0) {
				throw new ErrorException("SYS9997", "");
			}
			try {
				// 插入成功后获取用户基本信息更新到数据库
				Map<String, Object> userInfoWct = WechatUtil.getUserInfo(params);
				String subscribe = String.valueOf(userInfoWct.get("subscribe"));
				String sex = String.valueOf(userInfoWct.get("sex"));
				// 更新数据库
				params.putAll(userInfoWct);
				params.put("sex", sex == null || "".equals(sex) ? "" : sex.substring(0, sex.indexOf(".")));
				params.put("subscribe", subscribe.substring(0, subscribe.indexOf(".")));
				params.put("nick_name", userInfoWct.get("nickname"));
				params.put("head_img_url", userInfoWct.get("headimgurl"));
				params.put("group_id", userInfoWct.get("groupid"));
				// tagid_list是数组形式，把数据变成字符串
				ArrayList<?> tagid_list = (ArrayList<?>) userInfoWct.get("tagid_list");
				StringBuffer tagid_str = new StringBuffer("");
				for (Object tagid : tagid_list) {
					tagid_str.append(tagid);
					tagid_str.append(",");
				}
				params.put("tagid_list",
						"".equals(tagid_str.toString()) ? "" : tagid_str.substring(0, tagid_str.indexOf(",")));
				ret = comnService.commUpdate("UPDATE0003", params);
				if (ret <= 0) {
					logger.error("更新用户基本信息出错，继续处理");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("更新用户基本信息出错，继续处理");
			}
		} else {
			// 用户重复订阅
			Map<String, Object> userInfo = userList.get(0);
			params.putAll(userInfo);
			// 更新数据库订阅状态（先更新一下状态，再去查询用户信息刷新到表中，更新状态和更新用户信息的处理不相同）
			userInfo.put("subscribe", GlobDict.subscribe_suc.getKey());
			int ret = comnService.commUpdate("UPDATE0004", params);
			if (ret <= 0) {
				throw new ErrorException("SYS9997", "");
			}
			try {
				// 插入成功后获取用户基本信息更新到数据库
				Map<String, Object> userInfoWct = WechatUtil.getUserInfo(params);
				String subscribe = String.valueOf(userInfoWct.get("subscribe"));
				String sex = String.valueOf(userInfoWct.get("sex"));
				// 更新数据库
				params.putAll(userInfoWct);
				params.put("sex", sex == null || "".equals(sex) ? "" : sex.substring(0, sex.indexOf(".")));
				params.put("subscribe", subscribe.substring(0, subscribe.indexOf(".")));
				params.put("nick_name", userInfoWct.get("nickname"));
				params.put("head_img_url", userInfoWct.get("headimgurl"));
				params.put("group_id", userInfoWct.get("groupid"));
				// tagid_list是数组形式，把数据变成字符串
				ArrayList<?> tagid_list = (ArrayList<?>) userInfoWct.get("tagid_list");
				StringBuffer tagid_str = new StringBuffer("");
				for (Object tagid : tagid_list) {
					tagid_str.append(tagid);
					tagid_str.append(",");
				}
				params.put("tagid_list",
						"".equals(tagid_str.toString()) ? "" : tagid_str.substring(0, tagid_str.indexOf(",")));
				ret = comnService.commUpdate("UPDATE0003", params);
				if (ret <= 0) {
					logger.error("更新用户基本信息出错，继续处理");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("更新用户基本信息出错，继续处理");
			}
		}
		// 回复欢迎信息
		return replyWelcome(params);
	}

	/**
	 * 回复欢迎信息
	 * 
	 * @param params
	 * @return
	 * @throws ErrorException
	 * @throws IOException
	 */
	private String replyWelcome(Map<String, Object> params) throws ErrorException, IOException {
		String respMessage = "";
		// 查询这个应用下所有的欢迎信息（修改时间降序）
		params.put("wel_status", GlobDict.wel_status_succ.getKey());
		List<Map<String, Object>> welInfoList = comnService.commQuery("QUERY0004", params);
		// 查看最新一条的消息类型
		Map<String, Object> welInfo = welInfoList.get(0);
		if (GlobDict.msg_type_text.getKey().equals(welInfo.get("msg_type"))) {
			// 回复文本消息
			String content = String.valueOf(welInfo.get("content"));
			content = content == null ? "" : content;
			// 设置返回信息
			Map<String, Object> TextMsg = new HashMap<String, Object>();
			TextMsg.put("ToUserName", params.get("FromUserName"));
			TextMsg.put("FromUserName", params.get("ToUserName"));
			TextMsg.put("CreateTime", params.get("CreateTime"));
			TextMsg.put("MsgType", GlobDict.msg_type_text.getKey());
			 TextMsg.put("Content", content);
			respMessage = XmlUtil.mapToXML(TextMsg);
		} else {
			// 其他消息暂时不支持
		}
		return respMessage;
	}

}
