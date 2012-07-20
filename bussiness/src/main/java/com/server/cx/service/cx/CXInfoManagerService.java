package com.server.cx.service.cx;

import com.server.cx.entity.cx.CXInfo;
import com.server.cx.exception.SystemException;

import java.util.List;
import java.util.Map;

public interface CXInfoManagerService {
	public List<CXInfo> browserAllData(Map<String, String> paramsMap) throws SystemException;
	
	public void addNewCXInfo(final String serverPath, CXInfo cxInfo) throws SystemException;
}
