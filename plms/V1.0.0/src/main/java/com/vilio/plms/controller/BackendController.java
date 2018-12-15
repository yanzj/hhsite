package com.vilio.plms.controller;

import com.vilio.plms.dao.CommDao;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.PlmsFileModels;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.HttpUtil;
import com.vilio.plms.util.PlmsUtil;
import com.vilio.plms.util.SpringContextUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： CoreController<br>
 * 功能：页面调用<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */

@Controller
public class BackendController {
    private static final Logger logger = Logger.getLogger(CoreController.class);

    @Resource
    CommDao commDao;

    /**
     * 核心controller
     */
    @RequestMapping(value = "/backendapi",method = RequestMethod.POST)
    @ResponseBody
    public Map doRequest(@RequestBody Map paramMap) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        BufferedReader reader = null;
        try {
            Map<String, Object> params = paramMap;
            Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
            String transCode = String.valueOf(head.get(Fields.PARAM_FUNCTION_NO));
            //全部小写
            transCode = transCode.toLowerCase();
            //调用相应流程
            BaseService bService = (BaseService) SpringContextUtil.getBean(transCode);
            respMap = bService.doMain(params);
//            PlmsUtil.returnData(request, response, respMap);
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            logger.error("调用业务逻辑，交易码流程不存在！");
            Map<String, Object> head = new HashMap<String, Object>();
            Map<String, Object> body = new HashMap<String, Object>();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
//            PlmsUtil.returnData(request, response, respMap);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return respMap;

    }

    @RequestMapping(value = "/fileLoad/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFile(HttpServletResponse rsp, HttpServletRequest rqs, @RequestParam("file") MultipartFile[] files) throws Exception{
        Integer length = null == files ? -1 : files.length;
        String filePath = rqs.getParameter("filePath");
        logger.info("文件上传(BackendController.uploadFile)被调用开始,入参：：length = " + length + ",filePath = " + filePath);

        Map param = new HashMap();
        param.put("files",files);
        param.put("filePath",filePath);
        Map result = null;

        try {
            CommonUtil.dealEmpty2Null(param);
            result = uploadFile(param);

        }catch (Exception e){
            logger.error("系统异常：",e);
        }

        logger.info("文件上传(BackendController.uploadFile),输出结果：" + result);
        return result;
    }

    public Map uploadFile(Map paramMap) {
        Map map = new HashMap();
        MultipartFile[] files = (MultipartFile[])paramMap.get("files");
        String applyFilePath = (String) paramMap.get("filePath");
        String[] paths = {applyFilePath};
        map.put("filePath", paths);

        JSONObject result = null;
        try {
            String url = GlobParam.fmsUrl + "/fileLoad/uploadFile";
            result = HttpUtil.httpPost(url, map, files,false);
        }catch (Exception e){
            logger.error("上传文件异常：",e);
        }
        return result;
    }

    @RequestMapping(value = "/fileLoad/getFile",method = RequestMethod.GET)
    public void getFile(HttpServletResponse rsp, HttpServletRequest rqs) throws Exception{
        logger.info("获取文件(BackendController.getFile)被调用开始,入参：" + "" );

        String serialNo = rqs.getParameter("serialNo");
        Map result = null;

        Map param = new HashMap();
        param.put("serialNo",serialNo);

        try {
            CommonUtil.dealEmpty2Null(param);
            result = getFile(param, rsp);
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

        logger.info("获取文件(BackendController.getFile)被调用结束,输出结果：" + result);

    }

    @RequestMapping(value = "/fileLoad/getModelFile",method = RequestMethod.GET)
    public void getModelFile(HttpServletResponse rsp, HttpServletRequest rqs) throws Exception{
        String type = rqs.getParameter("type");
        logger.info("获取文件(BackendController.getModelFile)被调用开始，模板类型为：" + type );

        PlmsFileModels fileModel = commDao.getFileModelByType(type);
        if(fileModel == null){
            logger.error("模板文件不存在！");
            Map<String, Object> respMap = new HashMap<String, Object>();
            Map<String, Object> head = new HashMap<String, Object>();
            Map<String, Object> body = new HashMap<String, Object>();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SYSTEM_EXCEPTION));
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
            rsp.setContentType("application/json; charset=utf-8");
            PlmsUtil.returnData(rqs, rsp, respMap);
        }
        String serialNo = fileModel.getFileCode();
        Map result = null;
        Map param = new HashMap();
        param.put("serialNo",serialNo);

        try {
            CommonUtil.dealEmpty2Null(param);
            result = getFile(param, rsp);
        }catch (Exception e){
            logger.error("系统异常：",e);
        }


//        Map body = (Map) result.get("body");
//        String fileName = (String) body.get("fileName");
//        rsp.setContentType("application/x-msdownload");
//        String iso_filename = new String(fileName.getBytes("GBK"), "ISO-8859-1");
//        rsp.setHeader("Content-Disposition",
//                "attachment;filename=" + iso_filename);

        OutputStream outputStream = rsp.getOutputStream();
        InputStream in = (InputStream)result.get("is");
        int len=0;
        byte[]buf=new byte[1024];

        while((len=in.read(buf,0,1024))!=-1){
            outputStream.write(buf, 0, len);
        }
        outputStream.close();

        logger.info("获取文件(BackendController.getModelFile)被调用结束,输出结果：" + result);

    }

    public Map getFile(Map map, HttpServletResponse rsp) throws Exception{
        Map result = new HashMap();
        String serialNo = (String)map.get("serialNo");

        //JSONObject response = null;
        InputStream in = null;
        try {
            String url = GlobParam.fmsUrl + "/fileLoad/getFile" + "?serialNo=" + serialNo;
            in = HttpUtil.httpGetFileWithResponse(url, rsp);
            result.put("is", in);
        }catch (Exception e){
            logger.error("下载文件异常：",e);
        }

        return result;
    }

}
