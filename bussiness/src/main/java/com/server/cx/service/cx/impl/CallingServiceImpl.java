package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.MGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CallingService;
import com.server.cx.service.util.BusinessFunctions;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("callingService")
@Transactional
public class CallingServiceImpl implements CallingService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CallingServiceImpl.class);
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private MGraphicDao mGraphicDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Override
    public DataItem getCallingMGraphic(Optional<String> imsi, Optional<String> callPhoneNo) throws SystemException {
        Preconditions.checkNotNull(imsi.isPresent(),"imsi 为空");
        Preconditions.checkNotNull(callPhoneNo.isPresent(),"imsi 为空");

        UserInfo userInfo = userInfoDao.findByImsi(imsi.get());

        UserInfo callerUserInfo = userInfoDao.findByPhoneNo(callPhoneNo.get());

        if(callerUserInfo == null){
            return businessFunctions.mGraphicTransformToDataItem(imsi.get()).apply(getSystemDefaultMGraphic(callPhoneNo));
        }

        int maxPriority = mGraphicDao.queryMaxPriorityByUserInfo(callerUserInfo,userInfo == null?null:userInfo.getPhoneNo());
        MGraphic mGraphic;
        if(maxPriority == -1){
            mGraphic = getSystemDefaultMGraphic(callPhoneNo);
        }else {
            List<MGraphic> mGraphics = mGraphicDao.queryUserMGraphics(callerUserInfo,maxPriority, userInfo == null?null:userInfo.getPhoneNo());
            if(mGraphics != null && mGraphics.size()>0){
                mGraphic = mGraphics.get(RandomUtils.nextInt(mGraphics.size()));
            }else {
                mGraphic = getSystemDefaultMGraphic(callPhoneNo);
                LOGGER.error("calling phone error:should have a user MGraphic!");
            }
        }

        return businessFunctions.mGraphicTransformToDataItem(imsi.get()).apply(mGraphic);
    }

    private MGraphic getSystemDefaultMGraphic(Optional<String> callPhoneNo) {
        return mGraphicDao.queryDefaultMGraphic(callPhoneNo.get());
    }

}