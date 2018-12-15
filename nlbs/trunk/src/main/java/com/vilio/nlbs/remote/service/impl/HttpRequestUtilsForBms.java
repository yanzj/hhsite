package com.vilio.nlbs.remote.service.impl;

/**
 * Created by xiezhilei on 2017/1/6.
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpRequestUtilsForBms {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtilsForBms.class);    //日志记录

    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam){
        return httpPost(url, jsonParam, false);
    }

    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");

                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200 || result.getStatusLine().getStatusCode() == 406) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
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
     * @param url
     * @param param
     * @return
     */
    public static JSONObject httpPut(String url, Map<String, String[]> param) {
        return httpPut(url, param, null, false);
    }

    /**
     * post请求
     * @param url         url地址
     * @param param     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url,Map<String,String[]> param,MultipartFile[] files,boolean noNeedResponse){
        Charset chars = Charset.forName("UTF-8");
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            HttpMultipartMode mode;
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,
                    null, chars);
            //处理参数
            if(null != param){
                for (Map.Entry<String,String[]> entry : param.entrySet()){
                    if(null != entry){
                        String[] values = entry.getValue();
                        for(String value : values){
                            if(null != value){
                                reqEntity.addPart(entry.getKey(),new StringBody(value,chars));
                            }
                        }
                    }
                }
            }

            //处理文件
            for (int i=0; i<files.length;i++) {
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                ContentBody fileBody = new InputStreamBody(file.getInputStream(),ContentType.create("application/octet-stream",chars),fileName);
                reqEntity.addPart("file", fileBody);
                reqEntity.addPart("fileName",new StringBody(fileName,chars));

                method.setEntity(reqEntity);
            }

            url = URLDecoder.decode(url, "UTF-8");
            method.setEntity(reqEntity);
            HttpResponse result = httpClient.execute(method);

            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200 || result.getStatusLine().getStatusCode() == 406) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
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
    public static Map<String,Object> httpGet(String url){
        //get请求返回结果
        Map<String,Object> jsonMap = null;
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK || response.getStatusLine().getStatusCode() == 406) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
//                jsonResult = JSONObject.fromObject(strResult);
                jsonMap = new Gson().fromJson(strResult,new TypeToken<Map<String,Object>>(){}.getType());
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        } finally {
            client.close();
        }
        return jsonMap;
    }

    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static InputStream httpGetFile(String url){

        InputStream in = null;

        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK || response.getStatusLine().getStatusCode() == 406) {
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

    /**
     * HttpClient PUT请求
     * @author huang
     * @date 2013-4-10
     * @return
     */
    public static JSONObject httpPut(String url, Map<String,String[]> param, Map<String, String> headers, boolean noNeedResponse){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        try {
            url = URLDecoder.decode(url, "UTF-8");
            HttpPut method = new HttpPut(url);
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
            if (result.getStatusLine().getStatusCode() == 200 || result.getStatusLine().getStatusCode() == 406) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                    logger.error("put请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("put请求提交失败:" + url, e);
        }
        return jsonResult;
    }


    public static void main(String[] args) {
        String str = "{\"user\":\"15601936981\",\"password\":\"L123123123\"}";
        JSONObject jsonParam = JSONObject.fromObject(str);
        JSONObject result = httpPost("http://localhost:8080/nlbs/api/login",jsonParam);
        System.out.println(result.toString());
    }

}