package com.vilio.pcfs.service.quartz;

import com.vilio.pcfs.dao.MessagesDao;
import com.vilio.pcfs.dao.WaitSendMessageDao;
import com.vilio.pcfs.exception.ErrorException;
import com.vilio.pcfs.glob.GlobDict;
import com.vilio.pcfs.glob.ReturnCode;
import com.vilio.pcfs.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名： FilterCreateMsgService<br>
 * 功能：创建和过滤待发送的信息业务处理<br>
 * 版本： 1.0<br>
 * 日期： 2017年8月15日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class FilterCreateMsgService extends BaseService {

    private static Logger logger = Logger.getLogger(FilterCreateMsgService.class);

    @Resource
    private WaitSendMessageDao waitSendMessageDao;

    @Resource
    private MessagesDao messagesDao;

    /**
     * 定时任务执行主流程实现
     */
    @Override
    public void execute() throws Exception {
        //直接更新数据库中待处理状态的数据，如果沒有待处理数据，则更新数为0
        Map updateMap = new HashMap();
        updateMap.put("newStatus", GlobDict.wait_send_message_ing.getKey());
        updateMap.put("batchNo", getUUID());
        updateMap.put("sendMethod", GlobDict.send_method_delay.getKey());
        updateMap.put("oldStatus", GlobDict.wait_send_message_init.getKey());
        int ret = waitSendMessageDao.updateWaitSendMessage(updateMap);
        if (ret <= 0) {
            logger.info("没有需要处理的待推送信息" + ret);
            return;
        }
        //插入信息表中（包含在用户信息表中的用户的待推送信息）
        try {
            ret = messagesDao.insertMessagesForWaitMessage(updateMap.get("batchNo").toString());
            if (ret < 0) {
                logger.error("插入信息表出错：" + ret);
                //出错后更新待发送数据库中的状态为待处理状态
                updateMap.put("status", GlobDict.wait_send_message_init.getKey());
                ret = waitSendMessageDao.updateWaitSendMessageStatusByBatchNo(updateMap);
                if (ret <= 0) {
                    logger.error("更新出错：" + ret);
                    return;
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //出错后更新待发送数据库中的状态为待处理状态
            updateMap.put("status", GlobDict.wait_send_message_init.getKey());
            ret = waitSendMessageDao.updateWaitSendMessageStatusByBatchNo(updateMap);
            if (ret <= 0) {
                logger.error("更新出错：" + ret);
                return;
            }
            return;
        }
        //插入成功后，更新待发送表状态为处理完成
        updateMap.put("status", GlobDict.wait_send_message_finish.getKey());
        ret = waitSendMessageDao.updateWaitSendMessageStatusByBatchNo(updateMap);
        if (ret <= 0) {
            logger.error("更新出错：" + ret);
            return;
        }
        //处理成功后的信息转移到历史表中
        ((FilterCreateMsgService) AopContext.currentProxy()).shiftLog(updateMap);
    }

    /**
     * 待发送信息表转移到历史表
     *
     * @param updateMap
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void shiftLog(Map updateMap) throws Exception {
        int ret = waitSendMessageDao.insertWaitSendMessageLogByBatchNo(updateMap);
        if (ret <= 0) {
            logger.error("插入出错：" + ret);
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
        //删除待发送表
        ret = waitSendMessageDao.deleteWaitSendMessageLogByBatchNo(updateMap);
        if (ret <= 0) {
            logger.error("删除出错：" + ret);
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
    }


}
