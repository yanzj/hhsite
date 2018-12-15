package com.vilio.fms.fileLoad.controller;

import com.vilio.fms.fileLoad.service.FileDownloadService;
import com.vilio.fms.util.CommonUtil;
import com.vilio.fms.util.Fields;
import com.vilio.fms.util.ZipUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/5/17.
 */
@Controller("fileDownloadController")
public class FileDownloadController {
    private static Logger logger = Logger.getLogger(FileDownloadController.class);

    @Resource
    FileDownloadService fileDownloadService;

    @RequestMapping(value = "/api/fileLoad/getFile",method = RequestMethod.GET)
    public void getFile(HttpServletResponse rsp, HttpServletRequest rqs) throws Exception{
        logger.info("获取文件(FileDownloadController.getFile)被调用开始,入参：" + "" );

        String serialNo = rqs.getParameter("serialNo");
        Map result = null;

        Map param = new HashMap();
        param.put("serialNo",serialNo);

        try {
            CommonUtil.dealEmpty2Null(param);
            result = fileDownloadService.getFile(param);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        Map body = (Map) result.get("body");
        String fileName = (String) body.get("fileName");

        rsp.setContentType("application/x-msdownload");
        String iso_filename = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        rsp.setHeader("Content-Disposition",
                "attachment;filename=" + iso_filename);

        OutputStream outputStream = rsp.getOutputStream();
        InputStream in = (InputStream)result.get("is");
        int len=0;
        byte[]buf=new byte[1024];

        while((len=in.read(buf,0,1024))!=-1){
            outputStream.write(buf, 0, len);
        }
        outputStream.close();

        logger.info("获取文件(FileDownloadController.getFile)被调用结束,输出结果：" );

    }

    @RequestMapping(value = "/api/fileLoad/getFileZip",method = RequestMethod.POST)
    public void getFileZip(HttpServletResponse rsp, HttpServletRequest rqs) throws Exception{
        // 读取请求内容  
        BufferedReader br = new BufferedReader(new InputStreamReader(rqs.getInputStream(),"utf-8"));
        logger.info("获取文件(FileDownloadController.getFile)被调用开始,入：" + br);
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null){
            sb.append(line);
        }
        logger.info("获取文件(FileDownloadController.getFile)被调用开始,入参：" + sb );
        //将json字符串转换为json对象  
        JSONObject json = JSONObject.fromObject(sb.toString());
        Object serialNoList = json.get("serialNoList");

        Map result = null;

        Map param = new HashMap();
        param.put("serialNoList",serialNoList);

        try {
            CommonUtil.dealEmpty2Null(param);
            result = fileDownloadService.getFileZip(param);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        Map body = (Map) result.get("body");
        String fileName = (String) body.get("fileName");

        rsp.setContentType("application/x-msdownload");
        String iso_filename = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        rsp.setHeader("Content-Disposition",
                "attachment;filename=" + iso_filename + ".zip");

        OutputStream outputStream = rsp.getOutputStream();
        InputStream in = (InputStream)result.get("is");
        int len=0;
        byte[]buf=new byte[1024];

        while((len=in.read(buf,0,1024))!=-1){
            outputStream.write(buf, 0, len);
        }
        outputStream.close();

        //上传后删除本地生成文件
        boolean flag = ZipUtil.deleteZipFile(iso_filename ,in);


        logger.info("获取文件(FileDownloadController.getFile)被调用结束,输出结果：" + flag);

    }

    @RequestMapping(value = "/api/fileLoad/getUrlList",method = RequestMethod.POST)
    @ResponseBody
    public Map getUrlList(@RequestBody Map paramMap) throws Exception{
        logger.info("获取文件地址(FileDownloadController.getUrlList)被调用开始,入参：" + "" );

        Map result = new HashMap();
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> bodyMap = new HashMap<String, Object>();

        Map param = new HashMap();

        try {
            headMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_HEAD);
            bodyMap = (Map<String, Object>) paramMap.get(Fields.PARAM_MESSAGE_BODY);
            CommonUtil.dealEmpty2Null(bodyMap);
            bodyMap = fileDownloadService.getUrlList(bodyMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        result.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        result.put(Fields.PARAM_MESSAGE_BODY, bodyMap.get(Fields.PARAM_MESSAGE_BODY));
        logger.info("获取文件地址(FileDownloadController.getUrlList)被调用结束,输出结果：" );

        return result;

    }

}
