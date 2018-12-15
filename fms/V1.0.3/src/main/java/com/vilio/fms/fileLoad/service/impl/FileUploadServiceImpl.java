package com.vilio.fms.fileLoad.service.impl;

import com.vilio.fms.common.service.CommonService;
import com.vilio.fms.commonMapper.dao.FmsFileLoadMapper;
import com.vilio.fms.commonMapper.pojo.FmsFileLoad;
import com.vilio.fms.fileLoad.service.FileDownloadService;
import com.vilio.fms.fileLoad.service.FileUploadService;
import com.vilio.fms.fileLoad.service.OssFileService;
import com.vilio.fms.fileLoad.util.Consts;
import com.vilio.fms.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dell on 2017/5/17.
 */
@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    private static Logger logger = Logger.getLogger(FileUploadServiceImpl.class);
    private static final String zipTempFile = "./zipTempFile/";

    @Resource
    private CommonService commonService;
    @Resource
    OssFileService ossFileService;
    @Resource
    FmsFileLoadMapper fmsFileLoadMapper;
    @Resource
    ConfigInfo configInfo;
    @Resource
    FileDownloadService fileDownloadService;


    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map uploadFile(Map map) throws Exception {
        return myUpload(map);
    }

    public Map zipAndUploadFile(Map map) throws Exception {
        MultipartFile[] files = (MultipartFile[]) map.get("files");
        String applyFilePath = (String) map.get("filePath");
        if (StringUtils.isBlank(applyFilePath)) {
            applyFilePath = Constants.APPLY_FILE_PATH;
        }
        String fileNameSer = CommonUtil.getCurrentTimeStr("F", "X");
        //压缩
        InputStream in = ZipUtil.writeZip(files, fileNameSer);

        String interfaceType = Consts.UPLOAD_INTERFACE_TYPE;
        if (StringUtils.isBlank(interfaceType)) {
            interfaceType = Consts.UPLOAD_INTERFACE_TYPE_DEFAULT;
        }

        Map body = new HashMap();
        List<Map> fileMaps = new ArrayList();

        String serialNo = commonService.getUUID();

        String fileSuffix = "zip";
        String fileName;
        fileName = fileNameSer + "." + fileSuffix;
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
        if (Consts.UPLOAD_INTERFACE_TYPE_Oss.equals(interfaceType)) {
            Map returnMap = ossFileService.uploadFile2(in, applyFilePath, fileName);

            String md5key = (String) returnMap.get("md5key");
            url = "/fileLoad/getFile" + "?serialNo=" + serialNo;
            fmsFileLoad.setUrl(url);

            fmsFileLoadMapper.modifyFileLoadUrl(fmsFileLoad);

        }

        //上传后删除本地生成文件
        boolean flag = ZipUtil.deleteZipFile(fileNameSer, in);

        Map fileMap = new HashMap();
        fileMap.put("serialNo", fmsFileLoad.getSerialNo());
        fileMap.put("fileName", fileName);
        fileMap.put("url", url);
        fileMaps.add(fileMap);

        body.put("fileMaps", fileMaps);

        body.put("returnCode", ReturnCode.SUCCESS_CODE);
        body.put("returnMessage", "完成上传");

        Map result = new HashMap();
        result.put("body", body);

        return result;
    }

    /**
     * 个性化定制接口，仅供生成外发压缩材料
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map zipAndUploadFileSerialNoList(Map map) throws Exception {
        String applyFilePath = (String) map.get("filePath");
        String originalFileName = (String) map.get("fileName");
        if (StringUtils.isBlank(applyFilePath)) {
            applyFilePath = Constants.APPLY_FILE_PATH;
        }
        List<Map> serialNoList = (List) map.get("serialNoList");
        if (null == serialNoList) {
            return null;
        }
        String[] fileTypeArray = {"身份证", "户口本", "婚姻证明", "征信报告", "抵押房产证", "产调", "评估单", "银行流水", "看房照片", "备用房产证", "企业材料", "风险信息查询", "借款申请书及外访调查报告", "放款银行卡", "其他"};
        int dirIndex = 1;
        String zipDirName = zipTempFile + CommonUtil.getCurrentTimeStr("tempzip_", "");
        String realZipDirName = zipDirName + File.separator + originalFileName;
        File zipDirFile = new File(zipDirName);//用以区别唯一性
        File realZipDirFile = new File(realZipDirName);//用以打包zip
        if(!realZipDirFile.exists()){
            realZipDirFile.mkdirs();
        }
        for (String dirType : fileTypeArray) {
            boolean needPlus = false;
            for (Map serialNoMap : serialNoList) {
                boolean haveMatch = false;
                String finalFileName = "";
                InputStream in = null;
                //若文件有类型，则按照不同的类型分类放入到对应的文件夹里
                if (dirType.equals(serialNoMap.get(Fields.PARAM_FILE_TYPE))) {
                    haveMatch = true;
                    needPlus = true;
                    Map requestMap = new HashMap();
                    requestMap.put("serialNo", serialNoMap.get("serialNo"));
                    Map resonMap = fileDownloadService.getFile(requestMap);
                    Map body = (Map) resonMap.get("body");
                    in = (InputStream) resonMap.get("is");
                    String typeFileName = String.format("%02d", dirIndex);
                    String typeDir = realZipDirName + File.separator + typeFileName + dirType;
                    finalFileName = typeDir + File.separator + body.get("fileName");
                    //分类的文件夹
                    File typeFile = new File(typeDir);
                    if (!typeFile.exists()) {
                        typeFile.mkdirs();
                    }
                }
                //没有类型的文件，则直接放在根目录下即可(只需首轮添加这种无类型文件)
                if(dirIndex == 1) {
                    if (serialNoMap.get(Fields.PARAM_FILE_TYPE) == null || "".equals(serialNoMap.get(Fields.PARAM_FILE_TYPE))) {
                        haveMatch = true;
                        Map requestMap = new HashMap();
                        requestMap.put("serialNo", serialNoMap.get("serialNo"));
                        Map resonMap = fileDownloadService.getFile(requestMap);
                        Map body = (Map) resonMap.get("body");
                        in = (InputStream) resonMap.get("is");
                        finalFileName = realZipDirName + File.separator + body.get("fileName");
                    }
                }
                if(haveMatch) {
                    File finalFile = new File(finalFileName);
                    if (!(finalFile.exists())) {
                        finalFile.createNewFile();
                    }
                    //写入文件
                    FileOutputStream fos = new FileOutputStream(finalFileName);
                    byte[] b = new byte[8192];
                    int len = 0;
                    while ((len = in.read(b)) != -1) {
                        fos.write(b, 0, len);
                    }
                    in.close();
                    fos.close();
                }
            }
            if(needPlus){
                dirIndex++;
            }
        }

        String fileNameSer = CommonUtil.getCurrentTimeStr("TEMPZIP_", ".zip");
        ZipUtil.createZip(realZipDirFile.getAbsolutePath(), zipDirFile.getParent() + File.separator + originalFileName + ".zip");
        //压缩
        InputStream in = new FileInputStream(zipDirFile.getParent() + File.separator + originalFileName + ".zip");

        String interfaceType = Consts.UPLOAD_INTERFACE_TYPE;
        if (StringUtils.isBlank(interfaceType)) {
            interfaceType = Consts.UPLOAD_INTERFACE_TYPE_DEFAULT;
        }

        Map body = new HashMap();

        String serialNo = commonService.getUUID();

        String fileSuffix = "zip";
        //保存本地
        FmsFileLoad fmsFileLoad = new FmsFileLoad();
        fmsFileLoad.setInterfaceType(interfaceType);
        fmsFileLoad.setFileName(fileNameSer);
        fmsFileLoad.setFilePath(applyFilePath);
        fmsFileLoad.setFileSuffix(fileSuffix);
        fmsFileLoad.setSerialNo(serialNo);
        fmsFileLoad.setOriginalFileName(null == originalFileName ? fileNameSer : (originalFileName + ".zip"));

        fmsFileLoadMapper.saveFileLoad(fmsFileLoad);

        String url = null;
        if (Consts.UPLOAD_INTERFACE_TYPE_Oss.equals(interfaceType)) {
            Map returnMap = ossFileService.uploadFile2(in, applyFilePath, fileNameSer);

            String md5key = (String) returnMap.get("md5key");
            url = "/fileLoad/getFile" + "?serialNo=" + serialNo;
            fmsFileLoad.setUrl(url);

            fmsFileLoadMapper.modifyFileLoadUrl(fmsFileLoad);

        }

        //上传后删除本地生成文件
        ZipUtil.deleteDir(zipDirFile);
        boolean flag = ZipUtil.deleteZipFile(originalFileName + ".zip", in);

        body.put("serialNo", fmsFileLoad.getSerialNo());
        body.put("fileName", null == (originalFileName + ".zip") ? fileNameSer : (originalFileName + ".zip"));
        body.put("url", url);

        body.put("returnCode", ReturnCode.SUCCESS_CODE);
        body.put("returnMessage", "完成上传");

        Map result = new HashMap();
        result.put("body", body);

        return result;
    }

    private synchronized Map myUpload(Map map) throws Exception {
        MultipartFile[] files = (MultipartFile[]) map.get("files");
        String applyFilePath = (String) map.get("filePath");
        if (StringUtils.isBlank(applyFilePath)) {
            applyFilePath = Constants.APPLY_FILE_PATH;
        }

        String interfaceType = Consts.UPLOAD_INTERFACE_TYPE;
        if (StringUtils.isBlank(interfaceType)) {
            interfaceType = Consts.UPLOAD_INTERFACE_TYPE_DEFAULT;
        }

        Map body = new HashMap();
        List<Map> fileMaps = new ArrayList();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String fileName = "";
            if (file instanceof CommonsMultipartFile) {
                fileName = ((CommonsMultipartFile) file).getFileItem().getName();
            } else {
                fileName = file.getName();
            }
            String sysFileName = commonService.getUUID();
            String serialNo = commonService.getUUID();

            String fileSuffix = "";
            if (fileName.lastIndexOf(".") >= 0) {
                sysFileName = sysFileName + fileName.substring(fileName.lastIndexOf("."));
                fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
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
            if (Consts.UPLOAD_INTERFACE_TYPE_Oss.equals(interfaceType)) {
                Map returnMap = ossFileService.uploadFile2(file.getInputStream(), applyFilePath, sysFileName);

                String md5key = (String) returnMap.get("md5key");
                url = "/fileLoad/getFile" + "?serialNo=" + serialNo;
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

        body.put("fileMaps", fileMaps);

        body.put("returnCode", ReturnCode.SUCCESS_CODE);
        body.put("returnMessage", "完成上传");

        Map result = new HashMap();
        result.put("body", body);

        return result;
    }
}
