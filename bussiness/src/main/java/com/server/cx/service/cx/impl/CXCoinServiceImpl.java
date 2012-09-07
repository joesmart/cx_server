package com.server.cx.service.cx.impl;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.cl.cx.platform.dto.CXCoinAccountDTO;
import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.OperationDescription;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.CXCoinTotalItemDao;
import com.server.cx.dao.cx.UserSubscribeRecordDao;
import com.server.cx.dao.cx.spec.SubscribeRecordSpecifications;
import com.server.cx.entity.cx.CXCoinAccount;
import com.server.cx.entity.cx.CXCoinTotalItem;
import com.server.cx.entity.cx.UserSubscribeRecord;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CXCoinService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.ObjectFactory;

@Component
@Transactional(readOnly = true)
public class CXCoinServiceImpl extends CXCoinBasicService implements CXCoinService {
    @Autowired
    private CXCoinTotalItemDao cxCoinTotalItemDao;
    
    @Autowired
    private UserSubscribeRecordDao userSubscribeRecordDao;
    
    @Autowired
    private BusinessFunctions businessFunctions;
    
    @Autowired
    private BasicService basicService;
    
    @Override
    @Transactional(readOnly = false)
    public OperationDescription register(String imsi, CXCoinAccountDTO coinAccountDTO) throws SystemException {
        checkUserUnRegisterCXCoinAccount(imsi);
        CXCoinAccount cxCoinAccount = new CXCoinAccount();
        cxCoinAccount.setName(coinAccountDTO.getName());
        cxCoinAccount.setPassword(coinAccountDTO.getPassword());
        
        CXCoinTotalItem cxCoinTotalItem = findCXCoinTotalItem();
        checkCXCoinTotalEnough(cxCoinTotalItem.getCxCoinCount(), 50D);
        cxCoinTotalItem.setCxCoinCount(cxCoinTotalItem.getCxCoinCount() - 5D);
        cxCoinTotalItemDao.save(cxCoinTotalItem);
        
        UserSubscribeRecord userSubscribeRecord = ObjectFactory.buildUserCXCoinIncomeRecord(userInfo, 50D, "用户注册");
        userSubscribeRecordDao.save(userSubscribeRecord);
        
        cxCoinAccount.setCoin(50D);
        cxCoinAccountDao.save(cxCoinAccount);
        OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
            HttpServletResponse.SC_CREATED, "register");
        return operationDescription;
    }

    private void checkCXCoinTotalEnough(double cxCoinCount, double consumeCXCoinCount) {
        if (cxCoinCount < consumeCXCoinCount) {
            throw new SystemException("酷币总额不足");
        }

    }

    private CXCoinTotalItem findCXCoinTotalItem() {
        List<CXCoinTotalItem> cxCoinTotalItems = cxCoinTotalItemDao.findAll();
        if (cxCoinTotalItems != null && !cxCoinTotalItems.isEmpty()) {
            return cxCoinTotalItems.get(0);
        }
        throw new SystemException("没有提供酷币总额");
    }

    @Override
    public OperationDescription login(String imsi, CXCoinAccountDTO coinAccountDTO) throws SystemException {
        checkAndSetUserInfoExists(imsi);
        CXCoinAccount cxCoinAccount = cxCoinAccountDao.findByNameAndPasswordAndUserInfo(coinAccountDTO.getName(),
            coinAccountDTO.getPassword(), userInfo);
        if (cxCoinAccount != null) {
            OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
                HttpServletResponse.SC_NO_CONTENT, "login");
            return operationDescription;
        }
        return ObjectFactory.buildErrorOperationDescription(HttpServletResponse.SC_NOT_ACCEPTABLE, "login", "登录失败");
    }

    @Override
    @Transactional(readOnly = false)
    public OperationDescription consumeCXCoin(String imsi, CXCoinAccountDTO cxCoinAccountDTO) throws SystemException {
        checkUserRegisterCXCoinAccount(imsi);
        checkUserUnConsumeCXCoin(userInfo);
        CXCoinTotalItem cxCoinTotalItem = findCXCoinTotalItem();
        checkCXCoinTotalEnough(cxCoinTotalItem.getCxCoinCount(), 5D);
        cxCoinTotalItem.setCxCoinCount(cxCoinTotalItem.getCxCoinCount() - 5D);
        cxCoinTotalItemDao.save(cxCoinTotalItem);
        
        UserSubscribeRecord userSubscribeRecord = ObjectFactory.buildUserCXCoinIncomeRecord(userInfo, 5D, "抢酷币");
        userSubscribeRecordDao.save(userSubscribeRecord);
        
        cxCoinAccount.setCoin(cxCoinAccount.getCoin() + 5);
        cxCoinAccountDao.save(cxCoinAccount);
        OperationDescription operationDescription = ObjectFactory.buildOperationDescription(
            HttpServletResponse.SC_NO_CONTENT, "consumeCXCoin");
        return operationDescription;
    }

    @Override
    public CXCoinAccount getCXCoinAccount(String imsi) throws SystemException {
        checkUserRegisterCXCoinAccount(imsi);
        return cxCoinAccount;
    }
    

    @Override
    public DataPage getUserCXCoinRecords(String imsi, Integer offset, Integer limit) {
        checkUserRegisterCXCoinAccount(imsi);
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.DESC, "createdOn");
        Page page = userSubscribeRecordDao.findAll(SubscribeRecordSpecifications.userSubscribeRecord(userInfo),
            pageRequest);
        List<UserSubscribeRecord> userSubscribeRecords = page.getContent();
        List<DataItem> dataItems = transformToSubscribeRecordList(imsi, userSubscribeRecords);
        return generateDataPage(imsi, offset, limit, page, dataItems);
    }

    private List<DataItem> transformToSubscribeRecordList(String imsi, List<UserSubscribeRecord> userSubscribeRecords) {
        return Lists.transform(userSubscribeRecords, businessFunctions.subscribeRecordTransferormToDataItem());
    }

    private DataPage generateDataPage(String imsi, Integer offset, Integer limit, Page page, List<DataItem> dataItems) {
        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(page.getTotalPages());
        dataPage.setItems(dataItems);
        dataPage.setHref(generatePageURL(imsi, offset, limit));

        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(generatePageURL(imsi, previousOffset, limit));
        }
        if (offset + 1 < dataPage.getTotal()) {
            int nextOffset = offset + 1;
            dataPage.setNext(generatePageURL(imsi, nextOffset, limit));
        }
        dataPage.setFirst(generatePageURL(imsi, 0, limit));
        dataPage.setLast(generatePageURL(imsi, (dataPage.getTotal() - 1), limit));
        return dataPage;
    }

    private String generatePageURL(String imsi, int offset, Integer limit) {
        return basicService.generateURL(imsi,"/cxCoin/records?" + "&offset=" + offset + "&limit="  + limit);
    }
    
    

}