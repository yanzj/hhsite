package com.vilio.fms.fileLoad.service;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by dell on 2017/5/17.
 */
public interface OssFileService {
    public String uploadFile(InputStream is, String filePath, String fileName) throws Exception;

    public Map uploadFile2(InputStream is, String filePath, String fileName) throws Exception;

    public void deleteFile(String filePath, String fileName) throws Exception;

    public InputStream getFile(String filePath, String fileName) throws Exception;
}
