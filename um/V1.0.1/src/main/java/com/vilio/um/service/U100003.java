package com.vilio.um.service;

import com.vilio.comm.exception.ErrorException;
import com.vilio.comm.service.BaseService;
import com.vilio.comm.util.JudgeUtil;
import com.vilio.um.dao.UmRoleDao;
import com.vilio.um.pojo.UmRole;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： U100003<br>
 * 功能：查询角色信息业务处理类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月21日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class U100003 extends BaseService {

    private static final Logger logger = Logger.getLogger(U100003.class);

    @Resource
    private UmRoleDao umRoleDao;


    /**
     * 参数校验
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //校验非必填字段
        //用户编号
        if (JudgeUtil.isNull(body.get("userId")) && body.get("userId").toString().length() > 40) {
            throw new ErrorException("9998", "");
        }
        //角色编码
        if (JudgeUtil.isNull(body.get("roleId")) && body.get("roleId").toString().length() > 40) {
            throw new ErrorException("9998", "");
        }
        //角色状态
        if (JudgeUtil.isNull(body.get("roleStatus")) && body.get("roleStatus").toString().length() > 2) {
            throw new ErrorException("9998", "");
        }
        //查询系统
        if (JudgeUtil.isNull(body.get("systemId")) && body.get("systemId").toString().length() > 40) {
            throw new ErrorException("9998", "");
        }
    }

    /**
     * 查询角色业务主流程处理
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
        Map qryParam = new HashMap();
        qryParam.put("userId",body.get("userId")==null?"":body.get("userId").toString());
        qryParam.put("roleId",body.get("roleId")==null?"":body.get("roleId").toString());
        qryParam.put("roleStatus",body.get("roleStatus")==null?"":body.get("roleStatus").toString());
        qryParam.put("systemId",body.get("systemId")==null?"":body.get("systemId").toString());
        List<UmRole> roles = umRoleDao.queryRole(qryParam);
        resultMap.put("roles",roles);
    }

}
