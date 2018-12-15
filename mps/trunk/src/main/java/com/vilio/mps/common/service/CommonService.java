package com.vilio.mps.common.service;

import com.vilio.mps.common.dao.CommonDao;
import com.vilio.mps.exception.ErrorException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by dell on 2017/8/2.
 */
@Service("commonService")
public class CommonService {
    private static final Logger logger = Logger.getLogger(CommonService.class);

    @Resource
    private CommonDao commonDao;

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
        int ret = commonDao.nextval(seqName);
        if (ret <= 0) {
            throw new ErrorException("9997", "");
        }
        //查询当前序列值
        Long seq = commonDao.currval(seqName);
        if (seq.toString().length() > num) {
            //如果大于所传num，则进行截取
            seq = Long.parseLong(seq.toString().substring(0, num));
        }
        return String.format("%0" + num + "d", seq);
    }

}
