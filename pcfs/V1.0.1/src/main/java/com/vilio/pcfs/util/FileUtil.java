package com.vilio.pcfs.util;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
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
     * base64上传文件到fms上（批量上传文件）
     *
     * @param base64List  {"fileName":"","base64str":""}
     * @throws ErrorException
     */
    public static List base64BatchUploadFms(List<Map<String, Object>> base64List) throws ErrorException {
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            for (Map<String, Object> base64Map:base64List) {
                base64Map.put("fileInputStream",new ByteArrayInputStream(decoder.decodeBuffer(base64Map.get("base64str").toString())));
            }
            return uploadFms(base64List);
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


    public static List uploadFms(List<Map<String, Object>> base64List) throws ErrorException {
        //定义传送参数
        Map textMap = new HashMap();
        textMap.put("filePath", GlobParam.fmsPath);
        String resultStr = uploadFms(base64List, textMap);
        logger.info("接受fms返回信息：" + resultStr);
        Map resultMap = JsonUtil.jsonToMap(resultStr);
        Map resultBody = (Map) resultMap.get("body");
        String returnCode = resultBody.get("returnCode").toString();
        String returnMessage = resultBody.get("returnMessage").toString();
        if (!"0000".equals(returnCode)) {
            throw new ErrorException(returnCode, returnMessage);
        }
        List fileMaps = (List) resultBody.get("fileMaps");
        if (fileMaps.size() == 0) {
            throw new ErrorException(ReturnCode.FILE_UPLOAD_FAIL, "");
        }
        return fileMaps;
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
        if (!"0000".equals(returnCode)) {
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
        if (!"0000".equals(returnCode)) {
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
        return uploadFms(listPath, GlobParam.fmsUrl, textMap);
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

        String str = "/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAA4KCwwLCQ4MCwwQDw4RFSMXFRMTFSsfIRojMy02NTI" +
                "tMTA4P1FFODxNPTAxRmBHTVRWW1xbN0RjamNYalFZW1f/2wBDAQ8QEBUSFSkXFylXOjE6V1dXV1dXV1d" +
                "XV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1dXV1f/wAARCAJiA5MDASIAAhEBAxE" +
                "B/8QAGwAAAgMBAQEAAAAAAAAAAAAAAAECAwQFBgf/xAA7EAACAgEDAwIEBAUEAQQCAwAAAQIRAwQhMRJ" +
                "BUQVhE3GBkSIyobEUQsHR8AYjUuEzFSRy8TRDYnOy/8QAGQEBAQEBAQEAAAAAAAAAAAAAAAECAwQF/8Q" +
                "AJxEBAQEBAAIDAAICAwEAAwAAAAERAiExAxJBBFETIjJhcRQFM8H/2gAMAwEAAhEDEQA/APT6ifVSXfY" +
                "t0unqNtcmfRJ5pqUlt2OsoqKpGZPDd/6c/M1jbXYhCXU9mS1quS9yOng9rOXXisNWNNLcsbSRWpKKohK" +
                "d8MzZqwpy2ZkzZHGLdk8mSrRz9XmTi1e514mRnu5GfUapxtJttsnpIzyJSl33Ry1KWTI1u99juempxil" +
                "Nbo9M8R4+P9uvLoafDSVoueNJXRZBxrZFjpoy9Unhil4rkp6aZrnBW2iEYNvcW+Gc2q1jTe5KONXSRZX" +
                "S6LccVdnP9dpPCEcaT3RfHGm1sSUETiqZ05qVJYU1wRlh24NGN7FlJoXqyjk5tMpJ7U+zMD6sUqktuzP" +
                "QzxJrgxajTKSaaGS+llxihJNck3JNcmacJ4JNO3Hz4Gsl8MzfHtrNGSXS015N2my7Lc5mWVs1aSXUl5R" +
                "JfJY7GOVouTtGXDdI0x2KwkIGwIEwBiKBiY2JlEZboommzQ1ZBxss8DOouy2MWSUSaiW0R6SEo1ZdRGU" +
                "bskoxZnSZkyZumS2Z0MmO07MuTSqTTo682fqXWdahW1bJRzprZ2Wx0kU26JrTJKki2xJqCm21RZG3dsf" +
                "wWqD4bTZjYqSSosikU9LSLYJpEqpOimUku5ZJverOfqZzUlSdX4LJqW41davkfUjnrNNPdD/iJJO0a+p" +
                "roda8gmmc96lppNPcuw51NtUyWWGtiVicPYnDdFjja4J6VllErcaZqlD2KpRpllRowSSSNcWmjmwn0vc" +
                "vjnSXJnqaRpyNUzDmSbLJ5k1yZMuZNvcczCh0gi12M8syXcePKm+TY2RJophK6LUwJoaEhmRFqyLgn2L" +
                "VwA0QUEhqKRICASSAAAkAhhQILEESCyIBUrCyIASsBAEADCgFQUOgAVDoBgKhgAAAAAAJtB1JAMCLmhO" +
                "YxU7E2ip5Pci8nuMRc5JCc0jO8nuReTncoveREXk9zO8j7EXJsmxcXvJ7kXk9yl2+4dN8saYm8vuQeRv" +
                "gOlIKQ1cQbkxU3yywTJtVDpCkSYAKqHaBkWwh2AgKo0ElGCN7naOXgTxvofZm+LTijktVZ4ufzRLDBpF" +
                "qjbouWNRjxsYvO3WLWTJF0VO1FtmrJSb8FEmpJpI19MjO+WHPO1+Hk5GolJtppr5naljtvbZFGfTwyR3" +
                "VNcM5zuS5V74t52e3L0uJLInJWm+Tt4VGLTXBzscVG0+xphkbVJ8Ho/6eXi47GKSaRbaaOTjzOLVs2wy" +
                "dSTszXo5sq9xTZFxpbDUqRGU01RNbkQk1aLISrkoak3fYabXzFjTape5Jbsz45N7F65NcsX204+C1FMH" +
                "sWJks8qkV5IponZFu9hPAwajCpJ2jkZIPFN1x2O7m4ZzNRBO9jPXTfNYZys06CdTafcy5E4uqJ6eTjNM" +
                "SrXocT2RensY8GRSitzQpbGmKtsLKrY1IIsER6h2NDELqCxoYUJNErRQqDgbYiaHyDViToGyiLgmQeNE" +
                "26RFt2NEVBEugE9ySdk0VuPsQcX2Re3XYipK6ZqChwk1umShCuxeqaGkqFopcUZ8mNSe6NrSshKMW+EW" +
                "UYfgRb4IvTJp7G7oQOCov2Mc2WlTadEsWBRbdG5xQKKsfYwscaSVFzdIgmkiE8iXLJuqc5pGbJNb7kM2" +
                "ZLh2ZJ5nLZJmpZGV8siT5IvMkuTFOU22Vtze9suyjZPVJLkyz1LeyRS9+WyzHjtW1sNhlqEpylvbRdpZ" +
                "Sb3ZDLBpNpcE9Kr3EulmOjhfBpTM+JcF67GqixDEhmFMYkADAEBAAAAFhYAFAAAQAAAA0AAAwABgArQD" +
                "Ag5JA5oCQWVOZF5C4L7SF1JFDn7kXk9yDQ5og8hQ5+5BzbL4Gh5Pci5lNthTJq4seT3IubFQqSGgcmwt" +
                "sewDVRp92FIbCyBUg2CxNgFhYyIDsTAVhQ2AmDALENk8WGWR7J15CIKMpukmy6Glct2bsOnUYpJF6xpE" +
                "2DEtKqWwG/oQE1HndUujL1LjuX4MlpWy7UYVJPY5ym8U3Dw9h1M8tS7MdjDV2WyezMemy9UUaHJtbCM2" +
                "M2aW7RXF1b7lmTG22/JCMOEuRaknnVbSaZkzzUU09jpLFTtmfVadTSdboxPi261134yOLGMpNttq3Zfi" +
                "xyg7f3N+PTql1RtBPFGK24OvUyeHnnGeVKha3RpjcUmQSSVklLajltduOZq6M21SRbixt7tEdNib3fc6" +
                "GPEqWxqT9rVqhYduAeFeDb0pLgg0jW6jIsXSyxKi3pTIuIkxE4cFpVF0Wppol9qTIPYmyD4ApyO0YM63" +
                "aNuV0ZJq2zl21HPyxvkhHZo15IcmOX4ZNE5rc8ujpcvSkmzfCSa5OHCb23N+nzcJs6S6zY6KdoZCMrSJ" +
                "rcYwLoi5USaKprYl3BLrBTKbp0SqzM0WKa5smsiM0m0ip5el7s3JaluNzmhdasyxy2rG53wy2WLPLXF2" +
                "SKcU10ot61QkFU5NcFTyu1uqJZJrfczylFtK0dJzo1Rnfgl8RLsZIy/Fs9i9NNc0Y65sJ5TeVd7BTi3y" +
                "VOKfcFCnszEtWxpTVckrVGdWu5ZFto1piT9hVuFkG2nsy6ht0yE59KbCbdGXNKVNJEtJ5Sea3SLIzbME" +
                "ZSU2mjVCXAktaskaOppFWSn7sndoGlQ9Ixyx27ZBwS7GyUVXBnkkmjNt1ZFLxJvgFpU+xograLlFUa3I" +
                "ljnZNKkrSJrD0xW3Y1TWzIyeypdiS6TwyzxJrgz6RJbe5sm6MulVX8zrwXy341SLlyVY+CxcnSsrUMih" +
                "mQx8IQm6RBKx3sVOVdwUnQwW2CK1Jkk7IJAAWAxA2hOSQDAg5oTn7lwWWFpFLye5FzGDQ5Ii50UOfuJz" +
                "HgXvIQeT3KXJsVtjVWuZFz9yFPyFE0xJzbE5NiSQxphbsKGFk1SpDpBYrAYCsLAdhYrCwALCxWAAKwsB" +
                "iYNoi5AMCLkLqCpOhWRcvcg5gW2hOSK+oi5jEXRacknwdPA0opI4qm000a8OrUeXwXPA7KaBzS7nLl6g" +
                "kvwp/Uzy105cNJGcR2/iryBwf4uf/ADYDFx180Uk9jgatP+I28noc+6ZxNXj/ABuS7IWbCXyv0qaSR0I" +
                "r8KMGk/KjoQ4JIVCUEkVRjTs0ST38FbSsuIlKKaKJJU7+iLJTai65M7ltuzcSxLZKtjDnyKMmm+C5zcb" +
                "d7HOzKc8jfZvgX0x1V8J2qNGKPVJXwZMMGqTOjhhSTOOeXTj014YpJGuMkkZIy6UDzbbG81WqWVLuVPM" +
                "r5Ms5yfBU5ST3suRnXRU00NNMyQm6LoysuGrU9yaZCCTLUjNUVZCSotSIySozKMuVWmzG3u0bci2Zikq" +
                "mZ7mrKhNbcGPPBNWuUdNQTSdFWbCnF7Cc1ZXNxvdGuFxaZjknjyNP6GmEk0hPDd8ulhnaW5epGDDKmlZ" +
                "pUzbnYvclRCUl5K5ToqlkLiLLuSL4pUc9ZPx7s1wyKuTMi2YnlSp0cvO6ndm/NmSTdnF1WdOTpnTmZWb" +
                "6bY5V0rcksyXLOQtSo8soza+k0nRuyU56z29Dj1UfJY9VFLlHkV6g066mi5a9NbysTmJe5a9DPVRbaTR" +
                "Wsibuzzs9W7uL3K/4/UJ0p/obkxn7PUKaTTsvjmXk8kvUdQu6ZJeq51yk/qS86TuR655lWzK3mp7M8wv" +
                "Wcq5jf1JL1mSe8H9yThb8kepjltcl0cqrk8pH1uK5i0Wx9cx97X0M/RfvHp3lXki8yvlHnP8A1vE1XU1" +
                "9Aj6tib3mPofeV6L4ifcqyTSTORD1XDX/AJF9wl6jikrU0/qPpUljbKa6rZZHKl3ORLWwb2mvuOOthW8" +
                "l9zX1avcdtZlXJJZkzirWx7SX3JR1sU+TN48n2jrvImUzkm0zC9XFr8y+5CWrTfJPo1OpHShNXyXKSrZ" +
                "nJjqlfJfDVKt2S8eCdSt+ze4SqttjNHURe6aZKWaKTbaS92YnOLahnaq1yUadVfzIZ9RBppST8i001J7" +
                "OzrJjNro4+CxclWN7Fie5pFiJEUSRmhoTVoYEFbi/AJNFtIKXguipImtidIi0QJzaIOfuNpkHFsAc/cT" +
                "m/IdDE4MapORFyZPoF00TTEbbCmx8BY0Kh0gCyKNgFYWAAFisB2FkbCwJWFkbBsCVisVkeoCdibIOSE5" +
                "gWWDkipz9yLmMVa5ITl7lTmLqGJq5yIufuVOQrZcNWuZFzIWxMYibmJyIioCTkRbYAAWxWAAJgMAFbI0" +
                "SoCiNASoAPR5mqZy9TFOMn7G/NNKL3ObqMlRa8kk8CeiT6VZ0cas52kkqR08VJGbVpyWxnmqbNEmkmZ5" +
                "u5Mk8iuT2Zkyt7s1vgzyVujrGapjFye43h3svhBLsTlFJWSpjNDHTVo0xpJEElYOVHKtT0tcuxOEersZ" +
                "4u5I24qtGp6WD4SrghPCmm63NezRFpU0NRijHy9yyKadE3FJgnujUF0OEWp7FUUWJbGaHZCT2JtWQlF1" +
                "ZmDPkezMcn+M2ZVszBOVZEmak1GzErQThaewYWqROclTNK4evXTkXu2Qwz9y/1FKUW+63RzMWRp0cevF" +
                "dZdjr457pWa4y2TOZhndM245XEsqWLJzpM5+bVRg2m0vqaczfS6PN+rdcJ9abrhm55Y6uTXQlr4p31fY" +
                "F6soruec+M2CnOT2s3JI53u13M3qs5ppNJGKWrcm3dsywwzm1szVj0U3Vl9M7aqlmlJOilqc33Z1ceg4" +
                "tWaseiiv5V9hq/WuHi0s5Pj7m3F6dJrc7GLSpPg2Q06rgsq/WR59+myW6f6EX6dNO1+x6j+HVcEXp14N" +
                "/Y+seWloMngi9Dkrg9S9MvBF6VeBsT6PLPRZf+JB6TIv5WerelXgi9KvA2J9HlHpci/lZF4JrmD+x6p6" +
                "ReERejj/xX2LsPo8s8M1/K/sReN90z1D0Uf8AiReig/5V9hqfR5dwfgHBnpHoIv8AlX2Iv06D/kX2Lqf" +
                "SvOdLXkEpLuzvv06D/lX2IP02D/lHg+tcROa7v7jU5riT+52H6bHxQn6Yu37g+tcj4mVfzMmpZ6TU+e1" +
                "nRfpvzIP05p7NoGVgllzwq5NJq14ZKGrypU5m16KapWmqSaatMqn6c3vG17cohl/FP8dmim1Mg/UM0pb" +
                "u18ycvTslPcploskN2uCWYnlc9XNrdnV9MzOcE297PPOMk2qdo7PoykobruJG5fOPQ4XaRoXJmw8I0rk" +
                "zW1iJIhEmiKaBAhpGQwALRFACtC6giTSYnFEXJicgG0hOhWJsKTZBsk2Re5UQbYrJNNkGqLhppjsrboT" +
                "lsLDU3KiLmiEpFM50ZxdaOv3BSRj+JuWKdomK0OSE5FPUJyZcFzn7ic/cpthbGIscwcyqwsYam5icmRA" +
                "CVsVtiAAGAUAAFDAKCgoAFQUMKAVCJMAItCJUKgFQDoKKE0KiVCAQDoAOhmyttpGPMnJGrp33Izh+Fui" +
                "CnTT6ZJHVwybSs48YtZVR18C/CjF9tWeFk7aKWi9rYqls2ajKDWxS4/iL2iDW5pKUVTCfCBbMJNbE0kV" +
                "kWrJNq6It0jnbta9RBNxkvBrxZOLZnjFyaNEYbLY3PSRpjO1yTTbRmi2nRqxRbVkoqnF2QVpqzY4JqzP" +
                "OFMsothwixIhBbIsRmgoGthg+DIyZlSZyNRPoynYz8M4OvvrdeDtz6StOLVRSW6LZ6qHS22eXzZs2OTU" +
                "G6LMUs2RJybZb4SXzjdrdR1pqLswwlT3LlBq7RnknGR5urtd+fEb8MnaOjhlaRydPNNo6GKdbCFapq0c" +
                "7V6eORNNJ2blK1uVTSbs3GbHAl6bFStLYuxaKKrY6bim+CyONI6SudkjJi0sV2NUMCXYujFIsSQFccKX" +
                "YmsaRNDCiMEnwaMaVFK5NGNF/EWJKuBOK8ElwBFR6V4FS8EgouiDivAnBEwGog4LwReNeC0KLpil40J4" +
                "14LqFQ0UvEvAnhXgvoVF2ih4V4IvCvBpoKG0xleFeCLwLwa6FXsXamMrwquCDwLwbaXf9hNJ7UNMYnp1" +
                "4E9OvBtaS5QkuXS+xdpjBLTxapxS90Zc2mVNUjsOKrjsZc0VT25LLqY4GTSpS2Rs0GFQVJUWzgnLgvwQ" +
                "SV0a/Ezy04lRoVIpjsTb3RitRcmqGpIrTCzOKt6g6itMkrJglYrBJ+BqLIFYWSUR0gIbsKZZQUQV0w6S" +
                "yiLQEaQmkTaE0UVtFc0XNIrnRYM8tiDexZJbMolKrNaht2U5EHXTFJ2ZqxVW5bHghW5ZFbGQ0A6GFRCi" +
                "VCAVDoBgIBgAUFAFAFAA6KENCGABQAQAUHAnJIB0KiDyJdxPKvJRYIqeZVyQeZLuDV9oG1fJleoS7lct" +
                "VFb2gmtrkvInNLuc+Wtgv5imWvgv5gfaOr8RAcb/1CPkAmx6tK2TkriCg1Jpok1aMWtyMM105EdDT5Pw" +
                "pGPJD8TZLDPpaTZI1+OldorlzYoztIk9yxlWRap2WNJFcmkatTFcpUVymEnbIdNmfNWeEXOnbIyyWx5I" +
                "OnSMzU01+F/Y1OGerXU0yUqZsUNjJok+lNpo6EUS+Gp6V/D3W25pikkhKKJmLdQmUZkaCnKrE9ggtkWI" +
                "jBbIkhQwfAAQZsytM5WowuUm6O1KFmfJhV8HSUcDLpE27QQ06iqSOtlxJJ7FDiki27Exz8uGldGDNGm9" +
                "jtZY2mcrURqzj1HSVmwz6ZpNm/DkuSpnGlJqe3k6Olk5NMzG3UTtFc5NKiyPCIzjaOkjFquLbZdF7FUY" +
                "Fl0dpHO1ZFliKossiyUiaHZGwsCcXuacZli9zTjKLlwMS4GZUgoYAQYDYmAAAFQCGIAF3GVzywgm5SSS" +
                "5bZYJkHK3S+5kya6HZN+E9rK5a1pJtRSfEe7+e4MbrXdshLNCFty48nPnqozdObtdk0Q+PibpJza5d7I" +
                "LjofGU3SdXw62Jxgmm923XL9zBDUYkt5Unwrv7Fq1UIxaTtLvy19gY2NKO657oE0032ryZ8mqbx9Sg6T" +
                "u01ZkjrJyck4SinVJpr5c/MamOnJrpbrdVTMuoWzogtVGUuhpquW2tiUpOaXS0/ZOyyyGMjT6t0zRhWx" +
                "W4tStptF8Kt0qXzN74ZxNLcnW4o0t3+hNLuZqmoskojS9iSTIpKKJJAkSSRNCSJBsFmQUFBYWwCgpIW4" +
                "BTbSE2hMQA5EXIbTfCIyTQEXJlUpbcjnJmebe+5qIJz9zJlnTdE5t7lGRWrKIObbLYu0iity3GtqJSVY" +
                "kTS2ElsToigAQEAAAUAABAAAFAADAAAAAEAEAADAi3SM2bJ0p2zRPhmDVrZlSs+bWxhdsyy9SiuGc7Wy" +
                "ab3Zz3N3yyXrK53qu5L1PwymXqUnwzj9TurZJNsz96ztrpPXzd7lU9XNrlmSNt8EpLYbaW0S1U2+WQee" +
                "bfJVJOwS3M21FvxJeQIUgJtXH1xJNWE4/htEcclKKafYsk/wtFnt6ayZImeSado1yV2Zska3OmeElXYJ" +
                "2kmzZGqObhlTo3QlaOdWnJ0UZHuXtWilxtmpN9p6Uu7oEico77Ak74NSYmoSTrghXGxqpNcEXFLsXRdp" +
                "2kkjZEx4Wk0jXF7GKq5DIKSrcaaZjESITVkm0iDabLARVJEhLgdoUMBWhOSXcmCVEJpVuDyJdyrJmVcl" +
                "kooz1TMrWxdlnfcpa2OgqyLZnL1StM601szl6rlo5dN8uNJN5GuyZ0NGkqMjSU2adNKmjEbdeD2RJxso" +
                "wytmlbnXljpW40iLdMtktip8naOdSi9y2LKYlsWSiywshY0wJw5NWMyQ5NeMUi9cAC4AyoAAATIskyLA" +
                "AARUBFtJWDdHnvX/X46NvT6dqWZrdrfp9vn+w3PZbntr9T9ZwaJuDkp5P+CfHz8Hns3rE80lPLkbp2kv" +
                "wpe1LdnJ6pybz55ttp9SbttvsVKezyZFSr8MSWud7/p1J6+dJprGnwk3dElqptXJquXff3Zy4Sc8jyTe" +
                "yVpPuGbUTlFRhs3xv+pZZIz966ktfKc1BLbjjfb9ixatRiot21vtwjiRn8NOnu9m7+9k4ZmmrlbbLOof" +
                "eu5j1Uo7zdt8RT2S9zfi1kYwScmm96R59ZFBJptvsvL8l2HNSeSTbSW/9fqaanT1GPVJ4t1V8J/1Zc4Y" +
                "tQk8juDu23drweejq3KKcm6SVr38fsvuasOu+JNLaKjs1eyvcY1Olut0T/ib0zhixqNpzTfU6bpVwqVG" +
                "XRayUsqxSjJzTa3i3ut2r8HUyyWaEJ0mnbW7TTS2b9tv1OZr9V8CCz40uvNsm1VNqm/a6Tv8AuZsa3PL" +
                "qw1GzakmnumpWi7HONLarV7NM8fotfHGlBQnTaVXab8pq2n9DqQ1sJX1Y5PodOUGnt5oT/onUvt6GMk2" +
                "Xpb8HF0+shkSePKlwqk6te509PqU2oZE4vi+zGrn9NaTGkycUqJUkZ1EUgUWStB1DVKmHSNy9wUvcA6P" +
                "cfSg6kJzSJ5B0pCaSE5og8irkuCboVpFMsqXcg8yT5GDQ2qK5SSKHqIruiqepi7p2XBPLNJmeUmyDy27" +
                "bK5ZUlyXEORVOq5IyzLfconnTT3KantZbjMsMik+TXi3SIkq5LYkhIdkUAOKtlscV9gKQpmlYVfBJYl4" +
                "Ay9LfZg4vwzasa8ElhXgKwU/DCn4NzwrwL4K8AYqfgDa8K8FU8dLggzDBqnQih2AAAAAEClwYNX+Vm+X" +
                "Bg1X5WVK81rqtnOrc6Ou5ZgStnPv24kluSSJKNsbVMwSCK3JyWwoocro1Cs0luCRJr8TGluZpBQEqANZ" +
                "H0jTZXSTNybasw4IdLSa4N8Fsaj0VCSKckbRoyLYqaTR0nphjk3GVmnDO0tynNHZkNPJqVPizFnlueY6" +
                "Sdoi63CHAMsZqDW4JJsb5I3UjVSNEYpok8Sa4HippMuUdjNqsqxqLL41QpKnZFTp8j2p5JNK0U/xPS6b" +
                "oeaaaaObqZPem7NSaldP+JT4Y1mvucWOWaXLLo533Zr6RnXXWZVyJ50u5zPjyeysi5zkPrDXRlqUu5XL" +
                "VJdzAozb3bJrFa3GSHlbPVt8WytZ5zfDJLEiSgkxsUotvdk6sEqGlsZoqmqTOZqqTbOpldJnH1kt2jl1" +
                "5b5c6b/Ey/Tp7GeUW5o36WFtbGJPLbbp41FM1RRHHGlRalSO0mOdvlXPuUvkvkUS5OkYoiy1MqStlqRo" +
                "SsEJDQE4cmvH2MkOTVj7GaRoXACXAzKgAEwBkWNiYAxDZTqM0dPgnlm0lBNtsqOR/qH1V6LF8HDKss1" +
                "drsvJ4KeaUptptyk+Xz9zX6prJarU5Ms27m7VvhdkYIuk5tcKl7sxbtcertxKTtqCdqHPuwlK5KN3XP" +
                "ggmoLZ3X7gr6XLltmWV3Vs155b7kOpuLltbdJkJSaTSe7dL+pNrZRTVLb+41ClKve+EWYmk7e7rkpbu" +
                "dreicW1ST9/oWexfPNbTT2Wy8t9y+Mqiot7Om0/Bgirnu9k/uX9dttN0uE+5qVZWxZm264Stry3uy7B" +
                "kUbaey5aOd8R9Lp7vkujPpxJLlttv6m50SvQ6bUyksaTTVLv3f/0U6/HHNhxyyRfQ1JU210U6v61+hj" +
                "0uSpJXumkb5R+Jii3KoQTfFtt7frb+W5q+nSXY5qwrCm4YnjydNxlF/nSe6d8P5EMGSHV0uoNv8LtpN" +
                "/Pt8mbITTTwNtqFwjftdfU5uqxTx5XOMW4v8zrh+/bczfHk9enUjGOJObj0tK5dKq1at1xd1xs7vY2Y" +
                "dW4tQk+tNcvl+NzmaLPFaacMjfQ00nV9N1uvb2NHw4ObU200lUovZqluv7C+WpXqNBq1OKg5XStN8te" +
                "/vwbHlS7nlcOR42ksqtbxb/m9vZ+6Opj1HxYKUW/dePYmTW3TeZLuReoiu6OXPNXLKnqIpvdFxNdZ6m" +
                "Pki9Uk9mcn+KguWhPWQSe6NTk+zrPWJdmyuWsbeyRyJ66CX5l9zLl9TgrSe4+tZ+0d16qT7pCeobW8j" +
                "zj9Tt7WJ6+clsanFqXuR35ahd5fqVy1CXc89LW5W+aHHNOXdmv8dZ/yz8dqeqilyjPPXxTdtHMnKbTt" +
                "swZZy6mmx9Evy47kvUo1SdlU9e3wcrCnJq2a1jpKyziM/e1OWtk3RFaicnyVygk+NiWOFtKu5v6TGJ1" +
                "db9InJJs6mKNJGXTY6S7G6CSXJ5+vb08zwkuBNh1JLkg5Iy00YabNsI2YNPJN7HRxu0hSJdI6RIiyBp" +
                "IsSRBck4kqjpQdKJiogi4qjPlikmamtjNm4ZYOfk/MRJZPzkSoAAAAEAwqMuDBq/ys3y4MOr/KwleZ1" +
                "y3Zgitzoa7dswR2Zjv24rklYSW1hFjlujDURjsOQJEZFjNVPkmkRSbZYscmrSZmkRAn0S8MA1r6WqTN" +
                "MJWjHKTTLsM1W5p6L6XS3Ki17qytrc6SsVnzR2ZRjXTNGvIrTMcn0yJVjoY3cSVmfDO1RamSFN7MhJpv" +
                "ccnRmyz6Zcmkjo4ZJJI0KSo5OLUJOmzStVGqTM4uLs00kzDLUpTaHmzJp0zLVttm5Bpll6ldmeacmWRii" +
                "aii+kULH7E1jS7FlIFfFDTBGCSJUkCTZJQ8mb1FktRSSHb7IsUUiVIze419aqSbJKD8k0kuw18jP31fr" +
                "iummDdIskkkZsk6T3Nb4Zs8q886T3OTnVts15sjk6solG9znfLcY447d0dHSY6V0U48fsb8MUkti8wv" +
                "pdFbIkxxVCZ1clctyqSLZEGjUEUqRNcBWwXsbZNMaIx3ZNEEocmrH2MsOTVj7GasXrgYlwMyoEwsQCB" +
                "gDKEed/1Xrlh0600XvkTb9l5PQt0m3wfPv9TatZvUsji7Ufwrxtz+pLcjPVyOHmn1Tdd2D2UIduWQSV" +
                "2+27CTdPzSSOTgG+p7Kk+CSlSdccIrT/FS7IdpP2SCHdSW/wCVfr3LL6YNur7lUE2/nvY8sqgkuBKHC" +
                "X4W+7pklOm32qrK00khN7fN0NE1LZt9yxSahS5v9Shuq39y2PEV3dtllE4v8SXZck/iPrTvZIphLdyt" +
                "c3fsiWFOU1fHLLKOvpoNvGm6tNvy/wDH+xvjNyUIRVJp73bfv9znYZUnNt307e2509KlJYlvajvfa2/" +
                "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";
        try {
            System.out.println(base64UploadFms(str,"temp.jpg"));
        } catch (ErrorException e) {
            e.printStackTrace();
        }

//        try {
//            uploadFms("E:\\软件安装包\\timg.jpg");
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


    }

}
