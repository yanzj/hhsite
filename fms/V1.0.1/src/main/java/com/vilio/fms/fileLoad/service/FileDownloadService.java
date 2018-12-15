package com.vilio.fms.fileLoad.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by dell on 2017/5/17.
 */
public interface FileDownloadService {
    public Map getFile(Map map) throws Exception;

    public Map getFileZip(Map map) throws Exception;

    public Map getUrlList(Map map) throws Exception;
}
