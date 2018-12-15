package com.vilio.plms.service.base;

import com.vilio.plms.glob.Fields;
import com.vilio.plms.glob.GlobParam;
import com.vilio.plms.glob.ReturnCode;
import com.vilio.plms.util.CommonUtil;
import com.vilio.plms.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiezhilei on 2017/8/15.
 */
@Service
public class RemoteFmsService {
    private static Logger logger = Logger.getLogger(RemoteFmsService.class);
    /**
     * 获取文件信息
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map getUrlList(Map paramMap) throws Exception {
        Map result = new HashMap();
        JSONObject response = null;
        Map remoteMap = new HashMap();
        Map headMap = new HashMap();
        remoteMap.put(Fields.PARAM_MESSAGE_HEAD, headMap);
        remoteMap.put(Fields.PARAM_MESSAGE_BODY, paramMap);
        try {
            JSONObject jsonParam = JSONObject.fromObject(remoteMap);
            String url = GlobParam.fmsUrl + "/fileLoad/getUrlList";
            response = HttpUtil.httpPost(url,jsonParam);

        }catch (Exception e){
            logger.error("获取文件列表异常：",e);
        }

        result = CommonUtil.toMap(response);
        return result;
    }

    public InputStream getFile(String fileCode) throws Exception{

        InputStream in = null;
        try {
            String url = GlobParam.fmsUrl + "/fileLoad/getFile" + "?serialNo=" + fileCode;
            in = HttpUtil.httpGetFile(url);
        }catch (Exception e){
            logger.error("下载文件异常：",e);
        }

        return in;
    }
}
