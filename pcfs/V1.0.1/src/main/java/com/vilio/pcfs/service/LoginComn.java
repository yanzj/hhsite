package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.LoginInfoDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.pojo.LoginInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： LoginComn<br>
 * 功能：登录公共处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月15日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class LoginComn {

    @Resource
    private LoginInfoDao loginInfoDao;

    /**
     * 设置session
     *
     * @param session
     */
    public void setSession(LoginInfo session) throws ErrorException {
        //查询登录信息表
        LoginInfo loginInfo = loginInfoDao.selectLoginInfo(session);
        //设置登录状态为登录中
        session.setLoginStatus(GlobDict.login_status_effective.getKey());
        if (null != loginInfo) {
            //登录信息表中存在，更新登录信息
            int ret = loginInfoDao.updateLoginInfo(session);
            if (ret <= 0) {
                throw new ErrorException("SYS9997", "");
            }
        } else {
            //登录信息表中没有，则插入登录信息表
            int ret = loginInfoDao.insert(session);
            if (ret <= 0) {
                throw new ErrorException("SYS9997", "");
            }
        }
    }

    /**
     * 获取sesion值
     *
     * @param session
     * @return
     */
    public LoginInfo getSession(LoginInfo session) {
        //查询登录信息表
        //登录状态设置为登录中
        session.setLoginStatus(GlobDict.login_status_effective.getKey());
        LoginInfo loginInfo = loginInfoDao.selectLoginInfo(session);
        return loginInfo;
    }

    /**
     * 清空sesion信息(这里修改为不删除登录的用户信息，只修改登录状态为登出状态  wangxf by 20170809)
     *
     * @param param
     * @return
     */
    public int deleteSession(Map param) {
        return loginInfoDao.updateLoginStatusInvalidById(param);
    }
}
