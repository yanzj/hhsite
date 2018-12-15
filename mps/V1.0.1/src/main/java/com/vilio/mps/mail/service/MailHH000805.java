package com.vilio.mps.mail.service;

import com.vilio.mps.common.dao.CommonDao;
import com.vilio.mps.common.dao.MpsMessageReceiveInfoMapper;
import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;
import com.vilio.mps.common.service.BaseService;
import com.vilio.mps.exception.ErrorException;
import com.vilio.mps.glob.ConfigInfo;
import com.vilio.mps.glob.Constants;
import com.vilio.mps.glob.GlobDict;
import com.vilio.mps.mail.Mail;
import com.vilio.mps.mail.dao.MailDao;
import com.vilio.mps.mail.pojo.MailInfo;
import com.vilio.mps.util.DateUtil;
import com.vilio.mps.util.JsonUtil;
import com.vilio.mps.util.JudgeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： MailHH000805<br>
 * 功能：邮件单条推送<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class MailHH000805 extends BaseService {

    private static Logger logger = Logger.getLogger(MailHH000805.class);

    @Resource
    private CommonDao commonDao;

    @Resource
    private MpsMessageReceiveInfoMapper mpsMessageReceiveInfoMapper;

    @Resource
    private MailDao mailDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParamNoTransaction(Map<String, Object> body) throws ErrorException {
        //信息发送方身份Id（如果没有则填写系统默认发送身份id）
        checkField(ObjectUtils.toString(body.get("senderIdentityId")), "信息发送方身份标识", null, 100);

        //消息来源系统
        checkField(ObjectUtils.toString(body.get("senderSystem")), "消息来源系统", null, 18);

        //请求流水号（后面可根据请求流水号查询推送业务）
        checkField(ObjectUtils.toString(body.get("requestNo")), "请求流水号", null, 50);

        //发件人
        checkField(ObjectUtils.toString(body.get("userName")), "发件人", null, 50);

        //发件人密码
        checkField(ObjectUtils.toString(body.get("password")), "发件人密码", null, 50);

        //发送人列表（最大支持20个）
        checkField(ObjectUtils.toString(body.get("toUserList")), "发送人列表", null, null);
        if (((List) body.get("toUserList")).size() > 20) {
            throw new ErrorException("9998", "发送人列表不能大于20");
        }
        //标题
        checkField(ObjectUtils.toString(body.get("subject")), "标题", null, 100);
        //发送内容
        checkField(ObjectUtils.toString(body.get("content")), "发送内容", null, 2000);


        //校验非必填参数
        //发件人名称（可空）
        if (JudgeUtil.isNull(body.get("displayName")) && body.get("displayName").toString().length() > 255) {
            throw new ErrorException("9998", "发件人名称超出长度限制");
        }
        //抄送列表
        if (body.get("toCcList") != null) {
            if (((List<Map>) body.get("toCcList")).size() != 0 && ((List<Map>) body.get("toCcList")).size() > 20) {
                throw new ErrorException("9998", "抄送人列表不能大于20");
            }
        }
        if (body.get("fileList") != null) {
            List<Map> fileList = (List) body.get("fileList");
            for (Map fileInfo : fileList) {
                //发送文件地址
                checkField(ObjectUtils.toString(fileInfo.get("urlFile")), "发送文件地址", null, 255);
                //发送文件名
                checkField(ObjectUtils.toString(fileInfo.get("urlFileName")), "发送文件名", null, 255);
            }
        }
    }


    /**
     * 发送邮件流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws Exception {
        //生成序列号
        Long seq = commonDao.querySequence("SERIAL_NO");
        String serialNo = DateUtil.getCurrentDateTime() + String.format("%012d", seq);
        //数据入库，入主表和短信子表，进行事务控制
        ((MailHH000805) AopContext.currentProxy()).insertMsg(body, serialNo);
        Mail mail = new Mail();
        mail.setIfAuth(true);
        mail.setSmtpServer(ConfigInfo.mailSmtpServer);
        //和发件人用户名一样
        mail.setFrom(ObjectUtils.toString(body.get("userName")));
        mail.setDisplayName(ObjectUtils.toString(body.get("displayName")));
        mail.setUserName(ObjectUtils.toString(body.get("userName")));
        mail.setPassword(ObjectUtils.toString(body.get("password")));
        List<String> toAddress = new ArrayList<String>();
        for (Map toUser : (List<Map>) body.get("toUserList")) {
            toAddress.add(ObjectUtils.toString(toUser.get("toUser")));
        }
        mail.setTo(toAddress);
        if (body.get("toCcList") != null && ((List<Map>) body.get("toCcList")).size() != 0) {
            //抄送人
            List<String> ccAddress = new ArrayList<String>();
            for (Map ccUser : (List<Map>) body.get("toCcList")) {
                if (ccUser.get("ccUser")!=null&&!"".equals(ccUser.get("ccUser"))) {
                    ccAddress.add(ObjectUtils.toString(ccUser.get("ccUser")));
                }
            }
            mail.setCc(ccAddress);
        }
        mail.setSubject(ObjectUtils.toString(body.get("subject")));
        mail.setContent(ObjectUtils.toString(body.get("content")));

        List<Map> fileList = (List) body.get("fileList");
        for (Map fileInfo : fileList) {
            Map urlFile = new HashMap();
            urlFile.put("url", ObjectUtils.toString(fileInfo.get("urlFile")));
            urlFile.put("attachFileName", ObjectUtils.toString(fileInfo.get("urlFileName")));//附件文件名
            mail.addUrlAttachfile(urlFile);
        }
        Map result = mail.send();
        int sendNum = 1;
        if (!GlobDict.send_succ.getKey().equals(result.get("sendStatus"))) {
            //发送失败重发
            result = mail.send();
            sendNum++;
        }
        result.put("sendNum", sendNum);
        //修改主表和短信表的状态
        ((MailHH000805) AopContext.currentProxy()).updateMsgStatus(serialNo, result);
        if (!GlobDict.send_succ.getKey().equals(result.get("sendStatus"))) {
            throw new ErrorException("0067", "");
        }
        //设置返回信息，将传来的信息原样返回，并加入渠道返回信息
        resultMap.putAll(body);
        resultMap.putAll(result);
        resultMap.put("serialNo", serialNo);
    }

    /**
     * 修改状态
     *
     * @param serialNo
     * @param result
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void updateMsgStatus(String serialNo, Map result) throws ErrorException {
        MpsReceiveMessageInfo messageReceiveInfo = new MpsReceiveMessageInfo();
        messageReceiveInfo.setSerialNo(serialNo);
        messageReceiveInfo.setStatus(result.get("sendStatus").toString());
        messageReceiveInfo.setSendTime(DateUtil.getCurrentDateTime());
        int ret = mpsMessageReceiveInfoMapper.updateStatusBySerialNo(messageReceiveInfo);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //修改邮件信息表
        //修改短信表
        MailInfo mailUpdateParam = new MailInfo();
        mailUpdateParam.setSerialNo(serialNo);
        mailUpdateParam.setSendStatus(result.get("sendStatus").toString());
        mailUpdateParam.setChlState(result.get("state").toString());
        mailUpdateParam.setChlMessage(result.get("message") == null ? "" : result.get("message").toString());
        mailUpdateParam.setSendNum(result.get("sendNum").toString());
        ret = mailDao.updateMailStatusBySerialNo(mailUpdateParam);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
    }

    /**
     * 批量入库
     *
     * @param body
     * @param serialNo
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void insertMsg(Map<String, Object> body, String serialNo) throws ErrorException {
        //发送内容
        String content = ObjectUtils.toString(body.get("content"));
        String toUserList = JsonUtil.objectToJson(body.get("toUserList"));
        //插入主表
        MpsReceiveMessageInfo messageReceiveInfo = new MpsReceiveMessageInfo();
        messageReceiveInfo.setSerialNo(serialNo);
        messageReceiveInfo.setType(Constants.PUSH_TYPE_EMAIL);
        messageReceiveInfo.setSenderIdentityId(body.get("senderIdentityId").toString());
        messageReceiveInfo.setSenderName(body.get("senderName") == null ? "" : body.get("senderName").toString());
        messageReceiveInfo.setSenderSystem(body.get("senderSystem").toString());
        messageReceiveInfo.setReceiverIdentityId(toUserList);
        messageReceiveInfo.setTitle(ObjectUtils.toString(body.get("subject")));
        messageReceiveInfo.setContent(content);
        messageReceiveInfo.setRequestNo(body.get("requestNo").toString());
        //状态初始化成功
        messageReceiveInfo.setStatus(GlobDict.send_init.getKey());
        int ret = mpsMessageReceiveInfoMapper.insertMessageReceiveInfo(messageReceiveInfo);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //插入邮件信息表
        MailInfo mail = new MailInfo();
        mail.setSerialNo(serialNo);
        mail.setDisplayName(ObjectUtils.toString(body.get("displayName")));
        mail.setUserName(ObjectUtils.toString(body.get("userName")));
        mail.setMd5Pwd(ObjectUtils.toString(body.get("password")));
        mail.setToUserList(toUserList);
        mail.setToCcList(body.get("toCcList") == null ? "" : JsonUtil.objectToJson(body.get("toCcList")));
        mail.setSubject(ObjectUtils.toString(body.get("subject")));
        mail.setContent(ObjectUtils.toString(body.get("content")));
        mail.setUrlFileList(body.get("fileList") == null ? "" : JsonUtil.objectToJson(body.get("fileList")));
        mail.setTimelyNews(GlobDict.timely_news.getKey());
        mail.setSendStatus(GlobDict.send_init.getKey());
        ret = mailDao.insertMailInfo(mail);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
    }


}
