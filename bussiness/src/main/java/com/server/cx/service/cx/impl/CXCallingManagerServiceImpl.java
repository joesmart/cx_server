package com.server.cx.service.cx.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.MGraphicStoreModeDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dto.Result;
import com.server.cx.dto.UserCXInfo;
import com.server.cx.entity.cx.MGraphicStoreMode;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.InvalidParameterException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CXCallingManagerService;
import com.server.cx.util.DateUtil;
import com.server.cx.util.StringUtil;
import com.server.cx.util.business.MGraphicStoreModeUtil;
import com.server.cx.util.business.ValidationUtil;
import com.server.cx.xml.util.XMLMarshalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("cxCallingManagerService")
@Transactional
public class CXCallingManagerServiceImpl implements CXCallingManagerService {

  @Autowired
  private MGraphicStoreModeDao mgraphicStoreModeDao;
  @Autowired
  private UserInfoDao userInfoDao;
  
  private UserInfo callerUserInfo;
  private UserInfo myselfInfo;
  private String callerPhoneNo;

  public CXCallingManagerServiceImpl() {}

  @Override
  public String getCallingCXInfo(Map<String, String> paramsMap) throws SystemException {
    String userPhoneNo = paramsMap.get(Constants.PHONE_NO_STR);
    ValidationUtil.checkParametersNotNull(userPhoneNo);
    Result xmlResult = new Result();
    List<UserCXInfo> list = getUserCXInfoInCall(paramsMap);
    if (list != null && list.size() > 0) {
      xmlResult.setFlag(Constants.SUCCESS_FLAG);
      xmlResult.setUserCXInfos(list);
    } else {
      xmlResult.setFlag(Constants.DATA_NOTFOUND_FLAG);
      xmlResult.setContent("数据找不到!");
    }

    XMLMarshalUtil marshalUtil = new XMLMarshalUtil(xmlResult);
    String result = marshalUtil.writeOut();
    return result;
  }

  private List<UserCXInfo> getUserCXInfoInCall(Map<String, String> params) throws SystemException {
    List<UserCXInfo> resultList = getTheCanllingCXInfoList(params);
    if (resultList != null && resultList.size() > 0) {
      return resultList;
    }
    return null;
  }

  // WORKAROUND 临时解决方案,需要重构
  private List<UserCXInfo> getTheCanllingCXInfoList(Map<String, String> params) throws SystemException {
    MGraphicStoreMode resultMGraphicStoreMode = null;
    callerPhoneNo = params.get(Constants.PHONE_NO_STR);
    String myselfPhoneNo = "";
    String imsi = params.get(Constants.IMSI_STR);

    Preconditions.checkNotNull(callerPhoneNo);
    Preconditions.checkNotNull(imsi);

    fillInUserInfo(imsi, callerPhoneNo);

    // 找不到该呼叫用户,说明该呼叫的用户未开通该业务.
    // TODO 需要验证当前用户有没有订阅主题包,如果有的话显示主题包,否则显示系统默认彩像.
    if (callerUserInfo == null) {
      resultMGraphicStoreMode = mgraphicStoreModeDao.getDefaulModeUserCXInfo();
      return generateResultByUserCXInfo(resultMGraphicStoreMode);
    }

    // TODO 显示状态彩像.
    // 返回状态彩像.
    resultMGraphicStoreMode = mgraphicStoreModeDao.getCurrentValidStatusMGraphicStoreMode(callerUserInfo.getId(), DateUtil.getCurrentHour());
    if(resultMGraphicStoreMode != null){
      return generateResultByUserCXInfo(resultMGraphicStoreMode); 
    }

    myselfPhoneNo = myselfInfo.getPhoneNo();

    // TODO 匹配时间模式,寻找最佳的匹配模式.
    List<MGraphicStoreMode> callerUserCXInfos =
        mgraphicStoreModeDao.getAllMGraphicStoreModeByUserId(callerUserInfo.getId());
    resultMGraphicStoreMode = filterOutTheUserCXInfo(callerUserCXInfos, myselfPhoneNo);

    // TODO 对方无设定任何彩像时,显示系统默认彩像.
    if (resultMGraphicStoreMode == null) {
      resultMGraphicStoreMode = mgraphicStoreModeDao.getDefaulModeUserCXInfo();
    }
    // generateARandomCXInfo(userCXInfo);
    return generateResultByUserCXInfo(resultMGraphicStoreMode);
  }

  private void fillInUserInfo(String imsi, String phoneNo) throws InvalidParameterException {
    callerUserInfo = null;
    callerPhoneNo = StringUtil.getPhoneNo(phoneNo);
    if (!ValidationUtil.isPhoneNo(callerPhoneNo)) {
      throw new InvalidParameterException("用户手机号码格式不对");
    }
    callerUserInfo = userInfoDao.getUserInfoByPhoneNo(callerPhoneNo);
    myselfInfo = userInfoDao.getUserInfoByImsi(imsi);
    if (myselfInfo == null) {
      throw new InvalidParameterException("用户未注册");
    }
  }

  private MGraphicStoreMode filterOutTheUserCXInfo(List<MGraphicStoreMode> userCXInfos, String specialPhoneNo) {
    MGraphicStoreMode result = null;
    Map<String, MGraphicStoreMode> map = Maps.newHashMap();
    int maxPriority = 0;
    int tempPriority = 0;
    if (userCXInfos == null || userCXInfos.size() == 0) {
      return null;
    } else {

      for (MGraphicStoreMode tempUserCXInfo : userCXInfos) {
        tempPriority = MGraphicStoreModeUtil.getPrioritrNumber(tempUserCXInfo, specialPhoneNo);
        if (tempPriority >= maxPriority) {
          maxPriority = tempPriority;
        }
        map.put(String.valueOf(tempPriority), tempUserCXInfo);
      }
    }
    if (maxPriority == 1) return null;

    // 没有设定一般模式并且时间模式和特定号码模式都超出时间限制
    result = map.get(String.valueOf(maxPriority));
    if (maxPriority - 1 == result.getModeType()) {
      return null;
    }

    return result;
  }

  private List<UserCXInfo> generateResultByUserCXInfo(MGraphicStoreMode mGraphicStoreMode) {
    List<UserCXInfo> result = Lists.newArrayList();
    if (mGraphicStoreMode != null) result.add(mGraphicStoreMode.convertMGraphicStoreModeToUserCXInfo());
    return result;
  }

}
