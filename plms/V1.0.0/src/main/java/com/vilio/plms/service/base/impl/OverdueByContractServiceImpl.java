package com.vilio.plms.service.base.impl;

import com.vilio.plms.dao.SysInfoParamDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.service.Plms100100;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.MessageService;
import com.vilio.plms.service.base.OverdueService;
import com.vilio.plms.service.quartz.impl.OverdueServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/8/25.
 */
@Service
public class OverdueByContractServiceImpl extends BaseService implements OverdueService {
    private static final Logger logger = Logger.getLogger(OverdueByContractServiceImpl.class);

    @Resource
    MessageService messageService;
    @Resource
    SysInfoParamDao sysInfoParamDao;
    @Resource
    OverdueServiceImpl overdueService;

    public void overdue(String contractCode,String executeDate) throws ErrorException{
//        List<HashMap> accountLockList = (List)sysInfoParamDao.qryAccountLockList();
//        Map accountLock = new HashMap();
//        if (accountLockList!=null&&accountLockList.size()>0){
//            accountLock = accountLockList.get(0);
//            String itemCval = (String)accountLock.get("itemCval");
//            if (itemCval!=null && "Y".equals(itemCval)){
//                throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
//            }
//        }

//        String executeDate = (String)body.get("executeDate");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date executeTime = new Date();
//        try {
//            executeTime = sdf.parse(calculationDate);
//        }catch(ParseException e){
//            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
//        }
//        List<HashMap> calculateOverdueInterestJobList = (List)sysInfoParamDao.qryCurrentDayOverdueJobList();
//        if (calculateOverdueInterestJobList!=null&&calculateOverdueInterestJobList.size()>0){
//            for (int i = 0;i<calculateOverdueInterestJobList.size();i++){
//                HashMap calculateOverdueInterestJob = calculateOverdueInterestJobList.get(0);
//                executeTime = (Date)calculateOverdueInterestJob.get("executeTime");
//            }
//        }
//        Calendar calendar = Calendar.getInstance();
//        if (executeTime!=null) {
//            calendar.setTime(executeTime);
//            calendar.add(Calendar.DATE,1);
//        }
//        Date currentExecuteTime = calendar.getTime();

        //计算calculationDate当天的罚息
//        String calculationDate = (String)sdf.format(executeTime);
        //定时任务应该执行的日期
//        String currentExecuteDate = (String)sdf.format(currentExecuteTime);
//        System.out.println(currentExecuteTime);
//        List<HashMap> payScheduleJobList = (List)sysInfoParamDao.qryCurrentDayPayScheduleJobList(executeDate);
//        if (payScheduleJobList==null||payScheduleJobList.size()==0){
//            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
//        }
        ((OverdueByContractServiceImpl) AopContext.currentProxy()).calculateOverdue(executeDate,contractCode);
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void calculateOverdue(String currentExecuteDate,String contractCode){
        Map map = new HashMap();
        map.put("currentExecuteDate",currentExecuteDate);
        map.put("contractCode",contractCode);
        List<HashMap> overdueFirstDayRepaymentScheduleDetailList = (List)sysInfoParamDao.qryOverdueRepaymentScheduleDetailListByContractNo(map);
        System.out.println(overdueFirstDayRepaymentScheduleDetailList);
        if (overdueFirstDayRepaymentScheduleDetailList.size()>0) {
            for (int i = 0; i < overdueFirstDayRepaymentScheduleDetailList.size(); i++) {
                HashMap overdueRepaymentScheduleDetail = overdueFirstDayRepaymentScheduleDetailList.get(i);
                overdueService.calculateOverdueByScheduleDetail(overdueRepaymentScheduleDetail);
            }
        }
    }
}
