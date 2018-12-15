package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.CutomCardNo;

public interface CutomCardNoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CutomCardNo record);

    int insertSelective(CutomCardNo record);

    CutomCardNo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CutomCardNo record);

    int updateByPrimaryKey(CutomCardNo record);
}