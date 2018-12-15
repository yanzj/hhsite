package com.vilio.wct.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.vilio.wct.exception.ErrorException;
import com.vilio.wct.glob.GlobParam;
import com.vilio.wct.pojo.SqlConfig;
import com.vilio.wct.util.MatchUtil;
import com.vilio.wct.util.SpringContextUtil;

/**
 * 类名： CommService<br>
 * 功能：公共执行sql处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：可根据不同的业务场景扩展此类<br>
 */

@Service
public class ComnService {

	private static final Logger logger = Logger.getLogger(ComnService.class);

	public List<Map<String, Object>> commQuery(String sqlId) throws ErrorException {
		return commQuery(sqlId, null);
	}

	/**
	 * 公共执行查询sql.
	 * 
	 * @param sqlId
	 *            sql标识.
	 * @param param
	 *            sql中的参数.
	 * @return
	 * @throws ErrorException
	 */
	public List<Map<String, Object>> commQuery(String sqlId, Map<String, Object> params) throws ErrorException {
		List<Map<String, Object>> reslut = new ArrayList<Map<String, Object>>();
		try {
			if (sqlId == null || "".equals(sqlId)) {
				throw new ErrorException("SYS0001", "");
			}
			SqlConfig sqlConfig = GlobParam.sqlConfig.get(sqlId);
			logger.info("参数：" + params);
			logger.info("说明：" + sqlConfig.getSqlname());
			logger.info("执行的sql：" + sqlConfig.getSql());
			if (sqlConfig.getSql() == null || "".equals(sqlConfig.getSql())) {
				throw new ErrorException("SYS0001", "");
			}
			String sql = sqlConfig.getSql();
			sql = MatchUtil.matchValue(sql, params);
			JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextUtil.getBean("jdbcTemplate");
			reslut = jdbcTemplate.queryForList(sql);
		} catch (ErrorException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ErrorException(e.getErroCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ErrorException("SYS9999", "sql执行出错：" + e.getMessage());
		}
		return reslut;
	}

	
	/**
	 * 公共执行更新sql.
	 * 
	 * @param sqlId
	 *            sql标识.
	 * @param param
	 *            sql中的参数.
	 * @return
	 * @throws ErrorException
	 */
	public int commUpdate(String sqlId, Map<String, Object> params) throws ErrorException {
		int ret = 0;
		try {
			if (sqlId == null || "".equals(sqlId)) {
				throw new ErrorException("SYS0001", "");
			}
			SqlConfig sqlConfig = GlobParam.sqlConfig.get(sqlId);
			logger.info("参数：" + params);
			logger.info("说明：" + sqlConfig.getSqlname());
			logger.info("执行的sql：" + sqlConfig.getSql());
			if (sqlConfig.getSql() == null || "".equals(sqlConfig.getSql())) {
				throw new ErrorException("SYS0001", "");
			}
			String sql = sqlConfig.getSql();
			sql = MatchUtil.matchValue(sql, params);
			JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextUtil.getBean("jdbcTemplate");
			ret = jdbcTemplate.update(sql);
		} catch (ErrorException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ErrorException(e.getErroCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ErrorException("SYS9999", "sql执行出错：" + e.getMessage());
		}
		return ret;
	}

}
