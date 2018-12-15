package com.vilio.plms.service.fundManagement;

import com.vilio.plms.service.Plms000001;
import com.vilio.plms.service.base.RemoteFmsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Created by xiezhilei on 2017/8/23.
 */
@Service
public class ReceiptsRecordUploadFileDealTask implements Runnable, Serializable {

    private static final long serialVersionUID = -6626027616177700489L;
    private static final Logger logger = Logger.getLogger(ReceiptsRecordUploadFileDealTask.class);

    @Resource
    FundManagerService fundManagerService;

    private String code;
    private String fileCode;
    private String fileName;
    private String userNo;

    public void run(){
        logger.info(Thread.currentThread().getName() + "------------------------");
        try {
            fundManagerService.dealFile(code,fileCode,fileName,userNo);
        } catch (Exception e) {
            logger.error("解析文件异步任务失败",e);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
