package com.vilio.plms.dao;

import com.vilio.plms.pojo.EmailInfo;

/**
 * 类名： EmailInfoDao<br>
 * 功能：邮件信息Dao<br>
 * 版本： 1.0<br>
 * 日期： 2017年9月5日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface EmailInfoDao {
    //插入历史流水表记录
    public int insertEmailInfoLog(EmailInfo emailInfo);

    public int updateEmailInfoSendStatus(EmailInfo emailInfo);

}
