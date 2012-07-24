package com.server.cx.dao.cx;

import com.server.cx.entity.cx.MGraphicStoreMode;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface MGraphicStoreModeDao {
  public MGraphicStoreMode getDefaulModeUserCXInfo() throws SystemException;

  public List<MGraphicStoreMode> getAllMGraphicStoreModeByUserId(Long userId) throws SystemException;

  public MGraphicStoreMode getCurrentValidStatusMGraphicStoreMode(Long userId, Integer currentHour);

  public List<String> getIdOfTheSameMGraphicStoreMode(Long userId, MGraphicStoreMode mgraphicStoreMode)
      throws SystemException;
  
  public List<MGraphicStoreMode> getAllCatactsMGraphicStoreModes(Long userId);
  
  public void deleteUserAllStatus(Long userid);

  public MGraphicStoreMode getMGraphicStoreModeByModeType(Long userId, Integer modeType);
}
