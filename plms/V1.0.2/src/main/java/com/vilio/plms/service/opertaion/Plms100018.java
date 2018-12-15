package com.vilio.plms.service.opertaion;

import com.vilio.plms.dao.AccountDetailDao;
import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.AccountDetail;
import com.vilio.plms.service.base.BaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms100018<br>
 * 功能：确认还款计划<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100018 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100018.class);

    @Resource
    AccountDetailDao accountDetailDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        String contractCode = body.get("contractCode").toString();
        AccountDetail accountDetail = accountDetailDao.getAccountDetailByCode(contractCode);

        if(accountDetail == null){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION,"账户明细数据不存在");
        }

        if(!"01".equals(accountDetail.getConfirmed())){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION,"还款计划状态不对，不是未确认。");
        }
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
        String contractCode = body.get("contractCode").toString();

        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setContractCode(contractCode);
        accountDetail.setConfirmed("02");
        //获取贷款详情-不包含还款计划、放款记录等关联信息
        int result = accountDetailDao.updateConfirmedByContractCode(accountDetail);

        if(result < 0){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION,"确认失败，未成功更新数据！");
        }
    }

}
