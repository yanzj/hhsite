package com.vilio.wct.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

	@SuppressWarnings("finally")
	public static Properties getProperties(String file) {
		Properties p = null;
		try {
			InputStream in = ClassLoader.getSystemResourceAsStream(file);
			p = new Properties();
			p.load(in);
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			return p;
		}

	}
}
