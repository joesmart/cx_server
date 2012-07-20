package com.server.cx.util.business;

import com.server.cx.entity.cx.UserCXInfo;

import java.util.Calendar;

public class UserCXInfoUtil{

    public static int getPrioritrNumber(UserCXInfo tempUserCXInfo, String specialPhoneNo){
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        return  getPriorityNumber(tempUserCXInfo, specialPhoneNo,currentHour);
    }
    
    public static int getPriorityNumber(UserCXInfo tempUserCXInfo, String specialPhoneNo,int currentHour) {
        int priority = 1;
        int startTime = tempUserCXInfo.getStartTime() == null?0:tempUserCXInfo.getStartTime();
        int endTime = tempUserCXInfo.getEndTime()==null?24:tempUserCXInfo.getEndTime();
        
        if(specialPhoneNo!=null && !"".equals(specialPhoneNo)&&specialPhoneNo.equals(tempUserCXInfo.getPhoneNo())&& tempUserCXInfo.getModeType()==3){
            priority+=tempUserCXInfo.getModeType()+11;
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
            
        }
        
        if(tempUserCXInfo.getModeType() ==2){
            priority += tempUserCXInfo.getModeType();
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
        }
        
        if(tempUserCXInfo.getModeType() == 1){
            priority += tempUserCXInfo.getModeType();
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
        }
        
        if(currentHour >= startTime && currentHour< endTime && tempUserCXInfo.getModeType() == 4){
            priority += 4;
            priority = updateTheTimelimitPriority(currentHour, priority, startTime, endTime);
        }
        
        return priority;
    }

    private static int updateTheTimelimitPriority(int currentHour, int priority, int startTime, int endTime) {
        if(currentHour >= startTime && currentHour< endTime){
            priority += getPriorityByTime(currentHour,startTime,endTime);
        }
        return priority;
    }

    private static int getPriorityByTime(int currentHour, Integer startTime, Integer endTime) {
        int delatTime1 = currentHour - startTime;
        int delatTime2 = endTime - currentHour;
        int keyTime =delatTime1<=delatTime2?delatTime1:delatTime2;
        return 24 - keyTime;
    }
}
