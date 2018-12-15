package com.vilio.plms.service;

import com.vilio.plms.dao.OperatorRecordDao;
import com.vilio.plms.dao.ReceiptsRecordDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.OperatorRecord;
import com.vilio.plms.pojo.ReceiptsRecord;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JudgeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： Plms000015<br>
 * 功能：资金入账<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000015 extends BaseService{

    private static final Logger logger = Logger.getLogger(Plms000015.class);

    @Resource
    private ReceiptsRecordDao receiptsRecordDao;

    @Resource
    private OperatorRecordDao operatorRecordDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //入账日期
        checkField(ObjectUtils.toString(body.get("receiptsDate")), "入账日期", null, 10);
        //入账金额
        if (!"00".equals(JudgeUtil.isMoney(ObjectUtils.toString(body.get("receiptsAmount")), 9, 2))) {
            //金额校验失败
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "入账金额");
        }
        //借款合同编码
        checkField(ObjectUtils.toString(body.get("contractCode")), "合同编码", null, 36);
        //资方账户或者宏获账户
        checkField(ObjectUtils.toString(body.get("accountType")), "账户类型", null, null);
        if (!GlobDict.account_type_vilio.getKey().equals(ObjectUtils.toString(body.get("accountType")))
                &&!GlobDict.account_type_fund_side.getKey().equals(ObjectUtils.toString(body.get("accountType")))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING,"账户类型错误");
        }

        //提交用户标识
        checkField(ObjectUtils.toString(body.get("operateUser")), "用户标识", null, 36);

        //还款申请表code
        //checkField(ObjectUtils.toString(body.get("repaymentApplyCode")), "还款申请表", null, null);
    }


    /**
     * 主业务流程实现
     *
     * @param head
     * @param body
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //接收到请求后，先把资金入账流水入库，等待定时任务启动
        if (!JudgeUtil.isNull(body.get("code"))) {
            body.put("code",getUUID());
        }
        ReceiptsRecord receiptsRecord = new ReceiptsRecord();
        receiptsRecord.setCode(ObjectUtils.toString(body.get("code")));
        receiptsRecord.setReceiptsDate(ObjectUtils.toString(body.get("receiptsDate")));
        receiptsRecord.setReceiptsAmount(ObjectUtils.toString(body.get("receiptsAmount")));
        receiptsRecord.setContractCode(ObjectUtils.toString(body.get("contractCode")));
        receiptsRecord.setAccountType(ObjectUtils.toString(body.get("accountType")));
        receiptsRecord.setCreateDate(DateUtil.getCurrentDateTime2());
        receiptsRecord.setModifyDate(DateUtil.getCurrentDateTime2());
        receiptsRecord.setStatus(GlobDict.valid.getKey());
        receiptsRecord.setDealStatus(GlobDict.receipts_deal_stauts_init.getKey());
        receiptsRecord.setDealBatchNo("");
        receiptsRecord.setDealMsg("初始化");
        receiptsRecord.setRepaymentApplyCode(ObjectUtils.toString(body.get("repaymentApplyCode")));
        receiptsRecord.setDealUser(ObjectUtils.toString(body.get("operateUser")));
        receiptsRecord.setDealTime(DateUtil.getCurrentDateTime2());
        receiptsRecord.setRemark(ObjectUtils.toString(body.get("remark")));
        receiptsRecord.setFundSource(ObjectUtils.toString(body.get("fundSource")));
        int ret = receiptsRecordDao.saveReceiptsRecord(receiptsRecord);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //插入操作历史表
        OperatorRecord operatorRecord = new OperatorRecord();
        operatorRecord.setCode(getUUID());
        operatorRecord.setType(GlobDict.receipts_init.getKey());
        operatorRecord.setOperateTime(DateUtil.getCurrentDateTime2());
        operatorRecord.setOperateUser(ObjectUtils.toString(body.get("operateUser")));
        operatorRecord.setCreateDate(DateUtil.getCurrentDateTime());
        operatorRecord.setModifyDate(DateUtil.getCurrentDateTime());
        operatorRecord.setStatus(GlobDict.valid.getKey());
        operatorRecord.setKeyCode(receiptsRecord.getCode());
        ret = operatorRecordDao.insertOperatorRecord(operatorRecord);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }

}
