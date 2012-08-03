package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.UserCommonMGraphic;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface UserCommonMGraphicCustomDao {
    public UserCommonMGraphic getDefaultModeUserCXInfo() throws SystemException;

    public List<UserCommonMGraphic> getAllMGraphicStoreModeByUserId(String userId) throws SystemException;

    public UserCommonMGraphic getCurrentValidStatusMGraphicStoreMode(String userId, Integer currentHour);

    public List<String> getIdOfTheSameMGraphicStoreMode(Long userId, UserCommonMGraphic mgraphicUserCommon) throws SystemException;

    public List<UserCommonMGraphic> getAllContactsMGraphicStoreModes(String userId);

    public void deleteUserAllStatus(String userId);

    public UserCommonMGraphic getMGraphicStoreModeByModeType(String userId, Integer modeType);
}
