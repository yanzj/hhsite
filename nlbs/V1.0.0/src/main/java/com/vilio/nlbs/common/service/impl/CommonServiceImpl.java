package com.vilio.nlbs.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vilio.nlbs.common.dao.CommonDao;
import com.vilio.nlbs.common.service.CommonService;
import com.vilio.nlbs.commonMapper.dao.*;
import com.vilio.nlbs.commonMapper.pojo.*;
import com.vilio.nlbs.user.dao.UserDao;
import com.vilio.nlbs.util.BPSStatus;
import com.vilio.nlbs.util.Constants;
import com.vilio.nlbs.util.Fields;
import com.vilio.nlbs.util.ReturnCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by xiezhilei on 2017/1/12.
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Resource
    CommonDao commonDao;

    @Resource
    UserDao userDao;

    @Resource
    NlbsDistributorMapper nlbsDistributorMapper;

    @Resource
    NlbsCityMapper nlbsCityMapper;

    @Resource
    NlbsUserMapper nlbsUserMapper;

    @Resource
    NlbsRoleUserMapper nlbsRoleUserMapper;

    public String getUUID() throws Exception{
        return commonDao.getUUID();
    }

    public List<NlbsDistributor> selectChildrenListDistributor(String distributorCode) throws Exception {
        List<NlbsDistributor> nlbsDistributorList = new ArrayList<NlbsDistributor>();
        List<NlbsDistributor> distributorList = nlbsDistributorMapper.selectChildListDistributor(distributorCode);
        if(distributorList != null){
            nlbsDistributorList.addAll(distributorList);
            for(NlbsDistributor nb : distributorList){
                nlbsDistributorList.addAll(selectChildrenListDistributor(nb.getCode()));
            }
        }
        //去除重复项
        HashSet hs = new HashSet(nlbsDistributorList);
        nlbsDistributorList.clear();
        nlbsDistributorList.addAll(hs);
        return nlbsDistributorList;
    }

    /**
     * 查找所有下级用户（不含自身）
     * @param userNo
     * @return
     * @throws Exception
     */
    public List<NlbsUser> selectChildrenListUser(String userNo) throws Exception {
        List<NlbsUser> nlbsUserList = new ArrayList<NlbsUser>();
        List<NlbsUser> userList = nlbsUserMapper.selectChildListUser(userNo);
        if(userList != null){
            nlbsUserList.addAll(userList);
            for(NlbsUser nb : userList){
                nlbsUserList.addAll(selectChildrenListUser(nb.getUserNo()));
            }
        }
        //去除重复项
        HashSet hs = new HashSet(nlbsUserList);
        nlbsUserList.clear();
        nlbsUserList.addAll(hs);
        return nlbsUserList;
    }

    public List<Map> queryAllMaterialTypeList() throws Exception {
        return commonDao.queryAllMaterialTypeList();
    }

    public List<Map> queryAllApplyRecordStatusList() throws Exception {
        return commonDao.queryAllApplyRecordStatusList();
    }

    public List<NlbsCity> queryAllCity() throws Exception {
        return nlbsCityMapper.queryAllCity();
    }

    @Override
    public NlbsUser queryNlbsUserByUserNoIgnoreStatus(String userNo) throws Exception {
        return userDao.queryNlbsUserByUserNoIgnoreStatus(userNo);
    }

    /**
     * 判断用户是否是超级管理员
     * @param userNo--需判断的用户
     * @param roleList--管理员的角色代码列表
     * @return
     * @throws Exception
     */
    @Override
    public boolean isAdministrator(String userNo, List<String> roleList) throws Exception {

        if(StringUtils.isBlank(userNo)){
            return false;
        }
        //获取当前用户所拥有的所有角色
        NlbsRoleUser nlbsRoleUser = new NlbsRoleUser();
        nlbsRoleUser.setUserNo(userNo);
        List<NlbsRoleUser> nlbsRoleUserList = nlbsRoleUserMapper.selectUserRole(nlbsRoleUser);

        if(roleList != null && nlbsRoleUserList != null){
            for(String str : roleList){
                for(NlbsRoleUser nru : nlbsRoleUserList){
                    if(str.equals(nru.getRoleCode())){
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
