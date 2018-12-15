package com.vilio.bps.util;

/**
 * Created by xiezhilei on 2017/1/6.
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.*;

public class HttpRequestUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);    //日志记录

    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam, Map<String, String> headers){
        return httpPost(url, jsonParam, headers, false);
    }

    /**
     * post请求
     * @param url         url地址
     * @param param     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url, Map<String,String[]> param, Map<String, String> headers, boolean noNeedResponse){
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        try {
            url = URLDecoder.decode(url, "UTF-8");
            HttpPost method = new HttpPost(url);
            JSONObject jsonParam = JSONObject.fromObject(param);
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
                // 添加请求头
                if (headers != null) {
                    Set<String> keys = headers.keySet();
                    for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                        String key = (String) i.next();
                        method.addHeader(key, headers.get(key));
                    }
                }

            }
            HttpResponse result = httpClient.execute(method);
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String strResult = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    strResult = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    //城市接口个性化
                    if(strResult != null && strResult.contains("\\\"")){
                        strResult = strResult.replace("\\", "");
                    }
                    if(strResult != null && strResult.startsWith("\"") && strResult.endsWith("\"")){
                        strResult = strResult.substring(1, strResult.length() - 1);
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(strResult);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }


    /**
     *
     * @param url
     * @param param
     * @param files
     * @return
     */
    public static JSONObject httpPost(String url,Map<String,String[]> param,MultipartFile[] files){
        return httpPost(url, param,files, false);
    }

    /**
     * post请求
     * @param url         url地址
     * @param param     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    private static JSONObject httpPost(String url,Map<String,String[]> param,MultipartFile[] files,boolean noNeedResponse){
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            MultipartEntity reqEntity = new MultipartEntity();

            //处理参数
            for (Map.Entry<String,String[]> entry : param.entrySet()){
                String[] values = entry.getValue();
                for(String value : values){
                    reqEntity.addPart(entry.getKey(),new StringBody(value));
                }
            }

            //处理文件
            for (int i=0; i<files.length;i++) {
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                ContentBody fileBody = new InputStreamBody(file.getInputStream(),fileName);
                reqEntity.addPart("file", fileBody);
                reqEntity.addPart("file_name",new StringBody("fileName"));
            }

            method.setEntity(reqEntity);
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String strResult = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    strResult = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(strResult);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static Object httpGet(String url, Map<String, String> headers){
        //get请求返回结果
        Object jsonResult = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            url = URLDecoder.decode(url, "UTF-8");
            HttpGet request = new HttpGet(url);
            // 添加请求头
            if (null != headers) {
                Set<String> keys = headers.keySet();
                for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                    String key = (String) i.next();
                    request.addHeader(key, headers.get(key));
                }
            }

            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                //城市接口个性化
                if(strResult != null && strResult.contains("\\\"")){
                    strResult = strResult.replace("\\", "");
                }
                if(strResult != null && strResult.startsWith("\"") && strResult.endsWith("\"")){
                    strResult = strResult.substring(1, strResult.length() - 1);
                }
                /**把json字符串转换成json对象**/
//                jsonResult = JSONObject.fromObject(strResult);

                if(!StringUtils.isBlank(strResult)){
                    JSONTokener jsonTokener = new JSONTokener(strResult);
                    Object json = jsonTokener.nextValue();
                    if(json instanceof JSONObject){
                        jsonResult = JSONObject.fromObject(strResult);
                    } else if(json instanceof JSONArray){
                        jsonResult = JSONArray.fromObject(strResult);
                    }
                }

            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static InputStream httpGetFile(String url){

        InputStream in = null;

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                in = response.getEntity().getContent();
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return in;
    }

    public static void main(String[] args) {
        String str = "{\"user\":\"15601936981\",\"password\":\"L123123123\"}";
        JSONObject jsonParam = JSONObject.fromObject(str);
//        JSONObject result = httpPost("http://localhost:8080/bms/api/login", jsonParam, new ArrayList<Map<String, String>>());
//        System.out.println(result.toString());
    }

}
