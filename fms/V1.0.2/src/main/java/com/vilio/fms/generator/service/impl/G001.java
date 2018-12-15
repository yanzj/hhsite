package com.vilio.fms.generator.service.impl;

import com.vilio.fms.common.service.GenerateService;
import com.vilio.fms.commonMapper.dao.FmsFileLoadMapper;
import com.vilio.fms.commonMapper.pojo.FmsFileLoad;
import com.vilio.fms.fileLoad.service.FileUploadService;
import com.vilio.fms.fileLoad.service.OssFileService;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.HHBizException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/7/6/0006.
 */
@Service("G001")
public class G001 extends GenerateService {

    @Resource
    OssFileService ossFileService;

    @Resource
    FmsFileLoadMapper fmsFileLoadMapper;

    @Resource
    FileUploadService fileUploadService;

    private static Logger logger = Logger.getLogger(G001.class);

    /**
     * 生成文件并上传到阿里云服务器
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public Map subExcute(Map paramMap, Map rootMap) throws Exception {

        String modelFileCode = paramMap.get(Fields.PARAM_MODEL_FILE_CODE) == null ? "" : paramMap.get(Fields.PARAM_MODEL_FILE_CODE).toString();
        String modelFileName = paramMap.get(Fields.PARAM_MODEL_FILE_NAME) == null ? "" : paramMap.get(Fields.PARAM_MODEL_FILE_NAME).toString();
        String modelName = paramMap.get(Fields.PARAM_MODEL_NAME) == null ? "" : paramMap.get(Fields.PARAM_MODEL_NAME).toString();
        String fileSuffix = paramMap.get(Fields.PARAM_FILE_SUFFIX) == null ? "" : paramMap.get(Fields.PARAM_FILE_SUFFIX).toString();

        //Step1 从阿里云服务器获取模版文件流
        FmsFileLoad fmsFileLoad = fmsFileLoadMapper.queryBySerialNo(modelFileCode);
        if(fmsFileLoad == null){
            logger.info("获取不到模板文件，请检查相关配置项！此次生成失败！！！\n");
            throw new HHBizException("9999", "获取不到模板文件，请检查相关配置项！此次生成失败！");
        }
        String filePath = fmsFileLoad.getFilePath();
        String fileName = fmsFileLoad.getFileName();
        InputStream is = ossFileService.getFile(filePath,fileName);
        String templateContent = CommonUtil.inputstream2Str(is, "UTF-8");

        //Step2 开始生成文件
        String fileRandomName = CommonUtil.getCurrentTimeStr("_", "");
        String uploadFileName = modelName + fileRandomName + "." + fileSuffix;
        Writer out = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            //创建配置实例
            Configuration configuration = new Configuration();
            //设置编码
            configuration.setDefaultEncoding("UTF-8");
            //设置templateLoading
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("HHTemplate", templateContent);
            configuration.setTemplateLoader(stringLoader);
            //获取模板
            Template template = configuration.getTemplate("HHTemplate","UTF-8");

            //将模板和数据模型合并生成文件
            byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
            out = new BufferedWriter(outputStreamWriter);

            //生成文件
            template.process(rootMap, out);
            //关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        //Step3 上传文件
        Map uploadFileParamMap = new HashMap();
        MultipartFile[] files = {null};
        MockMultipartFile hhMultipartFile = new MockMultipartFile(uploadFileName, byteArrayOutputStream.toByteArray());
        files[0] = hhMultipartFile;
        uploadFileParamMap.put("files", files);
        uploadFileParamMap.put("filePath", "fms/targetfile/");
        CommonUtil.dealEmpty2Null(uploadFileParamMap);

        return fileUploadService.uploadFile(uploadFileParamMap);
    }

    @Override
    public String getGenerateDescription() {
        return "G001(word,excel,pdf...)";
    }

}
