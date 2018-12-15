package com.vilio.pcfs.util;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 类名： HttpUtil<br>
 * 功能：发送http方法<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月8日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：可以根据不同需求，新增此方法<br>
 */
@SuppressWarnings({"rawtypes", "unchecked", "deprecation", "resource"})
public class HttpUtil {

    protected static Logger log = Logger.getLogger(HttpUtil.class.getName());
    static int REQUEST_TIMEOUT = 60000;
    static int SO_TIMEOUT = 60000;
    static String CHAR_SET = "utf-8";
    static String METHOD_GET = "GET";
    static String METHOD_POST = "POST";

    /**
     * httppost请求提交参数
     *
     * @param url    地址
     * @param params 所提交的参数
     * @return
     * @throws ErrorException
     */
    public static String sendHttp(String url, Map<String, Object> params) throws ErrorException {
        return sendHttp(url, params, REQUEST_TIMEOUT, SO_TIMEOUT);
    }

    /**
     * httppost请求提交参数
     *
     * @param url             地址
     * @param params          所提交的参数
     * @param REQUEST_TIMEOUT
     * @param SO_TIMEOUT
     * @return
     * @throws ErrorException
     */
    private static String sendHttp(String url, Map<String, Object> params, int REQUEST_TIMEOUT, int SO_TIMEOUT)
            throws ErrorException {
        HttpClient httpclient = null;
        HttpResponse response = null;
        String result = "";
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (String key : params.keySet()) {
                    pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
            }

            BasicHttpParams httpParams = new BasicHttpParams();
            // 设置请求超时
            HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
            // 设置读取超时
            HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
            httpclient = new DefaultHttpClient(httpParams);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, "utf-8"));
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new ErrorException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity resentity = response.getEntity();
            result = EntityUtils.toString(resentity, "UTF-8");
            return result;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络连接超时" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络连接超时!");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络出现异常" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络出现异常!");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络出现异常" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络出现异常!");
        } catch (ErrorException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络出现异常!");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("HttpClientUtils：未知异常" + e.getMessage());
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    /**
     * @param url     发送的地址
     * @param content 发送的内容
     * @throws ErrorException
     */
    public static String sendHttp(String url, String content) throws ErrorException {
        log.info("发送地址："+url);
        log.info("发送内容："+url);
        return sendHttp(url, content, REQUEST_TIMEOUT, SO_TIMEOUT);
    }

    /**
     * http方法公用
     *
     * @param url     发送的地址
     * @param content 发送的内容
     * @return
     * @throws ErrorException
     */
    private static String sendHttp(String url, String content, int REQUEST_TIMEOUT, int SO_TIMEOUT) throws ErrorException {

        HttpClient httpclient = null;
        HttpResponse response = null;
        String result = "";
        try {
            BasicHttpParams httpParams = new BasicHttpParams();
            // 设置请求超时
            HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
            // 设置读取超时
            HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
            httpclient = new DefaultHttpClient(httpParams);
            HttpPost httpPost = new HttpPost(url);
            StringEntity strEntity = new StringEntity(content, "utf-8");
            strEntity.setContentEncoding("UTF-8");
            strEntity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(strEntity);
            response = httpclient.execute(httpPost);
            HttpEntity resentity = response.getEntity();
            result = EntityUtils.toString(resentity, "UTF-8");
            return result;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络连接超时" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络连接超时!");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络出现异常" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络出现异常!");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络出现异常" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络出现异常!");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("HttpClientUtils：未知异常" + e.getMessage());
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    /**
     * @param url     发送的地址
     * @param params 发送的参数
     * @throws ErrorException
     */
    public static String sendHttps(String url, Map<String, Object> params, String method) throws ErrorException {
        return sendHttps(url, params, method, REQUEST_TIMEOUT, SO_TIMEOUT, CHAR_SET);
    }

    /**
     * 发送https求情
     *
     * @param url
     * @param params
     * @param method
     * @return
     * @throws ErrorException
     */
    public static String sendHttps(String url, Map<String, Object> params, String method, int REQUEST_TIMEOUT, int SO_TIMEOUT,
                            String CHAR_SET) throws ErrorException {
        String responseBody = null;
        HttpClient httpclient = null;
        try {
            BasicHttpParams httpParams = new BasicHttpParams();
            // 设置请求超时
            HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
            // 设置读取超时
            HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
            httpclient = new DefaultHttpClient(httpParams);
            SSLContext ctx = SSLContext.getInstance("SSL");
            // Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ClientConnectionManager ccm = httpclient.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
            // 判断请求时get还是post请求
            if (METHOD_GET.equals(method)) {
                HttpGet httpget = null;
                try {
                    httpget = new HttpGet(url);
                    // 判断有没有请求参数
                    if (params != null && params.size() != 0) {
                        HttpParams sendParams = httpclient.getParams();
                        for (Entry<String, Object> entry : params.entrySet()) {
                            sendParams.setParameter(entry.getKey(), entry.getValue());
                        }
                        httpget.setParams(sendParams);
                    }
                    // 接受返回信息
                    HttpResponse response = httpclient.execute(httpget);
                    HttpEntity entity = response.getEntity();
                    responseBody = EntityUtils.toString(entity, "UTF-8").trim();
                } finally {
                    if (httpget != null) {
                        httpget.abort();
                    }
                }
                // 接受返回信息
                // ResponseHandler responseHandler = new BasicResponseHandler();
                // responseBody = httpclient.execute(httpget,
                // responseHandler).toString();
            } else {
                // post请求
                HttpPost httpPost = null;
                try {
                    httpPost = new HttpPost(url);
                    // 判断有没有请求参数
                    if (params != null && params.size() != 0) {
                        // 设置参数
                        List<NameValuePair> list = new ArrayList<NameValuePair>();
                        for (Entry<String, Object> entry : params.entrySet()) {
                            list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                        }
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, CHAR_SET);
                        httpPost.setEntity(entity);
                    }
                    HttpResponse response = httpclient.execute(httpPost);
                    if (response != null) {
                        HttpEntity resEntity = response.getEntity();
                        if (resEntity != null) {
                            // 接受返回值
                            responseBody = EntityUtils.toString(resEntity, CHAR_SET);
                        }
                    }
                } finally {
                    if (httpPost != null) {
                        httpPost.abort();
                    }
                }

            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络连接超时" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络连接超时!");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络出现异常" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络出现异常!");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClientUtils：网络出现异常" + e.getMessage());
            throw new ErrorException(ReturnCode.TIME_OUT, "网络出现异常!");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("HttpClientUtils：未知异常" + e.getMessage());
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION, "");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return responseBody;
    }


    public static void main(String[] args) {
        HttpUtil hu = new HttpUtil();
        try {
            String i = hu.sendHttp("http://localhost:8080/mobileCore.m", "{\"head\":{\"functionNo\":\"100001\"},\"body\":{\"userName\":\"wangxf\",\"password\":\"1234567\"}}");
            System.out.println(i);
        } catch (ErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
