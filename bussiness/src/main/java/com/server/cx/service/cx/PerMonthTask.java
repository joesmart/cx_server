package com.server.cx.service.cx;

import com.server.cx.exception.SystemException;

public interface PerMonthTask {
    public void doTask() throws SystemException;
}
