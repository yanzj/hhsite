package com.vilio.pcfs.service;

import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Pcfs000025<br>
 * 功能：退出登录<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月22日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000025 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000025.class);

    @Resource
    private LoginComn loginComn;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //退出登录
        Map param = new HashMap();
        param.put(GlobParam.PARAM_USER_ID,head.get(GlobParam.PARAM_USER_ID));
        loginComn.deleteSession(param);
    }
}
