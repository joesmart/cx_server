package com.server.cx.util.business;

import java.sql.Timestamp;
import java.util.List;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.exception.SystemException;

public class ValidationUtil {

    public static boolean isVerionString(String version) {
        boolean result = isContainDigitAndSpecialCharacter(version, '.');
        return result;
    }

    public static boolean isMultiUserFavoritesId(String version) {
        boolean result = isContainDigitAndSpecialCharacter(version, ',');
        return result;
    }

    public static boolean isContainDigitAndSpecialCharacter(String version, Character c) {
        CharMatcher matcher = CharMatcher.DIGIT.or(CharMatcher.is(c));
        boolean result = matcher.matchesAllOf(version);
        return result;
    }

    public static boolean isDigit(String numberString) {
        boolean result = false;
        CharMatcher matcher = CharMatcher.DIGIT;
        result = matcher.matchesAllOf(numberString);
        return result;
    }

    public static <E> String join(List<E> list, String splitChar) {
        String result = Joiner.on(splitChar).join(list);
        return result;

    }

    public static List<String> split(String string, String splitChar) {

        Iterable<String> result = Splitter.on(splitChar).omitEmptyStrings().split(string);
        List<String> resultList = Lists.newArrayList(result);
        return resultList;
    }

    public static boolean isPhoneNo(String phoneNo) {
        return isContainDigitAndSpecialCharacter(phoneNo, '+')
            && ((phoneNo.length() >= 7 && phoneNo.length() <= 14) || (phoneNo.length() >= 3 && phoneNo.length() <= 6));
    }

    public static boolean isShortPhoneNo(String phoneNO) {
        if (isPhoneNo(phoneNO)) {
            return (phoneNO.length() <= 6 && phoneNO.length() >= 3) ;
        } else {
            return false;
        }
    }

    public static boolean isStatusValidTime(String validTime) {
        boolean isContainHour = validTime.contains("时");// isContainDigitAndSpecialCharacter(validTime,'时','分');
        boolean isContainMinuter = validTime.contains("分");//isContainDigitAndSpecialCharacter(validTime, '分');

        //WORKAROUND for test
        if (isContainHour == false && isContainMinuter == false) {
            isContainHour = validTime.contains("H");
            isContainMinuter = validTime.contains("M");
        }
        return (isContainHour && isContainMinuter);
        
    }

    public static boolean isCanEnableCurrentUserStatus(Timestamp begingTime, Timestamp endTime) {
        boolean result = false;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (currentTime.after(begingTime) && currentTime.before(endTime)) {
            result = true;
        }
        if (currentTime.equals(begingTime)) {
            result = true;
        }
        if (currentTime.equals(endTime)) {
            result = true;
        }
        return result;
    }

    public static <T> T[] checkParametersNotNull(T... parameters) throws SystemException {
        try {
            for (T temp : parameters) {
                Preconditions.checkNotNull(temp, "输入参数为空");
            }
        } catch (Exception e) {
            throw new InvalidParameterException(e.getMessage());
        }
        return parameters;
    }
}
