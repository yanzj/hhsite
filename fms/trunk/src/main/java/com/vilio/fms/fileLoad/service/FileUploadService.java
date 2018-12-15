package com.vilio.fms.fileLoad.service;

import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/13.
 */
public interface FileUploadService {
    public Map uploadFile(Map map) throws Exception;

    public Map zipAndUploadFile(Map map) throws Exception;

    public Map zipAndUploadFileSerialNoList(Map map) throws Exception;
}
