package com.server.cx.dao.cx;

import com.server.cx.entity.cx.StatusPackage;
import com.server.cx.exception.SystemException;

public interface StatusPackageDao {
    
    public StatusPackage getDefaultStatusPackage() throws SystemException;
    
}
