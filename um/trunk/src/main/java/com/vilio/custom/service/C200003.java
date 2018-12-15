package com.vilio.custom.service;

import com.vilio.comm.dao.CommDao;
import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.glob.GlobParam;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.BeanUtil;
import com.vilio.comm.util.CalendarUtil;
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
 * 类名： U209001<br>
 * 功能：对外用户注册业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class C200003 extends BaseService {

    private static final Logger logger = Logger.getLogger(C200003.class);

    @Resource
    private CustomUserDao customUserDao;

    @Resource
    private CommDao commDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //必填参数的校验
        //密码必填，且不大于50位
        if (!JudgeUtil.isNull(body.get("password")) || body.get("password").toString().length() > 50) {
            throw new ErrorException("9996", "");
        }
        //非必填参数的校验
        //用户名非必填，且不大于40位
        if (JudgeUtil.isNull(body.get("userName")) && body.get("userName").toString().length() > 40) {
            throw new ErrorException("9996", "");
        }
        //校验手机号
        if (JudgeUtil.isNull(body.get("mobile")) && body.get("mobile").toString().length() > 15) {
            throw new ErrorException("9996", "");
        }
        //校验邮箱
        if (JudgeUtil.isNull(body.get("email")) && body.get("email").toString().length() > 50) {
            throw new ErrorException("9996", "");
        }
        //用户名、手机号、邮箱必须有一个，因为登录只支持这三种方式
        if (!JudgeUtil.isNull(body.get("email"))&&!JudgeUtil.isNull(body.get("mobile"))&&!JudgeUtil.isNull(body.get("userName"))){
            throw new ErrorException("9996", "");
        }
        //校验姓名
        if (JudgeUtil.isNull(body.get("full_name")) && body.get("full_name").toString().length() > 20) {
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
        //校验生日
        if (JudgeUtil.isNull(body.get("birthday")) && body.get("birthday").toString().length() > 10) {
            throw new ErrorException("9996", "");
        }
        //校验出生地
        if (JudgeUtil.isNull(body.get("birth_addr")) && body.get("birth_addr").toString().length() > 100) {
            throw new ErrorException("9996", "");
        }
        //校验昵称
        if (JudgeUtil.isNull(body.get("nick")) && body.get("nick").toString().length() > 50) {
            throw new ErrorException("9996", "");
        }
        //校验头像地址
        if (JudgeUtil.isNull(body.get("head_img")) && body.get("head_img").toString().length() > 200) {
            throw new ErrorException("9996", "");
        }
    }

    /**
     * 注册业务主流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        CustomUser checkUserParam = new CustomUser();
        CustomUser checkUser = new CustomUser();
        checkUserParam.setSystemId(head.get(GlobParam.PARAM_SYSTEM_ID).toString());
        if (JudgeUtil.isNull(body.get("userName"))){
            //校验用户名是否被注册
            checkUserParam.setUserName(body.get("userName").toString());
            //查询用户
            checkUser = customUserDao.queryUser(checkUserParam);
            if (null != checkUser) {
                throw new ErrorException("0012", "");
            }
            checkUserParam.setUserName(null);
        }
        if (JudgeUtil.isNull(body.get("mobile"))) {
            //校验手机号是否存在
            checkUserParam.setMobile(body.get("mobile").toString());
            checkUser = customUserDao.queryUser(checkUserParam);
            if (null != checkUser) {
                throw new ErrorException("0013", "");
            }
            checkUserParam.setMobile(null);
        }
        if (JudgeUtil.isNull(body.get("email"))) {
            //校验邮箱
            checkUserParam.setEmail(body.get("email").toString());
            checkUser = customUserDao.queryUser(checkUserParam);
            if (null != checkUser) {
                throw new ErrorException("0014", "");
            }
            checkUserParam.setEmail(null);
        }
        //生成userid
        Long seq = commDao.querySequence("USER_ID");
        String userId = CalendarUtil.getNowTime("yyyyMMddHHmmss") + String.format("%08d", seq);
        CustomUser addUserParam = new CustomUser();
        addUserParam.setUserId(userId);
        addUserParam.setUserName(body.get("userName") == null ? null : body.get("userName").toString());
        addUserParam.setMobile(body.get("mobile") == null ? null : body.get("mobile").toString());
        addUserParam.setPassword(body.get("password") == null ? null : body.get("password").toString());
        addUserParam.setEmail(body.get("email") == null ? null : body.get("email").toString());
        //默认有效
        addUserParam.setStatus(GlobDict.user_status_valid.getKey());
        //默认首次登录
        addUserParam.setFirstLogin(GlobDict.first_login.getKey());
        addUserParam.setFullName(body.get("fullName") == null ? null : body.get("fullName").toString());
        addUserParam.setSex(body.get("sex") == null ? null : body.get("sex").toString());
        addUserParam.setBirthday(body.get("birthday") == null ? null : body.get("birthday").toString());
        addUserParam.setBirthAddr(body.get("birthAddr") == null ? null : body.get("birthAddr").toString());
        addUserParam.setNick(body.get("nick") == null ? null : body.get("nick").toString());
        addUserParam.setHeadImg(body.get("headImg") == null ? null : body.get("headImg").toString());
        addUserParam.setRegisterChl(head.get(GlobParam.PARAM_SYSTEM_ID).toString());
        //登录错误次数默认0
        addUserParam.setLoginError("0");
        //默认用户未锁定
        addUserParam.setHashLock(GlobDict.un_hash_lock.getKey());
        addUserParam.setSystemId(head.get(GlobParam.PARAM_SYSTEM_ID).toString());
        //插入用户
        int ret = customUserDao.insertUser(addUserParam);
        if (ret <= 0) {
            throw new ErrorException("0015", "");
        }
        //注册成功后，把用户基本信息返回
        resultMap.putAll(BeanUtil.transBean2Map(addUserParam));
    }


}
