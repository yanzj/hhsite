package com.vilio.mps.common.pojo;

/**
 * 类名： Template<br>
 * 功能：模板实体<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：其他消息有可能也有用到签名实体和模板实体<br>
 */
public class Template {

    private Integer id;
    private String innerTemplateCode;
    private String chlTemplateCode;
    private String templateParam;
    private String status;
    private String content;
    private String templateDesc;
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInnerTemplateCode() {
        return innerTemplateCode;
    }

    public void setInnerTemplateCode(String innerTemplateCode) {
        this.innerTemplateCode = innerTemplateCode;
    }

    public String getChlTemplateCode() {
        return chlTemplateCode;
    }

    public void setChlTemplateCode(String chlTemplateCode) {
        this.chlTemplateCode = chlTemplateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Template(Integer id, String innerTemplateCode, String chlTemplateCode, String templateParam, String status, String content, String templateDesc, String createTime) {
        this.id = id;
        this.innerTemplateCode = innerTemplateCode;
        this.chlTemplateCode = chlTemplateCode;
        this.templateParam = templateParam;
        this.status = status;
        this.content = content;
        this.templateDesc = templateDesc;
        this.createTime = createTime;
    }

    public Template() {
    }
}
