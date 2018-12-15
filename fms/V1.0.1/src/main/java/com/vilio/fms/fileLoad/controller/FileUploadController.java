package com.vilio.fms.fileLoad.controller;
import com.vilio.fms.fileLoad.service.FileUploadService;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.ReturnCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuxu on 2017/1/13.
 */
@Controller("fileUploadController")
public class FileUploadController implements ServletContextAware {

    private static Logger logger = Logger.getLogger(FileUploadController.class);

    @Resource
    FileUploadService fileUploadService;

    //Spring这里是通过实现ServletContextAware接口来注入ServletContext对象
    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @RequestMapping(value = "/api/fileLoad/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFile(HttpServletResponse rsp,HttpServletRequest rqs,@RequestParam("file") MultipartFile[] files) throws Exception{
        Integer length = null == files ? -1 : files.length;
        String filePath = rqs.getParameter("filePath");
        logger.info("文件上传(FileUploadController.uploadFile)被调用开始,入参：length = " + length + ",filePath = " + filePath);
        Map param = new HashMap();

        param.put("files",files);
        param.put("filePath",filePath);
        Map result = null;

        try {
            CommonUtil.dealEmpty2Null(param);
            result = fileUploadService.uploadFile(param);

        }catch (Exception e){
            logger.error("系统异常：",e);
            result.put("returnCode", ReturnCode.SYSTEM_EXCEPTION);
        }

        logger.info("文件上传(FileUploadController.uploadFile),输出结果：" + result);
        return result;
    }

    @RequestMapping(value = "/api/fileLoad/zipAndUploadFile",method = RequestMethod.POST)
    @ResponseBody
    public Map zipAndUploadFile(HttpServletResponse rsp,HttpServletRequest rqs,@RequestParam("file") MultipartFile[] files) throws Exception{
        logger.info("文件压缩并上传(FileUploadController.zipAndUploadFile)被调用开始,入参：" + "" );
        Map param = new HashMap();
        String filePath = rqs.getParameter("filePath");
        param.put("files",files);
        param.put("filePath",filePath);
        Map result = null;

        try {
            CommonUtil.dealEmpty2Null(param);
            result = fileUploadService.zipAndUploadFile(param);

        }catch (Exception e){
            logger.error("系统异常：",e);
            result.put("returnCode", ReturnCode.SYSTEM_EXCEPTION);
        }

        logger.info("文件压缩并上传(FileUploadController.zipAndUploadFile),输出结果：" + result);
        return result;
    }

    @RequestMapping(value = "/api/fileLoad/zipAndUploadFileSerialNoList",method = RequestMethod.POST)
    @ResponseBody
    public Map zipAndUploadFileSerialNoList(@RequestBody Map paramMap) throws Exception{
        logger.info("文件压缩并上传(FileUploadController.zipAndUploadFileSerialNoList)被调用开始,入参：" + "" );

        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();

        Map param = new HashMap();

        try {
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            CommonUtil.dealEmpty2Null(bodyMap);
            bodyMap = fileUploadService.zipAndUploadFileSerialNoList(bodyMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap.get(Fields.PARAM_MESSAGE_BODY));
        logger.info("文件压缩并上传(FileUploadController.zipAndUploadFileSerialNoList)被调用结束,输出结果：" );

        return result;

    }

}
