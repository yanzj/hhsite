package com.vilio.pcfs.service;

import com.vilio.pcfs.dao.PushMessageDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 类名： Pcfs000028<br>
 * 功能：用户推送消息操作功能 （已读与失效）<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月4日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 * 注：
 */
@Service
public class Pcfs000028 extends BaseService{
    private static final Logger logger = Logger.getLogger(Pcfs000028.class);
    @Resource
    private PushMessageDao pushMessageDao;
    /**
     * 参数验证
     *
     * @param body
     */
    public void checkParam(Map<String, Object> body) throws ErrorException {
       //验证消息id
        if ( !JudgeUtil.isNull(body.get("messageId"))){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "");
        }
        //验证操作类型
        if ( !JudgeUtil.isNull(body.get("operationType")) || body.get("operationType").toString().length() > 1){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "");
        }

        String operationType = body.get("operationType").toString();
        if (!operationType.equals("0") && !operationType.equals("1")){
            throw new ErrorException(ReturnCode.REQUIRED_FIELD_MISSING, "");
        }




    }


    /**
     * 主业务流程
     *
     * @param head
     * @param body
     */
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {

      String messageId = body.get("messageId").toString();

      String operationType = body.get("operationType").toString();

      int result = 1;
      //将当前信息置为已读
      if (operationType.equals("0")){
          result = pushMessageDao.updateUserMesaageReadFlag(messageId);

      }else {
          result = pushMessageDao.updateUserMessageStatu(messageId);

      }
      if (result < 0){
          throw new ErrorException(ReturnCode.UPDATE_FAIL, "");

      }

    }


}
