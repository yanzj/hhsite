package com.vilio.mps.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.vilio.mps.exception.ErrorException;
import com.vilio.mps.glob.ConfigInfo;
import com.vilio.mps.glob.GlobDict;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 类名： SmsUtil<br>
 * 功能：发送短信工具类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月26日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class SmsUtil {

    protected static Logger logger = Logger.getLogger(SmsUtil.class);

    /**
     * 单笔发送短信
     *
     * @param phone
     * @param signName
     * @param templateCode
     * @param templateParam
     * @param outId
     * @return
     * @throws ErrorException
     */
    public static Map singleSendSms(String phone, String signName, String templateCode, Map templateParam, String outId) {
        logger.info(templateCode);
        List phoneList = new ArrayList();
        phoneList.add(phone);
        return batchSendSms(phoneList, signName, templateCode, templateParam, outId);
    }

    /**
     * 批量发送短信
     *
     * @param phoneList
     * @param signName
     * @param templateCode
     * @param templateParam
     * @param outId
     * @throws ErrorException
     */
    public static Map batchSendSms(List<String> phoneList, String signName, String templateCode, Map templateParam, String outId) {
        Map result = new HashMap();
        StringBuffer phoneNumbers = new StringBuffer();
        for (String phone : phoneList) {
            phoneNumbers.append(phone);
            phoneNumbers.append(",");
        }
        phoneNumbers.substring(0, phoneNumbers.length() - 1);
        IClientProfile profile = DefaultProfile.getProfile(ConfigInfo.regionId, ConfigInfo.accessKeyId, ConfigInfo.accessKeySecret);
        try {
            DefaultProfile.addEndpoint(ConfigInfo.regionId, ConfigInfo.regionId, ConfigInfo.product, ConfigInfo.domain);
            IAcsClient client = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(phoneNumbers.toString());
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(JsonUtil.objectToJson(templateParam));
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId(outId);
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = client.getAcsResponse(request);
            logger.info("发送短信返回信息：" + JsonUtil.objectToJson(sendSmsResponse));
            result = BeanUtil.transBean2Map(sendSmsResponse);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                result.put("sendStatus", GlobDict.send_succ.getKey());
                //请求成功
                return result;
            } else {
                //发送失败
                result.put("sendStatus", GlobDict.send_fail.getKey());
                return result;
            }
        } catch (ClientException e) {
            e.printStackTrace();
            if (e.getErrCode().equals("SDK.ServerUnreachable")) {
                //网络连接超时
                result.put("sendStatus", GlobDict.send_unknown.getKey());
            } else {
                //发送失败
                result.put("sendStatus", GlobDict.send_fail.getKey());
            }
            result.put("code", e.getErrCode());
            result.put("message", e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "9999");
            result.put("message", e.getMessage());
            result.put("sendStatus", GlobDict.send_fail.getKey());
        }
        return result;
    }

    public static void main(String[] args) {
//        JSONObject jsonObject = JSONObject.fromObject("{\"head\":{\"functionNo\":\"HH000805\"},\"body\":{\"type\":\"Email\",\"senderIdentityId\":\"pcfs001\",\"senderSystem\":\"pcfs\",\"requestNo\":\"123456\",\n" +
//                "                                          \"senderName\":null,\"userName\":\"fengkong@vilio.com.cn\",\"password\":\"Honghuo@123\",\n" +
//                "                                          \"toUserList\":[\"xuefeng.wang@vilio.com.cn\",\"632364386@qq.com\"],\"subject\":\"subject\",\"content\":\"content\"}}");
//        Map map = jsonObject;
//        Map body = (Map) map.get("body");
//        System.out.println(body);
//        System.out.println(body.get("toUserList"));
//        System.out.println(((List)body.get("toUserList")).get(0));
//        System.out.println(body.get("senderName").getClass());
//        System.out.println(body.get("senderName") instanceof JSONNull);

    }

}
