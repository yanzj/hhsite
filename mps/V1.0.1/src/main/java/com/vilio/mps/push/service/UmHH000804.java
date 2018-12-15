package com.vilio.mps.push.service;

import com.vilio.mps.common.dao.AppDao;
import com.vilio.mps.common.dao.MpsMessageReceiveInfoMapper;
import com.vilio.mps.common.pojo.App;
import com.vilio.mps.common.pojo.MpsReceiveMessageInfo;
import com.vilio.mps.common.service.BaseService;
import com.vilio.mps.common.service.CommonService;
import com.vilio.mps.exception.ErrorException;
import com.vilio.mps.glob.Constants;
import com.vilio.mps.glob.GlobDict;
import com.vilio.mps.push.dao.UmengDao;
import com.vilio.mps.push.pojo.Umeng;
import com.vilio.mps.util.DateUtil;
import com.vilio.mps.util.JudgeUtil;
import com.vilio.mps.util.UmengUtil;
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
 * 类名： UmHH000804<br>
 * 功能：友盟推送<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月10日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class UmHH000804 extends BaseService {

    private static Logger logger = Logger.getLogger(UmHH000804.class);

    @Resource
    private AppDao appDao;

    @Resource
    private MpsMessageReceiveInfoMapper mpsMessageReceiveInfoMapper;

    @Resource
    private UmengDao umengDao;

    @Resource
    private CommonService commonService;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParamNoTransaction(Map<String, Object> body) throws ErrorException {
        //校验必填字段
        //信息发送方身份Id（如果没有则填写系统默认发送身份id）
        checkField(ObjectUtils.toString(body.get("senderIdentityId")), "信息发送方身份标识", null, 100);

        //信息接收方身份Id（下面的device_token）
        //checkField(ObjectUtils.toString(body.get("receiverIdentityId")), "信息接收方身份标识", null, 100);

        //消息来源系统
        checkField(ObjectUtils.toString(body.get("senderSystem")), "消息来源系统", null, 18);

        //请求流水号（后面可根据请求流水号查询推送业务）
        checkField(ObjectUtils.toString(body.get("requestNo")), "请求流水号", null, 50);

        //应用标识号
        checkField(ObjectUtils.toString(body.get("appCode")), "应用标识号", null, 50);

        //title
        checkField(ObjectUtils.toString(body.get("title")), "通知标题", null, 500);

        //text
        checkField(ObjectUtils.toString(body.get("text")), "通知文字描述", null, 1000);

        //系统类型
//        checkField(ObjectUtils.toString(body.get("systemType")), "系统类型", null, 20);
//        if (!GlobDict.system_type_android.getKey().equals(ObjectUtils.toString(body.get("systemType"))) &&
//                !GlobDict.system_type_ios.getKey().equals(ObjectUtils.toString(body.get("systemType")))) {
//            throw new ErrorException("9998", "系统类型错误");
//        }

        //device_tokens逗号分割，最大支持500个
        checkField(ObjectUtils.toString(body.get("deviceTokens")), "", null, null);
        String[] deviceTokens = ObjectUtils.toString(body.get("deviceTokens")).split(",");
        if (deviceTokens.length > 500) {
            throw new ErrorException("9998", "发送数必须小于500");
        }


        //校验非必填字段
        //信息发送方名称
        if (JudgeUtil.isNull(body.get("senderName")) && body.get("senderName").toString().length() > 255) {
            throw new ErrorException("9998", "信息发送方名称超出长度限制");
        }

        //检查应用是否存在
        checkField(ObjectUtils.toString(body.get("appCode")), "应用标识 ", null, 36);
        App app = appDao.queryAppInfoByCode(ObjectUtils.toString(body.get("appCode")));
        if (app == null) {
            throw new ErrorException("0081", "");
        }
        if (GlobDict.app_status_disable.getKey().equals(app.getStatus())) {
            //签名已停用
            throw new ErrorException("0083", "");
        } else if (GlobDict.app_status_delete.getKey().equals(app.getStatus())) {
            //签名已删除
            throw new ErrorException("0082", "");
        } else if (!GlobDict.app_status_valid.getKey().equals(app.getStatus())) {
            //不等于有效
            throw new ErrorException("0084", "");
        }

        body.put("app", app);

        if (GlobDict.system_type_android.getKey().equals(app.getSystemType())) {
            //ticker（安卓必填）
            checkField(ObjectUtils.toString(body.get("ticker")), "通知栏提示文字", null, 500);
        } else if (GlobDict.system_type_ios.getKey().equals(app.getSystemType())) {
            //subtitle（非必填）
            if (JudgeUtil.isNull(body.get("subtitle")) && ObjectUtils.toString(body.get("subtitle")).length() > 500) {
                throw new ErrorException("9998", "子标题长度超出限制");
            }
        }

    }


    /**
     * 批量友盟发送流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws Exception {
        //初始化入库
        List<Umeng> umengs = ((UmHH000804) AopContext.currentProxy()).insertMsg(body);
        App app = (App) body.get("app");
        //发送到友盟
        Map result = new HashMap();
        //发送次数默认1
        int sendNum = 1;
        if (GlobDict.system_type_android.getKey().equals(app.getSystemType())) {
            //安卓
            result = UmengUtil.sendAndroidUnicast(app.getAppMasterSecret(), app.getAppKey(), ObjectUtils.toString(body.get("deviceTokens")),
                    ObjectUtils.toString(body.get("ticker")), ObjectUtils.toString(body.get("title")), ObjectUtils.toString(body.get("text")));
        } else if (GlobDict.system_type_ios.getKey().equals(app.getSystemType())) {
            //苹果
            result = UmengUtil.sendIOSUnicast(app.getAppMasterSecret(), app.getAppKey(), ObjectUtils.toString(body.get("deviceTokens")),
                    ObjectUtils.toString(body.get("title")), ObjectUtils.toString(body.get("subtitle")), ObjectUtils.toString(body.get("text")));
        }
        String ret = result.get("ret").toString();
        if (!GlobDict.send_succ.getKey().equals(result.get("sendStatus"))) {
            logger.info("发送失败，重发一次" + result.toString());
            sendNum += 1;
            if (GlobDict.system_type_android.getKey().equals(app.getSystemType())) {
                //安卓
                result = UmengUtil.sendAndroidUnicast(app.getAppMasterSecret(), app.getAppKey(), ObjectUtils.toString(body.get("deviceTokens")),
                        ObjectUtils.toString(body.get("ticker")), ObjectUtils.toString(body.get("title")), ObjectUtils.toString(body.get("text")));
            } else if (GlobDict.system_type_ios.getKey().equals(app.getSystemType())) {
                //苹果
                result = UmengUtil.sendIOSUnicast(app.getAppMasterSecret(), app.getAppKey(), ObjectUtils.toString(body.get("deviceTokens")),
                        ObjectUtils.toString(body.get("ticker")), ObjectUtils.toString(body.get("title")), ObjectUtils.toString(body.get("text")));
            }
        }
        result.put("sendNum", sendNum);
        //修改发送信息表
        ((UmHH000804) AopContext.currentProxy()).updateMsgStatus(umengs, result);
        if (GlobDict.send_unknown.getKey().equals(result.get("sendStatus"))) {
            throw new ErrorException("9992", "");
        }
        if (!GlobDict.send_succ.getKey().equals(result.get("sendStatus"))) {
            throw new ErrorException("0067", "");
        }
        //设置返回信息，将传来的信息原样返回，并加入渠道返回信息
        resultMap.putAll(body);
        resultMap.putAll(result);
    }

    /**
     * 修改发送状态
     *
     * @param umengs
     * @param result
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void updateMsgStatus(List<Umeng> umengs, Map result) throws ErrorException {
        List<MpsReceiveMessageInfo> mpsReceiveMessageInfos = new ArrayList<>();
        String ret = ObjectUtils.toString(result.get("ret"));
        Map data = result.get("data") == null ? new HashMap() : (Map) result.get("data");
        String msgId = ObjectUtils.toString(data.get("msg_id"));
        String taskId = ObjectUtils.toString(data.get("task_id"));
        String errorCode = ObjectUtils.toString(data.get("error_code"));
        for (Umeng umeng : umengs) {
            MpsReceiveMessageInfo mpsReceiveMessageInfo = new MpsReceiveMessageInfo();
            mpsReceiveMessageInfo.setSerialNo(umeng.getSerialNo());
            mpsReceiveMessageInfos.add(mpsReceiveMessageInfo);
        }
        //修改表结构
        Map mrmiParam = new HashMap();
        mrmiParam.put("status", result.get("sendStatus"));
        mrmiParam.put("sendTime", DateUtil.getCurrentDateTime());
        mrmiParam.put("mpsReceiveMessageInfos", mpsReceiveMessageInfos);
        int uret = mpsMessageReceiveInfoMapper.updateMessageReceiveInfoBatch(mrmiParam);
        if (uret <= 0) {
            throw new ErrorException("9997", "");
        }

        Map umengParam = new HashMap();
        umengParam.put("chlRet", ret);
        umengParam.put("chlRetData", data.toString());
        umengParam.put("chlErrorCode", errorCode);
        umengParam.put("sendStatus", result.get("sendStatus"));
        umengParam.put("sendNum", ObjectUtils.toString(result.get("sendNum")));
        umengParam.put("chlMsgId", msgId);
        umengParam.put("taskId", taskId);
        umengParam.put("umengs", umengs);
        uret = umengDao.updateUmengBatch(umengParam);
        if (uret <= 0) {
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
    public List<Umeng> insertMsg(Map<String, Object> body) throws ErrorException {
        App app = (App) body.get("app");
        List<MpsReceiveMessageInfo> mpsReceiveMessageInfos = new ArrayList<>();
        List<Umeng> umengs = new ArrayList<>();
        String[] deviceTokens = ObjectUtils.toString(body.get("deviceTokens")).split(",");
        for (String deviceToken : deviceTokens) {
            String seq = commonService.getSeq("SERIAL_NO", 12);
            String serialNo = DateUtil.getCurrentDateTime() + seq;
            MpsReceiveMessageInfo mpsReceiveMessageInfo = new MpsReceiveMessageInfo();
            mpsReceiveMessageInfo.setSerialNo(serialNo);
            mpsReceiveMessageInfo.setType(Constants.PUSH_TYPE_UMENG);
            mpsReceiveMessageInfo.setSenderIdentityId(body.get("senderIdentityId").toString());
            mpsReceiveMessageInfo.setSenderName(body.get("senderName") == null ? "" : body.get("senderName").toString());
            mpsReceiveMessageInfo.setSenderSystem(body.get("senderSystem").toString());
            mpsReceiveMessageInfo.setReceiverIdentityId(deviceToken);
            mpsReceiveMessageInfo.setTitle(ObjectUtils.toString(body.get("title")));
            mpsReceiveMessageInfo.setContent(ObjectUtils.toString(body.get("text")));
            mpsReceiveMessageInfo.setRequestNo(body.get("requestNo").toString());
            //状态初始化成功
            mpsReceiveMessageInfo.setStatus(GlobDict.send_init.getKey());

            Umeng umeng = new Umeng();
            umeng.setSerialNo(serialNo);
            umeng.setAppCode(ObjectUtils.toString(body.get("appCode")));
            umeng.setTicker(ObjectUtils.toString(body.get("ticker")));
            umeng.setSubtitle(ObjectUtils.toString(body.get("subtitle")));
            umeng.setTitle(ObjectUtils.toString(body.get("title")));
            umeng.setText(ObjectUtils.toString(body.get("text")));
            umeng.setDeviceToken(deviceToken);
            umeng.setSendStatus(GlobDict.send_init.getKey());
            umeng.setTimelyNews(GlobDict.timely_news.getKey());
            umeng.setSystemType(app.getSystemType());

            mpsReceiveMessageInfos.add(mpsReceiveMessageInfo);
            umengs.add(umeng);
        }
        //批量插入数据库
        int ret = mpsMessageReceiveInfoMapper.insertMessageReceiveInfoBatch(mpsReceiveMessageInfos);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        ret = umengDao.insertUmengBatch(umengs);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        return umengs;
    }

}
