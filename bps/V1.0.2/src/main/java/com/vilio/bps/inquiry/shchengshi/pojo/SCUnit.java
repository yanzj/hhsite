package com.vilio.bps.inquiry.shchengshi.pojo;

/**
 * Created by dell on 2017/6/6/0006.
 */
public class SCUnit {

    private String id;

    private String unitNo;

    private String year;

    private String totalFloor;

    /** 朝向（东:1南:2西:3北:4）**/
    private String towards;
    /** 房屋类型(1:公寓/新工房 2:花园住宅 3:联列住宅 4:新式里弄 5:店铺 6:商场 7:办公房 8:旧里 9:其他) **/
    private String houseType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(String totalFloor) {
        this.totalFloor = totalFloor;
    }

    public String getTowards() {
        return towards;
    }

    public void setTowards(String towards) {
        this.towards = towards;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
}
