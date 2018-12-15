package com.vilio.plms.service.opertaion;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.OperationManagerDao;
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
 * 类名： Plms100017<br>
 * 功能：还款计划确认查看详情<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100017 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100017.class);

    @Resource
    OperationManagerDao operationManagerDao;
    @Resource
    QueryDao queryDao;

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

        //获取贷款详情-不包含还款计划、放款记录等关联信息
        List list = operationManagerDao.queryContractList(body);

        if(list == null || list.isEmpty()){
            throw new ErrorException(ReturnCode.SYSTEM_EXCEPTION,"未找到贷款详情信息");
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

        //取还款计划列表
        Integer pageNo = null != body.get(Fields.PARAM_PAGE_NO) ? new Integer(body.get(Fields.PARAM_PAGE_NO).toString()) : 1;
        Integer pageSize = null != body.get(Fields.PARAM_PAGE_SIZE) ? new Integer(body.get(Fields.PARAM_PAGE_SIZE).toString()) : 10;

        PageHelper.startPage(pageNo, pageSize);
        List repaymentList = queryDao.queryRepaymentScheduleListByContractCode(body);
        PageInfo pageInfo = new PageInfo(repaymentList);

        resultMap.put("repaymentList", repaymentList);
        resultMap.put(Fields.PARAM_PAGES,pageInfo.getPages());
        resultMap.put(Fields.PARAM_TOTAL,pageInfo.getTotal());
        resultMap.put(Fields.PARAM_CURRENT_PAGE,pageInfo.getPageNum());
    }

}
