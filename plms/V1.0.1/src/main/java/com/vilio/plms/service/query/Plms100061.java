package com.vilio.plms.service.query;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： Plms100061<br>
 * 功能：账户信息查询初始化<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： xiezhilei<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms100061 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms100061.class);

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
        String userNo = body.get(Fields.PARAM_USER_NO) == null ? "" : body.get(Fields.PARAM_USER_NO).toString();

        //获取城市列表
        List cityList = commonService.queryCityListByUserId(userNo);
        resultMap.put(Fields.PARAM_CITY_LIST, cityList);

        //获取渠道列表
        List<Map> returnDistributorList = new ArrayList<>();
        List<Map> distributorList = commonService.getAvaliableDistributorListByUserId(userNo);
        if (null != distributorList && distributorList.size() > 0) {
            for (Map nb : distributorList) {
                Map nbMap = new HashMap();
                nbMap.put(Fields.PARAM_DISTRIBUTRO_CODE, nb.get(Fields.PARAM_DISTRIBUTRO_CODE));
                nbMap.put(Fields.PARAM_DISTRIBUTRO_NAME, nb.get(Fields.PARAM_DISTRIBUTRO_NAME));
                returnDistributorList.add(nbMap);
            }
        }
        resultMap.put(Fields.PARAM_DISTRIBUTRO_LIST, returnDistributorList);

    }

}
