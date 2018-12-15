package com.vilio.plms.service;

import com.vilio.plms.dao.CustomerDao;
import com.vilio.plms.dao.UserInfoDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.UserInfo;
import com.vilio.plms.service.base.BaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： Plms000013<br>
 * 功能：姓名和身份证校验<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月21日<br>
 * 作者： zx<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class Plms000013 extends BaseService {

    private static final Logger logger = Logger.getLogger(Plms000013.class);

    @Resource
    private  CustomerDao customerDao;

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        checkField(ObjectUtils.toString(body.get("name")), "姓名", null, 10);

        checkField(ObjectUtils.toString(body.get("idNo")), "身份证号", null, 40);

    }

    /**
     * 主业务流程空实现
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //修改为查询用户信息表中的字段
        UserInfo queryParam = new UserInfo();
        queryParam.setUmId(ObjectUtils.toString(head.get(Fields.PARAM_USER_ID)));
        UserInfo userInfo = userInfoDao.queryUserInfo(queryParam);
        if (userInfo == null) {
            throw new ErrorException(ReturnCode.USER_INFO_FAIL, "");
        }

        //根据userId查询借款人信息
        //Customer customerParam = new Customer();
        //customerParam.setUmId(ObjectUtils.toString(head.get(Fields.PARAM_USER_ID)));
        //Customer customer = customerDao.qryCustomer(customerParam);
        //判断用户是否存在
        //if (customer==null) {
        //    //借款人信息不存在
        //    throw new ErrorException(ReturnCode.BORROWER_INFO_FAIL,"");
        //}
        //判断姓名是否正确
        if (!ObjectUtils.toString(body.get("name")).equals(userInfo.getName())){
            //姓名不正确
            throw new ErrorException(ReturnCode.NAME_CARD_FAIL,"");
        }
        //判断身份证
        if (!ObjectUtils.toString(body.get("idNo")).equals(userInfo.getIdNo())){
            //身份证不正确
            throw new ErrorException(ReturnCode.NAME_CARD_FAIL,"");
        }
    }
}
