package com.vilio.ppms.service.common.impl;

import com.vilio.ppms.dao.CommDao;
import com.vilio.ppms.exception.ErrorException;
import com.vilio.ppms.glob.ReturnCode;
import com.vilio.ppms.service.common.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 类名： CommonService<br>
 * 功能：公共基础类<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月19日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService{

    @Resource
    private CommDao commDao;

    /**
     * 参数校验公共方法
     * @param field
     * @param name
     * @param needLength
     * @param maxLength
     * @throws ErrorException
     */
    public void checkField(String field, String name, Integer needLength, Integer maxLength) throws ErrorException {
        if (field == null || field.length() == 0) {
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, String.format("缺少%s必要参数,或数据格式不正确", name));
        }
        if (needLength != null) {
            if (field == null || field.length() != needLength) {
                throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, String.format("%s长度必须为%d", name, needLength));
            }
        }
        if (maxLength != null) {
            if (field == null || field.length() > maxLength) {
                throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, String.format("%s长度不能大于%d", name, maxLength));
            }
        }
    }


    /**
     * 获取序列号方法
     *
     * @param seqName
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public String getSeq(String seqName, int num) throws ErrorException {
        //先更改序列表，序列增加
        int ret = commDao.nextval(seqName);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //查询当前序列值
        Long seq = commDao.currval(seqName);
        if (seq.toString().length() > num) {
            //如果大于所传num，则进行截取
            seq = Long.parseLong(seq.toString().substring(0, num));
        }
        return String.format("%0" + num + "d", seq);
    }

    /**
     * 生成uuid
     *
     * @return
     * @throws ErrorException
     */
    public String getUUID() {
        return UUID.randomUUID().toString();
    }



}
