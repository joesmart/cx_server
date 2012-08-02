package com.server.cx.service.cx;

import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.IdDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.server.cx.exception.SystemException;

public interface UserFavoritesService {

    public OperationDescription addNewUserFavorites(String imsi, IdDTO idDTO) throws SystemException;

    public OperationDescription deleteUserFavorites(String imsi, IdDTO idDTO) throws SystemException;

    public OperationDescription deleteUserFavoritesById(String imsi, String userFavoriteId) throws SystemException;

    public DataPage getAllUserFavorites(String imsi, Integer offset, Integer limit)
            throws SystemException;

}
