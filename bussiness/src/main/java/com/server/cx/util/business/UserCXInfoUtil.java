package com.server.cx.util.business;

public class UserCXInfoUtil {


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
