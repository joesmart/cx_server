package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.server.cx.dao.cx.MGraphicDao;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.MGraphic;
import com.server.cx.entity.cx.UserCommonMGraphic;
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
    private UserCommonMGraphicDao userCommonMGraphicDao;

    @Autowired
    private BusinessFunctions businessFunctions;

    @Override
    public DataItem getCallingMGraphic(Optional<String> imsi, Optional<String> callPhoneNo) throws SystemException {
        Preconditions.checkNotNull(imsi.isPresent(),"imsi 为空");
        Preconditions.checkNotNull(callPhoneNo.isPresent(),"imsi 为空");
        //本人的UserInfo
        UserInfo userInfo = userInfoDao.findByImsi(imsi.get());
        UserInfo callerUserInfo;
        if(callPhoneNo.equals(userInfo.getPhoneNo())){
            callerUserInfo = userInfo;
        }else {
            //根据手机号码得到对方 UserInfo
            callerUserInfo = userInfoDao.findByPhoneNo(callPhoneNo.get());
        }

        if(callerUserInfo == null){
            return businessFunctions.mGraphicTransformToDataItem(imsi.get(), "mGraphics").apply(getSystemDefaultMGraphic(callPhoneNo));
        }

        int maxPriority = mGraphicDao.queryMaxPriorityByUserInfo(callerUserInfo,userInfo == null?null:userInfo.getPhoneNo());
        MGraphic mGraphic;
        if(maxPriority == -1){
            mGraphic = getSystemDefaultMGraphic(callPhoneNo);
        }else {
            List<UserCommonMGraphic> mGraphics = userCommonMGraphicDao.queryUserMGraphics(callerUserInfo,maxPriority, userInfo == null?null:userInfo.getPhoneNo());
            if(mGraphics != null && mGraphics.size()>0){
                if(maxPriority !=5 && maxPriority != 6){
                    mGraphic = mGraphics.get(RandomUtils.nextInt(mGraphics.size()));
                }else if(maxPriority == 5 || maxPriority ==6){
                    mGraphic = mGraphics.get(0);
                }else {
                    mGraphic = getSystemDefaultMGraphic(callPhoneNo);
                    LOGGER.error("calling phone error:should have a user MGraphic!");
                }
            }else {
                mGraphic = getSystemDefaultMGraphic(callPhoneNo);
                LOGGER.error("calling phone error:should have a user MGraphic!");
            }

        }

        return businessFunctions.mGraphicTransformToDataItem(imsi.get(), "mGraphics").apply(mGraphic);
    }

    private MGraphic getSystemDefaultMGraphic(Optional<String> callPhoneNo) {
        return mGraphicDao.queryDefaultMGraphic(callPhoneNo.get());
    }

}
