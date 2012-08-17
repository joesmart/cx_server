/*
 * Copyright (c) 2011 CieNet, Ltd. Created on 2011-9-21
 */
package com.server.cx.service.cx;

import com.cl.cx.platform.dto.OperationDescription;
import com.cl.cx.platform.dto.RegisterDTO;


/**
 * register service interface. Provide register method.
 */
public interface RegisterService {

    public abstract OperationDescription register(RegisterDTO registerDTO, String phoneNo);

}
