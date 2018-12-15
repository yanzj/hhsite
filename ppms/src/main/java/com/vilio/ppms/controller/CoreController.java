package com.vilio.ppms.controller;

import com.vilio.ppms.service.base.CoreService;
import com.vilio.ppms.util.PpmsUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 类名： CoreController<br>
 * 功能：核心请求Controller类<br>
 * 版本： 1.0<br>
 * 日期： 2017年7月7日<br>
 * 作者： wangxf<br>
 * 版权：vilio<br>
 * 说明：<br>
 */
@Controller
public class CoreController {

    private static final Logger logger = Logger.getLogger(CoreController.class);

    @Resource
    private CoreService coreService;

    /**
     * 核心controller
     *
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping("/payCore.json")
    public void mobileCore(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Map<String, Object> params = (Map<String, Object>) request.getAttribute("params");
        Map<String, Object> respMap = coreService.doMain(params);
        PpmsUtil.returnData((HttpServletRequest) request, (HttpServletResponse) response, respMap);
    }

}
