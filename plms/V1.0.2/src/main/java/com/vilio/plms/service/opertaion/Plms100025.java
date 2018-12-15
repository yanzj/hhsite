package com.vilio.plms.service.opertaion;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.ContractDao;
import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.dao.PaidInfoDao;
import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.Contract;
import com.vilio.plms.pojo.PaidInfo;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.MathUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms100025<br>
 * 功能：删除还款计划明细临时数据<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100025 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100025.class);

    @Resource
    OperationManagerDao operationManagerDao;
    @Resource
    ContractDao contractDao;
    @Resource
    PaidInfoDao paidInfoDao;
    @Resource
    OperationManagerService operationManagerService;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException  {
        String userNo = body.get("userNo") == null ? "" : body.get("userNo").toString();
        String contractCode = body.get("contractCode") == null ? "" : body.get("contractCode").toString();

        Map lockOwerMap = operationManagerDao.queryLockOwerByContractCode(contractCode);
        if(lockOwerMap != null){
            if(!userNo.equals(lockOwerMap.get("lockOwerCode"))){
                throw new ErrorException(ReturnCode.LOCK_USER_ISNOT_CURRENT_USER, lockOwerMap.get("lockOwerName") == null ? "" : lockOwerMap.get("lockOwerName").toString());
            }
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
        String scheduleDetailTmpCode = body.get("scheduleDetailTmpCode").toString();
        String paidCode = body.get("paidCode").toString();

        //作废当前记录
        operationManagerDao.nullifyRepaymentScheduleDetailTmpByCode(scheduleDetailTmpCode);

        //按还款日期变更
        List tmpDataList = operationManagerDao.queryRepaymentScheduleDetailTmpListByPaidCode(paidCode);
        for(int i=0; null!=tmpDataList && i < tmpDataList.size(); i++){
            Map result = (Map)tmpDataList.get(i);
            Map tmpMap = new HashMap();
            tmpMap.put("code",result.get("scheduleDetailTmpCode"));
            tmpMap.put("totalPeriod",tmpDataList.size());
            tmpMap.put("currentPeriod",i+1);
            operationManagerDao.updatePeriodForRepaymentScheduleDetailTmp(tmpMap);
        }

    }

}
