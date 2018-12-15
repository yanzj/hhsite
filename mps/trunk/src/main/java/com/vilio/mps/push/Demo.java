package com.vilio.mps.push;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.vilio.mps.push.android.*;
import com.vilio.mps.push.ios.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class Demo {
    private String appkey = null;
    private String appMasterSecret = null;
    private String timestamp = null;

    public Demo(String key, String secret) {
        try {
            this.appkey = key;
            this.appMasterSecret = secret;
            this.timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000L));
        } catch (Exception var4) {
            var4.printStackTrace();
            System.exit(1);
        }

    }

    public void sendAndroidBroadcast() throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast();
        broadcast.setAppMasterSecret(this.appMasterSecret);
        broadcast.setPredefinedKeyValue("appkey", this.appkey);
        broadcast.setPredefinedKeyValue("timestamp", this.timestamp);
        broadcast.setPredefinedKeyValue("ticker", "Android broadcast ticker");
        broadcast.setPredefinedKeyValue("title", "中文的title");
        broadcast.setPredefinedKeyValue("text", "Android broadcast text");
        broadcast.setPredefinedKeyValue("after_open", "go_app");
        broadcast.setPredefinedKeyValue("display_type", "notification");
        broadcast.setPredefinedKeyValue("production_mode", "true");
        broadcast.setExtraField("test", "helloworld");
        broadcast.send();
    }

    public void sendAndroidUnicast() throws Exception {
        AndroidUnicast unicast = new AndroidUnicast();
        unicast.setAppMasterSecret(this.appMasterSecret);
        unicast.setPredefinedKeyValue("appkey", this.appkey);
        unicast.setPredefinedKeyValue("timestamp", this.timestamp);
        unicast.setPredefinedKeyValue("device_tokens", "xxxx");
        unicast.setPredefinedKeyValue("ticker", "Android unicast ticker");
        unicast.setPredefinedKeyValue("title", "中文的title");
        unicast.setPredefinedKeyValue("text", "Android unicast text");
        unicast.setPredefinedKeyValue("after_open", "go_app");
        unicast.setPredefinedKeyValue("display_type", "notification");
        unicast.setPredefinedKeyValue("production_mode", "true");
        unicast.setExtraField("test", "helloworld");
        unicast.send();
    }

    public void sendAndroidGroupcast() throws Exception {
        AndroidGroupcast groupcast = new AndroidGroupcast();
        groupcast.setAppMasterSecret(this.appMasterSecret);
        groupcast.setPredefinedKeyValue("appkey", this.appkey);
        groupcast.setPredefinedKeyValue("timestamp", this.timestamp);
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        JSONObject testTag = new JSONObject();
        JSONObject TestTag = new JSONObject();
        testTag.put("tag", "test");
        TestTag.put("tag", "Test");
        tagArray.put(testTag);
        tagArray.put(TestTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());
        groupcast.setPredefinedKeyValue("filter", filterJson);
        groupcast.setPredefinedKeyValue("ticker", "Android groupcast ticker");
        groupcast.setPredefinedKeyValue("title", "中文的title");
        groupcast.setPredefinedKeyValue("text", "Android groupcast text");
        groupcast.setPredefinedKeyValue("after_open", "go_app");
        groupcast.setPredefinedKeyValue("display_type", "notification");
        groupcast.setPredefinedKeyValue("production_mode", "true");
        groupcast.send();
    }

    public void sendAndroidCustomizedcast() throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
        customizedcast.setAppMasterSecret(this.appMasterSecret);
        customizedcast.setPredefinedKeyValue("appkey", this.appkey);
        customizedcast.setPredefinedKeyValue("timestamp", this.timestamp);
        customizedcast.setPredefinedKeyValue("alias", "xx");
        customizedcast.setPredefinedKeyValue("alias_type", "xx");
        customizedcast.setPredefinedKeyValue("ticker", "Android customizedcast ticker");
        customizedcast.setPredefinedKeyValue("title", "中文的title");
        customizedcast.setPredefinedKeyValue("text", "Android customizedcast text");
        customizedcast.setPredefinedKeyValue("after_open", "go_app");
        customizedcast.setPredefinedKeyValue("display_type", "notification");
        customizedcast.setPredefinedKeyValue("production_mode", "true");
        customizedcast.send();
    }

    public void sendAndroidFilecast() throws Exception {
        AndroidFilecast filecast = new AndroidFilecast();
        filecast.setAppMasterSecret(this.appMasterSecret);
        filecast.setPredefinedKeyValue("appkey", this.appkey);
        filecast.setPredefinedKeyValue("timestamp", this.timestamp);
        filecast.uploadContents("aa\nbb");
        filecast.setPredefinedKeyValue("ticker", "Android filecast ticker");
        filecast.setPredefinedKeyValue("title", "中文的title");
        filecast.setPredefinedKeyValue("text", "Android filecast text");
        filecast.setPredefinedKeyValue("after_open", "go_app");
        filecast.setPredefinedKeyValue("display_type", "notification");
        filecast.send();
    }

    public void sendIOSBroadcast() throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast();
        broadcast.setAppMasterSecret(this.appMasterSecret);
        broadcast.setPredefinedKeyValue("appkey", this.appkey);
        broadcast.setPredefinedKeyValue("timestamp", this.timestamp);
        broadcast.setPredefinedKeyValue("alert", "IOS 广播测试");
        broadcast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        broadcast.setPredefinedKeyValue("sound", "chime");
        broadcast.setPredefinedKeyValue("production_mode", "false");
        broadcast.setCustomizedField("test", "helloworld");
        broadcast.send();
    }

    public void sendIOSUnicast() throws Exception {
        IOSUnicast unicast = new IOSUnicast();
        unicast.setAppMasterSecret(this.appMasterSecret);
        unicast.setPredefinedKeyValue("appkey", this.appkey);
        unicast.setPredefinedKeyValue("timestamp", this.timestamp);
        unicast.setPredefinedKeyValue("device_tokens", "xx");
        unicast.setPredefinedKeyValue("alert", "IOS 单播测试");
        unicast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        unicast.setPredefinedKeyValue("sound", "chime");
        unicast.setPredefinedKeyValue("production_mode", "false");
        unicast.setCustomizedField("test", "helloworld");
        unicast.send();
    }

    public void sendIOSGroupcast() throws Exception {
        IOSGroupcast groupcast = new IOSGroupcast();
        groupcast.setAppMasterSecret(this.appMasterSecret);
        groupcast.setPredefinedKeyValue("appkey", this.appkey);
        groupcast.setPredefinedKeyValue("timestamp", this.timestamp);
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        JSONObject testTag = new JSONObject();
        testTag.put("tag", "iostest");
        tagArray.put(testTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());
        groupcast.setPredefinedKeyValue("filter", filterJson);
        groupcast.setPredefinedKeyValue("alert", "IOS 组播测试");
        groupcast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        groupcast.setPredefinedKeyValue("sound", "chime");
        groupcast.setPredefinedKeyValue("production_mode", "false");
        groupcast.send();
    }

    public void sendIOSCustomizedcast() throws Exception {
        IOSCustomizedcast customizedcast = new IOSCustomizedcast();
        customizedcast.setAppMasterSecret(this.appMasterSecret);
        customizedcast.setPredefinedKeyValue("appkey", this.appkey);
        customizedcast.setPredefinedKeyValue("timestamp", this.timestamp);
        customizedcast.setPredefinedKeyValue("alias", "xx");
        customizedcast.setPredefinedKeyValue("alias_type", "xx");
        customizedcast.setPredefinedKeyValue("alert", "IOS 个性化测试");
        customizedcast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        customizedcast.setPredefinedKeyValue("sound", "chime");
        customizedcast.setPredefinedKeyValue("production_mode", "false");
        customizedcast.send();
    }

    public void sendIOSFilecast() throws Exception {
        IOSFilecast filecast = new IOSFilecast();
        filecast.setAppMasterSecret(this.appMasterSecret);
        filecast.setPredefinedKeyValue("appkey", this.appkey);
        filecast.setPredefinedKeyValue("timestamp", this.timestamp);
        filecast.uploadContents("aa\nbb");
        filecast.setPredefinedKeyValue("alert", "IOS 文件播测试");
        filecast.setPredefinedKeyValue("badge", Integer.valueOf(0));
        filecast.setPredefinedKeyValue("sound", "chime");
        filecast.setPredefinedKeyValue("production_mode", "false");
        filecast.send();
    }

    public static void main(String[] args) {
//        push.Demo demo = new push.Demo("your appkey", "the app master secret");
//
//        try {
//            demo.sendAndroidUnicast();
//        } catch (Exception var3) {
//            var3.printStackTrace();
//        }

    }
}

