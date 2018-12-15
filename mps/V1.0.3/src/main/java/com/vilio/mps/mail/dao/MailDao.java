package com.vilio.mps.mail.dao;

import com.vilio.mps.mail.pojo.MailInfo;

/**
 * 类名： MailDao<br>
 * 功能：邮件Dao层<br>
 * 版本： 1.0<br>
 * 日期： 2017年6月27日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface MailDao {

    public int insertMailInfo(MailInfo mail);


    public int updateMailStatusBySerialNo(MailInfo mail);

}
