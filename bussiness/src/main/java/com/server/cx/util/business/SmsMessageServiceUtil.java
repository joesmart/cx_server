package com.server.cx.util.business;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.server.cx.util.StringUtil;

import java.util.List;
import java.util.Set;


public class SmsMessageServiceUtil {

    public static List<String> getTheNotRegisterPhoneNoList(List<String> mobilesList, List<String> alreadyRegisterPhoneNos) {
        Set<String> allPhoneNoSet = Sets.newHashSet(mobilesList);
        Set<String> alreadyRegisteredPhoneNoSet = Sets.newHashSet(alreadyRegisterPhoneNos);

        SetView<String> notRegisterPhoneNoSet = Sets.difference(allPhoneNoSet, alreadyRegisteredPhoneNoSet);

        List<String> notRegisteredPhoneNoList = Lists.newArrayList();

        for (String tempPhoneNo : notRegisterPhoneNoSet) {
            notRegisteredPhoneNoList.add(tempPhoneNo);
        }
        return notRegisteredPhoneNoList;
    }

    public static List<String> generateSmsContent(List<String> mobileList, String userInfoPhoneNo) {
        List<String> contentList = Lists.newArrayList();
        String content;
        for (int i = 0; i < mobileList.size(); i++) {
            content = StringUtil.updateTheSmsContent(userInfoPhoneNo);
            contentList.add(content);
        }
        return contentList;
    }

}
