package com.vilio.pcfs.service;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.pojo.LoginInfo;
import com.vilio.pcfs.util.FileUtil;
import com.vilio.pcfs.util.JudgeUtil;
import com.vilio.pcfs.util.PcfsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 类名： Pcfs000031<br>
 * 功能：修改头像<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月23日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000031 extends BaseService {
    private static final Logger logger = Logger.getLogger(Pcfs000031.class);

    @Resource
    private LoginComn loginComn;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //判断头像是否存在
        checkField(ObjectUtils.toString(body.get("uploadHeadImgStr")), "头像", null, null);
    }


    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //现上传到文件服务器上
        Map fileRet = FileUtil.base64UploadFms(ObjectUtils.toString(body.get("uploadHeadImgStr")), System.currentTimeMillis() + new Random().nextInt(999999) + ".jpg");
        if (!JudgeUtil.isNull(fileRet.get("serialNo"))) {
            //文件上传失败
            throw new ErrorException(ReturnCode.FILE_HANDLE_FAIL, "");
        }
        //获取文件标识
        //发送um系统验密
        Map sendUmParam = new HashMap();
        sendUmParam.put("headImg", fileRet.get("serialNo"));
        sendUmParam.put(GlobParam.PARAM_USER_ID, head.get(GlobParam.PARAM_USER_ID));
        sendUmParam.put(GlobParam.PARAM_FUNCTION_NO, GlobParam.umUpdateUserInfo);
        Map umResult = PcfsUtil.sendUMRetJudge(sendUmParam);
        //修改本地头像
        LoginInfo session = new LoginInfo();
        session.setUserId(head.get(GlobParam.PARAM_USER_ID).toString());
        session.setHeadImg(ObjectUtils.toString(fileRet.get("serialNo")));
        loginComn.setSession(session);
        resultMap.put("headImg",fileRet.get("serialNo"));
    }


}
