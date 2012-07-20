package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserFavorites;

import java.util.List;

public interface UserFavoritesDao {
    
    public boolean isAlreadAddedInUserFavorites(Long userId, Long cxInfoId);
    
    public List<UserFavorites> getAllUserFavorites(Long userid, String typeId, int requestPage, int pageSize);
    
    public Integer getUserFavoritesTotalCount(Long userId);
}
