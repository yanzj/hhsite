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
 * 类名： ImageService<br>
 * 功能：图片展示接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月4日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class ImageService {

    /**
     * 查看图片主方法
     *
     * @param serialNo
     * @return
     */
    public void doMain(String serialNo, HttpServletResponse response) throws IOException {
        InputStream inStream = null;
        OutputStream os = null;
        try {
            if (!JudgeUtil.isNull(serialNo)) {
                //如果为空，则填写默认图片
                serialNo = GlobParam.imageDefault;
            }
            response.setContentType("text/html; charset=UTF-8");
            response.setContentType("image/jpeg");
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
