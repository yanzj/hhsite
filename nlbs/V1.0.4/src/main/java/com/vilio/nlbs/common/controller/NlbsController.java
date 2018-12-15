package com.vilio.nlbs.common.controller;

import com.vilio.nlbs.apply.service.ApplyService;
import com.vilio.nlbs.common.service.PretreatmentService;
import com.vilio.nlbs.remote.service.RemoteBpsService;
import com.vilio.nlbs.util.CommonUtil;
import com.vilio.nlbs.util.Fields;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
public class NlbsController {

    private static Logger logger = Logger.getLogger(NlbsController.class);

    @Resource
    private PretreatmentService pretreatmentService;

    @Resource
    private ApplyService applyService;

    @Resource
    private RemoteBpsService remoteBpsService;


    @RequestMapping(value = "/backendapi",method = RequestMethod.POST)
    @ResponseBody
    public Map nlbsGeneralController(@RequestBody Map paramMap) throws Exception{
        logger.info("*****************************************************************************");
        logger.info("【进件系统(nlbs)】接收到请求(NlbsController.nlbsGeneralController),入参：" + paramMap );
        //定义返回值的map
        Map result = new HashMap();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            //调用服务，分发请求
            result = pretreatmentService.dispatchServices(paramMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }
        logger.info("【进件系统(nlbs)】请求处理完成(NlbsController.nlbsGeneralController),输出结果：" + result);
        logger.info("*****************************************************************************");
        return result;

    }

    /* 暂时不用 */
    //@RequestMapping(value = "/callbpsservice",method = RequestMethod.POST)
    //@ResponseBody
    public Map callBpsService(@RequestBody Map paramMap) throws Exception{
        logger.info("*****************************************************************************");
        logger.info("【进件系统(nlbs)--询价系统（bps）】接收到请求(NlbsController.callBpsService),入参：" + paramMap );
        //定义返回值的map
        Map result = new HashMap();
        try {
            CommonUtil.dealEmpty2Null(paramMap);
            //调用服务，分发请求
            result = remoteBpsService.callService(paramMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }
        logger.info("【进件系统(nlbs)--询价系统（bps）】请求处理完成(NlbsController.callBpsService),输出结果：" + result);
        logger.info("*****************************************************************************");
        return result;

    }

    @RequestMapping(value = "/fileLoad/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFile(HttpServletResponse rsp, HttpServletRequest rqs, @RequestParam("file") MultipartFile[] files) throws Exception{
        Integer length = null == files ? -1 : files.length;
        String filePath = rqs.getParameter("filePath");
        logger.info("文件上传(NlbsController.uploadFile)被调用开始,入参：：length = " + length + ",filePath = " + filePath);

        Map param = new HashMap();
        param.put("files",files);
        param.put("filePath",filePath);
        Map result = null;

        try {
            CommonUtil.dealEmpty2Null(param);
            result = applyService.uploadFile(param);

        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        logger.info("文件上传(NlbsController.uploadFile),输出结果：" + result);
        return result;
    }

    @RequestMapping(value = "/fileLoad/getFile",method = RequestMethod.GET)
    public void getFile(HttpServletResponse rsp, HttpServletRequest rqs) throws Exception{
        logger.info("获取文件(NlbsController.getFile)被调用开始,入参：" + "" );

        String serialNo = rqs.getParameter("serialNo");
        Map result = null;

        Map param = new HashMap();
        param.put("serialNo",serialNo);

        try {
            CommonUtil.dealEmpty2Null(param);
            result = applyService.getFile(param);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        OutputStream outputStream = rsp.getOutputStream();
        InputStream in = (InputStream)result.get("is");
        int len=0;
        byte[]buf=new byte[1024];

        while((len=in.read(buf,0,1024))!=-1){
            outputStream.write(buf, 0, len);
        }
        outputStream.close();

        logger.info("获取文件(NlbsController.getFile)被调用结束,输出结果：" + result);

    }
    @RequestMapping(value = "/fileLoad/downloadFile",method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse rsp, HttpServletRequest rqs) throws Exception{
        logger.info("获取文件(NlbsController.downloadFile)被调用开始,入参：" + "" );

        String serialNo = rqs.getParameter("serialNo");
        Map result = null;

        Map param = new HashMap();
        param.put("serialNo",serialNo);

        try {
            CommonUtil.dealEmpty2Null(param);
            result = applyService.downloadFile(param, rsp);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        OutputStream outputStream = rsp.getOutputStream();
        InputStream in = (InputStream)result.get("is");
        int len=0;
        byte[]buf=new byte[1024];

        while((len=in.read(buf,0,1024))!=-1){
            outputStream.write(buf, 0, len);
        }
        outputStream.close();

        logger.info("获取文件(NlbsController.downloadFile)被调用结束,输出结果：" + result);

    }

    @RequestMapping(value = "/fileLoad/getUrlList",method = RequestMethod.POST)
    @ResponseBody
    public Map getUrlList(@RequestBody Map paramMap) throws Exception{
        logger.info("获取文件地址(NlbsController.getUrlList)被调用开始,入参：" + "" );

        Map result = null;

        try {
            CommonUtil.dealEmpty2Null(paramMap);
            result = applyService.getUrlList(paramMap);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        if( "null".equals(result.get(Fields.PARAM_MESSAGE_HEAD).toString())){
            result.remove(Fields.PARAM_MESSAGE_HEAD);
            result.put(Fields.PARAM_MESSAGE_HEAD, null);
        }
        logger.info("获取文件地址(NlbsController.getUrlList)被调用结束,输出结果："+ result);

        return result;

    }

}
