/*
 * Copyright (c) 2011 CieNet, Ltd.
 * Created on 2011-9-21
 */
package com.server.cx.service.cx;

import com.server.cx.exception.SystemException;

import java.util.Map;

/**
 * register service interface. Provide register method.
 */
public interface RegisterService {

    String registe(Map<String, String> paramsMap) throws SystemException;

}
