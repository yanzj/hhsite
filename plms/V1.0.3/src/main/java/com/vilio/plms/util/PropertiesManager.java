package com.vilio.plms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesManager {

    @SuppressWarnings("finally")
    public static Properties getProperties(String file) {
        Properties p = null;
        try {
            InputStreamReader in =new InputStreamReader(new FileInputStream(new File(file)),"UTF-8");
            p = new Properties();
            p.load(in);
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            return p;
        }

    }

}