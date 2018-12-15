package com.vilio.ppms.util;

import com.vilio.ppms.exception.ErrorException;
import com.vilio.ppms.glob.GlobParam;
import com.vilio.ppms.glob.ReturnCode;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 类名： FileUtil<br>
 * 功能：文件处理公共方法<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月6日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class);


    /**
     * base64上传文件到fms上
     *
     * @param base64string
     * @param fileName
     * @throws ErrorException
     */
    public static Map base64UploadFms(String base64string, String fileName) throws ErrorException {
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
            return uploadFms(stream, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ErrorException(ReturnCode.FILE_HANDLE_FAIL, "");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ErrorException(ReturnCode.FILE_HANDLE_FAIL, "");
                }
            }
        }
    }

    /**
     * base64流转文件(path前自动添加根路径)
     *
     * @param fileStr
     * @param fileName
     * @param path
     * @return
     * @throws ErrorException
     */
    public static void generateFile(String fileStr, String fileName, String path) throws ErrorException {
        byte[] b = fileStr.getBytes();
        Base64 base64 = new Base64();
        b = base64.decode(b);
        String basePach = FileUtil.class.getClassLoader().getResource("").getPath();
        path = basePach + File.separator + path;
        File file = new File(path);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            logger.info(path + "不存在,自动创建");
            file.mkdirs();
        }
        // 生成jpg图片
        String imgFilePath = path + File.separator + fileName;// 新生成的图片
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ErrorException(ReturnCode.FILE_HANDLE_FAIL, "");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ErrorException(ReturnCode.FILE_HANDLE_FAIL, "");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new ErrorException(ReturnCode.FILE_HANDLE_FAIL, "");
            }
        }
    }

    /**
     * 流的方式上传单个文件
     *
     * @param fileIn
     * @return
     */
    public static Map uploadFms(InputStream fileIn, String fileName) throws ErrorException {
        return uploadFms(fileIn, fileName, GlobParam.fmsPath);
    }

    /**
     * 流的方式上传文件到fms指定路径
     *
     * @param fileIn
     * @param savePath
     * @return
     * @throws ErrorException
     */
    public static Map uploadFms(InputStream fileIn, String fileName, String savePath) throws ErrorException {
        //定义传送参数
        Map textMap = new HashMap();
        textMap.put("filePath", savePath);
        //定义本地文件列表
        List<Map<String, Object>> listPath = new ArrayList<Map<String, Object>>();
        Map localFile = new HashMap();
        localFile.put("fileInputStream", fileIn);
        localFile.put("fileName", fileName);
        listPath.add(localFile);
        String resultStr = uploadFms(listPath, textMap);
        logger.info("接受fms返回信息：" + resultStr);
        Map resultMap = JsonUtil.jsonToMap(resultStr);
        Map resultBody = (Map) resultMap.get("body");
        String returnCode = resultBody.get("returnCode").toString();
        String returnMessage = resultBody.get("returnMessage").toString();
        if (!ReturnCode.SUCCESS_CODE.equals(returnCode)) {
            throw new ErrorException(returnCode, returnMessage);
        }
        List fileMaps = (List) resultBody.get("fileMaps");
        if (fileMaps.size() == 0) {
            throw new ErrorException(ReturnCode.FILE_UPLOAD_FAIL, "");
        }
        return (Map) fileMaps.get(0);
    }

    /**
     * 上传单个文件
     *
     * @param filePath
     * @return
     */
    public static Map uploadFms(String filePath) throws ErrorException {
        return uploadFms(filePath, GlobParam.fmsPath);
    }


    /**
     * 上传本地文件到fms指定路径
     *
     * @param filePath
     * @param savePath
     * @return
     * @throws ErrorException
     */
    public static Map uploadFms(String filePath, String savePath) throws ErrorException {
        logger.info("本地文件路径：" + filePath);
        logger.info("保存路径：" + savePath);
        //定义传送参数
        Map textMap = new HashMap();
        textMap.put("filePath", savePath);
        //定义本地文件列表
        List<Map<String, Object>> listPath = new ArrayList<Map<String, Object>>();
        Map localFile = new HashMap();
        localFile.put("localFilePath", filePath);
        listPath.add(localFile);
        String resultStr = uploadFms(listPath, textMap);
        logger.info("接受fms返回信息：" + resultStr);
        Map resultMap = JsonUtil.jsonToMap(resultStr);
        Map resultBody = (Map) resultMap.get("body");
        String returnCode = resultBody.get("returnCode").toString();
        String returnMessage = resultBody.get("returnMessage").toString();
        if (!ReturnCode.SUCCESS_CODE.equals(returnCode)) {
            throw new ErrorException(returnCode, returnMessage);
        }
        List fileMaps = (List) resultBody.get("fileMaps");
        if (fileMaps.size() == 0) {
            throw new ErrorException(ReturnCode.FILE_UPLOAD_FAIL, "");
        }
        return (Map) fileMaps.get(0);
    }


    /**
     * 上传多个文件到fms上，并且传送参数到fms上
     *
     * @param listPath
     * @return
     */
    public static String uploadFms(List<Map<String, Object>> listPath, Map textMap) throws ErrorException {
        for (int i = 0; i < listPath.size(); i++) {
            Map<String, Object> fileInfo = listPath.get(i);
            if (fileInfo.get("localFilePath") != null && !"".equals(fileInfo.get("localFilePath"))) {
                File file = new File(fileInfo.get("localFilePath").toString());
                try {
                    fileInfo.put("fileInputStream", new FileInputStream(file));
                    fileInfo.put("fileName", file.getName());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new ErrorException(ReturnCode.FILE_HANDLE_FAIL, e.getMessage());
                }
            }
        }
        return uploadFms(listPath, GlobParam.fmsUrl + "/fileLoad/uploadFile", textMap);
    }

    /**
     * 根据流上传文件到fms上
     *
     * @param listPath
     * @param urlPath
     * @return
     */
    public static String uploadFms(List<Map<String, Object>> listPath, String urlPath, Map textMap) throws ErrorException {
        //返回数据
        StringBuffer msg = new StringBuffer();
        HttpURLConnection conn = null;
        OutputStream out = null;
        try {
            String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            out = new DataOutputStream(conn.getOutputStream());
            //传输参数
            if (textMap != null) {
                Set<String> keys = textMap.keySet();
                for (String key : keys) {
                    out.write(("--" + BOUNDARY + "\r\n").getBytes());
                    out.write(("Content-Disposition: form-data; name=\"").getBytes());
                    out.write(key.getBytes());
                    out.write(("\"" + "\r\n").getBytes());
                    out.write("\r\n".getBytes());
                    out.write(textMap.get(key).toString().getBytes());
                    out.write("\r\n".getBytes());
                }
            }
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
            int leng = listPath.size();
            for (int i = 0; i < leng; i++) {
                DataInputStream in = null;
                try {
                    Map<String, Object> fileInfo = listPath.get(i);
                    String fileName = fileInfo.get("fileName").toString();
                    InputStream fin = (InputStream) fileInfo.get("fileInputStream");
                    StringBuilder sb = new StringBuilder();
                    sb.append("--");
                    sb.append(BOUNDARY);
                    sb.append("\r\n");
                    sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + fileName + "\"\r\n");
                    sb.append("Content-Type:application/octet-stream\r\n\r\n");
                    byte[] data = sb.toString().getBytes();
                    out.write(data);
                    in = new DataInputStream(fin);
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个
                } finally {
                    if (in != null) {
                        in.close();
                    }
                }
            }
            out.write(end_data);
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                msg.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorException(ReturnCode.FILE_UPLOAD_FAIL, "");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ErrorException(ReturnCode.FILE_UPLOAD_FAIL, "");
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return msg.toString();
    }

    /**
     * 上传网络文件
     *
     * @param fileName
     * @param urlPath
     * @return
     */
    public static String uploadFms(String fileName, String urlPath, byte[] bytesFile) {
        //返回数据
        StringBuffer msg = new StringBuffer();
        try {
            String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
            // 数据流部分  //
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + fileName + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] data = sb.toString().getBytes();
            out.write(data);
            //写入文件
            out.write(bytesFile, 0, bytesFile.length);
            out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个
            // 数据流部分  //
            out.write(end_data);
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                msg.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg.toString();
    }


    public static void main(String[] args) {
//        try {
//            System.out.println(base64UploadFms(str,"temp.jpg"));
//        } catch (ErrorException e) {
//            e.printStackTrace();
//        }

//        try {
//            System.out.println(uploadFms("C:\\Users\\dell\\Desktop\\测试报文.txt"));
//        } catch (ErrorException e) {
//            e.printStackTrace();
//        }

//        List<String> listPath = new ArrayList<String>();
//        listPath.add("E:\\软件安装包\\timg.jpg");
//        listPath.add("E:\\软件安装包\\timg.jpg");
//        try {
//            uploadFms(listPath, "/test/img/");
//        } catch (ErrorException e) {
//            e.printStackTrace();
//        }


        try {
            URL url = new URL("http://192.168.0.4:8081/fms/api/fileLoad" +
                    "/getFile?serialNo=d68f3f5d-9df0-11e7-8729-1866dae83f00");
            InputStream in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
