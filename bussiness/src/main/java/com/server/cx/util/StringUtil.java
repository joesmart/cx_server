package com.server.cx.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.google.common.base.CharMatcher;
import com.server.cx.constants.Constants;
import com.server.cx.xml.Result;
import com.server.cx.xml.util.XMLMarshalUtil;

public class StringUtil {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss.S";

    public static String convertDateToString(Date date) {

        String result = convertDateToString(date, DEFAULT_PATTERN);
        return result;
    }

    public static String convertDateToString(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Date convertStringToDate(String dateString) {

        Date date = convertStringToDate(dateString, DEFAULT_PATTERN);
        return date;
    }

    private static Date convertStringToDate(String dateString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            Date date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateXMLResultString(String flag, String content) {
        String returnResult;

        Result result = new Result();
        result.setFlag(flag);
        result.setContent(content);
        XMLMarshalUtil xmlMarshalUtil = new XMLMarshalUtil(result);
        returnResult = xmlMarshalUtil.writeOut();
        return returnResult;
    }

    public static String generateAddUserCXInfoSuccesfulXMLResultString(String flag, String content, String graphicId,
                                                                       String mgraphicId) {
        String returnResult;

        Result result = new Result();
        result.setFlag(flag);
        result.setContent(content);
        result.setCxInfoId(String.valueOf(graphicId));
        result.setUserCXInfoId(String.valueOf(mgraphicId));
        XMLMarshalUtil xmlMarshalUtil = new XMLMarshalUtil(result);
        returnResult = xmlMarshalUtil.writeOut();
        return returnResult;
    }

    public static String updateTheSmsContent(String mobile) {
        String contentTemplate = Constants.SMS_CONTENT;
        CharMatcher question = CharMatcher.is('?');
        int possiton = question.indexIn(contentTemplate);
        contentTemplate = contentTemplate.substring(0, possiton) + mobile + contentTemplate.substring(possiton + 1);
        return contentTemplate;
    }

    public static Boolean isNeedToForceUpgrade(String clientVersion, String serverVersion) {

        return null;
    }

    public static String generateXMLResultFromObject(Object result) {
        XMLMarshalUtil xmlMarshalUtil = new XMLMarshalUtil(result);
        return xmlMarshalUtil.writeOut();
    }

    public static String convertTimeStampToString(Timestamp time) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.CHINESE);
        calendar.setTimeInMillis(time.getTime());
        // int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minuter = calendar.get(Calendar.MINUTE);
        String minuterString = String.valueOf(minuter);
        String hourString = String.valueOf(hour);
        if (minuter < 10) {
            minuterString = "0" + minuter;
        }
        if (hour < 10) {
            hourString = "0" + hourString;
        }
        String timeString = String.valueOf(month + 1) + "/" + String.valueOf(day) + " " + hourString + ":" + minuterString;
        return timeString;
    }

    public static String getPhoneNo(String phoneNo) {
        if (phoneNo.length() > 11) {
            phoneNo = phoneNo.substring(phoneNo.length() - 11);
        }
        return phoneNo;
    }
}
