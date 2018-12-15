package com.vilio.plms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.PlmsRepaymentScheduleDetailDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.util.JudgeUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 逾期列表接口查询
 */
@Service
public class Plms000017  extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms000017.class);
    @Resource
    PlmsRepaymentScheduleDetailDao plmsRepaymentScheduleDetailDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (!JudgeUtil.isNull(body.get(Fields.PARAM_PAGE_NO))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "页数参数校验失败");
        }
        if (!JudgeUtil.isNull(body.get(Fields.PARAM_PAGE_SIZE))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "当前页请求个数校验失败");
        }
        if (!JudgeUtil.isNull(body.get("overdueType"))) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "传入类型为空！");
        }
    }


    /**
     * 主业务流程实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //0-逾期中 1-历史逾期
        String reqStatus = (String) body.get("overdueType");
        String userId = head.get(Fields.PARAM_USER_ID).toString();
        Map map = new HashedMap();
        map.put("umId", userId);
        if(reqStatus.equals("0")){
            map.put("overdueStatus", "02");
            map.put("detailStatus", "02");
        }else{
            map.put("overdueStatus", "01");
            map.put("detailStatus", "01");
        }

        PageHelper.startPage(Integer.valueOf(body.get(Fields.PARAM_PAGE_NO).toString()), Integer.valueOf(body.get(Fields.PARAM_PAGE_SIZE).toString()));
        List overdueList = plmsRepaymentScheduleDetailDao.queryOverdueRecordByStatus(map);

        resultMap.put("overdueList", overdueList);
        PageInfo pageInfo = new PageInfo(overdueList);
        resultMap.put(Fields.PARAM_TOTAL_PAGE, Integer.valueOf(pageInfo.getPages()));
        resultMap.put(Fields.PARAM_TOTAL, Long.valueOf(pageInfo.getTotal()));
        resultMap.put(Fields.PARAM_CURRENT_PAGE, Integer.valueOf(pageInfo.getPageNum()));

    }
}
