package com.server.cx.service.cx.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.CXCoinConsumeRecordDao;
import com.server.cx.dao.cx.UserStatusMGraphicDao;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserDailyService;

@Component
@Transactional(readOnly = true)
public class UserDailyServiceImpl implements UserDailyService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserDailyServiceImpl.class);
    @Autowired
    private UserStatusMGraphicDao userStatusMGraphicDao;
    
    @Autowired
    private CXCoinConsumeRecordDao cxCoinConsumeRecordDao;

    @Transactional(readOnly = false)
    private void clearUserStatusMGraphic() {
        LOGGER.info("Into clearUserStatusMGraphic");
        userStatusMGraphicDao.deleteAll();
    }

    @Override
    public void doDailyTask() throws SystemException {
        clearUserStatusMGraphic();
        clearCXCoinConsumeRecord();
    }
    
    @Transactional(readOnly = false)
    private void clearCXCoinConsumeRecord() {
        LOGGER.info("Into clearCXCoinConsumeRecord");
        cxCoinConsumeRecordDao.deleteAll();
    }

}
