package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.server.cx.dao.cx.MGraphicDao;
import com.server.cx.dao.cx.UserCommonMGraphicDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.entity.cx.*;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CallingService;
import com.server.cx.service.util.BusinessFunctions;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.Date;
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
        Preconditions.checkNotNull(imsi.isPresent(), "imsi 为空");
        Preconditions.checkNotNull(callPhoneNo.isPresent(), "imsi 为空");
        //本人的UserInfo
        UserInfo userInfo = userInfoDao.findByImsi(imsi.get());
        UserInfo callerUserInfo;
        MGraphic mGraphic ;
        if (callPhoneNo.get().equals(userInfo.getPhoneNo())) {
            callerUserInfo = userInfo;
        } else {
            //根据手机号码得到对方 UserInfo
            callerUserInfo = userInfoDao.findByPhoneNo(callPhoneNo.get());
        }

        if (callerUserInfo == null) {
            mGraphic = getSystemDefaultMGraphic(callPhoneNo);
            return businessFunctions.mGraphicTransformToDataItem(imsi.get(), "mGraphics").apply(mGraphic);
        }


        final String phoneNo = userInfo.getPhoneNo();
        List<MGraphic> userMGraphics = mGraphicDao.findByUserInfoOrderByCreatedOnAsc(callerUserInfo);

        final CallingMGraphic callingMGraphic = new CallingMGraphic();
        List<MGraphic> afterFilerList = Lists.newArrayList(Iterables.filter(userMGraphics, new Predicate<MGraphic>() {
            @Override
            public boolean apply(@Nullable MGraphic input) {
                if (input == null) {
                    return false;
                }
                if (input.getModeType() == 2) {
                    UserCommonMGraphic userCommonMGraphic = (UserCommonMGraphic) input;
                    if (userCommonMGraphic.getCommon()) {
                        updateCallingMGraphicStatus(input, userCommonMGraphic, callingMGraphic);
                    } else {
                        if (userCommonMGraphic.getPhoneNos().contains(phoneNo)) {
                            updateCallingMGraphicStatus(input, userCommonMGraphic, callingMGraphic);
                        }
                    }

                }

                if (input.getModeType() == 3) {
                    UserCustomMGraphic userCustomMGraphic = (UserCustomMGraphic) input;

                    if (userCustomMGraphic.getBegin() == null || userCustomMGraphic.getEnd() == null) {
                        userCustomMGraphic.setBegin(LocalDate.parse("1900-1-1").toDate());
                        userCustomMGraphic.setEnd(LocalDate.parse("2100-1-1").toDate());
                    }
                    Date end = userCustomMGraphic.getEnd();
                    Date begin = userCustomMGraphic.getBegin();
                    Date currentDate = LocalDate.now().toDate();
                    if (userCustomMGraphic.getCommon() && timeCompared(currentDate, begin, end)) {
                        updateCallingMGraphicStatus(input, userCustomMGraphic, callingMGraphic);
                    } else {
                        if (userCustomMGraphic.getPhoneNos().contains(phoneNo) && timeCompared(currentDate, begin, end)) {
                            updateCallingMGraphicStatus(input, userCustomMGraphic, callingMGraphic);
                        }
                    }
                }

                if (input.getModeType() == 4) {
                    UserHolidayMGraphic userHolidayMGraphic = (UserHolidayMGraphic) input;
                    Date currentDate = LocalDate.now().toDate();
                    if (userHolidayMGraphic.getCommon() && currentDate.equals(userHolidayMGraphic.getHoliday())) {
                        updateCallingMGraphicStatus(input, userHolidayMGraphic, callingMGraphic);
                    } else {
                        if (userHolidayMGraphic.getPhoneNos().contains(phoneNo) && currentDate.equals(userHolidayMGraphic.getHoliday())) {
                            updateCallingMGraphicStatus(input, userHolidayMGraphic, callingMGraphic);
                        }
                    }
                }

                if (input.getModeType() == 5) {
                    UserStatusMGraphic userStatusMGraphic = (UserStatusMGraphic) input;
                    Date currentDate = LocalDate.now().toDate();
                    if (currentDate.equals(userStatusMGraphic.getValidDate())) {
                        updateCallingMGraphicStatus(input, userStatusMGraphic, callingMGraphic);
                    }
                }
                return true;
            }
        }));
        LOGGER.info("" + afterFilerList.size());
        mGraphic = callingMGraphic.retrieveMaxPriorityMGraphic();
        if (mGraphic == null) {
            mGraphic = getSystemDefaultMGraphic(callPhoneNo);
            LOGGER.error("calling phone error:should have a user MGraphic!");
        }

        return businessFunctions.mGraphicTransformToDataItem(imsi.get(), "mGraphics").apply(mGraphic);
    }

    private boolean timeCompared(Date currentDate, Date begin, Date end) {

        if (currentDate != null && begin != null && end != null) {
            if (currentDate.getTime() == begin.getTime() || currentDate.getTime() == end.getTime()) {
                return true;
            } else {
                return currentDate.before(end) && currentDate.after(begin);
            }
        }else {
            return false;
        }
    }

    private void updateCallingMGraphicStatus(MGraphic input, MGraphic mgraphic, CallingMGraphic callingMGraphic) {
        if (mgraphic.getPriority() >= callingMGraphic.getMaxPriority()) {
            callingMGraphic.setMaxPriority(mgraphic.getPriority());
            callingMGraphic.put(mgraphic.getPriority(), input);
        }
    }

    private MGraphic getSystemDefaultMGraphic(Optional<String> callPhoneNo) {
        return mGraphicDao.queryDefaultMGraphic(callPhoneNo.get());
    }

}
