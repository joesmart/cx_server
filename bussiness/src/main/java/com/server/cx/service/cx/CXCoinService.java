package com.server.cx.service.cx;

import com.cl.cx.platform.dto.CXCoinAccountDTO;
import com.cl.cx.platform.dto.CXCoinNotfiyDataDTO;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.entity.cx.CXCoinNotfiyData;
import com.server.cx.exception.SystemException;

public interface CXCoinService {

    public OperationDescription register(String imsi, CXCoinAccountDTO coinAccountDTO) throws SystemException;

    public OperationDescription login(String imsi, CXCoinAccountDTO coinAccountDTO) throws SystemException;

    public CXCoinAccount consumeCXCoin(String imsi, CXCoinAccountDTO cxCoinAccountDTO) throws SystemException;

    public CXCoinAccount getCXCoinAccount(String imsi) throws SystemException;

    public abstract DataPage getUserCXCoinRecords(String name, String password, String imsi, Integer offset,
                                                  Integer limit);

    public void handleCXCoinPurchaseCallback(CXCoinNotfiyData cxCoinNotfiyData) throws SystemException;

    public CXCoinAccount confirmPurchase(String imsi, String outTradeNo, CXCoinAccountDTO cxCoinAccountDTO)
        throws SystemException;

    public OperationDescription preparePurchase(String imsi, String accountName, CXCoinNotfiyDataDTO cxCoinAccountDTO);

}
