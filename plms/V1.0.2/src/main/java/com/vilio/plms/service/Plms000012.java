package com.vilio.plms.service;

import com.vilio.plms.dao.AccountDao;
import com.vilio.plms.dao.AccountDetailDao;
import com.vilio.plms.dao.BorrowApplyDao;
import com.vilio.plms.dao.OperatorRecordDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.BorrowApply;
import com.vilio.plms.pojo.OperatorRecord;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.DateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： Plms000012<br>
 * 功能：审批通过或审批不通过<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月22日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000012 extends BaseService{

    private static final Logger logger = Logger.getLogger(Plms000012.class);

    @Resource
    private BorrowApplyDao borrowApplyDao;

    @Resource
    private AccountDetailDao accountDetailDao;

    @Resource
    private AccountDao accountDao;

    @Resource
    private OperatorRecordDao operatorRecordDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //提交用户标识
        checkField(ObjectUtils.toString(body.get("operateUser")), "用户标识", null, 36);
        //校验借款订单标识号
        checkField(ObjectUtils.toString(body.get("borrowCode")), "业务标识", null, 36);
        //校验申请状态
        checkField(ObjectUtils.toString(body.get("checkStatus")), "审批状态", null, 2);
        //审批意见
        checkField(ObjectUtils.toString(body.get("comments")), "审批意见", null, 100);
        //申请状态必须为放款中或审核未通过
        if (!GlobDict.borrow_check_succ.getKey().equals(body.get("checkStatus")) && !GlobDict.borrow_check_fail.getKey().equals(body.get("checkStatus"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "申请状态不正确");
        }
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //查询当前借款订单信息
        BorrowApply borrowApplyQuery = new BorrowApply();
        borrowApplyQuery.setCode(ObjectUtils.toString(body.get("borrowCode")));
        BorrowApply borrowApply = borrowApplyDao.queryBorrowApplyByCode(borrowApplyQuery);
        //判断是否为审批中状态，如果不是审批中状态不能修改
        if (!GlobDict.order_status_audity_ing.getKey().equals(borrowApply.getApplyStatus())) {
            //不是审批中，不能修改
            throw new ErrorException(ReturnCode.BORROW_APPLY_STATUS_FAIL, "");
        }
        updateBorrowApply(borrowApply, body);
    }


    /**
     * 修改借款申请表信息
     *
     * @param borrowApply
     * @param body
     * @throws ErrorException
     */
    public void updateBorrowApply(BorrowApply borrowApply, Map<String, Object> body) throws ErrorException, Exception {
        //更改借款订单状态,并且保存审批意见
        BorrowApply borrowApplyParam = new BorrowApply();
        borrowApplyParam.setCode(ObjectUtils.toString(body.get("borrowCode")));
        if (GlobDict.borrow_check_succ.getKey().equals(body.get("checkStatus"))) {
            //审批通过，状态修改为放款中
            borrowApplyParam.setApplyStatus(GlobDict.order_status_loan_ing.getKey());
        } else if (GlobDict.borrow_check_fail.getKey().equals(body.get("checkStatus"))) {
            //审批未通过，状态修改审核未通过
            borrowApplyParam.setApplyStatus(GlobDict.order_status_audity_fail.getKey());
        }
        borrowApplyParam.setModifyDate(DateUtil.getCurrentDateTime());
        borrowApplyParam.setComments(ObjectUtils.toString(body.get("comments")));
        int ret = borrowApplyDao.updateBorrowApplyForStatus(borrowApplyParam);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        if (GlobDict.order_status_audity_fail.getKey().equals(borrowApplyParam.getApplyStatus())) {
            //审批不通过，账户明细表和账户汇总表剩余额度回滚
            Map updateQuotaParam = new HashMap();
            updateQuotaParam.put("amount", borrowApply.getAmount());
            updateQuotaParam.put("contractCode", borrowApply.getContractCode());
            //更改明细表剩余额度
            ret = accountDetailDao.updateAccountDetailQuota(updateQuotaParam);
            if (ret <= 0) {
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }
            //更新汇总表剩余额度
            ret = accountDao.updateAccountQuota(updateQuotaParam);
            if (ret <= 0) {
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }
        }
        //插入操作历史表
        OperatorRecord operatorRecord = new OperatorRecord();
        operatorRecord.setCode(getUUID());
        if (GlobDict.borrow_check_succ.getKey().equals(body.get("checkStatus"))) {
            //审批通过，历史操作为审批通过
            operatorRecord.setType(GlobDict.audit_sull.getKey());
        } else if (GlobDict.borrow_check_fail.getKey().equals(body.get("checkStatus"))) {
            //审批未通过，历史操作为审批未通过
            operatorRecord.setType(GlobDict.audit_fail.getKey());
        }
        operatorRecord.setOperateTime(DateUtil.getCurrentDateTime2());
        operatorRecord.setOperateUser(ObjectUtils.toString(body.get("operateUser")));
        operatorRecord.setCreateDate(DateUtil.getCurrentDateTime());
        operatorRecord.setModifyDate(DateUtil.getCurrentDateTime());
        operatorRecord.setStatus(GlobDict.valid.getKey());
        operatorRecord.setKeyCode(ObjectUtils.toString(body.get("borrowCode")));
        ret = operatorRecordDao.insertOperatorRecord(operatorRecord);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

}
