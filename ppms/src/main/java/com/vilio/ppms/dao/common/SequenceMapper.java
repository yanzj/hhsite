package com.vilio.ppms.dao.common;

import com.vilio.ppms.pojo.common.Sequence;

public interface SequenceMapper {
    int insert(Sequence record);

    int insertSelective(Sequence record);
}