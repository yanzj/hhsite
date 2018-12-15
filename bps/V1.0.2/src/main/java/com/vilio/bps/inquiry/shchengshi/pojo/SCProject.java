package com.vilio.bps.inquiry.shchengshi.pojo;

/**
 * Created by dell on 2017/6/6/0006.
 */
public class SCProject {

    private String id;

    private String projectName;

    private String address;

    private String year;

    /** 小区类型(1:公寓，2：别墅，3：混合，4，新里洋房，6：农民自建别墅小区，7：工房小区) **/
    private String projectType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
