package com.vilio.pcfs.dao;

import com.vilio.pcfs.pojo.Verify;

import java.util.Map;

/**
 * Created by dell on 2017/7/4.
 */
public interface VerifyDao {

    //初始化入库
    public int insertVerify(Verify verify);

    //更改验证码状态
    public int updateVerifyStatus(Map param);

    //根据手机号和验证码查询最近一条验证码流水
    public Verify queryVerifyByMobileAndVerify(Verify verify);


    //更改验证码状态
    public int updateVerifyStatusById(Verify verify);

    //更改验证码状态
    public int checkVerifySecond(Map param);

}
