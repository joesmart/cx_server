package com.server.cx.dao.cx;

import com.server.cx.entity.cx.UserCXInfo;
import com.server.cx.exception.SystemException;

import java.util.List;

public interface UserCXInfoDao {

  public List<UserCXInfo> loadUserCXInfoByImsi(String imsi);

  public UserCXInfo getDefaulModeUserCXInfo() throws SystemException;

  public List<UserCXInfo> getAllUserCXInfosByUserId(Long userId) throws SystemException;

  public Integer getCurrentUserCXInfoModeType(Long id);

  public UserCXInfo getStatusUserCXInfo(Integer type);

  public Long getCommonModeUserCXInfo(Long userId) throws SystemException;

  public int getUserCXInfosCountByModetype(Long userId, int modeType) throws SystemException;

  public List<Long> getIdOfTheSameUserCXInfo(Long userId, UserCXInfo userCXInfo) throws SystemException;
}
