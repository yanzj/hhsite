package com.vilio.pcfs.service;

import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.util.JudgeUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 类名： FileService<br>
 * 功能：文件下载接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月4日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class FileService {


    /**
     * 下载文件主方法
     *
     * @param serialNo
     * @return
     */
    public void doMain(String serialNo, String fileName, HttpServletResponse response) throws IOException {
        InputStream inStream = null;
        OutputStream os = null;
        try {
            response.setContentType("multipart/form-data");
            if (JudgeUtil.isNull(fileName)) {
                response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
            }
            URL url = new URL(GlobParam.fmsDownloadUrl + "?serialNo=" + serialNo);
            URLConnection conn = url.openConnection();
            inStream = conn.getInputStream();
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = inStream.read(buffer)) != -1)
                os.write(buffer, 0, count);
            os.flush();
        } finally {
            if (inStream != null) {
                inStream.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }






}
