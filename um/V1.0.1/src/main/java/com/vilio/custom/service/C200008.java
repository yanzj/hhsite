package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.service.LoginComm;
import com.vilio.custom.dao.CustomUserDao;
import com.vilio.custom.pojo.CustomUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： C200008<br>
 * 功能：交易密码修改接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月20日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200008 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200008.class);

    @Resource
    private CustomUserDao customUserDao;

    @Resource
    private LoginComm loginComm;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //用户编码是否为空
        checkField(ObjectUtils.toString(body.get("userId")), "用户编号", null, 40);
        //判断原交易密码
        checkField(ObjectUtils.toString(body.get("oldTransPassword")), "原密码", null, 40);
        //判断新密码
        checkField(ObjectUtils.toString(body.get("newTransPassword")), "新密码", null, 40);
        //判断原密码和新密码
        if (!ObjectUtils.toString(body.get("newTransPassword")).equals(ObjectUtils.toString(body.get("reNewTransPassword")))) {
            throw new ErrorException("9998", "新交易密码与确认交易密码不一致");
        }

    }

    /**
     * 交易密码验证接口
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        //查询用户信息
        CustomUser qryUserParam = new CustomUser();
        qryUserParam.setUserId(body.get("userId").toString());
        qryUserParam.setSystemId(head.get(GlobParam.PARAM_SYSTEM_ID).toString());
        CustomUser user = customUserDao.queryUser(qryUserParam);
        if (null==user){
            throw new ErrorException("9004", "");
        }
        //用户是否有效(状态是否存在，用户状态是否有效)
        loginComm.checkUserStatus(user.getStatus());
        if (!body.get("oldTransPassword").equals(user.getTransPassword())){
            //输入错误的话，对交易密码错误次数进行处理
            dealTransNum(user);
        }
        //如果输入正确，那么重置交易密码错误次数
        user.setTransError("0");
        customUserDao.updateTransErrorByUserId(user);
        //修改密码
        Map updateParam = new HashMap();
        updateParam.put("userId", body.get("userId"));
        updateParam.put("oldTransPassword", body.get("oldTransPassword"));
        updateParam.put("newTransPassword", body.get("newTransPassword"));
        updateParam.put("transError", "0");
        updateParam.put("status", GlobDict.user_status_valid.getKey());
        int ret = customUserDao.updateTransPassword(updateParam);
        if (ret <= 0) {
            logger.error("更新密码失败：" + ret);
            throw new ErrorException("0010", "");
        }
        //修改成功
        logger.info("修改成功");
    }


    /**
     * 对交易密码错误次数进行处理
     *
     * @param user
     */
    private void dealTransNum(CustomUser user) throws ErrorException {
        //登录次数加1
        String transError = user.getTransError();
        user.setTransError(transError == null || transError == "" ? "1" : String.valueOf(Integer.parseInt(transError) + 1));
        int ret = customUserDao.updateTransErrorByUserId(user);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        if (Integer.parseInt(user.getTransError()) >= GlobParam.customTransError) {
            //用户交易锁定
            user.setTransHashLock(GlobDict.trans_hash_lock.getKey());
            user.setTransLockTime(String.valueOf(System.currentTimeMillis()));
            ret = customUserDao.updateTransHashLockByUserId(user);
            if (ret <= 0) {
                throw new ErrorException("9997", "");
            }
            throw new ErrorException("0103", "原交易密码错误，您已输错" + GlobParam.customLoginError + "次，交易关闭。如需帮助，请联系您的客户经理。");
        } else {
            throw new ErrorException("0104", "原交易密码错误，您还可以输入" + (GlobParam.customLoginError - Integer.parseInt(user.getTransError())) + "次");
        }
    }


}
