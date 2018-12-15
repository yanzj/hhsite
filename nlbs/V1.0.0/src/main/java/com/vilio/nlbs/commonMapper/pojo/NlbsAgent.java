package com.vilio.nlbs.commonMapper.pojo;

/**
 * Created by dell on 2017/5/24.
 */
public class NlbsAgent {
    private String id;
    private String agentId;
    private String name;
    private String beRecordClerk;

    public NlbsAgent() {
        super();
    }

    public String getId() {
        return id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeRecordClerk() {
        return beRecordClerk;
    }

    public void setBeRecordClerk(String beRecordClerk) {
        this.beRecordClerk = beRecordClerk;
    }
}
