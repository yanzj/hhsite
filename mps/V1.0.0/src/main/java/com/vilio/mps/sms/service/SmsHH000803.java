package com.vilio.mps.sms.service;

import com.vilio.mps.common.dao.CommonDao;
import com.vilio.mps.common.dao.MpsMessageReceiveInfoMapper;
import com.vilio.mps.common.dao.SignDao;
import com.vilio.mps.common.dao.TemplateDao;
import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;
import com.vilio.mps.common.pojo.Sign;
import com.vilio.mps.common.pojo.Template;
import com.vilio.mps.common.service.BaseService;
import com.vilio.mps.exception.ErrorException;
import com.vilio.mps.glob.Constants;
import com.vilio.mps.glob.GlobDict;
import com.vilio.mps.sms.dao.SmsDao;
import com.vilio.mps.sms.pojo.Sms;
import com.vilio.mps.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： SHH000803<br>
 * 功能：短信单条推送<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class SmsHH000803 extends BaseService {

    private static Logger logger = Logger.getLogger(SmsHH000803.class);

    @Resource
    private SignDao signDao;

    @Resource
    private TemplateDao templateDao;

    @Resource
    private CommonDao commonDao;

    @Resource
    private MpsMessageReceiveInfoMapper mpsMessageReceiveInfoMapper;

    @Resource
    private SmsDao smsDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //校验必填字段
        //信息发送方身份Id（如果没有则填写系统默认发送身份id）
        if (!JudgeUtil.isNull(body.get("senderIdentityId")) || body.get("senderIdentityId").toString().length() > 100) {
            throw new ErrorException("9996", "");
        }
        //手机号（信息接收方身份Id）
        if (!JudgeUtil.isNull(body.get("mobile")) || body.get("mobile").toString().length() > 18 || !JudgeUtil.isMobile(body.get("mobile").toString())) {
            throw new ErrorException("9996", "");
        }
        //消息来源系统
        if (!JudgeUtil.isNull(body.get("senderSystem")) || body.get("senderSystem").toString().length() > 18) {
            throw new ErrorException("9996", "");
        }
        //请求流水号（后面可根据请求流水号查询推送业务）
        if (!JudgeUtil.isNull(body.get("requestNo")) || body.get("requestNo").toString().length() > 50) {
            throw new ErrorException("9996", "");
        }
        //参数列表
        if (!JudgeUtil.isNull(body.get("templateParam")) || JsonUtil.objectToJson((Map) body.get("templateParam")).length() > 50) {
            throw new ErrorException("9996", "");
        }
        //签名id
        if (!JudgeUtil.isNull(body.get("signNo")) || body.get("signNo").toString().length() > 40) {
            throw new ErrorException("9996", "");
        }
        //模板id
        if (!JudgeUtil.isNull(body.get("templateCode")) || body.get("templateCode").toString().length() > 50) {
            throw new ErrorException("9996", "");
        }

        //校验非必填字段
        //信息发送方名称
        if (JudgeUtil.isNull(body.get("senderName")) && body.get("senderName").toString().length() > 255) {
            throw new ErrorException("9996", "");
        }
        //外部流水扩展字段
        if (JudgeUtil.isNull(body.get("outId")) && body.get("outId").toString().length() > 255) {
            throw new ErrorException("9996", "");
        }

        //校验签名编码是否存在和状态
        Sign sign = signDao.getSignById(body.get("signNo").toString());
        if (null == sign) {
            throw new ErrorException("0008", "");
        }
        if (GlobDict.sign_status_disable.getKey().equals(sign.getStatus())) {
            //签名已停用
            throw new ErrorException("0017", "");
        } else if (GlobDict.sign_status_delete.getKey().equals(sign.getStatus())) {
            //签名已删除
            throw new ErrorException("0008", "");
        } else if (!GlobDict.sign_status_valid.getKey().equals(sign.getStatus())) {
            //不等于有效
            throw new ErrorException("0018", "");
        }

        //检查模板是否有效
        Template template = templateDao.getTemplateById(body.get("templateCode").toString());
        if (null == template) {
            throw new ErrorException("0019", "");
        }
        if (GlobDict.template_status_disable.getKey().equals(template.getStatus())) {
            //签名已停用
            throw new ErrorException("0020", "");
        } else if (GlobDict.template_status_delete.getKey().equals(template.getStatus())) {
            //签名已删除
            throw new ErrorException("0019", "");
        } else if (!GlobDict.template_status_valid.getKey().equals(template.getStatus())) {
            //不等于有效
            throw new ErrorException("0021", "");
        }

        //开始校验参数列表
        Map templateParam = (Map) body.get("templateParam");
        Map templateParamRule = JsonUtil.jsonToMap(template.getTemplateParam().toString());
        for (Object key : templateParamRule.keySet()) {
            if (!templateParam.containsKey(key) || "".equals(templateParam.get(key))
                    || templateParam.get(key).toString().length() > Long.parseLong(templateParamRule.get(key).toString())) {
                //参数不存在或者为空，或者长度大于阈值，则抛出错误
                throw new ErrorException("0022", "");
            }
        }
        //签名信息和模板信息填入body中
        body.put("sign", sign);
        body.put("template", template);
    }


    /**
     * 单笔短信发送流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        //生成序列号
        Long seq = commonDao.querySequence("SERIAL_NO");
        String serialNo = DateUtil.getCurrentDateTime() + String.format("%012d", seq);
        //获取签名和模板
        Sign sign = (Sign) body.get("sign");
        Template template = (Template) body.get("template");
        //数据入库，入主表和短信子表，进行事务控制
        insertMsg(body, serialNo, sign, template);
        //开始发送短信
        Map result = SmsUtil.singleSendSms(body.get("mobile").toString(), sign.getChlSignNo(), template.getChlTemplateCode(),
                (Map) body.get("templateParam"), body.get("outId") == null ? "" : body.get("outId").toString());
        //发送次数默认1
        int sendNum = 1;
        //失败重发
        if (!GlobDict.send_succ.getKey().equals(result.get("sendStatus"))) {
            //如果发送失败，进行一次重发机制
            logger.info("发送失败或者超时，进行重发机制：" + result);
            result = SmsUtil.singleSendSms(body.get("mobile").toString(), sign.getChlSignNo(), template.getChlTemplateCode(),
                    (Map) body.get("templateParam"), body.get("outId") == null ? "" : body.get("outId").toString());
            sendNum++;
        }
        result.put("sendNum", sendNum);
        //修改主表和短信表的状态
        updateMsgStatus(serialNo, result);
        //判断短信发送状态，设置返回码
        if (GlobDict.send_unknown.getKey().equals(result.get("sendStatus"))){
            //超时
            throw new ErrorException("9992", "");
        }else if (GlobDict.send_fail.getKey().equals(result.get("sendStatus"))){
            throw new ErrorException("0024", result.get("message").toString());
        }
        //设置返回信息，将传来的信息原样返回，并加入渠道返回信息
        resultMap.putAll(body);
        resultMap.putAll(result);
    }

    /**
     * 修改主表和短信表的状态
     *
     * @param serialNo
     * @param result
     */
    private void updateMsgStatus(String serialNo, Map result) throws ErrorException {
        MpsReceiveMessageInfo messageReceiveInfo = new MpsReceiveMessageInfo();
        messageReceiveInfo.setSerialNo(serialNo);
        messageReceiveInfo.setStatus(result.get("sendStatus").toString());
        int ret = mpsMessageReceiveInfoMapper.updateStatusBySerialNo(messageReceiveInfo);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //修改短信表
        Sms smsUpdateParam = new Sms();
        smsUpdateParam.setSerialNo(serialNo);
        smsUpdateParam.setStatus(result.get("sendStatus").toString());
        smsUpdateParam.setReceiptNo(result.get("bizId") == null ? "" : result.get("bizId").toString());
        smsUpdateParam.setChlRetCode(result.get("code") == null ? "" : result.get("code").toString());
        smsUpdateParam.setChlRetMsg(result.get("message") == null ? "" : result.get("message").toString());
        smsUpdateParam.setSendNum(result.get("sendNum").toString());
        ret = smsDao.updateSmsStatusBySerialNo(smsUpdateParam);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
    }


    /**
     * 插入推送信息主表和信息子表
     *
     * @param body
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    private void insertMsg(Map<String, Object> body, String serialNo, Sign sign, Template template) throws ErrorException {
        Map templateParam = (Map) body.get("templateParam");
        templateParam.put("sign_name", sign.getSignName());
        //发送内容
        String content = MatchUtil.matchValue(template.getContent(), templateParam);
        //插入主表
        MpsReceiveMessageInfo messageReceiveInfo = new MpsReceiveMessageInfo();
        messageReceiveInfo.setSerialNo(serialNo);
        messageReceiveInfo.setType(Constants.PUSH_TYPE_SMS);
        messageReceiveInfo.setSenderIdentityId(body.get("senderIdentityId").toString());
        messageReceiveInfo.setSenderName(body.get("senderIdentityId") == null ? "" : body.get("senderIdentityId").toString());
        messageReceiveInfo.setSenderName(body.get("senderSystem").toString());
        messageReceiveInfo.setReceiverIdentityId(body.get("mobile").toString());
        messageReceiveInfo.setTitle(sign.getSignName());
        messageReceiveInfo.setContent(content);
        messageReceiveInfo.setRequestNo(body.get("requestNo").toString());
        //状态初始化成功
        messageReceiveInfo.setStatus(GlobDict.send_init.getKey());
        int ret = mpsMessageReceiveInfoMapper.insertMessageReceiveInfo(messageReceiveInfo);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //插入短信流水表
        Sms smsInserParam = new Sms();
        smsInserParam.setSerialNo(serialNo);
        smsInserParam.setInnerSignNo(sign.getInnerSignNo());
        smsInserParam.setInnerTemplateCode(template.getInnerTemplateCode());
        smsInserParam.setTemplateParam(JsonUtil.objectToJson(templateParam));
        smsInserParam.setOutId(body.get("outId") == null ? "" : body.get("outId").toString());
        smsInserParam.setTimelyNews(GlobDict.timely_news.getKey());
        smsInserParam.setContent(content);
        smsInserParam.setMobile(body.get("mobile").toString());
        //状态初始化成功
        smsInserParam.setStatus(GlobDict.send_init.getKey());
        ret = smsDao.insertSmsInfo(smsInserParam);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
    }
}
