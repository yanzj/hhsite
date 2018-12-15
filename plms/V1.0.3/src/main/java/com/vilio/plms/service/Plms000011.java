package com.vilio.plms.service;

import com.vilio.plms.dao.HouseDao;
import com.vilio.plms.dao.PlmsContractInfoDao;
import com.vilio.plms.dao.RepaymentScheduleDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.House;
import com.vilio.plms.service.base.BaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms000011<br>
 * 功能：合同信息信息查询<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月19日<br>
 * 作者： zx<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000011 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000011.class);

    @Resource
    private HouseDao houseDao;
    @Resource
    PlmsContractInfoDao plmsContractInfoDao;


    @Resource
    private RepaymentScheduleDao repaymentScheduleDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        checkField(ObjectUtils.toString(body.get("contractCode")), "合同编码", null, null);
    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String contractCode = (String) body.get("contractCode");
        //查询贷款业务信息
        Map contractInfo = plmsContractInfoDao.queryContractInfoByCode(contractCode);
        if (contractInfo == null) {
            throw new ErrorException(ReturnCode.CONTRACT_INFO_FAIL, "");
        }
        //收集抵押物信息
        List<House> houseList = houseDao.qryHouseForContract(contractCode);
        if (houseList == null || houseList.size() < 1) {
            throw new ErrorException(ReturnCode.HOUSE_INFO_FAIL, "");
        }
        //拼装产证地址
        StringBuffer strTotalHouse = new StringBuffer();
        for(int i = 0; i < houseList.size(); i++){
            House house = houseList.get(i);
            if(i>0){
                strTotalHouse.append(",");
            }
            strTotalHouse.append(house.getCertificateAddress());
        }
        contractInfo.put("certificateAddress", strTotalHouse.toString());
        //计算期数，还款计划表中大于今天，小于等于本金归还日，统计条数
        String loanCount = repaymentScheduleDao.qryLoanCount(contractInfo);
        if (loanCount == null || "".equals(loanCount) || Integer.parseInt(loanCount) == 0) {
            //最高期数为0，不能进行借款操作
            throw new ErrorException(ReturnCode.LOAN_COUNT_FAIL, "");
        }
        contractInfo.put("loanCount", loanCount);
        resultMap.putAll(contractInfo);
    }
}
