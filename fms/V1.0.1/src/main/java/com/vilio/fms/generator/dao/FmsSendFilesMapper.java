package com.vilio.fms.generator.dao;/**
 * Created by dell on 2017/8/28/0028.
 */


import com.vilio.fms.generator.pojo.FmsSendFiles;

import java.util.List;
import java.util.Map;

/**
 * 类名： FmsSendFilesMapper<br>
 * 功能：<br>
 * 版本： 1.0<br>
 * 日期： 2017/8/28/0028<br>
 * 作者： liuzhu.feng<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
public interface FmsSendFilesMapper {

    public int insertSendFiles(FmsSendFiles fmsSendFiles) throws Exception;

    public List<FmsSendFiles> querySendFilesBySendCode(FmsSendFiles fmsSendFiles) throws Exception;


}
