package com.vilio.mps.push;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.vilio.mps.util.JsonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public abstract class UmengNotification {
    protected static Logger logger = Logger.getLogger(UmengNotification.class);
    protected final JSONObject rootJson = new JSONObject();
    protected HttpClient client = new DefaultHttpClient();
    protected static final String host = "http://msg.umeng.com";
    protected static final String uploadPath = "/upload";
    protected static final String postPath = "/api/send";
    protected String appMasterSecret;
    protected final String USER_AGENT = "Mozilla/5.0";
    protected static final HashSet<String> ROOT_KEYS = new HashSet(Arrays.asList(new String[]{"appkey", "timestamp", "type", "device_tokens", "alias", "alias_type", "file_id", "filter", "production_mode", "feedback", "description", "thirdparty_id"}));
    protected static final HashSet<String> POLICY_KEYS = new HashSet(Arrays.asList(new String[]{"start_time", "expire_time", "max_send_num"}));

    public UmengNotification() {
    }

    public abstract boolean setPredefinedKeyValue(String var1, Object var2) throws Exception;

    public void setAppMasterSecret(String secret) {
        this.appMasterSecret = secret;
    }

    public Map<?, ?> send() throws IOException, ClientProtocolException {
        String url = "http://msg.umeng.com/api/send";
        String postBody = this.rootJson.toString();
        String sign = DigestUtils.md5Hex("POST" + url + postBody + this.appMasterSecret);
        url = url + "?sign=" + sign;
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", "Mozilla/5.0");
        StringEntity se = new StringEntity(postBody, "UTF-8");
        post.setEntity(se);
        HttpResponse response = this.client.execute(post);
        int status = response.getStatusLine().getStatusCode();
        logger.info("Response Code : " + status);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while((line = rd.readLine()) != null) {
            result.append(line);
        }
        logger.info(result.toString());
        if(status == 200) {
            logger.info("Notification sent successfully.");
        } else {
            logger.info("Failed to send the notification!");
        }
        return JsonUtil.jsonToMap(result.toString());
    }
}
