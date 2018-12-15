package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/5/24.
 */
public class NlbsAgentDistributor {
    private String id;
    private String agentId;
    private String distributorCode;

    public NlbsAgentDistributor() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }
}
