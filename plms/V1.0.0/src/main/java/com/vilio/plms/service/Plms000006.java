package com.vilio.plms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.PlmsRepaymentApplyDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms000006<br>
 * 功能：还款记录列表查询接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： zhuxz<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000006 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000001.class);
    @Resource
    private PlmsRepaymentApplyDao applyDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (!JudgeUtil.isNull(body.get("pageNo"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "页数参数校验失败");
        }

        if (!JudgeUtil.isNull(body.get("pageSize"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "请求个数参数校验失败");
        }

        if (!JudgeUtil.isNull(body.get("status"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "请求个数参数校验失败(status)");
        }

    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        String userId = head.get(Fields.PARAM_USER_ID).toString();
        String applyStatus = body.get("status").toString();

        Integer pageNo = Integer.valueOf(body.get("pageNo").toString());
        Integer pageSize =Integer.valueOf(body.get("pageSize").toString());
        PageHelper.startPage(pageNo, pageSize);


        Map conditionMap = new HashMap();
        conditionMap.put("userId", userId);
        conditionMap.put("applyStatus", applyStatus);
        List<Map> repaymentInfoList = applyDao.queryRepaymentApplyInfo(conditionMap);
        PageInfo pageInfo = new PageInfo(repaymentInfoList);

        if(null != repaymentInfoList && repaymentInfoList.size() >0){
            for(Map repayment: repaymentInfoList){
                repayment.put("businessCode", repayment.get("businessCode"));
                repayment.put("applayAmount", repayment.get("applayAmount"));
                repayment.put("applayTime", repayment.get("applayTime"));
                repayment.put("repaymentTime", repayment.get("repaymentTime"));
                repayment.put("repaymentAmount", repayment.get("repaymentAmount"));
            }
        }
        resultMap.put("orderlist", repaymentInfoList);
        resultMap.put("pages", Integer.valueOf(pageInfo.getPages()));
        resultMap.put("total", Long.valueOf(pageInfo.getTotal()));
        resultMap.put("currentPage", Integer.valueOf(pageInfo.getPageNum()));

    }


}
