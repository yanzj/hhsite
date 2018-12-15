package com.vilio.um.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.glob.GlobDict;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.um.dao.UmUserDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： U100007<br>
 * 功能：根据agentId查询用户信息<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class U100007 extends BaseService {

    private static final Logger logger = Logger.getLogger(U100007.class);

    @Resource
    private UmUserDao umUserDao;

    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (!JudgeUtil.isNull(body.get("agentIds")) && !JudgeUtil.isNull(body.get("userIds"))) {
            throw new ErrorException("9998", "");
        }
    }

    /**
     * 根据agentId查询用户信息主流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        //根据机构查询用户信息
        Map param = new HashMap();
        param.put("systemId", body.get("systemId"));
        if (JudgeUtil.isNull(body.get("userIds")) && ((List) body.get("userIds")).size() > 0) {
            List userIds = (List) body.get("userIds");
            param.put("userIds", userIds);
        }
        if (JudgeUtil.isNull(body.get("agentIds")) && ((List) body.get("agentIds")).size() > 0) {
            List agentIds = (List) body.get("agentIds");
            param.put("agentIds", agentIds);
        }
        if (JudgeUtil.isNull(body.get("userStatus"))) {
            param.put("userStatus", body.get("userStatus"));
        }else{
            param.put("userStatus", GlobDict.user_status_valid.getKey());
        }
        List users = this.umUserDao.queryUserListByAgentIds(param);
        resultMap.put("users", users);
    }

}
