package com.vilio.nlbs.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.nlbs.bms.dao.BmsCommonDao;
import com.vilio.nlbs.bms.pojo.ApplyRecordRespBean;
import com.vilio.nlbs.bms.service.BmsCommonService;
import com.vilio.nlbs.util.Fields;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/1/12.
 */
@Service
public class BmsCommonServiceImpl implements BmsCommonService {
    @Resource
    BmsCommonDao bmsCommonDao;


    public Map pageQueryApplyList(Integer pageNo, Integer pageSize, Map paraMap) throws Exception {
        Map result = new HashMap();

        pageNo = null != pageNo ? pageNo : 1;
        pageSize = null != pageSize ? pageSize : 10;
        PageHelper.startPage(pageNo, pageSize);

        List<ApplyRecordRespBean> applyList = bmsCommonDao.queryApplyList(paraMap);
        PageInfo pageInfo = new PageInfo(applyList);

        result.put(Fields.PARAM_APPLY_LOAN_LIST,applyList);
        result.put(Fields.PARAM_PAGES,pageInfo.getPages());
        result.put(Fields.PARAM_TOTAL,pageInfo.getTotal());
        result.put(Fields.PARAM_CURRENT_PAGE,pageInfo.getPageNum());

        return result;
    }
}
