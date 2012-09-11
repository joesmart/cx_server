package com.server.cx.util;

import com.google.common.base.CharMatcher;
import com.server.cx.constants.Constants;

public class StringUtil {

    //TODO use String format to refactor by Zou YanJian
    public static String updateTheSmsContent(String mobile) {
        String contentTemplate = Constants.SMS_CONTENT;
        CharMatcher question = CharMatcher.is('?');
        int position = question.indexIn(contentTemplate);
        contentTemplate = contentTemplate.substring(0, position) + mobile + contentTemplate.substring(position + 1);
        return contentTemplate;
    }


    public static String getPhoneNo(String phoneNo) {
        if (phoneNo.length() > 11) {
            phoneNo = phoneNo.substring(phoneNo.length() - 11);
        }
        return phoneNo;
    }

    public static boolean notNull(String str) {
        return !(str == null || "".equals(str));
    }
}
