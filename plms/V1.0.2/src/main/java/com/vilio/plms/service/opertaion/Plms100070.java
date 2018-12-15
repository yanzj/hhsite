package com.vilio.plms.service.opertaion;

import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
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
 * 类名： Plms100070<br>
 * 功能：变更/解除还款计划的修改锁定（即换锁或解锁）<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100070 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100070.class);

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
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

        //获取合同编号，当前用户
        String userNo = body.get("userNo") == null ? "" : body.get("userNo").toString();
        String contractCode = body.get("contractCode") == null ? "" : body.get("contractCode").toString();
        String lockFlag = body.get("lockFlag") == null ? "1" : body.get("lockFlag").toString();//0、解锁；1、换锁

        //如果是单独的解锁请求，则只能解自己的锁
        if("0".equals(lockFlag)) {
            Map lockOwerMap = operationManagerDao.queryLockOwerByContractCode(contractCode);
            if(lockOwerMap != null){
                if(userNo.equals(lockOwerMap.get("lockOwerCode"))){
                    operationManagerDao.nullifyRepaymentScheduleDetailChangeControlByContractCode(contractCode);
                }
            }
        }

        if("1".equals(lockFlag)) {
            //先解除锁定
            operationManagerDao.nullifyRepaymentScheduleDetailChangeControlByContractCode(contractCode);
            //创建一个合同的还款计划明细数据修改控制表数据，添加锁定
            Map param = new HashMap();
            param.put("contractCode", contractCode);
            param.put("changeStatus", "00");
            param.put("changeUmId", userNo);
            operationManagerDao.createRepaymentScheduleDetailChangeControl(param);
        }

    }

}
