package com.server.cx.service.cx.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.server.cx.dao.cx.SubscribeTypeDao;
import com.server.cx.entity.cx.SubscribeType;
import com.server.cx.service.cx.SubscribeTypeService;

@Component
@Transactional(readOnly = true)
public class SubscribeTypeServiceImpl implements SubscribeTypeService {
    @Autowired
    private SubscribeTypeDao subscribeTypeDao;
    
    @Transactional(readOnly = false)
    @Override
    public void batchSaveSubscribeType(List<SubscribeType> subscribeTypes) {
        subscribeTypeDao.batchSaveSubscribeType(subscribeTypes);
    }

}
