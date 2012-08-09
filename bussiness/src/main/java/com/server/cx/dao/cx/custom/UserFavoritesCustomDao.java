package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.UserFavorites;
import com.server.cx.entity.cx.UserInfo;

import java.util.List;

public interface UserFavoritesCustomDao {

    public boolean isAlreadAddedInUserFavorites(String userId, String graphicInfoId);

    public List<UserFavorites> getAllUserFavorites(String userid, int requestPage, int pageSize);

    public Integer getUserFavoritesTotalCount(String userId);

    public List<String> getGraphicIdListByUserInfo(UserInfo userInfo);
}
