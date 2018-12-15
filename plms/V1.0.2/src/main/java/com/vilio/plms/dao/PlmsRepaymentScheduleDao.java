package com.vilio.plms.dao;

import com.vilio.plms.pojo.PlmsRepaymentScheduleBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/7.
 */
public interface PlmsRepaymentScheduleDao {
    /**
     * 查询当前合同的还款计划
     * @param map
     * @return
     * @throws Exception
     */
    public List<PlmsRepaymentScheduleBean> queryNeedRepaymentScheduleForContractCode(Map map) throws Exception;

    public BigDecimal queryNeedRepaymentScheduleTotalAmountForContractCode(Map map) throws Exception;

    //根据还款计划流水号查询 划款计划详情
    public List<Map> queryRepaymentDetailInfo(Map map);

    public List<PlmsRepaymentScheduleBean> getScheduleInfoByContractCode(Map map) throws Exception;

    int saveScheduleInfo(Map map) throws Exception;

    int updateScheduleAmount(Map map) throws Exception;

    //根据合同时间和资金到账日查询还款计划列表
    public List<PlmsRepaymentScheduleBean> queryRepaymentScheduleForContractCodeAndReceiptsDate(Map param);

    //根据code查询还款计划
    public PlmsRepaymentScheduleBean queryRepaymentScheduleByCode(PlmsRepaymentScheduleBean repaymentScheduleBean);

    //更新当前还款计划明细记录关联的还款计划表记录,应还金额、应还罚息、应还服务费违约金
    public int updateRepaymentScheduleAmountByCode(PlmsRepaymentScheduleBean repaymentScheduleBean);

    public int updateRepaymentScheduleRepaymentAmountByCode(PlmsRepaymentScheduleBean scheduleBean)throws Exception;

    public int updateFinishedScheduleStatusByCodeAndStatus(PlmsRepaymentScheduleBean schedule)throws Exception;

    //更新已还本金、已还利息、已还服务费、已还罚息、已还服务费违约金、状态
    public int updateRepaymentScheduleToRepaymentedByCode(PlmsRepaymentScheduleBean repaymentScheduleBean);

    int updateScheduleList(List list);

    public int updateRepaymentSchedule(Map param);

    //根据日期查询还款计划表
    public List<Map> queryRepaymentScheduleByDate(Map param);

    //根据日期和渠道code查询还款计划表
    public List<Map> queryRepaymentScheduleByDateAndChannel(Map param);

    Integer getPayingScheduleOfCustomerCountByRepaymentDateAndDistribute(Map param);

    public List<Map> getPayingScheduleByRepaymentDateAndDistribute(Map param);

    public List<Map> getPayingScheduleByRepaymentDateWithAgentGroup(Map param);

    public List<Map> getPayingScheduleByRepaymentDateWithDistributorGroup(Map param);

    public List<Map> getClerkListWithRepaymentDate(Map param);

    public List<Map> getBmsAgentsWithPlmsAgents(Map param);

    public List<Map> getOverdueSchedule(Map param);

    public List<Map> getClerkListForOverdueSchedule(Map param);
}
