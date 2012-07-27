package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.UserFavorites;

import java.util.List;

public interface UserFavoritesCustomDao {

    public boolean isAlreadAddedInUserFavorites(Long userId, String resourceId);

    public List<UserFavorites> getAllUserFavorites(Long userid, int requestPage, int pageSize);

    public Integer getUserFavoritesTotalCount(Long userId);
}
