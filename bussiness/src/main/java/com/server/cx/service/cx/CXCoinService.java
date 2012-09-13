package com.server.cx.service.cx;

import com.cl.cx.platform.dto.CXCoinAccountDTO;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.exception.SystemException;

public interface CXCoinService {

    public OperationDescription register(String imsi, CXCoinAccountDTO coinAccountDTO) throws SystemException;

    public OperationDescription login(String imsi, CXCoinAccountDTO coinAccountDTO) throws SystemException;

    public CXCoinAccount consumeCXCoin(String imsi, CXCoinAccountDTO cxCoinAccountDTO) throws SystemException;

    public CXCoinAccount getCXCoinAccount(String imsi) throws SystemException;

    public abstract DataPage getUserCXCoinRecords(String name, String password, String imsi, Integer offset, Integer limit);

}
