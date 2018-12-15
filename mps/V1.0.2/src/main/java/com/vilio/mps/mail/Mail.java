package com.vilio.mps.mail;

import com.vilio.mps.glob.GlobDict;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.URL;
import java.security.Security;
import java.util.*;

public class Mail {
    //定义发件人、收件人、SMTP服务器、用户名、密码、主题、内容等
    private String displayName;
    private List<String> to;
    private List<String> cc;
    private String from;
    private String smtpServer;
    private String username;
    private String password;
    private String subject;
    private String content;
    private boolean ifAuth; //服务器是否要身份认证
    private Vector<Map> fileAttachment = new Vector<Map>(); //本地文件附件(需要指定本地文件的全路径)
    private Vector<Map> urlAttachment = new Vector<Map>(); //网络文件附件(需要指定URL的全路径)


    /**
     * 设置SMTP服务器地址
     */
    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    /**
     * 设置发件人的地址
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 设置显示的名称
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 设置服务器是否需要身份认证
     */
    public void setIfAuth(boolean ifAuth) {
        this.ifAuth = ifAuth;
    }

    /**
     * 设置E-mail用户名
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * 设置E-mail密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 设置接收者
     */
    public void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * 设置抄送
     */
    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    /**
     * 设置主题
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 设置主体内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 设置本地文件附件(需要指定本地文件的全路径)
     */
    public void addFileAttachfile(Map file) {
        fileAttachment.addElement(file);
    }

    /**
     * 设置URL文件附件(需要指定URL的全路径)
     */
    public void addUrlAttachfile(Map urlFile) {
        urlAttachment.addElement(urlFile);
    }

    public Mail() {

    }

    /**
     * 初始化SMTP服务器地址、发送者E-mail地址、用户名、密码、接收者、主题、内容
     */
    public Mail(String smtpServer, String from, String displayName, String username, String password, List<String> to, List<String> cc, String subject, String content) {
        this.smtpServer = smtpServer;
        this.from = from;
        this.displayName = displayName;
        this.ifAuth = true;
        this.username = username;
        this.password = password;
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.content = content;
    }

    /**
     * 初始化SMTP服务器地址、发送者E-mail地址、接收者、主题、内容
     */
    public Mail(String smtpServer, String from, String displayName, List<String> to, List<String> cc, String subject, String content) {
        this.smtpServer = smtpServer;
        this.from = from;
        this.displayName = displayName;
        this.ifAuth = false;
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.content = content;
    }

    /**
     * 发送邮件
     */
    public HashMap send() throws Exception {
        HashMap map = new HashMap();
        map.put("state", "success");
        map.put("sendStatus", GlobDict.send_succ.getKey());
        String message = "邮件发送成功！";

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Session session = null;
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.mime.splitlongparameters", "false");//禁止截断附件文件名
        if (ifAuth) { //服务器需要身份认证
            props.put("mail.smtp.auth", "true");
            SmtpAuth smtpAuth = new SmtpAuth(username, password);
            session = Session.getDefaultInstance(props, smtpAuth);
//            session = Session.getDefaultInstance(props, new Authenticator(){
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(username, password);
//                }});
        } else {
            props.put("mail.smtp.auth", "false");
            session = Session.getDefaultInstance(props, null);
        }
        session.setDebug(true);
        Transport trans = null;
        try {
            Message msg = new MimeMessage(session);
            try {
                Address from_address = new InternetAddress(from, MimeUtility.encodeText(displayName, MimeUtility.mimeCharset("gb2312"), null));
                msg.setFrom(from_address);
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //设置收件人
            InternetAddress[] toAddress = new InternetAddress[to.size()];
            for (int i = 0; to != null && i < to.size(); i++) {
                toAddress[i] = new InternetAddress(to.get(i));
            }
            msg.setRecipients(Message.RecipientType.TO, toAddress);

            //设置收件人
            if (cc != null && cc.size() != 0) {
                InternetAddress[] ccAddress = new InternetAddress[cc.size()];
                for (int i = 0; cc != null && i < cc.size(); i++) {
                    ccAddress[i] = new InternetAddress(cc.get(i));
                }
                msg.setRecipients(Message.RecipientType.CC, ccAddress);
            }

            msg.setSubject(MimeUtility.encodeText(subject, MimeUtility.mimeCharset("gb2312"), null));
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(content.toString(), "text/html;charset=gb2312");
            mp.addBodyPart(mbp);

            //设置网络文件附件(适用于仅有fileCode的场景)
            if (!urlAttachment.isEmpty()) {//有附件
                Enumeration efile = urlAttachment.elements();
                while (efile.hasMoreElements()) {
                    mbp = new MimeBodyPart();
                    Map urlFile = (Map) efile.nextElement();
                    String url = urlFile.get("url").toString(); //选择出每一个附件名
                    String attachFileName = urlFile.get("attachFileName").toString(); //选择出每一个附件名
                    DataSource fds = new URLDataSource(new URL(url)); //得到数据源
                    mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
                    mbp.setFileName(MimeUtility.encodeText(attachFileName, MimeUtility.mimeCharset("gb2312"), null));
                    mp.addBodyPart(mbp);
                }
                urlAttachment.removeAllElements();
            }

            //设置本地文件附件
            if (!fileAttachment.isEmpty()) {//有附件
                Enumeration efile = fileAttachment.elements();
                while (efile.hasMoreElements()) {
                    mbp = new MimeBodyPart();
                    Map urlFile = (Map) efile.nextElement();
                    String filePath = urlFile.get("filePath").toString(); //选择出每一个附件名
                    String attachFileName = urlFile.get("attachFileName").toString(); //选择出每一个附件名
                    DataSource fds = new FileDataSource(filePath); //得到数据源
                    mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
                    mbp.setFileName(MimeUtility.encodeText(attachFileName));
                    mp.addBodyPart(mbp);
                }
                fileAttachment.removeAllElements();
            }


            msg.setContent(mp); //Multipart加入到信件
            msg.setSentDate(new Date());     //设置信件头的发送日期
            //发送信件
            msg.saveChanges();
            trans = session.getTransport("smtp");
            trans.connect(smtpServer, username, password);
            trans.sendMessage(msg, msg.getAllRecipients());
        } catch (AuthenticationFailedException e) {
            map.put("state", "failed");
            message = "邮件发送失败！错误原因：\n" + "身份验证错误!";
            map.put("sendStatus", GlobDict.send_fail.getKey());
            e.printStackTrace();
        } catch (MessagingException e) {
            message = "邮件发送失败！错误原因：\n" + e.getMessage();
            map.put("state", "failed");
            map.put("sendStatus", GlobDict.send_fail.getKey());
            e.printStackTrace();
            Exception ex = null;
            if ((ex = e.getNextException()) != null) {
                System.out.println(ex.toString());
                ex.printStackTrace();
            }
        } finally {
            if (trans != null) {
                trans.close();
            }
        }
        map.put("message", message);
        return map;
    }

}