package com.vilio.bps.inquiry.shchengshi.service;

import com.vilio.bps.inquiry.shchengshi.pojo.SCProject;
import com.vilio.bps.inquiry.shchengshi.pojo.SCUnit;

import java.util.List;

/**
 * Created by dell on 2017/6/6/0006.
 */
public interface SCGetHousingInfo {

    public List<SCProject> getProjectList(String key) throws Exception;

    public List<SCUnit> getUnitList(String projectId, String key) throws Exception;

}
