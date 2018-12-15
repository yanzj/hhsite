package com.vilio.plms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.BorrowApplyDao;
import com.vilio.plms.dao.PlmsPaidInfoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.BorrowApply;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms000007<br>
 * 功能：查询借款记录列表信息<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000007 extends BaseService{

    private static final Logger logger = Logger.getLogger(Plms000007.class);

    @Resource
    private BorrowApplyDao borrowApplyDao;
    @Resource
    PlmsPaidInfoDao plmsPaidInfoDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        //校验必填参数
        if (!JudgeUtil.isNull(body.get(Fields.PARAM_PAGE_NO))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "页数参数校验失败");
        }
        if (!JudgeUtil.isNull(body.get(Fields.PARAM_PAGE_SIZE))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "当前页请求个数校验失败");
        }
        if (JudgeUtil.isNull(body.get(Fields.PARAM_STATUS))) {
            //如果订单状态不为空，则校验是否符合规则
            if (!GlobDict.order_status_loan_ing.getKey().equals(body.get("status"))
                    && !GlobDict.order_status_audity_ing.getKey().equals(body.get("status"))
                    && !GlobDict.order_status_loan_succ.getKey().equals(body.get("status"))
                    && !GlobDict.order_status_audity_fail.getKey().equals(body.get("status"))) {
                throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "状态参数校验失败");
            }
        }else {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "状态参数不能为空");
        }

    }


    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        Integer pageNo = Integer.valueOf(body.get(Fields.PARAM_PAGE_NO).toString());
        Integer pageSize = Integer.valueOf(body.get(Fields.PARAM_PAGE_SIZE).toString());
        String status = (String) body.get(Fields.PARAM_STATUS);
        BorrowApply borrowApplyPram = new BorrowApply();
        borrowApplyPram.setApplyStatus(status);
        List<BorrowApply> borrowApplies = null;
        PageHelper.startPage(pageNo.intValue(), pageSize.intValue());
        if(status.equals(GlobDict.order_status_loan_succ.getKey())){
            //放款表
            borrowApplies = plmsPaidInfoDao.queryUserPaidInfo(borrowApplyPram);
        }else{
            borrowApplies = borrowApplyDao.queryBorrowApply(borrowApplyPram);
        }
        PageInfo pageInfo = new PageInfo(borrowApplies);
        resultMap.put(Fields.PARAM_TOTAL_PAGE, Integer.valueOf(pageInfo.getPages()));
        resultMap.put(Fields.PARAM_TOTAL, Long.valueOf(pageInfo.getTotal()));
        resultMap.put(Fields.PARAM_CURRENT_PAGE, Integer.valueOf(pageInfo.getPageNum()));
        resultMap.put(Fields.PARAM_BORROW_APPLY_LIST, borrowApplies);
    }


}
