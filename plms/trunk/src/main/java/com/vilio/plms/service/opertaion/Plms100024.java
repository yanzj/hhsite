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
 * 类名： Plms100024<br>
 * 功能：修改还款计划明细临时数据<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100024 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100024.class);

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
        //查询对应放款信息
        String paidCode = body.get("paidCode").toString();
        PaidInfo paidInfo = new PaidInfo();
        paidInfo.setCode(paidCode);
        paidInfo = paidInfoDao.queryPaidInfoByCode(paidInfo);

        //查询对应合同信息
        Contract contract = new Contract();
        contract.setCode(paidInfo.getContractCode());
        contract = contractDao.qryContract(contract);

        //校验应还本金
        if(null != body.get("principal") && !"".equals(body.get("principal").toString().trim())){
            String principal = body.get("principal").toString();

            //应还本金大于放款金额
            if(operationManagerService.isPrincipalGreaterThanPaidAmount(principal,paidInfo)){
                throw new ErrorException(ReturnCode.PRINCIPAL_GREATER_THAN_PAID_AMOUNT,"");
            }
        }

        //校验应还利息
        if(null != body.get("interest") && !"".equals(body.get("interest").toString().trim())){
            String interest = body.get("interest").toString();

            //应还利息大于放款信息表记录的应还利息
            if(operationManagerService.isInterestGreaterThanTotalInterest(interest,paidInfo)){
                throw new ErrorException(ReturnCode.INTEREST_GREATER_THAN_TOTAL_INTEREST,"");
            }
        }

        //校验应还服务费
        if(null != body.get("serviceFee") && !"".equals(body.get("serviceFee").toString().trim())){
            String serviceFee = body.get("serviceFee").toString();

            //应还服务费大于放款信息表记录的应还服务费
            if(operationManagerService.isServiceFeeGreaterThanTotalServiceFee(serviceFee,paidInfo)){
                throw new ErrorException(ReturnCode.SERVICE_FEE_GREATER_THAN_TOTAL_SERVICE_FEE,"");
            }
        }

        //校验应还保证金
        if(null != body.get("bail") && !"".equals(body.get("bail").toString().trim())){
            String bail = body.get("bail").toString();

            //应还服务费大于放款信息表记录的应还服务费
            if(operationManagerService.isBailGreaterThanTotalServiceFee(bail,paidInfo)){
                throw new ErrorException(ReturnCode.BAIL_GREATER_THAN_TOTAL_SERVICE_FEE,"");
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

        Map tmpMap = new HashMap();
        tmpMap.put("code",scheduleDetailTmpCode);

        if(null != body.get("repaymentDate") && !"".equals(body.get("repaymentDate").toString().trim())){
            String repaymentDate = body.get("repaymentDate").toString();
            tmpMap.put("repaymentDate",repaymentDate);
        }

        if(null != body.get("principal") && !"".equals(body.get("principal").toString().trim())){
            String principal = body.get("principal").toString();
            tmpMap.put("principal",principal);
        }

        if(null != body.get("interest") && !"".equals(body.get("interest").toString().trim())){
            String interest = body.get("interest").toString();
            tmpMap.put("interest",interest);
        }

        if(null != body.get("serviceFee") && !"".equals(body.get("serviceFee").toString().trim())){
            String serviceFee = body.get("serviceFee").toString();
            tmpMap.put("serviceFee",serviceFee);
        }

        if(null != body.get("bail") && !"".equals(body.get("bail").toString().trim())){
            String bail = body.get("bail").toString();
            tmpMap.put("bail",bail);
        }

        operationManagerDao.updateRepaymentScheduleDetailTmp(tmpMap);

    }

}
