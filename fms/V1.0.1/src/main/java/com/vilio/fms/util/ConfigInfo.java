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

    @Value("${mpsUrl}")
    private String mpsUrl;

    @Value("${fmsOuterNetUrl}")
    private String fmsOuterNetUrl;

    @Value("${riskManagentName}")
    private String riskManagentName;

    @Value("${riskManagentEmailUserName}")
    private String riskManagentEmailUserName;

    @Value("${riskManagentEmailPassword}")
    private String riskManagentEmailPassword;

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

    public String getMpsUrl() {
        return mpsUrl;
    }

    public void setMpsUrl(String mpsUrl) {
        this.mpsUrl = mpsUrl;
    }

    public String getFmsOuterNetUrl() {
        return fmsOuterNetUrl;
    }

    public void setFmsOuterNetUrl(String fmsOuterNetUrl) {
        this.fmsOuterNetUrl = fmsOuterNetUrl;
    }

    public String getRiskManagentName() {
        return riskManagentName;
    }

    public void setRiskManagentName(String riskManagentName) {
        this.riskManagentName = riskManagentName;
    }

    public String getRiskManagentEmailUserName() {
        return riskManagentEmailUserName;
    }

    public void setRiskManagentEmailUserName(String riskManagentEmailUserName) {
        this.riskManagentEmailUserName = riskManagentEmailUserName;
    }

    public String getRiskManagentEmailPassword() {
        return riskManagentEmailPassword;
    }

    public void setRiskManagentEmailPassword(String riskManagentEmailPassword) {
        this.riskManagentEmailPassword = riskManagentEmailPassword;
    }
}
