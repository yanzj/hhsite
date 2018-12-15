package com.vilio.mps.push.ios;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.vilio.mps.push.UmengNotification;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;

public abstract class IOSNotification extends UmengNotification {
    protected static final HashSet<String> APS_KEYS = new HashSet(Arrays.asList(new String[]{"alert", "badge", "sound", "content-available"}));

    public IOSNotification() {
    }

    public boolean setPredefinedKeyValue(String key, Object value) throws Exception {
        if(ROOT_KEYS.contains(key)) {
            this.rootJson.put(key, value);
        } else {
            JSONObject policyJson;
            if(APS_KEYS.contains(key)) {
                policyJson = null;
                JSONObject payloadJson = null;
                if(this.rootJson.has("payload")) {
                    payloadJson = this.rootJson.getJSONObject("payload");
                } else {
                    payloadJson = new JSONObject();
                    this.rootJson.put("payload", payloadJson);
                }

                if(payloadJson.has("aps")) {
                    policyJson = payloadJson.getJSONObject("aps");
                } else {
                    policyJson = new JSONObject();
                    payloadJson.put("aps", policyJson);
                }

                policyJson.put(key, value);
            } else {
                if(!POLICY_KEYS.contains(key)) {
                    if(key != "payload" && key != "aps" && key != "policy") {
                        throw new Exception("Unknownd key: " + key);
                    }

                    throw new Exception("You don\'t need to set value for " + key + " , just set values for the sub keys in it.");
                }

                policyJson = null;
                if(this.rootJson.has("policy")) {
                    policyJson = this.rootJson.getJSONObject("policy");
                } else {
                    policyJson = new JSONObject();
                    this.rootJson.put("policy", policyJson);
                }

                policyJson.put(key, value);
            }
        }

        return true;
    }

    public boolean setCustomizedField(String key, String value) throws Exception {
        JSONObject payloadJson = null;
        if(this.rootJson.has("payload")) {
            payloadJson = this.rootJson.getJSONObject("payload");
        } else {
            payloadJson = new JSONObject();
            this.rootJson.put("payload", payloadJson);
        }

        payloadJson.put(key, value);
        return true;
    }
}

