package com.server.cx.service.cx;

import com.server.cx.exception.SystemException;

public interface UserFavoritesService {

    public String addNewUserFavorites(String imsi, String cxInfoId) throws SystemException;

    public String deleteUserFavorites(String imsi, String userFavoritesId) throws SystemException;

    public String getAllUserFavorites(String imsi, String typeId, String requestPage, String pageSize)
            throws SystemException;

}
