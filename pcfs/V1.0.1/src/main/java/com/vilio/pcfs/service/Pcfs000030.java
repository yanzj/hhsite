package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.LoginInfoDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： Pcfs000030<br>
 * 功能：通知消息设置<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月7日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000030 extends BaseService {
    private static final Logger logger = Logger.getLogger(Pcfs000030.class);

    @Resource
    LoginInfoDao loginInfoDao;



    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //逾期通知设置
        if (!JudgeUtil.isNull(body.get("overdueSet")) || body.get("overdueSet").toString().length() > 1 ) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "逾期通知设置参数错误");
        }
        //还款通知设置
        if (!JudgeUtil.isNull(body.get("paymentSet")) || body.get("paymentSet").toString().length() > 1 ) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "还款通知设置参数错误");
        }
        //显示消息详情
        if (!JudgeUtil.isNull(body.get("displayDetailSet")) || body.get("displayDetailSet").toString().length() > 1 ) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "显示消息详情参数错误");
        }

    }


    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        body.put("userId",head.get("userId"));

        int result = loginInfoDao.updateUserMessageSet(body);

        if (result < 0){
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");

        }


    }




}
