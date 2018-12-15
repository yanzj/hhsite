package com.vilio.plms.service.quartz.impl;

import com.alibaba.fastjson.JSON;
import com.vilio.plms.dao.BmsSynchronizateDao;
import com.vilio.plms.glob.GlobDict;
import com.vilio.plms.service.bms.impl.BmsSynchronizationServiceImpl;
import com.vilio.plms.service.quartz.BmsSynchronizeService;
import com.vilio.plms.util.JsonUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
//import javax.json.Json;
//import javax.json.JsonObject;
import java.util.*;

/**
 * Created by martin on 2017/9/5.
 */
@Service("bmsSynchronizeService")
public class BmsSynchronizeServiceImpl implements BmsSynchronizeService {
    private static Logger logger = Logger.getLogger(BmsSynchronizeServiceImpl.class);
    @Resource
    BmsSynchronizationServiceImpl bmsSynchronizationService;
    @Resource
    BmsSynchronizateDao bmsSynchronizateDao;
    public void execute() throws Exception{
        List bmsSynchronizeList = (ArrayList)bmsSynchronizateDao.qryInitBmsSynchronize();
        for (int i = 0;i<bmsSynchronizeList.size();i++) {
            Map bmsSynchronize = (HashMap)bmsSynchronizeList.get(i);
            String synchronizeInfo = (String) bmsSynchronize.get("synchronizeInfo");
            Map jsonObject = JSON.parseObject(synchronizeInfo);
//            JSONObject jsonObject = JSONObject.fromObject(synchronizeInfo);
            Map synchronizeInfoMap = (Map)jsonObject;
//            Map synchronizeInfoMap = (TreeMap) JsonUtil.jsonToMap(synchronizeInfo);
            try {
                bmsSynchronize.put("status",GlobDict.bms_synchronize_status_executing.getKey());
                bmsSynchronizateDao.update(bmsSynchronize);
                bmsSynchronizationService.synchronize(synchronizeInfoMap);
                bmsSynchronize.put("status", GlobDict.bms_synchronize_status_executed.getKey());
                bmsSynchronizateDao.update(bmsSynchronize);
            }catch (Exception e){
                bmsSynchronize.put("status", GlobDict.bms_synchronize_status_exception.getKey());
                bmsSynchronizateDao.update(bmsSynchronize);
                logger.error("bms同步任务异常:");
                e.printStackTrace();
            }
        }
    }

}
