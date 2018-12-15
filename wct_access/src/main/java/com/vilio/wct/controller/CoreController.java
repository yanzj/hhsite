package com.vilio.wct.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vilio.wct.glob.GlobDict;
import com.vilio.wct.service.BaseService;
import com.vilio.wct.util.JsonUtil;
import com.vilio.wct.util.SpringContextUtil;
import com.vilio.wct.util.XmlUtil;

/**
 * 类名： CoreController<br>
 * 功能：微信核心Controller类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Controller
public class CoreController {
	private static final Logger logger = Logger.getLogger(CoreController.class);

	/**
	 * 接受微信请求服务并处理返回
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 */

	@RequestMapping("/wechatCore.htm")
	public void wechatCore(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String method = request.getMethod();
		logger.info("请求类型：" + method);
		PrintWriter pw = null;
		BufferedReader reader = null;
		try {
			request.setCharacterEncoding("UTF-8");  
			response.setCharacterEncoding("UTF-8");
			// 定义返回参数
			String respMessage = null;
			if (RequestMethod.GET.toString().equals(method)) {
				Map<String, String[]> params = request.getParameterMap();
				logger.info("接受到的参数：" + JsonUtil.objectToJson(params));
				// 如果是get请求，证明是微信过来验证地址的请求
				// 调用微信接入处理方法
				BaseService bService = (BaseService) SpringContextUtil.getBean("accessService");
				respMessage = bService.doWctMain(params);
			} else {
				reader = request.getReader();
				StringBuffer buffer = new StringBuffer();
				String str;
				while ((str = reader.readLine()) != null) {
					buffer.append(str);
				}
				// 解析xml
				Map<String, Object> params = XmlUtil.XmlToMap(buffer.toString());
				logger.info("接受到的参数：" + JsonUtil.objectToJson(params));
				// 接受微信的消息，对不同的信息类型分别调用不同的流程
				String msgType = String.valueOf(params.get("MsgType"));
				// 微信的不同事件类型，不同事件类型要用不同的参数判断流程
				if (GlobDict.event.getKey().equals(msgType)) {
					// 事件推送，判断事件类型
					String event = String.valueOf(params.get("Event"));
					if (GlobDict.event_subscribe.getKey().equals(event)) {
						// 订阅事件推送
						BaseService bService = (BaseService) SpringContextUtil.getBean("subscribe");
						respMessage = bService.doWctMain(params);
					}else if (GlobDict.event_unsubscribe.getKey().equals(event)) {
						// 取消订阅事件推送
						BaseService bService = (BaseService) SpringContextUtil.getBean("unsubscribe");
						respMessage = bService.doWctMain(params);
					} else {
						// 暂不支持此类事件推送
						logger.info("暂不支持此类事件的推送：" + event);
						respMessage = "";
					}

				}

				// try {
				// BaseService bService = (BaseService)
				// SpringContextUtil.getBean(msgType.toUpperCase());
				// respMessage = bService.doWctMain(params);
				// } catch (NoSuchBeanDefinitionException e) {
				// // 没有实例化bean，暂时不支持当前类型
				// logger.error("没有实例化bean，暂时不支持当前消息类型:" + msgType);
				// respMessage = "";
				// }
			}
			logger.info(respMessage);
			if (respMessage != null) {
				pw = response.getWriter();
				pw.print(respMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("微信核心接口返回出错：" + e.getMessage());
		} catch (JDOMException e) {
			e.printStackTrace();
			logger.error("微信核心接口解析xml出错：" + e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}

		}

	}

}
