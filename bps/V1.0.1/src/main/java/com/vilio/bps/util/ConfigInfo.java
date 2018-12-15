package com.vilio.bps.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by xiezhilei on 2017/1/13.
 */

@Component("backendConfigInfo")
public class ConfigInfo {

    @Value("${WUUsername}")
    private String wUUsername;

    @Value("${WUPassword}")
    private String wUPassword;


    @Value("${nlbsUrl}")
    private String nlbsUrl;



    public String getwUUsername() {
        return wUUsername;
    }

    public String getwUPassword() {
        return wUPassword;
    }

    public String getNlbsUrl() {
        return nlbsUrl;
    }
}
