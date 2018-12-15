package com.vilio.pcfs.service.inner;

import com.vilio.pcfs.dao.PushMessageDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobParam;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.service.BaseService;
import com.vilio.pcfs.service.Pcfs000025;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类名： Pcfs000027<br>
 * 功能：接受内部推送消息批量插入数据库<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月4日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000027 extends BaseService {

    private static final Logger logger = Logger.getLogger(Pcfs000025.class);
    @Resource
    private PushMessageDao pushMessageDao;


    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
        if (body.get("messageList") == null || ((List) body.get("messageList")).size() <= 0) {
            throw new ErrorException(ReturnCode.MESSAGE_LIST_FAIL, "");
        }
    }


    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
        List<Map> messageList = (List) body.get("messageList");
        for (Map messageInfo : messageList) {
            messageInfo.put(GlobParam.PARAM_SYSTEM_NO, head.get(GlobParam.PARAM_SYSTEM_NO));
        }
        int result = pushMessageDao.insertPushMessagesList(messageList);
        if (result <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }


}
