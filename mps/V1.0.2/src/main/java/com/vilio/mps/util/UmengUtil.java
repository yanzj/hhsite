package com.vilio.mps.util;

import com.vilio.mps.glob.ConfigInfo;
import com.vilio.mps.glob.GlobDict;
import com.vilio.mps.push.android.AndroidBroadcast;
import com.vilio.mps.push.android.AndroidUnicast;
import com.vilio.mps.push.ios.IOSBroadcast;
import com.vilio.mps.push.ios.IOSUnicast;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： SmsUtil<br>
 * 功能：发送短信工具类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月26日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class UmengUtil {

    protected static Logger logger = Logger.getLogger(UmengUtil.class);

    private static Integer badge = Integer.valueOf(0);
    private static String sound = "chime";
    private static String after_open = "go_app";
    private static String display_type = "notification";


    /**
     * IOS列播
     *
     * @throws Exception
     */
    public static Map<String, Object> sendIOSUnicast(String appMasterSecret, String appkey, String device_tokens,
                                                         String title, String subtitle, String body) throws Exception {
        IOSUnicast unicast = new IOSUnicast();
        unicast.setAppMasterSecret(appMasterSecret);
        unicast.setPredefinedKeyValue("appkey", appkey);
        unicast.setPredefinedKeyValue("timestamp", System.currentTimeMillis());
        unicast.setPredefinedKeyValue("device_tokens", device_tokens);
        Map alert = new HashMap();
        alert.put("title", title);
        alert.put("subtitle", subtitle);
        alert.put("body", body);
        unicast.setPredefinedKeyValue("alert", alert);
        unicast.setPredefinedKeyValue("badge", badge);
        unicast.setPredefinedKeyValue("sound", sound);
        unicast.setPredefinedKeyValue("production_mode", ConfigInfo.umengProductionMode);
        Map result = new HashMap();
        try {
            result = unicast.send();
            String ret = result.get("ret").toString();
            if ("SUCCESS".equals(ret)) {
                //发送成功
                result.put("sendStatus", GlobDict.send_succ.getKey());
            } else {
                //发送失败
                result.put("sendStatus", GlobDict.send_fail.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
            //发送超时
            result.put("sendStatus", GlobDict.send_unknown.getKey());
        }
        return result;
    }


    /**
     * IOS广播
     *
     * @throws Exception
     */
    public static Map<String, Object> sendIOSBroadcast(String appMasterSecret, String appkey, String title,
                                                       String subtitle, String body) throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast();
        broadcast.setAppMasterSecret(appMasterSecret);
        broadcast.setPredefinedKeyValue("appkey", appkey);
        broadcast.setPredefinedKeyValue("timestamp", System.currentTimeMillis());
        Map alert = new HashMap();
        alert.put("title", title);
        alert.put("subtitle", subtitle);
        alert.put("body", body);
        broadcast.setPredefinedKeyValue("alert", alert);
        broadcast.setPredefinedKeyValue("sound", sound);
        broadcast.setPredefinedKeyValue("production_mode", ConfigInfo.umengProductionMode);
        Map result = new HashMap();
        try {
            result = broadcast.send();
            String ret = result.get("ret").toString();
            if ("SUCCESS".equals(ret)) {
                //发送成功
                result.put("sendStatus", GlobDict.send_succ.getKey());
            } else {
                //发送失败
                result.put("sendStatus", GlobDict.send_fail.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
            //发送超时
            result.put("sendStatus", GlobDict.send_unknown.getKey());
        }
        return result;
    }


    /**
     * 安卓广播
     *
     * @throws Exception
     */
    public Map<String, Object> sendAndroidBroadcast(String appMasterSecret, String appkey, String ticker,
                                                    String title, String text) throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast();
        broadcast.setAppMasterSecret(appMasterSecret);
        broadcast.setPredefinedKeyValue("appkey", appkey);
        broadcast.setPredefinedKeyValue("timestamp", System.currentTimeMillis());
        broadcast.setPredefinedKeyValue("ticker", ticker);
        broadcast.setPredefinedKeyValue("title", title);
        broadcast.setPredefinedKeyValue("text", text);
        broadcast.setPredefinedKeyValue("after_open", after_open);
        broadcast.setPredefinedKeyValue("display_type", display_type);
        broadcast.setPredefinedKeyValue("production_mode", ConfigInfo.umengProductionMode);
        Map result = new HashMap();
        try {
            result = broadcast.send();
            String ret = result.get("ret").toString();
            if ("SUCCESS".equals(ret)) {
                //发送成功
                result.put("sendStatus", GlobDict.send_succ.getKey());
            } else {
                //发送失败
                result.put("sendStatus", GlobDict.send_fail.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
            //发送超时
            result.put("sendStatus", GlobDict.send_unknown.getKey());
        }
        return result;
    }


    /**
     * 安卓列播
     *
     * @throws Exception
     */
    public static Map<String, Object> sendAndroidUnicast(String appMasterSecret, String appkey, String device_tokens,
                                                         String ticker, String title, String text) throws Exception {
        AndroidUnicast unicast = new AndroidUnicast();
        unicast.setAppMasterSecret(appMasterSecret);
        unicast.setPredefinedKeyValue("appkey", appkey);
        unicast.setPredefinedKeyValue("timestamp", System.currentTimeMillis());
        unicast.setPredefinedKeyValue("device_tokens", device_tokens);
        unicast.setPredefinedKeyValue("ticker", ticker);
        unicast.setPredefinedKeyValue("title", title);
        unicast.setPredefinedKeyValue("text", text);
        unicast.setPredefinedKeyValue("after_open", after_open);
        unicast.setPredefinedKeyValue("display_type", display_type);
        unicast.setPredefinedKeyValue("production_mode", ConfigInfo.umengProductionMode);
        Map result = new HashMap();
        try {
            result = unicast.send();
            String ret = result.get("ret").toString();
            if ("SUCCESS".equals(ret)) {
                //发送成功
                result.put("sendStatus", GlobDict.send_succ.getKey());
            } else {
                //发送失败
                result.put("sendStatus", GlobDict.send_fail.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
            //发送超时
            result.put("sendStatus", GlobDict.send_unknown.getKey());
        }
        return result;
    }


    public static void main(String[] args) {
        try {
            //sendIOSUnicast();
            //sendIOSBroadcast();
            //sendAndroidUnicast();
            sendIOSUnicast("nv1tekso3tcnnecr13th8g8pvmlweucv", "59536b29677baa621100114e", "9e75da4732d5174054c781879073b242078ce73822753e9b19e4397409a0684d",
                    "title", "subtitle", "body");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

