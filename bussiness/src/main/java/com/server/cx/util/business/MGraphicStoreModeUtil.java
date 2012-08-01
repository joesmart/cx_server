package com.server.cx.util.business;

import com.server.cx.entity.cx.MGraphicStoreMode;

import java.util.Calendar;

public class MGraphicStoreModeUtil {

    public static int getPrioritrNumber(MGraphicStoreMode mgraphicStoreMode, String specialPhoneNo) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        return getPriorityNumber(mgraphicStoreMode, specialPhoneNo, currentHour);
    }

    public static int getPriorityNumber(MGraphicStoreMode mgraphicStoreMode, String specialPhoneNo, int currentHour) {
        int priority = 1;
        int startTime = mgraphicStoreMode.getStartHour() == null ? 0 : mgraphicStoreMode.getStartHour();
        int endTime = mgraphicStoreMode.getEndHour() == null ? 24 : mgraphicStoreMode.getEndHour();

        if (specialPhoneNo != null && !"".equals(specialPhoneNo) && specialPhoneNo.equals(mgraphicStoreMode.getPhoneNo())
                && mgraphicStoreMode.getModeType() == 3) {
            priority += mgraphicStoreMode.getModeType() + 11;
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);

        }

        if (mgraphicStoreMode.getModeType() == 2) {
            priority += mgraphicStoreMode.getModeType();
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
        }

        if (mgraphicStoreMode.getModeType() == 1) {
            priority += mgraphicStoreMode.getModeType();
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
        }

        if (currentHour >= startTime && currentHour < endTime && mgraphicStoreMode.getModeType() == 4) {
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
