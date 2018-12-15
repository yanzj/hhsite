package com.vilio.um.pojo;

import java.util.List;

/**
 * 类名： Group<br>
 * 功能：组织机构对象<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class UmGroup {
    private Integer id;
    private String groupId;
    private String groupName;
    private String superId;
    private String status;
    private String groupDesc;
    private String groupType;
    private String groupCity;
    private String groupAbbr;
    private String englishAbbr;
    private String createTime;
    private String groupCityAbbrName;
    private String groupCityFullName;
    private String groupCityLevel;
    public List<UmGroup> childGroups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupCity() {
        return groupCity;
    }

    public void setGroupCity(String groupCity) {
        this.groupCity = groupCity;
    }

    public String getGroupAbbr() {
        return groupAbbr;
    }

    public void setGroupAbbr(String groupAbbr) {
        this.groupAbbr = groupAbbr;
    }

    public String getEnglishAbbr() {
        return englishAbbr;
    }

    public void setEnglishAbbr(String englishAbbr) {
        this.englishAbbr = englishAbbr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGroupCityAbbrName() {
        return groupCityAbbrName;
    }

    public void setGroupCityAbbrName(String groupCityAbbrName) {
        this.groupCityAbbrName = groupCityAbbrName;
    }

    public String getGroupCityFullName() {
        return groupCityFullName;
    }

    public void setGroupCityFullName(String groupCityFullName) {
        this.groupCityFullName = groupCityFullName;
    }

    public String getGroupCityLevel() {
        return groupCityLevel;
    }

    public void setGroupCityLevel(String groupCityLevel) {
        this.groupCityLevel = groupCityLevel;
    }

    public List<UmGroup> getChildGroups() {
        return childGroups;
    }

    public void setChildGroups(List<UmGroup> childGroups) {
        this.childGroups = childGroups;
    }

    public UmGroup() {
    }

    public UmGroup(Integer id, String groupId, String groupName, String superId, String status, String groupDesc, String groupType, String groupCity, String groupAbbr, String englishAbbr, String createTime, String groupCityAbbrName, String groupCityFullName, String groupCityLevel, List<UmGroup> childGroups) {
        this.id = id;
        this.groupId = groupId;
        this.groupName = groupName;
        this.superId = superId;
        this.status = status;
        this.groupDesc = groupDesc;
        this.groupType = groupType;
        this.groupCity = groupCity;
        this.groupAbbr = groupAbbr;
        this.englishAbbr = englishAbbr;
        this.createTime = createTime;
        this.groupCityAbbrName = groupCityAbbrName;
        this.groupCityFullName = groupCityFullName;
        this.groupCityLevel = groupCityLevel;
        this.childGroups = childGroups;
    }
}
