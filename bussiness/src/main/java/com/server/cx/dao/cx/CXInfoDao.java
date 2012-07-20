package com.server.cx.dao.cx;

import com.server.cx.entity.cx.CXInfo;
import com.server.cx.exception.SystemException;

import java.util.List;
import java.util.Map;

public interface CXInfoDao {

    public abstract List<CXInfo> browserAllData(Map<String, String> params) throws SystemException;
    public List<CXInfo> getCMCCCXInfos();

}
