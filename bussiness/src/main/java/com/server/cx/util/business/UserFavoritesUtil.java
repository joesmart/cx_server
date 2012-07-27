package com.server.cx.util.business;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class UserFavoritesUtil {

    public static List<Long> convertDigitStrignIntoLongList(String digitString, String splitString) {
        Iterable<String> userFavoritesIdStringList = Splitter.on(splitString).split(digitString);
        List<Long> userFavoritesIdLongList = Lists.newArrayList();
        for (String id : userFavoritesIdStringList) {
            userFavoritesIdLongList.add(Long.parseLong(id));
        }
        return userFavoritesIdLongList;
    }

}
