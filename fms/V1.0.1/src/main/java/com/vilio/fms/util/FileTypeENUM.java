package com.vilio.fms.util;

/**
 * Created by dell on 2017/5/15/0015.
 */
public enum FileTypeENUM {

    WORD_01("001", "保证合同.ftl", "保证书模版1"),

    WORD_02("002", "", "保证书模版2");

    private String index;

    private String fileName;

    private String description;


    private FileTypeENUM(String index, String fileName, String description) {
        this.index = index;
        this.fileName = fileName;
        this.description = description;
    }

    // 普通方法
    public static FileTypeENUM getACEnumByIndex(String index) {
        for (FileTypeENUM ac : FileTypeENUM.values()) {
            if (index.equals(ac.getIndex())) {
                return ac;
            }
        }
        return null;
    }

    //get set 方法

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.index + "_" + this.fileName;
    }


}
