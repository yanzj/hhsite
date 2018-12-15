package com.vilio.nlbs.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by xiezhilei on 2017/1/13.
 */

@Component("configInfo")
public class ConfigInfo {

    @Value("${fmsUrl}")
    private String fmsUrl;

    @Value("${bpsUrl}")
    private String bpsUrl;

    @Value("${mpsUrl}")
    private String mpsUrl;

    @Value("${umUrl}")
    private String umUrl;

    public String getFmsUrl() {
        return fmsUrl;
    }

    public String getBpsUrl() {
        return bpsUrl;
    }

    public String getMpsUrl() {
        return mpsUrl;
    }

    public String getUmUrl() {
        return umUrl;
    }
}
