package com.vilio.custom.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.service.LoginComm;
import com.vilio.comm.util.BeanUtil;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.custom.dao.CustomUserDao;
import com.vilio.custom.pojo.CustomUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 类名： U200006<br>
 * 功能：对外用户信息修改接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200006 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200006.class);


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
        //必填参数
        //用户编号必填
        if (!JudgeUtil.isNull(body.get("userId")) || body.get("userId").toString().length() > 40) {
            throw new ErrorException("9996", "");
        }
        //非必填参数
        //用户名
        if (JudgeUtil.isNull(body.get("userName")) && body.get("userName").toString().length() > 40) {
            throw new ErrorException("9996", "");
        }
        //手机号
        if (JudgeUtil.isNull(body.get("mobile")) && body.get("mobile").toString().length() > 15) {
            throw new ErrorException("9996", "");
        }
        //邮箱
        if (JudgeUtil.isNull(body.get("email")) && body.get("email").toString().length() > 50) {
            throw new ErrorException("9996", "");
        }
        //姓名
        if (JudgeUtil.isNull(body.get("fullName")) && body.get("fullName").toString().length() > 20) {
            throw new ErrorException("9996", "");
        }
        //校验性别
        if (JudgeUtil.isNull(body.get("sex"))) {
            if (body.get("sex").toString().length() > 2) {
                throw new ErrorException("9996", "");
            }
            if (!GlobDict.sex_man.getKey().equals(body.get("sex")) && !GlobDict.sex_woman.getKey().equals(body.get("sex"))){
                throw new ErrorException("9996", "");
            }
        }
        //生日
        if (JudgeUtil.isNull(body.get("birthday")) && body.get("birthday").toString().length() > 10) {
            throw new ErrorException("9996", "");
        }
        //出生地
        if (JudgeUtil.isNull(body.get("birthAddr")) && body.get("birthAddr").toString().length() > 100) {
            throw new ErrorException("9996", "");
        }
        //昵称
        if (JudgeUtil.isNull(body.get("nick")) && body.get("nick").toString().length() > 50) {
            throw new ErrorException("9996", "");
        }
        //头像地址
        if (JudgeUtil.isNull(body.get("headImg")) && body.get("headImg").toString().length() > 200) {
            throw new ErrorException("9996", "");
        }
    }

    /**
     * 用户信息修改接口
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        //查询用户是否存在
        CustomUser qryUserParam = new CustomUser();
        qryUserParam.setUserId(body.get("userId").toString());
        qryUserParam.setSystemId(head.get("systemId").toString());
        CustomUser user = customUserDao.queryUser(qryUserParam);
        if (null == user) {
            throw new ErrorException("9004", "");
        }
        loginComm.checkUserStatus(user.getStatus());
        //如果用户名不存在，则可以修改用户名，如果用户名存在，不能修改用户名
        if (JudgeUtil.isNull(body.get("userName"))) {
            if (!body.get("userName").equals(user.getUserName())){
                if (JudgeUtil.isNull(user.getUserName())) {
                    throw new ErrorException("0016", "");
                }
                user.setUserName(body.get("userName").toString());
            }
        }
        //定义修改用户参数
        user.setMobile(body.get("mobile") == null ? null : body.get("mobile").toString());
        user.setEmail(body.get("email") == null ? null : body.get("email").toString());
        user.setFullName(body.get("fullName") == null ? null : body.get("fullName").toString());
        user.setSex(body.get("sex") == null ? null : body.get("sex").toString());
        user.setBirthday(body.get("birthday") == null ? null : body.get("birthday").toString());
        user.setBirthAddr(body.get("birthAddr") == null ? null : body.get("birthAddr").toString());
        user.setNick(body.get("nick") == null ? null : body.get("nick").toString());
        user.setHeadImg(body.get("headImg") == null ? null : body.get("headImg").toString());
        int ret = customUserDao.updateUserInfo(user);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        resultMap.putAll(BeanUtil.transBean2Map(user));
    }
}
