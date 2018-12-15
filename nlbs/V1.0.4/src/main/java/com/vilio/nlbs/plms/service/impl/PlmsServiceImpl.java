package com.vilio.nlbs.plms.service.impl;/**
 * Created by dell on 2017/9/7/0007.
 */

import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.NlbsLoginInfoMapper;
import com.vilio.nlbs.commonMapper.pojo.NlbsLoginInfo;
import com.vilio.nlbs.plms.service.PlmsService;
import com.vilio.nlbs.remote.service.RemotePlmsService;
import com.vilio.nlbs.util.Constants;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.HHBizException;
import com.vilio.nlbs.util.ReturnCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名： <br>
 * 功能：贷后系统服务<br>
 * 版本： 1.0<br>
 * 日期： 2017/9/7/0007<br>
 * 作者： liuzhu.feng<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Service
public class PlmsServiceImpl implements PlmsService {

    @Resource
    CommonService commonService;

    @Resource
    RemotePlmsService remotePlmsService;

    @Resource
    NlbsLoginInfoMapper nlbsLoginInfoMapper;


    /**
     * @Description:贷款业务查询初始化接口
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300009(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();

        //获取城市列表
        returnMap.put(Fields.PARAM_CITY_LIST, commonService.queryCityListByUserNo(userNo));

        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300009", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.put("loanStatusList", plmsBodyMap.get("loanStatusList"));
            }else{
               throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取状态列表，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取状态列表，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "贷款业务查询初始化成功！");

        return returnMap;
    }

    /**
     * @Description:贷款业务列表查询
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300010(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        //判断当前用户是不是超管或者风控等内部用户
        List<String> roleList = new ArrayList<String>();
        roleList.add(Constants.ROLE_SUPPER_MANAGER);
        boolean isAdmin = commonService.isAdministrator(userNo, roleList);
        //获取渠道列表
        if(!isAdmin){
            paramMap.put(Fields.PARAM_DISTRIBUTRO_LIST, commonService.queryDistributorListByUserNo(userNo));
        }
        //获取业务员编号
        NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
        if (nlbsLoginInfo != null) {
            paramMap.put(Fields.PARAM_AGENT_ID, nlbsLoginInfo.getAgentId());
        }
        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300010", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.put("dataList", plmsBodyMap.get("dataList"));
                returnMap.put(Fields.PARAM_TOTAL, plmsBodyMap.get(Fields.PARAM_TOTAL));
                returnMap.put(Fields.PARAM_PAGES, plmsBodyMap.get(Fields.PARAM_PAGES));
                returnMap.put(Fields.PARAM_CURRENT_PAGE, plmsBodyMap.get(Fields.PARAM_CURRENT_PAGE));
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "贷款业务查询，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "贷款业务查询，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "贷款业务查询成功！");

        return returnMap;
    }

    /**
     * @Description:贷款业务查询详情
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300011(Map paramMap) throws Exception {
        Map returnMap = new HashMap();

        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300011", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null){
                returnMap.putAll(plmsBodyMap);
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取贷款业务查询详情，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取贷款业务查询详情，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取贷款业务查询详情成功！");

        return returnMap;
    }

    /**
     * @Description: 还款计划查询初始化
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300012(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();

        //获取城市列表
        returnMap.put(Fields.PARAM_CITY_LIST, commonService.queryCityListByUserNo(userNo));

        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300012", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.put("loanStatusList", plmsBodyMap.get("loanStatusList"));
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "还款计划查询初始化，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "还款计划查询初始化，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "还款计划查询初始化成功！");

        return returnMap;
    }

    /**
     * @Description: 还款计划查看详情
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300014(Map paramMap) throws Exception {
        Map returnMap = new HashMap();

        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300014", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.putAll(plmsBodyMap);
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取还款计划查看详情，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取还款计划查看详情，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取还款计划查看详情成功！");

        return returnMap;
    }

    /**
     * @Description: 获取还款计划列表
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300013(Map paramMap) throws Exception {
        Map returnMap = new HashMap();

        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        //判断当前用户是不是超管或者风控等内部用户
        List<String> roleList = new ArrayList<String>();
        roleList.add(Constants.ROLE_SUPPER_MANAGER);
        boolean isAdmin = commonService.isAdministrator(userNo, roleList);
        //获取渠道列表
        if(!isAdmin){
            paramMap.put(Fields.PARAM_DISTRIBUTRO_LIST, commonService.queryDistributorListByUserNo(userNo));
        }
        //获取业务员编号
        NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
        if (nlbsLoginInfo != null) {
            paramMap.put(Fields.PARAM_AGENT_ID, nlbsLoginInfo.getAgentId());
        }
        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300013", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.put("dataList", plmsBodyMap.get("dataList"));
                returnMap.put(Fields.PARAM_TOTAL, plmsBodyMap.get(Fields.PARAM_TOTAL));
                returnMap.put(Fields.PARAM_PAGES, plmsBodyMap.get(Fields.PARAM_PAGES));
                returnMap.put(Fields.PARAM_CURRENT_PAGE, plmsBodyMap.get(Fields.PARAM_CURRENT_PAGE));
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取还款计划列表，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取还款计划列表，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取还款计划列表成功！");

        return returnMap;
    }

    /**
     * @Description:贷款业务查询-还款记录
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300029(Map paramMap) throws Exception {
        Map returnMap = new HashMap();

        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300029", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.putAll(plmsBodyMap);
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取贷款业务查询-还款记录，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取贷款业务查询-还款记录，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "贷款业务查询-还款记录成功！");

        return returnMap;
    }

    /**
     * @Description:贷款业务查询-放款记录
     * @param:
     * @return: 
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300034(Map paramMap) throws Exception {
        Map returnMap = new HashMap();

        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300034", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.putAll(plmsBodyMap);
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "贷款业务查询-放款记录，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "贷款业务查询-放款记录，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "贷款业务查询-放款记录成功！");

        return returnMap;
    }

    /**
     * @Description:贷款业务查询-还款计划
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300035(Map paramMap) throws Exception {
        Map returnMap = new HashMap();

        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300035", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.putAll(plmsBodyMap);
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取贷款业务查询-还款计划，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取贷款业务查询-还款计划，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "贷款业务查询-还款计划成功！");

        return returnMap;
    }

    /**
     * @Description: 逾期记录查询初始化
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300049(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();

        //获取城市列表
        returnMap.put(Fields.PARAM_CITY_LIST, commonService.queryCityListByUserNo(userNo));

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "逾期记录查询初始化成功！");

        return returnMap;
    }

    /**
     * @Description:逾期记录列表
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300050(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        String userNo = paramMap.get(Fields.PARAM_USER_NO) == null ? "" : paramMap.get(Fields.PARAM_USER_NO).toString();
        //判断当前用户是不是超管或者风控等内部用户
        List<String> roleList = new ArrayList<String>();
        roleList.add(Constants.ROLE_SUPPER_MANAGER);
        boolean isAdmin = commonService.isAdministrator(userNo, roleList);
        //获取渠道列表
        if(!isAdmin){
            paramMap.put(Fields.PARAM_DISTRIBUTRO_LIST, commonService.queryDistributorListByUserNo(userNo));
        }
        //获取业务员编号
        NlbsLoginInfo nlbsLoginInfo = nlbsLoginInfoMapper.queryNlbsUserByUserNo(userNo);
        if (nlbsLoginInfo != null) {
            paramMap.put(Fields.PARAM_AGENT_ID, nlbsLoginInfo.getAgentId());
        }
        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300050", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.put("dataList", plmsBodyMap.get("dataList"));
                returnMap.put(Fields.PARAM_TOTAL, plmsBodyMap.get(Fields.PARAM_TOTAL));
                returnMap.put(Fields.PARAM_PAGES, plmsBodyMap.get(Fields.PARAM_PAGES));
                returnMap.put(Fields.PARAM_CURRENT_PAGE, plmsBodyMap.get(Fields.PARAM_CURRENT_PAGE));
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取逾期记录列表，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取逾期记录列表，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取逾期记录列表成功！");

        return returnMap;
    }

    /**
     * @Description:逾期记录详情查询
     * @param:
     * @return:
     * @Author: liuzhu.feng
     * @Date: 2017/9/8/0008
     */
    @Override
    public Map plms300060(Map paramMap) throws Exception {
        Map returnMap = new HashMap();
        //整理参数，调用PLMS获取相应的初始化接口
        Map plmsReturnMap = remotePlmsService.callService("plms300060", paramMap);
        if(plmsReturnMap != null){
            Map plmsBodyMap = (Map) plmsReturnMap.get("body");
            if(plmsBodyMap != null) {
                returnMap.putAll(plmsBodyMap);
            }else{
                throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取逾期记录详细，贷后消息体异常");
            }
        } else {
            throw new HHBizException(ReturnCode.SYSTEM_EXCEPTION, "获取逾期记录详细，调用贷后系统返回为空");
        }

        returnMap.put(Fields.PARAM_MESSAGE_ERR_CODE, ReturnCode.SUCCESS_CODE);
        returnMap.put(Fields.PARAM_MESSAGE_ERR_MESG, "获取逾期记录详细成功！");

        return returnMap;
    }
}
