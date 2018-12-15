package com.vilio.plms.service.opertaion;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.OperationManagerDao;
import com.vilio.plms.dao.PaidInfoDao;
import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms100021<br>
 * 功能：还款计划确认查看详情<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100021 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100021.class);

    @Resource
    OperationManagerDao operationManagerDao;
    @Resource
    QueryDao queryDao;
    @Resource
    PaidInfoDao paidInfoDao;
    @Resource
    OperationManagerService operationManagerService;

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
        String contractCode = body.get("contractCode").toString();

        //根据合同编码，创建对应的还款计划明细表临时数据,以及控制表数据
        operationManagerService.createRepaymentScheduleDetailTmpAndControlData(body);

        //获取贷款详情-不包含还款计划、放款记录等关联信息
        List list = operationManagerDao.queryContractList(body);

        if(list == null || list.isEmpty()){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION,"未找到还款计划详情！");
        }

        //取第一条记录，即为当前合同基本信息
        Map contractInfo = (Map)list.get(0);
        resultMap.putAll(contractInfo);

        //获取抵押物地址信息
        List houseList = queryDao.queryHousInfoByContractCode(body);
        if (houseList != null && houseList.size() > 0) {
            resultMap.put("houseList", houseList);
        } else {
            resultMap.put("houseList", new ArrayList<>());
        }

        //取放款计划列表
        List paidInfoList = paidInfoDao.queryPaidInfoByContractCode(contractCode);
        if(paidInfoList == null ){
            paidInfoList = new ArrayList();
        }
        resultMap.put("paidInfoList", paidInfoList);
    }

}
