package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Optional;
import com.server.cx.exception.SystemException;

public interface CallingService {
    public DataItem getCallingMGraphic(Optional<String> imsi, Optional<String> callPhoneNo) throws SystemException;
}
