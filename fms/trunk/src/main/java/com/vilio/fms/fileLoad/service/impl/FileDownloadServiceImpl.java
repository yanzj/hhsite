package com.vilio.fms.fileLoad.service.impl;

import com.vilio.fms.common.service.CommonService;
import com.vilio.fms.commonMapper.dao.FmsFileLoadMapper;
import com.vilio.fms.commonMapper.pojo.FmsFileLoad;
import com.vilio.fms.fileLoad.controller.FileUploadController;
import com.vilio.fms.fileLoad.service.FileDownloadService;
import com.vilio.fms.fileLoad.service.FileUploadService;
import com.vilio.fms.fileLoad.service.OssFileService;
import com.vilio.fms.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/17.
 */
@Service("fileDownloadService")
public class FileDownloadServiceImpl implements FileDownloadService {

    private static Logger logger = Logger.getLogger(FileDownloadServiceImpl.class);

    @Resource
    private CommonService commonService;
    @Resource
    OssFileService ossFileService;
    @Resource
    FmsFileLoadMapper fmsFileLoadMapper;
    @Resource
    ConfigInfo configInfo;


    public Map getFile(Map map) throws Exception{
        Map result = new HashMap();

        String serialNo = (String)map.get("serialNo");

        FmsFileLoad fmsFileLoad = fmsFileLoadMapper.queryBySerialNo(serialNo);


        //目前只支持单文件下载
        String filePath = fmsFileLoad.getFilePath();
        String fileName = fmsFileLoad.getFileName();
        String originalfileName = fmsFileLoad.getOriginalFileName();
        InputStream is = ossFileService.getFile(filePath,fileName);

        Map body = new HashMap();
        body.put("fileName",originalfileName);
        body.put("returnCode", ReturnCode.SUCCESS_CODE);

        result.put("is",is);
        result.put("body",body);

        return result;
    }

    public Map getFileZip(Map map) throws Exception{
        Map result = new HashMap();

        InputStream is = null;
        String zipFileName = CommonUtil.getCurrentTimeStr(null, null);

        List<Map> serialNoList = (List<Map>) map.get("serialNoList");

        List<Map<String, InputStream>> fileList = new ArrayList();
        if(null != serialNoList && serialNoList.size() > 0){
            for(Map serialMap: serialNoList){
                String serialNo = (String)serialMap.get("serialNo");
                FmsFileLoad fmsFileLoad = fmsFileLoadMapper.queryBySerialNo(serialNo);
                String filePath = fmsFileLoad.getFilePath();
                String fileName = fmsFileLoad.getFileName();
                String originalfileName = fmsFileLoad.getOriginalFileName();
                InputStream in = ossFileService.getFile(filePath,fileName);
                Map<String, InputStream> inputStreaminMap = new HashedMap();
                inputStreaminMap.put(originalfileName, in);
                fileList.add(inputStreaminMap);
            }
            is = ZipUtil.writeZip(fileList ,zipFileName);
        }


        Map body = new HashMap();
        body.put("fileName",zipFileName);
        body.put("returnCode", ReturnCode.SUCCESS_CODE);

        result.put("is",is);
        result.put("body",body);

        return result;
    }

    public Map getUrlList(Map map) throws Exception {
        Map result = new HashMap();

        List<Map> serialNoList = (List<Map>) map.get("serialNoList");

        List<Map> fileMaps = new ArrayList();
        if(null != serialNoList && serialNoList.size() > 0){
            for(Map m : serialNoList){
                String serialNo = (String) m.get("serialNo");
                FmsFileLoad fmsFileLoad = fmsFileLoadMapper.queryBySerialNo(serialNo);
                if(null != fmsFileLoad){
                    Map fileMap = new HashMap();
                    fileMap.put("serialNo", fmsFileLoad.getSerialNo());
                    fileMap.put("fileName", fmsFileLoad.getOriginalFileName());
                    fileMap.put("fileSuffix", fmsFileLoad.getFileSuffix());
                    fileMap.put("uploadTime", DateUtil.formatDateTime(fmsFileLoad.getCreateTime(),DateUtil.DATE_TIME_PATTERN3));
                    String url = fmsFileLoad.getUrl();
                    fileMap.put("url", url);

                    fileMaps.add(fileMap);
                }
            }
        }

        Map body = new HashMap();
        body.put("fileMaps",fileMaps);
        body.put("returnCode", ReturnCode.SUCCESS_CODE);

        result.put("body",body);

        return result;
    }
}
