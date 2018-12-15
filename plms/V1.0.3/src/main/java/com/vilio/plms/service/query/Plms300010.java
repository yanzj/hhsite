package com.vilio.plms.service.query;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.plms.dao.DistributorDao;
import com.vilio.plms.dao.LoginInfoDao;
import com.vilio.plms.dao.QueryDao;
import com.vilio.plms.dao.QueryForNlbsDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.service.base.BaseService;
import com.vilio.plms.service.base.CommonService;
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
 * Created by dell on 2017/9/7.
 */
@Service
public class Plms300010 extends BaseService {
    private static final Logger logger = Logger.getLogger(Plms300010.class);

    @Resource
    QueryForNlbsDao queryForNlbsDao;

    @Resource
    DistributorDao distributorDao;

    @Resource
    LoginInfoDao loginInfoDao;

    @Resource
    CommonService commonService;

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
        String agentId = body.get("agentId") == null ? "" : body.get("agentId").toString();

        Integer pageNo = null != body.get(Fields.PARAM_PAGE_NO) ? new Integer(body.get(Fields.PARAM_PAGE_NO).toString()) : 1;
        Integer pageSize = null != body.get(Fields.PARAM_PAGE_SIZE) ? new Integer(body.get(Fields.PARAM_PAGE_SIZE).toString()) : 10;

        //获取贷后的distributorList
        List<Map> plmsDistributorList = distributorDao.queryDistributorListByBmsCode(body);
        if(plmsDistributorList == null || plmsDistributorList.size() == 0){
            body.put("distributorList", null);
        } else {
            body.put("distributorList", plmsDistributorList);
        }
        //获取业务员列表
        Map agentInfo = loginInfoDao.queryAgentInfoByAgentId(agentId);
        if(agentInfo != null) {
            List allChildUsers = commonService.queryAllChildAgents(agentInfo.get("code") == null ? "" : agentInfo.get("code").toString());
            if (null == allChildUsers) {
                allChildUsers = new ArrayList();
            }
            allChildUsers.add(agentInfo);
            body.put("agentList", allChildUsers);
        }

        PageHelper.startPage(pageNo, pageSize);
        List list = queryForNlbsDao.qryLoanList(body);
        PageInfo pageInfo = new PageInfo(list);

        resultMap.put("dataList", list);
        resultMap.put(Fields.PARAM_PAGES,pageInfo.getPages());
        resultMap.put(Fields.PARAM_TOTAL,pageInfo.getTotal());
        resultMap.put(Fields.PARAM_CURRENT_PAGE,pageInfo.getPageNum());
    }

}
