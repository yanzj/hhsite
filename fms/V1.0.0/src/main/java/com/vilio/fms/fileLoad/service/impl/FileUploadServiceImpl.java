package com.vilio.fms.fileLoad.service.impl;

import com.vilio.fms.common.service.CommonService;
import com.vilio.fms.commonMapper.dao.FmsFileLoadMapper;
import com.vilio.fms.commonMapper.pojo.FmsFileLoad;
import com.vilio.fms.fileLoad.service.FileUploadService;
import com.vilio.fms.fileLoad.service.OssFileService;
import com.vilio.fms.fileLoad.util.Consts;
import com.vilio.fms.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dell on 2017/5/17.
 */
@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    private static Logger logger = Logger.getLogger(FileUploadServiceImpl.class);

    @Resource
    private CommonService commonService;
    @Resource
    OssFileService ossFileService;
    @Resource
    FmsFileLoadMapper fmsFileLoadMapper;
    @Resource
    ConfigInfo configInfo;

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map uploadFile(Map map) throws Exception{
        return myUpload(map);
    }

    public Map zipAndUploadFile(Map map) throws Exception {
        MultipartFile[] files = (MultipartFile[])map.get("files");
        String applyFilePath = (String) map.get("filePath");
        if(StringUtils.isBlank(applyFilePath)){
            applyFilePath = Constants.APPLY_FILE_PATH;
        }
        String fileNameSer = CommonUtil.getCurrentTimeStr("F","X");
        //压缩
        InputStream in = ZipUtil.writeZip(files, fileNameSer);

        String interfaceType = Consts.UPLOAD_INTERFACE_TYPE;
        if(StringUtils.isBlank(interfaceType)){
            interfaceType = Consts.UPLOAD_INTERFACE_TYPE_DEFAULT;
        }

        Map body = new HashMap();
        List<Map> fileMaps = new ArrayList();

        String serialNo = commonService.getUUID();

        String fileSuffix =  "zip";
        String fileName;
        fileName= fileNameSer + "." + fileSuffix;
        //保存本地
        FmsFileLoad fmsFileLoad = new FmsFileLoad();
        fmsFileLoad.setInterfaceType(interfaceType);
        fmsFileLoad.setFileName(fileName);
        fmsFileLoad.setFilePath(applyFilePath);
        fmsFileLoad.setFileSuffix(fileSuffix);
        fmsFileLoad.setSerialNo(serialNo);
        fmsFileLoad.setOriginalFileName(fileName);

        fmsFileLoadMapper.saveFileLoad(fmsFileLoad);

        String url = null;
        if(Consts.UPLOAD_INTERFACE_TYPE_Oss.equals(interfaceType)){
            Map returnMap = ossFileService.uploadFile2(in, applyFilePath,fileName);

            String md5key = (String) returnMap.get("md5key");
            url = "/fileLoad/getFile" + "?serialNo=" +  serialNo;
            fmsFileLoad.setUrl(url);

            fmsFileLoadMapper.modifyFileLoadUrl(fmsFileLoad);

        }

        //上传后删除本地生成文件
        boolean flag = ZipUtil.deleteZipFile(fileNameSer ,in);

        Map fileMap = new HashMap();
        fileMap.put("serialNo", fmsFileLoad.getSerialNo());
        fileMap.put("fileName", fileName);
        fileMap.put("url", url);
        fileMaps.add(fileMap);

        body.put("fileMaps",fileMaps);

        body.put("returnCode",ReturnCode.SUCCESS_CODE);
        body.put("returnMessage","完成上传");

        Map result = new HashMap();
        result.put("body",body);

        return result;
    }

    private synchronized Map myUpload(Map map) throws Exception{
        MultipartFile[] files = (MultipartFile[])map.get("files");
        String applyFilePath = (String) map.get("filePath");
        if(StringUtils.isBlank(applyFilePath)){
            applyFilePath = Constants.APPLY_FILE_PATH;
        }

        String interfaceType = Consts.UPLOAD_INTERFACE_TYPE;
        if(StringUtils.isBlank(interfaceType)){
            interfaceType = Consts.UPLOAD_INTERFACE_TYPE_DEFAULT;
        }

        Map body = new HashMap();
        List<Map> fileMaps = new ArrayList();
        for (int i=0; i<files.length;i++){
            MultipartFile file = files[i];
            String fileName = "";
            if(file instanceof CommonsMultipartFile){
                fileName = ((CommonsMultipartFile)file).getFileItem().getName();
            } else {
                fileName = file.getName();
            }
            String sysFileName = commonService.getUUID();
            String serialNo = commonService.getUUID();

            String fileSuffix = "";
            if(fileName.lastIndexOf(".") >= 0){
                sysFileName = sysFileName + fileName.substring(fileName.lastIndexOf("."));
                fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);
            }

            //保存本地
            FmsFileLoad fmsFileLoad = new FmsFileLoad();
            fmsFileLoad.setInterfaceType(interfaceType);
            fmsFileLoad.setFileName(sysFileName);
            fmsFileLoad.setFilePath(applyFilePath);
            fmsFileLoad.setFileSuffix(fileSuffix);
            fmsFileLoad.setSerialNo(serialNo);
            fmsFileLoad.setOriginalFileName(fileName);

            fmsFileLoadMapper.saveFileLoad(fmsFileLoad);

            String url = null;
            if(Consts.UPLOAD_INTERFACE_TYPE_Oss.equals(interfaceType)){
                Map returnMap = ossFileService.uploadFile2(file.getInputStream(), applyFilePath,sysFileName);

                String md5key = (String) returnMap.get("md5key");
                url = "/fileLoad/getFile" + "?serialNo=" +  serialNo;
                fmsFileLoad.setUrl(url);

                fmsFileLoadMapper.modifyFileLoadUrl(fmsFileLoad);

            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map fileMap = new HashMap();
            fileMap.put("serialNo", fmsFileLoad.getSerialNo());
            fileMap.put("fileName", fileName);
            fileMap.put("url", url);
            fileMap.put("uploadTime", sdf.format(new Date()));
            fileMaps.add(fileMap);
        }

        body.put("fileMaps",fileMaps);

        body.put("returnCode",ReturnCode.SUCCESS_CODE);
        body.put("returnMessage","完成上传");

        Map result = new HashMap();
        result.put("body",body);

        return result;
    }
}
