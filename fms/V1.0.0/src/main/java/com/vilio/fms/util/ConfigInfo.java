package com.vilio.fms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by xiezhilei on 2017/1/13.
 */

@Component("configInfo")
public class ConfigInfo {

    @Value("${bmsModelUrl}")
    private String bmsModelUrl;

    @Value("${bmsApplyUrl}")
    private String bmsApplyUrl;

    public String getBmsModelUrl() {
        return bmsModelUrl;
    }

    public void setBmsModelUrl(String bmsModelUrl) {
        this.bmsModelUrl = bmsModelUrl;
    }

    public String getBmsApplyUrl() {
        return bmsApplyUrl;
    }

    public void setBmsApplyUrl(String bmsApplyUrl) {
        this.bmsApplyUrl = bmsApplyUrl;
    }
}
