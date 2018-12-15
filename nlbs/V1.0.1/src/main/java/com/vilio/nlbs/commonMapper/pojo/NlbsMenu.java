package com.vilio.nlbs.commonMapper.pojo;

public class NlbsMenu {
    private Integer id;

    private String code;

    private String menuName;

    private String menuUrl;

    private String menuOrderNo;

    private Integer menuLevel;

    private String fatherMenuCode;

    private Boolean emptyType;

    public NlbsMenu(Integer id, String code, String menuName, String menuUrl, String menuOrderNo, Integer menuLevel, String fatherMenuCode, Boolean emptyType) {
        this.id = id;
        this.code = code;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuOrderNo = menuOrderNo;
        this.menuLevel = menuLevel;
        this.fatherMenuCode = fatherMenuCode;
        this.emptyType = emptyType;
    }

    public NlbsMenu() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuOrderNo() {
        return menuOrderNo;
    }

    public void setMenuOrderNo(String menuOrderNo) {
        this.menuOrderNo = menuOrderNo == null ? null : menuOrderNo.trim();
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getFatherMenuCode() {
        return fatherMenuCode;
    }

    public void setFatherMenuCode(String fatherMenuCode) {
        this.fatherMenuCode = fatherMenuCode == null ? null : fatherMenuCode.trim();
    }

    public Boolean getEmptyType() {
        return emptyType;
    }

    public void setEmptyType(Boolean emptyType) {
        this.emptyType = emptyType;
    }
}