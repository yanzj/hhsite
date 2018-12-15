package com.vilio.plms.service;

import com.vilio.plms.dao.*;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.pojo.AccountDetail;
import com.vilio.plms.pojo.ApplyInfo;
import com.vilio.plms.pojo.Contract;
import com.vilio.plms.pojo.Product;
import com.vilio.plms.service.base.BaseService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/7/22.
 */
public class Plms000000 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms000001.class);

    @Resource
    private ApplyInfoDao applyInfoDao;
    @Resource
    private ProductDao productDao;
    @Resource
    private AccountDao accountDao;
    @Resource
    private AccountDetailDao accountDetailDao;
    @Resource
    private ContractDao contractDao;

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
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List <AccountDetail> accountDetailList = accountDetailDao.qryPrincipalDateForUpdate();
        for (AccountDetail accountDetail:accountDetailList){
            String principalDate = accountDetail.getPrincipalDate();
            Date principalDateTrans = sdf.parse(principalDate);

            String contractCode = accountDetail.getContractCode();
            Contract contract = new Contract();
            contract.setCode(contractCode);
            contract = contractDao.qryContract(contract);

            String applyCode = contract.getApplyCode();
            ApplyInfo applyInfo = new ApplyInfo();
            applyInfo.setCode(applyCode);
            applyInfo = applyInfoDao.qryApplyInfo(applyInfo);

            //删除申请表中的授信有效期字段，实体改了，报错，暂时注释，后续修改   wangxf by 20170725
            //String creditStartDate = applyInfo.getCreditStartDate();
            //Date creditStartDateTrans = sdf.parse(creditStartDate);
            //String creditEndDate = applyInfo.getCreditEndDate();
            //Date creditEndDateTrans = sdf.parse(creditEndDate);

            Product product = new Product();
            product.setApplyCode(applyCode);
            product = productDao.qryProduct(product);

            Date now = new Date();


            String principalRepaymentPeriod = product.getPrincipalRepaymentPeriod();
            int principalRepaymentPeriodTrans = Integer.getInteger(principalRepaymentPeriod);

            if (principalRepaymentPeriodTrans==0){
                //principalDate = creditEndDate;
            }
            else if (principalRepaymentPeriodTrans>0){
                //Date principalDateTransX = creditStartDateTrans;
                Date principalDateTransY = principalDateTrans;
                int counter = 1;

                while (principalDateTransY.before(now)){
                    Calendar calendar = Calendar.getInstance();
                    //calendar.setTime(principalDateTransX);
                    calendar.add(Calendar.MONTH,principalRepaymentPeriodTrans*counter);
                    principalDateTransY = calendar.getTime();
                    counter++;
                }

                //if (creditEndDateTrans.before(principalDateTransY)){
                //    principalDateTransY = creditEndDateTrans;
                //}

                //principalDate = sdf.format(principalDateTransY);
            }

            accountDetail.setPrincipalDate(principalDate);
            accountDetailDao.updateAccountDetail(accountDetail);

        }*/
    }
}
