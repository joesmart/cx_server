package com.server.cx.service.cx;

import com.server.cx.exception.SystemException;

import java.util.Map;

public interface CXCallingManagerService {
    public String getCallingCXInfo(Map<String, String> paramsMap) throws SystemException;
}
