package com.vilio.plms.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by martin on 2017/9/5.
 */
public interface BmsSynchronizateDao {
    public void insert(Map map);
    public String qryBmsSynchronizeInfo();
    public List qryInitBmsSynchronize();
    public void update(Map map);
}
