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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： Plms000016<br>
 * 功能：资金入账删除（<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000016 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000016.class);

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
        //资金入账code
        checkField(ObjectUtils.toString(body.get("receiptsRecordCode")), "入账流水标识", null, 36);
    }


    /**
     * 主业务流程实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //查询资金入账流水信息
        ReceiptsRecord receiptsRecordParam = new ReceiptsRecord();
        receiptsRecordParam.setCode(ObjectUtils.toString(body.get("receiptsRecordCode")));
        ReceiptsRecord receiptsRecord = receiptsRecordDao.queryReceiptsRecordByCode(receiptsRecordParam);
        if (receiptsRecord==null) {
            throw new ErrorException(ReturnCode.RECEIPTS_RECORD_FAIL, "");
        }
        //判断状态是否为待处理状态、处理失败，直接修改表
        if (GlobDict.receipts_deal_stauts_init.getKey().equals(receiptsRecord.getDealStatus()) || GlobDict.receipts_deal_stauts_fail.getKey().equals(receiptsRecord.getDealStatus())) {
            //直接修改表结构，标记为失效，修改账户余额
            receiptsRecord.setStatus(GlobDict.un_valid.getKey());
            int ret = receiptsRecordDao.updateReceiptsRecordStatusByCode(receiptsRecord);
            if (ret <= 0) {
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }
            return;
        } else if (GlobDict.receipts_deal_stauts_ing.getKey().equals(receiptsRecord.getDealStatus())) {
            //处理中状态，不能进行删除操作
            throw new ErrorException(ReturnCode.RECEIPTS_RECORD_STATUS_INF, "");
        } else if (GlobDict.receipts_deal_stauts_succ.getKey().equals(receiptsRecord.getDealStatus())) {
            //处理成功状态，更新表为删除处理中状态，等待删除定时任务启动
            receiptsRecord.setDealStatus(GlobDict.receipts_deal_stauts_delete.getKey());
            receiptsRecord.setDealMsg("原状态处理成功，更新为删除待处理状态");
            int ret = receiptsRecordDao.updateReceiptsRecordDealStatusByCode(receiptsRecord);
            if (ret <= 0) {
                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
            }
        } else {
            //状态不正确，不能删除
            throw new ErrorException(ReturnCode.RECEIPTS_RECORD_STATUS_FAIL, "");
        }
        //插入操作历史表
        OperatorRecord operatorRecord = new OperatorRecord();
        operatorRecord.setCode(getUUID());
        operatorRecord.setType(GlobDict.receipts_delete.getKey());
        operatorRecord.setOperateTime(DateUtil.getCurrentDateTime2());
        operatorRecord.setOperateUser(ObjectUtils.toString(body.get("operateUser")));
        operatorRecord.setCreateDate(DateUtil.getCurrentDateTime());
        operatorRecord.setModifyDate(DateUtil.getCurrentDateTime());
        operatorRecord.setStatus(GlobDict.valid.getKey());
        operatorRecord.setKeyCode(receiptsRecord.getCode());
        int ret = operatorRecordDao.insertOperatorRecord(operatorRecord);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }
}
