package com.vilio.plms.service.opertaion;

import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.service.base.BaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Plms100071<br>
 * 功能：根据合同编号获取当前锁🔒的拥有人<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100071 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100071.class);

    @Resource
    OperationManagerDao operationManagerDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        //获取合同编号，当前用户
        String userNo = body.get("userNo") == null ? "" : body.get("userNo").toString();
        String contractCode = body.get("contractCode") == null ? "" : body.get("contractCode").toString();

        Map lockOwerMap = operationManagerDao.queryLockOwerByContractCode(contractCode);
        if (lockOwerMap != null) {
            if (userNo.equals(lockOwerMap.get("lockOwerCode"))) {
                resultMap.put("canModify", "Y");
            } else {
                resultMap.put("canModify", "N");
            }
            resultMap.put("lockOwner", lockOwerMap.get("lockOwerName"));
        } else {
            resultMap.put("canModify", "Y");
            resultMap.put("lockOwner", "");
        }

    }

}
