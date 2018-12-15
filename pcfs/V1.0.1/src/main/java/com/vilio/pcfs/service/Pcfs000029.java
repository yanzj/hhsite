package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.VersionDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： Pcfs000029<br>
 * 功能：安卓版本信息查询接口<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月3日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000029 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000029.class);

    @Resource
    private VersionDao versionDao;

    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {

    }
    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        //查询安卓版本信息
        Map versionMap = versionDao.searchAnroidVersionInfo();
        if (versionMap == null){
            throw new ErrorException(ReturnCode.VERSION_FAIL, "");
        }else {
            resultMap.putAll(versionMap);
        }
    }
}
