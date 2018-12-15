package com.vilio.mps.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/8/25.
 */
public class MailTest {

    public static void main(String[] args) throws Exception{
        Mail mail = new Mail();
        mail.setSmtpServer("smtp.exmail.qq.com");
        mail.setFrom("fengkong@vilio.com.cn");
        //可空
        //mail.setDisplayName("老王");
        mail.setUserName("fengkong@vilio.com.cn");
        mail.setPassword("Honghuo@123");

        //收件人
        List<String> toAddress = new ArrayList<String>();
        toAddress.add("xuefeng.wang@vilio.com.cn");
        mail.setTo(toAddress);

        //抄送人
        List<String> ccAddress = new ArrayList<String>();
        ccAddress.add("632364386@qq.com");
        mail.setCc(ccAddress);

        mail.setSubject("测试邮件");
        mail.setContent("<H1>Hello哈哈</H1>");

        //添加网络文件附件(需要指定本地文件的全路径)
        Map urlFile = new HashMap();
        urlFile.put("url","http://192.168.0.4:8081/fms/api/fileLoad/getFile?serialNo=e2564769-89a9-11e7-9978-1866dae83f00");
        urlFile.put("attachFileName","我在附件中的名字1.rar");//附件文件名
        mail.addUrlAttachfile(urlFile);

        //添加本地文件附件(需要指定本地文件的全路径)
//        Map file = new HashMap();
//        file.put("filePath","C:\\Users\\xiezhilei\\Desktop\\test.rar");
//        file.put("attachFileName","我在附件中的名字2.rar");//附件文件名
//        mail.addFileAttachfile(file);

        mail.send();
    }
}
