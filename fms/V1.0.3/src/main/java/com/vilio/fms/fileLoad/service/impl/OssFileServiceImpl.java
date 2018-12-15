package com.vilio.fms.fileLoad.service.impl;

import com.aliyun.oss.OSSClient;
import com.vilio.fms.fileLoad.service.OssFileService;
import com.vilio.fms.util.OSSUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/5/17.
 */
@Service("ossfileService")
public class OssFileServiceImpl implements OssFileService {
    private static Logger logger = Logger.getLogger(OssFileServiceImpl.class);

    @Autowired
    private OSSUtil ossUtil;

    public String uploadFile(InputStream is, String filePath, String fileName) throws Exception{
        OSSClient ossClient = ossUtil.getOSSClient();
        String md5key = ossUtil.uploadObject2OSS(ossClient,is,fileName, filePath);
        logger.info("上传后的文件MD5数字唯一签名:" + md5key);//上传后的文件MD5数字唯一签名
        return md5key;
    }

    public Map uploadFile2(InputStream is, String filePath, String fileName) throws Exception{
        Map returnMap = new HashMap();
        OSSClient ossClient = ossUtil.getOSSClient();
        String md5key = ossUtil.uploadObject2OSS(ossClient,is,fileName, filePath);
        String url = ossUtil.getUrl(ossClient, filePath + fileName);
        returnMap.put("md5key", md5key);
        returnMap.put("url", url);
        logger.info("上传后的文件MD5数字唯一签名:" + md5key);//上传后的文件MD5数字唯一签名
        return returnMap;
    }

    public void deleteFile(String filePath,String fileName) throws Exception{
        OSSClient ossClient = ossUtil.getOSSClient();
        ossUtil.deleteFile(ossClient,filePath,fileName);
    }

    public InputStream getFile(String filePath,String fileName) throws Exception{
        OSSClient ossClient = ossUtil.getOSSClient();
        return ossUtil.getOSS2InputStream(ossClient,filePath,fileName);
    }
}
