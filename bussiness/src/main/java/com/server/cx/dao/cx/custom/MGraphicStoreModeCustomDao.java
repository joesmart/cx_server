package com.server.cx.dao.cx.custom;

import com.server.cx.entity.cx.MGraphicStoreMode;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface MGraphicStoreModeCustomDao {
    public MGraphicStoreMode getDefaulModeUserCXInfo() throws SystemException;

    public List<MGraphicStoreMode> getAllMGraphicStoreModeByUserId(String userId) throws SystemException;

    public MGraphicStoreMode getCurrentValidStatusMGraphicStoreMode(String userId, Integer currentHour);

    public List<String> getIdOfTheSameMGraphicStoreMode(Long userId, MGraphicStoreMode mgraphicStoreMode)
            throws SystemException;

    public List<MGraphicStoreMode> getAllCatactsMGraphicStoreModes(String userId);

    public void deleteUserAllStatus(Long userid);

    public MGraphicStoreMode getMGraphicStoreModeByModeType(String userId, Integer modeType);
}
