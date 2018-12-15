package com.vilio.ppms.util;

import com.vilio.ppms.dao.common.RequestLogMapper;
import com.vilio.ppms.glob.Fields;
import com.vilio.ppms.pojo.common.RequestLogWithBLOBs;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by wangxf on 2017/6/15.
 */
public class PpmsUtil {

    private static final Logger logger = Logger.getLogger(PpmsUtil.class);

    /**
     * 通用返回方法
     *
     * @param request
     * @param response
     * @param result
     */
    public static void returnData(HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> result) {
        PrintWriter pw = null;
        String respMessage = null;
        RequestLogWithBLOBs requestLogWithBLOBs = (RequestLogWithBLOBs) request.getAttribute("requestLogWithBLOBs");
        RequestLogMapper requestLogMapper = (RequestLogMapper)SpringContextUtil.getBean("RequestLogMapper");
        Map<String, Object> head = (Map<String, Object>) result.get(Fields.PARAM_MESSAGE_HEAD);
        //更新日志表返回码和返回信息
        requestLogWithBLOBs.setReturnCode(ObjectUtils.toString(head.get(Fields.PARAM_MESSAGE_ERR_CODE)));
        requestLogWithBLOBs.setReturnMessage(ObjectUtils.toString(head.get(Fields.PARAM_MESSAGE_ERR_MESG)));
        requestLogMapper.updateByPrimaryKeySelective(requestLogWithBLOBs);
        respMessage = JsonUtil.objectToJson(result);
        logger.info(respMessage);
        try {
            if (respMessage != null) {
                pw = response.getWriter();
                pw.print(respMessage);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.error("返回信息失败！");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
