package com.vilio.plms.service.query;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.service.base.BaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms100053<br>
 * 功能：放款记录详情查询<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月11日<br>
 * 作者： liuzhu.feng<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100053 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100053.class);

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
        String userNo = body.get(Fields.PARAM_USER_NO) == null ? "" : body.get(Fields.PARAM_USER_NO).toString();

        Map paidInfo = queryDao.queryPaidInfoByPaidInfoCode(body);

        if(paidInfo != null){
            String paidInfoCode = (String)paidInfo.get("paidInfoCode");
            List fileList = queryDao.queryFileListForPaidInfo(paidInfoCode);
            paidInfo.put("fileList",fileList);
            resultMap.putAll(paidInfo);
        }
    }

}
