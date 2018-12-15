package com.vilio.mps.push.android;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AndroidCustomizedcast extends AndroidNotification {
    public AndroidCustomizedcast() {
        try {
            this.setPredefinedKeyValue("type", "customizedcast");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.exit(1);
        }

    }

    public String uploadContents(String contents) throws Exception {
        if(this.rootJson.has("appkey") && this.rootJson.has("timestamp") && this.rootJson.has("validation_token")) {
            JSONObject uploadJson = new JSONObject();
            uploadJson.put("appkey", this.rootJson.getString("appkey"));
            uploadJson.put("timestamp", this.rootJson.getString("timestamp"));
            uploadJson.put("validation_token", this.rootJson.getString("validation_token"));
            uploadJson.put("content", contents);
            String url = "http://msg.umeng.com/upload";
            HttpPost post = new HttpPost(url);
            post.setHeader("User-Agent", "Mozilla/5.0");
            StringEntity se = new StringEntity(uploadJson.toString(), "UTF-8");
            post.setEntity(se);
            HttpResponse response = this.client.execute(post);
            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";

            while((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result.toString());
            JSONObject respJson = new JSONObject(result.toString());
            String ret = respJson.getString("ret");
            if(!ret.equals("SUCCESS")) {
                throw new Exception("Failed to upload file");
            } else {
                JSONObject data = respJson.getJSONObject("data");
                String fileId = data.getString("file_id");
                this.setPredefinedKeyValue("file_id", fileId);
                return fileId;
            }
        } else {
            throw new Exception("appkey, timestamp and validation_token needs to be set.");
        }
    }
}
