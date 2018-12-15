package com.vilio.plms.pojo;/**
 * Created by dell on 2017/8/29/0029.
 */

/**
 * 类名： PlmsFileModels<br>
 * 功能：<br>
 * 版本： 1.0<br>
 * 日期： 2017/8/29/0029<br>
 * 作者： liuzhu.feng<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class PlmsFileModels {

    private String id;

    private String code;

    private String type;

    private String fileCode;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
