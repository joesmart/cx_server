package com.server.cx.util.business;

import com.server.cx.entity.cx.UserCommonMGraphic;

import java.util.Calendar;

public class MGraphicStoreModeUtil {

    public static int getPrioritrNumber(UserCommonMGraphic mgraphicUserCommon, String specialPhoneNo) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        return getPriorityNumber(mgraphicUserCommon, specialPhoneNo, currentHour);
    }

    public static int getPriorityNumber(UserCommonMGraphic mgraphicUserCommon, String specialPhoneNo, int currentHour) {
        int priority = 1;
        int startTime =  0;// mgraphicUserCommon.getStartHour() == null ? 0 : mgraphicUserCommon.getStartHour();
        int endTime = 12;//mgraphicUserCommon.getEndHour() == null ? 24 : mgraphicUserCommon.getEndHour();

        if (specialPhoneNo != null && !"".equals(specialPhoneNo)
                && mgraphicUserCommon.getModeType() == 3) {
            priority += mgraphicUserCommon.getModeType() + 11;
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);

        }

        if (mgraphicUserCommon.getModeType() == 2) {
            priority += mgraphicUserCommon.getModeType();
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
        }

        if (mgraphicUserCommon.getModeType() == 1) {
            priority += mgraphicUserCommon.getModeType();
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
        }

        if (currentHour >= startTime && currentHour < endTime && mgraphicUserCommon.getModeType() == 4) {
            priority += 4;
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
        }

        return priority;
    }

    private static int updateTheTimelimitPriority(int currentHour, int priority, int startTime, int endTime) {
        if (currentHour >= startTime && currentHour < endTime) {
            priority += getPriorityByTime(currentHour, startTime, endTime);
        }
        return priority;
    }

    private static int getPriorityByTime(int currentHour, Integer startTime, Integer endTime) {
        int delatTime1 = currentHour - startTime;
        int delatTime2 = endTime - currentHour;
        int keyTime = delatTime1 <= delatTime2 ? delatTime1 : delatTime2;
        return 24 - keyTime;
    }
}
