package com.vilio.plms.dao;

import java.util.List;
import java.util.Map;

/**
 * 运营管理DAO
 * Created by xiezhilei on 2017/8/1.
 */
public interface OperationManagerDao {

    /**
     * 根据不同入参查询合同信息列表
     */
    public List queryContractList(Map paramMap);

    /**
     * 根据合同编码，作废对应的还款计划明细表临时数据
     */
    public int nullifyRepaymentScheduleDetailTmpByContractCode(String contractCode);

    /**
     *  根据临时表唯一代码，查询对应的还款计划明细表临时数据
     */
    public int nullifyRepaymentScheduleDetailTmpByCode(String scheduleDetailTmpCode);

    /**
     * 根据合同编码，创建对应的还款计划明细表临时数据
     */
    public int createRepaymentScheduleDetailTmpByContractCode(String contractCode);

    /**
     * 创建一个合同的还款计划明细数据修改控制表数据
     */
    public int createRepaymentScheduleDetailChangeControl(Map paramMap);

    /**
     *  根据合同编码，作废一个合同的还款计划明细数据修改控制表数据
     */
    public int nullifyRepaymentScheduleDetailChangeControlByContractCode(String contractCode);

    /**
     *  根据放款信息代码，查询对应的还款计划明细表临时数据
     */
    public List queryRepaymentScheduleDetailTmpListByPaidCode(String paidCode);

    public List queryRepaymentScheduleDetailListByPaidCode(String paidCode);

    /**
     *  根据放款信息代码、还款日期查询还款计划明细表临时数据记录数
     */
    public int countRepaymentScheduleDetailTmpByPaidCodeAndRepaymentDate(Map paramMap);

    /**
     *  通过copy，新增一条还款计划明细临时记录
     */
    public int createOneRepaymentScheduleDetailTmpByPaidCode(Map paramMap);

    /**
     *  变更还款计划记录的期数
     */
    public int updatePeriodForRepaymentScheduleDetailTmp(Map paramMap);

    public int updatePeriodForRepaymentScheduleDetail(Map paramMap);

    /**
     *  变更还款计划记录的应还信息
     */
    public int updateRepaymentScheduleDetailTmp(Map paramMap);

    /**
     *  根据合同编码，查询删除过的还款计划明细(指还款计划明细中存在，但是还款计划明细临时表中不存在的记录。用code做对比)
     */
    public List queryDeletedRepaymentScheduleDetail(String contractCode);

    /**
     *  根据合同编码，查询变更过的还款计划明细(指还款计划明细中存在，还款计划明细临时表中也存在，但是三项费用发生变更的记录。用code做对比)
     */
    public List queryUpdatedRepaymentScheduleDetail(String contractCode);

    /**
     *  根据合同编码，查询新增的还款计划明细(指还款计划明细中不存在，但是还款计划明细临时表中存在的记录。用code做对比)
     */
    public List queryInsertedRepaymentScheduleDetail(String contractCode);

    public Map queryRepaymentScheduleByPaidCodeAndRepaymentDate(Map map);

    /**
     * @Description: 根据合同编码，查询当前锁🔒的拥有者?
     */
    public Map queryLockOwerByContractCode(String contractCode);
}
