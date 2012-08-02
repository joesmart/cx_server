package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.MGraphicStoreMode;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface MGraphicStoreModeCustomDao {
    public MGraphicStoreMode getDefaultModeUserCXInfo() throws SystemException;

    public List<MGraphicStoreMode> getAllMGraphicStoreModeByUserId(String userId) throws SystemException;

    public MGraphicStoreMode getCurrentValidStatusMGraphicStoreMode(String userId, Integer currentHour);

    public List<String> getIdOfTheSameMGraphicStoreMode(Long userId, MGraphicStoreMode mgraphicStoreMode) throws SystemException;

    public List<MGraphicStoreMode> getAllContactsMGraphicStoreModes(String userId);

    public void deleteUserAllStatus(String userId);

    public MGraphicStoreMode getMGraphicStoreModeByModeType(String userId, Integer modeType);
}
