package com.vilio.wct.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vilio.wct.exception.ErrorException;
import com.vilio.wct.glob.GlobParam;

/**
 * 类名： BaseService<br>
 * 功能：所有service的父类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class BaseService {

	private static final Logger logger = Logger.getLogger(BaseService.class);

	/**
	 * 处理微信公众平台过来的信息
	 * 
	 * @see 返回微信和返回内部系统格式不同，单独定义入口函数。
	 * @param params
	 * @return
	 */
	public String doWctMain(Map<String, ?> params) {
		String respMessage = null;
		try {
			respMessage = doWctService(params);
		} catch (ErrorException e) {
			e.printStackTrace();
			logger.error("错误码" + e.getErroCode() == null || "".equals(e.getErroCode()) ? "SYS9999" : e.getErroCode());
			logger.error("错误信息" + e.getMessage() == null || "".equals(e.getMessage())
					? GlobParam.ERROR_CODE.get("SYS9999") : e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("错误码" + "SYS9999");
			logger.error("错误信息" + e.getMessage() == null || "".equals(e.getMessage())
					? GlobParam.ERROR_CODE.get("SYS9999") : e.getMessage());
		}
		return respMessage;
	}

	/**
	 * 处理内部系统过来的信息
	 * 
	 * @see 返回微信和返回内部系统格式不同，单独定义入口函数
	 * @param params
	 * @return
	 */
	public Map<String, Object> doMain(Map<String, Object> params) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		try {
			respMap = doService(params);
		} catch (ErrorException e) {
			e.printStackTrace();
			respMap.put("returnCode",
					e.getErroCode() == null || "".equals(e.getErroCode()) ? "SYS9999" : e.getErroCode());
			respMap.put("returnMessage", e.getMessage() == null || "".equals(e.getMessage())
					? GlobParam.ERROR_CODE.get("SYS9999") : e.getMessage());
			logger.error("错误码" + respMap.get("returnCode"));
			logger.error("错误信息" + respMap.get("returnMessage"));
		} catch (Exception e) {
			e.printStackTrace();
			respMap.put("returnCode", "SYS9999");
			respMap.put("returnMessage", "业务流程报错：" + e.getMessage());
			logger.error("错误码" + respMap.get("returnCode"));
			logger.error("错误信息" + respMap.get("returnMessage"));
		}
		return respMap;
	}

	/**
	 * 子类重新方法
	 * 
	 * @param params
	 * @return
	 * @throws ErrorException
	 */
	public String doWctService(Map<String, ?> params) throws ErrorException, Exception {
		return "";
	}

	/**
	 * 子类重新方法
	 * 
	 * @param params
	 * @return
	 * @throws ErrorException
	 */
	public Map<String, Object> doService(Map<String, Object> params) throws ErrorException, Exception {
		return new HashMap<String, Object>();
	}
}
