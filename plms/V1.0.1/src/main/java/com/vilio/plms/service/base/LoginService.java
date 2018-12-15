package com.vilio.plms.service.base;

import com.vilio.plms.dao.CommDao;
import com.vilio.plms.dao.LoginInfoDao;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.LoginInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2016/12/30.
 */
@Service("loginService")
public class LoginService {

    @Resource
    private LoginInfoDao loginInfoDao;

    @Resource
    private CommDao commDao;

    /**
     * 获取当前用户的session
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map getSession(Map map) throws Exception {
        String userNo = (String) map.get("userNo");
        return loginInfoDao.selectLoginInfoByUserNo(userNo);
    }

    /**
     * 设置当前用户的session
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map setSession(Map map) throws Exception {
        Map result = new HashMap();

        String userNo = map.get(Fields.PARAM_USER_NO) == null ? null : map.get(Fields.PARAM_USER_NO).toString();
        String userName = map.get(Fields.PARAM_USER_NAME) == null ? null : map.get(Fields.PARAM_USER_NAME).toString();
        String fullName = map.get(Fields.PARAM_FULL_NAME) == null ? null : map.get(Fields.PARAM_FULL_NAME).toString();
        String email = map.get(Fields.PARAM_E_MAIL) == null ? null : map.get(Fields.PARAM_E_MAIL).toString();
        String mobile = map.get(Fields.PARAM_MOBILE) == null ? null : map.get(Fields.PARAM_MOBILE).toString();
        String cityCode = map.get(Fields.PARAM_CITY_CODE) == null ? null : map.get(Fields.PARAM_CITY_CODE).toString();
        String cityName = map.get(Fields.PARAM_CITY_NAME) == null ? null : map.get(Fields.PARAM_CITY_NAME).toString();
        String groupId = map.get(Fields.PARAM_GROUP_ID) == null ? null : map.get(Fields.PARAM_GROUP_ID).toString();
        String groupName = map.get(Fields.PARAM_GROUP_NAME) == null ? null : map.get(Fields.PARAM_GROUP_NAME).toString();

        Map loginInfoMap = loginInfoDao.selectLoginInfoByUserNo(userNo);
        if (null != loginInfoMap && !loginInfoMap.isEmpty()) {
            loginInfoMap.put("systemTimestamp", map.get("systemTimestamp"));
            loginInfoMap.put("clientTimestamp", map.get("clientTimestamp"));
            loginInfoMap.put(Fields.PARAM_USER_NAME, userName);
            loginInfoMap.put(Fields.PARAM_FULL_NAME, fullName);
            loginInfoMap.put(Fields.PARAM_E_MAIL, email);
            loginInfoMap.put(Fields.PARAM_MOBILE, mobile);
            loginInfoMap.put(Fields.PARAM_CITY_CODE, cityCode);
            loginInfoMap.put(Fields.PARAM_CITY_NAME, cityName);
            loginInfoMap.put(Fields.PARAM_GROUP_ID, groupId);
            loginInfoMap.put(Fields.PARAM_GROUP_NAME, groupName);
            loginInfoDao.updateLoginInfoByUserNo(loginInfoMap);
        } else {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setCode(commDao.getUUID());
            loginInfo.setUserNo(userNo);
            if (null != map.get("systemTimestamp")) {
                loginInfo.setSystemTimestamp((String) map.get("systemTimestamp"));
            }
            if (null != map.get("clientTimestamp")) {
                loginInfo.setClientTimestamp((String) map.get("clientTimestamp"));
            }
            loginInfo.setUserName(userName);
            loginInfo.setFullName(fullName);
            loginInfo.setEmail(email);
            loginInfo.setMobile(mobile);
            loginInfo.setCityCode(cityCode);
            loginInfo.setCityName(cityName);
            loginInfo.setGroupId(groupId);
            loginInfo.setGroupName(groupName);
            loginInfoDao.insertSelective(loginInfo);
        }

        Map data = new HashMap();
        result.put("data", data);
        result.put("returnCode", ReturnCode.SUCCESS_CODE);

        return result;
    }

    /**
     * 删除某用户的session
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map removeSession(Map map) throws Exception {
        Map result = new HashMap();

        String userNo = (String) map.get(Fields.PARAM_USER_NO);

        loginInfoDao.deleteLoginInfoByUserNo(userNo);

        Map data = new HashMap();
        result.put("data", data);
        result.put("returnCode", ReturnCode.SUCCESS_CODE);

        return result;
    }

}
