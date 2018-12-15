package com.vilio.plms.service;

import com.vilio.plms.dao.PlmsPaidVoucherDao;
import com.vilio.plms.dao.PlmsRepaymentApplyDao;
import com.vilio.plms.dao.UserInfoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.PaidVoucher;
import com.vilio.plms.pojo.RepaymentApply;
import com.vilio.plms.pojo.UserInfo;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.BeanUtil;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JudgeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 还款接口
 */
@Service
public class Plms000003 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms000003.class);

    @Resource
    PlmsRepaymentApplyDao plmsRepaymentApplyDao;
    @Resource
    PlmsPaidVoucherDao plmsPaidVoucherDao;

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //判断借款金额
        checkField(ObjectUtils.toString(body.get("amount")), "借款金额", null, 18);
        //判断借款金额
        if (!"00".equals(JudgeUtil.isMoney(ObjectUtils.toString(body.get("amount")), 9, 2))) {
            //金额校验失败
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "借款金额错误");
        }
        //合同编码
        checkField(ObjectUtils.toString(body.get("contractCode")), "合同编码", null, null);
        //还款凭证
        if (body.get("voucherFileList")==null||((List)body.get("voucherFileList")).size()<1) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "最少上传一张凭证");
        }
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
        //查询当前用户信息
        UserInfo queryParam = new UserInfo();
        queryParam.setUmId(ObjectUtils.toString(head.get(Fields.PARAM_USER_ID)));
        UserInfo userInfo = userInfoDao.queryUserInfo(queryParam);
        if (userInfo == null) {
            throw new ErrorException(ReturnCode.USER_INFO_FAIL, "");
        }
        /*
        Step 2:保存还款申请
         */
        RepaymentApply repaymentApply = new RepaymentApply();
        repaymentApply.setCode(getUUID());
        repaymentApply.setAmount(ObjectUtils.toString(body.get("amount")));
        repaymentApply.setApplyTime(DateUtil.getCurrentDateTime2());
        repaymentApply.setApplyStatus(GlobDict.repayment_apply_status.getKey());
        repaymentApply.setBusinessCode(getDateSeq("SERNO", 4));
        repaymentApply.setContractCode(ObjectUtils.toString(body.get("contractCode")));
        repaymentApply.setCreateDate(DateUtil.getCurrentDateTime2());
        repaymentApply.setModifyDate(DateUtil.getCurrentDateTime2());
        repaymentApply.setUserCode(userInfo.getCode());
        int ret = plmsRepaymentApplyDao.saveRepaymentApplyInfo(repaymentApply);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //还款凭证入库
        List<PaidVoucher> paidVouchers = new ArrayList<PaidVoucher>();
        for (Object fileCode:(List)body.get("voucherFileList")) {
            PaidVoucher paidVoucher = new PaidVoucher();
            paidVoucher.setCode(getUUID());
            paidVoucher.setFileCode(ObjectUtils.toString(fileCode));
            paidVoucher.setStatus(GlobDict.valid.getKey());
            paidVoucher.setCreateDate(DateUtil.getCurrentDateTime2());
            paidVoucher.setModifyDate(DateUtil.getCurrentDateTime2());
            paidVoucher.setRepaymentApplyCode(repaymentApply.getCode());
            paidVouchers.add(paidVoucher);
        }
        ret = plmsPaidVoucherDao.saveVoucherInfo(paidVouchers);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        resultMap.putAll(BeanUtil.transBean2Map(repaymentApply));
    }
}
