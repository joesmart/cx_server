package com.server.cx.service.cx.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.UserStatusMGraphicDao;
import com.server.cx.service.cx.UserStatusDismissService;

@Component
@Transactional(readOnly = true)
public class UserStatusDismissServiceImpl implements UserStatusDismissService {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserStatusDismissServiceImpl.class);
    @Autowired
    private UserStatusMGraphicDao userStatusMGraphicDao;
    
    @Transactional(readOnly = false)
    @Override
    public void clearUserStatusMGraphic() {
        LOGGER.info("Into clearUserStatusMGraphic");
        userStatusMGraphicDao.deleteAll();
    }

}
