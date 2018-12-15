package com.vilio.plms.service.base;

import com.vilio.plms.dao.CommDao;
import com.vilio.plms.exception.ErrorException;
import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.pojo.SysParam;
import com.vilio.plms.util.DateUtil;
import com.vilio.plms.util.JudgeUtil;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 类名： BaseService<br>
 * 功能：所有service的父类<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月14日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public class BaseService {

    private static final Logger logger = Logger.getLogger(BaseService.class);

    @Resource
    private CommDao commDao;

    @Resource
    private CommonService commonService;

    /**
     * @param params
     * @return
     */
    public Map<String, Object> doMain(Map<String, Object> params) {
        Map<String, Object> respMap = new HashMap<String, Object>();
        Map<String, Object> head = new HashMap<String, Object>();
        Map<String, Object> body = new HashMap<String, Object>();
        respMap.put(Fields.PARAM_MESSAGE_BODY, params.get(Fields.PARAM_MESSAGE_BODY));
        try {
            head.putAll((Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD));
            head.put(Fields.PARAM_SYSTEM_TIME, DateUtil.getCurrentDateTime2());
            respMap.put(Fields.PARAM_MESSAGE_HEAD, head);
            body.putAll(((BaseService) AopContext.currentProxy()).doService(params));
            body.putAll(((BaseService) AopContext.currentProxy()).doServiceNoTransaction(params));
            JudgeUtil.MapValToString(body);
            respMap.put(Fields.PARAM_MESSAGE_BODY, body);
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, GlobParam.ERROR_CODE.get(ReturnCode.SUCCESS_CODE));
        } catch (ErrorException e) {
            //e.printStackTrace();
            String returnCode = e.getErroCode() == null || "".equals(e.getErroCode()) ? ReturnCode.SYSTEM_EXCEPTION : e.getErroCode();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, returnCode);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage() == null || "".equals(e.getMessage())
                    ? GlobParam.ERROR_CODE.get(returnCode) : e.getMessage());
            logger.error("错误码" + head.get(Fields.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(Fields.PARAM_MESSAGE_ERR_MESG));
        } catch (Exception e) {
            e.printStackTrace();
            head.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SYSTEM_EXCEPTION);
            head.put(Fields.PARAM_MESSAGE_ERR_MESG, e.getMessage());
            logger.error("错误码" + head.get(Fields.PARAM_MESSAGE_ERR_CODE));
            logger.error("错误信息" + head.get(Fields.PARAM_MESSAGE_ERR_MESG));
        }
        return respMap;
    }


    /**
     * 流程方法
     *
     * @param params
     * @return
     * @throws ErrorException
     */
    public Map<String, Object> doServiceNoTransaction(Map<String, Object> params) throws ErrorException, Exception {
        //初始化参数
        Map<String, Object> body = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_BODY);
        Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 参数校验
        checkParamNoTransaction(body);
        //登录业务处理
        busiServiceNoTransaction(head, body, resultMap);
        //返回参数
        return resultMap;
    }

    /**
     * 不需要事务的业务主流程空实现
     *
     * @param head
     * @param body
     * @param resultMap
     */
    public void busiServiceNoTransaction(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException {
    }

    /**
     * 不需要事务的检查参数空实现
     *
     * @param body
     */
    public void checkParamNoTransaction(Map<String, Object> body) throws ErrorException {
    }


    /**
     * 流程方法
     *
     * @param params
     * @return
     * @throws ErrorException
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public Map<String, Object> doService(Map<String, Object> params) throws ErrorException, Exception {
        //初始化参数
        Map<String, Object> body = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_BODY);
        Map<String, Object> head = (Map<String, Object>) params.get(Fields.PARAM_MESSAGE_HEAD);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 参数校验
        checkParam(body);
        //登录业务处理
        busiService(head, body, resultMap);
        //返回参数
        return resultMap;
    }


    /**
     * 参数验证空实现
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
    public void busiService(Map<String, Object> head, Map<String, Object> body, Map<String, Object> resultMap) throws ErrorException, Exception {
    }

    /**
     * 定时任务流程入口
     */
    public void execute() throws ErrorException, Exception {

    }

    /**
     * 获取系统参数字符值
     *
     * @return
     */
    public String getItemCval(String itemId) throws ErrorException {
        SysParam sysParam = commDao.getSysParam(itemId);
        if (sysParam == null) {
            throw new ErrorException(ReturnCode.SYS_PARAM_FAIL, "");
        }
        return sysParam.getItemCval();
    }

    /**
     * 获取系统参数整形值
     *
     * @return
     */
    public String getItemIval(String itemId) throws ErrorException {
        SysParam sysParam = commDao.getSysParam(itemId);
        if (sysParam == null) {
            throw new ErrorException(ReturnCode.SYS_PARAM_FAIL, "");
        }
        return sysParam.getItemIval();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public void setItemVal(String itemId, String itemCval, String itemIval) throws ErrorException {
        SysParam sysParam = new SysParam();
        sysParam.setItemId(itemId);
        sysParam.setItemCval(itemCval);
        sysParam.setItemIval(itemIval);
        int ret = commDao.setItemVal(sysParam);
        if (ret <= 0) {
            throw new ErrorException(ReturnCode.UPDATE_FAIL, "");
        }
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


    /**
     * 自动生成序列号，自动拼接当前时间到秒
     *
     * @param seqName 序列名
     * @param num     序列位数（不算拼接时间）
     * @return
     * @throws ErrorException
     */
    public String getDateSeq(String seqName, int num) throws ErrorException {
        return DateUtil.getCurrentDateTime() + commonService.getSeq(seqName, num);
    }

    /**
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


}
