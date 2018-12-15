package com.vilio.wct.glob;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vilio.wct.pojo.SqlConfig;
import com.vilio.wct.util.PropertiesManager;

/**
 * 类名： InitClobDispatcher<br>
 * 功能：初始化全局变量<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class InitClobDispatcher extends HttpServlet {

	private static final long serialVersionUID = -5716647045215874374L;
	protected static Logger logger = Logger.getLogger(InitClobDispatcher.class);

	public final void init(ServletConfig config) throws ServletException {
		// 循环读取sql文件夹下的所有xml文件
		String basePach = InitClobDispatcher.class.getClassLoader().getResource("").getPath();
		load(basePach + File.separator + "conf" + File.separator + "sql");
		// 加载错误码信息
		loadErrorCode(basePach + File.separator + "conf" + File.separator + "error-code.properties");
	}

	/**
	 * 加载错误码信息到缓存
	 */
	private void loadErrorCode(String filePath) {
		logger.info("开始加载错误码");
		Properties propTmp = PropertiesManager.getProperties(filePath);
		Iterator it = propTmp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = String.valueOf(entry.getKey());
			String value = String.valueOf(entry.getValue());
			GlobParam.ERROR_CODE.put(key.toString(), value);
		}
		logger.info("加载错误码完成");
	}

	/**
	 * 加载当前文件夹下的所有xml结尾文件
	 * 
	 * @param file
	 */
	private void load(File file) {
		if (!file.isDirectory()) {
			if (file.getName().endsWith(".xml")) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;
				Document doc = null;
				try {
					builder = factory.newDocumentBuilder();
					doc = builder.parse(file);
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
					logger.error("加载配置文件失败：" + e.getMessage());
				} catch (SAXException e) {
					e.printStackTrace();
					logger.error("加载配置文件失败：" + e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("加载配置文件失败：" + e.getMessage());
				}
				NodeList nl = doc.getElementsByTagName("sqlinfo");
				for (int i = 0; i < nl.getLength(); i++) {
					String sqlid = doc.getElementsByTagName("sqlid").item(i).getFirstChild().getNodeValue();
					String sqlname = doc.getElementsByTagName("sqlname").item(i).getFirstChild().getNodeValue();
					String sql = doc.getElementsByTagName("sql").item(i).getFirstChild().getNodeValue();
					SqlConfig sc = new SqlConfig(sqlid, sqlname, sql);
					GlobParam.sqlConfig.put(sqlid, sc);
				}
				logger.info("加载配置文件：" + file.getName());
			}
		} else {
			File fa[] = file.listFiles();
			for (File file2 : fa) {
				load(file2);
			}
		}
	}

	/**
	 * 加载当前文件夹下的所有xml结尾文件
	 * 
	 * @param filePath
	 */
	private void load(String filePath) {
		File file = new File(filePath);
		load(file);
	}

}
